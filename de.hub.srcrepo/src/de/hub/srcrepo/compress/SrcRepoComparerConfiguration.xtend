package de.hub.srcrepo.compress

import de.hub.emfcompress.ComparerConfiguration
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.Target
import de.hub.srcrepo.repositorymodel.UnresolvedLink
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.TypeAccess

abstract class SrcRepoComparerConfiguration implements ComparerConfiguration {

	protected abstract def String id(TypeAccess typeAccess, boolean original)
	
	override ignore(EStructuralFeature feature) {
		return false
	}
	
	override compareWithMatch(EObject original, EObject revised) {
		if (original.eClass == revised.eClass) {
			return original instanceof NamedElement ||
				original instanceof UnresolvedLink ||
				original instanceof Target ||
				original instanceof Model
		} else {
			return false
		}
	}
	
	override match(EObject original, EObject revised, (EObject,EObject)=>boolean match) {
		return switch original {
			UnresolvedLink: {
				val revisedLink = revised as UnresolvedLink
				return original.id == revisedLink.id &&
					original.featureID == revisedLink.featureID &&
					match.apply(original.source,revisedLink.source)
			}
			Target: original.id == (revised as Target).id
			Model: true
			CompilationUnitModel: true
			AbstractMethodDeclaration: {
				val revisedMethod = revised as AbstractMethodDeclaration
				return if (original.name == revisedMethod.name) {					
					if (original.parameters.size == revisedMethod.parameters.size) {
						var compatibleSignature = true
						for(i:0..<original.parameters.size) {
							val originalParameter = original.parameters.get(0)
							val revisedParameter = revisedMethod.parameters.get(0)
							compatibleSignature = compatibleSignature && originalParameter.type.id(true) == revisedParameter.type.id(false)
						}
						compatibleSignature
					} else {
						false
					}
				} else {
					false
				}
			}
			NamedElement: {
				original.name == (revised as NamedElement).name
			}
			default: throw new RuntimeException("unreachable")
		}
	}
	
}