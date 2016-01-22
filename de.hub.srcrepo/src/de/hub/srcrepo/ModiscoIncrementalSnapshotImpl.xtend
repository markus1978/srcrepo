package de.hub.srcrepo

import com.google.common.base.Preconditions
import de.hub.srcrepo.internal.SrcRepoBindingManager
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.ASTNode
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
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement

class ModiscoIncrementalSnapshotImpl implements IModicsoIncrementalSnapshotModel {

	val JavaPackage metaModel;
	val Model model;

	/**
	 * A map that connects all CompilationUnits in the model, with the SSCompilationUnitModel they are from. 
	 */
	val Map<CompilationUnit, SSCompilationUnitModel> compilationUnits = newHashMap

	val Map<String, NamedElement> targets = newHashMap
	val Map<String, UnresolvedItem> unresolvedItems = newHashMap

	val Map<CompilationUnitModel, SSCompilationUnitModel> currentCUs = newHashMap
	val List<SSCompilationUnitModel> inCUs = newArrayList
	val List<SSCompilationUnitModel> outCUs = newArrayList

	new(JavaPackage metaModel) {
		this.metaModel = metaModel;
		model = metaModel.javaFactory.createModel
	}

	def getMetaModel() {
		return metaModel
	}

	def getCompilationUnits() {
		return compilationUnits
	}

	override addCU(CompilationUnitModel model) {
		inCUs += new SSCompilationUnitModel(this, model)
	}

	override removeCU(CompilationUnitModel model) {
		outCUs += currentCUs.get(model)
	}

	override getSnapshot() {
		if (!outCUs.empty || !inCUs.empty) {
			computeSnapshot
		}
		return model
	}

	private def computeSnapshot() {
		println("#compute")
		val List<PendingElement> pendingElementsToResolve = newArrayList

		// remove old CUs
		outCUs.forEach [
			println("#remove: " + it)
			// delete all references that leave or are completely within old CUs
			it.pendingElements.forEach[delete]
			// replace all references that enter old CUs with place holders and 
			// add them to the list of pending elements, since they need to be 
			// resolved again. Ignoring unresolved references, which must be
			// references that were delete one step before.
			it.incomingReferences.filter[resolved].forEach [
				it.revert
				pendingElementsToResolve += it
			]
			// remove the containment hierarchy
			compilationUnits.remove(it.removeFromModel(model))
			// remove targets
			it.removeTargets(targets)
			currentCUs.remove(it.source)
		]

		// add new CUs
		inCUs.forEach [
			println("#add: " + it)
			// add containment hierarchy
			compilationUnits.put(it.addToModel(model), it)
			// add the pending elements of the new CU to the list of pending elements
			pendingElementsToResolve += it.getPendingElements
			// add targets
			it.fillTargets(targets)
			currentCUs.put(it.source, it)
		]

		// resolve all pending elements 
		val bindingManager = new SrcRepoBindingManager(model, metaModel, targets, pendingElementsToResolve,
			unresolvedItems);
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
				default: throw new RuntimeException("unreachable")
			}
		].toList.forEach [
			unresolvedItems.remove(it.name) // modisco uses the binding name/id as name for the unresolved item
			EcoreUtil.remove(it)
		]

		outCUs.clear
		inCUs.clear
	}
}

// TODO make internal class of snapshot and remove obsolete access methods
class SSPendingElement extends PendingElement {
	val ModiscoIncrementalSnapshotImpl snapshot
	var ASTNode target = null;
	var int index = -1;
	var reverted = false

	new(ModiscoIncrementalSnapshotImpl snapshot, ASTNode clientNode, String linkName, String binding) {
		super(snapshot.metaModel.javaFactory)
		this.snapshot = snapshot
		this.clientNode = clientNode
		this.linkName = linkName
		this.binding = new Binding(binding)
	}

	override affectTarget(ASTNode target) {
		Preconditions.checkArgument(target != null)
		if (this.target == null) {
			resolve(target)
		} else {
			replace(target)
		}
		reverted = false
	}

