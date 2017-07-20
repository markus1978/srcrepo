package de.hub.srcrepo.emffrag.commands

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

class UpdateDirectoryDerivedFeaturesCommand extends AbstractSrcRepoCommand {
	override protected run() {
		directory.updateImportStatuses
	}
}