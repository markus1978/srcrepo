package de.hub.srcrepo.metrics

import de.hub.srcrepo.SrcRepoActivator
import java.io.File
import java.io.PrintWriter
import org.eclipse.gmt.modisco.java.BodyDeclaration
import org.eclipse.gmt.modisco.java.Model

import static extension de.hub.srcrepo.metrics.ModiscoMetrics.*

class GenerateModiscoMetricsTests extends AbstractMetricsTests {
	
	val srcFolder = "src-gen"
	val modelFileName = "models/de.hub.srcrepo.metrics.testdata.javamodel"
	
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
		val metrics = ModiscoMetrics::metrics
		
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
					«FOR test: modelElementsWithMetric(metric.metricName, metric.metricSourceType as Class<BodyDeclaration>)»
						@Test
						public void test«test.metric.toFirstUpper»For«test.element.name.toFirstUpper»() {
							int result = ModiscoMetrics.«metric.name»((«metric.metricSourceType.simpleName»)toElement("«test.element.qualifiedName»"));
							Assert.assertSame(«test.expectedValue», result);
						}
					«ENDFOR»
				«ENDFOR»
			}
		'''
	}
	
	public static def void main(String[] args) {
		SrcRepoActivator::standalone
		new GenerateModiscoMetricsTests().generate(typeof(ModiscoMetrics))
	}

}