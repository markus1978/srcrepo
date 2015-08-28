package de.hub.srcrepo.emffrag;


import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.fragmentation.Fragment;
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.srcrepo.MoDiscoGitImportTest;
import de.hub.srcrepo.MoDiscoRevVisitor;
import de.hub.srcrepo.RepositoryModelTraversal;
import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.emffrag.EmfFragSrcRepoImport.Configuration;
import de.hub.srcrepo.ocl.OclUtil;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public class MongoDBMoDiscoGitImportTest extends MoDiscoGitImportTest {	
	
	public static URI testModelURI = URI.createURI("mongodb://localhost/srcrepo.example.gitmodel");
	
	@Override
	protected URI getTestModelURI() {
		return testModelURI;
	}		
	
	@Before @Override
	public void initialize() {
		EmfFragActivator.class.getName();
		SrcRepoActivator.class.getName();
		EmfFragMongoDBActivator.class.getName();
	}	
	
	protected Configuration prepareConfiguration() {
		String repositoryURL = null;
		if (!getWorkingCopy().exists() || !onlyCloneIfNecessary()) {
			repositoryURL = getCloneURL();
		}		
		URI modelURI = getTestModelURI();
		
		return new EmfFragSrcRepoImport.GitConfiguration(getWorkingCopy(), modelURI).
				repositoryURL(repositoryURL);
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
		RepositoryModel repositoryModel = EmfFragSrcRepoImport.importRepository(prepareConfiguration());
		assertRepositoryModel(repositoryModel, 10);
		closeRepositoryModel(repositoryModel);
	}
	
	@Test @Override // just to run it before next test
	public void testClone() {	
		super.testClone();
	}

	@Test @Override // just to run it before next test
	public void modelImportTest() {	
		super.modelImportTest();
	}

	@Override
	protected RepositoryModel openRepositoryModel(boolean dropExisting) {
		return (RepositoryModel) EmfFragSrcRepoImport.openFragmentation(prepareConfiguration()).getRootFragment().getContents().get(0);
	}

	@Override
	protected void closeRepositoryModel(RepositoryModel model) {
		EmfFragSrcRepoImport.closeFragmentation(prepareConfiguration(), ((Fragment)model.eResource()).fFragmentation());
	}

	@Test // can only run after import test
	final public void testImportedModel() {
		RepositoryModel repositoryModel = openRepositoryModel(false);
		
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
		
		closeRepositoryModel(repositoryModel);
	}
}
