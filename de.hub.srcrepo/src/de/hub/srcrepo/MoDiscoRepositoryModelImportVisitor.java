package de.hub.srcrepo;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.kdm.source.extension.discovery.AbstractRegionDiscoverer2;
import org.eclipse.modisco.kdm.source.extension.discovery.SourceVisitListener;

import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.internal.SrcRepoBindingManager;
import de.hub.srcrepo.internal.SrcRepoMethodRedefinitionManager;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.JavaBindings;
import de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.MoDiscoImportState;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;

public class MoDiscoRepositoryModelImportVisitor implements IRepositoryModelVisitor, SourceVisitListener {

	protected final ISourceControlSystem sourceControlSystem;
	protected final JavaFactory javaFactory;
	protected final JavaPackage javaPackage;
	protected final Model javaModel;	
	protected final RepositoryModel repositoryModel;
	protected final RepositoryModelFactory repositoryFactory;

	// helper
	private final AbstractRegionDiscoverer2<Object> abstractRegionDiscoverer;

	// state
	private SrcRepoBindingManager currentJavaBindings;
	private HashMap<Rev, SrcRepoBindingManager> bindingsPerBranch = new HashMap<Rev, SrcRepoBindingManager>();
	
	// volatile
	private IProject[] allProjects;
	private Rev currentRev;
	private Map<ICompilationUnit, Diff> javaDiffsInCurrentRev = new HashMap<ICompilationUnit, Diff>();	
	private boolean updateJavaProjectStructureForMerge = false;
	private CompilationUnit importedCompilationUnit;	
	
	// statistics
	private int RevCounter = 0;
	private int importedCompilationUnits = 0;
	private int createdProjects = 0;
	private int knownProjects = 0;
	
	// constants
	private IJobManager jobManager;
	private IPath absoluteWorkingDirectoryPath;
	private Collection<String> javaLikeExtensions = new HashSet<String>();
	
	public MoDiscoRepositoryModelImportVisitor(ISourceControlSystem sourceControlSystem, RepositoryModel repositoryModel) {				
		this.javaModel = repositoryModel.getJavaModel();
		this.javaPackage = (JavaPackage)javaModel.eClass().getEPackage();
		this.javaFactory = (JavaFactory)javaPackage.getEFactoryInstance();
		
		this.sourceControlSystem = sourceControlSystem;
		this.repositoryFactory = (RepositoryModelFactory)repositoryModel.eClass().getEPackage().getEFactoryInstance();
		this.repositoryModel = repositoryModel;
		
	
		this.allProjects = new IProject[0];
		this.absoluteWorkingDirectoryPath = new Path(sourceControlSystem.getWorkingCopy().getAbsolutePath());
		Collections.addAll(javaLikeExtensions, JavaCore.getJavaLikeExtensions());
		
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

	}

	@Override
	public void loadState(TraversalState traversal) {
		MoDiscoImportState moDiscoImport = (MoDiscoImportState)traversal;
		JavaBindings bindings = moDiscoImport.getBindings();
		currentJavaBindings = new SrcRepoBindingManager(javaFactory, javaModel, bindings);
		bindingsPerBranch.clear();
		for (JavaBindingsPerBranch javaBindingsPerBranch: moDiscoImport.getBindingsPerBranch()) {
			bindingsPerBranch.put(javaBindingsPerBranch.getBranch(), new SrcRepoBindingManager(javaFactory, javaModel, javaBindingsPerBranch.getBindings()));
		}
	}



	@Override
	public void sourceRegionVisited(String filePath, int startOffset, int endOffset, int startLine, int endLine,
			EObject targetNode) {
		if (targetNode.eClass() == javaPackage.getCompilationUnit()) {
			importedCompilationUnit = (CompilationUnit) targetNode;
		}
	}	

	@Override
	public void onMerge(Rev mergeRev, Rev branchRev) {
		updateJavaProjectStructureForMerge = true;
		if (branchRev != null) {
			SrcRepoBindingManager branchBindings = bindingsPerBranch.get(branchRev);
			if (branchBindings == null && currentJavaBindings != null) {
				branchBindings = new SrcRepoBindingManager(currentJavaBindings);
				bindingsPerBranch.put(branchRev, branchBindings);
				SrcRepoActivator.INSTANCE.info("Visit first branch on branch ref " + branchRev.getName() + "[" + branchRev.getTime() +"]. Saving current bindings.");
			} else {
				SrcRepoActivator.INSTANCE.info("Visit next branch on branch ref " + branchRev.getName() + "[" + branchRev.getTime() +"]. Resetting bindings.");
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
				reportImportError(currentRev, "Unexpected error during job execution [" + job.getName() + "]", e, false);
			} else {
				reportImportError(currentRev, "Job with unexpected status [" + job.getName() + "]", null, false);
			}
		}
	}

