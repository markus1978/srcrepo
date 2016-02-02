package de.hub.srcrepo.snapshot

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.snapshot.internal.SSCompilationUnitModel
import de.hub.srcrepo.snapshot.internal.SSLink
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.gmt.modisco.java.AnnotationTypeMemberDeclaration
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.LabeledStatement
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.Package
import org.eclipse.gmt.modisco.java.Type
import org.eclipse.gmt.modisco.java.UnresolvedItem
import org.eclipse.gmt.modisco.java.UnresolvedTypeDeclaration
import org.eclipse.gmt.modisco.java.VariableDeclaration
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.gmt.modisco.java.ASTNode

class ModiscoIncrementalSnapshotImpl implements IModiscoSnapshotModel {
	
	static val printDebug = false

	val JavaPackage metaModel
	val SSCopier copier
	val Model model

	/**
	 * A map that connects all CompilationUnits in the model, with the SSCompilationUnitModel they are from. 
	 */
	val Map<String, NamedElement> targets = newHashMap
	val Map<CompilationUnit, SSCompilationUnitModel> currentCompilationUnits = newHashMap
	
	val Map<CompilationUnitModel, SSCompilationUnitModel> currentCompilationUnitModels = newHashMap	
	val List<SSCompilationUnitModel> newCompilationUnitModels = newArrayList
	val List<SSCompilationUnitModel> oldCompilationUnitModels = newArrayList

	new(JavaPackage metaModel) {
		this.metaModel = metaModel;
		copier = new SSCopier(metaModel)
		model = metaModel.javaFactory.createModel
	}

	override getMetaModel() {
		return metaModel
	}
	
	override getTargets() {
		return targets
	}

	override addCompilationUnitModel(CompilationUnitModel model) {
		Preconditions.checkArgument(model != null)
		val ssCompilationUnitModel = new SSCompilationUnitModel(metaModel, model)
		newCompilationUnitModels += ssCompilationUnitModel
		currentCompilationUnitModels.put(model, ssCompilationUnitModel)
	}

	override removeCompilationUnitModel(CompilationUnitModel model) {
		oldCompilationUnitModels += currentCompilationUnitModels.remove(model)
	}
	
	override end() {
		if (!oldCompilationUnitModels.empty || !newCompilationUnitModels.empty) {
			computeSnapshot
		}
	}

	override getModel() {
		return model
	}

	private def computeSnapshot() {
		debug("#compute")
		val List<SSLink> linksToResolve = newArrayList

		// TODO
		// The current implementation removes unresolvedItems/orphanTypes/proxies 
		// before they could be replaced by equal elements in an in CU. This is
		// probably not very efficient.

		// 1. remove old CUs
		oldCompilationUnitModels.forEach [
			debug('''#remove: «it»''')
			
			// 1.1. revert all links the exit the CU
			it.outgoingLinks.forEach[revert]
			// 1.2. revert all links that target the CU
			// replace all references that enter old CUs with place holders and 
			// add them to the list of pending elements, since they need to be 
			// resolved again. Ignoring unresolved references, which must be
			// references that were delete one step before.
			it.incomingLinks.filter[resolved].forEach [
				it.revert
				linksToResolve += it				
			]
			
			// 1.3 remove the containment hierarchy
			remove(model, it.getOriginalCompilationUnitModel.javaModel, it.originalReverseTargets)
			currentCompilationUnits.remove(getCopyCompilationUnit)
			model.compilationUnits.remove(getCopyCompilationUnit)			
		]

		// 2 add new CUs
		// 2.1. add containment for all CUs
		newCompilationUnitModels.forEach [
			debug("#add: " + it)
			
			merge(model, it.getOriginalCompilationUnitModel.javaModel, it.originalReverseTargets, true)
			it.copyCompilationUnit = copier.get(it.getOriginalCompilationUnitModel.compilationUnit) as CompilationUnit
			currentCompilationUnits.put(it.getCopyCompilationUnit, it)	
		]
		// 2.2. add all references within new CUs
		copier.copyReferences
		// 2.3. collect all links added by the new CUs
		newCompilationUnitModels.forEach [
			outgoingLinks += originalCompilationUnitModel.unresolvedLinks.filter[copier.get(it.source) != null].map[ 
				new SSLink(it, copier.get(it.source) as ASTNode, metaModel.javaFactory.create(it.target.eClass) as NamedElement)				
			]
			linksToResolve += outgoingLinks
		]
		copier.clear
			
		// 3. resolve all new and re-resolve all priorly reverted references
		debug("resolving links: ")
		linksToResolve.filter[!resolved].forEach[
			debug("   " + it)
			val resolvedTarget = targets.get(it.id)
			if (resolvedTarget != null) {
				it.resolve(resolvedTarget)
				if (!resolvedTarget.proxy) {					
					val cum = currentCompilationUnits.get(resolvedTarget.originalCompilationUnit)
					cum.incomingLinks += it			
				}
			} else {
				Preconditions.checkState(false, "This should not happen.")
			}
		]
	}
	
	override start() {
		oldCompilationUnitModels.clear
		newCompilationUnitModels.clear
	}
	
	override toString() {
		return '''
			snapshot {
				current: [
					«FOR cu:currentCompilationUnits.values»
						«cu»
					«ENDFOR»
				]
				in: [
					«FOR in:newCompilationUnitModels»
						«in»
					«ENDFOR»
				]
				out: [
					«FOR out:oldCompilationUnitModels»
						«out»
					«ENDFOR»
				]
			}
		'''
	}
	
	override clear() {	
		EcoreUtil.delete(model, true)
		
		currentCompilationUnits.clear
		targets.clear
		newCompilationUnitModels.clear
		oldCompilationUnitModels.clear
	}

