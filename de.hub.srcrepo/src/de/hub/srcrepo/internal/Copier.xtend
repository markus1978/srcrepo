package de.hub.srcrepo.internal

import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.EObject

class Copier extends EcoreUtil.Copier {		
	static val Map<EClass, EClass> classCache = newHashMap
	static val Map<EStructuralFeature,EStructuralFeature> featureCache = newHashMap
	
	val List<EPackage> targetMetaModels = newArrayList
	
	new(EPackage[] targetMetaModels) {
		targetMetaModels.forEach[this.targetMetaModels += it]
	}
	
	public static def <T extends EObject> T copy(T eObject, EPackage... targetMetaModels) {
		val copier = new Copier(targetMetaModels)
		val result = copier.copy(eObject)
		copier.copyReferences
		return result as T
	}
	
	public def <T extends EObject> T copyWithReferences(T eObject) {
		clear
		val result = copy(eObject)
		copyReferences
		return result as T
	}
	
	override getTarget(EClass eClass) {
		return classCache.access(eClass)[
			targetMetaModels.map[it.getEClassifier(eClass.getName()) as EClass].findFirst[it != null]
		]			
	}

	override getTarget(EStructuralFeature eStructuralFeature) {
		return featureCache.access(eStructuralFeature)[
			val eClass = eStructuralFeature.getEContainingClass();
			eClass.target.getEStructuralFeature(eStructuralFeature.getName());	
		]			
	}
	
	private static def <K,V> V access(Map<K,V> cache, K key, (K)=>V create) {
		val existingValue = cache.get(key)
		return if (existingValue == null) {
			val newValue = create.apply(key)
			cache.put(key, newValue)
			newValue
		} else {
			existingValue
		}
	}		
}