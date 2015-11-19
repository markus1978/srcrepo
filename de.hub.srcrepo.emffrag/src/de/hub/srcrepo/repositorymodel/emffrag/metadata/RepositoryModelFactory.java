/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.metadata;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.DataStoreMetaData;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ImportError;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.MongoDBMetaData;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.PendingElement;
import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Target;
import de.hub.srcrepo.repositorymodel.TraversalState;
import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
 * @generated
 */
public interface RepositoryModelFactory extends de.hub.srcrepo.repositorymodel.RepositoryModelFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RepositoryModelFactory eINSTANCE = de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelFactoryImpl.init();

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
	 * Returns a new object of class '<em>Pending Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pending Element</em>'.
	 * @generated
	 */
	PendingElement createPendingElement();

	/**
	 * Returns a new object of class '<em>Target</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Target</em>'.
	 * @generated
	 */
	Target createTarget();

	/**
	 * Returns a new object of class '<em>Compilation Unit Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compilation Unit Model</em>'.
	 * @generated
	 */
	CompilationUnitModel createCompilationUnitModel();

	/**
	 * Returns a new object of class '<em>Import Error</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Import Error</em>'.
	 * @generated
	 */
	ImportError createImportError();

	/**
	 * Returns a new object of class '<em>Repository Meta Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repository Meta Data</em>'.
	 * @generated
	 */
	RepositoryMetaData createRepositoryMetaData();

	/**
	 * Returns a new object of class '<em>Directory</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Directory</em>'.
	 * @generated
	 */
	RepositoryModelDirectory createRepositoryModelDirectory();

	/**
	 * Returns a new object of class '<em>Data Store Meta Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Store Meta Data</em>'.
	 * @generated
	 */
	DataStoreMetaData createDataStoreMetaData();

	/**
	 * Returns a new object of class '<em>Mongo DB Meta Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mongo DB Meta Data</em>'.
	 * @generated
	 */
	MongoDBMetaData createMongoDBMetaData();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RepositoryModelPackage getRepositoryModelPackage();

} //RepositoryModelFactory
