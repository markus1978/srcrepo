package de.hub.srcrepo.snapshot.internal

import com.google.common.base.Preconditions
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.emf.JavaPackage

class SSCopier extends EcoreUtil.Copier {
	val JavaPackage metaModel
	val Map<EObject, EObject> reverseCopiedMap = newHashMap

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
	 * 
	 * It also goes up existing containment hierarchies to add respective entries to the 
	 * copied map.
	 */
	def <T extends EObject, R extends EObject> T copy(R ctxCopy, R ctxOriginal, NamedElement original) {
		val parentCopy = if (original.eContainer != ctxOriginal) {
				copy(ctxCopy, ctxOriginal, original.eContainer as NamedElement)
			} else {
				ctxCopy
			}

		val existing = parentCopy.eContents.findFirst [
			eClass == original.eClass.target && (it as NamedElement).name == original.name
		] as T
		if (existing == null) {
			val feature = getTarget(original.eContainingFeature)
			val copy = copy(original)
			Preconditions.checkState(feature.isMany)
			(parentCopy.eGet(feature) as List<EObject>).add(copy)
			return copy as T
		} else {
			put(original, existing) // TODO elements contained in the original are not merged into the existing hierarchy
			return existing as T
		}
	}

	def <T extends EObject> T copyt(T source) {
		return super.copy(source) as T
	}

	def <T extends EObject> T copied(T original) {
		val result = super.get(original) as T
		Preconditions.checkArgument(result != null)
		return result;
	}
	
	def <T extends EObject> T ifCopied(T original) {
		return super.get(original) as T
	}
	
	def <T extends EObject> T original(T copy) {
		val result = reverseCopiedMap.get(copy) as T
		Preconditions.checkArgument(result != null)
		return result;
	}
	
	override put(EObject key, EObject value) {
		super.put(key, value)
		reverseCopiedMap.put(value, key)
	}
	
	override remove(Object key) {
		val oldValue = super.remove(key)
		reverseCopiedMap.remove(oldValue)
		return oldValue
	}
	
}