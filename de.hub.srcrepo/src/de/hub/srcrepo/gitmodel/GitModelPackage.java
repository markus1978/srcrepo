/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import de.hub.srcrepo.emffrag.EmfFragPackage;

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
 * @see de.hub.srcrepo.gitmodel.GitModelFactory
 * @model kind="package"
 * @generated
 */
public interface GitModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "gitmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://gitmodel/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "gitmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GitModelPackage eINSTANCE = de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.gitmodel.impl.SourceRepositoryImpl <em>Source Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.gitmodel.impl.SourceRepositoryImpl
	 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getSourceRepository()
	 * @generated
	 */
	int SOURCE_REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>First Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REPOSITORY__FIRST_KEY = EmfFragPackage.INDEXED_MAP__FIRST_KEY;

	/**
	 * The feature id for the '<em><b>Last Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REPOSITORY__LAST_KEY = EmfFragPackage.INDEXED_MAP__LAST_KEY;

	/**
	 * The feature id for the '<em><b>All Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REPOSITORY__ALL_REFS = EmfFragPackage.INDEXED_MAP_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>All Commits</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REPOSITORY__ALL_COMMITS = EmfFragPackage.INDEXED_MAP_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Java Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REPOSITORY__JAVA_MODEL = EmfFragPackage.INDEXED_MAP_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Root Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REPOSITORY__ROOT_COMMIT = EmfFragPackage.INDEXED_MAP_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Source Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_REPOSITORY_FEATURE_COUNT = EmfFragPackage.INDEXED_MAP_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.gitmodel.impl.CommitImpl <em>Commit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.gitmodel.impl.CommitImpl
	 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getCommit()
	 * @generated
	 */
	int COMMIT = 1;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__AUTHOR = 0;

	/**
	 * The feature id for the '<em><b>Commiter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__COMMITER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__NAME = 2;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__TIME = 3;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__MESSAGE = 4;

	/**
	 * The feature id for the '<em><b>Parent Relations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__PARENT_RELATIONS = 5;

	/**
	 * The feature id for the '<em><b>Child Relations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT__CHILD_RELATIONS = 6;

	/**
	 * The number of structural features of the '<em>Commit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMIT_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.gitmodel.impl.RefImpl <em>Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.gitmodel.impl.RefImpl
	 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getRef()
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
	 * The meta object id for the '{@link de.hub.srcrepo.gitmodel.impl.DiffImpl <em>Diff</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.gitmodel.impl.DiffImpl
	 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getDiff()
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
	 * The number of structural features of the '<em>Diff</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIFF_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.gitmodel.impl.JavaDiffImpl <em>Java Diff</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.gitmodel.impl.JavaDiffImpl
	 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getJavaDiff()
	 * @generated
	 */
	int JAVA_DIFF = 4;

	/**
	 * The feature id for the '<em><b>New Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_DIFF__NEW_PATH = DIFF__NEW_PATH;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_DIFF__TYPE = DIFF__TYPE;

	/**
	 * The feature id for the '<em><b>Old Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_DIFF__OLD_PATH = DIFF__OLD_PATH;

	/**
	 * The feature id for the '<em><b>Compilation Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_DIFF__COMPILATION_UNIT = DIFF_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Diff</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_DIFF_FEATURE_COUNT = DIFF_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.gitmodel.impl.RevCompilationUnitImpl <em>Rev Compilation Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.gitmodel.impl.RevCompilationUnitImpl
	 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getRevCompilationUnit()
	 * @generated
	 */
	int REV_COMPILATION_UNIT = 5;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__COMMENTS = JavaPackage.COMPILATION_UNIT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Original Compilation Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__ORIGINAL_COMPILATION_UNIT = JavaPackage.COMPILATION_UNIT__ORIGINAL_COMPILATION_UNIT;

	/**
	 * The feature id for the '<em><b>Original Class File</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__ORIGINAL_CLASS_FILE = JavaPackage.COMPILATION_UNIT__ORIGINAL_CLASS_FILE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__NAME = JavaPackage.COMPILATION_UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Proxy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__PROXY = JavaPackage.COMPILATION_UNIT__PROXY;

	/**
	 * The feature id for the '<em><b>Usages In Imports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__USAGES_IN_IMPORTS = JavaPackage.COMPILATION_UNIT__USAGES_IN_IMPORTS;

	/**
	 * The feature id for the '<em><b>Original File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__ORIGINAL_FILE_PATH = JavaPackage.COMPILATION_UNIT__ORIGINAL_FILE_PATH;

	/**
	 * The feature id for the '<em><b>Comment List</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__COMMENT_LIST = JavaPackage.COMPILATION_UNIT__COMMENT_LIST;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__IMPORTS = JavaPackage.COMPILATION_UNIT__IMPORTS;

	/**
	 * The feature id for the '<em><b>Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__PACKAGE = JavaPackage.COMPILATION_UNIT__PACKAGE;

	/**
	 * The feature id for the '<em><b>Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__TYPES = JavaPackage.COMPILATION_UNIT__TYPES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__PARENT = JavaPackage.COMPILATION_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT__CHILDREN = JavaPackage.COMPILATION_UNIT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Rev Compilation Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REV_COMPILATION_UNIT_FEATURE_COUNT = JavaPackage.COMPILATION_UNIT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link de.hub.srcrepo.gitmodel.impl.ParentRelationImpl <em>Parent Relation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.hub.srcrepo.gitmodel.impl.ParentRelationImpl
	 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getParentRelation()
	 * @generated
	 */
	int PARENT_RELATION = 6;

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
	 * The meta object id for the '<em>Change Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.jgit.diff.DiffEntry.ChangeType
	 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getChangeType()
	 * @generated
	 */
	int CHANGE_TYPE = 7;

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.gitmodel.SourceRepository <em>Source Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Repository</em>'.
	 * @see de.hub.srcrepo.gitmodel.SourceRepository
	 * @generated
	 */
	EClass getSourceRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.gitmodel.SourceRepository#getAllRefs <em>All Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Refs</em>'.
	 * @see de.hub.srcrepo.gitmodel.SourceRepository#getAllRefs()
	 * @see #getSourceRepository()
	 * @generated
	 */
	EReference getSourceRepository_AllRefs();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.gitmodel.SourceRepository#getAllCommits <em>All Commits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Commits</em>'.
	 * @see de.hub.srcrepo.gitmodel.SourceRepository#getAllCommits()
	 * @see #getSourceRepository()
	 * @generated
	 */
	EReference getSourceRepository_AllCommits();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.gitmodel.SourceRepository#getJavaModel <em>Java Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Java Model</em>'.
	 * @see de.hub.srcrepo.gitmodel.SourceRepository#getJavaModel()
	 * @see #getSourceRepository()
	 * @generated
	 */
	EReference getSourceRepository_JavaModel();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.gitmodel.SourceRepository#getRootCommit <em>Root Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Root Commit</em>'.
	 * @see de.hub.srcrepo.gitmodel.SourceRepository#getRootCommit()
	 * @see #getSourceRepository()
	 * @generated
	 */
	EReference getSourceRepository_RootCommit();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.gitmodel.Commit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Commit</em>'.
	 * @see de.hub.srcrepo.gitmodel.Commit
	 * @generated
	 */
	EClass getCommit();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Commit#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see de.hub.srcrepo.gitmodel.Commit#getAuthor()
	 * @see #getCommit()
	 * @generated
	 */
	EAttribute getCommit_Author();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Commit#getCommiter <em>Commiter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Commiter</em>'.
	 * @see de.hub.srcrepo.gitmodel.Commit#getCommiter()
	 * @see #getCommit()
	 * @generated
	 */
	EAttribute getCommit_Commiter();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Commit#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.hub.srcrepo.gitmodel.Commit#getName()
	 * @see #getCommit()
	 * @generated
	 */
	EAttribute getCommit_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Commit#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see de.hub.srcrepo.gitmodel.Commit#getTime()
	 * @see #getCommit()
	 * @generated
	 */
	EAttribute getCommit_Time();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Commit#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see de.hub.srcrepo.gitmodel.Commit#getMessage()
	 * @see #getCommit()
	 * @generated
	 */
	EAttribute getCommit_Message();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.gitmodel.Commit#getParentRelations <em>Parent Relations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parent Relations</em>'.
	 * @see de.hub.srcrepo.gitmodel.Commit#getParentRelations()
	 * @see #getCommit()
	 * @generated
	 */
	EReference getCommit_ParentRelations();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.gitmodel.Commit#getChildRelations <em>Child Relations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Child Relations</em>'.
	 * @see de.hub.srcrepo.gitmodel.Commit#getChildRelations()
	 * @see #getCommit()
	 * @generated
	 */
	EReference getCommit_ChildRelations();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.gitmodel.Ref <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ref</em>'.
	 * @see de.hub.srcrepo.gitmodel.Ref
	 * @generated
	 */
	EClass getRef();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.gitmodel.Ref#getReferencedCommit <em>Referenced Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Referenced Commit</em>'.
	 * @see de.hub.srcrepo.gitmodel.Ref#getReferencedCommit()
	 * @see #getRef()
	 * @generated
	 */
	EReference getRef_ReferencedCommit();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Ref#isIsPeeled <em>Is Peeled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Peeled</em>'.
	 * @see de.hub.srcrepo.gitmodel.Ref#isIsPeeled()
	 * @see #getRef()
	 * @generated
	 */
	EAttribute getRef_IsPeeled();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Ref#isIsSymbolic <em>Is Symbolic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Symbolic</em>'.
	 * @see de.hub.srcrepo.gitmodel.Ref#isIsSymbolic()
	 * @see #getRef()
	 * @generated
	 */
	EAttribute getRef_IsSymbolic();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Ref#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.hub.srcrepo.gitmodel.Ref#getName()
	 * @see #getRef()
	 * @generated
	 */
	EAttribute getRef_Name();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.gitmodel.Diff <em>Diff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diff</em>'.
	 * @see de.hub.srcrepo.gitmodel.Diff
	 * @generated
	 */
	EClass getDiff();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Diff#getNewPath <em>New Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Path</em>'.
	 * @see de.hub.srcrepo.gitmodel.Diff#getNewPath()
	 * @see #getDiff()
	 * @generated
	 */
	EAttribute getDiff_NewPath();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Diff#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.hub.srcrepo.gitmodel.Diff#getType()
	 * @see #getDiff()
	 * @generated
	 */
	EAttribute getDiff_Type();

	/**
	 * Returns the meta object for the attribute '{@link de.hub.srcrepo.gitmodel.Diff#getOldPath <em>Old Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Path</em>'.
	 * @see de.hub.srcrepo.gitmodel.Diff#getOldPath()
	 * @see #getDiff()
	 * @generated
	 */
	EAttribute getDiff_OldPath();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.gitmodel.JavaDiff <em>Java Diff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Diff</em>'.
	 * @see de.hub.srcrepo.gitmodel.JavaDiff
	 * @generated
	 */
	EClass getJavaDiff();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.gitmodel.JavaDiff#getCompilationUnit <em>Compilation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Compilation Unit</em>'.
	 * @see de.hub.srcrepo.gitmodel.JavaDiff#getCompilationUnit()
	 * @see #getJavaDiff()
	 * @generated
	 */
	EReference getJavaDiff_CompilationUnit();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.gitmodel.RevCompilationUnit <em>Rev Compilation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rev Compilation Unit</em>'.
	 * @see de.hub.srcrepo.gitmodel.RevCompilationUnit
	 * @generated
	 */
	EClass getRevCompilationUnit();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.gitmodel.RevCompilationUnit#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parent</em>'.
	 * @see de.hub.srcrepo.gitmodel.RevCompilationUnit#getParent()
	 * @see #getRevCompilationUnit()
	 * @generated
	 */
	EReference getRevCompilationUnit_Parent();

	/**
	 * Returns the meta object for the reference list '{@link de.hub.srcrepo.gitmodel.RevCompilationUnit#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Children</em>'.
	 * @see de.hub.srcrepo.gitmodel.RevCompilationUnit#getChildren()
	 * @see #getRevCompilationUnit()
	 * @generated
	 */
	EReference getRevCompilationUnit_Children();

	/**
	 * Returns the meta object for class '{@link de.hub.srcrepo.gitmodel.ParentRelation <em>Parent Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parent Relation</em>'.
	 * @see de.hub.srcrepo.gitmodel.ParentRelation
	 * @generated
	 */
	EClass getParentRelation();

	/**
	 * Returns the meta object for the containment reference list '{@link de.hub.srcrepo.gitmodel.ParentRelation#getDiffs <em>Diffs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Diffs</em>'.
	 * @see de.hub.srcrepo.gitmodel.ParentRelation#getDiffs()
	 * @see #getParentRelation()
	 * @generated
	 */
	EReference getParentRelation_Diffs();

	/**
	 * Returns the meta object for the reference '{@link de.hub.srcrepo.gitmodel.ParentRelation#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see de.hub.srcrepo.gitmodel.ParentRelation#getParent()
	 * @see #getParentRelation()
	 * @generated
	 */
	EReference getParentRelation_Parent();

	/**
	 * Returns the meta object for the container reference '{@link de.hub.srcrepo.gitmodel.ParentRelation#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Child</em>'.
	 * @see de.hub.srcrepo.gitmodel.ParentRelation#getChild()
	 * @see #getParentRelation()
	 * @generated
	 */
	EReference getParentRelation_Child();

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
	GitModelFactory getGitModelFactory();

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
		 * The meta object literal for the '{@link de.hub.srcrepo.gitmodel.impl.SourceRepositoryImpl <em>Source Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.gitmodel.impl.SourceRepositoryImpl
		 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getSourceRepository()
		 * @generated
		 */
		EClass SOURCE_REPOSITORY = eINSTANCE.getSourceRepository();

		/**
		 * The meta object literal for the '<em><b>All Refs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_REPOSITORY__ALL_REFS = eINSTANCE.getSourceRepository_AllRefs();

		/**
		 * The meta object literal for the '<em><b>All Commits</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_REPOSITORY__ALL_COMMITS = eINSTANCE.getSourceRepository_AllCommits();

		/**
		 * The meta object literal for the '<em><b>Java Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_REPOSITORY__JAVA_MODEL = eINSTANCE.getSourceRepository_JavaModel();

		/**
		 * The meta object literal for the '<em><b>Root Commit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_REPOSITORY__ROOT_COMMIT = eINSTANCE.getSourceRepository_RootCommit();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.gitmodel.impl.CommitImpl <em>Commit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.gitmodel.impl.CommitImpl
		 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getCommit()
		 * @generated
		 */
		EClass COMMIT = eINSTANCE.getCommit();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT__AUTHOR = eINSTANCE.getCommit_Author();

		/**
		 * The meta object literal for the '<em><b>Commiter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT__COMMITER = eINSTANCE.getCommit_Commiter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT__NAME = eINSTANCE.getCommit_Name();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT__TIME = eINSTANCE.getCommit_Time();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMIT__MESSAGE = eINSTANCE.getCommit_Message();

		/**
		 * The meta object literal for the '<em><b>Parent Relations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT__PARENT_RELATIONS = eINSTANCE.getCommit_ParentRelations();

		/**
		 * The meta object literal for the '<em><b>Child Relations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMIT__CHILD_RELATIONS = eINSTANCE.getCommit_ChildRelations();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.gitmodel.impl.RefImpl <em>Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.gitmodel.impl.RefImpl
		 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getRef()
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
		 * The meta object literal for the '{@link de.hub.srcrepo.gitmodel.impl.DiffImpl <em>Diff</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.gitmodel.impl.DiffImpl
		 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getDiff()
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
		 * The meta object literal for the '{@link de.hub.srcrepo.gitmodel.impl.JavaDiffImpl <em>Java Diff</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.gitmodel.impl.JavaDiffImpl
		 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getJavaDiff()
		 * @generated
		 */
		EClass JAVA_DIFF = eINSTANCE.getJavaDiff();

		/**
		 * The meta object literal for the '<em><b>Compilation Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JAVA_DIFF__COMPILATION_UNIT = eINSTANCE.getJavaDiff_CompilationUnit();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.gitmodel.impl.RevCompilationUnitImpl <em>Rev Compilation Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.gitmodel.impl.RevCompilationUnitImpl
		 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getRevCompilationUnit()
		 * @generated
		 */
		EClass REV_COMPILATION_UNIT = eINSTANCE.getRevCompilationUnit();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REV_COMPILATION_UNIT__PARENT = eINSTANCE.getRevCompilationUnit_Parent();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REV_COMPILATION_UNIT__CHILDREN = eINSTANCE.getRevCompilationUnit_Children();

		/**
		 * The meta object literal for the '{@link de.hub.srcrepo.gitmodel.impl.ParentRelationImpl <em>Parent Relation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.hub.srcrepo.gitmodel.impl.ParentRelationImpl
		 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getParentRelation()
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
		 * The meta object literal for the '<em>Change Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.jgit.diff.DiffEntry.ChangeType
		 * @see de.hub.srcrepo.gitmodel.impl.GitModelPackageImpl#getChangeType()
		 * @generated
		 */
		EDataType CHANGE_TYPE = eINSTANCE.getChangeType();

	}

} //GitModelPackage
