/**
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Directory</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getSubDirectories <em>Sub Directories</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getRepositoryModels <em>Repository Models</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getDescription <em>Description</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getUrl <em>Url</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModelDirectory()
 * @model
 * @generated
 */
public interface RepositoryModelDirectory extends EObject {
	/**
	 * Returns the value of the '<em><b>Sub Directories</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Directories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Directories</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModelDirectory_SubDirectories()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<RepositoryModelDirectory> getSubDirectories();

	/**
	 * Returns the value of the '<em><b>Repository Models</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.RepositoryModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository Models</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository Models</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModelDirectory_RepositoryModels()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<RepositoryModel> getRepositoryModels();

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
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModelDirectory_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModelDirectory_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryModelDirectory_Url()
	 * @model
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

} // RepositoryModelDirectory
