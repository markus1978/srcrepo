/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Bindings Per Branch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch#getBindings <em>Bindings</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch#getBranch <em>Branch</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getJavaBindingsPerBranch()
 * @model
 * @generated
 */
public interface JavaBindingsPerBranch extends EObject {
	/**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bindings</em>' containment reference.
	 * @see #setBindings(JavaBindings)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getJavaBindingsPerBranch_Bindings()
	 * @model containment="true"
	 * @generated
	 */
	JavaBindings getBindings();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch#getBindings <em>Bindings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bindings</em>' containment reference.
	 * @see #getBindings()
	 * @generated
	 */
	void setBindings(JavaBindings value);

	/**
	 * Returns the value of the '<em><b>Branch</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branch</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch</em>' reference.
	 * @see #setBranch(Rev)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getJavaBindingsPerBranch_Branch()
	 * @model
	 * @generated
	 */
	Rev getBranch();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch#getBranch <em>Branch</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch</em>' reference.
	 * @see #getBranch()
	 * @generated
	 */
	void setBranch(Rev value);

} // JavaBindingsPerBranch
