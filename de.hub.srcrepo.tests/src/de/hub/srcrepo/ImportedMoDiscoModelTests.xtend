package de.hub.srcrepo

import static extension de.hub.srcrepo.ocl.OclExtensions.*
import static extension de.hub.srcrepo.ocl.OclUtil.javaDiffs

import de.hub.srcrepo.repositorymodel.RepositoryModel
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.junit.Before
import org.junit.Test
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import org.junit.Assert
import org.eclipse.gmt.modisco.java.Model
import de.hub.srcrepo.repositorymodel.Rev

class ImportedMoDiscoModelTests {
	val uri = MoDiscoGitImportTest.testModelURI; 
    
  	var Resource resource
  	var RepositoryModel gitModel

	def configure() {
		val rs = new ResourceSetImpl
		resource = rs.getResource(uri, true)
		gitModel = resource.getContents().get(0) as RepositoryModel
	}

	@Before final def void init() {
		configure()
	}
  
  	@Test def void testJavaDiffs() {
    	val javaCompilationUnitRefs = gitModel.javaDiffs
      
	    val allJavaDiffs = javaCompilationUnitRefs.size
	    val javaDiffsWithCU = javaCompilationUnitRefs.collect[
	    	(it.file as JavaCompilationUnitRef).compilationUnitModel.compilationUnit
	    ].size
    
	    Assert.assertTrue(allJavaDiffs > 0);
	    Assert.assertEquals(javaDiffsWithCU, allJavaDiffs);
  	}

	def testJavaModels((Model)=>void testModel) {
    	RepositoryModelTraversal.traverse(gitModel, new MoDiscoRevVisitor(org.eclipse.gmt.modisco.java.emf.JavaPackage.eINSTANCE) {
      		override def onRev(Rev rev, Model javaModel) {
	    		testModel.apply(javaModel);    
      		}
      		override def filter(Rev rev) {
      			!rev.getName().equals("879076c35867e58b2a95e17139729315acbc65fa") // there is a syntax error in this rev; this makes it ok to fail the tests.
      		}
    	});
  	}
  	
  	@Test def void testCompilationUnits() {
    	testJavaModels[javaModel |
      		val typesInCUs = javaModel.getCompilationUnits().collectAll[types].size
      		val cus = javaModel.getCompilationUnits().size();

			Assert.assertTrue(cus > 0);
		    Assert.assertTrue(typesInCUs + ">=" + cus, typesInCUs >= cus);
    	]
  	}
  
  	@Test def void testPackageStructure() {
    	testJavaModels[javaModel|
      		val topLevelTypesInPackageStructure = javaModel.getOwnedElements().closure[ownedPackages].collectAll[ownedElements]
      		val cus = javaModel.getCompilationUnits();
      
      		Assert.assertTrue(topLevelTypesInPackageStructure.size > 0);
      		Assert.assertEquals(cus.size(), topLevelTypesInPackageStructure.size());
    	]
  	}	
}