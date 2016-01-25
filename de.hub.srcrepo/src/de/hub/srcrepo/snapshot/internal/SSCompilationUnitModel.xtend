package de.hub.srcrepo.snapshot.internal

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement

public class SSCompilationUnitModel {
	
	static val Map<CompilationUnit, SSCompilationUnitModel> allInstances = newHashMap
	static def get(CompilationUnit cu) {
		return allInstances.get(cu)
	}
	
	val CompilationUnitModel source;
	val IModiscoSnapshotModel snapshot
	val extension SSCopier copier
	val List<SSPendingElement> incomingReferences = newArrayList
	val List<SSPendingElement> pendingElements = newArrayList

	var boolean isAttached = false

	new(IModiscoSnapshotModel snapshot, CompilationUnitModel source) {
		this.snapshot = snapshot
		this.source = source
		this.copier = new SSCopier(snapshot.metaModel)
	}

	def getPendingElements() {
		return pendingElements
	}

	/**
	 * All pending elements that represent references that point towards elements in this CU.
	 * This includes references sources within this very same CU.
	 */
	def getIncomingReferences() {
		Preconditions.checkState(isAttached)
		return incomingReferences
	}

	def void fillTargets(Map<String, NamedElement> allTargets) {
		Preconditions.checkState(isAttached)
		source.targets.forEach[allTargets.put(id, target.copied)]
	}

	def void removeTargets(Map<String, NamedElement> allTargets) {
		source.targets.forEach[allTargets.remove(it.id)]
	}

	def CompilationUnit addToModel(Model model) {
		Preconditions.checkState(!isAttached, "Can only be attached to the snapshot model once.")
		// compilation unit
		val cuCopy = copyt(source.compilationUnit)
		
		model.compilationUnits.add(cuCopy)
		// types
		source.compilationUnit.types.forEach[copy(model, source.javaModel, it)]
		// orphan types
		source.javaModel.orphanTypes.forEach[copy(model, source.javaModel, it)]
		copyReferences

		pendingElements += source.pendings.map [
			new SSPendingElement(snapshot, copied(clientNode), linkName, binding)
		]
		isAttached = true

		allInstances.put(cuCopy, this)
		return cuCopy
	}

	def CompilationUnit removeFromModel(Model model) {
		Preconditions.checkState(isAttached, "Can only removed compilation unit model that is attached to a model.")
		// orphant types
		source.javaModel.orphanTypes.forEach [
			val copy = copied(it)
			if (copy.usagesInTypeAccess.empty && copy.usagesInImports.empty) {
				EcoreUtil.remove(copy)
			}
		]
		// types
		copied(source.compilationUnit).types.forEach [
			var container = it.eContainer
			it.delete
			// also remove emptied packages
			while (container.eContents.empty && container instanceof NamedElement) {
				val content = container
				container = container.eContainer
				EcoreUtil.remove(content)
			}
		]
		// compilation unit
		val cuCopy = copied(source.compilationUnit)
		cuCopy.delete
		// reset
		copier.clear
		incomingReferences.clear
		pendingElements.clear
		isAttached = false

		allInstances.remove(cuCopy)
		return cuCopy;
	}

	def getSource() {
		return source
	}

	private def void delete(EObject obj) {
		obj.eContents.forEach[it.delete]
		EcoreUtil.remove(obj)
	}

	override toString() {
		return source.compilationUnit.originalFilePath
	}
	
	def <T extends EObject> T copied(T source) {
		return copier.copied(source)
	}
}