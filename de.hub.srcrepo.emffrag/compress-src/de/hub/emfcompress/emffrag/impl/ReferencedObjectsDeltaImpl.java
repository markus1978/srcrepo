/**
 */
package de.hub.emfcompress.emffrag.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.hub.emfcompress.ObjectReference;
import de.hub.emfcompress.ReferencedObjectsDelta;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Referenced Objects Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.ReferencedObjectsDeltaImpl#getRevisedObjectReferences <em>Revised Object References</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferencedObjectsDeltaImpl extends ValuesDeltaImpl implements ReferencedObjectsDelta {
	/**
	 * The cached value of the '{@link #getRevisedObjectReferences() <em>Revised Object References</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRevisedObjectReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<ObjectReference> revisedObjectReferences;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferencedObjectsDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfCompressPackage.Literals.REFERENCED_OBJECTS_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ObjectReference> getRevisedObjectReferences() {
		if (revisedObjectReferences == null) {
			revisedObjectReferences = new EObjectContainmentEList<ObjectReference>(ObjectReference.class, this, EmfCompressPackage.REFERENCED_OBJECTS_DELTA__REVISED_OBJECT_REFERENCES);
		}
		return revisedObjectReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmfCompressPackage.REFERENCED_OBJECTS_DELTA__REVISED_OBJECT_REFERENCES:
				return ((InternalEList<?>)getRevisedObjectReferences()).basicRemove(otherEnd, msgs);
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
			case EmfCompressPackage.REFERENCED_OBJECTS_DELTA__REVISED_OBJECT_REFERENCES:
				return getRevisedObjectReferences();
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
			case EmfCompressPackage.REFERENCED_OBJECTS_DELTA__REVISED_OBJECT_REFERENCES:
				getRevisedObjectReferences().clear();
				getRevisedObjectReferences().addAll((Collection<? extends ObjectReference>)newValue);
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
			case EmfCompressPackage.REFERENCED_OBJECTS_DELTA__REVISED_OBJECT_REFERENCES:
				getRevisedObjectReferences().clear();
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
			case EmfCompressPackage.REFERENCED_OBJECTS_DELTA__REVISED_OBJECT_REFERENCES:
				return revisedObjectReferences != null && !revisedObjectReferences.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ReferencedObjectsDeltaImpl
