/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import de.hub.srcrepo.repositorymodel.JavaBindings;
import de.hub.srcrepo.repositorymodel.JavaBindingsPerBranch;
import de.hub.srcrepo.repositorymodel.MoDiscoImport;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mo Disco Import</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.MoDiscoImportImpl#getBindings <em>Bindings</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.MoDiscoImportImpl#getBindingsPerBranch <em>Bindings Per Branch</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MoDiscoImportImpl extends TraversalImpl implements MoDiscoImport {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MoDiscoImportImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.MO_DISCO_IMPORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaBindings getBindings() {
		return (JavaBindings)eGet(RepositoryModelPackage.Literals.MO_DISCO_IMPORT__BINDINGS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBindings(JavaBindings newBindings) {
		eSet(RepositoryModelPackage.Literals.MO_DISCO_IMPORT__BINDINGS, newBindings);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<JavaBindingsPerBranch> getBindingsPerBranch() {
		return (EList<JavaBindingsPerBranch>)eGet(RepositoryModelPackage.Literals.MO_DISCO_IMPORT__BINDINGS_PER_BRANCH, true);
	}

} //MoDiscoImportImpl
