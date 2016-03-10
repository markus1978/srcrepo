package de.hub.srcrepo

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

class EMFPrettyPrint {

	val (EObject,EStructuralFeature,Object)=>String additionalValueSerialization
	
	private new((EObject,EStructuralFeature,Object)=>String additionalValueSerialization) {
		this.additionalValueSerialization = additionalValueSerialization
	}
	
	public static def String prettyPrint(EObject eObject) {
		val pretterPrinter = new EMFPrettyPrint[null]
		return pretterPrinter.prettyPrintObject(eObject)
	}
	
	public static def String prettyPrint(EObject eObject, (EObject,EStructuralFeature,Object)=>String additionalValueSerialization) {
		val pretterPrinter = new EMFPrettyPrint(additionalValueSerialization)
		return pretterPrinter.prettyPrintObject(eObject)
	}
	
	private def String prettyPrintObject(EObject eObject) {
		val features = eObject.eClass.EAllStructuralFeatures
			.filter[!isDerived && !isTransient && !isVolatile]
			.filter[eObject.eIsSet(it)]		
			.filter[eObject.eGet(it) != eObject.eContainer]	
			.filter[it.name != "name"].toList
			
		val ext = additionalValueSerialization.apply(eObject, null, null)
		val signature = '''«eObject.signature»«IF (ext!=null)» («ext»)«ENDIF»'''
		return if (features.empty) {
			eObject.signature
		} else {
			'''
				«signature» {
					«FOR feature:features»
						«prettyPrintFeature(eObject,feature)»
					«ENDFOR»
				}
			'''.toString.replace("\t", "  ")
		}	
	}
	
		
	public static def signature(EObject eObject) {
		return '''«eObject.name»«IF (eObject.name != "")» «ENDIF»[«eObject.eClass.name»]'''
	}
	
	private def prettyPrintFeature(EObject eObject, EStructuralFeature feature) {
		if (feature.many) {
			'''
				«feature.name» = [
					«FOR value: eObject.eGet(feature) as List<?>»
						«prettyPrintValue(eObject, value, feature)»
					«ENDFOR»
				]
			'''
		} else {
			'''«feature.name» = «prettyPrintValue(eObject, eObject.eGet(feature), feature)»'''
		}
	}
	
	private def prettyPrintValue(EObject container, Object object, EStructuralFeature feature) {
		if (object == null) {
			return null
		}
		
		val value = switch (object) {
			EObject: if ((feature as EReference).containment) {
					prettyPrintObject(object)
				} else {
					'''ref<«object.signature»>'''
				}
			String: object.normalize
			default: object.toString
		}
		
		val ext = additionalValueSerialization.apply(container, feature, object)
		
		return value + if (ext != null) ''' («ext»)''' else ""
	}
	
	public static def name(EObject eObject) {
		val eClass = eObject.eClass
		val nameAttr = eClass.EAllAttributes.filter[!many].findFirst[it.name.toLowerCase == "name" || it.name.toLowerCase == "id"]
		return if (nameAttr != null)
			eObject.eGet(nameAttr)?.toString?.normalize
		else 
			""
	}
	
	public static def normalize(String str) {
		if (str.length > 32) str.substring(0, 30) + "..." else str
	}
}