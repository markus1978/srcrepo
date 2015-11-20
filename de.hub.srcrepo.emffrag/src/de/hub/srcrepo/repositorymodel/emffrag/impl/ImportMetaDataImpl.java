/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import de.hub.emffrag.fragmentation.FObjectImpl;

import de.hub.srcrepo.repositorymodel.ImportMetaData;

import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Import Meta Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl#isScheduled <em>Scheduled</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl#isImporting <em>Importing</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl#isImported <em>Imported</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl#getImportDate <em>Import Date</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl#getImportStats <em>Import Stats</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl#getImportStatsAsJSON <em>Import Stats As JSON</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImportMetaDataImpl extends FObjectImpl implements ImportMetaData {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportMetaDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.IMPORT_META_DATA;
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
	public boolean isScheduled() {
		return (Boolean)eGet(RepositoryModelPackage.Literals.IMPORT_META_DATA__SCHEDULED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScheduled(boolean newScheduled) {
		eSet(RepositoryModelPackage.Literals.IMPORT_META_DATA__SCHEDULED, newScheduled);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isImporting() {
		return (Boolean)eGet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORTING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImporting(boolean newImporting) {
		eSet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORTING, newImporting);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isImported() {
		return (Boolean)eGet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORTED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImported(boolean newImported) {
		eSet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORTED, newImported);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getImportDate() {
		return (Date)eGet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORT_DATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportDate(Date newImportDate) {
		eSet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORT_DATE, newImportDate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImportStats() {
		return (String)eGet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORT_STATS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportStats(String newImportStats) {
		eSet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORT_STATS, newImportStats);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImportStatsAsJSON() {
		return (String)eGet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORT_STATS_AS_JSON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportStatsAsJSON(String newImportStatsAsJSON) {
		eSet(RepositoryModelPackage.Literals.IMPORT_META_DATA__IMPORT_STATS_AS_JSON, newImportStatsAsJSON);
	}

} //ImportMetaDataImpl
