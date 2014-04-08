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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import de.hub.srcrepo.repositorymodel.JavaBindings;
import de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Bindings Per Branch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.JavaBindingsPerBranchImpl#getBindings <em>Bindings</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.JavaBindingsPerBranchImpl#getBranch <em>Branch</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaBindingsPerBranchImpl extends EObjectImpl implements JavaBindingsPerBranch {
	/**
	 * The cached value of the '{@link #getBindings() <em>Bindings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindings()
	 * @generated
	 * @ordered
	 */
	protected JavaBindings bindings;

	/**
	 * The cached value of the '{@link #getBranch() <em>Branch</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranch()
	 * @generated
	 * @ordered
	 */
	protected Rev branch;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaBindingsPerBranchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.JAVA_BINDINGS_PER_BRANCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaBindings getBindings() {
		return bindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBindings(JavaBindings newBindings, NotificationChain msgs) {
		JavaBindings oldBindings = bindings;
		bindings = newBindings;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BINDINGS, oldBindings, newBindings);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBindings(JavaBindings newBindings) {
		if (newBindings != bindings) {
			NotificationChain msgs = null;
			if (bindings != null)
				msgs = ((InternalEObject)bindings).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BINDINGS, null, msgs);
			if (newBindings != null)
				msgs = ((InternalEObject)newBindings).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BINDINGS, null, msgs);
			msgs = basicSetBindings(newBindings, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BINDINGS, newBindings, newBindings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev getBranch() {
		if (branch != null && branch.eIsProxy()) {
			InternalEObject oldBranch = (InternalEObject)branch;
			branch = (Rev)eResolveProxy(oldBranch);
			if (branch != oldBranch) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BRANCH, oldBranch, branch));
			}
		}
		return branch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev basicGetBranch() {
		return branch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBranch(Rev newBranch) {
		Rev oldBranch = branch;
		branch = newBranch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BRANCH, oldBranch, branch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BINDINGS:
				return basicSetBindings(null, msgs);
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
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BINDINGS:
				return getBindings();
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BRANCH:
				if (resolve) return getBranch();
				return basicGetBranch();
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
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BINDINGS:
				setBindings((JavaBindings)newValue);
				return;
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BRANCH:
				setBranch((Rev)newValue);
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
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BINDINGS:
				setBindings((JavaBindings)null);
				return;
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BRANCH:
				setBranch((Rev)null);
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
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BINDINGS:
				return bindings != null;
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH__BRANCH:
				return branch != null;
		}
		return super.eIsSet(featureID);
	}

} //JavaBindingsPerBranchImpl
