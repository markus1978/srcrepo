package de.hub.srcrepo.snapshot;

import java.util.Map;

import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;

public interface IModiscoSnapshotModel {
	public void addCU(CompilationUnitModel model);
	
	public Model getSnapshot();
	public JavaPackage getMetaModel();
	public Map<CompilationUnit, SSCompilationUnitModel> getCompilationUnits();
}
