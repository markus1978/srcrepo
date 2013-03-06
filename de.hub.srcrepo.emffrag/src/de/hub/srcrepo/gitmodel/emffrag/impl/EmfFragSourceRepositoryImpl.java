/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.emffrag.impl;

import de.hub.emffrag.datastore.KeyType;
import de.hub.emffrag.datastore.StringKeyType;
import de.hub.emffrag.model.emffrag.impl.IndexedMapImpl;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.EmfFragSourceRepository;
import de.hub.srcrepo.gitmodel.Ref;
import de.hub.srcrepo.gitmodel.SourceRepository;

import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.gmt.modisco.java.Model;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Emf Frag Source Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.EmfFragSourceRepositoryImpl#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.EmfFragSourceRepositoryImpl#getAllCommits <em>All Commits</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.EmfFragSourceRepositoryImpl#getJavaModel <em>Java Model</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.EmfFragSourceRepositoryImpl#getRootCommit <em>Root Commit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EmfFragSourceRepositoryImpl extends IndexedMapImpl<String, Commit> implements EmfFragSourceRepository {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EmfFragSourceRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GitModelPackage.Literals.EMF_FRAG_SOURCE_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Ref> getAllRefs() {
		return (EList<Ref>)eGet(GitModelPackage.Literals.SOURCE_REPOSITORY__ALL_REFS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Commit> getAllCommits() {
		return (EList<Commit>)eGet(GitModelPackage.Literals.SOURCE_REPOSITORY__ALL_COMMITS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model getJavaModel() {
		return (Model)eGet(GitModelPackage.Literals.SOURCE_REPOSITORY__JAVA_MODEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJavaModel(Model newJavaModel) {
		eSet(GitModelPackage.Literals.SOURCE_REPOSITORY__JAVA_MODEL, newJavaModel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getRootCommit() {
		return (Commit)eGet(GitModelPackage.Literals.SOURCE_REPOSITORY__ROOT_COMMIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootCommit(Commit newRootCommit) {
		eSet(GitModelPackage.Literals.SOURCE_REPOSITORY__ROOT_COMMIT, newRootCommit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Commit getCommit(String name) {
		return exact(name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void putCommit(String name, Commit commit) {
		put(name, commit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == SourceRepository.class) {
			switch (derivedFeatureID) {
				case GitModelPackage.EMF_FRAG_SOURCE_REPOSITORY__ALL_REFS: return GitModelPackage.SOURCE_REPOSITORY__ALL_REFS;
				case GitModelPackage.EMF_FRAG_SOURCE_REPOSITORY__ALL_COMMITS: return GitModelPackage.SOURCE_REPOSITORY__ALL_COMMITS;
				case GitModelPackage.EMF_FRAG_SOURCE_REPOSITORY__JAVA_MODEL: return GitModelPackage.SOURCE_REPOSITORY__JAVA_MODEL;
				case GitModelPackage.EMF_FRAG_SOURCE_REPOSITORY__ROOT_COMMIT: return GitModelPackage.SOURCE_REPOSITORY__ROOT_COMMIT;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == SourceRepository.class) {
			switch (baseFeatureID) {
				case GitModelPackage.SOURCE_REPOSITORY__ALL_REFS: return GitModelPackage.EMF_FRAG_SOURCE_REPOSITORY__ALL_REFS;
				case GitModelPackage.SOURCE_REPOSITORY__ALL_COMMITS: return GitModelPackage.EMF_FRAG_SOURCE_REPOSITORY__ALL_COMMITS;
				case GitModelPackage.SOURCE_REPOSITORY__JAVA_MODEL: return GitModelPackage.EMF_FRAG_SOURCE_REPOSITORY__JAVA_MODEL;
				case GitModelPackage.SOURCE_REPOSITORY__ROOT_COMMIT: return GitModelPackage.EMF_FRAG_SOURCE_REPOSITORY__ROOT_COMMIT;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	@Override
	public KeyType<String> getKeytype() {
		return StringKeyType.instance;
	}

} //EmfFragSourceRepositoryImpl
