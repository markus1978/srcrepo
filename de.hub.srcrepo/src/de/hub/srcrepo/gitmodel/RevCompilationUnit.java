/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.gmt.modisco.java.CompilationUnit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rev Compilation Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.RevCompilationUnit#getParent <em>Parent</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.RevCompilationUnit#getCommit <em>Commit</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.RevCompilationUnit#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getRevCompilationUnit()
 * @model
 * @generated
 */
public interface RevCompilationUnit extends CompilationUnit {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference list.
	 * The list contents are of type {@link de.hub.srcrepo.gitmodel.RevCompilationUnit}.
	 * It is bidirectional and its opposite is '{@link de.hub.srcrepo.gitmodel.RevCompilationUnit#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference list.
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getRevCompilationUnit_Parent()
	 * @see de.hub.srcrepo.gitmodel.RevCompilationUnit#getChildren
	 * @model opposite="children"
	 * @generated
	 */
	EList<RevCompilationUnit> getParent();

	/**
	 * Returns the value of the '<em><b>Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit</em>' reference.
	 * @see #setCommit(Commit)
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getRevCompilationUnit_Commit()
	 * @model
	 * @generated
	 */
	Commit getCommit();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.RevCompilationUnit#getCommit <em>Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit</em>' reference.
	 * @see #getCommit()
	 * @generated
	 */
	void setCommit(Commit value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' reference list.
	 * The list contents are of type {@link de.hub.srcrepo.gitmodel.RevCompilationUnit}.
	 * It is bidirectional and its opposite is '{@link de.hub.srcrepo.gitmodel.RevCompilationUnit#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' reference list.
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getRevCompilationUnit_Children()
	 * @see de.hub.srcrepo.gitmodel.RevCompilationUnit#getParent
	 * @model opposite="parent"
	 * @generated
	 */
	EList<RevCompilationUnit> getChildren();

} // RevCompilationUnit
