/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.gitmodel.impl;

import de.hub.srcrepo.nofrag.emffrag.impl.IndexedMapImpl;

import de.hub.srcrepo.nofrag.gitmodel.Commit;
import de.hub.srcrepo.nofrag.gitmodel.GitModelPackage;
import de.hub.srcrepo.nofrag.gitmodel.Ref;
import de.hub.srcrepo.nofrag.gitmodel.SourceRepository;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.impl.SourceRepositoryImpl#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.impl.SourceRepositoryImpl#getAllCommits <em>All Commits</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SourceRepositoryImpl extends IndexedMapImpl<String, Commit> implements SourceRepository {
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
	 * The cached value of the '{@link #getAllCommits() <em>All Commits</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllCommits()
	 * @generated
	 * @ordered
	 */
	protected EList<Commit> allCommits;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SourceRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GitModelPackage.Literals.SOURCE_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Ref> getAllRefs() {
		if (allRefs == null) {
			allRefs = new EObjectContainmentEList<Ref>(Ref.class, this, GitModelPackage.SOURCE_REPOSITORY__ALL_REFS);
		}
		return allRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Commit> getAllCommits() {
		if (allCommits == null) {
			allCommits = new EObjectContainmentEList<Commit>(Commit.class, this, GitModelPackage.SOURCE_REPOSITORY__ALL_COMMITS);
		}
		return allCommits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GitModelPackage.SOURCE_REPOSITORY__ALL_REFS:
				return ((InternalEList<?>)getAllRefs()).basicRemove(otherEnd, msgs);
			case GitModelPackage.SOURCE_REPOSITORY__ALL_COMMITS:
				return ((InternalEList<?>)getAllCommits()).basicRemove(otherEnd, msgs);
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
			case GitModelPackage.SOURCE_REPOSITORY__ALL_REFS:
				return getAllRefs();
			case GitModelPackage.SOURCE_REPOSITORY__ALL_COMMITS:
				return getAllCommits();
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
			case GitModelPackage.SOURCE_REPOSITORY__ALL_REFS:
				getAllRefs().clear();
				getAllRefs().addAll((Collection<? extends Ref>)newValue);
				return;
			case GitModelPackage.SOURCE_REPOSITORY__ALL_COMMITS:
				getAllCommits().clear();
				getAllCommits().addAll((Collection<? extends Commit>)newValue);
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
			case GitModelPackage.SOURCE_REPOSITORY__ALL_REFS:
				getAllRefs().clear();
				return;
			case GitModelPackage.SOURCE_REPOSITORY__ALL_COMMITS:
				getAllCommits().clear();
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
			case GitModelPackage.SOURCE_REPOSITORY__ALL_REFS:
				return allRefs != null && !allRefs.isEmpty();
			case GitModelPackage.SOURCE_REPOSITORY__ALL_COMMITS:
				return allCommits != null && !allCommits.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SourceRepositoryImpl
