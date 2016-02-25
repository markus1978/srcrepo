package de.hub.srcrepo.snapshot;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;

import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;

public interface IModiscoSnapshotModel {

	public interface Factory {
		public IModiscoSnapshotModel create(String projectID);
	}

	/**
	 * Start snapshot update. Needs to be called before CUs are added or removed
	 * for computing the next snapshot.
	 */
	public void start();

	/**
	 * Clears the snapshot and rebuilds it with its current contributing refs.
	 * 
	 * @returns false if there were errors during incremental snapshot creation.
	 *          This is a suggestion to build a new snapshot, if possible.
	 */
	public boolean rebuild();
	
	public void clear();
	
	public JavaCompilationUnitRef getContributingRef(String path);
	
	/**
	 * @return the paths of all contributing refs
	 */
	public Collection<String> getContributingRefs();

	/**
	 * Add a compilation unit model to the snapshot. The models are taken from a
	 * persistent repository model to the snapshot. Changes to compilation units
	 * need to be implemented as remove/add pairs.
	 */
	public void addCompilationUnitModel(String path, JavaCompilationUnitRef ref);

	/**
	 * Remove an old compilation unit model from the snapshot. The models are
	 * taken from a persistent repository model to the snapshot. Changes to
	 * compilation units need to be implemented as remove/add pairs.
	 */
	public void removeCompilationUnitModel(String path, JavaCompilationUnitRef ref);

	/**
	 * End the current snapshot update. Needs to be called after all CUs for the
	 * next snapshot are added and removed.
	 * 
	 * @returns false if there were errors during incremental snapshot creation.
	 *          This is a suggestion to build a new snapshot, if possible.
	 */
	public boolean end();

	public Model getModel();

	public Map<String, NamedElement> getTargets();

	public <T extends EObject> T getPersistedOriginal(T source); // TODO
}
