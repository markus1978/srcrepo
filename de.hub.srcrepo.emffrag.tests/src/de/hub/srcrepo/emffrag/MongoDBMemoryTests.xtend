package de.hub.srcrepo.emffrag

import com.sun.management.HotSpotDiagnosticMXBean
import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.Fragmentation
import de.hub.emffrag.FragmentationSet
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import de.hub.srcrepo.MemoryTests
import de.hub.srcrepo.MoDiscoRevVisitor
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import java.io.File
import java.lang.management.ManagementFactory
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

import static extension de.hub.srcrepo.RepositoryModelTraversal.*
import static extension de.hub.srcrepo.ocl.OclExtensions.*

class MongoDBMemoryTests extends MemoryTests {
	
	@AfterClass
	static public def heapdump() {
		heapdump("testdata/heapdump")		
	} 
	
	static public def heapdump(String dumpPath) {	
		val file = new File(dumpPath)
		if (file.exists) {
			file.delete
		}
		
		val server = ManagementFactory.getPlatformMBeanServer()
		val bean = ManagementFactory.newPlatformMXBeanProxy(server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean)
		bean.dumpHeap(dumpPath, true);
	}
	
	private var Fragmentation fragmentation = null
	
	override protected getRepositoryModelURI() {
//		return MongoDBMoDiscoGitImportTest.testJavaModelURI
		return URI.createURI("mongodb://localhost/EclipseAtGitHub.cdo")
	}

	@BeforeClass
	static def void standalone() {
		if (EmfFragActivator.instance == null) EmfFragActivator.standalone(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE)
		if (EmfFragMongoDBActivator.instance == null) EmfFragMongoDBActivator.standalone
		if (SrcRepoActivator.INSTANCE == null) SrcRepoActivator.standalone
	}

	override openRepositoryModel() {
		val fs = new FragmentationSet(
			#[JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE], 
			[uri|DataStoreImpl::createDataStore(uri)], 
			1)		
		fragmentation = fs.getFragmentation(repositoryModelURI)
		return fragmentation.root as RepositoryModel
	}

	override closeRepositoryModel(RepositoryModel model) {
		fragmentation.close
		fragmentation.dataStore.close
	}	
	
	var i = 0
	
	@Test
	override def snapshot() {		
		performMemoryTest(1) [
			repositoryModel.traverse(new MoDiscoRevVisitor(org.eclipse.gmt.modisco.java.emf.JavaPackage.eINSTANCE) {
				
				override onBranch(Rev commonPreviousRev, Rev newBranchRev) {
					super.onBranch(commonPreviousRev, newBranchRev)
					println('''##BRANCH «commonPreviousRev?.name»->«newBranchRev?.name»''')
				}
				
				override onMerge(Rev commonMergedRev, Rev lastBranchRev) {
					super.onMerge(commonMergedRev, lastBranchRev)
					println('''##BRANCH «lastBranchRev?.name»->«commonMergedRev?.name»''')
				}
								
				override protected onRevWithSnapshot(Rev rev, Rev traversalParentRev, Map<String, IModiscoSnapshotModel> snapshot) {
					i = i + 1
					println('''«i» «rev.name» CUs: «snapshot.values.sum[it.model.compilationUnits.size]»''')
					if (i % 1000 == 0) {
						heapdump('''testdata/«i»dump2.hprof''')
						println('''#################  «i» dump #####################''')
					}
				}				
			})
		]
	}
	
}