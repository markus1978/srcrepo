/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.gmt.modisco.java.emffrag.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.gmt.modisco.java.emffrag.impl.ClassDeclarationImpl#getSuperClass <em>Super Class</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassDeclarationImpl extends TypeDeclarationImpl implements ClassDeclaration {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JavaPackage.eINSTANCE.getClassDeclaration();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeAccess getSuperClass() {
		return (TypeAccess)eGet(JavaPackage.eINSTANCE.getClassDeclaration_SuperClass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperClass(TypeAccess newSuperClass) {
		eSet(JavaPackage.eINSTANCE.getClassDeclaration_SuperClass(), newSuperClass);
	}

} //ClassDeclarationImpl
