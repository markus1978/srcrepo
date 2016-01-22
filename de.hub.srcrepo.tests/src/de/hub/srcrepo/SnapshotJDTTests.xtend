package de.hub.srcrepo

import de.hub.srcrepo.internal.SrcRepoBindingManager
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory
import java.util.HashMap
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.emf.ecore.EObject
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.emf.JavaFactory
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.JavaCore
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader
import org.junit.Test

import static org.junit.Assert.*
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.CompilationUnit
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.EList
import java.util.Comparator
import java.util.Arrays
import java.util.ListIterator

class SnapshotJDTTests {
	private val goalRev = "goal"
	
	private val javaFactory = JavaFactory.eINSTANCE
	private val repositoryModelFactory = RepositoryModelFactory.eINSTANCE
	
	private def IJavaProject openProject(String projectPath) {
		val projectDescriptionFile = new Path(projectPath + "/.project");
		val description = ResourcesPlugin.getWorkspace().loadProjectDescription(projectDescriptionFile); 
	    var project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());	
	    if (!project.exists()) {						  		    
			project.create(description, null);
			if (!project.isOpen()) {
		    	project.open(null);
		    }					
	    }
	    
	    return JavaCore.create(project);
	}
	
	private def CompilationUnitModel createCompilationUnitModel(String testCaseName, String revName, String compilationUnitName) {
		val javaProject = openProject("/Users/markus/Documents/Projects/srcrepo-mars/03-git/srcrepo/de.hub.srcrepo.tests")
		val cumPath = '''src/de/hub/srcrepo/sstestdata/«testCaseName»/«revName»/«compilationUnitName».java'''.toString
		
		val resource = javaProject.getProject().findMember(cumPath)
		val element = JavaCore.create(resource, javaProject)
		val cu = element as ICompilationUnit
		
		assertNotNull(cu)
		
		val importJob = new ImportJavaCompilationUnitsJob(newArrayList(cu), javaFactory, repositoryModelFactory) {			
			override protected skipError(String message, Exception e) {
				fail('''«message» («e.message»)''')
			}
			
			override protected skipWarning(String message) {
				fail(message)
			}			
		}
		
		importJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
		importJob.schedule();
		importJob.join();
		if (importJob.result == null || !importJob.result.isOK()) {
			fail("Could not run import job successfully.")
		}
		
		val cum = importJob.results.get(cu)
		assertNotNull(cum)
		
		cum.eAllContents.forEach[
			if (it instanceof org.eclipse.gmt.modisco.java.Package) {
				if (it.name == revName) {
					it.name = goalRev
				} 
			}
			if (it instanceof CompilationUnit) {
				it.originalFilePath = it.originalFilePath.replace(revName, goalRev)
			}
		]
		return cum										
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
		val jProject = openProject("/Users/markus/Documents/Projects/srcrepo-mars/03-git/srcrepo/de.hub.srcrepo.tests")
		assertNotNull(jProject)
		val cum = createCompilationUnitModel("", "", "C1")
		val classDeclaration = cum.javaModel.get("de.hub.srcrepo.sstestdata.C1")
		assertNotNull(classDeclaration)
	}
	
	private def void performTest(String testName, List<Map<String, String>> revsData) {		
		val currentCUMs = newHashMap
		val snapshot = new Snapshot(JavaPackage.eINSTANCE)
		for (index:1..revsData.size) {
			val revData = revsData.get(index-1)
			revData.values.filter[it != null].toList.sort.forEach[snapshot.removeCU(currentCUMs.remove(it))]
			revData.keySet.toList.sort.forEach[
				val cum = createCompilationUnitModel(testName, '''r«index»''', it)
				currentCUMs.put(it, cum)
				snapshot.addCU(cum)
			]
			assertNotNull(snapshot.snapshot)
		}
		
		val goalSnapshot = new Snapshot(JavaPackage.eINSTANCE)
		currentCUMs.keySet.toList.sort.forEach[
			val goalCum = createCompilationUnitModel(testName, goalRev, it)
			goalSnapshot.addCU(goalCum)
		]		
 	
 		assertTrue(EcoreUtil.equals(goalSnapshot.snapshot.normalize, snapshot.snapshot.normalize))
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
			if (it instanceof org.eclipse.gmt.modisco.java.Package) {
				it.ownedElements.sortComposite(namedElementCmp)
				it.ownedPackages.sortComposite(namedElementCmp)
			}
		]
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
	public def void outerRefsTest() {
		performTest("outerRefs", #[#{"A"->null, "B"->null}, #{"AChanged"->"A"}])
	}
}