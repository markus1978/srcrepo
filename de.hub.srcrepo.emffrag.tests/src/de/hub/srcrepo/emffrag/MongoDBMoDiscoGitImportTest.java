package de.hub.srcrepo.emffrag;


import org.eclipse.emf.common.util.URI;

import de.hub.emffrag.fragmentation.Fragment;
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
		
		return new EmfFragSrcRepoImport.GitConfiguration(getWorkingCopy(), modelURI).
				repositoryURL(repositoryURL);
	}

	@Override
	protected void runImport() {
		RepositoryModel repositoryModel = EmfFragSrcRepoImport.importRepository(prepareConfiguration());
		assertRepositoryModel(repositoryModel, 16);
	}

	@Override
	protected RepositoryModel openRepositoryModel(boolean dropExisting) {
		return (RepositoryModel) EmfFragSrcRepoImport.openFragmentation(prepareConfiguration(), dropExisting).getRootFragment().getContents().get(0);
	}

	@Override
	protected void closeRepositoryModel(RepositoryModel model) {
		EmfFragSrcRepoImport.closeFragmentation(prepareConfiguration(), ((Fragment)model.eResource()).fFragmentation());
	}
}
