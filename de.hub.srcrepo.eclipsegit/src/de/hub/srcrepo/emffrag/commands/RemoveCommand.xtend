package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import org.apache.commons.cli.CommandLine

class RemoveCommand extends AbstractRepositoryCommand {
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel model, CommandLine cl) {
		if (directory.repositories.remove(model)) {
			val fragmentation = fs.fragmentations.findFirst[root == model]
			fs.fragmentations.remove(fragmentation)
			fragmentation.dataStore.drop
			println("Removed " + model.name + " in " + directory.name)
		}		
	}
	
}