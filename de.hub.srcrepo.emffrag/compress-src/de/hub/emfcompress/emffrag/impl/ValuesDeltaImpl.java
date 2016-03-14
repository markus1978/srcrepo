/**
 */
package de.hub.emfcompress.emffrag.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.hub.emfcompress.ValuesDelta;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;
import de.hub.emffrag.FObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Values Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.ValuesDeltaImpl#getOriginalStart <em>Original Start</em>}</li>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.ValuesDeltaImpl#getOriginalEnd <em>Original End</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ValuesDeltaImpl extends FObjectImpl implements ValuesDelta {
	/**
	 * The default value of the '{@link #getOriginalStart() <em>Original Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalStart()
	 * @generated
	 * @ordered
	 */
	protected static final int ORIGINAL_START_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOriginalStart() <em>Original Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalStart()
	 * @generated
	 * @ordered
	 */
	protected int originalStart = ORIGINAL_START_EDEFAULT;

	/**
	 * The default value of the '{@link #getOriginalEnd() <em>Original End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalEnd()
	 * @generated
	 * @ordered
	 */
	protected static final int ORIGINAL_END_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOriginalEnd() <em>Original End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalEnd()
	 * @generated
	 * @ordered
	 */
	protected int originalEnd = ORIGINAL_END_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ValuesDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfCompressPackage.Literals.VALUES_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOriginalStart() {
		return originalStart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalStart(int newOriginalStart) {
		int oldOriginalStart = originalStart;
		originalStart = newOriginalStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfCompressPackage.VALUES_DELTA__ORIGINAL_START, oldOriginalStart, originalStart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOriginalEnd() {
		return originalEnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalEnd(int newOriginalEnd) {
		int oldOriginalEnd = originalEnd;
		originalEnd = newOriginalEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfCompressPackage.VALUES_DELTA__ORIGINAL_END, oldOriginalEnd, originalEnd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmfCompressPackage.VALUES_DELTA__ORIGINAL_START:
				return getOriginalStart();
			case EmfCompressPackage.VALUES_DELTA__ORIGINAL_END:
				return getOriginalEnd();
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
			case EmfCompressPackage.VALUES_DELTA__ORIGINAL_START:
				setOriginalStart((Integer)newValue);
				return;
			case EmfCompressPackage.VALUES_DELTA__ORIGINAL_END:
				setOriginalEnd((Integer)newValue);
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
			case EmfCompressPackage.VALUES_DELTA__ORIGINAL_START:
				setOriginalStart(ORIGINAL_START_EDEFAULT);
				return;
			case EmfCompressPackage.VALUES_DELTA__ORIGINAL_END:
				setOriginalEnd(ORIGINAL_END_EDEFAULT);
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
			case EmfCompressPackage.VALUES_DELTA__ORIGINAL_START:
				return originalStart != ORIGINAL_START_EDEFAULT;
			case EmfCompressPackage.VALUES_DELTA__ORIGINAL_END:
				return originalEnd != ORIGINAL_END_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (originalStart: ");
		result.append(originalStart);
		result.append(", originalEnd: ");
		result.append(originalEnd);
		result.append(')');
		return result.toString();
	}

} //ValuesDeltaImpl
