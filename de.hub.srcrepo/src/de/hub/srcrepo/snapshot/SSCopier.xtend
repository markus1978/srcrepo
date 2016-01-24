package de.hub.srcrepo.snapshot

import com.google.common.base.Preconditions
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.emf.JavaPackage

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