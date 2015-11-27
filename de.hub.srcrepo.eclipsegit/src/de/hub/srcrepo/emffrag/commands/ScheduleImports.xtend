package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.repositorymodel.RepositoryModel
import org.apache.commons.cli.CommandLine
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory

class ScheduleImports extends AbstractRepositoryCommand {
	
	override protected def void runOnRepository(RepositoryModelDirectory directory, RepositoryModel it, CommandLine cl) {
		val importMetaData = it.metaData.importMetaData
		if (!importMetaData.imported && !importMetaData.importing && !importMetaData.scheduled) {
			System.out.println("Schedule " + it.name)				
			importMetaData.scheduled = true				
		}
	}
}