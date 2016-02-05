package de.hub.srcrepo.snapshot.internal

import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.EObject

class DebugAdapter extends AdapterImpl {
	var String value
	
	private new(String value) {
		this.value = value
	}
	
	public static def setMark(EObject object, String value) {
		val existing = object.eAdapters.findFirst[it instanceof DebugAdapter] as DebugAdapter
		if (existing != null) {
			existing.value = value
		} else {
			object.eAdapters += new DebugAdapter(value)
		}
	}
	
	public static def getMark(EObject object) {
		val existing = object.eAdapters.findFirst[it instanceof DebugAdapter] as DebugAdapter
		return existing?.value
	}
}