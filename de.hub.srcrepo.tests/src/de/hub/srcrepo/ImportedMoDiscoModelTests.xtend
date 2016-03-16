package de.hub.srcrepo

import de.hub.srcrepo.compress.CompressionMeasureVisitor
import de.hub.srcrepo.ocl.OclUtil
import de.hub.srcrepo.repositorymodel.AbstractFileRef
import de.hub.srcrepo.repositorymodel.Diff
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import java.io.PrintStream
import java.util.Collection
import java.util.HashSet
import java.util.Map
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.junit.Assert
import org.junit.Test

import static extension de.hub.srcrepo.ocl.OclExtensions.*
import static extension de.hub.srcrepo.ocl.OclUtil.javaDiffs

class ImportedMoDiscoModelTests extends AbstractSingleRepositoryModelTests { 
	val PrintStream out = System.out;
    
	override protected getRepositoryModelURI() {
		return MoDiscoGitImportTest.testJavaModelURI
	}
  
  	@Test def void testJavaDiffs() {
    	val javaCompilationUnitRefs = repositoryModel.javaDiffs
      
	    val allJavaDiffs = javaCompilationUnitRefs.size
	    val javaDiffsWithCU = javaCompilationUnitRefs.collect[
	    	(it.file as JavaCompilationUnitRef).compilationUnitModel.compilationUnit
	    ].size
    
	    Assert.assertTrue(allJavaDiffs > 0);
	    Assert.assertEquals(javaDiffsWithCU, allJavaDiffs);
  	}

	def testJavaModels((Rev,Model)=>void testModel) {
    	val visitor = new MoDiscoRevVisitor(JavaPackage.eINSTANCE) {
      		override def onRev(Rev rev, String projectID, IModiscoSnapshotModel snapshot) {
	    		testModel.apply(rev,snapshot.model)  
      		}
      		override def filter(Rev rev) {
      			!rev.getName().equals("879076c35867e58b2a95e17139729315acbc65fa") // there is a syntax error in this rev; this makes it ok to fail the tests.
      		}
    	}
		RepositoryModelTraversal.traverse(repositoryModel, visitor);
		visitor.close(repositoryModel)
  	}
  	
  	@Test def void testCompilationUnits() {
    	testJavaModels[rev,javaModel |
      		val typesInCUs = javaModel.getCompilationUnits().collectAll[types].size
      		val cus = javaModel.getCompilationUnits().size();

			Assert.assertTrue('''Rev «rev.name» has no CUs''', cus > 0);
		    Assert.assertTrue(typesInCUs + ">=" + cus, typesInCUs >= cus);
    	]
  	}
  
  	@Test def void testPackageStructure() {
    	testJavaModels[rev,javaModel|
      		val topLevelTypesInPackageStructure = javaModel.getOwnedElements().closure[ownedPackages].collectAll[ownedElements].filter[!proxy]
      		val cus = javaModel.getCompilationUnits();
      
      		Assert.assertTrue(topLevelTypesInPackageStructure.size > 0);
      		Assert.assertEquals(cus.size(), topLevelTypesInPackageStructure.size());
    	]
  	}	
  	
  	var addedLines = 0
  	var removedLines = 0
  	
  	@Test def void testAddedRemovedLines() {		
  		RepositoryModelTraversal.traverse(repositoryModel, new RevVisitor() {					
			override protected onRev(Rev rev, Rev traversalParentRev, Map<String, AbstractFileRef> files) {
				for(file: files.values) {
					addedLines += (file.eContainer as Diff).linesAdded
					removedLines += (file.eContainer as Diff).linesRemoved					
				}
			}  			
  		})
  		
  		Assert.assertTrue(addedLines > 0)
  		Assert.assertTrue(removedLines > 0)
  		Assert.assertTrue(addedLines > removedLines)  	
  	}
  	
  	@Test def void testContents() {
  		testJavaModels[rev,javaModel|			
			javaModel.eAllContents.forEach[
				Assert.assertNotNull(it)
			]			
  		]
  	}
  	
