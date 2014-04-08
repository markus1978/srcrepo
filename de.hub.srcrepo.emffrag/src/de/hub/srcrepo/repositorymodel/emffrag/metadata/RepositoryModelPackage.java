/**
 * <copyright>
 * </copyright>
 *
 * $Id$
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
	 * The feature id for the '<em><b>Java Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__JAVA_MODEL = 2;

	/**
	 * The feature id for the '<em><b>Root Rev</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL__ROOT_REV = 3;

	/**
	 * The number of structural features of the '<em>Repository Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_MODEL_FEATURE_COUNT = 4;

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
	 * The number of structural features of the '<em>Rev</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_FEATURE_COUNT = 7;

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
	 * The meta object id for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.AbstractFileRefImpl <em>Abstract File Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.AbstractFileRefImpl
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getAbstractFileRef()
	 * @generated
	 */
	int ABSTRACT_FILE_REF = 5;

	/**
	 * The number of structural features of the '<em>Abstract File Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_FILE_REF_FEATURE_COUNT = 0;

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
	 * The feature id for the '<em><b>Compilation Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT = ABSTRACT_FILE_REF_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Compilation Unit Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_COMPILATION_UNIT_REF_FEATURE_COUNT = ABSTRACT_FILE_REF_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '<em>Change Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jgit.diff.DiffEntry.ChangeType
	 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getChangeType()
	 * @generated
	 */
	int CHANGE_TYPE = 7;


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
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getJavaModel <em>Java Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Java Model</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModel#getJavaModel()
	 * @see #getRepositoryModel()
	 * @generated
	 */
	EReference getRepositoryModel_JavaModel();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.RepositoryModel#getRootRev <em>Root Rev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Root Rev</em>'.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModel#getRootRev()
	 * @see #getRepositoryModel()
	 * @generated
	 */
	EReference getRepositoryModel_RootRev();

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
	 * Returns the meta object for class '{@link de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef <em>Java Compilation Unit Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Compilation Unit Ref</em>'.
	 * @see de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
	 * @generated
	 */
	EClass getJavaCompilationUnitRef();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef#getCompilationUnit <em>Compilation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Compilation Unit</em>'.
	 * @see de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef#getCompilationUnit()
	 * @see #getJavaCompilationUnitRef()
	 * @generated
	 */
	EReference getJavaCompilationUnitRef_CompilationUnit();

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
		 * The meta object literal for the '<em><b>Java Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL__JAVA_MODEL = eINSTANCE.getRepositoryModel_JavaModel();

		/**
		 * The meta object literal for the '<em><b>Root Rev</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY_MODEL__ROOT_REV = eINSTANCE.getRepositoryModel_RootRev();

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
		 * The meta object literal for the '{@link de.hub.srcrepo.repositorymodel.emffrag.impl.JavaCompilationUnitRefImpl <em>Java Compilation Unit Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.JavaCompilationUnitRefImpl
		 * @see de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelPackageImpl#getJavaCompilationUnitRef()
		 * @generated
		 */
		EClass JAVA_COMPILATION_UNIT_REF = eINSTANCE.getJavaCompilationUnitRef();

		/**
		 * The meta object literal for the '<em><b>Compilation Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT = eINSTANCE.getJavaCompilationUnitRef_CompilationUnit();

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
