package de.hub.srcrepo

import de.hub.srcrepo.repositorymodel.AbstractFileRef
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.Rev
import java.util.Map

abstract class ProjectAwareRevVisitor extends RevVisitor {
			
	protected abstract def void onRev(Rev rev, String projectID, Map<String, CompilationUnitModel> cus)

	override onRev(Rev rev, Map<String, AbstractFileRef> files) {
		val Map<String, Map<String, CompilationUnitModel>> projectFiles = newHashMap()
		files.entrySet.filter[value instanceof JavaCompilationUnitRef].forEach[
			val compilationUnitModel = (value as JavaCompilationUnitRef).compilationUnitModel
			val projectID = compilationUnitModel.projectID
			var existingFiles = projectFiles.get(projectID)
			if (existingFiles == null) {
				existingFiles = newHashMap
				projectFiles.put(projectID, existingFiles)
			}
			existingFiles.put(key, compilationUnitModel)
		]	
		projectFiles.entrySet.forEach[onRev(rev, key, value)]
	}
	
}