/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parent Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.ParentRelation#getDiffs <em>Diffs</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.ParentRelation#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getParentRelation()
 * @model
 * @generated
 */
public interface ParentRelation extends EObject {
	/**
	 * Returns the value of the '<em><b>Diffs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.gitmodel.Diff}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diffs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diffs</em>' containment reference list.
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getParentRelation_Diffs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Diff> getDiffs();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Commit)
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getParentRelation_Parent()
	 * @model
	 * @generated
	 */
	Commit getParent();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.ParentRelation#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Commit value);

} // ParentRelation
