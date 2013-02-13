/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.emffrag.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedList;
import de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedMap;
import de.hub.srcrepo.nofrag.emffrag.EmfFragPackage;
import de.hub.srcrepo.nofrag.emffrag.IndexedList;
import de.hub.srcrepo.nofrag.emffrag.IndexedMap;
import de.hub.srcrepo.nofrag.emffrag.IsContainment;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.nofrag.emffrag.EmfFragPackage
 * @generated
 */
public class EmfFragAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EmfFragPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfFragAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = EmfFragPackage.eINSTANCE;
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
	protected EmfFragSwitch<Adapter> modelSwitch =
		new EmfFragSwitch<Adapter>() {
			@Override
			public <K, V> Adapter caseIndexedMap(IndexedMap<K, V> object) {
				return createIndexedMapAdapter();
			}
			@Override
			public <V> Adapter caseIndexedList(IndexedList<V> object) {
				return createIndexedListAdapter();
			}
			@Override
			public Adapter caseIsContainment(IsContainment object) {
				return createIsContainmentAdapter();
			}
			@Override
			public <K, V> Adapter caseContainmentIndexedMap(ContainmentIndexedMap<K, V> object) {
				return createContainmentIndexedMapAdapter();
			}
			@Override
			public <V> Adapter caseContainmentIndexedList(ContainmentIndexedList<V> object) {
				return createContainmentIndexedListAdapter();
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
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.nofrag.emffrag.IndexedMap <em>Indexed Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.nofrag.emffrag.IndexedMap
	 * @generated
	 */
	public Adapter createIndexedMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.nofrag.emffrag.IndexedList <em>Indexed List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.nofrag.emffrag.IndexedList
	 * @generated
	 */
	public Adapter createIndexedListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.nofrag.emffrag.IsContainment <em>Is Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.nofrag.emffrag.IsContainment
	 * @generated
	 */
	public Adapter createIsContainmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedMap <em>Containment Indexed Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedMap
	 * @generated
	 */
	public Adapter createContainmentIndexedMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedList <em>Containment Indexed List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.nofrag.emffrag.ContainmentIndexedList
	 * @generated
	 */
	public Adapter createContainmentIndexedListAdapter() {
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

} //EmfFragAdapterFactory
