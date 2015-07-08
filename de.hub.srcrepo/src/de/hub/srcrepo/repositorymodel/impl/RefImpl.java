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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RefImpl#getReferencedCommit <em>Referenced Commit</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RefImpl#isIsPeeled <em>Is Peeled</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RefImpl#isIsSymbolic <em>Is Symbolic</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RefImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RefImpl extends EObjectImpl implements Ref {
	/**
	 * The cached value of the '{@link #getReferencedCommit() <em>Referenced Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedCommit()
	 * @generated
	 * @ordered
	 */
	protected Rev referencedCommit;
	/**
	 * The default value of the '{@link #isIsPeeled() <em>Is Peeled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsPeeled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_PEELED_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isIsPeeled() <em>Is Peeled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsPeeled()
	 * @generated
	 * @ordered
	 */
	protected boolean isPeeled = IS_PEELED_EDEFAULT;
	/**
	 * The default value of the '{@link #isIsSymbolic() <em>Is Symbolic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSymbolic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SYMBOLIC_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isIsSymbolic() <em>Is Symbolic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSymbolic()
	 * @generated
	 * @ordered
	 */
	protected boolean isSymbolic = IS_SYMBOLIC_EDEFAULT;
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev getReferencedCommit() {
		if (referencedCommit != null && referencedCommit.eIsProxy()) {
			InternalEObject oldReferencedCommit = (InternalEObject)referencedCommit;
			referencedCommit = (Rev)eResolveProxy(oldReferencedCommit);
			if (referencedCommit != oldReferencedCommit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepositoryModelPackage.REF__REFERENCED_COMMIT, oldReferencedCommit, referencedCommit));
			}
		}
		return referencedCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev basicGetReferencedCommit() {
		return referencedCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferencedCommit(Rev newReferencedCommit) {
		Rev oldReferencedCommit = referencedCommit;
		referencedCommit = newReferencedCommit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REF__REFERENCED_COMMIT, oldReferencedCommit, referencedCommit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsPeeled() {
		return isPeeled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsPeeled(boolean newIsPeeled) {
		boolean oldIsPeeled = isPeeled;
		isPeeled = newIsPeeled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REF__IS_PEELED, oldIsPeeled, isPeeled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSymbolic() {
		return isSymbolic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsSymbolic(boolean newIsSymbolic) {
		boolean oldIsSymbolic = isSymbolic;
		isSymbolic = newIsSymbolic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REF__IS_SYMBOLIC, oldIsSymbolic, isSymbolic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REF__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RepositoryModelPackage.REF__REFERENCED_COMMIT:
				if (resolve) return getReferencedCommit();
				return basicGetReferencedCommit();
			case RepositoryModelPackage.REF__IS_PEELED:
				return isIsPeeled();
			case RepositoryModelPackage.REF__IS_SYMBOLIC:
				return isIsSymbolic();
			case RepositoryModelPackage.REF__NAME:
				return getName();
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
			case RepositoryModelPackage.REF__REFERENCED_COMMIT:
				setReferencedCommit((Rev)newValue);
				return;
			case RepositoryModelPackage.REF__IS_PEELED:
				setIsPeeled((Boolean)newValue);
				return;
			case RepositoryModelPackage.REF__IS_SYMBOLIC:
				setIsSymbolic((Boolean)newValue);
				return;
			case RepositoryModelPackage.REF__NAME:
				setName((String)newValue);
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
			case RepositoryModelPackage.REF__REFERENCED_COMMIT:
				setReferencedCommit((Rev)null);
				return;
			case RepositoryModelPackage.REF__IS_PEELED:
				setIsPeeled(IS_PEELED_EDEFAULT);
				return;
			case RepositoryModelPackage.REF__IS_SYMBOLIC:
				setIsSymbolic(IS_SYMBOLIC_EDEFAULT);
				return;
			case RepositoryModelPackage.REF__NAME:
				setName(NAME_EDEFAULT);
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
			case RepositoryModelPackage.REF__REFERENCED_COMMIT:
				return referencedCommit != null;
			case RepositoryModelPackage.REF__IS_PEELED:
				return isPeeled != IS_PEELED_EDEFAULT;
			case RepositoryModelPackage.REF__IS_SYMBOLIC:
				return isSymbolic != IS_SYMBOLIC_EDEFAULT;
			case RepositoryModelPackage.REF__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (isPeeled: ");
		result.append(isPeeled);
		result.append(", isSymbolic: ");
		result.append(isSymbolic);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //RefImpl