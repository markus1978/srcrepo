/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;

import de.hub.emffrag.FObjectImpl;
import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.PendingElement;
import de.hub.srcrepo.repositorymodel.Target;
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
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl#getPendings <em>Pendings</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl#getTargets <em>Targets</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.CompilationUnitModelImpl#getProxyTargets <em>Proxy Targets</em>}</li>
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
	public EList<PendingElement> getPendings() {
		return (EList<PendingElement>)eGet(RepositoryModelPackage.Literals.COMPILATION_UNIT_MODEL__PENDINGS, true);
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
	public EList<NamedElement> getProxyTargets() {
		return (EList<NamedElement>)eGet(RepositoryModelPackage.Literals.COMPILATION_UNIT_MODEL__PROXY_TARGETS, true);
	}

} //CompilationUnitModelImpl
