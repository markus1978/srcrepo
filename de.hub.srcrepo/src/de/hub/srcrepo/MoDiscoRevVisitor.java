package de.hub.srcrepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import com.google.common.base.Preconditions;

import de.hub.jstattrack.TimeStatistic;
import de.hub.jstattrack.TimeStatistic.Timer;
import de.hub.jstattrack.services.BatchedPlot;
import de.hub.jstattrack.services.Summary;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.snapshot.IModiscoIncrementalSnapshotModel;
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel;
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl;

public abstract class MoDiscoRevVisitor extends ProjectAwareRevVisitor {
	
	public static final TimeStatistic revVisitETStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).register(MoDiscoRevVisitor.class, "revSnapshotsVisitET");
	public static final TimeStatistic revUDFETStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).register(MoDiscoRevVisitor.class, "revSnapshotUDFET");
	
	private int count = 0;
	
	private final IModiscoIncrementalSnapshotModel.Factory snapshotFactory;
	private final Map<String, IModiscoIncrementalSnapshotModel> snapshots;

	public MoDiscoRevVisitor(IModiscoIncrementalSnapshotModel.Factory snapshotFactory) {
		super();
		this.snapshotFactory = snapshotFactory;
		snapshots = new HashMap<String, IModiscoIncrementalSnapshotModel>();
	}
	
	public MoDiscoRevVisitor(JavaPackage metaModel) {
		this(new IModiscoIncrementalSnapshotModel.Factory() {			
			@Override
			public IModiscoIncrementalSnapshotModel create(String projectID) {
				return new ModiscoIncrementalSnapshotImpl(metaModel, projectID);
			}
		});
	}

	@Override
	public void onRev(Rev rev, Rev traversalParentRev) {
		SrcRepoActivator.INSTANCE.info("Visiting revision: " + rev.getName() + "(" + (++count) + "/" + ((RepositoryModel)rev.eContainer()).getAllRevs().size() + ")");
		super.onRev(rev, traversalParentRev);
	}

	@Override
	protected final void onRev(Rev rev, Rev traversalParentRev, Map<String,Map<String,JavaCompilationUnitRef>> projectFiles) {		
		Timer timer = revVisitETStat.timer();
		if (!filter(rev)) {
			return;
		}

		// remove projects that do not longer exist
		List<String> projectsToRemove = null;
		for(String projectID: snapshots.keySet()) {
			if (projectFiles.get(projectID)==null){
				if (projectsToRemove == null) {
					projectsToRemove = new ArrayList<String>();
				}
				projectsToRemove.add(projectID);
			}
		}
		if (projectsToRemove != null) {
			for (String projectID: projectsToRemove) {
				snapshots.remove(projectID).clear();
			}
		}
		
		// update projects
		ProjectLoop: for(String projectID: projectFiles.keySet()) {			
			Preconditions.checkArgument(projectID != null);
			Map<String,JavaCompilationUnitRef> files = projectFiles.get(projectID);

			if (!files.isEmpty()) {
				IModiscoIncrementalSnapshotModel snapshot = snapshots.get(projectID);
				if (snapshot == null) {
					snapshot = snapshotFactory.create(projectID);
					snapshots.put(projectID, snapshot);
				}
				
				boolean reBuildSnapshot = false;
				boolean updateSnapshot = true;
				while (updateSnapshot || reBuildSnapshot) {
					try {
						updateSnapshot = false;
						
						// add contributing models in case we cleared the snapshot because of an error in a prior revision
						if (reBuildSnapshot) {
							snapshot.rebuild();
						}
						
						snapshot.start();						
						int changedCUs = 0;
						// merge the models from all compilation units
						Collection<String> pathsInFiles = new HashSet<String>();
						for (Map.Entry<String, JavaCompilationUnitRef> entry : files.entrySet()) {
							String path = entry.getKey();
							pathsInFiles.add(path);
							
							JavaCompilationUnitRef newRef = entry.getValue();
							JavaCompilationUnitRef oldRef = snapshot.getContributingRef(path);
							
							if (newRef != oldRef) {												
								if (oldRef != null) {
									snapshot.removeCompilationUnitModel(path, oldRef);
								} 
								snapshot.addCompilationUnitModel(path, newRef);
								changedCUs++;															
							}
						} 
						for (String path : new ArrayList<String>(snapshot.getContributingRefs())) {
							if (!pathsInFiles.contains(path)) {
								snapshot.removeCompilationUnitModel(path, snapshot.getContributingRef(path));
								changedCUs++;
							}
						}
						if (!snapshot.end()) {
							throw new RuntimeException("try again.");
						}
						if (changedCUs > 0) {
							SrcRepoActivator.INSTANCE.info("Updated snapshot for " + projectID + ", number of changed CUs " + changedCUs);
							Timer udfTimer = revUDFETStat.timer();
							try {								
								onRev(rev, projectID, snapshot);
							} catch (Exception e) {
								SrcRepoActivator.INSTANCE.error("Exception in visitor UDF.", e);
							}
							udfTimer.track();
						}
					} catch (Exception incrementalException) {
						// something unexpected happend. Loose the failed snapshot and start from scratch
						SrcRepoActivator.INSTANCE.warning("Could not update a snapshot; replaced snapshot with a fresh one.", incrementalException); 
						
						try {							
							if (!snapshot.rebuild()) {
								SrcRepoActivator.INSTANCE.warning("There were also warnings creating the snapshots, Ill continue anyways.");
							}							
						} catch (Exception newException) {
							SrcRepoActivator.INSTANCE.warning("Also could not create a fresh snapshot. Will try to create one for next revision.", newException);							
							if (!reBuildSnapshot) {
								reBuildSnapshot = true;
							}
							continue ProjectLoop;
						}
					}
					reBuildSnapshot = false;
				}				
			}
		}
		Timer udfTimer = revUDFETStat.timer();
		try {					
			onRevWithSnapshots(rev, traversalParentRev, snapshots);					
		} catch (Exception e) {
			SrcRepoActivator.INSTANCE.error("Exception in visitor UDF.", e);
		}
		udfTimer.track();
		timer.track();
	}

	protected boolean filter(Rev rev) {
		return true;
	}

	protected abstract void onRev(Rev rev, String projectID, IModiscoSnapshotModel snapshot);
	
	protected void onRevWithSnapshots(Rev rev, Rev traversalParentRev, Map<String, ? extends IModiscoSnapshotModel> snapshot) {
		
	}

}
