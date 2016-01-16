package de.hub.srcrepo.emffrag

import de.hub.emffrag.FObject
import de.hub.emffrag.mongodb.MongoDBDataStore
import de.hub.emffrag.tests.AbstractDataStoreTests
import de.hub.emffrag.tests.ModelGenerator
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import org.junit.Test
import org.eclipse.gmt.modisco.java.Model
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage

import static org.junit.Assert.*

class RandomlyGeneratedModelTests extends AbstractDataStoreTests {
	override createDataStore() {
		return new MongoDBDataStore("localhost", "testmodel", true);	
	}
	
	override protected cacheSize() {
		return 50
	}
	
	override packages() {
		return newArrayList(RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE)
	}
	
	@Test
	def void randomlyGeneratedRepositoryModelTest() {
		// create and save
		var size = 0
		val rmFactory = RepositoryModelFactory.eINSTANCE
		val model = rmFactory.createRepositoryModel
		size++
		fragmentation.root = model as FObject
		var Rev lastRev = null
		for(revIndex:0..1000) {
			val rev = rmFactory.createRev
			model.allRevs += rev
			size++
			rev.name = "rev" + revIndex
			val relation = rmFactory.createParentRelation
			size++
			relation.parent = lastRev
			rev.parentRelations += relation
			for(diffIndex:0..5) {
				val diff = rmFactory.createDiff
				size++
				relation.diffs += diff
				val ref = rmFactory.createJavaCompilationUnitRef
				size++
				diff.file = ref
				val cu = rmFactory.createCompilationUnitModel
				size++
				ref.compilationUnitModel = cu
				val javaGenerator = new ModelGenerator(diffIndex, 10)
				cu.javaModel = javaGenerator.generateModel(JavaPackage.eINSTANCE.model) as Model
				size += cu.javaModel.eAllContents.size + 1				
			}
			if (revIndex%10 == 0) print(".")
		}
		
		reinit
		
		assertEquals(size, fragmentation.root.eAllContents.size + 1)
	}
	
	@Test 
	def void randomlyGeneratedJavaModelTest() {
		for (i:0..100) {
			val generator = new ModelGenerator(i, 12)
			val model = generator.generateModel(JavaPackage.eINSTANCE.model) as FObject
			performSaveLoadTest(model, newArrayList(JavaPackage.eINSTANCE))
			if (i%10==0) print(".")		
		}
	}
}