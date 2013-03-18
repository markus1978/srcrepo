package de.hub.srcrepo
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.gmt.modisco.java.Model
import org.junit.Before
import org.junit.Test
import de.hub.srcrepo.gitmodel.JavaDiff
import de.hub.srcrepo.gitmodel.SourceRepository
import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage
import de.hub.srcrepo.emffrag.extensions.ExtensionsPackage
import de.hub.emffrag.fragmentation.NoReferencesIdSemantics
import de.hub.emffrag.fragmentation.IndexBasedIdSemantics.IdBehaviour

class ImportedModelTests extends ScalaTest {
  
  val uriString = "mongodb://141.20.23.228/emffrag.bin"
    
  var resource:Resource = null
  var javaModel:Model = null
  var gitModel:SourceRepository = null
    
  @Before def init() {
    EmfFragActivator.standalone(JavaPackage.eINSTANCE, GitModelPackage.eINSTANCE, ExtensionsPackage.eINSTANCE);
    EmfFragMongoDBActivator.standalone();
    SrcRepoActivator.standalone();
    
    EmfFragActivator.instance.useBinaryFragments = true
    EmfFragActivator.instance.idSemantics = new NoReferencesIdSemantics(IdBehaviour.strict)
    
    val rs = new ResourceSetImpl()
    resource = rs.createResource(URI.createURI(uriString))
    
    javaModel = resource.getContents().get(1).asInstanceOf[Model]
    gitModel = resource.getContents().get(0).asInstanceOf[SourceRepository]
  }
  
  @Test def testJavaDiffs() {
    val javaDiffs = gitModel.getAllCommits().collectAll((e)=>e.getParentRelations()).collectAll((e)=>e.getDiffs()).select((e)=>e.isInstanceOf[JavaDiff]).collect((e)=>e.asInstanceOf[JavaDiff])
    val allJavaDiffs = javaDiffs.size()
    val javaDiffsWithCU = javaDiffs.select((e)=>e.getCompilationUnit() != null).size()
    
    System.out.println(javaDiffsWithCU + "/" + allJavaDiffs + " have a compilation unit")
  }
  
  @Test def testCompilationUnits() {
    val typesInCUs = javaModel.getCompilationUnits().collectAll((e)=>e.getTypes()).size()
    val cus = javaModel.getCompilationUnits().size();
    
    System.out.println("There are " + typesInCUs + " types in " + cus+ " compilation units");
  }
  
  @Test def testPackageStructure() {
      val topLevelTypesInPackageStructure = javaModel.getOwnedElements().closure((e)=>e.getOwnedPackages()).collectAll((e)=>e.getOwnedElements())
      val cus = javaModel.getCompilationUnits().size();
    
      System.out.println("There are " + topLevelTypesInPackageStructure.size() + " top level in the package structure and " + cus+ " compilation units in the model");
  }

}