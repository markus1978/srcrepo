/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.gmt.modisco.java.Model;
import de.hub.srcrepo.emffrag.IndexedMap;

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
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getSourceRepository()
 * @model superTypes="de.hub.srcrepo.emffrag.IndexedMap<org.eclipse.emf.ecore.EString, de.hub.srcrepo.gitmodel.Commit>"
 * @generated
 */
public interface SourceRepository extends IndexedMap<String, Commit> {
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
	 *        annotation="de.hub.emffrag Fragmentation='true'"
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

} // SourceRepository