package de.hub.srcrepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jgit.api.CheckoutCommand.Stage;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.kdm.source.extension.discovery.AbstractRegionDiscoverer2;
import org.eclipse.modisco.kdm.source.extension.discovery.SourceVisitListener;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.JavaDiff;
import de.hub.srcrepo.gitmodel.ParentRelation;
import de.hub.srcrepo.gitmodel.SourceRepository;
import de.hub.srcrepo.modisco.SrcRepoBindingManager;
import de.hub.srcrepo.modisco.SrcRepoMethodRedefinitionManager;


public class MoDiscoGitModelImportVisitor implements IGitModelVisitor, SourceVisitListener {

	// parameter
	private final JavaFactory javaFactory;
	private final JavaPackage javaPackage;
	private final Model javaModel;
	private final SourceRepository gitModel;
	private final Git git;

	// helper
	private final AbstractRegionDiscoverer2<Object> abstractRegionDiscoverer;
	private final JavaProjectStructure javaProjectStructure;

	// state
	private JavaReader javaReader;
	private SrcRepoBindingManager javaBindings;
	private CompilationUnit lastCU;
	private Commit currentCommit;
	private int i = 0;
	
	private final String lastCommit;
	private boolean importCurrentBranch = true;

	public MoDiscoGitModelImportVisitor(Git git, SourceRepository gitModel, Model targetModel) {
		this(git, gitModel, targetModel, "");
	}
	
	public MoDiscoGitModelImportVisitor(Git git, SourceRepository gitModel, Model targetModel, String lastCommit) {
		super();
		
		this.lastCommit =  lastCommit;
		this.javaPackage = (JavaPackage)targetModel.eClass().getEPackage();
		this.javaFactory = (JavaFactory)javaPackage.getEFactoryInstance();
		this.gitModel = gitModel;
		this.javaModel = targetModel;
		this.git = git;
		this.javaProjectStructure = new JavaProjectStructure(new Path(git.getRepository().getWorkTree().getAbsolutePath()));
		

		abstractRegionDiscoverer = new AbstractRegionDiscoverer2<Object>() {
			@Override
			public boolean isApplicableTo(Object source) {
				return false;
			}

			@Override
			protected void basicDiscoverElement(Object source, IProgressMonitor monitor) throws DiscoveryException {
				throw new UnsupportedOperationException();
			}
		};
		abstractRegionDiscoverer.addSourceVisitListener(this);
	}

	@Override
	public void sourceRegionVisited(String filePath, int startOffset, int endOffset, int startLine, int endLine,
			EObject targetNode) {
		if (targetNode.eClass() == javaPackage.getCompilationUnit()) {
			lastCU = (CompilationUnit) targetNode;
		}
	}	

	@Override
	public void onMerge(Commit mergeCommit, Commit branchCommit) {
		if (branchCommit != null) {
			importCurrentBranch = false;
			reportImportError(branchCommit, "Ignoring a branch because only importing the first encounterd branch.", null, true);
		} else {
			importCurrentBranch = true;
		}
	}

