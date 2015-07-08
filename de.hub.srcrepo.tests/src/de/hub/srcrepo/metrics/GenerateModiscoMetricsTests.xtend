package de.hub.srcrepo.metrics

import org.eclipse.gmt.modisco.java.Model
import static extension java.lang.reflect.Modifier.isStatic
import java.lang.reflect.Method
import java.io.PrintWriter
import java.io.File
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.metrics.AbstractMetricsTests.MetricsTest
import org.junit.Test
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration
import org.junit.Assert
import org.eclipse.gmt.modisco.java.BodyDeclaration

class GenerateModiscoMetricsTests extends AbstractMetricsTests {
	
	val srcFolder = "src-gen"
	val modelFileName = "models/de.hub.srcrepo.metrics.testdata.javamodel"
	
	private def metricName(Method method) {
		(method.annotations.findFirst[it instanceof Metric] as Metric).name
	}
	
	private def metricSourceType(Method method) {
		method.parameters.get(0).type
	}
	
	override protected getModelFileName() {
		return modelFileName
	}
	
	def generate(Class<?> metricsClass) {
		setup();
		val file = new File('''«srcFolder»/«metricsClass.package.name.replaceAll("\\.", "/")»/«metricsClass.simpleName»Tests.java''')
		file.parentFile.mkdirs
		file.createNewFile
		val out = new PrintWriter(file)
		out.println(model.genetate(metricsClass))
		out.close
	}
	
	def genetate(Model model, Class<?> metricsClass) {
		val metrics = metricsClass.methods.filter[
			it.annotations.exists[
				it instanceof Metric
			]
		]
		
		return '''
			package «metricsClass.package.name»;
			
			import org.eclipse.gmt.modisco.java.*;
			import org.junit.Assert;
			import org.junit.Test;
			
			public class «metricsClass.simpleName»Tests extends AbstractMetricsTests {
				
				@Override
				protected String getModelFileName() {
					return "«modelFileName»";
				}
			
				«FOR metric: metrics»
					@Test
					public void test«metric.name.toFirstUpper»() {
						int tests = 0;
						for (MetricsTest<«metric.metricSourceType.simpleName»> test: modelElementsWithMetric("«metric.metricName»", «metric.metricSourceType.simpleName».class)) {
							Assert.assertSame("Failed for " + test.element.toString() + ".", test.expectedValue, ModiscoMetrics.«metric.name»(test.element));
							tests++;
						}
						Assert.assertSame(«modelElementsWithMetric(metric.metricName, metric.metricSourceType as Class<BodyDeclaration>).size», tests);
					}
				«ENDFOR»
			}
		'''
	}
	
	public static def void main(String[] args) {
		SrcRepoActivator::standalone
		new GenerateModiscoMetricsTests().generate(typeof(ModiscoMetrics))
	}

}