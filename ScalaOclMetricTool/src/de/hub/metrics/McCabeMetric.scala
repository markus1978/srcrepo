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

    val expressionAsSet = new BasicEList[InfixExpression]();
    expressionAsSet.add(expression);

    //elemental infix expression with logical operator e.g. "if( a < b )"
    if (checkOperator(expression) && isElementalInfixExpression(expression)) {
      1.0
    } //if(a < b && b > 100) [s. McCabe: if(c1 AND c2) == if(c1) then if(c2) => count the conditions, and not the AND-Operator itself]
    else if (checkOperator(expression) && !isElementalInfixExpression(expression)) {

      val list1 = expressionAsSet.select((expression) => expression.getLeftOperand().isInstanceOf[ParenthesizedExpression])
        .iterate(() => new BasicEList[Double], (expr, list: EList[Double]) => {
          list.add(analyzeExpression((expr.getLeftOperand().asInstanceOf[ParenthesizedExpression]).getExpression(), true))
          list;
        })

      val list2 = expressionAsSet.select((expression) => expression.getLeftOperand().isInstanceOf[InfixExpression])
        .iterate(() => list1, (expr, list: EList[Double]) => {
          list.add(analyzeInfixExpression(expr.getLeftOperand().asInstanceOf[InfixExpression], true))
          list;
        })

      val list3 = expressionAsSet.select((expression) => expression.getRightOperand().isInstanceOf[ParenthesizedExpression])
        .iterate(() => list2, (expr, list: EList[Double]) => {
          list.add(analyzeExpression((expr.getRightOperand().asInstanceOf[ParenthesizedExpression]).getExpression(), true))
          list;
        })

      val list4 = expressionAsSet.select((expression) => expression.getRightOperand().isInstanceOf[InfixExpression])
        .iterate(() => list3, (expr, list: EList[Double]) => {
          list.add(analyzeInfixExpression(expr.getRightOperand().asInstanceOf[InfixExpression], true))
          list;
        })

      summe(list4);
    } //this line is used in the case of nested Terms within a branchcondition, e.g. (a < (3+6)), the right hand side doesn't cause
    //a branch on its own and therefore would return 0. However, the LESS has to be counted. 
    else if (nestedTerm) {
      1.0;
    } else
      0.0;
  }

  /**
   * Calculates the sum over all list items. Just needed because of the Typerestrictions for 'Double' in OclList.
   * @param list - the list over which items the sum shall be calculated
   * @return the sum
   */
  def summe(list: EList[Double]): Double = {
    var result = 0.0;
    if (list.size() > 0) {
      for (index <- 0 to list.size() - 1) {
        result = result + list.get(index);
      }
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
    val compilationUnit = model.getCompilationUnits().collect((unit) => unit);
    val abstractMethodDeclarations = model.eContents().closure((e) => e.eContents())
      .select((e) => e.isInstanceOf[AbstractMethodDeclaration])
      .collect((e) => e.asInstanceOf[AbstractMethodDeclaration]);

    compilationUnit.iterate(() => new BasicEList[ResultObject], (unit, outerList: EList[ResultObject]) => {
      val methods = abstractMethodDeclarations.select((method) => method.getOriginalCompilationUnit() != null)
        .select((method) => method.getOriginalCompilationUnit().equals(unit))

      // methods can be empty, because we look from the perspective of CompilationUnits  including e. g. Interfaces. Therefore when we iterate over the methods
      // comparing with for example an interface we will get an empty set.
      if (!methods.isEmpty()) {
        //calculate metric for each method contained inside the current Unit
        val metricsForUnit = methods.iterate(() => new ListBuffer[Double], (currentMethod, innerList: ListBuffer[Double]) => {
          innerList.append(mcCabeMetric(currentMethod.getBody()));
          innerList;
        })
        outerList.add(new ResultObject(metricsForUnit, unit.getName()));
      }
      outerList;
    });
  }
}