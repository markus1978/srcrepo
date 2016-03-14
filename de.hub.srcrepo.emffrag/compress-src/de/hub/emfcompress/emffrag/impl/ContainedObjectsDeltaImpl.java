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

import de.hub.emfcompress.ContainedObjectsDelta;
import de.hub.emfcompress.ObjectContainment;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contained Objects Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.ContainedObjectsDeltaImpl#getRevisedObjectContainments <em>Revised Object Containments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContainedObjectsDeltaImpl extends ValuesDeltaImpl implements ContainedObjectsDelta {
	/**
	 * The cached value of the '{@link #getRevisedObjectContainments() <em>Revised Object Containments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRevisedObjectContainments()
	 * @generated
	 * @ordered
	 */
	protected EList<ObjectContainment> revisedObjectContainments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContainedObjectsDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfCompressPackage.Literals.CONTAINED_OBJECTS_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ObjectContainment> getRevisedObjectContainments() {
		if (revisedObjectContainments == null) {
			revisedObjectContainments = new EObjectContainmentEList<ObjectContainment>(ObjectContainment.class, this, EmfCompressPackage.CONTAINED_OBJECTS_DELTA__REVISED_OBJECT_CONTAINMENTS);
		}
		return revisedObjectContainments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmfCompressPackage.CONTAINED_OBJECTS_DELTA__REVISED_OBJECT_CONTAINMENTS:
				return ((InternalEList<?>)getRevisedObjectContainments()).basicRemove(otherEnd, msgs);
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
			case EmfCompressPackage.CONTAINED_OBJECTS_DELTA__REVISED_OBJECT_CONTAINMENTS:
				return getRevisedObjectContainments();
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
			case EmfCompressPackage.CONTAINED_OBJECTS_DELTA__REVISED_OBJECT_CONTAINMENTS:
				getRevisedObjectContainments().clear();
				getRevisedObjectContainments().addAll((Collection<? extends ObjectContainment>)newValue);
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
			case EmfCompressPackage.CONTAINED_OBJECTS_DELTA__REVISED_OBJECT_CONTAINMENTS:
				getRevisedObjectContainments().clear();
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
			case EmfCompressPackage.CONTAINED_OBJECTS_DELTA__REVISED_OBJECT_CONTAINMENTS:
				return revisedObjectContainments != null && !revisedObjectContainments.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ContainedObjectsDeltaImpl
