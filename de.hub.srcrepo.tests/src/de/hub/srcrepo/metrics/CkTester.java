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
public class CkTester extends AbstractTester {

	@Override
	protected void testModel(Model javaModel) {
		CKMetric ckMetric = new CKMetric();
		
		long start_time = 0;
		long end_time = 0;
		
		//test WMC-Metric
		start_time = System.nanoTime();
		List<?> WmcForEachCommit = ckMetric.WmcMetric(javaModel);
		end_time = System.nanoTime();
		//testerTools.printTimeDifference(start_time, end_time, "WMC-Metric on a given Model");
		testerTools.printFormattedResultGreaterZero(WmcForEachCommit, "Wmc-Metric");
		
		//test DIT-Metric		
		start_time = System.nanoTime();
		List<?> DitForEachCommit = ckMetric.DitMetric(javaModel);
		end_time = System.nanoTime();
		//testerTools.printTimeDifference(start_time, end_time, "DIT-Metric on a given Model");
		testerTools.printFormattedResultGreaterZero(DitForEachCommit, "Dit-Metric");
		
		//test NOC-Metric		
		start_time = System.nanoTime();
		List<?> NocForEachCommit = ckMetric.NocMetric(javaModel);
		end_time = System.nanoTime();
		//testerTools.printTimeDifference(start_time, end_time, "NOC-Metric on a given Model");
		testerTools.printFormattedResultGreaterZero(NocForEachCommit, "Noc-Metric");		
		
		//test CBO-Metric
		start_time = System.nanoTime();
		List<?> CboForEachCommit = ckMetric.CboMetric(javaModel);
		end_time = System.nanoTime();
		//testerTools.printTimeDifference(start_time, end_time, "CBO-Metric on a given Model");
		testerTools.printFormattedResultGreaterZero(CboForEachCommit, "Cbo-Metric");
		
		//test RFC-Metric
		start_time = System.nanoTime();
		List<?> RfcForEachCommit = ckMetric.RfcMetric(javaModel);
		end_time = System.nanoTime();
		//testerTools.printTimeDifference(start_time, end_time, "RFC-Metric on a given Model");
		testerTools.printFormattedResultGreaterZero(RfcForEachCommit, "Rfc-Metric");
		
		//test LCOM-Metric
		start_time = System.nanoTime();
		List<?> LcomForEachCommit = ckMetric.LcomMetric(javaModel);
		end_time = System.nanoTime();
		//testerTools.printTimeDifference(start_time, end_time, "LCOM-Metric on a given Model");
		testerTools.printFormattedResultGreaterZero(LcomForEachCommit, "LCOM-Metric");	
	}	
}
