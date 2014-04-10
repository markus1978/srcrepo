package de.hub.srcrepo;

import org.junit.Test;

import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;

public class EmfFragResumeMongoDBSrcRepoTest extends EmfFragMongoDBSrcRepoTest {

	@Override
	@Test
	public void importTest() {
		Configuration configuration = prepareConfiguration();
		configuration.stopAfterNumberOfRevs(5);
		assertRepositoryModel(EmfFragSrcRepoImport.importRepository(configuration), 5);
	}
	
	@Test
	public void resumeImpotTest() {
		Configuration configuration = prepareConfiguration();
		configuration.resume(true);
		assertRepositoryModel(EmfFragSrcRepoImport.importRepository(configuration), 14);
	}

}
