package de.hub.srcrepo;

import org.eclipse.emf.common.util.URI;

import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;

public class EmfFragMongoDBSrcRepoTest extends EmfFragMemorySrcRepoGitTest {
	
	public static URI testModelURI = URI.createURI("mongodb://localhost/srcrepo.example.gitmodel.bin");
	
	@Override
	protected URI getTestRepositoryModelURI() {
		return testModelURI;
	}		
	
	@Override
	public void loadDependencies() {
		super.loadDependencies();
		EmfFragMongoDBActivator.class.getName();
	}

	@Override
	protected boolean useBinaryFragments() {
		return true;
	}	
	
}
