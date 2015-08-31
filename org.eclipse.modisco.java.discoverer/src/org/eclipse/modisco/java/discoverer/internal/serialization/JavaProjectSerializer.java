/*******************************************************************************
 * Copyright (c) 2011 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - initial API and implementation
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer.internal.serialization;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.facet.util.emf.core.serialization.ISerializer;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;

public class JavaProjectSerializer implements ISerializer<IJavaProject> {

	public JavaProjectSerializer() {
		//
	}

	public Class<IJavaProject> getType() {
		return IJavaProject.class;
	}

	public String serialize(final IJavaProject javaProject) {
		return javaProject.getElementName();
	}

	public IJavaProject deserialize(final String serializedValue) {
		try {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(serializedValue);
			if (project != null && project.hasNature(JavaCore.NATURE_ID)) {
				return JavaCore.create(project);
			}
		} catch (CoreException e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
		}
		return null;
	}
}
