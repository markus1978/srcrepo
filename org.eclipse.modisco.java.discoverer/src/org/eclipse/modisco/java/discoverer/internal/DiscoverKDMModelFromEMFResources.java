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
 *    Gregoire Dupe (Mia-Software) - Bug 403912 - Java to KDM discoverer active on Resource
 *******************************************************************************/

package org.eclipse.modisco.java.discoverer.internal;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.modisco.infra.discovery.core.AbstractModelDiscoverer;
import org.eclipse.modisco.infra.discovery.core.annotations.Parameter;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.kdm.source.discoverer.IKDMDiscoveryConstants;

/**
 * Discover a KDM model from a project with the java nature.
 * 
 * @since 0.12
 */
public class DiscoverKDMModelFromEMFResources extends
		AbstractModelDiscoverer<Resource> {

	public static final String ID = "org.eclipse.modisco.java.discoverer.javaResourceToKDM"; //$NON-NLS-1$

	private String pathToTransfo = null;

	@Parameter(name = "PATH_TO_TRANSFORMATION", description = "Indicates which .asm file to use for the ATL transformation (specified as a java URL)")
	public void setPathToTransfo(final String pathToTransfo) {
		this.pathToTransfo = pathToTransfo;
	}

	public boolean isApplicableTo(final Resource resource) {
		boolean result = false;
		final List<EObject> contents = resource.getContents();
		if (!contents.isEmpty()) {
			final EObject eObject = contents.get(0);
			final EPackage ePackage = eObject.eClass().getEPackage();
			result = ePackage == JavaPackage.eINSTANCE;
		}
		return result;
	}

	@Override
	protected void basicDiscoverElement(final Resource inputResource,
			final IProgressMonitor monitor) throws DiscoveryException {
		try {
			final TranslateJavaModelToKdm kdmTranslater = new TranslateJavaModelToKdm();
			if (this.pathToTransfo != null) {
				kdmTranslater
						.setPathToTransformation(this.pathToTransfo);
			}
			final URI javaModelURI = inputResource.getURI();
			final URI kdmModelUri = javaModelURI
					.appendSegment(IKDMDiscoveryConstants.KDM_MODEL_FILE_SUFFIX);
			setDefaultTargetURI(kdmModelUri);
			final Resource kdmResource = kdmTranslater
					.getKDMModelFromJavaModelWithCustomTransformation(
							javaModelURI, inputResource, kdmModelUri);
			getResourceSet().getResources().add(kdmResource);
			setTargetModel(kdmResource);
		} catch (Exception e) {
			throw new DiscoveryException("Error discovering KDM model", e); //$NON-NLS-1$
		}
	}
}
