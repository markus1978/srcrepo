package de.hub.srcrepo

import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl
import java.io.File
import java.util.Arrays
import java.util.Comparator
import java.util.List
import java.util.Map
import org.apache.commons.io.FileUtils
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.ConstructorDeclaration
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.Package
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment
import org.eclipse.gmt.modisco.java.emf.JavaFactory
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.junit.BeforeClass
import org.junit.Test

import static de.hub.srcrepo.metrics.ModiscoMetrics.*
import static org.junit.Assert.*

import static extension de.hub.srcrepo.ocl.OclExtensions.*
import java.lang.ref.WeakReference
import org.junit.Assert
import java.text.NumberFormat
import java.text.DecimalFormat

class ModiscoSnapshotTests {
	private static var isStandalone = false
	
	private val goalRev = "goal"
	private val deleted = "__deleted"
	private var deleteModifier = 0
	
	private val javaFactory = JavaFactory.eINSTANCE
	private val repositoryModelFactory = RepositoryModelFactory.eINSTANCE
	
	extension val ModiscoSnapshotTestUtils utils = new ModiscoSnapshotTestUtils(javaFactory, repositoryModelFactory)
	
	private def deleted() {
		return deleted + (deleteModifier++)
	}
	
	private def CompilationUnitModel createCompilationUnitModel(String testCaseName, String revName, String compilationUnitName) {
		val cumPath = '''«testCaseName»/«revName»/«compilationUnitName»'''
		val cum = createCompilationUnitModel(cumPath)
		if (revName != null) {
			cum.eAllContents.forEach[
				if (it instanceof Package) {
					if (it.name == revName) {
						it.name = goalRev
					} 
				}
				if (it instanceof CompilationUnit) {
					it.originalFilePath = it.originalFilePath.replace(revName, goalRev)
				}			
			]
			cum.targets.forEach[it.id = it.id.replace(revName, goalRev)]
			cum.unresolvedLinks.forEach[it.id = it.id.replace(revName, goalRev)]
		}
		return cum
	}
	
	@BeforeClass
	static def standaloneSrcRepo() {
		if (SrcRepoActivator.INSTANCE == null) {
			ModiscoSnapshotTests.isStandalone = true
			SrcRepoActivator.standalone
		}
	}
	
	private def CompilationUnitModel createCompilationUnitModel(String compilationUnitPath) {
		val cumPath = '''src-testdata/de/hub/srcrepo/sstestdata/«compilationUnitPath».java'''.toString
		val xmiPath = if (ModiscoSnapshotTests.isStandalone) {
			"testdata/models/" + cumPath.replace("src-testdata/", "").replace(".java", ".xmi").trim()
		} else {
			"/Users/markus/Documents/Projects/srcrepo-mars/03-git/srcrepo/de.hub.srcrepo.tests" + "/testdata/models/" + cumPath.replace("src-testdata/", "").replace(".java", ".xmi").trim() 
		}
		
		return createCompilationUnitModel(cumPath, xmiPath, ModiscoSnapshotTests.isStandalone)											
	}
	
	private def <T extends NamedElement> get(EObject container, String qualifiedName) {
		var current = container 
		for (name:qualifiedName.trim.split("\\.")) {
			current = current.eContents.filter[it instanceof NamedElement].map[it as NamedElement].findFirst[it.name == name]
			if (current == null) {
				return null
			}
		}
		return current
	}
	
	@Test
	public def void compilationUnitModelImportTest() {
		val cum = createCompilationUnitModel("", "", "C1")
		val classDeclaration = cum.javaModel.get("de.hub.srcrepo.sstestdata.C1")
		assertNotNull(classDeclaration)
	}
	
	private def void performTest(String testName, List<Map<String, String>> revsData) {		
		val currentCUMs = newHashMap
		val snapshot = new ModiscoIncrementalSnapshotImpl(JavaPackage.eINSTANCE)
		for (index:1..revsData.size) {
			snapshot.start
			val revData = revsData.get(index-1)
			revData.values.filter[it != null].toList.sort.forEach[snapshot.removeCompilationUnitModel(currentCUMs.remove(it))] // remove changed & deleted
			revData.keySet.filter[!it.startsWith(deleted)].toList.sort.forEach[ // add new & changed
				val cum = createCompilationUnitModel(testName, '''r«index»''', it)
				currentCUMs.put(it, cum)
				snapshot.addCompilationUnitModel(cum)
			]
			snapshot.end
			assertNotNull(snapshot.model)
		}
		
		val goalSnapshot = new ModiscoIncrementalSnapshotImpl(JavaPackage.eINSTANCE)
		goalSnapshot.start
		currentCUMs.keySet.toList.sort.forEach[
			val goalCum = createCompilationUnitModel(testName, goalRev, it)
			goalSnapshot.addCompilationUnitModel(goalCum)
		]		
 		goalSnapshot.end
 		
 		assertCompare(snapshot.model, goalSnapshot.model)
	}
	
	private def assertCompare(Model result, Model goal) {
		result.normalize
		goal.normalize
		try {	
 			assertTrue(EcoreUtil.equals(goal, result))	
 		} catch (Throwable e) {
 			FileUtils.write(new File("testdata/goal.txt"), '''GOAL\n«EMFPrettyPrint.prettyPrint(goal)»''')
 			FileUtils.write(new File("testdata/result.txt"), '''RESULT\n«EMFPrettyPrint.prettyPrint(result)»''')
 			throw e
 		}
	}
	
