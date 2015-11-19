/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.DataStoreMetaData;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Store Meta Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DataStoreMetaDataImpl#getCount <em>Count</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DataStoreMetaDataImpl extends FObjectImpl implements DataStoreMetaData {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataStoreMetaDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.DATA_STORE_META_DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getCount() {
		return (Long)eGet(RepositoryModelPackage.Literals.DATA_STORE_META_DATA__COUNT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCount(long newCount) {
		eSet(RepositoryModelPackage.Literals.DATA_STORE_META_DATA__COUNT, newCount);
	}

} //DataStoreMetaDataImpl