  	@Test def void testMetrics() {
  		testJavaModels[rev,model|
			out.println("Primitives: " + OclUtil.countPrimitives(model));
			out.println("Top level classes: " + OclUtil.countTopLevelClasses(model));
			out.println("Methods: " + OclUtil.countMethodDeclarations(model));
			out.println("Type usages: " + OclUtil.countTypeUsages(model));
			out.println("Methods wo body: " + OclUtil.nullMethod(model));
			out.println("McCabe: " + OclUtil.mcCabeMetric(model));
		]
  	}
  	
  	private var hasRevisionWithRequiredLOCMetric = false;
  	
  	@Test def void testLOC() {
  		hasRevisionWithRequiredLOCMetric = false
  		RepositoryModelTraversal.traverse(repositoryModel, new RevVisitor()  {  			
			override protected onRev(Rev rev, Rev traversalParentRev, Map<String, AbstractFileRef> files) {
				if (rev.name == "4e238b9752b33e18301bb0849ec9b5319a8cfa09") {
					for (Diff diff: rev.getParentRelations().get(0).getDiffs()) {
						if (diff.getFile() != null && diff.getFile() instanceof JavaCompilationUnitRef) {
							val locMetrics = RepositoryModelUtil.getData(diff.getFile(), "LOC-metrics")
							Assert.assertNotNull(locMetrics)
							Assert.assertEquals(4, locMetrics.getData().get("ncss"))
							hasRevisionWithRequiredLOCMetric = true
						}
					}
				}
			}	
    	});
    	
    	Assert.assertTrue(hasRevisionWithRequiredLOCMetric)
  	}
  	
  	@Test def void testModelMetaData() {		
		val revNames = repositoryModel.allRevs.map[name].toSet
		
		for(Rev root: repositoryModel.getRootRevs()) {
			Assert.assertTrue("Root revision isn't root.", RepositoryModelUtil.isRoot(root));
		}
		
		val Collection<String> visitedRevNames = new HashSet<String>();
		val rootNames = new HashSet<String>();
		val stats = RepositoryModelTraversal.traverse(repositoryModel, new MoDiscoRevVisitor(JavaPackage.eINSTANCE) {
			override onRevWithSnapshots(Rev rev, Rev parent, Map<String, ? extends IModiscoSnapshotModel> snapshots) {
				try {
					Assert.assertTrue("Revs should not be visited twice", visitedRevNames.add(rev.getName()));
					
					if (RepositoryModelUtil.isRoot(rev)) {
						rootNames.add(rev.getName());
					}									
				} catch (Exception e) {
					Assert.fail(e.getMessage());
				}
			}
			
			override protected onRev(Rev rev, String projectID, IModiscoSnapshotModel snapshot) {

			}			
		});				
		
		val revNamesDiff = new HashSet<String>();
		for(String name: revNames) {
			if (!visitedRevNames.contains(name)) {
				revNamesDiff.add(name);
				System.out.print("not visited: " + name);
				val rev = RepositoryModelUtil.getRev(repositoryModel, name);
				System.out.println(" " + RepositoryModelUtil.isRoot(rev));
			}
		}
		
		Assert.assertSame("Wrong number of merges.", 1, stats.mergeCounter)
		Assert.assertSame("Wrong number of branches.", 3, stats.branchCounter) // one for the start, two for the actual branch (one for each path after branch)
		Assert.assertEquals("Not all revisions are reached by traversal.", revNames.size(), visitedRevNames.size());	
  	}
  	
  	@Test 
  	def void testTraverseMetaData() {
  		{
	  		testMetrics()
	  		val dataSet = repositoryModel.dataSets.findFirst[name==RevVisitor.traverseMetaData]
	  		Assert.assertNotNull(dataSet)
	  		Assert.assertNotNull(dataSet.jsonData)
	  		shutdown()	  	
	  	}
  		{
  			init()
	  		val dataSet = repositoryModel.dataSets.findFirst[name==RevVisitor.traverseMetaData]
	  		Assert.assertNotNull(dataSet)
	  		Assert.assertNotNull(dataSet.jsonData)	
	  	}
  	}
  	
  	@Test
  	def void testCompressMeasure() {
  		RepositoryModelTraversal.traverse(repositoryModel, new CompressionMeasureVisitor)
  	}
}