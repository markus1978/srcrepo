package de.hub.srcrepo.metrics

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

interface AnalysisVisitor {
	def void visit(EObject object, EStructuralFeature feature, Object value)
	
	static def void traverse(EObject obj, AnalysisVisitor visitor) {
		// visitor that traverses down the containment hierarchy		
		val AnalysisVisitor extendedVisitor = [o,f,v|
			val reference = f as EReference
			if(!reference.derived) {
				visitor.visit(o,f,v)
				if (reference.containment) {
					traverse(v as EObject, visitor)
				}				
			}
		]
		// go through all references and corresponding values
		obj.eClass.EAllReferences.forEach[
			if (it.isMany) {
				for (value:obj.eGet(it) as List<EObject>) {
					extendedVisitor.visit(obj, it as EReference, value)
				}
			} else {
				val value = obj.eGet(it) as EObject
				if (value != null) {
					extendedVisitor.visit(obj, it as EReference, value)					
				}	
			}			
		]
		obj.eClass.EAllAttributes.forEach[
			if (it.isMany) {
				for (value:obj.eGet(it) as List<EObject>) {
					visitor.visit(obj,it,value)
				}
			} else {
					visitor.visit(obj,it,obj.eGet(it))
			}
		]
	}	
}