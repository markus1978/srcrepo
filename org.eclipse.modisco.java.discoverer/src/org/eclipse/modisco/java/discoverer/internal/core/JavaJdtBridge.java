/*
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Gabriel Barbier (Mia-Software) - initial API and implementation
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 */

package org.eclipse.modisco.java.discoverer.internal.core;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.EnumConstantDeclaration;
import org.eclipse.gmt.modisco.java.EnumDeclaration;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.VariableDeclaration;
import org.eclipse.gmt.modisco.java.VariableDeclarationExpression;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.eclipse.gmt.modisco.java.VariableDeclarationStatement;
import org.eclipse.gmt.modisco.java.internal.util.JavaUtil;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.Messages;

/** @author Gabriel Barbier */
public class JavaJdtBridge {

	private final boolean debug = false;

	/**
	 * From an IType (instance from JDT abstract syntax tree) we would like to
	 * retrieve corresponding element in JavaModel of the project. The strategy
	 * is to match compilation unit which contains the IType with instance of
	 * CompilationUnit in JavaModel. Then, we will have to find corresponding
	 * type.
	 * 
	 * @param resource
	 *            the JavaModel reflecting the java project
	 * @param jdtType
	 *            the IType instance from JDT AST
	 * @return the TypeDeclaration instance which corresponds to the IType
	 *         instance
	 */
	public final Type getJavaType(final Resource resource, final IType jdtType) {
		Type result = null;
		/*
		 * first step retrieve the model
		 */
		Model rootModel = null;
		for (EObject eObject : resource.getContents()) {
			if (eObject instanceof Model) {
				rootModel = (Model) eObject;
			}
		}
		if (rootModel != null) {
			/*
			 * 2. we use util tools to get corresponding element from qualified
			 * name
			 */
			String qualifiedName = jdtType.getFullyQualifiedName();
			NamedElement element = JavaUtil
					.getNamedElementByQualifiedName(rootModel, qualifiedName);
			if (this.debug) {
				System.out.println(Messages.JavaJdtBridge_0 + element.getName());
			}
			if (element instanceof Type) {
				result = (Type) element;
			}
		}
		return result;
	}

	/**
	 * From an IMethod (instance from JDT abstract syntax tree) we would like to
	 * retrieve corresponding element in JavaModel of the project. It could be a
	 * constructor or an operation. The strategy is to match compilation unit
	 * which contains the IMethod with instance of CompilationUnit in JavaModel.
	 * Then, we will have to find corresponding type and method.
	 * 
	 * @param resource
	 *            the JavaModel reflecting the java project
	 * @param javaMethod
	 *            the IMethod instance from JDT AST
	 * @return the AbstractMethodDeclaration instance which corresponds to the
	 *         IMethod instance
	 */
	public final AbstractMethodDeclaration getJavaOperation(final Resource resource,
			final IMethod jdtMethod) {
		AbstractMethodDeclaration result = null;
		/*
		 * first step retrieve the model
		 */
		Model rootModel = null;
		for (EObject eObject : resource.getContents()) {
			if (eObject instanceof Model) {
				rootModel = (Model) eObject;
			}
		}
		if (rootModel != null) {
			/*
			 * we use util tools to get corresponding element from qualified
			 * name
			 */
			String qualifiedName = jdtMethod.getDeclaringType().getFullyQualifiedName();
			NamedElement element = JavaUtil
					.getNamedElementByQualifiedName(rootModel, qualifiedName);
			if (this.debug) {
				System.out.println(Messages.JavaJdtBridge_0 + element.getName());
			}
			if (element instanceof AbstractTypeDeclaration) {
				AbstractTypeDeclaration parentClass = (AbstractTypeDeclaration) element;
				/*
				 * get the good operation using signature : - name - number of
				 * parameters - type of parameters - name of parameters
				 */
				for (BodyDeclaration bodyDeclaration : parentClass.getBodyDeclarations()) {
					if (bodyDeclaration instanceof AbstractMethodDeclaration) {
						AbstractMethodDeclaration operation = (AbstractMethodDeclaration) bodyDeclaration;
						if (jdtMethod.getElementName().equals(operation.getName())) {
							if (jdtMethod.getNumberOfParameters() == operation.getParameters()
									.size()) {
								// check attributes name and type
								result = operation;

							}
						}
					}
				}
			}
		}

		return result;
	}

