package de.hub.srcrepo;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.util.FileUtils;

import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.SourceRepository;
import de.hub.srcrepo.gitmodel.util.GitModelUtil;
import de.hub.srcrepo.gitmodel.util.GitModelUtil.Direction;

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
			FileUtils.delete(directory, FileUtils.RECURSIVE);
		}
		cloneCommand.setDirectory(directory);
		try {
			return cloneCommand.call();
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause() instanceof IOException) {
				throw (IOException)e.getCause();
			} else {
				throw new RuntimeException(e);
			}
		}
	}
	
	public interface ImportConfiguration {
		public void configure(Resource model);
		public JavaPackage getJavaPackage();
		public GitModelPackage getGitPackage();
		public SourceRepository createSourceRepository();
	}
	
	public static Resource importGit(String cloneURL, String workingDirectory, URI modelURI, ImportConfiguration config) throws Exception {
		return importGit(cloneURL, workingDirectory, modelURI, "", config);
	}
	
	public static Resource importGit(String cloneURL, String workingDirectory, URI modelURI, String lastCommit, ImportConfiguration config) throws Exception {
		lastCommit = lastCommit == null ? "" : lastCommit;
		ResourceSet rs = new ResourceSetImpl();
		Resource model = rs.createResource(modelURI);
		config.configure(model);
		
		SourceRepository gitModel = config.createSourceRepository();
		Model javaModel = ((JavaFactory)config.getJavaPackage().getEFactoryInstance()).createModel();
		model.getContents().add(gitModel);
		model.getContents().add(javaModel);
		
		// create git and clone repository
		Git git = null;		
		git = JGitUtil.clone(cloneURL, workingDirectory);
		git = Git.open(new File(workingDirectory));

		
		// import the git commit structure
		JGitModelImport modelImport = new JGitModelImport(git, gitModel);
		modelImport.runImport();
		
		// visit the git commits and import java on the fly		
		MoDiscoGitModelImportVisitor visitor = new MoDiscoGitModelImportVisitor(git, gitModel, javaModel, lastCommit);
		GitModelUtil.visitCommitHierarchy(gitModel.getRootCommit(), Direction.FROM_PARENT, visitor);
		
		// save the resulting model in its resource
		model.save(null);
		
		return model;
	}
}
