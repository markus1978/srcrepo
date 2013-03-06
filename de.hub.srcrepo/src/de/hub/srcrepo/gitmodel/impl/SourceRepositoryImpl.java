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
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.gmt.modisco.java.Model;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.Ref;
import de.hub.srcrepo.gitmodel.SourceRepository;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.SourceRepositoryImpl#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.SourceRepositoryImpl#getAllCommits <em>All Commits</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.SourceRepositoryImpl#getJavaModel <em>Java Model</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.SourceRepositoryImpl#getRootCommit <em>Root Commit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SourceRepositoryImpl extends EObjectImpl implements SourceRepository {
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
	 * The cached value of the '{@link #getJavaModel() <em>Java Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaModel()
	 * @generated
	 * @ordered
	 */
	protected Model javaModel;
	/**
	 * The cached value of the '{@link #getRootCommit() <em>Root Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootCommit()
	 * @generated
	 * @ordered
	 */
	protected Commit rootCommit;

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
	public Model getJavaModel() {
		if (javaModel != null && javaModel.eIsProxy()) {
			InternalEObject oldJavaModel = (InternalEObject)javaModel;
			javaModel = (Model)eResolveProxy(oldJavaModel);
			if (javaModel != oldJavaModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GitModelPackage.SOURCE_REPOSITORY__JAVA_MODEL, oldJavaModel, javaModel));
			}
		}
		return javaModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model basicGetJavaModel() {
		return javaModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJavaModel(Model newJavaModel) {
		Model oldJavaModel = javaModel;
		javaModel = newJavaModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.SOURCE_REPOSITORY__JAVA_MODEL, oldJavaModel, javaModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getRootCommit() {
		if (rootCommit != null && rootCommit.eIsProxy()) {
			InternalEObject oldRootCommit = (InternalEObject)rootCommit;
			rootCommit = (Commit)eResolveProxy(oldRootCommit);
			if (rootCommit != oldRootCommit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GitModelPackage.SOURCE_REPOSITORY__ROOT_COMMIT, oldRootCommit, rootCommit));
			}
		}
		return rootCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit basicGetRootCommit() {
		return rootCommit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootCommit(Commit newRootCommit) {
		Commit oldRootCommit = rootCommit;
		rootCommit = newRootCommit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.SOURCE_REPOSITORY__ROOT_COMMIT, oldRootCommit, rootCommit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Commit getCommit(String name) {
		for (Commit commit: getAllCommits()) {
			if (commit.getName().equals(name)) {
				return commit;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void putCommit(String name, Commit commit) {
		getAllCommits().add(commit);
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
			case GitModelPackage.SOURCE_REPOSITORY__JAVA_MODEL:
				if (resolve) return getJavaModel();
				return basicGetJavaModel();
			case GitModelPackage.SOURCE_REPOSITORY__ROOT_COMMIT:
				if (resolve) return getRootCommit();
				return basicGetRootCommit();
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
			case GitModelPackage.SOURCE_REPOSITORY__JAVA_MODEL:
				setJavaModel((Model)newValue);
				return;
			case GitModelPackage.SOURCE_REPOSITORY__ROOT_COMMIT:
				setRootCommit((Commit)newValue);
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
			case GitModelPackage.SOURCE_REPOSITORY__JAVA_MODEL:
				setJavaModel((Model)null);
				return;
			case GitModelPackage.SOURCE_REPOSITORY__ROOT_COMMIT:
				setRootCommit((Commit)null);
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
			case GitModelPackage.SOURCE_REPOSITORY__JAVA_MODEL:
				return javaModel != null;
			case GitModelPackage.SOURCE_REPOSITORY__ROOT_COMMIT:
				return rootCommit != null;
		}
		return super.eIsSet(featureID);
	}	

} //SourceRepositoryImpl
