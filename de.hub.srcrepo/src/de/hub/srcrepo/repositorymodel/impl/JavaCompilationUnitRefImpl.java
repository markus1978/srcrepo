/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Compilation Unit Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.JavaCompilationUnitRefImpl#getCompilationUnitModel <em>Compilation Unit Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaCompilationUnitRefImpl extends AbstractFileRefImpl implements JavaCompilationUnitRef {
	/**
	 * The cached value of the '{@link #getCompilationUnitModel() <em>Compilation Unit Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompilationUnitModel()
	 * @generated
	 * @ordered
	 */
	protected CompilationUnitModel compilationUnitModel;

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
	public CompilationUnitModel getCompilationUnitModel() {
		return compilationUnitModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompilationUnitModel(CompilationUnitModel newCompilationUnitModel, NotificationChain msgs) {
		CompilationUnitModel oldCompilationUnitModel = compilationUnitModel;
		compilationUnitModel = newCompilationUnitModel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL, oldCompilationUnitModel, newCompilationUnitModel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompilationUnitModel(CompilationUnitModel newCompilationUnitModel) {
		if (newCompilationUnitModel != compilationUnitModel) {
			NotificationChain msgs = null;
			if (compilationUnitModel != null)
				msgs = ((InternalEObject)compilationUnitModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL, null, msgs);
			if (newCompilationUnitModel != null)
				msgs = ((InternalEObject)newCompilationUnitModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL, null, msgs);
			msgs = basicSetCompilationUnitModel(newCompilationUnitModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL, newCompilationUnitModel, newCompilationUnitModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL:
				return basicSetCompilationUnitModel(null, msgs);
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL:
				return getCompilationUnitModel();
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL:
				setCompilationUnitModel((CompilationUnitModel)newValue);
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL:
				setCompilationUnitModel((CompilationUnitModel)null);
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL:
				return compilationUnitModel != null;
		}
		return super.eIsSet(featureID);
	}

} //JavaCompilationUnitRefImpl
