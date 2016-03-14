/**
 */
package de.hub.emfcompress.emffrag.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import de.hub.emfcompress.DataValuesDelta;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Values Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.emfcompress.emffrag.impl.DataValuesDeltaImpl#getRevisedValues <em>Revised Values</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DataValuesDeltaImpl extends ValuesDeltaImpl implements DataValuesDelta {
	/**
	 * The cached value of the '{@link #getRevisedValues() <em>Revised Values</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRevisedValues()
	 * @generated
	 * @ordered
	 */
	protected EList<Object> revisedValues;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataValuesDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfCompressPackage.Literals.DATA_VALUES_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Object> getRevisedValues() {
		if (revisedValues == null) {
			revisedValues = new EDataTypeUniqueEList<Object>(Object.class, this, EmfCompressPackage.DATA_VALUES_DELTA__REVISED_VALUES);
		}
		return revisedValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmfCompressPackage.DATA_VALUES_DELTA__REVISED_VALUES:
				return getRevisedValues();
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
			case EmfCompressPackage.DATA_VALUES_DELTA__REVISED_VALUES:
				getRevisedValues().clear();
				getRevisedValues().addAll((Collection<? extends Object>)newValue);
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
			case EmfCompressPackage.DATA_VALUES_DELTA__REVISED_VALUES:
				getRevisedValues().clear();
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
			case EmfCompressPackage.DATA_VALUES_DELTA__REVISED_VALUES:
				return revisedValues != null && !revisedValues.isEmpty();
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
		result.append(" (revisedValues: ");
		result.append(revisedValues);
		result.append(')');
		return result.toString();
	}

} //DataValuesDeltaImpl
