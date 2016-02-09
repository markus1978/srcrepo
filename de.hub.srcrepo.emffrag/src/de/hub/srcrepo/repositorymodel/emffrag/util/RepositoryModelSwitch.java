/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.util;

import de.hub.srcrepo.repositorymodel.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
 * @generated
 */
public class RepositoryModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RepositoryModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryModelSwitch() {
		if (modelPackage == null) {
			modelPackage = RepositoryModelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case RepositoryModelPackage.REPOSITORY_MODEL: {
				RepositoryModel repositoryModel = (RepositoryModel)theEObject;
				T result = caseRepositoryModel(repositoryModel);
				if (result == null) result = caseDirectoryElement(repositoryModel);
				if (result == null) result = caseRepositoryElement(repositoryModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.REV: {
				Rev rev = (Rev)theEObject;
				T result = caseRev(rev);
				if (result == null) result = caseRepositoryElement(rev);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.REF: {
				Ref ref = (Ref)theEObject;
				T result = caseRef(ref);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.DIFF: {
				Diff diff = (Diff)theEObject;
				T result = caseDiff(diff);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.PARENT_RELATION: {
				ParentRelation parentRelation = (ParentRelation)theEObject;
				T result = caseParentRelation(parentRelation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.ABSTRACT_FILE_REF: {
				AbstractFileRef abstractFileRef = (AbstractFileRef)theEObject;
				T result = caseAbstractFileRef(abstractFileRef);
				if (result == null) result = caseRepositoryElement(abstractFileRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF: {
				JavaCompilationUnitRef javaCompilationUnitRef = (JavaCompilationUnitRef)theEObject;
				T result = caseJavaCompilationUnitRef(javaCompilationUnitRef);
				if (result == null) result = caseAbstractFileRef(javaCompilationUnitRef);
				if (result == null) result = caseRepositoryElement(javaCompilationUnitRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.TARGET: {
				Target target = (Target)theEObject;
				T result = caseTarget(target);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.COMPILATION_UNIT_MODEL: {
				CompilationUnitModel compilationUnitModel = (CompilationUnitModel)theEObject;
				T result = caseCompilationUnitModel(compilationUnitModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.IMPORT_ERROR: {
				ImportError importError = (ImportError)theEObject;
				T result = caseImportError(importError);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.REPOSITORY_META_DATA: {
				RepositoryMetaData repositoryMetaData = (RepositoryMetaData)theEObject;
				T result = caseRepositoryMetaData(repositoryMetaData);
				if (result == null) result = caseDataSet(repositoryMetaData);
				if (result == null) result = caseRepositoryElement(repositoryMetaData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.REPOSITORY_MODEL_DIRECTORY: {
				RepositoryModelDirectory repositoryModelDirectory = (RepositoryModelDirectory)theEObject;
				T result = caseRepositoryModelDirectory(repositoryModelDirectory);
				if (result == null) result = caseDirectoryElement(repositoryModelDirectory);
				if (result == null) result = caseRepositoryElement(repositoryModelDirectory);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.DATA_STORE_META_DATA: {
				DataStoreMetaData dataStoreMetaData = (DataStoreMetaData)theEObject;
				T result = caseDataStoreMetaData(dataStoreMetaData);
				if (result == null) result = caseDataSet(dataStoreMetaData);
				if (result == null) result = caseRepositoryElement(dataStoreMetaData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.MONGO_DB_META_DATA: {
				MongoDBMetaData mongoDBMetaData = (MongoDBMetaData)theEObject;
				T result = caseMongoDBMetaData(mongoDBMetaData);
				if (result == null) result = caseDataStoreMetaData(mongoDBMetaData);
				if (result == null) result = caseDataSet(mongoDBMetaData);
				if (result == null) result = caseRepositoryElement(mongoDBMetaData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.DIRECTORY_ELEMENT: {
				DirectoryElement directoryElement = (DirectoryElement)theEObject;
				T result = caseDirectoryElement(directoryElement);
				if (result == null) result = caseRepositoryElement(directoryElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.IMPORT_META_DATA: {
				ImportMetaData importMetaData = (ImportMetaData)theEObject;
				T result = caseImportMetaData(importMetaData);
				if (result == null) result = caseTaskData(importMetaData);
				if (result == null) result = caseDataSet(importMetaData);
				if (result == null) result = caseRepositoryElement(importMetaData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.REPOSITORY_ELEMENT: {
				RepositoryElement repositoryElement = (RepositoryElement)theEObject;
				T result = caseRepositoryElement(repositoryElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.DATA_SET: {
				DataSet dataSet = (DataSet)theEObject;
				T result = caseDataSet(dataSet);
				if (result == null) result = caseRepositoryElement(dataSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.TASK_DATA: {
				TaskData taskData = (TaskData)theEObject;
				T result = caseTaskData(taskData);
				if (result == null) result = caseDataSet(taskData);
				if (result == null) result = caseRepositoryElement(taskData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.UNRESOLVED_LINK: {
				UnresolvedLink unresolvedLink = (UnresolvedLink)theEObject;
				T result = caseUnresolvedLink(unresolvedLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Repository Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Repository Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRepositoryModel(RepositoryModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rev</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rev</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRev(Rev object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRef(Ref object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Diff</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diff</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDiff(Diff object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parent Relation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parent Relation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParentRelation(ParentRelation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract File Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract File Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractFileRef(AbstractFileRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Compilation Unit Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Compilation Unit Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaCompilationUnitRef(JavaCompilationUnitRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTarget(Target object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Compilation Unit Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Compilation Unit Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompilationUnitModel(CompilationUnitModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Import Error</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Import Error</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImportError(ImportError object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Repository Meta Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Repository Meta Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRepositoryMetaData(RepositoryMetaData object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Directory</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Directory</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRepositoryModelDirectory(RepositoryModelDirectory object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Store Meta Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Store Meta Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataStoreMetaData(DataStoreMetaData object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mongo DB Meta Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mongo DB Meta Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMongoDBMetaData(MongoDBMetaData object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Directory Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Directory Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDirectoryElement(DirectoryElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Import Meta Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Import Meta Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImportMetaData(ImportMetaData object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Repository Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Repository Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRepositoryElement(RepositoryElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Data Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataSet(DataSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTaskData(TaskData object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unresolved Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unresolved Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnresolvedLink(UnresolvedLink object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //RepositoryModelSwitch
