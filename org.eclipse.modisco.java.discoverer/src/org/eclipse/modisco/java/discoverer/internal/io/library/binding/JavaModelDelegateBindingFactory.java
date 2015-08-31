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
 *******************************************************************************/

package org.eclipse.modisco.java.discoverer.internal.io.library.binding;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.ClassBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.FieldBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.JDTDelegateBindingFactory;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.MethodBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PackageBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.ParameterBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.UnresolvedBinding;
import org.eclipse.modisco.java.discoverer.internal.io.library.ClassFileParser;

/**
 * A factory which uses the Java Model API to build MoDisco {@link Binding}s.
 * 
 */
public final class JavaModelDelegateBindingFactory {

	/**
	 * The unique instance of this factory.
	 */
	private static JavaModelDelegateBindingFactory instance = new JavaModelDelegateBindingFactory();

	/**
	 * The signature of the {@link Object} type.
	 */
	public static final String JAVA_LANG_OBJECT_SIGNATURE = Signature.createTypeSignature(
			"java.lang.Object", true); //$NON-NLS-1$

	/**
	 * Singleton pattern.
	 */
	private JavaModelDelegateBindingFactory() {
		super();
	}

	/**
	 * Returns the unique instance of this factory.
	 * 
	 * @return the instance.
	 */
	public static JavaModelDelegateBindingFactory getInstance() {
		return JavaModelDelegateBindingFactory.instance;
	}

	/**
	 * Returns the MoDisco {@link ClassBinding} corresponding to the Java type
	 * represented by the Java signature.
	 * <p>
	 * Please note that if the represented type cannot be resolved, the returned
	 * {@code ClassBinding} will only have a non-parameterized readable
	 * {@link ClassBinding#toString() name} (eg : {@code java.lang.ArrayList})
	 * </p>
	 * 
	 * @param sig
	 *            the java signature (eg : {@code Ljava.lang.Object;})
	 * @param visitor
	 *            the {@code ClassFileParser}
	 * @param removeGenerics
	 *            indicate if we remove the generics from the given signature
	 * @return the MoDisco {@code ClassBinding}
	 */
	public ClassBinding getBindingForName(final String sig, final ClassFileParser visitor,
			final boolean removeGenerics) {
		String readableSignature = null;
		if (removeGenerics) {
			readableSignature = Signature.toString(Signature.getTypeErasure(sig));
		} else {
			readableSignature = Signature.toString(sig);
		}
		try {
			switch (Signature.getTypeSignatureKind(sig)) {
			case Signature.ARRAY_TYPE_SIGNATURE:
				return getBindingForArrayType(sig, visitor, removeGenerics);

			case Signature.BASE_TYPE_SIGNATURE:
				return getBindingForPrimitiveType(sig, visitor);

			case Signature.CLASS_TYPE_SIGNATURE:
				if (Signature.getTypeArguments(sig).length > 0 && !removeGenerics) {
					return getBindingForParameterizedType(sig, visitor, removeGenerics);
				}
				IType type = visitor.getTypeFinder().getType(readableSignature);
				if (type != null) {
					return getClassBinding(type, visitor);
				}
				break;

			case Signature.WILDCARD_TYPE_SIGNATURE:
				return getBindingForWildCardType(sig, visitor);

			case Signature.TYPE_VARIABLE_SIGNATURE:
				ITypeParameter typeParameter = visitor.findTypeParameter(readableSignature);
				return getTypeParameterBinding(typeParameter, visitor, removeGenerics);

			case Signature.CAPTURE_TYPE_SIGNATURE:
				// fall back to java.lang.Object
				return getBindingForName(
						JavaModelDelegateBindingFactory.JAVA_LANG_OBJECT_SIGNATURE, visitor,
						removeGenerics);
			default:
			}
		} catch (JavaModelException e) {
			// Nothing
			assert (true); // dummy code for "EmptyBlock" Rule
		}

		ClassBinding binding = new ClassBinding();
		binding.setName(readableSignature);
		return binding;
	}