	def resolve(ASTNode target) {
		Preconditions.checkState(!reverted && this.target == null)
		this.target = target

		val owner = getClientNode()
		val linkName = getLinkName()
		val feature = owner.eClass().getEStructuralFeature(linkName) as EReference

		index = -1;
		if (feature.isMany()) {
			val lst = owner.eGet(feature) as EList<EObject>
			lst += target
			index = lst.size() - 1;
		} else {
			owner.eSet(feature, target);
		}

		if (!(target instanceof UnresolvedItem)) {
			snapshot.compilationUnits.get(target.originalCompilationUnit).incomingReferences += this
		}
	}

	def replace(ASTNode newTarget) {
		Preconditions.checkState(reverted && target != null)
		this.target = newTarget

		val owner = getClientNode()
		val linkName = getLinkName()
		val feature = owner.eClass().getEStructuralFeature(linkName) as EReference

		if (index != -1) {
			(owner.eGet(feature) as EList<EObject>).set(index, newTarget)
		} else {
			owner.eSet(feature, newTarget)
		}
	}

	def isResolved() {
		return target != null && !reverted
	}

	/**
	 * Replaces the resolved target with an empty place holder
	 */
	def revert() {
		Preconditions.checkState(target != null && !reverted)
		println("#revert: ->" + (target as NamedElement).name)
		val placeHolder = snapshot.metaModel.javaFactory.create(target.eClass) as ASTNode
		(placeHolder as InternalEObject).eSetProxyURI(URI.createURI(linkName)); // TODO remove, for debug only
		reverted = true;
		replace(placeHolder)
	}

	def delete() {
		Preconditions.checkState(!reverted && target != null)
		println("#delete: ->" + (target as NamedElement).name)
		val owner = getClientNode()
		val feature = owner.eClass().getEStructuralFeature(linkName) as EReference

		if (index != -1) {
			(owner.eGet(feature) as EList<EObject>).remove(index)
		} else {
			owner.eUnset(feature)
		}
		target = null
	}
}

public class SSCompilationUnitModel {
	val CompilationUnitModel source;
	val ModiscoIncrementalSnapshotImpl snapshot
	val extension SSCopier copier
	val List<SSPendingElement> incomingReferences = newArrayList
	val List<SSPendingElement> pendingElements = newArrayList

	var boolean isAttached = false

	new(ModiscoIncrementalSnapshotImpl snapshot, CompilationUnitModel source) {
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
}

class SSCopier extends EcoreUtil.Copier {
	val JavaPackage metaModel

	new(JavaPackage metaModel) {
		this.metaModel = metaModel
	}

	override getTarget(EClass eClass) {
		return metaModel.getEClassifier(eClass.getName()) as EClass
	}

	override getTarget(EStructuralFeature eStructuralFeature) {
		val eClass = eStructuralFeature.getEContainingClass();
		return getTarget(eClass).getEStructuralFeature(eStructuralFeature.getName());
	}

	/**
	 * Looks for an existing copy, within the copy of the parent before it is copied. Returns
	 * the old copy if one already exists. Elements are identified by name. If no
	 * copy of the parent exist, it is created recursively until ctxOriginal is met.
	 * CtxOriginal is not copied, it is assumed that ctxCopy is the copy of ctxOriginal.
	 * Therefore, this method allows to merge a new structure into an existing model. For
	 * example: if ctxCopy is empty, the whole containment path from original to ctxOriginal
	 * is copied into ctxCopy.
	 * 
	 * This method assumes that, original is an (indirect) contents of ctxOriginal;
	 * that all parents of original are NamedElements. 
	 */
	def <T extends EObject, R extends EObject> T copy(R ctxCopy, R ctxOriginal, NamedElement original) {
		val parent = if (original.eContainer != ctxOriginal) {
				copy(ctxCopy, ctxOriginal, original.eContainer as NamedElement)
			} else {
				ctxCopy
			}

		val existing = parent.eContents.findFirst [
			eClass == original.eClass.target && (it as NamedElement).name == original.name
		] as T
		if (existing == null) {
			val feature = getTarget(original.eContainingFeature)
			val copy = copy(original)
			Preconditions.checkState(feature.isMany)
			(parent.eGet(feature) as List<EObject>).add(copy)
			return copy as T
		} else {
			put(original, existing)
			return existing as T
		}
	}

	def <T extends EObject> T copyt(T source) {
		return super.copy(source) as T
	}

	def <T extends EObject> T copied(T key) {
		val result = super.get(key) as T
		Preconditions.checkArgument(result != null)
		return result;
	}
}