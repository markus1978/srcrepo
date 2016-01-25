package de.hub.srcrepo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

				if (oldCUModel != null) {
					snapshot.removeCU(oldCUModel);
				}
				snapshot.addCU(newCUModel);
				contributingModels.put(path, newCUModel);
			}
		}
		for (Entry<String, CompilationUnitModel> entry : contributingModels.entrySet()) {
			if (!pathsInFiles.contains(entry.getKey())) {
				snapshot.removeCU(entry.getValue());
			}
		}
		snapshot.end();
		onRev(rev, snapshot.getModel());
	}

	protected boolean filter(Rev rev) {
		return true;
	}

	protected abstract void onRev(Rev rev, Model model);

}
