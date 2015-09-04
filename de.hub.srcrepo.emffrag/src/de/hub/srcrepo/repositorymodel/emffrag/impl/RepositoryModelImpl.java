/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl#getAllRevs <em>All Revs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl#getRootRevs <em>Root Revs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl#getTraversals <em>Traversals</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepositoryModelImpl extends FObjectImpl implements RepositoryModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.REPOSITORY_MODEL;
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
	public EList<Ref> getAllRefs() {
		return (EList<Ref>)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ALL_REFS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Rev> getAllRevs() {
		return (EList<Rev>)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ALL_REVS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Rev> getRootRevs() {
		return (EList<Rev>)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ROOT_REVS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TraversalState getTraversals() {
		return (TraversalState)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__TRAVERSALS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTraversals(TraversalState newTraversals) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__TRAVERSALS, newTraversals);
	}

} //RepositoryModelImpl
