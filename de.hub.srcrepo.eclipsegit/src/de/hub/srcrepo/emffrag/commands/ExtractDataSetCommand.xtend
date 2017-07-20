package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.internal.FStoreFragmentation
import de.hub.emffrag.mongodb.MongoDBDataStore
import de.hub.jstattrack.Statistics
import de.hub.srcrepo.IRepositoryModelVisitor
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor
import de.hub.srcrepo.MoDiscoRevVisitor
import de.hub.srcrepo.RepositoryModelTraversal
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
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.json.JSONArray
import org.json.JSONObject

import static extension de.hub.jstattrack.StatisticsUtil.*
import static extension de.hub.srcrepo.RepositoryModelUtil.*
import static extension de.hub.srcrepo.metrics.ModiscoMetrics.*
import de.hub.srcrepo.SrcRepoActivator
import java.util.HashMap
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration
import org.eclipse.gmt.modisco.java.NamedElement

import static extension de.hub.srcrepo.ocl.OclExtensions.*
import org.eclipse.gmt.modisco.java.SingleVariableAccess
import org.eclipse.gmt.modisco.java.MethodInvocation
import org.eclipse.emf.ecore.EObject
import org.eclipse.gmt.modisco.java.AnonymousClassDeclaration

/**
 * WORK IN PROGRESS ...
 * 
 * This command extracts data from repositories and stores it as JSON records in a document
 * database.
 * Schema:
 *   collection projects
 *     name: string; // UID
 *     description: string;
 *     url: string;
 *     dataSets: any[];
 *   collection revisions // one entry per project/revision
 *     project: string;
 *     id: string; // UID
 *     comitter: string;
 *     message: string;
 *     time: string;
 *     parents: string[]; // revision IDs
 *     children: string[]; // revision IDs
 *     diffs: [{
 *       parent: string; // revision ID
 *       oldPath: string;
 *       newPath: string;
 *       cu: string; // path
 *       type: string;
 *       linesAdded: number;
 *       linesRemoved: number;
 *     }]
 *   collection snapshots // one entry per project/revision
 *     project: string;
 *     revision: string;
 *     cus: string[];
 *   collection cus // one entry per project/revision/cu
 *     project: string;
 *     revision: string;
 *     path: string;
 *     metrics: {
 *       loc: number;
 *     }
 *     types: [{
 *       name: string;
 *       children: [~];
 *       metrics: {
 *         ... all CC metrics and more
 *       };
 *       dependencies: [{
 *         revision: string;
 *         cu: string;
 *         typeName: string;
 *         metrics?: {
 *            ... dependency metrics?
 *         }
 *       }]
 *     }]; 
 */
class ExtractDataSetCommand extends AbstractDataCommand {

	
	private static abstract class ExtractDataSetModiscoRevVisitor extends MoDiscoRevVisitor {	

		abstract protected def void updateDBEntry(String collection, JSONObject entry)
			
		val String project
		val timeFormat = new SimpleDateFormat("dd-MM-yyyy")
		val currentSnapshotCUs = new HashMap<String, JavaCompilationUnitRef>()
		val List<Metric> metrics = newArrayList()
			
		new(String project) {
			super(JavaPackage.eINSTANCE)
			this.project = project
		}
		
		def addMetric(String name, (AbstractTypeDeclaration) => double metric) {
			metrics.add(new Metric(name) {				
				override calc(AbstractTypeDeclaration type) {
					return metric.apply(type)
				}				
			})
		}
		
		private def void safeCurrentSnapshotData(Rev rev) {
			val data = new JSONObject()
			data.put("project", project)
			data.put("rev", rev.name)
			val cus = new JSONArray()
			currentSnapshotCUs.values.forEach[
				cus.put(path)
			]
			data.put("cus", cus)
			
			updateDBEntry("snapshots", data)
		}
		
		private def toContainingType(EObject eObject) {
			eObject.eSelectContainer[
				it instanceof AbstractTypeDeclaration && !(it instanceof AnonymousClassDeclaration)				
			] as AbstractTypeDeclaration
		}
		
		private def JSONObject typeToJSON(AbstractTypeDeclaration type) {
			val data = new JSONObject()
			data.put("name", type.qualifiedName)
			
			val metricsData = new JSONObject()
			metrics.forEach[ data.put(name, calc(type))	]
			data.put("metrics", metricsData)
			
			val childData = new JSONArray()
			type.eContents.typeSelect(typeof(AbstractTypeDeclaration)).forEach[ childData.put(it.typeToJSON) ]
				
			val dependenciesData = new JSONArray()
			val dependencies = newArrayList()
			dependencies.addAll(type.eAllContentsAsIterable
			  	.typeSelect(typeof(SingleVariableAccess))
			  	.map[ variable.toContainingType	])
			dependencies.addAll(type.eAllContentsAsIterable
			  	.typeSelect(typeof(MethodInvocation))
			  	.map[ method.toContainingType ])
			dependencies.forEach[
				val dependencyCU = it.eTypeSelectContainer(JavaCompilationUnitRef)
				if (dependencyCU != null) {
					val dependencyData = new JSONObject()
					val dependencyRev = dependencyCU.eContainer.eContainer.eContainer as Rev
					dependencyData.put("rev", dependencyRev.name)
					dependencyData.put("cu", dependencyCU.path)
					dependencyData.put("typeName", it.qualifiedName)
					dependenciesData.put(dependencyData)
				}
			]
			data.put("dependencies", dependenciesData)
			
			return data
		}
		
