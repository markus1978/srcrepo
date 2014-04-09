package de.hub.srcrepo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	EmfFragMemorySrcRepoGitTest.class,
	EmfFragMongoDBSrcRepoTest.class,
	EmfFragMongoDBXmiSrcRepoTest.class,
	ImportedModelTests.class
})
public class EmfFragTestSuite {

}
