/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.UnresolvedItem;

import de.hub.srcrepo.repositorymodel.JavaBindings;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Bindings</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.JavaBindingsImpl#getTargets <em>Targets</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.JavaBindingsImpl#getUnresolved <em>Unresolved</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaBindingsImpl extends EObjectImpl implements JavaBindings {
	/**
	 * The cached value of the '{@link #getTargets() <em>Targets</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargets()
	 * @generated
	 * @ordered
	 */
	protected EList<NamedElement> targets;

	/**
	 * The cached value of the '{@link #getUnresolved() <em>Unresolved</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnresolved()
	 * @generated
	 * @ordered
	 */
	protected EList<UnresolvedItem> unresolved;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaBindingsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.JAVA_BINDINGS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NamedElement> getTargets() {
		if (targets == null) {
			targets = new EObjectResolvingEList<NamedElement>(NamedElement.class, this, RepositoryModelPackage.JAVA_BINDINGS__TARGETS);
		}
		return targets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UnresolvedItem> getUnresolved() {
		if (unresolved == null) {
			unresolved = new EObjectResolvingEList<UnresolvedItem>(UnresolvedItem.class, this, RepositoryModelPackage.JAVA_BINDINGS__UNRESOLVED);
		}
		return unresolved;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RepositoryModelPackage.JAVA_BINDINGS__TARGETS:
				return getTargets();
			case RepositoryModelPackage.JAVA_BINDINGS__UNRESOLVED:
				return getUnresolved();
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
			case RepositoryModelPackage.JAVA_BINDINGS__TARGETS:
				getTargets().clear();
				getTargets().addAll((Collection<? extends NamedElement>)newValue);
				return;
			case RepositoryModelPackage.JAVA_BINDINGS__UNRESOLVED:
				getUnresolved().clear();
				getUnresolved().addAll((Collection<? extends UnresolvedItem>)newValue);
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
			case RepositoryModelPackage.JAVA_BINDINGS__TARGETS:
				getTargets().clear();
				return;
			case RepositoryModelPackage.JAVA_BINDINGS__UNRESOLVED:
				getUnresolved().clear();
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
			case RepositoryModelPackage.JAVA_BINDINGS__TARGETS:
				return targets != null && !targets.isEmpty();
			case RepositoryModelPackage.JAVA_BINDINGS__UNRESOLVED:
				return unresolved != null && !unresolved.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //JavaBindingsImpl
