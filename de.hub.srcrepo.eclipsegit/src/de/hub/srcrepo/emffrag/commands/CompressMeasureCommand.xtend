package de.hub.srcrepo.emffrag.commands

import de.hub.jstattrack.Statistics
import de.hub.srcrepo.IRepositoryModelVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.compress.CompressionMeasureVisitor
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.io.File
import java.io.PrintWriter
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

import static extension de.hub.jstattrack.StatisticsUtil.*

class CompressMeasureCommand extends AbstractRepositoryCommand {
	
	private def traverse(RepositoryModel repo, IRepositoryModelVisitor visitor) {
		RepositoryModelTraversal.traverse(repo, visitor)
		visitor.close(repo)
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)		
		options.addOption(Option.builder("o").longOpt("output").desc("File name to store the ouput instead of printing it").hasArg.build)
	}
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel repo, CommandLine cl) {
		repo.traverse(new CompressionMeasureVisitor)
	
		val data = Statistics.reportToJSON.toSummaryData(CompressionMeasureVisitor.statNames).toArray
				
		if (cl.hasOption("o")) {
			val out = new File(cl.getOptionValue("o"))
			val printer = new PrintWriter(out)
			printer.println(data.toCSV)
			printer.close
		} else {
			if (cl.hasOption("v")) { println('''The results...''') }				
		}
		println(data.toHumanReadable)		
	}
}