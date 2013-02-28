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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.gmt.modisco.java.emffrag.impl.JavaFactoryImpl;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jgit.api.Git;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.kdm.source.extension.discovery.AbstractRegionDiscoverer2;
import org.eclipse.modisco.kdm.source.extension.discovery.SourceVisitListener;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.JavaDiff;
import de.hub.srcrepo.gitmodel.ParentRelation;


public class MoDiscoGitModelImportVisitor implements IGitModelVisitor, SourceVisitListener {

	// parameter
	private final Model javaModel;
	private final Git git;

	// helper
	private final AbstractRegionDiscoverer2<Object> abstractRegionDiscoverer;
	private final JavaProjectStructure javaProjectStructure;

	// state
	private JavaReader javaReader;
	private SrcRepoBindingManager javaBindings;
	private CompilationUnit lastCU;

	public MoDiscoGitModelImportVisitor(Git git, Model targetModel) {
		super();
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
		if (targetNode.eClass() == JavaPackage.eINSTANCE.getCompilationUnit()) {
			lastCU = (CompilationUnit) targetNode;
		}
	}

	@Override
	public boolean onStartCommit(Commit commit) {
		// if
		// (commit.getName().equals("98f56da6e548af6d5660f0c42726a5cde17f23e3"))
		// {
		// return false;
		// }

		SrcRepoActivator.INSTANCE.info("Visit commit " + commit.getName());
		// checkout the corresponding revision and update the eclipse project
		try {
			git.checkout().setName(commit.getName()).call();
			javaProjectStructure.refresh();
		} catch (Exception e) {
			SrcRepoActivator.INSTANCE.error("Exception while checking out and updating IJavaProject", e);
		}

		// TODO one JavaReader instance should be enough
		// setup the JavaReader used to import the java model
		javaReader = new JavaReader(JavaFactoryImpl.init(), new HashMap<String, Object>(), abstractRegionDiscoverer) {
			@Override
			protected BindingManager getBindingManager() {
				return getGlobalBindings();
			}
		};
		javaReader.setDeepAnalysis(true);
		javaReader.setIncremental(false);

		// start with fresh bindings for each commit. These are later merged
		// with the existing bindings from former commits.
		SrcRepoBindingManager bindings = new SrcRepoBindingManager(JavaFactoryImpl.init());
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
		
		loop: for (ParentRelation parentRelation: commit.getParentRelations()) {
			for (Diff diff: parentRelation.getDiffs()) {
				if (isProjectFileDiff(diff)) {
					javaProjectStructure.update();
					break loop;
				}
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
				javaReader.readModel(compilationUnit, javaModel, new NullProgressMonitor());
				if (lastCU != null) {
					javaDiff.setCompilationUnit(lastCU);
				} else {
					SrcRepoActivator.INSTANCE.error("Reading comilation unit did not result in a CompilationUnit model for " + path);
				}
			} else {
				SrcRepoActivator.INSTANCE.error("Could not find a compilation unit at JavaDiff path " + path);
			}
		} 
	}

	@Override
	public void onDeletedFile(Diff diff) {
		
	}
	
	private boolean isProjectFileDiff(Diff diff) {
		return diff.getNewPath().endsWith(".project") && new Path(diff.getNewPath()).lastSegment().equals(".project");
	}
	
	private static class JavaProjectStructure {
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
				    if (!javaProject.isOpen()) {
						javaProject.open(new NullProgressMonitor());
				    				
					}
				    
				    IPath projectPath = new Path(javaProject.getProject().getDescription().getLocationURI().getPath());
				    projectPath = projectPath.makeRelativeTo(rootPath);
				    
				    javaProjects.put(projectPath, javaProject);
				} catch (CoreException e) {
					SrcRepoActivator.INSTANCE.error("Exception during importing a project into workspace, the project is ignored.", e);
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
					SrcRepoActivator.INSTANCE.error("Could not get path for directory in working copy. Not all directories are checked for projects.", exception);
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
							SrcRepoActivator.INSTANCE.error("Could not get path for directory in working copy. Not all directories are checked for projects.", exception);
						}
						collectProjectFilesFromDirectory(files, contents[i], directoriesVisited);
					}
				}
			}
			return true;
		}
	}

}
