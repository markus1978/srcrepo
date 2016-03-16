package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.repositorymodel.MongoDBMetaData
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.text.DecimalFormat

import static extension de.hub.srcrepo.RepositoryModelUtil.*
import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

class ListCommand extends AbstractRepositoryCommand {
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel model) {
		val countFormat = new DecimalFormat("#,###,###,###")
		val size = if (model.dataStoreMetaData instanceof MongoDBMetaData) {
			countFormat.format((model.dataStoreMetaData as MongoDBMetaData).storeSize / 1000000) + "MB"
		} else {
			"unknown"
		}
		val status = if (model.importMetaData.imported) {
			"imported"
		} else if (model.importMetaData.scheduled) {
			"scheduled"
		} else if (model.importMetaData.importing) {
			"importing"
		} else {
			"listed only"
		}
		println('''«model.qualifiedName» («status») gitsize=«model.metaData.size»KB revs=«countFormat.format(model.metaData.revCount)» cus=«countFormat.format(model.metaData.cuCount)» size=«size»''')	
	}
	
}