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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.hub.srcrepo.repositorymodel.JavaBindings;
import de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch;
import de.hub.srcrepo.repositorymodel.MoDiscoImport;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mo Disco Import</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.MoDiscoImportImpl#getBindings <em>Bindings</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.MoDiscoImportImpl#getBindingsPerBranch <em>Bindings Per Branch</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MoDiscoImportImpl extends TraversalImpl implements MoDiscoImport {
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
	 * The cached value of the '{@link #getBindingsPerBranch() <em>Bindings Per Branch</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindingsPerBranch()
	 * @generated
	 * @ordered
	 */
	protected EList<JavaBindingsPerBranch> bindingsPerBranch;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MoDiscoImportImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.MO_DISCO_IMPORT;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS, oldBindings, newBindings);
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
				msgs = ((InternalEObject)bindings).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS, null, msgs);
			if (newBindings != null)
				msgs = ((InternalEObject)newBindings).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS, null, msgs);
			msgs = basicSetBindings(newBindings, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS, newBindings, newBindings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<JavaBindingsPerBranch> getBindingsPerBranch() {
		if (bindingsPerBranch == null) {
			bindingsPerBranch = new EObjectContainmentEList<JavaBindingsPerBranch>(JavaBindingsPerBranch.class, this, RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS_PER_BRANCH);
		}
		return bindingsPerBranch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS:
				return basicSetBindings(null, msgs);
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS_PER_BRANCH:
				return ((InternalEList<?>)getBindingsPerBranch()).basicRemove(otherEnd, msgs);
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
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS:
				return getBindings();
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS_PER_BRANCH:
				return getBindingsPerBranch();
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
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS:
				setBindings((JavaBindings)newValue);
				return;
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS_PER_BRANCH:
				getBindingsPerBranch().clear();
				getBindingsPerBranch().addAll((Collection<? extends JavaBindingsPerBranch>)newValue);
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
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS:
				setBindings((JavaBindings)null);
				return;
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS_PER_BRANCH:
				getBindingsPerBranch().clear();
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
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS:
				return bindings != null;
			case RepositoryModelPackage.MO_DISCO_IMPORT__BINDINGS_PER_BRANCH:
				return bindingsPerBranch != null && !bindingsPerBranch.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MoDiscoImportImpl
