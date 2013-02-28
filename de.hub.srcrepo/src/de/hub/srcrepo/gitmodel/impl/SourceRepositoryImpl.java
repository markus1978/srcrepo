/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmt.modisco.java.Model;

import de.hub.emffrag.datastore.KeyType;
import de.hub.emffrag.datastore.StringKeyType;
import de.hub.emffrag.model.emffrag.impl.IndexedMapImpl;
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
public class SourceRepositoryImpl extends IndexedMapImpl<String, Commit> implements SourceRepository {
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

	@Override
	public KeyType<String> getKeytype() {
		return StringKeyType.instance;
	}
	
	

} //SourceRepositoryImpl
