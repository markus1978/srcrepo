/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Traversal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.Traversal#getRemaingBranchPoints <em>Remaing Branch Points</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.Traversal#getCurrentBranchpoint <em>Current Branchpoint</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.Traversal#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.Traversal#getMerges <em>Merges</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.Traversal#getNextRev <em>Next Rev</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getTraversal()
 * @model
 * @generated
 */
public interface Traversal extends EObject {
	/**
	 * Returns the value of the '<em><b>Remaing Branch Points</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.BranchPoint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remaing Branch Points</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remaing Branch Points</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getTraversal_RemaingBranchPoints()
	 * @model containment="true"
	 * @generated
	 */
	EList<BranchPoint> getRemaingBranchPoints();

	/**
	 * Returns the value of the '<em><b>Current Branchpoint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Branchpoint</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Branchpoint</em>' containment reference.
	 * @see #setCurrentBranchpoint(BranchPoint)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getTraversal_CurrentBranchpoint()
	 * @model containment="true"
	 * @generated
	 */
	BranchPoint getCurrentBranchpoint();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.Traversal#getCurrentBranchpoint <em>Current Branchpoint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Branchpoint</em>' containment reference.
	 * @see #getCurrentBranchpoint()
	 * @generated
	 */
	void setCurrentBranchpoint(BranchPoint value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getTraversal_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.Traversal#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Merges</b></em>' reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Rev}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merges</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Merges</em>' reference list.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getTraversal_Merges()
	 * @model
	 * @generated
	 */
	EList<Rev> getMerges();

	/**
	 * Returns the value of the '<em><b>Next Rev</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next Rev</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Next Rev</em>' reference.
	 * @see #setNextRev(Rev)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getTraversal_NextRev()
	 * @model
	 * @generated
	 */
	Rev getNextRev();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.Traversal#getNextRev <em>Next Rev</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Next Rev</em>' reference.
	 * @see #getNextRev()
	 * @generated
	 */
	void setNextRev(Rev value);

} // Traversal
