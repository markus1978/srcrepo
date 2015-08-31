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

/**
 * <code>Binding</code> representing a method parameter.
 * 
 */
public class ParameterBinding extends Binding {

	/**
	 * dimensions of this array type
	 */
	private int dimensions = 0;

	private ClassBinding elementType = null;

	public ClassBinding getElementType() {
		return this.elementType;
	}

	public void setElementType(final ClassBinding elementType) {
		this.elementType = elementType;
	}

	public int getDimensions() {
		return this.dimensions;
	}

	public void setDimensions(final int dimensions) {
		this.dimensions = dimensions;
	}

	public boolean isArray() {
		return this.dimensions > 0;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(getElementType().toString());
		for (int i = 0; i < getDimensions(); i++) {
			buffer.append("[]"); //$NON-NLS-1$
		}
		return buffer.toString();
	}

}
