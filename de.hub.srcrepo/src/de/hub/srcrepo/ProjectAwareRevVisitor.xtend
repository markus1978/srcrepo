package de.hub.srcrepo

import de.hub.jstattrack.TimeStatistic
import de.hub.jstattrack.services.BatchedPlot
import de.hub.jstattrack.services.Summary
import de.hub.srcrepo.repositorymodel.AbstractFileRef
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.Rev
import java.util.Map
import java.util.concurrent.TimeUnit
import com.google.common.base.Preconditions

abstract class ProjectAwareRevVisitor extends AbstractRevVisitor {
	
	private static final TimeStatistic cusLoadETStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary).with(BatchedPlot).register(ProjectAwareRevVisitor, "CUsLoadET");
	private static final TimeStatistic cusVisitETStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary).with(BatchedPlot).register(ProjectAwareRevVisitor, "CUsVisitET");		
			
	val Map<String, Map<String, CompilationUnitModel>> projectFiles = newHashMap()
	val Map<String, String> pathToProject = newHashMap
			
	protected abstract def void onRev(Rev rev, Map<String, Map<String, CompilationUnitModel>> cus)
	
	private def loadCompilagtionUnitModel(JavaCompilationUnitRef fileRef) {
		val timer = cusLoadETStat.timer
		val cum = fileRef.compilationUnitModel
		timer.track
		return cum
	}
	
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
			val compilationUnitModel = (fileRef as JavaCompilationUnitRef).loadCompilagtionUnitModel
			projectFiles(compilationUnitModel.projectID).put(name, compilationUnitModel)
			pathToProject.put(name, compilationUnitModel.projectID)
		}
	}
	
	override protected clearFiles() {
		projectFiles.values.forEach[it.clear]
	}
	
	override protected onRev(Rev rev) {
		val timer = cusVisitETStat.timer
		onRev(rev, projectFiles)
		timer.track
	}
	
	override protected removeFile(String name) {
		val projectID = pathToProject.get(name)
		if (projectID != null) {
			projectFiles(projectID).remove(name)		
		}
	}	
}