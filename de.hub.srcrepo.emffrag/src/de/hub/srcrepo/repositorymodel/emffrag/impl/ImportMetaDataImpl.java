/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.ecore.EClass;

import de.hub.srcrepo.repositorymodel.ImportMetaData;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

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
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl#getWorkingCopy <em>Working Copy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImportMetaDataImpl extends TaskDataImpl implements ImportMetaData {
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
	public String getWorkingCopy() {
		return (String)eGet(RepositoryModelPackage.Literals.IMPORT_META_DATA__WORKING_COPY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkingCopy(String newWorkingCopy) {
		eSet(RepositoryModelPackage.Literals.IMPORT_META_DATA__WORKING_COPY, newWorkingCopy);
	}

} //ImportMetaDataImpl
