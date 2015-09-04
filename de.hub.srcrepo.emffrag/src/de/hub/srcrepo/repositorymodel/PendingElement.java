/**
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.ASTNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pending Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.PendingElement#getClientNode <em>Client Node</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.PendingElement#getBinding <em>Binding</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.PendingElement#getLinkName <em>Link Name</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getPendingElement()
 * @model
 * @generated
 */
public interface PendingElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Client Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Client Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Client Node</em>' reference.
	 * @see #setClientNode(ASTNode)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getPendingElement_ClientNode()
	 * @model
	 * @generated
	 */
	ASTNode getClientNode();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.PendingElement#getClientNode <em>Client Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Client Node</em>' reference.
	 * @see #getClientNode()
	 * @generated
	 */
	void setClientNode(ASTNode value);

	/**
	 * Returns the value of the '<em><b>Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binding</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binding</em>' attribute.
	 * @see #setBinding(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getPendingElement_Binding()
	 * @model
	 * @generated
	 */
	String getBinding();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.PendingElement#getBinding <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binding</em>' attribute.
	 * @see #getBinding()
	 * @generated
	 */
	void setBinding(String value);

	/**
	 * Returns the value of the '<em><b>Link Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link Name</em>' attribute.
	 * @see #setLinkName(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getPendingElement_LinkName()
	 * @model
	 * @generated
	 */
	String getLinkName();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.PendingElement#getLinkName <em>Link Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link Name</em>' attribute.
	 * @see #getLinkName()
	 * @generated
	 */
	void setLinkName(String value);

} // PendingElement
