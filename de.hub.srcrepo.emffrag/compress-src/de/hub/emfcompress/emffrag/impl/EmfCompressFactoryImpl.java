/**
 */
package de.hub.emfcompress.emffrag.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.hub.emfcompress.ContainedObjectsDelta;
import de.hub.emfcompress.DataValuesDelta;
import de.hub.emfcompress.ObjectDelta;
import de.hub.emfcompress.OriginalObjectContainment;
import de.hub.emfcompress.OriginalObjectReference;
import de.hub.emfcompress.ReferencedObjectsDelta;
import de.hub.emfcompress.RevisedObjectContainment;
import de.hub.emfcompress.RevisedObjectReference;
import de.hub.emfcompress.SettingDelta;
import de.hub.emfcompress.Trash;
import de.hub.emfcompress.emffrag.meta.EmfCompressFactory;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmfCompressFactoryImpl extends EFactoryImpl implements EmfCompressFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EmfCompressFactory init() {
		try {
			EmfCompressFactory theEmfCompressFactory = (EmfCompressFactory)EPackage.Registry.INSTANCE.getEFactory(EmfCompressPackage.eNS_URI);
			if (theEmfCompressFactory != null) {
				return theEmfCompressFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EmfCompressFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfCompressFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case EmfCompressPackage.OBJECT_DELTA: return (EObject)createObjectDelta();
			case EmfCompressPackage.SETTING_DELTA: return (EObject)createSettingDelta();
			case EmfCompressPackage.DATA_VALUES_DELTA: return (EObject)createDataValuesDelta();
			case EmfCompressPackage.CONTAINED_OBJECTS_DELTA: return (EObject)createContainedObjectsDelta();
			case EmfCompressPackage.REFERENCED_OBJECTS_DELTA: return (EObject)createReferencedObjectsDelta();
			case EmfCompressPackage.ORIGINAL_OBJECT_REFERENCE: return (EObject)createOriginalObjectReference();
			case EmfCompressPackage.REVISED_OBJECT_REFERENCE: return (EObject)createRevisedObjectReference();
			case EmfCompressPackage.ORIGINAL_OBJECT_CONTAINMENT: return (EObject)createOriginalObjectContainment();
			case EmfCompressPackage.REVISED_OBJECT_CONTAINMENT: return (EObject)createRevisedObjectContainment();
			case EmfCompressPackage.TRASH: return (EObject)createTrash();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectDelta createObjectDelta() {
		ObjectDeltaImpl objectDelta = new ObjectDeltaImpl();
		return objectDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SettingDelta createSettingDelta() {
		SettingDeltaImpl settingDelta = new SettingDeltaImpl();
		return settingDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataValuesDelta createDataValuesDelta() {
		DataValuesDeltaImpl dataValuesDelta = new DataValuesDeltaImpl();
		return dataValuesDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContainedObjectsDelta createContainedObjectsDelta() {
		ContainedObjectsDeltaImpl containedObjectsDelta = new ContainedObjectsDeltaImpl();
		return containedObjectsDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferencedObjectsDelta createReferencedObjectsDelta() {
		ReferencedObjectsDeltaImpl referencedObjectsDelta = new ReferencedObjectsDeltaImpl();
		return referencedObjectsDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OriginalObjectReference createOriginalObjectReference() {
		OriginalObjectReferenceImpl originalObjectReference = new OriginalObjectReferenceImpl();
		return originalObjectReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisedObjectReference createRevisedObjectReference() {
		RevisedObjectReferenceImpl revisedObjectReference = new RevisedObjectReferenceImpl();
		return revisedObjectReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OriginalObjectContainment createOriginalObjectContainment() {
		OriginalObjectContainmentImpl originalObjectContainment = new OriginalObjectContainmentImpl();
		return originalObjectContainment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevisedObjectContainment createRevisedObjectContainment() {
		RevisedObjectContainmentImpl revisedObjectContainment = new RevisedObjectContainmentImpl();
		return revisedObjectContainment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Trash createTrash() {
		TrashImpl trash = new TrashImpl();
		return trash;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfCompressPackage getEmfCompressPackage() {
		return (EmfCompressPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EmfCompressPackage getPackage() {
		return EmfCompressPackage.eINSTANCE;
	}

} //EmfCompressFactoryImpl
