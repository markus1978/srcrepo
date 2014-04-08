/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.UnresolvedItem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Bindings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.JavaBindings#getTargets <em>Targets</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.JavaBindings#getUnresolved <em>Unresolved</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getJavaBindings()
 * @model
 * @generated
 */
public interface JavaBindings extends EObject {
	/**
	 * Returns the value of the '<em><b>Targets</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.gmt.modisco.java.NamedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Targets</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Targets</em>' reference list.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getJavaBindings_Targets()
	 * @model
	 * @generated
	 */
	EList<NamedElement> getTargets();

	/**
	 * Returns the value of the '<em><b>Unresolved</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.gmt.modisco.java.UnresolvedItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unresolved</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unresolved</em>' reference list.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getJavaBindings_Unresolved()
	 * @model
	 * @generated
	 */
	EList<UnresolvedItem> getUnresolved();

} // JavaBindings
