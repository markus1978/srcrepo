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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.facet.util.emf.core.serialization.ISerializationRegistry;
import org.eclipse.emf.facet.util.emf.core.serialization.ISerializationService;
import org.eclipse.emf.facet.util.emf.core.serialization.ISerializer;
import org.eclipse.gmt.modisco.infra.common.core.internal.utils.StringUtils;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.modisco.java.discoverer.ElementsToAnalyze;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;

public class ElementsToAnalyzeSerializer implements ISerializer<ElementsToAnalyze> {

	private static final String ELEMENT_PREFIX = "E:"; //$NON-NLS-1$
	private static final String PROJECT_PREFIX = "P:"; //$NON-NLS-1$

	private static final char SEPARATOR1 = ',';
	private static final char SEPARATOR1_REPLACEMENT = '1';
	private static final char SEPARATOR2 = ';';
	private static final char SEPARATOR2_REPLACEMENT = '2';
	private static final char SEPARATOR3 = '|';
	private static final char SEPARATOR3_REPLACEMENT = '3';
	private static final char ESCAPE_CHAR = '\\';

	public ElementsToAnalyzeSerializer() {
		//
	}

	public Class<ElementsToAnalyze> getType() {
		return ElementsToAnalyze.class;
	}

	private static String escape(final String str) {
		return StringUtils.escape(str, ElementsToAnalyzeSerializer.ESCAPE_CHAR, new char[] {
				ElementsToAnalyzeSerializer.SEPARATOR1, ElementsToAnalyzeSerializer.SEPARATOR2,
				ElementsToAnalyzeSerializer.SEPARATOR3 }, new char[] {
				ElementsToAnalyzeSerializer.SEPARATOR1_REPLACEMENT,
				ElementsToAnalyzeSerializer.SEPARATOR2_REPLACEMENT,
				ElementsToAnalyzeSerializer.SEPARATOR3_REPLACEMENT });
	}

	private static String unescape(final String str) {
		return StringUtils.unescape(str, ElementsToAnalyzeSerializer.ESCAPE_CHAR, new char[] {
				ElementsToAnalyzeSerializer.SEPARATOR1, ElementsToAnalyzeSerializer.SEPARATOR2,
				ElementsToAnalyzeSerializer.SEPARATOR3 }, new char[] {
				ElementsToAnalyzeSerializer.SEPARATOR1_REPLACEMENT,
				ElementsToAnalyzeSerializer.SEPARATOR2_REPLACEMENT,
				ElementsToAnalyzeSerializer.SEPARATOR3_REPLACEMENT });
	}

	public String serialize(final ElementsToAnalyze elementsToAnalyze) {
		try {
			StringBuilder builder = new StringBuilder();
			IJavaProject javaProject = elementsToAnalyze.getJavaProject();
			if (javaProject == null) {
				return ""; //$NON-NLS-1$
			}

			builder.append(escape(javaProject.getElementName()));
			builder.append(ElementsToAnalyzeSerializer.SEPARATOR1);

			Set<Object> elementsToDiscover = elementsToAnalyze.getElementsToDiscover();
			boolean first = true;
			for (Object object : elementsToDiscover) {
				if (!first) {
					builder.append(ElementsToAnalyzeSerializer.SEPARATOR1);
				}
				first = false;

				if (object instanceof IJavaProject) {
					IJavaProject javaProject2 = (IJavaProject) object;
					builder.append(ElementsToAnalyzeSerializer.PROJECT_PREFIX);
					builder.append(escape(javaProject2.getElementName()));
				} else if (object instanceof IJavaElement) {
					IJavaElement javaElement = (IJavaElement) object;
					builder.append(ElementsToAnalyzeSerializer.ELEMENT_PREFIX);
					builder.append(escape(javaElement.getJavaProject().getElementName()));
					builder.append(ElementsToAnalyzeSerializer.SEPARATOR3);
					builder.append(escape(javaElement.getElementName()));
					builder.append(ElementsToAnalyzeSerializer.SEPARATOR3);
					builder.append(escape(javaElement.getPath().toString()));
				} else {
					MoDiscoLogger.logError("Unexpected element: " + object.getClass().getName(), //$NON-NLS-1$
							JavaActivator.getDefault());
					continue;
				}

				Map<String, Object> discoveryOptions = elementsToAnalyze
						.getDiscoveryOptions(object);
				for (Entry<String, Object> entry : discoveryOptions.entrySet()) {

					builder.append(ElementsToAnalyzeSerializer.SEPARATOR2);
					builder.append(escape(entry.getKey()));
					builder.append(ElementsToAnalyzeSerializer.SEPARATOR2);
					ISerializer<?> serializer2 = ISerializationRegistry.INSTANCE
							.getSerializerFor(entry.getValue().getClass());
					if (serializer2 != null) {
						String serialized2 = ISerializationService.INSTANCE.serialize(entry
								.getValue());
						if (serialized2 != null) {
							builder.append(escape(serialized2));
						}
					} else {
						MoDiscoLogger.logError(
								"No serializer for: " + entry.getValue().getClass().getName(), //$NON-NLS-1$
								JavaActivator.getDefault());
					}
				}
			}

			return builder.toString();
		} catch (Exception e) {
			MoDiscoLogger.logError(e, "Error serializing elements to analyze", //$NON-NLS-1$
					JavaActivator.getDefault());
			return ""; //$NON-NLS-1$
		}
	}

