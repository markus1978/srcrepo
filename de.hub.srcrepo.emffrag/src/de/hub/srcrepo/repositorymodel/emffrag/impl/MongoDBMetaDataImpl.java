/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import de.hub.srcrepo.repositorymodel.MongoDBMetaData;

import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mongo DB Meta Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl#getNs <em>Ns</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl#getAvgObjectSize <em>Avg Object Size</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl#getStoreSize <em>Store Size</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl#getServer <em>Server</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MongoDBMetaDataImpl extends DataStoreMetaDataImpl implements MongoDBMetaData {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MongoDBMetaDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.MONGO_DB_META_DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNs() {
		return (String)eGet(RepositoryModelPackage.Literals.MONGO_DB_META_DATA__NS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNs(String newNs) {
		eSet(RepositoryModelPackage.Literals.MONGO_DB_META_DATA__NS, newNs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getAvgObjectSize() {
		return (Long)eGet(RepositoryModelPackage.Literals.MONGO_DB_META_DATA__AVG_OBJECT_SIZE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAvgObjectSize(long newAvgObjectSize) {
		eSet(RepositoryModelPackage.Literals.MONGO_DB_META_DATA__AVG_OBJECT_SIZE, newAvgObjectSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getStoreSize() {
		return (Long)eGet(RepositoryModelPackage.Literals.MONGO_DB_META_DATA__STORE_SIZE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStoreSize(long newStoreSize) {
		eSet(RepositoryModelPackage.Literals.MONGO_DB_META_DATA__STORE_SIZE, newStoreSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getServer() {
		return (String)eGet(RepositoryModelPackage.Literals.MONGO_DB_META_DATA__SERVER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServer(String newServer) {
		eSet(RepositoryModelPackage.Literals.MONGO_DB_META_DATA__SERVER, newServer);
	}

} //MongoDBMetaDataImpl
