/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.emffrag.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import de.hub.srcrepo.nofrag.emffrag.EmfFragPackage;
import de.hub.srcrepo.nofrag.emffrag.IndexedMap;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Indexed Map</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.nofrag.emffrag.impl.IndexedMapImpl#getFirstKey <em>First Key</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.emffrag.impl.IndexedMapImpl#getLastKey <em>Last Key</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IndexedMapImpl<K, V> extends EObjectImpl implements IndexedMap<K, V> {
	/**
	 * The cached value of the '{@link #getFirstKey() <em>First Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstKey()
	 * @generated
	 * @ordered
	 */
	protected K firstKey;

	/**
	 * This is true if the First Key attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean firstKeyESet;

	/**
	 * The cached value of the '{@link #getLastKey() <em>Last Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastKey()
	 * @generated
	 * @ordered
	 */
	protected K lastKey;

	/**
	 * This is true if the Last Key attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean lastKeyESet;
	
	/**
	 * @generated NOT
	 */
	private Map<K,V> map = new HashMap<K, V>();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IndexedMapImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfFragPackage.Literals.INDEXED_MAP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public K getFirstKey() {
		return firstKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFirstKey(K newFirstKey) {
		K oldFirstKey = firstKey;
		firstKey = newFirstKey;
		boolean oldFirstKeyESet = firstKeyESet;
		firstKeyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfFragPackage.INDEXED_MAP__FIRST_KEY, oldFirstKey, firstKey, !oldFirstKeyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFirstKey() {
		K oldFirstKey = firstKey;
		boolean oldFirstKeyESet = firstKeyESet;
		firstKey = null;
		firstKeyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, EmfFragPackage.INDEXED_MAP__FIRST_KEY, oldFirstKey, null, oldFirstKeyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFirstKey() {
		return firstKeyESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public K getLastKey() {
		return lastKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastKey(K newLastKey) {
		K oldLastKey = lastKey;
		lastKey = newLastKey;
		boolean oldLastKeyESet = lastKeyESet;
		lastKeyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfFragPackage.INDEXED_MAP__LAST_KEY, oldLastKey, lastKey, !oldLastKeyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLastKey() {
		K oldLastKey = lastKey;
		boolean oldLastKeyESet = lastKeyESet;
		lastKey = null;
		lastKeyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, EmfFragPackage.INDEXED_MAP__LAST_KEY, oldLastKey, null, oldLastKeyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLastKey() {
		return lastKeyESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Iterator<V> iterator() {
		return map.values().iterator();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Iterator<V> iterator(K from, K to) {
		return map.values().iterator();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public V exact(K key) {
		return map.get(key);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public V next(K key) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void put(K key, V value) {
		map.put(key, value);
		if (!firstKeyESet) {
			firstKey = key;
			firstKeyESet = true;
		} else {
			// TODO
		}
		if (!lastKeyESet) {
			lastKey = key;
			lastKeyESet = true;
		} else {
			// TODO
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<byte[]> keyToBytes(K key) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public K bytesToKey(byte[] bytes) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmfFragPackage.INDEXED_MAP__FIRST_KEY:
				return getFirstKey();
			case EmfFragPackage.INDEXED_MAP__LAST_KEY:
				return getLastKey();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EmfFragPackage.INDEXED_MAP__FIRST_KEY:
				setFirstKey((K)newValue);
				return;
			case EmfFragPackage.INDEXED_MAP__LAST_KEY:
				setLastKey((K)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case EmfFragPackage.INDEXED_MAP__FIRST_KEY:
				unsetFirstKey();
				return;
			case EmfFragPackage.INDEXED_MAP__LAST_KEY:
				unsetLastKey();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EmfFragPackage.INDEXED_MAP__FIRST_KEY:
				return isSetFirstKey();
			case EmfFragPackage.INDEXED_MAP__LAST_KEY:
				return isSetLastKey();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (firstKey: ");
		if (firstKeyESet) result.append(firstKey); else result.append("<unset>");
		result.append(", lastKey: ");
		if (lastKeyESet) result.append(lastKey); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //IndexedMapImpl
