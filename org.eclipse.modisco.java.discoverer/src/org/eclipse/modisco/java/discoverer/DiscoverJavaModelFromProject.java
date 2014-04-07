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
 *    Nicolas Bros (Mia-Software) - Bug 342548 - [Java Discovery] Illegal parameter initializer for ELEMENTS_TO_ANALYZE
 *******************************************************************************/

package org.eclipse.modisco.java.discoverer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.modisco.infra.discovery.core.annotations.ParameterInitialValue;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;

/** Discover a Java model from a project with the java nature. */
public class DiscoverJavaModelFromProject extends AbstractDiscoverJavaModelFromProject<IProject> {

	public static final String ID = "org.eclipse.modisco.java.discoverer.project"; //$NON-NLS-1$

	public boolean isApplicableTo(final IProject project) {
		try {
			return project.isAccessible() && project.hasNature(JavaCore.NATURE_ID);
		} catch (CoreException e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
			return false;
		}
	}

	@Override
	protected void basicDiscoverElement(final IProject project, final IProgressMonitor monitor)
			throws DiscoveryException {
		try {
			if (project.isAccessible() && project.hasNature(JavaCore.NATURE_ID)) {
				IJavaProject javaProject = JavaCore.create(project);
				analyzeJavaProject(javaProject, monitor);
			}
		} catch (CoreException e) {
			throw new DiscoveryException("Error discovering Java project", e); //$NON-NLS-1$
		}
	}

	/**
	 * This initializer method is implemented in order to set the IJavaProject
	 * on the instance of ElementsToAnalyze.
	 */
	@ParameterInitialValue(name = "ELEMENTS_TO_ANALYZE")
	public static ElementsToAnalyze getElementsToAnalyzeInitialValue(final IProject project) {
		if (project == null) {
			return null;
		}
		try {
			if (project.isAccessible() && project.hasNature(JavaCore.NATURE_ID)) {
				IJavaProject javaProject = JavaCore.create(project);
				return new ElementsToAnalyze(javaProject);
			}
		} catch (CoreException e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
		}
		return null;
	}
}
