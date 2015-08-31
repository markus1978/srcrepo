/*******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Romain Dervaux (Mia-Software) - initial API and implementation
 *******************************************************************************/

package org.eclipse.modisco.java.discoverer.internal.io.library;

import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement;
import org.eclipse.modisco.java.discoverer.internal.io.library.binding.JavaModelDelegateBindingFactory;

/**
 * The class provides tools for the ClassFileParser.
 */
public final class ClassFileParserUtils {

	private static final int ESCAPED_CHAR_LENGTH = 3;

	/**
	 * No instantiation.
	 */
	private ClassFileParserUtils() {
		// nothing
	}

	/**
	 * Resolves the Java model element and sorts the targeted
	 * <code>element</code> in the global <code>BindingManager</code>.
	 * 
	 * @param element
	 *            the declared Java entity.
	 * @param javaElement
	 *            The Java model element corresponding to the declared Java
	 *            entity.
	 * @param visitor
	 *            the {@code ClassFileParser}.
	 */
	public static void manageBindingDeclaration(final NamedElement element,
			final IJavaElement javaElement, final ClassFileParser visitor) {
		Binding id = JavaModelDelegateBindingFactory.getInstance().getBindingForElement(
				javaElement, visitor);
		visitor.getGlobalBindings().addTarget(id, element);
	}

	/**
	 * Manage a reference on a type. If the targeted type is already known, the
	 * reference is resolved and the supplied {@code TypeAccess} is completed.
	 * If unknown, a pending reference is created and sorted in the global
	 * {@code BindingManager}.
	 * 
	 * @param element
	 *            the {@code TypeAccess} associated with this reference
	 * @param name
	 *            The qualified dot based name of the referenced java entity.
	 * @param visitor
	 *            the {@code ClassFileParser}
	 */
	public static void manageBindingRef(final TypeAccess element, final String name,
			final ClassFileParser visitor) {
		Binding id = JavaModelDelegateBindingFactory.getInstance().getBindingForName(name, visitor,
				false);
		NamedElement target = visitor.getGlobalBindings().getTarget(id);
		if (target != null) {
			element.setType((Type) target);
		} else {
			PendingElement pending = new PendingElement(visitor.getFactory());
			pending.setClientNode(element);
			pending.setLinkName("type"); //$NON-NLS-1$
			visitor.getGlobalBindings().addPending(pending, id);
		}
	}

	/**
	 * First creation of the Java primitives types which are placed as targets
	 * in the global {@code BindingManager}.
	 * 
	 * @param factory
	 *            the EMF {@code factory}
	 * @param model
	 *            the EMF {@code Model} object
	 * @param globalBindings
	 *            the global {@code BindingManager}
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
	 * Returns the string as it would be in the source code.
	 * 
	 * @see org.eclipse.jdt.core.dom.StringLiteral#getEscapedValue
	 */
	public static String escapeString(final String value) {
		int len = value.length();
		StringBuilder b = new StringBuilder(len + 2);

		b.append("\""); //$NON-NLS-1$
		for (int i = 0; i < len; i++) {
			char c = value.charAt(i);
			switch (c) {
			case '\b':
				b.append("\\b"); //$NON-NLS-1$
				break;
			case '\t':
				b.append("\\t"); //$NON-NLS-1$
				break;
			case '\n':
				b.append("\\n"); //$NON-NLS-1$
				break;
			case '\f':
				b.append("\\f"); //$NON-NLS-1$
				break;
			case '\r':
				b.append("\\r"); //$NON-NLS-1$
				break;
			case '\"':
				b.append("\\\""); //$NON-NLS-1$
				break;
			case '\'':
				b.append("\\\'"); //$NON-NLS-1$
				break;
			case '\\':
				b.append("\\\\"); //$NON-NLS-1$
				break;
			case '\0':
				b.append("\\0"); //$NON-NLS-1$
				break;
			case '\1':
				b.append("\\1"); //$NON-NLS-1$
				break;
			case '\2':
				b.append("\\2"); //$NON-NLS-1$
				break;
			case '\3':
				b.append("\\3"); //$NON-NLS-1$
				break;
			case '\4':
				b.append("\\4"); //$NON-NLS-1$
				break;
			case '\5':
				b.append("\\5"); //$NON-NLS-1$
				break;
			case '\6':
				b.append("\\6"); //$NON-NLS-1$
				break;
			case '\7':
				b.append("\\7"); //$NON-NLS-1$
				break;
			default:
				b.append(c);
			}
		}
		b.append("\""); //$NON-NLS-1$
		return b.toString();
	}

	/**
	 * Returns the character as it would be in the source code.
	 * 
	 * @see org.eclipse.jdt.core.dom.CharacterLiteral#getEscapedValue
	 */
	public static String escapeCharacter(final char value) {
		StringBuilder b = new StringBuilder(ClassFileParserUtils.ESCAPED_CHAR_LENGTH);

		b.append('\'');
		switch (value) {
		case '\b':
			b.append("\\b"); //$NON-NLS-1$
			break;
		case '\t':
			b.append("\\t"); //$NON-NLS-1$
			break;
		case '\n':
			b.append("\\n"); //$NON-NLS-1$
			break;
		case '\f':
			b.append("\\f"); //$NON-NLS-1$
			break;
		case '\r':
			b.append("\\r"); //$NON-NLS-1$
			break;
		case '\"':
			b.append("\\\""); //$NON-NLS-1$
			break;
		case '\'':
			b.append("\\\'"); //$NON-NLS-1$
			break;
		case '\\':
			b.append("\\\\"); //$NON-NLS-1$
			break;
		case '\0':
			b.append("\\0"); //$NON-NLS-1$
			break;
		case '\1':
			b.append("\\1"); //$NON-NLS-1$
			break;
		case '\2':
			b.append("\\2"); //$NON-NLS-1$
			break;
		case '\3':
			b.append("\\3"); //$NON-NLS-1$
			break;
		case '\4':
			b.append("\\4"); //$NON-NLS-1$
			break;
		case '\5':
			b.append("\\5"); //$NON-NLS-1$
			break;
		case '\6':
			b.append("\\6"); //$NON-NLS-1$
			break;
		case '\7':
			b.append("\\7"); //$NON-NLS-1$
			break;
		default:
			b.append(value);
		}
		b.append('\'');
		return b.toString();
	}

	/**
	 * Indicate if a type signature corresponds to the {@link Object} type.
	 * 
	 * @param signature
	 *            the signature
	 * @return {@code true} if the supplied signature corresponds to the Object
	 *         type, {@code false} otherwise
	 */
	public static boolean isJavaLangObject(final String signature) {
		return JavaModelDelegateBindingFactory.JAVA_LANG_OBJECT_SIGNATURE.equals(signature);
	}
}
