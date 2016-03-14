/**
 */
package de.hub.emfcompress.emffrag.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.hub.emfcompress.ObjectDelta;
import de.hub.emfcompress.SettingDelta;
import de.hub.emfcompress.ValuesDelta;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;
import de.hub.emffrag.FObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Setting Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.SettingDeltaImpl#getValueDeltas <em>Value Deltas</em>}</li>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.SettingDeltaImpl#getFeatureID <em>Feature ID</em>}</li>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.SettingDeltaImpl#getMatchedObjects <em>Matched Objects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SettingDeltaImpl extends FObjectImpl implements SettingDelta {
	/**
	 * The cached value of the '{@link #getValueDeltas() <em>Value Deltas</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueDeltas()
	 * @generated
	 * @ordered
	 */
	protected EList<ValuesDelta> valueDeltas;

	/**
	 * The default value of the '{@link #getFeatureID() <em>Feature ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureID()
	 * @generated
	 * @ordered
	 */
	protected static final int FEATURE_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getFeatureID() <em>Feature ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureID()
	 * @generated
	 * @ordered
	 */
	protected int featureID = FEATURE_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMatchedObjects() <em>Matched Objects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatchedObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<ObjectDelta> matchedObjects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SettingDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfCompressPackage.Literals.SETTING_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ValuesDelta> getValueDeltas() {
		if (valueDeltas == null) {
			valueDeltas = new EObjectContainmentEList<ValuesDelta>(ValuesDelta.class, this, EmfCompressPackage.SETTING_DELTA__VALUE_DELTAS);
		}
		return valueDeltas;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFeatureID() {
		return featureID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureID(int newFeatureID) {
		int oldFeatureID = featureID;
		featureID = newFeatureID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfCompressPackage.SETTING_DELTA__FEATURE_ID, oldFeatureID, featureID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ObjectDelta> getMatchedObjects() {
		if (matchedObjects == null) {
			matchedObjects = new EObjectContainmentEList<ObjectDelta>(ObjectDelta.class, this, EmfCompressPackage.SETTING_DELTA__MATCHED_OBJECTS);
		}
		return matchedObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmfCompressPackage.SETTING_DELTA__VALUE_DELTAS:
				return ((InternalEList<?>)getValueDeltas()).basicRemove(otherEnd, msgs);
			case EmfCompressPackage.SETTING_DELTA__MATCHED_OBJECTS:
				return ((InternalEList<?>)getMatchedObjects()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmfCompressPackage.SETTING_DELTA__VALUE_DELTAS:
				return getValueDeltas();
			case EmfCompressPackage.SETTING_DELTA__FEATURE_ID:
				return getFeatureID();
			case EmfCompressPackage.SETTING_DELTA__MATCHED_OBJECTS:
				return getMatchedObjects();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EmfCompressPackage.SETTING_DELTA__VALUE_DELTAS:
				getValueDeltas().clear();
				getValueDeltas().addAll((Collection<? extends ValuesDelta>)newValue);
				return;
			case EmfCompressPackage.SETTING_DELTA__FEATURE_ID:
				setFeatureID((Integer)newValue);
				return;
			case EmfCompressPackage.SETTING_DELTA__MATCHED_OBJECTS:
				getMatchedObjects().clear();
				getMatchedObjects().addAll((Collection<? extends ObjectDelta>)newValue);
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
			case EmfCompressPackage.SETTING_DELTA__VALUE_DELTAS:
				getValueDeltas().clear();
				return;
			case EmfCompressPackage.SETTING_DELTA__FEATURE_ID:
				setFeatureID(FEATURE_ID_EDEFAULT);
				return;
			case EmfCompressPackage.SETTING_DELTA__MATCHED_OBJECTS:
				getMatchedObjects().clear();
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
			case EmfCompressPackage.SETTING_DELTA__VALUE_DELTAS:
				return valueDeltas != null && !valueDeltas.isEmpty();
			case EmfCompressPackage.SETTING_DELTA__FEATURE_ID:
				return this.featureID != FEATURE_ID_EDEFAULT;
			case EmfCompressPackage.SETTING_DELTA__MATCHED_OBJECTS:
				return matchedObjects != null && !matchedObjects.isEmpty();
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
		result.append(" (featureID: ");
		result.append(featureID);
		result.append(')');
		return result.toString();
	}

} //SettingDeltaImpl
