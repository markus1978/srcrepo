package de.hub.srcrepo.snapshot.internal

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.UnresolvedLink
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.NamedElement

class SSPendingElement {
	val SSCopier copier
	val UnresolvedLink originalUnresolvedLink 	
	var NamedElement resolvedTarget = null
	var NamedElement unresolvedTarget = null

	new(SSCopier copier, UnresolvedLink originalUnresolvedLink) {
		this.copier = copier
		this.originalUnresolvedLink = originalUnresolvedLink
		this.unresolvedTarget = copier.copied(originalUnresolvedLink.target)
	}

	def resolve(NamedElement resolvedTarget) {
		println("#resolve: ->" + (resolvedTarget as NamedElement).name)
		Preconditions.checkState(!resolved)
		this.resolvedTarget = resolvedTarget
		unresolvedTarget = replaceTarget(resolvedTarget)

//		if (!(target instanceof UnresolvedItem)) {
//			val cum = SSCompilationUnitModel.get(target.originalCompilationUnit)
//			cum.incomingReferences += this
//		}
	}
	
	private def ASTNode getSource() {
		copier.copied(originalUnresolvedLink.source)
	}
	
	private def NamedElement replaceTarget(NamedElement target) {
		val source = getSource()
		val feature = source.eClass().getEStructuralFeature(originalUnresolvedLink.featureID) as EReference

		if (feature.isMany()) {			
			val lst = source.eGet(feature) as EList<NamedElement>
			return lst.set(originalUnresolvedLink.featureIndex, target)
		} else {
			val old = source.eGet(feature) as NamedElement
			source.eSet(feature, target);
			return old
		}
	}

	def isResolved() {
		return resolvedTarget != null
	}

	def revert() {
		Preconditions.checkState(resolved)		
		replaceTarget(unresolvedTarget);
		resolvedTarget = null
	}

//	def delete() {
//		Preconditions.checkState(resolved)
//		println("#delete: ->" + resolvedTarget.name)
//	
//		val source = getSource()
//		val feature = source.eClass().getEStructuralFeature(originalUnresolvedLink.featureID) as EReference
//
//		val index = originalUnresolvedLink.featureIndex
//		if (index != -1) {
//			(source.eGet(feature) as EList<EObject>).remove(index)
//		} else {
//			source.eUnset(feature)
//		}
//		resolvedTarget = null
//	}
	
	override toString() {
		val targetStr = if (resolvedTarget == null) {
			'''unresolved[«unresolvedTarget.name»]'''
		} else {
			resolvedTarget.name
		}
		return '''«source.eClass.name» -> «targetStr»'''
	}
	
}
