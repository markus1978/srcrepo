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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.modisco.infra.discovery.core.AbstractModelDiscoverer;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.kdm.source.discoverer.IKDMDiscoveryConstants;

/** Discover a KDM model from a project with the java nature. */
public class DiscoverKDMModelFromProject extends AbstractModelDiscoverer<IProject> {

	public static final String ID = "org.eclipse.modisco.java.discoverer.projectToKDM"; //$NON-NLS-1$

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
		setDefaultTargetURI(URI.createPlatformResourceURI(
				project.getFullPath().append(project.getName()).toString()
						+ IKDMDiscoveryConstants.KDM_MODEL_FILE_SUFFIX, true));

		IJavaProject javaProject = JavaCore.create(project);
		Resource kdmModel = DiscoverKDMModelFromJavaProject.discoverKDM(javaProject,
				getDefaultTargetURI(), monitor);
		getResourceSet().getResources().add(kdmModel);
		setTargetModel(kdmModel);
	}
}
