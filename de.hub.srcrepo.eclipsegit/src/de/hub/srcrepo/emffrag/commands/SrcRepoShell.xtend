package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.SrcRepoActivator
import java.util.Map
import jline.console.ConsoleReader
import jline.console.completer.StringsCompleter
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options

class SrcRepoShell {
	
	private val Map<String, AbstractSrcRepoCommand> commands = newHashMap(
			"derive" -> new UpdateDirectoryDerivedFeaturesCommand,
			"schedule" -> new ScheduleImportCommand,
			"import" -> new ImportCommand,
			"data" -> new MetaDataCommand,
			"eclipse" -> new CreateEclipseRepositoriesCommand,
			"github" -> new CreateGitHubUserRepositoriesCommand,
			"ls" -> new ListCommand,
			"rm" -> new RemoveCommand,
			"par" -> new ParallelCommand,
			"metrics" -> new MetricsCommand,
			"drop" -> new DropCommand,
			"compress" -> new CompressMeasureCommand
		)
	
	
	private def execute(String[] args) {
		val commandName = args.get(0)
		val arguments = if(args.length > 1) args.subList(1, args.length) else newArrayList

		val command = commands.get(commandName)
		if (command == null) {
			if (commandName == "exit") {
				return false
			} else {
				println("There is no command " + commandName)
			}
		} else {
			val options = new Options
			command.addOptions(options)
			val clParser = new DefaultParser
			val cl = try {
				clParser.parse(options, arguments)
			} catch (Exception e) {
				println("Could not parse arguments: " + e.message)
				println("Usage: srcrepo <command> [options...]")
				null
			}
			command.cl = cl
			val clIsValid = cl != null && command.validateOptions()
			if (clIsValid) {
				try {
					command.init()
					command.run()
					command.after
				} catch (Exception e) {
					println("Could not execute command due to exception: " + e.message)
					e.printStackTrace(System.out)
				}
			} else {
				new HelpFormatter().printHelp('''srcrepo «commandName» [options...]''', options);
			}
		}
		
		return true
	}
	
	public static def void main(String[] appArgs) throws Exception {
		SrcRepoActivator.standalone
		val shell = new SrcRepoShell
		
		if (appArgs.length >= 1) {
			// just run the command
			shell.execute(appArgs)
		} else {
			// run as shell			
            val reader = new ConsoleReader();
            reader.historyEnabled = true
            reader.setPrompt(">> ")
            reader.addCompleter(new StringsCompleter(shell.commands.keySet))
            var String line = null

			println("Interactive mode. Enter your command and options ...")
            while ((line = reader.readLine()) != null) {
				val args = line.split(" ")
				if (!shell.execute(args)) {
					System.exit(0)
				}
				reader.output.flush
			}
        }
	}
}