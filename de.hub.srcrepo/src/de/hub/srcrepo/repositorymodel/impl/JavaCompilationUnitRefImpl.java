/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.gmt.modisco.java.CompilationUnit;

import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Compilation Unit Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.JavaCompilationUnitRefImpl#getCompilationUnit <em>Compilation Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaCompilationUnitRefImpl extends AbstractFileRefImpl implements JavaCompilationUnitRef {
	/**
	 * The cached value of the '{@link #getCompilationUnit() <em>Compilation Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompilationUnit()
	 * @generated
	 * @ordered
	 */
	protected CompilationUnit compilationUnit;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaCompilationUnitRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.JAVA_COMPILATION_UNIT_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilationUnit getCompilationUnit() {
		if (compilationUnit != null && compilationUnit.eIsProxy()) {
			InternalEObject oldCompilationUnit = (InternalEObject)compilationUnit;
			compilationUnit = (CompilationUnit)eResolveProxy(oldCompilationUnit);
			if (compilationUnit != oldCompilationUnit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT, oldCompilationUnit, compilationUnit));
			}
		}
		return compilationUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilationUnit basicGetCompilationUnit() {
		return compilationUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompilationUnit(CompilationUnit newCompilationUnit) {
		CompilationUnit oldCompilationUnit = compilationUnit;
		compilationUnit = newCompilationUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT, oldCompilationUnit, compilationUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT:
				if (resolve) return getCompilationUnit();
				return basicGetCompilationUnit();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT:
				setCompilationUnit((CompilationUnit)newValue);
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT:
				setCompilationUnit((CompilationUnit)null);
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT:
				return compilationUnit != null;
		}
		return super.eIsSet(featureID);
	}

} //JavaCompilationUnitRefImpl