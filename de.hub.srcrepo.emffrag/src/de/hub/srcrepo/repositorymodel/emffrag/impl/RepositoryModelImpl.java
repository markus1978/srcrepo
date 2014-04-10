/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmt.modisco.java.Model;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.Ref;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl#getAllRefs <em>All Refs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl#getAllRevs <em>All Revs</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl#getJavaModel <em>Java Model</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl#getRootRev <em>Root Rev</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.RepositoryModelImpl#getTraversals <em>Traversals</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepositoryModelImpl extends FObjectImpl implements RepositoryModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.REPOSITORY_MODEL;
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
	@SuppressWarnings("unchecked")
	public EList<Ref> getAllRefs() {
		return (EList<Ref>)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ALL_REFS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Rev> getAllRevs() {
		return (EList<Rev>)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ALL_REVS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model getJavaModel() {
		return (Model)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__JAVA_MODEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJavaModel(Model newJavaModel) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__JAVA_MODEL, newJavaModel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rev getRootRev() {
		return (Rev)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ROOT_REV, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootRev(Rev newRootRev) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ROOT_REV, newRootRev);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TraversalState getTraversals() {
		return (TraversalState)eGet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__TRAVERSALS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTraversals(TraversalState newTraversals) {
		eSet(RepositoryModelPackage.Literals.REPOSITORY_MODEL__TRAVERSALS, newTraversals);
	}

	/**
	 * @generated NOT
	 */
	private final Map<String, Rev> revisions = new HashMap<String, Rev>();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rev getRev(String name) {
		Rev rev = revisions.get(name);
		if (rev == null) {
			revisions.clear();
			for (Rev revision: getAllRevs()) {
				revisions.put(revision.getName(), revision);				
			}
			return revisions.get(name);
		} else {				
			return rev;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void putRev(String name, Rev rev) {
		getAllRevs().add(rev);
		revisions.put(rev.getName(), rev);
	}

} //RepositoryModelImpl
