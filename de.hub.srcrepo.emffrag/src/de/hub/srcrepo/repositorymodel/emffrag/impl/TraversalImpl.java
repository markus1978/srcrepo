/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.BranchPoint;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Traversal;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Traversal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalImpl#getRemaingBranchPoints <em>Remaing Branch Points</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalImpl#getCurrentBranchpoint <em>Current Branchpoint</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalImpl#getMerges <em>Merges</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalImpl#getNextRev <em>Next Rev</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TraversalImpl extends FObjectImpl implements Traversal {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TraversalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.TRAVERSAL;
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
	public EList<BranchPoint> getRemaingBranchPoints() {
		return (EList<BranchPoint>)eGet(RepositoryModelPackage.Literals.TRAVERSAL__REMAING_BRANCH_POINTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BranchPoint getCurrentBranchpoint() {
		return (BranchPoint)eGet(RepositoryModelPackage.Literals.TRAVERSAL__CURRENT_BRANCHPOINT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentBranchpoint(BranchPoint newCurrentBranchpoint) {
		eSet(RepositoryModelPackage.Literals.TRAVERSAL__CURRENT_BRANCHPOINT, newCurrentBranchpoint);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(RepositoryModelPackage.Literals.TRAVERSAL__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(RepositoryModelPackage.Literals.TRAVERSAL__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Rev> getMerges() {
		return (EList<Rev>)eGet(RepositoryModelPackage.Literals.TRAVERSAL__MERGES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev getNextRev() {
		return (Rev)eGet(RepositoryModelPackage.Literals.TRAVERSAL__NEXT_REV, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNextRev(Rev newNextRev) {
		eSet(RepositoryModelPackage.Literals.TRAVERSAL__NEXT_REV, newNextRev);
	}

} //TraversalImpl
