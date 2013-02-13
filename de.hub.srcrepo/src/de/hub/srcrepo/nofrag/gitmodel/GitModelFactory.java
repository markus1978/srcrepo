/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.nofrag.gitmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.nofrag.gitmodel.GitModelPackage
 * @generated
 */
public interface GitModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GitModelFactory eINSTANCE = de.hub.srcrepo.nofrag.gitmodel.impl.GitModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Source Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Source Repository</em>'.
	 * @generated
	 */
	SourceRepository createSourceRepository();

	/**
	 * Returns a new object of class '<em>Commit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Commit</em>'.
	 * @generated
	 */
	Commit createCommit();

	/**
	 * Returns a new object of class '<em>Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ref</em>'.
	 * @generated
	 */
	Ref createRef();

	/**
	 * Returns a new object of class '<em>Diff</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Diff</em>'.
	 * @generated
	 */
	Diff createDiff();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GitModelPackage getGitModelPackage();

} //GitModelFactory
