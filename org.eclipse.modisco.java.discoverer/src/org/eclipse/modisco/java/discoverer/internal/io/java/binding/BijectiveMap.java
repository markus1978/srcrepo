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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a parameterized bijective map
 */
public class BijectiveMap<Key, Value> {

	private final Map<Key, Value> keyValue = new HashMap<Key, Value>();
	private final Map<Value, Key> valueKey = new HashMap<Value, Key>();

	public void put(final Key key, final Value value) {
		// Here there should be some cleaning in maps for old (key, value') or
		// old (key', value)
		// Not done for performances
		this.keyValue.put(key, value);
		this.valueKey.put(value, key);
	}

	public Value getValue(final Key key) {
		return this.keyValue.get(key);
	}

	@SuppressWarnings("unchecked")
	public Value get(final Object key) {
		Value result = null;
		// if (key instanceof Key) {
		result = this.getValue((Key) key);
		// }
		return result;
	}

	public Key getKey(final Value value) {
		return this.valueKey.get(value);
	}

	public Collection<Value> getValues() {
		return this.valueKey.keySet(); // this.keyValue.values(); //
	}

	public Collection<Key> getKeys() {
		return this.keyValue.keySet(); // this.valueKey.values(); //
	}
}
