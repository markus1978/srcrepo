package de.hub.srcrepo;

import static de.hub.srcrepo.RepositoryModelUtil.getImportMetaData;
import static de.hub.srcrepo.RepositoryModelUtil.getMetaData;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.eclipse.core.filesystem.EFS;
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
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.jstattrack.Statistics;
import de.hub.jstattrack.TimeStatistic;
import de.hub.jstattrack.TimeStatistic.Timer;
import de.hub.jstattrack.ValueStatistic;
import de.hub.jstattrack.services.BatchedPlot;
import de.hub.jstattrack.services.Histogram;
import de.hub.jstattrack.services.Summary;
import de.hub.jstattrack.services.WindowedPlot;
import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.internal.ImportJavaCompilationUnitsJob;
import de.hub.srcrepo.internal.ProjectUtil;
import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.DataSet;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ImportError;
import de.hub.srcrepo.repositorymodel.ImportMetaData;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;
import javancss.Javancss;

public class MoDiscoRepositoryModelImportVisitor implements IRepositoryModelVisitor {

	protected final ISourceControlSystem sourceControlSystem;
	protected final JavaFactory javaFactory;
	protected final JavaPackage javaPackage;
	protected final RepositoryModel repositoryModel;
	protected final RepositoryModelFactory repositoryFactory;
	protected final RepositoryModelPackage repositoryPackage;

	// volatile
	private IProject[] allProjects;
	private Rev currentRev;
	private List<Diff> projectFileDiffs = new ArrayList<Diff>();	
	private List<Diff> potentialJavaDiffs = new ArrayList<Diff>();
	private boolean updateJavaProjectStructureForMerge = false;	
	
	// statistics
	private int importedCompilationUnits = 0;
	private int createdProjects = 0;
	private int knownProjects = 0;
	
	// constants
	private IPath absoluteWorkingDirectoryPath;
	private Collection<String> javaLikeExtensions = new HashSet<String>();
	
