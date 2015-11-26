package de.hub.srcrepo.repositorymodel.util

import de.hub.srcrepo.repositorymodel.RepositoryModel
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.List
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

/**
 * Program that executes a headless {@link SrcRepoDirectoryImport} 
 * for each scheduled repository. Only executes a certain number of
 * imports at a time.
 */
class SrcRepoDirectoryImportScript extends AbstractRepositoryModelMain {
	
	val List<Integer> ports = Collections.synchronizedList(newArrayList)   
	
	private def void runImport(RepositoryModel repository) {
		val port = ports.remove(0)
		
		System.out.println('''Start import of «repository.qualifiedName». Stats at port «port».''')
		var result = 0
		try {
			val cmd = '''02-scripts/run-importdeamon.sh «repository.qualifiedName» «port» «new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Date.newInstance)»'''		
			val process = Runtime.runtime.exec(cmd)
			result = process.waitFor
		} catch (Exception e) {
			System.out.println('''Could not finish import of «repository.qualifiedName» with «result», because «e.toString + ":" + e.message».''')
			e.printStackTrace(System.out)
			return
		} 
		ports.add(port)
		System.out.println('''Finished import of «repository.qualifiedName» with «result».''')
	}
	
	override protected perform(String[] args) {		
		val importerCount = if (args.length == 0) 5 else Integer::parseInt(args.get(0))
		for (i:0..importerCount) ports += 8080 + i
		val scheduledRepositories = RepositoryModelUtils::scheduledForImport(directory)
		
		val executor = Executors::newFixedThreadPool(importerCount)
		scheduledRepositories.forEach[repository|
			executor.submit[
				repository.runImport
			]
		]
		
		executor.shutdown()
		executor.awaitTermination(10, TimeUnit::DAYS)
	}
	
	public static def void main(String[] args) {
		val instance = new SrcRepoDirectoryImportScript
		instance.run(args)
	}
}