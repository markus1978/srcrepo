package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.List
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

/**
 * Program that executes a headless {@link SrcRepoDirectoryImport} 
 * for each scheduled repository. Only executes a certain number of
 * imports at a time.
 */
class ImportCommand extends AbstractRepositoryCommand {
	
	val List<Integer> ports = Collections.synchronizedList(newArrayList)   

	private def dateTime() {
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.newInstance)
	}
	
	private def void runImport(RepositoryModel repository) {
		val port = ports.remove(0)
		var result = 0
		System.out.println('''Start import of «repository.qualifiedName». Stats at port «port».''')
		try {
			val cmd = '''02-scripts/run-importdeamon.sh «repository.qualifiedName» «port» «new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Date.newInstance)»'''		
			val process = Runtime.runtime.exec(cmd)
			if (!process.waitFor(12, TimeUnit.HOURS)) {
				System.out.println('''«dateTime»: Need to abort the import of «repository.qualifiedName» because of timeout.''')
				process.destroy
				result = -1
			} else {
				result = process.exitValue				
			}
		} catch (Exception e) {
			System.out.println('''«dateTime»: Could not finish import of «repository.qualifiedName» with «result», because «e.toString + ":" + e.message».''')
			e.printStackTrace(System.out)
			return
		} 
		ports.add(port)
		System.out.println('''«dateTime»: Finished import of «repository.qualifiedName» with «result».''')
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("i").longOpt("--instances").desc("The number of parallel importers to run. Default is 5").hasArg.build)
	}
	
	var ExecutorService executor = null
	
	override protected run(CommandLine cl) {					
		val importerCount = Integer.parseInt(cl.getOptionValue("i", "5"))
		for (i:0..importerCount) ports += 8080 + i
		executor = Executors::newFixedThreadPool(importerCount)
		
		super.run(cl)
		
		executor.shutdown()
		executor.awaitTermination(10, TimeUnit::DAYS)
	}
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel model, CommandLine cl) {
		executor.submit[
			model.runImport
		]
	}
	
}