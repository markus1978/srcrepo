package de.hub.srcrepo.emffrag.commands

import de.hub.jstattrack.Statistics
import de.hub.srcrepo.IRepositoryModelVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.compress.CompressionMeasureVisitor
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

import static extension de.hub.jstattrack.StatisticsUtil.*
import de.hub.srcrepo.compress.EmfComparenMeasureVisitor
import org.json.JSONArray
import org.json.JSONObject

class CompressMeasureCommand extends AbstractDataCommand {
	
	private def traverse(RepositoryModel repo, IRepositoryModelVisitor visitor) {
		val traversal = new RepositoryModelTraversal(repo, visitor)
		if (cl.hasOption("abort")) {
			traversal.abort = Integer.parseInt(cl.getOptionValue("abort"))	
		}
		traversal.run
		visitor.close(repo)
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder().longOpt("emf").desc("Use EMF compare instead our own.").build)
		options.addOption(Option.builder().longOpt("abort").desc("Number of revisions after which measure should be aborted.").hasArg.build)
	}
	
	override protected run(RepositoryModelDirectory directory, RepositoryModel repo) {
		val visitor = if (cl.hasOption("emf")) {
			new EmfComparenMeasureVisitor
		} else {
			new CompressionMeasureVisitor
		}
		
		repo.traverse(visitor)
				
		val data = Statistics.reportToJSON.toSummaryData(repo.name, CompressionMeasureVisitor.statNames, cl.hasOption("h")).toArray
			
		if (cl.hasOption("h")) {
			out.println(data.toHumanReadable)
		} else {			
			printHeader(data.toCSVHeader)
			out.println(data.toCSV(false))
		}
		
		
		val timePerCountData = new JSONArray()
		switch(visitor) {
			EmfComparenMeasureVisitor: {
				for(datum:visitor.timePerCount) {
					val object = new JSONObject
					object.put("count", datum.key)
					object.put("time", datum.value)
					timePerCountData.put(object)
				}		
			}
			CompressionMeasureVisitor: {
				for(pair:visitor.timePerCount.entrySet) {
					for(datum:pair.value) {
						val object = new JSONObject
						object.put("count", datum.key)
						object.put("time", datum.value)
						object.put("algorithm", pair.key)
						timePerCountData.put(object)
					}					
				}
			}
		}
		
		auxOut('''«repo.name»-data''').println(timePerCountData.toCSV)		
	}
}