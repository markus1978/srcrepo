package de.hub.srcrepo.repositorymodel.util

import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import java.util.List
import static extension de.hub.srcrepo.ocl.OclExtensions.*

class RepositoryModelUtils {
	
	public static def List<RepositoryModel> selectRepositoryModels(RepositoryModelDirectory directory, (RepositoryModel)=>boolean predicate) {
		directory.subDirectories.closure[subDirectories]
				.collectAll[repositories]
				.filter[predicate.apply(it)].toList
	}
	
	public static def List<RepositoryModel> scheduledForImport(RepositoryModelDirectory directory) {
		directory.selectRepositoryModels[metaData != null && metaData.importMetaData.scheduled].toList
	}
	
	public static def List<RepositoryModel> imported(RepositoryModelDirectory directory) {
		directory.selectRepositoryModels[metaData != null && metaData.importMetaData.imported].toList
	}
	
	public static def void updateImportStatuses(RepositoryModelDirectory directory) {
		directory.subDirectories.forEach[
			it.updateImportStatuses
		]
		
		directory.imported.clear();
		directory.imported.addAll(imported(directory));
		
		directory.scheduledForImport.clear();
		directory.scheduledForImport.addAll(scheduledForImport(directory));
	}
	
	public static def qualifiedName(RepositoryModel repository) {
		var name = repository.name.replaceAll("[^\\w_\\-\\.]+", "_")
		var container = repository.eContainer as RepositoryModelDirectory
		while (container != null) {
			name = container.name.replaceAll("[^\\w_\\-\\.]+", "_") + "-" + name
			container = container.eContainer as RepositoryModelDirectory
		}
		return name
	}
}