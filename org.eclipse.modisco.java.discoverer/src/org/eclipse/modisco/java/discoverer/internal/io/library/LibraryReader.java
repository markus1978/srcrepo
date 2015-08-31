/*******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Romain Dervaux (Mia-Software) - initial API and implementation
 *******************************************************************************/

package org.eclipse.modisco.java.discoverer.internal.io.library;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.Archive;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.modisco.java.discoverer.internal.IModelReader;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.Messages;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.java.discoverer.internal.io.java.MethodRedefinitionManager;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;

/**
 * A {@code LibraryReader} reads the contents of .class files and builds the
 * corresponding Java model.
 * <p>
 * As source, a {@code LibraryReader} accepts {@link IPackageFragmentRoot
 * libraries} and single .class {@link IClassFile files}.
 * </p>
 * <p>
 * The analysis doesn't go beyond root types and members. Bytecode (inside
 * methods), local classes & anonymous classes are ignored.
 * </p>
 * <p>
 * The model is built along the Java 1.4 language specification. This means that
 * all specificities of Java 5 are not handled (Enumerations and annotations are
 * translated respectively as classes & interfaces, only raw types of
 * parameterized types are considered, type variables & wildcard types are
 * translated as basic {@link Object}s, ...).
 * </p>
 * <p>
 * It handles both internal and {@link IPackageFragmentRoot#isExternal()
 * external} libraries.
 * </p>
 * <p>
 * If sources are
 * {@link IPackageFragmentRoot#attachSource(IPath, IPath, org.eclipse.core.runtime.IProgressMonitor)
 * attached} to the library, the use of the
 * {@link LibraryReaderOptions#USE_SOURCES USE_SOURCES} option will cause the
 * creation of the model from the sources, relying on a {@code JavaReader}.
 * </p>
 * 
 * @see JavaReader
 */
public class LibraryReader implements IModelReader {

	/**
	 * the EMF factory.
	 */
	private final JavaFactory factory;

	/**
	 * the resulting model.
	 */
	private Model resultModel;

	/**
	 * the global {@code BindingManager}.
	 */
	private BindingManager globalBindings;

	/**
	 * some options for this reader.
	 */
	@SuppressWarnings("unused")
	private final Map<String, Object> options;

	/**
	 * a {@code TypeFinder}.
	 */
	private TypeFinder typeFinder;

	/**
	 * indicate if the user wanted to analyse the sources
	 */
	private final boolean useSources;

	/**
	 * Contructs a new {@code LibraryReader} with no options.
	 * 
	 * @param factory
	 *            the EMF factory
	 * @param library
	 *            the library
	 */
	public LibraryReader(final JavaFactory factory) {
		this(factory, new HashMap<String, Object>());
	}

	/**
	 * Contructs a new {@code LibraryReader} with options.
	 * 
	 * @param factory
	 *            the EMF factory
	 * @param library
	 *            the library to analyse
	 * @param options
	 *            the {@link LibraryReaderOptions options} of this
	 *            {@code LibraryReader}
	 */
	public LibraryReader(final JavaFactory factory, final Map<String, Object> options) {
		this.factory = factory;
		this.options = options;
		this.useSources = Boolean.TRUE.equals(options.get(LibraryReaderOptions.USE_SOURCES
				.toString()));
	}

	public void readModel(final Object source, final Model resultModel1,
			final IProgressMonitor monitor) {
		readModel(source, resultModel1, getBindingManager(), monitor);
	}

	public void readModel(final Object source, final Model resultModel1,
			final BindingManager globalBindings1, final IProgressMonitor monitor) {

		if (source == null) {
			return;
		}

		this.resultModel = resultModel1;
		this.globalBindings = globalBindings1;
		ClassFileParserUtils.initializePrimitiveTypes(this.factory, resultModel1,
				this.globalBindings);
		try {

			if (source instanceof IPackageFragmentRoot) {
				IPackageFragmentRoot library = (IPackageFragmentRoot) source;

				if (resultModel1.getName() == null || resultModel1.getName().length() == 0) {
					resultModel1.setName(library.getElementName());
				}
				this.typeFinder = new TypeFinder(library.getJavaProject());

				IJavaElement[] children = library.getChildren();
				for (IJavaElement element : children) {
					IPackageFragment packageFolder = (IPackageFragment) element;
					if (packageFolder.getClassFiles().length > 0) {
						// report some feedback
						monitor.subTask(Messages.LibraryReader_DiscoveringTask
								+ packageFolder.getElementName());
						// parse package
						parsePackage(library, resultModel1, packageFolder, monitor);
						if (monitor.isCanceled()) {
							return;
						}
					}
				}
			} else if (source instanceof IClassFile) {
				IClassFile cf = (IClassFile) source;
				this.typeFinder = new TypeFinder(cf.getJavaProject());
				parseClassFile(cf);

			} else {
				throw new IllegalArgumentException(
						"Library reader can not handle source object : " + source.toString()); //$NON-NLS-1$
			}
		} catch (Exception e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
		}
	}

	protected void parsePackage(final IPackageFragmentRoot library, final Model resultModel1,
			final IPackageFragment parent, final IProgressMonitor monitor)
			throws JavaModelException {

		IClassFile[] children = parent.getClassFiles();
		for (IClassFile cf : children) {
			parseClassFile(cf);
			if (monitor.isCanceled()) {
				return;
			}
		}
	}

