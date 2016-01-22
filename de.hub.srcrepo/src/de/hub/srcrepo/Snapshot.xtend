package de.hub.srcrepo

import com.google.common.base.Preconditions
import de.hub.srcrepo.internal.SrcRepoBindingManager
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.UnresolvedAnnotationDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedClassDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedEnumDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedInterfaceDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedItem
import org.eclipse.gmt.modisco.java.UnresolvedLabeledStatement
import org.eclipse.gmt.modisco.java.UnresolvedMethodDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedSingleVariableDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedType
import org.eclipse.gmt.modisco.java.UnresolvedTypeDeclaration
import org.eclipse.gmt.modisco.java.UnresolvedVariableDeclarationFragment
import org.eclipse.gmt.modisco.java.emf.JavaFactory
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement

class Snapshot implements IModiscoSnapshotModel {
	
	val JavaPackage metaModel;
	val Model model;
	
	val Map<String, UnresolvedItem> unresolvedItems = newHashMap
	
	val List<SSCompilationUnitModel> currentCUs = newArrayList
	val List<SSCompilationUnitModel> inCUs = newArrayList
	val List<SSCompilationUnitModel> outCUs = newArrayList
	
	
	
	new(JavaPackage metaModel) {
		this.metaModel = metaModel;
		model = metaModel.javaFactory.createModel
	}
	
	override addCU(CompilationUnitModel model) {
		inCUs += new SSCompilationUnitModel(metaModel, model)
	}
	
	override removeCU(CompilationUnitModel model) {
		outCUs += currentCUs.findFirst[it.source == model] // TODO performance
	}
	
	override getSnapshot() {
		if (!outCUs.empty || !inCUs.empty) {
			computeSnapshot
		}
		return model
	}
	
	private def computeSnapshot() {
		println("#compute")
		// revert references to CUs that will be removed
		outCUs.forEach[pendings.forEach[revert]] // TODO wrong direction (pendings are outgoing reference, not incoming)
		
		// remove and add compilation unit contents
		outCUs.forEach[it.removeFromModel(model)]
		currentCUs -= outCUs
		inCUs.forEach[it.addToModel(model)]
		currentCUs += inCUs
		
		// resolve all pending elements in the set of current CUs
		val pendingElements = currentCUs.map[(it.pendings as List<?>) as List<PendingElement>].flatten.toList // TODO performance, keep only unresolved
		val targets = newHashMap(); currentCUs.forEach[it.fillTargets(targets)] // TODO performance, do not compute fully do it incrementally by adding and removing targets
		
		val bindingManager = new SrcRepoBindingManager(model, metaModel, targets, pendingElements, unresolvedItems); 
		// TODO keep only one manager?
		//      perhaps make this class a binding manager decendant?
		bindingManager.resolveBindings(model); // unresolved bindings are added to the model's unresolvedItems
		
		// remove unresolvedItems that are no longer referenced
		for (UnresolvedItem unresolvedItem: model.unresolvedItems) {
			val isNotUsed = unresolvedItem.usagesInImports.empty && switch (unresolvedItem) {
				UnresolvedAnnotationDeclaration: unresolvedItem.usagesInTypeAccess.empty
				UnresolvedClassDeclaration: unresolvedItem.usagesInTypeAccess.empty
				UnresolvedInterfaceDeclaration: unresolvedItem.usagesInTypeAccess.empty
				UnresolvedEnumDeclaration: unresolvedItem.usagesInTypeAccess.empty
				UnresolvedLabeledStatement: unresolvedItem.usagesInBreakStatements.empty && unresolvedItem.usagesInContinueStatements.empty
				UnresolvedMethodDeclaration: unresolvedItem.usages.empty && unresolvedItem.usagesInDocComments.empty
				UnresolvedSingleVariableDeclaration: unresolvedItem.usageInVariableAccess.empty
				UnresolvedType: unresolvedItem.usagesInTypeAccess.empty
				UnresolvedTypeDeclaration: unresolvedItem.usagesInTypeAccess.empty
				UnresolvedVariableDeclarationFragment: unresolvedItem.usageInVariableAccess.empty	
				default: throw new RuntimeException("unreachable")		
			}
			if (isNotUsed) {
				EcoreUtil.remove(unresolvedItem)
			}
		}
		
		outCUs.clear
		inCUs.clear
	}  
}

