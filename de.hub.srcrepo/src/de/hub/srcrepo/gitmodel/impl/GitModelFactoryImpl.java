/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.impl;

import de.hub.srcrepo.gitmodel.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.GitModelFactory;
import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.Ref;
import de.hub.srcrepo.gitmodel.SourceRepository;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GitModelFactoryImpl extends EFactoryImpl implements GitModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GitModelFactory init() {
		try {
			GitModelFactory theGitModelFactory = (GitModelFactory)EPackage.Registry.INSTANCE.getEFactory("http://gitmodel/1.0"); 
			if (theGitModelFactory != null) {
				return theGitModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GitModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GitModelFactoryImpl() {
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
			case GitModelPackage.SOURCE_REPOSITORY: return createSourceRepository();
			case GitModelPackage.COMMIT: return createCommit();
			case GitModelPackage.REF: return createRef();
			case GitModelPackage.DIFF: return createDiff();
			case GitModelPackage.JAVA_DIFF: return createJavaDiff();
			case GitModelPackage.REV_COMPILATION_UNIT: return createRevCompilationUnit();
			case GitModelPackage.PARENT_RELATION: return createParentRelation();
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
			case GitModelPackage.CHANGE_TYPE:
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
			case GitModelPackage.CHANGE_TYPE:
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
	public SourceRepository createSourceRepository() {
		SourceRepositoryImpl sourceRepository = new SourceRepositoryImpl();
		return sourceRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit createCommit() {
		CommitImpl commit = new CommitImpl();
		return commit;
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
	public JavaDiff createJavaDiff() {
		JavaDiffImpl javaDiff = new JavaDiffImpl();
		return javaDiff;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RevCompilationUnit createRevCompilationUnit() {
		RevCompilationUnitImpl revCompilationUnit = new RevCompilationUnitImpl();
		return revCompilationUnit;
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
	public GitModelPackage getGitModelPackage() {
		return (GitModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GitModelPackage getPackage() {
		return GitModelPackage.eINSTANCE;
	}

} //GitModelFactoryImpl
