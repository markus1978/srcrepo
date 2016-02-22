/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.DataSet;
import de.hub.srcrepo.repositorymodel.DataStoreMetaData;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.DirectoryElement;
import de.hub.srcrepo.repositorymodel.ImportError;
import de.hub.srcrepo.repositorymodel.ImportMetaData;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.MongoDBMetaData;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.RepositoryElement;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Target;
import de.hub.srcrepo.repositorymodel.TaskData;
import de.hub.srcrepo.repositorymodel.UnresolvedLink;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RepositoryModelPackageImpl extends EPackageImpl implements RepositoryModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass repositoryModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass revEClass = null;

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
	private EClass parentRelationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractFileRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaCompilationUnitRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass targetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compilationUnitModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importErrorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass repositoryMetaDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass repositoryModelDirectoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataStoreMetaDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mongoDBMetaDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass directoryElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importMetaDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass repositoryElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass taskDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unresolvedLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType changeTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType eDataMapEDataType = null;

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
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RepositoryModelPackageImpl() {
		super(eNS_URI, RepositoryModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RepositoryModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RepositoryModelPackage init() {
		if (isInited) return (RepositoryModelPackage)EPackage.Registry.INSTANCE.getEPackage(RepositoryModelPackage.eNS_URI);

		// Obtain or create and register package
		RepositoryModelPackageImpl theRepositoryModelPackage = (RepositoryModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RepositoryModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RepositoryModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		JavaPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRepositoryModelPackage.createPackageContents();

		// Initialize created meta-data
		theRepositoryModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRepositoryModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RepositoryModelPackage.eNS_URI, theRepositoryModelPackage);
		return theRepositoryModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRepositoryModel() {
		return repositoryModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepositoryModel_AllRefs() {
		return (EReference)repositoryModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepositoryModel_AllRevs() {
		return (EReference)repositoryModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepositoryModel_RootRevs() {
		return (EReference)repositoryModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRev() {
		return revEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRev_Author() {
		return (EAttribute)revEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRev_Commiter() {
		return (EAttribute)revEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRev_Name() {
		return (EAttribute)revEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRev_Time() {
		return (EAttribute)revEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRev_Message() {
		return (EAttribute)revEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRev_ParentRelations() {
		return (EReference)revEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRev_ChildRelations() {
		return (EReference)revEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRev_ImportErrors() {
		return (EReference)revEClass.getEStructuralFeatures().get(7);
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
	public EReference getDiff_File() {
		return (EReference)diffEClass.getEStructuralFeatures().get(3);
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
	public EClass getAbstractFileRef() {
		return abstractFileRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractFileRef_Path() {
		return (EAttribute)abstractFileRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaCompilationUnitRef() {
		return javaCompilationUnitRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJavaCompilationUnitRef_CompilationUnitModel() {
		return (EReference)javaCompilationUnitRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaCompilationUnitRef_ProjectID() {
		return (EAttribute)javaCompilationUnitRefEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTarget() {
		return targetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTarget_Id() {
		return (EAttribute)targetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTarget_Target() {
		return (EReference)targetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompilationUnitModel() {
		return compilationUnitModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompilationUnitModel_CompilationUnit() {
		return (EReference)compilationUnitModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompilationUnitModel_JavaModel() {
		return (EReference)compilationUnitModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompilationUnitModel_Targets() {
		return (EReference)compilationUnitModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompilationUnitModel_UnresolvedLinks() {
		return (EReference)compilationUnitModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImportError() {
		return importErrorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportError_Message() {
		return (EAttribute)importErrorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportError_Conrolled() {
		return (EAttribute)importErrorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportError_ExceptionClassName() {
		return (EAttribute)importErrorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRepositoryMetaData() {
		return repositoryMetaDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRepositoryMetaData_Origin() {
		return (EAttribute)repositoryMetaDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRepositoryMetaData_OldestRev() {
		return (EAttribute)repositoryMetaDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRepositoryMetaData_RevCount() {
		return (EAttribute)repositoryMetaDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRepositoryMetaData_NewestRev() {
		return (EAttribute)repositoryMetaDataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRepositoryMetaData_RevsWithErrors() {
		return (EAttribute)repositoryMetaDataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRepositoryMetaData_CusWithErrors() {
		return (EAttribute)repositoryMetaDataEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRepositoryMetaData_Size() {
		return (EAttribute)repositoryMetaDataEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRepositoryModelDirectory() {
		return repositoryModelDirectoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepositoryModelDirectory_Repositories() {
		return (EReference)repositoryModelDirectoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepositoryModelDirectory_SubDirectories() {
		return (EReference)repositoryModelDirectoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepositoryModelDirectory_ScheduledForImport() {
		return (EReference)repositoryModelDirectoryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepositoryModelDirectory_Imported() {
		return (EReference)repositoryModelDirectoryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataStoreMetaData() {
		return dataStoreMetaDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataStoreMetaData_Count() {
		return (EAttribute)dataStoreMetaDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMongoDBMetaData() {
		return mongoDBMetaDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMongoDBMetaData_Ns() {
		return (EAttribute)mongoDBMetaDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMongoDBMetaData_AvgObjectSize() {
		return (EAttribute)mongoDBMetaDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMongoDBMetaData_StoreSize() {
		return (EAttribute)mongoDBMetaDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMongoDBMetaData_Server() {
		return (EAttribute)mongoDBMetaDataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDirectoryElement() {
		return directoryElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDirectoryElement_Name() {
		return (EAttribute)directoryElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDirectoryElement_Description() {
		return (EAttribute)directoryElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDirectoryElement_Url() {
		return (EAttribute)directoryElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImportMetaData() {
		return importMetaDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportMetaData_Scheduled() {
		return (EAttribute)importMetaDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportMetaData_Importing() {
		return (EAttribute)importMetaDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportMetaData_Imported() {
		return (EAttribute)importMetaDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportMetaData_WorkingCopy() {
		return (EAttribute)importMetaDataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRepositoryElement() {
		return repositoryElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepositoryElement_DataSets() {
		return (EReference)repositoryElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataSet() {
		return dataSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataSet_Name() {
		return (EAttribute)dataSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataSet_Data() {
		return (EAttribute)dataSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataSet_JsonData() {
		return (EAttribute)dataSetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTaskData() {
		return taskDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTaskData_Date() {
		return (EAttribute)taskDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTaskData_StatsAsJSON() {
		return (EAttribute)taskDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTaskData_Description() {
		return (EAttribute)taskDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnresolvedLink() {
		return unresolvedLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUnresolvedLink_Source() {
		return (EReference)unresolvedLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUnresolvedLink_Target() {
		return (EReference)unresolvedLinkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnresolvedLink_FeatureID() {
		return (EAttribute)unresolvedLinkEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnresolvedLink_FeatureIndex() {
		return (EAttribute)unresolvedLinkEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnresolvedLink_Id() {
		return (EAttribute)unresolvedLinkEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRepositoryMetaData_CuCount() {
		return (EAttribute)repositoryMetaDataEClass.getEStructuralFeatures().get(4);
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
	public EDataType getEDataMap() {
		return eDataMapEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryModelFactory getRepositoryModelFactory() {
		return (RepositoryModelFactory)getEFactoryInstance();
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
		repositoryModelEClass = createEClass(REPOSITORY_MODEL);
		createEReference(repositoryModelEClass, REPOSITORY_MODEL__ALL_REFS);
		createEReference(repositoryModelEClass, REPOSITORY_MODEL__ALL_REVS);
		createEReference(repositoryModelEClass, REPOSITORY_MODEL__ROOT_REVS);

		revEClass = createEClass(REV);
		createEAttribute(revEClass, REV__AUTHOR);
		createEAttribute(revEClass, REV__COMMITER);
		createEAttribute(revEClass, REV__NAME);
		createEAttribute(revEClass, REV__TIME);
		createEAttribute(revEClass, REV__MESSAGE);
		createEReference(revEClass, REV__PARENT_RELATIONS);
		createEReference(revEClass, REV__CHILD_RELATIONS);
		createEReference(revEClass, REV__IMPORT_ERRORS);

		refEClass = createEClass(REF);
		createEReference(refEClass, REF__REFERENCED_COMMIT);
		createEAttribute(refEClass, REF__IS_PEELED);
		createEAttribute(refEClass, REF__IS_SYMBOLIC);
		createEAttribute(refEClass, REF__NAME);

		diffEClass = createEClass(DIFF);
		createEAttribute(diffEClass, DIFF__NEW_PATH);
		createEAttribute(diffEClass, DIFF__TYPE);
		createEAttribute(diffEClass, DIFF__OLD_PATH);
		createEReference(diffEClass, DIFF__FILE);

		parentRelationEClass = createEClass(PARENT_RELATION);
		createEReference(parentRelationEClass, PARENT_RELATION__DIFFS);
		createEReference(parentRelationEClass, PARENT_RELATION__PARENT);
		createEReference(parentRelationEClass, PARENT_RELATION__CHILD);

		abstractFileRefEClass = createEClass(ABSTRACT_FILE_REF);
		createEAttribute(abstractFileRefEClass, ABSTRACT_FILE_REF__PATH);

		javaCompilationUnitRefEClass = createEClass(JAVA_COMPILATION_UNIT_REF);
		createEReference(javaCompilationUnitRefEClass, JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL);
		createEAttribute(javaCompilationUnitRefEClass, JAVA_COMPILATION_UNIT_REF__PROJECT_ID);

		targetEClass = createEClass(TARGET);
		createEAttribute(targetEClass, TARGET__ID);
		createEReference(targetEClass, TARGET__TARGET);

		compilationUnitModelEClass = createEClass(COMPILATION_UNIT_MODEL);
		createEReference(compilationUnitModelEClass, COMPILATION_UNIT_MODEL__COMPILATION_UNIT);
		createEReference(compilationUnitModelEClass, COMPILATION_UNIT_MODEL__JAVA_MODEL);
		createEReference(compilationUnitModelEClass, COMPILATION_UNIT_MODEL__TARGETS);
		createEReference(compilationUnitModelEClass, COMPILATION_UNIT_MODEL__UNRESOLVED_LINKS);

		importErrorEClass = createEClass(IMPORT_ERROR);
		createEAttribute(importErrorEClass, IMPORT_ERROR__MESSAGE);
		createEAttribute(importErrorEClass, IMPORT_ERROR__CONROLLED);
		createEAttribute(importErrorEClass, IMPORT_ERROR__EXCEPTION_CLASS_NAME);

		repositoryMetaDataEClass = createEClass(REPOSITORY_META_DATA);
		createEAttribute(repositoryMetaDataEClass, REPOSITORY_META_DATA__ORIGIN);
		createEAttribute(repositoryMetaDataEClass, REPOSITORY_META_DATA__OLDEST_REV);
		createEAttribute(repositoryMetaDataEClass, REPOSITORY_META_DATA__REV_COUNT);
		createEAttribute(repositoryMetaDataEClass, REPOSITORY_META_DATA__NEWEST_REV);
		createEAttribute(repositoryMetaDataEClass, REPOSITORY_META_DATA__CU_COUNT);
		createEAttribute(repositoryMetaDataEClass, REPOSITORY_META_DATA__REVS_WITH_ERRORS);
		createEAttribute(repositoryMetaDataEClass, REPOSITORY_META_DATA__CUS_WITH_ERRORS);
		createEAttribute(repositoryMetaDataEClass, REPOSITORY_META_DATA__SIZE);

		repositoryModelDirectoryEClass = createEClass(REPOSITORY_MODEL_DIRECTORY);
		createEReference(repositoryModelDirectoryEClass, REPOSITORY_MODEL_DIRECTORY__REPOSITORIES);
		createEReference(repositoryModelDirectoryEClass, REPOSITORY_MODEL_DIRECTORY__SUB_DIRECTORIES);
		createEReference(repositoryModelDirectoryEClass, REPOSITORY_MODEL_DIRECTORY__SCHEDULED_FOR_IMPORT);
		createEReference(repositoryModelDirectoryEClass, REPOSITORY_MODEL_DIRECTORY__IMPORTED);

		dataStoreMetaDataEClass = createEClass(DATA_STORE_META_DATA);
		createEAttribute(dataStoreMetaDataEClass, DATA_STORE_META_DATA__COUNT);

		mongoDBMetaDataEClass = createEClass(MONGO_DB_META_DATA);
		createEAttribute(mongoDBMetaDataEClass, MONGO_DB_META_DATA__NS);
		createEAttribute(mongoDBMetaDataEClass, MONGO_DB_META_DATA__AVG_OBJECT_SIZE);
		createEAttribute(mongoDBMetaDataEClass, MONGO_DB_META_DATA__STORE_SIZE);
		createEAttribute(mongoDBMetaDataEClass, MONGO_DB_META_DATA__SERVER);

		directoryElementEClass = createEClass(DIRECTORY_ELEMENT);
		createEAttribute(directoryElementEClass, DIRECTORY_ELEMENT__NAME);
		createEAttribute(directoryElementEClass, DIRECTORY_ELEMENT__DESCRIPTION);
		createEAttribute(directoryElementEClass, DIRECTORY_ELEMENT__URL);

		importMetaDataEClass = createEClass(IMPORT_META_DATA);
		createEAttribute(importMetaDataEClass, IMPORT_META_DATA__SCHEDULED);
		createEAttribute(importMetaDataEClass, IMPORT_META_DATA__IMPORTING);
		createEAttribute(importMetaDataEClass, IMPORT_META_DATA__IMPORTED);
		createEAttribute(importMetaDataEClass, IMPORT_META_DATA__WORKING_COPY);

		repositoryElementEClass = createEClass(REPOSITORY_ELEMENT);
		createEReference(repositoryElementEClass, REPOSITORY_ELEMENT__DATA_SETS);

		dataSetEClass = createEClass(DATA_SET);
		createEAttribute(dataSetEClass, DATA_SET__NAME);
		createEAttribute(dataSetEClass, DATA_SET__DATA);
		createEAttribute(dataSetEClass, DATA_SET__JSON_DATA);

		taskDataEClass = createEClass(TASK_DATA);
		createEAttribute(taskDataEClass, TASK_DATA__DATE);
		createEAttribute(taskDataEClass, TASK_DATA__STATS_AS_JSON);
		createEAttribute(taskDataEClass, TASK_DATA__DESCRIPTION);

		unresolvedLinkEClass = createEClass(UNRESOLVED_LINK);
		createEReference(unresolvedLinkEClass, UNRESOLVED_LINK__SOURCE);
		createEReference(unresolvedLinkEClass, UNRESOLVED_LINK__TARGET);
		createEAttribute(unresolvedLinkEClass, UNRESOLVED_LINK__FEATURE_ID);
		createEAttribute(unresolvedLinkEClass, UNRESOLVED_LINK__FEATURE_INDEX);
		createEAttribute(unresolvedLinkEClass, UNRESOLVED_LINK__ID);

		// Create data types
		changeTypeEDataType = createEDataType(CHANGE_TYPE);
		eDataMapEDataType = createEDataType(EDATA_MAP);
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
		JavaPackage theJavaPackage = (JavaPackage)EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		repositoryModelEClass.getESuperTypes().add(this.getDirectoryElement());
		revEClass.getESuperTypes().add(this.getRepositoryElement());
		abstractFileRefEClass.getESuperTypes().add(this.getRepositoryElement());
		javaCompilationUnitRefEClass.getESuperTypes().add(this.getAbstractFileRef());
		repositoryMetaDataEClass.getESuperTypes().add(this.getDataSet());
		repositoryModelDirectoryEClass.getESuperTypes().add(this.getDirectoryElement());
		dataStoreMetaDataEClass.getESuperTypes().add(this.getDataSet());
		mongoDBMetaDataEClass.getESuperTypes().add(this.getDataStoreMetaData());
		directoryElementEClass.getESuperTypes().add(this.getRepositoryElement());
		importMetaDataEClass.getESuperTypes().add(this.getTaskData());
		dataSetEClass.getESuperTypes().add(this.getRepositoryElement());
		taskDataEClass.getESuperTypes().add(this.getDataSet());

		// Initialize classes, features, and operations; add parameters
		initEClass(repositoryModelEClass, RepositoryModel.class, "RepositoryModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRepositoryModel_AllRefs(), this.getRef(), null, "allRefs", null, 0, -1, RepositoryModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRepositoryModel_AllRevs(), this.getRev(), null, "allRevs", null, 0, -1, RepositoryModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRepositoryModel_RootRevs(), this.getRev(), null, "rootRevs", null, 0, -1, RepositoryModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(revEClass, Rev.class, "Rev", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRev_Author(), ecorePackage.getEString(), "author", null, 0, 1, Rev.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRev_Commiter(), ecorePackage.getEString(), "commiter", null, 0, 1, Rev.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRev_Name(), ecorePackage.getEString(), "name", null, 0, 1, Rev.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRev_Time(), ecorePackage.getEDate(), "time", null, 0, 1, Rev.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRev_Message(), ecorePackage.getEString(), "message", null, 0, 1, Rev.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRev_ParentRelations(), this.getParentRelation(), this.getParentRelation_Child(), "parentRelations", null, 0, -1, Rev.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRev_ChildRelations(), this.getParentRelation(), this.getParentRelation_Parent(), "childRelations", null, 0, -1, Rev.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRev_ImportErrors(), this.getImportError(), null, "importErrors", null, 0, -1, Rev.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(refEClass, Ref.class, "Ref", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRef_ReferencedCommit(), this.getRev(), null, "referencedCommit", null, 0, 1, Ref.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRef_IsPeeled(), ecorePackage.getEBoolean(), "isPeeled", null, 0, 1, Ref.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRef_IsSymbolic(), ecorePackage.getEBoolean(), "isSymbolic", null, 0, 1, Ref.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRef_Name(), ecorePackage.getEString(), "name", null, 0, 1, Ref.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(diffEClass, Diff.class, "Diff", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDiff_NewPath(), ecorePackage.getEString(), "newPath", null, 0, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiff_Type(), this.getChangeType(), "type", null, 0, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDiff_OldPath(), ecorePackage.getEString(), "oldPath", null, 0, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiff_File(), this.getAbstractFileRef(), null, "file", null, 0, 1, Diff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parentRelationEClass, ParentRelation.class, "ParentRelation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParentRelation_Diffs(), this.getDiff(), null, "diffs", null, 0, -1, ParentRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParentRelation_Parent(), this.getRev(), this.getRev_ChildRelations(), "parent", null, 0, 1, ParentRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParentRelation_Child(), this.getRev(), this.getRev_ParentRelations(), "child", null, 0, 1, ParentRelation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractFileRefEClass, AbstractFileRef.class, "AbstractFileRef", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractFileRef_Path(), ecorePackage.getEString(), "path", null, 0, 1, AbstractFileRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(javaCompilationUnitRefEClass, JavaCompilationUnitRef.class, "JavaCompilationUnitRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJavaCompilationUnitRef_CompilationUnitModel(), this.getCompilationUnitModel(), null, "compilationUnitModel", null, 0, 1, JavaCompilationUnitRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaCompilationUnitRef_ProjectID(), ecorePackage.getEString(), "projectID", null, 0, 1, JavaCompilationUnitRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(targetEClass, Target.class, "Target", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTarget_Id(), ecorePackage.getEString(), "id", null, 0, 1, Target.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTarget_Target(), theJavaPackage.getNamedElement(), null, "target", null, 0, 1, Target.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compilationUnitModelEClass, CompilationUnitModel.class, "CompilationUnitModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompilationUnitModel_CompilationUnit(), theJavaPackage.getCompilationUnit(), null, "compilationUnit", null, 0, 1, CompilationUnitModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompilationUnitModel_JavaModel(), theJavaPackage.getModel(), null, "javaModel", null, 0, 1, CompilationUnitModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompilationUnitModel_Targets(), this.getTarget(), null, "targets", null, 0, -1, CompilationUnitModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompilationUnitModel_UnresolvedLinks(), this.getUnresolvedLink(), null, "unresolvedLinks", null, 0, -1, CompilationUnitModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(importErrorEClass, ImportError.class, "ImportError", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImportError_Message(), ecorePackage.getEString(), "message", null, 0, 1, ImportError.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportError_Conrolled(), ecorePackage.getEBoolean(), "conrolled", null, 0, 1, ImportError.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportError_ExceptionClassName(), ecorePackage.getEString(), "exceptionClassName", null, 0, 1, ImportError.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(repositoryMetaDataEClass, RepositoryMetaData.class, "RepositoryMetaData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRepositoryMetaData_Origin(), ecorePackage.getEString(), "origin", null, 0, 1, RepositoryMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRepositoryMetaData_OldestRev(), ecorePackage.getEDate(), "oldestRev", null, 0, 1, RepositoryMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRepositoryMetaData_RevCount(), ecorePackage.getEInt(), "revCount", null, 0, 1, RepositoryMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRepositoryMetaData_NewestRev(), ecorePackage.getEDate(), "newestRev", null, 0, 1, RepositoryMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRepositoryMetaData_CuCount(), ecorePackage.getELong(), "cuCount", null, 0, 1, RepositoryMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRepositoryMetaData_RevsWithErrors(), ecorePackage.getEInt(), "revsWithErrors", null, 0, 1, RepositoryMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRepositoryMetaData_CusWithErrors(), ecorePackage.getEInt(), "cusWithErrors", null, 0, 1, RepositoryMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRepositoryMetaData_Size(), ecorePackage.getEInt(), "size", null, 0, 1, RepositoryMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(repositoryModelDirectoryEClass, RepositoryModelDirectory.class, "RepositoryModelDirectory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRepositoryModelDirectory_Repositories(), this.getRepositoryModel(), null, "repositories", null, 0, -1, RepositoryModelDirectory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRepositoryModelDirectory_SubDirectories(), this.getRepositoryModelDirectory(), null, "subDirectories", null, 0, -1, RepositoryModelDirectory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRepositoryModelDirectory_ScheduledForImport(), this.getRepositoryModel(), null, "scheduledForImport", null, 0, -1, RepositoryModelDirectory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRepositoryModelDirectory_Imported(), this.getRepositoryModel(), null, "imported", null, 0, -1, RepositoryModelDirectory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataStoreMetaDataEClass, DataStoreMetaData.class, "DataStoreMetaData", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDataStoreMetaData_Count(), ecorePackage.getELong(), "count", null, 0, 1, DataStoreMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mongoDBMetaDataEClass, MongoDBMetaData.class, "MongoDBMetaData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMongoDBMetaData_Ns(), ecorePackage.getEString(), "ns", null, 0, 1, MongoDBMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMongoDBMetaData_AvgObjectSize(), ecorePackage.getELong(), "avgObjectSize", null, 0, 1, MongoDBMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMongoDBMetaData_StoreSize(), ecorePackage.getELong(), "storeSize", null, 0, 1, MongoDBMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMongoDBMetaData_Server(), ecorePackage.getEString(), "server", null, 0, 1, MongoDBMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(directoryElementEClass, DirectoryElement.class, "DirectoryElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDirectoryElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, DirectoryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDirectoryElement_Description(), ecorePackage.getEString(), "description", null, 0, 1, DirectoryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDirectoryElement_Url(), ecorePackage.getEString(), "url", null, 0, 1, DirectoryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(importMetaDataEClass, ImportMetaData.class, "ImportMetaData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImportMetaData_Scheduled(), ecorePackage.getEBoolean(), "scheduled", null, 0, 1, ImportMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportMetaData_Importing(), ecorePackage.getEBoolean(), "importing", null, 0, 1, ImportMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportMetaData_Imported(), ecorePackage.getEBoolean(), "imported", null, 0, 1, ImportMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImportMetaData_WorkingCopy(), ecorePackage.getEString(), "workingCopy", null, 0, 1, ImportMetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(repositoryElementEClass, RepositoryElement.class, "RepositoryElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRepositoryElement_DataSets(), this.getDataSet(), null, "dataSets", null, 0, -1, RepositoryElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataSetEClass, DataSet.class, "DataSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDataSet_Name(), ecorePackage.getEString(), "name", null, 0, 1, DataSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataSet_Data(), this.getEDataMap(), "data", null, 0, 1, DataSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataSet_JsonData(), ecorePackage.getEString(), "jsonData", null, 0, 1, DataSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(taskDataEClass, TaskData.class, "TaskData", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTaskData_Date(), ecorePackage.getEDate(), "date", null, 0, 1, TaskData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskData_StatsAsJSON(), ecorePackage.getEString(), "statsAsJSON", null, 0, 1, TaskData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskData_Description(), ecorePackage.getEString(), "description", null, 0, 1, TaskData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unresolvedLinkEClass, UnresolvedLink.class, "UnresolvedLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUnresolvedLink_Source(), theJavaPackage.getASTNode(), null, "source", null, 0, 1, UnresolvedLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUnresolvedLink_Target(), theJavaPackage.getNamedElement(), null, "target", null, 0, 1, UnresolvedLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnresolvedLink_FeatureID(), ecorePackage.getEInt(), "featureID", null, 0, 1, UnresolvedLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnresolvedLink_FeatureIndex(), ecorePackage.getEInt(), "featureIndex", null, 0, 1, UnresolvedLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnresolvedLink_Id(), ecorePackage.getEString(), "id", null, 0, 1, UnresolvedLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(changeTypeEDataType, ChangeType.class, "ChangeType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(eDataMapEDataType, Map.class, "EDataMap", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.Map<java.lang.String, java.io.Serializable>");

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/OCL/Import
		createImportAnnotations();
		// de.hub.emffrag
		createDeAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/OCL/Import</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createImportAnnotations() {
		String source = "http://www.eclipse.org/OCL/Import";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "ecore", "http://www.eclipse.org/emf/2002/Ecore",
			 "java", "../../../plugin/org.eclipse.gmt.modisco.java/model/java.ecore#/"
		   });
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
		  (getRepositoryModel_AllRevs(), 
		   source, 
		   new String[] {
			 "fragments", "true"
		   });	
		addAnnotation
		  (getJavaCompilationUnitRef_CompilationUnitModel(), 
		   source, 
		   new String[] {
			 "fragments", "true"
		   });	
		addAnnotation
		  (getRepositoryModelDirectory_Repositories(), 
		   source, 
		   new String[] {
			 "fragments", "true"
		   });
	}

} //RepositoryModelPackageImpl
