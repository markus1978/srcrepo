package de.hub.srcrepo.snapshot.internal

import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.NamedElement

import static de.hub.srcrepo.SrcRepoActivator.*

import static extension de.hub.srcrepo.ocl.OclExtensions.*

class SSLink {
	val ASTNode source
	val EReference feature 	
	val int featureIndex
	val String id
	
	var NamedElement placeHolder = null

	new(ASTNode source, EReference feature, int featureIndex, String id, NamedElement placeHolder) {
		condition[source != null]
		condition[source.eContainer != null]
		this.source = source
		this.feature = feature
		this.featureIndex = featureIndex
		this.id = id
		this.placeHolder = placeHolder
		replaceTarget(placeHolder)
	}
	
	def getId() {
		return id
	}
	
	def getCurrentTarget() {
		val source = getSource()
		if (feature.many) {
			return (source.eGet(feature) as List<NamedElement>).get(featureIndex)
		} else {
			return source.eGet(feature) as NamedElement
		}
	}
	
	def ASTNode getSource() {
		source
	}
	
	def isResolved() {
		return currentTarget != placeHolder 
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
			'''<unresolved>'''
		}
		return '''«source.eClass.name» -> «targetStr»'''
	}
	
	private def isPersistent(EObject obj) {	
		return obj.eRoot instanceof CompilationUnitModel	
	}
	
	private def NamedElement replaceTarget(NamedElement target) {
		val source = getSource() 

		if (feature.isMany()) {			
			val lst = source.eGet(feature) as EList<NamedElement>
			if (target != null) {
				return lst.set(featureIndex, target)				
			} else {
				return lst.remove(featureIndex)
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
