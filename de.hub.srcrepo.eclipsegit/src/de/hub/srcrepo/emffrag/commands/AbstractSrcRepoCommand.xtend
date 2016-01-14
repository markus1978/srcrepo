package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.Fragmentation
import de.hub.emffrag.FragmentationSet
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.emffrag.EmffragSrcRepo
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import java.util.Map
import java.util.Scanner
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.emf.common.util.URI
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage

abstract class AbstractSrcRepoCommand {

	static val defaultModelURI = "mongodb://localhost/EclipseAtGitHub"
	static val defaultCacheSize = 50

	protected var FragmentationSet fs
	protected var Fragmentation fragmentation
	protected var RepositoryModelDirectory directory
	protected var URI modelURI
	
	protected final def void init(CommandLine cl) {
		EmfFragActivator::standalone(RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE)
		EmfFragActivator::instance.logInStandAlone = false;
		EmfFragMongoDBActivator::standalone()
		SrcRepoActivator::standalone

		this.modelURI = URI.createURI(if (cl.hasOption("m")) cl.getOptionValue("m") else defaultModelURI)
		fs = new FragmentationSet(EmffragSrcRepo.packages, [uri|DataStoreImpl::createDataStore(uri)], if (cl.hasOption("cache")) Integer.parseInt(cl.getOptionValue("cache")) else defaultCacheSize)		
		fragmentation = fs.getFragmentation(this.modelURI)		
		val content = fragmentation.root
		if (content instanceof RepositoryModelDirectory) {
			directory = content as RepositoryModelDirectory		
		} else {
			directory = null
		}
	} 
		
	protected def addOptions(Options options) {
		options.addOption(Option.builder().longOpt("log").desc("Prints log output.").build)
		options.addOption(Option.builder("m").desc('''Uses the given model URI. Default is «defaultModelURI».''').longOpt("model").hasArg.build)
		options.addOption(Option.builder().desc('''Uses the given fragmentation cache size. Default is «defaultCacheSize».''').longOpt("cache").hasArg.build)
	}
	
	protected def checkIntArg(CommandLine cl, String name, int minimum) {
		if (cl.hasOption(name)) {
			try {
				return Integer.parseInt(cl.getOptionValue(name)) > minimum
			} catch (NumberFormatException e) {
				return false
			}			
		}
		return true
	}
	
	protected def validateOptions(CommandLine cl) {
		return checkIntArg(cl, "cache", 0)
	}
				
	protected abstract def void run(CommandLine cl)
		
	protected def void after() {
		fs.close()
	}
}

public class SrcRepo {
		
	public static def void main(String[] argsVal) {
		SrcRepoActivator.standalone
		
		val Map<String, AbstractSrcRepoCommand> commands = newHashMap(
			"derive" -> new UpdateDirectoryDerivedFeaturesCommand,
			"schedule" -> new ScheduleImportCommand,
			"import" -> new ImportCommand,
			"data" -> new ImportDataCommand,
			"eclipse" -> new CreateEclipseRepositoriesCommand,
			"github" -> new CreateGitHubUserRepositoriesCommand,
			"ls" -> new ListCommand,
			"rm" -> new RemoveCommand,
			"par" -> new ParallelCommand
		)
		
		var args = argsVal
		var continue = true
		if (args.length < 1) {
			System.out.println("Interactive mode. Enter your command and options ...")
		}
		
		while (continue) {
			args = if (args == null || args.length < 1) {	
				print(">> ")	
				val input = new Scanner(System.in).nextLine.trim
				input.split(" ")	
			} else {
				continue = false
				args
			}
			
			if (args.length < 1) {
				System.out.println("No command given. Usage: srcrepo <command> [options...]")
				System.exit(1)
			}
			
			val commandName = args.get(0)
			val arguments = if (args.length > 1) args.subList(1, args.length) else newArrayList
			
			val command = commands.get(commandName)
			if (command == null) {
				if (commandName == "exit") {
					continue = false
				} else {
					System.out.println("There is no command " + commandName)					
				}			
			} else {
				val options = new Options
				command.addOptions(options)
				val clParser = new DefaultParser
				val cl = try {
					clParser.parse(options, arguments)
				} catch (Exception e) {
					println("Could not parse arguments: " + e.message)
					println("Ussage: srcrepo <command> [options...]")
					null
				}
				val clIsValid = cl != null && command.validateOptions(cl)
				if (clIsValid) {
					try {
						command.init(cl)
						command.run(cl)
						command.after
					} catch (Exception e) {
						println("Could not execute command due to exception: " + e.message)
						e.printStackTrace(System.out)
					}	
				} else {
					new HelpFormatter().printHelp('''srcrepo «commandName» [options...]''', options);		
				}				
			}
			
			args = null
		}	
	}
}