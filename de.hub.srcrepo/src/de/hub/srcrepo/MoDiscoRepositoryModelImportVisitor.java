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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement;

import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.internal.SrcRepoBindingManager;
import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Target;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;

public class MoDiscoRepositoryModelImportVisitor implements IRepositoryModelVisitor, IRepositoryModelVisitor.ETMExtension {

	protected final ISourceControlSystem sourceControlSystem;
	protected final JavaFactory javaFactory;
	protected final JavaPackage javaPackage;
	protected final RepositoryModel repositoryModel;
	protected final RepositoryModelFactory repositoryFactory;
	
	private EtmMonitor etmMonitor = null;
	
	// volatile
	private IProject[] allProjects;
	private Rev currentRev;
	private Map<ICompilationUnit, Diff> javaDiffsInCurrentRev = new HashMap<ICompilationUnit, Diff>();	
	private boolean updateJavaProjectStructureForMerge = false;	
	
	// statistics
	private int importedCompilationUnits = 0;
	private int createdProjects = 0;
	private int knownProjects = 0;
	
	// constants
	private IPath absoluteWorkingDirectoryPath;
	private Collection<String> javaLikeExtensions = new HashSet<String>();
	
	public MoDiscoRepositoryModelImportVisitor(ISourceControlSystem sourceControlSystem, RepositoryModel repositoryModel, JavaPackage javaPackage) {
		this.javaPackage = javaPackage;
		this.javaFactory = (JavaFactory)javaPackage.getEFactoryInstance();
		
		this.sourceControlSystem = sourceControlSystem;
		this.repositoryFactory = (RepositoryModelFactory)repositoryModel.eClass().getEPackage().getEFactoryInstance();
		this.repositoryModel = repositoryModel;
		
	
		this.allProjects = new IProject[0];
		this.absoluteWorkingDirectoryPath = new Path(sourceControlSystem.getWorkingCopy().getAbsolutePath());
		Collections.addAll(javaLikeExtensions, JavaCore.getJavaLikeExtensions());
	}

	@Override
	public void setETMMonitor(EtmMonitor monitor) {
		this.etmMonitor = monitor;
	}



	@Override
	public void onMerge(Rev mergeRev, Rev branchRev) {
		updateJavaProjectStructureForMerge = true;
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
	public boolean onStartRev(final Rev rev, int number) {		
	
		SrcRepoActivator.INSTANCE.info("Visit rev " + rev.getName() + " "   				
				+ "(" + number + "/" + repositoryModel.getAllRevs().size() + "), " 
				+ importedCompilationUnits + "/" + createdProjects + "/" + knownProjects);
		
		String author = rev.getAuthor(); author = author == null ? "[NO AUTHOR]" : author.trim();		
		String message = rev.getMessage(); message = message == null ? "[NO MESSAGE]" : message.trim();
		SrcRepoActivator.INSTANCE.info("Info for visited ref; " + rev.getTime() +", " + author + ":\n" + message);
		
		// move current ref to the new ref
		currentRev = rev;
		
		// checkout the new ref
		runJob(new CheckoutAndRefreshJob());		

		// check for new projects and refresh all java projects
		//runJob(new JavaRefreshJob());
		
		// clear the java diffs collected for the last ref
		javaDiffsInCurrentRev.clear();
		
		return true;
	}

	@Override
	public void onCompleteRev(Rev rev) {
		if (javaDiffsInCurrentRev.size() == 0) {
			return;
		}
		
		runJob(new ImportJavaCompilationUnits(javaDiffsInCurrentRev));		
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
			EtmPoint point = etmMonitor.createPoint("Rev:checkout");
			try {				
				sourceControlSystem.checkoutRevision(currentRev.getName());				
			} catch (SourceControlException e) {							
				reportImportError(currentRev, "Error while checking out.", e, true);
			} catch (Exception e) {
				reportImportError(currentRev, "Exception while checking out.", e, true);
			} finally {
				point.collect();
				
				point = etmMonitor.createPoint("Rev:refresh");
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
				point.collect();
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

		public ImportJavaCompilationUnits(Map<ICompilationUnit, Diff> javaDiffs) {
			super(MoDiscoRepositoryModelImportVisitor.class.getName() + " import compilation units for current ref.");
			this.javaDiffs = javaDiffs;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {									
			// import diffs
			SrcRepoActivator.INSTANCE.info("about to import " + javaDiffs.size() + " compilation units");
			int count = 0;
			for(ICompilationUnit compilationUnit: javaDiffs.keySet()) {
				SrcRepoBindingManager bindings = new SrcRepoBindingManager(javaFactory);
				// TODO reuse javaReader and Discover...
				JavaReader javaReader = new JavaReader(javaFactory, new HashMap<String, Object>(), null);
				javaReader.setGlobalBindings(bindings);
				javaReader.setDeepAnalysis(false);
				javaReader.setIncremental(false);
				Model javaModel = javaFactory.createModel();
				SrcRepoActivator.INSTANCE.debug("import compilation unit " + compilationUnit.getPath());
				try {
					javaReader.readModel(compilationUnit, javaModel, bindings, new NullProgressMonitor());		
				} catch (Exception e) {
					if (e.getClass().getName().endsWith("AbortCompilation")) {
						reportImportError(currentRev, "Could not compile a compilation unit (is ignored): " + e.getMessage(), e, true);
						EcoreUtil.delete(javaModel);
						continue;
					} else {
						EcoreUtil.delete(javaModel);
						throw new RuntimeException(e);
					}
				}							
				
				if (javaModel.getCompilationUnits().size() == 1) {
					// check if a new top level class was imported (and not only the compilation unit)					
					if (javaModel.getCompilationUnits().get(0).getTypes().isEmpty()) {
						SrcRepoActivator.INSTANCE.warning("A compilation was imported, but no new type created, probably due to parser errors: " + compilationUnit.getPath());						
					}
					
					CompilationUnit importedCompilationUnit = javaModel.getCompilationUnits().get(0);
					JavaCompilationUnitRef ref = repositoryFactory.createJavaCompilationUnitRef();
					CompilationUnitModel compilationUnitModel = repositoryFactory.createCompilationUnitModel();
					ref.setCompilationUnitModel(compilationUnitModel);
					compilationUnitModel.setCompilationUnit(importedCompilationUnit);
					compilationUnitModel.setJavaModel(javaModel);
					ref.setPath(compilationUnit.getPath().toOSString());
					
					// save targets
					for(Map.Entry<String, NamedElement> target: bindings.getTargets().entrySet()) {
						Target targetModel = repositoryFactory.createTarget();
						targetModel.setId(target.getKey());
						targetModel.setTarget(target.getValue());
						compilationUnitModel.getTargets().add(targetModel);
					}
					
					// save pending bindings
					for(PendingElement pendingElement: bindings.getPendings()) {
						de.hub.srcrepo.repositorymodel.PendingElement pendingElementModel = repositoryFactory.createPendingElement();
						pendingElementModel.setBinding(pendingElement.getBinding().toString());
						pendingElementModel.setLinkName(pendingElement.getLinkName());
						pendingElementModel.setClientNode(pendingElement.getClientNode());
						compilationUnitModel.getPendings().add(pendingElementModel);
					}
					javaDiffs.get(compilationUnit).setFile(ref);
					count++;
					importedCompilationUnits++;
				} else {
					EcoreUtil.delete(javaModel);
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
	public void close() {
		
	}
}
