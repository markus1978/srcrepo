/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.NamedElement;

import de.hub.emffrag.FObjectImpl;
import de.hub.srcrepo.repositorymodel.UnresolvedLink;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unresolved Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.UnresolvedLinkImpl#getSource <em>Source</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.UnresolvedLinkImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.UnresolvedLinkImpl#getFeatureID <em>Feature ID</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.UnresolvedLinkImpl#getFeatureIndex <em>Feature Index</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.UnresolvedLinkImpl#getId <em>Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UnresolvedLinkImpl extends FObjectImpl implements UnresolvedLink {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UnresolvedLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.UNRESOLVED_LINK;
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
	public ASTNode getSource() {
		return (ASTNode)eGet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__SOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(ASTNode newSource) {
		eSet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__SOURCE, newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedElement getTarget() {
		return (NamedElement)eGet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__TARGET, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(NamedElement newTarget) {
		eSet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__TARGET, newTarget);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFeatureID() {
		return (Integer)eGet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__FEATURE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureID(int newFeatureID) {
		eSet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__FEATURE_ID, newFeatureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFeatureIndex() {
		return (Integer)eGet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__FEATURE_INDEX, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureIndex(int newFeatureIndex) {
		eSet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__FEATURE_INDEX, newFeatureIndex);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return (String)eGet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(RepositoryModelPackage.Literals.UNRESOLVED_LINK__ID, newId);
	}

} //UnresolvedLinkImpl
