package de.hub.metrics

import java.util.List
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.CompilationUnit
import de.hub.srcrepo.ocl.OclList
import de.hub.srcrepo.ocl.OclUtil
import org.eclipse.emf.common.util.EList
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.EClass

/**
 * @author Frederik Marticke
 *
 */
class CKMetric {
  implicit def elistToOclList[E >: Null <: AnyRef](l: EList[E]):OclList[E] = new OclList[E](l)
	
	/**
	 * Calculates the Weighted Method Complexity (WMC) for each CompilationUnit inside a MoDisco Model.
	 * Basically it is expected that all methods will have the same Complexity.<br />
	 * To use different weights, a concept has to be defined how this weights are tracked inside the 
	 * model.
	 * @param model
	 * @return An List with WMC Values.
	 */
	def WmcMetric(model : Model ) : List[ResultObject] = {
	    model.getCompilationUnits().collect((unit) => {
	      WmcMetricForUnit(unit)
	  })
	}
	  
	/**
	 * Calculates the Weighted Method Complexity (WMC) for a unit. Basically it is expected that all 
	 * methods will have the same Complexity.<br />
	 * To use different weights, a concept has to be defined how this weights are tracked inside the MoDisco
	 * model.
	 * @param unit
	 * @return
	 */
	def WmcMetricForUnit(unit : CompilationUnit ) : ResultObject = {
	  var resultObject:ResultObject = new ResultObject();
	  resultObject.setFileName(unit.getName())
	  resultObject.getValues().append(
	      unit.eContents()
	      .closure((e)=>e.eContents())
	      .select((e)=>e.isInstanceOf[AbstractMethodDeclaration])
	      .size());  
	  return resultObject;
	  }
	
	/**
	 * Calculates the Depth Inheritance Tree (DIT) for each CompilationUnit inside a MoDisco Model.
	 * @param model
	 * @return a List of ResultObjects containing the longest inheritance tree for each Compilation Unit
	 */
	def DitMetric(model : Model ) : List[ResultObject] = {
	    model.getCompilationUnits().collect((unit) => {
	      DitMetricForClass(unit.eClass())
	  })
	}
	
	/**
	 * Calculates the Depth Inheritance Tree (DIT) for a eClass inside a MoDisco Model.	  
	 * @param currentClass : the class to calculate
	 * @return a @see{ResultObject} containing the deepest inheritance value and the name of
	 * the corresponding eclass.
	 */
	def DitMetricForClass(currentClass : EClass ) : ResultObject = {
	  var resultObject:ResultObject = new ResultObject();
	  resultObject.setFileName(currentClass.getName())
	  resultObject.getValues().append(
	      currentClass
	      .getEAllSuperTypes()	      
	      .sum((superType)=>
	        if(superType.getEAllSuperTypes().isEmpty()) 
	          1
	        else 1 + DitMetricForClass(superType).getValues.max
	        ))
	  return resultObject;
	  }
	
	/**
	 * Calculates the Number of Children (NOC) for each CompilationUnit inside a MoDisco Model.
	 * @param model
	 * @return a List of ResultObjects containing the NOC values for each Compilation Unit
	 */
	def NocMetric(model : Model ) : List[ResultObject] = {
	    model.getCompilationUnits().collect((unit) => {
	      NocMetricForClass(unit.eClass())
	  })
	}
	
	/**
	 * Calculates the Number of Children (NOC) for a eClass inside a MoDisco Model.	  
	 * @param currentClass : the class to calculate
	 * @return a @see{ResultObject} containing the deepest inheritance value and the name of
	 * the corresponding eclass.
	 */
	def NocMetricForClass(currentClass : EClass ) : ResultObject = {
	  var resultObject:ResultObject = new ResultObject();
	  resultObject.setFileName(currentClass.getName())
	  resultObject.getValues().append(
	      currentClass
	      .getEAllReferences()	      
	      .sum((reference)=>
	        if(currentClass.isSuperTypeOf(reference.getEReferenceType()))
	          1
	        else 0
	        ))
	  return resultObject;
	  }

}
