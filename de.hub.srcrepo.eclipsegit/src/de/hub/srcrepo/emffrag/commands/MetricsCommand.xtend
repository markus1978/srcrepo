package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.MoDiscoRevVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import java.text.SimpleDateFormat
import java.util.Map
import org.apache.commons.cli.CommandLine
import org.eclipse.emf.ecore.EObject
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.json.JSONArray
import org.json.JSONObject

import static extension de.hub.srcrepo.metrics.ModiscoMetrics.*
import static extension de.hub.srcrepo.ocl.OclExtensions.*
import de.hub.jstattrack.TimeStatistic
import java.util.concurrent.TimeUnit
import de.hub.jstattrack.services.Summary

class MetricsCommand extends AbstractRepositoryCommand {
	public val TimeStatistic revETStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary).register(MetricsCommand, "RevET")
	
	val format = new SimpleDateFormat("dd-MM-yyyy")
	
	val Map<EObject, Integer> values = newHashMap
	
	static abstract class EmffragModiscoRevVisitor extends MoDiscoRevVisitor {
		new() {
			super(JavaPackage.eINSTANCE);
		}		
	}
	
	private def traverse(RepositoryModel repo, EmffragModiscoRevVisitor visitor) {
		RepositoryModelTraversal.traverse(repo, visitor)
		visitor.close(repo)
	}
	
	private def <T extends NamedElement> int collect(Rev rev, IModiscoSnapshotModel snapshot, T container, String key, (T)=>int function) {
		return if (!values.containsKey(container)) {
			snapshot.getPersistedOriginal(container)
			val newValue = function.apply(container)
			// TODO save in model for later traversals; necessary provision in meta-model/emffrag is missing			
			values.put(container, newValue)
			newValue
		} else {
			values.get(container)
		}
	}
	
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel repo, CommandLine cl) {
		val data = new JSONArray
		repo.traverse[rev, traversalParentRev, snapshots|
			val timer = revETStat.timer
			val datum = new JSONObject
			datum.put("time", format.format(rev.time))
			datum.put("rev", rev.name)
			if (traversalParentRev != null) { 
				datum.put("parent", traversalParentRev.name)				
			} else {
				datum.put("parent", "before_root")
			} 
			datum.put("cus", snapshots.values.sum[model.compilationUnits.size])
			datum.put("wmcHsv", snapshots.values.sum[snapshot|snapshot.model.compilationUnits.filter[!types.empty].map[types.get(0)].sum[
				collect(rev, snapshot, it, "wmc_hsv") [weightedMethodPerClassWithHalsteadVolume]
			]])
			data.put(datum)
			timer.track
		]
		println(data.toHumanReadable)
	}
}