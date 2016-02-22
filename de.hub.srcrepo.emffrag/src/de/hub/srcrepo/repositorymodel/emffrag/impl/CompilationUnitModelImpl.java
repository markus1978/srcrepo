/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;

import de.hub.emffrag.FObjectImpl;
import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.Target;
import de.hub.srcrepo.repositorymodel.UnresolvedLink;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compilation Unit Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl#getCompilationUnit <em>Compilation Unit</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl#getJavaModel <em>Java Model</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl#getTargets <em>Targets</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl#getUnresolvedLinks <em>Unresolved Links</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompilationUnitModelImpl extends FObjectImpl implements CompilationUnitModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompilationUnitModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.COMPILATION_UNIT_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilationUnit getCompilationUnit() {
		return (CompilationUnit)eGet(RepositoryModelPackage.Literals.COMPILATION_UNIT_MODEL__COMPILATION_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompilationUnit(CompilationUnit newCompilationUnit) {
		eSet(RepositoryModelPackage.Literals.COMPILATION_UNIT_MODEL__COMPILATION_UNIT, newCompilationUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model getJavaModel() {
		return (Model)eGet(RepositoryModelPackage.Literals.COMPILATION_UNIT_MODEL__JAVA_MODEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJavaModel(Model newJavaModel) {
		eSet(RepositoryModelPackage.Literals.COMPILATION_UNIT_MODEL__JAVA_MODEL, newJavaModel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Target> getTargets() {
		return (EList<Target>)eGet(RepositoryModelPackage.Literals.COMPILATION_UNIT_MODEL__TARGETS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<UnresolvedLink> getUnresolvedLinks() {
		return (EList<UnresolvedLink>)eGet(RepositoryModelPackage.Literals.COMPILATION_UNIT_MODEL__UNRESOLVED_LINKS, true);
	}

} //CompilationUnitModelImpl
