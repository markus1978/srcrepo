package de.hub.srcrepo.compress

import de.hub.emfcompress.Comparer
import de.hub.emfcompress.ComparerConfiguration
import de.hub.emfcompress.ObjectDelta
import de.hub.emfcompress.Patcher
import de.hub.jstattrack.AbstractStatistic
import de.hub.jstattrack.TimeStatistic
import de.hub.jstattrack.ValueStatistic
import de.hub.jstattrack.services.Summary
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage
import de.hub.srcrepo.repositorymodel.Rev
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.concurrent.TimeUnit
import org.eclipse.gmt.modisco.java.TypeAccess
import org.eclipse.gmt.modisco.java.emf.JavaPackage

class CompressionMeasureVisitor extends AbstractCompressionMeasureVisitor {
	
	public val Map<String,ArrayList<Pair<Integer,Long>>> timePerCount = new HashMap
	
	private static val List<Pair<String, (JavaPackage,RepositoryModelPackage, (TypeAccess,Boolean)=>String)=>ComparerConfiguration>> configurations = #[
//		"NamedElement" -> [j,r,id| new SrcRepoComparerConfiguration(j,r) {			
//			override protected id(TypeAccess typeAccess, boolean original) {
//				return id.apply(typeAccess, original)
//			}			
//		}],
		"MetaClass" -> [j,r,id| new SrcRepoComparerConfigurationFullMetaClass(j,r) {			
			override protected id(TypeAccess typeAccess, boolean original) {
				return id.apply(typeAccess, original)
			}			
		}]
//		"Heuristics" -> [j,r,id| new SrcRepoComparerConfigurationFullHeuristics(j,r) {			
//			override protected id(TypeAccess typeAccess, boolean original) {
//				return id.apply(typeAccess, original)
//			}			
//		}]	
	]
	
	private static def Map<String,ValueStatistic> configurationBasedVS(String name) {
		configurationBasedStat(name)[new ValueStatistic().with(Summary).register(CompressionMeasureVisitor, it)]
	}
	private static def Map<String,TimeStatistic> configurationBasedTS(String name) {
		configurationBasedStat(name)[new TimeStatistic(TimeUnit.NANOSECONDS).with(Summary).register(CompressionMeasureVisitor, it)]
	}
	private static def <T> Map<String,T> configurationBasedStat(String name, (String)=>AbstractStatistic factory) {
		val result = newHashMap
		for(configuration:configurations) {
			val fullName = name + configuration.key.toFirstUpper
			val stat =  (factory.apply(fullName)) as T
			result.put(configuration.key, stat)
			statNames += (stat as AbstractStatistic) -> fullName
		}
		return result
	}
	
	public static val compressETStat = configurationBasedTS("CompressET")
	public static val patchETState = configurationBasedTS("PatchET")
	public static val deltaSize = configurationBasedVS("DeltaSize")
	public static val deltaObjects = configurationBasedVS("DeltaObjectCount")
	public static val failedCompares = configurationBasedVS("FailedCompared")
	public static val failedPatches = configurationBasedVS("FailedPatches")
	public static val matchedObjects = configurationBasedVS("MatchedObjectsCount")	
	
	override protected compare(Rev rev, JavaCompilationUnitRef parentCURef, JavaCompilationUnitRef newCURef, int newCURefCount, ()=>void onFail) {
		var boolean failed = false
		for (configuration: configurations) {	
			try {				
				val original = parentCURef.compilationUnitModel.copyWithReferences.normalize
				val revised = newCURef.compilationUnitModel.copyWithReferences.normalize
				val comparerConfiguration = configuration.value.apply(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE) [typeAccess,forOriginal|
					val model = if (forOriginal) original else revised
					val type = typeAccess.type
					return if (type == null) {
						model.unresolvedLinks.findFirst[source==typeAccess]?.id
					} else {
						model.targets.findFirst[target == type]?.id
					}
				]
				val comparer = new Comparer(comparerConfiguration)
				val compressTimer = compressETStat.get(configuration.key).timer
				var ObjectDelta delta = null
				val time = try {															
					delta = comparer.compare(original.javaModel, revised.javaModel)
					compressTimer.track
				} catch (Exception e) {
					SrcRepoActivator.INSTANCE.error('''Exception on comparing («configuration.key») «rev.name»/«newCURef.path»''', e)
					0
				} finally {
					compressTimer.track
				}
				
				if (time != 0) {
					val existingList = timePerCount.get(configuration.key)
					val list = if (existingList == null) {
						val newList = newArrayList
						timePerCount.put(configuration.key, newList)
						newList
					} else {
						existingList
					}
					list.add = newCURefCount -> time
				}
				
				if (delta != null) {
					matchedObjects.get(configuration.key).track(comparer.size)				
					
					count = 0
					deltaSize.get(configuration.key).track(delta.size)
					deltaObjects.get(configuration.key).track(count)					
															
//					val patchTimer = patchETState.get(configuration.key).timer
//					try {
//						val patcher = new Patcher
//						patcher.patch(original, delta)	
//						failedPatches.get(configuration.key).track(0)															
//					} catch (Exception e) {
//						SrcRepoActivator.INSTANCE.error('''Exception on patching («configuration.key») «rev.name»/«newCURef.path»''', e)
//						failedPatches.get(configuration.key).track(1)							
//					} 
//					patchTimer.track
					failedCompares.get(configuration.key).track(0)
					compressedRevisions.track(1)
				} else {
					failedCompares.get(configuration.key).track(1)
					if (!failed) {
						failed = true
						onFail.apply()
					}					
				} 
				
				delta?.delete
				original?.delete
				revised?.delete						
			} catch (Exception e) {
				SrcRepoActivator.INSTANCE.error('''Exception on measuring «configuration.key» for «rev.name»/«newCURef.path»''', e)
			}																	
		}
	}
	
	override onStartRev(Rev rev, Rev traversalParentRev, int number) {
		if (number < 10) {
			super.onStartRev(rev, traversalParentRev, number)		
		} else {
			return false
		}
	}
	
}
