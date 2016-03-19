package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.internal.FStoreFragmentation
import de.hub.emffrag.mongodb.MongoDBDataStore
import de.hub.jstattrack.Statistics
import de.hub.srcrepo.MoDiscoRevVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl
import java.text.SimpleDateFormat
import java.util.Map
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.json.JSONArray
import org.json.JSONObject

import static extension de.hub.jstattrack.StatisticsUtil.*
import static extension de.hub.srcrepo.metrics.ModiscoMetrics.*
import static extension de.hub.srcrepo.ocl.OclExtensions.*

class MetricsCommand extends AbstractDataCommand {
	val format = new SimpleDateFormat("dd-MM-yyyy")
	
	val Map<NamedElementUUID, Integer> values = newHashMap
	val Map<IModiscoSnapshotModel, Pair<Integer, Integer>> snapshotValues = newHashMap
	
	static abstract class EmffragModiscoRevVisitor extends MoDiscoRevVisitor {
		new() {
			super(JavaPackage.eINSTANCE);
		}
		
		override protected onRev(Rev rev, String projectID, IModiscoSnapshotModel snapshot) {
			
		}
		
		override protected onRevWithSnapshots(Rev rev, Rev traversalParentRev, Map<String, ? extends IModiscoSnapshotModel> snapshots) {
			onRevWithSnapshotsCustom(rev, traversalParentRev, snapshots as Map<String, IModiscoSnapshotModel>)
		}
		
		abstract def void onRevWithSnapshotsCustom(Rev rev, Rev parent, Map<String, IModiscoSnapshotModel> snapshots);
	}
	
	private def traverse(RepositoryModel repo, EmffragModiscoRevVisitor visitor) {
		RepositoryModelTraversal.traverse(repo, visitor)
		visitor.close(repo)
	}
	
	private def qualifiedName(NamedElement namedElement) {
		var named = namedElement
		val builder = new StringBuilder
		while (named != null) {
			builder.append(named.name)
			builder.append(".")
			val container = named.eContainer
			if (container instanceof NamedElement) {
				named = container as NamedElement
			} else {
				named = null
			}
		}
		return builder.toString
	}
	
	private def <T extends NamedElement> int collect(String projectID, IModiscoSnapshotModel snapshot, T container, (T)=>int function) {
		val uuid = new NamedElementUUID(snapshot.getRev(container.originalCompilationUnit), projectID, snapshot.getId(container))
		
		val existing = values.get(uuid)
		if (existing != null) {
			return existing
		} else {
			val value = function.apply(container)
			values.put(uuid, value)
			return value
		}
	}
	
	private def <T extends NamedElement> int collect(Map<String,IModiscoSnapshotModel> snapshots, (String,IModiscoSnapshotModel)=>int function) {
		snapshots.entrySet.sum[
			val projectID = it.key
			val snapshot = it.value
			val existingSnapshotValue = snapshotValues.get(snapshot)
			if (existingSnapshotValue == null || existingSnapshotValue.key != snapshot.modCount) {
				val value = function.apply(projectID, snapshot)
				snapshotValues.put(snapshot, snapshot.modCount -> value)
				return value
			} else {
				return existingSnapshotValue.value
			}
		]		
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)		
		options.addOption(Option.builder("o").longOpt("output").desc("File name to store the ouput instead of printing it").hasArg.build)
	}
	
	override protected run(RepositoryModelDirectory directory, RepositoryModel repo) {
		{
			// metrics data
			val data = new JSONArray
			repo.traverse[rev, traversalParentRev, snapshots|
				val datum = new JSONObject
				datum.put("time", format.format(rev.time))
				datum.put("rev", rev.name)
				if (traversalParentRev != null) { 
					datum.put("parent", traversalParentRev.name)				
				} else {
					datum.put("parent", "before_root")
				} 
				datum.put("cus", snapshots.values.sum[model.compilationUnits.size])
				datum.put("wmcHsv", snapshots.collect[projectID,snapshot|
					snapshot.model.compilationUnits.filter[!types.empty].map[types.get(0)].sum[
						collect(projectID, snapshot, it) [weightedMethodPerClassWithHalsteadVolume]
					]
				])			
				data.put(datum)
			]
			
			if (cl.hasOption("h")) {
				auxOut('''«repo.name»-data''').println(data.toHumanReadable)
			} else {
				printHeader(auxOut('''«repo.name»-data'''), data.toCSVHeader)
				auxOut('''«repo.name»-data''').println(data.toCSV(false))
			}
		}	
		{
			// statistics data
			val data = Statistics.reportToJSON.toSummaryData(repo.name, #[
				RepositoryModelTraversal.visitFullETStat -> "FullVisitET",
				FStoreFragmentation.loadETStat -> "FragLoadET",
				FStoreFragmentation.unloadETStat -> "FragUnloadET",
				MongoDBDataStore.readTimeStatistic -> "DataReadET",
				ModiscoIncrementalSnapshotImpl.computeSnapshotETStat -> "ComputeSSET",
				ModiscoIncrementalSnapshotImpl.cusLoadETStat -> "LoadCUModelET",
				MoDiscoRevVisitor.revUDFETStat -> "MetricsET"
			], cl.hasOption("h")).toArray
			
			if (cl.hasOption("h")) {
				out.println(data.toHumanReadable)
			} else {				
				printHeader(data.toCSVHeader)
				out.println(data.toCSV(false))		
			}
		}		
	}
}
