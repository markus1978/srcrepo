package de.hub.srcrepo

import com.google.common.base.Preconditions
import de.hub.srcrepo.internal.SrcRepoBindingManager
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.UnresolvedItem
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement
import org.eclipse.gmt.modisco.java.emf.JavaFactory
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.common.util.URI

class Snapshot implements IModiscoSnapshotModel {
	
	val JavaPackage metaModel;
	val Model model;
	
	val Map<String, UnresolvedItem> unresolvedItems = newHashMap
	val extension Copier copier
	
	val List<SSCompilationUnitModel> currentCUs = newArrayList
	val List<SSCompilationUnitModel> inCUs = newArrayList
	val List<SSCompilationUnitModel> outCUs = newArrayList
	
	private static class SSCompilationUnitModel {
		val CompilationUnitModel dbCUModel;
		val List<SSPendingElement> out = newArrayList
		val List<SSPendingElement> in = newArrayList
		
		new (extension Snapshot snapshot, CompilationUnitModel dbCUModel) {
			this.dbCUModel = dbCUModel;
			out += dbCUModel.pendings.map[new SSPendingElement(snapshot, snapshot.copier.copied(clientNode), linkName)]
		}
	}
	
	private static class SSPendingElement extends PendingElement {
		val JavaFactory factory	
		var ASTNode target = null;
		var int index = -1;
		var reverted = false
		
		new(Snapshot snapshot, ASTNode clientNode, String linkName) {
			super(snapshot.metaModel.javaFactory)
			this.factory = snapshot.metaModel.javaFactory
			this.clientNode = clientNode
			this.linkName = linkName
		}
		
		override affectTarget(ASTNode target) {
			if (target != null) {
				replace(target)			
			} else {
				resolve(target)
			}
			reverted = false
		}
		
		def resolve(ASTNode target) {
			Preconditions.checkState(!reverted && target == null)
			this.target = target
			
			val owner = getClientNode()
			val linkName = getLinkName()
			val feature = owner.eClass().getEStructuralFeature(linkName) as EReference
			
			index = -1;
			if (feature.isMany()) {
				val lst = owner.eGet(feature) as EList<EObject>
				lst += target
				index = lst.size() - 1;
			} else {			
				owner.eSet(feature, target);
			}		
		}
		
		def replace(ASTNode newTarget) {
			Preconditions.checkState(reverted && target != null)
			this.target = newTarget
			
			val owner = getClientNode()
			val linkName = getLinkName()
			val feature = owner.eClass().getEStructuralFeature(linkName) as EReference
			
			if (index != -1) {
				(owner.eGet(feature) as EList<EObject>).set(index, newTarget)	
			} else {
				owner.eSet(feature, newTarget)
			}
		}
		
		def isResolved() {
			return target != null && !reverted
		}

		/**
		 * Replaces the resolved target with an empty place holder
		 */
		def revert() {
			Preconditions.checkState(target != null && !reverted)
			val placeHolder = factory.create(target.eClass) as ASTNode
			(placeHolder as InternalEObject).eSetProxyURI(URI.createURI(linkName)); // TODO remove, for debug only
			reverted = true;
			replace(placeHolder)
		}
	}
	
	new(JavaPackage metaModel) {
		this.metaModel = metaModel;
		model = metaModel.javaFactory.createModel
		this.copier = new Copier(metaModel)
	}
	
	override addCU(CompilationUnitModel model) {
		inCUs += new SSCompilationUnitModel(this, model)
	}
	
	override removeCU(CompilationUnitModel model) {
		outCUs += currentCUs.findFirst[it.dbCUModel == model] // TODO performance
	}
	
	override getSnapshot() {
		if (!outCUs.empty || !inCUs.empty) {
			computeSnapshot
		}
		return model
	}
	
	private def computeSnapshot() {
		// revert references to CUs that will be removed
		outCUs.forEach[in.forEach[revert]]
		
		// remove and add compilation unit contents
		outCUs.forEach[it.removeCompilationUnitContents]
		currentCUs -= outCUs
		inCUs.forEach[it.addCompilationUnitContents]
		currentCUs += inCUs
		
		// resolve all pending elements in the set of current CUs
		val pendingElements = currentCUs.map[in.filter[!resolved].map[it as PendingElement]].flatten.toList // TODO performance?
		val targets = newHashMap(currentCUs.map[dbCUModel.targets.map[id->target.copied]].flatten)
		
		val bindingManager = new SrcRepoBindingManager(model, metaModel, targets, pendingElements, unresolvedItems); 
		// TODO keep only one manager?
		//      perhaps make this class a binding manager decendant?
		bindingManager.resolveBindings(); // unresolved bindings are added to the model's unresolved elements
	}
	
	private def removeCompilationUnitContents(SSCompilationUnitModel model) {
		model.dbCUModel.compilationUnit.types.forEach[
			deleteViaDBObject(it)
		]		
	}
	
	private def void deleteViaDBObject(EObject obj) {
		obj.eContents.forEach[it.deleteViaDBObject]
		EcoreUtil.delete(copier.copied(obj))
		copier.remove(obj)
	}
	
	private def addCompilationUnitContents(SSCompilationUnitModel cuModel) {
		cuModel.dbCUModel.compilationUnit.types.forEach[
			copy(this.model, cuModel.dbCUModel.javaModel, it)
		]
	}
	
    private static class Copier extends EcoreUtil.Copier {
		val JavaPackage metaModel
		val Map<EObject, EObject> reverseMap = newHashMap
		new(JavaPackage metaModel) {
			this.metaModel = metaModel
		}
		
		def removeOriginal(EObject copy) {
			val original = reverseMap.remove(copy)
			remove(original)
		}
		
		override put(EObject key, EObject value) {
			super.put(key, value)
			reverseMap.put(value, key)
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
		def <T extends EObject,R extends EObject> T copy(R ctxCopy, R ctxOriginal, NamedElement original) {
			val parent = if (original.eContainer != ctxOriginal) {
				copy(ctxCopy, ctxOriginal, original.eContainer as NamedElement)
			} else {
				ctxCopy
			}
			
			val existing = parent.eContents.findFirst[eClass == original.eClass.target] as T
			if (existing == null) {
				val feature = getTarget(original.eContainingFeature)
				val copy = copy(original)
				Preconditions.checkState(feature.isMany)
				(parent.eGet(feature) as List<EObject>).add(copy)
				return copy as T	
			} else {
				return existing as T
			}	
		}

		def <T extends EObject> T copied(T key) {
			return super.get(key) as T;
		}		
	}
}