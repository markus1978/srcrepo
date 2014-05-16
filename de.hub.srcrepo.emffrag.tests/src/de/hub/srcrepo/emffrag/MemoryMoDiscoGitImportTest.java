package de.hub.srcrepo.emffrag;

import junit.framework.Assert;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.junit.Before;
import org.junit.Test;

import de.hub.emffrag.EmfFragActivator;
import de.hub.srcrepo.MoDiscoGitImportTest;
import de.hub.srcrepo.MoDiscoRevVisitor;
import de.hub.srcrepo.RepositoryModelTraversal;
import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;
import de.hub.srcrepo.ocl.OclUtil;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public class MemoryMoDiscoGitImportTest extends MoDiscoGitImportTest {	
	
	@Before @Override
	public void initialize() {
		EmfFragActivator.class.getName();
		SrcRepoActivator.class.getName();		
	}	
	
	protected boolean useBinaryFragments() {
		return true;
	}
	
	protected Configuration prepareConfiguration() {
		String repositoryURL = null;
		if (!getWorkingCopy().exists() || !onlyCloneIfNecessary()) {
			repositoryURL = getCloneURL();
		}		
		URI modelURI = getTestModelURI();
		
		return new EmfFragSrcRepoImport.GitConfiguration(getWorkingCopy(), modelURI).
				repositoryURL(repositoryURL).
				withBinaryResources(useBinaryFragments());
	}
	
	protected void assertRepositoryModel(RepositoryModel importedRepository, int minimumNumberOfRevs) {
		Assert.assertNotNull(importedRepository);
		Assert.assertTrue(importedRepository.getAllRevs().size() >= minimumNumberOfRevs);
	}
	
	
	@Override
	protected boolean onlyCloneIfNecessary() {
		return true;
	}

	@Override
	protected void runImport() {
		assertRepositoryModel(EmfFragSrcRepoImport.importRepository(prepareConfiguration()), 10);
	}
	
	@Test @Override // just to run it before next test
	public void testClone() {	
		super.testClone();
	}

	@Test @Override // just to run it before next test
	public void modelImportTest() {	
		super.modelImportTest();
	}

	@Test // can only run after import test
	final public void testImportedModel() {
		EList<EObject> contents = new ResourceSetImpl().createResource(getTestModelURI()).getContents();
		Assert.assertEquals("Repository model is empty.", 1, contents.size());
		RepositoryModel repositoryModel = (RepositoryModel)contents.get(0);
		
		final OclUtil scalaTest = new OclUtil();
		System.out.println("Java diffs: " + scalaTest.coutJavaDiffs(repositoryModel));		
		RepositoryModelTraversal.traverse(repositoryModel, new MoDiscoRevVisitor(JavaPackage.eINSTANCE) {			
			@Override
			protected void onRev(Rev rev, Model model) {
				System.out.println("-----------------------------------------");
				TreeIterator<EObject> i = model.eAllContents();
				while(i.hasNext()) {
					EObject next = i.next();
					if (next instanceof AbstractTypeDeclaration) {
						System.out.println("Class: " + ((AbstractTypeDeclaration)next).getName());
					}
				}
												
				System.out.println("Primitives: " + scalaTest.countPrimitives(model));
				System.out.println("Top level classes: " + scalaTest.countTopLevelClasses(model));
				System.out.println("Methods: " + scalaTest.countMethodDeclarations(model));
				System.out.println("Type usages: " + scalaTest.countTypeUsages(model));
				System.out.println("Methods wo body: " + scalaTest.nullMethod(model));
				System.out.println("McCabe: " + scalaTest.mcCabeMetric(model));
			}
		});		
	}
}
