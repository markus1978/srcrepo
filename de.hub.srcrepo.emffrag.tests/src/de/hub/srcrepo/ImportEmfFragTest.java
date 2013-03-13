package de.hub.srcrepo;

import junit.framework.Assert;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.EmfFragActivator.IndexedValueSetBahaviour;
import de.hub.emffrag.fragmentation.FragmentedModel;
import de.hub.emffrag.fragmentation.IndexBasedIdSemantics.IdBehaviour;
import de.hub.emffrag.fragmentation.NoReferencesIdSemantics;
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.emffrag.mongodb.MongoDBUtil;
import de.hub.srcrepo.emffrag.EmfFragImportConfiguration;

public class ImportEmfFragTest {

	@Test
	public void importEmfFragTest() {
		EmfFragActivator.class.getName();
		EmfFragMongoDBActivator.class.getName();
		
		EmfFragActivator.instance.useBinaryFragments = true;
	
		URI modelURI = URI.createURI("mongodb://localhost/de.hub.emffrag.bin");
		MongoDBUtil.dropCollection(modelURI);
		
		try {

			JGitUtil.importGit(
					"https://github.com/markus1978/emf-fragments.git", 
					"../../../01_tmp/srcrepo/clones/emffrag.git", modelURI, 
//					"31d01c2b1749c6cb87d27ecedd9fe85e1c85b99d",
					new EmfFragImportConfiguration() {
						@Override
						public void configure(Resource model) {
							EmfFragActivator.instance.collectStatistics = true;
							EmfFragActivator.instance.indexedValueSetBahaviour = IndexedValueSetBahaviour.neverContains;
							EmfFragActivator.instance.idSemantics = new NoReferencesIdSemantics(IdBehaviour.defaultModel);
							EmfFragActivator.instance.defaultModel = (FragmentedModel)model;
						}						
					});
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception during import.");
		}
	}
}
