package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.io.File
import java.io.InputStreamReader
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Scanner
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
class ParallelCommand extends AbstractRepositoryCommand {
	
	var CommandLine cl = null
	var PrintWriter out = null

	private def dateTime() {
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.newInstance)
	}
	
	private def void redirectOutput(Process cmd) {		
		val inStream = cmd.getInputStream();
		new Thread(new Runnable() {
			override def run() {
				val reader = new InputStreamReader(inStream);
				val scan = new Scanner(reader);
				while (scan.hasNextLine()) {					
					val nextLine = scan.nextLine
					if (out == null) {
						System.out.println(nextLine)						
					} else {
						out.println(nextLine)
					}
				}
			}
		}).start();
	}
	
	private def void runImport(RepositoryModel repository) {
		var result = 0
		try {
			val cmd = '''java -jar 01-software/srcrepo.jar «cl.getOptionValue("c")» -d «(repository.eContainer as RepositoryModelDirectory).name» -r «repository.name»'''
			println('''«dateTime»: Start command: «cmd»''')		
			val process = Runtime.runtime.exec(cmd)
			redirectOutput(process)
			if (!process.waitFor(12, TimeUnit.HOURS)) {
				println('''«dateTime»: Need to abort the command for «repository.qualifiedName» because of timeout.''')
				process.destroy
				result = -1
			} else {
				result = process.exitValue				
			}
		} catch (Exception e) {
			println('''«dateTime»: Could not finish the command for «repository.qualifiedName» with «result», because «e.toString + ":" + e.message».''')
			e.printStackTrace(System.out)
			return
		} 
		System.out.println('''«dateTime»: Finished the command for «repository.qualifiedName» with «result».''')
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder.longOpt("instances").desc("The number of parallel instances to run. Default is 5").hasArg.build)
		options.addOption(Option.builder("c").longOpt("command").desc("The the command to run, with all its parameters.").hasArg.required.build)
		options.addOption(Option.builder("o").longOpt("output").desc("File to redirect stdout of commands to.").hasArg.build)
	}
	
	var ExecutorService executor = null
	
	override protected run(CommandLine cl) {	
		this.cl = cl				
		
		if (cl.hasOption("o")) {
			val file = new File(cl.getOptionValue("o"))
			out = new PrintWriter(file)
		}
		
		val importerCount = Integer.parseInt(cl.getOptionValue("instances", "5"))
		executor = Executors::newFixedThreadPool(importerCount)
		
		super.run(cl)
		
		executor.shutdown()
		executor.awaitTermination(10, TimeUnit::DAYS)
		
		if (out != null) {
			out.close
		}
	}
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel model, CommandLine cl) {
		executor.submit[
			model.runImport
		]
	}
	
}