/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.DataStoreMetaData;
import de.hub.srcrepo.repositorymodel.ImportMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository Meta Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getOrigin <em>Origin</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getOldestRev <em>Oldest Rev</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getRevCount <em>Rev Count</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getNewestRev <em>Newest Rev</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getCuCount <em>Cu Count</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getWorkingCopy <em>Working Copy</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getRevsWithErrors <em>Revs With Errors</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getDataStoreMetaData <em>Data Store Meta Data</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getImportMetaData <em>Import Meta Data</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepositoryMetaDataImpl extends FObjectImpl implements RepositoryMetaData {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryMetaDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.REPOSITORY_META_DATA;
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
	public String getOrigin() {
		return (String)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__ORIGIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrigin(String newOrigin) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__ORIGIN, newOrigin);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getOldestRev() {
		return (Date)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__OLDEST_REV, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldestRev(Date newOldestRev) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__OLDEST_REV, newOldestRev);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getRevCount() {
		return (Integer)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__REV_COUNT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRevCount(int newRevCount) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__REV_COUNT, newRevCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getNewestRev() {
		return (Date)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__NEWEST_REV, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewestRev(Date newNewestRev) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__NEWEST_REV, newNewestRev);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWorkingCopy() {
		return (String)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__WORKING_COPY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkingCopy(String newWorkingCopy) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__WORKING_COPY, newWorkingCopy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getRevsWithErrors() {
		return (Integer)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__REVS_WITH_ERRORS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRevsWithErrors(int newRevsWithErrors) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__REVS_WITH_ERRORS, newRevsWithErrors);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataStoreMetaData getDataStoreMetaData() {
		return (DataStoreMetaData)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__DATA_STORE_META_DATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataStoreMetaData(DataStoreMetaData newDataStoreMetaData) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__DATA_STORE_META_DATA, newDataStoreMetaData);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportMetaData getImportMetaData() {
		return (ImportMetaData)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__IMPORT_META_DATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportMetaData(ImportMetaData newImportMetaData) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__IMPORT_META_DATA, newImportMetaData);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getCuCount() {
		return (Long)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__CU_COUNT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCuCount(long newCuCount) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__CU_COUNT, newCuCount);
	}

} //RepositoryMetaDataImpl
