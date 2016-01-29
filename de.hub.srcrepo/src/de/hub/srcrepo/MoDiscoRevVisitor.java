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

import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel;
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl;

public abstract class MoDiscoRevVisitor extends RevVisitor {

	private final IModiscoSnapshotModel snapshot;
	private final Map<String, CompilationUnitModel> contributingModels;

	public MoDiscoRevVisitor(IModiscoSnapshotModel snapshot) {
		super();
		this.snapshot = snapshot;
		contributingModels = new HashMap<String, CompilationUnitModel>();
	}
	
	public MoDiscoRevVisitor(JavaPackage metaModel) {
		this(new ModiscoIncrementalSnapshotImpl(metaModel));
	}

	@Override
	public void onMerge(Rev mergeRev, Rev branchRev) {
		super.onMerge(mergeRev, branchRev);
		snapshot.clear();
		contributingModels.clear();
	}

	@Override
	protected final void onRev(Rev rev, Map<String, AbstractFileRef> files) {
		if (!filter(rev)) {
			return;
		}
		snapshot.start();
		// merge the models from all compilation units
		Collection<String> pathsInFiles = new HashSet<String>();
		for (Entry<String, AbstractFileRef> entry : files.entrySet()) {
			AbstractFileRef ref = entry.getValue();
			String path = entry.getKey();
			pathsInFiles.add(path);
			if (ref instanceof JavaCompilationUnitRef) {
				JavaCompilationUnitRef compilationUnitRef = (JavaCompilationUnitRef) ref;
				CompilationUnitModel newCUModel = compilationUnitRef.getCompilationUnitModel();
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
		onRev(rev, snapshot.getModel());
	}

	protected boolean filter(Rev rev) {
		return true;
	}

	protected abstract void onRev(Rev rev, Model model);

}
