package de.hub.srcrepo.snapshot.internal

import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.Rev
import java.util.List
import java.util.Map
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.UnresolvedItem

import static extension de.hub.srcrepo.snapshot.internal.SnapshotUtils.*
import static de.hub.srcrepo.SrcRepoActivator.*

public class SSCompilationUnitModel {
	
	val CompilationUnitModel originalCompilationUnitModel;
	var CompilationUnit copyCompilationUnit = null
	
	val List<SSLink> outgoingLinks = newArrayList
	val List<SSLink> incomingLinks = newArrayList

	var Map<NamedElement, String> ids = null
	
	val String rev

	new(String rev, CompilationUnitModel originalCompilationUnitModel) {
		this.rev = rev
		this.originalCompilationUnitModel = originalCompilationUnitModel	
	}
	
	public def getRev() {
		return rev
	}
	
	public def Map<NamedElement, String> getIds() {
		if (ids == null) {
			ids = newHashMap
			originalCompilationUnitModel.targets.forEach[
				ids.put(it.target, it.id)
			]
			originalCompilationUnitModel.unresolvedLinks.forEach[
				if (ids.get(it.target) == null) {
					condition[it.target instanceof UnresolvedItem]
					ids.put(it.target, it.fullId)
				}
			]	
		}
		return ids
	}
		
	public def clearIds() {
		ids = null
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