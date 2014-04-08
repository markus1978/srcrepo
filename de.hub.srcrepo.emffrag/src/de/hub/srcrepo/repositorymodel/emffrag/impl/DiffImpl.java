/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl#getNewPath <em>New Path</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl#getOldPath <em>Old Path</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl#getFile <em>File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DiffImpl extends FObjectImpl implements Diff {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiffImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.DIFF;
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
	public String getNewPath() {
		return (String)eGet(RepositoryModelPackage.Literals.DIFF__NEW_PATH, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewPath(String newNewPath) {
		eSet(RepositoryModelPackage.Literals.DIFF__NEW_PATH, newNewPath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeType getType() {
		return (ChangeType)eGet(RepositoryModelPackage.Literals.DIFF__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ChangeType newType) {
		eSet(RepositoryModelPackage.Literals.DIFF__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOldPath() {
		return (String)eGet(RepositoryModelPackage.Literals.DIFF__OLD_PATH, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldPath(String newOldPath) {
		eSet(RepositoryModelPackage.Literals.DIFF__OLD_PATH, newOldPath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractFileRef getFile() {
		return (AbstractFileRef)eGet(RepositoryModelPackage.Literals.DIFF__FILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFile(AbstractFileRef newFile) {
		eSet(RepositoryModelPackage.Literals.DIFF__FILE, newFile);
	}

} //DiffImpl
