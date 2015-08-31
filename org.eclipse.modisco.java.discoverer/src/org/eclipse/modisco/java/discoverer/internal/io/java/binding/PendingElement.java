/*******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sebastien Minguet (Mia-Software) - initial API and implementation
 *    Frederic Madiot (Mia-Software) - initial API and implementation
 *    Fabien Giquel (Mia-Software) - initial API and implementation
 *    Gabriel Barbier (Mia-Software) - initial API and implementation
 *    Erwan Breton (Sodifrance) - initial API and implementation
 *    Romain Dervaux (Mia-Software) - initial API and implementation
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer.internal.io.java.binding;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractMethodInvocation;
import org.eclipse.gmt.modisco.java.AnnotationMemberValuePair;
import org.eclipse.gmt.modisco.java.BreakStatement;
import org.eclipse.gmt.modisco.java.ClassFile;
import org.eclipse.gmt.modisco.java.Comment;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.ContinueStatement;
import org.eclipse.gmt.modisco.java.MethodRef;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.UnresolvedItem;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;

/**
 * This class represents a reference (containment or not) between two
 * {@link ASTNode}s in the Java model.
 * <p>
 * It knows a client node (the origin of the reference), a link name (the name
 * of the {@link EStructuralFeature feature}), and a {@code Binding} (which
 * represents the targeted Java entity).
 * </p>
 * <p>
 * It uses the EMF reflexive API to complete references.
 * </p>
 * <p>
 * This class implements the meta-class {@code ASTNode} but is <b>not</b>
 * modeled in the Java meta-model. It is a trick to ease the usage of this class
 * in the {@code JDTVisitor}.
 * </p>
 */
public class PendingElement implements ASTNode {

	/**
	 * The {@link ASTNode} which has a reference on the Java entity represented
	 * by the {@code binding}.
	 */
	private ASTNode clientNode;

	/**
	 * the name of the {@link EStructuralFeature}.
	 */
	private String linkName;

	/**
	 * The {@link Binding} representing the targeted {@code NamedElement}.
	 */
	private Binding binding;

	/**
	 * The EMF factory.
	 */
	private final JavaFactory factory;

	/**
	 * The constructor.
	 * 
	 * @param factory
	 *            the EMF factory
	 */
	public PendingElement(final JavaFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns the client node of this reference.
	 * 
	 * @return the client node
	 */
	public ASTNode getClientNode() {
		return this.clientNode;
	}

	/**
	 * Set the client node of this reference.
	 * 
	 * @param clientNode
	 *            the client node
	 */
	public void setClientNode(final ASTNode clientNode) {
		this.clientNode = clientNode;
	}

	/**
	 * Returns the {@link EStructuralFeature feature}'s name of this reference.
	 * 
	 * @see org.eclipse.emf.ecore.EClass#getEStructuralFeature(String)
	 * @return the feature's name.
	 */
	public String getLinkName() {
		return this.linkName;
	}

	/**
	 * Set the {@link EStructuralFeature feature}'s name of this reference.
	 * 
	 * @param linkName
	 *            the string name of the feature
	 */
	public void setLinkName(final String linkName) {
		this.linkName = linkName;
	}

	/**
	 * Returns the {@code Binding} representing the targeted Java entity.
	 * 
	 * @return the binding
	 */
	public Binding getBinding() {
		return this.binding;
	}

	/**
	 * Set the {@code Binding} representing the targeted Java entity.
	 * 
	 * @param binding
	 *            the binding
	 */
	public void setBinding(final Binding binding) {
		this.binding = binding;
	}

	/**
	 * Affect the given {@code target} to the client node. This method use the
	 * EMF reflexive API.
	 * 
	 * @param target
	 *            the target.
	 */
	public void affectTarget(final ASTNode target) {

		if (this.clientNode != null) {
			EStructuralFeature feature = this.clientNode.eClass().getEStructuralFeature(
					this.linkName);

			affectTarget0(feature, target);
		}
	}

	private void affectTarget0(final EStructuralFeature feature, final ASTNode target) {
		// multi-valued attribute
		if (feature.isMany()) {

			@SuppressWarnings("unchecked") // type erasure with EMF reflective API
			EList<EObject> lst = (EList<EObject>) this.clientNode.eGet(feature);
			try {
				lst.add(target);
			} catch (Exception e) {
				MoDiscoLogger.logError(e, JavaActivator.getDefault());

			}
		} else {
			// simple attribute
			try {
				this.clientNode.eSet(feature, target);
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR, JavaActivator.PLUGIN_ID, e.getMessage(),
						e);
				JavaActivator.getDefault().getLog().log(status);
			}
		}
	}

