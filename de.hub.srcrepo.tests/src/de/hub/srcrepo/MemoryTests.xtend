package de.hub.srcrepo

import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import java.util.Map
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.junit.Test

import static extension de.hub.srcrepo.RepositoryModelTraversal.*

class MemoryTests extends AbstractSingleRepositoryModelTests {
	
	override protected getRepositoryModelURI() {
		return MoDiscoGitImportTest.testJavaModelURI
	}
	
	@Test
	public def traversal() {
		performMemoryTest(0)[
			repositoryModel.traverse(new EmptyRepositoryModelVisitor())	
		]	
	}
	
	@Test
	public def snapshot() {		
		performMemoryTest(1) [
			repositoryModel.traverse(new MoDiscoRevVisitor(JavaPackage.eINSTANCE) {				
				override protected onRevWithSnapshot(Rev rev, Rev traversalParentRev, Map<String, IModiscoSnapshotModel> snapshot) {
					
				}				
			})
		]
	}
}