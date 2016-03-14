/**
 */
package de.hub.emfcompress.emffrag.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.hub.emfcompress.ObjectDelta;
import de.hub.emfcompress.SettingDelta;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;
import de.hub.emffrag.FObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.ObjectDeltaImpl#getSettingDeltas <em>Setting Deltas</em>}</li>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.ObjectDeltaImpl#getOriginalClass <em>Original Class</em>}</li>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.ObjectDeltaImpl#getOriginalIndex <em>Original Index</em>}</li>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.ObjectDeltaImpl#getOriginalProxy <em>Original Proxy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ObjectDeltaImpl extends FObjectImpl implements ObjectDelta {
	/**
	 * The cached value of the '{@link #getSettingDeltas() <em>Setting Deltas</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSettingDeltas()
	 * @generated
	 * @ordered
	 */
	protected EList<SettingDelta> settingDeltas;

	/**
	 * The cached value of the '{@link #getOriginalClass() <em>Original Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalClass()
	 * @generated
	 * @ordered
	 */
	protected EClass originalClass;

	/**
	 * The default value of the '{@link #getOriginalIndex() <em>Original Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int ORIGINAL_INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOriginalIndex() <em>Original Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalIndex()
	 * @generated
	 * @ordered
	 */
	protected int originalIndex = ORIGINAL_INDEX_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOriginalProxy() <em>Original Proxy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalProxy()
	 * @generated
	 * @ordered
	 */
	protected EObject originalProxy;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ObjectDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfCompressPackage.Literals.OBJECT_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SettingDelta> getSettingDeltas() {
		if (settingDeltas == null) {
			settingDeltas = new EObjectContainmentEList<SettingDelta>(SettingDelta.class, this, EmfCompressPackage.OBJECT_DELTA__SETTING_DELTAS);
		}
		return settingDeltas;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOriginalClass() {
		if (originalClass != null && originalClass.eIsProxy()) {
			InternalEObject oldOriginalClass = (InternalEObject)originalClass;
			originalClass = (EClass)eResolveProxy(oldOriginalClass);
			if (originalClass != oldOriginalClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EmfCompressPackage.OBJECT_DELTA__ORIGINAL_CLASS, oldOriginalClass, originalClass));
			}
		}
		return originalClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetOriginalClass() {
		return originalClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalClass(EClass newOriginalClass) {
		EClass oldOriginalClass = originalClass;
		originalClass = newOriginalClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfCompressPackage.OBJECT_DELTA__ORIGINAL_CLASS, oldOriginalClass, originalClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOriginalIndex() {
		return originalIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalIndex(int newOriginalIndex) {
		int oldOriginalIndex = originalIndex;
		originalIndex = newOriginalIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfCompressPackage.OBJECT_DELTA__ORIGINAL_INDEX, oldOriginalIndex, originalIndex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getOriginalProxy() {
		return originalProxy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOriginalProxy(EObject newOriginalProxy, NotificationChain msgs) {
		EObject oldOriginalProxy = originalProxy;
		originalProxy = newOriginalProxy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EmfCompressPackage.OBJECT_DELTA__ORIGINAL_PROXY, oldOriginalProxy, newOriginalProxy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalProxy(EObject newOriginalProxy) {
		if (newOriginalProxy != originalProxy) {
			NotificationChain msgs = null;
			if (originalProxy != null)
				msgs = ((InternalEObject)originalProxy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EmfCompressPackage.OBJECT_DELTA__ORIGINAL_PROXY, null, msgs);
			if (newOriginalProxy != null)
				msgs = ((InternalEObject)newOriginalProxy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EmfCompressPackage.OBJECT_DELTA__ORIGINAL_PROXY, null, msgs);
			msgs = basicSetOriginalProxy(newOriginalProxy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfCompressPackage.OBJECT_DELTA__ORIGINAL_PROXY, newOriginalProxy, newOriginalProxy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmfCompressPackage.OBJECT_DELTA__SETTING_DELTAS:
				return ((InternalEList<?>)getSettingDeltas()).basicRemove(otherEnd, msgs);
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_PROXY:
				return basicSetOriginalProxy(null, msgs);
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
			case EmfCompressPackage.OBJECT_DELTA__SETTING_DELTAS:
				return getSettingDeltas();
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_CLASS:
				if (resolve) return getOriginalClass();
				return basicGetOriginalClass();
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_INDEX:
				return getOriginalIndex();
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_PROXY:
				return getOriginalProxy();
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
			case EmfCompressPackage.OBJECT_DELTA__SETTING_DELTAS:
				getSettingDeltas().clear();
				getSettingDeltas().addAll((Collection<? extends SettingDelta>)newValue);
				return;
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_CLASS:
				setOriginalClass((EClass)newValue);
				return;
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_INDEX:
				setOriginalIndex((Integer)newValue);
				return;
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_PROXY:
				setOriginalProxy((EObject)newValue);
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
			case EmfCompressPackage.OBJECT_DELTA__SETTING_DELTAS:
				getSettingDeltas().clear();
				return;
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_CLASS:
				setOriginalClass((EClass)null);
				return;
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_INDEX:
				setOriginalIndex(ORIGINAL_INDEX_EDEFAULT);
				return;
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_PROXY:
				setOriginalProxy((EObject)null);
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
			case EmfCompressPackage.OBJECT_DELTA__SETTING_DELTAS:
				return settingDeltas != null && !settingDeltas.isEmpty();
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_CLASS:
				return originalClass != null;
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_INDEX:
				return originalIndex != ORIGINAL_INDEX_EDEFAULT;
			case EmfCompressPackage.OBJECT_DELTA__ORIGINAL_PROXY:
				return originalProxy != null;
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
		result.append(" (originalIndex: ");
		result.append(originalIndex);
		result.append(')');
		return result.toString();
	}

} //ObjectDeltaImpl
