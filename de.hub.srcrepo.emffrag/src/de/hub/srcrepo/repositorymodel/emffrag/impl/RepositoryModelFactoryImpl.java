/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import de.hub.srcrepo.repositorymodel.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ImportError;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.PendingElement;
import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Target;
import de.hub.srcrepo.repositorymodel.TraversalState;
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
			case RepositoryModelPackage.REPOSITORY_MODEL: return createRepositoryModel();
			case RepositoryModelPackage.REV: return createRev();
			case RepositoryModelPackage.REF: return createRef();
			case RepositoryModelPackage.DIFF: return createDiff();
			case RepositoryModelPackage.PARENT_RELATION: return createParentRelation();
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF: return createJavaCompilationUnitRef();
			case RepositoryModelPackage.TRAVERSAL_STATE: return createTraversalState();
			case RepositoryModelPackage.PENDING_ELEMENT: return createPendingElement();
			case RepositoryModelPackage.TARGET: return createTarget();
			case RepositoryModelPackage.COMPILATION_UNIT_MODEL: return createCompilationUnitModel();
			case RepositoryModelPackage.IMPORT_ERROR: return createImportError();
			case RepositoryModelPackage.REPOSITORY_META_DATA: return createRepositoryMetaData();
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
	public TraversalState createTraversalState() {
		TraversalStateImpl traversalState = new TraversalStateImpl();
		return traversalState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PendingElement createPendingElement() {
		PendingElementImpl pendingElement = new PendingElementImpl();
		return pendingElement;
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
