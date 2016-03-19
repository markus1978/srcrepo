package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.internal.FStoreFragmentation
import de.hub.emffrag.mongodb.MongoDBDataStore
import de.hub.jstattrack.Statistics
import de.hub.srcrepo.IRepositoryModelVisitor
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor
import de.hub.srcrepo.MoDiscoRevVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.emffrag.commands.MetricsCommand.Metric
import de.hub.srcrepo.emffrag.commands.MetricsCommand.MetricsModiscoRevVisitor
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.snapshot.IModiscoIncrementalSnapshotModel
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import de.hub.srcrepo.snapshot.ModiscoIncrementalSnapshotImpl
import java.text.SimpleDateFormat
import java.util.List
import java.util.Map
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.json.JSONArray
import org.json.JSONObject

import static extension de.hub.jstattrack.StatisticsUtil.*
import static extension de.hub.srcrepo.RepositoryModelUtil.*
import static extension de.hub.srcrepo.metrics.ModiscoMetrics.*

class MetricsCommand extends AbstractDataCommand {
		
	private static class DoubleMap<E> {
		val Map<E,Double> values = newHashMap()
		def get(E key) {
			values.get(key)?:0.0			
		}
		def put(E key, double value) {
			values.put(key, value)
		}
	}	
		
	private abstract static class Metric {
		private val values = new DoubleMap<IModiscoSnapshotModel>
		val String name
		private val Map<String, Double> cachedValues = newHashMap
		
		new(String name) {
			this.name = name
		}
		def void clear(IModiscoSnapshotModel ss) {
			values.put(ss, 0.0)
			cachedValues.clear
		}
		def double calc(IModiscoIncrementalSnapshotModel snapshot, String path) {
			calc(snapshot.getCompilationUnit(path))
		}
		def double calc(CompilationUnit cu) {
			throw new RuntimeException("Needs to be overriden.")
		}
		def void add(IModiscoIncrementalSnapshotModel snapshot, String path) {
			val increment = calc(snapshot, path)
			values.put(snapshot, values.get(snapshot) + increment)
			cachedValues.put(path,increment)
		}			
		def void remove(IModiscoIncrementalSnapshotModel snapshot, String path) {
			val existing = cachedValues.remove(path)
			val increment = if (existing != null) {
				existing
			} else {
				calc(snapshot, path)
			}
			values.put(snapshot, values.get(snapshot) - increment)
		}
		
		def getValue(Iterable<? extends IModiscoSnapshotModel> snapshots) {
			snapshots.fold(0.0)[r,t|r + values.get(t)]
		}
	}
	
	private abstract static class MetricsModiscoRevVisitor extends MoDiscoRevVisitor {
		
		val format = new SimpleDateFormat("dd-MM-yyyy")		
		val List<Metric> metrics = newArrayList
		var IModiscoIncrementalSnapshotModel incrementalSnapshot = null
			
		new() {
			super(JavaPackage.eINSTANCE);
		}
		
		def addMetric(Metric metric) {
			metrics += metric
		}
		
		def addMetric(String name, (CompilationUnit)=>double calculate) {
			metrics+= new Metric(name) {				
				override calc(CompilationUnit cu) {
					calculate.apply(cu)
				}
			}	
		}
		
		override protected onRev(Rev rev, Rev traversalParentRev, String projectID, IModiscoSnapshotModel snapshot) {
			incrementalSnapshot = snapshot as IModiscoIncrementalSnapshotModel
			if (!incrementalSnapshot.incremental) { 
				metrics.forEach[clear(incrementalSnapshot)]
			} 
			for (removedRef:incrementalSnapshot.removedRefs) {
				metrics.forEach[remove(incrementalSnapshot, removedRef)]
			}
			for (addedRef:incrementalSnapshot.addedRefs) {
				metrics.forEach[add(incrementalSnapshot, addedRef)]
			}
		}
		
