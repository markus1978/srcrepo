/**
 */
package de.hub.srcrepo.repositorymodel;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository Meta Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getOrigin <em>Origin</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getOldestRev <em>Oldest Rev</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRevCount <em>Rev Count</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportStats <em>Import Stats</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportStatsAsJSON <em>Import Stats As JSON</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getNewestRev <em>Newest Rev</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportDate <em>Import Date</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getCuCount <em>Cu Count</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getObjectCount <em>Object Count</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRawByteSize <em>Raw Byte Size</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getWorkingCopy <em>Working Copy</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRevsWithErrors <em>Revs With Errors</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getDataStoreMetaData <em>Data Store Meta Data</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData()
 * @model
 * @generated
 */
public interface RepositoryMetaData extends EObject {
	/**
	 * Returns the value of the '<em><b>Origin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Origin</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Origin</em>' attribute.
	 * @see #setOrigin(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_Origin()
	 * @model
	 * @generated
	 */
	String getOrigin();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getOrigin <em>Origin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin</em>' attribute.
	 * @see #getOrigin()
	 * @generated
	 */
	void setOrigin(String value);

	/**
	 * Returns the value of the '<em><b>Oldest Rev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Oldest Rev</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Oldest Rev</em>' attribute.
	 * @see #setOldestRev(Date)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_OldestRev()
	 * @model
	 * @generated
	 */
	Date getOldestRev();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getOldestRev <em>Oldest Rev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Oldest Rev</em>' attribute.
	 * @see #getOldestRev()
	 * @generated
	 */
	void setOldestRev(Date value);

	/**
	 * Returns the value of the '<em><b>Rev Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rev Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rev Count</em>' attribute.
	 * @see #setRevCount(int)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_RevCount()
	 * @model
	 * @generated
	 */
	int getRevCount();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRevCount <em>Rev Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rev Count</em>' attribute.
	 * @see #getRevCount()
	 * @generated
	 */
	void setRevCount(int value);

	/**
	 * Returns the value of the '<em><b>Import Stats</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import Stats</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import Stats</em>' attribute.
	 * @see #setImportStats(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_ImportStats()
	 * @model
	 * @generated
	 */
	String getImportStats();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportStats <em>Import Stats</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import Stats</em>' attribute.
	 * @see #getImportStats()
	 * @generated
	 */
	void setImportStats(String value);

	/**
	 * Returns the value of the '<em><b>Import Stats As JSON</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import Stats As JSON</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import Stats As JSON</em>' attribute.
	 * @see #setImportStatsAsJSON(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_ImportStatsAsJSON()
	 * @model
	 * @generated
	 */
	String getImportStatsAsJSON();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportStatsAsJSON <em>Import Stats As JSON</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import Stats As JSON</em>' attribute.
	 * @see #getImportStatsAsJSON()
	 * @generated
	 */
	void setImportStatsAsJSON(String value);

	/**
	 * Returns the value of the '<em><b>Newest Rev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Newest Rev</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Newest Rev</em>' attribute.
	 * @see #setNewestRev(Date)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_NewestRev()
	 * @model
	 * @generated
	 */
	Date getNewestRev();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getNewestRev <em>Newest Rev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Newest Rev</em>' attribute.
	 * @see #getNewestRev()
	 * @generated
	 */
	void setNewestRev(Date value);

	/**
	 * Returns the value of the '<em><b>Import Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import Date</em>' attribute.
	 * @see #setImportDate(Date)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_ImportDate()
	 * @model
	 * @generated
	 */
	Date getImportDate();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportDate <em>Import Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import Date</em>' attribute.
	 * @see #getImportDate()
	 * @generated
	 */
	void setImportDate(Date value);

	/**
	 * Returns the value of the '<em><b>Cu Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cu Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cu Count</em>' attribute.
	 * @see #setCuCount(long)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_CuCount()
	 * @model
	 * @generated
	 */
	long getCuCount();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getCuCount <em>Cu Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cu Count</em>' attribute.
	 * @see #getCuCount()
	 * @generated
	 */
	void setCuCount(long value);

	/**
	 * Returns the value of the '<em><b>Object Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Count</em>' attribute.
	 * @see #setObjectCount(long)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_ObjectCount()
	 * @model
	 * @generated
	 */
	long getObjectCount();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getObjectCount <em>Object Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Count</em>' attribute.
	 * @see #getObjectCount()
	 * @generated
	 */
	void setObjectCount(long value);

	/**
	 * Returns the value of the '<em><b>Raw Byte Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Raw Byte Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Raw Byte Size</em>' attribute.
	 * @see #setRawByteSize(long)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_RawByteSize()
	 * @model
	 * @generated
	 */
	long getRawByteSize();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRawByteSize <em>Raw Byte Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Raw Byte Size</em>' attribute.
	 * @see #getRawByteSize()
	 * @generated
	 */
	void setRawByteSize(long value);

	/**
	 * Returns the value of the '<em><b>Working Copy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Working Copy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Working Copy</em>' attribute.
	 * @see #setWorkingCopy(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_WorkingCopy()
	 * @model
	 * @generated
	 */
	String getWorkingCopy();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getWorkingCopy <em>Working Copy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Working Copy</em>' attribute.
	 * @see #getWorkingCopy()
	 * @generated
	 */
	void setWorkingCopy(String value);

	/**
	 * Returns the value of the '<em><b>Revs With Errors</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Revs With Errors</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Revs With Errors</em>' attribute.
	 * @see #setRevsWithErrors(int)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_RevsWithErrors()
	 * @model
	 * @generated
	 */
	int getRevsWithErrors();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRevsWithErrors <em>Revs With Errors</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Revs With Errors</em>' attribute.
	 * @see #getRevsWithErrors()
	 * @generated
	 */
	void setRevsWithErrors(int value);

	/**
	 * Returns the value of the '<em><b>Data Store Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Store Meta Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Store Meta Data</em>' containment reference.
	 * @see #setDataStoreMetaData(DataStoreMetaData)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getRepositoryMetaData_DataStoreMetaData()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	DataStoreMetaData getDataStoreMetaData();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getDataStoreMetaData <em>Data Store Meta Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Store Meta Data</em>' containment reference.
	 * @see #getDataStoreMetaData()
	 * @generated
	 */
	void setDataStoreMetaData(DataStoreMetaData value);

} // RepositoryMetaData
