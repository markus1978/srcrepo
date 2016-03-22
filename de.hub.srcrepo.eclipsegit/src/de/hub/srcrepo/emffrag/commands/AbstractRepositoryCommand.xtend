package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.util.regex.Pattern
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

import static extension de.hub.srcrepo.RepositoryModelUtil.*
import static extension de.hub.srcrepo.ocl.OclExtensions.*

abstract class AbstractRepositoryCommand extends AbstractSrcRepoCommand {
	
	abstract protected def void runOnRepository(RepositoryModelDirectory directory, RepositoryModel model)
	
	private def patternForOption(String optionName) {
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
	
	override protected run() {
		val repositoryPattern = patternForOption("r")
		val directoryPattern = patternForOption("d")
		
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
			
		if (cl.hasOption("largest")) {
			repositories.sort[one,two|
				two.value.metaData.size.compareTo(one.value.metaData.size)
			]
			val count = Integer.parseInt(cl.getOptionValue("largest"))
			while(repositories.size > count) {
				repositories.remove(repositories.size - 1)	
			}
		}
		
		repositories.forEach[entry|
			val imd = entry.value.importMetaData
			val goodStatus = (imd.imported || !cl.hasOption("i")) &&
				(imd.scheduled || !cl.hasOption("s")) &&
				((!imd.imported && !imd.scheduled && !imd.importing) || !cl.hasOption("x"))
			if (goodStatus) {
				runOnRepository(entry.key, entry.value)
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
		options.addOption(Option.builder().longOpt("largest").desc("Schedules the largest X repos that are not already installed or scheduled.").hasArg.build)				
	}
}