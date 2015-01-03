package de.hub.metricTests;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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

import de.hub.metrics.McCabeMetric;
import de.hub.metrics.ResultObject;
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
public class McCabeTester {
	
	/** The Linuxstyle formatted path to the cloned repository. */
	final String LINUX_PATH_TO_CLONE_DIR = "/home/smoovie/Git_Workspace/Studienarbeit/testGitRepoCheckoutDir/clones/";
	/** The Linuxstyle formatted path to the bare repository */
	final String LINUX_PATH_TO_REPO = "/home/smoovie/Git_Workspace/Studienarbeit/";
	
	/** The Windowsstyle formatted path to the cloned repository. */
	final String WIN_PATH_TO_CLONE_DIR = "C:/Users/Worm/Git_Workspace/Studienarbeit/testGitRepoCheckoutDir/clones/";
	/** The Windowsstyle formatted path to the bare repository */
	final String WIN_PATH_TO_REPO = "C:/Users/Worm/Git_Workspace/Studienarbeit/";

	
	/**
	 * Creates a Model based on the referenced git repository and calculates the McCabe-Metric for each compilationunit inside.
	 */
	@Test
	public void runMcCabeMetricTest() {
		EPackage.Registry.INSTANCE.put(RepositoryModelPackage.eINSTANCE.getNsURI(), RepositoryModelPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("gitmodel", new XMIResourceFactoryImpl());
		
		GitSourceControlSystem scs = new GitSourceControlSystem();
		try {
//			scs.createWorkingCopy(new File(LINUX_PATH_TO_CLONE_DIR+"mcCabeMetricTest.git"), LINUX_PATH_TO_REPO+"ScalaOclMetricToolTestclasses/ScalaOclMetricToolTestclasses");
			scs.createWorkingCopy(new File(WIN_PATH_TO_CLONE_DIR+"mcCabeMetricTest.git"), WIN_PATH_TO_REPO+"ScalaOclMetricToolTestclasses/ScalaOclMetricToolTestclasses");
		} catch (SourceControlException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.createResource(URI.createURI("models/mcCabeTest.java.gitmodel"));
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
		McCabeMetric mcCabe = new McCabeMetric();
		List<?> metricForEachCommit = mcCabe.mcCabeMetric(javaModel);
		printFormattedResult(metricForEachCommit, "McCabeMetric");
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
