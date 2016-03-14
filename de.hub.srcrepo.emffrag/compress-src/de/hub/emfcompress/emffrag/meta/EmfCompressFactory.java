/**
 */
package de.hub.emfcompress.emffrag.meta;

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

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.emfcompress.emffrag.meta.EmfCompressPackage
 * @generated
 */
public interface EmfCompressFactory extends de.hub.emfcompress.EmfCompressFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmfCompressFactory eINSTANCE = de.hub.emfcompress.emffrag.impl.EmfCompressFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Object Delta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Delta</em>'.
	 * @generated
	 */
	ObjectDelta createObjectDelta();

	/**
	 * Returns a new object of class '<em>Setting Delta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Setting Delta</em>'.
	 * @generated
	 */
	SettingDelta createSettingDelta();

	/**
	 * Returns a new object of class '<em>Data Values Delta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Values Delta</em>'.
	 * @generated
	 */
	DataValuesDelta createDataValuesDelta();

	/**
	 * Returns a new object of class '<em>Contained Objects Delta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Contained Objects Delta</em>'.
	 * @generated
	 */
	ContainedObjectsDelta createContainedObjectsDelta();

	/**
	 * Returns a new object of class '<em>Referenced Objects Delta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Referenced Objects Delta</em>'.
	 * @generated
	 */
	ReferencedObjectsDelta createReferencedObjectsDelta();

	/**
	 * Returns a new object of class '<em>Original Object Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Original Object Reference</em>'.
	 * @generated
	 */
	OriginalObjectReference createOriginalObjectReference();

	/**
	 * Returns a new object of class '<em>Revised Object Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Revised Object Reference</em>'.
	 * @generated
	 */
	RevisedObjectReference createRevisedObjectReference();

	/**
	 * Returns a new object of class '<em>Original Object Containment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Original Object Containment</em>'.
	 * @generated
	 */
	OriginalObjectContainment createOriginalObjectContainment();

	/**
	 * Returns a new object of class '<em>Revised Object Containment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Revised Object Containment</em>'.
	 * @generated
	 */
	RevisedObjectContainment createRevisedObjectContainment();

	/**
	 * Returns a new object of class '<em>Trash</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Trash</em>'.
	 * @generated
	 */
	Trash createTrash();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EmfCompressPackage getEmfCompressPackage();

} //EmfCompressFactory
