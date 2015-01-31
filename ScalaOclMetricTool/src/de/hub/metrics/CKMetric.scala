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
import org.eclipse.gmt.modisco.java.emf.impl.TypeAccessImpl
import org.eclipse.gmt.modisco.java.emf.impl.ClassDeclarationImpl

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
	 * Calculates the Weighted Method Complexity (WMC) for a CompilationUnit. <br />
	 * Basically it is expected that all methods will have the same Complexity and no nested classes or anonymous functions are present, 
	 * i.e. a Class has the normal structure of methods like in testclasses/CkWmcTest.java<br />
	 * To use different weights, a concept has to be defined how this weights are tracked inside the MoDisco model.
	 * 
	 * ToDo: Check the behaviour in case of nested classes or other special kinds of methoddeclarations. <br />
	 * How to handle different weights?
	 * @param unit
	 * @return
	 */
	def WmcMetricForUnit(unit : CompilationUnit ) : ResultObject = {
	  val resultObject:ResultObject = new ResultObject();
	  resultObject.setFileName(unit.getName());
	  
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
	  return model.getCompilationUnits().collect((unit) => {
	    DitMetricForClass(unit)
	  })
	}
	
	/**
	 * Calculates the Depth Inheritance Tree (DIT) for a compilationUnit inside a MoDisco Model.	  
	 * @param currentUnit : the Unit to calculate
	 * @return a @see{ResultObject} containing the deepest inheritance value and the name of
	 * the corresponding CompilationUnit.
	 */
	def DitMetricForClass(currentUnit : CompilationUnit) : ResultObject = {
	  val resultObject:ResultObject = new ResultObject();
	  resultObject.setFileName(currentUnit.getName())
	  
	  val ditValue = currentUnit
  	  //get the 'Types' reference list for the current Unit
	  .getTypes()
	  //select all classdeclarations for this unit from the types set
	  .select((currentType) => currentType.isInstanceOf[ClassDeclarationImpl])
	  //cast all those items to classdeclarations 
	  .collect((classDeclarationImpl) => classDeclarationImpl.asInstanceOf[ClassDeclarationImpl])
	  //calculate the DIT-value
	  .sum((currentClass)=>
	    	//no superclass yields a DIT of 0
	        if(currentClass.getSuperClass() == null) 
	          0
	        else	        
	          //In Java multiple inheritance is not allowed, but in general the longest path must be returned
	          1 + DitMetricForClass(currentClass.getSuperClass().getType().getOriginalCompilationUnit()).getValues.max	        
	        )
	  resultObject.getValues().append(ditValue)	      
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
