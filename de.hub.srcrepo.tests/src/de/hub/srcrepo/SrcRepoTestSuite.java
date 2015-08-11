package de.hub.srcrepo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	MoDiscoGitImportTest.class, // order of tests is important: ImportedMoDiscoModelTests use the import of MoDiscoGitImportTest
	ImportedMoDiscoModelTests.class,
	MoDiscoGitEmptyCDOImportTest.class
})
public class SrcRepoTestSuite {
	
	public static final String workingCopiesPrefix = "/Users/markus/Documents/Projects/srcrepo/06-working-copies/";

}