	/**
	 * Returns an {@link UnresolvedItem} compatible with the feature's type of
	 * the client node.
	 * <p>
	 * For example, for a {@code MethodInvocation} which references an
	 * unresolved {@code MethodDeclaration}, this method will return an
	 * {@code UnresolvedMethodDeclaration}.
	 * <p>
	 * It will fall back to the generic {@code UnresolvedItem} meta-class for
	 * unkown client nodes.
	 * 
	 * @return an UnresolvedItem compatible with the feature's type of the
	 *         clientNode.
	 */
	public NamedElement affectUnresolvedTarget() {
		NamedElement target = null;
		// BreakStatement / ContinueStatement
		if ((this.clientNode instanceof BreakStatement || this.clientNode instanceof ContinueStatement)
				&& this.linkName.equals("label")) { //$NON-NLS-1$
			target = this.factory.createUnresolvedLabeledStatement();
		} else if ((this.clientNode instanceof MethodRef || this.clientNode instanceof AbstractMethodInvocation)
				&& this.linkName.equals("method")) { //$NON-NLS-1$
			target = this.factory.createUnresolvedMethodDeclaration();
		} else if ((this.clientNode instanceof TypeAccess) && this.linkName.equals("type")) { //$NON-NLS-1$
			target = this.factory.createUnresolvedTypeDeclaration();
		} else if ((this.clientNode instanceof SingleVariableAccess)
				&& this.linkName.equals("variable")) { //$NON-NLS-1$
			target = this.factory.createUnresolvedVariableDeclarationFragment();
		} else if ((this.clientNode instanceof AnnotationMemberValuePair)
				&& this.linkName.equals("member")) { //$NON-NLS-1$
			target = this.factory.createUnresolvedAnnotationTypeMemberDeclaration();
		}

		if (target == null) {
			target = this.factory.createUnresolvedItem();
		}
		return target;
	}

	// useless methods
	public EList<Comment> getComments() {
		return null;
	}

	public CompilationUnit getOriginalCompilationUnit() {
		return null;
	}

	public void setOriginalCompilationUnit(final CompilationUnit value) {
		return;
	}

	public TreeIterator<EObject> eAllContents() {
		return null;
	}

	public EClass eClass() {
		return null;
	}

	public EObject eContainer() {
		return null;
	}

	public EStructuralFeature eContainingFeature() {
		return null;
	}

	public EReference eContainmentFeature() {
		return null;
	}

	public EList<EObject> eContents() {
		return null;
	}

	public EList<EObject> eCrossReferences() {
		return null;
	}

	public Object eGet(final EStructuralFeature feature) {
		return null;
	}

	public Object eGet(final EStructuralFeature feature, final boolean resolve) {
		return null;
	}

	public boolean eIsProxy() {
		return false;
	}

	public boolean eIsSet(final EStructuralFeature feature) {
		return false;
	}

	public Resource eResource() {
		return null;
	}

	public void eSet(final EStructuralFeature feature, final Object newValue) {
		return;
	}

	public void eUnset(final EStructuralFeature feature) {
		return;
	}

	public EList<Adapter> eAdapters() {
		return null;
	}

	public boolean eDeliver() {
		return false;
	}

	public void eNotify(final Notification notification) {
		return;
	}

	public void eSetDeliver(final boolean deliver) {
		return;
	}

	public ClassFile getOriginalClassFile() {
		return null;
	}

	public void setOriginalClassFile(final ClassFile value) {
		return;
	}

	public Object eInvoke(final EOperation operation, final EList<?> arguments) {
		return null;
	}
}