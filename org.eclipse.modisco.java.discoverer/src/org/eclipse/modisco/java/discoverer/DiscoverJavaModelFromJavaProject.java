/*******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sebastien Minguet (Mia-Software) - initial API and implementation
 *    Frederic Madiot (Mia-Software) - initial API and implementation
 *    Fabien Giquel (Mia-Software) - initial API and implementation
 *    Gabriel Barbier (Mia-Software) - initial API and implementation
 *    Erwan Breton (Sodifrance) - initial API and implementation
 *    Romain Dervaux (Mia-Software) - initial API and implementation
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *******************************************************************************/

package org.eclipse.modisco.java.discoverer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.modisco.infra.discovery.core.annotations.ParameterInitialValue;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;

/** Discover a Java model from a Java project and optionally its libraries. */
public class DiscoverJavaModelFromJavaProject extends
		AbstractDiscoverJavaModelFromProject<IJavaProject> {

	public static final String ID = "org.eclipse.modisco.java.discoverer.javaProject"; //$NON-NLS-1$

	public boolean isApplicableTo(final IJavaProject source) {
		return source.getProject().isAccessible();
	}

	@Override
	protected void basicDiscoverElement(final IJavaProject source, final IProgressMonitor monitor)
			throws DiscoveryException {
		analyzeJavaProject(source, monitor);
	}

	/**
	 * This initializer method is implemented in order to set the IJavaProject
	 * on the instance of ElementsToAnalyze.
	 */
	@ParameterInitialValue(name = "ELEMENTS_TO_ANALYZE")
	public static ElementsToAnalyze getElementsToAnalyzeInitialValue(final IJavaProject source) {
		if (source == null) {
			return null;
		}
		return new ElementsToAnalyze(source);
	}
}
