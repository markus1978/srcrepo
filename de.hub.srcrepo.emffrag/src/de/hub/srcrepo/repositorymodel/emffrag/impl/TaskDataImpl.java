/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;

import de.hub.srcrepo.repositorymodel.TaskData;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TaskDataImpl#getDate <em>Date</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TaskDataImpl#getStatsAsJSON <em>Stats As JSON</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.TaskDataImpl#getDescription <em>Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TaskDataImpl extends DataSetImpl implements TaskData {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.TASK_DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDate() {
		return (Date)eGet(RepositoryModelPackage.Literals.TASK_DATA__DATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDate(Date newDate) {
		eSet(RepositoryModelPackage.Literals.TASK_DATA__DATE, newDate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStatsAsJSON() {
		return (String)eGet(RepositoryModelPackage.Literals.TASK_DATA__STATS_AS_JSON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatsAsJSON(String newStatsAsJSON) {
		eSet(RepositoryModelPackage.Literals.TASK_DATA__STATS_AS_JSON, newStatsAsJSON);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eGet(RepositoryModelPackage.Literals.TASK_DATA__DESCRIPTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eSet(RepositoryModelPackage.Literals.TASK_DATA__DESCRIPTION, newDescription);
	}

} //TaskDataImpl
