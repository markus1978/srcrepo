package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.internal.FStoreFragmentation
import de.hub.emffrag.mongodb.MongoDBDataStore
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
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl
import java.util.List
import java.util.concurrent.TimeUnit
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.emf.ecore.EObject
import org.json.JSONArray
import org.json.JSONObject

import static extension de.hub.jstattrack.StatisticsUtil.*
import static extension de.hub.srcrepo.RepositoryModelUtil.*
import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

class MetaDataCommand extends AbstractDataCommand {
		
	private var TimeStatistic traverseOneKObjectsExecTimeStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(typeof(Summary)).with(typeof(Histogram)).with(typeof(BatchedPlot)).with(typeof(WindowedPlot)).register(this.class, "Traverse execution time for a million objects");
	
	override protected run(RepositoryModelDirectory directory, RepositoryModel repo) {
		if (repo.importMetaData.statsAsJSON == null) {
			return 			
		}
		
		logOut.println("Aquire data for " + repo.qualifiedName)
		
		val List<String> data = newArrayList
		
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
				«importStatJSON.summaryDatumJSONStr(RepositoryModelTraversal.visitFullETStat, "2_revVisitTime")»,
				«importStatJSON.summaryDatumJSONStr(MoDiscoRepositoryModelImportVisitor.revCheckoutETStat, "3_checkoutTime")»,
				«importStatJSON.summaryDatumJSONStr(MoDiscoRepositoryModelImportVisitor.revRefreshStat, "4_refreshTime")»,
				«importStatJSON.summaryDatumJSONStr(MoDiscoRepositoryModelImportVisitor.revImportTimeStat, "5_importTime")»,
				«importStatJSON.summaryDatumJSONStr(MongoDBDataStore.writeTimeStatistic, "6_writeTime")»,
				«importStatJSON.summaryDatumJSONStr(MoDiscoRepositoryModelImportVisitor.revLOCTimeStat, "7_locTime")»,
				«IF withElementCount»					
					«currentStatDataJSON.summaryDatumJSONStr(traverseOneKObjectsExecTimeStat, "8_traverseTime")»,
					«currentStatDataJSON.summaryDatumJSONStr(FStoreFragmentation.loadETStat, "9_fragLoadET")»,
					«currentStatDataJSON.summaryDatumJSONStr(FStoreFragmentation.unloadETStat, "10_fragUnloadET")»,
					«currentStatDataJSON.summaryDatumJSONStr(MongoDBDataStore.readTimeStatistic, "11_dataReadET")»,
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
				«traverseStatJSON.summaryDatumJSONStr(RepositoryModelTraversal.visitFullETStat, "2_revVisitTime")»,
				«traverseStatJSON.summaryDatumJSONStr(ModiscoIncrementalSnapshotImpl.cusLoadETStat, "3_cusLoadTime")»,
				«traverseStatJSON.summaryDatumJSONStr(MoDiscoRevVisitor.revVisitETStat, "4_revVisitTime")»,
				«currentStatDataJSON.summaryDatumJSONStr(FStoreFragmentation.loadETStat, "5_fragLoadET")»,
				«currentStatDataJSON.summaryDatumJSONStr(FStoreFragmentation.unloadETStat, "6_fragUnloadET")»,
			}
		''').toString
		
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
		
		if (cl.hasOption("h")) {
			val result = jsonData.toHumanReadable
			out.println(result)
		} else {
			printHeader(jsonData.toCSVHeader)
			out.println(jsonData.toCSV(false))
		}		
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
		options.addOption(Option.builder("ds").longOpt("data-set").desc("The data set you wan: import(default),traverse").hasArg.build)
		options.addOption(Option.builder("f").longOpt("filter").desc("Filter for specific stats").hasArg.build)
	}
	
	private def withElementCount() { 
		cl.hasOption("w")
	}
}
