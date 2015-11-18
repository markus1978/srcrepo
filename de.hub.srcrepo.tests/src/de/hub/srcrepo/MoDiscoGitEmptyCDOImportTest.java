package de.hub.srcrepo;

import java.io.File;

import de.hub.srcrepo.repositorymodel.RepositoryModel;

public class MoDiscoGitEmptyCDOImportTest extends MoDiscoGitImportTest {

	public final static File workingCopy = new File(SrcRepoTestSuite.workingCopiesPrefix + "org.eclipse.emf.cdo.git");
	
	@Override
	protected File getWorkingCopy() {
		return workingCopy;
	}
	
	@Override
	protected boolean onlyCloneIfNecessary() {
		return true;
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

	@Override
	protected void assertMetaData(RepositoryModel repositoryModel) {

	}
}
