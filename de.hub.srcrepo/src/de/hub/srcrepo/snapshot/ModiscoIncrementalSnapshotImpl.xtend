package de.hub.srcrepo.snapshot

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.snapshot.internal.SSCompilationUnitModel
import de.hub.srcrepo.snapshot.internal.SSLink
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.emf.JavaPackage

class ModiscoIncrementalSnapshotImpl implements IModiscoSnapshotModel {

	val JavaPackage metaModel;
	var Model model = null;

	/**
	 * A map that connects all CompilationUnits in the model, with the SSCompilationUnitModel they are from. 
	 */
	val Map<String, NamedElement> targets = newHashMap

	val Map<CompilationUnit, SSCompilationUnitModel> currentCompilationUnits = newHashMap
	val Map<CompilationUnitModel, SSCompilationUnitModel> currentCompilationUnitModels = newHashMap
	val List<SSCompilationUnitModel> newCompilationUnitModels = newArrayList
	val List<SSCompilationUnitModel> oldCompilationUnitModels = newArrayList
	
	// TODO this is for preconditions and debugging only
	val Map<String, SSCompilationUnitModel> currentCUPaths = newHashMap

	new(JavaPackage metaModel) {
		this.metaModel = metaModel;
		model = metaModel.javaFactory.createModel
	}

	override getMetaModel() {
		return metaModel
	}

	override getPersistentOriginal(EObject eObject) {
		while(true) {
			if (eObject instanceof NamedElement) {
				if (eObject.originalCompilationUnit != null) {
					return currentCompilationUnits.get(eObject.originalCompilationUnit).original(eObject)
				}
			}	
		}		
	}

	override addCompilationUnitModel(CompilationUnitModel model) {
		Preconditions.checkArgument(model != null)
		Preconditions.checkArgument(currentCUPaths.get(model.compilationUnit.originalFilePath) == null || oldCompilationUnitModels.exists[source.compilationUnit.originalFilePath == model.compilationUnit.originalFilePath])
		Preconditions.checkArgument(!newCompilationUnitModels.exists[it.source == model]) // TODO remove, to expensive
		Preconditions.checkArgument(!oldCompilationUnitModels.exists[it.source == model]) // TODO remove, to expensive
		newCompilationUnitModels += new SSCompilationUnitModel(this, model)
	}

	override removeCompilationUnitModel(CompilationUnitModel model) {
		Preconditions.checkArgument(model != null && currentCompilationUnitModels.get(model) != null)
		Preconditions.checkArgument(!newCompilationUnitModels.exists[it.source == model]) // TODO remove, to expensive
		Preconditions.checkArgument(!oldCompilationUnitModels.exists[it.source == model]) // TODO remove, to expensive
		oldCompilationUnitModels += currentCompilationUnitModels.get(model)
	}
	
	override end() {
		if (!oldCompilationUnitModels.empty || !newCompilationUnitModels.empty) {
			computeSnapshot
		}
	}

	override getModel() {
		return model
	}

	private def computeSnapshot() {
		println("#compute")
		val List<SSLink> linksToResolve = newArrayList

		// TODO
		// The current implementation removes unresolvedItems/orphanTypes/proxies 
		// before they could be replaced by equal elements in an in CU. This is
		// probably not very efficient.

		// remove old CUs
		oldCompilationUnitModels.forEach [
			println("#remove: " + it)
			// delete all references that leave or are completely within old CUs
			it.outgoingLinks.forEach[revert]
			// replace all references that enter old CUs with place holders and 
			// add them to the list of pending elements, since they need to be 
			// resolved again. Ignoring unresolved references, which must be
			// references that were delete one step before.
			it.incomingLinks.filter[resolved].forEach [
				it.revert
				linksToResolve += it				
			]
			
			// remove targets
			it.removeTargets(targets)
			
			// remove the containment hierarchy
			currentCompilationUnits.remove(it.removeFromModel(model))
		]
		oldCompilationUnitModels.forEach[
			currentCompilationUnitModels.remove(it.source)
			val path = it.source.compilationUnit.originalFilePath // TODO remove?
			Preconditions.checkState(currentCUPaths.get(path) != null)
			currentCUPaths.remove(path)
		]

		// add new CUs
		// add containment
		newCompilationUnitModels.forEach [
			println("#add: " + it)
			// add containment hierarchy
			currentCompilationUnits.put(it.addCompilationUnitToModel(model), it)
			copyReferences
			
			currentCompilationUnitModels.put(it.source, it)
			val path = it.source.compilationUnit.originalFilePath // TODO remove?
			Preconditions.checkState(currentCUPaths.get(path) == null)
			currentCUPaths.put(path, it)
		]
		// add unresolved stuff
		newCompilationUnitModels.forEach[addLinksToModel(model); fillTargets(targets)]
		
		linksToResolve += newCompilationUnitModels.map[outgoingLinks].flatten
		
		// resolve
		println("resolving links: ")
		linksToResolve.filter[!resolved].forEach[
			println("   " + it)
			val resolvedTarget = targets.get(it.id)
			if (resolvedTarget != null) {
				it.resolve(resolvedTarget)
			} else {
				Preconditions.checkState(false, "This should not happen.")
			}
		]
	}
	
	override start() {
		oldCompilationUnitModels.clear
		newCompilationUnitModels.clear
	}
	
	override toString() {
		return '''
			snapshot {
				current: [
					«FOR cu:currentCompilationUnitModels.values»
						«cu»
					«ENDFOR»
				]
				in: [
					«FOR in:newCompilationUnitModels»
						«in»
					«ENDFOR»
				]
				out: [
					«FOR out:oldCompilationUnitModels»
						«out»
					«ENDFOR»
				]
			}
		'''
	}
	
	override clear() {
		currentCompilationUnitModels.values.forEach[
//			it.pendingElements.forEach[delete]
			it.removeFromModel(model)
			it.removeTargets(targets)
		]
		
		// just in case
		EcoreUtil.delete(model)
		model = metaModel.javaFactory.createModel
		
		currentCompilationUnitModels.clear
		currentCUPaths.clear
		
		currentCompilationUnits.clear
		targets.clear
		newCompilationUnitModels.clear
		oldCompilationUnitModels.clear
	}
	
}