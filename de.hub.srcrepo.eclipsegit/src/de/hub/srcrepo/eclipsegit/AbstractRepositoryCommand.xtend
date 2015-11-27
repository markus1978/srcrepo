package de.hub.srcrepo.eclipsegit

import org.apache.commons.cli.Options
import org.apache.commons.cli.Option
import org.apache.commons.cli.CommandLine
import java.util.regex.Pattern
import de.hub.srcrepo.repositorymodel.RepositoryModel

abstract class AbstractRepositoryCommand extends AbstractSrcRepoCommand {
	
	abstract protected def void runOnRepository(RepositoryModel it, CommandLine cl)
	
	override protected run(CommandLine cl) {
		val Pattern pattern = try {
			Pattern.compile(cl.getOptionValue("p"))
		} catch (Exception e) {
			System.out.println("Could not parse your pattern: " + e.message)
			System.exit(1)
			null
		}

		if (cl.hasOption("r")) {
			directory.subDirectories.forEach[it.repositories.filter[pattern.asPredicate.test(it.name)].forEach[it.runOnRepository(cl)]]	
		} else {
			directory.subDirectories.filter[pattern.asPredicate.test(it.name)].forEach[repositories.forEach[it.runOnRepository(cl)]]
		}
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("p").longOpt("pattern")
			.desc("Pattern that each potential repository or directory name is matched against.")
			.required.hasArg.build
		)
		options.addOption(Option.builder("r").longOpt("repositories").desc("Match repositories and not directories.").build)		
	}
}