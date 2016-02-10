package de.hub.srcrepo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import de.hub.srcrepo.metrics.ModiscoMetricsTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TraverseTests.class,
	ModiscoSnapshotTests.class,
	MoDiscoGitImportTest.class,
	ImportedMoDiscoModelTests.class,
	ModiscoMetricsTests.class
})
public class SrcRepoTestSuite {
	
	public static final String workingCopiesPrefix = "../../../06-testdata/workingcopies/";

}
