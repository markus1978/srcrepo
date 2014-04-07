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
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *******************************************************************************/

package org.eclipse.modisco.java.discoverer.internal.io.java;

import java.util.Iterator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration;
import org.eclipse.gmt.modisco.java.ArrayType;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.NamespaceAccess;
import org.eclipse.gmt.modisco.java.PackageAccess;
import org.eclipse.gmt.modisco.java.ParameterizedType;
import org.eclipse.gmt.modisco.java.PrimitiveType;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.UnresolvedItemAccess;
import org.eclipse.gmt.modisco.java.WildCardType;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.JDTDelegateBindingFactory;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement;

/**
 * The class provides tools for the JDTVisitor.
 */
public final class JDTVisitorUtils {

	private static final String TRACEID_QN = "JavaDiscovererUtils/getQualifiedName"; //$NON-NLS-1$
	private static final boolean TRACE_QN = JavaActivator.getDefault().isDebugging()
			&& new Boolean(Platform.getDebugOption(JDTVisitorUtils.TRACEID_QN));

	/**
	 * No instantiation.
	 */
	private JDTVisitorUtils() {
		// Nothing
	}

	/**
	 * Resolves the JDT <code>name</code> and sorts the targeted
	 * <code>element</code> in the local or global <code>BindingManager</code>.
	 * 
	 * @param element
	 *            the declared Java entity.
	 * @param name
	 *            The JDT Name of the java entity.
	 * @param visitor
	 *            the JDTVisitor.
	 */
	public static void manageBindingDeclaration(final NamedElement element, final Name name,
			final JDTVisitor visitor) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForName(name);
		if (!id.isUnresolved()) { // in some circonstances JDT may not resolve a
									// declaration (too many compilation errors)
									// , we do not want to register a wrong
									// qualified target
			if (JDTDelegateBindingFactory.getInstance().isLocal(name)) {
				visitor.getLocalBindings().addTarget(id, element);
			} else {
				visitor.getGlobalBindings().addTarget(id, element);
			}
		}
	}

	/**
	 * Resolves and sorts the pending reference in the local or the global
	 * <code>BindingManager</code>.
	 * 
	 * @param element
	 *            the <code>PendingElement</code> associated with this reference
	 * @param name
	 *            The JDT Name of the referenced java entity.
	 * @param visitor
	 *            the JDTVisitor
	 * @return
	 */
	public static NamedElement manageBindingRef(final PendingElement element,
			final org.eclipse.jdt.core.dom.Name name, final JDTVisitor visitor) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForName(name);
		NamedElement target = visitor.getGlobalBindings().getTarget(id);
		if (target == null) {
			if (JDTDelegateBindingFactory.getInstance().isLocal(name)) {
				visitor.getLocalBindings().addPending(element, id);
			} else {
				visitor.getGlobalBindings().addPending(element, id);
			}
		}
		return target;
	}

	/**
	 * Resolves this JDT PrimitiveType <code>type</code> and creates the
	 * corresponding MoDisco {@link PrimitiveType} object (or returns a similar
	 * PrimitiveType if any).
	 * 
	 * @param type
	 *            the JDT PrimitiveType node.
	 * @param visitor
	 *            the JDTVisitor.
	 * @return the MoDisco PrimitiveType object.
	 */
	public static PrimitiveType manageBindingRef(final org.eclipse.jdt.core.dom.PrimitiveType type,
			final JDTVisitor visitor) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForPrimitiveType(type);
		PrimitiveType primitiveType = (PrimitiveType) visitor.getGlobalBindings().getTarget(id);
		if (primitiveType == null) {
			initializePrimitiveTypes(visitor.getFactory(), visitor.getJdtModel(),
					visitor.getGlobalBindings());
			primitiveType = (PrimitiveType) visitor.getGlobalBindings().getTarget(id);
		}
		if (primitiveType == null) { // should never happen
			primitiveType = visitor.getFactory().createPrimitiveType();
			primitiveType.setName(id.toString());
			visitor.getJdtModel().getOrphanTypes().add(primitiveType);
			visitor.getGlobalBindings().addTarget(id, primitiveType);
			MoDiscoLogger.logError("primitiveType == null", JavaActivator.getDefault()); //$NON-NLS-1$ 

		}
		return primitiveType;
	}

	/**
	 * Resolves this JDT ParameterizedType <code>type</code> and creates the
	 * corresponding MoDisco {@link ParameterizedType} object (or returns a
	 * similar ParameterizedType if any).
	 * 
	 * @param type
	 *            the JDT ParameterizedType node.
	 * @param visitor
	 *            the JDTVisitor.
	 * @return the MoDisco ParameterizedType object.
	 */
	public static ParameterizedType manageBindingRef(
			final org.eclipse.jdt.core.dom.ParameterizedType type, final JDTVisitor visitor) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForParameterizedType(type);
		ParameterizedType parameterizedType = (ParameterizedType) visitor.getGlobalBindings()
				.getTarget(id);
		if (parameterizedType == null) {
			parameterizedType = visitor.getFactory().createParameterizedType();
			parameterizedType.setName(id.toString());
			if (visitor.getBijectiveMap().get(type.getType()) != null) {
				parameterizedType.setType(completeTypeAccess(
						visitor.getBijectiveMap().get(type.getType()), visitor));
			}
			for (Iterator<?> i = type.typeArguments().iterator(); i.hasNext();) {
				Object typeArgument = i.next();
				ASTNode node = visitor.getBijectiveMap().get(typeArgument);
				if (node == null) {
					RuntimeException e = new RuntimeException(
							"typeArgument not found in visitor bijective map: " + type.getParent().getParent().toString()); //$NON-NLS-1$
					MoDiscoLogger.logWarning(e, JavaActivator.getDefault());
				}
				TypeAccess itElement = completeTypeAccess(node, visitor);
				if (itElement != null) {
					parameterizedType.getTypeArguments().add(itElement);
				}
			}
			visitor.getJdtModel().getOrphanTypes().add(parameterizedType);
			visitor.getGlobalBindings().addTarget(id, parameterizedType);
		}
		return parameterizedType;
	}

	/**
	 * Resolves this JDT WildCardType <code>type</code> and creates the
	 * corresponding MoDisco {@link WildCardType} object (or returns a similar
	 * WildCardType if any).
	 * 
	 * @param type
	 *            the JDT WildCardType node.
	 * @param visitor
	 *            the JDTVisitor.
	 * @return the MoDisco WildCardType object.
	 */
	public static WildCardType manageBindingRef(final org.eclipse.jdt.core.dom.WildcardType type,
			final JDTVisitor visitor) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForWildCardType(type);
		WildCardType wildCardType = (WildCardType) visitor.getGlobalBindings().getTarget(id);
		if (wildCardType == null) {
			wildCardType = visitor.getFactory().createWildCardType();

			if (visitor.getBijectiveMap().get(type.getBound()) != null) {
				wildCardType.setBound(completeTypeAccess(
						visitor.getBijectiveMap().get(type.getBound()), visitor));
			}
			wildCardType.setName(id.toString());
			wildCardType.setUpperBound(type.isUpperBound());
			visitor.getJdtModel().getOrphanTypes().add(wildCardType);
			visitor.getGlobalBindings().addTarget(id, wildCardType);
		}
		return wildCardType;
	}

	/**
	 * Resolves this JDT ArrayType <code>type</code> and creates the
	 * corresponding MoDisco {@link ArrayType} object (or returns a similar
	 * ArrayType if any).
	 * 
	 * @param type
	 *            the JDT ArrayType node.
	 * @param visitor
	 *            the JDTVisitor.
	 * @return the MoDisco ArrayType object.
	 */
	public static ArrayType manageBindingRef(final org.eclipse.jdt.core.dom.ArrayType type,
			final JDTVisitor visitor) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForArrayType(type);
		ArrayType arrayType = (ArrayType) visitor.getGlobalBindings().getTarget(id);
		if (arrayType == null) {
			arrayType = visitor.getFactory().createArrayType();
			arrayType.setDimensions(type.getDimensions());
			arrayType.setName(id.toString());
			if (visitor.getBijectiveMap().get(type.getElementType()) != null) {
				arrayType.setElementType(completeTypeAccess(
						visitor.getBijectiveMap().get(type.getElementType()), visitor));
			}

			visitor.getJdtModel().getOrphanTypes().add(arrayType);
			visitor.getGlobalBindings().addTarget(id, arrayType);
		}
		return arrayType;
	}

	/**
	 * Manages the reference to a constructor. If the targeted constructor has
	 * already been visited, the reference is resolved. Otherwise, the pending
	 * reference is added to the global <code>BindingManager</code>.
	 * 
	 * @param element
	 *            the <code>PendingElement</code> associated with this reference
	 * @param constructorCall
	 *            the JDT object representing the constructor call
	 * @param visitor
	 *            the JDTVisitor
	 */
	public static void manageBindingRef(final PendingElement element,
			final org.eclipse.jdt.core.dom.ClassInstanceCreation constructorCall,
			final JDTVisitor visitor) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForClassInstanceCreation(
				constructorCall);
		AbstractMethodDeclaration method = (AbstractMethodDeclaration) visitor.getGlobalBindings()
				.getTarget(id);
		if (method != null) {
			element.affectTarget(method);
		} else {
			visitor.getGlobalBindings().addPending(element, id);
		}
	}

	/**
	 * Manages the reference to a constructor. If the targeted constructor has
	 * already been visited, the reference is resolved. Otherwise, the pending
	 * reference is added to the global <code>BindingManager</code>.
	 * 
	 * @param element
	 *            the <code>PendingElement</code> associated with this reference
	 * @param constructorCall
	 *            the JDT object representing the constructor call
	 * @param visitor
	 *            the JDTVisitor
	 */
	public static void manageBindingRef(final PendingElement element,
			final org.eclipse.jdt.core.dom.ConstructorInvocation constructorCall,
			final JDTVisitor visitor) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForConstructorInvocation(
				constructorCall);
		AbstractMethodDeclaration method = (AbstractMethodDeclaration) visitor.getGlobalBindings()
				.getTarget(id);
		if (method != null) {
			element.affectTarget(method);
		} else {
			visitor.getGlobalBindings().addPending(element, id);
		}
	}

	/**
	 * Manages the reference to a super constructor. If the targeted super
	 * constructor has already been visited, the reference is resolved.
	 * Otherwise, the pending reference is added to the global
	 * <code>BindingManager</code>.
	 * 
	 * @param element
	 *            the <code>PendingElement</code> associated with this reference
	 * @param constructorCall
	 *            the JDT object representing the super constructor call
	 * @param visitor
	 *            the JDTVisitor
	 */
	public static void manageBindingRef(final PendingElement element,
			final org.eclipse.jdt.core.dom.SuperConstructorInvocation constructorCall,
			final JDTVisitor visitor) {
		Binding id = JDTDelegateBindingFactory.getInstance()
				.getBindingForSuperConstructorInvocation(constructorCall);
		AbstractMethodDeclaration method = (AbstractMethodDeclaration) visitor.getGlobalBindings()
				.getTarget(id);
		if (method != null) {
			element.affectTarget(method);
		} else {
			visitor.getGlobalBindings().addPending(element, id);
		}
	}

	/**
	 * First creation of the Java primitives types which are placed as targets
	 * in the global <code>BindingManager</code>.
	 * 
	 * @param factory
	 *            the EMF <code>factory</code>
	 * @param model
	 *            the EMF <code>Model</code> object
	 * @param globalBindings
	 *            the global <code>BindingManager</code>
	 */
	public static void initializePrimitiveTypes(final JavaFactory factory, final Model model,
			final BindingManager globalBindings) {
		org.eclipse.gmt.modisco.java.PrimitiveType primitiveType;
		if (globalBindings.getTarget(org.eclipse.jdt.core.dom.PrimitiveType.INT.toString()) == null) {
			primitiveType = factory.createPrimitiveTypeInt();
			primitiveType.setName(org.eclipse.jdt.core.dom.PrimitiveType.INT.toString());
			model.getOrphanTypes().add(primitiveType);
			globalBindings.addTarget(org.eclipse.jdt.core.dom.PrimitiveType.INT.toString(),
					primitiveType);
		}

		if (globalBindings.getTarget(org.eclipse.jdt.core.dom.PrimitiveType.LONG.toString()) == null) {
			primitiveType = factory.createPrimitiveTypeLong();
			primitiveType.setName(org.eclipse.jdt.core.dom.PrimitiveType.LONG.toString());
			model.getOrphanTypes().add(primitiveType);
			globalBindings.addTarget(org.eclipse.jdt.core.dom.PrimitiveType.LONG.toString(),
					primitiveType);
		}

		if (globalBindings.getTarget(org.eclipse.jdt.core.dom.PrimitiveType.FLOAT.toString()) == null) {
			primitiveType = factory.createPrimitiveTypeFloat();
			primitiveType.setName(org.eclipse.jdt.core.dom.PrimitiveType.FLOAT.toString());
			model.getOrphanTypes().add(primitiveType);
			globalBindings.addTarget(org.eclipse.jdt.core.dom.PrimitiveType.FLOAT.toString(),
					primitiveType);
		}

		if (globalBindings.getTarget(org.eclipse.jdt.core.dom.PrimitiveType.DOUBLE.toString()) == null) {
			primitiveType = factory.createPrimitiveTypeDouble();
			primitiveType.setName(org.eclipse.jdt.core.dom.PrimitiveType.DOUBLE.toString());
			model.getOrphanTypes().add(primitiveType);
			globalBindings.addTarget(org.eclipse.jdt.core.dom.PrimitiveType.DOUBLE.toString(),
					primitiveType);
		}

		if (globalBindings.getTarget(org.eclipse.jdt.core.dom.PrimitiveType.BOOLEAN.toString()) == null) {
			primitiveType = factory.createPrimitiveTypeBoolean();
			primitiveType.setName(org.eclipse.jdt.core.dom.PrimitiveType.BOOLEAN.toString());
			model.getOrphanTypes().add(primitiveType);
			globalBindings.addTarget(org.eclipse.jdt.core.dom.PrimitiveType.BOOLEAN.toString(),
					primitiveType);
		}

		if (globalBindings.getTarget(org.eclipse.jdt.core.dom.PrimitiveType.VOID.toString()) == null) {
			primitiveType = factory.createPrimitiveTypeVoid();
			primitiveType.setName(org.eclipse.jdt.core.dom.PrimitiveType.VOID.toString());
			model.getOrphanTypes().add(primitiveType);
			globalBindings.addTarget(org.eclipse.jdt.core.dom.PrimitiveType.VOID.toString(),
					primitiveType);
		}

		if (globalBindings.getTarget(org.eclipse.jdt.core.dom.PrimitiveType.CHAR.toString()) == null) {
			primitiveType = factory.createPrimitiveTypeChar();
			primitiveType.setName(org.eclipse.jdt.core.dom.PrimitiveType.CHAR.toString());
			model.getOrphanTypes().add(primitiveType);
			globalBindings.addTarget(org.eclipse.jdt.core.dom.PrimitiveType.CHAR.toString(),
					primitiveType);
		}

		if (globalBindings.getTarget(org.eclipse.jdt.core.dom.PrimitiveType.SHORT.toString()) == null) {
			primitiveType = factory.createPrimitiveTypeShort();
			primitiveType.setName(org.eclipse.jdt.core.dom.PrimitiveType.SHORT.toString());
			model.getOrphanTypes().add(primitiveType);
			globalBindings.addTarget(org.eclipse.jdt.core.dom.PrimitiveType.SHORT.toString(),
					primitiveType);
		}

		if (globalBindings.getTarget(org.eclipse.jdt.core.dom.PrimitiveType.BYTE.toString()) == null) {
			primitiveType = factory.createPrimitiveTypeByte();
			primitiveType.setName(org.eclipse.jdt.core.dom.PrimitiveType.BYTE.toString());
			model.getOrphanTypes().add(primitiveType);
			globalBindings.addTarget(org.eclipse.jdt.core.dom.PrimitiveType.BYTE.toString(),
					primitiveType);
		}
	}

	/**
	 * 
	 * @param oldClientNode
	 * @param newClientNode
	 * @param oldClientLinkName
	 * @param newClientLinkName
	 * @param visitor
	 */
	public static void substitutePendingClientNode(final ASTNode oldClientNode,
			final ASTNode newClientNode, final String oldClientLinkName,
			final String newClientLinkName, final JDTVisitor visitor) {
		PendingElement pe = visitor.getGlobalBindings()
				.getPending(oldClientNode, oldClientLinkName);
		if (pe != null) {
			pe.setClientNode(newClientNode);
			pe.setLinkName(newClientLinkName);
			visitor.getBijectiveMap().put(visitor.getBijectiveMap().getKey(oldClientNode),
					newClientNode);
			EcoreUtil.remove(oldClientNode);
		}
	}

	/**
	 * Complete <code>PendingElement</code> instances with client node and
	 * client link name.
	 * 
	 * @param clientNode
	 * @param modiscoNode
	 * @param clientLinkName
	 */
	public static boolean completeBinding(final ASTNode clientNode, final ASTNode modiscoNode,
			final String clientLinkName) {
		if (modiscoNode != null) {
			if (modiscoNode instanceof PendingElement) {
				PendingElement pending = (PendingElement) modiscoNode;
				pending.setLinkName(clientLinkName);
				pending.setClientNode(clientNode);
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * For unresolved items, <code>TypeAccess</code> may not have been yet
	 * created (no kind of binding)
	 * 
	 * @param node
	 *            a type access (<code>PendingElement</code> or resolved
	 *            <code>TypeAccess</code>)
	 * @param visitor
	 *            the JDTVisitor
	 */
	public static TypeAccess completeTypeAccess(final ASTNode node, final JDTVisitor visitor) {
		if (node instanceof TypeAccess) {
			return (TypeAccess) node;
		} else if (node instanceof PackageAccess) {
			// in some case, it could be a PackageAccess (see bug 328143)
			TypeAccess typAcc = visitor.getFactory().createTypeAccess();
			typAcc.setQualifier((PackageAccess) node);
			JDTVisitorUtils.substitutePendingClientNode(node, typAcc, "element", "type", visitor); //$NON-NLS-1$ //$NON-NLS-2$
			return typAcc;
		} else if (node instanceof UnresolvedItemAccess) {
			// now we know that the unresolved item is a type access -> we
			// substitute UIA with TypeAccess
			TypeAccess typAcc = visitor.getFactory().createTypeAccess();
			if (((UnresolvedItemAccess) node).getQualifier() != null) {
				typAcc.setQualifier((NamespaceAccess) ((UnresolvedItemAccess) node).getQualifier());
			}
			JDTVisitorUtils.substitutePendingClientNode(node, typAcc, "element", "type", visitor); //$NON-NLS-1$ //$NON-NLS-2$
			return typAcc;
		} else {
			TypeAccess typAcc = visitor.getFactory().createTypeAccess();
			((PendingElement) node).setClientNode(typAcc);
			((PendingElement) node).setLinkName("type"); //$NON-NLS-1$
			return typAcc;
		}
	}

	/**
	 * For unresolved items, <code>SingleVariableAccess</code> may not have been
	 * yet created (no kind of binding)
	 * 
	 * @param node
	 *            a variable access (<code>PendingElement</code> or resolved
	 *            <code>SingleVariableAccess</code>)
	 * @param visitor
	 *            the JDTVisitor
	 */
	public static SingleVariableAccess completeVariableAccess(final ASTNode node,
			final JDTVisitor visitor) {
		if (node instanceof SingleVariableAccess) {
			return (SingleVariableAccess) node;
		} else if (node instanceof UnresolvedItemAccess) {
			// now we know that the unresolved item is a variable access -> we
			// substitute UIA with VariableAccess
			SingleVariableAccess varAcc = visitor.getFactory().createSingleVariableAccess();
			if (((UnresolvedItemAccess) node).getQualifier() != null) {
				varAcc.setQualifier((Expression) ((UnresolvedItemAccess) node).getQualifier());
			}
			JDTVisitorUtils.substitutePendingClientNode(node, varAcc,
					"element", "variable", visitor); //$NON-NLS-1$ //$NON-NLS-2$
			return varAcc;
		} else {
			SingleVariableAccess variableAccess = visitor.getFactory().createSingleVariableAccess();

			((PendingElement) node).setClientNode(variableAccess);
			((PendingElement) node).setLinkName("variable"); //$NON-NLS-1$
			return variableAccess;
		}
	}

	/**
	 * For unresolved items as qualifiers, <code>UnresolvedItemAccess</code> may
	 * not have been yet created
	 * 
	 * @param node
	 *            an expression (<code>PendingElement</code> or resolved
	 *            <code>Expression</code>)
	 * @param visitor
	 *            the JDTVisitor
	 */
	public static Expression completeExpression(final ASTNode node, final JDTVisitor visitor) {
		if (node instanceof Expression) {
			return (Expression) node;
		}
		UnresolvedItemAccess unrAcc = visitor.getFactory().createUnresolvedItemAccess();
		((PendingElement) node).setClientNode(unrAcc);
		((PendingElement) node).setLinkName("element"); //$NON-NLS-1$
		return unrAcc;
	}

	/**
	 * For unresolved items as qualifiers, <code>UnresolvedItemAccess</code> may
	 * not have been yet created
	 * 
	 * @param node
	 *            an expression or PackageAccess (<code>PendingElement</code> or
	 *            resolved <code>Expression</code> or resolved
	 *            <code>PackageAccess</code>)
	 * @param visitor
	 *            the JDTVisitor
	 */
	public static ASTNode completeExpressionOrPackageAccess(final ASTNode node,
			final JDTVisitor visitor) {
		if (node instanceof Expression || node instanceof PackageAccess) {
			return node;
		}
		UnresolvedItemAccess unrAcc = visitor.getFactory().createUnresolvedItemAccess();
		((PendingElement) node).setClientNode(unrAcc);
		((PendingElement) node).setLinkName("element"); //$NON-NLS-1$
		return unrAcc;
	}

	public static String getQualifiedName(final org.eclipse.jdt.core.dom.ASTNode node) {
		String resultQualifiedName = ""; //$NON-NLS-1$

		if (node instanceof PackageDeclaration) {
			Name name = ((PackageDeclaration) node).getName();
			resultQualifiedName = JDTDelegateBindingFactory.getInstance().getBindingForName(name)
					.toString();
		} else if (node instanceof MethodDeclaration) {
			Name name = ((MethodDeclaration) node).getName();
			resultQualifiedName = JDTDelegateBindingFactory.getInstance().getBindingForName(name)
					.toString();
		} else if (node instanceof AbstractTypeDeclaration) {
			Name name = ((AbstractTypeDeclaration) node).getName();
			resultQualifiedName = JDTDelegateBindingFactory.getInstance().getBindingForName(name)
					.toString();
		} else if (node instanceof AbstractTypeDeclaration) {
			Name name = ((AbstractTypeDeclaration) node).getName();
			resultQualifiedName = JDTDelegateBindingFactory.getInstance().getBindingForName(name)
					.toString();
		} else if (node instanceof SingleVariableDeclaration) {
			SingleVariableDeclaration svd = (SingleVariableDeclaration) node;
			if (svd.getParent() instanceof MethodDeclaration) {
				MethodDeclaration md = (MethodDeclaration) svd.getParent();
				resultQualifiedName = getQualifiedName(md) + "." + svd.getName().getIdentifier(); //$NON-NLS-1$
			}
		} else if (node instanceof VariableDeclarationFragment) {
			VariableDeclarationFragment vdf = (VariableDeclarationFragment) node;
			if (vdf.getParent() instanceof FieldDeclaration) {
				resultQualifiedName = JDTDelegateBindingFactory.getInstance()
						.getBindingForName(vdf.getName()).toString();
			}
		} else if (node instanceof EnumConstantDeclaration) {
			EnumConstantDeclaration enumDecl = (EnumConstantDeclaration) node;
			resultQualifiedName = JDTDelegateBindingFactory.getInstance()
					.getBindingForName(enumDecl.getName()).toString();
		} else if (node instanceof AnnotationTypeMemberDeclaration) {
			AnnotationTypeMemberDeclaration annoDecl = (AnnotationTypeMemberDeclaration) node;
			resultQualifiedName = JDTDelegateBindingFactory.getInstance()
					.getBindingForName(annoDecl.getName()).toString();
		}

		if (JDTVisitorUtils.TRACE_QN) {
			System.out.println(JDTVisitorUtils.TRACEID_QN + " : " + resultQualifiedName); //$NON-NLS-1$
		}
		return resultQualifiedName;
	}

}
