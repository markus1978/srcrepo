package de.hub.srcrepo.snapshot.internal

import com.google.common.base.Preconditions
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.gmt.modisco.java.AnnotationTypeMemberDeclaration
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.LabeledStatement
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.Package
import org.eclipse.gmt.modisco.java.Type
import org.eclipse.gmt.modisco.java.UnresolvedItem
import org.eclipse.gmt.modisco.java.UnresolvedTypeDeclaration
import org.eclipse.gmt.modisco.java.VariableDeclaration

public class SSCompilationUnitModel {

	static val Map<CompilationUnit, SSCompilationUnitModel> allInstances = newHashMap

	static def get(CompilationUnit cu) {
		return allInstances.get(cu)
	}

	val CompilationUnitModel originalCompilationUnitModel;
	val IModiscoSnapshotModel snapshot
	val extension SSCopier copier
	
	val List<SSLink> outgoingLinks = newArrayList
	val List<SSLink> incomingLinks = newArrayList

	var boolean isAttached = false

	new(IModiscoSnapshotModel snapshot, CompilationUnitModel originalCompilationUnitModel) {
		this.snapshot = snapshot
		this.originalCompilationUnitModel = originalCompilationUnitModel
		this.copier = new SSCopier(snapshot.metaModel)
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

	def void fillTargets(Map<String, NamedElement> allTargets) {
		Preconditions.checkState(isAttached)
		originalCompilationUnitModel.targets.forEach[allTargets.put(id, target.copied)]
	}

	def void removeTargets(Map<String, NamedElement> allTargets) {
		originalCompilationUnitModel.targets.filter[!target.copied.isUsed].forEach[allTargets.remove(id)]
	}

	def CompilationUnit addToModel(Model model) {
		Preconditions.checkState(!isAttached, "Can only be attached to the snapshot model once.")

		// compilation unit
		val cuCopy = copyt(originalCompilationUnitModel.compilationUnit)
		model.compilationUnits.add(cuCopy)
		
		// owned types
		originalCompilationUnitModel.compilationUnit.types.forEach [
			copy(model, originalCompilationUnitModel.javaModel, it)			
		]
		
		// orphan types
		originalCompilationUnitModel.javaModel.orphanTypes.forEach[
			copy(model, originalCompilationUnitModel.javaModel, it)
		]
		
		// proxies and unresolved types
		originalCompilationUnitModel.unresolvedLinks.forEach[
			if (target == null) {
				// TODO
				SrcRepoActivator.INSTANCE.warning("Have to deal with absolutely not resolved elements. Implementation is missing.")
			} else if (target instanceof UnresolvedItem) {
				copy(model, originalCompilationUnitModel.javaModel, target)
			} else if (target.isProxy) {
				copy(model, originalCompilationUnitModel.javaModel, target)
			} else {
				Preconditions.checkState(false, "This should be impossible to read.")
			}
		]
		
		// TODO remove debug only
		Preconditions.checkState(originalCompilationUnitModel.javaModel.eAllContents.filter[it instanceof NamedElement].forall[copied != null])
		copyReferences

		isAttached = true
		
		Preconditions.checkState(outgoingLinks.empty)
		outgoingLinks += originalCompilationUnitModel.unresolvedLinks.map[new SSLink(it, copied(it.source), it.target?.copied)]

		allInstances.put(cuCopy, this)
		return cuCopy
	}
	
	private def isUsed(NamedElement it) {
		return !(usagesInImports.empty && switch (it) {
			Type: usagesInTypeAccess.empty
			AbstractMethodDeclaration: usages.empty && usagesInDocComments.empty
			LabeledStatement: usagesInBreakStatements.empty && usagesInContinueStatements.empty
			Package: usagesInPackageAccess.empty
			VariableDeclaration: usageInVariableAccess.empty
			AnnotationTypeMemberDeclaration: usages.empty 			
			UnresolvedItem: true
			default: throw new RuntimeException("unreachable " + (it instanceof UnresolvedTypeDeclaration))
		})
	}

	def CompilationUnit removeFromModel(Model model) {
		Preconditions.checkState(isAttached, "Can only removed compilation unit model that is attached to a model.")
		
		// proxies and unresolved types
		originalCompilationUnitModel.unresolvedLinks.map[target?.copied].filter[it != null && !isUsed].forEach[delete]	
		
		// orphant types
		originalCompilationUnitModel.javaModel.orphanTypes.map[copied].filter[!isUsed].forEach[delete]			
		
		// owned types
		copied(originalCompilationUnitModel.compilationUnit).types.forEach [
			var container = it.eContainer
			it.delete
			// also remove emptied packages
			while (container.eContents.empty && container instanceof NamedElement && !(container as NamedElement).isUsed) {
				val content = container
				container = container.eContainer
				EcoreUtil.remove(content)
			}
		]
		
		// compilation unit
		val cuCopy = copied(originalCompilationUnitModel.compilationUnit)
		cuCopy.delete
		
		// reset
		copier.clear
		outgoingLinks.clear
		incomingLinks.clear
		isAttached = false

		allInstances.remove(cuCopy)
		return cuCopy;
	}

	def getSource() {
		return originalCompilationUnitModel
	}

	private def void delete(EObject object) {
		val objects = object.eAllContents.toList
		objects.forEach[EcoreUtil.remove(it)]
		EcoreUtil.remove(object)
	}

	override toString() {
		var path = originalCompilationUnitModel.compilationUnit.originalFilePath
		if (path.contains("src")) {
			path = path.substring(path.lastIndexOf("src"))
		}
		return '''«path» at «(originalCompilationUnitModel?.eContainer?.eContainer?.eContainer?.eContainer as Rev)?.name»'''
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