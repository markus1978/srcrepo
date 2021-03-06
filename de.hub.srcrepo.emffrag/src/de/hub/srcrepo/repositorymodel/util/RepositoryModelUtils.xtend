package de.hub.srcrepo.repositorymodel.util

import de.hub.srcrepo.repositorymodel.ImportMetaData
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.util.List

import static extension de.hub.srcrepo.RepositoryModelUtil.*
import static extension de.hub.srcrepo.ocl.OclExtensions.*

class RepositoryModelUtils {
	
	public static def List<RepositoryModel> selectRepositoryModels(RepositoryModelDirectory directory, (ImportMetaData)=>boolean predicate) {
		val directories = directory.subDirectories.closure[subDirectories].union(newArrayList(directory)).toList
		return directories.collectAll[repositories].filter[
			val metaData = it.getData(typeof(ImportMetaData))
			if (metaData != null) {
				return predicate.apply(metaData)
			} else {
				return false
			}
		].toList
	}
	
	public static def List<RepositoryModel> scheduledForImport(RepositoryModelDirectory directory) {
		directory.selectRepositoryModels[scheduled].toList
	}
	
	public static def List<RepositoryModel> imported(RepositoryModelDirectory directory) {
		directory.selectRepositoryModels[imported].toList
	}
	
	public static def void updateImportStatuses(RepositoryModelDirectory directory) {
		directory.subDirectories.forEach[it.updateImportStatuses]
		
		directory.imported.clear()
		imported(directory).forEach[directory.imported.add(it)]
		
		directory.scheduledForImport.clear()
		scheduledForImport(directory).forEach[directory.scheduledForImport.add(it)]
	}
	
	public static def qualifiedName(RepositoryModel repository) {
		if (repository.name == null) {
			return "<unknown>"
		}
		var name = repository.name.replaceAll("[^\\w_\\-\\.]+", "_")
		var container = repository.eContainer as RepositoryModelDirectory
		while (container != null) {
			name = container.name.replaceAll("[^\\w_\\-\\.]+", "_") + "." + name
			container = container.eContainer as RepositoryModelDirectory
		}
		return name
	}
}