package de.hub.srcrepo

import org.eclipse.emf.common.util.EList
import scala.collection.JavaConversions._
import org.eclipse.emf.common.util.BasicEList

class OclList[E >: Null <: AnyRef](val l: EList[E]) {

	def exists(callback: (E) => Boolean): Boolean = {
		for (val e <- l) {
			if (callback(e)) return true;
		}
		return false;
	}

	def forAll(callback: (E) => Boolean): Boolean = {
		for (val e <- l) {
			if (!callback(e)) return false;
		}
		return true;
	}

	def select(callback: (E) => Boolean): E = {
		for (val e <- l) {
			if (callback(e)) return e;
		}
		return null;
	}

	def collect[F](callback: (E) => F): EList[F] = {
		val result = new BasicEList[F](l.size())
		for (val e <- l) {
			result.add(callback(e))
		}
		return result;
	}
	
	def aggregate[T](seed:T, callback: (E,T)=> T): T = {
		var result : T = seed;
		for (val e <- l) {
			result = callback(e,result);
	    }
		return result;
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