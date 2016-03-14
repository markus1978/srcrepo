/**
 */
package de.hub.emfcompress.emffrag.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.hub.emfcompress.RevisedObjectContainment;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Revised Object Containment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.RevisedObjectContainmentImpl#getRevisedObject <em>Revised Object</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RevisedObjectContainmentImpl extends ObjectContainmentImpl implements RevisedObjectContainment {
	/**
	 * The cached value of the '{@link #getRevisedObject() <em>Revised Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRevisedObject()
	 * @generated
	 * @ordered
	 */
	protected EObject revisedObject;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RevisedObjectContainmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfCompressPackage.Literals.REVISED_OBJECT_CONTAINMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getRevisedObject() {
		return revisedObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRevisedObject(EObject newRevisedObject, NotificationChain msgs) {
		EObject oldRevisedObject = revisedObject;
		revisedObject = newRevisedObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EmfCompressPackage.REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT, oldRevisedObject, newRevisedObject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRevisedObject(EObject newRevisedObject) {
		if (newRevisedObject != revisedObject) {
			NotificationChain msgs = null;
			if (revisedObject != null)
				msgs = ((InternalEObject)revisedObject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EmfCompressPackage.REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT, null, msgs);
			if (newRevisedObject != null)
				msgs = ((InternalEObject)newRevisedObject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EmfCompressPackage.REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT, null, msgs);
			msgs = basicSetRevisedObject(newRevisedObject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfCompressPackage.REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT, newRevisedObject, newRevisedObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmfCompressPackage.REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT:
				return basicSetRevisedObject(null, msgs);
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
			case EmfCompressPackage.REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT:
				return getRevisedObject();
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
			case EmfCompressPackage.REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT:
				setRevisedObject((EObject)newValue);
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
			case EmfCompressPackage.REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT:
				setRevisedObject((EObject)null);
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
			case EmfCompressPackage.REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT:
				return revisedObject != null;
		}
		return super.eIsSet(featureID);
	}

} //RevisedObjectContainmentImpl