	@Override
	public boolean onStartRev(final Rev ref) {		
	
		SrcRepoActivator.INSTANCE.info("Visit ref " + ref.getName() + " "   				
				+ "(" + ++RevCounter + "/" + repositoryModel.getAllRevs().size() + "), " 
				+ importedCompilationUnits + "/" + createdProjects + "/" + knownProjects);
		
		String author = ref.getAuthor(); author = author == null ? "[NO AUTHOR]" : author.trim();		
		String message = ref.getMessage(); message = message == null ? "[NO MESSAGE]" : message.trim();
		SrcRepoActivator.INSTANCE.info("Info for visited ref; " + ref.getTime() +", " + author + ":\n" + message);
		
		// move current ref to the new ref
		currentRev = ref;
		
		// checkout the new ref
		runJob(new CheckoutAndRefreshJob());		

		// check for new projects and refresh all java projects
		//runJob(new JavaRefreshJob());
		
		// clear the java diffs collected for the last ref
		javaDiffsInCurrentRev.clear();
		
		return true;
	}

	private static void moveBinding(String name, BindingManager source, BindingManager target) {
		NamedElement binding = source.getTarget(name);
		if (binding != null) {
			target.addTarget(name, binding);
		}
	}

	@Override
	public void onCompleteRev(Rev ref) {
		if (javaDiffsInCurrentRev.size() == 0) {
			return;
		}
		
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

		// start with fresh bindings for each ref. These are later merged
		// with the existing bindings from former Revs.
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
		runJob(new ImportJavaCompilationUnits(javaDiffsInCurrentRev, javaReader));		
		
		try {
			// resolve references
			SrcRepoActivator.INSTANCE.info("Resolving references...");
			// this is only necessary, if there was some java in the current revision.
			if (javaReader.getResultModel() != null) {
				// merge bindings and then resolve all references (indirectly via #terminate)
				SrcRepoBindingManager RevBindings = (SrcRepoBindingManager)javaReader.getGlobalBindings();
				if (currentJavaBindings == null) {
					currentJavaBindings = RevBindings;
				} else {
					currentJavaBindings.addBindings(RevBindings);
					javaReader.setGlobalBindings(currentJavaBindings);
				}
				javaReader.terminate(new NullProgressMonitor());
			}
		} catch (Exception e) {
			reportImportError(currentRev, "Could not finalize import of compilation units, some references are probably not resolved.", e, true);
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
		ICompilationUnit compilationUnit = getCompilationUnitForDiff(diff);
		if (compilationUnit != null) {
			javaDiffsInCurrentRev.put(compilationUnit, diff);			
		}		
	}

	@Override
	public void onDeletedFile(Diff diff) {
		
	}
	
	private boolean isNewOrModifiedProjectFileDiff(Diff diff) {
		return (diff.getNewPath().endsWith(".project") && new Path(diff.getNewPath()).lastSegment().equals(".project"));
	}
	
	private class CheckoutAndRefreshJob extends WorkspaceJob {
		
		public CheckoutAndRefreshJob() {
			super(MoDiscoRepositoryModelImportVisitor.class.getName() + " git checkout.");
		}		

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {		
			// checkout
			try {
				sourceControlSystem.checkoutRevision(currentRev.getName());				
			} catch (SourceControlException e) {							
				reportImportError(currentRev, "Error while checking out.", e, true);
			} catch (Exception e) {
				reportImportError(currentRev, "Exception while checking out.", e, true);
			} finally {								
				// refresh and remove deleted projects from workspace implicetely
				ProjectUtil.refreshValidProjects(allProjects, true, new NullProgressMonitor());
				
				boolean hasNewOrModifiedProjectFileDiff = false;
				loop: for (ParentRelation parentRelation: currentRev.getParentRelations()) {			
					for (Diff diff: parentRelation.getDiffs()) {
						if (isNewOrModifiedProjectFileDiff(diff)) {
							hasNewOrModifiedProjectFileDiff = true;
							break loop;
						}
					}			
				}	
				if (hasNewOrModifiedProjectFileDiff || updateJavaProjectStructureForMerge) {		
					Collection<File> actualProjectFiles = new HashSet<File>();
					ProjectUtil.findProjectFiles(actualProjectFiles,sourceControlSystem.getWorkingCopy(), null, new NullProgressMonitor());
					
					Collection<File> workspaceProjectFiles = new HashSet<File>();				
					for(IProject p: allProjects) {
						IPath projectLocation = p.getLocation();
						if (!p.isOpen() || projectLocation == null) {
							continue;
						}							
						String projectFilePath = projectLocation.append(IProjectDescription.DESCRIPTION_FILE_NAME).toOSString();
						File projectFile = new File(projectFilePath);
						workspaceProjectFiles.add(projectFile);
					}
					
					// create workspace projects for protentially new project files
					for(File actualProjectFile: actualProjectFiles) {
						if (!workspaceProjectFiles.contains(actualProjectFile)) {					
							IPath projectDescriptionFile = new Path(actualProjectFile.getAbsolutePath());
							IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(projectDescriptionFile); 
						    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());	
						    if (!project.exists()) {						  
							    try {
									project.create(description, null);
									if (!project.isOpen()) {
								    	project.open(null);
								    }		
									createdProjects++;
									SrcRepoActivator.INSTANCE.info("Created project " + actualProjectFile);
							    } catch (CoreException e) {
							    	reportImportError(currentRev, "Could not create project for project file " + projectDescriptionFile, e, true);
							    }
						    }
						}
					}
					
					allProjects = ProjectUtil.getValidOpenProjects(sourceControlSystem.getWorkingCopy());
					knownProjects = allProjects.length;
				}		
			}
			
			return Status.OK_STATUS;
		}		
	}
	
