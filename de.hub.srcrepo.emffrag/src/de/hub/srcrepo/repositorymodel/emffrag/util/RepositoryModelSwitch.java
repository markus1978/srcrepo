/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.emffrag.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.JavaBindings;
import de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.MoDiscoImportState;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;
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
	 * @parameter ePackage the package in question.
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
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.REV: {
				Rev rev = (Rev)theEObject;
				T result = caseRev(rev);
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
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.JAVA_COMPILATION_UNIT_REF: {
				JavaCompilationUnitRef javaCompilationUnitRef = (JavaCompilationUnitRef)theEObject;
				T result = caseJavaCompilationUnitRef(javaCompilationUnitRef);
				if (result == null) result = caseAbstractFileRef(javaCompilationUnitRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.TRAVERSAL_STATE: {
				TraversalState traversalState = (TraversalState)theEObject;
				T result = caseTraversalState(traversalState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.MO_DISCO_IMPORT_STATE: {
				MoDiscoImportState moDiscoImportState = (MoDiscoImportState)theEObject;
				T result = caseMoDiscoImportState(moDiscoImportState);
				if (result == null) result = caseTraversalState(moDiscoImportState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.JAVA_BINDINGS: {
				JavaBindings javaBindings = (JavaBindings)theEObject;
				T result = caseJavaBindings(javaBindings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RepositoryModelPackage.JAVA_BINDINGS_PER_BRANCH: {
				JavaBindingsPerBranch javaBindingsPerBranch = (JavaBindingsPerBranch)theEObject;
				T result = caseJavaBindingsPerBranch(javaBindingsPerBranch);
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
	 * Returns the result of interpreting the object as an instance of '<em>Traversal State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Traversal State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTraversalState(TraversalState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mo Disco Import State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mo Disco Import State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMoDiscoImportState(MoDiscoImportState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Bindings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Bindings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaBindings(JavaBindings object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Bindings Per Branch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Bindings Per Branch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaBindingsPerBranch(JavaBindingsPerBranch object) {
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
