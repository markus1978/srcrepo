package de.hub.srcrepo.emffrag;

import java.io.File;

import de.hub.srcrepo.EmptyRepositoryModelVisitor;
import de.hub.srcrepo.GitSourceControlSystem;
import de.hub.srcrepo.IRepositoryModelVisitor;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;
import de.hub.srcrepo.repositorymodel.RepositoryModel;

public class MongoDBMoDiscoGitEmptyCDOImportTest extends MongoDBMoDiscoGitImportTest {
	
	public final static File workingCopy = new File("c:/tmp/srcrepo/clones/cdo.git");
	
	@Override
	protected File getWorkingCopy() {
		return workingCopy;
	}
	
	@Override
	protected boolean onlyCloneIfNecessary() {
		return true;
	}

	@Override
	protected Configuration prepareConfiguration() {	
		return super.prepareConfiguration().withRepositoryModelVisitorFactory(new EmfFragSrcRepoImport.RepositoryModelVisitorFactory() {			
			@Override
			public IRepositoryModelVisitor createRepositoryModelVisitor() {
				return new EmptyRepositoryModelVisitor();
			}
		});
	}

	@Override
	protected String getCloneURL() {
		return "git://git.eclipse.org/gitroot/cdo/cdo.git";
	}

	@Override
	protected IRepositoryModelVisitor createVisitor(GitSourceControlSystem scs, RepositoryModel repositoryModel) {
		return new EmptyRepositoryModelVisitor();
	}

	@Override
	public void logTest() throws Exception {

	}	
}