	public static final TimeStatistic revCheckoutETStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).register(MoDiscoRepositoryModelImportVisitor.class, "Revision checkout time");
	public static final TimeStatistic revRefreshStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).register(MoDiscoRepositoryModelImportVisitor.class, "Revision refresh time");
	public static final TimeStatistic revImportTimeStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).register(MoDiscoRepositoryModelImportVisitor.class, "Revision import time");
	public static final TimeStatistic revLOCTimeStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).register(MoDiscoRepositoryModelImportVisitor.class, "Revision LOC time");
	public static final ValueStatistic revImportSizeStat = new ValueStatistic("#").with(Summary.class).with(Histogram.class).with(new WindowedPlot(100)).register(MoDiscoRepositoryModelImportVisitor.class, "Imported Java diffs / revision");
	
	public static final TimeStatistic revRefreshStructure = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).with(WindowedPlot.class).register(MoDiscoRepositoryModelImportVisitor.class, "Revision refresh time (project structure)");
	public static final TimeStatistic revGetCompilationUnits = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).with(WindowedPlot.class).register(MoDiscoRepositoryModelImportVisitor.class, "Revision refresh time (gather CUs)");
	public static final TimeStatistic revRefreshCompilationUnits = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).with(WindowedPlot.class).register(MoDiscoRepositoryModelImportVisitor.class, "Revision refresh time (CUs)");
	
	public static final ValueStatistic revNumberOfRefreshedResourcesStat = new ValueStatistic("#").with(Summary.class).with(BatchedPlot.class).with(new WindowedPlot(100)).register(MoDiscoRepositoryModelImportVisitor.class, "Refreshed resources per rev");
	public static final ValueStatistic revNumberOfChangedProjectFiles = new ValueStatistic("#").with(Summary.class).with(BatchedPlot.class).with(new WindowedPlot(100)).with(Histogram.class).register(MoDiscoRepositoryModelImportVisitor.class, "Revision number of changed project files");
	
	private final RepositoryMetaData repositoryMetaData;
	private final ImportMetaData importMetaData;
	private long cuCount = 0;
	private long skippedCuCount = 0;
	
	public MoDiscoRepositoryModelImportVisitor(ISourceControlSystem sourceControlSystem, RepositoryModel repositoryModel, JavaPackage javaPackage) {
		this.javaPackage = javaPackage;
		this.javaFactory = (JavaFactory)javaPackage.getEFactoryInstance();
		
		this.sourceControlSystem = sourceControlSystem;
		this.repositoryFactory = (RepositoryModelFactory)repositoryModel.eClass().getEPackage().getEFactoryInstance();
		this.repositoryPackage = (RepositoryModelPackage)repositoryFactory.getEPackage();
		this.repositoryModel = repositoryModel;
		
	
		this.allProjects = new IProject[0];
		this.absoluteWorkingDirectoryPath = new Path(sourceControlSystem.getWorkingCopy().getAbsolutePath());
		Collections.addAll(javaLikeExtensions, JavaCore.getJavaLikeExtensions());
		
		repositoryMetaData = getMetaData(repositoryModel);
		importMetaData = getImportMetaData(repositoryModel);
				
		importMetaData.setDate(new Date());
		importMetaData.setScheduled(false);
		importMetaData.setImported(false);
		importMetaData.setImporting(true);
		repositoryMetaData.setOrigin(sourceControlSystem.getOrigin());
		cuCount = repositoryMetaData.getCuCount();
	}
	
	protected void updateDataStoreMetaData(RepositoryModel model) {
		
	}

	@Override
	public void onMerge(Rev mergeRev, Rev branchRev) {
		// nothing to do
	}
	
	@Override
	public void onBranch(Rev commonPreviousRev, Rev newBranchRev) {
		updateJavaProjectStructureForMerge = true;
	}

	private boolean runJob(WorkspaceJob job) {
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
			} else if (result.getCode() != IStatus.CANCEL) {
				reportImportError(currentRev, "Job with unexpected status [" + job.getName() + "]", null, false);
			}
		} 
		
		return result.getCode() == IStatus.OK;
	}

	@Override
	public boolean onStartRev(final Rev rev, Rev traversalParentRev, int number) {		
	
		SrcRepoActivator.INSTANCE.info("Visit rev " + rev.getName() + " "   				
				+ "(" + number + "/" + repositoryModel.getAllRevs().size() + "), " 
				+ importedCompilationUnits + "/" + createdProjects + "/" + knownProjects);
		
		String author = rev.getAuthor(); author = author == null ? "[NO AUTHOR]" : author.trim();		
		String message = rev.getMessage(); message = message == null ? "[NO MESSAGE]" : message.trim();
		SrcRepoActivator.INSTANCE.info("Info for visited ref; " + rev.getTime() +", " + author + ":\n" + message);
		
		// move current ref to the new ref
		currentRev = rev;	
		
		// clear the knowledge about project file diffs
		projectFileDiffs.clear();
		
		// clear the java diffs collected for the last ref
		potentialJavaDiffs.clear();
		
		return true;
	}

	@Override
	public void onCompleteRev(Rev rev, Rev traversalParentRev) {
		
		revNumberOfChangedProjectFiles.track(projectFileDiffs.size());
		
		boolean checkoutSuccessful = true;
		Timer checkoutTimer = revCheckoutETStat.timer();
		if (!potentialJavaDiffs.isEmpty() || !projectFileDiffs.isEmpty()) {
			checkoutSuccessful = runJob(new CheckoutJob(rev.getName()));
		}
		checkoutTimer.track();
		
		RefreshJob refreshJob = null;
		
		if (checkoutSuccessful) {
			Map<ICompilationUnit, Diff> javaDiffs = new HashMap<ICompilationUnit, Diff>();
			
			boolean refreshSuccessful = true;
			Timer refreshTimer = revRefreshStat.timer();
			if (!potentialJavaDiffs.isEmpty() || !projectFileDiffs.isEmpty()) {
				// calls revNumberOfRefreshedResourcesStat.track(int); implicitly
				refreshJob = new RefreshJob(projectFileDiffs, potentialJavaDiffs, javaDiffs);
				refreshSuccessful = runJob(refreshJob);
			} else {
				revNumberOfRefreshedResourcesStat.track(0);	
			}
			refreshTimer.track();
			
			Timer importTimer = revImportTimeStat.timer();
			if (refreshSuccessful && javaDiffs.size() > 0) {
				revImportSizeStat.track(javaDiffs.size());
				cuCount += javaDiffs.size();
				runJob(new ImportJavaCompilationUnits(javaDiffs));
				for (Diff javaDiff: javaDiffs.values()) {
					try {
						if (((JavaCompilationUnitRef)javaDiff.getFile()).getCompilationUnitModel().getCompilationUnit().getTypes().isEmpty()) {
							repositoryMetaData.setCusWithErrors(repositoryMetaData.getCusWithErrors() + 1);
						}
					} catch (Exception e) {
						repositoryMetaData.setCusWithErrors(repositoryMetaData.getCusWithErrors() + 1);
					}
				}
				importTimer.track();
				
				Timer locTimer = revLOCTimeStat.timer();
				runJob(new GetLOCCount(javaDiffs));
				locTimer.track();
			} else {
				revImportSizeStat.track(0);
				importTimer.track();
			}
		} else {
			revRefreshStat.timer().track();
			revImportTimeStat.timer().track();
			revImportSizeStat.track(0);
			revNumberOfRefreshedResourcesStat.track(0);
		}
		
		updateJavaProjectStructureForMerge = false;
		
		Date revTime = rev.getTime();
		if (revTime != null) {
			if (repositoryMetaData.getOldestRev() == null || repositoryMetaData.getOldestRev().getTime() > revTime.getTime()) {
				repositoryMetaData.setOldestRev(revTime);
			}
			if (repositoryMetaData.getNewestRev() == null || repositoryMetaData.getNewestRev().getTime() < revTime.getTime()) {
				repositoryMetaData.setNewestRev(revTime);
			}
		}
		repositoryMetaData.setRevCount(repositoryMetaData.getRevCount()+1);
	}

	@Override
	public void onCopiedFile(Diff diff) {
		onDiff(diff);
	}

	@Override
	public void onRenamedFile(Diff diff) {
		onDiff(diff);
	}

	@Override
	public void onAddedFile(Diff diff) {
		onDiff(diff);
	}

	@Override
	public void onModifiedFile(final Diff diff) {	
		onDiff(diff);
	}

	@Override
	public void onDeletedFile(Diff diff) {
		onDiff(diff);
	}
	
	private void onDiff(Diff diff) {
		boolean isJavaDiff = isSpecificDiff(diff, (Path path)-> {
			String fileExtension = path.getFileExtension();
			return fileExtension != null && javaLikeExtensions.contains(fileExtension) && !path.lastSegment().equals("package-info.java");
		});
		if (isJavaDiff) {
			potentialJavaDiffs.add(diff);
		}
		
		boolean isProjectFileDiff = isSpecificDiff(diff, (Path path)-> {
			return path.lastSegment().equals(".project");
		});
		if (isProjectFileDiff) {
			projectFileDiffs.add(diff);
		}		
	}
	
	private boolean isSpecificDiff(final Diff diff, Predicate<Path> predicate) {
		boolean result = false;
		for (Path path: getPaths(diff)) {
			result |= predicate.test(path);
		}
		return result;		
	}
	
	private Path[] getPaths(final Diff diff) {
		if (diff.getType() == ChangeType.DELETE) {
			return new Path[] {new Path(diff.getOldPath())}; 
		} else if (diff.getType() == ChangeType.RENAME) {
			return new Path[] {new Path(diff.getOldPath()), new Path(diff.getNewPath())};
		} else {
			return new Path[] {new Path(diff.getNewPath())};
		}
	}

	private class CheckoutJob extends WorkspaceJob {
		private final String revName;
		
		public CheckoutJob(String revName) {
			super(MoDiscoRepositoryModelImportVisitor.class.getName() + " checkout");
			this.revName = revName;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {		
			Timer revCheckoutTimer = revCheckoutETStat.timer();
			try {				
				sourceControlSystem.checkoutRevision(revName);				
			} catch (SourceControlException e) {							
				reportImportError(currentRev, "Error while checking out.", e, true);
				return Status.CANCEL_STATUS;
			} catch (Exception e) {
				reportImportError(currentRev, "Exception while checking out.", e, true);
				return Status.CANCEL_STATUS;
			} finally {
				revCheckoutTimer.track();
			}
			
			return Status.OK_STATUS;
		}
	}
	
	private class RefreshJob extends WorkspaceJob {
		
		private final boolean hasProjectFileDiffs;
		private final List<Diff> potentialJavaDiffs;	
		private final Map<ICompilationUnit, Diff> javaDiffs;
		private final Collection<IJavaProject> refreshedProjects = new HashSet<IJavaProject>();
				
		public RefreshJob(List<Diff> projectFileDiffs, List<Diff> potentialJavaDiffs, Map<ICompilationUnit, Diff> javaDiffs) {
			super(MoDiscoRepositoryModelImportVisitor.class.getName() + " refresh.");
			this.hasProjectFileDiffs = !projectFileDiffs.isEmpty();
			this.potentialJavaDiffs = potentialJavaDiffs;
			this.javaDiffs = javaDiffs;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {	
			boolean hasErrors = false;
			boolean refreshAll = hasProjectFileDiffs || updateJavaProjectStructureForMerge;
			int refreshedResources = 0;
			Timer refreshStructureTimer = revRefreshStructure.timer();
			if (refreshAll) {
				SrcRepoActivator.INSTANCE.debug("Refreshing whole workspace after checkout.");
				// refresh and remove deleted projects from workspace implicetely
				refreshedResources += ProjectUtil.refreshValidProjects(allProjects, true, new NullProgressMonitor());
				
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
						    	hasErrors = true;
						    }
					    }
					}
				}
				
				allProjects = ProjectUtil.getValidOpenProjects(sourceControlSystem.getWorkingCopy());
				knownProjects = allProjects.length;
			} 
			refreshStructureTimer.track();
			
			// refresh java files
			Timer gathrCUsTimer = revGetCompilationUnits.timer();
			for (Diff diff: potentialJavaDiffs) {
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
							try {
								IPath projectPath = project.getLocation();
								projectPath = projectPath.makeRelativeTo(absoluteWorkingDirectoryPath);
								
								for (Path javaDiffPath: getPaths(diff)) {
									if (projectPath.isPrefixOf(javaDiffPath.makeAbsolute())) {
										IPath relativeToWorkingDirectoryPath = javaDiffPath.makeRelativeTo(projectPath);
										IJavaProject javaProject = JavaCore.create(project);
										if (diff.getType() != ChangeType.MODIFY && !refreshAll && !refreshedProjects.contains(javaProject)) {
											try {
												IResource resource = javaProject.getResource();
												ProjectUtil.refreshResources(new IResource[]{resource}, new NullProgressMonitor());
												SrcRepoActivator.INSTANCE.debug("Refreshing project after checkout: " + resource.getLocation());
												refreshedResources++;
											} catch (Exception e) {
												SrcRepoActivator.INSTANCE.error("Could not refresh a java project.", e);	
												hasErrors = true;
											}
	
											refreshedProjects.add(javaProject);
										}
										
										if (diff.getType() != ChangeType.DELETE && new Path(diff.getNewPath()).equals(javaDiffPath)) {
											IResource resource = javaProject.getProject().findMember(relativeToWorkingDirectoryPath);
											IJavaElement element = JavaCore.create(resource, javaProject);
											if (element != null && element instanceof ICompilationUnit) {
												javaDiffs.put((ICompilationUnit)element, diff);			
											}
										}
									}
								}
							} catch (Exception e) {
								reportImportError(currentRev, "Could not create a ICompilationUnit from a Java resource for some unforseen reasons.", e, true);
							}
						}
					}
				}
				if (hasSomeClosedProjects) {
					SrcRepoActivator.INSTANCE.warning("Resource some projects are closed.");
				}				
			}
			gathrCUsTimer.track();
			
			Timer refreshCUsTimer = revRefreshCompilationUnits.timer();
			if (!javaDiffs.isEmpty() && !refreshAll) {
				IResource[] resources = new IResource[javaDiffs.size()];
				int i = 0;
				for (ICompilationUnit cu: javaDiffs.keySet()) {
					IResource resource = cu.getResource();
					resources[i++] = resource;
					SrcRepoActivator.INSTANCE.debug("Refreshing java resource after checkout: " + resource.getLocation());
				}
				
				try {
					ProjectUtil.refreshResources(resources, new NullProgressMonitor());					
					refreshedResources += resources.length;
				} catch (Exception e) {
					SrcRepoActivator.INSTANCE.error("Could not refresh all compilation units.", e);			
					hasErrors = true;
				}
			}
			refreshCUsTimer.track();
			revNumberOfRefreshedResourcesStat.track(refreshedResources);
			return hasErrors ? Status.CANCEL_STATUS : Status.OK_STATUS;
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
						try {
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
						} catch (Exception e) {
							reportImportError(currentRev, "Could not create a ICompilationUnit from a Java resource for some unforseen reasons.", e, true);
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
	
	private class ImportJavaCompilationUnits extends ImportJavaCompilationUnitsJob {
		private final Map<ICompilationUnit, Diff> javaDiffs;
		public ImportJavaCompilationUnits(Map<ICompilationUnit, Diff> javaDiffs) {
			super(javaDiffs.keySet(), javaFactory, repositoryFactory);
			this.javaDiffs = javaDiffs;
		}
		@Override
		protected void skipWarning(String message) {
			reportImportError(currentRev, message, null, true);
			skippedCuCount++;
		}
		@Override
		protected void skipError(String message, Exception e) {
			reportImportError(currentRev, message, e, true);
			skippedCuCount++;
		}
		
		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			IStatus result = super.runInWorkspace(monitor);
			
			for (Map.Entry<ICompilationUnit, Diff> entry: javaDiffs.entrySet()) {
				ICompilationUnit compilationUnit = entry.getKey();
				CompilationUnitModel compilationUnitModel = getResults().get(compilationUnit);
				if (compilationUnitModel != null) {
					JavaCompilationUnitRef ref = repositoryFactory.createJavaCompilationUnitRef();					
					ref.setCompilationUnitModel(compilationUnitModel);
					
					String scsPath = sourceControlSystem.getWorkingCopy().getAbsolutePath();					
					IProject project = compilationUnit.getJavaProject().getProject();
					String projectFullPath = project.getRawLocation().toOSString();
					projectFullPath = projectFullPath.substring(scsPath.length());
					ref.setPath(projectFullPath + "/" + compilationUnit.getResource().getProjectRelativePath());
					entry.getValue().setFile(ref);
					importedCompilationUnits++;
				}		
			}
			
			return result;
		}	
	}
	
	private class GetLOCCount extends WorkspaceJob {
		
		private final Map<ICompilationUnit, Diff> javaDiffs;	

		public GetLOCCount(Map<ICompilationUnit, Diff> javaDiffs) {
			super(MoDiscoRepositoryModelImportVisitor.class.getName() + " cound LOC metrics for current ref.");
			this.javaDiffs = javaDiffs;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {			
			SrcRepoActivator.INSTANCE.info("about to count LOC metrics for " + javaDiffs.size() + " compilation units");
			String absoluteScsPath = sourceControlSystem.getWorkingCopy().getAbsolutePath();
			for(Diff diff: javaDiffs.values()) {
				try {
					File file =  new File(absoluteScsPath + diff.getFile().getPath());
					long fileSize = EFS.getStore(file.toURI()).fetchInfo().getLength();
					Javancss ncss = new Javancss(file);	
					DataSet dataSet = repositoryFactory.createDataSet();
					dataSet.setData(new HashMap<String,Serializable>());
					diff.getFile().getDataSets().add(dataSet);
					dataSet.setName("LOC-metrics");
					
					if (ncss.getLastError() != null) {
						SrcRepoActivator.INSTANCE.error("Error during counting LOC-metrics.", (Exception)ncss.getLastError());
						dataSet.getData().put("error", ncss.getLastErrorMessage());
					} else {					
						dataSet.getData().put("loc", ncss.getLOC());
						dataSet.getData().put("ncss", ncss.getNcss());
						dataSet.getData().put("comments", ncss.getJdcl() + ncss.getSl() + ncss.getMl());
						dataSet.getData().put("filesize",  fileSize);
					}
				} catch (Exception e) {
					SrcRepoActivator.INSTANCE.error("Exception during counting LOC-metrics: " + e.getMessage());		
				}
			}										
			return Status.OK_STATUS;
		}		
	}
	
	private void reportImportError(Rev rev, String message, Throwable e, boolean controlledFail) {
		if (e != null) {
			message += ": " + e.getMessage() + "[" + e.getClass().getCanonicalName() + "].";
		}

		ImportError importError = repositoryFactory.createImportError();
		importError.setMessage(message);
		importError.setConrolled(controlledFail);
		if (e != null) {
			importError.setExceptionClassName(e.getClass().getCanonicalName());
		}
		rev.getImportErrors().add(importError);
		
		if (controlledFail) {			
			SrcRepoActivator.INSTANCE.warning(message, (Exception)e);
		} else {
			SrcRepoActivator.INSTANCE.error(message, (Exception)e);
		}
	}

	@Override
	public void close(RepositoryModel repositoryModel) {
		importMetaData.setDate(new Date());
		repositoryMetaData.setOrigin(sourceControlSystem.getOrigin());
		repositoryMetaData.setCuCount(cuCount);
		repositoryMetaData.setData(new HashMap<String,Serializable>());
		repositoryMetaData.getData().put("skippedCuCount", skippedCuCount);
		importMetaData.setStatsAsJSON(Statistics.reportToJSON().toString(1));
		importMetaData.setImported(true);
		importMetaData.setImporting(false);
		updateDataStoreMetaData(repositoryModel);
	}
}
