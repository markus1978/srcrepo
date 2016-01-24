package de.hub.srcrepo.snapshot;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;

/**
 * Incremental snapshot. Allows models of individual compilation units to enter and to leave the snapshot.
 * Enables srcrepo to incrementally evolve the snapshot from one revision to another using VCS information 
 * about changed, added and removed CUs.
 */
public interface IModicsoIncrementalSnapshotModel extends IModiscoSnapshotModel {
	public void removeCU(CompilationUnitModel model);
}
