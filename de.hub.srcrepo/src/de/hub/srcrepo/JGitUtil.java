package de.hub.srcrepo;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.util.FileUtils;

public class JGitUtil {

	/**
	 * Clones the repository from the given URI and deletes any existing clone first.
	 */
	public static Git clone(String uri, String localPath) throws IOException {
		CloneCommand cloneCommand = new CloneCommand();
		cloneCommand.setCloneAllBranches(true);
		cloneCommand.setRemote("origin");
		cloneCommand.setURI(uri);	
		File directory = new File(localPath);
		if (directory.exists()) {
			FileUtils.delete(directory);
		}
		cloneCommand.setDirectory(directory);
		try {
			return cloneCommand.call();
		} catch (JGitInternalException e) {
			if (e.getCause() != null && e.getCause() instanceof IOException) {
				throw (IOException)e.getCause();
			} else {
				throw e;
			}
		}
	}
}
