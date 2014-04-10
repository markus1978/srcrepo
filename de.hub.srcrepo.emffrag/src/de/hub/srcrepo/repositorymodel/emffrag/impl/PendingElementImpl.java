/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmt.modisco.java.ASTNode;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.PendingElement;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pending Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.PendingElementImpl#getClientNode <em>Client Node</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.PendingElementImpl#getBinding <em>Binding</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.PendingElementImpl#getLinkName <em>Link Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PendingElementImpl extends FObjectImpl implements PendingElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PendingElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.PENDING_ELEMENT;
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
	public ASTNode getClientNode() {
		return (ASTNode)eGet(RepositoryModelPackage.Literals.PENDING_ELEMENT__CLIENT_NODE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClientNode(ASTNode newClientNode) {
		eSet(RepositoryModelPackage.Literals.PENDING_ELEMENT__CLIENT_NODE, newClientNode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBinding() {
		return (String)eGet(RepositoryModelPackage.Literals.PENDING_ELEMENT__BINDING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBinding(String newBinding) {
		eSet(RepositoryModelPackage.Literals.PENDING_ELEMENT__BINDING, newBinding);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLinkName() {
		return (String)eGet(RepositoryModelPackage.Literals.PENDING_ELEMENT__LINK_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinkName(String newLinkName) {
		eSet(RepositoryModelPackage.Literals.PENDING_ELEMENT__LINK_NAME, newLinkName);
	}

} //PendingElementImpl
