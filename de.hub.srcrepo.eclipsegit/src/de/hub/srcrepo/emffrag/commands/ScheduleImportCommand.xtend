package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import static extension de.hub.srcrepo.RepositoryModelUtil.*

class ScheduleImportCommand extends AbstractRepositoryCommand {
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("f").longOpt("force").desc("Try to clear the repository and schedule it no matter its current status.").build)
	}
	
	override protected def void runOnRepository(RepositoryModelDirectory directory, RepositoryModel it, CommandLine cl) {
		val importMetaData = it.importMetaData
		if (!importMetaData.imported && !importMetaData.importing && !importMetaData.scheduled) {
			System.out.println("Schedule " + it.name)				
			importMetaData.scheduled = true				
		} else if (cl.hasOption("f")) {
			System.out.println("Force schedule " + it.name)
			
			it.metaData.revCount = 0
			it.metaData.cuCount = 0
			it.metaData.cusWithErrors = 0
			it.metaData.revsWithErrors = 0
			
			it.dataSets.remove(it.dataStoreMetaData)
			
			importMetaData.imported =  false
			importMetaData.importing = false				
			importMetaData.scheduled = true
			
			it.traversals = null
			it.rootRevs.clear
			it.allRefs.clear
			it.allRevs.clear
		}
	}
}