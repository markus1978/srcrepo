/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.emffrag.util;

import de.hub.srcrepo.nofrag.emffrag.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.nofrag.emffrag.EmfFragPackage
 * @generated
 */
public class EmfFragSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EmfFragPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfFragSwitch() {
		if (modelPackage == null) {
			modelPackage = EmfFragPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case EmfFragPackage.INDEXED_MAP: {
				IndexedMap<?, ?> indexedMap = (IndexedMap<?, ?>)theEObject;
				T result = caseIndexedMap(indexedMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfFragPackage.INDEXED_LIST: {
				IndexedList<?> indexedList = (IndexedList<?>)theEObject;
				T result = caseIndexedList(indexedList);
				if (result == null) result = caseIndexedMap(indexedList);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfFragPackage.IS_CONTAINMENT: {
				IsContainment isContainment = (IsContainment)theEObject;
				T result = caseIsContainment(isContainment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfFragPackage.CONTAINMENT_INDEXED_MAP: {
				ContainmentIndexedMap<?, ?> containmentIndexedMap = (ContainmentIndexedMap<?, ?>)theEObject;
				T result = caseContainmentIndexedMap(containmentIndexedMap);
				if (result == null) result = caseIsContainment(containmentIndexedMap);
				if (result == null) result = caseIndexedMap(containmentIndexedMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfFragPackage.CONTAINMENT_INDEXED_LIST: {
				ContainmentIndexedList<?> containmentIndexedList = (ContainmentIndexedList<?>)theEObject;
				T result = caseContainmentIndexedList(containmentIndexedList);
				if (result == null) result = caseIsContainment(containmentIndexedList);
				if (result == null) result = caseIndexedList(containmentIndexedList);
				if (result == null) result = caseIndexedMap(containmentIndexedList);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Indexed Map</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Indexed Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <K, V> T caseIndexedMap(IndexedMap<K, V> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Indexed List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Indexed List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <V> T caseIndexedList(IndexedList<V> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Is Containment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Is Containment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIsContainment(IsContainment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Containment Indexed Map</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Containment Indexed Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <K, V> T caseContainmentIndexedMap(ContainmentIndexedMap<K, V> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Containment Indexed List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Containment Indexed List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <V> T caseContainmentIndexedList(ContainmentIndexedList<V> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //EmfFragSwitch
