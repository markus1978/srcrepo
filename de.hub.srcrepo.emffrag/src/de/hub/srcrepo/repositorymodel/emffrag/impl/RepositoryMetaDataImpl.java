/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;

import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;

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
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getImportStats <em>Import Stats</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getImportStatsAsJSON <em>Import Stats As JSON</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getNewestRev <em>Newest Rev</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getImportDate <em>Import Date</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getObjectCount <em>Object Count</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getRawByteSize <em>Raw Byte Size</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl#getCuCount <em>Cu Count</em>}</li>
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
	public String getImportStats() {
		return (String)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__IMPORT_STATS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportStats(String newImportStats) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__IMPORT_STATS, newImportStats);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImportStatsAsJSON() {
		return (String)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__IMPORT_STATS_AS_JSON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportStatsAsJSON(String newImportStatsAsJSON) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__IMPORT_STATS_AS_JSON, newImportStatsAsJSON);
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
	public Date getImportDate() {
		return (Date)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__IMPORT_DATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportDate(Date newImportDate) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__IMPORT_DATE, newImportDate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getObjectCount() {
		return (Long)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__OBJECT_COUNT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectCount(long newObjectCount) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__OBJECT_COUNT, newObjectCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getRawByteSize() {
		return (Long)eGet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__RAW_BYTE_SIZE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRawByteSize(long newRawByteSize) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_META_DATA__RAW_BYTE_SIZE, newRawByteSize);
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
