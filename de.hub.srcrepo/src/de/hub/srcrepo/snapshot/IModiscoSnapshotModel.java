package de.hub.srcrepo.snapshot;

import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;

public interface IModiscoSnapshotModel {

	public String getId(NamedElement named);
	
	public String getRev(CompilationUnit cu);
	
	public NamedElement getTarget(String id);
	
	public Model getModel();
	
	public int getModCount();
}
