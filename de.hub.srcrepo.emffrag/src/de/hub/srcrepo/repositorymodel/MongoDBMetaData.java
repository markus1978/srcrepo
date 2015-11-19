/**
 */
package de.hub.srcrepo.repositorymodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mongo DB Meta Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getNs <em>Ns</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getAvgObjectSize <em>Avg Object Size</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getStoreSize <em>Store Size</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getServer <em>Server</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getMongoDBMetaData()
 * @model
 * @generated
 */
public interface MongoDBMetaData extends DataStoreMetaData {
	/**
	 * Returns the value of the '<em><b>Ns</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ns</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ns</em>' attribute.
	 * @see #setNs(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getMongoDBMetaData_Ns()
	 * @model
	 * @generated
	 */
	String getNs();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getNs <em>Ns</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ns</em>' attribute.
	 * @see #getNs()
	 * @generated
	 */
	void setNs(String value);

	/**
	 * Returns the value of the '<em><b>Avg Object Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Avg Object Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Avg Object Size</em>' attribute.
	 * @see #setAvgObjectSize(long)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getMongoDBMetaData_AvgObjectSize()
	 * @model
	 * @generated
	 */
	long getAvgObjectSize();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getAvgObjectSize <em>Avg Object Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Avg Object Size</em>' attribute.
	 * @see #getAvgObjectSize()
	 * @generated
	 */
	void setAvgObjectSize(long value);

	/**
	 * Returns the value of the '<em><b>Store Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Store Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Store Size</em>' attribute.
	 * @see #setStoreSize(long)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getMongoDBMetaData_StoreSize()
	 * @model
	 * @generated
	 */
	long getStoreSize();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getStoreSize <em>Store Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Store Size</em>' attribute.
	 * @see #getStoreSize()
	 * @generated
	 */
	void setStoreSize(long value);

	/**
	 * Returns the value of the '<em><b>Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Server</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Server</em>' attribute.
	 * @see #setServer(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getMongoDBMetaData_Server()
	 * @model
	 * @generated
	 */
	String getServer();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getServer <em>Server</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Server</em>' attribute.
	 * @see #getServer()
	 * @generated
	 */
	void setServer(String value);

} // MongoDBMetaData
