/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.gitmodel;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Commit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getAuthor <em>Author</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getCommiter <em>Commiter</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getTime <em>Time</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getParents <em>Parents</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getDiffs <em>Diffs</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getCommit()
 * @model
 * @generated
 */
public interface Commit extends EObject {
	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getCommit_Author()
	 * @model
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * Returns the value of the '<em><b>Commiter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commiter</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commiter</em>' attribute.
	 * @see #setCommiter(String)
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getCommit_Commiter()
	 * @model
	 * @generated
	 */
	String getCommiter();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getCommiter <em>Commiter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commiter</em>' attribute.
	 * @see #getCommiter()
	 * @generated
	 */
	void setCommiter(String value);

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
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getCommit_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(Date)
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getCommit_Time()
	 * @model
	 * @generated
	 */
	Date getTime();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(Date value);

	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getCommit_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.nofrag.gitmodel.Commit#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Parents</b></em>' reference list.
	 * The list contents are of type {@link de.hub.srcrepo.nofrag.gitmodel.Commit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parents</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parents</em>' reference list.
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getCommit_Parents()
	 * @model
	 * @generated
	 */
	EList<Commit> getParents();

	/**
	 * Returns the value of the '<em><b>Diffs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.nofrag.gitmodel.Diff}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diffs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diffs</em>' containment reference list.
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getCommit_Diffs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Diff> getDiffs();

} // Commit
