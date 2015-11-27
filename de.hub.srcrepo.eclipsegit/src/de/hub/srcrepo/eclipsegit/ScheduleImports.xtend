package de.hub.srcrepo.eclipsegit

import de.hub.srcrepo.repositorymodel.RepositoryModel
import org.apache.commons.cli.CommandLine

class ScheduleImports extends AbstractRepositoryCommand {
	
	override protected def void runOnRepository(RepositoryModel it, CommandLine cl) {
		val importMetaData = it.metaData.importMetaData
		if (!importMetaData.imported && !importMetaData.importing && !importMetaData.scheduled) {
			System.out.println("Schedule " + it.name)				
			importMetaData.scheduled = true				
		}
	}
}