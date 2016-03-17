package de.hub.srcrepo.emffrag.commands

import de.hub.jstattrack.Statistics
import de.hub.srcrepo.IRepositoryModelVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.compress.CompressionMeasureVisitor
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory

import static extension de.hub.jstattrack.StatisticsUtil.*

class CompressMeasureCommand extends AbstractDataCommand {
	
	private def traverse(RepositoryModel repo, IRepositoryModelVisitor visitor) {
		RepositoryModelTraversal.traverse(repo, visitor)
		visitor.close(repo)
	}
	
	override protected run(RepositoryModelDirectory directory, RepositoryModel repo) {
		repo.traverse(new CompressionMeasureVisitor)
	
		val data = Statistics.reportToJSON.toSummaryData(repo.name, CompressionMeasureVisitor.statNames, cl.hasOption("h")).toArray
			
		if (cl.hasOption("h")) {
			out.println(data.toHumanReadable)
		} else {			
			printHeader(data.toCSVHeader)
			out.println(data.toCSV(false))
		}
	}
}