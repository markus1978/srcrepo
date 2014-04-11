package de.hub.srcrepo.emffrag

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import org.junit.Before
import org.junit.Test

import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.fragmentation.FragmentedModel
import de.hub.emffrag.fragmentation.IndexBasedIdSemantics.IdBehaviour
import de.hub.emffrag.fragmentation.NoReferencesIdSemantics
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import de.hub.emffrag.util.Extensions
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.emffrag.extensions.ExtensionsPackage
import de.hub.srcrepo.emffrag.extensions.ImportLog
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import junit.framework.Assert

class ImportedMoDiscoModelTests extends de.hub.srcrepo.ImportedMoDiscoModelTests {
  
  override def uri():URI = MongoDBMoDiscoGitImportTest.testModelURI;   
    
  override def configure() {    
    EmfFragActivator.standalone(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE, ExtensionsPackage.eINSTANCE);
    EmfFragActivator.instance.logInStandAlone = false;
    EmfFragMongoDBActivator.standalone();
    SrcRepoActivator.standalone();
    
    EmfFragActivator.instance.useBinaryFragments = true
    EmfFragActivator.instance.idSemantics = new NoReferencesIdSemantics(IdBehaviour.defaultModel)
    
    val rs = new ResourceSetImpl()
    resource = rs.createResource(uri)
    
    EmfFragActivator.instance.defaultModel = resource.asInstanceOf[FragmentedModel]
    
    gitModel = resource.getContents().get(0).asInstanceOf[RepositoryModel]
  }
  
  @Test def countErrors() {
    var errors = 0;
    var commitsWithErrors = 0;
    gitModel.getAllRevs().run((c)=>{
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
    
    Assert.assertTrue(errors == 0);
    Assert.assertTrue(commitsWithErrors == 0);
  }
}