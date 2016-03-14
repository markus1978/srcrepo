/**
 */
package de.hub.emfcompress.emffrag.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import de.hub.emfcompress.ContainedObjectsDelta;
import de.hub.emfcompress.DataValuesDelta;
import de.hub.emfcompress.ObjectContainment;
import de.hub.emfcompress.ObjectDelta;
import de.hub.emfcompress.ObjectReference;
import de.hub.emfcompress.OriginalObjectContainment;
import de.hub.emfcompress.OriginalObjectReference;
import de.hub.emfcompress.ReferencedObjectsDelta;
import de.hub.emfcompress.RevisedObjectContainment;
import de.hub.emfcompress.RevisedObjectReference;
import de.hub.emfcompress.SettingDelta;
import de.hub.emfcompress.Trash;
import de.hub.emfcompress.ValuesDelta;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;

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
 * @see de.hub.emfcompress.emffrag.meta.EmfCompressPackage
 * @generated
 */
public class EmfCompressSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EmfCompressPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfCompressSwitch() {
		if (modelPackage == null) {
			modelPackage = EmfCompressPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
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
			case EmfCompressPackage.OBJECT_DELTA: {
				ObjectDelta objectDelta = (ObjectDelta)theEObject;
				T result = caseObjectDelta(objectDelta);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.SETTING_DELTA: {
				SettingDelta settingDelta = (SettingDelta)theEObject;
				T result = caseSettingDelta(settingDelta);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.VALUES_DELTA: {
				ValuesDelta valuesDelta = (ValuesDelta)theEObject;
				T result = caseValuesDelta(valuesDelta);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.DATA_VALUES_DELTA: {
				DataValuesDelta dataValuesDelta = (DataValuesDelta)theEObject;
				T result = caseDataValuesDelta(dataValuesDelta);
				if (result == null) result = caseValuesDelta(dataValuesDelta);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.CONTAINED_OBJECTS_DELTA: {
				ContainedObjectsDelta containedObjectsDelta = (ContainedObjectsDelta)theEObject;
				T result = caseContainedObjectsDelta(containedObjectsDelta);
				if (result == null) result = caseValuesDelta(containedObjectsDelta);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.REFERENCED_OBJECTS_DELTA: {
				ReferencedObjectsDelta referencedObjectsDelta = (ReferencedObjectsDelta)theEObject;
				T result = caseReferencedObjectsDelta(referencedObjectsDelta);
				if (result == null) result = caseValuesDelta(referencedObjectsDelta);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.ORIGINAL_OBJECT_REFERENCE: {
				OriginalObjectReference originalObjectReference = (OriginalObjectReference)theEObject;
				T result = caseOriginalObjectReference(originalObjectReference);
				if (result == null) result = caseObjectReference(originalObjectReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.REVISED_OBJECT_REFERENCE: {
				RevisedObjectReference revisedObjectReference = (RevisedObjectReference)theEObject;
				T result = caseRevisedObjectReference(revisedObjectReference);
				if (result == null) result = caseObjectReference(revisedObjectReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.OBJECT_REFERENCE: {
				ObjectReference objectReference = (ObjectReference)theEObject;
				T result = caseObjectReference(objectReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.OBJECT_CONTAINMENT: {
				ObjectContainment objectContainment = (ObjectContainment)theEObject;
				T result = caseObjectContainment(objectContainment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.ORIGINAL_OBJECT_CONTAINMENT: {
				OriginalObjectContainment originalObjectContainment = (OriginalObjectContainment)theEObject;
				T result = caseOriginalObjectContainment(originalObjectContainment);
				if (result == null) result = caseObjectContainment(originalObjectContainment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.REVISED_OBJECT_CONTAINMENT: {
				RevisedObjectContainment revisedObjectContainment = (RevisedObjectContainment)theEObject;
				T result = caseRevisedObjectContainment(revisedObjectContainment);
				if (result == null) result = caseObjectContainment(revisedObjectContainment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EmfCompressPackage.TRASH: {
				Trash trash = (Trash)theEObject;
				T result = caseTrash(trash);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Delta</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Delta</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectDelta(ObjectDelta object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Setting Delta</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Setting Delta</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSettingDelta(SettingDelta object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Values Delta</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Values Delta</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValuesDelta(ValuesDelta object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Values Delta</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Values Delta</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataValuesDelta(DataValuesDelta object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Contained Objects Delta</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Contained Objects Delta</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContainedObjectsDelta(ContainedObjectsDelta object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Referenced Objects Delta</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Referenced Objects Delta</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferencedObjectsDelta(ReferencedObjectsDelta object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Original Object Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Original Object Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOriginalObjectReference(OriginalObjectReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Revised Object Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Revised Object Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRevisedObjectReference(RevisedObjectReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectReference(ObjectReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Containment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Containment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectContainment(ObjectContainment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Original Object Containment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Original Object Containment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOriginalObjectContainment(OriginalObjectContainment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Revised Object Containment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Revised Object Containment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRevisedObjectContainment(RevisedObjectContainment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Trash</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Trash</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrash(Trash object) {
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

} //EmfCompressSwitch
