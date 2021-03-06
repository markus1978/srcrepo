/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import java.io.Serializable;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.DataSet;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ImportError;
import de.hub.srcrepo.repositorymodel.ImportMetaData;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.MongoDBMetaData;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Target;
import de.hub.srcrepo.repositorymodel.UnresolvedLink;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RepositoryModelFactoryImpl extends EFactoryImpl implements RepositoryModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RepositoryModelFactory init() {
		try {
			RepositoryModelFactory theRepositoryModelFactory = (RepositoryModelFactory)EPackage.Registry.INSTANCE.getEFactory(RepositoryModelPackage.eNS_URI);
			if (theRepositoryModelFactory != null) {
				return theRepositoryModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RepositoryModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case RepositoryModelPackage.REPOSITORY_MODEL: return (EObject)createRepositoryModel();
			case RepositoryModelPackage.REV: return (EObject)createRev();
			case RepositoryModelPackage.REF: return (EObject)createRef();
			case RepositoryModelPackage.DIFF: return (EObject)createDiff();
			case RepositoryModelPackage.PARENT_RELATION: return (EObject)createParentRelation();
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF: return (EObject)createJavaCompilationUnitRef();
			case RepositoryModelPackage.TARGET: return (EObject)createTarget();
			case RepositoryModelPackage.COMPILATION_UNIT_MODEL: return (EObject)createCompilationUnitModel();
			case RepositoryModelPackage.IMPORT_ERROR: return (EObject)createImportError();
			case RepositoryModelPackage.REPOSITORY_META_DATA: return (EObject)createRepositoryMetaData();
			case RepositoryModelPackage.REPOSITORY_MODEL_DIRECTORY: return (EObject)createRepositoryModelDirectory();
			case RepositoryModelPackage.MONGO_DB_META_DATA: return (EObject)createMongoDBMetaData();
			case RepositoryModelPackage.IMPORT_META_DATA: return (EObject)createImportMetaData();
			case RepositoryModelPackage.DATA_SET: return (EObject)createDataSet();
			case RepositoryModelPackage.UNRESOLVED_LINK: return (EObject)createUnresolvedLink();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case RepositoryModelPackage.CHANGE_TYPE:
				return createChangeTypeFromString(eDataType, initialValue);
			case RepositoryModelPackage.EDATA_MAP:
				return createEDataMapFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case RepositoryModelPackage.CHANGE_TYPE:
				return convertChangeTypeToString(eDataType, instanceValue);
			case RepositoryModelPackage.EDATA_MAP:
				return convertEDataMapToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryModel createRepositoryModel() {
		RepositoryModelImpl repositoryModel = new RepositoryModelImpl();
		return repositoryModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev createRev() {
		RevImpl rev = new RevImpl();
		return rev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ref createRef() {
		RefImpl ref = new RefImpl();
		return ref;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Diff createDiff() {
		DiffImpl diff = new DiffImpl();
		return diff;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParentRelation createParentRelation() {
		ParentRelationImpl parentRelation = new ParentRelationImpl();
		return parentRelation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaCompilationUnitRef createJavaCompilationUnitRef() {
		JavaCompilationUnitRefImpl javaCompilationUnitRef = new JavaCompilationUnitRefImpl();
		return javaCompilationUnitRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Target createTarget() {
		TargetImpl target = new TargetImpl();
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilationUnitModel createCompilationUnitModel() {
		CompilationUnitModelImpl compilationUnitModel = new CompilationUnitModelImpl();
		return compilationUnitModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportError createImportError() {
		ImportErrorImpl importError = new ImportErrorImpl();
		return importError;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryMetaData createRepositoryMetaData() {
		RepositoryMetaDataImpl repositoryMetaData = new RepositoryMetaDataImpl();
		return repositoryMetaData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryModelDirectory createRepositoryModelDirectory() {
		RepositoryModelDirectoryImpl repositoryModelDirectory = new RepositoryModelDirectoryImpl();
		return repositoryModelDirectory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MongoDBMetaData createMongoDBMetaData() {
		MongoDBMetaDataImpl mongoDBMetaData = new MongoDBMetaDataImpl();
		return mongoDBMetaData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportMetaData createImportMetaData() {
		ImportMetaDataImpl importMetaData = new ImportMetaDataImpl();
		return importMetaData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataSet createDataSet() {
		DataSetImpl dataSet = new DataSetImpl();
		return dataSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnresolvedLink createUnresolvedLink() {
		UnresolvedLinkImpl unresolvedLink = new UnresolvedLinkImpl();
		return unresolvedLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeType createChangeTypeFromString(EDataType eDataType, String initialValue) {
		return (ChangeType)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertChangeTypeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> createEDataMapFromString(EDataType eDataType, String initialValue) {
		return (Map<String, Serializable>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEDataMapToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryModelPackage getRepositoryModelPackage() {
		return (RepositoryModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RepositoryModelPackage getPackage() {
		return RepositoryModelPackage.eINSTANCE;
	}

} //RepositoryModelFactoryImpl
