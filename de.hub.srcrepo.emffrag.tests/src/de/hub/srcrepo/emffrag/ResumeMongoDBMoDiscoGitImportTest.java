package de.hub.srcrepo.emffrag;

import org.junit.Test;

import de.hub.emffrag.FragmentationImpl;
import de.hub.emffrag.mongodb.MongoDBDataStore;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;
import de.hub.srcrepo.repositorymodel.RepositoryModel;

public class ResumeMongoDBMoDiscoGitImportTest extends MongoDBMoDiscoGitImportTest {	
	
	@Override
	protected void assertMetaData(RepositoryModel repositoryModel) {

	}

	@Override
	protected void runImport() {
		Configuration configuration = prepareConfiguration();
		configuration.stopAfterNumberOfRevs(5);
		
		EmfFragSrcRepoImport.importRepository(configuration);
		
		FragmentationImpl fragmentation = new FragmentationImpl(EmffragSrcRepo.packages, MongoDBDataStore.createDataStore(testModelURI, false), 1);
		assertRepositoryModel(((RepositoryModel)fragmentation.getRoot()), 5);
		fragmentation.close();
	}
	
	@Test
	public void resumeImpotTest() {
		Configuration configuration = prepareConfiguration();
		configuration.resume(true);
		
		EmfFragSrcRepoImport.importRepository(configuration);
		
		FragmentationImpl fragmentation = new FragmentationImpl(EmffragSrcRepo.packages, MongoDBDataStore.createDataStore(testModelURI, false), 1);
		assertRepositoryModel(((RepositoryModel)fragmentation.getRoot()), 14);
		fragmentation.close();
	}
}
