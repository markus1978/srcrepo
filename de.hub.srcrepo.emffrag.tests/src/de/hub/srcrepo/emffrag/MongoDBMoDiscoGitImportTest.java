package de.hub.srcrepo.emffrag;

import org.eclipse.emf.common.util.URI;

import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;

public class MongoDBMoDiscoGitImportTest extends MemoryMoDiscoGitImportTest {
	
	public static URI testModelURI = URI.createURI("mongodb://localhost/srcrepo.example.gitmodel.bin");
	
	@Override
	protected URI getTestModelURI() {
		return testModelURI;
	}		
	
	@Override
	public void initialize() {
		super.initialize();
		EmfFragMongoDBActivator.class.getName();
	}

	@Override
	protected boolean useBinaryFragments() {
		return true;
	}
	
	
}
