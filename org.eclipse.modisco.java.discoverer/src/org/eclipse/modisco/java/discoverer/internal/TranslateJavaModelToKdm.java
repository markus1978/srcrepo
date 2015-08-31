/**
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Fabien GIQUEL (Mia-Software) - initial API and implementation
 *    Bjorn Tietjens
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer.internal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.modisco.util.atl.core.internal.AtlLaunchHelper;

public class TranslateJavaModelToKdm {

	// resources location
	private static final String KDM_MM_URI = org.eclipse.gmt.modisco.omg.kdm.action.ActionPackage.eNS_URI;
	private static final String JAVA_MM_URI = org.eclipse.gmt.modisco.java.emf.JavaPackage.eNS_URI;

	private String pathToTransformation;
	private static final String PATH_TO_TRANSFORMATION_DEFAULT = "resources/transformations/javaToKdm.asm"; //$NON-NLS-1$

	public void setPathToTransformation(final String newPathToTransformation) {
		this.pathToTransformation = newPathToTransformation;
	}

	public String getPathToTransformation() {
		return this.pathToTransformation;
	}

	public Resource getKDMModelFromJavaModelWithCustomTransformation(final URI javaSourceModelUri,
			final Resource javaModel, final URI kdmTargetModelUri) throws IOException {
		if (getPathToTransformation() == null || getPathToTransformation().length() == 0) {
			setPathToTransformation(TranslateJavaModelToKdm.PATH_TO_TRANSFORMATION_DEFAULT);
		}
		// relative search
		URL url = TranslateJavaModelToKdm.class.getResource(getPathToTransformation());

		if (url == null) {
			// absolute search
			try {
				url = new URL(getPathToTransformation());
			} catch (MalformedURLException e) {
				MoDiscoLogger.logError(e,
						"wrong URL: " + getPathToTransformation(), JavaActivator.getDefault()); //$NON-NLS-1$
			}

			if (url == null) {
				url = TranslateJavaModelToKdm.class
						.getResource(TranslateJavaModelToKdm.PATH_TO_TRANSFORMATION_DEFAULT);
			}
		}
		final URL transformation = url;

		return TranslateJavaModelToKdm.getKDMModelFromJavaModelWithCustomTransformation(
				javaSourceModelUri, javaModel, transformation, kdmTargetModelUri);
	}

	public static Resource getKDMModelFromJavaModelWithCustomTransformation(
			final URI javaSourceModelUri, final Resource javaModel, final URL transformation,
			final URI kdmTargetModelUri) throws IOException {
		URI localKdmTargetModelUri = kdmTargetModelUri;

		if (kdmTargetModelUri == null) {
			localKdmTargetModelUri = javaSourceModelUri.trimFileExtension().appendFileExtension(
					"kdm"); //$NON-NLS-1$
			// default value
		}
		final AtlLaunchHelper.ModelInfo inputModel = new AtlLaunchHelper.ModelInfo(
				"IN", javaSourceModelUri, javaModel, "java", //$NON-NLS-1$ //$NON-NLS-2$
				URI.createURI(TranslateJavaModelToKdm.JAVA_MM_URI));
		final AtlLaunchHelper.ModelInfo outputModel = new AtlLaunchHelper.ModelInfo(
				"OUT", localKdmTargetModelUri, null, "kdm", //$NON-NLS-1$ //$NON-NLS-2$
				URI.createURI(TranslateJavaModelToKdm.KDM_MM_URI));

		AtlLaunchHelper atlHelper = new AtlLaunchHelper();
		Resource result = atlHelper.runTransformation(transformation, inputModel, outputModel);

		return result;
	}

}
