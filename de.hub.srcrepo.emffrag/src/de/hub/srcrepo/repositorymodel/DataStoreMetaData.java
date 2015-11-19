/**
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Store Meta Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.DataStoreMetaData#getCount <em>Count</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getDataStoreMetaData()
 * @model
 * @generated
 */
public interface DataStoreMetaData extends EObject {

	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count</em>' attribute.
	 * @see #setCount(long)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getDataStoreMetaData_Count()
	 * @model
	 * @generated
	 */
	long getCount();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.DataStoreMetaData#getCount <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count</em>' attribute.
	 * @see #getCount()
	 * @generated
	 */
	void setCount(long value);
} // DataStoreMetaData
