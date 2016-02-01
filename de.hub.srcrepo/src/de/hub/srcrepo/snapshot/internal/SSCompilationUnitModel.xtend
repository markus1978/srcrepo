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
import org.eclipse.jdt.core.dom.rewrite.TargetSourceRangeComputer.SourceRange

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

	def void removeTargets(Map<String, NamedElement> allTargets) {
		originalCompilationUnitModel.targets.filter[!target.copied.isUsed].forEach[allTargets.remove(id)]
	}

	def copyReferences() {
		copier.copyReferences
	}
	
	def addLinksToModel(Model model) {
		// orphan types
		originalCompilationUnitModel.javaModel.orphanTypes.forEach[
			copy(model, originalCompilationUnitModel.javaModel, it)
		]
		
		// unresolved types and proxy targets
		originalCompilationUnitModel.unresolvedLinks.filter[target != null && target instanceof UnresolvedItem].forEach[			
			copy(model, originalCompilationUnitModel.javaModel, target)			
		]
		
		// proxy targets
		originalCompilationUnitModel.targets.map[target].filter[proxy].forEach[
			copy(model, originalCompilationUnitModel.javaModel, it)
		]
		
		// populate outgoingLinks
		Preconditions.checkState(outgoingLinks.empty)
		outgoingLinks += originalCompilationUnitModel.unresolvedLinks.map[
			new SSLink(it, copied(it.source), snapshot.metaModel.javaFactory.create(it.target.eClass) as NamedElement)
		]
	}
	
	def addCompilationUnitToModel(Model model) {
		Preconditions.checkState(!isAttached, "Can only be attached to the snapshot model once.")
				
		// compilation unit
		val cuCopy = copyt(originalCompilationUnitModel.compilationUnit)
		model.compilationUnits.add(cuCopy)
		
		// owned types
		originalCompilationUnitModel.compilationUnit.types.forEach [
			val existing = copy(model, originalCompilationUnitModel.javaModel, it)
			Preconditions.checkState(!existing.proxy)
		]
		isAttached = true
		allInstances.put(cuCopy, this)
		return cuCopy
	}
	
	def void fillTargets(Map<String, NamedElement> allTargets) {
		Preconditions.checkState(isAttached)
		originalCompilationUnitModel.targets.forEach[
			val existing = allTargets.get(id)
			if (existing == null || existing.isProxy) {
				allTargets.put(id, target.copied) // TODO there are proxy targets in the model not covered by unresolved references
			}
		]
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
		originalCompilationUnitModel.unresolvedLinks.map[target?.copied].filter[it != null && (proxy || eContainmentFeature == snapshot.metaModel.model_OrphanTypes || it instanceof UnresolvedItem) && !isUsed].forEach[delete]	
		
		// orphant types
		originalCompilationUnitModel.javaModel.orphanTypes.map[copied].filter[!isUsed].forEach[delete]			
		
		// owned types
		copied(originalCompilationUnitModel.compilationUnit).types.forEach [
			var container = it.eContainer
			it.delete
			// also remove emptied packages
			while (container != null && container.eContents.empty && container instanceof NamedElement && !(container as NamedElement).isUsed) {
				val content = container
				container = content.eContainer
				content.delete
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
		if (object instanceof NamedElement) {
			println("#delete: " + object.name)		
		}
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