	/**
	 * Returns the MoDisco {@link Binding} corresponding to the Java entity
	 * represented by the Java Model element.
	 * 
	 * @param element
	 *            the Java Model element
	 * @param visitor
	 *            the {@code ClassFileParser}
	 * @return the MoDisco {@code Binding}
	 */
	public Binding getBindingForElement(final IJavaElement element, final ClassFileParser visitor) {
		Binding result = null;
		try {
			result = getBinding(element, visitor);
		} catch (JavaModelException e) {
			IStatus status = new Status(IStatus.WARNING, JavaActivator.PLUGIN_ID, e.getException()
					.getMessage(), e);
			JavaActivator.getDefault().getLog().log(status);
		}
		if (result == null) {
			result = new UnresolvedBinding(element.getElementName());
		}
		return result;
	}

	/**
	 * @see JDTDelegateBindingFactory#getBindingForWildCardType(org.eclipse.jdt.core.dom.WildcardType)
	 */
	public ClassBinding getBindingForWildCardType(final String signature,
			final ClassFileParser visitor) {
		ClassBinding binding = new ClassBinding();
		StringBuilder buffer = new StringBuilder();

		String boundSignature = null;

		buffer.append("?"); //$NON-NLS-1$
		if (signature.charAt(0) == Signature.C_EXTENDS) {
			buffer.append(" extends "); //$NON-NLS-1$
			boundSignature = signature.substring(1);
		} else if (signature.charAt(0) == Signature.C_SUPER) {
			buffer.append(" super "); //$NON-NLS-1$
			boundSignature = signature.substring(1);
		}

		if (boundSignature != null) {
			ClassBinding typeErasureBinding = getBindingForName(boundSignature, visitor, false);
			buffer.append(typeErasureBinding.toString());
		}
		binding.setName(buffer.toString());
		return binding;
	}

	/**
	 * @see JDTDelegateBindingFactory#getBindingForParameterizedType(org.eclipse.jdt.core.dom.ParameterizedType)
	 */
	public ClassBinding getBindingForParameterizedType(final String signature,
			final ClassFileParser visitor, final boolean removeGenerics) {
		ClassBinding binding = new ClassBinding();
		StringBuilder buffer = new StringBuilder();

		// type erasure
		String typeErasureSignature = Signature.getTypeErasure(signature);
		ClassBinding typeErasureBinding = getBindingForName(typeErasureSignature, visitor,
				removeGenerics);
		buffer.append(typeErasureBinding.toString());

		if (!removeGenerics) {
			buffer.append('<');

			// type arguments
			String[] typeArgumentsSignatures = Signature.getTypeArguments(signature);
			for (int i = 0; i < typeArgumentsSignatures.length; i++) {
				if (i > 0) {
					buffer.append(","); //$NON-NLS-1$
				}
				ClassBinding typeArgumentBinding = getBindingForName(typeArgumentsSignatures[i],
						visitor, removeGenerics);
				buffer.append(typeArgumentBinding.toString());
			}
			buffer.append('>');
		}
		binding.setName(buffer.toString());
		return binding;
	}

	/**
	 * @see JDTDelegateBindingFactory#getBindingForPrimitiveType(org.eclipse.jdt.core.dom.PrimitiveType)
	 */
	public static ClassBinding getBindingForPrimitiveType(final String signature,
			final ClassFileParser visitor) {
		ClassBinding binding = new ClassBinding();
		binding.setName(Signature.toString(signature));
		return binding;
	}

	/**
	 * @see JDTDelegateBindingFactory#getBindingForArrayType(org.eclipse.jdt.core.dom.ArrayType)
	 */
	public ClassBinding getBindingForArrayType(final String type, final ClassFileParser visitor,
			final boolean removeGenerics) {
		ClassBinding result = new ClassBinding();

		ClassBinding elementTypeBinding = getBindingForName(Signature.getElementType(type),
				visitor, removeGenerics);

		int dimensions = Signature.getArrayCount(type);
		char[] brackets = new char[dimensions * 2];
		for (int i = dimensions * 2 - 1; i >= 0; i -= 2) {
			brackets[i] = ']';
			brackets[i - 1] = '[';
		}

		result.setName(elementTypeBinding.toString() + String.valueOf(brackets));
		return result;
	}

	private Binding getBinding(final IJavaElement element, final ClassFileParser visitor)
			throws JavaModelException {
		Binding result = null;
		if (element instanceof IMethod) {
			result = getMethodBinding((IMethod) element, visitor);

		} else if (element instanceof IType) {
			result = getClassBinding((IType) element, visitor);

		} else if (element instanceof ITypeParameter) {
			result = getTypeParameterBinding((ITypeParameter) element, visitor, false);

		} else if (element instanceof IPackageFragment) {
			result = getPackageBinding((IPackageFragment) element, visitor);

		} else if (element instanceof IField) {
			result = getFieldBinding((IField) element, visitor);
		}
		return result;
	}

