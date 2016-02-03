package de.hub.srcrepo.snapshot.internal

import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.Rev
import java.util.List
import java.util.Map
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.emf.JavaPackage

public class SSCompilationUnitModel {
	
	val JavaPackage metaModel
	val CompilationUnitModel originalCompilationUnitModel;
	var CompilationUnit copyCompilationUnit = null
	
	val List<SSLink> outgoingLinks = newArrayList
	val List<SSLink> incomingLinks = newArrayList
	val Map<NamedElement, String> originalReverseTargetMap = newHashMap

	new(JavaPackage metaModel, CompilationUnitModel originalCompilationUnitModel) {
		this.metaModel = metaModel
		this.originalCompilationUnitModel = originalCompilationUnitModel

		originalCompilationUnitModel.targets.forEach[originalReverseTargetMap.put(target, id)]
	}

	/**
	 * All pending elements that represent references that point towards elements in this CU.
	 * This includes references sources within this very same CU.
	 */	
	def List<SSLink> getIncomingLinks() {
		return incomingLinks
	}
	
	def List<SSLink> getOutgoingLinks() {
		return outgoingLinks
	}
	
	def getOriginalReverseTargets() {
		return originalReverseTargetMap
	}
	
	def getOriginalCompilationUnitModel() {
		return originalCompilationUnitModel
	}
	
	def getCopyCompilationUnit() {
		return copyCompilationUnit
	} 
	
	def setCopyCompilationUnit(CompilationUnit compilationUnit) {
		this.copyCompilationUnit = compilationUnit
	}

	override toString() {
		var path = originalCompilationUnitModel.compilationUnit.originalFilePath
		if (path != null && path.contains("src")) {
			path = path.substring(path.lastIndexOf("src"))
		}
		return '''«path» at «(originalCompilationUnitModel?.eContainer?.eContainer?.eContainer?.eContainer as Rev)?.name»'''
	}
}