package de.hub.srcrepo.emffrag

import de.hub.emffrag.FObject
import de.hub.emffrag.mongodb.MongoDBDataStore
import de.hub.emffrag.tests.AbstractDataStoreTests
import de.hub.srcrepo.GitSourceControlSystem
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor
import de.hub.srcrepo.repositorymodel.Diff
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import java.io.File
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import org.junit.Test

import static org.junit.Assert.*

class SpecificRevFileTests extends AbstractDataStoreTests {
	
	private val revName = "db2148258dc782d8edb331b6e4b96c61b0e3c590"
	private val filePath = "plugins/org.eclipse.emf.cdo.explorer.ui/src/org/eclipse/emf/cdo/explorer/ui/CDORepositoryItemProvider.java"
	private val gitURI = "git@github.com:eclipse/cdo.git"
	private val File workingCopy = new File("/Users/markus/Documents/Projects/srcrepo-mars/04-import/workingcopies/Eclipse_Foundation.cdo/");
	
	override createDataStore() {
		return new MongoDBDataStore("localhost", null, "testmodel", true);	
	}
	
	override protected cacheSize() {
		return 50
	}
	
	override packages() {
		return newArrayList(RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE)
	}
	
	@Test 
	def void specificRevFileTest() {
		val scs = new GitSourceControlSystem();
		scs.createWorkingCopy(workingCopy, gitURI, true);
		
		val rmFactory = RepositoryModelFactory.eINSTANCE
		val model = rmFactory.createRepositoryModel
				
		fragmentation.root = model as FObject

		scs.importRevisions(model, revName);
		val rev = model.allRevs.findFirst[it.name.equals(revName)]
		assertNotNull(rev)
		
		var Diff diff = null
		for(pr:rev.parentRelations) {
			val potDiff = pr.diffs.findFirst[
				it.newPath == filePath
			]
			if (potDiff != null) diff = potDiff
		}
		assertNotNull(diff)
		
		val visitor = new MoDiscoRepositoryModelImportVisitor(scs, model, JavaPackage.eINSTANCE)
		visitor.onStartRev(rev, null, 0)
		visitor.onBranch(null, null) // causes refresh of workspace
		visitor.onAddedFile(diff)
		visitor.onCompleteRev(rev, null)
		
		assertNotNull((diff.file as JavaCompilationUnitRef).compilationUnitModel)
		
		reinit
		
		println(fragmentation.root.eAllContents.size)
	}
}