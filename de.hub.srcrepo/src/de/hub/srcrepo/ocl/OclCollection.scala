package de.hub.srcrepo

trait OclCollection[E] extends java.lang.Iterable[E] {
  
  def size(): Int
  
  def first(): E

  def exists(predicate: (E) => Boolean): Boolean

  def forAll(predicate: (E) => Boolean): Boolean

  def select(predicate: (E) => Boolean): OclCollection[E]

  def reject(predicate: (E) => Boolean): OclCollection[E]

  def collect[R](expr: (E) => R): OclCollection[R]

  def collectAll[R](expr: (E) => OclCollection[R]): OclCollection[R]

  def aggregate[R](expr: (E) => R, start: () => R, aggr: (R, R) => R): R

  def sum(expr: (E) => Double): Double

  def product(expr: (E) => Double): Double
  
  def max(expr: (E) => Double): Double

  def min(expr: (E) => Double): Double

  def closure(expr: (E) => OclCollection[E]): OclCollection[E]

//  def union[F >: E](other: OclCollection[F]): OclCollection[E]
//
//  def join[F, R](l: OclCollection[F], pred: (E, F) => R): OclCollection[R]
}