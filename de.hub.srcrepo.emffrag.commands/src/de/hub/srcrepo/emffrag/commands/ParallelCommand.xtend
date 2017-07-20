package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*
import java.util.Stack

class ParallelCommand extends AbstractRepositoryCommand {
	
	private val ports = new Stack<String>

	private def void runInstance(RepositoryModel repository) {
		var result = 0
		val port = ports.pop
		try {
			val cmd = '''java -DJStatTrackPort=«port» «IF cl.hasOption("heap")»-Xmx«cl.getOptionValue("heap")»m «ENDIF»-jar 01-software/srcrepo.jar «cl.getOptionValue("c")» -d «(repository.eContainer as RepositoryModelDirectory).name» -r ^«repository.name»$'''
			
			SrcRepoActivator.INSTANCE.info('''Start command: «cmd»''')		
			val process = Runtime.runtime.exec(cmd)
			if (!process.waitFor(24, TimeUnit.HOURS)) {
				SrcRepoActivator.INSTANCE.error('''Need to abort the command for «repository.qualifiedName» because of timeout.''')
				process.destroy
				result = -1
			} else {
				result = process.exitValue				
			}
		} catch (Exception e) {
			SrcRepoActivator.INSTANCE.error('''Could not finish the command for «repository.qualifiedName» with «result», because «e.toString + ":" + e.message».''')
			e.printStackTrace(System.out)
			return
		} finally {
			ports.push(port)
		}
		
		SrcRepoActivator.INSTANCE.info('''Finished the command for «repository.qualifiedName» with «result».''')
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("p").longOpt("parallel-instance").desc("The number of parallel instances to run. Default is 1.").hasArg.build)
		options.addOption(Option.builder("c").longOpt("command").desc("The the command to run, with all its parameters.").hasArg.required.build)
		options.addOption(Option.builder().longOpt("heap").desc("Max heap mem in MB.").hasArg.build)
	}
	
	var ExecutorService executor = null
	
	override protected run() {	
		this.cl = cl				
		
		val importerCount = Integer.parseInt(cl.getOptionValue("instances", "5"))
		executor = Executors::newFixedThreadPool(importerCount)
		for (i:8001..<(8001+importerCount)) {
			ports.push(Integer.toString(i))
		}
		
		super.run()
		
		executor.shutdown()
		executor.awaitTermination(10, TimeUnit::DAYS)
	}
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel model) {
		executor.submit[model.runInstance]
	}
	
}