package de.hub.srcrepo;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import de.hub.emffrag.EmfFragActivator;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport;

public class EmfFragMemorySrcRepoGitTest {
	
	protected URI getTestRepositoryModelURI() {
		return URI.createURI("memory://localhost/srcrepos.example.repositorymodel.bin");
	}		
	
	@Before
	public void loadDependencies() {
		EmfFragActivator.class.getName();
		SrcRepoActivator.class.getName();		
	}
	
	protected boolean useBinaryFragments() {
		return true;
	}
	
	@Test
	public void importTest() {
		File workingDirectory = new File("../../../01_tmp/srcrepo/clones/srcrepo.example.git");
		String repositoryURL = "https://github.com/markus1978/srcrepo.example.git";
		URI modelURI = getTestRepositoryModelURI();
		
		EmfFragSrcRepoImport.importRepository(
				new GitSourceControlSystem(), 
				workingDirectory, 
				repositoryURL, 
				modelURI, 
				useBinaryFragments(), false, false, 1000, 1000, null);
	}
	
}
