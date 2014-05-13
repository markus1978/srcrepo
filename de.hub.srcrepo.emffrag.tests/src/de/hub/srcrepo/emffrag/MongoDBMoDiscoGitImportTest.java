package de.hub.srcrepo.emffrag;

import java.io.File;

import org.eclipse.emf.common.util.URI;

import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;

public class MongoDBMoDiscoGitImportTest extends MemoryMoDiscoGitImportTest {
	
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
	
	
	// temporary test of import large repo without source code
	public static File workingDirectory = new File("C:/tmp/srcrepo/clones/org.eclipse.emf.git");
	
	protected File getWorkingDirectory() {
		return workingDirectory;
	}
	
	protected Configuration prepareConfiguration() {
		return super.prepareConfiguration().skipSourceCodeImport();
	}
	
	
}
