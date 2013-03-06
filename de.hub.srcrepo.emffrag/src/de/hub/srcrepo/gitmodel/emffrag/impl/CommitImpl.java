/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.emffrag.impl;

import de.hub.emffrag.fragmentation.FObjectImpl;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.ParentRelation;

import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.CommitImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.CommitImpl#getCommiter <em>Commiter</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.CommitImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.CommitImpl#getTime <em>Time</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.CommitImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.CommitImpl#getParentRelations <em>Parent Relations</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.CommitImpl#getChildRelations <em>Child Relations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CommitImpl extends FObjectImpl implements Commit {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GitModelPackage.Literals.COMMIT;
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
		return (String)eGet(GitModelPackage.Literals.COMMIT__AUTHOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		eSet(GitModelPackage.Literals.COMMIT__AUTHOR, newAuthor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCommiter() {
		return (String)eGet(GitModelPackage.Literals.COMMIT__COMMITER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommiter(String newCommiter) {
		eSet(GitModelPackage.Literals.COMMIT__COMMITER, newCommiter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(GitModelPackage.Literals.COMMIT__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(GitModelPackage.Literals.COMMIT__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getTime() {
		return (Date)eGet(GitModelPackage.Literals.COMMIT__TIME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTime(Date newTime) {
		eSet(GitModelPackage.Literals.COMMIT__TIME, newTime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessage() {
		return (String)eGet(GitModelPackage.Literals.COMMIT__MESSAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		eSet(GitModelPackage.Literals.COMMIT__MESSAGE, newMessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ParentRelation> getParentRelations() {
		return (EList<ParentRelation>)eGet(GitModelPackage.Literals.COMMIT__PARENT_RELATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ParentRelation> getChildRelations() {
		return (EList<ParentRelation>)eGet(GitModelPackage.Literals.COMMIT__CHILD_RELATIONS, true);
	}

} //CommitImpl
