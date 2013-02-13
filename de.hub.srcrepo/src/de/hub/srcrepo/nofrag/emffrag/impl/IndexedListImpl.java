/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.emffrag.impl;

import org.eclipse.emf.ecore.EClass;

import de.hub.srcrepo.nofrag.emffrag.EmfFragPackage;
import de.hub.srcrepo.nofrag.emffrag.IndexedList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Indexed List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class IndexedListImpl<V> extends IndexedMapImpl<Integer, V> implements IndexedList<V> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IndexedListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfFragPackage.Literals.INDEXED_LIST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void add(V value) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

} //IndexedListImpl
