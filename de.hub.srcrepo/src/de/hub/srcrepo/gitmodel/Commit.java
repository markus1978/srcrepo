/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel;

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
 *   <li>{@link de.hub.srcrepo.gitmodel.Commit#getAuthor <em>Author</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.Commit#getCommiter <em>Commiter</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.Commit#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.Commit#getTime <em>Time</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.Commit#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.Commit#getParentRelations <em>Parent Relations</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.Commit#getChildRelations <em>Child Relations</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getCommit()
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
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getCommit_Author()
	 * @model
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.Commit#getAuthor <em>Author</em>}' attribute.
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
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getCommit_Commiter()
	 * @model
	 * @generated
	 */
	String getCommiter();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.Commit#getCommiter <em>Commiter</em>}' attribute.
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
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getCommit_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.Commit#getName <em>Name</em>}' attribute.
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
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getCommit_Time()
	 * @model
	 * @generated
	 */
	Date getTime();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.Commit#getTime <em>Time</em>}' attribute.
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
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getCommit_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.Commit#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Parent Relations</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.gitmodel.ParentRelation}.
	 * It is bidirectional and its opposite is '{@link de.hub.srcrepo.gitmodel.ParentRelation#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Relations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Relations</em>' containment reference list.
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getCommit_ParentRelations()
	 * @see de.hub.srcrepo.gitmodel.ParentRelation#getChild
	 * @model opposite="child" containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<ParentRelation> getParentRelations();

	/**
	 * Returns the value of the '<em><b>Child Relations</b></em>' reference list.
	 * The list contents are of type {@link de.hub.srcrepo.gitmodel.ParentRelation}.
	 * It is bidirectional and its opposite is '{@link de.hub.srcrepo.gitmodel.ParentRelation#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Relations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Relations</em>' reference list.
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getCommit_ChildRelations()
	 * @see de.hub.srcrepo.gitmodel.ParentRelation#getParent
	 * @model opposite="parent"
	 * @generated
	 */
	EList<ParentRelation> getChildRelations();

} // Commit
