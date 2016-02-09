package de.hub.srcrepo

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import de.hub.srcrepo.ocl.OclUtil
import de.hub.srcrepo.repositorymodel.AbstractFileRef
import de.hub.srcrepo.repositorymodel.Diff
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.Rev
import java.io.PrintStream
import java.util.HashSet
import java.util.Map
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import static extension de.hub.srcrepo.ocl.OclExtensions.*
import static extension de.hub.srcrepo.ocl.OclUtil.javaDiffs
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import org.junit.BeforeClass

class ImportedMoDiscoModelTests {
	val uri = MoDiscoGitImportTest.testModelURI; 
	val PrintStream out = System.out;
    
  	var Resource resource
  	var RepositoryModel repositoryModel
	
	@BeforeClass
	public static def void standaloneSrcRepo() {
		if (SrcRepoActivator.INSTANCE == null) {	
			SrcRepoActivator.standalone();
		}
	}
	
	protected def openRepositoryModel() {
		val rs = new ResourceSetImpl
		resource = rs.getResource(uri, true)
		repositoryModel = resource.getContents().get(0) as RepositoryModel
		return repositoryModel
	}
	
	protected def closeRepositoryModel(RepositoryModel repositoryModel) {
		EcoreUtil.delete(resource.contents.get(0), true)
	}

	@Before final def void init() {
		repositoryModel = openRepositoryModel
	}
	
	@After final def void shutdown() {
		closeRepositoryModel(repositoryModel)
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

	def testJavaModels((Model)=>void testModel) {
    	val visitor = new MoDiscoRevVisitor(JavaPackage.eINSTANCE) {
      		override def onRevWithSnapshot(Rev rev, Map<String, IModiscoSnapshotModel> snapshots) {
	    		snapshots.forEach[projectID,snapshot|testModel.apply(snapshot.model)]   
      		}
      		override def filter(Rev rev) {
      			!rev.getName().equals("879076c35867e58b2a95e17139729315acbc65fa") // there is a syntax error in this rev; this makes it ok to fail the tests.
      		}
    	}
		RepositoryModelTraversal.traverse(repositoryModel, visitor);
		visitor.close(repositoryModel)
  	}
  	
  	@Test def void testCompilationUnits() {
    	testJavaModels[javaModel |
      		val typesInCUs = javaModel.getCompilationUnits().collectAll[types].size
      		val cus = javaModel.getCompilationUnits().size();

			Assert.assertTrue(cus > 0);
		    Assert.assertTrue(typesInCUs + ">=" + cus, typesInCUs >= cus);
    	]
  	}
  
  	@Test def void testPackageStructure() {
    	testJavaModels[javaModel|
      		val topLevelTypesInPackageStructure = javaModel.getOwnedElements().closure[ownedPackages].collectAll[ownedElements].filter[!proxy]
      		val cus = javaModel.getCompilationUnits();
      
      		Assert.assertTrue(topLevelTypesInPackageStructure.size > 0);
      		Assert.assertEquals(cus.size(), topLevelTypesInPackageStructure.size());
    	]
  	}	
  	
  	@Test def void testContents() {
  		testJavaModels[			
			it.eAllContents.forEach[
				Assert.assertNotNull(it)
			]			
  		]
  	}
  	
  	@Test def void testMetrics() {
  		testJavaModels[model|
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
			override protected onRev(Rev rev, Map<String, AbstractFileRef> files) {
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
		
		val Multimap<String, String> visitedRevNames = HashMultimap.create();
		val rootNames = new HashSet<String>();
		val stats = RepositoryModelTraversal.traverse(repositoryModel, new MoDiscoRevVisitor(JavaPackage.eINSTANCE) {
			override onRevWithSnapshot(Rev rev, Map<String, IModiscoSnapshotModel> snapshots) {
				snapshots.forEach[projectID, snapshot|				
					try {
						Assert.assertTrue("Revs should not be visited twice", visitedRevNames.put(projectID, rev.getName()));
						
						if (RepositoryModelUtil.isRoot(rev)) {
							rootNames.add(rev.getName());
						}									
					} catch (Exception e) {
						Assert.fail(e.getMessage());
					}					
				]
			}
		});				
		
		val revNamesDiff = new HashSet<String>();
		for(String name: revNames) {
			if (!visitedRevNames.values().contains(name)) {
				revNamesDiff.add(name);
				System.out.print("not visited: " + name);
				val rev = RepositoryModelUtil.getRev(repositoryModel, name);
				System.out.println(" " + RepositoryModelUtil.isRoot(rev));
			}
		}
		
		Assert.assertEquals("Branches and merges do not match.", stats.mergeCounter + stats.openBranchCounter, stats.branchCounter);
		Assert.assertEquals("Not all revisions are reached by traversal.", revNames.size(), new HashSet<String>(visitedRevNames.values()).size());	
  	}
  	
  	@Test def void testTraverseMetaData() {
  		{
	  		testMetrics()
	  		val dataSet = repositoryModel.dataSets.findFirst[name==RevVisitor.TRAVERSE_METADATA_KEY]
	  		Assert.assertNotNull(dataSet)
	  		Assert.assertNotNull(dataSet.jsonData)
	  		resource.save(null)
	  		shutdown()	  	
	  	}
  		{
  			init()
	  		val dataSet = repositoryModel.dataSets.findFirst[name==RevVisitor.TRAVERSE_METADATA_KEY]
	  		Assert.assertNotNull(dataSet)
	  		Assert.assertNotNull(dataSet.jsonData)	
	  	}
  	}
}