package de.hub.srcrepo.snapshot;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;

public interface IModiscoSnapshotModel {

	/**
	 * Start snapshot update. Needs to be called before CUs are added or removed
	 * for computing the next snapshot.
	 */
	public void start();

	/**
	 * Add a compilation unit model to the snapshot. The models are taken from a
	 * persistent repository model to the snapshot. Changes to compilation units
	 * need to be implemented as remove/add pairs.
	 */
	public void addCU(CompilationUnitModel model);

	/**
	 * Remove an old compilation unit model from the snapshot. The models are
	 * taken from a persistent repository model to the snapshot. Changes to
	 * compilation units need to be implemented as remove/add pairs.
	 */
	public void removeCU(CompilationUnitModel model);

	/**
	 * End the current snapshot update. Needs to be called after all CUs for the
	 * next snapshot are added and removed.
	 */
	public void end();

	public Model getModel();

	public JavaPackage getMetaModel();

	/**
	 * Implements a map that connects the objects in the snapshot, with the original object
	 * stored in the persistent repository model.
	 * @param eObject A snapshot model element.
	 * @return The corresponding original persistent repository model element.
	 */
	public <T extends EObject> T getPersistentOriginal(T eObject);
}
