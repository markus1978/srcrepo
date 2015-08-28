package de.hub.srcrepo.emffrag;

import org.junit.Test;

import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;

public class ResumeMongoDBMoDiscoGitImportTest extends MongoDBMoDiscoGitImportTest {

	@Override
	protected void runImport() {
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
