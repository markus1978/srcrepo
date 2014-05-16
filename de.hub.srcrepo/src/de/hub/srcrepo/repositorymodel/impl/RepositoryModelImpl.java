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

import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RepositoryModelImpl#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RepositoryModelImpl#getAllRevs <em>All Revs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RepositoryModelImpl#getRootRevs <em>Root Revs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RepositoryModelImpl#getTraversals <em>Traversals</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepositoryModelImpl extends EObjectImpl implements RepositoryModel {
	/**
	 * The cached value of the '{@link #getAllRefs() <em>All Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<Ref> allRefs;

	/**
	 * The cached value of the '{@link #getAllRevs() <em>All Revs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllRevs()
	 * @generated
	 * @ordered
	 */
	protected EList<Rev> allRevs;

	/**
	 * The cached value of the '{@link #getRootRevs() <em>Root Revs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootRevs()
	 * @generated
	 * @ordered
	 */
	protected EList<Rev> rootRevs;

	/**
	 * The cached value of the '{@link #getTraversals() <em>Traversals</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraversals()
	 * @generated
	 * @ordered
	 */
	protected TraversalState traversals;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.REPOSITORY_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Ref> getAllRefs() {
		if (allRefs == null) {
			allRefs = new EObjectContainmentEList<Ref>(Ref.class, this, RepositoryModelPackage.REPOSITORY_MODEL__ALL_REFS);
		}
		return allRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rev> getAllRevs() {
		if (allRevs == null) {
			allRevs = new EObjectContainmentEList<Rev>(Rev.class, this, RepositoryModelPackage.REPOSITORY_MODEL__ALL_REVS);
		}
		return allRevs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rev> getRootRevs() {
		if (rootRevs == null) {
			rootRevs = new EObjectResolvingEList<Rev>(Rev.class, this, RepositoryModelPackage.REPOSITORY_MODEL__ROOT_REVS);
		}
		return rootRevs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TraversalState getTraversals() {
		return traversals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTraversals(TraversalState newTraversals, NotificationChain msgs) {
		TraversalState oldTraversals = traversals;
		traversals = newTraversals;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS, oldTraversals, newTraversals);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTraversals(TraversalState newTraversals) {
		if (newTraversals != traversals) {
			NotificationChain msgs = null;
			if (traversals != null)
				msgs = ((InternalEObject)traversals).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS, null, msgs);
			if (newTraversals != null)
				msgs = ((InternalEObject)newTraversals).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS, null, msgs);
			msgs = basicSetTraversals(newTraversals, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS, newTraversals, newTraversals));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REFS:
				return ((InternalEList<?>)getAllRefs()).basicRemove(otherEnd, msgs);
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REVS:
				return ((InternalEList<?>)getAllRevs()).basicRemove(otherEnd, msgs);
			case RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS:
				return basicSetTraversals(null, msgs);
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
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REFS:
				return getAllRefs();
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REVS:
				return getAllRevs();
			case RepositoryModelPackage.REPOSITORY_MODEL__ROOT_REVS:
				return getRootRevs();
			case RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS:
				return getTraversals();
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
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REFS:
				getAllRefs().clear();
				getAllRefs().addAll((Collection<? extends Ref>)newValue);
				return;
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REVS:
				getAllRevs().clear();
				getAllRevs().addAll((Collection<? extends Rev>)newValue);
				return;
			case RepositoryModelPackage.REPOSITORY_MODEL__ROOT_REVS:
				getRootRevs().clear();
				getRootRevs().addAll((Collection<? extends Rev>)newValue);
				return;
			case RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS:
				setTraversals((TraversalState)newValue);
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
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REFS:
				getAllRefs().clear();
				return;
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REVS:
				getAllRevs().clear();
				return;
			case RepositoryModelPackage.REPOSITORY_MODEL__ROOT_REVS:
				getRootRevs().clear();
				return;
			case RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS:
				setTraversals((TraversalState)null);
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
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REFS:
				return allRefs != null && !allRefs.isEmpty();
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REVS:
				return allRevs != null && !allRevs.isEmpty();
			case RepositoryModelPackage.REPOSITORY_MODEL__ROOT_REVS:
				return rootRevs != null && !rootRevs.isEmpty();
			case RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS:
				return traversals != null;
		}
		return super.eIsSet(featureID);
	}

} //RepositoryModelImpl
