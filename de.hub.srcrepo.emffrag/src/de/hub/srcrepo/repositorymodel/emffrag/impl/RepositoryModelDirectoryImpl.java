/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

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
 * </ul>
 *
 * @generated
 */
public class RepositoryModelDirectoryImpl extends ModelImpl implements RepositoryModelDirectory {
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

} //RepositoryModelDirectoryImpl
