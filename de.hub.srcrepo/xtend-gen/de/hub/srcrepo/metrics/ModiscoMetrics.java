package de.hub.srcrepo.metrics;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import de.hub.srcrepo.metrics.Metric;
import de.hub.srcrepo.ocl.OclExtensions;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.AbstractVariablesContainer;
import org.eclipse.gmt.modisco.java.AnonymousClassDeclaration;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.Modifier;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.VariableDeclaration;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.eclipse.gmt.modisco.java.VisibilityKind;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class ModiscoMetrics {
  private static class UnorderedPair<E extends Object> {
    private final E one;
    
    private final E two;
    
    public UnorderedPair(final E one, final E two) {
      this.one = one;
      this.two = two;
    }
    
    @Override
    public int hashCode() {
      int _hashCode = this.one.hashCode();
      int _hashCode_1 = this.two.hashCode();
      return (_hashCode + _hashCode_1);
    }
    
    @Override
    public boolean equals(final Object obj) {
      boolean _xifexpression = false;
      if ((obj instanceof ModiscoMetrics.UnorderedPair<?>)) {
        boolean _xblockexpression = false;
        {
          final ModiscoMetrics.UnorderedPair<E> other = ((ModiscoMetrics.UnorderedPair<E>) obj);
          boolean _or = false;
          boolean _and = false;
          boolean _equals = this.one.equals(other.one);
          if (!_equals) {
            _and = false;
          } else {
            boolean _equals_1 = this.two.equals(other.two);
            _and = _equals_1;
          }
          if (_and) {
            _or = true;
          } else {
            boolean _and_1 = false;
            boolean _equals_2 = this.two.equals(other.one);
            if (!_equals_2) {
              _and_1 = false;
            } else {
              boolean _equals_3 = this.one.equals(other.two);
              _and_1 = _equals_3;
            }
            _or = _and_1;
          }
          _xblockexpression = _or;
        }
        _xifexpression = _xblockexpression;
      } else {
        _xifexpression = false;
      }
      return _xifexpression;
    }
  }
  
  /**
   * Calculates the Weighted number of Methods per Class (WMC) with a constant weight of 1.
   * @param type Is the type that constitutes the "Class" that this metric is applied to.
   * @returns the number of method declarations in a given type. All method declarations declared within the type are counted.
   */
  @Metric(name = "wmc")
  public static Integer weightedMethodsPerClass(final AbstractTypeDeclaration type) {
    EList<BodyDeclaration> _bodyDeclarations = type.getBodyDeclarations();
    final Function1<BodyDeclaration, Boolean> _function = (BodyDeclaration it) -> {
      return Boolean.valueOf((it instanceof AbstractMethodDeclaration));
    };
    Iterable<BodyDeclaration> _select = OclExtensions.<BodyDeclaration>select(_bodyDeclarations, _function);
    final Function1<BodyDeclaration, Integer> _function_1 = (BodyDeclaration it) -> {
      return Integer.valueOf(1);
    };
    return OclExtensions.<BodyDeclaration>sum(_select, _function_1);
  }
  
  /**
   * Calculates the Depth of Inheritance Tree (DIT) for a given type. The type it self is not counted.
   * All super types and interfaces as defined within MoDisco's semantics are counted. This should
   * leave out Java's default super type Object.
   * @param type Is the type that this metric is applied to.
   * @returns the DIT of the given type.
   */
  @Metric(name = "dit")
  public static int depthOfInheritenceTree(final AbstractTypeDeclaration type) {
    Iterable<TypeAccess> _xifexpression = null;
    boolean _and = false;
    if (!(type instanceof ClassDeclaration)) {
      _and = false;
    } else {
      TypeAccess _superClass = ((ClassDeclaration) type).getSuperClass();
      boolean _notEquals = (!Objects.equal(_superClass, null));
      _and = _notEquals;
    }
    if (_and) {
      TypeAccess _superClass_1 = ((ClassDeclaration) type).getSuperClass();
      EList<TypeAccess> _superInterfaces = type.getSuperInterfaces();
      _xifexpression = Iterables.<TypeAccess>concat(Collections.<Collection<TypeAccess>>unmodifiableSet(CollectionLiterals.<Collection<TypeAccess>>newHashSet(Collections.<TypeAccess>unmodifiableSet(CollectionLiterals.<TypeAccess>newHashSet(_superClass_1)), _superInterfaces)));
    } else {
      _xifexpression = type.getSuperInterfaces();
    }
    final Iterable<TypeAccess> superTypes = _xifexpression;
    int _xifexpression_1 = (int) 0;
    boolean _isEmpty = IterableExtensions.isEmpty(superTypes);
    if (_isEmpty) {
      _xifexpression_1 = 0;
    } else {
      final Function1<TypeAccess, Integer> _function = (TypeAccess it) -> {
        Type _type = it.getType();
        if ((_type instanceof AbstractTypeDeclaration)) {
          Type _type_1 = it.getType();
          int _depthOfInheritenceTree = ModiscoMetrics.depthOfInheritenceTree(((AbstractTypeDeclaration) _type_1));
          return Integer.valueOf((1 + _depthOfInheritenceTree));
        } else {
          return Integer.valueOf(0);
        }
      };
      return (int) OclExtensions.<TypeAccess>max(superTypes, _function);
    }
    return _xifexpression_1;
  }
  
  /**
   * Calculates the Number Of Children (NOC) metrik for a given type. NOC is the number of direct sub-types.
   * This implementation counts references based on MoDiscos abstractTypeDeclaration_SuperInterfaces
   * and classDeclaration_SuperClass references.
   * @param type Is the type that this metric is applied to.
   * @returns the NOT of the given type.
   */
  @Metric(name = "noc")
  public static int numberOfChildren(final AbstractTypeDeclaration type) {
    Integer _xifexpression = null;
    EList<TypeAccess> _usagesInTypeAccess = type.getUsagesInTypeAccess();
    boolean _isEmpty = _usagesInTypeAccess.isEmpty();
    if (_isEmpty) {
      return 0;
    } else {
      EList<TypeAccess> _usagesInTypeAccess_1 = type.getUsagesInTypeAccess();
      final Function1<TypeAccess, Integer> _function = (TypeAccess it) -> {
        final EStructuralFeature accessingFeature = it.eContainingFeature();
        boolean _or = false;
        EReference _abstractTypeDeclaration_SuperInterfaces = JavaPackage.eINSTANCE.getAbstractTypeDeclaration_SuperInterfaces();
        boolean _equals = Objects.equal(accessingFeature, _abstractTypeDeclaration_SuperInterfaces);
        if (_equals) {
          _or = true;
        } else {
          EReference _classDeclaration_SuperClass = JavaPackage.eINSTANCE.getClassDeclaration_SuperClass();
          boolean _equals_1 = Objects.equal(accessingFeature, _classDeclaration_SuperClass);
          _or = _equals_1;
        }
        if (_or) {
          return Integer.valueOf(1);
        } else {
          return Integer.valueOf(0);
        }
      };
      _xifexpression = OclExtensions.<TypeAccess>sum(_usagesInTypeAccess_1, _function);
    }
    return (_xifexpression).intValue();
  }
  
  private static Iterable<AbstractTypeDeclaration> allSuperTypes(final AbstractTypeDeclaration clazz) {
    final Function1<AbstractTypeDeclaration, Iterable<AbstractTypeDeclaration>> _function = (AbstractTypeDeclaration type) -> {
      Iterable<TypeAccess> _xifexpression = null;
      boolean _and = false;
      if (!(type instanceof ClassDeclaration)) {
        _and = false;
      } else {
        TypeAccess _superClass = ((ClassDeclaration) type).getSuperClass();
        boolean _notEquals = (!Objects.equal(_superClass, null));
        _and = _notEquals;
      }
      if (_and) {
        TypeAccess _superClass_1 = ((ClassDeclaration) type).getSuperClass();
        EList<TypeAccess> _superInterfaces = type.getSuperInterfaces();
        _xifexpression = Iterables.<TypeAccess>concat(Collections.<Collection<TypeAccess>>unmodifiableSet(CollectionLiterals.<Collection<TypeAccess>>newHashSet(Collections.<TypeAccess>unmodifiableSet(CollectionLiterals.<TypeAccess>newHashSet(_superClass_1)), _superInterfaces)));
      } else {
        _xifexpression = type.getSuperInterfaces();
      }
      final Function1<TypeAccess, Type> _function_1 = (TypeAccess it) -> {
        return it.getType();
      };
      Iterable<Type> _collect = OclExtensions.<TypeAccess, Type>collect(_xifexpression, _function_1);
      return OclExtensions.<Type, AbstractTypeDeclaration>typeSelect(_collect, AbstractTypeDeclaration.class);
    };
    return OclExtensions.<AbstractTypeDeclaration>closure(clazz, _function);
  }
  
  private static Iterable<EObject> eAllContentsWithoutAnonymousClasses(final EObject container) {
    final Function1<EObject, Boolean> _function = (EObject it) -> {
      return Boolean.valueOf((!(it instanceof AnonymousClassDeclaration)));
    };
    return OclExtensions.eAllContentsAsIterable(container, _function);
  }
  
  /**
   * Calculates the Coupling Between Object (CBO) metric. Counts accesses of members and not of static members.
   * @param type Is the type that this metric is applied to.
   * @Returns sum of count of all accesses to different classes via fields and methods in all contents of given type.
   */
  @Metric(name = "cbo")
  public static int couplingBetweenObjects(final AbstractTypeDeclaration type) {
    if ((type instanceof ClassDeclaration)) {
      final ClassDeclaration clazz = ((ClassDeclaration) type);
      EList<BodyDeclaration> _bodyDeclarations = clazz.getBodyDeclarations();
      Iterable<AbstractMethodDeclaration> _typeSelect = OclExtensions.<BodyDeclaration, AbstractMethodDeclaration>typeSelect(_bodyDeclarations, AbstractMethodDeclaration.class);
      final Function1<AbstractMethodDeclaration, Iterable<EObject>> _function = (AbstractMethodDeclaration it) -> {
        return ModiscoMetrics.eAllContentsWithoutAnonymousClasses(it);
      };
      final Iterable<EObject> allContentsWithOutAnonymousClasses = OclExtensions.<AbstractMethodDeclaration, EObject>collectAll(_typeSelect, _function);
      final HashSet<AbstractTypeDeclaration> coupledTypes = new HashSet<AbstractTypeDeclaration>();
      Iterable<SingleVariableAccess> _typeSelect_1 = OclExtensions.<EObject, SingleVariableAccess>typeSelect(allContentsWithOutAnonymousClasses, SingleVariableAccess.class);
      final Function1<SingleVariableAccess, VariableDeclaration> _function_1 = (SingleVariableAccess it) -> {
        return it.getVariable();
      };
      Iterable<VariableDeclaration> _collect = OclExtensions.<SingleVariableAccess, VariableDeclaration>collect(_typeSelect_1, _function_1);
      Iterable<VariableDeclarationFragment> _typeSelect_2 = OclExtensions.<VariableDeclaration, VariableDeclarationFragment>typeSelect(_collect, VariableDeclarationFragment.class);
      final Function1<VariableDeclarationFragment, AbstractVariablesContainer> _function_2 = (VariableDeclarationFragment it) -> {
        return it.getVariablesContainer();
      };
      Iterable<AbstractVariablesContainer> _collect_1 = OclExtensions.<VariableDeclarationFragment, AbstractVariablesContainer>collect(_typeSelect_2, _function_2);
      Iterable<FieldDeclaration> _typeSelect_3 = OclExtensions.<AbstractVariablesContainer, FieldDeclaration>typeSelect(_collect_1, FieldDeclaration.class);
      final Function1<FieldDeclaration, Boolean> _function_3 = (FieldDeclaration it) -> {
        boolean _and = false;
        Modifier _modifier = it.getModifier();
        boolean _notEquals = (!Objects.equal(_modifier, null));
        if (!_notEquals) {
          _and = false;
        } else {
          Modifier _modifier_1 = it.getModifier();
          boolean _isStatic = _modifier_1.isStatic();
          boolean _not = (!_isStatic);
          _and = _not;
        }
        return Boolean.valueOf(_and);
      };
      Iterable<FieldDeclaration> _select = OclExtensions.<FieldDeclaration>select(_typeSelect_3, _function_3);
      final Function1<FieldDeclaration, AbstractTypeDeclaration> _function_4 = (FieldDeclaration it) -> {
        return OclExtensions.<AbstractTypeDeclaration>eTypeSelectContainer(it, AbstractTypeDeclaration.class);
      };
      Iterable<AbstractTypeDeclaration> _collect_2 = OclExtensions.<FieldDeclaration, AbstractTypeDeclaration>collect(_select, _function_4);
      final Function1<AbstractTypeDeclaration, Boolean> _function_5 = (AbstractTypeDeclaration it) -> {
        return Boolean.valueOf((!Objects.equal(it, type)));
      };
      Iterable<AbstractTypeDeclaration> _select_1 = OclExtensions.<AbstractTypeDeclaration>select(_collect_2, _function_5);
      Iterables.<AbstractTypeDeclaration>addAll(coupledTypes, _select_1);
      Iterable<MethodInvocation> _typeSelect_4 = OclExtensions.<EObject, MethodInvocation>typeSelect(allContentsWithOutAnonymousClasses, MethodInvocation.class);
      final Function1<MethodInvocation, AbstractMethodDeclaration> _function_6 = (MethodInvocation it) -> {
        return it.getMethod();
      };
      Iterable<AbstractMethodDeclaration> _collect_3 = OclExtensions.<MethodInvocation, AbstractMethodDeclaration>collect(_typeSelect_4, _function_6);
      final Function1<AbstractMethodDeclaration, Boolean> _function_7 = (AbstractMethodDeclaration it) -> {
        boolean _and = false;
        Modifier _modifier = it.getModifier();
        boolean _notEquals = (!Objects.equal(_modifier, null));
        if (!_notEquals) {
          _and = false;
        } else {
          Modifier _modifier_1 = it.getModifier();
          boolean _isStatic = _modifier_1.isStatic();
          boolean _not = (!_isStatic);
          _and = _not;
        }
        return Boolean.valueOf(_and);
      };
      Iterable<AbstractMethodDeclaration> _select_2 = OclExtensions.<AbstractMethodDeclaration>select(_collect_3, _function_7);
      final Function1<AbstractMethodDeclaration, AbstractTypeDeclaration> _function_8 = (AbstractMethodDeclaration it) -> {
        return OclExtensions.<AbstractTypeDeclaration>eTypeSelectContainer(it, AbstractTypeDeclaration.class);
      };
      Iterable<AbstractTypeDeclaration> _collect_4 = OclExtensions.<AbstractMethodDeclaration, AbstractTypeDeclaration>collect(_select_2, _function_8);
      final Function1<AbstractTypeDeclaration, Boolean> _function_9 = (AbstractTypeDeclaration it) -> {
        return Boolean.valueOf((!Objects.equal(it, type)));
      };
      Iterable<AbstractTypeDeclaration> _select_3 = OclExtensions.<AbstractTypeDeclaration>select(_collect_4, _function_9);
      Iterables.<AbstractTypeDeclaration>addAll(coupledTypes, _select_3);
      return coupledTypes.size();
    } else {
      return 0;
    }
  }
  
  private static String qualifiedName(final Type element) {
    final Function1<EObject, Boolean> _function = (EObject it) -> {
      return Boolean.valueOf((it instanceof NamedElement));
    };
    Iterable<EObject> _eAllContainer = OclExtensions.eAllContainer(element, _function);
    final Function1<EObject, String> _function_1 = (EObject it) -> {
      return ((NamedElement) it).getName();
    };
    Iterable<String> _collect = OclExtensions.<EObject, String>collect(_eAllContainer, _function_1);
    List<String> _list = IterableExtensions.<String>toList(_collect);
    List<String> _reverse = ListExtensions.<String>reverse(_list);
    return IterableExtensions.join(_reverse, ".");
  }
  
  private static String signature(final AbstractMethodDeclaration method) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = method.getName();
    _builder.append(_name, "");
    _builder.append("@(");
    EList<SingleVariableDeclaration> _parameters = method.getParameters();
    final Function1<SingleVariableDeclaration, String> _function = (SingleVariableDeclaration it) -> {
      TypeAccess _type = it.getType();
      Type _type_1 = _type.getType();
      return ModiscoMetrics.qualifiedName(_type_1);
    };
    Iterable<String> _collect = OclExtensions.<SingleVariableDeclaration, String>collect(_parameters, _function);
    String _join = IterableExtensions.join(_collect, ",");
    _builder.append(_join, "");
    _builder.append(")");
    return _builder.toString();
  }
  
  /**
   * Calculates the Response For a Class (RFC) metrik that is the count of all member methods in the type's interface.
   * I.e., the sum of all methods in all super types that are member methods and do not override an existing method.
   * @param type Is the type that this metric is applied to.
   * @returns the RFC of the given type.
   */
  @Metric(name = "rfc")
  public static int responseForClass(final AbstractTypeDeclaration type) {
    Iterable<AbstractTypeDeclaration> _allSuperTypes = ModiscoMetrics.allSuperTypes(type);
    final Function1<AbstractTypeDeclaration, Iterable<String>> _function = (AbstractTypeDeclaration it) -> {
      EList<BodyDeclaration> _bodyDeclarations = it.getBodyDeclarations();
      Iterable<AbstractMethodDeclaration> _typeSelect = OclExtensions.<BodyDeclaration, AbstractMethodDeclaration>typeSelect(_bodyDeclarations, AbstractMethodDeclaration.class);
      final Function1<AbstractMethodDeclaration, Boolean> _function_1 = (AbstractMethodDeclaration it_1) -> {
        boolean _and = false;
        boolean _and_1 = false;
        Modifier _modifier = it_1.getModifier();
        boolean _notEquals = (!Objects.equal(_modifier, null));
        if (!_notEquals) {
          _and_1 = false;
        } else {
          Modifier _modifier_1 = it_1.getModifier();
          boolean _isStatic = _modifier_1.isStatic();
          boolean _not = (!_isStatic);
          _and_1 = _not;
        }
        if (!_and_1) {
          _and = false;
        } else {
          boolean _or = false;
          Modifier _modifier_2 = it_1.getModifier();
          VisibilityKind _visibility = _modifier_2.getVisibility();
          boolean _equals = Objects.equal(_visibility, VisibilityKind.PUBLIC);
          if (_equals) {
            _or = true;
          } else {
            Modifier _modifier_3 = it_1.getModifier();
            VisibilityKind _visibility_1 = _modifier_3.getVisibility();
            boolean _equals_1 = Objects.equal(_visibility_1, VisibilityKind.NONE);
            _or = _equals_1;
          }
          _and = _or;
        }
        return Boolean.valueOf(_and);
      };
      Iterable<AbstractMethodDeclaration> _select = OclExtensions.<AbstractMethodDeclaration>select(_typeSelect, _function_1);
      final Function1<AbstractMethodDeclaration, String> _function_2 = (AbstractMethodDeclaration it_1) -> {
        return ModiscoMetrics.signature(it_1);
      };
      return OclExtensions.<AbstractMethodDeclaration, String>collect(_select, _function_2);
    };
    Iterable<String> _collectAll = OclExtensions.<AbstractTypeDeclaration, String>collectAll(_allSuperTypes, _function);
    Set<String> _set = IterableExtensions.<String>toSet(_collectAll);
    return _set.size();
  }
  
  /**
   * Calculates the Lack of Cohesion Of Methods (LCOM) metrik. The difference of number of member method pairs that use
   * and use not at least one common field of the type.
   * @param type Is the type that this metric is applied to.
   * @Returns the LCOM number of the given type or 0 if the LCOM number is negative.
   */
  @Metric(name = "lcom")
  public static int lackOfCohesionInMethods(final AbstractTypeDeclaration type) {
    EList<BodyDeclaration> _bodyDeclarations = type.getBodyDeclarations();
    final Iterable<AbstractMethodDeclaration> methods = OclExtensions.<BodyDeclaration, AbstractMethodDeclaration>typeSelect(_bodyDeclarations, AbstractMethodDeclaration.class);
    HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>> _hashMap = new HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>();
    final Function2<HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>, AbstractMethodDeclaration, HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>> _function = (HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>> result, AbstractMethodDeclaration method) -> {
      Iterable<EObject> _eAllContentsWithoutAnonymousClasses = ModiscoMetrics.eAllContentsWithoutAnonymousClasses(method);
      Iterable<SingleVariableAccess> _typeSelect = OclExtensions.<EObject, SingleVariableAccess>typeSelect(_eAllContentsWithoutAnonymousClasses, SingleVariableAccess.class);
      final Function1<SingleVariableAccess, VariableDeclaration> _function_1 = (SingleVariableAccess it) -> {
        return it.getVariable();
      };
      Iterable<VariableDeclaration> _collect = OclExtensions.<SingleVariableAccess, VariableDeclaration>collect(_typeSelect, _function_1);
      final Function1<VariableDeclaration, Boolean> _function_2 = (VariableDeclaration it) -> {
        AbstractTypeDeclaration _eTypeSelectContainer = OclExtensions.<AbstractTypeDeclaration>eTypeSelectContainer(it, AbstractTypeDeclaration.class);
        return Boolean.valueOf(Objects.equal(_eTypeSelectContainer, type));
      };
      final Iterable<VariableDeclaration> accessedFieldsOfType = OclExtensions.<VariableDeclaration>select(_collect, _function_2);
      result.put(method, accessedFieldsOfType);
      return result;
    };
    final HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>> methodToFieldsMap = IterableExtensions.<AbstractMethodDeclaration, HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>>fold(methods, _hashMap, _function);
    final HashSet<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>> pairsWithAccessesOfCommonFields = new HashSet<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>>();
    final HashSet<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>> pairsWithOutAccessesOfCommonFields = new HashSet<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>>();
    final Consumer<AbstractMethodDeclaration> _function_1 = (AbstractMethodDeclaration m1) -> {
      final Consumer<AbstractMethodDeclaration> _function_2 = (AbstractMethodDeclaration m2) -> {
        boolean _notEquals = (!Objects.equal(m1, m2));
        if (_notEquals) {
          Iterable<VariableDeclaration> _get = methodToFieldsMap.get(m1);
          final Function1<VariableDeclaration, Boolean> _function_3 = (VariableDeclaration field) -> {
            Iterable<VariableDeclaration> _get_1 = methodToFieldsMap.get(m2);
            final Function1<VariableDeclaration, Boolean> _function_4 = (VariableDeclaration it) -> {
              return Boolean.valueOf(Objects.equal(it, field));
            };
            return Boolean.valueOf(IterableExtensions.<VariableDeclaration>exists(_get_1, _function_4));
          };
          boolean _exists = IterableExtensions.<VariableDeclaration>exists(_get, _function_3);
          if (_exists) {
            ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration> _unorderedPair = new ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>(m1, m2);
            pairsWithAccessesOfCommonFields.add(_unorderedPair);
          } else {
            ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration> _unorderedPair_1 = new ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>(m1, m2);
            pairsWithOutAccessesOfCommonFields.add(_unorderedPair_1);
          }
        }
      };
      methods.forEach(_function_2);
    };
    methods.forEach(_function_1);
    int _size = pairsWithOutAccessesOfCommonFields.size();
    int _size_1 = pairsWithAccessesOfCommonFields.size();
    final int result = (_size - _size_1);
    int _xifexpression = (int) 0;
    if ((result >= 0)) {
      _xifexpression = result;
    } else {
      _xifexpression = 0;
    }
    return _xifexpression;
  }
  
  public static String getMetricName(final Method method) {
    Annotation[] _annotations = method.getAnnotations();
    final Function1<Annotation, Boolean> _function = (Annotation it) -> {
      return Boolean.valueOf((it instanceof Metric));
    };
    Annotation _findFirst = IterableExtensions.<Annotation>findFirst(((Iterable<Annotation>)Conversions.doWrapArray(_annotations)), _function);
    return ((Metric) _findFirst).name();
  }
  
  public static Class<?> getMetricSourceType(final Method method) {
    Class<?>[] _parameterTypes = method.getParameterTypes();
    return _parameterTypes[0];
  }
  
  public static Iterable<Method> getMetrics() {
    Method[] _methods = ModiscoMetrics.class.getMethods();
    final Function1<Method, Boolean> _function = (Method it) -> {
      Annotation[] _annotations = it.getAnnotations();
      final Function1<Annotation, Boolean> _function_1 = (Annotation it_1) -> {
        return Boolean.valueOf((it_1 instanceof Metric));
      };
      return Boolean.valueOf(IterableExtensions.<Annotation>exists(((Iterable<Annotation>)Conversions.doWrapArray(_annotations)), _function_1));
    };
    return IterableExtensions.<Method>filter(((Iterable<Method>)Conversions.doWrapArray(_methods)), _function);
  }
  
  public static String qualifiedName(final EObject element) {
    final Function1<EObject, Boolean> _function = (EObject it) -> {
      return Boolean.valueOf((it instanceof NamedElement));
    };
    Iterable<EObject> _eAllContainer = OclExtensions.eAllContainer(element, _function);
    final Function1<EObject, String> _function_1 = (EObject it) -> {
      return ((NamedElement) it).getName();
    };
    Iterable<String> _collect = OclExtensions.<EObject, String>collect(_eAllContainer, _function_1);
    List<String> _list = IterableExtensions.<String>toList(_collect);
    List<String> _reverse = ListExtensions.<String>reverse(_list);
    return IterableExtensions.join(_reverse, "/");
  }
}
