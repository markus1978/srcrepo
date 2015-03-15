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
import org.eclipse.gmt.modisco.java.TypeAccess
import org.eclipse.gmt.modisco.java.VariableDeclaration
import org.eclipse.gmt.modisco.java.MethodInvocation
import org.eclipse.gmt.modisco.java.emf.impl.SingleVariableDeclarationImpl
import org.eclipse.gmt.modisco.java.PrimitiveType
import org.eclipse.gmt.modisco.java.Statement
import org.eclipse.gmt.modisco.java.VariableDeclarationStatement
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment
import org.eclipse.gmt.modisco.java.FieldAccess
import org.eclipse.gmt.modisco.java.SingleVariableAccess
import org.eclipse.gmt.modisco.java.Expression
import org.eclipse.gmt.modisco.java.Block
import org.eclipse.gmt.modisco.java.AbstractMethodInvocation
import org.eclipse.gmt.modisco.java.ASTNode
import org.eclipse.gmt.modisco.java.emf.impl.MethodDeclarationImpl
import com.sun.org.apache.xpath.internal.operations.Equals
import org.eclipse.gmt.modisco.java.FieldDeclaration
import org.eclipse.gmt.modisco.java.PostfixExpression
import org.eclipse.gmt.modisco.java.ExpressionStatement
import org.eclipse.gmt.modisco.java.InstanceofExpression
import org.eclipse.gmt.modisco.java.ReturnStatement
import org.eclipse.gmt.modisco.java.InfixExpression
import org.eclipse.gmt.modisco.java.ParenthesizedExpression
import org.eclipse.gmt.modisco.java.IfStatement
import org.eclipse.gmt.modisco.java.WhileStatement
import org.eclipse.gmt.modisco.java.DoStatement
import org.eclipse.gmt.modisco.java.Assignment
import org.eclipse.gmt.modisco.java.PrefixExpression

/**
 * @author Frederik Marticke
 *
 */
class CKMetric {
  implicit def elistToOclList[E >: Null <: AnyRef](l: EList[E]): OclList[E] = new OclList[E](l)

