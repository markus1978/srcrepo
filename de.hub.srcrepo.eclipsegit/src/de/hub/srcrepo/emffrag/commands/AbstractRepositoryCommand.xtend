package de.hub.srcrepo.emffrag.commands

import org.apache.commons.cli.Options
import org.apache.commons.cli.Option
import org.apache.commons.cli.CommandLine
import java.util.regex.Pattern
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import static extension de.hub.srcrepo.ocl.OclExtensions.*
import static extension de.hub.srcrepo.RepositoryModelUtil.*

abstract class AbstractRepositoryCommand extends AbstractSrcRepoCommand {
	
	abstract protected def void runOnRepository(RepositoryModelDirectory directory, RepositoryModel model, CommandLine cl)
	
	private def patternForOption(CommandLine cl, String optionName) {
		if (cl.hasOption(optionName)) {
			try {
				Pattern.compile(cl.getOptionValue(optionName))
			} catch (Exception e) {
				System.out.println("Could not parse your pattern: " + e.message)
				System.exit(1)
				null
			}
		} else {
			null
		}
	}
	
	override protected run(CommandLine cl) {
		val repositoryPattern = cl.patternForOption("r")
		val directoryPattern = cl.patternForOption("d")
		
		if (directory == null) {
			println("There is no directory to list repos from.")
			return
		}
		
		val directories = newArrayList(directory)
		directories.addAll(directory.subDirectories.filter[if (directoryPattern != null) directoryPattern.asPredicate.test(it.name) else true])
		val repositories = directories
			.collectAll[subdirectory|
				subdirectory.repositories.filter[
					if (repositoryPattern != null)
						if (it.name != null) 
							repositoryPattern.asPredicate.test(it.name)
						else 
							false 
					else true
				].map[model| subdirectory->model]
			].toList
			
		repositories.forEach[entry|
			val imd = entry.value.importMetaData
			val goodStatus = (imd.imported || !cl.hasOption("i")) &&
				(imd.scheduled || !cl.hasOption("s")) &&
				((!imd.imported && !imd.scheduled && !imd.importing) || !cl.hasOption("x"))
			if (goodStatus) {
				runOnRepository(entry.key, entry.value, cl)
			}
		]			
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("r").longOpt("repositories").desc("Matches repositories.").hasArg.build)
		options.addOption(Option.builder("d").longOpt("directories").desc("Matches repositories.").hasArg.build)
		options.addOption(Option.builder("i").longOpt("imported").desc("Matches imported only.").build)
		options.addOption(Option.builder("s").longOpt("scheduled").desc("Matches scheduled for import only.").build)
		options.addOption(Option.builder("x").longOpt("listed-only").desc("Matches listed only repositories only.").build)				
	}
}