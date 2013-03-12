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

class ScalaTest {

	implicit def elistToOclList[E >: Null <: AnyRef](l: EList[E]) = new OclList[E](l)
	implicit def elistToFirst[E >: Null <: AnyRef](l: EList[E]) = {
		if (l.isEmpty()) {
			null
		} else {
			l.get(0)
		}
	}

	def checkClassExists(self: Model, name: String): Boolean = {
		self.getCompilationUnits() exists (cu =>
			cu.getTypes() exists (aType =>
				aType.getName() == name))
	}

	def findType(self: Model, name: String): AbstractTypeDeclaration = {
	  self.getOwnedElements().closure((p)=>p.getOwnedPackages())
	  	.collectAll((p)=>p.getOwnedElements())
	  	.select((at)=>at.getName().equals(name))
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
}

	