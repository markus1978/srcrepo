/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.emffrag.impl;

import de.hub.srcrepo.gitmodel.JavaCompilationUnitRef;

import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.gmt.modisco.java.CompilationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Compilation Unit Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.emffrag.impl.JavaCompilationUnitRefImpl#getCompilationUnit <em>Compilation Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaCompilationUnitRefImpl extends AbstractFileRefImpl implements JavaCompilationUnitRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaCompilationUnitRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GitModelPackage.Literals.JAVA_COMPILATION_UNIT_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilationUnit getCompilationUnit() {
		return (CompilationUnit)eGet(GitModelPackage.Literals.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompilationUnit(CompilationUnit newCompilationUnit) {
		eSet(GitModelPackage.Literals.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT, newCompilationUnit);
	}

} //JavaCompilationUnitRefImpl