	@Override
	public boolean onStartCommit(Commit commit) {
		if (commit.getName().equals(lastCommit) || !importCurrentBranch) {
			return false;
		}
		currentCommit = commit;
		
		SrcRepoActivator.INSTANCE.info("Visit commit " + commit.getName() + " (" + ++i + "/" + gitModel.getAllCommits().size() + ")");
		// checkout the corresponding revision and update the eclipse project
		try {
			git.reset().setMode(ResetType.HARD).call();
			git.checkout().setForce(true).setStage(Stage.THEIRS).setName(commit.getName()).call();
			javaProjectStructure.refresh();
		} catch (JGitInternalException e) {
			if (e.getMessage().contains("conflict")) {
				reportImportError(currentCommit, "Checkout with conflicts", e, false);
			} else {
				reportImportError(currentCommit, "Exception while checking out and updating IJavaProject", e, false);
			}			
		} catch (Exception e) {
			reportImportError(currentCommit, "Exception while checking out and updating IJavaProject", e, false);
		}

		// TODO one JavaReader instance should be enough
		// setup the JavaReader used to import the java model
		javaReader = new JavaReader(javaFactory, new HashMap<String, Object>(), abstractRegionDiscoverer) {
			@Override
			protected BindingManager getBindingManager() {
				return getGlobalBindings();
			}

			@Override			
			protected void resolveMethodRedefinition(final Model model) {
				// using our own MethodRedifnitionManager that only check the current compilation unit and not the whole model.
				EList<CompilationUnit> compilationUnits = model.getCompilationUnits();
				CompilationUnit compilationUnit = compilationUnits.get(compilationUnits.size() - 1);
				SrcRepoMethodRedefinitionManager.resolveMethodRedefinitions(compilationUnit, javaFactory);
			}
			
		};
		javaReader.setDeepAnalysis(true);
		javaReader.setIncremental(false);

		// start with fresh bindings for each commit. These are later merged
		// with the existing bindings from former commits.
		SrcRepoBindingManager bindings = new SrcRepoBindingManager(javaFactory);
		// resuse existing primitive types and packages
		if (javaBindings != null) {
			bindings.addPackageBindings(javaBindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.INT.toString(), javaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.LONG.toString(), javaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.FLOAT.toString(), javaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.DOUBLE.toString(), javaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.BOOLEAN.toString(), javaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.VOID.toString(), javaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.CHAR.toString(), javaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.SHORT.toString(), javaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.BYTE.toString(), javaBindings, bindings);
		}

		javaReader.setGlobalBindings(bindings);
		if (javaReader.isIncremental()) {
			javaReader.getGlobalBindings().enableIncrementalDiscovering(javaModel);
		}
		
		boolean hasProjectFileDiff = false;
		loop: for (ParentRelation parentRelation: commit.getParentRelations()) {			
			for (Diff diff: parentRelation.getDiffs()) {
				if (isProjectFileDiff(diff)) {
					hasProjectFileDiff = true;
					break loop;
				}
			}			
		}	
		if (hasProjectFileDiff) {
			try {
				javaProjectStructure.update();
			} catch (Exception e) {
				reportImportError(currentCommit, "Exception during updating a project structure.", e, false);
			}
		}

		return true;
	}

	private static void moveBinding(String name, BindingManager source, BindingManager target) {
		NamedElement binding = source.getTarget(name);
		if (binding != null) {
			target.addTarget(name, binding);
		}
	}

	@Override
	public void onCompleteCommit(Commit commit) {
		// this is only necessary, if there was some java in the current revision.
		if (javaReader.getResultModel() != null) {
			try {
				// merge bindings and then resolve all references (indirectly via
				// #terminate)
				SrcRepoBindingManager commitBindings = (SrcRepoBindingManager)javaReader.getGlobalBindings();
				if (javaBindings == null) {
					javaBindings = commitBindings;
				} else {
					javaBindings.addBindings(commitBindings);
					javaReader.setGlobalBindings(javaBindings);
				}
				javaReader.terminate(new NullProgressMonitor());
			} catch (Exception e) {
				reportImportError(currentCommit, "Exception on resolving references at the end of processing a commit.", e, false);
			}
		}
	}

	@Override
	public void onCopiedFile(Diff diff) {

	}

	@Override
	public void onRenamedFile(Diff diff) {

	}

	@Override
	public void onAddedFile(Diff diff) {
		onModifiedFile(diff);
	}

	@Override
	public void onModifiedFile(Diff diff) {
		if (diff instanceof JavaDiff) {
			JavaDiff javaDiff = (JavaDiff) diff;
			IPath path = new Path(javaDiff.getNewPath());
			ICompilationUnit compilationUnit = javaProjectStructure.getCompilationUnitForPath(path);
			
			if (compilationUnit != null) {
				lastCU = null;
				SrcRepoActivator.INSTANCE.info("import compilation unit " + compilationUnit.getPath());
				try {
					javaReader.readModel(compilationUnit, javaModel, new NullProgressMonitor());
				} catch (Exception e) {
					if (e.getClass().getName().endsWith("AbortCompilation")) {
						reportImportError(currentCommit, "Could not compile a compilation unit (is ignored): " + e.getMessage(), e, true);
					} else {
						throw new RuntimeException(e);
					}
				}
				if (lastCU != null) {
					javaDiff.setCompilationUnit(lastCU);
				} else {
					reportImportError(currentCommit, "Reading comilation unit did not result in a CompilationUnit model for " + path, null, true);
				}
			} else {
				reportImportError(currentCommit, "Could not find a compilation unit at JavaDiff path " + path, null, true);
			}
		} 
	}

	@Override
	public void onDeletedFile(Diff diff) {
		
	}
	
	private boolean isProjectFileDiff(Diff diff) {
		return diff.getNewPath().endsWith(".project") && new Path(diff.getNewPath()).lastSegment().equals(".project");
	}
	
	private class JavaProjectStructure {
		private static final String METADATA_FOLDER = ".metadata";
		
