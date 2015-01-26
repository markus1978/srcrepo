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
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration
import org.eclipse.gmt.modisco.java.BodyDeclaration
import org.eclipse.gmt.modisco.java.MethodDeclaration
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList
import org.eclipse.gmt.modisco.java.ClassDeclaration

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
	    val compilationUnits =  model.getCompilationUnits()	    
	    compilationUnits.collect((unit) => {
	      WmcMetricForUnit(unit)
	  })
	}
	  
	/**
	 * Calculates the Weighted Method Complexity (WMC) for a unit. Basically it is expected that all 
	 * methods will have the same Complexity and no nested classes or anonymous functions are present, 
	 * i.e. a Class has the normal structure of methods like in testclasses/CkWmcTest.java<br />
	 * To use different weights, a concept has to be defined how this weights are tracked inside the MoDisco
	 * model.
	 * 
	 * ToDo: Check the behaviour in case of nested classes or other special kinds of methoddeclarations.
	 * @param unit
	 * @return
	 */
	def WmcMetricForUnit(unit : CompilationUnit ) : ResultObject = {
	  var resultObject:ResultObject = new ResultObject();
	  resultObject.setFileName(unit.getName())
	  
	  //get the 'Types' reference list for the current Unit
	  val methodCount = unit.getTypes
	  // get the 'Body Declarations' containment reference list for all Types
	  .collectAll((typeEntry) => typeEntry.getBodyDeclarations())
	  //select only the AbstractMethodDeclarations from the Body Declarations
	  .select((s) => s.isInstanceOf[AbstractMethodDeclaration])
	  //the final size equals the number of method declarations inside this Unit 
	  .size()
	  
	  resultObject.getValues().append(methodCount)
	        
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
