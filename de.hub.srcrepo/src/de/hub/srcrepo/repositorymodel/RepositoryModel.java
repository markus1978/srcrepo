/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.Model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getAllRevs <em>All Revs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getJavaModel <em>Java Model</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getRootRev <em>Root Rev</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getRepositoryModel()
 * @model
 * @generated
 */
public interface RepositoryModel extends EObject {
	/**
	 * Returns the value of the '<em><b>All Refs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Ref}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Refs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Refs</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getRepositoryModel_AllRefs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Ref> getAllRefs();

	/**
	 * Returns the value of the '<em><b>All Revs</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Rev}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Revs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Revs</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getRepositoryModel_AllRevs()
	 * @model containment="true"
	 *        annotation="de.hub.emffrag indexes='true'"
	 * @generated
	 */
	EList<Rev> getAllRevs();

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
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getRepositoryModel_JavaModel()
	 * @model
	 * @generated
	 */
	Model getJavaModel();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getJavaModel <em>Java Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Model</em>' reference.
	 * @see #getJavaModel()
	 * @generated
	 */
	void setJavaModel(Model value);

	/**
	 * Returns the value of the '<em><b>Root Rev</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Rev</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Rev</em>' reference.
	 * @see #setRootRev(Rev)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getRepositoryModel_RootRev()
	 * @model
	 * @generated
	 */
	Rev getRootRev();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getRootRev <em>Root Rev</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Rev</em>' reference.
	 * @see #getRootRev()
	 * @generated
	 */
	void setRootRev(Rev value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Rev getRev(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void putRev(String name, Rev commit);

} // RepositoryModel
