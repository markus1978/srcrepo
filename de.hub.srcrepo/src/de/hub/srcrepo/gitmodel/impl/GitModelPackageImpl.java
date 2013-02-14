/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.emffrag.EmfFragPackage;
import de.hub.srcrepo.emffrag.impl.EmfFragPackageImpl;
import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.GitModelFactory;
import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.JavaDiff;
import de.hub.srcrepo.gitmodel.ParentRelation;
import de.hub.srcrepo.gitmodel.Ref;
import de.hub.srcrepo.gitmodel.RevCompilationUnit;
import de.hub.srcrepo.gitmodel.SourceRepository;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GitModelPackageImpl extends EPackageImpl implements GitModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sourceRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass refEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass diffEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaDiffEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass revCompilationUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parentRelationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType changeTypeEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.hub.srcrepo.gitmodel.GitModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GitModelPackageImpl() {
		super(eNS_URI, GitModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link GitModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GitModelPackage init() {
		if (isInited) return (GitModelPackage)EPackage.Registry.INSTANCE.getEPackage(GitModelPackage.eNS_URI);

		// Obtain or create and register package
		GitModelPackageImpl theGitModelPackage = (GitModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof GitModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new GitModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		JavaPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		EmfFragPackageImpl theEmfFragPackage = (EmfFragPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EmfFragPackage.eNS_URI) instanceof EmfFragPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EmfFragPackage.eNS_URI) : EmfFragPackage.eINSTANCE);

		// Create package meta-data objects
		theGitModelPackage.createPackageContents();
		theEmfFragPackage.createPackageContents();

		// Initialize created meta-data
		theGitModelPackage.initializePackageContents();
		theEmfFragPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGitModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GitModelPackage.eNS_URI, theGitModelPackage);
		return theGitModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSourceRepository() {
		return sourceRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceRepository_AllRefs() {
		return (EReference)sourceRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceRepository_AllCommits() {
		return (EReference)sourceRepositoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceRepository_JavaModel() {
		return (EReference)sourceRepositoryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceRepository_RootCommit() {
		return (EReference)sourceRepositoryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommit() {
		return commitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommit_Author() {
		return (EAttribute)commitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommit_Commiter() {
		return (EAttribute)commitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommit_Name() {
		return (EAttribute)commitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommit_Time() {
		return (EAttribute)commitEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommit_Message() {
		return (EAttribute)commitEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommit_ParentRelations() {
		return (EReference)commitEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommit_ChildRelations() {
		return (EReference)commitEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRef() {
		return refEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRef_ReferencedCommit() {
		return (EReference)refEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRef_IsPeeled() {
		return (EAttribute)refEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRef_IsSymbolic() {
		return (EAttribute)refEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRef_Name() {
		return (EAttribute)refEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDiff() {
		return diffEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDiff_NewPath() {
		return (EAttribute)diffEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDiff_Type() {
		return (EAttribute)diffEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDiff_OldPath() {
		return (EAttribute)diffEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaDiff() {
		return javaDiffEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJavaDiff_CompilationUnit() {
		return (EReference)javaDiffEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRevCompilationUnit() {
		return revCompilationUnitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRevCompilationUnit_Parent() {
		return (EReference)revCompilationUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRevCompilationUnit_Children() {
		return (EReference)revCompilationUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParentRelation() {
		return parentRelationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParentRelation_Diffs() {
		return (EReference)parentRelationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParentRelation_Parent() {
		return (EReference)parentRelationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParentRelation_Child() {
		return (EReference)parentRelationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getChangeType() {
		return changeTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GitModelFactory getGitModelFactory() {
		return (GitModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		sourceRepositoryEClass = createEClass(SOURCE_REPOSITORY);
		createEReference(sourceRepositoryEClass, SOURCE_REPOSITORY__ALL_REFS);
		createEReference(sourceRepositoryEClass, SOURCE_REPOSITORY__ALL_COMMITS);
		createEReference(sourceRepositoryEClass, SOURCE_REPOSITORY__JAVA_MODEL);
		createEReference(sourceRepositoryEClass, SOURCE_REPOSITORY__ROOT_COMMIT);

		commitEClass = createEClass(COMMIT);
		createEAttribute(commitEClass, COMMIT__AUTHOR);
		createEAttribute(commitEClass, COMMIT__COMMITER);
		createEAttribute(commitEClass, COMMIT__NAME);
		createEAttribute(commitEClass, COMMIT__TIME);
		createEAttribute(commitEClass, COMMIT__MESSAGE);
		createEReference(commitEClass, COMMIT__PARENT_RELATIONS);
		createEReference(commitEClass, COMMIT__CHILD_RELATIONS);

		refEClass = createEClass(REF);
		createEReference(refEClass, REF__REFERENCED_COMMIT);
		createEAttribute(refEClass, REF__IS_PEELED);
		createEAttribute(refEClass, REF__IS_SYMBOLIC);
		createEAttribute(refEClass, REF__NAME);

		diffEClass = createEClass(DIFF);
		createEAttribute(diffEClass, DIFF__NEW_PATH);
		createEAttribute(diffEClass, DIFF__TYPE);
		createEAttribute(diffEClass, DIFF__OLD_PATH);

		javaDiffEClass = createEClass(JAVA_DIFF);
		createEReference(javaDiffEClass, JAVA_DIFF__COMPILATION_UNIT);

		revCompilationUnitEClass = createEClass(REV_COMPILATION_UNIT);
		createEReference(revCompilationUnitEClass, REV_COMPILATION_UNIT__PARENT);
		createEReference(revCompilationUnitEClass, REV_COMPILATION_UNIT__CHILDREN);

		parentRelationEClass = createEClass(PARENT_RELATION);
		createEReference(parentRelationEClass, PARENT_RELATION__DIFFS);
		createEReference(parentRelationEClass, PARENT_RELATION__PARENT);
		createEReference(parentRelationEClass, PARENT_RELATION__CHILD);

		// Create data types
		changeTypeEDataType = createEDataType(CHANGE_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EmfFragPackage theEmfFragPackage = (EmfFragPackage)EPackage.Registry.INSTANCE.getEPackage(EmfFragPackage.eNS_URI);
		JavaPackage theJavaPackage = (JavaPackage)EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		EGenericType g1 = createEGenericType(theEmfFragPackage.getIndexedMap());
		EGenericType g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getCommit());
		g1.getETypeArguments().add(g2);
		sourceRepositoryEClass.getEGenericSuperTypes().add(g1);
		javaDiffEClass.getESuperTypes().add(this.getDiff());
		revCompilationUnitEClass.getESuperTypes().add(theJavaPackage.getCompilationUnit());

		// Initialize classes and features; add operations and parameters
		initEClass(sourceRepositoryEClass, SourceRepository.class, "SourceRepository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSourceRepository_AllRefs(), this.getRef(), null, "allRefs", null, 0, -1, SourceRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSourceRepository_AllCommits(), this.getCommit(), null, "allCommits", null, 0, -1, SourceRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSourceRepository_JavaModel(), theJavaPackage.getModel(), null, "javaModel", null, 0, 1, SourceRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSourceRepository_RootCommit(), this.getCommit(), null, "rootCommit", null, 0, 1, SourceRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commitEClass, Commit.class, "Commit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommit_Author(), ecorePackage.getEString(), "author", null, 0, 1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommit_Commiter(), ecorePackage.getEString(), "commiter", null, 0, 1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommit_Name(), ecorePackage.getEString(), "name", null, 0, 1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommit_Time(), ecorePackage.getEDate(), "time", null, 0, 1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommit_Message(), ecorePackage.getEString(), "message", null, 0, 1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommit_ParentRelations(), this.getParentRelation(), this.getParentRelation_Child(), "parentRelations", null, 0, -1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommit_ChildRelations(), this.getParentRelation(), this.getParentRelation_Parent(), "childRelations", null, 0, -1, Commit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(refEClass, Ref.class, "Ref", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRef_ReferencedCommit(), this.getCommit(), null, "referencedCommit", null, 0, 1, Ref.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRef_IsPeeled(), ecorePackage.getEBoolean(), "isPeeled", null, 0, 1, Ref.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRef_IsSymbolic(), ecorePackage.getEBoolean(), "isSymbolic", null, 0, 1, Ref.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRef_Name(), ecorePackage.getEString(), "name", null, 0, 1, Ref.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(diffEClass, Diff.class, "Diff", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDiff_NewPath(), ecorePackage.getEString(), "newPath", null, 0, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiff_Type(), this.getChangeType(), "type", null, 0, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiff_OldPath(), ecorePackage.getEString(), "oldPath", null, 0, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(javaDiffEClass, JavaDiff.class, "JavaDiff", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJavaDiff_CompilationUnit(), theJavaPackage.getCompilationUnit(), null, "compilationUnit", null, 0, 1, JavaDiff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(revCompilationUnitEClass, RevCompilationUnit.class, "RevCompilationUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRevCompilationUnit_Parent(), this.getRevCompilationUnit(), this.getRevCompilationUnit_Children(), "parent", null, 0, -1, RevCompilationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRevCompilationUnit_Children(), this.getRevCompilationUnit(), this.getRevCompilationUnit_Parent(), "children", null, 0, -1, RevCompilationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parentRelationEClass, ParentRelation.class, "ParentRelation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParentRelation_Diffs(), this.getDiff(), null, "diffs", null, 0, -1, ParentRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParentRelation_Parent(), this.getCommit(), this.getCommit_ChildRelations(), "parent", null, 0, 1, ParentRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParentRelation_Child(), this.getCommit(), this.getCommit_ParentRelations(), "child", null, 0, 1, ParentRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(changeTypeEDataType, ChangeType.class, "ChangeType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// de.hub.emffrag
		createDeAnnotations();
	}

	/**
	 * Initializes the annotations for <b>de.hub.emffrag</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createDeAnnotations() {
		String source = "de.hub.emffrag";		
		addAnnotation
		  (getSourceRepository_AllCommits(), 
		   source, 
		   new String[] {
			 "Fragmentation", "true"
		   });		
		addAnnotation
		  (getJavaDiff_CompilationUnit(), 
		   source, 
		   new String[] {
			 "Fragmentation", "true"
		   });
	}

} //GitModelPackageImpl