	private ClassBinding getTypeParameterBinding(final ITypeParameter element,
			final ClassFileParser visitor, final boolean removeGenerics) throws JavaModelException {
		if (removeGenerics) {
			String erasure = element.getBounds()[0];
			return getBindingForName(Signature.createTypeSignature(erasure, true), visitor,
					removeGenerics);
		}
		ClassBinding result = new ClassBinding();
		result.setName(element.getElementName());
		result.setTypeVariable(true);
		return result;
	}

	private MethodBinding getMethodBinding(final IMethod method, final ClassFileParser visitor)
			throws JavaModelException {
		MethodBinding result = new MethodBinding();

		result.setName(method.getElementName());
		result.setDeclaringClass(getClassBinding(method.getDeclaringType(), visitor));
		result.setConstructor(method.isConstructor());
		result.setAnnotationMember(method.getDeclaringType().isAnnotation());
		for (int i = 0; i < method.getParameterTypes().length; i++) {
			result.getParameters().add(getParameterBinding(method.getParameterTypes()[i], visitor));
		}
		return result;
	}

	private ParameterBinding getParameterBinding(final String type, final ClassFileParser visitor) {
		ParameterBinding result = new ParameterBinding();

		int dimensions = Signature.getArrayCount(type);
		result.setDimensions(dimensions);
		if (dimensions > 0) {
			result.setElementType(getBindingForName(Signature.getElementType(type), visitor, true));
		} else {
			result.setElementType(getBindingForName(type, visitor, true));
		}
		return result;
	}

	private static PackageBinding getPackageBinding(final IPackageFragment pck,
			final ClassFileParser visitor) {
		PackageBinding result = new PackageBinding();
		result.setName(pck.getElementName());
		return result;
	}

	private ClassBinding getClassBinding(final IType type, final ClassFileParser visitor)
			throws JavaModelException {
		ClassBinding result = new ClassBinding();

		result.setInterface(type.isInterface());
		result.setAnnotation(type.isAnnotation());
		result.setEnum(type.isEnum());
		result.setName(type.getElementName());

		if (type.getPackageFragment() != null && type.getPackageFragment().exists()) {
			result.setOwnerPackage(getPackageBinding(type.getPackageFragment(), visitor));
		}
		if (type.getDeclaringType() != null && type.getDeclaringType().exists()) {
			result.setDeclaringClass(getClassBinding(type.getDeclaringType(), visitor));
		}
		if (type.isClass()) {
			String superClassSignature = type.getSuperclassTypeSignature();
			if (superClassSignature != null
					&& !superClassSignature
							.equals(JavaModelDelegateBindingFactory.JAVA_LANG_OBJECT_SIGNATURE)) {
				result.setSuperClass(getBindingForName(superClassSignature, visitor, true));
			}
		}
		// annotations can't have explicit superinterfaces
		// they implement implicitely java.lang.annotation.Annotation
		// we don't save this information because an annotation don't have
		// superinterfaces in the model
		if (!type.isAnnotation() && type.getSuperInterfaceTypeSignatures() != null) {
			for (String superInterfaceSignature : type.getSuperInterfaceTypeSignatures()) {
				if (!superInterfaceSignature
						.equals(JavaModelDelegateBindingFactory.JAVA_LANG_OBJECT_SIGNATURE)) {
					result.getSuperInterfaces().add(
							getBindingForName(superInterfaceSignature, visitor, true));
				}
			}
		}
		// types parameters
		if (type.getTypeParameters() != null) {
			for (ITypeParameter typeParameter : type.getTypeParameters()) {
				result.addTypeParameters(typeParameter.getElementName());
			}
		}
		return result;
	}

	private FieldBinding getFieldBinding(final IField field, final ClassFileParser visitor)
			throws JavaModelException {
		FieldBinding result = new FieldBinding();
		result.setName(field.getElementName());
		result.setEnumConstant(field.isEnumConstant());
		if (field.getDeclaringType() != null && field.getDeclaringType().exists()) {
			result.setDeclaringClass(getClassBinding(field.getDeclaringType(), visitor));
		}
		return result;
	}

}
