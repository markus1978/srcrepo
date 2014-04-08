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
 * A representation of the model object '<em><b>Parent Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.ParentRelation#getDiffs <em>Diffs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.ParentRelation#getParent <em>Parent</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.ParentRelation#getChild <em>Child</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getParentRelation()
 * @model
 * @generated
 */
public interface ParentRelation extends EObject {
	/**
	 * Returns the value of the '<em><b>Diffs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Diff}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diffs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diffs</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getParentRelation_Diffs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Diff> getDiffs();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.hub.srcrepo.repositorymodel.Rev#getChildRelations <em>Child Relations</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Rev)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getParentRelation_Parent()
	 * @see de.hub.srcrepo.repositorymodel.Rev#getChildRelations
	 * @model opposite="childRelations"
	 * @generated
	 */
	Rev getParent();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.ParentRelation#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Rev value);

	/**
	 * Returns the value of the '<em><b>Child</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.hub.srcrepo.repositorymodel.Rev#getParentRelations <em>Parent Relations</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child</em>' container reference.
	 * @see #setChild(Rev)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getParentRelation_Child()
	 * @see de.hub.srcrepo.repositorymodel.Rev#getParentRelations
	 * @model opposite="parentRelations" transient="false"
	 * @generated
	 */
	Rev getChild();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.ParentRelation#getChild <em>Child</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Child</em>' container reference.
	 * @see #getChild()
	 * @generated
	 */
	void setChild(Rev value);

} // ParentRelation
