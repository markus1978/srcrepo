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
import org.eclipse.gmt.modisco.java.emf.impl.MethodDeclarationImpl
import org.eclipse.gmt.modisco.java.ParenthesizedExpression
import org.eclipse.gmt.modisco.java.Expression
import org.eclipse.gmt.modisco.java.CatchClause
import org.eclipse.gmt.modisco.java.ThrowStatement

class McCabeMetric {
  implicit def elistToOclList[E >: Null <: AnyRef](l: EList[E]): OclList[E] = new OclList[E](l)

  def checkForConditionalIfStatements(statement: IfStatement): Double = {
    val elist = new BasicEList[IfStatement]();
    elist.add(statement)
    elist.select((item) => item.getExpression().isInstanceOf[InfixExpression])
      .sum((item) => analyzeInfixExpression(item.getExpression().asInstanceOf[InfixExpression], false)) +
      elist.select((item) => item.getExpression().isInstanceOf[ParenthesizedExpression])
      .sum((item) => analyzeExpression(item.getExpression().asInstanceOf[ParenthesizedExpression].getExpression(), false))
  }

  def checkForConditionalDoStatements(statement: DoStatement): Double = {
    val elist = new BasicEList[DoStatement]();
    elist.add(statement)
    elist.select((item) => item.getExpression().isInstanceOf[InfixExpression])
      .sum((item) => analyzeInfixExpression(item.getExpression().asInstanceOf[InfixExpression], false)) +
      elist.select((item) => item.getExpression().isInstanceOf[ParenthesizedExpression])
      .sum((item) => analyzeExpression(item.getExpression().asInstanceOf[ParenthesizedExpression].getExpression(), false))
  }

  def checkForConditionalWhileStatements(statement: WhileStatement): Double = {
    val elist = new BasicEList[WhileStatement]();
    elist.add(statement)
    elist.select((item) => item.getExpression().isInstanceOf[InfixExpression])
      .sum((item) => analyzeInfixExpression(item.getExpression().asInstanceOf[InfixExpression], false)) +
      elist.select((item) => item.getExpression().isInstanceOf[ParenthesizedExpression])
      .sum((item) => analyzeExpression(item.getExpression().asInstanceOf[ParenthesizedExpression].getExpression(), false))
  }

  def checkForConditionalForStatements(statement: ForStatement): Double = {
    val elist = new BasicEList[ForStatement]();
    elist.add(statement)
    elist.select((item) => item.getExpression().isInstanceOf[InfixExpression])
      .sum((item) => analyzeInfixExpression(item.getExpression().asInstanceOf[InfixExpression], false)) +
      elist.select((item) => item.getExpression().isInstanceOf[ParenthesizedExpression])
      .sum((item) => analyzeExpression(item.getExpression().asInstanceOf[ParenthesizedExpression].getExpression(), false))
  }

  def checkForConditionalReturnStatements(statement: ReturnStatement): Double = {
    val elist = new BasicEList[ReturnStatement]();
    elist.add(statement)

    // return statements, which gets analyzed here, are already checked not to be the last statement of a method and therefore has to be counted at least + 1
    // + whatever is defined inside the return
    1 + elist.select((item) => item.getExpression().isInstanceOf[InfixExpression])
      .sum((item) => analyzeInfixExpression(item.getExpression().asInstanceOf[InfixExpression], false)) +
      elist.select((item) => item.getExpression().isInstanceOf[ConditionalExpression])
      .sum((item) => analyzeConditionalExpression(item.getExpression().asInstanceOf[ConditionalExpression], false)) +
      elist.select((item) => item.getExpression().isInstanceOf[ParenthesizedExpression])
      .sum((item) => analyzeExpression(item.getExpression().asInstanceOf[ParenthesizedExpression].getExpression(), false))
  }

