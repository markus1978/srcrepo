package de.hub.srcrepo

import com.google.common.base.Preconditions
import de.hub.srcrepo.repositorymodel.AbstractFileRef
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.Rev
import java.util.Map

abstract class ProjectAwareRevVisitor extends AbstractRevVisitor {
	static class ProjectFile {
		val JavaCompilationUnitRef ref;
		val String projectID;

		new(String projectID, JavaCompilationUnitRef ref) {
			this.ref = ref
			this.projectID = projectID
		}
	}
		
	val Map<String, Map<String, JavaCompilationUnitRef>> projectFiles = newHashMap()
	val Map<String, String> pathToProject = newHashMap
	var revWithClear = false
			
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
	
	override protected getFile(AbstractFileRef fileRef) {
		return if (fileRef instanceof JavaCompilationUnitRef) {
			new ProjectFile(fileRef.projectID, fileRef)
		} else {
			null
		}
	}
	
	override protected addFile(String name, Object ref) {
		val projectFile = ref as ProjectFile
		projectFiles(projectFile.projectID).put(name, projectFile.ref)
		pathToProject.put(name, projectFile.projectID)
	}
	
	override protected clearFiles() {
		projectFiles.values.forEach[it.clear]
		pathToProject.clear
		revWithClear = true
	}
	
	override protected onRev(Rev rev, Rev traversalParentRev) {
		val it = projectFiles.entrySet.iterator
		if (revWithClear) {
			while (it.hasNext) {
				if (it.next.value.empty) {
					it.remove
				}
			}
			revWithClear = false		
		}
		onRev(rev, traversalParentRev, projectFiles)
	}
	
	override protected removeFile(String name) {
		val projectID = pathToProject.get(name)
		if (projectID != null) {
			val files = projectFiles(projectID)
			files.remove(name)
			if (files.empty) {
				projectFiles.remove(projectID)
			}
		}
	}	
}