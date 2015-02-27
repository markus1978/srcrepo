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
   * @param model
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
   * @param unit
   * @return
   */
  def WmcMetricForUnit(unit: CompilationUnit): ResultObject = {
    val resultObject: ResultObject = new ResultObject();
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
  def DitMetric(model: Model): List[ResultObject] = {
    return model.getCompilationUnits().collect((unit) => {
      DitMetricForUnit(unit)
    })
  }

  /**
   * Calculates the Depth Inheritance Tree (DIT) for a compilationUnit inside a MoDisco Model.
   * @param currentUnit : the Unit to calculate
   * @return a @see{ResultObject} containing the deepest inheritance value and the name of
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
   * @param model
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
                  && foo(node.getSuperInterfaces(), currentUnit.getName().split(".java")(0))))).size();
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
   * @param model: the MoDisco model to calculate
   * @return a List of ResultObjects containing the coupling value for each compilationUnit
   */
  def CboMetric(model: Model): List[ResultObject] = {
    val cboResultList: EList[ResultObject] = new BasicEList[ResultObject];

    //as part of an Type for 'VariableDeclaration'

    model.eContents().closure((e) => e.eContents())
      .select((e) => e.isInstanceOf[VariableDeclarationStatement])
      .collect((e) => e.asInstanceOf[VariableDeclarationStatement])
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
      .select((e) => e.isInstanceOf[MethodInvocation])
      .collect((e) => e.asInstanceOf[MethodInvocation])
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
      .select((e) => e.isInstanceOf[SingleVariableAccess])
      .collect((e) => e.asInstanceOf[SingleVariableAccess]);

    //as part of an 'Statement'

    singleVariableAccess.select((s) => s.eContainer().isInstanceOf[Statement])
      //we only need those fields, which are part of an comppilationUnit not the one who are system defaults (i.e. "out" in system.out.println)
      .select((item) => item.getVariable().getOriginalCompilationUnit() != null)
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

    singleVariableAccess.select((s) => s.eContainer().isInstanceOf[Expression])
      .select((item) => item.getVariable().getOriginalCompilationUnit() != null)
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
