package de.hub.srcrepo.metrics;

import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.metrics.AbstractMetricsTests;
import de.hub.srcrepo.metrics.Metric;
import de.hub.srcrepo.metrics.ModiscoMetrics;
import java.io.File;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class GenerateModiscoMetricsTests extends AbstractMetricsTests {
  private final String srcFolder = "src-gen";
  
  private final String modelFileName = "models/de.hub.srcrepo.metrics.testdata.javamodel";
  
  private String metricName(final Method method) {
    Annotation[] _annotations = method.getAnnotations();
    final Function1<Annotation, Boolean> _function = new Function1<Annotation, Boolean>() {
      @Override
      public Boolean apply(final Annotation it) {
        return Boolean.valueOf((it instanceof Metric));
      }
    };
    Annotation _findFirst = IterableExtensions.<Annotation>findFirst(((Iterable<Annotation>)Conversions.doWrapArray(_annotations)), _function);
    return ((Metric) _findFirst).name();
  }
  
  private Class<?> metricSourceType(final Method method) {
    Parameter[] _parameters = method.getParameters();
    Parameter _get = _parameters[0];
    return _get.getType();
  }
  
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
    Method[] _methods = metricsClass.getMethods();
    final Function1<Method, Boolean> _function = new Function1<Method, Boolean>() {
      @Override
      public Boolean apply(final Method it) {
        Annotation[] _annotations = it.getAnnotations();
        final Function1<Annotation, Boolean> _function = new Function1<Annotation, Boolean>() {
          @Override
          public Boolean apply(final Annotation it) {
            return Boolean.valueOf((it instanceof Metric));
          }
        };
        return Boolean.valueOf(IterableExtensions.<Annotation>exists(((Iterable<Annotation>)Conversions.doWrapArray(_annotations)), _function));
      }
    };
    final Iterable<Method> metrics = IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(_methods)), _function);
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
        _builder.append("\t");
        _builder.append("@Test");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("public void test");
        String _name_1 = metric.getName();
        String _firstUpper = StringExtensions.toFirstUpper(_name_1);
        _builder.append(_firstUpper, "\t");
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("int tests = 0;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("for (MetricsTest<");
        Class<?> _metricSourceType = this.metricSourceType(metric);
        String _simpleName_1 = _metricSourceType.getSimpleName();
        _builder.append(_simpleName_1, "\t\t");
        _builder.append("> test: modelElementsWithMetric(\"");
        String _metricName = this.metricName(metric);
        _builder.append(_metricName, "\t\t");
        _builder.append("\", ");
        Class<?> _metricSourceType_1 = this.metricSourceType(metric);
        String _simpleName_2 = _metricSourceType_1.getSimpleName();
        _builder.append(_simpleName_2, "\t\t");
        _builder.append(".class)) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("Assert.assertSame(\"Failed for \" + test.element.toString() + \".\", test.expectedValue, ModiscoMetrics.");
        String _name_2 = metric.getName();
        _builder.append(_name_2, "\t\t\t");
        _builder.append("(test.element));");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("tests++;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("Assert.assertSame(");
        String _metricName_1 = this.metricName(metric);
        Class<?> _metricSourceType_2 = this.metricSourceType(metric);
        Iterable<AbstractMetricsTests.MetricsTest<BodyDeclaration>> _modelElementsWithMetric = this.<BodyDeclaration>modelElementsWithMetric(_metricName_1, ((Class<BodyDeclaration>) _metricSourceType_2));
        int _size = IterableExtensions.size(_modelElementsWithMetric);
        _builder.append(_size, "\t\t");
        _builder.append(", tests);");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
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
