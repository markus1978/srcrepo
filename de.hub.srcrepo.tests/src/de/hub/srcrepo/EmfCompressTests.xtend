package de.hub.srcrepo

import de.hub.emfcompress.Comparer
import de.hub.emfcompress.Patcher
import de.hub.srcrepo.compress.SrcRepoComparerConfiguration
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import java.io.File
import org.apache.commons.io.FileUtils
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.TypeAccess
import org.junit.Test

import static org.junit.Assert.*
import de.hub.srcrepo.EMFPrettyPrint
import de.hub.emfcompress.EmfCompressPackage
import de.hub.emfcompress.ObjectDelta
import de.hub.emfcompress.SettingDelta
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage
import de.hub.srcrepo.repositorymodel.UnresolvedLink
import de.hub.emfcompress.EmfCompressFactory
import org.eclipse.gmt.modisco.java.emf.JavaPackage

class EmfCompressTests extends AbstractSingleRepositoryModelTests { 
    
	override protected getRepositoryModelURI() {
		return MoDiscoGitImportTest.testJavaModelURI
	}
  
  	@Test def void testJavaDiffs() {
    	val helloWorldCURefs = repositoryModel.allRevs.map[
    		it.parentRelations.findFirst[true]?.diffs.findFirst[
	    		it.file != null &&
	    		it.file instanceof JavaCompilationUnitRef &&
	    		it.file.path.contains("HelloWorld")    		    	
    		]
    	].filter[it!=null].map[it.file as JavaCompilationUnitRef].toList
    	
    	assertTrue(helloWorldCURefs.size > 1)
    	val original = helloWorldCURefs.get(0).compilationUnitModel
    	
    	for(i:1..<helloWorldCURefs.size) {    		
    		val revised = helloWorldCURefs.get(i).compilationUnitModel
    		println("%%%% " + revised)
    		
    		val delta = new Comparer(new SrcRepoComparerConfiguration(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE) {						
				override protected id(TypeAccess typeAccess, boolean forOriginal) {
					val model = if (forOriginal) original else revised
					val type = typeAccess.type
					return if (type == null) {
						model.unresolvedLinks.findFirst[source==typeAccess].id
					} else {
						model.targets.findFirst[target == type].id
					}
				}    			
    		}).compare(original, revised)
    		
    		val patched = EcoreUtil.copy(original)
    		val patcher = new Patcher(EmfCompressFactory.eINSTANCE)
    		patcher.patch(patched, delta)
    		try {
    			assertEmfEquals(patched, revised, original)    		
    		} catch (Throwable e) {
    			println(prettyPrint(delta, patcher))
    			throw e
    		}
    	}
  	}
  	
  	private def String prettyPrint(ObjectDelta eObject, Patcher patcher) {
  		EMFPrettyPrint.prettyPrint(eObject) [container,feature,value|
  			if (feature == null) {
  				if (container instanceof ObjectDelta) {
  					val original = patcher.getPatchedOriginal(container as ObjectDelta)
  					EMFPrettyPrint.signature(original)
  				} else if (container instanceof SettingDelta) {
  					(container.eContainer as ObjectDelta).originalClass.getEStructuralFeature(container.featureID).name
  				} else {
  					null
  				}
  			} else if (feature == EmfCompressPackage.eINSTANCE.settingDelta_FeatureID) {
				(container.eContainer as ObjectDelta).originalClass.getEStructuralFeature(value as Integer).name
			} else {
				null
			}
  		]
  	}
  	
  	private def String prettyPrint(EObject eObject) {
  		EMFPrettyPrint.prettyPrint(eObject) [container,feature,value|
  			if (feature == RepositoryModelPackage.UNRESOLVED_LINK__FEATURE_ID) {
				(container as UnresolvedLink).source.eClass.getEStructuralFeature(value as Integer).name
			} else {
				null
			}
  		]
  	}

	protected def void assertEmfEquals(EObject patched, EObject revised, EObject original) {	
		try {	
 			assertTrue(EcoreUtil.equals(revised, patched))	
 		} catch (Throwable e) {
 			FileUtils.write(new File("testdata/revised.txt"), '''REVISED\n«prettyPrint(revised)»''')
 			FileUtils.write(new File("testdata/patched.txt"), '''PATCHED\n«prettyPrint(patched)»''')
 			FileUtils.write(new File("testdata/original.txt"), '''ORIG\n«prettyPrint(original)»''')
 			throw e
 		}
	}
}