package de.hub.srcrepo.snapshot.internal

import com.google.common.base.Preconditions
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.UnresolvedItem
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement

class SSPendingElement extends PendingElement {
	val IModiscoSnapshotModel snapshot
	var ASTNode target = null;
	var int index = -1;
	var reverted = false

	new(IModiscoSnapshotModel snapshot, ASTNode clientNode, String linkName, String binding) {
		super(snapshot.metaModel.javaFactory)
		this.snapshot = snapshot
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
		println("#resolve: ->" + (target as NamedElement).name)
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

		if (!(target instanceof UnresolvedItem)) {
			val cum = SSCompilationUnitModel.get(target.originalCompilationUnit)
			cum.incomingReferences += this
		}
	}

	def replace(ASTNode newTarget) {
		println("#replace: " + (target as NamedElement)?.name + "<-" + (newTarget as NamedElement)?.name)
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
		val placeHolder = snapshot.metaModel.javaFactory.create(target.eClass) as ASTNode
		(placeHolder as InternalEObject).eSetProxyURI(URI.createURI(linkName)); // TODO remove, for debug only
		reverted = true;
		replace(placeHolder)
	}

	def delete() {
		Preconditions.checkState(!reverted && target != null)
		println("#delete: ->" + (target as NamedElement).name)
		val owner = getClientNode()
		val feature = owner.eClass().getEStructuralFeature(linkName) as EReference

		if (index != -1) {
			(owner.eGet(feature) as EList<EObject>).remove(index)
		} else {
			owner.eUnset(feature)
		}
		target = null
	}
}
