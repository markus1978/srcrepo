/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.emffrag.extensions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import de.hub.emffrag.model.emffrag.EmfFragPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.emffrag.extensions.ExtensionsFactory
 * @model kind="package"
 * @generated
 */
public interface ExtensionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "extensions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://srcrepo/extensions/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sre";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExtensionsPackage eINSTANCE = de.hub.srcrepo.emffrag.extensions.impl.ExtensionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.emffrag.extensions.impl.ImportLogImpl <em>Import Log</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.emffrag.extensions.impl.ImportLogImpl
	 * @see de.hub.srcrepo.emffrag.extensions.impl.ExtensionsPackageImpl#getImportLog()
	 * @generated
	 */
	int IMPORT_LOG = 0;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_LOG__ENTRIES = EmfFragPackage.EXTENSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Import Log</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_LOG_FEATURE_COUNT = EmfFragPackage.EXTENSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.emffrag.extensions.impl.ImportLogEntryImpl <em>Import Log Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.emffrag.extensions.impl.ImportLogEntryImpl
	 * @see de.hub.srcrepo.emffrag.extensions.impl.ExtensionsPackageImpl#getImportLogEntry()
	 * @generated
	 */
	int IMPORT_LOG_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_LOG_ENTRY__MESSAGE = 0;

	/**
	 * The feature id for the '<em><b>Exception</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_LOG_ENTRY__EXCEPTION = 1;

	/**
	 * The feature id for the '<em><b>Exception Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_LOG_ENTRY__TYPE = 3;

	/**
	 * The number of structural features of the '<em>Import Log Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_LOG_ENTRY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntryType <em>Import Log Entry Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntryType
	 * @see de.hub.srcrepo.emffrag.extensions.impl.ExtensionsPackageImpl#getImportLogEntryType()
	 * @generated
	 */
	int IMPORT_LOG_ENTRY_TYPE = 2;


	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.emffrag.extensions.ImportLog <em>Import Log</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Log</em>'.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLog
	 * @generated
	 */
	EClass getImportLog();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.emffrag.extensions.ImportLog#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLog#getEntries()
	 * @see #getImportLog()
	 * @generated
	 */
	EReference getImportLog_Entries();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry <em>Import Log Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Log Entry</em>'.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntry
	 * @generated
	 */
	EClass getImportLogEntry();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getMessage()
	 * @see #getImportLogEntry()
	 * @generated
	 */
	EAttribute getImportLogEntry_Message();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getException <em>Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exception</em>'.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getException()
	 * @see #getImportLogEntry()
	 * @generated
	 */
	EAttribute getImportLogEntry_Exception();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getExceptionMessage <em>Exception Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exception Message</em>'.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getExceptionMessage()
	 * @see #getImportLogEntry()
	 * @generated
	 */
	EAttribute getImportLogEntry_ExceptionMessage();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getType()
	 * @see #getImportLogEntry()
	 * @generated
	 */
	EAttribute getImportLogEntry_Type();

	/**
	 * Returns the meta object for enum '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntryType <em>Import Log Entry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Import Log Entry Type</em>'.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntryType
	 * @generated
	 */
	EEnum getImportLogEntryType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExtensionsFactory getExtensionsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.emffrag.extensions.impl.ImportLogImpl <em>Import Log</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.emffrag.extensions.impl.ImportLogImpl
		 * @see de.hub.srcrepo.emffrag.extensions.impl.ExtensionsPackageImpl#getImportLog()
		 * @generated
		 */
		EClass IMPORT_LOG = eINSTANCE.getImportLog();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPORT_LOG__ENTRIES = eINSTANCE.getImportLog_Entries();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.emffrag.extensions.impl.ImportLogEntryImpl <em>Import Log Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.emffrag.extensions.impl.ImportLogEntryImpl
		 * @see de.hub.srcrepo.emffrag.extensions.impl.ExtensionsPackageImpl#getImportLogEntry()
		 * @generated
		 */
		EClass IMPORT_LOG_ENTRY = eINSTANCE.getImportLogEntry();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_LOG_ENTRY__MESSAGE = eINSTANCE.getImportLogEntry_Message();

		/**
		 * The meta object literal for the '<em><b>Exception</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_LOG_ENTRY__EXCEPTION = eINSTANCE.getImportLogEntry_Exception();

		/**
		 * The meta object literal for the '<em><b>Exception Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE = eINSTANCE.getImportLogEntry_ExceptionMessage();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_LOG_ENTRY__TYPE = eINSTANCE.getImportLogEntry_Type();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntryType <em>Import Log Entry Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntryType
		 * @see de.hub.srcrepo.emffrag.extensions.impl.ExtensionsPackageImpl#getImportLogEntryType()
		 * @generated
		 */
		EEnum IMPORT_LOG_ENTRY_TYPE = eINSTANCE.getImportLogEntryType();

	}

} //ExtensionsPackage
