package de.hub.srcrepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel;
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl;

public abstract class MoDiscoRevVisitor extends ProjectAwareRevVisitor {

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
	public void onMerge(Rev mergeRev, Rev branchRev) {
		super.onMerge(mergeRev, branchRev);
		for(IModiscoSnapshotModel snapshot: snapshots.values()) {
			snapshot.clear();
		}
		snapshots.clear();
		contributingModels.clear();
	}

	@Override
	protected final void onRev(Rev rev, String projectID, Map<String, CompilationUnitModel> files) {
		if (!filter(rev)) {
			return;
		}
		IModiscoSnapshotModel snapshot = snapshots.get(projectID);
		if (snapshot == null) {
			snapshot = snapshotFactory.create(projectID);
			snapshots.put(projectID, snapshot);
		}
		Map<String, CompilationUnitModel> contributingModels = this.contributingModels.get(projectID);
		if (contributingModels == null) {
			contributingModels = new HashMap<String, CompilationUnitModel>();
			this.contributingModels.put(projectID, contributingModels);
		}
		snapshot.start();
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
			}
		}
		for (String pathToRemoveFromContributingModel: pathsToRemoveFromContributingModel) {
			contributingModels.remove(pathToRemoveFromContributingModel);
		}
		snapshot.end();
		onRev(rev, projectID, snapshot.getModel());
	}

	protected boolean filter(Rev rev) {
		return true;
	}

	protected abstract void onRev(Rev rev, String projectID, Model model);

}
