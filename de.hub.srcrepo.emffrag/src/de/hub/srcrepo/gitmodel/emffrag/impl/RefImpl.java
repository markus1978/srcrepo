/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.emffrag.impl;

import de.hub.emffrag.fragmentation.FObjectImpl;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Ref;

import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.RefImpl#getReferencedCommit <em>Referenced Commit</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.RefImpl#isIsPeeled <em>Is Peeled</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.RefImpl#isIsSymbolic <em>Is Symbolic</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.RefImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RefImpl extends FObjectImpl implements Ref {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GitModelPackage.Literals.REF;
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
	public Commit getReferencedCommit() {
		return (Commit)eGet(GitModelPackage.Literals.REF__REFERENCED_COMMIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferencedCommit(Commit newReferencedCommit) {
		eSet(GitModelPackage.Literals.REF__REFERENCED_COMMIT, newReferencedCommit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsPeeled() {
		return (Boolean)eGet(GitModelPackage.Literals.REF__IS_PEELED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsPeeled(boolean newIsPeeled) {
		eSet(GitModelPackage.Literals.REF__IS_PEELED, newIsPeeled);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSymbolic() {
		return (Boolean)eGet(GitModelPackage.Literals.REF__IS_SYMBOLIC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsSymbolic(boolean newIsSymbolic) {
		eSet(GitModelPackage.Literals.REF__IS_SYMBOLIC, newIsSymbolic);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(GitModelPackage.Literals.REF__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(GitModelPackage.Literals.REF__NAME, newName);
	}

} //RefImpl
