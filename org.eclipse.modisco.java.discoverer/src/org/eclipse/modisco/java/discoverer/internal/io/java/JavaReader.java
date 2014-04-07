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

package org.eclipse.modisco.java.discoverer.internal.io.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.modisco.java.discoverer.internal.IModelReader;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.Messages;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.java.discoverer.internal.io.library.LibraryReader;
import org.eclipse.modisco.kdm.source.extension.discovery.ISourceRegionNotifier;

/**
 * A {@code JavaReader} reads the contents of .java files and builds the
 * corresponding Java model.
 * <p>
 * As source, a {@code JavaReader} accepts {@link IJavaProject Java projects},
 * single .java {@link ICompilationUnit files}, and .class {@link IClassFile
 * files} with source attached.
 * </p>
 * <p>
 * It uses JDT model construction. It is dedicated to J2SE5 (JDK 5 ~ JLS 3).
 * </p>
 * Using a more recent JLS release may require a new metamodel definition, since
 * some new meta-objects may be required.
 * <p>
 * This reader does not work outside of an eclipse workspace context :
 * javaProject should reference a java project of an alive Eclipse Workspace!
 * (cf https://bugs.eclipse.org/bugs/show_bug.cgi?id=87852)
 * </p>
 * 
 * @see LibraryReader
 * @see ASTParser
 */
public class JavaReader implements IModelReader {

	private static final int FILE_BUFFER_SIZE = 100;
	private boolean incremental = false;
	private boolean deepAnalysis = true;
	private final JavaFactory factory;
	private Model resultModel;
	private BindingManager globalBindings;
	private Map<String, Object> options;
	private String excludedElementsRegEx;
	private String includedElementsRegEx;
	private ISourceRegionNotifier<?> abstractRegionDiscoverer;
	private boolean logJavaAnalysisWarnings;

	/**
	 * Constructs a {@code JavaReader}.
	 * 
	 * @param factory
	 *            the EMF factory
	 * @param options
	 *            the options for this reader (may be <code>null</code>)
	 * @param abstractRegionDiscoverer
	 *            a abstractRegionDiscoverer to be notified of the compilation
	 *            unit visits
	 */
	public JavaReader(final JavaFactory factory, final Map<String, Object> options,
			final ISourceRegionNotifier<?> abstractRegionDiscoverer) {
		this.factory = factory;
		this.abstractRegionDiscoverer = abstractRegionDiscoverer;
		if (options != null) {
			setOptions(options);
		} else {
			setOptions(new HashMap<String, Object>());
		}
	}

	public void setDeepAnalysis(final boolean deepAnalysis) {
		this.deepAnalysis = deepAnalysis;
	}

	public boolean isDeepAnalysis() {
		return this.deepAnalysis;
	}

	public void setIncludedElementsRegEx(final String includedElementsRegEx) {
		this.includedElementsRegEx = includedElementsRegEx;
	}

	public String getIncludedElementsRegEx() {
		return this.includedElementsRegEx;
	}

	public void setExcludedElementsRegEx(final String excludedElementsRegEx) {
		this.excludedElementsRegEx = excludedElementsRegEx;
	}

	public String getExcludedElementsRegEx() {
		return this.excludedElementsRegEx;
	}

	public void setIncremental(final boolean incremental) {
		this.incremental = incremental;
	}

	public boolean isIncremental() {
		return this.incremental;
	}

	public void readModel(final Object source, final Model resultModel1,
			final IProgressMonitor monitor) {
		readModel(source, resultModel1, getBindingManager(), monitor);
	}

	public void readModel(final Object source, final Model resultModel1,
			final BindingManager bindingManager, final IProgressMonitor monitor) {

		if (source == null) {
			return;
		}

		setResultModel(resultModel1);
		setGlobalBindings(bindingManager);
		if (this.incremental) {
			getGlobalBindings().enableIncrementalDiscovering(getResultModel());
		} else {
			getGlobalBindings().disableIncrementalDiscovering();
		}
		JDTVisitorUtils.initializePrimitiveTypes(this.factory, resultModel1, getGlobalBindings());

		try {
			if (source instanceof IJavaProject) {
				IJavaProject javaProject = (IJavaProject) source;

				if (resultModel1.getName() == null || resultModel1.getName().length() == 0) {
					resultModel1.setName(javaProject.getElementName());
				}
				IPackageFragment[] packageFolder = javaProject.getPackageFragments();
				// loop on CompilationUnit-s
				for (IPackageFragment parent : packageFolder) {
					// test if package has compilations units and has not been
					// excluded
					if (parent.getCompilationUnits().length > 0 && !ignorePackage(parent)) {
						// report some feedback
						monitor.subTask(Messages.JavaReader_discoveringTask
								+ parent.getElementName());
						// parse package
						parsePackage(javaProject, resultModel1, parent, monitor);
					}
					if (monitor.isCanceled()) {
						return;
					}
				}
			} else if (source instanceof ITypeRoot) {
				parseTypeRoot((ITypeRoot) source);

			} else {
				throw new IllegalArgumentException(
						"Java reader can not handle source object : " + source.toString()); //$NON-NLS-1$
			}
		} catch (Exception e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
		}
	}

