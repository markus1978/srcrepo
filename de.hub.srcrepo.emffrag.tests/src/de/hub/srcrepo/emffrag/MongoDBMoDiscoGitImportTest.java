package de.hub.srcrepo.emffrag;

import static de.hub.srcrepo.RepositoryModelUtil.getDataStoreMetaData;
import static de.hub.srcrepo.RepositoryModelUtil.getMetaData;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;
import org.junit.Assert;

import com.google.common.collect.Lists;

import de.hub.emffrag.Fragmentation;
import de.hub.emffrag.FragmentationImpl;
import de.hub.emffrag.mongodb.MongoDBDataStore;
import de.hub.srcrepo.MoDiscoGitImportTest;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

public class MongoDBMoDiscoGitImportTest extends MoDiscoGitImportTest {	
	
	public static URI testModelURI = URI.createURI("mongodb://localhost/srcrepo.example.gitmodel");
	
	private Fragmentation fragmentation = null;
	
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
		EmfFragSrcRepoImport.importRepository(prepareConfiguration());
		FragmentationImpl fragmentation = new FragmentationImpl(Lists.newArrayList(RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE), MongoDBDataStore.createDataStore(testModelURI, false), 1);
		assertRepositoryModel(((RepositoryModel)fragmentation.getRoot()), 16);
		fragmentation.close();
	}

	@Override
	protected RepositoryModel openRepositoryModel(boolean dropExisting) {
		fragmentation = EmfFragSrcRepoImport.openFragmentation(prepareConfiguration(), dropExisting);
		return (RepositoryModel) fragmentation.getRoot();
	}

	@Override
	protected void closeRepositoryModel(RepositoryModel model) {
		EmfFragSrcRepoImport.closeFragmentation(prepareConfiguration(), fragmentation);
	}

	@Override
	protected void assertMetaData(RepositoryModel repositoryModel) {
		super.assertMetaData(repositoryModel);
		Assert.assertNotNull(getDataStoreMetaData(repositoryModel));
		Assert.assertTrue(getDataStoreMetaData(repositoryModel).getCount() >= 
				(getMetaData(repositoryModel).getRevCount() + getMetaData(repositoryModel).getCuCount()));
	}
	
	
}
