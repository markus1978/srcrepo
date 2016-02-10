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

class MetricsCommand extends AbstractRepositoryCommand {
	
	val format = new SimpleDateFormat("dd-MM-yyyy")
	
	val Map<EObject, Integer> values = newHashMap
//	val Map<Rev, Integer> data = newHashMap
	
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
			// TODO save			
			values.put(container, newValue)
			newValue
		} else {
			values.get(container)
		}
	}
	
//	var count = 0
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel repo, CommandLine cl) {
		val data = new JSONArray
		repo.traverse[rev, snapshots|
			val datum = new JSONObject
			datum.put("1_time", format.format(rev.time))
			datum.put("2_cus", snapshots.values.sum[model.compilationUnits.size])
			datum.put("3_wmcHsv", snapshots.values.sum[snapshot|snapshot.model.compilationUnits.map[types.get(0)].sum[
				collect(rev, snapshot, it, "wmc_hsv") [weightedMethodPerClassWithHalsteadVolume]
			]])
			data.put(datum)
		]
		
		println(data.toHumanReadable)
	}
}