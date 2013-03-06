/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.ParentRelation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parent Relation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.ParentRelationImpl#getDiffs <em>Diffs</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.ParentRelationImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.ParentRelationImpl#getChild <em>Child</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParentRelationImpl extends EObjectImpl implements ParentRelation {
	/**
	 * The cached value of the '{@link #getDiffs() <em>Diffs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiffs()
	 * @generated
	 * @ordered
	 */
	protected EList<Diff> diffs;
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected Commit parent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParentRelationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GitModelPackage.Literals.PARENT_RELATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Diff> getDiffs() {
		if (diffs == null) {
			diffs = new EObjectContainmentEList<Diff>(Diff.class, this, GitModelPackage.PARENT_RELATION__DIFFS);
		}
		return diffs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (Commit)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GitModelPackage.PARENT_RELATION__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(Commit newParent, NotificationChain msgs) {
		Commit oldParent = parent;
		parent = newParent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GitModelPackage.PARENT_RELATION__PARENT, oldParent, newParent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Commit newParent) {
		if (newParent != parent) {
			NotificationChain msgs = null;
			if (parent != null)
				msgs = ((InternalEObject)parent).eInverseRemove(this, GitModelPackage.COMMIT__CHILD_RELATIONS, Commit.class, msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, GitModelPackage.COMMIT__CHILD_RELATIONS, Commit.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.PARENT_RELATION__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getChild() {
		if (eContainerFeatureID() != GitModelPackage.PARENT_RELATION__CHILD) return null;
		return (Commit)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChild(Commit newChild, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newChild, GitModelPackage.PARENT_RELATION__CHILD, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChild(Commit newChild) {
		if (newChild != eInternalContainer() || (eContainerFeatureID() != GitModelPackage.PARENT_RELATION__CHILD && newChild != null)) {
			if (EcoreUtil.isAncestor(this, newChild))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newChild != null)
				msgs = ((InternalEObject)newChild).eInverseAdd(this, GitModelPackage.COMMIT__PARENT_RELATIONS, Commit.class, msgs);
			msgs = basicSetChild(newChild, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.PARENT_RELATION__CHILD, newChild, newChild));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GitModelPackage.PARENT_RELATION__PARENT:
				if (parent != null)
					msgs = ((InternalEObject)parent).eInverseRemove(this, GitModelPackage.COMMIT__CHILD_RELATIONS, Commit.class, msgs);
				return basicSetParent((Commit)otherEnd, msgs);
			case GitModelPackage.PARENT_RELATION__CHILD:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetChild((Commit)otherEnd, msgs);
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
			case GitModelPackage.PARENT_RELATION__DIFFS:
				return ((InternalEList<?>)getDiffs()).basicRemove(otherEnd, msgs);
			case GitModelPackage.PARENT_RELATION__PARENT:
				return basicSetParent(null, msgs);
			case GitModelPackage.PARENT_RELATION__CHILD:
				return basicSetChild(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case GitModelPackage.PARENT_RELATION__CHILD:
				return eInternalContainer().eInverseRemove(this, GitModelPackage.COMMIT__PARENT_RELATIONS, Commit.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GitModelPackage.PARENT_RELATION__DIFFS:
				return getDiffs();
			case GitModelPackage.PARENT_RELATION__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case GitModelPackage.PARENT_RELATION__CHILD:
				return getChild();
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
			case GitModelPackage.PARENT_RELATION__DIFFS:
				getDiffs().clear();
				getDiffs().addAll((Collection<? extends Diff>)newValue);
				return;
			case GitModelPackage.PARENT_RELATION__PARENT:
				setParent((Commit)newValue);
				return;
			case GitModelPackage.PARENT_RELATION__CHILD:
				setChild((Commit)newValue);
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
			case GitModelPackage.PARENT_RELATION__DIFFS:
				getDiffs().clear();
				return;
			case GitModelPackage.PARENT_RELATION__PARENT:
				setParent((Commit)null);
				return;
			case GitModelPackage.PARENT_RELATION__CHILD:
				setChild((Commit)null);
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
			case GitModelPackage.PARENT_RELATION__DIFFS:
				return diffs != null && !diffs.isEmpty();
			case GitModelPackage.PARENT_RELATION__PARENT:
				return parent != null;
			case GitModelPackage.PARENT_RELATION__CHILD:
				return getChild() != null;
		}
		return super.eIsSet(featureID);
	}

} //ParentRelationImpl
