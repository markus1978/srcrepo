/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Compilation Unit Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef#getCompilationUnitModel <em>Compilation Unit Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getJavaCompilationUnitRef()
 * @model
 * @generated
 */
public interface JavaCompilationUnitRef extends AbstractFileRef {
	/**
	 * Returns the value of the '<em><b>Compilation Unit Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compilation Unit Model</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compilation Unit Model</em>' containment reference.
	 * @see #setCompilationUnitModel(CompilationUnitModel)
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage#getJavaCompilationUnitRef_CompilationUnitModel()
	 * @model containment="true"
	 *        annotation="de.hub.emffrag fragments='true'"
	 * @generated
	 */
	CompilationUnitModel getCompilationUnitModel();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef#getCompilationUnitModel <em>Compilation Unit Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compilation Unit Model</em>' containment reference.
	 * @see #getCompilationUnitModel()
	 * @generated
	 */
	void setCompilationUnitModel(CompilationUnitModel value);

} // JavaCompilationUnitRef
