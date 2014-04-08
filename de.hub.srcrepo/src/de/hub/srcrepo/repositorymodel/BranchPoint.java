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
 * A representation of the model object '<em><b>Branch Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.BranchPoint#getParent <em>Parent</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.BranchPoint#getChildren <em>Children</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.BranchPoint#getNext <em>Next</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getBranchPoint()
 * @model
 * @generated
 */
public interface BranchPoint extends EObject {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Rev)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getBranchPoint_Parent()
	 * @model
	 * @generated
	 */
	Rev getParent();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.BranchPoint#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Rev value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Rev}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' reference list.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getBranchPoint_Children()
	 * @model
	 * @generated
	 */
	EList<Rev> getChildren();

	/**
	 * Returns the value of the '<em><b>Next</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Next</em>' reference.
	 * @see #setNext(Rev)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getBranchPoint_Next()
	 * @model
	 * @generated
	 */
	Rev getNext();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.BranchPoint#getNext <em>Next</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Next</em>' reference.
	 * @see #getNext()
	 * @generated
	 */
	void setNext(Rev value);

} // BranchPoint
