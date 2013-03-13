/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.emffrag.extensions.impl;

import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.emffrag.extensions.ExtensionsPackage;
import de.hub.srcrepo.emffrag.extensions.ImportLogEntry;
import de.hub.srcrepo.emffrag.extensions.ImportLogEntryType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Import Log Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.emffrag.extensions.impl.ImportLogEntryImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.emffrag.extensions.impl.ImportLogEntryImpl#getException <em>Exception</em>}</li>
 *   <li>{@link de.hub.srcrepo.emffrag.extensions.impl.ImportLogEntryImpl#getExceptionMessage <em>Exception Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.emffrag.extensions.impl.ImportLogEntryImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ImportLogEntryImpl extends FObjectImpl implements ImportLogEntry {
	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getException() <em>Exception</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getException()
	 * @generated
	 * @ordered
	 */
	protected static final String EXCEPTION_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getExceptionMessage() <em>Exception Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExceptionMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String EXCEPTION_MESSAGE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final ImportLogEntryType TYPE_EDEFAULT = ImportLogEntryType.INFO;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportLogEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExtensionsPackage.Literals.IMPORT_LOG_ENTRY;
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
	public String getMessage() {
		return (String)eDynamicGet(ExtensionsPackage.IMPORT_LOG_ENTRY__MESSAGE, ExtensionsPackage.Literals.IMPORT_LOG_ENTRY__MESSAGE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		eDynamicSet(ExtensionsPackage.IMPORT_LOG_ENTRY__MESSAGE, ExtensionsPackage.Literals.IMPORT_LOG_ENTRY__MESSAGE, newMessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getException() {
		return (String)eDynamicGet(ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION, ExtensionsPackage.Literals.IMPORT_LOG_ENTRY__EXCEPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setException(String newException) {
		eDynamicSet(ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION, ExtensionsPackage.Literals.IMPORT_LOG_ENTRY__EXCEPTION, newException);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExceptionMessage() {
		return (String)eDynamicGet(ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE, ExtensionsPackage.Literals.IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExceptionMessage(String newExceptionMessage) {
		eDynamicSet(ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE, ExtensionsPackage.Literals.IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE, newExceptionMessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportLogEntryType getType() {
		return (ImportLogEntryType)eDynamicGet(ExtensionsPackage.IMPORT_LOG_ENTRY__TYPE, ExtensionsPackage.Literals.IMPORT_LOG_ENTRY__TYPE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ImportLogEntryType newType) {
		eDynamicSet(ExtensionsPackage.IMPORT_LOG_ENTRY__TYPE, ExtensionsPackage.Literals.IMPORT_LOG_ENTRY__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExtensionsPackage.IMPORT_LOG_ENTRY__MESSAGE:
				return getMessage();
			case ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION:
				return getException();
			case ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE:
				return getExceptionMessage();
			case ExtensionsPackage.IMPORT_LOG_ENTRY__TYPE:
				return getType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ExtensionsPackage.IMPORT_LOG_ENTRY__MESSAGE:
				setMessage((String)newValue);
				return;
			case ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION:
				setException((String)newValue);
				return;
			case ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE:
				setExceptionMessage((String)newValue);
				return;
			case ExtensionsPackage.IMPORT_LOG_ENTRY__TYPE:
				setType((ImportLogEntryType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ExtensionsPackage.IMPORT_LOG_ENTRY__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION:
				setException(EXCEPTION_EDEFAULT);
				return;
			case ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE:
				setExceptionMessage(EXCEPTION_MESSAGE_EDEFAULT);
				return;
			case ExtensionsPackage.IMPORT_LOG_ENTRY__TYPE:
				setType(TYPE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ExtensionsPackage.IMPORT_LOG_ENTRY__MESSAGE:
				return MESSAGE_EDEFAULT == null ? getMessage() != null : !MESSAGE_EDEFAULT.equals(getMessage());
			case ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION:
				return EXCEPTION_EDEFAULT == null ? getException() != null : !EXCEPTION_EDEFAULT.equals(getException());
			case ExtensionsPackage.IMPORT_LOG_ENTRY__EXCEPTION_MESSAGE:
				return EXCEPTION_MESSAGE_EDEFAULT == null ? getExceptionMessage() != null : !EXCEPTION_MESSAGE_EDEFAULT.equals(getExceptionMessage());
			case ExtensionsPackage.IMPORT_LOG_ENTRY__TYPE:
				return getType() != TYPE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //ImportLogEntryImpl
