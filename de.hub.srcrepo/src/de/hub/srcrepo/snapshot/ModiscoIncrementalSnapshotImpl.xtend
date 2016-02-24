package de.hub.srcrepo.snapshot

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.repositorymodel.UnresolvedLink
import de.hub.srcrepo.snapshot.internal.SSCompilationUnitModel
import de.hub.srcrepo.snapshot.internal.SSLink
import java.util.Collection
import java.util.HashSet
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer
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

import static de.hub.srcrepo.SrcRepoActivator.*

import static extension de.hub.srcrepo.ocl.OclExtensions.*
import static extension de.hub.srcrepo.snapshot.internal.DebugAdapter.*

class ModiscoIncrementalSnapshotImpl implements IModiscoSnapshotModel {

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
	
	override <T extends EObject> getPersistedOriginal(T source) {
		throw new RuntimeException("not implemented")
	}
	
	override checkCompilationUnitModel(CompilationUnitModel model) {
		if (currentCompilationUnitModels.get(model) == null) {
			addCompilationUnitModel(model)
		}
	}

	override addCompilationUnitModel(CompilationUnitModel model) {
		condition("Null model is not allowed.")[model != null]
		condition("Containment relation is broken.")[
			model.eAllContents.forall[
				it.eSelectContainer[it instanceof CompilationUnitModel] == model
			]
		]
		val extension ssCompilationUnitModel = new SSCompilationUnitModel(model)
		newCompilationUnitModels += ssCompilationUnitModel
		currentCompilationUnitModels.put(model, ssCompilationUnitModel)
		
		originalCompilationUnitModel.targets.forEach[
			originalReverseTargets.put(target, id)
		]
		originalCompilationUnitModel.unresolvedLinks.forEach[
			if (originalReverseTargets.get(it.target) == null) {
				condition[it.target instanceof UnresolvedItem]
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
		condition[oldCompilationUnitModel != null]
		oldCompilationUnitModels += oldCompilationUnitModel
	}
	
	override end() {
		if (!oldCompilationUnitModels.empty || !newCompilationUnitModels.empty) {
			computeSnapshot
		}
		return true
	}

	override getModel() {
		return model
	}

	private def computeSnapshot() {		
		debug[
			val rev = if (!newCompilationUnitModels.empty) { 
				newCompilationUnitModels.findFirst[true]
					.originalCompilationUnitModel
					.eSelectContainer[it instanceof Rev] as Rev
			}
			val revName = if (rev != null) {
				'''«projectID»[«rev.name»]'''
			} else {
				""				
			}			
			'''#compute«if (revName != null) " " + revName»'''
		]
		val List<SSLink> linksToResolve = newArrayList

		// TODO performance
		// The current implementation removes unresolvedItems/orphanTypes/proxies 
		// before they could be replaced by equal elements in an in CU. This is
		// probably not very efficient.

		// 1. remove old CUs
		val toDelete = new HashSet<EObject>
		
		// 1.1 remove the containment hierarchy
		for(it:oldCompilationUnitModels) {
			debug['''  #remove: «it»''']
			removeContents(model, it.getOriginalCompilationUnitModel.javaModel, it.originalReverseTargets, toDelete)
			currentCompilationUnits.remove(getCopyCompilationUnit)
			model.compilationUnits.remove(getCopyCompilationUnit)
		}
		deleteCrossReferences(toDelete, model)
		
		for(it:oldCompilationUnitModels) {
			// 1.2. revert all links the exit the CU
			for(it:it.outgoingLinks) {revert}
			// 1.3. revert all links that target the CU
			// replace all references that enter old CUs with place holders and 
			// add them to the list of pending elements, since they need to be 
			// resolved again. Ignoring unresolved references, which must be
			// references that were delete one step before.
			for(it:it.incomingLinks.filter[resolved && !toDelete.contains(it.source)]) {
				it.revert				
				linksToResolve += it				
			}			
		}

		// 2 add new CUs
		// 2.1. add containment for all CUs
		for(it:newCompilationUnitModels) {
			debug["  #add: " + it]
			
			mergeContents(model, it.getOriginalCompilationUnitModel.javaModel, it.originalReverseTargets, true)
			it.copyCompilationUnit = copier.get(it.getOriginalCompilationUnitModel.compilationUnit) as CompilationUnit
			currentCompilationUnits.put(it.getCopyCompilationUnit, it)
		}
		// 2.2. add all references within new CUs
		copier.copyReferences
		// 2.3. collect all links added by the new CUs
		debug["  #add links"]
		for(it:newCompilationUnitModels) {
			outgoingLinks += originalCompilationUnitModel.unresolvedLinks.filter[copier.get(it.source) != null].map[originalUnresolvedLink| 
				val sourceCopy = copier.get(originalUnresolvedLink.source) as ASTNode
				condition[sourceCopy.mark != "Replaced"]
				
				val originalFeature = originalUnresolvedLink.source.eClass.getEStructuralFeature(originalUnresolvedLink.featureID)
				val feature = copier.getTarget(originalFeature) as EReference
				try {
					val link = new SSLink(sourceCopy, feature, originalUnresolvedLink.featureIndex, originalUnresolvedLink.fullId, 
							metaModel.javaFactory.create(copier.getTarget(originalUnresolvedLink.target.eClass)) as NamedElement)
					debug["    #new link: " + link]
					return link				
				} catch (Exception e) {
					// Bug in modisco causes targets with incompatible type. 
					SrcRepoActivator.INSTANCE.warning("Have to ignore a link because of unexpected exception.")
					return null
				}
			].filter[it != null]
			linksToResolve += outgoingLinks
		}
		copier.clear
			
		// 3. resolve all new and re-resolve all priorly reverted references
		debug["  #resolving links: "]
		for(it:linksToResolve.filter[!resolved]) {
			debug["    #" + it]
			val resolvedTarget = targets.get(it.id)
			if (resolvedTarget != null) {
				it.resolve(resolvedTarget)
				if (!resolvedTarget.proxy) {					
					val originalCompilationUnit = resolvedTarget.originalCompilationUnit
					if (originalCompilationUnit != null) {
						val cum = currentCompilationUnits.get(originalCompilationUnit)
						condition[cum != null]
						cum.incomingLinks += it						
					} // not all elements (e.g. packages) have a reference to a compilation unit
					  // TODO what about merged elmenets?
				}
			} else {
				condition("This should not happen.")[false] // zest
			}
		}
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
		debug["# clear ###################################"]
		EcoreUtil.delete(model, true)
		
		currentCompilationUnits.clear
		targets.clear
		newCompilationUnitModels.clear
		oldCompilationUnitModels.clear
		copier.clear
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
			condition("All NamedElements with name need to be targets.")[originalChild.name == null]
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
			condition[result == null || result.eClass.name == originalChild.eClass.name]
			return result
		}		
	}
	
	/**
	 * Merges the contents of the given objects. NamedElements are merged based on their IDs. 
	 */
	private def <T extends EObject> void mergeContents(T copy, T original,  Map<NamedElement, String> originalTargets, boolean addContents) {
		condition[copy.eClass.name == original.eClass.name]		
		original.forEachChild[originalFeature,originalChild|	
			val copyFeature = copier.getTarget(originalFeature)
			if (originalChild.isTarget(originalTargets)) {
				val originalNamedElementChild = originalChild as NamedElement
				val existingCopyChild = getCopyChild(copy, original, originalNamedElementChild, originalTargets)			
				val copyChild = if (existingCopyChild != null) {
					// original child has a corresponding other child					
					// merge existing child copy with new original child
					if (!originalNamedElementChild.externalTarget && existingCopyChild.externalTarget) {
						// merge-with or replace the existing target
						if (existingCopyChild.eClass != copier.getTarget(originalNamedElementChild.eClass)) {
							// the new child original has a different type, how can this happen, if the old type was removed?
							SrcRepoActivator.INSTANCE.warning('''Detected target with switching metatype, should not happen: «originalNamedElementChild.name» («originalNamedElementChild.eClass.name»->«existingCopyChild.eClass.name»).''')
						} else {
							// push attributes and other content from new internal target onto existing external target
							debug["    #merge(new attribute values) " + existingCopyChild.name]
							copier.merge(existingCopyChild, originalNamedElementChild)																		
							mergeContents(existingCopyChild, originalNamedElementChild, originalTargets, true)
						}
					} else {
						// use the existing target						
						debug["    #merge " + existingCopyChild.name]
						copier.put(originalNamedElementChild, existingCopyChild)
						mergeContents(existingCopyChild, originalNamedElementChild, originalTargets, false)
					}
					existingCopyChild
				} else {
					// copy of the child does not already exist
					debug["    #new " + originalNamedElementChild.name + "(" + originalNamedElementChild.eClass.name + ")"]		
					val newCopyChild = copier.shallowCopy(originalChild) as NamedElement
					condition[copyFeature.many] 
					(copy.eGet(copyFeature) as List<EObject>).add(newCopyChild)	
					condition[newCopyChild.eContainmentFeature == copyFeature]
					condition[newCopyChild.eContainer == copy]								
					mergeContents(newCopyChild, originalChild, originalTargets, true)
					newCopyChild											
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
						val existingCopyChild = copy.eGet(copyFeature) as EObject
						if (existingCopyChild != null) {
							debug[existingCopyChild.mark = "Replaced"; null]
							for(existingOriginalChild:copier.getLastOriginal(existingCopyChild)) {
								condition[existingOriginalChild != null]
								copier.put(existingOriginalChild, copyChild, true)							
							}
						}
						debug[copyChild.mark = if (existingCopyChild != null) "Replacement" else "New"; null]						
						copy.eSet(copyFeature, copyChild)
					}					
				}	
			}
		]
	} 
	
