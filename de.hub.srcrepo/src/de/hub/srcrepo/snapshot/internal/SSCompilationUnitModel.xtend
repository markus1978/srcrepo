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
import de.hub.srcrepo.repositorymodel.Rev

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

	@Deprecated
	def getPendingElements() {
		return pendingElements
	}

	/**
	 * All pending elements that represent references that point towards elements in this CU.
	 * This includes references sources within this very same CU.
	 */
	@Deprecated
	def getIncomingReferences() {
		Preconditions.checkState(isAttached)
		return incomingReferences
	}
	
	def Iterable<SSPendingElement> getIncomingLinks() {
		// TODO
	}
	
	def Iterable<SSPendingElement> getOutgoingLinks() {
		// TODO
	}

	// TODO
	def void fillTargets(Map<String, NamedElement> allTargets) {
		Preconditions.checkState(isAttached)
		source.targets.forEach[allTargets.put(id, target.copied)]
	}

	// TODO
	def void removeTargets(Map<String, NamedElement> allTargets) {
		source.targets.forEach[allTargets.remove(it.id)]
	}

	// TODO
	def CompilationUnit addToModel(Model model) {
		Preconditions.checkState(!isAttached, "Can only be attached to the snapshot model once.")
		// compilation unit
		val cuCopy = copyt(source.compilationUnit)

		model.compilationUnits.add(cuCopy)
		// types
		source.compilationUnit.types.forEach [
			copy(model, source.javaModel, it)
		]
		// orphan types
		source.javaModel.orphanTypes.forEach[copy(model, source.javaModel, it)]
		// TODO remove debug only
		Preconditions.checkState(source.javaModel.eAllContents.filter[it instanceof NamedElement].
			forall[copied != null])
		copyReferences

//		pendingElements += source.pendings.filter[
//			// orphan types are merged with existing orphan types. orphan types can contain pending elements. 
//			// If merged those pending elements are already resolved, and no copy of the client node exists.
//			return if (ifCopied(it.clientNode) != null) {
//				true
//			} else { // TODO remove, debug only
//				var EObject parent = clientNode 
//				while(parent != null && parent.eContainingFeature != snapshot.metaModel.model_OrphanTypes) {
//					parent = parent.eContainer
//				}
//				Preconditions.checkState(parent != null)
//				false
//			}
//		].map [			
//			return new SSPendingElement(snapshot, copied(clientNode), linkName, binding)							
//		]
		isAttached = true

		allInstances.put(cuCopy, this)
		return cuCopy
	}

	// TODO
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

	private def void delete(EObject object) {
		val objects = object.eAllContents.toList
		objects.forEach[EcoreUtil.remove(it)]
		EcoreUtil.remove(object)
	}

	override toString() {
		var path = source.compilationUnit.originalFilePath
		if (path.contains("src")) {
			path = path.substring(path.lastIndexOf("src"))
		}
		return '''«path» at «(source?.eContainer?.eContainer?.eContainer?.eContainer as Rev)?.name»'''
	}

	/**
	 * @returns the copied snapshot model version of the given persistent source element.
	 */
	def <T extends EObject> T copied(T source) {
		return copier.copied(source)
	}

	/**
	 * @returns the original persistent element for a given snapshot model element.
	 */
	def <T extends EObject> T original(T copy) {
		return copier.original(copy)
	}
}