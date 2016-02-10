package de.hub.srcrepo

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory
import de.hub.srcrepo.repositorymodel.Rev
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

import static extension de.hub.srcrepo.RepositoryModelTraversal.*
import org.junit.BeforeClass

class TraverseTests {
	
	var RepositoryModelFactory factory;
	var RepositoryModel model;
	val Map<String,Rev> revisions = newHashMap
	val List<String> result = newArrayList
	val visitor = new EmptyRepositoryModelVisitor {
		
		override onBranch(Rev commonPreviousRev, Rev newBranchRev) {
			result.add('''%[«commonPreviousRev?.name»,«newBranchRev?.name»]''')
		}

		override onMerge2(Rev commonMergedRev, Rev lastBranchRev) {
			result.add('''#[«lastBranchRev.name»,«commonMergedRev.name»]''')
		}
		
		override onStartRev(Rev rev, int number) {
			result.add('''«rev.name»''')
		}
		
	}
	
	protected def createFactory() {
		return RepositoryModelFactory.eINSTANCE
	}
	
	@BeforeClass
	public static def standalone() {
		SrcRepoActivator.standalone
	} 
	
	@Before
	public def init() {
		this.factory = createFactory
		if (model != null) {
			EcoreUtil.delete(model)
		}
		model = factory.createRepositoryModel
		revisions.clear
		result.clear
	} 
	
	private def rev(String name) {
		val existingRev = revisions.get(name)
		return if (existingRev != null) {
			existingRev
		} else {
			val newRev = factory.createRev
			newRev.name = name
			revisions.put(name, newRev)
			model.allRevs += newRev
			newRev
		}
	}
	
	private def relation(String child, String parent) {
		val existingRelation = child.rev.parentRelations.findFirst[it.parent.name == parent] 
		return if (existingRelation == null) {
			val newRelation = factory.createParentRelation
			newRelation.parent = parent.rev
			newRelation.child = child.rev
			newRelation
		} else {
			existingRelation
		}
	}
	
	private def relation(List<String> revNames) {
		Preconditions.checkArgument(revNames.size >= 2)
		for(i:0..revNames.length-2) {
			relation(revNames.get(i), revNames.get(i+1))
		}
	}
	
	private def rootRev(String rev) {
		if (!model.rootRevs.contains(rev.rev)) {
			model.rootRevs += rev.rev			
		}
	}
	
	@Test
	public def emptyTest() {
		model.traverse(visitor)
		assertEquals("", result.join("-"))
	} 
	
	@Test
	public def singleRevTest() {
		rootRev("HEAD")
		model.traverse(visitor)
		assertEquals("%[,HEAD]-HEAD", result.join("-"))
	} 
	
	@Test
	public def singleBranchTest() {
		relation(#["HEAD", "two", "one"])
		rootRev("one")
		model.traverse(visitor)
		assertEquals("%[,one]-one-two-HEAD", result.join("-"))
	}
	
	@Test
	public def simpleBranchTest() {
		relation(#["H", "2", "1"])
		relation(#["B", "2"])
		rootRev("1")
		model.traverse(visitor)
		assertEquals("%[,1]-1-2-%[2,H]-H-%[2,B]-B", result.join("-"))
	}
	
	@Test
	public def complexBranchTest() {
		relation(#["H", "3", "2", "1"])
		relation(#["HB", "3B", "2"])
		rootRev("1")
		model.traverse(visitor)
		assertEquals("%[,1]-1-2-%[2,3]-3-H-%[2,3B]-3B-HB", result.join("-"))
	}
	
	@Test
	public def branchMergeTest() {
		relation(#["H", "4", "3", "2", "1"])
		relation(#["4", "3B", "2"])
		rootRev("1")
		model.traverse(visitor)
		assertEquals("%[,1]-1-2-%[2,3]-3-4-H-%[2,3B]-3B-#[3B,4]", result.join("-"))
	}
}