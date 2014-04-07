/**
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Romain DERVAUX (Mia-Software) - initial API and implementation
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer.internal.io.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Acts as a cache to make faster the resolution of an {@link IType} from a dot
 * based qualified name in the context of a Java project.
 */
public class TypeFinder {

	/**
	 * the resolved types.
	 */
	private final Map<String, IType> resolvedTypes = new HashMap<String, IType>();

	/**
	 * the unresolved types.
	 */
	private final List<String> unresolvedTypes = new ArrayList<String>();

	/**
	 * the java project.
	 */
	private final IJavaProject javaProject;

	public TypeFinder(final IJavaProject javaProject) {
		this.javaProject = javaProject;
	}

	/**
	 * Returns the {@link IType} corresponding to the supplied qualified name.
	 * 
	 * @param qualifiedName
	 *            the dot based qualified name ({@code java.lang.String} for
	 *            example)
	 * @return the {@code IType} corresponding to the qualified name, or
	 *         {@code null} if type is unknow (not on the classpath of the Java
	 *         Project)
	 * @see IJavaProject#findType(String)
	 */
	public IType getType(final String qualifiedName) {
		// first, check in the unresolved
		if (this.unresolvedTypes.contains(qualifiedName)) {
			return null;
		}
		// then, in the resolved
		IType type = this.resolvedTypes.get(qualifiedName);
		if (type == null) {
			try {
				// finally, ask the java project
				type = this.javaProject.findType(qualifiedName);
				if (type != null) {
					this.resolvedTypes.put(qualifiedName, type);
				} else {
					this.unresolvedTypes.add(qualifiedName);
				}
			} catch (JavaModelException e) {
				// Nothing
				assert (true); // dummy code to deactivate "EmptyBlock" rule
			}
		}
		return type;
	}

	/**
	 * Indicate if the designated type name has a corresponding {@code IType}.
	 * 
	 * @param qualifiedName
	 *            the dot based qualified name ({@code java.lang.String} for
	 *            example)
	 * @return {@code true} if this type exists, {@code false} otherwise
	 */
	public boolean isTypeExists(final String qualifiedName) {
		return getType(qualifiedName) != null;
	}

}
