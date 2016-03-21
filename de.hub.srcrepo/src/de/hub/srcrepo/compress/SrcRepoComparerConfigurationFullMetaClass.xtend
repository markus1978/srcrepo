package de.hub.srcrepo.compress

import de.hub.emfcompress.ComparerConfiguration
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage
import de.hub.srcrepo.repositorymodel.Target
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.TypeAccess
import org.eclipse.gmt.modisco.java.emf.JavaPackage

abstract class SrcRepoComparerConfigurationFullMetaClass implements ComparerConfiguration {

	val RepositoryModelPackage repositoryMetaModel
	val JavaPackage javaMetaModel
	
	new(JavaPackage javaMetaModel, RepositoryModelPackage repositoryModelPackage) {
		this.javaMetaModel = javaMetaModel
		this.repositoryMetaModel = repositoryModelPackage
	}

	protected abstract def String id(TypeAccess typeAccess, boolean original)
	
	override ignore(EStructuralFeature feature) {
		return false
	}
	
	override compareWithMatch(EClass eClass, EReference reference) {
		return true	
	}
	
	override match(EObject original, EObject revised) {
		return switch original {
			CompilationUnit: original.name == (revised as CompilationUnit).name
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
				if (original.name == null || (revised as NamedElement).name == null) {
					false
				} else {
					original.name == (revised as NamedElement).name				
				}
			}
			default: original.eClass == revised.eClass
		}
	}
	
}