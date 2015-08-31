/*******************************************************************************
 * Copyright (c) 2011 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.IJavaConstants;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;

public final class JavaModelUtils {
	private JavaModelUtils() {
		// utility class
	}

	public static boolean isJavaModelFile(final IFile file) {
		if (!file.exists()) {
			return false;
		}
		try {
			IContentDescription contentDescription = file.getContentDescription();
			return IJavaConstants.CONTENT_TYPE.equals(contentDescription.getContentType().getId());
		} catch (CoreException e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
		}

		return file.toString().endsWith(JavaDiscoveryConstants.JAVA_MODEL_FILE_SUFFIX);
	}
}
