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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ITypeBinding;

/**
 * {@code Binding} representing a type (Class, Interface, Annotation, or Enum).
 * 
 * @see ITypeBinding
 */
public class ClassBinding extends Binding {

	private ClassBinding declaringClass = null;
	private PackageBinding ownerPackage = null;
	private boolean isInterface = false;
	private boolean isAnnotation = false;
	private boolean isEnum = false;
	private ClassBinding superClass = null;
	private List<ClassBinding> superInterfaces = null;
	private List<String> typeParameters = null;
	private boolean isTypeVariable = false;

	/**
	 * the element type of this array type
	 */
	private ClassBinding elementType = null;

	public ClassBinding getElementType() {
		return this.elementType;
	}

	public void setElementType(final ClassBinding elementType) {
		this.elementType = elementType;
	}

	public boolean isTypeVariable() {
		return this.isTypeVariable;
	}

	public void setTypeVariable(final boolean isATypeVariable) {
		this.isTypeVariable = isATypeVariable;
	}

	public ClassBinding getDeclaringClass() {
		return this.declaringClass;
	}

	public void setDeclaringClass(final ClassBinding declaringClass) {
		this.declaringClass = declaringClass;
	}

	public PackageBinding getOwnerPackage() {
		return this.ownerPackage;
	}

	public void setOwnerPackage(final PackageBinding ownerPackage) {
		this.ownerPackage = ownerPackage;
	}

	public boolean isInterface() {
		return this.isInterface;
	}

	public void setInterface(final boolean isAnInterface) {
		this.isInterface = isAnInterface;
	}

	public ClassBinding getSuperClass() {
		return this.superClass;
	}

	public void setSuperClass(final ClassBinding superClass) {
		this.superClass = superClass;
	}

	public List<ClassBinding> getSuperInterfaces() {
		if (this.superInterfaces == null) {
			this.superInterfaces = new ArrayList<ClassBinding>(2);
		}
		return this.superInterfaces;
	}

	public List<String> getTypeParameters() {
		return this.typeParameters;
	}

	public void addTypeParameters(final String typeParameter) {
		if (this.typeParameters == null) {
			this.typeParameters = new ArrayList<String>();
		}
		this.typeParameters.add(typeParameter);
	}

	public void setAnnotation(final boolean isAnAnnotation) {
		this.isAnnotation = isAnAnnotation;
	}

	public boolean isAnnotation() {
		return this.isAnnotation;
	}

	public boolean isEnum() {
		return this.isEnum;
	}

	public void setEnum(final boolean isAnEnum) {
		this.isEnum = isAnEnum;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		if (isTypeVariable()) {
			// for type variables, we just print the name
			buffer.append(getName());
		} else {
			// top level type
			if (getOwnerPackage() != null && getDeclaringClass() == null) {
				buffer.append(getOwnerPackage().toString());
				buffer.append("."); //$NON-NLS-1$
			}
			// nested type
			if (getDeclaringClass() != null) {
				buffer.append(getDeclaringClass().toString());
				buffer.append("."); //$NON-NLS-1$
			}
			buffer.append(getName());
		}
		return buffer.toString();
	}

}
