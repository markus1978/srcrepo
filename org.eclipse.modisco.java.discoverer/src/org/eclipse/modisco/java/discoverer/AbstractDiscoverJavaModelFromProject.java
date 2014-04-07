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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.modisco.infra.discovery.core.annotations.Parameter;
import org.eclipse.modisco.java.discoverer.internal.IModelReader;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.Messages;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.java.discoverer.internal.io.library.LibraryReader;
import org.eclipse.modisco.kdm.source.extension.discovery.AbstractRegionDiscoverer2;

/**
 * Abstract class to discover a Java model from a project and optionally its
 * libraries.
 */
public abstract class AbstractDiscoverJavaModelFromProject<T> extends AbstractRegionDiscoverer2<T> {

	private ElementsToAnalyze fElementsToAnalyze;

	@Parameter(name = "ELEMENTS_TO_ANALYZE", description = "The Java projects or packages that will be analyzed. ")
	public void setElementsToAnalyze(final ElementsToAnalyze elementsToAnalyze) {
		this.fElementsToAnalyze = elementsToAnalyze;
	}

	protected ElementsToAnalyze getElementsToAnalyze() {
		return this.fElementsToAnalyze;
	}

	/**
	 * A parameter key for indicating to log warnings from java analysis.
	 *
	 * For instance, sometimes java bindings (reference to a Java type, method,
	 * ...) cannot be completed. Such java binding problems occurs typically
	 * when there are some missing libraries in the Java project classpath. Such
	 * java bindings problems will result in missing information in the
	 * resulting Java model (occurrences of
	 * {@link org.eclipse.gmt.modisco.java.UnresolvedItem})
	 */
	private boolean fLogJavaAnalysisWarnings = false;

	@Parameter(name = "LOG_JAVA_ANALYSIS_WARNINGS", description = "Whether to log warnings from java analysis.")
	public void setLogJavaAnalysisWarnings(final boolean logJavaAnalysisWarnings) {
		this.fLogJavaAnalysisWarnings = logJavaAnalysisWarnings;
	}

	protected boolean isLogJavaAnalysisWarnings() {
		return this.fLogJavaAnalysisWarnings;
	}

	private boolean fDeepAnalysis = true;

	@Parameter(name = "DEEP_ANALYSIS", description = "If true, analyze method bodies. If false, only analyze fields and method signatures.")
	public void setDeepAnalysis(final boolean deepAnalysis) {
		this.fDeepAnalysis = deepAnalysis;
	}

	protected boolean isDeepAnalysis() {
		return this.fDeepAnalysis;
	}

	private boolean fIncrementalMode;

	@Parameter(name = "INCREMENTAL_MODE", description = "Optimize memory use by analyzing incrementally (more time expensive).")
	public void setIncrementalMode(final boolean incrementalMode) {
		this.fIncrementalMode = incrementalMode;
	}

	protected boolean isIncrementalMode() {
		return this.fIncrementalMode;
	}

	private String includedElementsRegEx;

	@Parameter(name = "INCLUDED_ELEMENTS_REGEX", description = "A regular expression on qualified names for elements (types & packages) to be included during analysis")
	public void setIncludedElementsRegEx(final String includedElementsRegEx) {
		this.includedElementsRegEx = includedElementsRegEx;
	}

	protected String getIncludedElementsRegEx() {
		return this.includedElementsRegEx;
	}

	private String excludedElementsRegEx;

	@Parameter(name = "EXCLUDED_ELEMENTS_REGEX", description = "A regular expression on qualified names for elements (types & packages) to be excluded during analysis")
	public void setExcludedElementsRegEx(final String excludedElementsRegEx) {
		this.excludedElementsRegEx = excludedElementsRegEx;
	}

	protected String getExcludedElementsRegEx() {
		return this.excludedElementsRegEx;
	}

	@SuppressWarnings("static-method") /* designed for overridability */
	public JavaFactory getEFactory() {
		return org.eclipse.gmt.modisco.java.emf.JavaFactory.eINSTANCE;
	}

	protected void analyzeJavaProject(final IJavaProject source, final IProgressMonitor monitor) {
		if (source == null) {
			throw new IllegalArgumentException("source is null"); //$NON-NLS-1$
		}
		IProject project = source.getProject();

		setDefaultTargetURI(URI
				.createPlatformResourceURI(project.getFullPath().append(project.getName())
						.toString().concat(JavaDiscoveryConstants.JAVA_MODEL_FILE_SUFFIX), true));

		if (this.fElementsToAnalyze == null) {
			this.fElementsToAnalyze = new ElementsToAnalyze(source);
		}

		ElementsToAnalyze elementsToDiscover = computeElementsToDiscover(this.fElementsToAnalyze);

		createTargetModel();
		Model model = getEFactory().createModel();
		BindingManager globalBindings = getBindingManager();
		getTargetModel().getContents().add(model);

		IModelReader reader = null;
		for (Entry<IModelReader, Object> readerEntry : getReaders(elementsToDiscover).entrySet()) {
			if (monitor.isCanceled()) {
				return;
			}
			reader = readerEntry.getKey();
			Object element = readerEntry.getValue();
			reader.readModel(element, model, globalBindings, monitor);
		}
		if (reader != null) {
			reader.terminate(monitor);
		}
		model.setName(project.getName());
		endAnalyzeJavaProject(model);
	}

