package de.hub.srcrepo

import de.hub.srcrepo.repositorymodel.RepositoryModelFactory
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.emf.JavaFactory
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.junit.Test

import static org.junit.Assert.*

class SnapshotTests {
	
	val factory = JavaFactory.eINSTANCE
	val metaModel = JavaPackage.eINSTANCE
	
	@Test
	public def void containmentOnlyAddTest() {
		val snapshot = new ModiscoIncrementalSnapshotImpl(metaModel)
		
		val m1 = factory.createModel
		val p1 = factory.createPackage
		p1.name = "p1"
		m1.ownedElements += p1
		val c1 = factory.createClassDeclaration
		c1.name ="C1"
		p1.ownedElements += c1
		
		val cu1 = factory.createCompilationUnit
		m1.compilationUnits+=cu1
		cu1.types+=c1
		
		val cuModel1 = RepositoryModelFactory.eINSTANCE.createCompilationUnitModel
		cuModel1.javaModel = m1
		cuModel1.compilationUnit = cu1
		
		snapshot.start
		snapshot.addCompilationUnitModel(cuModel1)
		snapshot.end
		val model = snapshot.model
		assertNotNull(model)
		assertNotNull(model.ownedElements.get(0))
		assertNotNull(model.ownedElements.get(0).ownedElements.get(0))
		assertEquals("C1", model.ownedElements.get(0).ownedElements.get(0).name)
		assertFalse(c1 == model.ownedElements.get(0).ownedElements.get(0))
	} 
	
	@Test
	public def void containmentOnlyChangeTest() {
		val snapshot = new ModiscoIncrementalSnapshotImpl(metaModel)
		
		val m1 = factory.createModel
		val p1 = factory.createPackage
		p1.name = "p1"
		m1.ownedElements += p1
		val c1 = factory.createClassDeclaration
		c1.name ="C1"
		p1.ownedElements += c1
		
		val cu1 = factory.createCompilationUnit
		m1.compilationUnits+=cu1
		cu1.types+=c1
		
		val cuModel1 = RepositoryModelFactory.eINSTANCE.createCompilationUnitModel
		cuModel1.javaModel = m1
		cuModel1.compilationUnit = cu1
		
		val cuModel2 = EcoreUtil.copy(cuModel1)
		cuModel2.javaModel.ownedElements.get(0).ownedElements.get(0).name = "C2"
		
		snapshot.start
		snapshot.addCompilationUnitModel(cuModel1)
		snapshot.end
		snapshot.model
		snapshot.start
		snapshot.removeCompilationUnitModel(cuModel1)
		snapshot.addCompilationUnitModel(cuModel2)
		snapshot.end
		val model = snapshot.model
		
		assertNotNull(model)
		assertSame(1, model.ownedElements.size)
		assertNotNull(model.ownedElements.get(0))
		assertSame(1, model.ownedElements.get(0).ownedElements.size)
		assertNotNull(model.ownedElements.get(0).ownedElements.get(0))
		assertEquals("C2", model.ownedElements.get(0).ownedElements.get(0).name)
	} 
}