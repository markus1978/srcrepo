/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.emffrag.impl;

import de.hub.srcrepo.emffrag.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.hub.srcrepo.emffrag.ContainmentIndexedList;
import de.hub.srcrepo.emffrag.ContainmentIndexedMap;
import de.hub.srcrepo.emffrag.EmfFragFactory;
import de.hub.srcrepo.emffrag.EmfFragPackage;
import de.hub.srcrepo.emffrag.IndexedList;
import de.hub.srcrepo.emffrag.IndexedMap;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmfFragFactoryImpl extends EFactoryImpl implements EmfFragFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EmfFragFactory init() {
		try {
			EmfFragFactory theEmfFragFactory = (EmfFragFactory)EPackage.Registry.INSTANCE.getEFactory("http://de.hub.emffrag/emffrag"); 
			if (theEmfFragFactory != null) {
				return theEmfFragFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EmfFragFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfFragFactoryImpl() {
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
			case EmfFragPackage.INDEXED_MAP: return createIndexedMap();
			case EmfFragPackage.INDEXED_LIST: return createIndexedList();
			case EmfFragPackage.CONTAINMENT_INDEXED_MAP: return createContainmentIndexedMap();
			case EmfFragPackage.CONTAINMENT_INDEXED_LIST: return createContainmentIndexedList();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <K, V> IndexedMap<K, V> createIndexedMap() {
		IndexedMapImpl<K, V> indexedMap = new IndexedMapImpl<K, V>();
		return indexedMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <V> IndexedList<V> createIndexedList() {
		IndexedListImpl<V> indexedList = new IndexedListImpl<V>();
		return indexedList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <K, V> ContainmentIndexedMap<K, V> createContainmentIndexedMap() {
		ContainmentIndexedMapImpl<K, V> containmentIndexedMap = new ContainmentIndexedMapImpl<K, V>();
		return containmentIndexedMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <V> ContainmentIndexedList<V> createContainmentIndexedList() {
		ContainmentIndexedListImpl<V> containmentIndexedList = new ContainmentIndexedListImpl<V>();
		return containmentIndexedList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfFragPackage getEmfFragPackage() {
		return (EmfFragPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EmfFragPackage getPackage() {
		return EmfFragPackage.eINSTANCE;
	}

} //EmfFragFactoryImpl
