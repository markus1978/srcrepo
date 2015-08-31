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

import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.WildcardType;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.Messages;

/**
 * A factory which uses the JDT Binding API to build MoDisco {@link Binding}s.
 * 
 * @see IBinding
 * @see ASTParser#setResolveBindings(boolean)
 */
public final class JDTDelegateBindingFactory implements IBindingFactory {
	private boolean logJDTBindingsIssues = false;

	/**
	 * The unique instance of this factory.
	 */
	private static IBindingFactory instance = new JDTDelegateBindingFactory();

	/**
	 * Singleton pattern.
	 */
	private JDTDelegateBindingFactory() {
		super();
	}

	/**
	 * Returns the unique instance of this factory.
	 * 
	 * @return the instance.
	 */
	public static IBindingFactory getInstance() {
		return JDTDelegateBindingFactory.instance;
	}

	public void setLogJDTBindingsIssues(final boolean newValue) {
		this.logJDTBindingsIssues = newValue;
	}

	public Binding getBindingForName(final Name name) {
		Binding result = getBinding(name.resolveBinding());
		if (result == null) {
			// System.out.println("*** WARNING : binding '" +
			// name.getFullyQualifiedName() + "' unresolved.");
			result = new UnresolvedBinding(name.getFullyQualifiedName());
		}
		return result;
	}

	public Binding getBindingForPrimitiveType(final PrimitiveType type) {
		Binding result = new Binding(type.getPrimitiveTypeCode().toString());
		return result;
	}

	public Binding getBindingForParameterizedType(final ParameterizedType type) {
		Binding result = null;
		ITypeBinding binding = type.resolveBinding();
		if (binding == null) {
			if (this.logJDTBindingsIssues) {
				MoDiscoLogger.logWarning("*** WARNING : binding '" //$NON-NLS-1$
						+ type.toString() + "' unresolved.", JavaActivator //$NON-NLS-1$
						.getDefault());
			}
			result = new UnresolvedBinding(type.toString());
		} else {
			String bindingId = binding.getQualifiedName();
			// bindingId is of kind 'PP.XX<AA, BB, ..>'
			if (bindingId.indexOf('<') > 0) {
				result = new Binding(bindingId);
			} else {
				// wrong jdt 'PP.XX' return in some misc java construct with
				// compile errors (bugzilla 324761)
				result = new UnresolvedBinding(type.toString());
			}
		}
		return result;
	}

	public Binding getBindingForWildCardType(final WildcardType type) {
		Binding result = null;
		ITypeBinding binding = type.resolveBinding();
		if (binding == null) {
			if (this.logJDTBindingsIssues) {
				MoDiscoLogger.logWarning("*** WARNING : binding '" //$NON-NLS-1$
						+ type.toString() + "' unresolved.", JavaActivator //$NON-NLS-1$
						.getDefault());
			}
			result = new UnresolvedBinding(type.toString());
		} else {
			result = new Binding(binding.getQualifiedName());
		}
		return result;
	}

	public Binding getBindingForArrayType(final ArrayType type) {
		Binding result = null;
		ITypeBinding binding = type.resolveBinding();
		if (binding == null) {
			if (this.logJDTBindingsIssues) {
				MoDiscoLogger.logWarning("*** WARNING : binding '" //$NON-NLS-1$
						+ type.toString() + "' unresolved.", JavaActivator //$NON-NLS-1$
						.getDefault());
			}
			result = new UnresolvedBinding(type.toString());
		} else {
			result = new ClassBinding();
			result.setName(binding.getQualifiedName());
		}
		return result;
	}

