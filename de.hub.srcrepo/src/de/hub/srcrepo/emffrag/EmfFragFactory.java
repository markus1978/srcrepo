/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.emffrag;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.emffrag.EmfFragPackage
 * @generated
 */
public interface EmfFragFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmfFragFactory eINSTANCE = de.hub.srcrepo.emffrag.impl.EmfFragFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Indexed Map</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Indexed Map</em>'.
	 * @generated
	 */
	<K, V> IndexedMap<K, V> createIndexedMap();

	/**
	 * Returns a new object of class '<em>Indexed List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Indexed List</em>'.
	 * @generated
	 */
	<V> IndexedList<V> createIndexedList();

	/**
	 * Returns a new object of class '<em>Containment Indexed Map</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Containment Indexed Map</em>'.
	 * @generated
	 */
	<K, V> ContainmentIndexedMap<K, V> createContainmentIndexedMap();

	/**
	 * Returns a new object of class '<em>Containment Indexed List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Containment Indexed List</em>'.
	 * @generated
	 */
	<V> ContainmentIndexedList<V> createContainmentIndexedList();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EmfFragPackage getEmfFragPackage();

} //EmfFragFactory