	private def void forEachChild(EObject object, (EReference,EObject)=>void action) {
		for(feature:object.eClass.EAllReferences.filter[containment]) {
			val children = if (feature.many) {
				object.eGet(feature) as List<EObject>
			} else {
				#{object.eGet(feature) as EObject}
			}
			for (child: children) {
				action.apply(feature, child)
			}			
		}
	}

	private def externalTarget(NamedElement object) {		
		return if (object instanceof Package) {
			false
		}else if (object instanceof UnresolvedItem) {
			true
		} else if (object.eContainingFeature == metaModel.model_OrphanTypes) {
			true
		} else {
			object.proxy
		}
	}
	
	private def isTarget(EObject object, Map<NamedElement, String> ids) {
		return object instanceof NamedElement && ids.get(object) != null
	}
	
	/**
	 * Merges the contents of the given objects. NamedElements are merged based on their IDs. 
	 */
	private def <T extends EObject> void merge(T copy, T original,  Map<NamedElement, String> originalTargets, boolean addContents) {
		Preconditions.checkArgument(copy.eClass.name == original.eClass.name)		
		original.forEachChild[feature,child|
			if (child.isTarget(originalTargets)) {
				// original child is target (a.k.a. NamedElement with registered id)
				val namedElementChild = child as NamedElement
				val id = originalTargets.get(namedElementChild)				
				var copyChild = targets.get(id)
				if (copyChild == null) {
					// copy of the child does not already exist
					copyChild = copier.shallowCopy(child) as NamedElement
					debug("#merge " + copyChild.name)
					Preconditions.checkState(feature.many) 
					(copy.eGet(feature) as List<EObject>).add(copyChild)					
					merge(copyChild, child, originalTargets, true)
				} else {
					// merge existing child copy with new original child
					if (!namedElementChild.externalTarget && copyChild.externalTarget) {
						// replace the existing external child copy (i.e. proxy, unresolved, orphant) with a "real" copy of the original
						Preconditions.checkState(copyChild.eContainmentFeature.many)
						val setting = (copyChild.eContainer as InternalEObject).eSetting(copyChild.eContainmentFeature)
						EcoreUtil.delete(copyChild, true)						
						copyChild = copier.shallowCopy(namedElementChild) as NamedElement
						(setting.get(false) as List<EObject>).add(copyChild)
						merge(copyChild, namedElementChild, originalTargets, true)				
					} else {
						// use the existing target						
						copier.put(namedElementChild, copyChild)
						merge(copyChild, namedElementChild, originalTargets, false)
					}
				}
				// update the copie's target map
				targets.put(id, copyChild)
			} else {
				if (addContents) {					
					val copyChild = copier.copy(child)
					if (feature.many) {
						(copy.eGet(feature) as List<EObject>).add(copyChild)
					} else {
						copy.eSet(feature, copyChild)
					}					
				}	
			}
		]
	} 
	
	private static class SSCopier extends EcoreUtil.Copier {		
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
		
		
		override put(EObject key, EObject value) {
			val result = super.put(key, value)
			Preconditions.checkArgument(result == null, "Cannot copy an element twice.")
			return result
		}
		
		def <T extends EObject> T shallowCopy(T original) {
			if (original == null) {
				return null;
			} else {
				val copy = createCopy(original);
				if (copy != null) {
					put(original, copy);
					val eClass = original.eClass();
					for (eAttribute : eClass.EAllAttributes.filter[changeable && !isDerived]) {
						copyAttribute(eAttribute, original, copy);
					}
	
					copyProxyURI(original, copy);
				}
	
				return copy as T;
			}
		}
	}
	
	private def <T extends EObject> void remove(T copy, T original, Map<NamedElement,String> originalTargets) {
		Preconditions.checkArgument(copy.eClass.name == original.eClass.name)				
		
		original.forEachChild[feature,child|
			if (child.isTarget(originalTargets)) {
				// original child is target (a.k.a. NamedElement with registered id)
				val namedElementChild = child as NamedElement
				val id = originalTargets.get(namedElementChild)				
				var copyChild = targets.get(id)
				if (copyChild != null) {
					// copy of the child exist
					remove(copyChild, namedElementChild, originalTargets)
					if (copyChild.externalTarget) {
						// remove if not still used
						if (!copyChild.used) {
							debug("#delete: " + copyChild.name)
							EcoreUtil.delete(copyChild, true) // TODO performance
							targets.remove(id)
						}
					} else if (namedElementChild instanceof Package) {
						if (namedElementChild.eContents.empty) {
							debug("#delete: " + copyChild.name)
							EcoreUtil.delete(copyChild, true) // TODO performance
							targets.remove(id)
						}
					} else if (!namedElementChild.externalTarget) {
						// remove						
						debug("#delete: " + copyChild.name)
						EcoreUtil.delete(copyChild, true) // TODO performance
						targets.remove(id)
					}					
				}
			} 
		]
	}
	
	private def isUsed(NamedElement it) {
		return !(usagesInImports.empty && switch (it) {
			Type: usagesInTypeAccess.empty
			AbstractMethodDeclaration: usages.empty && usagesInDocComments.empty
			LabeledStatement: usagesInBreakStatements.empty && usagesInContinueStatements.empty
			Package: usagesInPackageAccess.empty
			VariableDeclaration: usageInVariableAccess.empty
			AnnotationTypeMemberDeclaration: usages.empty 			
			UnresolvedItem: true
			default: throw new RuntimeException("unreachable " + (it instanceof UnresolvedTypeDeclaration))
		})
	}
	
	public static def debug(String message) {
		if (printDebug) {
			println(message)			
		}
	}
}