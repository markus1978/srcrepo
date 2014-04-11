package de.hub.srcrepo.emffrag;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	MemoryMoDiscoGitImportTest.class,
	MongoDBMoDiscoGitImportTest.class,
	MongoDBXmiMoDiscoGitImportTest.class,
	ImportedMoDiscoModelTests.class,
	ResumeMongoDBMoDiscoGitImportTest.class
})
public class EmfFragSrcRepoTestSuite {

}