	public ICompilationUnit getCompilationUnitForDiff(Diff diff) {
		IPath relativeToWorkingDirectoryPath = new Path(diff.getNewPath());		
		String fileExtension = relativeToWorkingDirectoryPath.getFileExtension();		
		if (fileExtension != null && javaLikeExtensions.contains(fileExtension)) {
			// we only potentially have a compilation unit
			boolean hasSomeClosedProjects = false;
			for (IProject project: allProjects) {		
				if (!project.isOpen()) {
					hasSomeClosedProjects = true;
				} else {
					boolean hasJavaNature = false;
					try {
						hasJavaNature = project.isNatureEnabled(JavaCore.NATURE_ID);
					} catch (CoreException e) {
						reportImportError(currentRev, "Could not determine project nature. Assume non java project.", e, true);
					}
					if (hasJavaNature) {
						IPath projectPath = project.getLocation();
						projectPath = projectPath.makeRelativeTo(absoluteWorkingDirectoryPath);
						
						if (projectPath.isPrefixOf(relativeToWorkingDirectoryPath.makeAbsolute())) {
							relativeToWorkingDirectoryPath = relativeToWorkingDirectoryPath.makeRelativeTo(projectPath);
							IJavaProject javaProject = JavaCore.create(project);
							IResource resource = javaProject.getProject().findMember(relativeToWorkingDirectoryPath);
							IJavaElement element = JavaCore.create(resource, javaProject);
							if (element != null && element instanceof ICompilationUnit) {
								return (ICompilationUnit)element;							
							}
						}
					}
				}
			}
			if (hasSomeClosedProjects) {
				SrcRepoActivator.INSTANCE.warning("Resource " + relativeToWorkingDirectoryPath + " could not be found, and some projects are closed.");
			}			
		}
		
		return null;
	}
	
	private class ImportJavaCompilationUnits extends WorkspaceJob {
		
		private final Map<ICompilationUnit, Diff> javaDiffs;
		private final JavaReader javaReader;		

		public ImportJavaCompilationUnits(Map<ICompilationUnit, Diff> javaDiffs, JavaReader javaReader) {
			super(MoDiscoRepositoryModelImportVisitor.class.getName() + " import compilation units for current ref.");
			this.javaDiffs = javaDiffs;
			this.javaReader = javaReader;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {									
			// import diffs
			SrcRepoActivator.INSTANCE.info("about to import " + javaDiffs.size() + " compilation units");
			int count = 0;
			for(ICompilationUnit compilationUnit: javaDiffs.keySet()) {		
				SrcRepoActivator.INSTANCE.debug("import compilation unit " + compilationUnit.getPath());
				importedCompilationUnit = null;
				try {
					javaReader.readModel(compilationUnit, javaModel, new NullProgressMonitor());					
				} catch (Exception e) {
					if (e.getClass().getName().endsWith("AbortCompilation")) {
						reportImportError(currentRev, "Could not compile a compilation unit (is ignored): " + e.getMessage(), e, true);
						continue;
					} else {
						throw new RuntimeException(e);
					}
				}							
				
				if (importedCompilationUnit != null) {
					JavaCompilationUnitRef ref = repositoryFactory.createJavaCompilationUnitRef();
					ref.setCompilationUnit(importedCompilationUnit);
					javaDiffs.get(compilationUnit).setFile(ref);
					count++;
					importedCompilationUnits++;
				} else {
					reportImportError(currentRev, "Sucessfully imported a compilation unit, but no model was created: " + compilationUnit, null, true);
				}
			}										
				
			SrcRepoActivator.INSTANCE.info("imported " + count + " compilation units");
			return Status.OK_STATUS;
		}		
	}
	
	protected void reportImportError(EObject owner, String message, Throwable e, boolean controlledFail) {
		if (e != null) {
			message += ": " + e.getMessage() + "[" + e.getClass().getCanonicalName() + "].";
		}
		if (controlledFail) {			
			SrcRepoActivator.INSTANCE.warning(message, (Exception)e);
		} else {
			SrcRepoActivator.INSTANCE.error(message, (Exception)e);
		}
	}

	@Override
	public void saveState(TraversalState traversal) {
		MoDiscoImportState moDiscoImport = (MoDiscoImportState)traversal;
		JavaBindings bindings = moDiscoImport.getBindings();
		if (bindings == null) {
			bindings = repositoryFactory.createJavaBindings();
			moDiscoImport.setBindings(bindings);
		}
		bindings.getTargets().clear();
		bindings.getUnresolved().clear();
		currentJavaBindings.saveToBindingsModel(bindings);
	}

	@Override
	public void close() {
		
	}
	
	
}
