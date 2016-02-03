package de.hub.srcrepo.emffrag

import de.hub.emffrag.Fragmentation
import de.hub.emffrag.FragmentationImpl
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.mongodb.MongoDBDataStore
import de.hub.srcrepo.ImportedMoDiscoModelTests
import de.hub.srcrepo.repositorymodel.RepositoryModel
import org.eclipse.emf.common.util.URI
import org.junit.BeforeClass
import de.hub.srcrepo.SrcRepoActivator
import de.hub.emffrag.EmfFragActivator
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage

class MongoDBImportedMoDiscoModelTests extends ImportedMoDiscoModelTests {
	public static URI testModelURI = URI.createURI("mongodb://localhost/srcrepo.example.gitmodel");

	private var Fragmentation fragmentation = null

	@BeforeClass
	static def void standalone() {
		if (EmfFragActivator.instance == null) EmfFragActivator.standalone(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE)
		if (SrcRepoActivator.INSTANCE == null) SrcRepoActivator.standalone
	}

	override openRepositoryModel() {
		val baseDataStore = new MongoDBDataStore(testModelURI.authority(), testModelURI.path().substring(1), false);
		val dataStore = new DataStoreImpl(baseDataStore, testModelURI);
		fragmentation = new FragmentationImpl(EmffragSrcRepo.packages, dataStore, 1)
		return fragmentation.root as RepositoryModel
	}

	override closeRepositoryModel(RepositoryModel model) {
		fragmentation.close
		fragmentation.dataStore.close
	}	
}