package de.hub.srcrepo.metrics;

import com.google.common.base.Objects;
import de.hub.srcrepo.metrics.Metric;
import de.hub.srcrepo.ocl.OclExtensions;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.Annotation;
import org.eclipse.gmt.modisco.java.AnnotationMemberValuePair;
import org.eclipse.gmt.modisco.java.Block;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NumberLiteral;
import org.eclipse.gmt.modisco.java.StringLiteral;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings("all")
public abstract class AbstractMetricsTests {
  public static class MetricsTest<T extends BodyDeclaration> {
    public final T element;
    
    public final String metric;
    
    public final int expectedValue;
    
    public MetricsTest(final T element, final String metric, final int expectedValue) {
      this.element = element;
      this.metric = metric;
      this.expectedValue = expectedValue;
    }
  }
  
  protected Model model = null;
  
  protected abstract String getModelFileName();
  
  @Before
  public void setup() {
    String _nsURI = JavaPackage.eINSTANCE.getNsURI();
    EPackage.Registry.INSTANCE.put(_nsURI, JavaPackage.eINSTANCE);
    String _nsURI_1 = EcorePackage.eINSTANCE.getNsURI();
    EPackage.Registry.INSTANCE.put(_nsURI_1, EcorePackage.eINSTANCE);
    Map<String, Object> _extensionToFactoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
    XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put("javamodel", _xMIResourceFactoryImpl);
    final ResourceSetImpl rs = new ResourceSetImpl();
    String _modelFileName = this.getModelFileName();
    URI _createURI = URI.createURI(_modelFileName);
    final Resource r = rs.getResource(_createURI, true);
    EList<EObject> _contents = r.getContents();
    EObject _get = _contents.get(0);
    this.model = ((Model) _get);
  }
  
  @After
  public void teardown() {
    EcoreUtil.delete(this.model);
  }
  
  private int metricValue(final BodyDeclaration bodyDeclaration, final String metric) {
    EList<Annotation> _annotations = bodyDeclaration.getAnnotations();
    final Function1<Annotation, Boolean> _function = new Function1<Annotation, Boolean>() {
      @Override
      public Boolean apply(final Annotation it) {
        boolean _and = false;
        TypeAccess _type = it.getType();
        Type _type_1 = _type.getType();
        String _name = _type_1.getName();
        String _simpleName = Metric.class.getSimpleName();
        boolean _equals = _name.equals(_simpleName);
        if (!_equals) {
          _and = false;
        } else {
          EList<AnnotationMemberValuePair> _values = it.getValues();
          final Function1<AnnotationMemberValuePair, Boolean> _function = new Function1<AnnotationMemberValuePair, Boolean>() {
            @Override
            public Boolean apply(final AnnotationMemberValuePair it) {
              boolean _and = false;
              String _name = it.getName();
              boolean _equals = _name.equals("name");
              if (!_equals) {
                _and = false;
              } else {
                Expression _value = it.getValue();
                String _escapedValue = ((StringLiteral) _value).getEscapedValue();
                String _replace = _escapedValue.replace("\"", "");
                boolean _equals_1 = _replace.equals(metric);
                _and = _equals_1;
              }
              return Boolean.valueOf(_and);
            }
          };
          boolean _exists = IterableExtensions.<AnnotationMemberValuePair>exists(_values, _function);
          _and = _exists;
        }
        return Boolean.valueOf(_and);
      }
    };
    final Annotation annotation = IterableExtensions.<Annotation>findFirst(_annotations, _function);
    int _xifexpression = (int) 0;
    boolean _notEquals = (!Objects.equal(annotation, null));
    if (_notEquals) {
      int _xblockexpression = (int) 0;
      {
        EList<AnnotationMemberValuePair> _values = annotation.getValues();
        final Function1<AnnotationMemberValuePair, Boolean> _function_1 = new Function1<AnnotationMemberValuePair, Boolean>() {
          @Override
          public Boolean apply(final AnnotationMemberValuePair it) {
            String _name = it.getName();
            return Boolean.valueOf(_name.equals("value"));
          }
        };
        AnnotationMemberValuePair _findFirst = IterableExtensions.<AnnotationMemberValuePair>findFirst(_values, _function_1);
        Expression _value = _findFirst.getValue();
        final NumberLiteral number = ((NumberLiteral) _value);
        String _tokenValue = number.getTokenValue();
        _xblockexpression = Integer.parseInt(_tokenValue);
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = (-1);
    }
    return _xifexpression;
  }
  
  protected <T extends BodyDeclaration> Iterable<AbstractMetricsTests.MetricsTest<T>> modelElementsWithMetric(final String metric, final Class<T> elementClass) {
    final Function1<EObject, Boolean> _function = new Function1<EObject, Boolean>() {
      @Override
      public Boolean apply(final EObject it) {
        return Boolean.valueOf((it instanceof Block));
      }
    };
    Iterable<EObject> _eAllContentsAsIterable = OclExtensions.eAllContentsAsIterable(this.model, _function);
    Iterable<T> _typeSelect = OclExtensions.<EObject, T>typeSelect(_eAllContentsAsIterable, elementClass);
    final Function1<T, Boolean> _function_1 = new Function1<T, Boolean>() {
      @Override
      public Boolean apply(final T it) {
        int _metricValue = AbstractMetricsTests.this.metricValue(it, metric);
        return Boolean.valueOf((_metricValue >= 0));
      }
    };
    Iterable<T> _select = OclExtensions.<T>select(_typeSelect, _function_1);
    final Function1<T, AbstractMetricsTests.MetricsTest<T>> _function_2 = new Function1<T, AbstractMetricsTests.MetricsTest<T>>() {
      @Override
      public AbstractMetricsTests.MetricsTest<T> apply(final T it) {
        int _metricValue = AbstractMetricsTests.this.metricValue(it, metric);
        return new AbstractMetricsTests.MetricsTest<T>(it, metric, _metricValue);
      }
    };
    return OclExtensions.<T, AbstractMetricsTests.MetricsTest<T>>collect(_select, _function_2);
  }
}
