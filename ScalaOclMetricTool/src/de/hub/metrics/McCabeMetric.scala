package de.hub.metrics

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.gmt.modisco.java.Block
import org.eclipse.gmt.modisco.java.IfStatement
import org.eclipse.gmt.modisco.java.MethodDeclaration
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.Statement
import org.eclipse.gmt.modisco.java.WhileStatement
import de.hub.srcrepo.ocl.OclList
import de.hub.srcrepo.ocl.OclUtil
import org.eclipse.gmt.modisco.java.SwitchStatement
import org.eclipse.jdt.internal.compiler.ast.CaseStatement
import org.eclipse.gmt.modisco.java.emf.impl.IfStatementImpl
import org.eclipse.gmt.modisco.java.emf.impl.WhileStatementImpl
import org.eclipse.gmt.modisco.java.emf.impl.SwitchStatementImpl
import org.eclipse.gmt.modisco.java.Comment
import org.eclipse.gmt.modisco.java.SwitchCase
import org.eclipse.gmt.modisco.java.emf.impl.SwitchCaseImpl
import org.eclipse.emf.ecore.EObject
import scala.collection.mutable.ListBuffer
import java.util.List
import scala.collection.JavaConverters._
import org.eclipse.gmt.modisco.java.DoStatement
import org.eclipse.gmt.modisco.java.ForStatement
import org.eclipse.gmt.modisco.java.BreakStatement
import org.eclipse.gmt.modisco.java.ContinueStatement
import org.eclipse.gmt.modisco.java.ReturnStatement
import org.eclipse.gmt.modisco.java.ConditionalExpression
import org.eclipse.gmt.modisco.java.InfixExpression

class McCabeMetric {
	implicit def elistToOclList[E >: Null <: AnyRef](l: EList[E]):OclList[E] = new OclList[E](l)
	
	def checkForConditionalIfStatements(statement: IfStatement) : Double = {
	  val elist = new BasicEList[IfStatement]();
	  elist.add(statement)
	  elist.collect((item) => item.getExpression().asInstanceOf[InfixExpression])
	  .closure((expr) => {
	    val foundInfixExpressions = new BasicEList[InfixExpression]();	    
	    if(expr.getLeftOperand().isInstanceOf[InfixExpression]){
		    foundInfixExpressions.add(expr.getLeftOperand().asInstanceOf[InfixExpression]);
	    }
	    if(expr.getRightOperand().isInstanceOf[InfixExpression]){
		    foundInfixExpressions.add(expr.getRightOperand().asInstanceOf[InfixExpression]);
	    }
	    foundInfixExpressions;
	  }).sum((infixExpression) => {
	    if(infixExpression.asInstanceOf[InfixExpression].getOperator().getName().equalsIgnoreCase("CONDITIONAL_AND") || 
	        infixExpression.asInstanceOf[InfixExpression].getOperator().getName().equalsIgnoreCase("CONDITIONAL_OR")) 1
	    else 0
	  })      
	}
	
	def checkForConditionalDoStatements(statement: DoStatement) : Double = {
	  val elist = new BasicEList[DoStatement]();
	  elist.add(statement)	
	  elist.collect((item) => item.getExpression().asInstanceOf[InfixExpression])
	  .closure((expr) => {
	    val foundInfixExpressions = new BasicEList[InfixExpression]();	    
	    if(expr.getLeftOperand().isInstanceOf[InfixExpression]){
		    foundInfixExpressions.add(expr.getLeftOperand().asInstanceOf[InfixExpression]);
	    }
	    if(expr.getRightOperand().isInstanceOf[InfixExpression]){
		    foundInfixExpressions.add(expr.getRightOperand().asInstanceOf[InfixExpression]);
	    }
	    foundInfixExpressions
	  }).sum((infixExpression) => {
	    if(infixExpression.asInstanceOf[InfixExpression].getOperator().getName().equalsIgnoreCase("CONDITIONAL_AND") || 
	        infixExpression.asInstanceOf[InfixExpression].getOperator().getName().equalsIgnoreCase("CONDITIONAL_OR")) 1
	    else 0
	  })      
	}
	
