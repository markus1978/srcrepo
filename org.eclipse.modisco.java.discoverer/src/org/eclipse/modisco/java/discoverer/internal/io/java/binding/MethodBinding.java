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

import org.eclipse.jdt.core.dom.IMethodBinding;

/**
 * <code>Binding</code> representing a Java method.
 * 
 * @see IMethodBinding
 */
public class MethodBinding extends FeatureBinding {

	private static final int PARAMETER_NUMBER = 3;
	private List<ParameterBinding> parameters;
	private List<String> typeParameters;
	private boolean isAnnotationMember = false;
	private boolean isConstructor = false;

	/**
	 * @return Returns the parameters.
	 */
	public List<ParameterBinding> getParameters() {
		if (this.parameters == null) {
			this.parameters = new ArrayList<ParameterBinding>(MethodBinding.PARAMETER_NUMBER);
		}
		return this.parameters;
	}

	/**
	 * @param annotationMember
	 *            the annotationMember to set
	 */
	public void setAnnotationMember(final boolean annotationMember) {
		this.isAnnotationMember = annotationMember;
	}

	/**
	 * @return the annotationMember
	 */
	public boolean isAnnotationMember() {
		return this.isAnnotationMember;
	}

	public boolean isConstructor() {
		return this.isConstructor;
	}

	public void setConstructor(final boolean constructor) {
		this.isConstructor = constructor;
	}

	public List<String> getTypeParameters() {
		if (this.typeParameters == null) {
			this.typeParameters = new ArrayList<String>(2);
		}
		return this.typeParameters;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(""); //$NON-NLS-1$
		if (getDeclaringClass() != null) {
			result.append(getDeclaringClass().toString());
			result.append("."); //$NON-NLS-1$
		}
		result.append(getName());
		result.append("("); //$NON-NLS-1$
		for (int i = 0; i < getParameters().size(); i++) {
			if (i > 0) {
				result.append(","); //$NON-NLS-1$
			}
			result.append(getParameters().get(i).toString());
		}
		result.append(")"); //$NON-NLS-1$
		return result.toString();
	}
}
