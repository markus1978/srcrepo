package de.hub.srcrepo.compress

import de.hub.emfcompress.EmfCompressPackage
import de.hub.jstattrack.AbstractStatistic
import de.hub.jstattrack.TimeStatistic
import de.hub.jstattrack.ValueStatistic
import de.hub.jstattrack.services.Summary
import de.hub.srcrepo.AbstractRevVisitor
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.internal.Copier
import de.hub.srcrepo.repositorymodel.AbstractFileRef
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.Diff
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage
import de.hub.srcrepo.repositorymodel.Rev
import java.util.Arrays
import java.util.Comparator
import java.util.List
import java.util.Map
import java.util.concurrent.TimeUnit
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.match.DefaultComparisonFactory
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory
import org.eclipse.emf.compare.match.DefaultMatchEngine
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl
import org.eclipse.emf.compare.utils.UseIdentifiers
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.Package
import org.eclipse.gmt.modisco.java.emf.JavaPackage

import static extension de.hub.srcrepo.RepositoryModelUtil.*

abstract class AbstractCompressionMeasureVisitor extends AbstractRevVisitor {
	
	public static List<Pair<AbstractStatistic, String>> statNames = newArrayList
	
	protected static def vs(String name) {
		val result = new ValueStatistic().with(Summary).register(CompressionMeasureVisitor, name) as ValueStatistic
		statNames += (result as AbstractStatistic) -> name
		return result
	}
	
	protected static def ts(String name) {
		val result = new TimeStatistic(TimeUnit.NANOSECONDS).with(Summary).register(CompressionMeasureVisitor, name) as TimeStatistic
		statNames += (result as AbstractStatistic) -> name
		return result
	}
	
	public static val fullLines = vs("FullLineCount")
	public static val fullObjects = vs("FullObjectCount")
	public static val fullSize = vs("FullSize")
	
	public static val matchedLines = vs("MatchedLineCount")
	public static val addedLines = vs("AddedLines")
	public static val removedLines = vs("RemovedLines")
	
	public static val compressedRevisions = vs("CompressedRevisions")
	public static val uncompressedLines = vs("UCLineCount")
	public static val uncompressedSize = vs("UCSize")
	public static val uncompressedObjects = vs("UCObjectCount")
	
	protected val extension Copier copier = new Copier(#[RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE, EmfCompressPackage.eINSTANCE])
	
	val List<String> newRefs = newArrayList
	val Map<String, JavaCompilationUnitRef> currentRefs = newHashMap
	val Map<JavaCompilationUnitRef, JavaCompilationUnitRef> parentRefs = newHashMap
	
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
	
	override protected addFile(String name, Object file) {
		if (file instanceof JavaCompilationUnitRef) {
			val current = currentRefs.get(name)
			if (current != null) {
				parentRefs.put(file, current)
			}
			currentRefs.put(name, file)
			newRefs += name
		}
	}
	
	override protected clearFiles() {
		currentRefs.clear
	}
	
	override protected getFile(AbstractFileRef fileRef) {
		return fileRef
	}
	
	private def int toInt(Object o) {
		if (o != null) {
			o as Integer
		} else {
			-1
		}
	}
	
	protected abstract def void compare(Rev rev, JavaCompilationUnitRef parentCURef, JavaCompilationUnitRef newCURef, int newCUCount, ()=>void onFail)
	
	override protected onRev(Rev rev, Rev traversalParentRev) {
		var compressedFiles = 0
		for (newRef:newRefs) {
			val newCURef = currentRefs.get(newRef)
			if (newCURef.compilationUnitModel != null) {	
				count = 0			
				val newCUSize = newCURef.compilationUnitModel.size
				val newCUCount = count
				val newCULines = newCURef.getData("LOC-metrics").data.get("lines").toInt
				
				fullLines.track(newCULines)
				fullObjects.track(newCUCount)
				fullSize.track(newCUSize)
				
				val parentCURef = parentRefs.get(newCURef)
				if (parentCURef != null && parentCURef.compilationUnitModel != null) {
					val parentCULines = parentCURef.getData("LOC-metrics").data.get("lines").toInt
					matchedLines.track(parentCULines - (parentCURef.eContainer as Diff).linesRemoved)
					addedLines.track((parentCURef.eContainer as Diff).linesAdded)
					removedLines.track((parentCURef.eContainer as Diff).linesRemoved)	
					
					compare(rev, parentCURef, newCURef, newCUCount) [
						compressedRevisions.track(0)
						uncompressedLines.track(newCULines)
						uncompressedSize.track(newCUSize)
						uncompressedObjects.track(newCUCount)
					]
					compressedFiles += 1
				} else {
					compressedRevisions.track(0)
					uncompressedLines.track(newCULines)
					uncompressedSize.track(newCUSize)
					uncompressedObjects.track(newCUCount)				
				}				
			}
		}
		SrcRepoActivator.INSTANCE.info('''Compressed «compressedFiles» CUs in «rev.revInfo»''')		
		newRefs.clear
	}
	
	private def <T> void sortComposite(EList<T> list, Comparator<T> cmp) {
		val a = list.toArray
        Arrays.sort(a, cmp as Comparator<Object>);
        list.clear
        for(Object obj: a) {
        	list.add(obj as T)
        }        
	}
	
	protected def CompilationUnitModel normalize(CompilationUnitModel model) {
		model.javaModel.normalize
		model.targets.sortComposite[t1,t2|
			t1.id.compareTo(t2.id)
		]
		return model
	}
	
	protected def Model normalize(Model model) {
		val (NamedElement,NamedElement)=>int namedElementCmp = [one,two|one.name.compareTo(two.name)]
		model.orphanTypes.sortComposite(namedElementCmp)
		model.unresolvedItems.sortComposite(namedElementCmp)
		model.eAllContents.forEach[
			if (it instanceof Package) {
				it.ownedElements.sortComposite(namedElementCmp)
				it.ownedPackages.sortComposite(namedElementCmp)
			}
		]
		model.ownedElements.sortComposite(namedElementCmp)
		model.compilationUnits.sortComposite(namedElementCmp)
		return model
	}
	
	protected def void delete(EObject eObject) {
		eObject.eContents.forEach[it.delete]
		for(reference:eObject.eClass.EAllReferences.filter[!derived && (EOpposite == null || !EOpposite.containment)]) {
			eObject.eUnset(reference)
		}
	}
	
	override protected removeFile(String name) {

	}

	protected var int count = 0
	
	protected def int size(EObject eObject) {		
		var size = 0
		count++
		for(feature:eObject.eClass.EAllStructuralFeatures) {
			if (!feature.derived && !feature.transient && (
					feature instanceof EAttribute || 
					(feature as EReference).EOpposite == null || 
					!(feature as EReference).EOpposite.containment
			)) {
				if (feature.many) {
					size += (eObject.eGet(feature) as List<Object>).size*2	
				} else {
					val value = eObject.eGet(feature)
					size += if (value instanceof String) value.length else 2 
				}
			}
		}
		size += eObject.eContents.fold(0)[r,e|r + e.size]
		return size
	}
}