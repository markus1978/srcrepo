package de.hub.srcrepo.eclipsegit

import de.hub.srcrepo.repositorymodel.RepositoryModel
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.List
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Options
import org.apache.commons.cli.Option
import de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils

/**
 * Program that executes a headless {@link SrcRepoDirectoryImport} 
 * for each scheduled repository. Only executes a certain number of
 * imports at a time.
 */
class SrcRepoDirectoryImportScript extends AbstractSrcRepoCommand {
	
	val List<Integer> ports = Collections.synchronizedList(newArrayList)   

	
	private def void runImport(RepositoryModel repository) {
		val port = ports.remove(0)
		
		System.out.println('''Start import of «repository.qualifiedName». Stats at port «port».''')
		var result = 0
		try {
			val cmd = '''02-scripts/run-importdeamon.sh «repository.qualifiedName» «port» «new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Date.newInstance)»'''		
			val process = Runtime.runtime.exec(cmd)
			result = process.waitFor
		} catch (Exception e) {
			System.out.println('''Could not finish import of «repository.qualifiedName» with «result», because «e.toString + ":" + e.message».''')
			e.printStackTrace(System.out)
			return
		} 
		ports.add(port)
		System.out.println('''Finished import of «repository.qualifiedName» with «result».''')
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("i").longOpt("--instances").desc("The number of parallel importers to run. Default is 5").hasArg.build)
	}
	
	override protected run(CommandLine cl) {			
		val importerCount = Integer.parseInt(cl.getOptionValue("i", "5"))
		for (i:0..importerCount) ports += 8080 + i
		val scheduledRepositories = RepositoryModelUtils::scheduledForImport(directory)
		
		val executor = Executors::newFixedThreadPool(importerCount)
		scheduledRepositories.forEach[repository|
			executor.submit[
				repository.runImport
			]
		]
		
		executor.shutdown()
		executor.awaitTermination(10, TimeUnit::DAYS)
	}
}