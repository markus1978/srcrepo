/**
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Fabien GIQUEL (Mia-Software) - initial API and implementation
 *    Romain DERVAUX (Mia-Software)
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;

/**
 * Java discovery : bean for storing elements to analyze and their associated
 * options.
 * <p>
 * Note: having a separate class allows to define graphical elements (e.g cell
 * editor) for this specific type.
 */
public class ElementsToAnalyze {

	/** The context project */
	private IJavaProject fJavaProject;

	public void setJavaProject(final IJavaProject javaProject) {
		this.fJavaProject = javaProject;
	}

	public IJavaProject getJavaProject() {
		return this.fJavaProject;
	}

	/**
	 * The artifacts to analyze. Keys may be {@link IJavaProject} or
	 * {@link IPackageFragment}. Values are option maps for the discoverer.
	 */
	private final Map<Object, Map<String, Object>> elementsAndOptions;

	public ElementsToAnalyze(final IJavaProject javaProject) {
		this.elementsAndOptions = new LinkedHashMap<Object, Map<String, Object>>();
		this.fJavaProject = javaProject;
	}

	/** @return artifacts to be analyzed. The returned Set is unmodifiable. */
	public Set<Object> getElementsToDiscover() {
		return Collections.unmodifiableSet(this.elementsAndOptions.keySet());
	}

	/**
	 * @return options for analyzing the given artifact. The returned
	 *         {@link Map} can be modified.
	 */
	public Map<String, Object> getDiscoveryOptions(final Object element) {
		return this.elementsAndOptions.get(element);
	}

	/**
	 * Adds an artifact to analyze.
	 * 
	 * @param element
	 *            a {@link IJavaProject} or a {@link IPackageFragment}
	 */
	public void addElementToDiscover(final Object element) {
		if (!this.elementsAndOptions.containsKey(element)) {
			this.elementsAndOptions.put(element, new HashMap<String, Object>());
		}
	}

	/** Removes an artifact to analyze. */
	public void removeElementToDiscover(final Object element) {
		this.elementsAndOptions.remove(element);
	}

	/** Clear the list of artifacts to analyze. */
	public void cleanElementsToDiscover() {
		if (this.elementsAndOptions != null) {
			this.elementsAndOptions.clear();
		}
	}

	@Override
	protected ElementsToAnalyze clone() throws CloneNotSupportedException {
		ElementsToAnalyze elementsToAnalyze = new ElementsToAnalyze(this.fJavaProject);
		elementsToAnalyze.elementsAndOptions.putAll(this.elementsAndOptions);
		return elementsToAnalyze;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (Object object : getElementsToDiscover()) {
			if (!first) {
				builder.append(", "); //$NON-NLS-1$
			}
			first = false;
			if (object instanceof IJavaProject) {
				IJavaProject javaProject = (IJavaProject) object;
				builder.append(javaProject.getElementName());
			} else if (object instanceof IPackageFragmentRoot) {
				IPackageFragmentRoot packageFragmentRoot = (IPackageFragmentRoot) object;
				builder.append(packageFragmentRoot.getElementName());
			} else {
				builder.append(object.toString());
			}
		}
		return builder.toString();
	}
}