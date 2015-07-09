package de.hub.srcrepo.ocl;

import com.google.common.base.Objects;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class OclExtensions {
  private static class Result<T extends Object> implements Iterator<T> {
    private ArrayList<T> elements = new ArrayList<T>();
    
    private ArrayList<Iterable<T>> iterables = new ArrayList<Iterable<T>>();
    
    public void add(final T element) {
      this.elements.add(element);
    }
    
    public void addAll(final Iterable<T> iterable) {
      this.iterables.add(iterable);
    }
    
    public Iterator<T> reset() {
      Iterator<T> _xblockexpression = null;
      {
        this.elements.clear();
        this.iterables.clear();
        this.iterables.add(this.elements);
        _xblockexpression = this.iterator = null;
      }
      return _xblockexpression;
    }
    
    private Iterator<T> iterator = null;
    
    @Override
    public boolean hasNext() {
      boolean _equals = Objects.equal(this.iterator, null);
      if (_equals) {
        Iterable<T> _flatten = Iterables.<T>concat(this.iterables);
        Iterator<T> _iterator = _flatten.iterator();
        this.iterator = _iterator;
      }
      return this.iterator.hasNext();
    }
    
    @Override
    public T next() {
      boolean _equals = Objects.equal(this.iterator, null);
      if (_equals) {
        Iterable<T> _flatten = Iterables.<T>concat(this.iterables);
        Iterator<T> _iterator = _flatten.iterator();
        this.iterator = _iterator;
      }
      return this.iterator.next();
    }
    
    @Override
    public void remove() {
      throw new UnsupportedOperationException("Not supported, iterable is immutable.");
    }
  }
  
  private static <E extends Object, T extends Object> Iterable<T> iterate(final Iterable<E> source, final Function2<E, OclExtensions.Result<T>, Void> function) {
    return new FluentIterable<T>() {
      @Override
      public Iterator<T> iterator() {
        final Iterator<E> sourceIterator = source.iterator();
        final OclExtensions.Result<T> nextResult = new OclExtensions.Result<T>();
        return new AbstractIterator<T>() {
          @Override
          protected T computeNext() {
            boolean _hasNext = nextResult.hasNext();
            if (_hasNext) {
              return nextResult.next();
            } else {
              while (sourceIterator.hasNext()) {
                {
                  final E next = sourceIterator.next();
                  nextResult.reset();
                  function.apply(next, nextResult);
                  boolean _hasNext_1 = nextResult.hasNext();
                  if (_hasNext_1) {
                    return nextResult.next();
                  }
                }
              }
              return this.endOfData();
            }
          }
        };
      }
    };
  }
  
  public static <E extends Object, T extends Object> Iterable<T> collect(final Iterable<E> source, final Function1<E, T> function) {
    final Function2<E, OclExtensions.Result<T>, Void> _function = (E element, OclExtensions.Result<T> result) -> {
      T _apply = function.apply(element);
      result.add(_apply);
      return null;
    };
    return OclExtensions.<E, T>iterate(source, _function);
  }
  
  public static <E extends Object, T extends Object> Iterable<T> collectAll(final Iterable<E> source, final Function1<E, Iterable<T>> function) {
    final Function2<E, OclExtensions.Result<T>, Void> _function = (E element, OclExtensions.Result<T> result) -> {
      Iterable<T> _apply = function.apply(element);
      result.addAll(_apply);
      return null;
    };
    return OclExtensions.<E, T>iterate(source, _function);
  }
  
  public static <E extends Object> Iterable<E> select(final Iterable<E> source, final Function1<E, Boolean> function) {
    final Function2<E, OclExtensions.Result<E>, Void> _function = (E element, OclExtensions.Result<E> result) -> {
      Boolean _apply = function.apply(element);
      if ((_apply).booleanValue()) {
        result.add(element);
      }
      return null;
    };
    return OclExtensions.<E, E>iterate(source, _function);
  }
  
  public static <E extends Object> Iterable<E> closure(final Iterable<E> source, final Function1<E, Iterable<E>> function) {
    final Function2<E, OclExtensions.Result<E>, Void> _function = (E element, OclExtensions.Result<E> result) -> {
      result.add(element);
      Iterable<E> _apply = function.apply(element);
      Iterable<E> _closure = OclExtensions.<E>closure(_apply, function);
      result.addAll(_closure);
      return null;
    };
    return OclExtensions.<E, E>iterate(source, _function);
  }
  
  public static <E extends Object> Iterable<E> closure(final E source, final Function1<E, Iterable<E>> function) {
    return OclExtensions.<E>closure(Collections.<E>unmodifiableSet(CollectionLiterals.<E>newHashSet(source)), function);
  }
  
  public static <E extends Object> Integer sum(final Iterable<E> source, final Function1<E, Integer> function) {
    final Function2<Integer, E, Integer> _function = (Integer sum, E element) -> {
      Integer _apply = function.apply(element);
      return Integer.valueOf(((sum).intValue() + (_apply).intValue()));
    };
    return IterableExtensions.<E, Integer>fold(source, Integer.valueOf(0), _function);
  }
  
  public static <E extends Object> Integer max(final Iterable<E> source, final Function1<E, Integer> function) {
    final Function2<Integer, E, Integer> _function = (Integer max, E element) -> {
      final Integer value = function.apply(element);
      Integer _xifexpression = null;
      boolean _greaterThan = (value.compareTo(max) > 0);
      if (_greaterThan) {
        _xifexpression = value;
      } else {
        _xifexpression = max;
      }
      return _xifexpression;
    };
    return IterableExtensions.<E, Integer>fold(source, Integer.valueOf(Integer.MIN_VALUE), _function);
  }
  
  public static <E extends Object, T extends E> Iterable<T> typeSelect(final Iterable<E> source, final Class<T> type) {
    final Function1<E, Boolean> _function = (E it) -> {
      Class<?> _class = it.getClass();
      return Boolean.valueOf(type.isAssignableFrom(_class));
    };
    Iterable<E> _select = OclExtensions.<E>select(source, _function);
    final Function1<E, T> _function_1 = (E it) -> {
      return ((T) it);
    };
    return OclExtensions.<E, T>collect(_select, _function_1);
  }
  
  public static Iterable<EObject> eAllContentsAsIterable(final EObject eObject) {
    EList<EObject> _eContents = eObject.eContents();
    final Function1<EObject, Iterable<EObject>> _function = (EObject it) -> {
      return it.eContents();
    };
    return OclExtensions.<EObject>closure(_eContents, _function);
  }
  
  public static Iterable<EObject> eAllContentsAsIterable(final EObject eObject, final Function1<EObject, Boolean> filter) {
    return new FluentIterable<EObject>() {
      @Override
      public Iterator<EObject> iterator() {
        final TreeIterator<EObject> iterator = eObject.eAllContents();
        return new AbstractIterator<EObject>() {
          @Override
          protected EObject computeNext() {
            while (iterator.hasNext()) {
              {
                final EObject next = iterator.next();
                Boolean _apply = filter.apply(next);
                if ((_apply).booleanValue()) {
                  return next;
                } else {
                  iterator.prune();
                }
              }
            }
            return this.endOfData();
          }
        };
      }
    };
  }
  
  public static EObject eSelectContainer(final EObject eObject, final Function1<EObject, Boolean> function) {
    EObject _xifexpression = null;
    Boolean _apply = function.apply(eObject);
    if ((_apply).booleanValue()) {
      _xifexpression = eObject;
    } else {
      EObject _xifexpression_1 = null;
      EObject _eContainer = eObject.eContainer();
      boolean _equals = Objects.equal(_eContainer, null);
      if (_equals) {
        _xifexpression_1 = null;
      } else {
        EObject _eContainer_1 = eObject.eContainer();
        _xifexpression_1 = OclExtensions.eSelectContainer(_eContainer_1, function);
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  public static <T extends Object> T eTypeSelectContainer(final EObject eObject, final Class<T> type) {
    final Function1<EObject, Boolean> _function = (EObject it) -> {
      Class<? extends EObject> _class = it.getClass();
      return Boolean.valueOf(type.isAssignableFrom(_class));
    };
    EObject _eSelectContainer = OclExtensions.eSelectContainer(eObject, _function);
    return ((T) _eSelectContainer);
  }
  
  public static Iterable<EObject> eAllContainer(final EObject eObject, final Function1<EObject, Boolean> filter) {
    final ArrayList<EObject> result = new ArrayList<EObject>();
    EObject current = eObject;
    while ((!Objects.equal(current, null))) {
      {
        Boolean _apply = filter.apply(current);
        if ((_apply).booleanValue()) {
          result.add(current);
        }
        EObject _eContainer = current.eContainer();
        current = _eContainer;
      }
    }
    return result;
  }
  
  public static Iterable<EObject> eAllContainer(final EObject eObject) {
    final Function1<EObject, Boolean> _function = (EObject it) -> {
      return Boolean.valueOf(true);
    };
    return OclExtensions.eAllContainer(eObject, _function);
  }
}
