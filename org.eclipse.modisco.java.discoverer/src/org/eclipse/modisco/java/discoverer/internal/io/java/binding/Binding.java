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

import org.eclipse.jdt.core.dom.IBinding;

/**
 * <code>Binding</code> representing a Java entity.
 * <p>
 * The {@link #toString()} method returns an unique identifier for this entity
 * (fully qualified name for global entities (types, methods, ...) and number
 * for local variables).
 * </p>
 * 
 * @see IBinding
 */
public class Binding {

	private String name;

	public Binding() {
		// Nothing
	}

	public Binding(final String name) {
		this.name = name;
	}

	@Override
	public boolean equals(final Object o) {
		return (o != null && o instanceof Binding && toString().equals(o.toString()));
	}

	/**
	 * Returns an unique identifier for the Java entity represented by this
	 * binding.
	 * 
	 * @return an unique identifier (fully qualified name for global entities
	 *         (types, methods, ...) and number for local variables)
	 */
	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Indicate if this binding represents an unresolved Java entity. An
	 * unresolved binding only knows the name of the Java entity.
	 * 
	 * @return {@code true} if this binding is unresolved, {@code false}
	 *         otherwise
	 */
	@SuppressWarnings("static-method") // designed for extension
	public boolean isUnresolved() {
		return false;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
