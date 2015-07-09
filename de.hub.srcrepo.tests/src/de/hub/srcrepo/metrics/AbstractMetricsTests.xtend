package de.hub.srcrepo.metrics

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.gmt.modisco.java.Block
import org.eclipse.gmt.modisco.java.BodyDeclaration
import org.eclipse.gmt.modisco.java.Model
import org.eclipse.gmt.modisco.java.NamedElement
import org.eclipse.gmt.modisco.java.NumberLiteral
import org.eclipse.gmt.modisco.java.StringLiteral
import org.eclipse.gmt.modisco.java.emf.JavaPackage
import org.junit.After
import org.junit.Before

import static extension de.hub.srcrepo.ocl.OclExtensions.*

abstract class AbstractMetricsTests {
	
	protected var Model model = null
	
	protected abstract def String getModelFileName();
	
	@Before def void setup() {
		EPackage.Registry.INSTANCE.put(JavaPackage::eINSTANCE.getNsURI(), JavaPackage::eINSTANCE);
		EPackage.Registry.INSTANCE.put(EcorePackage::eINSTANCE.getNsURI(), EcorePackage::eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("javamodel", new XMIResourceFactoryImpl());
		
		val rs = new ResourceSetImpl();
		val r = rs.getResource(URI.createURI(modelFileName), true)
		model = r.contents.get(0) as Model
	}
	
	@After def void teardown() {
		EcoreUtil::delete(model)
	}

	private def metricValue(BodyDeclaration bodyDeclaration, String metric) {
		val annotation = bodyDeclaration.annotations.findFirst[
			it.type.type.name.equals(typeof(Metric).simpleName) &&
			it.values.exists[
				it.name.equals("name") && (it.value as StringLiteral).escapedValue.replace("\"", "").equals(metric)
			]
		]
		return if (annotation != null) {
			val number = annotation.values.findFirst[
				it.name.equals("value")
			].value as NumberLiteral
			Integer.parseInt(number.tokenValue)
		} else {
			-1
		}
	}	
	
	public static class MetricsTest<T extends BodyDeclaration> {
		public val T element
		public val String metric
		public val int expectedValue
		new (T element, String metric, int expectedValue) {
			this.element = element
			this.metric = metric
			this.expectedValue = expectedValue
		}
	}
	
	protected def BodyDeclaration toElement(String qualifiedName) {
		val names = qualifiedName.split("/")
		var EObject current = model
		for(name: names) {
			val next = current.eContents.findFirst[it instanceof NamedElement && (it as NamedElement).name.equals(name)]
			current = if (next == null) current.eContents.get(0) else next
		}
		return current as BodyDeclaration
	}
	
	protected def <T extends BodyDeclaration> modelElementsWithMetric(String metric, Class<T> elementClass) {
		model.eAllContentsAsIterable[!(it instanceof Block)]
			.typeSelect(elementClass)
			.select[it.metricValue(metric) >= 0]
			.collect[new MetricsTest(it, metric, it.metricValue(metric))]			
	}
}