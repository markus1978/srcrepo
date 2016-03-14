/**
 */
package de.hub.emfcompress.emffrag.meta;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.hub.emfcompress.emffrag.meta.EmfCompressFactory
 * @model kind="package"
 * @generated
 */
public interface EmfCompressPackage extends de.hub.emfcompress.EmfCompressPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "emfcompress";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://de.hub/EmfCompress/1.0/emffrag";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ec";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmfCompressPackage eINSTANCE = de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.ObjectDeltaImpl <em>Object Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.ObjectDeltaImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getObjectDelta()
	 * @generated
	 */
	int OBJECT_DELTA = 0;

	/**
	 * The feature id for the '<em><b>Setting Deltas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_DELTA__SETTING_DELTAS = 0;

	/**
	 * The feature id for the '<em><b>Original Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_DELTA__ORIGINAL_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Original Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_DELTA__ORIGINAL_INDEX = 2;

	/**
	 * The feature id for the '<em><b>Original Proxy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_DELTA__ORIGINAL_PROXY = 3;

	/**
	 * The number of structural features of the '<em>Object Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_DELTA_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Object Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_DELTA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.SettingDeltaImpl <em>Setting Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.SettingDeltaImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getSettingDelta()
	 * @generated
	 */
	int SETTING_DELTA = 1;

	/**
	 * The feature id for the '<em><b>Value Deltas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING_DELTA__VALUE_DELTAS = 0;

	/**
	 * The feature id for the '<em><b>Feature ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING_DELTA__FEATURE_ID = 1;

	/**
	 * The feature id for the '<em><b>Matched Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING_DELTA__MATCHED_OBJECTS = 2;

	/**
	 * The number of structural features of the '<em>Setting Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING_DELTA_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Setting Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTING_DELTA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.ValuesDeltaImpl <em>Values Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.ValuesDeltaImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getValuesDelta()
	 * @generated
	 */
	int VALUES_DELTA = 2;

	/**
	 * The feature id for the '<em><b>Original Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUES_DELTA__ORIGINAL_START = 0;

	/**
	 * The feature id for the '<em><b>Original End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUES_DELTA__ORIGINAL_END = 1;

	/**
	 * The number of structural features of the '<em>Values Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUES_DELTA_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Values Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUES_DELTA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.DataValuesDeltaImpl <em>Data Values Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.DataValuesDeltaImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getDataValuesDelta()
	 * @generated
	 */
	int DATA_VALUES_DELTA = 3;

	/**
	 * The feature id for the '<em><b>Original Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUES_DELTA__ORIGINAL_START = VALUES_DELTA__ORIGINAL_START;

	/**
	 * The feature id for the '<em><b>Original End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUES_DELTA__ORIGINAL_END = VALUES_DELTA__ORIGINAL_END;

	/**
	 * The feature id for the '<em><b>Revised Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUES_DELTA__REVISED_VALUES = VALUES_DELTA_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Data Values Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUES_DELTA_FEATURE_COUNT = VALUES_DELTA_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Data Values Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_VALUES_DELTA_OPERATION_COUNT = VALUES_DELTA_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.ContainedObjectsDeltaImpl <em>Contained Objects Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.ContainedObjectsDeltaImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getContainedObjectsDelta()
	 * @generated
	 */
	int CONTAINED_OBJECTS_DELTA = 4;

	/**
	 * The feature id for the '<em><b>Original Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_OBJECTS_DELTA__ORIGINAL_START = VALUES_DELTA__ORIGINAL_START;

	/**
	 * The feature id for the '<em><b>Original End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_OBJECTS_DELTA__ORIGINAL_END = VALUES_DELTA__ORIGINAL_END;

	/**
	 * The feature id for the '<em><b>Revised Object Containments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_OBJECTS_DELTA__REVISED_OBJECT_CONTAINMENTS = VALUES_DELTA_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Contained Objects Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_OBJECTS_DELTA_FEATURE_COUNT = VALUES_DELTA_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Contained Objects Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_OBJECTS_DELTA_OPERATION_COUNT = VALUES_DELTA_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.ReferencedObjectsDeltaImpl <em>Referenced Objects Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.ReferencedObjectsDeltaImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getReferencedObjectsDelta()
	 * @generated
	 */
	int REFERENCED_OBJECTS_DELTA = 5;

	/**
	 * The feature id for the '<em><b>Original Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCED_OBJECTS_DELTA__ORIGINAL_START = VALUES_DELTA__ORIGINAL_START;

	/**
	 * The feature id for the '<em><b>Original End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCED_OBJECTS_DELTA__ORIGINAL_END = VALUES_DELTA__ORIGINAL_END;

	/**
	 * The feature id for the '<em><b>Revised Object References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCED_OBJECTS_DELTA__REVISED_OBJECT_REFERENCES = VALUES_DELTA_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Referenced Objects Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCED_OBJECTS_DELTA_FEATURE_COUNT = VALUES_DELTA_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Referenced Objects Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCED_OBJECTS_DELTA_OPERATION_COUNT = VALUES_DELTA_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.ObjectReferenceImpl <em>Object Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.ObjectReferenceImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getObjectReference()
	 * @generated
	 */
	int OBJECT_REFERENCE = 8;

	/**
	 * The number of structural features of the '<em>Object Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_REFERENCE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Object Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_REFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.OriginalObjectReferenceImpl <em>Original Object Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.OriginalObjectReferenceImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getOriginalObjectReference()
	 * @generated
	 */
	int ORIGINAL_OBJECT_REFERENCE = 6;

	/**
	 * The feature id for the '<em><b>Original Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORIGINAL_OBJECT_REFERENCE__ORIGINAL_OBJECT = OBJECT_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Original Object Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORIGINAL_OBJECT_REFERENCE_FEATURE_COUNT = OBJECT_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Original Object Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORIGINAL_OBJECT_REFERENCE_OPERATION_COUNT = OBJECT_REFERENCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.RevisedObjectReferenceImpl <em>Revised Object Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.RevisedObjectReferenceImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getRevisedObjectReference()
	 * @generated
	 */
	int REVISED_OBJECT_REFERENCE = 7;

	/**
	 * The feature id for the '<em><b>Revised Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISED_OBJECT_REFERENCE__REVISED_OBJECT = OBJECT_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Revised Object Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISED_OBJECT_REFERENCE_FEATURE_COUNT = OBJECT_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Revised Object Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISED_OBJECT_REFERENCE_OPERATION_COUNT = OBJECT_REFERENCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.ObjectContainmentImpl <em>Object Containment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.ObjectContainmentImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getObjectContainment()
	 * @generated
	 */
	int OBJECT_CONTAINMENT = 9;

	/**
	 * The number of structural features of the '<em>Object Containment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_CONTAINMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Object Containment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_CONTAINMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.OriginalObjectContainmentImpl <em>Original Object Containment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.OriginalObjectContainmentImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getOriginalObjectContainment()
	 * @generated
	 */
	int ORIGINAL_OBJECT_CONTAINMENT = 10;

	/**
	 * The feature id for the '<em><b>Original Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORIGINAL_OBJECT_CONTAINMENT__ORIGINAL_OBJECT = OBJECT_CONTAINMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Original Object Containment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORIGINAL_OBJECT_CONTAINMENT_FEATURE_COUNT = OBJECT_CONTAINMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Original Object Containment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORIGINAL_OBJECT_CONTAINMENT_OPERATION_COUNT = OBJECT_CONTAINMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.RevisedObjectContainmentImpl <em>Revised Object Containment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.RevisedObjectContainmentImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getRevisedObjectContainment()
	 * @generated
	 */
	int REVISED_OBJECT_CONTAINMENT = 11;

	/**
	 * The feature id for the '<em><b>Revised Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT = OBJECT_CONTAINMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Revised Object Containment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISED_OBJECT_CONTAINMENT_FEATURE_COUNT = OBJECT_CONTAINMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Revised Object Containment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVISED_OBJECT_CONTAINMENT_OPERATION_COUNT = OBJECT_CONTAINMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.emfcompress.emffrag.impl.TrashImpl <em>Trash</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.emfcompress.emffrag.impl.TrashImpl
	 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getTrash()
	 * @generated
	 */
	int TRASH = 12;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRASH__CONTENTS = 0;

	/**
	 * The number of structural features of the '<em>Trash</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRASH_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Trash</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRASH_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.ObjectDelta <em>Object Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Delta</em>'.
	 * @see de.hub.emfcompress.ObjectDelta
	 * @generated
	 */
	EClass getObjectDelta();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.emfcompress.ObjectDelta#getSettingDeltas <em>Setting Deltas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Setting Deltas</em>'.
	 * @see de.hub.emfcompress.ObjectDelta#getSettingDeltas()
	 * @see #getObjectDelta()
	 * @generated
	 */
	EReference getObjectDelta_SettingDeltas();

	/**
	 * Returns the meta object for the reference '{@link de.hub.emfcompress.ObjectDelta#getOriginalClass <em>Original Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Original Class</em>'.
	 * @see de.hub.emfcompress.ObjectDelta#getOriginalClass()
	 * @see #getObjectDelta()
	 * @generated
	 */
	EReference getObjectDelta_OriginalClass();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.emfcompress.ObjectDelta#getOriginalIndex <em>Original Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Original Index</em>'.
	 * @see de.hub.emfcompress.ObjectDelta#getOriginalIndex()
	 * @see #getObjectDelta()
	 * @generated
	 */
	EAttribute getObjectDelta_OriginalIndex();

	/**
	 * Returns the meta object for the containment reference '{@link de.hub.emfcompress.ObjectDelta#getOriginalProxy <em>Original Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Original Proxy</em>'.
	 * @see de.hub.emfcompress.ObjectDelta#getOriginalProxy()
	 * @see #getObjectDelta()
	 * @generated
	 */
	EReference getObjectDelta_OriginalProxy();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.SettingDelta <em>Setting Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Setting Delta</em>'.
	 * @see de.hub.emfcompress.SettingDelta
	 * @generated
	 */
	EClass getSettingDelta();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.emfcompress.SettingDelta#getValueDeltas <em>Value Deltas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Value Deltas</em>'.
	 * @see de.hub.emfcompress.SettingDelta#getValueDeltas()
	 * @see #getSettingDelta()
	 * @generated
	 */
	EReference getSettingDelta_ValueDeltas();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.emfcompress.SettingDelta#getFeatureID <em>Feature ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature ID</em>'.
	 * @see de.hub.emfcompress.SettingDelta#getFeatureID()
	 * @see #getSettingDelta()
	 * @generated
	 */
	EAttribute getSettingDelta_FeatureID();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.emfcompress.SettingDelta#getMatchedObjects <em>Matched Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Matched Objects</em>'.
	 * @see de.hub.emfcompress.SettingDelta#getMatchedObjects()
	 * @see #getSettingDelta()
	 * @generated
	 */
	EReference getSettingDelta_MatchedObjects();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.ValuesDelta <em>Values Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Values Delta</em>'.
	 * @see de.hub.emfcompress.ValuesDelta
	 * @generated
	 */
	EClass getValuesDelta();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.emfcompress.ValuesDelta#getOriginalStart <em>Original Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Original Start</em>'.
	 * @see de.hub.emfcompress.ValuesDelta#getOriginalStart()
	 * @see #getValuesDelta()
	 * @generated
	 */
	EAttribute getValuesDelta_OriginalStart();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.emfcompress.ValuesDelta#getOriginalEnd <em>Original End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Original End</em>'.
	 * @see de.hub.emfcompress.ValuesDelta#getOriginalEnd()
	 * @see #getValuesDelta()
	 * @generated
	 */
	EAttribute getValuesDelta_OriginalEnd();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.DataValuesDelta <em>Data Values Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Values Delta</em>'.
	 * @see de.hub.emfcompress.DataValuesDelta
	 * @generated
	 */
	EClass getDataValuesDelta();

	/**
	 * Returns the meta object for the attribute list '{@link de.hub.emfcompress.DataValuesDelta#getRevisedValues <em>Revised Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Revised Values</em>'.
	 * @see de.hub.emfcompress.DataValuesDelta#getRevisedValues()
	 * @see #getDataValuesDelta()
	 * @generated
	 */
	EAttribute getDataValuesDelta_RevisedValues();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.ContainedObjectsDelta <em>Contained Objects Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contained Objects Delta</em>'.
	 * @see de.hub.emfcompress.ContainedObjectsDelta
	 * @generated
	 */
	EClass getContainedObjectsDelta();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.emfcompress.ContainedObjectsDelta#getRevisedObjectContainments <em>Revised Object Containments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Revised Object Containments</em>'.
	 * @see de.hub.emfcompress.ContainedObjectsDelta#getRevisedObjectContainments()
	 * @see #getContainedObjectsDelta()
	 * @generated
	 */
	EReference getContainedObjectsDelta_RevisedObjectContainments();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.ReferencedObjectsDelta <em>Referenced Objects Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Referenced Objects Delta</em>'.
	 * @see de.hub.emfcompress.ReferencedObjectsDelta
	 * @generated
	 */
	EClass getReferencedObjectsDelta();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.emfcompress.ReferencedObjectsDelta#getRevisedObjectReferences <em>Revised Object References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Revised Object References</em>'.
	 * @see de.hub.emfcompress.ReferencedObjectsDelta#getRevisedObjectReferences()
	 * @see #getReferencedObjectsDelta()
	 * @generated
	 */
	EReference getReferencedObjectsDelta_RevisedObjectReferences();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.OriginalObjectReference <em>Original Object Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Original Object Reference</em>'.
	 * @see de.hub.emfcompress.OriginalObjectReference
	 * @generated
	 */
	EClass getOriginalObjectReference();

	/**
	 * Returns the meta object for the reference '{@link de.hub.emfcompress.OriginalObjectReference#getOriginalObject <em>Original Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Original Object</em>'.
	 * @see de.hub.emfcompress.OriginalObjectReference#getOriginalObject()
	 * @see #getOriginalObjectReference()
	 * @generated
	 */
	EReference getOriginalObjectReference_OriginalObject();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.RevisedObjectReference <em>Revised Object Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Revised Object Reference</em>'.
	 * @see de.hub.emfcompress.RevisedObjectReference
	 * @generated
	 */
	EClass getRevisedObjectReference();

	/**
	 * Returns the meta object for the reference '{@link de.hub.emfcompress.RevisedObjectReference#getRevisedObject <em>Revised Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Revised Object</em>'.
	 * @see de.hub.emfcompress.RevisedObjectReference#getRevisedObject()
	 * @see #getRevisedObjectReference()
	 * @generated
	 */
	EReference getRevisedObjectReference_RevisedObject();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.ObjectReference <em>Object Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Reference</em>'.
	 * @see de.hub.emfcompress.ObjectReference
	 * @generated
	 */
	EClass getObjectReference();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.ObjectContainment <em>Object Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Containment</em>'.
	 * @see de.hub.emfcompress.ObjectContainment
	 * @generated
	 */
	EClass getObjectContainment();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.OriginalObjectContainment <em>Original Object Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Original Object Containment</em>'.
	 * @see de.hub.emfcompress.OriginalObjectContainment
	 * @generated
	 */
	EClass getOriginalObjectContainment();

	/**
	 * Returns the meta object for the reference '{@link de.hub.emfcompress.OriginalObjectContainment#getOriginalObject <em>Original Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Original Object</em>'.
	 * @see de.hub.emfcompress.OriginalObjectContainment#getOriginalObject()
	 * @see #getOriginalObjectContainment()
	 * @generated
	 */
	EReference getOriginalObjectContainment_OriginalObject();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.RevisedObjectContainment <em>Revised Object Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Revised Object Containment</em>'.
	 * @see de.hub.emfcompress.RevisedObjectContainment
	 * @generated
	 */
	EClass getRevisedObjectContainment();

	/**
	 * Returns the meta object for the containment reference '{@link de.hub.emfcompress.RevisedObjectContainment#getRevisedObject <em>Revised Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Revised Object</em>'.
	 * @see de.hub.emfcompress.RevisedObjectContainment#getRevisedObject()
	 * @see #getRevisedObjectContainment()
	 * @generated
	 */
	EReference getRevisedObjectContainment_RevisedObject();

	/**
	 * Returns the meta object for class '{@link de.hub.emfcompress.Trash <em>Trash</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trash</em>'.
	 * @see de.hub.emfcompress.Trash
	 * @generated
	 */
	EClass getTrash();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.emfcompress.Trash#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see de.hub.emfcompress.Trash#getContents()
	 * @see #getTrash()
	 * @generated
	 */
	EReference getTrash_Contents();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EmfCompressFactory getEmfCompressFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.ObjectDeltaImpl <em>Object Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.ObjectDeltaImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getObjectDelta()
		 * @generated
		 */
		EClass OBJECT_DELTA = eINSTANCE.getObjectDelta();

		/**
		 * The meta object literal for the '<em><b>Setting Deltas</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_DELTA__SETTING_DELTAS = eINSTANCE.getObjectDelta_SettingDeltas();

		/**
		 * The meta object literal for the '<em><b>Original Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_DELTA__ORIGINAL_CLASS = eINSTANCE.getObjectDelta_OriginalClass();

		/**
		 * The meta object literal for the '<em><b>Original Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECT_DELTA__ORIGINAL_INDEX = eINSTANCE.getObjectDelta_OriginalIndex();

		/**
		 * The meta object literal for the '<em><b>Original Proxy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_DELTA__ORIGINAL_PROXY = eINSTANCE.getObjectDelta_OriginalProxy();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.SettingDeltaImpl <em>Setting Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.SettingDeltaImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getSettingDelta()
		 * @generated
		 */
		EClass SETTING_DELTA = eINSTANCE.getSettingDelta();

		/**
		 * The meta object literal for the '<em><b>Value Deltas</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SETTING_DELTA__VALUE_DELTAS = eINSTANCE.getSettingDelta_ValueDeltas();

		/**
		 * The meta object literal for the '<em><b>Feature ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SETTING_DELTA__FEATURE_ID = eINSTANCE.getSettingDelta_FeatureID();

		/**
		 * The meta object literal for the '<em><b>Matched Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SETTING_DELTA__MATCHED_OBJECTS = eINSTANCE.getSettingDelta_MatchedObjects();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.ValuesDeltaImpl <em>Values Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.ValuesDeltaImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getValuesDelta()
		 * @generated
		 */
		EClass VALUES_DELTA = eINSTANCE.getValuesDelta();

		/**
		 * The meta object literal for the '<em><b>Original Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUES_DELTA__ORIGINAL_START = eINSTANCE.getValuesDelta_OriginalStart();

		/**
		 * The meta object literal for the '<em><b>Original End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUES_DELTA__ORIGINAL_END = eINSTANCE.getValuesDelta_OriginalEnd();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.DataValuesDeltaImpl <em>Data Values Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.DataValuesDeltaImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getDataValuesDelta()
		 * @generated
		 */
		EClass DATA_VALUES_DELTA = eINSTANCE.getDataValuesDelta();

		/**
		 * The meta object literal for the '<em><b>Revised Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_VALUES_DELTA__REVISED_VALUES = eINSTANCE.getDataValuesDelta_RevisedValues();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.ContainedObjectsDeltaImpl <em>Contained Objects Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.ContainedObjectsDeltaImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getContainedObjectsDelta()
		 * @generated
		 */
		EClass CONTAINED_OBJECTS_DELTA = eINSTANCE.getContainedObjectsDelta();

		/**
		 * The meta object literal for the '<em><b>Revised Object Containments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINED_OBJECTS_DELTA__REVISED_OBJECT_CONTAINMENTS = eINSTANCE.getContainedObjectsDelta_RevisedObjectContainments();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.ReferencedObjectsDeltaImpl <em>Referenced Objects Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.ReferencedObjectsDeltaImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getReferencedObjectsDelta()
		 * @generated
		 */
		EClass REFERENCED_OBJECTS_DELTA = eINSTANCE.getReferencedObjectsDelta();

		/**
		 * The meta object literal for the '<em><b>Revised Object References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCED_OBJECTS_DELTA__REVISED_OBJECT_REFERENCES = eINSTANCE.getReferencedObjectsDelta_RevisedObjectReferences();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.OriginalObjectReferenceImpl <em>Original Object Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.OriginalObjectReferenceImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getOriginalObjectReference()
		 * @generated
		 */
		EClass ORIGINAL_OBJECT_REFERENCE = eINSTANCE.getOriginalObjectReference();

		/**
		 * The meta object literal for the '<em><b>Original Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORIGINAL_OBJECT_REFERENCE__ORIGINAL_OBJECT = eINSTANCE.getOriginalObjectReference_OriginalObject();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.RevisedObjectReferenceImpl <em>Revised Object Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.RevisedObjectReferenceImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getRevisedObjectReference()
		 * @generated
		 */
		EClass REVISED_OBJECT_REFERENCE = eINSTANCE.getRevisedObjectReference();

		/**
		 * The meta object literal for the '<em><b>Revised Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVISED_OBJECT_REFERENCE__REVISED_OBJECT = eINSTANCE.getRevisedObjectReference_RevisedObject();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.ObjectReferenceImpl <em>Object Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.ObjectReferenceImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getObjectReference()
		 * @generated
		 */
		EClass OBJECT_REFERENCE = eINSTANCE.getObjectReference();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.ObjectContainmentImpl <em>Object Containment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.ObjectContainmentImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getObjectContainment()
		 * @generated
		 */
		EClass OBJECT_CONTAINMENT = eINSTANCE.getObjectContainment();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.OriginalObjectContainmentImpl <em>Original Object Containment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.OriginalObjectContainmentImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getOriginalObjectContainment()
		 * @generated
		 */
		EClass ORIGINAL_OBJECT_CONTAINMENT = eINSTANCE.getOriginalObjectContainment();

		/**
		 * The meta object literal for the '<em><b>Original Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORIGINAL_OBJECT_CONTAINMENT__ORIGINAL_OBJECT = eINSTANCE.getOriginalObjectContainment_OriginalObject();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.RevisedObjectContainmentImpl <em>Revised Object Containment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.RevisedObjectContainmentImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getRevisedObjectContainment()
		 * @generated
		 */
		EClass REVISED_OBJECT_CONTAINMENT = eINSTANCE.getRevisedObjectContainment();

		/**
		 * The meta object literal for the '<em><b>Revised Object</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVISED_OBJECT_CONTAINMENT__REVISED_OBJECT = eINSTANCE.getRevisedObjectContainment_RevisedObject();

		/**
		 * The meta object literal for the '{@link de.hub.emfcompress.emffrag.impl.TrashImpl <em>Trash</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.emfcompress.emffrag.impl.TrashImpl
		 * @see de.hub.emfcompress.emffrag.impl.EmfCompressPackageImpl#getTrash()
		 * @generated
		 */
		EClass TRASH = eINSTANCE.getTrash();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRASH__CONTENTS = eINSTANCE.getTrash_Contents();

	}

} //EmfCompressPackage
