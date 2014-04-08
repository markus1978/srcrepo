package de.hub.srcrepo;

import org.eclipse.emf.common.util.URI;

import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;

public class EmfFragMongoDBSrcRepoTest extends EmfFragMemorySrcRepoGitTest {
	
	@Override
	protected URI getTestRepositoryModelURI() {
		return URI.createURI("mongodb://localhost/example.java.gitmodel.bin");
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
