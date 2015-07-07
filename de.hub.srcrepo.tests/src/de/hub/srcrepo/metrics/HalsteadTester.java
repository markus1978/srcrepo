package de.hub.srcrepo.metrics;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gmt.modisco.java.Model;

/**
 * @author Frederik Marticke
 * A Class for testing the Halstead-Metric implementation.
 * 
 * To see how the repositoryimports and resourcecreations are working have a look at /de.hub.srcrepo.tests/src/de/hub/srcrepo/SrcRepoGitTest.java
 *
 */
public class HalsteadTester extends AbstractTester {
	
	@Override
	protected void testModel(Model javaModel) {
		HalsteadMetric halstead = new HalsteadMetric();
		List<?> metricForEachCommit = halstead.halsteadMetric(javaModel);
		printFormattedResult(metricForEachCommit, "Halstead");	
	}

	/**
	 * prints output like: 'filename *** metricType: metricValue(s)'
	 * @param result
	 * @param metricType
	 */
	private static void printFormattedResult(List<?> result, String metricType){
		System.out.println("#########################################################################");
		System.out.println("--- Result Overview ---");	
		int i = 0;
		for (Iterator<?> iter = result.iterator(); iter.hasNext(); ) {
			i++;
			ResultObject item = (ResultObject)iter.next(); 
			System.out.println("commit #" + i + ": " + item.getFileName() + " *** " + metricType + ": " + item.toString());
		}
		System.out.println("#########################################################################");
	}

}
