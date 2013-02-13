/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.emffrag;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.nofrag.emffrag.EmfFragFactory
 * @model kind="package"
 * @generated
 */
public interface EmfFragPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "emffrag";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://de.hub.emffrag/emffrag";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ef";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmfFragPackage eINSTANCE = de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.IndexedMapImpl <em>Indexed Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.IndexedMapImpl
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getIndexedMap()
	 * @generated
	 */
	int INDEXED_MAP = 0;

	/**
	 * The feature id for the '<em><b>First Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_MAP__FIRST_KEY = 0;

	/**
	 * The feature id for the '<em><b>Last Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_MAP__LAST_KEY = 1;

	/**
	 * The number of structural features of the '<em>Indexed Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.IndexedListImpl <em>Indexed List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.IndexedListImpl
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getIndexedList()
	 * @generated
	 */
	int INDEXED_LIST = 1;

	/**
	 * The feature id for the '<em><b>First Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_LIST__FIRST_KEY = INDEXED_MAP__FIRST_KEY;

	/**
	 * The feature id for the '<em><b>Last Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_LIST__LAST_KEY = INDEXED_MAP__LAST_KEY;

	/**
	 * The number of structural features of the '<em>Indexed List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_LIST_FEATURE_COUNT = INDEXED_MAP_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.IsContainmentImpl <em>Is Containment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.IsContainmentImpl
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getIsContainment()
	 * @generated
	 */
	int IS_CONTAINMENT = 2;

	/**
	 * The number of structural features of the '<em>Is Containment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_CONTAINMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.ContainmentIndexedMapImpl <em>Containment Indexed Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.ContainmentIndexedMapImpl
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getContainmentIndexedMap()
	 * @generated
	 */
	int CONTAINMENT_INDEXED_MAP = 3;

	/**
	 * The feature id for the '<em><b>First Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_INDEXED_MAP__FIRST_KEY = IS_CONTAINMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Last Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_INDEXED_MAP__LAST_KEY = IS_CONTAINMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Containment Indexed Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_INDEXED_MAP_FEATURE_COUNT = IS_CONTAINMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.ContainmentIndexedListImpl <em>Containment Indexed List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.ContainmentIndexedListImpl
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getContainmentIndexedList()
	 * @generated
	 */
	int CONTAINMENT_INDEXED_LIST = 4;

	/**
	 * The feature id for the '<em><b>First Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_INDEXED_LIST__FIRST_KEY = IS_CONTAINMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Last Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_INDEXED_LIST__LAST_KEY = IS_CONTAINMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Containment Indexed List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINMENT_INDEXED_LIST_FEATURE_COUNT = IS_CONTAINMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '<em>Iterator</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Iterator
	 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getIterator()
	 * @generated
	 */
	int ITERATOR = 5;


	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.nofrag.emffrag.IndexedMap <em>Indexed Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Indexed Map</em>'.
	 * @see de.hub.srcrepo.nofrag.emffrag.IndexedMap
	 * @generated
	 */
	EClass getIndexedMap();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.nofrag.emffrag.IndexedMap#getFirstKey <em>First Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>First Key</em>'.
	 * @see de.hub.srcrepo.nofrag.emffrag.IndexedMap#getFirstKey()
	 * @see #getIndexedMap()
	 * @generated
	 */
	EAttribute getIndexedMap_FirstKey();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.nofrag.emffrag.IndexedMap#getLastKey <em>Last Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Key</em>'.
	 * @see de.hub.srcrepo.nofrag.emffrag.IndexedMap#getLastKey()
	 * @see #getIndexedMap()
	 * @generated
	 */
	EAttribute getIndexedMap_LastKey();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.nofrag.emffrag.IndexedList <em>Indexed List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Indexed List</em>'.
	 * @see de.hub.srcrepo.nofrag.emffrag.IndexedList
	 * @generated
	 */
	EClass getIndexedList();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.nofrag.emffrag.IsContainment <em>Is Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Is Containment</em>'.
	 * @see de.hub.srcrepo.nofrag.emffrag.IsContainment
	 * @generated
	 */
	EClass getIsContainment();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedMap <em>Containment Indexed Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Containment Indexed Map</em>'.
	 * @see de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedMap
	 * @generated
	 */
	EClass getContainmentIndexedMap();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedList <em>Containment Indexed List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Containment Indexed List</em>'.
	 * @see de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedList
	 * @generated
	 */
	EClass getContainmentIndexedList();

	/**
	 * Returns the meta object for data type '{@link java.util.Iterator <em>Iterator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Iterator</em>'.
	 * @see java.util.Iterator
	 * @model instanceClass="java.util.Iterator" serializeable="false" typeParameters="E"
	 * @generated
	 */
	EDataType getIterator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EmfFragFactory getEmfFragFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.IndexedMapImpl <em>Indexed Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.IndexedMapImpl
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getIndexedMap()
		 * @generated
		 */
		EClass INDEXED_MAP = eINSTANCE.getIndexedMap();

		/**
		 * The meta object literal for the '<em><b>First Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INDEXED_MAP__FIRST_KEY = eINSTANCE.getIndexedMap_FirstKey();

		/**
		 * The meta object literal for the '<em><b>Last Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INDEXED_MAP__LAST_KEY = eINSTANCE.getIndexedMap_LastKey();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.IndexedListImpl <em>Indexed List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.IndexedListImpl
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getIndexedList()
		 * @generated
		 */
		EClass INDEXED_LIST = eINSTANCE.getIndexedList();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.IsContainmentImpl <em>Is Containment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.IsContainmentImpl
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getIsContainment()
		 * @generated
		 */
		EClass IS_CONTAINMENT = eINSTANCE.getIsContainment();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.ContainmentIndexedMapImpl <em>Containment Indexed Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.ContainmentIndexedMapImpl
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getContainmentIndexedMap()
		 * @generated
		 */
		EClass CONTAINMENT_INDEXED_MAP = eINSTANCE.getContainmentIndexedMap();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.nofrag.emffrag.impl.ContainmentIndexedListImpl <em>Containment Indexed List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.ContainmentIndexedListImpl
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getContainmentIndexedList()
		 * @generated
		 */
		EClass CONTAINMENT_INDEXED_LIST = eINSTANCE.getContainmentIndexedList();

		/**
		 * The meta object literal for the '<em>Iterator</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Iterator
		 * @see de.hub.srcrepo.nofrag.emffrag.impl.EmfFragPackageImpl#getIterator()
		 * @generated
		 */
		EDataType ITERATOR = eINSTANCE.getIterator();

	}

} //EmfFragPackage
