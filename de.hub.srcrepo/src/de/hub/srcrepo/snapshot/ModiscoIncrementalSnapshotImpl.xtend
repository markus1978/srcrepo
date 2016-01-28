package de.hub.srcrepo.snapshot

import com.google.common.base.Preconditions
import de.hub.srcrepo.internal.SrcRepoBindingManager
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.snapshot.internal.SSCompilationUnitModel
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.UnresolvedAnnotationDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedClassDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedEnumDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedInterfaceDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedItem
import org.eclipse.gmt.modisco.java.UnresolvedLabeledStatement
import org.eclipse.gmt.modisco.java.UnresolvedMethodDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedSingleVariableDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedType
import org.eclipse.gmt.modisco.java.UnresolvedTypeDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedVariableDeclarationFragment
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement
import de.hub.srcrepo.snapshot.internal.SSPendingElement

class ModiscoIncrementalSnapshotImpl implements IModiscoSnapshotModel {

	val JavaPackage metaModel;
	var Model model = null;

	/**
	 * A map that connects all CompilationUnits in the model, with the SSCompilationUnitModel they are from. 
	 */
	val Map<CompilationUnit, SSCompilationUnitModel> compilationUnits = newHashMap

	@Deprecated
	val Map<String, NamedElement> targets = newHashMap
	@Deprecated
	val Map<String, UnresolvedItem> unresolvedItems = newHashMap

	val Map<CompilationUnitModel, SSCompilationUnitModel> currentCUs = newHashMap
	val Map<String, SSCompilationUnitModel> currentCUPaths = newHashMap
	val List<SSCompilationUnitModel> inCUs = newArrayList
	val List<SSCompilationUnitModel> outCUs = newArrayList

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
					return compilationUnits.get(eObject.originalCompilationUnit).original(eObject)
				}
			}	
		}		
	}

	override addCU(CompilationUnitModel model) {
		Preconditions.checkArgument(model != null)
		Preconditions.checkArgument(currentCUPaths.get(model.compilationUnit.originalFilePath) == null || outCUs.exists[source.compilationUnit.originalFilePath == model.compilationUnit.originalFilePath])
		Preconditions.checkArgument(!inCUs.exists[it.source == model]) // TODO remove, to expensive
		Preconditions.checkArgument(!outCUs.exists[it.source == model]) // TODO remove, to expensive
		inCUs += new SSCompilationUnitModel(this, model)
	}

	override removeCU(CompilationUnitModel model) {
		Preconditions.checkArgument(model != null && currentCUs.get(model) != null)
		Preconditions.checkArgument(!inCUs.exists[it.source == model]) // TODO remove, to expensive
		Preconditions.checkArgument(!outCUs.exists[it.source == model]) // TODO remove, to expensive
		outCUs += currentCUs.get(model)
	}
	
	override end() {
		if (!outCUs.empty || !inCUs.empty) {
			computeSnapshot
		}
	}

	override getModel() {
		return model
	}

	private def computeSnapshot() {
		println("#compute")
		val List<SSPendingElement> linksToResolve = newArrayList

		// remove old CUs
		outCUs.forEach [
			println("#remove: " + it)
			// delete all references that leave or are completely within old CUs
			it.outgoingLinks.forEach[revert] // TODO or delete?
			// replace all references that enter old CUs with place holders and 
			// add them to the list of pending elements, since they need to be 
			// resolved again. Ignoring unresolved references, which must be
			// references that were delete one step before.
			it.incomingLinks.filter[resolved].forEach [
				it.revert
				linksToResolve += it
			]
			// remove the containment hierarchy
			compilationUnits.remove(it.removeFromModel(model))
			// remove targets
			it.removeTargets(targets)
		]
		outCUs.forEach[
			currentCUs.remove(it.source)
			val path = it.source.compilationUnit.originalFilePath // TODO remove?
			Preconditions.checkState(currentCUPaths.get(path) != null)
			currentCUPaths.remove(path)
		]

		// add new CUs
		inCUs.forEach [
			println("#add: " + it)
			// add containment hierarchy
			compilationUnits.put(it.addToModel(model), it)
			// add the pending elements of the new CU to the list of pending elements
			linksToResolve += it.outgoingLinks
			// add targets
			it.fillTargets(targets)			
		]
		inCUs.forEach[
			currentCUs.put(it.source, it)
			val path = it.source.compilationUnit.originalFilePath // TODO remove?
			Preconditions.checkState(currentCUPaths.get(path) == null)
			currentCUPaths.put(path, it)
		]

		// resolve all unresolved links
		println("links to resolve: ")
		for (link: linksToResolve) {
			println("   " + link)
		}
		
		{ // TODO resolve with own algorithm not binding manager
			val bindingManager = new SrcRepoBindingManager(model, metaModel, targets, null, unresolvedItems);
			bindingManager.resolveBindings(model); // unresolved bindings are added to the model's unresolvedItems
			// remove unresolvedItems that are no longer referenced
			model.unresolvedItems.filter [
				usagesInImports.empty && switch (it) {
					UnresolvedAnnotationDeclaration: usagesInTypeAccess.empty
					UnresolvedClassDeclaration: usagesInTypeAccess.empty
					UnresolvedInterfaceDeclaration: usagesInTypeAccess.empty
					UnresolvedEnumDeclaration: usagesInTypeAccess.empty
					UnresolvedLabeledStatement: usagesInBreakStatements.empty && usagesInContinueStatements.empty
					UnresolvedMethodDeclaration: usages.empty && usagesInDocComments.empty
					UnresolvedSingleVariableDeclaration: usageInVariableAccess.empty
					UnresolvedType: usagesInTypeAccess.empty
					UnresolvedTypeDeclaration: usagesInTypeAccess.empty
					UnresolvedVariableDeclarationFragment: usageInVariableAccess.empty
					UnresolvedItem: true
					default: throw new RuntimeException("unreachable " + (it instanceof UnresolvedTypeDeclaration))
				}
			].toList.forEach [
				println("#unresolved: ->" + it.name)
				unresolvedItems.remove(it.name) // modisco uses the binding name/id as name for the unresolved item
				EcoreUtil.remove(it)
			]			
		}
	}
	
	override start() {
		outCUs.clear
		inCUs.clear
	}
	
	override toString() {
		return '''
			snapshot {
				current: [
					«FOR cu:currentCUs.values»
						«cu»
					«ENDFOR»
				]
				in: [
					«FOR in:inCUs»
						«in»
					«ENDFOR»
				]
				out: [
					«FOR out:outCUs»
						«out»
					«ENDFOR»
				]
			}
		'''
	}
	
	override clear() {
		currentCUs.values.forEach[
//			it.pendingElements.forEach[delete]
			it.removeFromModel(model)
			it.removeTargets(targets)
		]
		
		// just in case
		EcoreUtil.delete(model)
		model = metaModel.javaFactory.createModel
		
		currentCUs.clear
		currentCUPaths.clear
		
		compilationUnits.clear
		targets.clear
		unresolvedItems.clear
		inCUs.clear
		outCUs.clear
	}
	
}