package de.hub.srcrepo

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.AbstractFileRef
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.Rev
import java.util.Map

abstract class ProjectAwareRevVisitor extends AbstractRevVisitor {
			
	val Map<String, Map<String, JavaCompilationUnitRef>> projectFiles = newHashMap()
	val Map<String, String> pathToProject = newHashMap
			
	protected abstract def void onRev(Rev rev, Rev traversalParentRev, Map<String, Map<String, JavaCompilationUnitRef>> cusRefs)
	
	private def projectFiles(String projectID) {
		Preconditions.checkArgument(projectID != null)
		var existingFiles = projectFiles.get(projectID)
		if (existingFiles == null) {
			existingFiles = newHashMap
			projectFiles.put(projectID, existingFiles)
		} 
		return existingFiles
	}
	
	override protected addFile(String name, AbstractFileRef fileRef) {
		if (fileRef instanceof JavaCompilationUnitRef) {
			projectFiles(fileRef.projectID).put(name, fileRef as JavaCompilationUnitRef)
			pathToProject.put(name, fileRef.projectID)
		}
	}
	
	override protected clearFiles() {
		projectFiles.values.forEach[it.clear]
	}
	
	override protected onRev(Rev rev, Rev traversalParentRev) {
		onRev(rev, traversalParentRev, projectFiles)
	}
	
	override protected removeFile(String name) {
		val projectID = pathToProject.get(name)
		if (projectID != null) {
			projectFiles(projectID).remove(name)		
		}
	}	
}