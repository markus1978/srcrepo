/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.Ref#getReferencedCommit <em>Referenced Commit</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.Ref#isIsPeeled <em>Is Peeled</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.Ref#isIsSymbolic <em>Is Symbolic</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.Ref#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getRef()
 * @model
 * @generated
 */
public interface Ref extends EObject {
	/**
	 * Returns the value of the '<em><b>Referenced Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Commit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Commit</em>' reference.
	 * @see #setReferencedCommit(Commit)
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getRef_ReferencedCommit()
	 * @model
	 * @generated
	 */
	Commit getReferencedCommit();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.Ref#getReferencedCommit <em>Referenced Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenced Commit</em>' reference.
	 * @see #getReferencedCommit()
	 * @generated
	 */
	void setReferencedCommit(Commit value);

	/**
	 * Returns the value of the '<em><b>Is Peeled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Peeled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Peeled</em>' attribute.
	 * @see #setIsPeeled(boolean)
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getRef_IsPeeled()
	 * @model
	 * @generated
	 */
	boolean isIsPeeled();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.Ref#isIsPeeled <em>Is Peeled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Peeled</em>' attribute.
	 * @see #isIsPeeled()
	 * @generated
	 */
	void setIsPeeled(boolean value);

	/**
	 * Returns the value of the '<em><b>Is Symbolic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Symbolic</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Symbolic</em>' attribute.
	 * @see #setIsSymbolic(boolean)
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getRef_IsSymbolic()
	 * @model
	 * @generated
	 */
	boolean isIsSymbolic();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.Ref#isIsSymbolic <em>Is Symbolic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Symbolic</em>' attribute.
	 * @see #isIsSymbolic()
	 * @generated
	 */
	void setIsSymbolic(boolean value);

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
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getRef_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.Ref#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Ref
