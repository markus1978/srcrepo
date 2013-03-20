package de.hub.srcrepo

import org.eclipse.emf.common.util.EList
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.Model
import de.hub.srcrepo.gitmodel.SourceRepository
import de.hub.srcrepo.gitmodel.Commit
import de.hub.srcrepo.gitmodel.ParentRelation
import de.hub.srcrepo.gitmodel.JavaDiff
import org.eclipse.gmt.modisco.java.MethodDeclaration
import org.eclipse.gmt.modisco.java.Package
import org.eclipse.gmt.modisco.java.PrimitiveTypeBoolean
import org.eclipse.gmt.modisco.java.PrimitiveType
import org.eclipse.gmt.modisco.java.Statement
import org.eclipse.gmt.modisco.java.Block
import org.eclipse.gmt.modisco.java.IfStatement
import org.eclipse.gmt.modisco.java.WhileStatement
import sun.tools.tree.CaseStatement
import org.eclipse.emf.ecore.EObject
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration

class ScalaTest {

	implicit def elistToOclList[E >: Null <: AnyRef](l: EList[E]):OclList[E] = new OclList[E](l)

	def checkClassExists(self: Model, name: String): Boolean = {
		self.getCompilationUnits() exists (cu =>
			cu.getTypes() exists (aType =>
				aType.getName() == name))
	}

	def findType(self: Model, name: String): AbstractTypeDeclaration = {
	  self.getOwnedElements().closure((p)=>p.getOwnedPackages())
	  	.collectAll((p)=>p.getOwnedElements())
	  	.select((at)=>at.getName().equals(name)).first()
	}
	
	def collectAllTypes(self: Model) = 
	  self.getOwnedElements().closure((p)=>p.getOwnedPackages())
	  	.collectAll((p)=>p.getOwnedElements())
	
	def countMethodDeclarations(self: Model): Int = 
	  self.getOwnedElements().closure((p)=>p.getOwnedPackages())
	  	.collectAll((p)=>p.getOwnedElements())
	  	.collectAll((at)=>at.getBodyDeclarations())
	  	.size()
	
	def countTypeUsages(self: Model): Int =
	  collectAllTypes(self).union(self.getOrphanTypes())
	    .collect((t)=>t.getUsagesInTypeAccess()).size()
	      
	  	
	def countTopLevelClasses(self: Model): Int = {
	  self.getOwnedElements().closure((p)=>p.getOwnedPackages())
	    .collectAll((p)=>p.getOwnedElements()).size()	  
	}
	
	def countPrimitives(self: Model): Int = {
	  self.getOrphanTypes()
	  	.collectAll((at)=>at.getUsagesInTypeAccess())
	  	.select((u)=> u != null).size()
	}
	
	def traverseJavaModelViaCU(self: Model): Boolean = {
	  self.getCompilationUnits().forAll(cu => !cu.getTypes().isEmpty())
	}
	
	def coutJavaDiffs(sr:SourceRepository): Int = {
	  sr.getAllCommits().collectAll((c)=>c.getParentRelations())
	    .collectAll((pr)=>pr.getDiffs())
	    .select((d)=>d.isInstanceOf[JavaDiff])
	    .size()
	}
	
	def mcCabeMetric(block: Block): Double = {
	  block.eContents().closure((e)=>e.eContents())
	    .select((e)=>e.isInstanceOf[Statement])
	    .collect((e)=>e.asInstanceOf[Statement])
	    .sum((s)=> 
	      if (s.isInstanceOf[IfStatement] || s.isInstanceOf[WhileStatement] || s.isInstanceOf[CaseStatement]) 1
	      else if (s.isInstanceOf[MethodDeclaration]) 1
	      else 0
	  ) + 1.0
	}
	
	def mcCabeMetric(model: Model): Double = {
	  model.eContents().closure((e)=>e.eContents())
	  	.select((e)=>e.isInstanceOf[AbstractMethodDeclaration])
	  	.collect((e)=>e.asInstanceOf[AbstractMethodDeclaration])
	  	.sum((e)=>if (e.getBody()!=null) mcCabeMetric(e.getBody()) else 0)
	}
	
	def nullMethod(model: Model): AbstractMethodDeclaration = {
	  model.eContents().closure((e)=>e.eContents())
	  	.select((e)=>e.isInstanceOf[AbstractMethodDeclaration])
	  	.collect((e)=>e.asInstanceOf[AbstractMethodDeclaration])
	  	.select((e)=>e.getBody() == null).first()
	}
	
//	def mcCabeMetric(model: Model) = {
//	  model.getOwnedElements().closure((e)=>e.getOwnedPackages())
//	    .collectAll((e)=>e.getOwnedElements())
//	    .collectAll((e)=>e.getBodyDeclarations())
//	    .closure((e)=>if (e.isInstanceOf[AbstractTypeDeclaration]) e.asInstanceOf[AbstractTypeDeclaration].getBodyDeclarations() else List())
//	    .sum(if (e.isInstanceOf[AbstractMethodDeclaration]) mcCabeMetric(e.asInstanceOf[AbstractMethodDeclaration].getBlock()) else 0)
//	}
	
//	def mcCabeMetric(block: Block) = {
//	  block.getStatements().closure((s)=>
//	    if (s.isInstanceOf[IfStatement]) {
//	      val ifStatement = s.asInstanceOf[IfStatement];
//	      List(ifStatement.getThenStatement(), ifStatement.getElseStatement())
//	    } 
//	    else if (s.isInstanceOf[Block]) s.asInstanceOf[Block].getStatements()
//	    else List()
//	  ).sum((s)=>
//	    if (s.isInstanceOf[IfStatement]) 1
//	    else if (s.isInstanceOf[WhileStatement]) 1
//	    else 0
//	  ) + 1
//	}
}

	