	private def <T> void sortComposite(EList<T> list, Comparator<T> cmp) {
		val a = list.toArray
        Arrays.sort(a, cmp as Comparator<Object>);
        list.clear
        for(Object obj: a) {
        	list.add(obj as T)
        }        
	}
	
	private def Model normalize(Model model) {
		val (NamedElement,NamedElement)=>int namedElementCmp = [one,two|one.name.compareTo(two.name)]
		model.orphanTypes.sortComposite(namedElementCmp)
		model.unresolvedItems.sortComposite(namedElementCmp)
		model.eAllContents.forEach[
			if (it instanceof Package) {
				it.ownedElements.sortComposite(namedElementCmp)
				it.ownedPackages.sortComposite(namedElementCmp)
			}
		]
		model.ownedElements.sortComposite(namedElementCmp)
		model.compilationUnits.sortComposite(namedElementCmp)
		return model
	}
	
	@Test
	public def void compositeChangeTest() {
		performTest("compositeChange", #[#{"C1"->null}, #{"C2"->"C1"}])
	}
	
	@Test
	public def void innerRefsTest() {
		performTest("innerRefs", #[#{"C1"->null}, #{"C1"->"C1"}])
	}
	
	@Test
	public def void outerRefsTargetTest() {
		performTest("outerRefsTarget", #[#{"A"->null, "B"->null}, #{"AChanged"->"A"}])
	}
	
	@Test
	public def void outerRefsSourceTest() {
		performTest("outerRefsSource", #[#{"A"->null, "B"->null}, #{"B"->"B"}])
	}
	
	@Test
	public def void outerRefsDeleteTest() {
		performTest("outerRefsDelete", #[#{"A"->null, "B"->null}, #{deleted()->"A", deleted()->"B", "C"->null}])
	}
	
	@Test
	public def void proxyTest() {		
		performTest("proxy", #[#{"A"->null, "B"->null}, #{"A"->"A", "B"->"B"}])
	}
	
	private def createSingleCUSingeRevSnapshot(String cuName) {
		return createSingleCUSingeRevSnapshot(cuName) []
	}
	
	private def createSingleCUSingeRevSnapshot(String cuName, (CompilationUnitModel)=>void assertCum) {
		val snapshot = new ModiscoIncrementalSnapshotImpl(JavaPackage.eINSTANCE)
		val cum = createCompilationUnitModel(cuName)
		
		assertCum.apply(cum)	
		
		snapshot.start
		snapshot.addCompilationUnitModel(cum)
		snapshot.end
		
		val model = snapshot.model
		assertEquals(0, model.unresolvedItems.size)
		assertTrue(model.eAllContentsAsIterable
			.typeSelect(NamedElement).filter[name != null && name.startsWith("java")].forall[
				it.proxy || it.eContainmentFeature == JavaPackage.eINSTANCE.model_OrphanTypes
			])
			
		return snapshot
	}
	
	private def createSingleRevSnapshot(String packageName, List<String> cuNames) {
		val snapshot = new ModiscoIncrementalSnapshotImpl(JavaPackage.eINSTANCE)
		snapshot.start
		cuNames.forEach[
			val cum = (packageName + "/" + it).createCompilationUnitModel
			snapshot.addCompilationUnitModel(cum)			
		]	
		snapshot.end
		
		val model = snapshot.model
		assertTrue(model.eAllContentsAsIterable
			.typeSelect(NamedElement).filter[name != null && name.startsWith("java")].forall[
				it.proxy || it.eContainmentFeature == JavaPackage.eINSTANCE.model_OrphanTypes
			])
			
		return snapshot
	}	
	
	@Test
	public def void complexBindingsTest() {
		val snapshot = createSingleCUSingeRevSnapshot("ComplexBindingTest") [		
			assertEquals(2, javaModel.ownedElements.size)	
		]
		assertTrue(weightedMethodsPerClass(snapshot.model.compilationUnits.get(0).types.get(0)) == 3)
	}
	
	@Test
	public def void fieldAccessTest() {
		val snapshot = createSingleCUSingeRevSnapshot("FieldAccessTest")
		assertNotNull(snapshot.model.eAllContentsAsIterable.typeSelect(VariableDeclarationFragment).findFirst[name=="out"])
	}
	
	@Test
	public def void fieldAccessRefsTest() {
		performTest("fieldAccessRefs", #[#{"A"->null, "B"->null}, #{"A"->"A"}])
	}
	
	@Test
	public def void implicitConstructorCallTest() {
		val model = createSingleRevSnapshot("implicitConstructorCall", #["A", "B"]).model
		val cuB = model.compilationUnits.findFirst[name=="B.java"]
		assertNotNull(cuB)
		assertFalse(cuB.types.get(0).eAllContentsAsIterable.typeSelect(ConstructorDeclaration).empty)
	}
	
	@Test
	public def void unresolvedTest() {
		val model = createSingleRevSnapshot("unresolved", #["A", "B"]).model
		assertEquals(15, model.unresolvedItems.size)
	}	
}