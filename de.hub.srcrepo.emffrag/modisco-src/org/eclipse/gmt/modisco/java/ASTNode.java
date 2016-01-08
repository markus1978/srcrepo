/**
 */
package org.eclipse.gmt.modisco.java;

import org.eclipse.emf.common.util.EList;

import de.hub.emffrag.FObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>AST Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.gmt.modisco.java.ASTNode#getComments <em>Comments</em>}</li>
 *   <li>{@link org.eclipse.gmt.modisco.java.ASTNode#getOriginalCompilationUnit <em>Original Compilation Unit</em>}</li>
 *   <li>{@link org.eclipse.gmt.modisco.java.ASTNode#getOriginalClassFile <em>Original Class File</em>}</li>
 * </ul>
 *
 * @see org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage#getASTNode()
 * @model abstract="true"
 * @extends FObject
 * @generated
 */
public interface ASTNode extends FObject {
	/**
	 * Returns the value of the '<em><b>Comments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.gmt.modisco.java.Comment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comments</em>' containment reference list.
	 * @see org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage#getASTNode_Comments()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Comment> getComments();

	/**
	 * Returns the value of the '<em><b>Original Compilation Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Original Compilation Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Original Compilation Unit</em>' reference.
	 * @see #setOriginalCompilationUnit(CompilationUnit)
	 * @see org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage#getASTNode_OriginalCompilationUnit()
	 * @model ordered="false"
	 * @generated
	 */
	CompilationUnit getOriginalCompilationUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.gmt.modisco.java.ASTNode#getOriginalCompilationUnit <em>Original Compilation Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Compilation Unit</em>' reference.
	 * @see #getOriginalCompilationUnit()
	 * @generated
	 */
	void setOriginalCompilationUnit(CompilationUnit value);

	/**
	 * Returns the value of the '<em><b>Original Class File</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Original Class File</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Original Class File</em>' reference.
	 * @see #setOriginalClassFile(ClassFile)
	 * @see org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage#getASTNode_OriginalClassFile()
	 * @model ordered="false"
	 * @generated
	 */
	ClassFile getOriginalClassFile();

	/**
	 * Sets the value of the '{@link org.eclipse.gmt.modisco.java.ASTNode#getOriginalClassFile <em>Original Class File</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Class File</em>' reference.
	 * @see #getOriginalClassFile()
	 * @generated
	 */
	void setOriginalClassFile(ClassFile value);

} // ASTNode
