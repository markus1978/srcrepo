package de.hub.srcrepo.metrics;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import de.hub.srcrepo.metrics.Metric;
import de.hub.srcrepo.ocl.OclExtensions;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.Annotation;
import org.eclipse.gmt.modisco.java.AnonymousClassDeclaration;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.Modifier;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.VariableDeclaration;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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
  
  @Metric(name = "wmc")
  public static Integer weightedMethodsPerClass(final AbstractTypeDeclaration type) {
    EList<BodyDeclaration> _bodyDeclarations = type.getBodyDeclarations();
    final Function1<BodyDeclaration, Boolean> _function = new Function1<BodyDeclaration, Boolean>() {
      @Override
      public Boolean apply(final BodyDeclaration it) {
        return Boolean.valueOf((it instanceof AbstractMethodDeclaration));
      }
    };
    Iterable<BodyDeclaration> _select = OclExtensions.<BodyDeclaration>select(_bodyDeclarations, _function);
    final Function1<BodyDeclaration, Integer> _function_1 = new Function1<BodyDeclaration, Integer>() {
      @Override
      public Integer apply(final BodyDeclaration it) {
        return Integer.valueOf(1);
      }
    };
    return OclExtensions.<BodyDeclaration>sum(_select, _function_1);
  }
  
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
      final Function1<TypeAccess, Integer> _function = new Function1<TypeAccess, Integer>() {
        @Override
        public Integer apply(final TypeAccess it) {
          Type _type = it.getType();
          if ((_type instanceof AbstractTypeDeclaration)) {
            Type _type_1 = it.getType();
            return Integer.valueOf(ModiscoMetrics.depthOfInheritenceTree(((AbstractTypeDeclaration) _type_1)));
          } else {
            return Integer.valueOf(0);
          }
        }
      };
      return (int) OclExtensions.<TypeAccess>max(superTypes, _function);
    }
    return _xifexpression_1;
  }
  
  @Metric(name = "noc")
  public static int numberOfChildren(final AbstractTypeDeclaration type) {
    Integer _xifexpression = null;
    EList<TypeAccess> _usagesInTypeAccess = type.getUsagesInTypeAccess();
    boolean _isEmpty = _usagesInTypeAccess.isEmpty();
    if (_isEmpty) {
      return 0;
    } else {
      EList<TypeAccess> _usagesInTypeAccess_1 = type.getUsagesInTypeAccess();
      final Function1<TypeAccess, Integer> _function = new Function1<TypeAccess, Integer>() {
        @Override
        public Integer apply(final TypeAccess it) {
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
            EObject _eContainer = accessingFeature.eContainer();
            return Integer.valueOf(ModiscoMetrics.numberOfChildren(((AbstractTypeDeclaration) _eContainer)));
          } else {
            return Integer.valueOf(0);
          }
        }
      };
      _xifexpression = OclExtensions.<TypeAccess>max(_usagesInTypeAccess_1, _function);
    }
    return (_xifexpression).intValue();
  }
  
  public static Iterable<AbstractTypeDeclaration> allSuperTypes(final AbstractTypeDeclaration clazz) {
    final Function1<AbstractTypeDeclaration, Iterable<AbstractTypeDeclaration>> _function = new Function1<AbstractTypeDeclaration, Iterable<AbstractTypeDeclaration>>() {
      @Override
      public Iterable<AbstractTypeDeclaration> apply(final AbstractTypeDeclaration type) {
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
        final Function1<TypeAccess, Type> _function = new Function1<TypeAccess, Type>() {
          @Override
          public Type apply(final TypeAccess it) {
            return it.getType();
          }
        };
        Iterable<Type> _collect = OclExtensions.<TypeAccess, Type>collect(_xifexpression, _function);
        return OclExtensions.<Type, AbstractTypeDeclaration>typeSelect(_collect, AbstractTypeDeclaration.class);
      }
    };
    return OclExtensions.<AbstractTypeDeclaration>closure(clazz, _function);
  }
  
  private static Iterable<EObject> eAllContentsWithoutAnonymousClasses(final EObject container) {
    final Function1<EObject, Boolean> _function = new Function1<EObject, Boolean>() {
      @Override
      public Boolean apply(final EObject it) {
        return Boolean.valueOf((it instanceof AnonymousClassDeclaration));
      }
    };
    return OclExtensions.eAllContentsAsIterable(container, _function);
  }
  
  /**
   * Counts accesses in all contents (also inner and anonymous classes). Count accesses of member and static members.
   * @Returns sum of count of all accesses to different classes via fields and methods in all contents of given type.
   */
  @Metric(name = "cbo")
  public static int couplingBetweenObjects(final AbstractTypeDeclaration type) {
    if ((type instanceof ClassDeclaration)) {
      final ClassDeclaration clazz = ((ClassDeclaration) type);
      EList<BodyDeclaration> _bodyDeclarations = clazz.getBodyDeclarations();
      Iterable<AbstractMethodDeclaration> _typeSelect = OclExtensions.<BodyDeclaration, AbstractMethodDeclaration>typeSelect(_bodyDeclarations, AbstractMethodDeclaration.class);
      final Function1<AbstractMethodDeclaration, Iterable<EObject>> _function = new Function1<AbstractMethodDeclaration, Iterable<EObject>>() {
        @Override
        public Iterable<EObject> apply(final AbstractMethodDeclaration it) {
          return ModiscoMetrics.eAllContentsWithoutAnonymousClasses(it);
        }
      };
      final Iterable<EObject> allContentsWithOutAnonymousClasses = OclExtensions.<AbstractMethodDeclaration, EObject>collectAll(_typeSelect, _function);
      Iterable<SingleVariableAccess> _typeSelect_1 = OclExtensions.<EObject, SingleVariableAccess>typeSelect(allContentsWithOutAnonymousClasses, SingleVariableAccess.class);
      final Function1<SingleVariableAccess, AbstractTypeDeclaration> _function_1 = new Function1<SingleVariableAccess, AbstractTypeDeclaration>() {
        @Override
        public AbstractTypeDeclaration apply(final SingleVariableAccess it) {
          VariableDeclaration _variable = it.getVariable();
          return OclExtensions.<AbstractTypeDeclaration>eTypeSelectContainer(_variable, AbstractTypeDeclaration.class);
        }
      };
      final Iterable<AbstractTypeDeclaration> l1 = OclExtensions.<SingleVariableAccess, AbstractTypeDeclaration>collect(_typeSelect_1, _function_1);
      Iterable<MethodInvocation> _typeSelect_2 = OclExtensions.<EObject, MethodInvocation>typeSelect(allContentsWithOutAnonymousClasses, MethodInvocation.class);
      final Function1<MethodInvocation, AbstractTypeDeclaration> _function_2 = new Function1<MethodInvocation, AbstractTypeDeclaration>() {
        @Override
        public AbstractTypeDeclaration apply(final MethodInvocation it) {
          AbstractMethodDeclaration _method = it.getMethod();
          return OclExtensions.<AbstractTypeDeclaration>eTypeSelectContainer(_method, AbstractTypeDeclaration.class);
        }
      };
      final Iterable<AbstractTypeDeclaration> l2 = OclExtensions.<MethodInvocation, AbstractTypeDeclaration>collect(_typeSelect_2, _function_2);
      final Function1<AbstractTypeDeclaration, Integer> _function_3 = new Function1<AbstractTypeDeclaration, Integer>() {
        @Override
        public Integer apply(final AbstractTypeDeclaration it) {
          return Integer.valueOf(1);
        }
      };
      Integer _sum = OclExtensions.<AbstractTypeDeclaration>sum(l1, _function_3);
      final Function1<AbstractTypeDeclaration, Integer> _function_4 = new Function1<AbstractTypeDeclaration, Integer>() {
        @Override
        public Integer apply(final AbstractTypeDeclaration it) {
          return Integer.valueOf(1);
        }
      };
      Integer _sum_1 = OclExtensions.<AbstractTypeDeclaration>sum(l2, _function_4);
      return ((_sum).intValue() + (_sum_1).intValue());
    } else {
      return 0;
    }
  }
  
  /**
   * @Returns the sum of all methods in all super types that are member methods and do not override an existing method.
   */
  @Metric(name = "rfc")
  public static int responseForClass(final AbstractTypeDeclaration type) {
    Iterable<AbstractTypeDeclaration> _allSuperTypes = ModiscoMetrics.allSuperTypes(type);
    final Function1<AbstractTypeDeclaration, Iterable<AbstractMethodDeclaration>> _function = new Function1<AbstractTypeDeclaration, Iterable<AbstractMethodDeclaration>>() {
      @Override
      public Iterable<AbstractMethodDeclaration> apply(final AbstractTypeDeclaration it) {
        EList<BodyDeclaration> _bodyDeclarations = it.getBodyDeclarations();
        Iterable<AbstractMethodDeclaration> _typeSelect = OclExtensions.<BodyDeclaration, AbstractMethodDeclaration>typeSelect(_bodyDeclarations, AbstractMethodDeclaration.class);
        final Function1<AbstractMethodDeclaration, Boolean> _function = new Function1<AbstractMethodDeclaration, Boolean>() {
          @Override
          public Boolean apply(final AbstractMethodDeclaration it) {
            boolean _and = false;
            Modifier _modifier = it.getModifier();
            boolean _isStatic = _modifier.isStatic();
            boolean _not = (!_isStatic);
            if (!_not) {
              _and = false;
            } else {
              EList<Annotation> _annotations = it.getAnnotations();
              final Function1<Annotation, Boolean> _function = new Function1<Annotation, Boolean>() {
                @Override
                public Boolean apply(final Annotation it) {
                  TypeAccess _type = it.getType();
                  Type _type_1 = _type.getType();
                  String _name = _type_1.getName();
                  return Boolean.valueOf(_name.endsWith("Override"));
                }
              };
              boolean _exists = IterableExtensions.<Annotation>exists(_annotations, _function);
              boolean _not_1 = (!_exists);
              _and = _not_1;
            }
            return Boolean.valueOf(_and);
          }
        };
        return OclExtensions.<AbstractMethodDeclaration>select(_typeSelect, _function);
      }
    };
    Iterable<AbstractMethodDeclaration> _collectAll = OclExtensions.<AbstractTypeDeclaration, AbstractMethodDeclaration>collectAll(_allSuperTypes, _function);
    final Function1<AbstractMethodDeclaration, Integer> _function_1 = new Function1<AbstractMethodDeclaration, Integer>() {
      @Override
      public Integer apply(final AbstractMethodDeclaration it) {
        return Integer.valueOf(1);
      }
    };
    return (int) OclExtensions.<AbstractMethodDeclaration>sum(_collectAll, _function_1);
  }
  
  /**
   * @Returns the difference of number of member method pairs that use and use not at least one common field of the type.
   */
  @Metric(name = "lcom")
  public static int lackOfCohesionInMethods(final AbstractTypeDeclaration type) {
    EList<BodyDeclaration> _bodyDeclarations = type.getBodyDeclarations();
    final Iterable<AbstractMethodDeclaration> methods = OclExtensions.<BodyDeclaration, AbstractMethodDeclaration>typeSelect(_bodyDeclarations, AbstractMethodDeclaration.class);
    final Function1<AbstractMethodDeclaration, Integer> _function = new Function1<AbstractMethodDeclaration, Integer>() {
      @Override
      public Integer apply(final AbstractMethodDeclaration it) {
        return Integer.valueOf(1);
      }
    };
    Integer _sum = OclExtensions.<AbstractMethodDeclaration>sum(methods, _function);
    String _plus = ("methods " + _sum);
    InputOutput.<String>println(_plus);
    HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>> _hashMap = new HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>();
    final Function2<HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>, AbstractMethodDeclaration, HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>> _function_1 = new Function2<HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>, AbstractMethodDeclaration, HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>>() {
      @Override
      public HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>> apply(final HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>> result, final AbstractMethodDeclaration method) {
        Iterable<EObject> _eAllContentsWithoutAnonymousClasses = ModiscoMetrics.eAllContentsWithoutAnonymousClasses(method);
        Iterable<SingleVariableAccess> _typeSelect = OclExtensions.<EObject, SingleVariableAccess>typeSelect(_eAllContentsWithoutAnonymousClasses, SingleVariableAccess.class);
        final Function1<SingleVariableAccess, VariableDeclaration> _function = new Function1<SingleVariableAccess, VariableDeclaration>() {
          @Override
          public VariableDeclaration apply(final SingleVariableAccess it) {
            return it.getVariable();
          }
        };
        Iterable<VariableDeclaration> _collect = OclExtensions.<SingleVariableAccess, VariableDeclaration>collect(_typeSelect, _function);
        final Function1<VariableDeclaration, Boolean> _function_1 = new Function1<VariableDeclaration, Boolean>() {
          @Override
          public Boolean apply(final VariableDeclaration it) {
            AbstractTypeDeclaration _eTypeSelectContainer = OclExtensions.<AbstractTypeDeclaration>eTypeSelectContainer(it, AbstractTypeDeclaration.class);
            return Boolean.valueOf(Objects.equal(_eTypeSelectContainer, type));
          }
        };
        final Iterable<VariableDeclaration> accessedFieldsOfType = OclExtensions.<VariableDeclaration>select(_collect, _function_1);
        result.put(method, accessedFieldsOfType);
        String _name = method.getName();
        String _plus = (_name + "->");
        final Function1<VariableDeclaration, Integer> _function_2 = new Function1<VariableDeclaration, Integer>() {
          @Override
          public Integer apply(final VariableDeclaration it) {
            return Integer.valueOf(1);
          }
        };
        Integer _sum = OclExtensions.<VariableDeclaration>sum(accessedFieldsOfType, _function_2);
        String _plus_1 = (_plus + _sum);
        InputOutput.<String>println(_plus_1);
        return result;
      }
    };
    final HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>> methodToFieldsMap = IterableExtensions.<AbstractMethodDeclaration, HashMap<AbstractMethodDeclaration, Iterable<VariableDeclaration>>>fold(methods, _hashMap, _function_1);
    final HashSet<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>> pairsWithAccessesOfCommonFields = new HashSet<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>>();
    final HashSet<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>> pairsWithOutAccessesOfCommonFields = new HashSet<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>>();
    final Consumer<AbstractMethodDeclaration> _function_2 = new Consumer<AbstractMethodDeclaration>() {
      @Override
      public void accept(final AbstractMethodDeclaration m1) {
        final Consumer<AbstractMethodDeclaration> _function = new Consumer<AbstractMethodDeclaration>() {
          @Override
          public void accept(final AbstractMethodDeclaration m2) {
            boolean _notEquals = (!Objects.equal(m1, m2));
            if (_notEquals) {
              Iterable<VariableDeclaration> _get = methodToFieldsMap.get(m1);
              final Function1<VariableDeclaration, Boolean> _function = new Function1<VariableDeclaration, Boolean>() {
                @Override
                public Boolean apply(final VariableDeclaration field) {
                  Iterable<VariableDeclaration> _get = methodToFieldsMap.get(m2);
                  final Function1<VariableDeclaration, Boolean> _function = new Function1<VariableDeclaration, Boolean>() {
                    @Override
                    public Boolean apply(final VariableDeclaration it) {
                      return Boolean.valueOf(Objects.equal(it, field));
                    }
                  };
                  return Boolean.valueOf(IterableExtensions.<VariableDeclaration>exists(_get, _function));
                }
              };
              boolean _exists = IterableExtensions.<VariableDeclaration>exists(_get, _function);
              if (_exists) {
                ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration> _unorderedPair = new ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>(m1, m2);
                pairsWithAccessesOfCommonFields.add(_unorderedPair);
              } else {
                ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration> _unorderedPair_1 = new ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>(m1, m2);
                pairsWithOutAccessesOfCommonFields.add(_unorderedPair_1);
              }
            }
          }
        };
        methods.forEach(_function);
      }
    };
    methods.forEach(_function_2);
    int _size = pairsWithOutAccessesOfCommonFields.size();
    int _size_1 = pairsWithAccessesOfCommonFields.size();
    final int result = (_size - _size_1);
    final Function1<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>, Integer> _function_3 = new Function1<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>, Integer>() {
      @Override
      public Integer apply(final ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration> it) {
        return Integer.valueOf(1);
      }
    };
    Integer _sum_1 = OclExtensions.<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>>sum(pairsWithAccessesOfCommonFields, _function_3);
    String _plus_1 = ("pwcf " + _sum_1);
    InputOutput.<String>println(_plus_1);
    final Function1<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>, Integer> _function_4 = new Function1<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>, Integer>() {
      @Override
      public Integer apply(final ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration> it) {
        return Integer.valueOf(1);
      }
    };
    Integer _sum_2 = OclExtensions.<ModiscoMetrics.UnorderedPair<AbstractMethodDeclaration>>sum(pairsWithOutAccessesOfCommonFields, _function_4);
    String _plus_2 = ("pwocf " + _sum_2);
    InputOutput.<String>println(_plus_2);
    int _xifexpression = (int) 0;
    if ((result >= 0)) {
      _xifexpression = result;
    } else {
      _xifexpression = 0;
    }
    return _xifexpression;
  }
}
