package de.hub.metrics

import java.util.List;

import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.CompilationUnit

import de.hub.srcrepo.ocl.OclList
import de.hub.srcrepo.ocl.OclUtil

import org.eclipse.emf.common.util.EList

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._

class HalsteadMetric {
  implicit def elistToOclList[E >: Null <: AnyRef](l: EList[E]):OclList[E] = new OclList[E](l)
	
  
 /**
 * ToDo: This method should calculate the Halstead metric. But it has to be discussed, 
 *     how this could work, because OCL is based on Set Theory and Predicate Logic. 
 *     In Combination with the abstracted model built by MoDisco its hard to collect
 *     for example the alphabet needed for this metric.
 * @param model
 * @return
 */
def halsteadMetric(model : Model ) : List[_] = {
    var result:ListBuffer[ResultObject] = new ListBuffer[ResultObject];
    var resultObject:ResultObject = null;
    result.append(resultObject);
    
  //not yet implemented, see todo above
     
     return result.asJava;
  }
}
