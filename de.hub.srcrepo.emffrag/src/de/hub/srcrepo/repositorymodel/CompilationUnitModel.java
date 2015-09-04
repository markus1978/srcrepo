/**
 */
package de.hub.srcrepo.repositorymodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compilation Unit Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getCompilationUnit <em>Compilation Unit</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getJavaModel <em>Java Model</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getPendings <em>Pendings</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getTargets <em>Targets</em>}</li>
 * </ul>
 *
 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getCompilationUnitModel()
 * @model
 * @generated
 */
public interface CompilationUnitModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Compilation Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compilation Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compilation Unit</em>' reference.
	 * @see #setCompilationUnit(CompilationUnit)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getCompilationUnitModel_CompilationUnit()
	 * @model
	 * @generated
	 */
	CompilationUnit getCompilationUnit();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getCompilationUnit <em>Compilation Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compilation Unit</em>' reference.
	 * @see #getCompilationUnit()
	 * @generated
	 */
	void setCompilationUnit(CompilationUnit value);

	/**
	 * Returns the value of the '<em><b>Java Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Model</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Model</em>' containment reference.
	 * @see #setJavaModel(Model)
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getCompilationUnitModel_JavaModel()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	Model getJavaModel();

	/**
	 * Sets the value of the '{@link de.hub.srcrepo.repositorymodel.CompilationUnitModel#getJavaModel <em>Java Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Model</em>' containment reference.
	 * @see #getJavaModel()
	 * @generated
	 */
	void setJavaModel(Model value);

	/**
	 * Returns the value of the '<em><b>Pendings</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.PendingElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pendings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pendings</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getCompilationUnitModel_Pendings()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<PendingElement> getPendings();

	/**
	 * Returns the value of the '<em><b>Targets</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.repositorymodel.Target}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Targets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Targets</em>' containment reference list.
	 * @see de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage#getCompilationUnitModel_Targets()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Target> getTargets();

} // CompilationUnitModel
