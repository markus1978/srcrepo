package de.hub.srcrepo.emffrag.commands

import de.hub.jstattrack.TimeStatistic
import de.hub.jstattrack.services.Summary
import de.hub.srcrepo.MoDiscoRevVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.Rev
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel
import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Map
import java.util.concurrent.TimeUnit
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.json.JSONArray
import org.json.JSONObject

import static extension de.hub.srcrepo.metrics.ModiscoMetrics.*
import static extension de.hub.srcrepo.ocl.OclExtensions.*
import de.hub.jstattrack.Statistics

class MetricsCommand extends AbstractRepositoryCommand {
	public val TimeStatistic revETStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary).register(MetricsCommand, "RevET")
	
	val format = new SimpleDateFormat("dd-MM-yyyy")
	
	val Map<String, Integer> values = newHashMap
	val Map<String, NamedElement> names = newHashMap
	
	static abstract class EmffragModiscoRevVisitor extends MoDiscoRevVisitor {
		new() {
			super(JavaPackage.eINSTANCE);
		}		
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
	
	private def <T extends NamedElement> int collect(Rev rev, IModiscoSnapshotModel snapshot, T container, String key, (T)=>int function) {
		// TODO save in model for later traversals; necessary provision in meta-model/emffrag is missing
		val name = container.qualifiedName
		val existing = names.get(name)
		if (existing != null) {
			if (existing == container) {
				return values.get(name) 
			} else {
				val newValue = function.apply(container)
				values.put(name, newValue)
				return newValue
			}			
		} else {
			val newValue = function.apply(container)
			names.put(name, container)	
			values.put(name, newValue)
			return newValue			
		}
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)		
		options.addOption(Option.builder("o").longOpt("output").desc("File name to store the ouput instead of printing it").hasArg.build)
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
		
		if (cl.hasOption("o")) {
			val out = new File(cl.getOptionValue("o"))
			val printer = new PrintWriter(out)
			printer.println(data.toCSV)
			printer.close
		} else {
			if (cl.hasOption("v")) { println('''The results...''') }				
		}
		println(data.toCSV)
		println(Statistics.reportToString)		
	}
}