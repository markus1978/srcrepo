/**
 */
package de.hub.emfcompress.emffrag.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.hub.emfcompress.RevisedObjectReference;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Revised Object Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.RevisedObjectReferenceImpl#getRevisedObject <em>Revised Object</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RevisedObjectReferenceImpl extends ObjectReferenceImpl implements RevisedObjectReference {
	/**
	 * The cached value of the '{@link #getRevisedObject() <em>Revised Object</em>}' reference.
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
	protected RevisedObjectReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfCompressPackage.Literals.REVISED_OBJECT_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getRevisedObject() {
		if (revisedObject != null && revisedObject.eIsProxy()) {
			InternalEObject oldRevisedObject = (InternalEObject)revisedObject;
			revisedObject = eResolveProxy(oldRevisedObject);
			if (revisedObject != oldRevisedObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EmfCompressPackage.REVISED_OBJECT_REFERENCE__REVISED_OBJECT, oldRevisedObject, revisedObject));
			}
		}
		return revisedObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetRevisedObject() {
		return revisedObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRevisedObject(EObject newRevisedObject) {
		EObject oldRevisedObject = revisedObject;
		revisedObject = newRevisedObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfCompressPackage.REVISED_OBJECT_REFERENCE__REVISED_OBJECT, oldRevisedObject, revisedObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmfCompressPackage.REVISED_OBJECT_REFERENCE__REVISED_OBJECT:
				if (resolve) return getRevisedObject();
				return basicGetRevisedObject();
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
			case EmfCompressPackage.REVISED_OBJECT_REFERENCE__REVISED_OBJECT:
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
			case EmfCompressPackage.REVISED_OBJECT_REFERENCE__REVISED_OBJECT:
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
			case EmfCompressPackage.REVISED_OBJECT_REFERENCE__REVISED_OBJECT:
				return revisedObject != null;
		}
		return super.eIsSet(featureID);
	}

} //RevisedObjectReferenceImpl
