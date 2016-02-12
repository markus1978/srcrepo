package de.hub.srcrepo.snapshot.internal

import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.UnresolvedLink
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.NamedElement

import static extension de.hub.srcrepo.ocl.OclExtensions.*
import static extension de.hub.srcrepo.SrcRepoActivator.*

class SSLink {
	val ASTNode copiedSource
	val UnresolvedLink originalUnresolvedLink 	
	val String id
	var NamedElement placeHolder = null

	new(UnresolvedLink originalUnresolvedLink, ASTNode copiedSource, String id, NamedElement placeHolder) {
		if (copiedSource.eContainer == null) {
			val container = originalUnresolvedLink.source.eContainer
			println(container)
			val cf = copiedSource.eContainingFeature
			println(cf)
		}
		condition[copiedSource.eContainer != null]
		this.originalUnresolvedLink = originalUnresolvedLink
		this.copiedSource = copiedSource
		this.placeHolder = placeHolder
		this.id = id
		replaceTarget(placeHolder)
		condition[!resolved]
	}
	
	def getCurrentTarget() {
		val source = getSource()
		val feature = source.eClass().getEStructuralFeature(originalUnresolvedLink.featureID) as EReference
		if (feature.many) {
			return (source.eGet(feature) as List<NamedElement>).get(originalUnresolvedLink.featureIndex)
		} else {
			return source.eGet(feature) as NamedElement
		}
	}
	
	def ASTNode getSource() {
		copiedSource
	}
	
	def isResolved() {
		return currentTarget != placeHolder 
	}
	
	def getId() {
		return id
	}

	def resolve(NamedElement resolvedTarget) {
		condition("You cannot resolve an resolved link.")[!isResolved]
		condition("You can only resolve a link with a target from an snaptshot model.")[!resolvedTarget.isPersistent]
		condition("Resolved target and source must be in the same model.")[source.eRoot == resolvedTarget.eRoot] // TODO zest
		
		replaceTarget(resolvedTarget)
		return resolvedTarget
	}
	
	def revert() {
		condition("Only resolved links can be reverted.")[isResolved] // TODO zest
		replaceTarget(placeHolder)
	}
	
	override toString() {
		val targetStr = if (resolved) {
			'''«currentTarget.name»'''
		} else {
			'''<unresolved:«id»>'''
		}
		return '''«source.eClass.name» -> «targetStr»'''
	}
	
	private def isPersistent(EObject obj) {	
		return obj.eRoot instanceof CompilationUnitModel	
	}
	
	private def NamedElement replaceTarget(NamedElement target) {
		val source = getSource()
		val feature = source.eClass().getEStructuralFeature(originalUnresolvedLink.featureID) as EReference

		if (feature.isMany()) {			
			val lst = source.eGet(feature) as EList<NamedElement>
			if (target != null) {
				return lst.set(originalUnresolvedLink.featureIndex, target)				
			} else {
				return lst.remove(originalUnresolvedLink.featureIndex)
			}
		} else {
			val old = source.eGet(feature) as NamedElement
			if (target != null) {
				source.eSet(feature, target);				
			} else {
				source.eUnset(feature)
			}
			return old
		}
	}
}
