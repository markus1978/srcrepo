/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.Model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.SourceRepository#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.SourceRepository#getAllCommits <em>All Commits</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.SourceRepository#getJavaModel <em>Java Model</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.SourceRepository#getRootCommit <em>Root Commit</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getSourceRepository()
 * @model
 * @generated
 */
public interface SourceRepository extends EObject {
	/**
	 * Returns the value of the '<em><b>All Refs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.gitmodel.Ref}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Refs</em>' containment reference list.
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getSourceRepository_AllRefs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Ref> getAllRefs();

	/**
	 * Returns the value of the '<em><b>All Commits</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.gitmodel.Commit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Commits</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Commits</em>' containment reference list.
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getSourceRepository_AllCommits()
	 * @model containment="true"
	 *        annotation="de.hub.emffrag indexes='true'"
	 * @generated
	 */
	EList<Commit> getAllCommits();

	/**
	 * Returns the value of the '<em><b>Java Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Model</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Model</em>' reference.
	 * @see #setJavaModel(Model)
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getSourceRepository_JavaModel()
	 * @model
	 * @generated
	 */
	Model getJavaModel();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.SourceRepository#getJavaModel <em>Java Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Model</em>' reference.
	 * @see #getJavaModel()
	 * @generated
	 */
	void setJavaModel(Model value);

	/**
	 * Returns the value of the '<em><b>Root Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Commit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Commit</em>' reference.
	 * @see #setRootCommit(Commit)
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getSourceRepository_RootCommit()
	 * @model
	 * @generated
	 */
	Commit getRootCommit();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.SourceRepository#getRootCommit <em>Root Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Commit</em>' reference.
	 * @see #getRootCommit()
	 * @generated
	 */
	void setRootCommit(Commit value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Commit getCommit(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void putCommit(String name, Commit commit);

} // SourceRepository
