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

	def selectType(self: Model, name: String): AbstractTypeDeclaration = {
		self.getCompilationUnits() collect (cu =>
			cu.getTypes() select (aType =>
				aType.getName() == name))
	}
	
	def countJavaTypeDefs(self: SourceRepository): Int = {
		self.getAllCommits().aggregate(0, (c,t:Int) => 
		  t + c.getParentRelations().aggregate(0, (pr,t:Int) =>
		    t + pr.getDiffs().aggregate(0, (diff,t:Int) =>
		      if (diff.isInstanceOf[JavaDiff])
		    	  t + diff.asInstanceOf[JavaDiff].getCompilationUnit().getTypes().aggregate(0, (aType,t:Int) =>
		    	    aType.getBodyDeclarations().aggregate(0, (bd,t:Int) => 
		    	      if (bd.isInstanceOf[MethodDeclaration]) 1 else 0))
		      else 0)));
	}
}