package de.hub.srcrepo.emffrag.commands

import com.google.common.collect.AbstractIterator
import com.google.common.collect.FluentIterable
import de.hub.jstattrack.TimeStatistic
import de.hub.jstattrack.services.BatchedPlot
import de.hub.jstattrack.services.Histogram
import de.hub.jstattrack.services.Summary
import de.hub.jstattrack.services.WindowedPlot
import de.hub.srcrepo.repositorymodel.MongoDBMetaData
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.io.File
import java.io.PrintWriter
import java.util.concurrent.TimeUnit
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.json.JSONArray
import org.json.JSONObject

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*
import java.util.List
import static extension de.hub.srcrepo.RepositoryModelUtil.*
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef

class ImportDataCommand extends AbstractRepositoryCommand {
	
	private var TimeStatistic traverseOneKObjectsExecTimeStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(typeof(Summary)).with(typeof(Histogram)).with(typeof(BatchedPlot)).with(typeof(WindowedPlot)).register(this.class, "Traverse execution time for 1k objects");
	private var withElementCount = false
	private var CommandLine cl = null
	private var List<String> data = newArrayList
	
	private def toIterable(JSONArray jsonArray) {
		return new FluentIterable<JSONObject> {			
			override iterator() {
				new AbstractIterator<JSONObject>() {
					var index = 0				
					override protected computeNext() {
						if (jsonArray.length > index) {
							return jsonArray.getJSONObject(index++)
						} else {
							endOfData
							return null
						}
					}					
				}
			}			
		}
	}

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
		if (cl.hasOption("v")) { println("Aquire data for " + repo.qualifiedName) }
		val elementCountResult = if (withElementCount) { repo.countObjects }
		val statJSON = new JSONArray(repo.importMetaData.statsAsJSON)
		data += '''
			{
				1_name : "«repo.qualifiedName»",
				2_revCount : «repo.metaData.revCount»,
				2_cuCount : «repo.metaData.cuCount»,
				2_revErrorCount : «repo.metaData.revsWithErrors»,
				2_dbEntryCount : «repo.dataStoreMetaData.count»,
				2_dbSize : «(repo.dataStoreMetaData as MongoDBMetaData).storeSize»,
				«IF withElementCount»
					2_elementCount : «elementCountResult.get("count")»,
					2_SLOC : «elementCountResult.get("ncss")»,
				«ENDIF»
				«statSummaryData(statJSON, "Revision checkout time", "3_checkoutTime")»,
				«statSummaryData(statJSON, "Revision refresh time", "4_refreshTime")»,
				«statSummaryData(statJSON, "Revision import time", "5_importTime")»,
				«statSummaryData(statJSON, "Write execution times", "6_writeTime")»,
				«statSummaryData(statJSON, "Revision LOC time", "7_locTime")»,
				«IF withElementCount»
					8_traverseTime : «elementCountResult.get("time")»
				«ENDIF»
			}
		'''.toString
	}
	
	private def toCSV(JSONArray json) {
		if (json.length > 0) {
			val keys = json.getJSONObject(0).keySet.toList.sort
			return '''
				«FOR key:keys SEPARATOR ", "»«key»«ENDFOR»
				«FOR entry:json.toIterable»
					«FOR key:keys SEPARATOR ", "»«entry.get(key).toString»«ENDFOR»
				«ENDFOR»
			'''
		} else {
			return ''''''
		}
	}
		
	private def countObjects(RepositoryModel model) {	
		val startTime = System.currentTimeMillis
		var long count = 0
		var long ncss = 0
		val i = model.eAllContents
		var timer = traverseOneKObjectsExecTimeStat.timer
		while (i.hasNext) {
			val next = i.next
			count++
			if ((count +1) % 1000 == 0) {
				timer.track
				timer = traverseOneKObjectsExecTimeStat.timer
			}
			if ((count +1) % 100000 == 0) {
				if (cl.hasOption("v")) { print(".") }
			}
			if (next instanceof JavaCompilationUnitRef) {
				ncss += (next?.getData("LOC-metrics")?.data?.get("ncss") as Integer)?:0
			}
		}
		val time = System.currentTimeMillis - startTime
		timer.track
		return newHashMap("time" -> time, "count" -> count, "ncss"-> ncss)
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