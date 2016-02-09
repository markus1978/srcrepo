package de.hub.srcrepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import com.google.common.base.Preconditions;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel;
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl;

public abstract class MoDiscoRevVisitor extends ProjectAwareRevVisitor {
	
	private int count = 0;
	
	private final IModiscoSnapshotModel.Factory snapshotFactory;
	private final Map<String, IModiscoSnapshotModel> snapshots;
	private final Map<String, Map<String, CompilationUnitModel>> contributingModels;

	public MoDiscoRevVisitor(IModiscoSnapshotModel.Factory snapshotFactory) {
		super();
		this.snapshotFactory = snapshotFactory;
		snapshots = new HashMap<String, IModiscoSnapshotModel>();
		contributingModels = new HashMap<String, Map<String, CompilationUnitModel>>();
	}
	
	public MoDiscoRevVisitor(JavaPackage metaModel) {
		this(new IModiscoSnapshotModel.Factory() {			
			@Override
			public IModiscoSnapshotModel create(String projectID) {
				return new ModiscoIncrementalSnapshotImpl(metaModel, projectID);
			}
		});
	}

	@Override
	public void onBranch(Rev commonPreviousRev, Rev newBranchRev) {		
		if (lastVisitedRev != newBranchRev) {
			for(IModiscoSnapshotModel snapshot: snapshots.values()) {
				snapshot.clear();
			}
			snapshots.clear();
			contributingModels.clear();
		}
		super.onBranch(commonPreviousRev, newBranchRev);
	}

	@Override
	public void onRev(Rev rev) {
		SrcRepoActivator.INSTANCE.info("Visiting revision: " + rev.getName() + "(" + (++count) + "/" + ((RepositoryModel)rev.eContainer()).getAllRevs().size() + ")");
		super.onRev(rev);
	}

	@Override
	protected final void onRev(Rev rev, Map<String, Map<String, CompilationUnitModel>> projectFiles) {
		if (!filter(rev)) {
			return;
		}
		for(Map.Entry<String, Map<String, CompilationUnitModel>> projectEntry: projectFiles.entrySet()) {
			String projectID = projectEntry.getKey();
			Preconditions.checkArgument(projectID != null);
			Map<String,CompilationUnitModel> files = projectEntry.getValue();

			IModiscoSnapshotModel snapshot = snapshots.get(projectID);
			if (snapshot == null) {
				snapshot = snapshotFactory.create(projectID);
				snapshots.put(projectID, snapshot);
			}
			Map<String, CompilationUnitModel> contributingModels = this.contributingModels.get(projectID); // TODO use the snapshot for that
			if (contributingModels == null) {
				contributingModels = new HashMap<String, CompilationUnitModel>();
				this.contributingModels.put(projectID, contributingModels);
			}
			
			try {
				snapshot.start();
				int changedCUs = 0;
				// merge the models from all compilation units
				Collection<String> pathsInFiles = new HashSet<String>();
				for (Entry<String, CompilationUnitModel> entry : files.entrySet()) {
					String path = entry.getKey();
					pathsInFiles.add(path);
					
					CompilationUnitModel newCUModel = entry.getValue();
					CompilationUnitModel oldCUModel = contributingModels.get(path);
		
					if (newCUModel != oldCUModel) {
						if (!newCUModel.getCompilationUnit().getTypes().isEmpty()) { // keep the old one, if the new one is obviously errornous
							if (oldCUModel != null) {
								snapshot.removeCompilationUnitModel(oldCUModel);
							} 
							snapshot.addCompilationUnitModel(newCUModel);
							contributingModels.put(path, newCUModel);
							changedCUs++;
						} else {
							SrcRepoActivator.INSTANCE.warning("Discovered a CU model " + 
									newCUModel.getCompilationUnit().getOriginalFilePath() + 
									" without a type (probably due to incorrect CU code), using older version of same CU.");
						}
					}			
				}
				List<String> pathsToRemoveFromContributingModel = new ArrayList<String>(); 
				for (Entry<String, CompilationUnitModel> entry : contributingModels.entrySet()) {
					if (!pathsInFiles.contains(entry.getKey())) {
						snapshot.removeCompilationUnitModel(entry.getValue());
						pathsToRemoveFromContributingModel.add(entry.getKey());
						changedCUs++;
					}
				}
				for (String pathToRemoveFromContributingModel: pathsToRemoveFromContributingModel) {
					contributingModels.remove(pathToRemoveFromContributingModel);
				}
				snapshot.end();
				if (changedCUs > 0) {
					SrcRepoActivator.INSTANCE.info("Updated snapshot for " + projectID + ", number of changed CUs " + changedCUs);
				}
			} catch (Exception e) {
				// something unexpected happend. Loose the failed snapshot and start from scratch
				SrcRepoActivator.INSTANCE.warning("Could not update a snapshot; replaced snapshot with a fresh one.", e); 
				
				snapshot.clear();
				snapshot = snapshotFactory.create(projectID);
				snapshots.put(projectID, snapshot);
				for (Entry<String, CompilationUnitModel> entry : contributingModels.entrySet()) {
					snapshot.start();
					snapshot.addCompilationUnitModel(entry.getValue());
					snapshot.end();
				}
			}
		}
		onRevWithSnapshot(rev, snapshots);
	}

	protected boolean filter(Rev rev) {
		return true;
	}

	protected abstract void onRevWithSnapshot(Rev rev, Map<String, IModiscoSnapshotModel> snapshot);

}
