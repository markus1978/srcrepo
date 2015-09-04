/**
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Traversal State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.TraversalState#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.TraversalState#getNumberOfImportedRevs <em>Number Of Imported Revs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.TraversalState#getMerges <em>Merges</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.TraversalState#getOpenBranches <em>Open Branches</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.TraversalState#getCompletedBranches <em>Completed Branches</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getTraversalState()
 * @model
 * @generated
 */
public interface TraversalState extends EObject {
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
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getTraversalState_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.TraversalState#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Number Of Imported Revs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Imported Revs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Imported Revs</em>' attribute.
	 * @see #setNumberOfImportedRevs(int)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getTraversalState_NumberOfImportedRevs()
	 * @model
	 * @generated
	 */
	int getNumberOfImportedRevs();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.TraversalState#getNumberOfImportedRevs <em>Number Of Imported Revs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Imported Revs</em>' attribute.
	 * @see #getNumberOfImportedRevs()
	 * @generated
	 */
	void setNumberOfImportedRevs(int value);

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
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getTraversalState_Merges()
	 * @model
	 * @generated
	 */
	EList<Rev> getMerges();

	/**
	 * Returns the value of the '<em><b>Open Branches</b></em>' reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Rev}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Open Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Open Branches</em>' reference list.
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getTraversalState_OpenBranches()
	 * @model
	 * @generated
	 */
	EList<Rev> getOpenBranches();

	/**
	 * Returns the value of the '<em><b>Completed Branches</b></em>' reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Rev}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Completed Branches</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Completed Branches</em>' reference list.
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getTraversalState_CompletedBranches()
	 * @model
	 * @generated
	 */
	EList<Rev> getCompletedBranches();

} // TraversalState
