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
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
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
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.LockFailedException;
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
	private SrcRepoBindingManager currentJavaBindings;
	private HashMap<Commit, SrcRepoBindingManager> bindingsPerBranch = new HashMap<Commit, SrcRepoBindingManager>();
	private CompilationUnit lastCU;
	private Commit currentCommit;
	private List<JavaDiff> javaDiffsInCurrentCommit = new ArrayList<JavaDiff>();
	private int i = 0;
	private IJobManager jobManager;
	
	private final String lastCommit;

	public MoDiscoGitModelImportVisitor(Git git, SourceRepository gitModel, Model targetModel) {
		this(git, gitModel, targetModel, "");
	}
	
	public MoDiscoGitModelImportVisitor(Git git, SourceRepository gitModel, Model targetModel, String lastCommit) {
		super();
		this.jobManager = Job.getJobManager();	
		
		jobManager.addJobChangeListener(new IJobChangeListener() {
			
			private void logEvent(String message, IJobChangeEvent event) {
				Job job = event.getJob();
				if (job == null) {
					SrcRepoActivator.INSTANCE.info(message + ", but there is no job info.");
				} else {
					ISchedulingRule rule = job.getRule();
					if (rule != null) {
						SrcRepoActivator.INSTANCE.debug(message + ": " + job.getName() + ", " + rule.isConflicting(ResourcesPlugin.getWorkspace().getRoot()));	
					} else {
						SrcRepoActivator.INSTANCE.debug(message + ": " + job.getName() + ", with no rule");
					}
				}								
			}
			
			@Override
			public void sleeping(IJobChangeEvent event) {
			
			}
			
			@Override
			public void scheduled(IJobChangeEvent event) {
				logEvent("Eclipse scheduled a job", event);
			}
			
			@Override
			public void running(IJobChangeEvent event) {
				
			}
			
			@Override
			public void done(IJobChangeEvent event) {
				logEvent("Eclipse is done with a job", event);
			}
			
			@Override
			public void awake(IJobChangeEvent event) {
				
			}
			
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				logEvent("Eclipse is about to run a job", event);
			}
		});
		
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
			SrcRepoBindingManager branchBindings = bindingsPerBranch.get(branchCommit);
			if (branchBindings == null && currentJavaBindings != null) {
				branchBindings = new SrcRepoBindingManager(currentJavaBindings);
				bindingsPerBranch.put(branchCommit, branchBindings);
				SrcRepoActivator.INSTANCE.info("Visit first branch on branch commit " + branchCommit.getName() + "[" + branchCommit.getTime() +"]. Saving current bindings.");
			} else {
				SrcRepoActivator.INSTANCE.info("Visit next branch on branch commit " + branchCommit.getName() + "[" + branchCommit.getTime() +"]. Resetting bindings.");
			}
			currentJavaBindings = branchBindings;
		} else {
			currentJavaBindings = null;
		}
	}
	
	private void runJob(WorkspaceJob job) {
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			new RuntimeException("Interupt during job execution. Impossible.", e);
		}
		IStatus result = job.getResult();
		if (result == null) {
			throw new RuntimeException("Job without result after joined execution. Impossible.");
		}
		if (!result.isOK()) {
			if (result.matches(IStatus.ERROR)) {
				Throwable e = result.getException();
				reportImportError(currentCommit, "Unexpected error during job execution [" + job.getName() + "]", e, false);
			} else {
				reportImportError(currentCommit, "Job with unexpected status [" + job.getName() + "]", null, false);
			}
		}
	}

	@Override
	public boolean onStartCommit(final Commit commit) {		
		if (commit.getName().equals(lastCommit)) {
			return false;
		}
		
		SrcRepoActivator.INSTANCE.info("Visit commit " + commit.getName() + "[" + commit.getTime() +"] (" + ++i + "/" + gitModel.getAllCommits().size() + ")");
		
		// move current commit to the new commit
		currentCommit = commit;
		
		// checkout the new commit
		runJob(new CheckoutJob());		
		
		// check for new projects and refresh all java projects
		runJob(new JavaRefreshJob());
		
		// clear the java diffs collected for the last commit
		javaDiffsInCurrentCommit.clear();
		
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
		// setup the JavaReader used to import the java model
		// TODO one JavaReader instance should be enough
		JavaReader javaReader = new JavaReader(javaFactory, new HashMap<String, Object>(), abstractRegionDiscoverer) {
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
		if (currentJavaBindings != null) {
			bindings.addPackageBindings(currentJavaBindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.INT.toString(), currentJavaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.LONG.toString(), currentJavaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.FLOAT.toString(), currentJavaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.DOUBLE.toString(), currentJavaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.BOOLEAN.toString(), currentJavaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.VOID.toString(), currentJavaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.CHAR.toString(), currentJavaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.SHORT.toString(), currentJavaBindings, bindings);
			moveBinding(org.eclipse.jdt.core.dom.PrimitiveType.BYTE.toString(), currentJavaBindings, bindings);
		}

		javaReader.setGlobalBindings(bindings);
		if (javaReader.isIncremental()) {
			javaReader.getGlobalBindings().enableIncrementalDiscovering(javaModel);
		}						
		
		// import all collected diffs and resolve references
		runJob(new ImportJavaCompilationUnits(javaDiffsInCurrentCommit, javaReader));		
		
		// resolve references
		// this is only necessary, if there was some java in the current revision.
		if (javaReader.getResultModel() != null) {
			// merge bindings and then resolve all references (indirectly via #terminate)
			SrcRepoBindingManager commitBindings = (SrcRepoBindingManager)javaReader.getGlobalBindings();
			if (currentJavaBindings == null) {
				currentJavaBindings = commitBindings;
			} else {
				currentJavaBindings.addBindings(commitBindings);
				javaReader.setGlobalBindings(currentJavaBindings);
			}
			javaReader.terminate(new NullProgressMonitor());
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
	public void onModifiedFile(final Diff diff) {
		// collect java diffs for later import see #onCompletedCommit
		if (diff instanceof JavaDiff) {
			javaDiffsInCurrentCommit.add((JavaDiff)diff);
		}
	}

	@Override
	public void onDeletedFile(Diff diff) {
		
	}
	
	private boolean isProjectFileDiff(Diff diff) {
		return (diff.getNewPath().endsWith(".project") && new Path(diff.getNewPath()).lastSegment().equals(".project")) ||
				(diff.getOldPath().endsWith(".project") && new Path(diff.getOldPath()).lastSegment().equals(".project"));
	}
	
	private class CheckoutJob extends WorkspaceJob {
		
		public CheckoutJob() {
			super(MoDiscoGitModelImportVisitor.class.getName() + " git checkout.");
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			try {
				// remove a possible lock file from prior errors or crashes
				File lockFile = new File(git.getRepository().getWorkTree().getPath() + "/.git/index.lock");
				if (lockFile.exists()) {
					SrcRepoActivator.INSTANCE.info("Have to remove git lock file.");
					lockFile.delete();
				}
				// clean the working tree from ignored or other untracked files
				git.clean().setCleanDirectories(true).setIgnore(false).setDryRun(false).call();
				// reset possible changes
				git.reset().setMode(ResetType.HARD).call();
				// checkout the new revision
				git.checkout().setForce(true).setName(currentCommit.getName()).call();					
			} catch (JGitInternalException e) {
				if (e.getCause() instanceof LockFailedException) {
					// TODO proper reaction
					reportImportError(currentCommit, "Exception while checking out and updating IJavaProject", e, true);
				} if (e.getMessage().contains("conflict")) {
					reportImportError(currentCommit, "Checkout with conflicts", e, true);
				} else {				
					reportImportError(currentCommit, "Exception while checking out and updating IJavaProject", e, true);
				}			
			} catch (GitAPIException e) {
				reportImportError(currentCommit, "Exception while checking out and updating IJavaProject", e, true);
			}
			
			return Status.OK_STATUS;
		}		
	}
	
	private class JavaRefreshJob extends WorkspaceJob {
			
		public JavaRefreshJob() {
			super(MoDiscoGitModelImportVisitor.class.getName() + " refresh java projects.");
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				boolean hasProjectFileDiff = false;
				loop: for (ParentRelation parentRelation: currentCommit.getParentRelations()) {			
					for (Diff diff: parentRelation.getDiffs()) {
						if (isProjectFileDiff(diff)) {
							hasProjectFileDiff = true;
							break loop;
						}
					}			
				}	
				if (hasProjectFileDiff) {
					SrcRepoActivator.INSTANCE.info("Update a project due to project file change.");
					javaProjectStructure.update();										
				}
				javaProjectStructure.refresh();									
			
			return Status.OK_STATUS;
		}		
	}
	
	private class ImportJavaCompilationUnits extends WorkspaceJob {
		
		private final Collection<JavaDiff> javaDiffs;
		private final JavaReader javaReader;		

		public ImportJavaCompilationUnits(Collection<JavaDiff> javaDiffs, JavaReader javaReader) {
			super(MoDiscoGitModelImportVisitor.class.getName() + " import compilation units for current commit.");
			this.javaDiffs = javaDiffs;
			this.javaReader = javaReader;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {						
			
			
			// import diffs
			for(JavaDiff javaDiff: javaDiffs) {
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
			
			return Status.OK_STATUS;
		}		
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
				javaProject.getProject().refreshLocal(IProject.DEPTH_INFINITE, null);
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
	
	protected void reportImportError(EObject owner, String message, Throwable e, boolean controlledFail) {
		StringBuffer jobDescription = new StringBuffer("");
		for(Job job: jobManager.find(null)) {
			if (jobDescription.length() > 0) {
				jobDescription.append(", ");
			}
			jobDescription.append(job.getName() + "(" + job.getState() + ")");
		}
		
		if (e != null) {
			message += ": " + e.getMessage() + "[" + e.getClass().getCanonicalName() + "]; jobs are " + jobDescription.toString();
		}
		if (controlledFail) {			
			SrcRepoActivator.INSTANCE.warning(message, (Exception)e);
		} else {
			SrcRepoActivator.INSTANCE.error(message, (Exception)e);
		}
	}
}