	public Binding getBindingForClassInstanceCreation(final ClassInstanceCreation constructorCall) {
		Binding result = null;
		IMethodBinding binding = constructorCall.resolveConstructorBinding();
		if (binding == null || binding.getDeclaringClass() == null) {
			if (this.logJDTBindingsIssues) {
				MoDiscoLogger.logWarning("*** WARNING : binding '" //$NON-NLS-1$
						+ constructorCall.toString() + Messages.JDTDelegateBindingFactory_10,
						JavaActivator.getDefault());
			}

			result = new UnresolvedBinding(constructorCall.toString());
		} else {
			result = getMethodBinding(binding);
		}
		return result;
	}

	public Binding getBindingForConstructorInvocation(final ConstructorInvocation constructorCall) {
		Binding result = null;
		IMethodBinding binding = constructorCall.resolveConstructorBinding();
		if (binding == null || binding.getDeclaringClass() == null) {
			if (this.logJDTBindingsIssues) {
				MoDiscoLogger.logWarning("*** WARNING : binding '" //$NON-NLS-1$
						+ constructorCall.toString() + "' unresolved.", //$NON-NLS-1$
						JavaActivator.getDefault());
			}
			result = new UnresolvedBinding(constructorCall.toString());
		} else {
			result = getMethodBinding(binding);
		}
		return result;
	}

	public Binding getBindingForSuperConstructorInvocation(
			final SuperConstructorInvocation constructorCall) {
		Binding result = null;
		IMethodBinding binding = constructorCall.resolveConstructorBinding();
		if (binding == null || binding.getDeclaringClass() == null) {
			// managing misc binding.getName() NPE
			if (this.logJDTBindingsIssues) {
				MoDiscoLogger.logWarning("*** WARNING : binding '" //$NON-NLS-1$
						+ constructorCall.toString() + "' unresolved.", //$NON-NLS-1$
						JavaActivator.getDefault());
			}
			result = new UnresolvedBinding(constructorCall.toString());
		} else {
			result = getMethodBinding(binding);
		}
		return result;
	}

	private Binding getBinding(final IBinding binding) {
		Binding result = null;
		if (binding instanceof IMethodBinding) {
			result = getMethodBinding((IMethodBinding) binding);

		} else if (binding instanceof ITypeBinding) {
			result = getClassBinding((ITypeBinding) binding, false);

		} else if (binding instanceof IPackageBinding) {
			result = getPackageBinding((IPackageBinding) binding);

		} else if (binding instanceof IVariableBinding && ((IVariableBinding) binding).isField()) {
			result = getFieldBinding((IVariableBinding) binding);

		} else if (binding instanceof IVariableBinding && !((IVariableBinding) binding).isField()) {
			result = getVariableBinding((IVariableBinding) binding);
		}
		return result;
	}

	private MethodBinding getMethodBinding(final IMethodBinding methodBinding) {
		/*
		 * in case of generic method, a parameterized method binding
		 * encapsulates real method in a second binding.
		 */
		IMethodBinding binding = methodBinding.getMethodDeclaration();
		MethodBinding result = new MethodBinding();

		result.setName(binding.getName());
		result.setDeclaringClass(getClassBinding(binding.getDeclaringClass(), false));
		result.setConstructor(binding.isConstructor());
		result.setAnnotationMember(binding.isAnnotationMember());
		for (int i = 0; i < binding.getParameterTypes().length; i++) {
			result.getParameters().add(getParameterBinding(binding.getParameterTypes()[i]));
		}
		return result;
	}

	private ParameterBinding getParameterBinding(final ITypeBinding binding) {
		ParameterBinding result = new ParameterBinding();
		result.setDimensions(binding.getDimensions());
		if (binding.isArray()) {
			result.setElementType(getClassBinding(binding.getElementType(), true));
		} else {
			result.setElementType(getClassBinding(binding, true));
		}
		return result;
	}

	private static PackageBinding getPackageBinding(final IPackageBinding binding) {
		PackageBinding result = new PackageBinding();
		result.setName(binding.getName());
		return result;
	}