	def checkForConditionalWhileStatements(statement: WhileStatement) : Double = {
	  val elist = new BasicEList[WhileStatement]();
	  elist.add(statement)
	  elist.collect((item) => item.getExpression().asInstanceOf[InfixExpression])
	  .closure((expr) => {
	    val foundInfixExpressions = new BasicEList[InfixExpression]();
	    
	    if(expr.getLeftOperand().isInstanceOf[InfixExpression]){
		    foundInfixExpressions.add(expr.getLeftOperand().asInstanceOf[InfixExpression]);
	    }
	    if(expr.getRightOperand().isInstanceOf[InfixExpression]){
		    foundInfixExpressions.add(expr.getRightOperand().asInstanceOf[InfixExpression]);
	    }
	    foundInfixExpressions
	  }).sum((infixExpression) => {
	    if(infixExpression.asInstanceOf[InfixExpression].getOperator().getName().equalsIgnoreCase("CONDITIONAL_AND") || 
	        infixExpression.asInstanceOf[InfixExpression].getOperator().getName().equalsIgnoreCase("CONDITIONAL_OR")) 1
	    else 0
	  })      
	}
	
	def checkForConditionalForStatements(statement: ForStatement) : Double = {
	  val elist = new BasicEList[ForStatement]();
	  elist.add(statement)
	  elist.collect((item) => item.getExpression().asInstanceOf[InfixExpression])
	  .closure((expr) => {
	    val foundInfixExpressions = new BasicEList[InfixExpression]();
	    
	    if(expr.getLeftOperand().isInstanceOf[InfixExpression]){
		    foundInfixExpressions.add(expr.getLeftOperand().asInstanceOf[InfixExpression]);
	    }
	    if(expr.getRightOperand().isInstanceOf[InfixExpression]){
		    foundInfixExpressions.add(expr.getRightOperand().asInstanceOf[InfixExpression]);
	    }
	    foundInfixExpressions
	  }).sum((infixExpression) => {
	    if(infixExpression.asInstanceOf[InfixExpression].getOperator().getName().equalsIgnoreCase("CONDITIONAL_AND") || 
	        infixExpression.asInstanceOf[InfixExpression].getOperator().getName().equalsIgnoreCase("CONDITIONAL_OR")) 1
	    else 0
	  })      
	}

	def mcCabeMetric(block: Block): Double = {
	  //gather all EStructuralFeature from Meta-Model
	  block.eContents().closure((e)=>e.eContents())
	  //Select all Statements
	    .select((e)=>e.isInstanceOf[Statement])
	    //Cast all Elements to Statement
	    .collect((e)=>e.asInstanceOf[Statement])
	    //add 1 for each keyword indicating a Branching or MethodDeclaration
	    .sum((s)=>	      
	      if (s.isInstanceOf[SwitchCase]) 1
	      else if (s.isInstanceOf[IfStatement]) {
	        1 + checkForConditionalIfStatements(s.asInstanceOf[IfStatement])
	      } 
	      else if (s.isInstanceOf[DoStatement]) {
	        1 + checkForConditionalDoStatements(s.asInstanceOf[DoStatement])
	      } 
	      else if (s.isInstanceOf[WhileStatement]) {
	        1 + checkForConditionalWhileStatements(s.asInstanceOf[WhileStatement])
	      } 
	      else if (s.isInstanceOf[ForStatement]) {
	        1 + checkForConditionalForStatements(s.asInstanceOf[ForStatement])
	      } 
	      
//	      Discuss: why shall these words increase complexity?
//	      || s.isInstanceOf[BreakStatement]
//	      || s.isInstanceOf[ContinueStatement]) 1	      
	      //|| s.isInstanceOf[ReturnStatement]) 1 TODO  only if not the last statement of function 	      
	      else if (s.isInstanceOf[MethodDeclaration]) 1
	      else 0
	  ) + 1.0
	}
	
