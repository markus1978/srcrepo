package de.hub.srcrepo.metrics;

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
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.junit.Assert;
import org.junit.Test;

import de.hub.srcrepo.GitSourceControlSystem;
import de.hub.srcrepo.IRepositoryModelVisitor;
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor;
import de.hub.srcrepo.MoDiscoRevVisitor;
import de.hub.srcrepo.RepositoryModelTraversal;
import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;

public abstract class AbstractTester {

	/** provides helpermethods e.g. for formatted output	 */
	protected final TesterTools testerTools = new TesterTools();

	protected abstract void testModel(Model javaModel);

	@Test
	public void runTest() {
		EPackage.Registry.INSTANCE.put(RepositoryModelPackage.eINSTANCE.getNsURI(), RepositoryModelPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("gitmodel", new XMIResourceFactoryImpl());
		
		GitSourceControlSystem scs = new GitSourceControlSystem();
		try {
			scs.createWorkingCopy(new File("metrics-test.git"), "models/metrics-testrepo", true);
		} catch (SourceControlException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.createResource(URI.createURI("models/metrics-test.java.gitmodel"));
		RepositoryModel repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel();
		resource.getContents().add(repositoryModel);
		
		try {
			scs.importRevisions(repositoryModel);
			IRepositoryModelVisitor importVisitor = new MoDiscoRepositoryModelImportVisitor(scs, repositoryModel, JavaPackage.eINSTANCE);
			RepositoryModelTraversal.traverse(repositoryModel, importVisitor);
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
		
		RepositoryModelTraversal.traverse(repositoryModel, new MoDiscoRevVisitor(JavaPackage.eINSTANCE) {			
			@Override
			protected void onRev(Rev rev, Model javaModel) {
				testModel(javaModel);
			}
		});
	}
}
