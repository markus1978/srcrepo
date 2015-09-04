/**
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getAllRevs <em>All Revs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getRootRevs <em>Root Revs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getTraversals <em>Traversals</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModel()
 * @model
 * @generated
 */
public interface RepositoryModel extends EObject {
	/**
	 * Returns the value of the '<em><b>All Refs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Ref}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Refs</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModel_AllRefs()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Ref> getAllRefs();

	/**
	 * Returns the value of the '<em><b>All Revs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Rev}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Revs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Revs</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModel_AllRevs()
	 * @model containment="true" resolveProxies="true"
	 *        annotation="de.hub.emffrag fragments='true'"
	 * @generated
	 */
	EList<Rev> getAllRevs();

	/**
	 * Returns the value of the '<em><b>Root Revs</b></em>' reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Rev}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Revs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Revs</em>' reference list.
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModel_RootRevs()
	 * @model
	 * @generated
	 */
	EList<Rev> getRootRevs();

	/**
	 * Returns the value of the '<em><b>Traversals</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traversals</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traversals</em>' containment reference.
	 * @see #setTraversals(TraversalState)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModel_Traversals()
	 * @model containment="true" resolveProxies="true"
	 *        annotation="de.hub.emffrag Fragmentation='true'"
	 * @generated
	 */
	TraversalState getTraversals();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getTraversals <em>Traversals</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Traversals</em>' containment reference.
	 * @see #getTraversals()
	 * @generated
	 */
	void setTraversals(TraversalState value);

} // RepositoryModel
