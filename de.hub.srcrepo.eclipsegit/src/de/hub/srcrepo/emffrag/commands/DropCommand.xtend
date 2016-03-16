package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.mongodb.MongoDBDataStore
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

class DropCommand extends AbstractSrcRepoCommand {
	
	override protected run() {
		if (cl.hasOption("really-sure")) {
			fs.close
			MongoDBDataStore.dropDatabase(modelURI)
			println("Dropped the whole database for " + modelURI)
		} else {
			println("Did not drop anything. You need to be really sure.")
		}
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder().longOpt("really-sure").desc("Will only drop if you are really sure.").build)
	}
	
}