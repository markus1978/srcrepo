/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.FObjectImpl;
import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RefImpl#getReferencedCommit <em>Referenced Commit</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RefImpl#isIsPeeled <em>Is Peeled</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RefImpl#isIsSymbolic <em>Is Symbolic</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RefImpl#getName <em>Name</em>}</li>
 * </ul>
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
		return RepositoryModelPackage.Literals.REF;
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
	public Rev getReferencedCommit() {
		return (Rev)eGet(RepositoryModelPackage.Literals.REF__REFERENCED_COMMIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferencedCommit(Rev newReferencedCommit) {
		eSet(RepositoryModelPackage.Literals.REF__REFERENCED_COMMIT, newReferencedCommit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsPeeled() {
		return (Boolean)eGet(RepositoryModelPackage.Literals.REF__IS_PEELED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsPeeled(boolean newIsPeeled) {
		eSet(RepositoryModelPackage.Literals.REF__IS_PEELED, newIsPeeled);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSymbolic() {
		return (Boolean)eGet(RepositoryModelPackage.Literals.REF__IS_SYMBOLIC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsSymbolic(boolean newIsSymbolic) {
		eSet(RepositoryModelPackage.Literals.REF__IS_SYMBOLIC, newIsSymbolic);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(RepositoryModelPackage.Literals.REF__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(RepositoryModelPackage.Literals.REF__NAME, newName);
	}

} //RefImpl
