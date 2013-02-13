package de.hub.srcrepo

import org.eclipse.emf.common.util.EList
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration
import org.eclipse.gmt.modisco.java.CompilationUnit
import org.eclipse.gmt.modisco.java.Model

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
}