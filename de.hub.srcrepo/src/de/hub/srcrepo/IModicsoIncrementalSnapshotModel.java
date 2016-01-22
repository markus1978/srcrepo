package de.hub.srcrepo;

import org.eclipse.gmt.modisco.java.Model;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;

/**
 * Incremental snapshot. Allows models of individual compilation units to enter and to leave the snapshot.
 * Enables srcrepo to incrementally evolve the snapshot from one revision to another using VCS information 
 * about changed, added and removed CUs.
 */
public interface IModicsoIncrementalSnapshotModel {
	public void removeCU(CompilationUnitModel model);
	public void addCU(CompilationUnitModel model);
	
	public Model getSnapshot();
}
