package de.hub.srcrepo.emffrag;

import org.eclipse.emf.common.util.URI;

public class MongoDBXmiMoDiscoGitImportTest extends MongoDBMoDiscoGitImportTest {
	
	@Override
	protected URI getTestModelURI() {
		return URI.createURI("mongodb://localhost/srcrepo.example.java.gitmodel.xmi");
	}		
	
	@Override
	protected boolean useBinaryFragments() {
		return false;
	}

}
