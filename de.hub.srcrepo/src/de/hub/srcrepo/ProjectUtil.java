/*******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and others.
 * Copyright (C) 2007, Martin Oberhuber (martin.oberhuber@windriver.com)
 * Copyright (C) 2008, Robin Rosenberg <robin.rosenberg@dewire.com>
 * Copyright (C) 2010, Jens Baumgart <jens.baumgart@sap.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.hub.srcrepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set; 

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.CoreText;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.osgi.util.NLS;

/**
 * This class contains utility methods related to projects
 * TODO: rename to RefreshUtil or ResourceUtil?
 */
public class ProjectUtil {

	/**
	 * The name of the folder containing metadata information for the workspace.
	 */
	public static final String METADATA_FOLDER = ".metadata"; //$NON-NLS-1$

	/**
	 * The method returns all valid open projects contained in the given Git
	 * repository. A project is considered as valid if the .project file exists.
	 * @see ProjectUtil#refreshValidProjects(IProject[], IProgressMonitor)
	 * @param repository
	 * @return valid open projects
	 * @throws CoreException
	 */
	public static IProject[] getValidOpenProjects(Repository repository)
			throws CoreException {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		List<IProject> result = new ArrayList<IProject>();
		final File parentFile = repository.getWorkTree();
		for (IProject p : projects) {
			IPath projectLocation = p.getLocation();
			if (!p.isOpen() || projectLocation == null)
				continue;
			String projectFilePath = projectLocation.append(
					IProjectDescription.DESCRIPTION_FILE_NAME).toOSString();
			File projectFile = new File(projectFilePath);
			if (projectFile.exists()) {
				final File file = p.getLocation().toFile();
				if (file.getAbsolutePath().startsWith(
						parentFile.getAbsolutePath())) {
					result.add(p);
				}
			}
		}
		return result.toArray(new IProject[result.size()]);
	}

	/**
	 * The method refreshes the given projects. Projects with missing .project
	 * file are deleted. The method should be called in the following flow:<br>
	 * <ol>
	 * <li>Call {@link ProjectUtil#getValidOpenProjects(Repository)}
	 * <li>Perform a workdir checkout (e.g. branch, reset)
	 * <li>Call
	 * {@link ProjectUtil#refreshValidProjects(IProject[], IProgressMonitor)}
	 * </ol>
	 *
	 * @param projects
	 *            list of valid projects before workdir checkout.
	 * @param monitor
	 * @throws CoreException
	 */
	public static void refreshValidProjects(IProject[] projects,
			IProgressMonitor monitor) throws CoreException {
		refreshValidProjects(projects, true, monitor);
	}

