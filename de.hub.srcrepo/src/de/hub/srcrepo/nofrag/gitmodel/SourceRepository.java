/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.gitmodel;

import org.eclipse.emf.common.util.EList;

import de.hub.srcrepo.nofrag.emffrag.IndexedMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.SourceRepository#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.SourceRepository#getAllCommits <em>All Commits</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getSourceRepository()
 * @model superTypes="de.hub.srcrepo.nofrag.emffrag.IndexedMap<org.eclipse.emf.ecore.EString, de.hub.srcrepo.nofrag.gitmodel.Commit>"
 * @generated
 */
public interface SourceRepository extends IndexedMap<String, Commit> {
	/**
	 * Returns the value of the '<em><b>All Refs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.nofrag.gitmodel.Ref}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Refs</em>' containment reference list.
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getSourceRepository_AllRefs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Ref> getAllRefs();

	/**
	 * Returns the value of the '<em><b>All Commits</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.nofrag.gitmodel.Commit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Commits</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Commits</em>' containment reference list.
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getSourceRepository_AllCommits()
	 * @model containment="true"
	 *        annotation="de.hub.emffrag Fragmentation='true'"
	 * @generated
	 */
	EList<Commit> getAllCommits();

} // SourceRepository
