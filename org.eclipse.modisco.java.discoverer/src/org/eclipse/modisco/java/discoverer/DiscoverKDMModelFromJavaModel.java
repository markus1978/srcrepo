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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.modisco.infra.discovery.core.AbstractModelDiscoverer;
import org.eclipse.modisco.infra.discovery.core.annotations.Parameter;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.TranslateJavaModelToKdm;
import org.eclipse.modisco.kdm.source.discoverer.IKDMDiscoveryConstants;

/** Discover a KDM model from a Java model file. */
public class DiscoverKDMModelFromJavaModel extends AbstractModelDiscoverer<IFile> {

	private static final String XMI_EXTENSION = ".xmi"; //$NON-NLS-1$

	public static final String ID = "org.eclipse.modisco.java.discoverer.javaToKDM"; //$NON-NLS-1$

	private String pathToTransformation = null;

	@Parameter(name = "PATH_TO_TRANSFORMATION", description = "Indicates which .asm file to use for the ATL transformation (specified as a java URL)")
	public void setPathToTransformation(final String pathToTransformation) {
		this.pathToTransformation = pathToTransformation;
	}

	public boolean isApplicableTo(final IFile file) {
		return JavaModelUtils.isJavaModelFile(file);
	}

	@Override
	protected void basicDiscoverElement(final IFile file, final IProgressMonitor monitor)
			throws DiscoveryException {

		try {
			TranslateJavaModelToKdm kdmTranslater = new TranslateJavaModelToKdm();
			if (this.pathToTransformation != null) {
				kdmTranslater.setPathToTransformation(this.pathToTransformation);
			}

			String javaFilePath = file.getFullPath().toString();
			if (javaFilePath.endsWith(JavaDiscoveryConstants.JAVA_MODEL_FILE_SUFFIX)) {
				javaFilePath = javaFilePath.substring(0, javaFilePath.length()
						- JavaDiscoveryConstants.JAVA_MODEL_FILE_SUFFIX.length());
			} else if (javaFilePath.endsWith(DiscoverKDMModelFromJavaModel.XMI_EXTENSION)) {
				javaFilePath = javaFilePath.substring(0, javaFilePath.length()
						- DiscoverKDMModelFromJavaModel.XMI_EXTENSION.length());
			}

			URI kdmModelUri = URI.createPlatformResourceURI(javaFilePath
					+ IKDMDiscoveryConstants.KDM_MODEL_FILE_SUFFIX, true);
			setDefaultTargetURI(kdmModelUri);
			URI javaModelURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			Resource kdmResource = kdmTranslater.getKDMModelFromJavaModelWithCustomTransformation(
					javaModelURI, null, kdmModelUri);
			getResourceSet().getResources().add(kdmResource);
			setTargetModel(kdmResource);
		} catch (Exception e) {
			throw new DiscoveryException("Error discovering KDM model", e); //$NON-NLS-1$
		}
	}
}
