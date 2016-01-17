package de.hub.srcrepo.emffrag.commands

import de.hub.jstattrack.Statistics
import de.hub.jstattrack.TimeStatistic
import de.hub.jstattrack.TimeStatistic.Timer
import de.hub.jstattrack.services.BatchedPlot
import de.hub.jstattrack.services.Histogram
import de.hub.jstattrack.services.Summary
import de.hub.jstattrack.services.WindowedPlot
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.MongoDBMetaData
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.io.File
import java.io.PrintWriter
import java.util.List
import java.util.concurrent.TimeUnit
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.emf.ecore.EObject
import org.json.JSONArray

import static extension de.hub.srcrepo.RepositoryModelUtil.*
import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

class ImportDataCommand extends AbstractRepositoryCommand {
	
	private static val traverseObjectsExecTimeStataTitle = "Traverse execution time for a million objects"
	
	private var TimeStatistic traverseOneKObjectsExecTimeStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(typeof(Summary)).with(typeof(Histogram)).with(typeof(BatchedPlot)).with(typeof(WindowedPlot)).register(this.class, traverseObjectsExecTimeStataTitle);
	private var withElementCount = false
	private var CommandLine cl = null
	private var List<String> data = newArrayList

	private def statSummaryData(JSONArray jsonData, String statName, String key) {
		for (stat:jsonData.toIterable) {
			if (stat.getString("name").equals(statName)) {				
				for (service: stat.getJSONArray("services").toIterable) {
					if (service.getString("name").equals("Summary")) {
						return '''
							«FOR dataTuple:service.getJSONArray("data").toIterable SEPARATOR ", "»
								«key»«dataTuple.getString("key").toFirstUpper» : «dataTuple.get("value").toString»
							«ENDFOR»
						'''
					}	
				}
			}
		}
		throw new IllegalArgumentException("Could not find a statistic called " + statName + ".")
	}
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel repo, CommandLine cl) {
		data.clear
		
		if (cl.hasOption("v")) { println("Aquire data for " + repo.qualifiedName) }
		val elementCountResult = if (withElementCount) { repo.countObjects }
		val importStatJSON = new JSONArray(repo.importMetaData.statsAsJSON)
		val dataStatJSON = Statistics.reportToJSON
		data += '''
			{
				1_name : "«repo.qualifiedName»",
				2_revCount : «repo.metaData.revCount»,
				2_cuCount : «repo.metaData.cuCount»,
				2_skippedCuCount : «repo.metaData.data.get("skippedCuCount")»,
				2_revErrorCount : «repo.metaData.revsWithErrors»,
				2_dbEntryCount : «repo.dataStoreMetaData.count»,
				2_dbSize : «(repo.dataStoreMetaData as MongoDBMetaData).storeSize»,
				2_gitSize : «repo.metaData.size»,
				«IF withElementCount»
					2_elementCount : «elementCountResult.get("count")»,
					2_SLOC : «elementCountResult.get("ncss")»,
				«ENDIF»
				«statSummaryData(importStatJSON, "Revision checkout time", "3_checkoutTime")»,
				«statSummaryData(importStatJSON, "Revision refresh time", "4_refreshTime")»,
				«statSummaryData(importStatJSON, "Revision import time", "5_importTime")»,
				«statSummaryData(importStatJSON, "Write execution times", "6_writeTime")»,
				«statSummaryData(importStatJSON, "Revision LOC time", "7_locTime")»,
				«IF withElementCount»					
					«statSummaryData(dataStatJSON, traverseObjectsExecTimeStataTitle, "8_traverseTime")»,
					«statSummaryData(dataStatJSON, "FragLoadET", "9_fragLoadET")»,
					«statSummaryData(dataStatJSON, "FragUnloadET", "10_fragUnloadET")»,
					«statSummaryData(dataStatJSON, "DataReadET", "11_dataReadET")»,
				«ENDIF»
			}
		'''.toString
	}
	
	private def void countFObjects(EObject eObject, (EObject)=>void apply) {
		eObject.eAllContents.forEach(apply)
	}
	
	var long count = 0
	var long ncss = 0
	var Timer timer = null	
		
	private def countObjects(RepositoryModel model) {
		count = 0
		ncss = 0
		timer = traverseOneKObjectsExecTimeStat.timer
		
		countFObjects(model) [	
			count++	
			if ((count +1) % 1000000 == 0) {
				timer.track
				timer = traverseOneKObjectsExecTimeStat.timer
				if (cl.hasOption("v")) { print(".") }
			}
			if (it instanceof JavaCompilationUnitRef) {
				ncss += (it?.getData("LOC-metrics")?.data?.get("ncss") as Integer)?:0
			}
		]
		
		timer.track
		return newHashMap("count" -> count, "ncss"-> ncss)
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("w").longOpt("with-counting").desc("Also counts the number of elements in each repository. Takes a while.").build)
		options.addOption(Option.builder("o").longOpt("output").desc("File name to store the ouput instead of printing it").hasArg.build)
		options.addOption(Option.builder("v").longOpt("verbose").desc("Create additional output. Careful in conjunction with -o").build)
	}
	
	override protected run(CommandLine cl) {
		this.cl = cl
		withElementCount = cl.hasOption("w")
		
		super.run(cl)
		val repositoryDataJSON = '''
			[
				«FOR repository: data SEPARATOR ", "»
					«repository»
				«ENDFOR»
			]
		'''
		val repositoryDataCSV = new JSONArray(repositoryDataJSON).toCSV
		
		if (cl.hasOption("o")) {
			val out = new File(cl.getOptionValue("o"))
			val printer = new PrintWriter(out)
			printer.println(repositoryDataCSV)
			printer.close
		} else {
			if (cl.hasOption("v")) { println('''The results...''') }
			println(repositoryDataCSV)	
		}
	}

}