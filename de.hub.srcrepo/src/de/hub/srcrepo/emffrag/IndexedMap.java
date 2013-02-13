/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.emffrag;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Indexed Map</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.emffrag.IndexedMap#getFirstKey <em>First Key</em>}</li>
 *   <li>{@link de.hub.srcrepo.emffrag.IndexedMap#getLastKey <em>Last Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.emffrag.EmfFragPackage#getIndexedMap()
 * @model
 * @generated
 */
public interface IndexedMap<K, V> extends EObject {
	/**
	 * Returns the value of the '<em><b>First Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Key</em>' attribute.
	 * @see #isSetFirstKey()
	 * @see #unsetFirstKey()
	 * @see #setFirstKey(Object)
	 * @see de.hub.srcrepo.emffrag.EmfFragPackage#getIndexedMap_FirstKey()
	 * @model unique="false" unsettable="true" transient="true" derived="true"
	 * @generated
	 */
	K getFirstKey();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.emffrag.IndexedMap#getFirstKey <em>First Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Key</em>' attribute.
	 * @see #isSetFirstKey()
	 * @see #unsetFirstKey()
	 * @see #getFirstKey()
	 * @generated
	 */
	void setFirstKey(K value);

	/**
	 * Unsets the value of the '{@link de.hub.srcrepo.emffrag.IndexedMap#getFirstKey <em>First Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFirstKey()
	 * @see #getFirstKey()
	 * @see #setFirstKey(Object)
	 * @generated
	 */
	void unsetFirstKey();

	/**
	 * Returns whether the value of the '{@link de.hub.srcrepo.emffrag.IndexedMap#getFirstKey <em>First Key</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>First Key</em>' attribute is set.
	 * @see #unsetFirstKey()
	 * @see #getFirstKey()
	 * @see #setFirstKey(Object)
	 * @generated
	 */
	boolean isSetFirstKey();

	/**
	 * Returns the value of the '<em><b>Last Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Key</em>' attribute.
	 * @see #isSetLastKey()
	 * @see #unsetLastKey()
	 * @see #setLastKey(Object)
	 * @see de.hub.srcrepo.emffrag.EmfFragPackage#getIndexedMap_LastKey()
	 * @model unique="false" unsettable="true" transient="true" derived="true"
	 * @generated
	 */
	K getLastKey();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.emffrag.IndexedMap#getLastKey <em>Last Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Key</em>' attribute.
	 * @see #isSetLastKey()
	 * @see #unsetLastKey()
	 * @see #getLastKey()
	 * @generated
	 */
	void setLastKey(K value);

	/**
	 * Unsets the value of the '{@link de.hub.srcrepo.emffrag.IndexedMap#getLastKey <em>Last Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLastKey()
	 * @see #getLastKey()
	 * @see #setLastKey(Object)
	 * @generated
	 */
	void unsetLastKey();

	/**
	 * Returns whether the value of the '{@link de.hub.srcrepo.emffrag.IndexedMap#getLastKey <em>Last Key</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Last Key</em>' attribute is set.
	 * @see #unsetLastKey()
	 * @see #getLastKey()
	 * @see #setLastKey(Object)
	 * @generated
	 */
	boolean isSetLastKey();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="de.hub.srcrepo.emffrag.Iterator<V>"
	 * @generated
	 */
	Iterator<V> iterator();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="de.hub.srcrepo.emffrag.Iterator<V>"
	 * @generated
	 */
	Iterator<V> iterator(K from, K to);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model keyRequired="true"
	 * @generated
	 */
	V exact(K key);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model keyRequired="true"
	 * @generated
	 */
	V next(K key);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model keyRequired="true"
	 * @generated
	 */
	void put(K key, V value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model keyRequired="true"
	 * @generated
	 */
	EList<byte[]> keyToBytes(K key);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model bytesRequired="true"
	 * @generated
	 */
	K bytesToKey(byte[] bytes);

} // IndexedMap
