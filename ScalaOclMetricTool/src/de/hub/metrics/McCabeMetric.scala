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

  /**
   * Calculates the McCabe Metric for the given InfixExpression. It is recommanded to use analyzeExpressionFromRoot, because this method is primary intended
   * for internal calculations.
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

  /**
   * @param expr: the Infix Expression to analyze
   * @return true if the passed expression doe not contain an Infix or Parenthesized Expression
   */
  def isElementalInfixExpression(expr: InfixExpression): Boolean = {
    if (expr.getLeftOperand().isInstanceOf[InfixExpression]
      || expr.getLeftOperand().isInstanceOf[ParenthesizedExpression]
      || expr.getRightOperand().isInstanceOf[InfixExpression]
      || expr.getRightOperand().isInstanceOf[ParenthesizedExpression])
      false
    else
      true
  }

  /**
   * @param expr: the Infix Expression to analyze
   * @return returns true if the Infix Epression Operator is kind of an logical Operation, i.e. part of [||, &&, >, <, >=, <=, ==, !=]
   */
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

  /**
   * Returns the McCabe value for the given Expression. If the Expression is nested inside another expression, this has to be indicated by setting the second argument to true.
   * E.g. The term: if( x < 0 || y > 100) consists of an Infix Expression with two nested Infix Expressions. So the first call for analyzing "||" is with setting the second argument to false
   * because the OR is like the root of the Expression.
   * But when analyzing the left/right hand side the calls have to be made with nestedTerm=true, because they are "nested" inside the OR-Expression.
   * argument set true.
   * In general its recommanded to use analyzeExpressionFromRoot just passing the Expression. This method is primary intended for internal calculations purposes.
   * @param expression: the expression to analyze
   * @param nestedTerm: a boolean indicating if the Expression is nested inside another expression
   * @return the McCabe value for the passed expression
   */
  def analyzeExpression(expression: Expression, nestedTerm: Boolean): Double = {
    if (expression.isInstanceOf[ParenthesizedExpression]) {
      return analyzeExpression(expression.asInstanceOf[ParenthesizedExpression].getExpression(), nestedTerm);
    } else if (expression.isInstanceOf[InfixExpression]) {
      return analyzeInfixExpression(expression.asInstanceOf[InfixExpression], nestedTerm)
    } else if (expression.isInstanceOf[ConditionalExpression]) {
      val elist = new BasicEList[ConditionalExpression]();
      elist.add(expression.asInstanceOf[ConditionalExpression]);
      elist.sum((expression) => {
        analyzeExpression(expression.getExpression(), false) +
          analyzeExpression(expression.getElseExpression(), false) +
          analyzeExpression(expression.getThenExpression(), false)
      })
    } else return 0
  }

  /**
   * Returns the McCabe value for the given Expression.
   * @param expression: the expression to analyze
   * @return the McCabe value for the passed expression
   */
  def analyzeExpressionFromRoot(expression: Expression): Double = {
    analyzeExpression(expression, false)
  }

  def mcCabeMetric(block: Block): Double = {
    //gather all EStructuralFeature from Meta-Model
    block.eContents().closure((e) => e.eContents())
      //Select all Statements
      .select((e) => e.isInstanceOf[Statement])
      //Cast all Elements to Statement
      .collect((e) => e.asInstanceOf[Statement])
      //add 1 for each keyword indicating a Branching
      .sum((s) =>
        if (s.isInstanceOf[SwitchCase]) 1
        else if (s.isInstanceOf[IfStatement]) {
          analyzeExpressionFromRoot(s.asInstanceOf[IfStatement].getExpression());
        } else if (s.isInstanceOf[DoStatement]) {
          analyzeExpressionFromRoot(s.asInstanceOf[DoStatement].getExpression());
        } else if (s.isInstanceOf[WhileStatement]) {
          analyzeExpressionFromRoot(s.asInstanceOf[WhileStatement].getExpression());
        } else if (s.isInstanceOf[ForStatement]) {
          analyzeExpressionFromRoot(s.asInstanceOf[ForStatement].getExpression());
        } else if (s.isInstanceOf[ReturnStatement]) {
          if ((s.asInstanceOf[ReturnStatement].eContainer().isInstanceOf[Block]
            && s.asInstanceOf[ReturnStatement].eContainer().asInstanceOf[Block].eContainer().isInstanceOf[MethodDeclaration])
            || s.asInstanceOf[ReturnStatement].eContainer().isInstanceOf[SwitchStatement]) //avoid counting switch-statements two times (s.a.)
            0 //return statements, which are the last statement in a MethodDeclaration doesnt increase complexity
          else
            1 + analyzeExpressionFromRoot(s.asInstanceOf[ReturnStatement].getExpression())
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

    val abstractMethodDeclarations = model.eContents().closure((e) => e.eContents())
      .select((e) => e.isInstanceOf[AbstractMethodDeclaration])
      .collect((e) => e.asInstanceOf[AbstractMethodDeclaration]);

    val iter = abstractMethodDeclarations.iterator();

    while (iter.hasNext) {
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
    } //while
    result.append(resultObject);

    //see: http://stackoverflow.com/questions/2429944/how-to-convert-a-scala-list-to-a-java-util-list
    return result.asJava
  }
}