	public final IMethod getJdtOperation(final IJavaProject javaProject,
			final AbstractMethodDeclaration operation) {
		IMethod result = null;
		/*
		 * Typically, we have the class containing this method, and will use its
		 * qualified name to retrieve corresponding element in java project ie,
		 * the real artifact (java file).
		 */
		if (operation.getAbstractTypeDeclaration() != null) {
			String containerQN = JavaUtil.getQualifiedName(operation.getAbstractTypeDeclaration());
			try {
				IType jdtType = javaProject.findType(containerQN);
				if (jdtType != null) {
					if (this.debug) {
						System.out.println(Messages.JavaJdtBridge_1
								+ jdtType.getFullyQualifiedName());
					}
					result = findCorrespondingMethod(jdtType, operation);
				}
			} catch (JavaModelException e) {
				MoDiscoLogger.logError(e, JavaActivator.getDefault());
			}
		}

		return result;
	}

	private final IMethod findCorrespondingMethod(final IType jdtType,
			final AbstractMethodDeclaration operation) throws JavaModelException {
		IMethod result = null;
		/*
		 * Finds the methods in this type that correspond to the given method. A
		 * method m1 corresponds to another method m2 if: -m1 has the same
		 * element name as m2. -m1 has the same number of arguments as m2 and
		 * the simple names of the argument types must be equals. -m1 exists.
		 */
		int size = operation.getParameters().size();
		if (this.debug) {
			System.out.println(Messages.JavaJdtBridge_2);
			System.out.println(Messages.JavaJdtBridge_3 + size);
		}
		for (IMethod jdtMethod : jdtType.getMethods()) {
			if (jdtMethod.getElementName().equals(operation.getName())) {
				if (jdtMethod.getNumberOfParameters() == size) {
					// check attributes name and type
					boolean found = true;
					for (int index = 0; found && (index < size); index++) {
						SingleVariableDeclaration javaParameter = operation.getParameters().get(
								index);
						String jdtName = jdtMethod.getParameterNames()[index];
						String jdtTypeName = jdtMethod.getParameterTypes()[index];
						found = javaParameter.getName().equals(jdtName);
						// There is "always" a Q as a prefix for the type name
						// TODO fix the type name provided by the jdt
						boolean typeNameFixed = false;
						if (typeNameFixed && found) {
							found = getTypeName(javaParameter).equals(jdtTypeName);
						}
					}
					if (found) {
						result = jdtMethod;
						if (this.debug) {
							System.out.println(Messages.JavaJdtBridge_4 + result.getSignature());
						}
					}
				}
			}
		}
		return result;
	}

	public final IJavaElement getJdtField(final IJavaProject javaProject,
			final VariableDeclaration field) {
		IJavaElement result = null;
		/*
		 * A variable declaration could correspond to different cases: 1.
		 * container is a field declaration 2. container is a variable
		 * declaration expression (in a loop) 3. container is a variable
		 * declaration statement (local variable) 4. container is a method
		 * declaration (parameter) 5. container is an enumeration declaration
		 * 
		 * 
		 * For cases 1 and 5, we are able to retrieve a jdt element, for other
		 * cases, there is no trivial way to retrieve corresponding jdt element.
		 */
		Type parent = getParentContainer(field);
		if (parent != null) {
			String containerQN = JavaUtil.getQualifiedName(parent);
			try {
				IType jdtType = javaProject.findType(containerQN);
				if (jdtType != null) {
					if (this.debug) {
						System.out.println(Messages.JavaJdtBridge_5
								+ jdtType.getFullyQualifiedName());
					}
					EObject container = field.eContainer();
					if ((container instanceof FieldDeclaration)
							|| (container instanceof EnumDeclaration)) {
						/*
						 * Finds the variable in this type that correspond to
						 * the given field.
						 */
						IField ifield = jdtType.getField(field.getName());
						if (ifield != null) {
							result = ifield;
						} else {
							MoDiscoLogger.logWarning(Messages.JavaJdtBridge_6,
									JavaActivator.getDefault());
						}
					} else if (container instanceof AbstractMethodDeclaration) {
						AbstractMethodDeclaration operation = (AbstractMethodDeclaration) container;
						IMethod jdtMethod = findCorrespondingMethod(jdtType, operation);
						// because we are not able to retrieve specific jdt
						// element
						// we will return method reference instead
						result = jdtMethod;
					} else if (container instanceof VariableDeclarationStatement) {
						// Nothing
						assert (true); // dummy code for "EmptyBlock"
					} else if (container instanceof VariableDeclarationExpression) {
						// Nothing
						assert (true); // dummy code for "EmptyBlock"
					}
				}
			} catch (JavaModelException e) {
				MoDiscoLogger.logError(e, JavaActivator.getDefault());
			}
		}

		return result;
	}

