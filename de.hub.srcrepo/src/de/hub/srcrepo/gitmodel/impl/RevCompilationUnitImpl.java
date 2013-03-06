/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.gmt.modisco.java.emf.impl.CompilationUnitImpl;

import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.RevCompilationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rev Compilation Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.RevCompilationUnitImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.RevCompilationUnitImpl#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RevCompilationUnitImpl extends CompilationUnitImpl implements RevCompilationUnit {
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected EList<RevCompilationUnit> parent;
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<RevCompilationUnit> children;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RevCompilationUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GitModelPackage.Literals.REV_COMPILATION_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RevCompilationUnit> getParent() {
		if (parent == null) {
			parent = new EObjectWithInverseResolvingEList.ManyInverse<RevCompilationUnit>(RevCompilationUnit.class, this, GitModelPackage.REV_COMPILATION_UNIT__PARENT, GitModelPackage.REV_COMPILATION_UNIT__CHILDREN);
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RevCompilationUnit> getChildren() {
		if (children == null) {
			children = new EObjectWithInverseResolvingEList.ManyInverse<RevCompilationUnit>(RevCompilationUnit.class, this, GitModelPackage.REV_COMPILATION_UNIT__CHILDREN, GitModelPackage.REV_COMPILATION_UNIT__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GitModelPackage.REV_COMPILATION_UNIT__PARENT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getParent()).basicAdd(otherEnd, msgs);
			case GitModelPackage.REV_COMPILATION_UNIT__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GitModelPackage.REV_COMPILATION_UNIT__PARENT:
				return ((InternalEList<?>)getParent()).basicRemove(otherEnd, msgs);
			case GitModelPackage.REV_COMPILATION_UNIT__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GitModelPackage.REV_COMPILATION_UNIT__PARENT:
				return getParent();
			case GitModelPackage.REV_COMPILATION_UNIT__CHILDREN:
				return getChildren();
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
			case GitModelPackage.REV_COMPILATION_UNIT__PARENT:
				getParent().clear();
				getParent().addAll((Collection<? extends RevCompilationUnit>)newValue);
				return;
			case GitModelPackage.REV_COMPILATION_UNIT__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends RevCompilationUnit>)newValue);
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
			case GitModelPackage.REV_COMPILATION_UNIT__PARENT:
				getParent().clear();
				return;
			case GitModelPackage.REV_COMPILATION_UNIT__CHILDREN:
				getChildren().clear();
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
			case GitModelPackage.REV_COMPILATION_UNIT__PARENT:
				return parent != null && !parent.isEmpty();
			case GitModelPackage.REV_COMPILATION_UNIT__CHILDREN:
				return children != null && !children.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RevCompilationUnitImpl
