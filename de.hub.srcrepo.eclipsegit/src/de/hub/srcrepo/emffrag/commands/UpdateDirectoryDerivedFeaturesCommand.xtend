package de.hub.srcrepo.emffrag.commands

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*
import org.apache.commons.cli.CommandLine

class UpdateDirectoryDerivedFeaturesCommand extends AbstractSrcRepoCommand {
	override protected run(CommandLine cl) {
		directory.updateImportStatuses
	}
}