package de.hub.srcrepo.metrics

import java.util.HashMap
import java.util.HashSet
import org.eclipse.emf.ecore.EObject
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration
import org.eclipse.gmt.modisco.java.AnonymousClassDeclaration
import org.eclipse.gmt.modisco.java.ClassDeclaration
import org.eclipse.gmt.modisco.java.MethodInvocation
import org.eclipse.gmt.modisco.java.SingleVariableAccess
import org.eclipse.gmt.modisco.java.VariableDeclaration
import org.eclipse.gmt.modisco.java.emf.JavaPackage

import static extension de.hub.srcrepo.ocl.OclExtensions.*

class ModiscoMetrics {

	@Metric(name="wmc")
	static def weightedMethodsPerClass(AbstractTypeDeclaration type) {
		type.bodyDeclarations.select[it instanceof AbstractMethodDeclaration].sum[1]
	}

	@Metric(name="dit")
	static def int depthOfInheritenceTree(AbstractTypeDeclaration type) {
		val superTypes = if (type instanceof ClassDeclaration && ((type as ClassDeclaration).superClass != null)) {
				#{#{(type as ClassDeclaration).superClass}, type.superInterfaces}.flatten
			} else {
				type.superInterfaces
			}

		return if (superTypes.empty) {
			0
		} else {
			return superTypes.max [
				if (it.type instanceof AbstractTypeDeclaration) {
					return (it.type as AbstractTypeDeclaration).depthOfInheritenceTree
				} else {
					return 0
				}
			]
		}
	}

	@Metric(name="noc")
	static def int numberOfChildren(AbstractTypeDeclaration type) {
		if (type.usagesInTypeAccess.empty) {
			return 0
		} else {
			type.usagesInTypeAccess.max [
				val accessingFeature = it.eContainingFeature
				if (accessingFeature == JavaPackage::eINSTANCE.abstractTypeDeclaration_SuperInterfaces ||
					accessingFeature == JavaPackage::eINSTANCE.classDeclaration_SuperClass) {
					return (accessingFeature.eContainer as AbstractTypeDeclaration).numberOfChildren
				} else {
					return 0
				}
			]
		}
	}

	static def allSuperTypes(AbstractTypeDeclaration clazz) {
		clazz.closure [ type |
			(if (type instanceof ClassDeclaration && ((type as ClassDeclaration).superClass != null)) {
				#{#{(type as ClassDeclaration).superClass}, type.superInterfaces}.flatten
			} else {
				type.superInterfaces
			}).collect[it.type].typeSelect(typeof(AbstractTypeDeclaration))
		]
	}
	
	private static def eAllContentsWithoutAnonymousClasses(EObject container) {
		container.eAllContentsAsIterable[it instanceof AnonymousClassDeclaration]
	}

	/**
	 * Counts accesses in all contents (also inner and anonymous classes). Count accesses of member and static members.
	 * @Returns sum of count of all accesses to different classes via fields and methods in all contents of given type.
	 */
	@Metric(name="cbo")
	static def int couplingBetweenObjects(AbstractTypeDeclaration type) {
		if (type instanceof ClassDeclaration) {
			val clazz = type as ClassDeclaration
			val allContentsWithOutAnonymousClasses = clazz.bodyDeclarations
				.typeSelect((typeof(AbstractMethodDeclaration)))
				.collectAll[it.eAllContentsWithoutAnonymousClasses]
			val l1 = allContentsWithOutAnonymousClasses.typeSelect(typeof(SingleVariableAccess)).collect [
					it.variable.eTypeSelectContainer(typeof(AbstractTypeDeclaration))
				]
			val l2 = allContentsWithOutAnonymousClasses.typeSelect(typeof(MethodInvocation)).collect [
				it.method.eTypeSelectContainer(typeof(AbstractTypeDeclaration))
			]
			return l1.sum[1] + l2.sum[1]
		} else {
			return 0
		}
	}

	/**
	 * @Returns the sum of all methods in all super types that are member methods and do not override an existing method.
	 */
	@Metric(name="rfc") 
	static def int responseForClass(AbstractTypeDeclaration type) {
		// TODO The check for if a method is a non overriding method is very ugly and should be changed.
		return type.allSuperTypes.collectAll [
			it.bodyDeclarations.typeSelect(typeof(AbstractMethodDeclaration)).select [
				!it.modifier.isStatic && !it.annotations.exists[it.type.type.name.endsWith("Override")]
			]
		].sum[1]
	}
	
	private static class UnorderedPair<E> {
		val E one
		val E two
		new (E one, E two) {
			this.one = one
			this.two = two
		}
		
		override hashCode() {
			one.hashCode + two.hashCode
		}
	
		override equals(Object obj) {
			return if (obj instanceof UnorderedPair<?>) {
				val other = obj as UnorderedPair<E>
				(one.equals(other.one) && two.equals(other.two)) ||
					(two.equals(other.one) && one.equals(other.two))	
			} else {			
				false				
			}
		}		
	}
	

	/**
	 * @Returns the difference of number of member method pairs that use and use not at least one common field of the type.
	 */
	 @Metric(name="lcom") 
	static def int lackOfCohesionInMethods(AbstractTypeDeclaration type) {
		val methods = type.bodyDeclarations.typeSelect(typeof(AbstractMethodDeclaration))
		println("methods " + methods.sum[1])
		val methodToFieldsMap = methods.fold(new HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>)[result, method |
			val accessedFieldsOfType = method
				.eAllContentsWithoutAnonymousClasses
				.typeSelect(typeof(SingleVariableAccess))
				.collect[it.variable]
				.select[it.eTypeSelectContainer(typeof(AbstractTypeDeclaration)) == type]
			result.put(method, accessedFieldsOfType)
			println(method.name + "->" + accessedFieldsOfType.sum[1])
			return result
		]
		val pairsWithAccessesOfCommonFields = new HashSet<UnorderedPair<AbstractMethodDeclaration>>
		val pairsWithOutAccessesOfCommonFields = new HashSet<UnorderedPair<AbstractMethodDeclaration>>
		methods.forEach[m1 | methods.forEach[m2 | 
			if (m1 != m2) {
				if (methodToFieldsMap.get(m1).exists[field| methodToFieldsMap.get(m2).exists[it == field]]) {
					pairsWithAccessesOfCommonFields.add(new UnorderedPair<AbstractMethodDeclaration>(m1,m2))
				} else {
					pairsWithOutAccessesOfCommonFields.add(new UnorderedPair<AbstractMethodDeclaration>(m1,m2))
				}
			}
		]]
		
		val result = pairsWithOutAccessesOfCommonFields.size - pairsWithAccessesOfCommonFields.size
		println("pwcf " + pairsWithAccessesOfCommonFields.sum[1])
		println("pwocf " + pairsWithOutAccessesOfCommonFields.sum[1])
		return if (result >= 0) result else 0
	}
}