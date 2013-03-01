/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmt.modisco.java.emffrag.impl.CompilationUnitImpl;

import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.RevCompilationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rev Compilation Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.RevCompilationUnitImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.RevCompilationUnitImpl#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RevCompilationUnitImpl extends CompilationUnitImpl implements RevCompilationUnit {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RevCompilationUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GitModelPackage.Literals.REV_COMPILATION_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RevCompilationUnit> getParent() {
		return (EList<RevCompilationUnit>)eGet(GitModelPackage.Literals.REV_COMPILATION_UNIT__PARENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RevCompilationUnit> getChildren() {
		return (EList<RevCompilationUnit>)eGet(GitModelPackage.Literals.REV_COMPILATION_UNIT__CHILDREN, true);
	}

} //RevCompilationUnitImpl
