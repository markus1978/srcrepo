package de.hub.srcrepo.metrics

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
import org.hamcrest.core.IsInstanceOf
import de.hub.metrics.BinaryTupleType

/**
 * @author Frederik Marticke
 *
 */
class CKMetric {
  implicit def elistToOclList[E >: Null <: AnyRef](l: EList[E]): OclList[E] = new OclList[E](l)

  /**
   * @return the weighted method count (WMC) of the given class (without its subclasses) 
   * with a constant weight of 1.
   */
  def weightedMethodCalls(typeDclr: AbstractTypeDeclaration): Int = {
    return typeDclr.getBodyDeclarations
      .select((bodyDeclaration) => bodyDeclaration.isInstanceOf[AbstractMethodDeclaration])
      .size();
  }

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
   * Basically it is expected that all methods will have the same Complexity.
   * To use different weights, a concept has to be defined how this weights are tracked inside the MoDisco model.
   *
   * @param unit: the compilation unit to analyze
   * @return a ResultObject containing the WMC-Value
   */
  def WmcMetricForUnit(unit: CompilationUnit): ResultObject = {
    val resultObject: ResultObject = new ResultObject();
    resultObject.setFileName(unit.getName());

    val methodCount = unit.getTypes().closure((currentType) => currentType.getBodyDeclarations()
      .select((bodyDeclaration) => bodyDeclaration.isInstanceOf[ClassDeclaration])
      .collect((bodyDeclaration) => bodyDeclaration.asInstanceOf[ClassDeclaration])) //closure to get all classDeclaration ends
      .sum((typeDeclaration) => weightedMethodCalls(typeDeclaration))

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
   * ATTENTION: If the compilationUnit contains NESTED/INNER classes, which inherit in any way, the DITfor the current CU will be increased as well!
   *
   * @param currentUnit : the Unit to calculate
   * @return a ResultObject containing the deepest inheritance value and the name of
   * the corresponding CompilationUnit.
   */
  def DitMetricForUnit(currentUnit: CompilationUnit): ResultObject = {
    val depthOfInheritance = currentUnit
      .getTypes()
      .select((currentType) => currentType.isInstanceOf[ClassDeclarationImpl])
      .collect((classDeclarationImpl) => classDeclarationImpl.asInstanceOf[ClassDeclarationImpl])
      .closure((classDeclaration) => classDeclaration.getBodyDeclarations().select((body) => body.isInstanceOf[ClassDeclarationImpl])
        .collect((classDeclarationImpl) => classDeclarationImpl.asInstanceOf[ClassDeclarationImpl]))
      .sum((currentClass) =>
        //no superclass yields a DIT of 0
        if (currentClass.getSuperClass() == null)
          0
        else
          //In Java multiple inheritance is not allowed, but in general the longest path must be returned
          1 + DitMetricForUnit(currentClass.getSuperClass().getType().getOriginalCompilationUnit()).getValues.max)

    val depthOfInheritanceValueAsSet: ListBuffer[Double] = new ListBuffer();
    depthOfInheritanceValueAsSet.append(depthOfInheritance)
    return new ResultObject(depthOfInheritanceValueAsSet, currentUnit.getName());
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
   * TODO_update: Das Kernproblem ist der Umstand, dass das Model immer über die gesamte CommitHistorie gebaut wird. Das
   * führt dazu, dass wenn eine Interface implementierende Unit bearbeitet wird, diese im Modell 2 bzw. N - Mal auftritt und
   * der NOC Wert des Interfaces entsprechend auf N steigt, selbst wenn es nur 1 mal Implementiert wird.
   *
   * aktueller Workaround: Die Menge der gefundenen Subklassen wird über den Namen auf ein duplikatefreies Set reduziert. Das ist zwar
   * besser als alles zu zählen, da es für einzelne commits korrekt ist, aber es spiegelt dennoch nicht den Zeitverlauf, sondern nur den letzten Stand wieder.
   * Um die Entwicklung über den Zeitverlauf zu betrachten, müsste das Model pro commit analysiert werden.
   *
   *
   * //Update - Prüfen: ob das wirklich nen Problem ist, derzeit siehts so aus als obs fuktioniert
   *
   * Ausserdem wäre es toll zu wissen wie man aus dem Modell den AKTUELLEN bezeichner einer Unit erhält, denn die Liste der Interfaces
   * arbeitet auf ggf. umbenannten Units, aber die gelieferte Liste der Compilation Units auf den Namen der  ursprünglich angelegten Unit.
   * M.a.W. Eine umbenannte Unit taucht unter ihrem NEUEN Namen nicht in der erstellten .gitmodel File auf, wohl aber in der Liste der implementierten Interfaces
   * einer Unit.
   * => Problem: Wie soll man diese dann matchen??
   * --------
   * Calculates the Number of Children (NOC) for a compilationUnit inside a MoDisco Model.
   * @param currentUnit : the unit to calculate
   * @param allUnits : the corresponding modisco java model
   * @return a @see{ResultObject} containing the the number of direct subclasses for the currentUnit
   */
  def NocMetricForUnit(currentUnit: CompilationUnit, allUnits: EList[CompilationUnit]): ResultObject = {
    val resultObject: ResultObject = new ResultObject();
    resultObject.setFileName(currentUnit.getName())

    val units = allUnits
      //get all other compilationUnits
      .select((unit) => !(unit.getName().equals(currentUnit.getName())))

    val numberOfChildren =
      //all subclasses
      units.collectAll((otherUnit) => {
        otherUnit.getTypes()
          .select((currentType) => currentType.isInstanceOf[ClassDeclarationImpl])
          .collect((classDeclarationImpl) => classDeclarationImpl.asInstanceOf[ClassDeclarationImpl])
          .closure((classDeclaration) => classDeclaration.getBodyDeclarations().select((body) => body.isInstanceOf[ClassDeclarationImpl])
            .collect((classDeclarationImpl) => classDeclarationImpl.asInstanceOf[ClassDeclarationImpl]))
          .select((node) =>
            //select all classes having the current class as superClass and return the total number
            if (node.getSuperClass() != null && node.getSuperClass().getType().getOriginalCompilationUnit().getName().equals(currentUnit.getName())) {
              println("Class: <" + currentUnit.getName() + "> is SuperClass of: <" + node.getName() + ">")
              true;
            } else
              false)
      }) //avoid multiple counting of edited Units
        .collect((child) => child.getName()).asSet()

        //union Interfaces
        .union(units.collectAll((otherUnit) => {
          otherUnit.getTypes().closure((currentType) => currentType.getBodyDeclarations()
            .select((bodyDeclaration) => bodyDeclaration.isInstanceOf[ClassDeclaration])
            .collect((bodyDeclaration) => bodyDeclaration.asInstanceOf[ClassDeclaration]))
            .select((node) =>
              if (!(node.getSuperInterfaces().isEmpty()) && (node.getSuperInterfaces().collect((interface) => interface.getType())
                .collect((currentType) => currentType.getName())
                .exists((currentName) => currentName.equals(currentUnit.getTypes().collect((currentType) => currentType.getName()).first)))) {
                val interfacList = node.getSuperInterfaces().collect((interface) => interface.getType()).collect((currentType) => currentType.getName())
                println("interfaces of <" + node.getName() + ">: <" + interfacList + "> --- currentUnit: <" + stripCompilationUnitName(currentUnit.getName()) + ">")
                println("Interface: <" + stripCompilationUnitName(currentUnit.getName()) + "> gets implemented by: <" + node.getName() + ">")
                true;
              } else false);
        }) //avoid multiple counting of edited Units
          .collect((child) => child.getName()).asSet())

        //NOC = size of union 	
        .size();

    val numberOfChildrenValueAsSet: ListBuffer[Double] = new ListBuffer();
    numberOfChildrenValueAsSet.append(numberOfChildren)
    return new ResultObject(numberOfChildrenValueAsSet, currentUnit.getName());
  }

  /**
   * Calculates the Coupling Between Objects (CBO) inside a MoDisco model.
   * @param model: the MoDisco model
   * @return a List of ResultObjects containing the coupling value for each compilationUnit
   */
  def CboMetric(model: Model): List[ResultObject] = {

    //as part of an Type for 'VariableDeclaration'
    val list1 = model.eContents().closure((e) => e.eContents())
      .select((content) => content.isInstanceOf[VariableDeclarationStatement])
      .collect((varDecStatement) => varDecStatement.asInstanceOf[VariableDeclarationStatement])
      .iterate(() => new BasicEList[ResultObject], (vdStatement, cboList: EList[ResultObject]) => {
        val statementContainingUnit = vdStatement.getOriginalCompilationUnit().getName();
        //1. getType = variableAccessImpl; 2.getType = type of Variable => only interessted in not primitive types
        if (!(vdStatement.getType().getType().isInstanceOf[PrimitiveType] ||
          vdStatement.getType().getType().getName().contains("String"))) {

          printCboCoupling("VariableDeclarationType",
            stripCompilationUnitName(vdStatement.getType().getType().getName()),
            stripCompilationUnitName(statementContainingUnit),
            stripCompilationUnitName(vdStatement.getType().getType().getName()))

          /**
           * addToCboList(stripCompilationUnitName(statementContainingUnit), stripCompilationUnitName(vdStatement.getType().getType().getName()), cboList);
           *
           *  For the sake of demonstration, the addToCboList method was removed here, and replaced by its implementation to show that its just a refactoring
           */

          val dependingUnit = cboList.select((e) => e.getFileName.equalsIgnoreCase(stripCompilationUnitName(statementContainingUnit)));
          //current unit has already couplings if its contained inside the select-result
          if (dependingUnit.size() == 1) {
            val isCoupled = dependingUnit.first().getCoupledUnits.select((allreadyDetectedCouple) => allreadyDetectedCouple.equalsIgnoreCase(stripCompilationUnitName(vdStatement.getType().getType().getName())))
            //the coupled class is not part of the list yet, otherwise it's nothing to do, because classes get count only once
            if (isCoupled.size() == 0) {
              dependingUnit.first.getCoupledUnits.add(stripCompilationUnitName(vdStatement.getType().getType().getName()));
              dependingUnit.first.getValues()(0) = dependingUnit.first().getValues()(0) + 1;
            }
          } else {
            //the current unit has no couplings until now	
            //it seems impossible to create a 'special' kind of List, and initialize it with values at the same time, like possible in Ocl when creating a new tuple		      
            val valueBuffer = new ListBuffer[Double];
            valueBuffer.append(1.0);
            val couplings = new BasicEList[String];
            couplings.add(vdStatement.getType().getType().getName());

            cboList.add(new ResultObject(valueBuffer, couplings, stripCompilationUnitName(statementContainingUnit)));
          }
        }
        cboList;
      });

    //as part of an 'MethodInvocation'
    val list2 = model.eContents().closure((e) => e.eContents())
      .select((content) => content.isInstanceOf[MethodInvocation])
      .collect((methodInvocation) => methodInvocation.asInstanceOf[MethodInvocation])
      .iterate(() => list1, (method, cboList: EList[ResultObject]) => {
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
        cboList;
      });

    //reusable, see below
    val singleVariableAccess = model.eContents().closure((e) => e.eContents())
      .select((content) => content.isInstanceOf[SingleVariableAccess])
      .collect((singleVarAccess) => singleVarAccess.asInstanceOf[SingleVariableAccess]);

    //as part of an 'Statement'
    val list3 = singleVariableAccess.select((singleVarAccess) => singleVarAccess.eContainer().isInstanceOf[Statement])
      //we only need those fields, which are part of an comppilationUnit not the one who are system defaults (i.e. "out" in system.out.println)
      .select((statement) => statement.getVariable().getOriginalCompilationUnit() != null)
      .iterate(() => list2, (variable, cboList: EList[ResultObject]) => {
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
        cboList;
      });

    //as part of an 'Expression'   
    val list4 = singleVariableAccess.select((singleVarAccess) => singleVarAccess.eContainer().isInstanceOf[Expression])
      .select((expression) => expression.getVariable().getOriginalCompilationUnit() != null)
      .iterate(() => list3, (variable, cboList: EList[ResultObject]) => {
        val declaredIn = variable.getVariable().getOriginalCompilationUnit().getName();
        val usedIn = variable.eContainer().asInstanceOf[Expression].getOriginalCompilationUnit().getName();
        if (!(declaredIn.equalsIgnoreCase(usedIn))) {
          printCboCoupling("SingleVariableAccess-Expression",
            stripCompilationUnitName(variable.getVariable().getName()),
            stripCompilationUnitName(usedIn),
            stripCompilationUnitName(declaredIn))

          addToCboList(stripCompilationUnitName(usedIn), stripCompilationUnitName(declaredIn), cboList)
        }
        cboList;
      });

    //as part of an 'MethodReturnType'
    val list5 = model.eContents().closure((e) => e.eContents())
      .select((content) => content.isInstanceOf[MethodDeclaration])
      .collect((content) => content.asInstanceOf[MethodDeclaration])
      .select((methodDeclaration) => methodDeclaration.getReturnType != null)
      .iterate(() => list4, (method, cboList: EList[ResultObject]) => {
        if (method.getReturnType().getType().isInstanceOf[ClassDeclaration]
          && method.getReturnType().getType().asInstanceOf[ClassDeclaration].getName() != "String"
          && method.getReturnType().getType().asInstanceOf[ClassDeclaration].getName() != "Object") {
          printCboCoupling("non primitive Returntyppe",
            stripCompilationUnitName(method.getReturnType().getType().asInstanceOf[ClassDeclaration].getName()),
            stripCompilationUnitName(method.getName()),
            stripCompilationUnitName(method.getReturnType().getType().asInstanceOf[ClassDeclaration].getName()))

          addToCboList(stripCompilationUnitName(method.getOriginalCompilationUnit().getName()), stripCompilationUnitName(method.getReturnType().getType().asInstanceOf[ClassDeclaration].getName()), cboList)
        }
        cboList;
      });

    //as part of an 'Method Parameter'
    val list6 = model.eContents().closure((e) => e.eContents())
      .select((content) => content.isInstanceOf[MethodDeclaration])
      .collect((content) => content.asInstanceOf[MethodDeclaration])
      .select((methodDeclaration) => methodDeclaration.getParameters() != null)
      .iterate(() => list5, (method, cboList: EList[ResultObject]) => {
        val parameter = method.getParameters();
        parameter.iterate(() => cboList, (param, innerList: EList[ResultObject]) => {
          if (param.getType().getType().isInstanceOf[ClassDeclaration]
            && param.getType().getType().asInstanceOf[ClassDeclaration].getName() != "String"
            && param.getType().getType().asInstanceOf[ClassDeclaration].getName() != "Object") {
            printCboCoupling("non primitive parameter",
              stripCompilationUnitName(param.getType().getType().asInstanceOf[ClassDeclaration].getName()),
              stripCompilationUnitName(method.getName()),
              stripCompilationUnitName(param.getType().getType().asInstanceOf[ClassDeclaration].getName()))

            addToCboList(stripCompilationUnitName(method.getOriginalCompilationUnit().getName()), stripCompilationUnitName(param.getType().getType().asInstanceOf[ClassDeclaration].getName()), cboList)
          }
          innerList;
        })

        cboList;
      });

    /**
     * This was initially part of the calculation, but in fact it is not part of the CBO definition and already covered by the DIT and NOC values.
     * Furthermore, to be consequent you would have to count inheritance as well and would have to decide for how many levels those couplings
     * should be considered.
     *
     * //Interface Implementation
     * val list5 = model.getCompilationUnits()
     * .collectAll((unit) =>
     * unit.getTypes()
     * .select((currentType) => currentType.isInstanceOf[ClassDeclarationImpl])
     * .collect((classDeclarationImpl) => classDeclarationImpl.asInstanceOf[ClassDeclarationImpl])
     * .select((classDeclarationWithInterface) => !classDeclarationWithInterface.getSuperInterfaces().isEmpty()))
     *
     * .iterate(() => list4, (classDec, cboList: EList[ResultObject]) => {
     * classDec.getSuperInterfaces().iterate(() => cboList, (interface, resList: EList[ResultObject]) => {
     * printCboCoupling("Interface-Implementation",
     * stripCompilationUnitName(interface.getType().getName()),
     * stripCompilationUnitName(classDec.getName()),
     * stripCompilationUnitName(interface.getType().getName()))
     *
     * addToCboList(stripCompilationUnitName(classDec.getName()), stripCompilationUnitName(interface.getType().getName()), resList)
     * })
     * })
     */
    return list6;
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
      /** this select decides if library methods are count or not, because those will not have an accessable compilationUnit */
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
  def RfcMetricForUnit(currentUnit: CompilationUnit, methodInvocations: EList[MethodInvocation]): ResultObject = {
    //This would be the {M} - Set according to C. & K.
    val methodsInsideClass = currentUnit.getTypes
      // get the 'Body Declarations' containment reference list for all Types
      .collectAll((currentType) => currentType.getBodyDeclarations())
      //select only the MethodDeclarationImpl from the Body Declarations
      .select((bodyDeclarations) => bodyDeclarations.isInstanceOf[MethodDeclarationImpl])
      .collect((methodDeclarationImpl) => methodDeclarationImpl.asInstanceOf[MethodDeclarationImpl])

    //the whole set is the {R}-Set as union of all method calls for this Unit according to C. & K.
    val calledMethods = methodInvocations.select((method) =>
      method.getOriginalCompilationUnit().equals(currentUnit))

    //The RFC-Value is the size of the union of {M} & {R} 
    val responseForClass = methodsInsideClass.union(calledMethods.asInstanceOf[EList[ASTNode]]);

    val responseForClassValueAsSet: ListBuffer[Double] = new ListBuffer();
    responseForClassValueAsSet.append(responseForClass.size())
    return new ResultObject(responseForClassValueAsSet, currentUnit.getName());

  }

  /**
   * Calculates the Lack Of Cohesion in Methods (LCOM) inside a MoDisco model.
   * @param model: the MoDisco model
   * @return a List of ResultObjects containing the coupling value for each compilationUnit
   */
  def LcomMetric(model: Model): List[ResultObject] = {
    val compilationUnits = model.getCompilationUnits()
    compilationUnits.collect((unit) => {
      LcomMetricForUnit(unit)
    })
  }

  /**
   * Calculates the Lack Of Cohesion in Methods (LCOM) for a compilationUnit inside a MoDisco Model.
   * @param unit: the compilation unit to analyze
   * @return a ResultObject containing the LCOM-Value
   */
  def LcomMetricForUnit(currentUnit: CompilationUnit): ResultObject = {

    //get the 'Types' reference list for the current Unit
    val methodsInsideUnit = currentUnit.getTypes
      // get the 'Body Declarations' containment reference list for all Types
      //filter all empty classes
      .select((currentType) => !(currentType.getBodyDeclarations().isEmpty()))
      .collectAll((currentType) => currentType.getBodyDeclarations())
      //select only the AbstractMethodDeclarations from the Body Declarations
      .select((bodyDeclaration) => bodyDeclaration.isInstanceOf[AbstractMethodDeclaration])
      .collect((bodyDeclaration) => bodyDeclaration.asInstanceOf[AbstractMethodDeclaration])
      //e. g. implicit declared construtor methods have an empty body
      .select((method) => method.getBody() != null && method.getBody().getStatements() != null)

    val instanceVariablesInsideUnit = currentUnit.getTypes
      // get the 'Body Declarations' containment reference list for all Types
      .collectAll((currentType) => currentType.getBodyDeclarations())
      //select only the FieldDeclaration representing class instance variables from the Body Declarations
      .select((bodyDeclaration) => bodyDeclaration.isInstanceOf[FieldDeclaration])
      .collect((bodyDeclaration) => bodyDeclaration.asInstanceOf[FieldDeclaration])
      .collect((fieldDeclaration) => fieldDeclaration.getFragments().first().getName())

    ////This would be the {{I1}, ... ,{In}} - Set according to C. & K. (with 1 <= i <= n if there are n Methods in this class)
    val SetOfUsedInstanceVariablesSets = methodsInsideUnit.iterate(() => new BasicEList[EList[String]], (method, variablesSet: BasicEList[EList[String]]) => {
      variablesSet.add(analyseMethodForLcom(method, instanceVariablesInsideUnit))
      variablesSet;
    })

    //the set of null intersections
    val P = SetOfUsedInstanceVariablesSets.iterate(() => new BasicEList[BinaryTupleType], (setI, listP: EList[BinaryTupleType]) => {
      SetOfUsedInstanceVariablesSets.iterate(() => listP, (otherSetI, otherListP: EList[BinaryTupleType]) => {
        //avoid tuple with empty/null items and avoid reflexive tuple, i.e. don't calculate intersection(setI, setI)
        if ((!otherSetI.isEmpty()) && (!setI.isEmpty()) && (otherSetI ne setI) && (setI.intersectForString(otherSetI).isEmpty()))
          otherListP.add(new BinaryTupleType(setI, otherSetI))
        otherListP;
      })
      listP;
    })

    //the set of nonempty intersections
    val Q = SetOfUsedInstanceVariablesSets.iterate(() => new BasicEList[BinaryTupleType], (setI, listQ: EList[BinaryTupleType]) => {
      SetOfUsedInstanceVariablesSets.iterate(() => listQ, (otherSetI, otherListQ: EList[BinaryTupleType]) => {
        if ((!otherSetI.isEmpty()) && (!setI.isEmpty()) && (otherSetI ne setI) && !(setI.intersectForString(otherSetI).isEmpty()))
          otherListQ.add(new BinaryTupleType(setI, otherSetI))
        otherListQ;
      })
      listQ;
    })

    //LCOM = number of nullintersections - number of nonempty intersections if P > Q, else 0
    if (P.size() > Q.size()) {
      val LackOfCohesionValueAsSet: ListBuffer[Double] = new ListBuffer();
      LackOfCohesionValueAsSet.append((P.size() - Q.size()) / 2.0) //The Sets intersection(Mi, C) include for each pair (i,j) the symmetric pair (j,i). For LCOM one Pair is enough, so the metric is the half of the calculated value     
      return new ResultObject(LackOfCohesionValueAsSet, currentUnit.getName());
    } else {
      val LackOfCohesionValueAsSet: ListBuffer[Double] = new ListBuffer();
      LackOfCohesionValueAsSet.append(0.0)
      return new ResultObject(LackOfCohesionValueAsSet, currentUnit.getName());
    }
  }

  //########################################
  //#			Helpermethods			   #
  //########################################

  /**
   * Helpermethod: Returns a list of names of all variables, which are used inside the analyzed methods and which are class instance variables as well.
   * The set of class instance variables has to be passed as argument
   * @param method: the method to analyze
   * @param instanceVariablesInsideUnit: the set of class instance variables
   * @return a list of names of used class instance variables
   */
  def analyseMethodForLcom(method: AbstractMethodDeclaration, instanceVariablesInsideUnit: EList[String]): EList[String] = {

    val methodVariablesSet: EList[String] = method.getBody().getStatements().iterate(() => new BasicEList[String], (statement, resultList: EList[String]) => {

      /**
       * Because the Model already defines the kind of the statement as ifstatement, dostatement... we could check the kind inside ONE if like
       * (statement.isInstanceOf[IfStatement] || statement.isInstanceOf[DoStatement] || ...) but we cannot use the same CAST for each of them.
       * i.e. statementAsSet.collect((expr) => expr.asInstanceOf[IfStatement]) on a doStatement leads to an invalidCastExpception.
       */

      //if Statement
      /**
       * the extended version as proof of concept, the following ones are shortend with addToIteratorAccu.
       *
       * Note: Even if it looks quite similar, resultList.union(...) is not a good way, because it always returns a new List, which is basically possible
       * to handle, but more complicated, if you only use 'val' and no 'var' declarations.
       * Also a straight return statement like 'return resultList.union(...)' does not work, because it not only returns the current iteration, but
       * the whole methodcall => the whole iterate gets canceled before all statements were analyzed.
       */
      if (statement.isInstanceOf[IfStatement]) {
        collectVariablesUsedInsideExpression(statement.asInstanceOf[IfStatement].getExpression())
          .iterate(() => resultList, (statement, resultList2: EList[String]) => {
            resultList2.add(statement)
            resultList2
          });
      } //do Statement
      else if (statement.isInstanceOf[DoStatement]) {
        addToIteratorAccu(collectVariablesUsedInsideExpression(statement.asInstanceOf[DoStatement].getExpression()), resultList);
      } //while Statement
      else if (statement.isInstanceOf[WhileStatement]) {
        addToIteratorAccu(collectVariablesUsedInsideExpression(statement.asInstanceOf[WhileStatement].getExpression()), resultList);
      } //return statement
      else if (statement.isInstanceOf[ReturnStatement]) {
        addToIteratorAccu(collectVariablesUsedInsideExpression(statement.asInstanceOf[ReturnStatement].getExpression()), resultList);
      } //variable declaration statement
      else if (statement.isInstanceOf[VariableDeclarationStatement]) {
        addToIteratorAccu(statement.asInstanceOf[VariableDeclarationStatement].getFragments()
          .iterate(() => new BasicEList[String], (variableDeclaration, instancesSet: EList[String]) =>
            instancesSet.union(collectVariablesUsedInsideExpression(variableDeclaration.getInitializer()))), resultList)
      } //prefix and postfix Expression
      else if (statement.isInstanceOf[ExpressionStatement] &&
        (statement.asInstanceOf[ExpressionStatement].getExpression().isInstanceOf[PostfixExpression] || statement.asInstanceOf[ExpressionStatement].getExpression().isInstanceOf[PrefixExpression])) {
        addToIteratorAccu(collectVariablesUsedInsideExpression(statement.asInstanceOf[ExpressionStatement].getExpression()), resultList);
      } //assignment Expression         
      else if (statement.isInstanceOf[ExpressionStatement] &&
        statement.asInstanceOf[ExpressionStatement].getExpression().isInstanceOf[Assignment]) {

        //equivalent to ocl shorthand: assignmentElement->...do something with statement in a set
        val assignmentAsSet = new BasicEList[Assignment];
        assignmentAsSet.add(statement.asInstanceOf[ExpressionStatement].getExpression().asInstanceOf[Assignment]);

        //left hand side always should be a var or val
        if (!assignmentAsSet.isEmpty()) {
          val LHS = assignmentAsSet.collect((assignmentExpression) => assignmentExpression.getLeftHandSide())
            .select((operand) => operand.isInstanceOf[SingleVariableAccess])
            .collect((operand) => operand.asInstanceOf[SingleVariableAccess]);
          if (LHS.size() == 1) {
            resultList.add(LHS.first().getVariable().getName())
          }

          //right hand side either can be an expression, literal or variable. We are only interessted in expressions and variables
          //variable
          val RHSVariable = assignmentAsSet.collect((assignmentExpression) => assignmentExpression.getRightHandSide())
            .select((operand) => operand.isInstanceOf[SingleVariableAccess])
            .collect((operand) => operand.asInstanceOf[SingleVariableAccess])
          if (RHSVariable.size() == 1) {
            resultList.add(RHSVariable.first().getVariable().getName())
          }

          //expression
          addToIteratorAccu(collectVariablesUsedInsideExpression(assignmentAsSet.collect((assignmentExpression) =>
            assignmentExpression.getRightHandSide())
            .select((operand) => operand.isInstanceOf[Expression])
            .collect((operand) => operand.asInstanceOf[Expression])
            .first()), resultList);
        }
      }
      resultList;
    }) //iterate to collect used variables

    methodVariablesSet.intersectForString(instanceVariablesInsideUnit).select((set) => !set.isEmpty());
  }

  /**
   * Helpermethod: If inside an Iterate method a new Collection is built, which items has to be added to the resultSet of the iterate, the accu and the new collection can be passed to
   * copy all items from the new collection to the resultSet.
   * @param itemsToAdd - the new collection
   * @param accu - the accu of the iterate method
   * @return the accu extended by the new items.
   */
  def addToIteratorAccu(itemsToAdd: EList[String], accu: EList[String]): EList[String] = {
    itemsToAdd.iterate(() => accu, (statement, resultList2: EList[String]) => {
      resultList2.add(statement)
      resultList2
    });
  }

  /**
   * Helpermethod: Returns a List of Names, containing all used variables of this expression. If a further expression is part of the current expression,
   * this gets analyzed as well, i. e. foo = (var1+4)-var5 returns [var1, var5] if the right hand side is given.
   * @param expression: the expression to analyze
   * @return a list of names of the used variables
   */
  def collectVariablesUsedInsideExpression(expression: Expression): EList[String] = {

    val tempList: EList[String] = new BasicEList[String];

    if (expression.isInstanceOf[PostfixExpression]) {
      val list: EList[String] = new BasicEList[String];
      list.add(expression.asInstanceOf[PostfixExpression].getOperand().asInstanceOf[SingleVariableAccess].getVariable().getName())
      list;
    } else if (expression.isInstanceOf[PrefixExpression]) {
      val list: EList[String] = new BasicEList[String];
      list.add(expression.asInstanceOf[PrefixExpression].getOperand().asInstanceOf[SingleVariableAccess].getVariable().getName())
      list;
    } //expression is leaf of AST and the leaf is a VariableAccess
    else if (expression.isInstanceOf[SingleVariableAccess]) {
      val list: EList[String] = new BasicEList[String];
      list.add(expression.asInstanceOf[SingleVariableAccess].getVariable().getName())
      list;
    } //expression is somewhere inside the AST and looks like (any_expression)
    else if (expression.isInstanceOf[ParenthesizedExpression]) {
      val list: EList[String] = new BasicEList[String];
      list.union(collectVariablesUsedInsideExpression(expression.asInstanceOf[ParenthesizedExpression].getExpression()));
    } //expression is somewhere inside the AST and looks like any_expression    
    else if (expression.isInstanceOf[InfixExpression]) {

      val infixExpression = expression.asInstanceOf[InfixExpression];
      val result: EList[String] = new BasicEList[String];

      //we have to carefully order the instanceOf tests from more specialized --> lower specialized
      //Left hand side
      if (infixExpression.getLeftOperand().isInstanceOf[InfixExpression]) {
        addToIteratorAccu(collectVariablesUsedInsideExpression(infixExpression.getLeftOperand().asInstanceOf[InfixExpression]), result);
      } else if (infixExpression.getLeftOperand().isInstanceOf[SingleVariableAccess]) {
        result.add(infixExpression.getLeftOperand().asInstanceOf[SingleVariableAccess].getVariable().getName())
      } else if (infixExpression.getLeftOperand().isInstanceOf[Expression]) {
        addToIteratorAccu(collectVariablesUsedInsideExpression(infixExpression.getLeftOperand().asInstanceOf[Expression]), result);
      }

      //Right hand side 
      if (infixExpression.getRightOperand().isInstanceOf[InfixExpression]) {
        addToIteratorAccu(collectVariablesUsedInsideExpression(infixExpression.getRightOperand().asInstanceOf[InfixExpression]), result);
      } else if (infixExpression.getRightOperand().isInstanceOf[SingleVariableAccess]) {
        result.add(infixExpression.getRightOperand().asInstanceOf[SingleVariableAccess].getVariable().getName())
      } else if (infixExpression.getRightOperand().isInstanceOf[Expression]) {
        addToIteratorAccu(collectVariablesUsedInsideExpression(infixExpression.getRightOperand().asInstanceOf[Expression]), result);
      }
      result;
    } else
      new BasicEList[String];
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
      val isCoupled = dependingUnit.first().getCoupledUnits.select((allreadyDetectedCouple) => allreadyDetectedCouple.equalsIgnoreCase(coupledUnit))
      //the coupled class is not part of the list yet, otherwise it's nothing to do, because classes get count only once
      if (isCoupled.size() == 0) {
        dependingUnit.first().getCoupledUnits.add(coupledUnit);
        dependingUnit.first().getValues()(0) = dependingUnit.first().getValues()(0) + 1;
      }
    } else {
      //the current unit has no couplings until now      
      val values = new ListBuffer[Double];
      val coupledUnits = new BasicEList[String];
      values.append(1.0);
      coupledUnits.add(coupledUnit);
      resultList.add(new ResultObject(values, coupledUnits, currentUnit));
    }
    resultList;
  }
}
