package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.fragmentation.Fragmentation
import de.hub.emffrag.fragmentation.FragmentationSet
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import de.hub.srcrepo.SrcRepoActivator
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

	static val defaultModelURI = "mongodb://localhost/git.eclipse.org"
	static val defaultCacheSize = 50

	protected var FragmentationSet fs
	protected var Fragmentation fragmentation
	protected var RepositoryModelDirectory directory
	protected var URI modelURI
	
	protected final def void init(CommandLine cl) {
		EmfFragActivator::standalone(RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE)
		EmfFragActivator::instance.logInStandAlone = cl.hasOption("v")
		EmfFragMongoDBActivator::standalone()
		SrcRepoActivator::standalone

		this.modelURI = URI.createURI(if (cl.hasOption("m")) cl.getOptionValue("m") else defaultModelURI)
		fs = new FragmentationSet(if (cl.hasOption("c")) Integer.parseInt(cl.getOptionValue("c")) else defaultCacheSize, [uri|DataStoreImpl::createDataStore(uri)])		
		fragmentation = fs.getFragmentation(this.modelURI)		
		val contents = fragmentation.rootFragment.contents
		val content = if (contents.size == 0) null else contents.get(0)
		if (content instanceof RepositoryModelDirectory) {
			directory = content as RepositoryModelDirectory		
		} else {
			directory = null
		}
	}
		
	protected def addOptions(Options options) {
		options.addOption(Option.builder("v").desc("Prints log output.").longOpt("--verbose").build)
		options.addOption(Option.builder("m").desc('''Uses the given model URI. Default is «defaultModelURI».''').longOpt("--model").hasArg.build)
		options.addOption(Option.builder("c").desc('''Uses the given fragmentation cache size. Default is «defaultCacheSize».''').longOpt("--cache").hasArg.build)
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
		return checkIntArg(cl, "c", 0)
	}
				
	protected abstract def void run(CommandLine cl)
		
	protected def void after() {
		fs.close()
	}
}

public class SrcRepo {
	private static val Map<String, AbstractSrcRepoCommand> commands = newHashMap(
		"update-derived" -> new UpdateDirectoryDerivedFeatures,
		"schedule-imports" -> new ScheduleImports,
		"import" -> new SrcRepoDirectoryImportScript,
		"data" -> new ImportDataExtractor,
		"eclipse" -> new EclipseGitMegaModel,
		"ls" -> new LSCommand,
		"rm" -> new RMCommand
	)
	
	public static def void main(String[] argsVal) {
		var args = argsVal
		args = if (args.length < 1) {
			System.out.println("Interactive mode. Enter your command and options ...")
			val input = new Scanner(System.in).nextLine.trim
			input.split(" ")	
		} else {
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
			System.out.println("There is no command " + commandName)
			System.exit(1)
		}
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
			command.init(cl)
			command.run(cl)
			command.after	
			System.out.println("Finished command execution.")	
		} else {
			new HelpFormatter().printHelp('''srcrepo «commandName» [options...]''', options);
			System.exit(1)			
		}	
	}
}