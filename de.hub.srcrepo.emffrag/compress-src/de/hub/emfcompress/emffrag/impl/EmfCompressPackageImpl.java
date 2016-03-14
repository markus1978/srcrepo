/**
 */
package de.hub.emfcompress.emffrag.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

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
import de.hub.emfcompress.emffrag.meta.EmfCompressFactory;
import de.hub.emfcompress.emffrag.meta.EmfCompressPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmfCompressPackageImpl extends EPackageImpl implements EmfCompressPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass settingDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valuesDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataValuesDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass containedObjectsDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referencedObjectsDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass originalObjectReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass revisedObjectReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectContainmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass originalObjectContainmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass revisedObjectContainmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass trashEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.hub.emfcompress.emffrag.meta.EmfCompressPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EmfCompressPackageImpl() {
		super(eNS_URI, EmfCompressFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link EmfCompressPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EmfCompressPackage init() {
		if (isInited) return (EmfCompressPackage)EPackage.Registry.INSTANCE.getEPackage(EmfCompressPackage.eNS_URI);

		// Obtain or create and register package
		EmfCompressPackageImpl theEmfCompressPackage = (EmfCompressPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EmfCompressPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EmfCompressPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theEmfCompressPackage.createPackageContents();

		// Initialize created meta-data
		theEmfCompressPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEmfCompressPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EmfCompressPackage.eNS_URI, theEmfCompressPackage);
		return theEmfCompressPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectDelta() {
		return objectDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectDelta_SettingDeltas() {
		return (EReference)objectDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectDelta_OriginalClass() {
		return (EReference)objectDeltaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObjectDelta_OriginalIndex() {
		return (EAttribute)objectDeltaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectDelta_OriginalProxy() {
		return (EReference)objectDeltaEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSettingDelta() {
		return settingDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSettingDelta_ValueDeltas() {
		return (EReference)settingDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSettingDelta_FeatureID() {
		return (EAttribute)settingDeltaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSettingDelta_MatchedObjects() {
		return (EReference)settingDeltaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValuesDelta() {
		return valuesDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValuesDelta_OriginalStart() {
		return (EAttribute)valuesDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValuesDelta_OriginalEnd() {
		return (EAttribute)valuesDeltaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataValuesDelta() {
		return dataValuesDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataValuesDelta_RevisedValues() {
		return (EAttribute)dataValuesDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContainedObjectsDelta() {
		return containedObjectsDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getContainedObjectsDelta_RevisedObjectContainments() {
		return (EReference)containedObjectsDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReferencedObjectsDelta() {
		return referencedObjectsDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReferencedObjectsDelta_RevisedObjectReferences() {
		return (EReference)referencedObjectsDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOriginalObjectReference() {
		return originalObjectReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOriginalObjectReference_OriginalObject() {
		return (EReference)originalObjectReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRevisedObjectReference() {
		return revisedObjectReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRevisedObjectReference_RevisedObject() {
		return (EReference)revisedObjectReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectReference() {
		return objectReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectContainment() {
		return objectContainmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOriginalObjectContainment() {
		return originalObjectContainmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOriginalObjectContainment_OriginalObject() {
		return (EReference)originalObjectContainmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRevisedObjectContainment() {
		return revisedObjectContainmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRevisedObjectContainment_RevisedObject() {
		return (EReference)revisedObjectContainmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTrash() {
		return trashEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTrash_Contents() {
		return (EReference)trashEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfCompressFactory getEmfCompressFactory() {
		return (EmfCompressFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		objectDeltaEClass = createEClass(OBJECT_DELTA);
		createEReference(objectDeltaEClass, OBJECT_DELTA__SETTING_DELTAS);
		createEReference(objectDeltaEClass, OBJECT_DELTA__ORIGINAL_CLASS);
		createEAttribute(objectDeltaEClass, OBJECT_DELTA__ORIGINAL_INDEX);
		createEReference(objectDeltaEClass, OBJECT_DELTA__ORIGINAL_PROXY);

		settingDeltaEClass = createEClass(SETTING_DELTA);
		createEReference(settingDeltaEClass, SETTING_DELTA__VALUE_DELTAS);
		createEAttribute(settingDeltaEClass, SETTING_DELTA__FEATURE_ID);
		createEReference(settingDeltaEClass, SETTING_DELTA__MATCHED_OBJECTS);

		valuesDeltaEClass = createEClass(VALUES_DELTA);
		createEAttribute(valuesDeltaEClass, VALUES_DELTA__ORIGINAL_START);
		createEAttribute(valuesDeltaEClass, VALUES_DELTA__ORIGINAL_END);

		dataValuesDeltaEClass = createEClass(DATA_VALUES_DELTA);
		createEAttribute(dataValuesDeltaEClass, DATA_VALUES_DELTA__REVISED_VALUES);

		containedObjectsDeltaEClass = createEClass(CONTAINED_OBJECTS_DELTA);
		createEReference(containedObjectsDeltaEClass, CONTAINED_OBJECTS_DELTA__REVISED_OBJECT_CONTAINMENTS);

		referencedObjectsDeltaEClass = createEClass(REFERENCED_OBJECTS_DELTA);
		createEReference(referencedObjectsDeltaEClass, REFERENCED_OBJECTS_DELTA__REVISED_OBJECT_REFERENCES);

		originalObjectReferenceEClass = createEClass(ORIGINAL_OBJECT_REFERENCE);
		createEReference(originalObjectReferenceEClass, ORIGINAL_OBJECT_REFERENCE__ORIGINAL_OBJECT);

		revisedObjectReferenceEClass = createEClass(REVISED_OBJECT_REFERENCE);
		createEReference(revisedObjectReferenceEClass, REVISED_OBJECT_REFERENCE__REVISED_OBJECT);

		objectReferenceEClass = createEClass(OBJECT_REFERENCE);

		objectContainmentEClass = createEClass(OBJECT_CONTAINMENT);

		originalObjectContainmentEClass = createEClass(ORIGINAL_OBJECT_CONTAINMENT);
		createEReference(originalObjectContainmentEClass, ORIGINAL_OBJECT_CONTAINMENT__ORIGINAL_OBJECT);

		revisedObjectContainmentEClass = createEClass(REVISED_OBJECT_CONTAINMENT);
		createEReference(revisedObjectContainmentEClass, REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT);

		trashEClass = createEClass(TRASH);
		createEReference(trashEClass, TRASH__CONTENTS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dataValuesDeltaEClass.getESuperTypes().add(this.getValuesDelta());
		containedObjectsDeltaEClass.getESuperTypes().add(this.getValuesDelta());
		referencedObjectsDeltaEClass.getESuperTypes().add(this.getValuesDelta());
		originalObjectReferenceEClass.getESuperTypes().add(this.getObjectReference());
		revisedObjectReferenceEClass.getESuperTypes().add(this.getObjectReference());
		originalObjectContainmentEClass.getESuperTypes().add(this.getObjectContainment());
		revisedObjectContainmentEClass.getESuperTypes().add(this.getObjectContainment());

		// Initialize classes, features, and operations; add parameters
		initEClass(objectDeltaEClass, ObjectDelta.class, "ObjectDelta", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getObjectDelta_SettingDeltas(), this.getSettingDelta(), null, "settingDeltas", null, 0, -1, ObjectDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObjectDelta_OriginalClass(), theEcorePackage.getEClass(), null, "originalClass", null, 0, 1, ObjectDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObjectDelta_OriginalIndex(), ecorePackage.getEInt(), "originalIndex", null, 0, 1, ObjectDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObjectDelta_OriginalProxy(), theEcorePackage.getEObject(), null, "originalProxy", null, 0, 1, ObjectDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(settingDeltaEClass, SettingDelta.class, "SettingDelta", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSettingDelta_ValueDeltas(), this.getValuesDelta(), null, "valueDeltas", null, 0, -1, SettingDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSettingDelta_FeatureID(), ecorePackage.getEInt(), "featureID", null, 0, 1, SettingDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSettingDelta_MatchedObjects(), this.getObjectDelta(), null, "matchedObjects", null, 0, -1, SettingDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valuesDeltaEClass, ValuesDelta.class, "ValuesDelta", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValuesDelta_OriginalStart(), ecorePackage.getEInt(), "originalStart", null, 0, 1, ValuesDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValuesDelta_OriginalEnd(), ecorePackage.getEInt(), "originalEnd", null, 0, 1, ValuesDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataValuesDeltaEClass, DataValuesDelta.class, "DataValuesDelta", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDataValuesDelta_RevisedValues(), ecorePackage.getEJavaObject(), "revisedValues", null, 0, -1, DataValuesDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(containedObjectsDeltaEClass, ContainedObjectsDelta.class, "ContainedObjectsDelta", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContainedObjectsDelta_RevisedObjectContainments(), this.getObjectContainment(), null, "revisedObjectContainments", null, 0, -1, ContainedObjectsDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referencedObjectsDeltaEClass, ReferencedObjectsDelta.class, "ReferencedObjectsDelta", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReferencedObjectsDelta_RevisedObjectReferences(), this.getObjectReference(), null, "revisedObjectReferences", null, 0, -1, ReferencedObjectsDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(originalObjectReferenceEClass, OriginalObjectReference.class, "OriginalObjectReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOriginalObjectReference_OriginalObject(), this.getObjectDelta(), null, "originalObject", null, 0, 1, OriginalObjectReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(revisedObjectReferenceEClass, RevisedObjectReference.class, "RevisedObjectReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRevisedObjectReference_RevisedObject(), theEcorePackage.getEObject(), null, "revisedObject", null, 0, 1, RevisedObjectReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(objectReferenceEClass, ObjectReference.class, "ObjectReference", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(objectContainmentEClass, ObjectContainment.class, "ObjectContainment", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(originalObjectContainmentEClass, OriginalObjectContainment.class, "OriginalObjectContainment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOriginalObjectContainment_OriginalObject(), this.getObjectDelta(), null, "originalObject", null, 0, 1, OriginalObjectContainment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(revisedObjectContainmentEClass, RevisedObjectContainment.class, "RevisedObjectContainment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRevisedObjectContainment_RevisedObject(), theEcorePackage.getEObject(), null, "revisedObject", null, 0, 1, RevisedObjectContainment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trashEClass, Trash.class, "Trash", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrash_Contents(), theEcorePackage.getEObject(), null, "contents", null, 0, -1, Trash.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //EmfCompressPackageImpl