	public ElementsToAnalyze deserialize(final String serializedValue) {
		try {
			String[] elements = serializedValue
					.split("\\" + ElementsToAnalyzeSerializer.SEPARATOR1); //$NON-NLS-1$

			ElementsToAnalyze elementsToAnalyze = new ElementsToAnalyze(
					getJavaProject(unescape(elements[0])));

			for (int i = 1; i < elements.length; i++) {
				String element = elements[i];
				if (element.startsWith(ElementsToAnalyzeSerializer.PROJECT_PREFIX)) {
					String[] elementParts = element.substring(
							ElementsToAnalyzeSerializer.PROJECT_PREFIX.length()).split(
							"\\" + ElementsToAnalyzeSerializer.SEPARATOR2); //$NON-NLS-1$
					String projectName2 = unescape(elementParts[0]);
					IJavaProject javaProject = getJavaProject(projectName2);
					elementsToAnalyze.addElementToDiscover(javaProject);
					fillMapWithRemainingParts(elementParts,
							elementsToAnalyze.getDiscoveryOptions(javaProject));
				} else if (element.startsWith(ElementsToAnalyzeSerializer.ELEMENT_PREFIX)) {
					String[] elementParts = element.substring(
							ElementsToAnalyzeSerializer.ELEMENT_PREFIX.length()).split(
							"\\" + ElementsToAnalyzeSerializer.SEPARATOR2); //$NON-NLS-1$
					String elementName = elementParts[0];
					String[] parts = elementName.split("\\" //$NON-NLS-1$
							+ ElementsToAnalyzeSerializer.SEPARATOR3);
					IJavaProject containingProject = getJavaProject(unescape(parts[0]));
					for (IJavaElement javaElement : containingProject.getChildren()) {
						if (unescape(parts[1]).equals(javaElement.getElementName())
								&& new Path(unescape(parts[2])).equals(javaElement.getPath())) {
							elementsToAnalyze.addElementToDiscover(javaElement);
							fillMapWithRemainingParts(elementParts,
									elementsToAnalyze.getDiscoveryOptions(javaElement));
							break;
						}
					}
				} else {
					MoDiscoLogger.logError("unhandled element", JavaActivator.getDefault()); //$NON-NLS-1$
				}

			}

			return elementsToAnalyze;
		} catch (Exception e) {
			MoDiscoLogger.logError(e, "Error deserializing elements to analyze", //$NON-NLS-1$
					JavaActivator.getDefault());
			return new ElementsToAnalyze(null);
		}
	}

	private static void fillMapWithRemainingParts(final String[] elementParts,
			final Map<String, Object> discoveryOptions) {
		for (int i = 1; i < elementParts.length; i += 2) {
			String strKey = elementParts[i];
			String strValue = elementParts[i + 1];
			Object deserialized = ISerializationService.INSTANCE.deserialize(strValue);
			discoveryOptions.put(strKey, deserialized);
		}
	}

	private static IJavaProject getJavaProject(final String projectName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return JavaCore.create(project);
	}
}
