package de.hub.srcrepo.snapshot

import de.hub.srcrepo.internal.SrcRepoBindingManager
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import java.util.List
import java.util.Map
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement
import org.eclipse.gmt.modisco.java.CompilationUnit

class ModiscoSnapshotImpl implements IModiscoSnapshotModel {

	val JavaPackage metaModel;
	val Model model;

	val List<PendingElement> pendingElementsToResolve = newArrayList
	val Map<String, NamedElement> targets = newHashMap
	val Map<CompilationUnit, SSCompilationUnitModel> compilationUnits = newHashMap

	new(JavaPackage metaModel) {
		this.metaModel = metaModel;
		model = metaModel.javaFactory.createModel
	}

	override getMetaModel() {
		return metaModel
	}

	override addCU(CompilationUnitModel cum) {
		val ssCompilationUnitModel = new SSCompilationUnitModel(this, cum)
		println("#add: " + ssCompilationUnitModel)
		// add containment hierarchy
		compilationUnits.put(ssCompilationUnitModel.addToModel(model), ssCompilationUnitModel)
		// add the pending elements of the new CU to the list of pending elements
		pendingElementsToResolve += ssCompilationUnitModel.getPendingElements
		// add targets
		ssCompilationUnitModel.fillTargets(targets)
	}
	
	override getSnapshot() {
		if (!pendingElementsToResolve.empty) {
			println("pendings: ")
			for (pe: pendingElementsToResolve) {
				println("   " + pe.binding)
			}
			val bindingManager = new SrcRepoBindingManager(model, metaModel, targets, pendingElementsToResolve, newHashMap);
			bindingManager.resolveBindings(model); 
			pendingElementsToResolve.clear
		}
		return model
	}
	
	override getCompilationUnits() {
		return compilationUnits
	}
}
