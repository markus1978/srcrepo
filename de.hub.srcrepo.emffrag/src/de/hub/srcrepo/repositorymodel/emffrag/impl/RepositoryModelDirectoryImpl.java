/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import de.hub.emffrag.fragmentation.FObjectImpl;

import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory;

import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Directory</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl#getSubDirectories <em>Sub Directories</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl#getRepositoryModels <em>Repository Models</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl#getUrl <em>Url</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepositoryModelDirectoryImpl extends FObjectImpl implements RepositoryModelDirectory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryModelDirectoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.REPOSITORY_MODEL_DIRECTORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RepositoryModelDirectory> getSubDirectories() {
		return (EList<RepositoryModelDirectory>)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL_DIRECTORY__SUB_DIRECTORIES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RepositoryModel> getRepositoryModels() {
		return (EList<RepositoryModel>)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL_DIRECTORY__REPOSITORY_MODELS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL_DIRECTORY__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_MODEL_DIRECTORY__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL_DIRECTORY__DESCRIPTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_MODEL_DIRECTORY__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrl() {
		return (String)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL_DIRECTORY__URL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrl(String newUrl) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_MODEL_DIRECTORY__URL, newUrl);
	}

} //RepositoryModelDirectoryImpl