	private static final String getTypeName(final SingleVariableDeclaration javaParameter) {
		String result = Messages.JavaJdtBridge_7;
		TypeAccess realType = javaParameter.getType();
		result = realType.getType().getName();
		return result;
	}

	private final Type getParentContainer(final EObject node) {
		Type parent = null;
		if (node instanceof Type) {
			parent = (Type) node;
		} else {
			if (node.eContainer() != null) {
				parent = getParentContainer(node.eContainer());
			}
		}
		return parent;
	}

	/**
	 * From an IPackageFragment (instance from JDT abstract syntax tree) we
	 * would like to retrieve corresponding element in JavaModel of the project.
	 * The strategy is to match the package declaration using qualified name.
	 * 
	 * @param resource
	 *            the JavaModel reflecting the java project
	 * @param jdtFragment
	 *            the IPackageFragment instance from JDT AST
	 * @return the Package instance which corresponds to the IPackageFragment
	 *         instance
	 */
	public final org.eclipse.gmt.modisco.java.Package getJavaPackage(final Resource resource,
			final IPackageFragment jdtFragment) {
		org.eclipse.gmt.modisco.java.Package result = null;
		/*
		 * first step retrieve the model
		 */
		Model rootModel = null;
		for (EObject eObject : resource.getContents()) {
			if (eObject instanceof Model) {
				rootModel = (Model) eObject;
			}
		}
		if (rootModel != null) {
			/*
			 * 2. we use util tools to get corresponding element from qualified
			 * name
			 */
			String qualifiedName = jdtFragment.getElementName();
			NamedElement element = JavaUtil
					.getNamedElementByQualifiedName(rootModel, qualifiedName);
			if (this.debug) {
				System.out.println(Messages.JavaJdtBridge_0 + element.getName());
			}
			if (element instanceof org.eclipse.gmt.modisco.java.Package) {
				result = (org.eclipse.gmt.modisco.java.Package) element;
			}
		}
		return result;
	}

	/**
	 * From an IField (instance from JDT abstract syntax tree) we would like to
	 * retrieve corresponding element in JavaModel of the project. The strategy
	 * is to match the variable declaration using qualified name. Because in ui,
	 * we are not able to select local variables and method parameters, so we
	 * only have to manage cases of class variables (static) and instance
	 * variables.
	 * 
	 * @param resource
	 *            the JavaModel reflecting the java project
	 * @param jdtField
	 *            the IField instance from JDT AST
	 * @return the VariableDeclaration instance which corresponds to the IField
	 *         instance
	 */
	public final VariableDeclaration getJavaField(final Resource resource, final IField jdtField) {
		VariableDeclaration result = null;
		/*
		 * first step retrieve the model
		 */
		Model rootModel = null;
		for (EObject eObject : resource.getContents()) {
			if (eObject instanceof Model) {
				rootModel = (Model) eObject;
			}
		}
		if (rootModel != null) {
			/*
			 * we use util tools to get corresponding element from qualified
			 * name
			 */
			String qualifiedName = jdtField.getDeclaringType().getFullyQualifiedName();
			NamedElement element = JavaUtil
					.getNamedElementByQualifiedName(rootModel, qualifiedName);
			if (this.debug) {
				System.out.println(Messages.JavaJdtBridge_0 + element.getName());
			}
			if (element instanceof AbstractTypeDeclaration) {
				AbstractTypeDeclaration parentClass = (AbstractTypeDeclaration) element;
				/*
				 * get the good field using name
				 */
				for (BodyDeclaration bodyDeclaration : parentClass.getBodyDeclarations()) {
					if (bodyDeclaration instanceof FieldDeclaration) {
						FieldDeclaration fieldDeclaration = (FieldDeclaration) bodyDeclaration;
						for (VariableDeclarationFragment fragment : fieldDeclaration.getFragments()) {
							if (jdtField.getElementName().equals(fragment.getName())) {
								result = fragment;
							}
						}
					}
				}
				if (result == null) {
					// perhaps in case of enumeration
					if (parentClass instanceof EnumDeclaration) {
						EnumDeclaration enumDeclaration = (EnumDeclaration) parentClass;
						for (EnumConstantDeclaration enumValue : enumDeclaration.getEnumConstants()) {
							if (jdtField.getElementName().equals(enumValue.getName())) {
								result = enumValue;
							}
						}
					}
				}
			}
		}
		return result;
	}
}
