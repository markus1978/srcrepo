/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.GitModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.DiffImpl#getNewPath <em>New Path</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.DiffImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.DiffImpl#getOldPath <em>Old Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DiffImpl extends EObjectImpl implements Diff {
	/**
	 * The default value of the '{@link #getNewPath() <em>New Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewPath()
	 * @generated
	 * @ordered
	 */
	protected static final String NEW_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNewPath() <em>New Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewPath()
	 * @generated
	 * @ordered
	 */
	protected String newPath = NEW_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final ChangeType TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ChangeType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOldPath() <em>Old Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldPath()
	 * @generated
	 * @ordered
	 */
	protected static final String OLD_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOldPath() <em>Old Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldPath()
	 * @generated
	 * @ordered
	 */
	protected String oldPath = OLD_PATH_EDEFAULT;

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
		return GitModelPackage.Literals.DIFF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNewPath() {
		return newPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewPath(String newNewPath) {
		String oldNewPath = newPath;
		newPath = newNewPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.DIFF__NEW_PATH, oldNewPath, newPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ChangeType newType) {
		ChangeType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.DIFF__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOldPath() {
		return oldPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldPath(String newOldPath) {
		String oldOldPath = oldPath;
		oldPath = newOldPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.DIFF__OLD_PATH, oldOldPath, oldPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GitModelPackage.DIFF__NEW_PATH:
				return getNewPath();
			case GitModelPackage.DIFF__TYPE:
				return getType();
			case GitModelPackage.DIFF__OLD_PATH:
				return getOldPath();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GitModelPackage.DIFF__NEW_PATH:
				setNewPath((String)newValue);
				return;
			case GitModelPackage.DIFF__TYPE:
				setType((ChangeType)newValue);
				return;
			case GitModelPackage.DIFF__OLD_PATH:
				setOldPath((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case GitModelPackage.DIFF__NEW_PATH:
				setNewPath(NEW_PATH_EDEFAULT);
				return;
			case GitModelPackage.DIFF__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case GitModelPackage.DIFF__OLD_PATH:
				setOldPath(OLD_PATH_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case GitModelPackage.DIFF__NEW_PATH:
				return NEW_PATH_EDEFAULT == null ? newPath != null : !NEW_PATH_EDEFAULT.equals(newPath);
			case GitModelPackage.DIFF__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case GitModelPackage.DIFF__OLD_PATH:
				return OLD_PATH_EDEFAULT == null ? oldPath != null : !OLD_PATH_EDEFAULT.equals(oldPath);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (newPath: ");
		result.append(newPath);
		result.append(", type: ");
		result.append(type);
		result.append(", oldPath: ");
		result.append(oldPath);
		result.append(')');
		return result.toString();
	}

} //DiffImpl
