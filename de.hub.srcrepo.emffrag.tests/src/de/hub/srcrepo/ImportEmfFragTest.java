package de.hub.srcrepo;

import junit.framework.Assert;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.fragmentation.FragmentedModel;
import de.hub.emffrag.fragmentation.IndexBasedIdSemantics.IdBehaviour;
import de.hub.emffrag.fragmentation.NoReferencesIdSemantics;
import de.hub.emffrag.hbase.EmfFragHBaseActivator;
import de.hub.emffrag.hbase.HBaseUtil;
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.emffrag.mongodb.MongoDBUtil;
import de.hub.srcrepo.emffrag.EmfFragImportConfiguration;

public class ImportEmfFragTest {

	@Test
	public void importEmfFragTest() {
		EmfFragActivator.class.getName();
		EmfFragMongoDBActivator.class.getName();
		EmfFragHBaseActivator.class.getName();
		
		EmfFragActivator.instance.useBinaryFragments = true;
		EmfFragActivator.instance.cacheSize = 100;
		
		boolean checkout = false;
		
//		String name = "srcrepo.example";
//		String name = "emffrag";
		String name = "org.eclipse.emf";
	
		String protocol = "hbase";
//		String protocol = "mongodb";
		
		String cloneURL = "http://git.eclipse.org/gitroot/emf/org.eclipse.emf.git"; 
//		String cloneURL = "https://github.com/markus1978/srcrepo.example.git";
				
	
		String gitName = name + ".git";
		String dataStoreName = name  + ".bin";
		
		URI modelURI = URI.createURI(protocol + "://localhost/" + dataStoreName);
		if (protocol.equals("mongodb")) {
			MongoDBUtil.dropCollection(modelURI);
		} else {
			HBaseUtil.dropTable(name);
		}
		
		try {

			EmfFragImportConfiguration config = new EmfFragImportConfiguration() {
				@Override
				public void configure(Resource model) {
					super.configure(model);
					EmfFragActivator.instance.collectStatistics = true;
					EmfFragActivator.instance.idSemantics = new NoReferencesIdSemantics(IdBehaviour.defaultModel);
					EmfFragActivator.instance.defaultModel = (FragmentedModel)model;
//							EmfFragActivator.instance.globalEventListener = new MemoryGlobalEvenListener(); done in super?
				}						
			};
			JGitUtil.importGit( 
					checkout ? cloneURL : "", 
					"../../../01_tmp/srcrepo/clones/" + gitName,
					modelURI,					
					config);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception during import.");
		}
	}
}
