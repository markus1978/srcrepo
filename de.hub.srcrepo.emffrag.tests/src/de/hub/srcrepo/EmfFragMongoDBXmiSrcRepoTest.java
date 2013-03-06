package de.hub.srcrepo;

import org.eclipse.emf.common.util.URI;

public class EmfFragMongoDBXmiSrcRepoTest extends EmfFragMongoDBSrcRepoTest {
	
	@Override
	protected URI getTestSourceModelURI() {
		return URI.createURI("mongodb://localhost/example.java.gitmodel.xmi");
	}		
	
	@Override
	protected boolean useBinaryFragments() {
		return false;
	}

}
