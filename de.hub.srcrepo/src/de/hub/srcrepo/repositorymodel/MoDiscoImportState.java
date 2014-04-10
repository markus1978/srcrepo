/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mo Disco Import State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.MoDiscoImportState#getBindings <em>Bindings</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.MoDiscoImportState#getBindingsPerBranch <em>Bindings Per Branch</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getMoDiscoImportState()
 * @model
 * @generated
 */
public interface MoDiscoImportState extends TraversalState {
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
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getMoDiscoImportState_Bindings()
	 * @model containment="true"
	 * @generated
	 */
	JavaBindings getBindings();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.MoDiscoImportState#getBindings <em>Bindings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bindings</em>' containment reference.
	 * @see #getBindings()
	 * @generated
	 */
	void setBindings(JavaBindings value);

	/**
	 * Returns the value of the '<em><b>Bindings Per Branch</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings Per Branch</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bindings Per Branch</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getMoDiscoImportState_BindingsPerBranch()
	 * @model containment="true"
	 * @generated
	 */
	EList<JavaBindingsPerBranch> getBindingsPerBranch();

} // MoDiscoImportState