  /**
   * @param expression: the expression to analyze
   * @param nestedTerm : a boolean value to indicate if the given Expression is part of an Left/Right hand side, representing an inner term to evaluate like in (a < (3+6))
   * @return The McCabe-Metric value for the whole expression.
   */
  def analyzeInfixExpression(expression: InfixExpression, nestedTerm: Boolean): Double = {
    val elist = new BasicEList[InfixExpression]();
    var result = 0.0;
    elist.add(expression)

    //elemental infix expression with logical operator e.g. "if( a < b )"
    if (checkOperator(expression) && isElementalInfixExpression(expression)) {
      result = result + 1.0
    } //if(a < b && b > 100) [s. McCabe: if(c1 AND c2) == if(c1) then if(c2) => count the conditions, and not the AND-Operator itself]
    else if (checkOperator(expression) && !isElementalInfixExpression(expression)) {

      if (expression.getLeftOperand().isInstanceOf[InfixExpression]) {
        result = result + analyzeInfixExpression(expression.getLeftOperand().asInstanceOf[InfixExpression], true);
      } else if (expression.getLeftOperand().isInstanceOf[ParenthesizedExpression]) {
        result = result + analyzeExpression((expression.getLeftOperand().asInstanceOf[ParenthesizedExpression]).getExpression(), true)
      }

      if (expression.getRightOperand().isInstanceOf[InfixExpression]) {
        result = result + analyzeInfixExpression(expression.getRightOperand().asInstanceOf[InfixExpression], true);
      } else if (expression.getRightOperand().isInstanceOf[ParenthesizedExpression]) {
        result = result + analyzeExpression((expression.getRightOperand().asInstanceOf[ParenthesizedExpression]).getExpression(), true)
      }
    } //this line is used in the case of nested Terms within a branchcondition, e.g. (a < (3+6)), the right hand side doesn't cause
    //a branch on its own and therefore would return 0. However, the LESS has to be counted. 
    else if (nestedTerm) {
      result = result + 1.0;
    }
    result;
  }

  def isElementalInfixExpression(expr: InfixExpression): Boolean = {
    if (expr.getLeftOperand().isInstanceOf[InfixExpression]
      || expr.getLeftOperand().isInstanceOf[ParenthesizedExpression]
      || expr.getRightOperand().isInstanceOf[InfixExpression]
      || expr.getRightOperand().isInstanceOf[ParenthesizedExpression])
      false
    else
      true
  }

  def checkOperator(expr: InfixExpression): Boolean = {
    if (expr.getOperator().getName().equalsIgnoreCase("CONDITIONAL_AND") ||
      expr.getOperator().getName().equalsIgnoreCase("CONDITIONAL_OR") ||
      expr.getOperator().getName().equalsIgnoreCase("GREATER") ||
      expr.getOperator().getName().equalsIgnoreCase("LESS") ||
      expr.getOperator().getName().equalsIgnoreCase("GREATER_EQUALS") ||
      expr.getOperator().getName().equalsIgnoreCase("LESS_EQUALS") ||
      expr.getOperator().getName().equalsIgnoreCase("EQUALS") ||
      expr.getOperator().getName().equalsIgnoreCase("NOT_EQUALS")) true
    else false
  }

  def analyzeExpression(expression: Expression, nestedTerm: Boolean): Double = {
    if (expression.isInstanceOf[ParenthesizedExpression]) {
      return analyzeExpression(expression.asInstanceOf[ParenthesizedExpression].getExpression(), nestedTerm);
    } else if (expression.isInstanceOf[InfixExpression]) {
      return analyzeInfixExpression(expression.asInstanceOf[InfixExpression], nestedTerm)
    } else return 0
  }

  def analyzeConditionalExpression(expression: ConditionalExpression, nestedTerm: Boolean): Double = {
    val elist = new BasicEList[ConditionalExpression]();
    elist.add(expression);

    elist.sum((expression) => {
      analyzeExpression(expression.getExpression(), nestedTerm) +
        analyzeExpression(expression.getElseExpression(), nestedTerm) +
        analyzeExpression(expression.getThenExpression(), nestedTerm)
    })
  }