	protected void parseClassFile(final IClassFile classFile) {
		try {
			IType type = classFile.getType();
			// we want only top level types
			if (type != null && type.exists() && !type.isAnonymous() && !type.isLocal()
					&& !type.isMember()) {
				String filePath = getPath(classFile);
				visitClassFile(classFile, filePath);
			}

		} catch (Exception e) {
			MoDiscoLogger.logError(e, JavaActivator.getDefault());
		}
	}

	protected void visitClassFile(final IClassFile classFile, final String filePath)
			throws JavaModelException {

		boolean classFileHasSource = false;
		String fileContent = null;

		// test if user wants to analyse source and if this class file has an
		// attached source
		if (this.useSources) {
			fileContent = classFile.getSource();
			if (fileContent != null) {
				classFileHasSource = true;
			}
		}

		if (classFileHasSource) {
			// source code retrieved, delegate model creation to a JavaReader
			IModelReader javaReader = new JavaReader(this.factory, null, null);
			javaReader.readModel(classFile, this.resultModel, this.globalBindings,
					new NullProgressMonitor());
		} else {
			// no source has been retrieved
			ClassFileParser jdtVisitor = new ClassFileParser(this.factory, this.resultModel,
					this.globalBindings, this.typeFinder, filePath);
			jdtVisitor.parse(classFile);
		}
	}

	protected void resolveMethodRedefinition(final Model resultModel1) {
		MethodRedefinitionManager.resolveMethodRedefinitions(resultModel1, this.factory);
	}

	protected void finalResolveBindings(final Model resultModel1) {
		this.globalBindings.resolveBindings(resultModel1);
	}

	protected BindingManager getBindingManager() {
		BindingManager bindingManager = new BindingManager(this.factory);
		return bindingManager;

	}

	public void terminate(final IProgressMonitor monitor) {
		monitor.subTask(Messages.LibraryReader_BindingTask);
		finalResolveBindings(this.resultModel);

		monitor.subTask(Messages.LibraryReader_RedefinitionsTask);
		resolveMethodRedefinition(this.resultModel);
	}

	/**
	 * Returns the {@link Archive} object which corresponds to the
	 * {@link IPackageFragmentRoot#isArchive() archive} in which this class file
	 * is contained. If a corresponding archive is present in the {@code model},
	 * it is returned, or a new one is created and added to the {@code model}.
	 * 
	 * @param classFile
	 *            the class file
	 * @param factory
	 *            the EMF factory
	 * @param model
	 *            the {@code Model}
	 * @return the {@code Archive object}, or {@code null} if {@code classFile}
	 *         is not contained in an archive
	 */
	public static Archive getArchive(final IClassFile classFile, final JavaFactory factory,
			final Model model) {
		Archive archive = null;
		IPackageFragmentRoot root = (IPackageFragmentRoot) classFile
				.getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT);
		if (root != null && root.isArchive()) {
			String libraryPath = getPath(root);
			// class file is in a library
			for (Archive itElement : model.getArchives()) {
				if (itElement.getOriginalFilePath().equals(libraryPath)) {
					return itElement;
				}
			}
			// if non present in model, create a new one
			archive = factory.createArchive();
			archive.setName(root.getElementName());
			archive.setOriginalFilePath(libraryPath);
			ManifestReader.completeArchiveWithManifest(root, archive, factory);
			model.getArchives().add(archive);
		}
		return archive;
	}

	/**
	 * Returns the archive-relative path of the class file. If this class file
	 * is in an archive (workspace or external), the path will be the path
	 * inside the archive. If it is in a folder (workspace or external), the
	 * path will be the full absolute path to this class file.
	 * 
	 * @param classFile
	 *            the class file
	 * @return the archive-relative path
	 */
	public static String getPath(final IClassFile classFile) {
		IPackageFragmentRoot library = (IPackageFragmentRoot) classFile
				.getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT);

		String filePath = null;
		if (library.isArchive()) { // zip or jar
			IPackageFragment parent = (IPackageFragment) classFile.getParent();
			String packagePath = parent.getElementName().replace('.', '/');
			filePath = '/' + packagePath + '/' + classFile.getElementName();
		} else { // folder
			if (library.isExternal()) {
				filePath = classFile.getPath().toOSString();
			} else {
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(classFile.getPath());
				filePath = file.getLocation().toOSString();
			}
		}
		return filePath;
	}

	/**
	 * Returns the absolute path of this library in the filesystem.
	 * 
	 * @param library
	 *            the library
	 * @return the absolute path of this library
	 */
	public static String getPath(final IPackageFragmentRoot library) {
		String filePath = library.getPath().toOSString();
		// non external resources are relative to the workspace
		if (!library.isExternal()) {
			IResource resource = null;
			if (library.isArchive()) { // zip or jar
				resource = ResourcesPlugin.getWorkspace().getRoot().getFile(library.getPath());
			} else { // folder
				resource = ResourcesPlugin.getWorkspace().getRoot().getFolder(library.getPath());
			}
			filePath = resource.getLocation().toOSString();
		}
		return filePath;
	}

	/**
	 * @see org.eclipse.jdt.core.ISourceReference#getSource()
	 */
	public static String getFileContent(final IClassFile classFile) {
		String source = null;
		try {
			source = classFile.getSource();
		} catch (JavaModelException e) {
			// Nothing
			assert (true); // dummy code for "EmptyBlock" rule
		}
		return source;
	}
}
