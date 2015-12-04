package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.fragmentation.FObject
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import org.apache.commons.cli.CommandLine

class RemoveCommand extends AbstractRepositoryCommand {
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel model, CommandLine cl) {
		directory.repositories.remove(model)
		val fragmentation = (model as FObject).fFragmentation
		fragmentation.fragmentationSet.fragmentations.remove(fragmentation)
		fragmentation.close
		fragmentation.dataStore.drop
		println("Removed " + model.name + " in " + directory.name)
	}
	
}