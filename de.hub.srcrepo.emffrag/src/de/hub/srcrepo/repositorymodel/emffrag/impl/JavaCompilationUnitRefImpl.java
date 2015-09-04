/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.ecore.EClass;

import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Compilation Unit Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.JavaCompilationUnitRefImpl#getCompilationUnitModel <em>Compilation Unit Model</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JavaCompilationUnitRefImpl extends AbstractFileRefImpl implements JavaCompilationUnitRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaCompilationUnitRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.JAVA_COMPILATION_UNIT_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilationUnitModel getCompilationUnitModel() {
		return (CompilationUnitModel)eGet(RepositoryModelPackage.Literals.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompilationUnitModel(CompilationUnitModel newCompilationUnitModel) {
		eSet(RepositoryModelPackage.Literals.JAVA_COMPILATION_UNIT_REF__COMPILATION_UNIT_MODEL, newCompilationUnitModel);
	}

} //JavaCompilationUnitRefImpl
