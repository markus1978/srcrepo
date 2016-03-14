/**
 */
package de.hub.emfcompress.emffrag.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.emfcompress.emffrag.meta.EmfCompressPackage
 * @generated
 */
public class EmfCompressAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EmfCompressPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfCompressAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = EmfCompressPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EmfCompressSwitch<Adapter> modelSwitch =
		new EmfCompressSwitch<Adapter>() {
			@Override
			public Adapter caseObjectDelta(ObjectDelta object) {
				return createObjectDeltaAdapter();
			}
			@Override
			public Adapter caseSettingDelta(SettingDelta object) {
				return createSettingDeltaAdapter();
			}
			@Override
			public Adapter caseValuesDelta(ValuesDelta object) {
				return createValuesDeltaAdapter();
			}
			@Override
			public Adapter caseDataValuesDelta(DataValuesDelta object) {
				return createDataValuesDeltaAdapter();
			}
			@Override
			public Adapter caseContainedObjectsDelta(ContainedObjectsDelta object) {
				return createContainedObjectsDeltaAdapter();
			}
			@Override
			public Adapter caseReferencedObjectsDelta(ReferencedObjectsDelta object) {
				return createReferencedObjectsDeltaAdapter();
			}
			@Override
			public Adapter caseOriginalObjectReference(OriginalObjectReference object) {
				return createOriginalObjectReferenceAdapter();
			}
			@Override
			public Adapter caseRevisedObjectReference(RevisedObjectReference object) {
				return createRevisedObjectReferenceAdapter();
			}
			@Override
			public Adapter caseObjectReference(ObjectReference object) {
				return createObjectReferenceAdapter();
			}
			@Override
			public Adapter caseObjectContainment(ObjectContainment object) {
				return createObjectContainmentAdapter();
			}
			@Override
			public Adapter caseOriginalObjectContainment(OriginalObjectContainment object) {
				return createOriginalObjectContainmentAdapter();
			}
			@Override
			public Adapter caseRevisedObjectContainment(RevisedObjectContainment object) {
				return createRevisedObjectContainmentAdapter();
			}
			@Override
			public Adapter caseTrash(Trash object) {
				return createTrashAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.ObjectDelta <em>Object Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.ObjectDelta
	 * @generated
	 */
	public Adapter createObjectDeltaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.SettingDelta <em>Setting Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.SettingDelta
	 * @generated
	 */
	public Adapter createSettingDeltaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.ValuesDelta <em>Values Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.ValuesDelta
	 * @generated
	 */
	public Adapter createValuesDeltaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.DataValuesDelta <em>Data Values Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.DataValuesDelta
	 * @generated
	 */
	public Adapter createDataValuesDeltaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.ContainedObjectsDelta <em>Contained Objects Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.ContainedObjectsDelta
	 * @generated
	 */
	public Adapter createContainedObjectsDeltaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.ReferencedObjectsDelta <em>Referenced Objects Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.ReferencedObjectsDelta
	 * @generated
	 */
	public Adapter createReferencedObjectsDeltaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.OriginalObjectReference <em>Original Object Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.OriginalObjectReference
	 * @generated
	 */
	public Adapter createOriginalObjectReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.RevisedObjectReference <em>Revised Object Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.RevisedObjectReference
	 * @generated
	 */
	public Adapter createRevisedObjectReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.ObjectReference <em>Object Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.ObjectReference
	 * @generated
	 */
	public Adapter createObjectReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.ObjectContainment <em>Object Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.ObjectContainment
	 * @generated
	 */
	public Adapter createObjectContainmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.OriginalObjectContainment <em>Original Object Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.OriginalObjectContainment
	 * @generated
	 */
	public Adapter createOriginalObjectContainmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.RevisedObjectContainment <em>Revised Object Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.RevisedObjectContainment
	 * @generated
	 */
	public Adapter createRevisedObjectContainmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.emfcompress.Trash <em>Trash</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.emfcompress.Trash
	 * @generated
	 */
	public Adapter createTrashAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //EmfCompressAdapterFactory