	/**
	 * Indicate if a element is excluded from analyse (user filter choice)
	 * 
	 * @param qualifiedName
	 *            the qualified name of the element
	 * @return
	 */
	protected boolean isElementExcluded(final String qualifiedName) {
		if (this.excludedElementsRegEx != null && this.excludedElementsRegEx.length() > 0) {
			return qualifiedName.matches(this.excludedElementsRegEx);
		}
		return false;
	}

	/**
	 * Indicate if a qualified name is included in analyse (user filter choice)
	 * 
	 * @param qualifiedName
	 *            the qualified name of the element
	 * @return
	 */
	protected boolean isElementIncluded(final String qualifiedName) {
		if (this.includedElementsRegEx != null && this.includedElementsRegEx.length() > 0) {
			return qualifiedName.matches(this.includedElementsRegEx);
		}
		return true; // no reg ex inclusion : all elements are included
	}

	/**
	 * Computes if a package does not contain any types to be analyzed
	 * (according to user filter choice)
	 * 
	 * @param aPackage
	 * @return
	 * @throws JavaModelException
	 */
	protected boolean ignorePackage(final IPackageFragment aPackage) throws JavaModelException {
		if (isElementExcluded(aPackage.getElementName())) {
			return true;
		}
		if (isElementIncluded(aPackage.getElementName())) {
			return false;
		}

		// Package is not explicitly included but maybe one of its owned types
		// has to be analyzed
		boolean noSubElementToAnalyse = true;
		for (ICompilationUnit cu : aPackage.getCompilationUnits()) {
			for (IType t : cu.getTypes()) {
				String qualifedName = t.getFullyQualifiedName();
				if (!isElementExcluded(qualifedName) && isElementIncluded(qualifedName)) {
					noSubElementToAnalyse = false;
				}
			}
		}

		// no need of recursion : aPackage does not own sub packages

		return noSubElementToAnalyse;
	}

	protected void parseTypeRoot(final ITypeRoot source) {

		org.eclipse.jdt.core.dom.CompilationUnit parsedCompilationUnit = parseCompilationUnit(source);
		String fileContent = null;
		String filePath = null;
		try {
			if (source instanceof ICompilationUnit) {
				IFile theIFile = ResourcesPlugin.getWorkspace().getRoot()
						.getFile(parsedCompilationUnit.getJavaElement().getPath());
				// getContent(IFile) is faster than ICompilationUnit.getSource()
				fileContent = getContent(theIFile).toString();
				IProject project = source.getJavaProject().getProject();
				filePath = getRelativePath(project, parsedCompilationUnit);
			} else {
				// IJavaElement.CLASS_FILE
				fileContent = LibraryReader.getFileContent((IClassFile) source);
				filePath = LibraryReader.getPath((IClassFile) source);
			}
			visitCompilationUnit(getResultModel(), parsedCompilationUnit, filePath, fileContent);

		} catch (Exception e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
		}
	}

	protected void parsePackage(final IJavaProject javaProject, final Model resultModel1,
			final IPackageFragment parent, final IProgressMonitor monitor)
			throws JavaModelException {
		boolean analyseAllPackage = !isElementExcluded(parent.getElementName())
				&& isElementIncluded(parent.getElementName());

		ICompilationUnit[] children = parent.getCompilationUnits();
		for (ICompilationUnit cu : children) {
			// iterate on each type of each CU and check if one is excluded
			boolean isExcluded = false;
			for (IType t : cu.getTypes()) {
				if (isElementExcluded(t.getFullyQualifiedName())
						|| !(analyseAllPackage || isElementIncluded(t.getFullyQualifiedName()))) {
					isExcluded = true;
					break;
				}
			}
			if (!isExcluded) {
				parseTypeRoot(cu);
			}
			if (monitor.isCanceled()) {
				return;
			}
		}
		if (this.incremental) {
			reset();
		}
	}