  def mcCabeMetric(block: Block): Double = {
    //gather all EStructuralFeature from Meta-Model
    block.eContents().closure((e) => e.eContents())
      //Select all Statements
      .select((e) => e.isInstanceOf[Statement])
      //Cast all Elements to Statement
      .collect((e) => e.asInstanceOf[Statement])
      //add 1 for each keyword indicating a Branching or MethodDeclaration
      .sum((s) =>
        if (s.isInstanceOf[SwitchCase]) 1
        else if (s.isInstanceOf[IfStatement]) {
          checkForConditionalIfStatements(s.asInstanceOf[IfStatement])
        } else if (s.isInstanceOf[DoStatement]) {
          checkForConditionalDoStatements(s.asInstanceOf[DoStatement])
        } else if (s.isInstanceOf[WhileStatement]) {
          checkForConditionalWhileStatements(s.asInstanceOf[WhileStatement])
        } else if (s.isInstanceOf[ForStatement]) {
          checkForConditionalForStatements(s.asInstanceOf[ForStatement])
        } else if (s.isInstanceOf[ReturnStatement]) {
          if ((s.asInstanceOf[ReturnStatement].eContainer().isInstanceOf[Block]
            && s.asInstanceOf[ReturnStatement].eContainer().asInstanceOf[Block].eContainer().isInstanceOf[MethodDeclaration])
            || s.asInstanceOf[ReturnStatement].eContainer().isInstanceOf[SwitchStatement]) //avoid counting switch-statements two times (s.a.)
            0 //return statements, which are the last statement in a MethodDeclaration doesnt increase complexity
          else
            checkForConditionalReturnStatements(s.asInstanceOf[ReturnStatement])
        } else if (s.isInstanceOf[CatchClause]) 1
        else if (s.isInstanceOf[ThrowStatement]) 1
        else if (s.isInstanceOf[BreakStatement]) 1
        else if (s.isInstanceOf[ContinueStatement]) 1
        else 0) +
      1.0 //each not empty Method has at least complexity 1
  }

  //TODO: Es gibt scheinbar in bestimmten Konstellationen einen Bug mit dem egit activator
  //see: e.g. https://bugs.eclipse.org/bugs/show_bug.cgi?id=370305, https://bugs.eclipse.org/bugs/show_bug.cgi?id=325829
  //muss noch genauer analysiert werden ob/wo ein problem besteht
  def mcCabeMetric(model: Model): List[_] = {
    var result: ListBuffer[ResultObject] = new ListBuffer[ResultObject];
    var resultObject: ResultObject = null;
    var lastCompilationUnit = "";
    var firstRun: Boolean = true;

    //gather all EStructuralFeature from Meta-Model
    val abstractMethodDeclarations = model.eContents().closure((e) => e.eContents())
      //select all AbstractMethodDeclarations
      .select((e) => e.isInstanceOf[AbstractMethodDeclaration])
      //casting all Elements to AbstractMethodDeclaration, returns a new Collection
      .collect((e) => e.asInstanceOf[AbstractMethodDeclaration]);
    //calculate the McCabe-Metric and save in result List	  

    val iter = abstractMethodDeclarations.iterator();

    while (iter.hasNext) {
      try {
        val abstractMethodDeclaration = iter.next();
        val methodBody = abstractMethodDeclaration.getBody();
        if (methodBody != null) {
          val metric = mcCabeMetric(methodBody);
          //if units differ
          if (!(lastCompilationUnit.equals(abstractMethodDeclaration.getOriginalCompilationUnit().toString()))) {

            //it is either a new file
            if (!firstRun) {
              //save the current resultObject according to the previous file
              result.append(resultObject);
              //create a new resultObject for the current File
              resultObject = new ResultObject();
              resultObject.setFileName(abstractMethodDeclaration.getOriginalCompilationUnit().getName());
            } //or the first run
            else {
              //create a new resultObject for the current File
              resultObject = new ResultObject();
              resultObject.setFileName(abstractMethodDeclaration.getOriginalCompilationUnit().getName());
              firstRun = false;
            }
          }

          //add last metric to the resultObject of the current File. Either it is a new File or a new Body within the previous
          resultObject.getValues().append(metric);
          lastCompilationUnit = abstractMethodDeclaration.getOriginalCompilationUnit().toString();
        }
      } catch {
        case e: Exception =>
          println("Exception caught: " + e.printStackTrace()); //TODO: why is the body of an abstractMethodDeclaration missing sometimes?  
      }
    } //while
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