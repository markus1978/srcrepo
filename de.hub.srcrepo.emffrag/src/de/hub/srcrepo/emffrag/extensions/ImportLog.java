/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.emffrag.extensions;

import org.eclipse.emf.common.util.EList;

import de.hub.emffrag.model.emffrag.Extension;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Log</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.hub.srcrepo.emffrag.extensions.ImportLog#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.hub.srcrepo.emffrag.extensions.ExtensionsPackage#getImportLog()
 * @model
 * @generated
 */
public interface ImportLog extends Extension {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link de.hub.srcrepo.emffrag.extensions.ImportLogEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see de.hub.srcrepo.emffrag.extensions.ExtensionsPackage#getImportLog_Entries()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<ImportLogEntry> getEntries();

} // ImportLog
