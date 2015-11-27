package de.hub.srcrepo.eclipsegit

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*
import org.apache.commons.cli.CommandLine

class UpdateDirectoryDerivedFeatures extends AbstractSrcRepoCommand {
	override protected run(CommandLine cl) {
		directory.updateImportStatuses
	}
}