		override protected onRevWithSnapshots(Rev rev, Rev traversalParentRev, Map<String, ? extends IModiscoSnapshotModel> snapshots) {
			val datum = new JSONObject
			datum.put("time", format.format(rev.time))
			datum.put("rev", rev.name)
			if (traversalParentRev != null) { 
				datum.put("parent", traversalParentRev.name)				
			} else {
				datum.put("parent", "before_root")
			} 
			for (metric:metrics) {
				datum.put(metric.name, metric.getValue(snapshots.values))
			}		
			datum.print
		}
		
		abstract def void print(JSONObject datum);
	}
	
	private def traverse(RepositoryModel repo, IRepositoryModelVisitor visitor) {
		RepositoryModelTraversal.traverse(repo, visitor)
		visitor.close(repo)
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)		
		options.addOption(Option.builder("o").longOpt("output").desc("File name to store the ouput instead of printing it").hasArg.build)
	}
	
	override protected run(RepositoryModelDirectory directory, RepositoryModel repo) {
		val visitor = new MetricsModiscoRevVisitor() {			
			override print(JSONObject datum) {
				// metrics data		
				val data = new JSONArray
				data.put(datum)	
				if (cl.hasOption("h")) {
					auxOut('''«repo.name»-data''').println(data.toHumanReadable)
				} else {
					printHeader(auxOut('''«repo.name»-data'''), data.toCSVHeader)
					auxOut('''«repo.name»-data''').println(data.toCSV(false))
				}
			}			
		}
		visitor.addMetric("cus", [1 as double])
		visitor.addMetric("wmcHal", [types.get(0).weightedMethodPerClassWithHalsteadVolume as double])
		visitor.addMetric("wmc1", [types.get(0).weightedMethodsPerClass as double])
		visitor.addMetric(new Metric("loc") {			
			override calc(IModiscoIncrementalSnapshotModel snapshot, String path) {
				val ref = snapshot.getContributingRef(path)
				val data = ref.getData(MoDiscoRepositoryModelImportVisitor.locMetricsDataSet).data.get("ncss") as Integer
				if (data == null) {
					0.0
				} else {
					data as double
				}
			}			
		})
		
		repo.traverse(visitor)	
		
		// statistics data
		val data = Statistics.reportToJSON.toSummaryData(repo.name, #[
			RepositoryModelTraversal.visitFullETStat -> "FullVisitET",
			FStoreFragmentation.loadETStat -> "FragLoadET",
			FStoreFragmentation.unloadETStat -> "FragUnloadET",
			MongoDBDataStore.readTimeStatistic -> "DataReadET",
			MoDiscoRevVisitor.revChangedCUCountStat -> "RevChangedCUCount",
			MoDiscoRevVisitor.revCUCountStat -> "RevCUCount",
			MoDiscoRevVisitor.revSnapshotCountStat -> "RevSSCount",
			MoDiscoRevVisitor.revUDFETStat -> "RevUDFET",
			MoDiscoRevVisitor.revComputeSSETStat -> "RevComputeSSET",
			ModiscoIncrementalSnapshotImpl.computeSnapshotETStat -> "ComputeSSET",
			ModiscoIncrementalSnapshotImpl.addCUsETStat -> "AddCUsET",
			ModiscoIncrementalSnapshotImpl.removeCUsETStat -> "RemoveCUsET",
			ModiscoIncrementalSnapshotImpl.removeCUsCompositeETStat -> "RemoveCUsCompositionET",
			ModiscoIncrementalSnapshotImpl.removeCUsReferencesETStat -> "RemoveCUsReferenceET",
			ModiscoIncrementalSnapshotImpl.resolveETStat -> "ResolveReferenceET",
			ModiscoIncrementalSnapshotImpl.cusLoadETStat -> "LoadCUModelET"
		], cl.hasOption("h")).toArray
		
		if (cl.hasOption("h")) {
			out.println(data.toHumanReadable)
		} else {				
			printHeader(data.toCSVHeader)
			out.println(data.toCSV(false))		
		}		
	}
}