  /**
   * Calculates the Weighted Method Complexity (WMC) for each CompilationUnit inside a MoDisco Model.
   * Basically it is expected that all methods will have the same Complexity.<br />
   * To use different weights, a concept has to be defined how this weights are tracked inside the
   * model.
   * @param model: the MoDisco Model
   * @return An List with WMC Values.
   */
  def WmcMetric(model: Model): List[ResultObject] = {
    val compilationUnits = model.getCompilationUnits()
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
   * @param unit: the compilation unit to analyze
   * @return a ResultObject containing the WMC-Value
   */
  def WmcMetricForUnit(unit: CompilationUnit): ResultObject = {
    val resultObject: ResultObject = new ResultObject();
    resultObject.setFileName(unit.getName());

    //get the 'Types' reference list for the current Unit
    val methodCount = unit.getTypes
      // get the 'Body Declarations' containment reference list for all Types
      .collectAll((currentType) => currentType.getBodyDeclarations())
      //select only the AbstractMethodDeclarations from the Body Declarations
      .select((bodyDeclaration) => bodyDeclaration.isInstanceOf[AbstractMethodDeclaration])
      //the final size equals the number of method declarations inside this Unit 
      .size()

    resultObject.getValues().append(methodCount)

    return resultObject;
  }

  /**
   * Calculates the Depth Inheritance Tree (DIT) for each CompilationUnit inside a MoDisco Model.
   * @param model: the MoDisco Model
   * @return a List of ResultObjects containing the longest inheritance tree for each Compilation Unit
   */
  def DitMetric(model: Model): List[ResultObject] = {
    return model.getCompilationUnits().collect((unit) => {
      DitMetricForUnit(unit)
    })
  }

  /**
   * Calculates the Depth Inheritance Tree (DIT) for a compilationUnit inside a MoDisco Model.
   * @param currentUnit : the Unit to calculate
   * @return a ResultObject containing the deepest inheritance value and the name of
   * the corresponding CompilationUnit.
   */
  def DitMetricForUnit(currentUnit: CompilationUnit): ResultObject = {
    val resultObject: ResultObject = new ResultObject();
    resultObject.setFileName(currentUnit.getName())

    val ditValue = currentUnit
      //get the 'Types' reference list for the current Unit
      .getTypes()
      //select all classdeclarations for this unit from the types set
      .select((currentType) => currentType.isInstanceOf[ClassDeclarationImpl])
      //cast all those items to classdeclarations 
      .collect((classDeclarationImpl) => classDeclarationImpl.asInstanceOf[ClassDeclarationImpl])
      //calculate the DIT-value
      .sum((currentClass) =>
        //no superclass yields a DIT of 0
        if (currentClass.getSuperClass() == null)
          0
        else
          //In Java multiple inheritance is not allowed, but in general the longest path must be returned
          1 + DitMetricForUnit(currentClass.getSuperClass().getType().getOriginalCompilationUnit()).getValues.max)
    resultObject.getValues().append(ditValue)
    return resultObject;
  }

  /**
   * Calculates the Number of Children (NOC) for each CompilationUnit inside a MoDisco Model.
   * @param model: the MoDisco Model
   * @return a List of ResultObjects containing the NOC values for each Compilation Unit
   */
  def NocMetric(model: Model): List[ResultObject] = {
    model.getCompilationUnits().collect((unit) => {
      NocMetricForUnit(unit, model.getCompilationUnits())
    })
  }

  /**
   * TODO: der Kurs ist schon ganz okay, aber wie zum geier kommt man an den Namen einer
   * CompilationUnit die umbenannt wurde??? bzw. an irgendwas anderes woran sich erkennen lässt
   * dass ein Interface implementiert wurde bzw. eine Klasse geerbt hat.
   * Ausserdem könnte es sein, dass Klassen mehrfach gezählt werden, wenn sie mehrfach als
   * CompilationUnit auftreten was z.B. den Wert 3.0 für #CompilationUnit #27: CkDitLevelTwoWithTwoParents.java *** Noc-Metric: 3.0#
   * erklären könnte der eigentlich nur 1.0 sein dürfte
   *
   * Calculates the Number of Children (NOC) for a compilationUnit inside a MoDisco Model.
   * @param currentUnit : the unit to calculate
   * @param allUnits : the corresponding modisco java model
   * @return a @see{ResultObject} containing the the number of direct subclasses for the currentUnit
   */
  def NocMetricForUnit(currentUnit: CompilationUnit, allUnits: EList[CompilationUnit]): ResultObject = {
    var resultObject: ResultObject = new ResultObject();
    resultObject.setFileName(currentUnit.getName())

    val nocValue = allUnits
      //get all other compilationUnits
      .select((unit) => !(unit.getName().equals(currentUnit.getName())))
      //sum up all classes having the current class as a direct superclass
      .sum((otherUnit) => {
        otherUnit.getTypes()
          //select all classdeclarations for this unit from the types set
          .select((currentType) => currentType.isInstanceOf[ClassDeclarationImpl])
          //cast all those items to classdeclarations 
          .collect((classDeclarationImpl) => classDeclarationImpl.asInstanceOf[ClassDeclarationImpl])
          //calculate the NOC-value
          .select((node) =>
            //select all classes having the current class as superClass and return the total number 
            (
              (
                node.getSuperClass() != null
                && node.getSuperClass().getType().getOriginalCompilationUnit().getName().equals(currentUnit.getName())) || (
                  !(node.getSuperInterfaces().isEmpty())
                  && foo(node.getSuperInterfaces(), currentUnit.getName().split(".")(0))))).size();
      })
    resultObject.getValues().append(nocValue);
    return resultObject;
  }

  /**
   * TODO: Das muss noch weg, aber dazu muss NOC richtig funktionieren...
   */
  def foo(list: EList[TypeAccess], name: String): Boolean = {
    val l = list;
    val gg = l.collect((interface) => interface.getType());
    val n = gg.collect((g) => g.getName())
    if (n.contains(name))
      println("<" + name + "> is contained in list: <" + n + ">___");

    n.contains(name);
  }

  /**
   * Calculates the Coupling Between Objects (CBO) inside a MoDisco model.
   * @param model: the MoDisco model
   * @return a List of ResultObjects containing the coupling value for each compilationUnit
   */
  def CboMetric(model: Model): List[ResultObject] = {
    val cboResultList: EList[ResultObject] = new BasicEList[ResultObject];

    //as part of an Type for 'VariableDeclaration'
    model.eContents().closure((e) => e.eContents())
      .select((content) => content.isInstanceOf[VariableDeclarationStatement])
      .collect((varDecStatement) => varDecStatement.asInstanceOf[VariableDeclarationStatement])
      .iterate(() => cboResultList, (vdStatement, cboList: EList[ResultObject]) => {
        val statementContainingUnit = vdStatement.getOriginalCompilationUnit().getName();
        //1. getType = variableAccessImpl; 2.getType = typ of Variable => only interessted in not primitive types
        if (!(vdStatement.getType().getType().isInstanceOf[PrimitiveType])) {

          printCboCoupling("VariableDeclarationType",
            stripCompilationUnitName(vdStatement.getType().getType().getName()),
            stripCompilationUnitName(statementContainingUnit),
            stripCompilationUnitName(vdStatement.getType().getType().getName()))

          addToCboList(stripCompilationUnitName(statementContainingUnit), stripCompilationUnitName(vdStatement.getType().getType().getName()), cboList);
        }
        cboList;
      });

    //as part of an 'MethodInvocation'

    model.eContents().closure((e) => e.eContents())
      .select((content) => content.isInstanceOf[MethodInvocation])
      .collect((methodInvocation) => methodInvocation.asInstanceOf[MethodInvocation])
      .iterate(() => cboResultList, (method, cboList: EList[ResultObject]) => {
        //only not default methods have an original compilation unit
        if ((method.getMethod().getOriginalCompilationUnit() != null
          //only interessed in methods defined in classes other than the one invoking the method
          && !(method.getMethod().getOriginalCompilationUnit().getName().equals(method.getOriginalCompilationUnit().getName())))) {

          printCboCoupling("Method",
            stripCompilationUnitName(method.getMethod().getName()),
            stripCompilationUnitName(method.getOriginalCompilationUnit().getName()),
            stripCompilationUnitName(method.getMethod().getOriginalCompilationUnit().getName()))

          addToCboList(stripCompilationUnitName(method.getOriginalCompilationUnit().getName()), stripCompilationUnitName(method.getMethod().getOriginalCompilationUnit().getName()), cboList)
        }
        cboList; //this line is only needed because iterate has to return something. Due to reference semantics the cboResultList already was updated
      });

    //reusable, see below
    val singleVariableAccess = model.eContents().closure((e) => e.eContents())
      .select((content) => content.isInstanceOf[SingleVariableAccess])
      .collect((singleVarAccess) => singleVarAccess.asInstanceOf[SingleVariableAccess]);

    //as part of an 'Statement'
    singleVariableAccess.select((singleVarAccess) => singleVarAccess.eContainer().isInstanceOf[Statement])
      //we only need those fields, which are part of an comppilationUnit not the one who are system defaults (i.e. "out" in system.out.println)
      .select((statement) => statement.getVariable().getOriginalCompilationUnit() != null)
      .iterate(() => cboResultList, (variable, cboList: EList[ResultObject]) => {
        //the unit inside the variable was declared
        val declaredIn = variable.getVariable().getOriginalCompilationUnit().getName();
        //the units inside the variable is used
        val usedIn = variable.eContainer().asInstanceOf[Statement].getOriginalCompilationUnit().getName();
        if (!(declaredIn.equalsIgnoreCase(usedIn))) {
          printCboCoupling("SingleVariableAccess-Statement",
            stripCompilationUnitName(variable.getVariable().getName()),
            stripCompilationUnitName(usedIn),
            stripCompilationUnitName(declaredIn))

          addToCboList(stripCompilationUnitName(usedIn), stripCompilationUnitName(declaredIn), cboList)
        }
        cboList; //this line is only needed because iterate has to return something. Due to reference semantics the cboResultList already was updated
      });

    //as part of an 'Expression'   
    singleVariableAccess.select((singleVarAccess) => singleVarAccess.eContainer().isInstanceOf[Expression])
      .select((expression) => expression.getVariable().getOriginalCompilationUnit() != null)
      .iterate(() => cboResultList, (variable, cboList: EList[ResultObject]) => {
        val declaredIn = variable.getVariable().getOriginalCompilationUnit().getName();
        val usedIn = variable.eContainer().asInstanceOf[Expression].getOriginalCompilationUnit().getName();
        if (!(declaredIn.equalsIgnoreCase(usedIn))) {
          printCboCoupling("SingleVariableAccess-Expression",
            stripCompilationUnitName(variable.getVariable().getName()),
            stripCompilationUnitName(usedIn),
            stripCompilationUnitName(declaredIn))

          addToCboList(stripCompilationUnitName(usedIn), stripCompilationUnitName(declaredIn), cboList)
        }
        cboList; //this line is only needed because iterate has to return something. Due to reference semantics the cboResultList already was updated
      });

    //Interface Implementation
    model.getCompilationUnits()
      .collectAll((unit) =>
        unit.getTypes()
          .select((currentType) => currentType.isInstanceOf[ClassDeclarationImpl])
          .collect((classDeclarationImpl) => classDeclarationImpl.asInstanceOf[ClassDeclarationImpl])
          .select((classDeclarationWithInterface) => !classDeclarationWithInterface.getSuperInterfaces().isEmpty()))

      .iterate(() => cboResultList, (classDec, cboList: EList[ResultObject]) => {
        classDec.getSuperInterfaces().iterate(() => cboList, (interface, resList: EList[ResultObject]) => {
          printCboCoupling("Interface-Implementation",
            stripCompilationUnitName(interface.getType().getName()),
            stripCompilationUnitName(classDec.getName()),
            stripCompilationUnitName(interface.getType().getName()))

          addToCboList(stripCompilationUnitName(classDec.getName()), stripCompilationUnitName(interface.getType().getName()), resList)
        })
      })

    return cboResultList;
  }

  /**
   * Calculates the Response for a Class (RFC) Value for each CompilationUnit inside a MoDisco Model.
   * @param model: the MoDisco Model
   * @return a List of ResultObjects containing the RFC-Value for each compilationUnit
   */
  def RfcMetric(model: Model): List[ResultObject] = {
    val methInvocations = model.eContents().closure((e) => e.eContents())
      .select((content) => content.isInstanceOf[MethodInvocation])
      .collect((methodInvocation) => methodInvocation.asInstanceOf[MethodInvocation])
      .select((methodInvocation) => methodInvocation.getMethod().getOriginalCompilationUnit() != null)

    model.getCompilationUnits().collect((unit) =>
      RfcMetricForUnit(unit, methInvocations))
  }

  /**
   * Calculates the RFC-Value for a compilation Unit, depending on the given Set of overall existing MethodInvocations inside a MoDisco Model
   * @param unit: the compilation unit to analyze
   * @param methodInvocations: the set of existing MethodInvocations
   * @return a ResultObject containing the RFC-Value
   */
  def RfcMetricForUnit(unit: CompilationUnit, methodInvocations: EList[MethodInvocation]): ResultObject = {
    val resultObject: ResultObject = new ResultObject();
    resultObject.setFileName(unit.getName());

    //This would be the {M} - Set according to C. & K.
    val methodsInsideClass = unit.getTypes
      // get the 'Body Declarations' containment reference list for all Types
      .collectAll((currentType) => currentType.getBodyDeclarations())
      //select only the MethodDeclarationImpl from the Body Declarations
      .select((bodyDeclarations) => bodyDeclarations.isInstanceOf[MethodDeclarationImpl])
      .collect((methodDeclarationImpl) => methodDeclarationImpl.asInstanceOf[MethodDeclarationImpl])

    //the whole set is the {R}-Set as union of all method calls for this Unit according to C. & K.
    val calledMethods = methodInvocations.select((method) =>
      //the units where the method was declared and where it is used has to be different
      !(method.getMethod().getOriginalCompilationUnit().getName().equals(method.getOriginalCompilationUnit().getName()))
        //and the unit where it used has to be the current unit
        && method.getOriginalCompilationUnit().getName().equals(unit.getName()))

    //The RFC-Value is the size of the union of {M} & {R} 
    val rfc = methodsInsideClass.union(calledMethods.asInstanceOf[EList[ASTNode]]);

    resultObject.getValues().append(rfc.size());

    return resultObject;
  }

  /**
   * @param model
   * @return
   */
  def LcomMetric(model: Model): List[ResultObject] = {
    val compilationUnits = model.getCompilationUnits()
    compilationUnits.collect((unit) => {
      LcomMetricForUnit(unit)
    })
  }

  /**
   *
   * @param unit: the compilation unit to analyze
   * @return a ResultObject containing the WMC-Value
   */
  def LcomMetricForUnit(unit: CompilationUnit): ResultObject = {
    val resultObject: ResultObject = new ResultObject();
    resultObject.setFileName(unit.getName());
    try {
      if (unit.getName().equalsIgnoreCase("CkLcomTest.java"))
        println("---->" + unit.getName() + "<------")

      //get the 'Types' reference list for the current Unit
      val methodsInsideUnit = unit.getTypes
        // get the 'Body Declarations' containment reference list for all Types
        //filter all empty classes
        .select((currentType) => !(currentType.getBodyDeclarations().isEmpty()))
        .collectAll((currentType) => currentType.getBodyDeclarations())
        //select only the AbstractMethodDeclarations from the Body Declarations
        .select((bodyDeclaration) => bodyDeclaration.isInstanceOf[AbstractMethodDeclaration])
        .collect((bodyDeclaration) => bodyDeclaration.asInstanceOf[AbstractMethodDeclaration])
        //e. g. implicit declared construtor methods have an empty body
        .select((method) => method.getBody() != null && method.getBody().getStatements() != null)

      val instanceVariablesInsideUnit = unit.getTypes
        // get the 'Body Declarations' containment reference list for all Types
        .collectAll((currentType) => currentType.getBodyDeclarations())
        //select only the FieldDeclaration representing class instance variables from the Body Declarations
        .select((bodyDeclaration) => bodyDeclaration.isInstanceOf[FieldDeclaration])
        .collect((bodyDeclaration) => bodyDeclaration.asInstanceOf[FieldDeclaration])
        .collect((fieldDeclaration) => fieldDeclaration.getFragments().get(0).getName())

      val SetOfUsedInstanceVariablesSets = methodsInsideUnit.iterate(() => new BasicEList[EList[String]], (method, variablesSet: BasicEList[EList[String]]) => {
        variablesSet.add(analyseMethodForLcom(method, instanceVariablesInsideUnit))
        variablesSet;
      })

      val P = SetOfUsedInstanceVariablesSets.iterate(() => new BasicEList[BinaryTupleType], (setI, listP: EList[BinaryTupleType]) => {
        SetOfUsedInstanceVariablesSets.iterate(() => listP, (otherSetI, otherListP: EList[BinaryTupleType]) => {
          //avoid tuple with empty/null items and avoid reflexive tuple, i.e. don't calculate intersection(setI, setI)
          if ((!otherSetI.isEmpty()) && (!setI.isEmpty()) && (otherSetI ne setI) && (setI.intersectForString(otherSetI).isEmpty()))
            otherListP.add(new BinaryTupleType(setI, otherSetI))
          otherListP;
        })
        listP;
      })

      val Q = SetOfUsedInstanceVariablesSets.iterate(() => new BasicEList[BinaryTupleType], (setI, listQ: EList[BinaryTupleType]) => {
        SetOfUsedInstanceVariablesSets.iterate(() => listQ, (otherSetI, otherListQ: EList[BinaryTupleType]) => {
          
          if ((!otherSetI.isEmpty()) && (!setI.isEmpty()) && (otherSetI ne setI) && !(setI.intersectForString(otherSetI).isEmpty()))
            otherListQ.add(new BinaryTupleType(setI, otherSetI))
          otherListQ;
        })
        listQ;
      })

      if (P.size() > Q.size())
        resultObject.getValues().append((P.size() - Q.size()) / 2) //The Sets intersection(Mi, C) include for each pair (i,j) the symmetric pair (j,i). For LCOM one Pair is enough, so the metric is the half of the calculated value 
      else
        resultObject.getValues().append(0)

    } catch {
      case ioe: Exception => ioe.printStackTrace()
    }

    resultObject;
  }

  //########################################
  //#			Helpermethods			   #
  //########################################

  //TODO: lokale variablen declarationen die auf werte von Instanzvariablen gesetzt werden

  def analyseMethodForLcom(method: AbstractMethodDeclaration, instanceVariablesInsideUnit: EList[String]): EList[String] = {
    var usedInstanceVariables: EList[String] = new BasicEList[String];
    var methodVariablesSet: EList[String] = new BasicEList[String];

    methodVariablesSet = method.getBody().getStatements().iterate(() => new BasicEList[String], (statement, myList: EList[String]) => {
      //the tempSet is used, because we need a mutable Set during the iteration to expand the collection using the union operation
      //the standard iterate works only for adding items to the same list, but union returns a new list to save
      var tempSet: EList[String] = new BasicEList[String];

      //equivalent to ocl: statement->...do something with statement in a set
      var statementAsSet = new BasicEList[Statement];
      statementAsSet.add(statement);

      /**
       * Because the Model already defines the kind of the statement as ifstatement, dostatement... we could check the kind inside ONE if like
       * (statement.isInstanceOf[IfStatement] || statement.isInstanceOf[DoStatement] || ...) but we cannot use the same CAST for each of them.
       * i.e. statementAsSet.collect((expr) => expr.asInstanceOf[IfStatement]) on a doStatement leads to an invalidCastExpception.
       * Therefore the following, bit ugly, redundant code is needed.
       */

      //if Statement
      if (statement.isInstanceOf[IfStatement])
        tempSet = statementAsSet.collect((expr) => expr.asInstanceOf[IfStatement])
          .iterate(() => new BasicEList[String], (ifstatement, instancesSet: EList[String]) => instancesSet.union(collectVariablesUsedInsideExpression(ifstatement.getExpression())))

      //do Statement
      if (statement.isInstanceOf[DoStatement])
        tempSet = statementAsSet.collect((expr) => expr.asInstanceOf[DoStatement])
          .iterate(() => new BasicEList[String], (doStatement, instancesSet: EList[String]) => instancesSet.union(collectVariablesUsedInsideExpression(doStatement.getExpression())))

      //while Statement
      if (statement.isInstanceOf[WhileStatement])
        tempSet = statementAsSet.collect((expr) => expr.asInstanceOf[WhileStatement])
          .iterate(() => new BasicEList[String], (whileStatement, instancesSet: EList[String]) => instancesSet.union(collectVariablesUsedInsideExpression(whileStatement.getExpression())))

      //return statement
      if (statement.isInstanceOf[ReturnStatement])
        tempSet = statementAsSet.collect((expr) => expr.asInstanceOf[ReturnStatement])
          .iterate(() => new BasicEList[String], (returnStatement, instancesSet: EList[String]) => instancesSet.union(collectVariablesUsedInsideExpression(returnStatement.getExpression())))

      //prefix and postfix Expression
      if (statement.isInstanceOf[ExpressionStatement] &&
        (statement.asInstanceOf[ExpressionStatement].getExpression().isInstanceOf[PostfixExpression]
          || statement.asInstanceOf[ExpressionStatement].getExpression().isInstanceOf[PrefixExpression]))
        tempSet = statementAsSet.collect((expr) => expr.asInstanceOf[ExpressionStatement])
          .iterate(() => new BasicEList[String], (expression, instancesSet: EList[String]) => instancesSet.union(collectVariablesUsedInsideExpression(expression.getExpression())))

      //assignment Expression         
      if (statement.isInstanceOf[ExpressionStatement] &&
        statement.asInstanceOf[ExpressionStatement].getExpression().isInstanceOf[Assignment]) {
        var assignmnet = statementAsSet.collect((expr) => expr.asInstanceOf[ExpressionStatement].getExpression().asInstanceOf[Assignment])

        //left hand side always should be a var or val
        if (!assignmnet.isEmpty()) {
          tempSet = assignmnet.collect((assignmentExpression) => assignmentExpression.getLeftHandSide())
            .select((operand) => operand.isInstanceOf[SingleVariableAccess])
            .collect((operand) => operand.asInstanceOf[SingleVariableAccess])
            .collect((singleVariableAccess) => singleVariableAccess.getVariable().getName())

          //right hand side either can be an expression, literal or variable. We are only interessted in expressions and variables
          //variable
          tempSet = tempSet.union(assignmnet.collect((assignmentExpression) => assignmentExpression.getRightHandSide())
            .select((operand) => operand.isInstanceOf[SingleVariableAccess])
            .collect((operand) => operand.asInstanceOf[SingleVariableAccess])
            .collect((singleVariableAccess) => singleVariableAccess.getVariable().getName()))

          //expression
          tempSet = tempSet.union(assignmnet.collect((assignmentExpression) => assignmentExpression.getRightHandSide())
            .select((operand) => operand.isInstanceOf[Expression])
            .collect((operand) => operand.asInstanceOf[Expression])
            .iterate(() => new BasicEList[String], (expression, instancesSet: EList[String]) => instancesSet.union(collectVariablesUsedInsideExpression(expression))))

        }
      }

      var iter = tempSet.iterator();
      while (iter.hasNext()) {
        myList.add(iter.next())
      }
      myList.asSet;
    }) //iterate to collect used variables

    usedInstanceVariables = methodVariablesSet.intersectForString(instanceVariablesInsideUnit).select((set) => !set.isEmpty());

    usedInstanceVariables;
  }

  def collectVariablesUsedInsideExpression(expression: Expression): EList[String] = {
    val elist = new BasicEList[InfixExpression]();
    var tempList: EList[String] = new BasicEList[String];

    if (expression.isInstanceOf[PostfixExpression]) {
      tempList.add(expression.asInstanceOf[PostfixExpression].getOperand().asInstanceOf[SingleVariableAccess].getVariable().getName())
    }

    if (expression.isInstanceOf[PrefixExpression]) {
      tempList.add(expression.asInstanceOf[PrefixExpression].getOperand().asInstanceOf[SingleVariableAccess].getVariable().getName())
    }

    //expression is leaf of AST and the leaf is a VariableAccess
    if (expression.isInstanceOf[SingleVariableAccess]) {
      tempList.add(expression.asInstanceOf[SingleVariableAccess].getVariable().getName())
    }

    //expression is somewhere inside the AST and looks like (any_expression)
    if (expression.isInstanceOf[ParenthesizedExpression]) {
      tempList = tempList.union(collectVariablesUsedInsideExpression(expression.asInstanceOf[ParenthesizedExpression].getExpression()));
    }

    //expression is somewhere inside the AST and looks like any_expression
    if (expression.isInstanceOf[InfixExpression]) {
      elist.add(expression.asInstanceOf[InfixExpression])

      tempList = tempList.union(elist.iterate(() => new BasicEList[String], (infixExpression, list: EList[String]) => {
        var mutableList: EList[String] = new BasicEList[String];

        //we have to carefully order the instanceOf tests from more specialized --> lower specialized
        //Left hand side
        if (infixExpression.getLeftOperand().isInstanceOf[InfixExpression])
          mutableList = mutableList.union(collectVariablesUsedInsideExpression(infixExpression.getLeftOperand().asInstanceOf[InfixExpression]))
        else if (infixExpression.getLeftOperand().isInstanceOf[SingleVariableAccess])
          mutableList.add(infixExpression.getLeftOperand().asInstanceOf[SingleVariableAccess].getVariable().getName())
        else if (infixExpression.getLeftOperand().isInstanceOf[Expression])
          mutableList = mutableList.union(collectVariablesUsedInsideExpression(infixExpression.getLeftOperand().asInstanceOf[Expression]));

        //Right hand side
        if (infixExpression.getRightOperand().isInstanceOf[InfixExpression])
          mutableList = mutableList.union(collectVariablesUsedInsideExpression(infixExpression.getRightOperand().asInstanceOf[InfixExpression]))
        else if (infixExpression.getRightOperand.isInstanceOf[SingleVariableAccess])
          mutableList.add(infixExpression.getRightOperand().asInstanceOf[SingleVariableAccess].getVariable().getName())
        else if (infixExpression.getRightOperand().isInstanceOf[Expression])
          mutableList = mutableList.union(collectVariablesUsedInsideExpression(infixExpression.getRightOperand().asInstanceOf[Expression]));

        //this is a bit hacky, but in scala it is impossible to define mutable function parameters and because iterate always returns the 
        //initially passed list, we have to fill it manually after finishing the recursion
        var iter = mutableList.iterator();
        while (iter.hasNext()) {
          list.add(iter.next())
        }
        list;
      }))
    }
    tempList;
  } // collectVariablesUsedInsideExpression

  /**
   * Helpermethod: Returns the filename of the Compilationunit without its extension
   * @param unit: the unit to strip
   * @return: the stripped filename
   */
  def stripCompilationUnitName(unitName: String): String = {
    return unitName.toString().split('.')(0);
  }

  /**
   * Helpermethod: Prints the given coupling in an verbose way
   * @param accessedType: the kind of coupling as a description, e.g. 'SingleVariableAccess-Expression' for a variable accessed inside an expression
   * @param accessedObjectName: the name of the used object, e.g. the variable name
   * @param usedIn: the compilationUnit using the object
   * @param declaredIn: the object where the used part is declared in
   */
  def printCboCoupling(accessedType: String, accessedObjectName: String, usedIn: String, declaredIn: String) = {
    println("[CBO-" + accessedType + "]: ---> " + accessedType + ": <" + accessedObjectName + ">"
      + " declared in: <" + declaredIn + ">"
      + " is used in: <" + usedIn + ">"
      + " and couples: <" + usedIn + "> --> <" + declaredIn + ">");
  }

  /**
   * Helpermethod: If an objectcoupling was detected, this methods checks if the coupling already exists and add a new coupling for this class otherwise
   * @param currentUnit - the unit which gets coupled by usng another component
   * @param coupledUnit - the unit defining the component
   * @param resultList - the list to collect all couples
   * @return the updated resultList
   */
  def addToCboList(currentUnit: String, coupledUnit: String, resultList: EList[ResultObject]): EList[ResultObject] = {
    val dependingUnit = resultList.select((e) => e.getFileName.equalsIgnoreCase(currentUnit));
    //current unit has already couplings if its contained inside the select-result
    if (dependingUnit.size() == 1) {
      val isCoupled = dependingUnit.get(0).getCoupledUnits.select((allreadyDetectedCouple) => allreadyDetectedCouple.equalsIgnoreCase(coupledUnit))
      //the coupled class is not part of the list yet, otherwise it's nothing to do, because classes get count only once
      if (isCoupled.size() == 0) {
        dependingUnit.get(0).getCoupledUnits.add(coupledUnit);
        dependingUnit.get(0).getValues()(0) = dependingUnit.get(0).getValues()(0) + 1;
      }
    } else {
      //the current unit has no couplings until now
      val resultObject = new ResultObject();
      val values = new ListBuffer[Double];
      values.append(1.0);
      resultObject.setValues(values);
      resultObject.setFileName(currentUnit);
      resultObject.getCoupledUnits.add(coupledUnit);
      resultList.add(resultObject);
    }
    resultList;
  }
}
