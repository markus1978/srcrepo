package de.hub.srcrepo.emffrag;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	MongoDBMoDiscoGitImportTest.class,
	MongoDBImportedMoDiscoModelTests.class, // order is important
})
public class EmfFragSrcRepoTestSuite {

}
