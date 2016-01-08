package de.hub.srcrepo.emffrag;

import static de.hub.srcrepo.RepositoryModelUtil.getDataStoreMetaData;
import static de.hub.srcrepo.RepositoryModelUtil.getMetaData;

import org.eclipse.emf.common.util.URI;
import org.junit.Assert;

import de.hub.emffrag.FObject;
import de.hub.srcrepo.MoDiscoGitImportTest;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;
import de.hub.srcrepo.repositorymodel.RepositoryModel;

public class MongoDBMoDiscoGitImportTest extends MoDiscoGitImportTest {	
	
	public static URI testModelURI = URI.createURI("mongodb://localhost/srcrepo.example.gitmodel");
	
	@Override
	protected URI getTestModelURI() {
		return testModelURI;
	}		
	
	protected Configuration prepareConfiguration() {
		String repositoryURL = null;
		if (!getWorkingCopy().exists() || !onlyCloneIfNecessary()) {
			repositoryURL = getCloneURL();
		}		
		URI modelURI = getTestModelURI();
		
		Configuration configuration = new EmfFragSrcRepoImport.GitConfiguration(getWorkingCopy(), modelURI).repositoryURL(repositoryURL);
		configuration.useCGit();
		configuration.fragmentCacheSize(1);
		return configuration;
	}

	@Override
	protected void runImport() {
		RepositoryModel repositoryModel = EmfFragSrcRepoImport.importRepository(prepareConfiguration());
		assertRepositoryModel(repositoryModel, 16);
	}

	@Override
	protected RepositoryModel openRepositoryModel(boolean dropExisting) {
		return (RepositoryModel) EmfFragSrcRepoImport.openFragmentation(prepareConfiguration(), dropExisting).getRoot();
	}

	@Override
	protected void closeRepositoryModel(RepositoryModel model) {
		EmfFragSrcRepoImport.closeFragmentation(prepareConfiguration(), ((FObject)model).fFragmentation());
	}

	@Override
	protected void assertMetaData(RepositoryModel repositoryModel) {
		super.assertMetaData(repositoryModel);
		Assert.assertNotNull(getDataStoreMetaData(repositoryModel));
		Assert.assertTrue(getDataStoreMetaData(repositoryModel).getCount() >= 
				(getMetaData(repositoryModel).getRevCount() + getMetaData(repositoryModel).getCuCount()));
	}
	
	
}