		private final Map<IPath, IJavaProject> javaProjects = new HashMap<IPath, IJavaProject>();
		private final IPath rootPath;
		
		public JavaProjectStructure(IPath rootPath) {
			this.rootPath = rootPath;
		}
		
		public void update() {
			javaProjects.clear();
			List<File> projectFiles = new ArrayList<File>();
			collectProjectFilesFromDirectory(projectFiles, rootPath.toFile(), null);
			for (File projectFile: projectFiles) {
				try {
					IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path(projectFile.getAbsolutePath())); 
				    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
				    if (!project.exists()) {
				    	project.create(description, null);
				    }
				    if (!project.isOpen()) {
				    	project.open(null);
				    }
	
				    IJavaProject javaProject = JavaCore.create(project);
				    if (javaProject.exists()) {
					    if (!javaProject.isOpen()) {
							javaProject.open(new NullProgressMonitor());
					    				
						}
					    
					    IPath projectPath = new Path(javaProject.getProject().getDescription().getLocationURI().getPath());
					    projectPath = projectPath.makeRelativeTo(rootPath);
					    
					    javaProjects.put(projectPath, javaProject);
				    } else {
				    	// this is actually not an error, if these projects are indeed no java projects
				    	reportImportError(currentCommit, "The project " + projectFile.getAbsolutePath() + " is not a java project", null, true);	
				    }
				} catch (CoreException e) {
					reportImportError(currentCommit, "Exception during importing a project into workspace, the project is ignored.", e, true);
				}				
			}
		}
		
		public ICompilationUnit getCompilationUnitForPath(IPath relativePath) {			

			for (IPath projectPath: javaProjects.keySet()) {
				if (projectPath.isPrefixOf(relativePath)) {
					relativePath = relativePath.makeRelativeTo(projectPath);
					IJavaProject javaProject = javaProjects.get(projectPath);
					IResource resource = javaProject.getProject().findMember(relativePath);
					IJavaElement element = JavaCore.create(resource, javaProject);
					
					if (element != null && element instanceof ICompilationUnit) {
						return (ICompilationUnit) element;
					}
				}
			}
			
			return null;
		}
		
		public void refresh() throws CoreException {
			for (IJavaProject javaProject: javaProjects.values()) {
				javaProject.getProject().refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
			}
		}
		
		/**
		 * Collect the list of .project files that are under directory into files.
		 * 
		 * @param files
		 * @param directory
		 * @param directoriesVisited
		 * 		Set of canonical paths of directories, used as recursion guard
		 * @param monitor
		 * 		The monitor to report to
		 * @return boolean <code>true</code> if the operation was completed.
		 */
		private boolean collectProjectFilesFromDirectory(Collection<File> files,
				File directory, Set<String> directoriesVisited) {
			File[] contents = directory.listFiles();
			if (contents == null)
				return false;

			// Initialize recursion guard for recursive symbolic links
			if (directoriesVisited == null) {
				directoriesVisited = new HashSet<String>();
				try {
					directoriesVisited.add(directory.getCanonicalPath());
				} catch (IOException exception) {
					reportImportError(currentCommit, "Could not get path for directory in working copy. Not all directories are checked for projects.", exception, false);
				}
			}

			// first look for project description files
			final String dotProject = IProjectDescription.DESCRIPTION_FILE_NAME;
			for (int i = 0; i < contents.length; i++) {
				File file = contents[i];
				if (file.isFile() && file.getName().equals(dotProject)) {
					files.add(file);
					// don't search sub-directories since we can't have nested
					// projects
					return true;
				}
			}
			// no project description found, so recurse into sub-directories
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isDirectory()) {
					if (!contents[i].getName().equals(METADATA_FOLDER)) {
						try {
							String canonicalPath = contents[i].getCanonicalPath();
							if (!directoriesVisited.add(canonicalPath)) {
								// already been here --> do not recurse
								continue;
							}
						} catch (IOException exception) {
							reportImportError(currentCommit, "Could not get path for directory in working copy. Not all directories are checked for projects.", exception, false);
						}
						collectProjectFilesFromDirectory(files, contents[i], directoriesVisited);
					}
				}
			}
			return true;
		}
	}
	
	protected void reportImportError(EObject owner, String message, Exception e, boolean controlledFail) {
		if (e != null) {
			message += ": " + e.getMessage() + "[" + e.getClass().getCanonicalName() + "]";
		}
		if (controlledFail) {			
			SrcRepoActivator.INSTANCE.warning(message, e);
		} else {
			SrcRepoActivator.INSTANCE.error(message, e);
		}
	}
}
