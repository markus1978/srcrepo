/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.ImportError;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rev</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl#getCommiter <em>Commiter</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl#getTime <em>Time</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl#getParentRelations <em>Parent Relations</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl#getChildRelations <em>Child Relations</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl#getImportErrors <em>Import Errors</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RevImpl extends FObjectImpl implements Rev {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RevImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.REV;
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
	public String getAuthor() {
		return (String)eGet(RepositoryModelPackage.Literals.REV__AUTHOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		eSet(RepositoryModelPackage.Literals.REV__AUTHOR, newAuthor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCommiter() {
		return (String)eGet(RepositoryModelPackage.Literals.REV__COMMITER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommiter(String newCommiter) {
		eSet(RepositoryModelPackage.Literals.REV__COMMITER, newCommiter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(RepositoryModelPackage.Literals.REV__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(RepositoryModelPackage.Literals.REV__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getTime() {
		return (Date)eGet(RepositoryModelPackage.Literals.REV__TIME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTime(Date newTime) {
		eSet(RepositoryModelPackage.Literals.REV__TIME, newTime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessage() {
		return (String)eGet(RepositoryModelPackage.Literals.REV__MESSAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		eSet(RepositoryModelPackage.Literals.REV__MESSAGE, newMessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ParentRelation> getParentRelations() {
		return (EList<ParentRelation>)eGet(RepositoryModelPackage.Literals.REV__PARENT_RELATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ParentRelation> getChildRelations() {
		return (EList<ParentRelation>)eGet(RepositoryModelPackage.Literals.REV__CHILD_RELATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ImportError> getImportErrors() {
		return (EList<ImportError>)eGet(RepositoryModelPackage.Literals.REV__IMPORT_ERRORS, true);
	}

} //RevImpl
