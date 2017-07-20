package de.hub.srcrepo.compress

import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.Rev
import org.eclipse.emf.compare.Comparison
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.match.DefaultComparisonFactory
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory
import org.eclipse.emf.compare.match.DefaultMatchEngine
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl
import org.eclipse.emf.compare.utils.UseIdentifiers
import org.eclipse.emf.ecore.EObject
import de.hub.srcrepo.SrcRepoActivator

import static extension de.hub.srcrepo.ocl.OclExtensions.*
import java.util.HashSet
import java.util.Collection
import java.util.Vector

class EmfComparenMeasureVisitor extends AbstractCompressionMeasureVisitor {
	
	private static long s = 1000000000;
	
	private Collection<String> blackList = new HashSet<String>();
	public val Vector<Pair<Integer,Long>> timePerCount = new Vector
	
	public static val compressETStat = ts("CompressET")	
	public static val deltaSize = vs("DeltaSize")
	public static val deltaObjects = vs("DeltaObjectCount")
	public static val failedCompares = vs("FailedCompared")
	public static val matchedObjects = vs("MatchedObjectsCount")
	public static val compressETPerObjectStat = ts("CompressETPerObjectStat")
	
	// public static val patchETState = ts("PatchET")
	// public static val failedPatches = vs("FailedPatches")
	
	val EMFCompare comparator;
	
	public new() {
		val matcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.NEVER);
		val comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
		 
		val matchEngineFactory = new MatchEngineFactoryImpl(matcher, comparisonFactory);
		matchEngineFactory.setRanking(20);
		val matchEngineRegistry = new MatchEngineFactoryRegistryImpl();
		matchEngineRegistry.add(matchEngineFactory);
		
		comparator = EMFCompare.builder().setMatchEngineFactoryRegistry(matchEngineRegistry).build();
	}
	
	override protected compare(Rev rev, JavaCompilationUnitRef parentCURef, JavaCompilationUnitRef newCURef, int newRefObjectCount, ()=>void onFail) {
		val path = newCURef.path
		if (/* !path.contains("src-gen") && !path.contains("gen-src")) && */ !blackList.contains(path) /* && newRefObjectCount < 10000 */) {		
			val original = parentCURef.compilationUnitModel.javaModel.copyWithReferences.normalize
			val revision = newCURef.compilationUnitModel.javaModel.copyWithReferences.normalize
			
			var timer = compressETStat.timer
			val comparison = try {
				val comarison = emfCompare(original, revision)
				val time = timer.track			
				timer = null				
				
				if (time > 30*s) {
					blackList.add(path)
					SrcRepoActivator.INSTANCE.warning('''Blacklist «rev.name»/«path» for taking «time/s»''')
				} else {
					timePerCount += newRefObjectCount -> time
				}
								
				count = 0				
				deltaSize.track(comarison.size)
				deltaObjects.track(count)
				
				matchedObjects.track(comarison.matches.closure[submatches].size)
				
				failedCompares.track(0)
				comarison
			} catch (Exception e) {
				if (timer != null) {
					timer.track;
				}
				failedCompares.track(1)
				onFail.apply();
				SrcRepoActivator.INSTANCE.error('''Exception on comparing «rev.name»/«newCURef.path»''', e)
				null				
			}
			
			comparison?.delete
			original?.delete
			revision?.delete			
		}
	}
	
	private def Comparison emfCompare(EObject one, EObject two) {		
		val scope = EMFCompare.createDefaultScope(one, two)
		comparator.compare(scope)
	}
}
