package de.hub.srcrepo.emffrag;

import static de.hub.srcrepo.RepositoryModelUtil.getDataStoreMetaData;
import static de.hub.srcrepo.RepositoryModelUtil.getMetaData;

import org.eclipse.emf.common.util.URI;
import org.junit.Assert;

import de.hub.emffrag.FObject;
import de.hub.emffrag.Fragmentation;
import de.hub.srcrepo.MoDiscoGitImportTest;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory;

public class MongoDBMoDiscoGitImportTest extends MoDiscoGitImportTest {	
	
	public static URI testModelURI = URI.createURI("mongodb://localhost/srcrepo.example.gitmodel");
	
	private Fragmentation fragmentation = null;
	
	public final static URI testJavaModelURI = URI.createURI("mongodb://localhost/srcrepo.example.java");
	public final static URI testGitModelURI = URI.createURI("mongodb://localhost/srcrepo.example.git");
	
	@Override
	protected URI getTestModelURI(TestModelKind kind) {
		if (kind == TestModelKind.GIT) {
			return testGitModelURI;
		} else {
			return testJavaModelURI;
		}
	}
			
	protected Configuration prepareConfiguration(TestModelKind kind) {
		String repositoryURL = null;
		if (!getWorkingCopy().exists() || !onlyCloneIfNecessary()) {
			repositoryURL = getCloneURL();
		}		
		URI modelURI = getTestModelURI(kind);
		
		Configuration configuration = new EmfFragSrcRepoImport.GitConfiguration(getWorkingCopy(), modelURI).repositoryURL(repositoryURL);
		configuration.useCGit();
		configuration.fragmentCacheSize(1);
		return configuration;
	}

	@Override
	protected void importJavaFromModisco(RepositoryModel repositoryModel) {
		closeRepositoryModel(TestModelKind.JAVA, repositoryModel);
		EmfFragSrcRepoImport.importRepository(prepareConfiguration(TestModelKind.JAVA));
		openRepositoryModel(TestModelKind.JAVA, false);
	}

	@Override
	protected RepositoryModel openRepositoryModel(TestModelKind kind, boolean dropExisting) {
		fragmentation = EmfFragSrcRepoImport.openFragmentation(prepareConfiguration(kind), dropExisting);
		RepositoryModel repositoryModel = (RepositoryModel) fragmentation.getRoot();
		if (repositoryModel == null && dropExisting) {
			repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel();
			fragmentation.setRoot((FObject)repositoryModel);
		}
		return repositoryModel;
	}

	@Override
	protected void closeRepositoryModel(TestModelKind kind, RepositoryModel model) {
		EmfFragSrcRepoImport.closeFragmentation(prepareConfiguration(TestModelKind.JAVA), fragmentation);
	}

	@Override
	protected void assertMetaData(RepositoryModel repositoryModel) {
		super.assertMetaData(repositoryModel);
		Assert.assertNotNull(getDataStoreMetaData(repositoryModel));
		Assert.assertTrue(getDataStoreMetaData(repositoryModel).getCount() >= 
				(getMetaData(repositoryModel).getRevCount() + getMetaData(repositoryModel).getCuCount()));
	}
	
	
}
