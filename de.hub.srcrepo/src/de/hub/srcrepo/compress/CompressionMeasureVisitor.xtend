package de.hub.srcrepo.compress

import de.hub.emfcompress.Comparer
import de.hub.emfcompress.EmfCompressPackage
import de.hub.emfcompress.Patcher
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
import java.io.File
import java.util.Arrays
import java.util.Comparator
import java.util.List
import java.util.Map
import java.util.concurrent.TimeUnit
import org.apache.commons.io.FileUtils
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.Package
import org.eclipse.gmt.modisco.java.TypeAccess
import org.eclipse.gmt.modisco.java.emf.JavaPackage

import static de.hub.srcrepo.compress.CompressionMeasureVisitor.*

import static extension de.hub.srcrepo.EMFPrettyPrint.*
import static extension de.hub.srcrepo.RepositoryModelUtil.*

class CompressionMeasureVisitor extends AbstractRevVisitor {
	
	public static val TimeStatistic compressETStat = new TimeStatistic(TimeUnit.NANOSECONDS).with(Summary).register(CompressionMeasureVisitor, "CompressET")
	public static val TimeStatistic patchETState = new TimeStatistic(TimeUnit.NANOSECONDS).with(Summary).register(CompressionMeasureVisitor, "PatchET")
	
	public static List<Pair<AbstractStatistic, String>> statNames = newArrayList
	private static def vs(String name) {
		if (statNames.empty) {
			statNames += (compressETStat as AbstractStatistic) -> "CompressET"
			statNames += (patchETState as AbstractStatistic) -> "PatchET"
		}
		val result = new ValueStatistic().with(Summary).register(CompressionMeasureVisitor, name) as ValueStatistic
		statNames += (result as AbstractStatistic) -> name
		return result
	}
	
	public static val fullLines = vs("FullLineCount")
	public static val fullObjects = vs("FullObjectCount")
	public static val fullSize = vs("FullSize")
	
	public static val matchedLines = vs("MatchedLineCount")
	public static val matchedObjects = vs("MatchedObjectsCount")
	public static val addedLines = vs("AddedLines")
	public static val deltaSize = vs("DeltaSize")
	
	public static val uncompressedLines = vs("UCLineCount")
	public static val uncompressedSize = vs("UCSize")
	
	val extension Copier copier = new Copier(#[RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE, EmfCompressPackage.eINSTANCE])
	
	val List<String> newRefs = newArrayList
	val Map<String, JavaCompilationUnitRef> currentRefs = newHashMap
	val Map<JavaCompilationUnitRef, JavaCompilationUnitRef> parentRefs = newHashMap
	
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
					
					val original = parentCURef.compilationUnitModel.copyWithReferences.normalize
					val revised = newCURef.compilationUnitModel.copyWithReferences.normalize
					val comparer = new Comparer(new SrcRepoComparerConfiguration(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE) {						
						override protected id(TypeAccess typeAccess, boolean forOriginal) {
							val model = if (forOriginal) original else revised
							val type = typeAccess.type
							return if (type == null) {
								model.unresolvedLinks.findFirst[source==typeAccess]?.id
							} else {
								model.targets.findFirst[target == type]?.id
							}
						}    			
		    		})		
					try {					
						val compressTimer = compressETStat.timer
									
						val delta = comparer.compare(original, revised)
						compressTimer.track
	
						deltaSize.track(delta.size)					
																
						compressedFiles += 1
					
						try {
							val patchTimer = patchETState.timer
							val patcher = new Patcher
							patcher.patch(original, delta)
							patchTimer.track										
						} catch (Exception e) {
							SrcRepoActivator.INSTANCE.error('''Exception on patching «rev.name»/«newCURef.path»''', e)
							FileUtils.write(new File("testdata/original.txt"), '''ORIG\n«original.prettyPrint»''')
							FileUtils.write(new File("testdata/revised.txt"), '''REVISED\n«revised.prettyPrint»''')
							FileUtils.write(new File("testdata/delta.txt"), '''REVISED\n«delta.prettyPrint»''')
							SrcRepoActivator.INSTANCE.error('''Exception on comparing «rev.name»/«newCURef.path»''', e)
						} finally {
							delta?.delete
							
						}
					} catch (Exception e) {
						FileUtils.write(new File("testdata/original.txt"), '''ORIG\n«original.prettyPrint»''')
						FileUtils.write(new File("testdata/revised.txt"), '''REVISED\n«revised.prettyPrint»''')
						SrcRepoActivator.INSTANCE.error('''Exception on comparing «rev.name»/«newCURef.path»''', e)
					} finally {
						original?.delete
						revised?.delete
					}	
						
					matchedObjects.track(comparer.size)
					matchedLines.track(parentCULines - (parentCURef.eContainer as Diff).linesRemoved)
					addedLines.track((parentCURef.eContainer as Diff).linesAdded)			
				} else {
					uncompressedLines.track(newCULines)
					uncompressedSize.track(newCUSize)				
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
	
	private def CompilationUnitModel normalize(CompilationUnitModel model) {
		model.javaModel.normalize
		model.targets.sortComposite[t1,t2|
			t1.id.compareTo(t2.id)
		]
		return model
	}
	
	private def Model normalize(Model model) {
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
	
	private def void delete(EObject eObject) {
		eObject.eContents.forEach[it.delete]
		for(reference:eObject.eClass.EAllReferences.filter[!derived && (EOpposite == null || !EOpposite.containment)]) {
			eObject.eUnset(reference)
		}
	}
	
	override protected removeFile(String name) {

	}
	
//	private def int size(EObject eObject) {
//		val baos = new ByteArrayOutputStream		
//		val oos = new EObjectOutputStream(baos, null)
//		oos.saveEObject(eObject as InternalEObject, Check.CONTAINER)
//		oos.flush
//		baos.close
//		return baos.size 
//	}

	var int count = 0
	
	private def int size(EObject eObject) {		
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