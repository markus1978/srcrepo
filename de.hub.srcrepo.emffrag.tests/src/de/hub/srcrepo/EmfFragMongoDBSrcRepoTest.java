package de.hub.srcrepo;

import org.eclipse.emf.common.util.URI;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.emffrag.mongodb.MongoDBUtil;

public class EmfFragMongoDBSrcRepoTest extends EmfFragSrcRepoTest {
	
	@Override
	protected URI getTestSourceModelURI() {
		return URI.createURI("mongodb://localhost/example.java.gitmodel.bin");
	}		
	
	@Override
	protected void loadDependencies() {
		super.loadDependencies();
		EmfFragMongoDBActivator.class.getName();
	}
	
	@Override
	public void init() {
		super.init();
		EmfFragActivator.instance.collectStatistics = true;
	}

	protected boolean useBinaryFragments() {
		return true;
	}

	@Override
	public void testImportJavaGitModel() throws Exception {
		MongoDBUtil.dropCollection(getTestSourceModelURI());
		super.testImportJavaGitModel();
	}	

	
}