class SSPendingElement extends PendingElement {
	val JavaFactory factory	
	var ASTNode target = null;
	var int index = -1;
	var reverted = false
	
	new(JavaPackage metaModel, ASTNode clientNode, String linkName, String binding) {
		super(metaModel.javaFactory)
		this.factory = metaModel.javaFactory
		this.clientNode = clientNode	
		this.linkName = linkName
		this.binding = new Binding(binding)
	}
	
	override affectTarget(ASTNode target) {
		Preconditions.checkArgument(target != null)
		if (this.target == null) {
			resolve(target)			
		} else {
			replace(target)
		}
		reverted = false
	}
	
	def resolve(ASTNode target) {
		Preconditions.checkState(!reverted && this.target == null)
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

public class SSCompilationUnitModel {
	val CompilationUnitModel dbCUModel;
	val JavaPackage metaModel
	val extension SSCopier copier
	val List<SSPendingElement> pendings = newArrayList 
	
	var boolean isAttached = false
	
	new (JavaPackage metaModel, CompilationUnitModel dbCUModel) {
		this.metaModel = metaModel
		this.dbCUModel = dbCUModel
		this.copier = new SSCopier(metaModel)	
	}
	
	def getPendings() {
		Preconditions.checkState(isAttached)
		return pendings
	}
	
	def void fillTargets(Map<String, NamedElement> allTargets) {
		Preconditions.checkState(isAttached)
		dbCUModel.targets.forEach[allTargets.put(id,target.copied)]
	}
	
	def void addToModel(Model model) {
		Preconditions.checkState(!isAttached, "Can only be attached to the snapshot model once.")
		println("<-" + dbCUModel.compilationUnit.originalFilePath)
		// compilation unit
		val cuCopy = copyt(dbCUModel.compilationUnit)
		model.compilationUnits.add(cuCopy)
		// types
		dbCUModel.compilationUnit.types.forEach[copy(model, dbCUModel.javaModel, it)]
		// orphan types
		dbCUModel.javaModel.orphanTypes.forEach[copy(model, dbCUModel.javaModel, it)]
		copyReferences
		
		pendings += dbCUModel.pendings.map[new SSPendingElement(metaModel, copied(clientNode), linkName, binding)]
		isAttached = true
	}
	
	def void removeFromModel(Model model) {
		println("->" + dbCUModel.compilationUnit.originalFilePath)		
		Preconditions.checkState(isAttached, "Can only removed compilation unit model that is attached to a model.")
		// orphant types
		dbCUModel.javaModel.orphanTypes.forEach[
			val copy = copied(it)
			if (copy.usagesInTypeAccess.empty && copy.usagesInImports.empty) {
				EcoreUtil.remove(copy)
				println(".")
			}
		]
		// types
		copied(dbCUModel.compilationUnit).types.forEach[
			var container = it.eContainer
			it.delete
			// also remove emptied packages
			while (container.eContents.empty && container instanceof NamedElement) {
				val content = container
				container = container.eContainer
				EcoreUtil.remove(content)
			}
		]
		// compilation unit
		copied(dbCUModel.compilationUnit).delete
		// reset
		copier.clear
		pendings.clear
		isAttached = false;	
	}
	
	def getSource() {
		return dbCUModel
	}
	
	private def void delete(EObject obj) {
		obj.eContents.forEach[it.delete]
		EcoreUtil.remove(obj)
	}
}

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
	def <T extends EObject,R extends EObject> T copy(R ctxCopy, R ctxOriginal, NamedElement original) {
		val parent = if (original.eContainer != ctxOriginal) {
			copy(ctxCopy, ctxOriginal, original.eContainer as NamedElement)
		} else {
			ctxCopy
		}
		
		val existing = parent.eContents.findFirst[eClass == original.eClass.target && (it as NamedElement).name == original.name] as T
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