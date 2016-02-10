/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.metadata;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/OCL/Import ecore='http://www.eclipse.org/emf/2002/Ecore' java='../../../plugin/org.eclipse.gmt.modisco.java/model/java.ecore#/'"
 * @generated
 */
public interface RepositoryModelPackage extends de.hub.srcrepo.repositorymodel.RepositoryModelPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "repositorymodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://hub.sam.repositorymodel/1.0/emffrag";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "repositorymodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RepositoryModelPackage eINSTANCE = de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryElementImpl <em>Repository Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryElementImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryElement()
	 * @generated
	 */
	int REPOSITORY_ELEMENT = 16;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_ELEMENT__DATA_SETS = 0;

	/**
	 * The number of structural features of the '<em>Repository Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Repository Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DirectoryElementImpl <em>Directory Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.DirectoryElementImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getDirectoryElement()
	 * @generated
	 */
	int DIRECTORY_ELEMENT = 14;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_ELEMENT__DATA_SETS = REPOSITORY_ELEMENT__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_ELEMENT__NAME = REPOSITORY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_ELEMENT__DESCRIPTION = REPOSITORY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_ELEMENT__URL = REPOSITORY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Directory Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_ELEMENT_FEATURE_COUNT = REPOSITORY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Directory Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_ELEMENT_OPERATION_COUNT = REPOSITORY_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl <em>Repository Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryModel()
	 * @generated
	 */
	int REPOSITORY_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__DATA_SETS = DIRECTORY_ELEMENT__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__NAME = DIRECTORY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__DESCRIPTION = DIRECTORY_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__URL = DIRECTORY_ELEMENT__URL;

	/**
	 * The feature id for the '<em><b>All Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__ALL_REFS = DIRECTORY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>All Revs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__ALL_REVS = DIRECTORY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Root Revs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__ROOT_REVS = DIRECTORY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Repository Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_FEATURE_COUNT = DIRECTORY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Repository Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_OPERATION_COUNT = DIRECTORY_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl <em>Rev</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRev()
	 * @generated
	 */
	int REV = 1;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__DATA_SETS = REPOSITORY_ELEMENT__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__AUTHOR = REPOSITORY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Commiter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__COMMITER = REPOSITORY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__NAME = REPOSITORY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__TIME = REPOSITORY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__MESSAGE = REPOSITORY_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parent Relations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__PARENT_RELATIONS = REPOSITORY_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Child Relations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__CHILD_RELATIONS = REPOSITORY_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Import Errors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__IMPORT_ERRORS = REPOSITORY_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Rev</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_FEATURE_COUNT = REPOSITORY_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Rev</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_OPERATION_COUNT = REPOSITORY_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RefImpl <em>Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RefImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRef()
	 * @generated
	 */
	int REF = 2;

	/**
	 * The feature id for the '<em><b>Referenced Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REF__REFERENCED_COMMIT = 0;

	/**
	 * The feature id for the '<em><b>Is Peeled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REF__IS_PEELED = 1;

	/**
	 * The feature id for the '<em><b>Is Symbolic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REF__IS_SYMBOLIC = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REF__NAME = 3;

	/**
	 * The number of structural features of the '<em>Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REF_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REF_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl <em>Diff</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getDiff()
	 * @generated
	 */
	int DIFF = 3;

	/**
	 * The feature id for the '<em><b>New Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF__NEW_PATH = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Old Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF__OLD_PATH = 2;

	/**
	 * The feature id for the '<em><b>File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF__FILE = 3;

	/**
	 * The number of structural features of the '<em>Diff</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Diff</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ParentRelationImpl <em>Parent Relation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.ParentRelationImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getParentRelation()
	 * @generated
	 */
	int PARENT_RELATION = 4;

	/**
	 * The feature id for the '<em><b>Diffs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RELATION__DIFFS = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RELATION__PARENT = 1;

	/**
	 * The feature id for the '<em><b>Child</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RELATION__CHILD = 2;

	/**
	 * The number of structural features of the '<em>Parent Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RELATION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Parent Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENT_RELATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.AbstractFileRefImpl <em>Abstract File Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.AbstractFileRefImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getAbstractFileRef()
	 * @generated
	 */
	int ABSTRACT_FILE_REF = 5;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILE_REF__DATA_SETS = REPOSITORY_ELEMENT__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILE_REF__PATH = REPOSITORY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract File Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILE_REF_FEATURE_COUNT = REPOSITORY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Abstract File Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILE_REF_OPERATION_COUNT = REPOSITORY_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.JavaCompilationUnitRefImpl <em>Java Compilation Unit Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.JavaCompilationUnitRefImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getJavaCompilationUnitRef()
	 * @generated
	 */
	int JAVA_COMPILATION_UNIT_REF = 6;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_COMPILATION_UNIT_REF__DATA_SETS = ABSTRACT_FILE_REF__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_COMPILATION_UNIT_REF__PATH = ABSTRACT_FILE_REF__PATH;

	/**
	 * The feature id for the '<em><b>Compilation Unit Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL = ABSTRACT_FILE_REF_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Compilation Unit Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_COMPILATION_UNIT_REF_FEATURE_COUNT = ABSTRACT_FILE_REF_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Java Compilation Unit Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_COMPILATION_UNIT_REF_OPERATION_COUNT = ABSTRACT_FILE_REF_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TargetImpl <em>Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.TargetImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getTarget()
	 * @generated
	 */
	int TARGET = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__ID = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl <em>Compilation Unit Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getCompilationUnitModel()
	 * @generated
	 */
	int COMPILATION_UNIT_MODEL = 8;

	/**
	 * The feature id for the '<em><b>Compilation Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL__COMPILATION_UNIT = 0;

	/**
	 * The feature id for the '<em><b>Java Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL__JAVA_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Targets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL__TARGETS = 2;

	/**
	 * The feature id for the '<em><b>Unresolved Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL__UNRESOLVED_LINKS = 3;

	/**
	 * The feature id for the '<em><b>Project ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL__PROJECT_ID = 4;

	/**
	 * The number of structural features of the '<em>Compilation Unit Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Compilation Unit Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportErrorImpl <em>Import Error</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.ImportErrorImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getImportError()
	 * @generated
	 */
	int IMPORT_ERROR = 9;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ERROR__MESSAGE = 0;

	/**
	 * The feature id for the '<em><b>Conrolled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ERROR__CONROLLED = 1;

	/**
	 * The feature id for the '<em><b>Exception Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ERROR__EXCEPTION_CLASS_NAME = 2;

	/**
	 * The number of structural features of the '<em>Import Error</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ERROR_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Import Error</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_ERROR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DataSetImpl <em>Data Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.DataSetImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getDataSet()
	 * @generated
	 */
	int DATA_SET = 17;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SET__DATA_SETS = REPOSITORY_ELEMENT__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SET__NAME = REPOSITORY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SET__DATA = REPOSITORY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Json Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SET__JSON_DATA = REPOSITORY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Data Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SET_FEATURE_COUNT = REPOSITORY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Data Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_SET_OPERATION_COUNT = REPOSITORY_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl <em>Repository Meta Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryMetaData()
	 * @generated
	 */
	int REPOSITORY_META_DATA = 10;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__DATA_SETS = DATA_SET__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__NAME = DATA_SET__NAME;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__DATA = DATA_SET__DATA;

	/**
	 * The feature id for the '<em><b>Json Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__JSON_DATA = DATA_SET__JSON_DATA;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__ORIGIN = DATA_SET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Oldest Rev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__OLDEST_REV = DATA_SET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rev Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__REV_COUNT = DATA_SET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Newest Rev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__NEWEST_REV = DATA_SET_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Cu Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__CU_COUNT = DATA_SET_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Revs With Errors</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__REVS_WITH_ERRORS = DATA_SET_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Cus With Errors</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__CUS_WITH_ERRORS = DATA_SET_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__SIZE = DATA_SET_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Repository Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA_FEATURE_COUNT = DATA_SET_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Repository Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA_OPERATION_COUNT = DATA_SET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl <em>Directory</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryModelDirectory()
	 * @generated
	 */
	int REPOSITORY_MODEL_DIRECTORY = 11;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__DATA_SETS = DIRECTORY_ELEMENT__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__NAME = DIRECTORY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__DESCRIPTION = DIRECTORY_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__URL = DIRECTORY_ELEMENT__URL;

	/**
	 * The feature id for the '<em><b>Repositories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__REPOSITORIES = DIRECTORY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sub Directories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__SUB_DIRECTORIES = DIRECTORY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Scheduled For Import</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__SCHEDULED_FOR_IMPORT = DIRECTORY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Imported</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__IMPORTED = DIRECTORY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Directory</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY_FEATURE_COUNT = DIRECTORY_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Directory</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY_OPERATION_COUNT = DIRECTORY_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DataStoreMetaDataImpl <em>Data Store Meta Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.DataStoreMetaDataImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getDataStoreMetaData()
	 * @generated
	 */
	int DATA_STORE_META_DATA = 12;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA__DATA_SETS = DATA_SET__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA__NAME = DATA_SET__NAME;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA__DATA = DATA_SET__DATA;

	/**
	 * The feature id for the '<em><b>Json Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA__JSON_DATA = DATA_SET__JSON_DATA;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA__COUNT = DATA_SET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Data Store Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA_FEATURE_COUNT = DATA_SET_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Data Store Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA_OPERATION_COUNT = DATA_SET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl <em>Mongo DB Meta Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getMongoDBMetaData()
	 * @generated
	 */
	int MONGO_DB_META_DATA = 13;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA__DATA_SETS = DATA_STORE_META_DATA__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA__NAME = DATA_STORE_META_DATA__NAME;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA__DATA = DATA_STORE_META_DATA__DATA;

	/**
	 * The feature id for the '<em><b>Json Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA__JSON_DATA = DATA_STORE_META_DATA__JSON_DATA;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA__COUNT = DATA_STORE_META_DATA__COUNT;

	/**
	 * The feature id for the '<em><b>Ns</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA__NS = DATA_STORE_META_DATA_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Avg Object Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA__AVG_OBJECT_SIZE = DATA_STORE_META_DATA_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Store Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA__STORE_SIZE = DATA_STORE_META_DATA_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA__SERVER = DATA_STORE_META_DATA_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Mongo DB Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA_FEATURE_COUNT = DATA_STORE_META_DATA_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Mongo DB Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONGO_DB_META_DATA_OPERATION_COUNT = DATA_STORE_META_DATA_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TaskDataImpl <em>Task Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.TaskDataImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getTaskData()
	 * @generated
	 */
	int TASK_DATA = 18;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DATA__DATA_SETS = DATA_SET__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DATA__NAME = DATA_SET__NAME;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DATA__DATA = DATA_SET__DATA;

	/**
	 * The feature id for the '<em><b>Json Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DATA__JSON_DATA = DATA_SET__JSON_DATA;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DATA__DATE = DATA_SET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Stats As JSON</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DATA__STATS_AS_JSON = DATA_SET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DATA__DESCRIPTION = DATA_SET_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Task Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DATA_FEATURE_COUNT = DATA_SET_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Task Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_DATA_OPERATION_COUNT = DATA_SET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl <em>Import Meta Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getImportMetaData()
	 * @generated
	 */
	int IMPORT_META_DATA = 15;

	/**
	 * The feature id for the '<em><b>Data Sets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__DATA_SETS = TASK_DATA__DATA_SETS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__NAME = TASK_DATA__NAME;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__DATA = TASK_DATA__DATA;

	/**
	 * The feature id for the '<em><b>Json Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__JSON_DATA = TASK_DATA__JSON_DATA;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__DATE = TASK_DATA__DATE;

	/**
	 * The feature id for the '<em><b>Stats As JSON</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__STATS_AS_JSON = TASK_DATA__STATS_AS_JSON;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__DESCRIPTION = TASK_DATA__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Scheduled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__SCHEDULED = TASK_DATA_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Importing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__IMPORTING = TASK_DATA_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Imported</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__IMPORTED = TASK_DATA_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Working Copy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA__WORKING_COPY = TASK_DATA_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Import Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA_FEATURE_COUNT = TASK_DATA_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Import Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_META_DATA_OPERATION_COUNT = TASK_DATA_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.UnresolvedLinkImpl <em>Unresolved Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.UnresolvedLinkImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getUnresolvedLink()
	 * @generated
	 */
	int UNRESOLVED_LINK = 19;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNRESOLVED_LINK__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNRESOLVED_LINK__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Feature ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNRESOLVED_LINK__FEATURE_ID = 2;

	/**
	 * The feature id for the '<em><b>Feature Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNRESOLVED_LINK__FEATURE_INDEX = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNRESOLVED_LINK__ID = 4;

	/**
	 * The number of structural features of the '<em>Unresolved Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNRESOLVED_LINK_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Unresolved Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNRESOLVED_LINK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>Change Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jgit.diff.DiffEntry.ChangeType
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getChangeType()
	 * @generated
	 */
	int CHANGE_TYPE = 20;


	/**
	 * The meta object id for the '<em>EData Map</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Map
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getEDataMap()
	 * @generated
	 */
	int EDATA_MAP = 21;


	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.RepositoryModel <em>Repository Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository Model</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModel
	 * @generated
	 */
	EClass getRepositoryModel();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getAllRefs <em>All Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Refs</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModel#getAllRefs()
	 * @see #getRepositoryModel()
	 * @generated
	 */
	EReference getRepositoryModel_AllRefs();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getAllRevs <em>All Revs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Revs</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModel#getAllRevs()
	 * @see #getRepositoryModel()
	 * @generated
	 */
	EReference getRepositoryModel_AllRevs();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getRootRevs <em>Root Revs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Root Revs</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModel#getRootRevs()
	 * @see #getRepositoryModel()
	 * @generated
	 */
	EReference getRepositoryModel_RootRevs();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.Rev <em>Rev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rev</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Rev
	 * @generated
	 */
	EClass getRev();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Rev#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Rev#getAuthor()
	 * @see #getRev()
	 * @generated
	 */
	EAttribute getRev_Author();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Rev#getCommiter <em>Commiter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commiter</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Rev#getCommiter()
	 * @see #getRev()
	 * @generated
	 */
	EAttribute getRev_Commiter();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Rev#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Rev#getName()
	 * @see #getRev()
	 * @generated
	 */
	EAttribute getRev_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Rev#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Rev#getTime()
	 * @see #getRev()
	 * @generated
	 */
	EAttribute getRev_Time();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Rev#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Rev#getMessage()
	 * @see #getRev()
	 * @generated
	 */
	EAttribute getRev_Message();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.Rev#getParentRelations <em>Parent Relations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parent Relations</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Rev#getParentRelations()
	 * @see #getRev()
	 * @generated
	 */
	EReference getRev_ParentRelations();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.repositorymodel.Rev#getChildRelations <em>Child Relations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Child Relations</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Rev#getChildRelations()
	 * @see #getRev()
	 * @generated
	 */
	EReference getRev_ChildRelations();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.Rev#getImportErrors <em>Import Errors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Import Errors</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Rev#getImportErrors()
	 * @see #getRev()
	 * @generated
	 */
	EReference getRev_ImportErrors();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.Ref <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ref</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Ref
	 * @generated
	 */
	EClass getRef();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.Ref#getReferencedCommit <em>Referenced Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Referenced Commit</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Ref#getReferencedCommit()
	 * @see #getRef()
	 * @generated
	 */
	EReference getRef_ReferencedCommit();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Ref#isIsPeeled <em>Is Peeled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Peeled</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Ref#isIsPeeled()
	 * @see #getRef()
	 * @generated
	 */
	EAttribute getRef_IsPeeled();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Ref#isIsSymbolic <em>Is Symbolic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Symbolic</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Ref#isIsSymbolic()
	 * @see #getRef()
	 * @generated
	 */
	EAttribute getRef_IsSymbolic();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Ref#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Ref#getName()
	 * @see #getRef()
	 * @generated
	 */
	EAttribute getRef_Name();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.Diff <em>Diff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diff</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Diff
	 * @generated
	 */
	EClass getDiff();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Diff#getNewPath <em>New Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Path</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Diff#getNewPath()
	 * @see #getDiff()
	 * @generated
	 */
	EAttribute getDiff_NewPath();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Diff#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Diff#getType()
	 * @see #getDiff()
	 * @generated
	 */
	EAttribute getDiff_Type();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Diff#getOldPath <em>Old Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Path</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Diff#getOldPath()
	 * @see #getDiff()
	 * @generated
	 */
	EAttribute getDiff_OldPath();

	/**
	 * Returns the meta object for the containment reference '{@link de.hub.srcrepo.repositorymodel.Diff#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>File</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Diff#getFile()
	 * @see #getDiff()
	 * @generated
	 */
	EReference getDiff_File();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.ParentRelation <em>Parent Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parent Relation</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ParentRelation
	 * @generated
	 */
	EClass getParentRelation();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.ParentRelation#getDiffs <em>Diffs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Diffs</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ParentRelation#getDiffs()
	 * @see #getParentRelation()
	 * @generated
	 */
	EReference getParentRelation_Diffs();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.ParentRelation#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ParentRelation#getParent()
	 * @see #getParentRelation()
	 * @generated
	 */
	EReference getParentRelation_Parent();

	/**
	 * Returns the meta object for the container reference '{@link de.hub.srcrepo.repositorymodel.ParentRelation#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Child</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ParentRelation#getChild()
	 * @see #getParentRelation()
	 * @generated
	 */
	EReference getParentRelation_Child();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.AbstractFileRef <em>Abstract File Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract File Ref</em>'.
	 * @see de.hub.srcrepo.repositorymodel.AbstractFileRef
	 * @generated
	 */
	EClass getAbstractFileRef();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.AbstractFileRef#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see de.hub.srcrepo.repositorymodel.AbstractFileRef#getPath()
	 * @see #getAbstractFileRef()
	 * @generated
	 */
	EAttribute getAbstractFileRef_Path();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef <em>Java Compilation Unit Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Compilation Unit Ref</em>'.
	 * @see de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
	 * @generated
	 */
	EClass getJavaCompilationUnitRef();

	/**
	 * Returns the meta object for the containment reference '{@link de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef#getCompilationUnitModel <em>Compilation Unit Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Compilation Unit Model</em>'.
	 * @see de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef#getCompilationUnitModel()
	 * @see #getJavaCompilationUnitRef()
	 * @generated
	 */
	EReference getJavaCompilationUnitRef_CompilationUnitModel();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Target
	 * @generated
	 */
	EClass getTarget();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.Target#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Target#getId()
	 * @see #getTarget()
	 * @generated
	 */
	EAttribute getTarget_Id();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.Target#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see de.hub.srcrepo.repositorymodel.Target#getTarget()
	 * @see #getTarget()
	 * @generated
	 */
	EReference getTarget_Target();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel <em>Compilation Unit Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compilation Unit Model</em>'.
	 * @see de.hub.srcrepo.repositorymodel.CompilationUnitModel
	 * @generated
	 */
	EClass getCompilationUnitModel();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getCompilationUnit <em>Compilation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Compilation Unit</em>'.
	 * @see de.hub.srcrepo.repositorymodel.CompilationUnitModel#getCompilationUnit()
	 * @see #getCompilationUnitModel()
	 * @generated
	 */
	EReference getCompilationUnitModel_CompilationUnit();

	/**
	 * Returns the meta object for the containment reference '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getJavaModel <em>Java Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Java Model</em>'.
	 * @see de.hub.srcrepo.repositorymodel.CompilationUnitModel#getJavaModel()
	 * @see #getCompilationUnitModel()
	 * @generated
	 */
	EReference getCompilationUnitModel_JavaModel();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getTargets <em>Targets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Targets</em>'.
	 * @see de.hub.srcrepo.repositorymodel.CompilationUnitModel#getTargets()
	 * @see #getCompilationUnitModel()
	 * @generated
	 */
	EReference getCompilationUnitModel_Targets();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getUnresolvedLinks <em>Unresolved Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Unresolved Links</em>'.
	 * @see de.hub.srcrepo.repositorymodel.CompilationUnitModel#getUnresolvedLinks()
	 * @see #getCompilationUnitModel()
	 * @generated
	 */
	EReference getCompilationUnitModel_UnresolvedLinks();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getProjectID <em>Project ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project ID</em>'.
	 * @see de.hub.srcrepo.repositorymodel.CompilationUnitModel#getProjectID()
	 * @see #getCompilationUnitModel()
	 * @generated
	 */
	EAttribute getCompilationUnitModel_ProjectID();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.ImportError <em>Import Error</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Error</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ImportError
	 * @generated
	 */
	EClass getImportError();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.ImportError#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ImportError#getMessage()
	 * @see #getImportError()
	 * @generated
	 */
	EAttribute getImportError_Message();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.ImportError#isConrolled <em>Conrolled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Conrolled</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ImportError#isConrolled()
	 * @see #getImportError()
	 * @generated
	 */
	EAttribute getImportError_Conrolled();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.ImportError#getExceptionClassName <em>Exception Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exception Class Name</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ImportError#getExceptionClassName()
	 * @see #getImportError()
	 * @generated
	 */
	EAttribute getImportError_ExceptionClassName();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData <em>Repository Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository Meta Data</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData
	 * @generated
	 */
	EClass getRepositoryMetaData();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getOrigin <em>Origin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Origin</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getOrigin()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_Origin();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getOldestRev <em>Oldest Rev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Oldest Rev</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getOldestRev()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_OldestRev();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRevCount <em>Rev Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rev Count</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRevCount()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_RevCount();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getNewestRev <em>Newest Rev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Newest Rev</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getNewestRev()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_NewestRev();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRevsWithErrors <em>Revs With Errors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Revs With Errors</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRevsWithErrors()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_RevsWithErrors();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getCusWithErrors <em>Cus With Errors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cus With Errors</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getCusWithErrors()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_CusWithErrors();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getSize()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_Size();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory <em>Directory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Directory</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
	 * @generated
	 */
	EClass getRepositoryModelDirectory();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getRepositories <em>Repositories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Repositories</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getRepositories()
	 * @see #getRepositoryModelDirectory()
	 * @generated
	 */
	EReference getRepositoryModelDirectory_Repositories();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getSubDirectories <em>Sub Directories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Directories</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getSubDirectories()
	 * @see #getRepositoryModelDirectory()
	 * @generated
	 */
	EReference getRepositoryModelDirectory_SubDirectories();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getScheduledForImport <em>Scheduled For Import</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Scheduled For Import</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getScheduledForImport()
	 * @see #getRepositoryModelDirectory()
	 * @generated
	 */
	EReference getRepositoryModelDirectory_ScheduledForImport();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getImported <em>Imported</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imported</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getImported()
	 * @see #getRepositoryModelDirectory()
	 * @generated
	 */
	EReference getRepositoryModelDirectory_Imported();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.DataStoreMetaData <em>Data Store Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Store Meta Data</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DataStoreMetaData
	 * @generated
	 */
	EClass getDataStoreMetaData();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.DataStoreMetaData#getCount <em>Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Count</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DataStoreMetaData#getCount()
	 * @see #getDataStoreMetaData()
	 * @generated
	 */
	EAttribute getDataStoreMetaData_Count();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData <em>Mongo DB Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mongo DB Meta Data</em>'.
	 * @see de.hub.srcrepo.repositorymodel.MongoDBMetaData
	 * @generated
	 */
	EClass getMongoDBMetaData();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getNs <em>Ns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ns</em>'.
	 * @see de.hub.srcrepo.repositorymodel.MongoDBMetaData#getNs()
	 * @see #getMongoDBMetaData()
	 * @generated
	 */
	EAttribute getMongoDBMetaData_Ns();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getAvgObjectSize <em>Avg Object Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Avg Object Size</em>'.
	 * @see de.hub.srcrepo.repositorymodel.MongoDBMetaData#getAvgObjectSize()
	 * @see #getMongoDBMetaData()
	 * @generated
	 */
	EAttribute getMongoDBMetaData_AvgObjectSize();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getStoreSize <em>Store Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Store Size</em>'.
	 * @see de.hub.srcrepo.repositorymodel.MongoDBMetaData#getStoreSize()
	 * @see #getMongoDBMetaData()
	 * @generated
	 */
	EAttribute getMongoDBMetaData_StoreSize();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData#getServer <em>Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Server</em>'.
	 * @see de.hub.srcrepo.repositorymodel.MongoDBMetaData#getServer()
	 * @see #getMongoDBMetaData()
	 * @generated
	 */
	EAttribute getMongoDBMetaData_Server();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.DirectoryElement <em>Directory Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Directory Element</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DirectoryElement
	 * @generated
	 */
	EClass getDirectoryElement();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.DirectoryElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DirectoryElement#getName()
	 * @see #getDirectoryElement()
	 * @generated
	 */
	EAttribute getDirectoryElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.DirectoryElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DirectoryElement#getDescription()
	 * @see #getDirectoryElement()
	 * @generated
	 */
	EAttribute getDirectoryElement_Description();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.DirectoryElement#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DirectoryElement#getUrl()
	 * @see #getDirectoryElement()
	 * @generated
	 */
	EAttribute getDirectoryElement_Url();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.ImportMetaData <em>Import Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Meta Data</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ImportMetaData
	 * @generated
	 */
	EClass getImportMetaData();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.ImportMetaData#isScheduled <em>Scheduled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scheduled</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ImportMetaData#isScheduled()
	 * @see #getImportMetaData()
	 * @generated
	 */
	EAttribute getImportMetaData_Scheduled();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.ImportMetaData#isImporting <em>Importing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Importing</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ImportMetaData#isImporting()
	 * @see #getImportMetaData()
	 * @generated
	 */
	EAttribute getImportMetaData_Importing();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.ImportMetaData#isImported <em>Imported</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Imported</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ImportMetaData#isImported()
	 * @see #getImportMetaData()
	 * @generated
	 */
	EAttribute getImportMetaData_Imported();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.ImportMetaData#getWorkingCopy <em>Working Copy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Working Copy</em>'.
	 * @see de.hub.srcrepo.repositorymodel.ImportMetaData#getWorkingCopy()
	 * @see #getImportMetaData()
	 * @generated
	 */
	EAttribute getImportMetaData_WorkingCopy();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.RepositoryElement <em>Repository Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository Element</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryElement
	 * @generated
	 */
	EClass getRepositoryElement();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.RepositoryElement#getDataSets <em>Data Sets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Sets</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryElement#getDataSets()
	 * @see #getRepositoryElement()
	 * @generated
	 */
	EReference getRepositoryElement_DataSets();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.DataSet <em>Data Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Set</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DataSet
	 * @generated
	 */
	EClass getDataSet();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.DataSet#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DataSet#getName()
	 * @see #getDataSet()
	 * @generated
	 */
	EAttribute getDataSet_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.DataSet#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DataSet#getData()
	 * @see #getDataSet()
	 * @generated
	 */
	EAttribute getDataSet_Data();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.DataSet#getJsonData <em>Json Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Json Data</em>'.
	 * @see de.hub.srcrepo.repositorymodel.DataSet#getJsonData()
	 * @see #getDataSet()
	 * @generated
	 */
	EAttribute getDataSet_JsonData();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.TaskData <em>Task Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Data</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TaskData
	 * @generated
	 */
	EClass getTaskData();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.TaskData#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TaskData#getDate()
	 * @see #getTaskData()
	 * @generated
	 */
	EAttribute getTaskData_Date();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.TaskData#getStatsAsJSON <em>Stats As JSON</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stats As JSON</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TaskData#getStatsAsJSON()
	 * @see #getTaskData()
	 * @generated
	 */
	EAttribute getTaskData_StatsAsJSON();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.TaskData#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TaskData#getDescription()
	 * @see #getTaskData()
	 * @generated
	 */
	EAttribute getTaskData_Description();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.UnresolvedLink <em>Unresolved Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unresolved Link</em>'.
	 * @see de.hub.srcrepo.repositorymodel.UnresolvedLink
	 * @generated
	 */
	EClass getUnresolvedLink();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.UnresolvedLink#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see de.hub.srcrepo.repositorymodel.UnresolvedLink#getSource()
	 * @see #getUnresolvedLink()
	 * @generated
	 */
	EReference getUnresolvedLink_Source();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.UnresolvedLink#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see de.hub.srcrepo.repositorymodel.UnresolvedLink#getTarget()
	 * @see #getUnresolvedLink()
	 * @generated
	 */
	EReference getUnresolvedLink_Target();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.UnresolvedLink#getFeatureID <em>Feature ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature ID</em>'.
	 * @see de.hub.srcrepo.repositorymodel.UnresolvedLink#getFeatureID()
	 * @see #getUnresolvedLink()
	 * @generated
	 */
	EAttribute getUnresolvedLink_FeatureID();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.UnresolvedLink#getFeatureIndex <em>Feature Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature Index</em>'.
	 * @see de.hub.srcrepo.repositorymodel.UnresolvedLink#getFeatureIndex()
	 * @see #getUnresolvedLink()
	 * @generated
	 */
	EAttribute getUnresolvedLink_FeatureIndex();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.UnresolvedLink#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see de.hub.srcrepo.repositorymodel.UnresolvedLink#getId()
	 * @see #getUnresolvedLink()
	 * @generated
	 */
	EAttribute getUnresolvedLink_Id();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getCuCount <em>Cu Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cu Count</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getCuCount()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_CuCount();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.jgit.diff.DiffEntry.ChangeType <em>Change Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Change Type</em>'.
	 * @see org.eclipse.jgit.diff.DiffEntry.ChangeType
	 * @model instanceClass="org.eclipse.jgit.diff.DiffEntry.ChangeType"
	 * @generated
	 */
	EDataType getChangeType();

	/**
	 * Returns the meta object for data type '{@link java.util.Map <em>EData Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EData Map</em>'.
	 * @see java.util.Map
	 * @model instanceClass="java.util.Map<java.lang.String, java.io.Serializable>"
	 * @generated
	 */
	EDataType getEDataMap();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RepositoryModelFactory getRepositoryModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl <em>Repository Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryModel()
		 * @generated
		 */
		EClass REPOSITORY_MODEL = eINSTANCE.getRepositoryModel();

		/**
		 * The meta object literal for the '<em><b>All Refs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL__ALL_REFS = eINSTANCE.getRepositoryModel_AllRefs();

		/**
		 * The meta object literal for the '<em><b>All Revs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL__ALL_REVS = eINSTANCE.getRepositoryModel_AllRevs();

		/**
		 * The meta object literal for the '<em><b>Root Revs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL__ROOT_REVS = eINSTANCE.getRepositoryModel_RootRevs();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl <em>Rev</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RevImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRev()
		 * @generated
		 */
		EClass REV = eINSTANCE.getRev();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REV__AUTHOR = eINSTANCE.getRev_Author();

		/**
		 * The meta object literal for the '<em><b>Commiter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REV__COMMITER = eINSTANCE.getRev_Commiter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REV__NAME = eINSTANCE.getRev_Name();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REV__TIME = eINSTANCE.getRev_Time();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REV__MESSAGE = eINSTANCE.getRev_Message();

		/**
		 * The meta object literal for the '<em><b>Parent Relations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REV__PARENT_RELATIONS = eINSTANCE.getRev_ParentRelations();

		/**
		 * The meta object literal for the '<em><b>Child Relations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REV__CHILD_RELATIONS = eINSTANCE.getRev_ChildRelations();

		/**
		 * The meta object literal for the '<em><b>Import Errors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REV__IMPORT_ERRORS = eINSTANCE.getRev_ImportErrors();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RefImpl <em>Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RefImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRef()
		 * @generated
		 */
		EClass REF = eINSTANCE.getRef();

		/**
		 * The meta object literal for the '<em><b>Referenced Commit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REF__REFERENCED_COMMIT = eINSTANCE.getRef_ReferencedCommit();

		/**
		 * The meta object literal for the '<em><b>Is Peeled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REF__IS_PEELED = eINSTANCE.getRef_IsPeeled();

		/**
		 * The meta object literal for the '<em><b>Is Symbolic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REF__IS_SYMBOLIC = eINSTANCE.getRef_IsSymbolic();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REF__NAME = eINSTANCE.getRef_Name();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl <em>Diff</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getDiff()
		 * @generated
		 */
		EClass DIFF = eINSTANCE.getDiff();

		/**
		 * The meta object literal for the '<em><b>New Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFF__NEW_PATH = eINSTANCE.getDiff_NewPath();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFF__TYPE = eINSTANCE.getDiff_Type();

		/**
		 * The meta object literal for the '<em><b>Old Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIFF__OLD_PATH = eINSTANCE.getDiff_OldPath();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIFF__FILE = eINSTANCE.getDiff_File();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ParentRelationImpl <em>Parent Relation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.ParentRelationImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getParentRelation()
		 * @generated
		 */
		EClass PARENT_RELATION = eINSTANCE.getParentRelation();

		/**
		 * The meta object literal for the '<em><b>Diffs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARENT_RELATION__DIFFS = eINSTANCE.getParentRelation_Diffs();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARENT_RELATION__PARENT = eINSTANCE.getParentRelation_Parent();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARENT_RELATION__CHILD = eINSTANCE.getParentRelation_Child();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.AbstractFileRefImpl <em>Abstract File Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.AbstractFileRefImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getAbstractFileRef()
		 * @generated
		 */
		EClass ABSTRACT_FILE_REF = eINSTANCE.getAbstractFileRef();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_FILE_REF__PATH = eINSTANCE.getAbstractFileRef_Path();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.JavaCompilationUnitRefImpl <em>Java Compilation Unit Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.JavaCompilationUnitRefImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getJavaCompilationUnitRef()
		 * @generated
		 */
		EClass JAVA_COMPILATION_UNIT_REF = eINSTANCE.getJavaCompilationUnitRef();

		/**
		 * The meta object literal for the '<em><b>Compilation Unit Model</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL = eINSTANCE.getJavaCompilationUnitRef_CompilationUnitModel();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TargetImpl <em>Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.TargetImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getTarget()
		 * @generated
		 */
		EClass TARGET = eINSTANCE.getTarget();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TARGET__ID = eINSTANCE.getTarget_Id();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET__TARGET = eINSTANCE.getTarget_Target();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl <em>Compilation Unit Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getCompilationUnitModel()
		 * @generated
		 */
		EClass COMPILATION_UNIT_MODEL = eINSTANCE.getCompilationUnitModel();

		/**
		 * The meta object literal for the '<em><b>Compilation Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILATION_UNIT_MODEL__COMPILATION_UNIT = eINSTANCE.getCompilationUnitModel_CompilationUnit();

		/**
		 * The meta object literal for the '<em><b>Java Model</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILATION_UNIT_MODEL__JAVA_MODEL = eINSTANCE.getCompilationUnitModel_JavaModel();

		/**
		 * The meta object literal for the '<em><b>Targets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILATION_UNIT_MODEL__TARGETS = eINSTANCE.getCompilationUnitModel_Targets();

		/**
		 * The meta object literal for the '<em><b>Unresolved Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILATION_UNIT_MODEL__UNRESOLVED_LINKS = eINSTANCE.getCompilationUnitModel_UnresolvedLinks();

		/**
		 * The meta object literal for the '<em><b>Project ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILATION_UNIT_MODEL__PROJECT_ID = eINSTANCE.getCompilationUnitModel_ProjectID();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportErrorImpl <em>Import Error</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.ImportErrorImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getImportError()
		 * @generated
		 */
		EClass IMPORT_ERROR = eINSTANCE.getImportError();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_ERROR__MESSAGE = eINSTANCE.getImportError_Message();

		/**
		 * The meta object literal for the '<em><b>Conrolled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_ERROR__CONROLLED = eINSTANCE.getImportError_Conrolled();

		/**
		 * The meta object literal for the '<em><b>Exception Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_ERROR__EXCEPTION_CLASS_NAME = eINSTANCE.getImportError_ExceptionClassName();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl <em>Repository Meta Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryMetaData()
		 * @generated
		 */
		EClass REPOSITORY_META_DATA = eINSTANCE.getRepositoryMetaData();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__ORIGIN = eINSTANCE.getRepositoryMetaData_Origin();

		/**
		 * The meta object literal for the '<em><b>Oldest Rev</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__OLDEST_REV = eINSTANCE.getRepositoryMetaData_OldestRev();

		/**
		 * The meta object literal for the '<em><b>Rev Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__REV_COUNT = eINSTANCE.getRepositoryMetaData_RevCount();

		/**
		 * The meta object literal for the '<em><b>Newest Rev</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__NEWEST_REV = eINSTANCE.getRepositoryMetaData_NewestRev();

		/**
		 * The meta object literal for the '<em><b>Revs With Errors</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__REVS_WITH_ERRORS = eINSTANCE.getRepositoryMetaData_RevsWithErrors();

		/**
		 * The meta object literal for the '<em><b>Cus With Errors</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__CUS_WITH_ERRORS = eINSTANCE.getRepositoryMetaData_CusWithErrors();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__SIZE = eINSTANCE.getRepositoryMetaData_Size();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl <em>Directory</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryModelDirectory()
		 * @generated
		 */
		EClass REPOSITORY_MODEL_DIRECTORY = eINSTANCE.getRepositoryModelDirectory();

		/**
		 * The meta object literal for the '<em><b>Repositories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL_DIRECTORY__REPOSITORIES = eINSTANCE.getRepositoryModelDirectory_Repositories();

		/**
		 * The meta object literal for the '<em><b>Sub Directories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL_DIRECTORY__SUB_DIRECTORIES = eINSTANCE.getRepositoryModelDirectory_SubDirectories();

		/**
		 * The meta object literal for the '<em><b>Scheduled For Import</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL_DIRECTORY__SCHEDULED_FOR_IMPORT = eINSTANCE.getRepositoryModelDirectory_ScheduledForImport();

		/**
		 * The meta object literal for the '<em><b>Imported</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL_DIRECTORY__IMPORTED = eINSTANCE.getRepositoryModelDirectory_Imported();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DataStoreMetaDataImpl <em>Data Store Meta Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.DataStoreMetaDataImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getDataStoreMetaData()
		 * @generated
		 */
		EClass DATA_STORE_META_DATA = eINSTANCE.getDataStoreMetaData();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_STORE_META_DATA__COUNT = eINSTANCE.getDataStoreMetaData_Count();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl <em>Mongo DB Meta Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getMongoDBMetaData()
		 * @generated
		 */
		EClass MONGO_DB_META_DATA = eINSTANCE.getMongoDBMetaData();

		/**
		 * The meta object literal for the '<em><b>Ns</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MONGO_DB_META_DATA__NS = eINSTANCE.getMongoDBMetaData_Ns();

		/**
		 * The meta object literal for the '<em><b>Avg Object Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MONGO_DB_META_DATA__AVG_OBJECT_SIZE = eINSTANCE.getMongoDBMetaData_AvgObjectSize();

		/**
		 * The meta object literal for the '<em><b>Store Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MONGO_DB_META_DATA__STORE_SIZE = eINSTANCE.getMongoDBMetaData_StoreSize();

		/**
		 * The meta object literal for the '<em><b>Server</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MONGO_DB_META_DATA__SERVER = eINSTANCE.getMongoDBMetaData_Server();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DirectoryElementImpl <em>Directory Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.DirectoryElementImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getDirectoryElement()
		 * @generated
		 */
		EClass DIRECTORY_ELEMENT = eINSTANCE.getDirectoryElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIRECTORY_ELEMENT__NAME = eINSTANCE.getDirectoryElement_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIRECTORY_ELEMENT__DESCRIPTION = eINSTANCE.getDirectoryElement_Description();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIRECTORY_ELEMENT__URL = eINSTANCE.getDirectoryElement_Url();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl <em>Import Meta Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.ImportMetaDataImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getImportMetaData()
		 * @generated
		 */
		EClass IMPORT_META_DATA = eINSTANCE.getImportMetaData();

		/**
		 * The meta object literal for the '<em><b>Scheduled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_META_DATA__SCHEDULED = eINSTANCE.getImportMetaData_Scheduled();

		/**
		 * The meta object literal for the '<em><b>Importing</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_META_DATA__IMPORTING = eINSTANCE.getImportMetaData_Importing();

		/**
		 * The meta object literal for the '<em><b>Imported</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_META_DATA__IMPORTED = eINSTANCE.getImportMetaData_Imported();

		/**
		 * The meta object literal for the '<em><b>Working Copy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_META_DATA__WORKING_COPY = eINSTANCE.getImportMetaData_WorkingCopy();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryElementImpl <em>Repository Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryElementImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryElement()
		 * @generated
		 */
		EClass REPOSITORY_ELEMENT = eINSTANCE.getRepositoryElement();

		/**
		 * The meta object literal for the '<em><b>Data Sets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_ELEMENT__DATA_SETS = eINSTANCE.getRepositoryElement_DataSets();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DataSetImpl <em>Data Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.DataSetImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getDataSet()
		 * @generated
		 */
		EClass DATA_SET = eINSTANCE.getDataSet();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_SET__NAME = eINSTANCE.getDataSet_Name();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_SET__DATA = eINSTANCE.getDataSet_Data();

		/**
		 * The meta object literal for the '<em><b>Json Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_SET__JSON_DATA = eINSTANCE.getDataSet_JsonData();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TaskDataImpl <em>Task Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.TaskDataImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getTaskData()
		 * @generated
		 */
		EClass TASK_DATA = eINSTANCE.getTaskData();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_DATA__DATE = eINSTANCE.getTaskData_Date();

		/**
		 * The meta object literal for the '<em><b>Stats As JSON</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_DATA__STATS_AS_JSON = eINSTANCE.getTaskData_StatsAsJSON();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_DATA__DESCRIPTION = eINSTANCE.getTaskData_Description();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.UnresolvedLinkImpl <em>Unresolved Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.UnresolvedLinkImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getUnresolvedLink()
		 * @generated
		 */
		EClass UNRESOLVED_LINK = eINSTANCE.getUnresolvedLink();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNRESOLVED_LINK__SOURCE = eINSTANCE.getUnresolvedLink_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNRESOLVED_LINK__TARGET = eINSTANCE.getUnresolvedLink_Target();

		/**
		 * The meta object literal for the '<em><b>Feature ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNRESOLVED_LINK__FEATURE_ID = eINSTANCE.getUnresolvedLink_FeatureID();

		/**
		 * The meta object literal for the '<em><b>Feature Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNRESOLVED_LINK__FEATURE_INDEX = eINSTANCE.getUnresolvedLink_FeatureIndex();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNRESOLVED_LINK__ID = eINSTANCE.getUnresolvedLink_Id();

		/**
		 * The meta object literal for the '<em><b>Cu Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__CU_COUNT = eINSTANCE.getRepositoryMetaData_CuCount();

		/**
		 * The meta object literal for the '<em>Change Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jgit.diff.DiffEntry.ChangeType
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getChangeType()
		 * @generated
		 */
		EDataType CHANGE_TYPE = eINSTANCE.getChangeType();

		/**
		 * The meta object literal for the '<em>EData Map</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Map
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getEDataMap()
		 * @generated
		 */
		EDataType EDATA_MAP = eINSTANCE.getEDataMap();

	}

} //RepositoryModelPackage
