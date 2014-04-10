/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.repositorymodel.RepositoryModelPackage
 * @generated
 */
public interface RepositoryModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RepositoryModelFactory eINSTANCE = de.hub.srcrepo.repositorymodel.impl.RepositoryModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Repository Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repository Model</em>'.
	 * @generated
	 */
	RepositoryModel createRepositoryModel();

	/**
	 * Returns a new object of class '<em>Rev</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rev</em>'.
	 * @generated
	 */
	Rev createRev();

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
	 * Returns a new object of class '<em>Parent Relation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parent Relation</em>'.
	 * @generated
	 */
	ParentRelation createParentRelation();

	/**
	 * Returns a new object of class '<em>Java Compilation Unit Ref</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Compilation Unit Ref</em>'.
	 * @generated
	 */
	JavaCompilationUnitRef createJavaCompilationUnitRef();

	/**
	 * Returns a new object of class '<em>Traversal State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Traversal State</em>'.
	 * @generated
	 */
	TraversalState createTraversalState();

	/**
	 * Returns a new object of class '<em>Mo Disco Import State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mo Disco Import State</em>'.
	 * @generated
	 */
	MoDiscoImportState createMoDiscoImportState();

	/**
	 * Returns a new object of class '<em>Java Bindings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Bindings</em>'.
	 * @generated
	 */
	JavaBindings createJavaBindings();

	/**
	 * Returns a new object of class '<em>Java Bindings Per Branch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Bindings Per Branch</em>'.
	 * @generated
	 */
	JavaBindingsPerBranch createJavaBindingsPerBranch();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RepositoryModelPackage getRepositoryModelPackage();

} //RepositoryModelFactory
