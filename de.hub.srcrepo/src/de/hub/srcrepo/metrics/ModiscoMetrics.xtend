package de.hub.srcrepo.metrics

import java.lang.reflect.Method
import java.util.HashMap
import java.util.HashSet
import org.eclipse.emf.ecore.EObject
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration
import org.eclipse.gmt.modisco.java.AnonymousClassDeclaration
import org.eclipse.gmt.modisco.java.Block
import org.eclipse.gmt.modisco.java.BooleanLiteral
import org.eclipse.gmt.modisco.java.CatchClause
import org.eclipse.gmt.modisco.java.CharacterLiteral
import org.eclipse.gmt.modisco.java.ClassDeclaration
import org.eclipse.gmt.modisco.java.DoStatement
import org.eclipse.gmt.modisco.java.FieldDeclaration
import org.eclipse.gmt.modisco.java.ForStatement
import org.eclipse.gmt.modisco.java.IfStatement
import org.eclipse.gmt.modisco.java.MethodInvocation
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.NumberLiteral
import org.eclipse.gmt.modisco.java.SingleVariableAccess
import org.eclipse.gmt.modisco.java.StringLiteral
import org.eclipse.gmt.modisco.java.SwitchStatement
import org.eclipse.gmt.modisco.java.Type
import org.eclipse.gmt.modisco.java.TypeAccess
import org.eclipse.gmt.modisco.java.VariableDeclaration
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment
import org.eclipse.gmt.modisco.java.VisibilityKind
import org.eclipse.gmt.modisco.java.WhileStatement
import org.eclipse.gmt.modisco.java.emf.JavaPackage

import static extension de.hub.srcrepo.metrics.AnalysisVisitor.*
import static extension de.hub.srcrepo.ocl.OclExtensions.*
import java.util.List
import java.util.ArrayList

class ModiscoMetrics {

	/**
	 * Calculates the Weighted number of Methods per Class (WMC) with a constant weight of 1.
	 * @param type Is the type that constitutes the "Class" that this metric is applied to.
	 * @returns the number of method declarations in a given type. All method declarations declared within the type are counted.
	 */
	@Metric(name="wmc")
	static def weightedMethodsPerClass(AbstractTypeDeclaration type) {
		type.weightedMethodsPerClass[1]
	}

	static def weightedMethodsPerClass(AbstractTypeDeclaration type, (Block)=>Integer weight) {
		type.bodyDeclarations.typeSelect(typeof(AbstractMethodDeclaration)).sum [
			if (it.body != null) {
				weight.apply(it.body)
			} else {
				1
			}
		]
	}

