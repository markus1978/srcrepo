package de.hub.srcrepo.repositorymodel.util

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

class UpdateDirectoryDerivedFeatures extends AbstractRepositoryModelMain {
	
	static def void main(String[] args) {
		val instance = new UpdateDirectoryDerivedFeatures()
		instance.run(args)
	}
	
	override protected perform(String[] args) {
		directory.updateImportStatuses
	}
	
}