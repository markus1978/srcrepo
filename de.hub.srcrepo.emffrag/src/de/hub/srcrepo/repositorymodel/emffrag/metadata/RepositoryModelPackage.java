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
	String eNS_URI = "http://hub.sam.repositorymodel/1.0";

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
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl <em>Repository Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryModel()
	 * @generated
	 */
	int REPOSITORY_MODEL = 0;

	/**
	 * The feature id for the '<em><b>All Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__ALL_REFS = 0;

	/**
	 * The feature id for the '<em><b>All Revs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__ALL_REVS = 1;

	/**
	 * The feature id for the '<em><b>Root Revs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__ROOT_REVS = 2;

	/**
	 * The feature id for the '<em><b>Traversals</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__TRAVERSALS = 3;

	/**
	 * The feature id for the '<em><b>Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__META_DATA = 4;

	/**
	 * The number of structural features of the '<em>Repository Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Repository Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_OPERATION_COUNT = 0;

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
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__AUTHOR = 0;

	/**
	 * The feature id for the '<em><b>Commiter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__COMMITER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__NAME = 2;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__TIME = 3;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__MESSAGE = 4;

	/**
	 * The feature id for the '<em><b>Parent Relations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__PARENT_RELATIONS = 5;

	/**
	 * The feature id for the '<em><b>Child Relations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__CHILD_RELATIONS = 6;

	/**
	 * The feature id for the '<em><b>Import Errors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV__IMPORT_ERRORS = 7;

	/**
	 * The number of structural features of the '<em>Rev</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Rev</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_OPERATION_COUNT = 0;

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
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILE_REF__PATH = 0;

	/**
	 * The number of structural features of the '<em>Abstract File Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILE_REF_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Abstract File Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILE_REF_OPERATION_COUNT = 0;

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
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalStateImpl <em>Traversal State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalStateImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getTraversalState()
	 * @generated
	 */
	int TRAVERSAL_STATE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STATE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Merges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STATE__MERGES = 1;

	/**
	 * The feature id for the '<em><b>Open Branches</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STATE__OPEN_BRANCHES = 2;

	/**
	 * The feature id for the '<em><b>Completed Branches</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STATE__COMPLETED_BRANCHES = 3;

	/**
	 * The feature id for the '<em><b>Number Of Imported Revs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STATE__NUMBER_OF_IMPORTED_REVS = 4;

	/**
	 * The number of structural features of the '<em>Traversal State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STATE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Traversal State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.PendingElementImpl <em>Pending Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.PendingElementImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getPendingElement()
	 * @generated
	 */
	int PENDING_ELEMENT = 8;

	/**
	 * The feature id for the '<em><b>Client Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PENDING_ELEMENT__CLIENT_NODE = 0;

	/**
	 * The feature id for the '<em><b>Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PENDING_ELEMENT__BINDING = 1;

	/**
	 * The feature id for the '<em><b>Link Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PENDING_ELEMENT__LINK_NAME = 2;

	/**
	 * The number of structural features of the '<em>Pending Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PENDING_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Pending Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PENDING_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TargetImpl <em>Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.TargetImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getTarget()
	 * @generated
	 */
	int TARGET = 9;

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
	int COMPILATION_UNIT_MODEL = 10;

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
	 * The feature id for the '<em><b>Pendings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL__PENDINGS = 2;

	/**
	 * The feature id for the '<em><b>Targets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL__TARGETS = 3;

	/**
	 * The number of structural features of the '<em>Compilation Unit Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILATION_UNIT_MODEL_FEATURE_COUNT = 4;

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
	int IMPORT_ERROR = 11;

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
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl <em>Repository Meta Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryMetaDataImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryMetaData()
	 * @generated
	 */
	int REPOSITORY_META_DATA = 12;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__ORIGIN = 0;

	/**
	 * The feature id for the '<em><b>Oldest Rev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__OLDEST_REV = 1;

	/**
	 * The feature id for the '<em><b>Rev Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__REV_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Import Stats</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__IMPORT_STATS = 3;

	/**
	 * The feature id for the '<em><b>Import Stats As JSON</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__IMPORT_STATS_AS_JSON = 4;

	/**
	 * The feature id for the '<em><b>Newest Rev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__NEWEST_REV = 5;

	/**
	 * The feature id for the '<em><b>Import Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__IMPORT_DATE = 6;

	/**
	 * The feature id for the '<em><b>Cu Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__CU_COUNT = 7;

	/**
	 * The feature id for the '<em><b>Object Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__OBJECT_COUNT = 8;

	/**
	 * The feature id for the '<em><b>Raw Byte Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__RAW_BYTE_SIZE = 9;

	/**
	 * The feature id for the '<em><b>Working Copy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__WORKING_COPY = 10;

	/**
	 * The feature id for the '<em><b>Revs With Errors</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__REVS_WITH_ERRORS = 11;

	/**
	 * The feature id for the '<em><b>Data Store Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA__DATA_STORE_META_DATA = 12;

	/**
	 * The number of structural features of the '<em>Repository Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA_FEATURE_COUNT = 13;

	/**
	 * The number of operations of the '<em>Repository Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_META_DATA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl <em>Directory</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelDirectoryImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getRepositoryModelDirectory()
	 * @generated
	 */
	int REPOSITORY_MODEL_DIRECTORY = 13;

	/**
	 * The feature id for the '<em><b>Sub Directories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__SUB_DIRECTORIES = 0;

	/**
	 * The feature id for the '<em><b>Repository Models</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__REPOSITORY_MODELS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__NAME = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY__URL = 4;

	/**
	 * The number of structural features of the '<em>Directory</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Directory</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_DIRECTORY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DataStoreMetaDataImpl <em>Data Store Meta Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.DataStoreMetaDataImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getDataStoreMetaData()
	 * @generated
	 */
	int DATA_STORE_META_DATA = 14;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA__COUNT = 0;

	/**
	 * The number of structural features of the '<em>Data Store Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Data Store Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STORE_META_DATA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl <em>Mongo DB Meta Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.MongoDBMetaDataImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getMongoDBMetaData()
	 * @generated
	 */
	int MONGO_DB_META_DATA = 15;

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
	 * The meta object id for the '<em>Change Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jgit.diff.DiffEntry.ChangeType
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getChangeType()
	 * @generated
	 */
	int CHANGE_TYPE = 16;


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
	 * Returns the meta object for the containment reference '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getTraversals <em>Traversals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Traversals</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModel#getTraversals()
	 * @see #getRepositoryModel()
	 * @generated
	 */
	EReference getRepositoryModel_Traversals();

	/**
	 * Returns the meta object for the containment reference '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getMetaData <em>Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Meta Data</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModel#getMetaData()
	 * @see #getRepositoryModel()
	 * @generated
	 */
	EReference getRepositoryModel_MetaData();

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
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.TraversalState <em>Traversal State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Traversal State</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TraversalState
	 * @generated
	 */
	EClass getTraversalState();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.TraversalState#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TraversalState#getName()
	 * @see #getTraversalState()
	 * @generated
	 */
	EAttribute getTraversalState_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.TraversalState#getNumberOfImportedRevs <em>Number Of Imported Revs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Imported Revs</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TraversalState#getNumberOfImportedRevs()
	 * @see #getTraversalState()
	 * @generated
	 */
	EAttribute getTraversalState_NumberOfImportedRevs();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.repositorymodel.TraversalState#getMerges <em>Merges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Merges</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TraversalState#getMerges()
	 * @see #getTraversalState()
	 * @generated
	 */
	EReference getTraversalState_Merges();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.repositorymodel.TraversalState#getOpenBranches <em>Open Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Open Branches</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TraversalState#getOpenBranches()
	 * @see #getTraversalState()
	 * @generated
	 */
	EReference getTraversalState_OpenBranches();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.repositorymodel.TraversalState#getCompletedBranches <em>Completed Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Completed Branches</em>'.
	 * @see de.hub.srcrepo.repositorymodel.TraversalState#getCompletedBranches()
	 * @see #getTraversalState()
	 * @generated
	 */
	EReference getTraversalState_CompletedBranches();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.PendingElement <em>Pending Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pending Element</em>'.
	 * @see de.hub.srcrepo.repositorymodel.PendingElement
	 * @generated
	 */
	EClass getPendingElement();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.PendingElement#getClientNode <em>Client Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Client Node</em>'.
	 * @see de.hub.srcrepo.repositorymodel.PendingElement#getClientNode()
	 * @see #getPendingElement()
	 * @generated
	 */
	EReference getPendingElement_ClientNode();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.PendingElement#getBinding <em>Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binding</em>'.
	 * @see de.hub.srcrepo.repositorymodel.PendingElement#getBinding()
	 * @see #getPendingElement()
	 * @generated
	 */
	EAttribute getPendingElement_Binding();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.PendingElement#getLinkName <em>Link Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Link Name</em>'.
	 * @see de.hub.srcrepo.repositorymodel.PendingElement#getLinkName()
	 * @see #getPendingElement()
	 * @generated
	 */
	EAttribute getPendingElement_LinkName();

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
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getPendings <em>Pendings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pendings</em>'.
	 * @see de.hub.srcrepo.repositorymodel.CompilationUnitModel#getPendings()
	 * @see #getCompilationUnitModel()
	 * @generated
	 */
	EReference getCompilationUnitModel_Pendings();

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
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportStats <em>Import Stats</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Import Stats</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportStats()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_ImportStats();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportStatsAsJSON <em>Import Stats As JSON</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Import Stats As JSON</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportStatsAsJSON()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_ImportStatsAsJSON();

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
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportDate <em>Import Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Import Date</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getImportDate()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_ImportDate();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getObjectCount <em>Object Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Count</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getObjectCount()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_ObjectCount();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRawByteSize <em>Raw Byte Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Raw Byte Size</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getRawByteSize()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_RawByteSize();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getWorkingCopy <em>Working Copy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Working Copy</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getWorkingCopy()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EAttribute getRepositoryMetaData_WorkingCopy();

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
	 * Returns the meta object for the containment reference '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData#getDataStoreMetaData <em>Data Store Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data Store Meta Data</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData#getDataStoreMetaData()
	 * @see #getRepositoryMetaData()
	 * @generated
	 */
	EReference getRepositoryMetaData_DataStoreMetaData();

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
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getRepositoryModels <em>Repository Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Repository Models</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getRepositoryModels()
	 * @see #getRepositoryModelDirectory()
	 * @generated
	 */
	EReference getRepositoryModelDirectory_RepositoryModels();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getName()
	 * @see #getRepositoryModelDirectory()
	 * @generated
	 */
	EAttribute getRepositoryModelDirectory_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getDescription()
	 * @see #getRepositoryModelDirectory()
	 * @generated
	 */
	EAttribute getRepositoryModelDirectory_Description();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory#getUrl()
	 * @see #getRepositoryModelDirectory()
	 * @generated
	 */
	EAttribute getRepositoryModelDirectory_Url();

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
		 * The meta object literal for the '<em><b>Traversals</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL__TRAVERSALS = eINSTANCE.getRepositoryModel_Traversals();

		/**
		 * The meta object literal for the '<em><b>Meta Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL__META_DATA = eINSTANCE.getRepositoryModel_MetaData();

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
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalStateImpl <em>Traversal State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.TraversalStateImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getTraversalState()
		 * @generated
		 */
		EClass TRAVERSAL_STATE = eINSTANCE.getTraversalState();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRAVERSAL_STATE__NAME = eINSTANCE.getTraversalState_Name();

		/**
		 * The meta object literal for the '<em><b>Number Of Imported Revs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRAVERSAL_STATE__NUMBER_OF_IMPORTED_REVS = eINSTANCE.getTraversalState_NumberOfImportedRevs();

		/**
		 * The meta object literal for the '<em><b>Merges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRAVERSAL_STATE__MERGES = eINSTANCE.getTraversalState_Merges();

		/**
		 * The meta object literal for the '<em><b>Open Branches</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRAVERSAL_STATE__OPEN_BRANCHES = eINSTANCE.getTraversalState_OpenBranches();

		/**
		 * The meta object literal for the '<em><b>Completed Branches</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRAVERSAL_STATE__COMPLETED_BRANCHES = eINSTANCE.getTraversalState_CompletedBranches();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.PendingElementImpl <em>Pending Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.PendingElementImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getPendingElement()
		 * @generated
		 */
		EClass PENDING_ELEMENT = eINSTANCE.getPendingElement();

		/**
		 * The meta object literal for the '<em><b>Client Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PENDING_ELEMENT__CLIENT_NODE = eINSTANCE.getPendingElement_ClientNode();

		/**
		 * The meta object literal for the '<em><b>Binding</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PENDING_ELEMENT__BINDING = eINSTANCE.getPendingElement_Binding();

		/**
		 * The meta object literal for the '<em><b>Link Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PENDING_ELEMENT__LINK_NAME = eINSTANCE.getPendingElement_LinkName();

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
		 * The meta object literal for the '<em><b>Pendings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILATION_UNIT_MODEL__PENDINGS = eINSTANCE.getCompilationUnitModel_Pendings();

		/**
		 * The meta object literal for the '<em><b>Targets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILATION_UNIT_MODEL__TARGETS = eINSTANCE.getCompilationUnitModel_Targets();

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
		 * The meta object literal for the '<em><b>Import Stats</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__IMPORT_STATS = eINSTANCE.getRepositoryMetaData_ImportStats();

		/**
		 * The meta object literal for the '<em><b>Import Stats As JSON</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__IMPORT_STATS_AS_JSON = eINSTANCE.getRepositoryMetaData_ImportStatsAsJSON();

		/**
		 * The meta object literal for the '<em><b>Newest Rev</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__NEWEST_REV = eINSTANCE.getRepositoryMetaData_NewestRev();

		/**
		 * The meta object literal for the '<em><b>Import Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__IMPORT_DATE = eINSTANCE.getRepositoryMetaData_ImportDate();

		/**
		 * The meta object literal for the '<em><b>Object Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__OBJECT_COUNT = eINSTANCE.getRepositoryMetaData_ObjectCount();

		/**
		 * The meta object literal for the '<em><b>Raw Byte Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__RAW_BYTE_SIZE = eINSTANCE.getRepositoryMetaData_RawByteSize();

		/**
		 * The meta object literal for the '<em><b>Working Copy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__WORKING_COPY = eINSTANCE.getRepositoryMetaData_WorkingCopy();

		/**
		 * The meta object literal for the '<em><b>Revs With Errors</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_META_DATA__REVS_WITH_ERRORS = eINSTANCE.getRepositoryMetaData_RevsWithErrors();

		/**
		 * The meta object literal for the '<em><b>Data Store Meta Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_META_DATA__DATA_STORE_META_DATA = eINSTANCE.getRepositoryMetaData_DataStoreMetaData();

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
		 * The meta object literal for the '<em><b>Sub Directories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL_DIRECTORY__SUB_DIRECTORIES = eINSTANCE.getRepositoryModelDirectory_SubDirectories();

		/**
		 * The meta object literal for the '<em><b>Repository Models</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL_DIRECTORY__REPOSITORY_MODELS = eINSTANCE.getRepositoryModelDirectory_RepositoryModels();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_MODEL_DIRECTORY__NAME = eINSTANCE.getRepositoryModelDirectory_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_MODEL_DIRECTORY__DESCRIPTION = eINSTANCE.getRepositoryModelDirectory_Description();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY_MODEL_DIRECTORY__URL = eINSTANCE.getRepositoryModelDirectory_Url();

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

	}

} //RepositoryModelPackage
