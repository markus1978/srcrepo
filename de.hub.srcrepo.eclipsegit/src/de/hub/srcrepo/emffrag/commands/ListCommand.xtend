package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.repositorymodel.MongoDBMetaData
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.text.DecimalFormat
import org.apache.commons.cli.CommandLine

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

class ListCommand extends AbstractRepositoryCommand {
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel model, CommandLine cl) {
		val countFormat = new DecimalFormat("#,###,###,###")
		val size = if (model.metaData.dataStoreMetaData instanceof MongoDBMetaData) {
			countFormat.format((model.metaData.dataStoreMetaData as MongoDBMetaData).storeSize / 1000000) + "MB"
		} else {
			"unknown"
		}
		val status = if (model.metaData.importMetaData.imported) {
			"imported"
		} else if (model.metaData.importMetaData.scheduled) {
			"scheduled"
		} else if (model.metaData.importMetaData.importing) {
			"importing"
		} else {
			"listed only"
		}
		println('''«model.qualifiedName» («status») revs=«countFormat.format(model.metaData.revCount)» cus=«countFormat.format(model.metaData.cuCount)» size=«size»''')	
	}
	
}