	private ClassBinding getClassBinding(final ITypeBinding bindingParameter,
			final boolean isParameterBinding) {
		/*
		 * in case of generic type, a parameterized type binding encapsulates
		 * real type in a second binding.
		 */
		ITypeBinding binding = bindingParameter.getTypeDeclaration();
		ClassBinding result = new ClassBinding();

		if (binding.isTypeVariable() && isParameterBinding) {
			/*
			 * we don't want type variables or generic types in parameter
			 * bindings, so we replace it by the erasure
			 * 
			 * @see org.eclipse.jdt.core.dom.ITypeBinding#getErasure()
			 */
			if (binding.getErasure() != null) {
				result = getClassBinding(binding.getErasure(), isParameterBinding);
			} else {
				// in some case, it could be null (see bug 328143)
				result.setName(binding.getName());
				result.setTypeVariable(binding.isTypeVariable());
			}

		} else if (binding.isAnonymous()) {
			/*
			 * as anonymous types don't have names, we cannot have a qualified
			 * name hence they suppose a local context, so we rely on the
			 * binding key
			 */
			result.setName(binding.getKey());

		} else {
			result.setName(binding.getName());
			result.setTypeVariable(binding.isTypeVariable());

			result.setInterface(binding.isInterface());
			result.setEnum(binding.isEnum());
			result.setAnnotation(binding.isAnnotation());
			if (binding.getPackage() != null) {
				result.setOwnerPackage(getPackageBinding(binding.getPackage()));
			}
			if (binding.getDeclaringClass() != null) {
				result.setDeclaringClass(getClassBinding(binding.getDeclaringClass(), false));
			}
			ITypeBinding superClass = binding.getSuperclass();
			if (binding.isClass() && superClass != null
					&& !superClass.getQualifiedName().equals("java.lang.Object")) { //$NON-NLS-1$
				result.setSuperClass(getClassBinding(superClass, false));
			}
			/*
			 * annotations can't have explicit superinterfaces we don't save
			 * this information because an annotation don't have superinterfaces
			 * in the model
			 */
			if (!binding.isAnnotation() && binding.getInterfaces() != null) {
				for (ITypeBinding anInterface : binding.getInterfaces()) {
					if (!anInterface.getQualifiedName().equals("java.lang.Object")) { //$NON-NLS-1$
						result.getSuperInterfaces().add(getClassBinding(anInterface, false));
					}
				}
			}
			if (binding.getTypeParameters() != null) {
				for (ITypeBinding typeParameter : binding.getTypeParameters()) {
					result.addTypeParameters(typeParameter.getName());
				}
			}
		}
		return result;
	}

	private FieldBinding getFieldBinding(final IVariableBinding binding) {
		FieldBinding result = new FieldBinding();
		result.setName(binding.getName());
		result.setEnumConstant(binding.isEnumConstant());
		if (binding.getDeclaringClass() != null) {
			result.setDeclaringClass(getClassBinding(binding.getDeclaringClass(), false));
		}
		return result;
	}

	private static VariableBinding getVariableBinding(final IVariableBinding binding) {
		VariableBinding result = new VariableBinding();
		result.setName(String.valueOf(binding.getVariableId()));
		return result;
	}

	public boolean isLocal(final Name name) {
		return isLocalVariable(name) || isLocalMethod(name);
	}

	private static boolean isLocalVariable(final Name name) {
		boolean result = false;
		IBinding binding = name.resolveBinding();
		if (binding != null) {
			result = (binding instanceof IVariableBinding && !((IVariableBinding) binding)
					.isField());
		}
		return result;
	}

	private static boolean isLocalMethod(final Name name) {
		boolean result = false;
		IBinding binding = name.resolveBinding();
		if (binding != null) {
			if (binding instanceof IMethodBinding) {
				ITypeBinding declaringClass = ((IMethodBinding) binding).getDeclaringClass();
				if (declaringClass != null) {
					result = declaringClass.isAnonymous();
				}
			}
		}
		return result;
	}

}
