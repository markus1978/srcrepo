package de.hub.srcrepo.metrics;

import java.util.List;

import org.eclipse.gmt.modisco.java.Model;

/**
 * @author Frederik Marticke
 * A Class for testing the McCabe-Metric implementation.
 * 
 * To see how the repositoryimports and resourcecreations are working have a look at /de.hub.srcrepo.tests/src/de/hub/srcrepo/SrcRepoGitTest.java
 *
 */
public class McCabeTester extends AbstractTester {

	@Override
	protected void testModel(Model javaModel) {
		McCabeMetric mcCabe = new McCabeMetric();
		long start_time = System.nanoTime();
		List<?> metricForEachCommit = mcCabe.mcCabeMetric(javaModel);
		long end_time = System.nanoTime();
		testerTools.printTimeDifference(start_time, end_time, "WMC-Metric on a given Model");
		testerTools.printFormattedResult(metricForEachCommit, "McCabeMetric");	
	}
	
}
