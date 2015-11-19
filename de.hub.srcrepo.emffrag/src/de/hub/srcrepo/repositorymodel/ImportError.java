/**
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Error</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.ImportError#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.ImportError#isConrolled <em>Conrolled</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.ImportError#getExceptionClassName <em>Exception Class Name</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getImportError()
 * @model
 * @generated
 */
public interface ImportError extends EObject {
	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getImportError_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.ImportError#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Conrolled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conrolled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conrolled</em>' attribute.
	 * @see #setConrolled(boolean)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getImportError_Conrolled()
	 * @model
	 * @generated
	 */
	boolean isConrolled();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.ImportError#isConrolled <em>Conrolled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conrolled</em>' attribute.
	 * @see #isConrolled()
	 * @generated
	 */
	void setConrolled(boolean value);

	/**
	 * Returns the value of the '<em><b>Exception Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exception Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exception Class Name</em>' attribute.
	 * @see #setExceptionClassName(String)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getImportError_ExceptionClassName()
	 * @model
	 * @generated
	 */
	String getExceptionClassName();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.ImportError#getExceptionClassName <em>Exception Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exception Class Name</em>' attribute.
	 * @see #getExceptionClassName()
	 * @generated
	 */
	void setExceptionClassName(String value);

} // ImportError
