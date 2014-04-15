/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.gmt.modisco.java.ASTNode;

import de.hub.srcrepo.repositorymodel.PendingElement;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pending Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.PendingElementImpl#getClientNode <em>Client Node</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.PendingElementImpl#getBinding <em>Binding</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.PendingElementImpl#getLinkName <em>Link Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PendingElementImpl extends EObjectImpl implements PendingElement {
	/**
	 * The cached value of the '{@link #getClientNode() <em>Client Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClientNode()
	 * @generated
	 * @ordered
	 */
	protected ASTNode clientNode;

	/**
	 * The default value of the '{@link #getBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinding()
	 * @generated
	 * @ordered
	 */
	protected static final String BINDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinding()
	 * @generated
	 * @ordered
	 */
	protected String binding = BINDING_EDEFAULT;

	/**
	 * The default value of the '{@link #getLinkName() <em>Link Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkName()
	 * @generated
	 * @ordered
	 */
	protected static final String LINK_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLinkName() <em>Link Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkName()
	 * @generated
	 * @ordered
	 */
	protected String linkName = LINK_NAME_EDEFAULT;

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
	public ASTNode getClientNode() {
		if (clientNode != null && clientNode.eIsProxy()) {
			InternalEObject oldClientNode = (InternalEObject)clientNode;
			clientNode = (ASTNode)eResolveProxy(oldClientNode);
			if (clientNode != oldClientNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepositoryModelPackage.PENDING_ELEMENT__CLIENT_NODE, oldClientNode, clientNode));
			}
		}
		return clientNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ASTNode basicGetClientNode() {
		return clientNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClientNode(ASTNode newClientNode) {
		ASTNode oldClientNode = clientNode;
		clientNode = newClientNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.PENDING_ELEMENT__CLIENT_NODE, oldClientNode, clientNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBinding() {
		return binding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBinding(String newBinding) {
		String oldBinding = binding;
		binding = newBinding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.PENDING_ELEMENT__BINDING, oldBinding, binding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLinkName() {
		return linkName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinkName(String newLinkName) {
		String oldLinkName = linkName;
		linkName = newLinkName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.PENDING_ELEMENT__LINK_NAME, oldLinkName, linkName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RepositoryModelPackage.PENDING_ELEMENT__CLIENT_NODE:
				if (resolve) return getClientNode();
				return basicGetClientNode();
			case RepositoryModelPackage.PENDING_ELEMENT__BINDING:
				return getBinding();
			case RepositoryModelPackage.PENDING_ELEMENT__LINK_NAME:
				return getLinkName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RepositoryModelPackage.PENDING_ELEMENT__CLIENT_NODE:
				setClientNode((ASTNode)newValue);
				return;
			case RepositoryModelPackage.PENDING_ELEMENT__BINDING:
				setBinding((String)newValue);
				return;
			case RepositoryModelPackage.PENDING_ELEMENT__LINK_NAME:
				setLinkName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RepositoryModelPackage.PENDING_ELEMENT__CLIENT_NODE:
				setClientNode((ASTNode)null);
				return;
			case RepositoryModelPackage.PENDING_ELEMENT__BINDING:
				setBinding(BINDING_EDEFAULT);
				return;
			case RepositoryModelPackage.PENDING_ELEMENT__LINK_NAME:
				setLinkName(LINK_NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RepositoryModelPackage.PENDING_ELEMENT__CLIENT_NODE:
				return clientNode != null;
			case RepositoryModelPackage.PENDING_ELEMENT__BINDING:
				return BINDING_EDEFAULT == null ? binding != null : !BINDING_EDEFAULT.equals(binding);
			case RepositoryModelPackage.PENDING_ELEMENT__LINK_NAME:
				return LINK_NAME_EDEFAULT == null ? linkName != null : !LINK_NAME_EDEFAULT.equals(linkName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (binding: ");
		result.append(binding);
		result.append(", linkName: ");
		result.append(linkName);
		result.append(')');
		return result.toString();
	}

} //PendingElementImpl