	private static class SSCopier extends EcoreUtil.Copier {		
		val Map<EClass, EClass> classCache = newHashMap
		val Map<EStructuralFeature,EStructuralFeature> featureCache = newHashMap
		
		val JavaPackage metaModel
		val Multimap<EObject,EObject> reverseMap = ArrayListMultimap.create
		
		new(JavaPackage metaModel) {
			this.metaModel = metaModel
		}
		
		private def <K,V> V access(Map<K,V> cache, K key, (K)=>V create) {
			val existingValue = cache.get(key)
			return if (existingValue == null) {
				val newValue = create.apply(key)
				cache.put(key, newValue)
				newValue
			} else {
				existingValue
			}
		}
	
		override getTarget(EClass eClass) {
			return classCache.access(eClass)[metaModel.getEClassifier(eClass.getName()) as EClass]			
		}
	
		override getTarget(EStructuralFeature eStructuralFeature) {
			return featureCache.access(eStructuralFeature)[
				val eClass = eStructuralFeature.getEContainingClass();
				getTarget(eClass).getEStructuralFeature(eStructuralFeature.getName());	
			]			
		}
		
		override put(EObject key, EObject value) {
			return put(key, value, false)
		}
		
		public def EObject put(EObject key, EObject value, boolean allowExistingValue) {
			val result = super.put(key, value)
			reverseMap.put(value, key)
			condition("Cannot copy an element twice.")[allowExistingValue || result == null]
			return result
		}
		
