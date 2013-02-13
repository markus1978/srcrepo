/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.gitmodel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Diff#getNewPath <em>New Path</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Diff#getType <em>Type</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Diff#getCompilationUnit <em>Compilation Unit</em>}</li>
 *   <li>{@link de.hub.srcrepo.nofrag.gitmodel.Diff#getOldPath <em>Old Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getDiff()
 * @model
 * @generated
 */
public interface Diff extends EObject {
	/**
	 * Returns the value of the '<em><b>New Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Path</em>' attribute.
	 * @see #setNewPath(String)
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getDiff_NewPath()
	 * @model
	 * @generated
	 */
	String getNewPath();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.nofrag.gitmodel.Diff#getNewPath <em>New Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Path</em>' attribute.
	 * @see #getNewPath()
	 * @generated
	 */
	void setNewPath(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(ChangeType)
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getDiff_Type()
	 * @model dataType="de.hub.srcrepo.nofrag.gitmodel.ChangeType"
	 * @generated
	 */
	ChangeType getType();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.nofrag.gitmodel.Diff#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(ChangeType value);

	/**
	 * Returns the value of the '<em><b>Compilation Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compilation Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compilation Unit</em>' reference.
	 * @see #setCompilationUnit(EObject)
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getDiff_CompilationUnit()
	 * @model annotation="de.hub.emffrag Fragmentation='true'"
	 * @generated
	 */
	EObject getCompilationUnit();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.nofrag.gitmodel.Diff#getCompilationUnit <em>Compilation Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compilation Unit</em>' reference.
	 * @see #getCompilationUnit()
	 * @generated
	 */
	void setCompilationUnit(EObject value);

	/**
	 * Returns the value of the '<em><b>Old Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Path</em>' attribute.
	 * @see #setOldPath(String)
	 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage#getDiff_OldPath()
	 * @model
	 * @generated
	 */
	String getOldPath();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.nofrag.gitmodel.Diff#getOldPath <em>Old Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Path</em>' attribute.
	 * @see #getOldPath()
	 * @generated
	 */
	void setOldPath(String value);

} // Diff
