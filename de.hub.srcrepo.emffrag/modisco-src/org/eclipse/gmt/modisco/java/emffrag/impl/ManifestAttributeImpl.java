/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.gmt.modisco.java.emffrag.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmt.modisco.java.ManifestAttribute;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

import de.hub.emffrag.FObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Manifest Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gmt.modisco.java.emffrag.impl.ManifestAttributeImpl#getKey <em>Key</em>}</li>
 *   <li>{@link org.eclipse.gmt.modisco.java.emffrag.impl.ManifestAttributeImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ManifestAttributeImpl extends FObjectImpl implements ManifestAttribute {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManifestAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JavaPackage.eINSTANCE.getManifestAttribute();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKey() {
		return (String)eGet(JavaPackage.eINSTANCE.getManifestAttribute_Key(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(String newKey) {
		eSet(JavaPackage.eINSTANCE.getManifestAttribute_Key(), newKey);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return (String)eGet(JavaPackage.eINSTANCE.getManifestAttribute_Value(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		eSet(JavaPackage.eINSTANCE.getManifestAttribute_Value(), newValue);
	}

} //ManifestAttributeImpl
