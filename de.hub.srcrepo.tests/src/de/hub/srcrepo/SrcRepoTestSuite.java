package de.hub.srcrepo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	MoDiscoGitImportTest.class,
	ImportedMoDiscoModelTests.class
})
public class SrcRepoTestSuite {

}
