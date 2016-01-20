package de.hub.srcrepo;

import org.eclipse.gmt.modisco.java.Model;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;

public interface IModiscoSnapshotModel {
	public void removeCU(CompilationUnitModel model);
	public void addCU(CompilationUnitModel model);
	
	public Model getSnapshot();
}
