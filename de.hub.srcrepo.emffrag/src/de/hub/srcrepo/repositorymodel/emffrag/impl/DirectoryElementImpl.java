/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.ecore.EClass;

import de.hub.srcrepo.repositorymodel.DirectoryElement;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Directory Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DirectoryElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DirectoryElementImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DirectoryElementImpl#getUrl <em>Url</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DirectoryElementImpl extends RepositoryElementImpl implements DirectoryElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DirectoryElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.DIRECTORY_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(RepositoryModelPackage.Literals.DIRECTORY_ELEMENT__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(RepositoryModelPackage.Literals.DIRECTORY_ELEMENT__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eGet(RepositoryModelPackage.Literals.DIRECTORY_ELEMENT__DESCRIPTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eSet(RepositoryModelPackage.Literals.DIRECTORY_ELEMENT__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrl() {
		return (String)eGet(RepositoryModelPackage.Literals.DIRECTORY_ELEMENT__URL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrl(String newUrl) {
		eSet(RepositoryModelPackage.Literals.DIRECTORY_ELEMENT__URL, newUrl);
	}

} //DirectoryElementImpl
