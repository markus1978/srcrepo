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

import org.eclipse.jdt.core.dom.IVariableBinding;

/**
 * <code>Binding</code> representing a field.
 * 
 * @see IVariableBinding
 */
public class FieldBinding extends FeatureBinding {

	private boolean isEnumConstant;

	public boolean isEnumConstant() {
		return this.isEnumConstant;
	}

	public void setEnumConstant(final boolean isAnEnumConstant) {
		this.isEnumConstant = isAnEnumConstant;
	}

}
