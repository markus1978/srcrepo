/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import java.io.Serializable;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;

import de.hub.srcrepo.repositorymodel.DataSet;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DataSetImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DataSetImpl#getData <em>Data</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DataSetImpl#getJsonData <em>Json Data</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DataSetImpl extends RepositoryElementImpl implements DataSet {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.DATA_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(RepositoryModelPackage.Literals.DATA_SET__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(RepositoryModelPackage.Literals.DATA_SET__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getData() {
		return (Map<String, Serializable>)eGet(RepositoryModelPackage.Literals.DATA_SET__DATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setData(Map<String, Serializable> newData) {
		eSet(RepositoryModelPackage.Literals.DATA_SET__DATA, newData);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJsonData() {
		return (String)eGet(RepositoryModelPackage.Literals.DATA_SET__JSON_DATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJsonData(String newJsonData) {
		eSet(RepositoryModelPackage.Literals.DATA_SET__JSON_DATA, newJsonData);
	}

} //DataSetImpl
