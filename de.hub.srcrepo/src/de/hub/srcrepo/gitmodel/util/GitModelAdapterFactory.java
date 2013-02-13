/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.NamedElement;
import de.hub.srcrepo.emffrag.IndexedMap;
import de.hub.srcrepo.gitmodel.*;
import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.Ref;
import de.hub.srcrepo.gitmodel.SourceRepository;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.hub.srcrepo.gitmodel.GitModelPackage
 * @generated
 */
public class GitModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GitModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GitModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = GitModelPackage.eINSTANCE;
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
	protected GitModelSwitch<Adapter> modelSwitch =
		new GitModelSwitch<Adapter>() {
			@Override
			public Adapter caseSourceRepository(SourceRepository object) {
				return createSourceRepositoryAdapter();
			}
			@Override
			public Adapter caseCommit(Commit object) {
				return createCommitAdapter();
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
			public Adapter caseJavaDiff(JavaDiff object) {
				return createJavaDiffAdapter();
			}
			@Override
			public Adapter caseRevCompilationUnit(RevCompilationUnit object) {
				return createRevCompilationUnitAdapter();
			}
			@Override
			public Adapter caseParentRelation(ParentRelation object) {
				return createParentRelationAdapter();
			}
			@Override
			public <K, V> Adapter caseIndexedMap(IndexedMap<K, V> object) {
				return createIndexedMapAdapter();
			}
			@Override
			public Adapter caseASTNode(ASTNode object) {
				return createASTNodeAdapter();
			}
			@Override
			public Adapter caseNamedElement(NamedElement object) {
				return createNamedElementAdapter();
			}
			@Override
			public Adapter caseCompilationUnit(CompilationUnit object) {
				return createCompilationUnitAdapter();
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
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.gitmodel.SourceRepository <em>Source Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.gitmodel.SourceRepository
	 * @generated
	 */
	public Adapter createSourceRepositoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.gitmodel.Commit <em>Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.gitmodel.Commit
	 * @generated
	 */
	public Adapter createCommitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.gitmodel.Ref <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.gitmodel.Ref
	 * @generated
	 */
	public Adapter createRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.gitmodel.Diff <em>Diff</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.gitmodel.Diff
	 * @generated
	 */
	public Adapter createDiffAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.gitmodel.JavaDiff <em>Java Diff</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.gitmodel.JavaDiff
	 * @generated
	 */
	public Adapter createJavaDiffAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.gitmodel.RevCompilationUnit <em>Rev Compilation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.gitmodel.RevCompilationUnit
	 * @generated
	 */
	public Adapter createRevCompilationUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.gitmodel.ParentRelation <em>Parent Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.gitmodel.ParentRelation
	 * @generated
	 */
	public Adapter createParentRelationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.hub.srcrepo.emffrag.IndexedMap <em>Indexed Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.hub.srcrepo.emffrag.IndexedMap
	 * @generated
	 */
	public Adapter createIndexedMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.gmt.modisco.java.ASTNode <em>AST Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.gmt.modisco.java.ASTNode
	 * @generated
	 */
	public Adapter createASTNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.gmt.modisco.java.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.gmt.modisco.java.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.gmt.modisco.java.CompilationUnit <em>Compilation Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.gmt.modisco.java.CompilationUnit
	 * @generated
	 */
	public Adapter createCompilationUnitAdapter() {
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

} //GitModelAdapterFactory
