/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.emffrag.metadata;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.EmfFragSourceRepository;
import de.hub.srcrepo.gitmodel.JavaDiff;
import de.hub.srcrepo.gitmodel.ParentRelation;
import de.hub.srcrepo.gitmodel.Ref;
import de.hub.srcrepo.gitmodel.RevCompilationUnit;
import de.hub.srcrepo.gitmodel.SourceRepository;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage
 * @generated
 */
public interface GitModelFactory extends de.hub.srcrepo.gitmodel.GitModelFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GitModelFactory eINSTANCE = de.hub.srcrepo.gitmodel.emffrag.impl.GitModelFactoryImpl.init();

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
	 * Returns a new object of class '<em>Java Diff</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Diff</em>'.
	 * @generated
	 */
	JavaDiff createJavaDiff();

	/**
	 * Returns a new object of class '<em>Rev Compilation Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rev Compilation Unit</em>'.
	 * @generated
	 */
	RevCompilationUnit createRevCompilationUnit();

	/**
	 * Returns a new object of class '<em>Parent Relation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parent Relation</em>'.
	 * @generated
	 */
	ParentRelation createParentRelation();

	/**
	 * Returns a new object of class '<em>Emf Frag Source Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Emf Frag Source Repository</em>'.
	 * @generated
	 */
	EmfFragSourceRepository createEmfFragSourceRepository();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GitModelPackage getGitModelPackage();

} //GitModelFactory