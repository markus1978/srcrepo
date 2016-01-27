/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import org.eclipse.emf.ecore.EClass;

import de.hub.emffrag.FObjectImpl;
import de.hub.srcrepo.repositorymodel.ImportError;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Import Error</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportErrorImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportErrorImpl#isConrolled <em>Conrolled</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.ImportErrorImpl#getExceptionClassName <em>Exception Class Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImportErrorImpl extends FObjectImpl implements ImportError {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportErrorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.IMPORT_ERROR;
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
	public String getMessage() {
		return (String)eGet(RepositoryModelPackage.Literals.IMPORT_ERROR__MESSAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		eSet(RepositoryModelPackage.Literals.IMPORT_ERROR__MESSAGE, newMessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConrolled() {
		return (Boolean)eGet(RepositoryModelPackage.Literals.IMPORT_ERROR__CONROLLED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConrolled(boolean newConrolled) {
		eSet(RepositoryModelPackage.Literals.IMPORT_ERROR__CONROLLED, newConrolled);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExceptionClassName() {
		return (String)eGet(RepositoryModelPackage.Literals.IMPORT_ERROR__EXCEPTION_CLASS_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExceptionClassName(String newExceptionClassName) {
		eSet(RepositoryModelPackage.Literals.IMPORT_ERROR__EXCEPTION_CLASS_NAME, newExceptionClassName);
	}

} //ImportErrorImpl
