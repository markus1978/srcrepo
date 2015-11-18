/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Traversal State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalStateImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalStateImpl#getMerges <em>Merges</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalStateImpl#getOpenBranches <em>Open Branches</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalStateImpl#getCompletedBranches <em>Completed Branches</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalStateImpl#getNumberOfImportedRevs <em>Number Of Imported Revs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TraversalStateImpl extends FObjectImpl implements TraversalState {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TraversalStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.TRAVERSAL_STATE;
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
	public String getName() {
		return (String)eGet(RepositoryModelPackage.Literals.TRAVERSAL_STATE__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(RepositoryModelPackage.Literals.TRAVERSAL_STATE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfImportedRevs() {
		return (Integer)eGet(RepositoryModelPackage.Literals.TRAVERSAL_STATE__NUMBER_OF_IMPORTED_REVS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfImportedRevs(int newNumberOfImportedRevs) {
		eSet(RepositoryModelPackage.Literals.TRAVERSAL_STATE__NUMBER_OF_IMPORTED_REVS, newNumberOfImportedRevs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Rev> getMerges() {
		return (EList<Rev>)eGet(RepositoryModelPackage.Literals.TRAVERSAL_STATE__MERGES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Rev> getOpenBranches() {
		return (EList<Rev>)eGet(RepositoryModelPackage.Literals.TRAVERSAL_STATE__OPEN_BRANCHES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Rev> getCompletedBranches() {
		return (EList<Rev>)eGet(RepositoryModelPackage.Literals.TRAVERSAL_STATE__COMPLETED_BRANCHES, true);
	}

} //TraversalStateImpl
