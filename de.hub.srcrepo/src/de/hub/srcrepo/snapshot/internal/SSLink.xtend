package de.hub.srcrepo.snapshot.internal

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.UnresolvedLink
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.NamedElement

class SSLink {
	val ASTNode copiedSource
	val UnresolvedLink originalUnresolvedLink 	

	var NamedElement placeHolder = null

	new(UnresolvedLink originalUnresolvedLink, ASTNode copiedSource, NamedElement placeHolder) {
		this.originalUnresolvedLink = originalUnresolvedLink
		this.copiedSource = copiedSource
		this.placeHolder = placeHolder
		replaceTarget(placeHolder)
		Preconditions.checkState(!resolved)
		
		println("#new link: " + toString)
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
		return originalUnresolvedLink.id
	}

	def resolve(NamedElement resolvedTarget) {
		Preconditions.checkState(!isResolved, "You cannot resolve an resolved link.")
		Preconditions.checkArgument(!resolvedTarget.isPersistent, "You can only resolve a link with a target from an snaptshot model.")
		Preconditions.checkArgument(source.root == resolvedTarget.root, "Resolved target and source must be in the same model.")
		
		println("#resolve: ->" + (resolvedTarget as NamedElement).name)
		replaceTarget(resolvedTarget)
		if (!resolvedTarget.proxy) {
			val cum = SSCompilationUnitModel.get(resolvedTarget.originalCompilationUnit)
			cum.incomingLinks += this			
		}
	}
	
		def revert() {
		Preconditions.checkState(isResolved, "Only resolved links can be reverted.")
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
		return obj.root instanceof CompilationUnitModel	
	}
	
	private def root(EObject obj) {
		var root = obj
		while (root.eContainer != null) root = root.eContainer
		return root	
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
