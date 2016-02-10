package de.hub.srcrepo.ocl

import org.eclipse.gmt.modisco.java.Model

import static extension de.hub.srcrepo.ocl.OclExtensions.*
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import org.eclipse.gmt.modisco.java.Block
import de.hub.srcrepo.metrics.ModiscoMetrics
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration

class OclUtil {
	
	static def checkClassExists(Model model, String name) {
		model.compilationUnits.exists[types.exists[it.name.equals(name)]]
	}

	static def findType(Model model, String name) {
	  	model.ownedElements.closure[ownedPackages].collectAll[ownedElements].findFirst[name.equals(name)]
	}
	
	static def collectAllTypes(Model model) { 
	  	model.ownedElements.closure[ownedPackages].collectAll[ownedElements]
	}
	
	static def countMethodDeclarations(Model model) { 
	  	model.ownedElements.closure[ownedPackages].collectAll[ownedElements].collectAll[bodyDeclarations].size	  	
	}
	
	static def countTypeUsages(Model model) {
	  	model.collectAllTypes.union(model.orphanTypes)
	    	.collect[usagesInTypeAccess].size
	}
	      
	  	
	static def countTopLevelClasses(Model model) {
		model.ownedElements.closure[ownedPackages].collectAll[ownedElements].size
	}

	static def countPrimitives(Model model) {
		model.orphanTypes.collectAll[usagesInTypeAccess].select[it != null].size
	}

	static def traverseJavaModelViaCU(Model model) {
		model.compilationUnits.forall[!types.empty]
	}

	static def javaDiffs(RepositoryModel sr) {
		sr.allRevs.collectAll[parentRelations].collectAll[diffs].select[
				file != null && file instanceof JavaCompilationUnitRef
			]
	}

	static def coutJavaDiffs(RepositoryModel sr) {
		sr.javaDiffs.size
	}

	static def mcCabeMetric(Block block) {
		ModiscoMetrics.cyclomaticComplexity(block)
	}

	static def mcCabeMetric(Model model) {
		model.eContents.closure[eContents].typeSelect(typeof(AbstractMethodDeclaration))
			.sum [if(body != null) body.mcCabeMetric else 0]
	}

	static def nullMethod(Model model) {
		model.eContents.closure[eContents].typeSelect(typeof(AbstractMethodDeclaration)).select[body == null].size
	}
}