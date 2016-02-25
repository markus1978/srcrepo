package de.hub.srcrepo.snapshot.internal

import de.hub.srcrepo.repositorymodel.UnresolvedLink
import org.eclipse.gmt.modisco.java.UnresolvedItem

class SnapshotUtils {	
	public static def fullId(extension UnresolvedLink link) {
		if (link.target instanceof UnresolvedItem) {
			return target.eClass.name + ":" + id
		} else {
			return id
		}
	}	
}