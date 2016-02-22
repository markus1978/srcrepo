package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.internal.FStoreFragmentation
import de.hub.emffrag.mongodb.MongoDBDataStore
import de.hub.jstattrack.AbstractStatistic
import de.hub.jstattrack.Statistics
import de.hub.jstattrack.TimeStatistic
import de.hub.jstattrack.TimeStatistic.Timer
import de.hub.jstattrack.services.BatchedPlot
import de.hub.jstattrack.services.Histogram
import de.hub.jstattrack.services.Summary
import de.hub.jstattrack.services.WindowedPlot
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor
import de.hub.srcrepo.MoDiscoRevVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.RevVisitor
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
import org.json.JSONObject

import static extension de.hub.srcrepo.RepositoryModelUtil.*
import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

class MetaDataCommand extends AbstractRepositoryCommand {
		
	private var TimeStatistic traverseOneKObjectsExecTimeStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(typeof(Summary)).with(typeof(Histogram)).with(typeof(BatchedPlot)).with(typeof(WindowedPlot)).register(this.class, "Traverse execution time for a million objects");
	private var withElementCount = false
	private var CommandLine cl = null
	private var List<String> data = newArrayList

	private def statSummaryData(JSONArray jsonData, AbstractStatistic statDef, String key) {	
		val serviceData = Statistics.getStatServiceDataFromJSONReport(statDef.id, Summary.serviceName, jsonData)
		if (serviceData == null) {
			throw new IllegalArgumentException("Could not find a statistic called " + statDef.id + ".")
		} else {
			return '''
				«FOR dataTuple:serviceData.toIterable SEPARATOR ", "»
					«key»«dataTuple.getString("key").toFirstUpper» : «dataTuple.get("value").toString»
				«ENDFOR»
			'''
		}
	}
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel repo, CommandLine cl) {
		if (cl.hasOption("v")) { println("Aquire data for " + repo.qualifiedName) }
		val elementCountResult = if (withElementCount) { repo.countObjects }
		val importStatJSON = new JSONArray(repo.importMetaData.statsAsJSON)
		val traverseMetaDataSet = repo.getData(RevVisitor.traverseMetaData)
		val traverseStatJSON = if (traverseMetaDataSet?.jsonData != null) new JSONArray(traverseMetaDataSet.jsonData)
		val currentStatDataJSON = Statistics.reportToJSON
		
		data += (if (cl.getOptionValue("ds", "import")=="import") '''
			{
				1_name : "«repo.qualifiedName»",
				1_revCount : «repo.metaData.revCount»,
				1_cuCount : «repo.metaData.cuCount»,
				1_skippedCuCount : «repo.metaData.data.get("skippedCuCount")»,
				1_revErrorCount : «repo.metaData.revsWithErrors»,
				1_dbEntryCount : «repo.dataStoreMetaData.count»,
				1_dbSize : «(repo.dataStoreMetaData as MongoDBMetaData).storeSize»,
				1_gitSize : «repo.metaData.size»,
				«IF withElementCount»
					1_elementCount : «elementCountResult.get("count")»,
					1_SLOC : «elementCountResult.get("ncss")»,
				«ENDIF»
				«statSummaryData(importStatJSON, RepositoryModelTraversal.visitFullETStat, "2_revVisitTime")»,
				«statSummaryData(importStatJSON, MoDiscoRepositoryModelImportVisitor.revCheckoutETStat, "3_checkoutTime")»,
				«statSummaryData(importStatJSON, MoDiscoRepositoryModelImportVisitor.revRefreshStat, "4_refreshTime")»,
				«statSummaryData(importStatJSON, MoDiscoRepositoryModelImportVisitor.revImportTimeStat, "5_importTime")»,
				«statSummaryData(importStatJSON, MongoDBDataStore.writeTimeStatistic, "6_writeTime")»,
				«statSummaryData(importStatJSON, MoDiscoRepositoryModelImportVisitor.revLOCTimeStat, "7_locTime")»,
				«IF withElementCount»					
					«statSummaryData(currentStatDataJSON, traverseOneKObjectsExecTimeStat, "8_traverseTime")»,
					«statSummaryData(currentStatDataJSON, FStoreFragmentation.loadETStat, "9_fragLoadET")»,
					«statSummaryData(currentStatDataJSON, FStoreFragmentation.unloadETStat, "10_fragUnloadET")»,
					«statSummaryData(currentStatDataJSON, MongoDBDataStore.readTimeStatistic, "11_dataReadET")»,
				«ENDIF»
			}
		''' else '''
			{
				1_name : "«repo.qualifiedName»",
				1_revCount : «repo.metaData.revCount»,
				1_cuCount : «repo.metaData.cuCount»,
				1_dbEntryCount : «repo.dataStoreMetaData.count»,
				1_dbSize : «(repo.dataStoreMetaData as MongoDBMetaData).storeSize»,
				1_gitSize : «repo.metaData.size»,				
				«statSummaryData(traverseStatJSON, RepositoryModelTraversal.visitFullETStat, "2_revVisitTime")»,
				«statSummaryData(traverseStatJSON, MoDiscoRevVisitor.cusLoadETStat, "3_cusLoadTime")»,
				«statSummaryData(traverseStatJSON, MoDiscoRevVisitor.revVisitETStat, "4_revVisitTime")»,
				«statSummaryData(currentStatDataJSON, FStoreFragmentation.loadETStat, "5_fragLoadET")»,
				«statSummaryData(currentStatDataJSON, FStoreFragmentation.unloadETStat, "6_fragUnloadET")»,
			}
		''').toString
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
		options.addOption(Option.builder("h").longOpt("human-readable").desc("Create human readable output instead of csv.").build)
		options.addOption(Option.builder("ds").longOpt("data-set").desc("The data set you wan: import(default),traverse").hasArg.build)
		options.addOption(Option.builder("f").longOpt("filter").desc("Filter for specific stats").hasArg.build)
	}
	
	override protected run(CommandLine cl) {
		data.clear
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
		
		val jsonData = new JSONArray(repositoryDataJSON)
		if (cl.hasOption("f")) {
			val filter = cl.getOptionValue("f")
			jsonData.map[it as JSONObject].forEach[obj|
				obj.keys.filter[!it.startsWith(filter + "_")].toList.forEach[obj.remove(it)]
			]		
		}
		val repositoryDataCSV = if (cl.hasOption("h")) jsonData.toHumanReadable else jsonData.toCSV
		
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