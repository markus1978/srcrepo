package de.hub.srcrepo

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gmt.modisco.java.Model
import org.junit.Before
import org.junit.Test
import de.hub.srcrepo.ocl.HandleCollectionConversions
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.Diff
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import junit.framework.Assert
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import de.hub.srcrepo.repositorymodel.Rev

class ImportedMoDiscoModelTests extends HandleCollectionConversions {
  
  def uri():URI = MoDiscoGitImportTest.testModelURI; 
    
  var resource:Resource = null
  var gitModel:RepositoryModel = null
  
  def configure() {
    val rs = new ResourceSetImpl()
    resource = rs.getResource(uri, true)           
    gitModel = resource.getContents().get(0).asInstanceOf[RepositoryModel]
  }
  
  @Before final def init() {        
    configure()
  }
  
  @Test def testJavaDiffs() {
    val javaCompilationUnitRefs = 
      gitModel.getAllRevs()
      .collectAll((e)=>e.getParentRelations())
      .collectAll((e)=>e.getDiffs())
      .select((e:Diff)=>{e.getFile() != null && e.getFile().isInstanceOf[JavaCompilationUnitRef]})
      .collect((e)=>e.getFile().asInstanceOf[JavaCompilationUnitRef])
      
    val allJavaDiffs = javaCompilationUnitRefs.size()
    val javaDiffsWithCU = javaCompilationUnitRefs.collect((e)=>e.getCompilationUnit()).size()
    
    Assert.assertTrue(allJavaDiffs > 0);
    Assert.assertEquals(javaDiffsWithCU, allJavaDiffs);
  }

  @Test def testCompilationUnits() {
    testJavaModels((javaModel: Model) => {
      val typesInCUs = javaModel.getCompilationUnits().collectAll((e) => e.getTypes()).size()
      val cus = javaModel.getCompilationUnits().size();

      Assert.assertTrue(cus > 0);
      Assert.assertTrue(typesInCUs + ">=" + cus, typesInCUs >= cus);
    });
  }
  
  @Test def testPackageStructure() {
    testJavaModels((javaModel: Model) => {
      val topLevelTypesInPackageStructure = javaModel.getOwnedElements().closure((e)=>e.getOwnedPackages()).collectAll((e)=>e.getOwnedElements())
      val cus = javaModel.getCompilationUnits();
      
      Assert.assertTrue(topLevelTypesInPackageStructure.size > 0);
      Assert.assertEquals(cus.size(), topLevelTypesInPackageStructure.size());
    });
  }
  
  def testJavaModels(testModel: (Model)=>Unit) {
    RepositoryModelTraversal.traverse(gitModel, new MoDiscoRevVisitor(org.eclipse.gmt.modisco.java.emf.JavaPackage.eINSTANCE) {
      override def onRev(rev: Rev, javaModel: Model) {
	    testModel(javaModel);    
      }
      override def filter(rev: Rev): Boolean = !rev.getName().equals("879076c35867e58b2a95e17139729315acbc65fa") // there is a syntax error in this rev; this makes it ok to fail the tests.
    });
  }
}