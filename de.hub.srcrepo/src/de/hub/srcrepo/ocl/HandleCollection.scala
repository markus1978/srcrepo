package de.hub.srcrepo.ocl
import de.hub.srcrepo.OclCollection
import java.util.Iterator

protected trait Handle[E] { 
  def isNull(): Boolean
  def value(): E
  def next(): Handle[E]
}

protected class ListHandle[E](val l:List[E], val index:Int) extends Handle[E] {
  override def isNull() = index >= l.length
  override def value():E = l(index)
  override def next = new ListHandle(l, index + 1)
}

protected class TreeHandle[P,E](
    val parent:()=>Handle[P], 
    val children:(Handle[P])=>Handle[E], 
    val childHandle:Handle[E]) extends Handle[E] {
  
  def this(parent:()=>Handle[P], children:(Handle[P])=>Handle[E]) {
    this(parent, children, if (parent == null || parent().isNull()) new NullHandle[E] else children(parent()))
  }
  
  val first:Handle[E] = {
    if (parent == null || parent().isNull()) {
      this
    } else if (childHandle.isNull()) {
      next().asInstanceOf[TreeHandle[P,E]]  
    } else {
      this
    }
  }
  
  override def isNull() = if (first == this) childHandle.isNull else first.isNull()
  
  override def value():E = if (first == this) childHandle.value() else first.value()
  
  override def next():Handle[E] = {
    var nextChild = childHandle.next();
    if (!nextChild.isNull()) {
      return new TreeHandle(parent, children, nextChild)
    } else {
      var nextParent = parent().next()
      while (!nextParent.isNull()) {
       nextChild = children(nextParent)
       if (!nextChild.isNull()) {
         return new TreeHandle(()=>nextParent, children, nextChild)
       } else {       
         nextParent = nextParent.next()
       }
      }      
      return new NullHandle()
    }
  }
}

protected class HeadHandle[E](val head:Handle[E], val tail:Handle[E]) extends Handle[E] {  
  override def isNull() = head.isNull();
  override def value() = head.value();
  override def next() = tail;
}

protected class ValueHandle[E](val theValue:E) extends Handle[E] {
  override def isNull() = false
  override def value() = theValue
  override def next() = new NullHandle[E]()
}

protected class NullHandle[E]() extends Handle[E] {
  override def value() = throw new UnsupportedOperationException
  override def isNull() = true
  override def next() = this
}

protected class IteratorHandle[E](val i:java.util.Iterator[E]) extends Handle[E] {
  class Value(val value:E)
  var current = if (i.hasNext()) new Value(i.next()) else null
  override def value() =  current.value
  override def next() = {
    if (i.hasNext) { 
      current = new Value(i.next())
    } else {
      current = null
    }
    this
  }
  override def isNull() = current == null
}

protected class HandleIterator[E](var h:Handle[E]) extends java.util.Iterator[E] {
  override def hasNext():Boolean = {
    h.isNull()
  }
  override def next(): E = {val r = h.value(); h = h.next(); r } 
  override def remove() { throw new UnsupportedOperationException() }
}

abstract class HandleCollection[E]() extends OclCollection[E] { 
  
  protected def handle():Handle[E]
  
  private def createHandle[R](b:OclCollection[R]):Handle[R] = { 
    if (b.isInstanceOf[HandleCollection[R]]) {      
      b.asInstanceOf[HandleCollection[R]].handle()
    } else {
      new IteratorHandle(b.iterator)
    }
  }
  
  private def compute[R,I](map:(E)=>Handle[I], reduce:(Handle[I])=>R): R = reduce(new TreeHandle[E,I](handle, (h)=>map(h.value())))
  
  override def iterator() = new HandleIterator(handle)
  
  override def first(): E = handle.value()
  
  override def size(): Int = aggregate[Int]((e)=>1, ()=>0, (a:Int,b:Int)=>a+b)
  
