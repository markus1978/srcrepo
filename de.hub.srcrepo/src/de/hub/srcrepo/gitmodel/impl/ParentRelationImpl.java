/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.ParentRelation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parent Relation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.ParentRelationImpl#getDiffs <em>Diffs</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.ParentRelationImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.ParentRelationImpl#getChild <em>Child</em>}</li>
 * </ul>
 * </p>
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
		return GitModelPackage.Literals.PARENT_RELATION;
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
		return (EList<Diff>)eGet(GitModelPackage.Literals.PARENT_RELATION__DIFFS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getParent() {
		return (Commit)eGet(GitModelPackage.Literals.PARENT_RELATION__PARENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Commit newParent) {
		eSet(GitModelPackage.Literals.PARENT_RELATION__PARENT, newParent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getChild() {
		return (Commit)eGet(GitModelPackage.Literals.PARENT_RELATION__CHILD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChild(Commit newChild) {
		eSet(GitModelPackage.Literals.PARENT_RELATION__CHILD, newChild);
	}

} //ParentRelationImpl