		private def void extractFromCU(Rev rev, JavaCompilationUnitRef ref) {
			val data = new JSONObject()
			data.put("project", project)
			data.put("rev", rev.name)
			data.put("path", ref.path)
			
			val types = new JSONArray()
			ref.compilationUnitModel.compilationUnit.types.forEach[	types.put(typeToJSON) ]
			data.put("types", types)		
			
			val locData = ref.getData(MoDiscoRepositoryModelImportVisitor.locMetricsDataSet).data.get("ncss") as Integer	
			data.put("loc", if (locData == null) {
				SrcRepoActivator.INSTANCE.warning("CU ref without LOC data for path " + ref.path)
				0.0
			} else {
				locData as double
			})	
			
			updateDBEntry("cus", data)
		}
		
		private def String qualifiedName(AbstractTypeDeclaration type) {
			val names = newArrayList(type.name)
			val i = type.eContainer
			while (i != null && i instanceof NamedElement) {
				names.add((i as NamedElement).name)
			}			
			return names.reverse.join(".")
		}
		
		private def void extractFromRevision(Rev rev) {
			val data = new JSONObject()
			data.put("project", project)
			data.put("id", rev.name)
			data.put("committer", rev.commiter)
			data.put("time", timeFormat.format(rev.time))
			data.put("message", rev.message)
			
			val parents = new JSONArray()
			val diffs = new JSONArray()
			rev.parentRelations.forEach[
				parents.put(it.parent.name)
				it.diffs.forEach[
					val diff = new JSONObject()
					diff.put("newPath", newPath)
					diff.put("oldPath", oldPath)
					diff.put("type", type.name)
					diff.put("linesAdded", linesAdded)
					diff.put("linesRemoved", linesRemoved)
					if (file instanceof JavaCompilationUnitRef) {
						diff.put("cu", file.path)
					}
					diffs.put(diff)
				]
			]
			data.put("diffs", diffs)
			data.put("parents", parents)
			
			val children = new JSONArray()
			rev.childRelations.forEach[ children.put(it.child.name) ]
			data.put("children", children)
			
			updateDBEntry("revisions", data)
		}
		
		override protected final onRev(Rev rev, Rev traversalParentRev, String projectID, IModiscoSnapshotModel snapshot) {
			extractFromRevision(rev)
			
			val incrementalSnapshot = snapshot as IModiscoIncrementalSnapshotModel
			if (!incrementalSnapshot.incremental) {
				currentSnapshotCUs.clear(); 
			} 
			for (removedRef:incrementalSnapshot.removedRefs) {
				currentSnapshotCUs.remove(removedRef);
			}
			for (addedRef:incrementalSnapshot.addedRefs) {
				val cuRef = incrementalSnapshot.getContributingRef(addedRef);
				currentSnapshotCUs.put(addedRef, cuRef);				
				if (cuRef != null) {
					extractFromCU(rev, cuRef);				
				}
			}			
		}
		
		override protected final onRevWithSnapshots(Rev rev, Rev traversalParentRev, Map<String, ? extends IModiscoSnapshotModel> snapshots) {
			extractFromRevision(rev);
			safeCurrentSnapshotData(rev);			
		}
	}
	
	
	static var errors = 0
		
	private abstract static class Metric {		
		val String name
		
		new(String name) {
			this.name = name
		}		
		def double calc(AbstractTypeDeclaration type) {
			throw new RuntimeException("Needs to be overriden.")
		}		
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
		val visitor = new ExtractDataSetModiscoRevVisitor(repo.name) {	
			override protected updateDBEntry(String collection, JSONObject entry) {
				println('''«collection» += «entry.toString(2)»''')
			}	
		}
		visitor.addMetric("wmcHal", [weightedMethodPerClassWithHalsteadVolume as double])
		visitor.addMetric("wmc1", [weightedMethodsPerClass as double])
		
		repo.traverse(visitor)	
		
		// statistics data
		val dataObject = Statistics.reportToJSON.toSummaryData(repo.name, #[
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
		], cl.hasOption("h"))
		dataObject.put("errors", errors)
		val data = dataObject.toArray
		
		if (cl.hasOption("h")) {
			out.println(data.toHumanReadable)
		} else {				
			printHeader(data.toCSVHeader)
			out.println(data.toCSV(false))		
		}		
	}
}
