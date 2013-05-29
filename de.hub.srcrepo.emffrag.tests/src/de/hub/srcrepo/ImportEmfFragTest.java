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
	
		URI modelURI = URI.createURI("hbase://localhost/srcrepo.example.bin");
//		URI modelURI = URI.createURI("mongodb://localhost/srcrepo.example.bin");
//		URI modelURI = URI.createURI("mongodb://localhost/emffrag.bin");
//		URI modelURI = URI.createURI("mongodb://localhost/org.eclipse.emf.bin");
		MongoDBUtil.dropCollection(modelURI);
		
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
//					"http://git.eclipse.org/gitroot/emf/org.eclipse.emf.git", 
//					"https://github.com/markus1978/srcrepo.example.git", 
					"",
					"../../../01_tmp/srcrepo/clones/srcrepo.example.git", modelURI, 
//					"../../../01_tmp/srcrepo/clones/emffrag.git", modelURI, 
//					"../../../01_tmp/srcrepo/clones/org.eclipse.emf.git/", modelURI,
//					"31d01c2b1749c6cb87d27ecedd9fe85e1c85b99d",
					config);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception during import.");
		}
	}
}