	/**
	 * The method refreshes the given projects. Projects with missing .project
	 * file are deleted. The method should be called in the following flow:<br>
	 * <ol>
	 * <li>Call {@link ProjectUtil#getValidOpenProjects(Repository)}
	 * <li>Perform a workdir checkout (e.g. branch, reset)
	 * <li>Call
	 * {@link ProjectUtil#refreshValidProjects(IProject[], IProgressMonitor)}
	 * </ol>
	 *
	 * @param projects
	 *            list of valid projects before workdir checkout.
	 * @param delete
	 *            true to delete projects, false to close them
	 * @param monitor
	 *
	 * @throws CoreException
	 */
	public static void refreshValidProjects(IProject[] projects,
			boolean delete, IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(CoreText.ProjectUtil_refreshingProjects,
					projects.length);
			for (IProject p : projects) {
				if (monitor.isCanceled())
					break;
				IPath projectLocation = p.getLocation();
				if (projectLocation == null)
					continue;
				String projectFilePath = projectLocation.append(
						IProjectDescription.DESCRIPTION_FILE_NAME).toOSString();
				File projectFile = new File(projectFilePath);
				if (projectFile.exists())
					p.refreshLocal(IResource.DEPTH_INFINITE,
							new SubProgressMonitor(monitor, 1));
				else if (delete)
					p.delete(false, true, new SubProgressMonitor(monitor, 1));
				else
					closeMissingProject(p, projectFile, monitor);
				monitor.worked(1);
			}
		} finally {
			monitor.done();
		}
	}

	/**
	 * Close a project that has already been deleted on disk. This will fall
	 * back to deleting the project if it cannot be successfully closed.
	 * <p>
	 * Closing a missing project involves creating a temporary '.project' file
	 * since only existing projects can be closed
	 *
	 * @param p
	 * @param projectFile
	 * @param monitor
	 * @throws CoreException
	 */
	private static void closeMissingProject(IProject p, File projectFile,
			IProgressMonitor monitor) throws CoreException {
		// Create temporary .project file so it can be closed
		boolean closeFailed = false;
		File projectRoot = projectFile.getParentFile();
		if (!projectRoot.isFile()) {
			boolean hasRoot = projectRoot.exists();
			try {
				if (!hasRoot)
					FileUtils.mkdirs(projectRoot, true);
				if (projectFile.createNewFile())
					p.close(new SubProgressMonitor(monitor, 1));
				else
					closeFailed = true;
			} catch (IOException e) {
				closeFailed = true;
			} finally {
				// Clean up created .project file
				try {
					FileUtils.delete(projectFile, FileUtils.RETRY
							| FileUtils.SKIP_MISSING);
				} catch (IOException e) {
					closeFailed = true;
				}
				// Clean up created folder
				if (!hasRoot)
					try {
						FileUtils.delete(projectRoot, FileUtils.RETRY
								| FileUtils.SKIP_MISSING | FileUtils.RECURSIVE);
					} catch (IOException e) {
						closeFailed = true;
					}
			}
		} else
			closeFailed = true;
		// Delete projects that can't be closed
		if (closeFailed)
			p.delete(false, true, new SubProgressMonitor(monitor, 1));
	}

	/**
	 * The method refreshes resources
	 *
	 * @param resources
	 *            resources to refresh
	 * @param monitor
	 * @throws CoreException
	 */
	public static void refreshResources(IResource[] resources,
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask(CoreText.ProjectUtil_refreshing,
					resources.length);
			for (IResource resource : resources) {
				if (monitor.isCanceled())
					break;
				resource.refreshLocal(IResource.DEPTH_INFINITE,
						new SubProgressMonitor(monitor, 1));
				monitor.worked(1);
			}
		} finally {
			monitor.done();
		}

	}

	/**
	 * The method retrieves all accessible projects related to the given
	 * repository
	 *
	 * @param repository
	 * @return list of projects
	 */
	public static IProject[] getProjects(Repository repository) {
		List<IProject> result = new ArrayList<IProject>();
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (IProject project : projects) {
			if (project.isAccessible()) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(project);
				if (mapping != null && mapping.getRepository() == repository)
					result.add(project);
			}
		}
		return result.toArray(new IProject[result.size()]);
	}

	/**
	 * Find directories containing .project files recursively starting at given
	 * directory
	 *
	 * @param files
	 * @param directory
	 * @param visistedDirs
	 * @param monitor
	 * @return true if projects files found, false otherwise
	 */
	public static boolean findProjectFiles(final Collection<File> files,
			final File directory, final Set<String> visistedDirs,
			final IProgressMonitor monitor) {
		if (directory == null)
			return false;

		if (directory.getName().equals(Constants.DOT_GIT)
				&& FileKey.isGitRepository(directory, FS.DETECTED))
			return false;

		IProgressMonitor pm = monitor;
		if (pm == null)
			pm = new NullProgressMonitor();
		else if (pm.isCanceled())
			return false;

		pm.subTask(NLS.bind(CoreText.ProjectUtil_taskCheckingDirectory,
				directory.getPath()));

		final File[] contents = directory.listFiles();
		if (contents == null || contents.length == 0)
			return false;

		Set<String> directoriesVisited;
		// Initialize recursion guard for recursive symbolic links
		if (visistedDirs == null) {
			directoriesVisited = new HashSet<String>();
			try {
				directoriesVisited.add(directory.getCanonicalPath());
			} catch (IOException exception) {
				Activator.logError(exception.getLocalizedMessage(), exception);
			}
		} else
			directoriesVisited = visistedDirs;

		// first look for project description files
		final String dotProject = IProjectDescription.DESCRIPTION_FILE_NAME;
		for (int i = 0; i < contents.length; i++) {
			File file = contents[i];
			if (file.isFile() && file.getName().equals(dotProject)) {
				files.add(file);
				// don't search sub-directories since we can't have nested
				// projects
				return true;
			}
		}
		// no project description found, so recurse into sub-directories
		for (int i = 0; i < contents.length; i++) {
			// Skip non-directories
			if (!contents[i].isDirectory())
				continue;
			// Skip .metadata folders
			if (contents[i].getName().equals(METADATA_FOLDER))
				continue;
			try {
				String canonicalPath = contents[i].getCanonicalPath();
				if (!directoriesVisited.add(canonicalPath)) {
					// already been here --> do not recurse
					continue;
				}
			} catch (IOException exception) {
				Activator.logError(exception.getLocalizedMessage(), exception);

			}
			findProjectFiles(files, contents[i], directoriesVisited, pm);
		}
		return true;
	}
}
