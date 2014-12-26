package de.hub.metrics

import org.eclipse.emf.common.util.EList
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.gmt.modisco.java.Block
import org.eclipse.gmt.modisco.java.IfStatement
import org.eclipse.gmt.modisco.java.MethodDeclaration
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.Statement
import org.eclipse.gmt.modisco.java.WhileStatement

import de.hub.srcrepo.ocl.OclList

class McCabeMetric {
	implicit def elistToOclList[E >: Null <: AnyRef](l: EList[E]):OclList[E] = new OclList[E](l) 

def mcCabeMetric(block: Block): Double = {
	  //gather all EStructuralFeature from Meta-Model
	  block.eContents().closure((e)=>e.eContents())
	  //Select all Statements
	    .select((e)=>e.isInstanceOf[Statement])
	    //Cast all Elements to Statement
	    .collect((e)=>e.asInstanceOf[Statement])
	    //add 1 for each keyword indicating a Branching or MethodDeclaration
	    .sum((s)=> 
	      if (s.isInstanceOf[IfStatement] || s.isInstanceOf[WhileStatement]) 1 // TODO cases
	      else if (s.isInstanceOf[MethodDeclaration]) 1
	      else 0
	  ) + 1.0
	}
	
	def mcCabeMetric(model: Model): Double = {
	  //gather all EStructuralFeature from Meta-Model
	  model.eContents().closure((e)=>e.eContents())
	  //select all AbstractMethodDeclarations
	  	.select((e)=>e.isInstanceOf[AbstractMethodDeclaration])
	  	//casting all Elements to AbstractMethodDeclaration, returns a new Collection
	  	.collect((e)=>e.asInstanceOf[AbstractMethodDeclaration])
	  	//calculate the McCabe-Metric
	  	.sum((e)=>if (e.getBody()!=null) mcCabeMetric(e.getBody()) else 0)
	}
}