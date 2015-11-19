/**
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmt.modisco.java.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.Target#getId <em>Id</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.Target#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getTarget()
 * @model
 * @generated
 */
public interface Target extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getTarget_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.Target#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(NamedElement)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getTarget_Target()
	 * @model
	 * @generated
	 */
	NamedElement getTarget();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.Target#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(NamedElement value);

} // Target
