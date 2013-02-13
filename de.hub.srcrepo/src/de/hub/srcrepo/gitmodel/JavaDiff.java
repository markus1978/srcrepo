/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel;

import org.eclipse.gmt.modisco.java.CompilationUnit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Diff</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.JavaDiff#getCompilationUnit <em>Compilation Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getJavaDiff()
 * @model
 * @generated
 */
public interface JavaDiff extends Diff {
	/**
	 * Returns the value of the '<em><b>Compilation Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compilation Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compilation Unit</em>' reference.
	 * @see #setCompilationUnit(CompilationUnit)
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#getJavaDiff_CompilationUnit()
	 * @model annotation="de.hub.emffrag Fragmentation='true'"
	 * @generated
	 */
	CompilationUnit getCompilationUnit();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.gitmodel.JavaDiff#getCompilationUnit <em>Compilation Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compilation Unit</em>' reference.
	 * @see #getCompilationUnit()
	 * @generated
	 */
	void setCompilationUnit(CompilationUnit value);

} // JavaDiff
