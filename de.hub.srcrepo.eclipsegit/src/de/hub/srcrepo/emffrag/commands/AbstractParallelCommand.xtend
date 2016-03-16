package de.hub.srcrepo.emffrag.commands

import de.hub.emfcompress.EmfCompressActivator
import de.hub.emffrag.EmfFragActivator
import de.hub.jstattrack.JStatTrackActivator
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.text.DateFormat
import java.util.Date
import java.util.Map
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.function.Supplier
import java.util.regex.Pattern
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

abstract class AbstractParallelCommand extends AbstractRepositoryCommand {
	
	protected var PrintStream out = null
		
	val Map<Thread, PrintStream> logOuts = new ConcurrentHashMap
	val logFileNames = newHashSet
	private val Supplier<PrintStream> logOutSupplier = [ getLogOut() ] 
	
	private Map<String,PrintStream> auxOuts = newHashMap
	private Map<PrintStream, Boolean> headerPrinted = newHashMap
	
	private def String metadata(String name, String description) {
		'''
			# name: «IF name!=null»«name»«ELSE»<no-name>«ENDIF»
			# description: «IF description!=null»«description»«ELSE»<no-description>«ENDIF»
			# date/time (at start): «DateFormat.dateTimeInstance.format(new Date())»
			# git info:
			#	JStatTrack: «JStatTrackActivator.commit»;
			#	EMF-Fragments: «EmfFragActivator.commit»;
			#	EMF-Compress: «EmfCompressActivator.commit»;
			#	srcrepo: «SrcRepoActivator.commit»;
		'''
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder().longOpt("name").desc("Name of the run.").hasArg.build)
		options.addOption(Option.builder().longOpt("description").desc("Description of the run.").hasArg.build)
		options.addOption(Option.builder("a").longOpt("append").desc("Append output to existing file.").build)
		options.addOption(Option.builder("o").longOpt("output").desc("File used to store results.").hasArg.build)
		options.addOption(Option.builder().longOpt("log-dir").desc("Directory used to put log files.").hasArg.build)
		options.addOption(Option.builder("p").longOpt("parallel").desc("Number of repositories that can be process in parallel. Default is one.").hasArg.build)
		options.addOption(Option.builder("h").longOpt("human-readable").desc("Create human readable output instead of csv.").build)		
	}
	
	var ExecutorService executor = null
	
	override protected run() {
		headerPrinted.clear
		logFileNames.clear
		logOuts.clear
		
		if (cl.hasOption("o")) {
			val file = new File(cl.getOptionValue("o"))
			file.absoluteFile.parentFile.mkdirs
			val os = new FileOutputStream(file, cl.hasOption("a"))
			out = new PrintStream(os)
		} else {
			out = System.out
		}
		
		val metaDataStr = metadata(cl.getOptionValue("name"), cl.getOptionValue("description"))
		if (!cl.hasOption("a")) {
			out.println(metaDataStr)
		}
		
		SrcRepoActivator.INSTANCE.logOutSupplier = logOutSupplier
		EmfFragActivator.instance.logOutSupplier = logOutSupplier
		
		executor = Executors.newFixedThreadPool(Integer.parseInt(cl.getOptionValue("p")?:"1"))
		
		super.run()
		
		executor.shutdown()
		executor.awaitTermination(2, TimeUnit::DAYS)
		
		if (out != System.out) {		
			out.close			
		}
		for (auxOut:auxOuts.values) {
			if (auxOut != System.out) {
				auxOut.close
			}
		}
		auxOuts.clear
		
		SrcRepoActivator.INSTANCE.logOutSupplier = [System.out]
		EmfFragActivator.instance.logOutSupplier = [System.out]
	}
	
	protected def PrintStream getLogOut() {
		return logOuts.get(Thread.currentThread)?:System.out
	}
	
	override final protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel model) {
		executor.submit[
			val logOut = if (cl.hasOption("log-dir")) {
				var logFileName = model.name
				while (logFileNames.contains(logFileName)) {
					logFileName = logFileName.unique
				}
				logFileNames += logFileName
				logFileName = logFileName + ".log"
				
				val file = new File(cl.getOptionValue("log-dir") + "/" + logFileName)
				file.absoluteFile.parentFile.mkdirs
				new PrintStream(new FileOutputStream(file, false))
			} else {
				System.out
			}	
			
			logOuts.put(Thread.currentThread, logOut)			
			run(directory, model)
					
			if (logOut != System.out) {
				logOut.close
			}	
			void
		]
	}
	
	protected def auxOut(String name) {
		var out = auxOuts.get(name)
		if (out == null) {
			if (cl.hasOption("o")) {
				var fileName = cl.getOptionValue("o")
				val index = fileName.lastIndexOf(".")
				if (index != -1) {
					val ending = fileName.substring(index + 1)
					fileName = fileName.substring(0, index) + "_" + name + "." + ending
				} else {
					fileName = fileName + "_" + name
				}
				val file = new File(fileName)
				file.absoluteFile.parentFile.mkdirs
				val os = new FileOutputStream(file, cl.hasOption("a"))
				out = new PrintStream(os)
			} else {
				out = System.out
			}
			auxOuts.put(name, out)
		}
		return out
	}	
	
	protected def void printHeader(String headerStr) {		
		printHeader(out, headerStr)
	}
	
	protected synchronized def void printHeader(PrintStream out, String headerStr) {		
		if (!cl.hasOption("a")) {
			val printed = headerPrinted.get(out)?:false
			if (!printed) {
				out.println(headerStr)
				headerPrinted.put(out, true)
			}
		}
	}
	
	protected abstract def void run(RepositoryModelDirectory directory, RepositoryModel model)
	
	private def String unique(String str) {
		return str + if (Pattern.matches("[0-9]+^", str)) {
			Integer.parseInt(str.substring(str.length-2)) + 1			
		} else {
			0
		}	
	}
}