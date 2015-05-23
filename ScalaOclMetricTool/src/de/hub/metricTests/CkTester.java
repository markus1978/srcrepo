package de.hub.metricTests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.junit.Assert;
import org.junit.Test;

import de.hub.metrics.CKMetric;
import de.hub.srcrepo.GitSourceControlSystem;
import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor;
import de.hub.srcrepo.RepositoryModelTraversal;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;

/**
 * @author Frederik Marticke
 * A Class for testing the McCabe-Metric implementation.
 * 
 * To see how the repositoryimports and resourcecreations are working have a look at /de.hub.srcrepo.tests/src/de/hub/srcrepo/SrcRepoGitTest.java
 *
 */
public class CkTester {
	
	/** The Linuxstyle formatted path to the cloned repository. */
	final String LINUX_PATH_TO_CLONE_DIR = "/home/smoovie/Git_Workspace/Studienarbeit/testGitRepoCheckoutDir/clones/";
	/** The Linuxstyle formatted path to the bare repository */
	final String LINUX_PATH_TO_REPO = "/home/smoovie/Git_Workspace/Studienarbeit/";
	
	/** The Windowsstyle formatted path to the cloned repository. */
	final String WIN_PATH_TO_CLONE_DIR = "C:/Users/Worm/Git_Workspace/Studienarbeit/testGitRepoCheckoutDir/clones/";
	/** The Windowsstyle formatted path to the bare repository */
	final String WIN_PATH_TO_REPO = "C:/Users/Worm/Git_Workspace/Studienarbeit/";

	/** provides helpermethods e.g. for formatted output	 */
	private final TesterTools testerTools = new TesterTools();
	
	/**
	 * Creates a Model based on the referenced git repository and calculates the McCabe-Metric for each compilationunit inside.
	 */
	@Test
	public void runCkMetricTest() {
		EPackage.Registry.INSTANCE.put(RepositoryModelPackage.eINSTANCE.getNsURI(), RepositoryModelPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("gitmodel", new XMIResourceFactoryImpl());
		
		GitSourceControlSystem scs = new GitSourceControlSystem();
		try {
//			scs.createWorkingCopy(new File(LINUX_PATH_TO_CLONE_DIR+"mcCabeMetricTest.git"), LINUX_PATH_TO_REPO+"ScalaOclMetricToolTestclasses/ScalaOclMetricToolTestclasses");
			scs.createWorkingCopy(new File(WIN_PATH_TO_CLONE_DIR+"CkMetricTest.git"), WIN_PATH_TO_REPO+"ScalaOclMetricToolTestclasses/ScalaOclMetricToolTestclasses");
		} catch (SourceControlException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.createResource(URI.createURI("models/CkTest.java.gitmodel"));
		RepositoryModel repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel();
		Model javaModel = JavaFactory.eINSTANCE.createModel();		
		repositoryModel.setJavaModel(javaModel);
		resource.getContents().add(repositoryModel);
		resource.getContents().add(javaModel);
		
		try {
			scs.importRevisions(repositoryModel);
			RepositoryModelTraversal.traverseRepository(repositoryModel, new MoDiscoRepositoryModelImportVisitor(scs, repositoryModel));
			scs.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		repositoryModel = (RepositoryModel)resource.getContents().get(0);
		
		javaModel = repositoryModel.getJavaModel();		
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