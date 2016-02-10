package de.hub.srcrepo.emffrag

import com.google.common.collect.Lists
import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.Fragmentation
import de.hub.emffrag.FragmentationImpl
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.mongodb.MongoDBDataStore
import de.hub.srcrepo.ImportedMoDiscoModelTests
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import org.junit.BeforeClass

class MongoDBImportedMoDiscoModelTests extends ImportedMoDiscoModelTests {
	
	private var Fragmentation fragmentation = null

	@BeforeClass
	static def void standalone() {
		if (EmfFragActivator.instance == null) EmfFragActivator.standalone(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE)
		if (SrcRepoActivator.INSTANCE == null) SrcRepoActivator.standalone
	}

	override openRepositoryModel() {
		val testModelURI = MongoDBMoDiscoGitImportTest.testJavaModelURI
		val baseDataStore = new MongoDBDataStore(testModelURI.authority(), testModelURI.path().substring(1), false);
		val dataStore = new DataStoreImpl(baseDataStore, testModelURI);
		fragmentation = new FragmentationImpl(Lists.newArrayList(RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE), dataStore, 1)
		return fragmentation.root as RepositoryModel
	}

	override closeRepositoryModel(RepositoryModel model) {
		fragmentation.close
		fragmentation.dataStore.close
	}	
}