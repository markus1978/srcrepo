package de.hub.srcrepo.emffrag.commands

import com.google.common.collect.AbstractIterator
import com.google.common.collect.FluentIterable
import de.hub.srcrepo.repositorymodel.MongoDBMetaData
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils
import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*
import org.apache.commons.cli.CommandLine
import org.json.JSONArray
import org.json.JSONObject
import org.apache.commons.cli.Options
import org.apache.commons.cli.Option

class ImportDataExtractor extends AbstractSrcRepoCommand {
	
	private var withElementCount = false
	
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
	
	private def aquireData(RepositoryModel repo) {
		println("Aquire data for " + repo.qualifiedName)
		val statJSON = new JSONArray(repo.metaData.importMetaData.importStatsAsJSON)
		'''
			{
				1_name : "«repo.qualifiedName»",
				2_revCount : «repo.metaData.revCount»,
				2_cuCount : «repo.metaData.cuCount»,
				2_revErrorCount : «repo.metaData.revsWithErrors»,
				2_dbEntryCount : «repo.metaData.dataStoreMetaData.count»,
				2_dbSize : «(repo.metaData.dataStoreMetaData as MongoDBMetaData).storeSize»,
				«IF withElementCount»
					2_elementCount : «repo.countObjects»,
				«ENDIF»
				«statSummaryData(statJSON, "Revision checkout time", "3_checkoutTime")»,
				«statSummaryData(statJSON, "Revision refresh time", "4_refreshTime")»,
				«statSummaryData(statJSON, "Revision import time", "5_importTime")»,
				«statSummaryData(statJSON, "Write execution times", "6_writeTime")»,
			}
		'''
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
		var long count = 0
		val i = model.eAllContents
		while (i.hasNext) {
			i.next
			count++
			if ((count +1) % 100000 == 0) {
				print(".")
			}
		}
		return count
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("w").longOpt("with-counting").desc("Also counts the number of elements in each repository. Takes a while.").build)
	}
	
	override protected run(CommandLine cl) {
		withElementCount = cl.hasOption("w")
		val importedRepositories = RepositoryModelUtils.imported(directory)
		System.out.println('''Found «importedRepositories.size» impored repositories.''')
		val repositoryDataJSON = '''
			[
				«FOR repository: importedRepositories SEPARATOR ", "»
					«repository.aquireData»
				«ENDFOR»
			]
		'''
		val repositoryDataCSV = new JSONArray(repositoryDataJSON).toCSV
		
		System.out.println("The results ...")
		System.out.println(repositoryDataCSV)
	}	
}