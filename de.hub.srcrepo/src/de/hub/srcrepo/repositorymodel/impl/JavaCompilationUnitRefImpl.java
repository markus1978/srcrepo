/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;

import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.PendingElement;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Compilation Unit Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.JavaCompilationUnitRefImpl#getCompilationUnit <em>Compilation Unit</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.JavaCompilationUnitRefImpl#getJavaModel <em>Java Model</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.JavaCompilationUnitRefImpl#getPendings <em>Pendings</em>}</li>
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
	 * The cached value of the '{@link #getJavaModel() <em>Java Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaModel()
	 * @generated
	 * @ordered
	 */
	protected Model javaModel;

	/**
	 * The cached value of the '{@link #getPendings() <em>Pendings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPendings()
	 * @generated
	 * @ordered
	 */
	protected EList<PendingElement> pendings;

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
	public Model getJavaModel() {
		return javaModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJavaModel(Model newJavaModel, NotificationChain msgs) {
		Model oldJavaModel = javaModel;
		javaModel = newJavaModel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__JAVA_MODEL, oldJavaModel, newJavaModel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJavaModel(Model newJavaModel) {
		if (newJavaModel != javaModel) {
			NotificationChain msgs = null;
			if (javaModel != null)
				msgs = ((InternalEObject)javaModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__JAVA_MODEL, null, msgs);
			if (newJavaModel != null)
				msgs = ((InternalEObject)newJavaModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__JAVA_MODEL, null, msgs);
			msgs = basicSetJavaModel(newJavaModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__JAVA_MODEL, newJavaModel, newJavaModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PendingElement> getPendings() {
		if (pendings == null) {
			pendings = new EObjectContainmentEList<PendingElement>(PendingElement.class, this, RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__PENDINGS);
		}
		return pendings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__JAVA_MODEL:
				return basicSetJavaModel(null, msgs);
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__PENDINGS:
				return ((InternalEList<?>)getPendings()).basicRemove(otherEnd, msgs);
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT:
				if (resolve) return getCompilationUnit();
				return basicGetCompilationUnit();
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__JAVA_MODEL:
				return getJavaModel();
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__PENDINGS:
				return getPendings();
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT:
				setCompilationUnit((CompilationUnit)newValue);
				return;
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__JAVA_MODEL:
				setJavaModel((Model)newValue);
				return;
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__PENDINGS:
				getPendings().clear();
				getPendings().addAll((Collection<? extends PendingElement>)newValue);
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__JAVA_MODEL:
				setJavaModel((Model)null);
				return;
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__PENDINGS:
				getPendings().clear();
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
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__JAVA_MODEL:
				return javaModel != null;
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF__PENDINGS:
				return pendings != null && !pendings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //JavaCompilationUnitRefImpl
