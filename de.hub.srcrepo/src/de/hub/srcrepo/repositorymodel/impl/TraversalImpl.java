/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.hub.srcrepo.repositorymodel.BranchPoint;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Traversal;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Traversal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.TraversalImpl#getRemaingBranchPoints <em>Remaing Branch Points</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.TraversalImpl#getCurrentBranchpoint <em>Current Branchpoint</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.TraversalImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.TraversalImpl#getMerges <em>Merges</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.TraversalImpl#getNextRev <em>Next Rev</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TraversalImpl extends EObjectImpl implements Traversal {
	/**
	 * The cached value of the '{@link #getRemaingBranchPoints() <em>Remaing Branch Points</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemaingBranchPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<BranchPoint> remaingBranchPoints;

	/**
	 * The cached value of the '{@link #getCurrentBranchpoint() <em>Current Branchpoint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentBranchpoint()
	 * @generated
	 * @ordered
	 */
	protected BranchPoint currentBranchpoint;

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
	 * The cached value of the '{@link #getNextRev() <em>Next Rev</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNextRev()
	 * @generated
	 * @ordered
	 */
	protected Rev nextRev;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TraversalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.TRAVERSAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BranchPoint> getRemaingBranchPoints() {
		if (remaingBranchPoints == null) {
			remaingBranchPoints = new EObjectContainmentEList<BranchPoint>(BranchPoint.class, this, RepositoryModelPackage.TRAVERSAL__REMAING_BRANCH_POINTS);
		}
		return remaingBranchPoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BranchPoint getCurrentBranchpoint() {
		return currentBranchpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCurrentBranchpoint(BranchPoint newCurrentBranchpoint, NotificationChain msgs) {
		BranchPoint oldCurrentBranchpoint = currentBranchpoint;
		currentBranchpoint = newCurrentBranchpoint;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.TRAVERSAL__CURRENT_BRANCHPOINT, oldCurrentBranchpoint, newCurrentBranchpoint);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentBranchpoint(BranchPoint newCurrentBranchpoint) {
		if (newCurrentBranchpoint != currentBranchpoint) {
			NotificationChain msgs = null;
			if (currentBranchpoint != null)
				msgs = ((InternalEObject)currentBranchpoint).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.TRAVERSAL__CURRENT_BRANCHPOINT, null, msgs);
			if (newCurrentBranchpoint != null)
				msgs = ((InternalEObject)newCurrentBranchpoint).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.TRAVERSAL__CURRENT_BRANCHPOINT, null, msgs);
			msgs = basicSetCurrentBranchpoint(newCurrentBranchpoint, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.TRAVERSAL__CURRENT_BRANCHPOINT, newCurrentBranchpoint, newCurrentBranchpoint));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.TRAVERSAL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rev> getMerges() {
		if (merges == null) {
			merges = new EObjectResolvingEList<Rev>(Rev.class, this, RepositoryModelPackage.TRAVERSAL__MERGES);
		}
		return merges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev getNextRev() {
		if (nextRev != null && nextRev.eIsProxy()) {
			InternalEObject oldNextRev = (InternalEObject)nextRev;
			nextRev = (Rev)eResolveProxy(oldNextRev);
			if (nextRev != oldNextRev) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepositoryModelPackage.TRAVERSAL__NEXT_REV, oldNextRev, nextRev));
			}
		}
		return nextRev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev basicGetNextRev() {
		return nextRev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNextRev(Rev newNextRev) {
		Rev oldNextRev = nextRev;
		nextRev = newNextRev;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.TRAVERSAL__NEXT_REV, oldNextRev, nextRev));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepositoryModelPackage.TRAVERSAL__REMAING_BRANCH_POINTS:
				return ((InternalEList<?>)getRemaingBranchPoints()).basicRemove(otherEnd, msgs);
			case RepositoryModelPackage.TRAVERSAL__CURRENT_BRANCHPOINT:
				return basicSetCurrentBranchpoint(null, msgs);
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
			case RepositoryModelPackage.TRAVERSAL__REMAING_BRANCH_POINTS:
				return getRemaingBranchPoints();
			case RepositoryModelPackage.TRAVERSAL__CURRENT_BRANCHPOINT:
				return getCurrentBranchpoint();
			case RepositoryModelPackage.TRAVERSAL__NAME:
				return getName();
			case RepositoryModelPackage.TRAVERSAL__MERGES:
				return getMerges();
			case RepositoryModelPackage.TRAVERSAL__NEXT_REV:
				if (resolve) return getNextRev();
				return basicGetNextRev();
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
			case RepositoryModelPackage.TRAVERSAL__REMAING_BRANCH_POINTS:
				getRemaingBranchPoints().clear();
				getRemaingBranchPoints().addAll((Collection<? extends BranchPoint>)newValue);
				return;
			case RepositoryModelPackage.TRAVERSAL__CURRENT_BRANCHPOINT:
				setCurrentBranchpoint((BranchPoint)newValue);
				return;
			case RepositoryModelPackage.TRAVERSAL__NAME:
				setName((String)newValue);
				return;
			case RepositoryModelPackage.TRAVERSAL__MERGES:
				getMerges().clear();
				getMerges().addAll((Collection<? extends Rev>)newValue);
				return;
			case RepositoryModelPackage.TRAVERSAL__NEXT_REV:
				setNextRev((Rev)newValue);
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
			case RepositoryModelPackage.TRAVERSAL__REMAING_BRANCH_POINTS:
				getRemaingBranchPoints().clear();
				return;
			case RepositoryModelPackage.TRAVERSAL__CURRENT_BRANCHPOINT:
				setCurrentBranchpoint((BranchPoint)null);
				return;
			case RepositoryModelPackage.TRAVERSAL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RepositoryModelPackage.TRAVERSAL__MERGES:
				getMerges().clear();
				return;
			case RepositoryModelPackage.TRAVERSAL__NEXT_REV:
				setNextRev((Rev)null);
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
			case RepositoryModelPackage.TRAVERSAL__REMAING_BRANCH_POINTS:
				return remaingBranchPoints != null && !remaingBranchPoints.isEmpty();
			case RepositoryModelPackage.TRAVERSAL__CURRENT_BRANCHPOINT:
				return currentBranchpoint != null;
			case RepositoryModelPackage.TRAVERSAL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RepositoryModelPackage.TRAVERSAL__MERGES:
				return merges != null && !merges.isEmpty();
			case RepositoryModelPackage.TRAVERSAL__NEXT_REV:
				return nextRev != null;
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

} //TraversalImpl
