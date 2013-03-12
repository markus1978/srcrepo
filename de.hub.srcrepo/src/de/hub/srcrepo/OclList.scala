package de.hub.srcrepo

import org.eclipse.emf.common.util.EList
import scala.collection.JavaConversions._
import org.eclipse.emf.common.util.BasicEList

class OclList[E >: Null <: AnyRef](val l: EList[E]) {
  
  def iterate[R](start: ()=>R, iter: (E,R)=>R): R = {
    var r = start()
    for (val e <- l) {
      r = iter(e,r);
    }
    r
  }
  
  def exists(predicate: (E)=>Boolean): Boolean = 
    iterate(()=>false, (e,r:Boolean)=>predicate(e) || r)
 
  def forAll(predicate: (E)=>Boolean): Boolean = 
    iterate(()=>true, (e,r:Boolean)=>predicate(e) && r)
  
  def select(predicate: (E)=>Boolean): EList[E] = 
    iterate(()=>new BasicEList[E](l.size()), (e,r:EList[E])=>{if (predicate(e)) r.add(e); r})
  
  def reject(predicate: (E)=>Boolean): EList[E] = 
    iterate(()=>new BasicEList[E](l.size()), (e,r:EList[E])=>{if (!predicate(e)) r.add(e); r})
  
  def collect[R](expr: (E)=>R): EList[R] =
    iterate(()=>new BasicEList[R](l.size()), (e,r:EList[R])=>{r.add(expr(e)); r})
    
  def collectAll[R](expr: (E)=>EList[R]): EList[R] =
    iterate(()=>new BasicEList[R](l.size()), (e,r:EList[R])=>{r.addAll(expr(e)); r})

  def aggregate[R](expr: (E)=>R, start: ()=>R, aggr: (R,R)=>R): R =
    iterate(start, (e,r:R)=>aggr(r,expr(e)))
   
  def sum(expr: (E)=>Double): Double =
    aggregate(expr, ()=>0.0, (a:Double,b:Double)=>a+b)
  
  def product(expr: (E)=>Double): Double =
    aggregate(expr, ()=>1.0, (a:Double,b:Double)=>a*b)
    
  def max(expr: (E)=>Double): Double =
    aggregate(expr, ()=>Double.MinValue, (a:Double,b:Double)=>Math.max(a, b))
    
  def min(expr: (E)=>Double): Double =
    aggregate(expr, ()=>Double.MaxValue, (a:Double,b:Double)=>Math.min(a, b))
    
  def closure(expr: (E)=>EList[E]): EList[E] =
  	_closure(()=>new BasicEList[E], expr)
  	  
  def _closure(start:()=>EList[E], expr: (E)=>EList[E]): EList[E] =
    iterate(start, (e,r:EList[E])=>{
      r.add(e); 
      new OclList(expr(e))._closure(()=>r,expr); 
      r
    })
    
  def union[F>:E](other:EList[F]): EList[E] = {
    val copy = (e:E,r:EList[E])=>{r.add(e); r};
    val r = iterate(()=>new BasicEList[E](l.size()+other.size()), copy);
    iterate(()=>r, copy)
  }
  	  
	def join[F >: Null <: AnyRef, R](l: OclList[F], pred: (E, F) => R): EList[R] = {
		val result = new BasicEList[R](this.l.size() * l.l.size())
		for (val e <- this.l) {
			for (val f <- l.l) {
				val r = pred(e, f)
				if (r != null) {
					result.add(r)
				}
			}
		}
		return result
	}

}