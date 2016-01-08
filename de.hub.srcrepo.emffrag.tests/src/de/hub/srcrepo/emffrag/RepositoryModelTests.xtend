package de.hub.srcrepo.emffrag

import de.hub.emffrag.FObject
import de.hub.emffrag.tests.AbstractDataStoreTests
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory
import org.junit.Test

import static org.junit.Assert.*
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage

class RepositoryModelTests extends AbstractDataStoreTests {
	
	override def packages() {
		newArrayList(RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE)
	}	
	
	override cacheSize() {
		return 1
	}
	
	@Test
	def repositoryModelBaseTest() {
		val repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel
		
		for (i:1..5) repositoryModel.dataSets += RepositoryModelFactory.eINSTANCE.createImportMetaData		
		for (i:1..5) repositoryModel.allRevs += RepositoryModelFactory.eINSTANCE.createRev
		
		fragmentation.root = repositoryModel as FObject
		reinit
		
		assertEquals(10, fragmentation.root.eAllContents.size)
	}
}