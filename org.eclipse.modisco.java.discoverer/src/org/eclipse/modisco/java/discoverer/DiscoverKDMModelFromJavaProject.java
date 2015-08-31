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

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.modisco.infra.discovery.core.AbstractModelDiscoverer;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.TranslateJavaModelToKdm;
import org.eclipse.modisco.kdm.source.discoverer.IKDMDiscoveryConstants;

/** Discover a KDM model from a Java project. */
public class DiscoverKDMModelFromJavaProject extends AbstractModelDiscoverer<IJavaProject> {

	public static final String ID = "org.eclipse.modisco.java.discoverer.javaProjectToKDM"; //$NON-NLS-1$

	public boolean isApplicableTo(final IJavaProject project) {
		return project.getProject().isAccessible();
	}

	@Override
	protected void basicDiscoverElement(final IJavaProject project, final IProgressMonitor monitor)
			throws DiscoveryException {
		setDefaultTargetURI(URI.createPlatformResourceURI(project.getProject().getFullPath()
				.append(project.getProject().getName()).toString()
				+ IKDMDiscoveryConstants.KDM_MODEL_FILE_SUFFIX, true));
		Resource kdmModel = discoverKDM(project, getDefaultTargetURI(), monitor);
		getResourceSet().getResources().add(kdmModel);
		setTargetModel(kdmModel);
	}

	static Resource discoverKDM(final IJavaProject project, final URI kdmModelURI,
			final IProgressMonitor monitor) throws DiscoveryException {
		try {
			// discover Java model
			DiscoverJavaModelFromJavaProject javaDiscoverer = new DiscoverJavaModelFromJavaProject();
			javaDiscoverer.discoverElement(project, monitor);
			Resource javaModel = javaDiscoverer.getTargetModel();

			// transform to KDM
			TranslateJavaModelToKdm kdmTranslater = new TranslateJavaModelToKdm();
			Resource kdmModel;
			kdmModel = kdmTranslater.getKDMModelFromJavaModelWithCustomTransformation(
					javaModel.getURI(), javaModel, kdmModelURI);
			return kdmModel;
		} catch (IOException e) {
			throw new DiscoveryException("Error discovering KDM model", e); //$NON-NLS-1$
		}
	}
}
