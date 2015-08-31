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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.modisco.infra.discovery.core.AbstractModelDiscoverer;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.IModelReader;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.io.library.LibraryReader;

/** Discover a Java model from a jar file. */
public class DiscoverJavaModelFromLibrary extends AbstractModelDiscoverer<IPackageFragmentRoot> {

	public static final String ID = "org.eclipse.modisco.java.discoverer.library"; //$NON-NLS-1$

	public boolean isApplicableTo(final IPackageFragmentRoot packageFragmentRoot) {
		try {
			return packageFragmentRoot.getKind() == IPackageFragmentRoot.K_BINARY;
		} catch (JavaModelException e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
			return false;
		}
	}

	protected static JavaFactory getEFactory() {
		return org.eclipse.gmt.modisco.java.emf.JavaFactory.eINSTANCE;
	}

	protected static LibraryReader getLibraryReader() {
		return new LibraryReader(getEFactory());
	}

	@Override
	protected void basicDiscoverElement(final IPackageFragmentRoot packageFragmentRoot,
			final IProgressMonitor monitor) throws DiscoveryException {

		final IJavaProject javaProject = packageFragmentRoot.getJavaProject();
		if (javaProject == null) {
			return;
		}
		IProject project = javaProject.getProject();

		setDefaultTargetURI(URI.createPlatformResourceURI(
				project.getFullPath().append(packageFragmentRoot.getElementName()).toString()
						.concat(JavaDiscoveryConstants.JAVA_MODEL_FILE_SUFFIX), true));

		Model model = getEFactory().createModel();
		createTargetModel().getContents().add(model);
		IModelReader reader = getLibraryReader();
		reader.readModel(packageFragmentRoot, model, monitor);

		if (monitor.isCanceled()) {
			return;
		}
		reader.terminate(monitor);
	}
}