		override remove(Object key) {
			val oldValue = super.remove(key)
			if (oldValue != null) reverseMap.remove(oldValue)
			return oldValue			
		}
		
		override clear() {
			super.clear
			reverseMap.clear
		}
		
		public def <T extends EObject> List<T> getLastOriginal(T copy) {
			return reverseMap.get(copy) as List<T>
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
	
	private def <T extends EObject> void removeContents(T copy, T original, Map<NamedElement,String> originalTargets, Collection<EObject> toDelete) {
		condition[copy.eClass.name == original.eClass.name]				
		
		original.forEachChild[feature,child|
			if (child.isTarget(originalTargets)) {
				val namedElementChild = child as NamedElement
				val existingCopyChild = getCopyChild(copy, original, namedElementChild, originalTargets)			
				if (existingCopyChild != null) {
					// copy of the child exist
					removeContents(existingCopyChild, namedElementChild, originalTargets, toDelete)			
					val delete = if (existingCopyChild.externalTarget) {
						if (existingCopyChild.name == null) {
							// unnamed container, e.g. FieldDeclaration with fragments
							namedElementChild.eContents.empty
						} else {
							!existingCopyChild.used							
						}
					} else if (namedElementChild instanceof Package) {
						namedElementChild.eContents.empty
					} else if (!namedElementChild.externalTarget) {
						true
					}		
					
					if (delete) {
						debug["    #delete: " + existingCopyChild.name]
						deleteWithOutCrossReferences(existingCopyChild, toDelete)
						val id = originalTargets.get(namedElementChild)
						if (id != null) {
							targets.remove(id)						
						}
					}
				} 
			} 
		]
	}
	
	private def void deleteRecursive(EObject eObject, Collection<EObject> removed) {
		for(child:eObject.eContents) {
			deleteRecursive(child,removed)
		}
		for(feature:eObject.eClass.EAllReferences) {
			if (feature.changeable && (feature.EOpposite == null || !feature.EOpposite.containment)) {
				eObject.eUnset(feature)
			}
		}
		removed.add(eObject)
	}
	
	private def deleteWithOutCrossReferences(EObject eObject, Collection<EObject> removed) {
		deleteRecursive(eObject, removed)
		EcoreUtil.remove(eObject)
	}
	
	private def deleteCrossReferences(Collection<EObject> eObjects, EObject rootEObject) {		
		val usages = UsageCrossReferencer.findAll(eObjects, rootEObject)
		for (entry:usages.entrySet) {
			val deletedEObject = entry.getKey()
			val settings = entry.getValue()
			for (setting:settings) {
				if (setting.getEStructuralFeature().isChangeable()) {
					EcoreUtil.remove(setting, deletedEObject)
				}
			}
		}
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
}