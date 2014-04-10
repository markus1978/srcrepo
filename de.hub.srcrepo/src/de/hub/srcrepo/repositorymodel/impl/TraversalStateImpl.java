/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Traversal State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.TraversalStateImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.TraversalStateImpl#getMerges <em>Merges</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.TraversalStateImpl#getOpenBranches <em>Open Branches</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.TraversalStateImpl#getCompletedBranches <em>Completed Branches</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TraversalStateImpl extends EObjectImpl implements TraversalState {
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
	 * The cached value of the '{@link #getMerges() <em>Merges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMerges()
	 * @generated
	 * @ordered
	 */
	protected EList<Rev> merges;

	/**
	 * The cached value of the '{@link #getOpenBranches() <em>Open Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpenBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<Rev> openBranches;

	/**
	 * The cached value of the '{@link #getCompletedBranches() <em>Completed Branches</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompletedBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<Rev> completedBranches;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TraversalStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.TRAVERSAL_STATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.TRAVERSAL_STATE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rev> getMerges() {
		if (merges == null) {
			merges = new EObjectResolvingEList<Rev>(Rev.class, this, RepositoryModelPackage.TRAVERSAL_STATE__MERGES);
		}
		return merges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rev> getOpenBranches() {
		if (openBranches == null) {
			openBranches = new EObjectResolvingEList<Rev>(Rev.class, this, RepositoryModelPackage.TRAVERSAL_STATE__OPEN_BRANCHES);
		}
		return openBranches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rev> getCompletedBranches() {
		if (completedBranches == null) {
			completedBranches = new EObjectResolvingEList<Rev>(Rev.class, this, RepositoryModelPackage.TRAVERSAL_STATE__COMPLETED_BRANCHES);
		}
		return completedBranches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RepositoryModelPackage.TRAVERSAL_STATE__NAME:
				return getName();
			case RepositoryModelPackage.TRAVERSAL_STATE__MERGES:
				return getMerges();
			case RepositoryModelPackage.TRAVERSAL_STATE__OPEN_BRANCHES:
				return getOpenBranches();
			case RepositoryModelPackage.TRAVERSAL_STATE__COMPLETED_BRANCHES:
				return getCompletedBranches();
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
			case RepositoryModelPackage.TRAVERSAL_STATE__NAME:
				setName((String)newValue);
				return;
			case RepositoryModelPackage.TRAVERSAL_STATE__MERGES:
				getMerges().clear();
				getMerges().addAll((Collection<? extends Rev>)newValue);
				return;
			case RepositoryModelPackage.TRAVERSAL_STATE__OPEN_BRANCHES:
				getOpenBranches().clear();
				getOpenBranches().addAll((Collection<? extends Rev>)newValue);
				return;
			case RepositoryModelPackage.TRAVERSAL_STATE__COMPLETED_BRANCHES:
				getCompletedBranches().clear();
				getCompletedBranches().addAll((Collection<? extends Rev>)newValue);
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
			case RepositoryModelPackage.TRAVERSAL_STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RepositoryModelPackage.TRAVERSAL_STATE__MERGES:
				getMerges().clear();
				return;
			case RepositoryModelPackage.TRAVERSAL_STATE__OPEN_BRANCHES:
				getOpenBranches().clear();
				return;
			case RepositoryModelPackage.TRAVERSAL_STATE__COMPLETED_BRANCHES:
				getCompletedBranches().clear();
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
			case RepositoryModelPackage.TRAVERSAL_STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RepositoryModelPackage.TRAVERSAL_STATE__MERGES:
				return merges != null && !merges.isEmpty();
			case RepositoryModelPackage.TRAVERSAL_STATE__OPEN_BRANCHES:
				return openBranches != null && !openBranches.isEmpty();
			case RepositoryModelPackage.TRAVERSAL_STATE__COMPLETED_BRANCHES:
				return completedBranches != null && !completedBranches.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //TraversalStateImpl
