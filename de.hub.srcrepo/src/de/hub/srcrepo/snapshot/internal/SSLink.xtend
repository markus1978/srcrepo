package de.hub.srcrepo.snapshot.internal

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.UnresolvedLink
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.UnresolvedItem
import java.lang.reflect.ParameterizedType
import javax.lang.model.type.PrimitiveType
import java.lang.reflect.WildcardType
import javax.lang.model.type.ArrayType

class SSLink {
	val ASTNode copiedSource
	val UnresolvedLink originalUnresolvedLink 	
	var NamedElement resolvedTarget = null
	var NamedElement unresolvedTarget = null

	new(UnresolvedLink originalUnresolvedLink, ASTNode copiedSource, NamedElement copiedUnresolvedTarget) {
		this.originalUnresolvedLink = originalUnresolvedLink
		this.unresolvedTarget = copiedUnresolvedTarget
		this.copiedSource = copiedSource
	}
	
	def getId() {
		return originalUnresolvedLink.id
	}

	def resolve(NamedElement resolvedTarget) {
		println("#resolve: ->" + (resolvedTarget as NamedElement).name)
		Preconditions.checkState(!resolved && !deleted)
		this.resolvedTarget = resolvedTarget
		unresolvedTarget = replaceTarget(resolvedTarget)

		if (!resolvedTarget.proxy) {
			val isValidTarget = switch (resolvedTarget) {
				UnresolvedItem: false
				ParameterizedType: false 
				PrimitiveType: false
				WildcardType: false
				ArrayType: false
				default: true
			}
			if (isValidTarget) {
				val cum = SSCompilationUnitModel.get(resolvedTarget.originalCompilationUnit)
				cum.incomingLinks += this
			}
		} 		
	}
	
	private def ASTNode getSource() {
		copiedSource
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

	def isResolved() {
		return resolvedTarget != null
	}
	
	def isMerged() {
		return resolvedTarget == unresolvedTarget && resolvedTarget != null
	}
	
	def isDeleted() {
		return resolvedTarget == unresolvedTarget && unresolvedTarget == null
	}

	def revert() {
		Preconditions.checkState(resolved && !merged && !deleted)	
		println("#revert: ->" + (unresolvedTarget as NamedElement).name)	
		replaceTarget(unresolvedTarget);
		resolvedTarget = null
	}
	
	def delete() {
		Preconditions.checkState(resolved && !deleted)
		replaceTarget(null)
		resolvedTarget = null
		unresolvedTarget = null
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