	protected void visitCompilationUnit(final Model resultModel1,
			final org.eclipse.jdt.core.dom.CompilationUnit parsedCompilationUnit,
			final String filePath, final String fileContent) {

		JDTVisitor jdtVisitor = new JDTVisitor(this.factory, resultModel1, getGlobalBindings(),
				filePath, fileContent, getGlobalBindings().isIncrementalDiscovering(),
				this.deepAnalysis, this.logJavaAnalysisWarnings);
		parsedCompilationUnit.accept(jdtVisitor);

		if (this.abstractRegionDiscoverer != null) {
			for (ASTNode key : jdtVisitor.getBijectiveMap().getKeys()) {
				int startPosition = key.getStartPosition();
				int endPosition = startPosition + key.getLength();
				int startLine = parsedCompilationUnit.getLineNumber(startPosition);
				int endLine = parsedCompilationUnit.getLineNumber(endPosition);
				this.abstractRegionDiscoverer
						.notifySourceRegionVisited(filePath, startPosition, endPosition, startLine,
								endLine, jdtVisitor.getBijectiveMap().getValue(key));
			}
		}
	}

	protected static CompilationUnit parseCompilationUnit(final ITypeRoot source) {
		// Code parsing : here is indicated the version of JDK (~JLS) to
		// consider, see Class comments
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setResolveBindings(true);
		parser.setSource(source);
		CompilationUnit parsedCompilationUnit = (CompilationUnit) parser.createAST(null);
		return parsedCompilationUnit;
	}

	protected void resolveMethodRedefinition(final Model resultModel1) {
		MethodRedefinitionManager.resolveMethodRedefinitions(resultModel1, this.factory);
	}

	protected void finalResolveBindings(final Model resultModel1) {
		getGlobalBindings().resolveBindings(resultModel1);
	}

	public static String getRelativePath(final IProject aProject,
			final CompilationUnit parsedCompilationUnit) {
		IPath projectpath = aProject.getFullPath();
		IPath filepath = parsedCompilationUnit.getJavaElement().getPath();
		// we want path relative to project directory
		if (projectpath.isPrefixOf(filepath)) {
			filepath = filepath.removeFirstSegments(projectpath.segmentCount());
		}
		String filePathString = filepath.toOSString();
		if (!filePathString.startsWith(java.io.File.separator)) {
			filePathString = java.io.File.separator + filePathString;
		}
		return filePathString;
	}

	public static StringBuilder getContent(final IFile anIFile) throws CoreException, IOException {
		InputStream is = anIFile.getContents();
		StringBuilder cuText = new StringBuilder();
		Reader r = new InputStreamReader(is);
		char[] chars = new char[JavaReader.FILE_BUFFER_SIZE];
		int read;
		while ((read = r.read(chars)) != -1) {
			if (read == JavaReader.FILE_BUFFER_SIZE) {
				cuText.append(chars);
			} else {
				cuText.append(chars, 0, read);
			}
		}
		is.close();
		return cuText;
	}

	protected void reset() {
		getGlobalBindings().resolveBindings(getResultModel());
		setGlobalBindings(getBindingManager());
	}

	protected BindingManager getBindingManager() {
		BindingManager bindingManager = new BindingManager(this.factory);
		if (this.incremental) {
			bindingManager.enableIncrementalDiscovering(getResultModel());
		}
		return bindingManager;
	}

	public void terminate(final IProgressMonitor monitor) {
		monitor.subTask(Messages.JavaReader_bindingsTask);
		finalResolveBindings(getResultModel());

		monitor.subTask(Messages.JavaReader_redefinitionsTask);
		resolveMethodRedefinition(getResultModel());
	}

	protected void setResultModel(final Model resultModel) {
		this.resultModel = resultModel;
	}

	public Model getResultModel() {
		return this.resultModel;
	}

	public void setGlobalBindings(final BindingManager globalBindings) {
		this.globalBindings = globalBindings;
	}

	public BindingManager getGlobalBindings() {
		return this.globalBindings;
	}

	public void setOptions(final Map<String, Object> options) {
		this.options = options;
	}

	public Map<String, Object> getOptions() {
		return this.options;
	}

	public void setAbstractRegionDiscoverer(
			final ISourceRegionNotifier<?> abstractRegionDiscoverer) {
		this.abstractRegionDiscoverer = abstractRegionDiscoverer;
	}

}
