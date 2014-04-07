/**
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Fabien GIQUEL (Mia-Software) - initial API and implementation
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer.internal.io.library;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.Archive;
import org.eclipse.gmt.modisco.java.Manifest;
import org.eclipse.gmt.modisco.java.ManifestAttribute;
import org.eclipse.gmt.modisco.java.ManifestEntry;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;

public final class ManifestReader {

	private ManifestReader() {
		// Nothing
	}

	/**
	 * Extract Manifest information.
	 */
	public static void completeArchiveWithManifest(final IPackageFragmentRoot physicalArchive,
			final Archive modelArchive, final JavaFactory factory) {
		try {
			File jarFile = null;
			if (physicalArchive.isExternal()) {
				jarFile = new File(physicalArchive.getPath().toOSString());
			} else {
				jarFile = new File(physicalArchive.getResource().getRawLocation().toOSString());
			}
			java.util.jar.JarFile jar = new java.util.jar.JarFile(jarFile);

			java.util.jar.Manifest manifest = jar.getManifest();
			if (manifest != null) {
				Manifest modelManifest = factory.createManifest();
				modelArchive.setManifest(modelManifest);

				java.util.jar.Attributes mainAttrs = manifest.getMainAttributes();
				modelManifest.getMainAttributes().addAll(readAttributes(mainAttrs, factory));

				for (Map.Entry<String, java.util.jar.Attributes> entry : manifest.getEntries()
						.entrySet()) {
					ManifestEntry modelEntry = factory.createManifestEntry();
					modelEntry.setName(entry.getKey());
					modelEntry.getAttributes().addAll(readAttributes(entry.getValue(), factory));
					modelManifest.getEntryAttributes().add(modelEntry);
				}
			}

		} catch (IOException e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
		}
	}

	public static List<ManifestAttribute> readAttributes(final java.util.jar.Attributes attributes,
			final JavaFactory factory) {
		List<ManifestAttribute> modelAttributes = new ArrayList<ManifestAttribute>();

		for (Map.Entry<Object, Object> attribute : attributes.entrySet()) {
			ManifestAttribute modelAttribute = factory.createManifestAttribute();
			modelAttribute.setKey(attribute.getKey().toString());
			modelAttribute.setValue(attribute.getValue().toString());
			modelAttributes.add(modelAttribute);
		}

		return modelAttributes;
	}
}
