package de.hub.srcrepo.ocl

import com.google.common.collect.AbstractIterator
import com.google.common.collect.FluentIterable
import de.hub.srcrepo.ocl.OclExtensions.Result
import java.util.ArrayList
import java.util.Iterator
import org.eclipse.emf.ecore.EObject

class OclExtensions {
	
	private static class Result<T> implements Iterator<T> {
		
		var elements = new ArrayList<T>
		var iterables = new ArrayList<Iterable<T>>
		
		def void add(T element) {
			elements.add(element)
		}
		
		def void addAll(Iterable<T> iterable) {
			iterables.add(iterable)
		}		
		
		def reset() {
			elements.clear
			iterables.clear
			iterables.add(elements)
			iterator = null
		}
		
		var Iterator<T> iterator = null
		
		override hasNext() {
			if (iterator == null) {
				iterator = iterables.flatten.iterator				
			}
			return iterator.hasNext	
		}
		
		override next() {
			if (iterator == null) {
				iterator = iterables.flatten.iterator				
			}
			return iterator.next
		}
		
		override remove() {
			throw new UnsupportedOperationException("Not supported, iterable is immutable.")
		}
		
	}
	
	private static def <E,T> Iterable<T> iterate(Iterable<E> source, Functions.Function2<E, Result<T>, Void> function) {
		return new FluentIterable<T>() {			
			override iterator() {
				val Iterator<E> sourceIterator = source.iterator
				val nextResult = new Result<T>
				return new AbstractIterator<T>() {					
					override protected computeNext() {
						if (nextResult.hasNext) {
							return nextResult.next
						} else {
							while (sourceIterator.hasNext) {
								val next = sourceIterator.next
								nextResult.reset
								function.apply(next, nextResult)
								if (nextResult.hasNext) {
									return nextResult.next
								}								
							}
							return endOfData
						}												
					}					
				}
			}			
		}
	}
	
	static def <E, T> collect(Iterable<E> source, Functions.Function1<E,T> function) {
		return source.iterate[element, result |
			result.add(function.apply(element))
			return null
		]
	}
	
	static def <E, T> collectAll(Iterable<E> source, Functions.Function1<E, ? extends Iterable<T>> function) {
		return source.iterate[element, result |
			result.addAll(function.apply(element))
			return null
		]
	}
	
	static def <E> select(Iterable<E> source, Functions.Function1<E,Boolean> function) {
		return source.iterate[element, result |
			if (function.apply(element)) {
				result.add(element)
			}
			return null
		]
	}
	
	static def <E> Iterable<E> closure(Iterable<E> source, Functions.Function1<E, Iterable<E>> function) {
		return source.iterate[element, result |
			result.add(element)
			result.addAll(function.apply(element).closure(function))
			return null
		]
	}
	
	static def <E> Iterable<E> closure(E source, Functions.Function1<E, Iterable<E>> function) {
		return #{source}.closure(function)	
	}
	
	static def <E> Iterable<E> union(Iterable<? extends E> one, Iterable<? extends E> two) {
		val oneIterator = one.iterator
		val twoIterator = two.iterator
		return new FluentIterable<E>() {			
			override iterator() {
				return new AbstractIterator<E> {					
					override protected computeNext() {
						if (oneIterator.hasNext) {
							return oneIterator.next
						} else if (twoIterator.hasNext) {
							return twoIterator.next
						} else {
							return endOfData
						}
					}					
				}
			}			
		}
	}
	
	static def <E> sum(Iterable<E> source, Functions.Function1<E,Integer> function) {
		return source.fold(0)[sum, element | sum + function.apply(element)]
	}
	
	static def <E> max(Iterable<E> source, Functions.Function1<E,Integer> function) {
		return source.fold(Integer.MIN_VALUE)[max, element | 
			val value = function.apply(element)
			return if (value > max) value else max
		]
	}
	
	static def <E, T extends E> typeSelect(Iterable<E> source, Class<T> type) {
		return source.select[type.isAssignableFrom(it.class)].collect[it as T]		
	}

	static def Iterable<EObject> eAllContentsAsIterable(EObject eObject) {
		return eObject.eContents.closure[it.eContents]		
	}	
	
	static def Iterable<EObject> eAllContentsAsIterable(EObject eObject, Functions.Function1<EObject,Boolean> filter) {
		return new FluentIterable<EObject>() {			
			override iterator() {
				val iterator = eObject.eAllContents
				return new AbstractIterator<EObject>() {					
					override protected computeNext() {
						while (iterator.hasNext) {
							val next = iterator.next
							if (filter.apply(next)) {
								return next
							} else {
								iterator.prune
							}
						} 
						return endOfData						
					}					
				}
			}			
		}
	} 
	
	static def EObject eSelectContainer(EObject eObject, Functions.Function1<EObject, Boolean> function) {
		return if (function.apply(eObject)) {
			eObject
		} else if (eObject.eContainer == null) {
			null
		} else {
			eObject.eContainer.eSelectContainer(function)
		}
	}
	
	static def <T> eTypeSelectContainer(EObject eObject, Class<T> type) {
		return eObject.eSelectContainer[type.isAssignableFrom(it.class)] as T	
	}
	
	static def Iterable<EObject> eAllContainer(EObject eObject, Functions.Function1<EObject,Boolean> filter) {
		val result = new ArrayList<EObject>
		var current = eObject
		while (current != null) {
			if (filter.apply(current)) {
				result.add(current)
			}
			current = current.eContainer
		}
		return result
	}
	
	static def Iterable<EObject> eAllContainer(EObject eObject) {
		return eObject.eAllContainer[true]	
	}
	
	static def <E> void eTraverse(EObject eObject, E start, Functions.Function2<EObject, E, E> function) {
		val result = function.apply(eObject, start)
		if (result != null) {
			eObject.eContents.forEach[eTraverse(result, function)]
		}
	}
	
	static def eRoot(EObject obj) {
		var root = obj
		while (root.eContainer != null) root = root.eContainer
		return root	
	}
}