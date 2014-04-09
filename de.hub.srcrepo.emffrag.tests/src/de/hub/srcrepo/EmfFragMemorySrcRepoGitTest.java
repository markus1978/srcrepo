package de.hub.srcrepo;

import java.io.File;

import junit.framework.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hub.emffrag.EmfFragActivator;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport;
import de.hub.srcrepo.repositorymodel.RepositoryModel;

public class EmfFragMemorySrcRepoGitTest {
	
	public static File workingDirectory = new File("C:/tmp/srcrepo/clones/srcrepo.example.git");
	
	protected URI getTestRepositoryModelURI() {
		return URI.createURI("memory://localhost/srcrepos.example.gitmodel.bin");
	}
	
	@Before
	public void loadDependencies() {
		EmfFragActivator.class.getName();
		SrcRepoActivator.class.getName();		
	}
	
	@Before
	public void clearWorkspace() {
		if (workingDirectory.exists()) {
			try {
				IProject[] projects = ProjectUtil.getValidOpenProjects(workingDirectory);
				for (IProject iProject : projects) {				
					iProject.delete(true, new NullProgressMonitor());		
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected boolean useBinaryFragments() {
		return true;
	}
	
	@Test
	public void importTest() {		
		String repositoryURL = null;
		if (!workingDirectory.exists()) {
			repositoryURL = "https://github.com/markus1978/srcrepo.example.git";
		}		
		URI modelURI = getTestRepositoryModelURI();
		
		RepositoryModel importedRepository = EmfFragSrcRepoImport.importRepository(
				new EmfFragSrcRepoImport.GitConfiguration(workingDirectory, modelURI).
				repositoryURL(repositoryURL).
				withBinaryResources(useBinaryFragments()));
		
		Assert.assertNotNull(importedRepository);
		Assert.assertTrue(importedRepository.getAllRevs().size() > 10);
	}
	
}