	protected void endAnalyzeJavaProject(final Model model) {
		//
	}

	/*
	 * This method makes sure that one ElementsToAnalyze instance is still coherent
	 * to one Java project (jars and dependencies may have changed), and update it if needed.
	 */
	public static ElementsToAnalyze computeElementsToDiscover(
			final ElementsToAnalyze userElementsToAnalyze) {
		try {
			IJavaProject javaProject = userElementsToAnalyze.getJavaProject();
			if (javaProject == null) {
				return userElementsToAnalyze.clone();
			}
			List<Object> discoverableElements = computeDiscoverableElements(userElementsToAnalyze
					.getJavaProject());
			ElementsToAnalyze elementsToAnalyze = userElementsToAnalyze.clone();

			// make sure the source java project is selected
			elementsToAnalyze.addElementToDiscover(javaProject);

			// remove any project/library that is not on the project classpath
			List<Object> elementsToRemove = new ArrayList<Object>();
			for (Object oldParameter : elementsToAnalyze.getElementsToDiscover()) {
				if (!oldParameter.equals(javaProject)
						&& !discoverableElements.contains(oldParameter)) {
					elementsToRemove.add(oldParameter);
				}
			}
			for (Object oldParameterToRemove : elementsToRemove) {
				elementsToAnalyze.removeElementToDiscover(oldParameterToRemove);
			}
			return elementsToAnalyze;
		} catch (CloneNotSupportedException e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
			return null;
		}
	}

	public static List<Object> computeDiscoverableElements(final IJavaProject javaProject) {
		// retrieve required projects and libraries
		Set<IJavaProject> projects = null;
		Set<IPackageFragmentRoot> libraries = null;
		final List<Object> discoverableElements = new ArrayList<Object>();
		try {
			projects = computeRequiredProjects(javaProject);
			libraries = computeRequiredLibraries(projects);
		} catch (JavaModelException e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
			return discoverableElements;
		}

		discoverableElements.addAll(projects);
		discoverableElements.addAll(libraries);

		return discoverableElements;
	}

	@Override
	public String toString() {
		return Messages.DiscoverJavaModelFromJavaProject_title;
	}

	public static Set<IJavaProject> computeRequiredProjects(final IJavaProject project)
			throws JavaModelException {
		Set<IJavaProject> projects = new LinkedHashSet<IJavaProject>();
		if (project == null) {
			return projects;
		}
		// we keep package fragments which are binaries
		projects.add(project);
		for (String projectName : project.getRequiredProjectNames()) {
			IJavaProject requiredProject = project.getJavaModel().getJavaProject(projectName);
			if (requiredProject.getProject().isAccessible()) {
				projects.add(requiredProject);
			}
		}
		return projects;
	}

	protected static Set<IPackageFragmentRoot> computeRequiredLibraries(
			final Collection<IJavaProject> projects) throws JavaModelException {
		Set<IPackageFragmentRoot> libraries = new LinkedHashSet<IPackageFragmentRoot>();
		// we keep package fragments which are binaries
		for (IJavaProject project : projects) {
			libraries.addAll(computeRequiredLibraries(project));
		}
		return libraries;
	}

	public static Set<IPackageFragmentRoot> computeRequiredLibraries(final IJavaProject project)
			throws JavaModelException {
		Set<IPackageFragmentRoot> libraries = new LinkedHashSet<IPackageFragmentRoot>();
		// we keep package fragments which are binaries
		for (IPackageFragmentRoot lib : project.getPackageFragmentRoots()) {
			if (lib.exists() && lib.getKind() == IPackageFragmentRoot.K_BINARY) {
				libraries.add(lib);
			}
		}
		return libraries;
	}

	protected Map<IModelReader, Object> getReaders(final ElementsToAnalyze elementsToDiscover) {
		Map<IModelReader, Object> readers = new HashMap<IModelReader, Object>();

		for (Object element : elementsToDiscover.getElementsToDiscover()) {
			Map<String, Object> elementOptions = elementsToDiscover.getDiscoveryOptions(element);
			if (element instanceof IJavaProject) {
				JavaReader javaReader = getJavaReader(elementOptions);
				javaReader.setIncremental(isIncrementalMode());
				javaReader.setDeepAnalysis(isDeepAnalysis());
				javaReader.setIncludedElementsRegEx(getIncludedElementsRegEx());
				javaReader.setExcludedElementsRegEx(getExcludedElementsRegEx());

				readers.put(javaReader, element);
			} else if (element instanceof IPackageFragmentRoot) {
				LibraryReader libraryReader = getLibraryReader(elementOptions);
				readers.put(libraryReader, element);
			}
		}
		return readers;
	}

	protected LibraryReader getLibraryReader(final Map<String, Object> elementOptions) {
		return new LibraryReader(getEFactory(), elementOptions);
	}

	protected JavaReader getJavaReader(final Map<String, Object> elementOptions) {
		return new JavaReader(getEFactory(), elementOptions, this);
	}

	protected BindingManager getBindingManager() {
		return new BindingManager(getEFactory());
	}
}