	/** 
	 * Calculates the Depth of Inheritance Tree (DIT) for a given type. The type it self is not counted. 
	 * All super types and interfaces as defined within MoDisco's semantics are counted. This should
	 * leave out Java's default super type Object. 
	 * @param type Is the type that this metric is applied to.
	 * @returns the DIT of the given type.
	 */
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
					return 1 + (it.type as AbstractTypeDeclaration).depthOfInheritenceTree
				} else {
					return 0
				}
			]
		}
	}

	/**
	 * Calculates the Number Of Children (NOC) metrik for a given type. NOC is the number of direct sub-types.
	 * This implementation counts references based on MoDiscos abstractTypeDeclaration_SuperInterfaces
	 * and classDeclaration_SuperClass references.
	 * @param type Is the type that this metric is applied to.
	 * @returns the NOT of the given type.
	 */
	@Metric(name="noc")
	static def int numberOfChildren(AbstractTypeDeclaration type) {
		if (type.usagesInTypeAccess.empty) {
			return 0
		} else {
			type.usagesInTypeAccess.sum [
				val accessingFeature = it.eContainingFeature
				if (accessingFeature == JavaPackage::eINSTANCE.abstractTypeDeclaration_SuperInterfaces ||
					accessingFeature == JavaPackage::eINSTANCE.classDeclaration_SuperClass) {
					return 1
				} else {
					return 0
				}
			]
		}
	}

	private static def allSuperTypes(AbstractTypeDeclaration clazz) {
		clazz.closure [ type |
			(if (type instanceof ClassDeclaration && ((type as ClassDeclaration).superClass != null)) {
				#{#{(type as ClassDeclaration).superClass}, type.superInterfaces}.flatten
			} else {
				type.superInterfaces
			}).collect[it.type].typeSelect(typeof(AbstractTypeDeclaration))
		]
	}

	private static def eAllContentsWithoutAnonymousClasses(EObject container) {
		container.eAllContentsAsIterable[!(it instanceof AnonymousClassDeclaration)]
	}

	/**
	 * Calculates the Coupling Between Object (CBO) metric. Counts accesses of members and not of static members.
	 * @param type Is the type that this metric is applied to.
	 * @Returns sum of count of all accesses to different classes via fields and methods in all contents of given type.
	 */
	@Metric(name="cbo")
	static def int couplingBetweenObjects(AbstractTypeDeclaration type) {
		if (type instanceof ClassDeclaration) {
			val clazz = type as ClassDeclaration
			val allContentsWithOutAnonymousClasses = clazz.bodyDeclarations.typeSelect((
				typeof(AbstractMethodDeclaration))).collectAll[it.eAllContentsWithoutAnonymousClasses]

			val coupledTypes = new HashSet<AbstractTypeDeclaration>();
			coupledTypes.addAll(
				allContentsWithOutAnonymousClasses.typeSelect(typeof(SingleVariableAccess)).collect[it.variable].
					typeSelect(typeof(VariableDeclarationFragment)).collect[it.variablesContainer].typeSelect(
						typeof(FieldDeclaration)).select[it.modifier != null && !it.modifier.isStatic].collect [
						it.eTypeSelectContainer(typeof(AbstractTypeDeclaration))
					].select[it != type]
			)

			coupledTypes.addAll(
				allContentsWithOutAnonymousClasses.typeSelect(typeof(MethodInvocation)).collect[it.method].select [
					it.modifier != null && !it.modifier.isStatic
				].collect[it.eTypeSelectContainer(typeof(AbstractTypeDeclaration))].select[it != type]
			)

			return coupledTypes.size
		} else {
			return 0
		}
	}
	
	static def List<AbstractTypeDeclaration> dependencies(AbstractTypeDeclaration source) {
		val dependencies = new ArrayList<AbstractTypeDeclaration>()
		val allContentsWithOutAnonymousClasses = #[source].collectAll[it.eAllContentsWithoutAnonymousClasses]
		dependencies.addAll(allContentsWithOutAnonymousClasses
			.typeSelect(typeof(MethodInvocation))
			.collect[it.method?.eTypeSelectContainer(typeof(AbstractTypeDeclaration))].filter[it != null && it != source]			
		)
		dependencies.addAll(allContentsWithOutAnonymousClasses
			.typeSelect(typeof(SingleVariableAccess)).collect[it.variable]
			.typeSelect(typeof(VariableDeclarationFragment)).collect[it.variablesContainer]
			.typeSelect(typeof(FieldDeclaration))
			.collect [
				it.eTypeSelectContainer(typeof(AbstractTypeDeclaration))
			].select[it != null && it != source]
		)
		
		dependencies.toList
	}
	
	static def List<AbstractMethodDeclaration> calledMethods(AbstractMethodDeclaration source) {
		val allContentsWithOutAnonymousClasses = #[source].collectAll[it.eAllContentsWithoutAnonymousClasses]
		allContentsWithOutAnonymousClasses.typeSelect(typeof(MethodInvocation)).collect[it.method].filter[it != null].toList
	}
	
	static def List<AbstractMethodDeclaration> callingMethods(AbstractMethodDeclaration target) {
		return target.usages.map[eTypeSelectContainer(typeof(AbstractMethodDeclaration))]
	}

	private static def qualifiedName(Type element) {
		element.eAllContainer[it instanceof NamedElement].collect[(it as NamedElement).name].toList.reverse.join(".")
	}

	private static def signature(AbstractMethodDeclaration method) {
		return '''«method.name»@(«method.parameters.collect[type.type.qualifiedName].join(",")»)'''
	}

	/**
	 * Calculates the Response For a Class (RFC) metrik that is the count of all member methods in the type's interface.
	 * I.e., the sum of all methods in all super types that are member methods and do not override an existing method.
	 * @param type Is the type that this metric is applied to.
	 * @returns the RFC of the given type.
	 */
	@Metric(name="rfc")
	static def int responseForClass(AbstractTypeDeclaration type) {
		return type.allSuperTypes.collectAll [
			it.bodyDeclarations.typeSelect(typeof(AbstractMethodDeclaration)).select [
				it.modifier != null && !it.modifier.isStatic &&
					(it.modifier.visibility == VisibilityKind.PUBLIC || it.modifier.visibility == VisibilityKind.NONE)
			].collect[it.signature]
		].toSet.size
	}

	private static class UnorderedPair<E> {
		val E one
		val E two

		new(E one, E two) {
			this.one = one
			this.two = two
		}

		override hashCode() {
			one.hashCode + two.hashCode
		}

		override equals(Object obj) {
			return if (obj instanceof UnorderedPair<?>) {
				val other = obj as UnorderedPair<E>
				(one.equals(other.one) && two.equals(other.two)) || (two.equals(other.one) && one.equals(other.two))
			} else {
				false
			}
		}
	}

	/**
	 * Calculates the Lack of Cohesion Of Methods (LCOM) metrik. The difference of number of member method pairs that use 
	 * and use not at least one common field of the type.   
	 * @param type Is the type that this metric is applied to.
	 * @Returns the LCOM number of the given type or 0 if the LCOM number is negative.
	 */
	@Metric(name="lcom")
	static def int lackOfCohesionInMethods(AbstractTypeDeclaration type) {
		val methods = type.bodyDeclarations.typeSelect(typeof(AbstractMethodDeclaration))
		val methodToFieldsMap = methods.fold(new HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>) [ result, method |
			val accessedFieldsOfType = method.eAllContentsWithoutAnonymousClasses.typeSelect(
				typeof(SingleVariableAccess)).collect[it.variable].select [
				it.eTypeSelectContainer(typeof(AbstractTypeDeclaration)) == type
			]
			result.put(method, accessedFieldsOfType)
			return result
		]
		val pairsWithAccessesOfCommonFields = new HashSet<UnorderedPair<AbstractMethodDeclaration>>
		val pairsWithOutAccessesOfCommonFields = new HashSet<UnorderedPair<AbstractMethodDeclaration>>
		methods.forEach [ m1 |
			methods.forEach [ m2 |
				if (m1 != m2) {
					if (methodToFieldsMap.get(m1).exists[field|methodToFieldsMap.get(m2).exists[it == field]]) {
						pairsWithAccessesOfCommonFields.add(new UnorderedPair<AbstractMethodDeclaration>(m1, m2))
					} else {
						pairsWithOutAccessesOfCommonFields.add(new UnorderedPair<AbstractMethodDeclaration>(m1, m2))
					}
				}
			]
		]

		val result = pairsWithOutAccessesOfCommonFields.size - pairsWithAccessesOfCommonFields.size
		return if(result >= 0) result else 0
	}

	private static class Value<T> {
		var T value

		new(T value) {
			this.value = value
		}
	}

	/**
	 * Calculates the cyclomatic complexity. If/for/while etc expressions are not analysed, they all count as 1.
	 * @param The block to compute the cyclomatic complexity for.
	 * @returns the cyclomatic complexity of the given block
	 */
	@Metric(name="cc")
	static def int cyclomaticComplexity(Block block) {
		block.eAllContentsAsIterable.sum [
			return switch it {
				IfStatement:
					1
				SwitchStatement:
					if (it.statements.size > 1) it.statements.size - 1 else 1
				ForStatement:
					1
				WhileStatement:
					1
				CatchClause:
					1
				DoStatement:
					1
				MethodInvocation:
					1
				default:
					0
			}
		] + 1 // return statement counts
	}
	
	static class HalsteadValues {
		var instances = 0
		var classes = newHashSet()
	}

	@Metric(name="halstead")
	static def int halsteadValume(Block block) {
		// Rationale: Java metamodel classes represent operators and operands. 
		// Some are operators, e.g. method-calls, arithmetic and bool operators. 
		// Others represent operands, like variables or literals.
		// Thus, counting used classes and their instances should give as a
		// reasonable halstead-ish metric.
		// In addition we should not count "equal" literals as different instances,
		// even though they are different instances in the model. 
		// Methods, variables, and types should be counted for as individuals.
		val values = new HalsteadValues
		block.traverse[obj,feature,value|
			if (value instanceof EObject) {
				values.classes.add(switch value {
					StringLiteral: value.escapedValue		
					NumberLiteral: value.tokenValue
					BooleanLiteral: if (value.value) Boolean.TRUE else Boolean.FALSE
					CharacterLiteral: value.escapedValue
					TypeAccess: value.type
					SingleVariableAccess: value.variable
					MethodInvocation: value.method
					default: value.eClass				
				})
				values.instances++
			}
		]
		return (values.classes.size*(Math.log(values.instances)/Math.log(2))) as int
	}

	@Metric(name="wmc_cc")
	static def int weightedMethodPerClassWithCCWeight(AbstractTypeDeclaration type) {
		return type.weightedMethodsPerClass[it.cyclomaticComplexity]
	}

	@Metric(name="wmc_hsv")
	static def int weightedMethodPerClassWithHalsteadVolume(AbstractTypeDeclaration type) {
		return type.weightedMethodsPerClass[it.halsteadValume]
	}

	public static def getMetricName(Method method) {
		(method.annotations.findFirst[it instanceof Metric] as Metric).name
	}

	public static def getMetricSourceType(Method method) {
		method.parameterTypes.get(0)
	}

	public static def getMetrics() {
		return ModiscoMetrics.methods.filter [
			it.annotations.exists [
				it instanceof Metric
			]
		]
	}

	public static def String qualifiedName(EObject element) {
		return element.eAllContainer[it instanceof NamedElement].collect[(it as NamedElement).name].toList.reverse.
			join("/")
	}
}