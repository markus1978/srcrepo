package de.hub.srcrepo.emffrag

import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.FragmentationSet
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.mongodb.MongoDBDataStore
import de.hub.srcrepo.ProjectAwareRevVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import org.junit.BeforeClass
import org.junit.Test

class IssueReproducingTest {
	private var i = 0
	
	private val revision = "136dfb9d17f0245c22df6200
 50cfda0d9fd2b35bb"	
//	private val cuName = "ImportRootsAction"
	private val testModelURI = URI.createURI("mongodb://localhost/EclipseAtGitHub.cdo")

	@BeforeClass
	static def standaloneSrcRepo() {
		EmfFragActivator.standalone(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE)
		SrcRepoActivator.standalone		
	}
	
	private def traverse(RepositoryModel repo, ProjectAwareRevVisitor visitor) {
		RepositoryModelTraversal.traverse(repo, visitor)
		visitor.close(repo)
	}
	
	protected def RepositoryModel openRepositoryModel() {
		
		val fs = new FragmentationSet(#[JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE]) [
			val baseDataStore = new MongoDBDataStore(it.authority(), it.port(), it.path().substring(1), false);
			return new DataStoreImpl(baseDataStore, it);
		]
		val fragmentation = fs.getFragmentation(testModelURI)
		return fragmentation.root as RepositoryModel
	}
	
	@Test
	def void reproduce() {
		traverse(openRepositoryModel) [rev, parentRev, files|
			if (rev.name == revision) {
				files.values.forEach[projectFiles|
					val snapshot = new ModiscoIncrementalSnapshotImpl(JavaPackage.eINSTANCE, "nop")
					snapshot.start
					projectFiles.values.forEach[
						snapshot.addCompilationUnitModel(it.path, it)
					]
					snapshot.end					
				]
			} else {
				println('''skip: «rev.name» («i++»)''')
			}
		]		
	}
}