package de.hub.srcrepo.snapshot

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.repositorymodel.UnresolvedLink
import de.hub.srcrepo.snapshot.internal.SSCompilationUnitModel
import de.hub.srcrepo.snapshot.internal.SSLink
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.gmt.modisco.java.AnnotationTypeMemberDeclaration
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.LabeledStatement
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.Package
import org.eclipse.gmt.modisco.java.Type
import org.eclipse.gmt.modisco.java.UnresolvedItem
import org.eclipse.gmt.modisco.java.VariableDeclaration
import org.eclipse.gmt.modisco.java.emf.JavaPackage

import static extension de.hub.srcrepo.ocl.OclExtensions.*

class ModiscoIncrementalSnapshotImpl implements IModiscoSnapshotModel {
	
	static val printDebug = true

	val String projectID
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
		this(metaModel, "<default>")
	}
	
	new(JavaPackage metaModel, String projectID) {
		this.projectID = projectID
		this.metaModel = metaModel;
		copier = new SSCopier(metaModel)
		model = metaModel.javaFactory.createModel
	}
	
	override getTargets() {
		return targets
	}

	override addCompilationUnitModel(CompilationUnitModel model) {
		Preconditions.checkArgument(model != null, "Null model is not allowed.")
		Preconditions.checkArgument(model.eAllContents.forall[it.eSelectContainer[it instanceof CompilationUnitModel] == model], "Containment relation is broken.")
		val extension ssCompilationUnitModel = new SSCompilationUnitModel(model)
		newCompilationUnitModels += ssCompilationUnitModel
		currentCompilationUnitModels.put(model, ssCompilationUnitModel)
		
		originalCompilationUnitModel.targets.forEach[
			originalReverseTargets.put(target, id)
		]
		originalCompilationUnitModel.unresolvedLinks.forEach[
			if (originalReverseTargets.get(it.target) == null) {
				Preconditions.checkState(it.target instanceof UnresolvedItem)
				originalReverseTargets.put(it.target, it.fullId)
			}
		]
	}
	
	private def fullId(extension UnresolvedLink link) {
		if (link.target instanceof UnresolvedItem) {
			return target.eClass.name + ":" + id
		} else {
			return id
		}
	}

	override removeCompilationUnitModel(CompilationUnitModel model) {
		val oldCompilationUnitModel = currentCompilationUnitModels.remove(model)
		Preconditions.checkArgument(oldCompilationUnitModel != null)
		oldCompilationUnitModels += oldCompilationUnitModel
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
		val rev = if (!newCompilationUnitModels.empty) newCompilationUnitModels.findFirst[true]
			.originalCompilationUnitModel
			.eSelectContainer[it instanceof Rev] as Rev
		val revName = if (rev != null) {
			'''«projectID»[«rev.name»]'''
		} else ""
		debug('''#compute«if (revName != null) " " + revName»''')
		val List<SSLink> linksToResolve = newArrayList

		// TODO
		// The current implementation removes unresolvedItems/orphanTypes/proxies 
		// before they could be replaced by equal elements in an in CU. This is
		// probably not very efficient.

		// 1. remove old CUs
		oldCompilationUnitModels.forEach [
			debug('''  #remove: «it»''')
			
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
			removeContents(model, it.getOriginalCompilationUnitModel.javaModel, it.originalReverseTargets)
			currentCompilationUnits.remove(getCopyCompilationUnit)
			model.compilationUnits.remove(getCopyCompilationUnit)			
		]

		// 2 add new CUs
		// 2.1. add containment for all CUs
		newCompilationUnitModels.forEach [
			debug("  #add: " + it)
			
			mergeContents(model, it.getOriginalCompilationUnitModel.javaModel, it.originalReverseTargets, true)
			it.copyCompilationUnit = copier.get(it.getOriginalCompilationUnitModel.compilationUnit) as CompilationUnit
			currentCompilationUnits.put(it.getCopyCompilationUnit, it)	
		]
		// 2.2. add all references within new CUs
		copier.copyReferences
		// 2.3. collect all links added by the new CUs
		debug("  #add links")
		newCompilationUnitModels.forEach [
			outgoingLinks += originalCompilationUnitModel.unresolvedLinks.filter[copier.get(it.source) != null].map[ 
				val link = new SSLink(it, copier.get(it.source) as ASTNode, it.fullId, metaModel.javaFactory.create(copier.getTarget(it.target.eClass)) as NamedElement)
				debug("    #new link: " + link)
				return link
			]
			linksToResolve += outgoingLinks
		]
		copier.clear
			
		// 3. resolve all new and re-resolve all priorly reverted references
		debug("  #resolving links: ")
		linksToResolve.filter[!resolved].forEach[
			debug("    #" + it)
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
		debug("# clear ###################################")
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
					val value = object.eGet(feature) as EObject
					if (value == null) #{} else #{value}					
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
		return object instanceof NamedElement && (ids.get(object) != null || (object as NamedElement).name == null)
	}
	
	private def NamedElement getCopyChild(EObject copy, EObject original, NamedElement originalChild, Map<NamedElement,String> ids) {
		val id = ids.get(originalChild)
		if (id == null) {
			Preconditions.checkState(originalChild.name == null, "All NamedElements with name need to be targets.")
			// unnamed named element, assume each container can only have one of those within same containment feature
			val feature = copier.getTarget(originalChild.eContainmentFeature)
			val value = if (feature.many) {
				(copy.eGet(feature) as List<NamedElement>).findFirst[it.eClass == copier.getTarget(originalChild.eClass) && name == null]
			} else {
				val singelValue = copy.eGet(feature) as NamedElement
				if (singelValue.eClass == copier.getTarget(originalChild.eClass) && singelValue.name == null) singelValue else null					
			}
			return value
		} else {
			// named element with target
			val result = targets.get(id)
			Preconditions.checkState(result == null || result.eClass.name == originalChild.eClass.name)
			return result
		}		
	}
	
	/**
	 * Merges the contents of the given objects. NamedElements are merged based on their IDs. 
	 */
	private def <T extends EObject> void mergeContents(T copy, T original,  Map<NamedElement, String> originalTargets, boolean addContents) {
		Preconditions.checkArgument(copy.eClass.name == original.eClass.name)		
		original.forEachChild[originalFeature,originalChild|
			val copyFeature = copier.getTarget(originalFeature)
			if (originalChild.isTarget(originalTargets)) {
				val originalNamedElementChild = originalChild as NamedElement
				var copyChild = getCopyChild(copy, original, originalNamedElementChild, originalTargets)			
				if (copyChild != null) {
					// original child has a corresponding other child					
					// merge existing child copy with new original child
					if (!originalNamedElementChild.externalTarget && copyChild.externalTarget) {
						// push attributes and other content from new internal target onto existing external target
						debug("    #merge(new attribute values) " + copyChild.name)
						copier.merge(copyChild, originalNamedElementChild)																		
						mergeContents(copyChild, originalNamedElementChild, originalTargets, true)				
					} else {
						// use the existing target						
						debug("    #merge " + copyChild.name)
						copier.put(originalNamedElementChild, copyChild)
						mergeContents(copyChild, originalNamedElementChild, originalTargets, false)
					}
				} else {
					// copy of the child does not already exist
					debug("    #new " + originalNamedElementChild.name)		
					copyChild = copier.shallowCopy(originalChild) as NamedElement
					Preconditions.checkState(copyFeature.many) 
					(copy.eGet(copyFeature) as List<EObject>).add(copyChild)	
					Preconditions.checkState(copyChild.eContainmentFeature == copyFeature)
					Preconditions.checkState(copyChild.eContainer == copy)								
					mergeContents(copyChild, originalChild, originalTargets, true)											
				}
				// update the copie's target map
				val id = originalTargets.get(originalChild)
				if (id != null) {
					targets.put(id, copyChild)				
				}
			} else {
				if (addContents) {					
					val copyChild = copier.copy(originalChild)
					if (copyFeature.many) {
						(copy.eGet(copyFeature) as List<EObject>).add(copyChild)
					} else {
						copy.eSet(copyFeature, copyChild)
					}					
				}	
			}
		]
	} 
	
	private static class SSCopier extends EcoreUtil.Copier {		
		val JavaPackage metaModel
		val Map<EObject,EObject> reverseMap = newHashMap
		
		new(JavaPackage metaModel) {
			this.metaModel = metaModel
		}
	
		override getTarget(EClass eClass) { // TODO performance
			return metaModel.getEClassifier(eClass.getName()) as EClass
		}
	
		override getTarget(EStructuralFeature eStructuralFeature) { // TODO performance
			val eClass = eStructuralFeature.getEContainingClass();
			return getTarget(eClass).getEStructuralFeature(eStructuralFeature.getName());
		}
		
		override put(EObject key, EObject value) {
			val result = super.put(key, value)
			reverseMap.put(value, key)
			Preconditions.checkArgument(result == null, "Cannot copy an element twice.")
			return result
		}
		
		override remove(Object key) {
			val oldValue = super.remove(key)
			if (oldValue != null) reverseMap.remove(oldValue)			
		}
		
		override clear() {
			super.clear
			reverseMap.clear
		}
		
		public def <T extends EObject> T getLastOriginal(T copy) {
			return reverseMap.get(copy) as T
		}
		
		def void merge(EObject copy, EObject original) {
			if (copy != null) {
				put(original, copy);
				val eClass = original.eClass();
				for (eAttribute : eClass.EAllAttributes.filter[changeable && !isDerived]) {
					copy.eUnset(getTarget(eAttribute));
					copyAttribute(eAttribute, original, copy);
				}
	
				copyProxyURI(original, copy);
			}
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
	
	private def <T extends EObject> void removeContents(T copy, T original, Map<NamedElement,String> originalTargets) {
		Preconditions.checkArgument(copy.eClass.name == original.eClass.name)				
		
		original.forEachChild[feature,child|
			if (child.isTarget(originalTargets)) {
				val namedElementChild = child as NamedElement
				var copyChild = getCopyChild(copy, original, namedElementChild, originalTargets)			
				if (copyChild != null) {
					// copy of the child exist
					removeContents(copyChild, namedElementChild, originalTargets)			
					val delete = if (copyChild.externalTarget) {
						if (copyChild.name == null) {
							// unnamed container, e.g. FieldDeclaration with fragments
							namedElementChild.eContents.empty
						} else {
							!copyChild.used							
						}
					} else if (namedElementChild instanceof Package) {
						namedElementChild.eContents.empty
					} else if (!namedElementChild.externalTarget) {
						true
					}		
					
					if (delete) {
						debug("    #delete: " + copyChild.name)
						EcoreUtil.delete(copyChild, true) // TODO performance
						val id = originalTargets.get(namedElementChild)
						if (id != null) {
							targets.remove(id)						
						}
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
			default: 
				throw new RuntimeException("unreachable " + it.eClass)
		})
	}
	
	public static def debug(String message) {
		if (printDebug) {
			println(message)			
		}
	}
}