  override def exists(pred: (E) => Boolean): Boolean = {
    compute[Boolean,Boolean]((e)=>new ValueHandle(pred(e)), 
        (h:Handle[Boolean])=>{
          while(!h.isNull()) {
            if (h.value()) return true
          }
          false
        })
  }

  override def forAll(pred: (E) => Boolean): Boolean = {
     compute[Boolean,Boolean]((e)=>new ValueHandle(pred(e)), 
        (h:Handle[Boolean])=>{
          while(!h.isNull()) {
            if (!h.value()) return false
          }
          true
        })
  }

  override def select(pred: (E) => Boolean): OclCollection[E] = {
    compute[OclCollection[E],E]((e)=>if (pred(e)) new ValueHandle(e) else new NullHandle(), (h)=>new HandleBasedHandleCollection(h))
  }

  override def reject(pred: (E) => Boolean): OclCollection[E] = {
    compute[OclCollection[E],E]((e)=>if (pred(e)) new NullHandle() else new ValueHandle(e), (h)=>new HandleBasedHandleCollection(h))
  }

  override def collect[R](pred:(E)=>R) = {
    compute[OclCollection[R],R]((e)=>new ValueHandle(pred(e)), (h:Handle[R])=>new HandleBasedHandleCollection(h))
  }

  override def collectAll[R](pred:(E)=>OclCollection[R]) = {
    compute[OclCollection[R],R]((e)=>new TreeHandle[R,R](()=>createHandle(pred(e)), (r)=>new ValueHandle[R](r.value())), (h)=>new HandleBasedHandleCollection(h))
  }

  override def aggregate[R](expr: (E) => R, start: () => R, aggr: (R, R) => R): R = 
    compute[R, R]((e)=>new ValueHandle[R](expr(e)), (h)=>{
	    var r = start()
	    var handle = h
	    while (!handle.isNull()) {
	      r = aggr(r,handle.value())
	      handle = handle.next()
	    }
	    r
	  })    

  override def sum(expr: (E)=>Double): Double =
    aggregate(expr, ()=>0.0, (a:Double,b:Double)=>a+b)
  
  override def product(expr: (E)=>Double): Double =
    aggregate(expr, ()=>1.0, (a:Double,b:Double)=>a*b)
    
  override def max(expr: (E)=>Double): Double =
    aggregate(expr, ()=>Double.MinValue, (a:Double,b:Double)=>Math.max(a, b))
    
  override def min(expr: (E)=>Double): Double =
    aggregate(expr, ()=>Double.MaxValue, (a:Double,b:Double)=>Math.min(a, b))
 
  override def closure(pred:(E)=>OclCollection[E]) = {
    val closure = new RecursiveMap[E]((e)=>createHandle(pred(e)))
    compute[OclCollection[E],E](closure.map, (h:Handle[E])=>new HandleBasedHandleCollection(h))
  }
  
  private class RecursiveMap[E](val pred:(E)=>Handle[E]) {
    def map:(E)=>Handle[E] = 
      (e)=>new TreeHandle[E,E](
          ()=>new HeadHandle(new ValueHandle(e), pred(e)), 
          (h)=> {
            if (h.isInstanceOf[HeadHandle[E]]) 
              new ValueHandle(h.value()) 
            else 
              map(h.value)
          })
  }
}

private class HandleBasedHandleCollection[E](val theHandle:Handle[E]) extends HandleCollection[E] {
  override def handle():Handle[E] = theHandle
}

class HandleCollectionConversions {  
  implicit def sListToOcl[E](l: List[E]):OclCollection[E] = new HandleCollection[E] {
    override def handle:Handle[E] = if (l.length == 0) new NullHandle() else new ListHandle(l, 0)
  }
  implicit def jListToOcl[E](l: java.util.List[E]):OclCollection[E] = new HandleCollection[E] {
    override def handle():Handle[E] = new IteratorHandle(l.iterator()) 
  }
}


//class HandleCollection[E](val theHandle:Handle[E]) extends Collection[E] { def handle():Handle[E] = theHandle }

