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

import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.WildcardType;

/**
 * A Factory which creates MoDisco {@link Binding}s from JDT nodes.
 * 
 * @see org.eclipse.jdt.core.dom.ASTNode
 */
public interface IBindingFactory {

	/**
	 * Returns the MoDisco {@link Binding} corresponding to the Java entity
	 * represented by the JDT {@code name}.
	 * 
	 * @param name
	 *            the JDT name.
	 * @return the MoDisco {@code Binding}.
	 */
	Binding getBindingForName(Name name);

	/**
	 * Returns the MoDisco {@link Binding} corresponding to the JDT
	 * PrimitiveType {@code type}.
	 * 
	 * @param type
	 *            the JDT PrimitiveType object.
	 * @return the MoDisco {@code Binding}.
	 */
	Binding getBindingForPrimitiveType(PrimitiveType type);

	/**
	 * Returns the MoDisco {@link Binding} corresponding to the JDT WildcardType
	 * {@code type}.
	 * 
	 * @param type
	 *            the JDT WildcardType object.
	 * @return the MoDisco {@code Binding}.
	 */
	Binding getBindingForWildCardType(WildcardType type);

	/**
	 * Returns the MoDisco {@link Binding} corresponding to the JDT
	 * ParameterizedType {@code type}.
	 * 
	 * @param type
	 *            the JDT ParameterizedType object.
	 * @return the MoDisco {@code Binding}.
	 */
	Binding getBindingForParameterizedType(ParameterizedType type);

	/**
	 * Returns the MoDisco {@link Binding} corresponding to the JDT ArrayType
	 * {@code type}.
	 * 
	 * @param type
	 *            the JDT ArrayType object.
	 * @return the MoDisco {@code Binding}.
	 */
	Binding getBindingForArrayType(ArrayType type);

	/**
	 * Returns the MoDisco {@link Binding} corresponding to a constructor call
	 * (basically, a {@link MethodBinding}).
	 * 
	 * @param constructorCall
	 *            the JDT ClassInstanceCreation object.
	 * @return the MoDisco {@code Binding}.
	 */
	Binding getBindingForClassInstanceCreation(ClassInstanceCreation constructorCall);

	/**
	 * Returns the MoDisco {@link Binding} corresponding to a constructor call
	 * (basically, a {@link MethodBinding}).
	 * 
	 * @param constructorCall
	 *            the JDT ConstructorInvocation object.
	 * @return the MoDisco {@code Binding}.
	 */
	Binding getBindingForConstructorInvocation(ConstructorInvocation constructorCall);

	/**
	 * Returns the MoDisco {@link Binding} corresponding to a super constructor
	 * call (basically, a {@link MethodBinding}).
	 * 
	 * @param constructorCall
	 *            the JDT SuperConstructorInvocation object.
	 * @return the MoDisco {@code Binding}.
	 */
	Binding getBindingForSuperConstructorInvocation(SuperConstructorInvocation constructorCall);

	/**
	 * Returns whether the entity represented by this {@code name} represents a
	 * local element :
	 * <ul>
	 * <li>local variable (declared in a method body or an initializer)</li>
	 * <li>local method (declared in an anonymous class)</li>
	 * </ul>
	 * 
	 * @param name
	 *            the JDT name.
	 * @return {@code true} if this {@code name} represents a local variable or
	 *         method, {@code false} otherwise.
	 */
	boolean isLocal(Name name);

}