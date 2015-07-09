package de.hub.srcrepo.metrics;

import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.metrics.AbstractMetricsTests;
import de.hub.srcrepo.metrics.ModiscoMetrics;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class GenerateModiscoMetricsTests extends AbstractMetricsTests {
  private final String srcFolder = "src-gen";
  
  private final String modelFileName = "models/de.hub.srcrepo.metrics.testdata.javamodel";
  
  @Override
  protected String getModelFileName() {
    return this.modelFileName;
  }
  
  public void generate(final Class<?> metricsClass) {
    try {
      this.setup();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(this.srcFolder, "");
      _builder.append("/");
      Package _package = metricsClass.getPackage();
      String _name = _package.getName();
      String _replaceAll = _name.replaceAll("\\.", "/");
      _builder.append(_replaceAll, "");
      _builder.append("/");
      String _simpleName = metricsClass.getSimpleName();
      _builder.append(_simpleName, "");
      _builder.append("Tests.java");
      final File file = new File(_builder.toString());
      File _parentFile = file.getParentFile();
      _parentFile.mkdirs();
      file.createNewFile();
      final PrintWriter out = new PrintWriter(file);
      String _genetate = this.genetate(this.model, metricsClass);
      out.println(_genetate);
      out.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public String genetate(final Model model, final Class<?> metricsClass) {
    final Iterable<Method> metrics = ModiscoMetrics.getMetrics();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    Package _package = metricsClass.getPackage();
    String _name = _package.getName();
    _builder.append(_name, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.gmt.modisco.java.*;");
    _builder.newLine();
    _builder.append("import org.junit.Assert;");
    _builder.newLine();
    _builder.append("import org.junit.Test;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _simpleName = metricsClass.getSimpleName();
    _builder.append(_simpleName, "");
    _builder.append("Tests extends AbstractMetricsTests {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("protected String getModelFileName() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return \"");
    _builder.append(this.modelFileName, "\t\t");
    _builder.append("\";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    {
      for(final Method metric : metrics) {
        {
          String _metricName = ModiscoMetrics.getMetricName(metric);
          Class<?> _metricSourceType = ModiscoMetrics.getMetricSourceType(metric);
          Iterable<AbstractMetricsTests.MetricsTest<BodyDeclaration>> _modelElementsWithMetric = this.<BodyDeclaration>modelElementsWithMetric(_metricName, ((Class<BodyDeclaration>) _metricSourceType));
          for(final AbstractMetricsTests.MetricsTest<BodyDeclaration> test : _modelElementsWithMetric) {
            _builder.append("\t");
            _builder.append("@Test");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("public void test");
            String _firstUpper = StringExtensions.toFirstUpper(test.metric);
            _builder.append(_firstUpper, "\t");
            _builder.append("For");
            String _name_1 = test.element.getName();
            String _firstUpper_1 = StringExtensions.toFirstUpper(_name_1);
            _builder.append(_firstUpper_1, "\t");
            _builder.append("() {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("int result = ModiscoMetrics.");
            String _name_2 = metric.getName();
            _builder.append(_name_2, "\t\t");
            _builder.append("((");
            Class<?> _metricSourceType_1 = ModiscoMetrics.getMetricSourceType(metric);
            String _simpleName_1 = _metricSourceType_1.getSimpleName();
            _builder.append(_simpleName_1, "\t\t");
            _builder.append(")toElement(\"");
            String _qualifiedName = ModiscoMetrics.qualifiedName(test.element);
            _builder.append(_qualifiedName, "\t\t");
            _builder.append("\"));");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("Assert.assertSame(");
            _builder.append(test.expectedValue, "\t\t");
            _builder.append(", result);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public static void main(final String[] args) {
    SrcRepoActivator.standalone();
    GenerateModiscoMetricsTests _generateModiscoMetricsTests = new GenerateModiscoMetricsTests();
    _generateModiscoMetricsTests.generate(ModiscoMetrics.class);
  }
}
