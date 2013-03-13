/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.emffrag.extensions;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Log Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getException <em>Exception</em>}</li>
 *   <li>{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getExceptionMessage <em>Exception Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.emffrag.extensions.ExtensionsPackage#getImportLogEntry()
 * @model
 * @generated
 */
public interface ImportLogEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see de.hub.srcrepo.emffrag.extensions.ExtensionsPackage#getImportLogEntry_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Exception</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exception</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exception</em>' attribute.
	 * @see #setException(String)
	 * @see de.hub.srcrepo.emffrag.extensions.ExtensionsPackage#getImportLogEntry_Exception()
	 * @model
	 * @generated
	 */
	String getException();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getException <em>Exception</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exception</em>' attribute.
	 * @see #getException()
	 * @generated
	 */
	void setException(String value);

	/**
	 * Returns the value of the '<em><b>Exception Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exception Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exception Message</em>' attribute.
	 * @see #setExceptionMessage(String)
	 * @see de.hub.srcrepo.emffrag.extensions.ExtensionsPackage#getImportLogEntry_ExceptionMessage()
	 * @model
	 * @generated
	 */
	String getExceptionMessage();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getExceptionMessage <em>Exception Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exception Message</em>' attribute.
	 * @see #getExceptionMessage()
	 * @generated
	 */
	void setExceptionMessage(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link de.hub.srcrepo.emffrag.extensions.ImportLogEntryType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntryType
	 * @see #setType(ImportLogEntryType)
	 * @see de.hub.srcrepo.emffrag.extensions.ExtensionsPackage#getImportLogEntry_Type()
	 * @model
	 * @generated
	 */
	ImportLogEntryType getType();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see de.hub.srcrepo.emffrag.extensions.ImportLogEntryType
	 * @see #getType()
	 * @generated
	 */
	void setType(ImportLogEntryType value);

} // ImportLogEntry