	def mcCabeMetric(model: Model): List[_] = {
	  var result:ListBuffer[ResultObject] = new ListBuffer[ResultObject];
	 
	  //gather all EStructuralFeature from Meta-Model
	  val vSelect = model.eContents().closure((e)=>e.eContents())
	  //select all AbstractMethodDeclarations
	 .select((e)=>e.isInstanceOf[AbstractMethodDeclaration])
	  	//casting all Elements to AbstractMethodDeclaration, returns a new Collection
	  val vCollect = vSelect.collect((e)=>e.asInstanceOf[AbstractMethodDeclaration]);
	  	//calculate the McCabe-Metric and save in result List	  
	  val iter = vCollect.iterator()
	  var i : Int = 0;
	  var lastCompilationUnit = "";
      var resultObject:ResultObject = null;
      var firstRun:Boolean = true;
	  while (iter.hasNext){
	    try{
	    val item = iter.next();
        val metric = mcCabeMetric(item.getBody());
        
        //if units differ
        if(!(lastCompilationUnit.equals(item.getOriginalCompilationUnit().toString()))){
          
            //it is either a new file
        	if(!firstRun){
        	  //save the current resultObject according to the previous file
        	  result.append(resultObject);
        	  //create a new resultObject for the current File
        	  resultObject = new ResultObject();
        	  resultObject.setFileName(item.getOriginalCompilationUnit().getName());
        	}
        	
        	//or the first run
        	else
        	{
        	   //create a new resultObject for the current File
        	  resultObject = new ResultObject();
        	  resultObject.setFileName(item.getOriginalCompilationUnit().getName());
        	  firstRun = false;
        	}        	
        } 
        
        //add last metric to the resultObject of the current File. Either it is a new File or a new Body within the previous
        resultObject.getValues().append(metric);
        lastCompilationUnit = item.getOriginalCompilationUnit().toString();
	    } catch {
	      case e: Exception => println("Probably no originalCompilationUnit exists. Exception caught: " + e.printStackTrace()); //TODO: why is it missing sometimes? 
	    }
	  
	  }//while
	  result.append(resultObject);
	  
	  //see: http://stackoverflow.com/questions/2429944/how-to-convert-a-scala-list-to-a-java-util-list
	  return result.asJava	  
	}
	
	
	//	def searchForNestedInfixExpressions(exp:OclList[InfixExpression]):Double = {
////	  var infixExpressionsList:EList[InfixExpression] = new BasicEList[InfixExpression]();
////	  infixExpressionsList.add(exp);
//	  val nestedInfixExpressions = exp.closure((expr) => {
//	    var foundInfixExpressions = new BasicEList[InfixExpression]();
//	    
//	    if(expr.getLeftOperand().isInstanceOf[InfixExpression]){
//		    foundInfixExpressions.add(expr.getLeftOperand().asInstanceOf[InfixExpression]);
//	    }
//	    if(expr.getRightOperand().isInstanceOf[InfixExpression]){
//		    foundInfixExpressions.add(expr.getRightOperand().asInstanceOf[InfixExpression]);
//	    }
//	    foundInfixExpressions;
//	  })
//	  
//	  nestedInfixExpressions.sum((infixExpression) => 
//	    if(infixExpression.getOperator().getName().equalsIgnoreCase("CONDITIONAL_AND") || infixExpression.getOperator().getName().equalsIgnoreCase("CONDITIONAL_OR")) 1
//	    else 0
//	  )  
//	}
	
	//	def checkForConditionalStatements(statement: Statement, kind:String):Double = {
//	  kind.toLowerCase() match {
////	    case "if" => {
////	      val exp = statement.asInstanceOf[IfStatement].getExpression().asInstanceOf[InfixExpression];
////	      val operator_name = exp.getOperator().getName();
////	      if(operator_name.equalsIgnoreCase("CONDITIONAL_AND") || operator_name.equalsIgnoreCase("CONDITIONAL_OR")) 1 + searchForNestedInfixExpressions(exp)
////	      else 1	      
////	    }
//	    case "do" => {
//	      val exp = statement.asInstanceOf[DoStatement].getExpression().asInstanceOf[InfixExpression];
//	      val operator_name = exp.getOperator().getName();
//	      if(operator_name.equalsIgnoreCase("CONDITIONAL_AND") || operator_name.equalsIgnoreCase("CONDITIONAL_OR")) 1 + searchForNestedInfixExpressions(exp)
//	      else 1	      
//	    }
//	    case "while" => {
//	      val exp = statement.asInstanceOf[WhileStatement].getExpression().asInstanceOf[InfixExpression];
//	      val operator_name = exp.getOperator().getName();
//	      if(operator_name.equalsIgnoreCase("CONDITIONAL_AND") || operator_name.equalsIgnoreCase("CONDITIONAL_OR")) 1 + searchForNestedInfixExpressions(exp)
//	      else 1	      
//	    }
//	    case "for" => {
//	      val exp = statement.asInstanceOf[ForStatement].getExpression().asInstanceOf[InfixExpression];
//	      val operator_name = exp.getOperator().getName();	     
//	      if(operator_name.equalsIgnoreCase("CONDITIONAL_AND") || operator_name.equalsIgnoreCase("CONDITIONAL_OR")) 1 + searchForNestedInfixExpressions(exp)
//	      else 1	      
//	    }
//	    case _ => 1
//	  }  
//	}
}