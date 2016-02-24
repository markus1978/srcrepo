package de.hub.srcrepo

import de.hub.srcrepo.repositorymodel.RepositoryModel
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.eclipse.gmt.modisco.java.emf.JavaPackage

abstract class AbstractSingleRepositoryModelTests {
	
	protected abstract def URI getRepositoryModelURI(); 
	
	protected def JavaPackage getJavaPackage() {
		return JavaPackage.eINSTANCE
	}
	
	var Resource resource
  	var RepositoryModel repositoryModel
	
	@BeforeClass
	public static def void standaloneSrcRepo() {
		if (SrcRepoActivator.INSTANCE == null) {	
			SrcRepoActivator.standalone();
		}
	}
	
	protected def getRepositoryModel() {
		return repositoryModel;
	}
	
	protected def openRepositoryModel() {
		val rs = new ResourceSetImpl
		resource = rs.getResource(repositoryModelURI, true)
		repositoryModel = resource.getContents().get(0) as RepositoryModel
		return repositoryModel
	}
	
	protected def closeRepositoryModel(RepositoryModel repositoryModel) {
		resource.save(null)
		EcoreUtil.delete(resource.contents.get(0), true)
	}

	@Before final def void init() {
		repositoryModel = openRepositoryModel
	}
	
	@After final def void shutdown() {
		closeRepositoryModel(repositoryModel)
	}
	
	protected def static void gc() {
		for (i:0..2) {
			var obj = new Object();
			val ref = new WeakReference<Object>(obj);
			obj = null;
			while (ref.get() != null) {
				System.gc();
			}
			System.runFinalization();
		}
	}
	
	protected def performMemoryTest(int maxRuns, ()=>void action) {
		var long lastMemory = 0
		var i = 0
		while (i < maxRuns || maxRuns <= 0) {
			i++
			val startTime = System.currentTimeMillis
			do {
				action.apply
			} while (System.currentTimeMillis - startTime < 1000)
			
			gc		
			val memory = Runtime.runtime.freeMemory
			println(new DecimalFormat("###,###,###,###").format(memory) + " " + i)
			lastMemory = Runtime.runtime.freeMemory						
		}	
	}
}