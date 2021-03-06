/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.FObjectImpl;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parent Relation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ParentRelationImpl#getDiffs <em>Diffs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ParentRelationImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ParentRelationImpl#getChild <em>Child</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParentRelationImpl extends FObjectImpl implements ParentRelation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParentRelationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.PARENT_RELATION;
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
	public EList<Diff> getDiffs() {
		return (EList<Diff>)eGet(RepositoryModelPackage.Literals.PARENT_RELATION__DIFFS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev getParent() {
		return (Rev)eGet(RepositoryModelPackage.Literals.PARENT_RELATION__PARENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Rev newParent) {
		eSet(RepositoryModelPackage.Literals.PARENT_RELATION__PARENT, newParent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev getChild() {
		return (Rev)eGet(RepositoryModelPackage.Literals.PARENT_RELATION__CHILD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChild(Rev newChild) {
		eSet(RepositoryModelPackage.Literals.PARENT_RELATION__CHILD, newChild);
	}

} //ParentRelationImpl
