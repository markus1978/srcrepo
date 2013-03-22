package de.hub.srcrepo
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import org.eclipse.gmt.modisco.java.Model
import org.junit.Before
import org.junit.Test
import de.hub.emffrag.fragmentation.IndexBasedIdSemantics.IdBehaviour
import de.hub.emffrag.fragmentation.NoReferencesIdSemantics
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import de.hub.emffrag.EmfFragActivator
import de.hub.srcrepo.emffrag.extensions.ExtensionsPackage
import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage
import de.hub.srcrepo.gitmodel.JavaDiff
import de.hub.srcrepo.gitmodel.SourceRepository
import de.hub.srcrepo.ocl.HandleCollectionConversions
import de.hub.srcrepo.gitmodel.Diff
import de.hub.emffrag.fragmentation.FInternalObjectImpl
import de.hub.emffrag.fragmentation.FragmentedModel
import de.hub.emffrag.util.EMFFragUtil
import de.hub.emffrag.util.Extensions
import de.hub.srcrepo.emffrag.extensions.ImportLog
import de.hub.srcrepo.emffrag.extensions.ImportLogEntry

class ImportedModelTests extends HandleCollectionConversions {
  
//  val uriString = "mongodb://141.20.23.228/emffrag.bin"
  val uriString = "mongodb://localhost/de.hub.emffrag.bin"
    
  var resource:Resource = null
  var javaModel:Model = null
  var gitModel:SourceRepository = null
    
  @Before def init() {
    EmfFragActivator.standalone(JavaPackage.eINSTANCE, GitModelPackage.eINSTANCE, ExtensionsPackage.eINSTANCE);
    EmfFragActivator.instance.logInStandAlone = false;
    EmfFragMongoDBActivator.standalone();
    SrcRepoActivator.standalone();
    
    EmfFragActivator.instance.useBinaryFragments = true
    EmfFragActivator.instance.idSemantics = new NoReferencesIdSemantics(IdBehaviour.strict)
    
    val rs = new ResourceSetImpl()
    resource = rs.createResource(URI.createURI(uriString))
    
    EmfFragActivator.instance.defaultModel = resource.asInstanceOf[FragmentedModel]
    
    javaModel = resource.getContents().get(1).asInstanceOf[Model]
    gitModel = resource.getContents().get(0).asInstanceOf[SourceRepository]
  }
  
  @Test def countErrors() {
    var errors = 0;
    var commitsWithErrors = 0;
    gitModel.getAllCommits().run((c)=>{
      val log:ImportLog = Extensions.get(c, classOf[ImportLog])
      if (log != null) {
        commitsWithErrors += 1
        System.out.println(c.getName() + " -------------------------------------------------------")
        log.getEntries().run((e)=>{
          if (e.getException() == null )
            println(e.getMessage())
          else 
        	println(e.getMessage() + ": [" + e.getException() + "] " + e.getExceptionMessage()); 
          errors = errors + 1
        })      
      }
    })
    
    System.out.println("Import contains " + errors + " errors in " + commitsWithErrors + " errornous commits.");
  }
  
  @Test def testJavaDiffs() {
    val javaDiffs = 
      gitModel.getAllCommits()
      .collectAll((e)=>e.getParentRelations())
      .collectAll((e)=>e.getDiffs())
      .select((e:Diff)=>{e.isInstanceOf[JavaDiff]})
      .collect((e)=>e.asInstanceOf[JavaDiff])
      
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