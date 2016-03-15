/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
 * @generated
 */
public class RepositoryModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RepositoryModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RepositoryModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryModelSwitch<Adapter> modelSwitch =
		new RepositoryModelSwitch<Adapter>() {
			@Override
			public Adapter caseRepositoryModel(RepositoryModel object) {
				return createRepositoryModelAdapter();
			}
			@Override
			public Adapter caseRev(Rev object) {
				return createRevAdapter();
			}
			@Override
			public Adapter caseRef(Ref object) {
				return createRefAdapter();
			}
			@Override
			public Adapter caseDiff(Diff object) {
				return createDiffAdapter();
			}
			@Override
			public Adapter caseParentRelation(ParentRelation object) {
				return createParentRelationAdapter();
			}
			@Override
			public Adapter caseAbstractFileRef(AbstractFileRef object) {
				return createAbstractFileRefAdapter();
			}
			@Override
			public Adapter caseJavaCompilationUnitRef(JavaCompilationUnitRef object) {
				return createJavaCompilationUnitRefAdapter();
			}
			@Override
			public Adapter caseTarget(Target object) {
				return createTargetAdapter();
			}
			@Override
			public Adapter caseCompilationUnitModel(CompilationUnitModel object) {
				return createCompilationUnitModelAdapter();
			}
			@Override
			public Adapter caseImportError(ImportError object) {
				return createImportErrorAdapter();
			}
			@Override
			public Adapter caseRepositoryMetaData(RepositoryMetaData object) {
				return createRepositoryMetaDataAdapter();
			}
			@Override
			public Adapter caseRepositoryModelDirectory(RepositoryModelDirectory object) {
				return createRepositoryModelDirectoryAdapter();
			}
			@Override
			public Adapter caseDataStoreMetaData(DataStoreMetaData object) {
				return createDataStoreMetaDataAdapter();
			}
			@Override
			public Adapter caseMongoDBMetaData(MongoDBMetaData object) {
				return createMongoDBMetaDataAdapter();
			}
			@Override
			public Adapter caseDirectoryElement(DirectoryElement object) {
				return createDirectoryElementAdapter();
			}
			@Override
			public Adapter caseImportMetaData(ImportMetaData object) {
				return createImportMetaDataAdapter();
			}
			@Override
			public Adapter caseRepositoryElement(RepositoryElement object) {
				return createRepositoryElementAdapter();
			}
			@Override
			public Adapter caseDataSet(DataSet object) {
				return createDataSetAdapter();
			}
			@Override
			public Adapter caseTaskData(TaskData object) {
				return createTaskDataAdapter();
			}
			@Override
			public Adapter caseUnresolvedLink(UnresolvedLink object) {
				return createUnresolvedLinkAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.RepositoryModel <em>Repository Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModel
	 * @generated
	 */
	public Adapter createRepositoryModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.Rev <em>Rev</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.Rev
	 * @generated
	 */
	public Adapter createRevAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.Ref <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.Ref
	 * @generated
	 */
	public Adapter createRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.Diff <em>Diff</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.Diff
	 * @generated
	 */
	public Adapter createDiffAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.ParentRelation <em>Parent Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.ParentRelation
	 * @generated
	 */
	public Adapter createParentRelationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.AbstractFileRef <em>Abstract File Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.AbstractFileRef
	 * @generated
	 */
	public Adapter createAbstractFileRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef <em>Java Compilation Unit Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
	 * @generated
	 */
	public Adapter createJavaCompilationUnitRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.Target
	 * @generated
	 */
	public Adapter createTargetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel <em>Compilation Unit Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.CompilationUnitModel
	 * @generated
	 */
	public Adapter createCompilationUnitModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.ImportError <em>Import Error</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.ImportError
	 * @generated
	 */
	public Adapter createImportErrorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.RepositoryMetaData <em>Repository Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryMetaData
	 * @generated
	 */
	public Adapter createRepositoryMetaDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.RepositoryModelDirectory <em>Directory</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
	 * @generated
	 */
	public Adapter createRepositoryModelDirectoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.DataStoreMetaData <em>Data Store Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.DataStoreMetaData
	 * @generated
	 */
	public Adapter createDataStoreMetaDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.MongoDBMetaData <em>Mongo DB Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.MongoDBMetaData
	 * @generated
	 */
	public Adapter createMongoDBMetaDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.DirectoryElement <em>Directory Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.DirectoryElement
	 * @generated
	 */
	public Adapter createDirectoryElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.ImportMetaData <em>Import Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.ImportMetaData
	 * @generated
	 */
	public Adapter createImportMetaDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.RepositoryElement <em>Repository Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.RepositoryElement
	 * @generated
	 */
	public Adapter createRepositoryElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.DataSet <em>Data Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.DataSet
	 * @generated
	 */
	public Adapter createDataSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.TaskData <em>Task Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.TaskData
	 * @generated
	 */
	public Adapter createTaskDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.repositorymodel.UnresolvedLink <em>Unresolved Link</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.repositorymodel.UnresolvedLink
	 * @generated
	 */
	public Adapter createUnresolvedLinkAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RepositoryModelAdapterFactory
