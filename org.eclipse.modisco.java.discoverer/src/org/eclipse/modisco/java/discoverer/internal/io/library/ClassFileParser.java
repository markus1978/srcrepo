/**
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Romain DERVAUX (Mia-Software) - initial API and implementation
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer.internal.io.library;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.Annotation;
import org.eclipse.gmt.modisco.java.AnnotationMemberValuePair;
import org.eclipse.gmt.modisco.java.AnnotationTypeMemberDeclaration;
import org.eclipse.gmt.modisco.java.Archive;
import org.eclipse.gmt.modisco.java.ArrayInitializer;
import org.eclipse.gmt.modisco.java.ArrayType;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.BooleanLiteral;
import org.eclipse.gmt.modisco.java.CharacterLiteral;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.ClassFile;
import org.eclipse.gmt.modisco.java.EnumConstantDeclaration;
import org.eclipse.gmt.modisco.java.EnumDeclaration;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.InheritanceKind;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Modifier;
import org.eclipse.gmt.modisco.java.NumberLiteral;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.ParameterizedType;
import org.eclipse.gmt.modisco.java.PrimitiveType;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.StringLiteral;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.TypeDeclaration;
import org.eclipse.gmt.modisco.java.TypeLiteral;
import org.eclipse.gmt.modisco.java.TypeParameter;
import org.eclipse.gmt.modisco.java.UnresolvedItem;
import org.eclipse.gmt.modisco.java.UnresolvedItemAccess;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.eclipse.gmt.modisco.java.VisibilityKind;
import org.eclipse.gmt.modisco.java.WildCardType;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeParameter;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.modisco.java.discoverer.internal.io.java.JDTVisitor;
import org.eclipse.modisco.java.discoverer.internal.io.java.JDTVisitorUtils;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.ClassBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.FieldBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.MethodBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement;
import org.eclipse.modisco.java.discoverer.internal.io.library.binding.JavaModelDelegateBindingFactory;

/**
 * The main class for populating the Java model from the JDT Java model of
 * {@link IClassFile class files}.
 * 
 * @see Model
 */
public class ClassFileParser {

	/**
	 * the EMF factory
	 */
	private JavaFactory factory;

	/**
	 * the model
	 */
	private final Model model;

	/**
	 * the global {@code BindingManager}
	 */
	private BindingManager globalBindings;

	/**
	 * a {@code TypeFinder}
	 */
	private final TypeFinder typeFinder;

	/**
	 * the path of the currently visited class file
	 */
	private final String filePath;

	/**
	 * the {@code ClassFile} object associated with the .class file of the
	 * top-level type
	 */
	private ClassFile cuNode;

	/**
	 * the current package
	 */
	private Package currentPackage;

	/**
	 * the currently visited java type or method. Useful to retrieve type
	 * variable definition.
	 */
	private IMember currentlyVisitedJavaElement = null;

	public ClassFileParser(final JavaFactory factory, final Model resultModel,
			final BindingManager globalBindings, final TypeFinder typeFinder, final String filePath) {
		setFactory(factory);
		this.model = resultModel;
		setGlobalBindings(globalBindings);
		this.typeFinder = typeFinder;
		this.filePath = filePath;
	}

	/**
	 * Finds the type variable associated with the currently visited Java
	 * element (type or method) or with it's parent.
	 * 
	 * @param name
	 *            the name of the type variable
	 * @return the {@code ITypeParameter}
	 */
	public ITypeParameter findTypeParameter(final String name) {
		IMember member = this.currentlyVisitedJavaElement;
		ITypeParameter typeParameter = null;
		while (member != null) {
			if (member.getElementType() == IJavaElement.METHOD) {
				typeParameter = ((IMethod) member).getTypeParameter(name);
			} else {
				typeParameter = ((IType) member).getTypeParameter(name);
			}
			if (typeParameter.exists()) {
				break;
			}
			member = member.getDeclaringType();
		}
		return typeParameter;
	}

	public TypeFinder getTypeFinder() {
		return this.typeFinder;
	}

	/**
	 * @see org.eclipse.modisco.java.discoverer.internal.io.java.JDTVisitor#initializeNode(ASTNode,
	 *      org.eclipse.jdt.core.dom.ASTNode)
	 */
	public void initializeNode(final ASTNode element) {
		element.setOriginalClassFile(this.cuNode);
	}

	public void parse(final IClassFile classFile) throws JavaModelException {

		IType type = classFile.getType();

		// we only want top level types
		if (!type.exists() || type.isAnonymous() || type.isLocal() || type.isMember()) {
			return;
		}

		// we check if this type has already been visited
		Binding id = JavaModelDelegateBindingFactory.getInstance().getBindingForElement(type, this);
		if (getGlobalBindings().containsTarget(id)) {
			return;
		}

		visitPackage(type.getPackageFragment());
		visitClassFile(classFile);

		Archive archive = LibraryReader.getArchive(classFile, getFactory(), this.model);
		if (archive != null) {
			// jar or zip file
			archive.getClassFiles().add(this.cuNode);
		} else {
			// folder
			this.model.getClassFiles().add(this.cuNode);
		}

		parse2(type, null);
	}

	/**
	 * Creation of the {@link ClassFile} object
	 * 
	 * @param classFile
	 */
	protected void visitClassFile(final IClassFile classFile) {
		ClassFile element = getFactory().createClassFile();
		this.model.getClassFiles().add(element);

		element.setName(classFile.getElementName());
		element.setOriginalFilePath(this.filePath);
		element.setPackage(this.currentPackage);

		this.cuNode = element;
	}

	/**
	 * Visit the members (fields, methods and types) of the {@code type}.
	 * 
	 * @param type
	 *            the java model {@code IType}
	 * @param enclosingType
	 *            the MoDisco enclosing type of the {@code IType}
	 * @return the {@link AbstractTypeDeclaration} object corresponding to the
	 *         {@code IType}, {@code null} if type does not exist.
	 * @throws JavaModelException
	 */
	protected AbstractTypeDeclaration parse2(final IType type,
			final AbstractTypeDeclaration enclosingType) throws JavaModelException {

		AbstractTypeDeclaration typeDeclaration = visitType(type);

		if (enclosingType == null) {
			// the class file object registers the top level type
			this.cuNode.setType(typeDeclaration);
		} else {
			// type is an member type
			enclosingType.getBodyDeclarations().add(typeDeclaration);
		}

		// fields
		for (IField f : type.getFields()) {
			if (type.isEnum() && f.isEnumConstant()) {
				EnumConstantDeclaration constant = visitEnumConstantDeclaration(f);
				if (constant != null) {
					((EnumDeclaration) typeDeclaration).getEnumConstants().add(constant);
				}
			} else {
				// declaring type is an annotation, interface or type
				FieldDeclaration field = visitField(f);
				if (field != null) {
					typeDeclaration.getBodyDeclarations().add(field);
				}
			}
		}

		// methods
		for (IMethod m : type.getMethods()) {
			BodyDeclaration method = null;
			if (type.isAnnotation()) {
				method = visitAnnotationTypeMemberDeclaration(m);
			} else {
				method = visitMethod(m);
			}
			if (method != null) {
				typeDeclaration.getBodyDeclarations().add(method);
			}
		}

		// member types
		for (IType subType : type.getTypes()) {
			if (subType.exists()) {
				parse2(subType, typeDeclaration);
			}
		}

		return typeDeclaration;
	}

	/**
	 * Visit the package of the class file.
	 * 
	 * @param packageFragment
	 *            the {@code IPackageFragment}
	 */
	protected void visitPackage(final IPackageFragment packageFragment) {

		Package element = null;

		// the empty package
		if (!packageFragment.exists() || packageFragment.getElementName().length() == 0) {
			element = (Package) getGlobalBindings().getTarget(JDTVisitor.DEFAULT_PKG_ID);
			if (element == null) {
				element = getFactory().createPackage();
				element.setName(JDTVisitor.DEFAULT_PKG_ID);
				element.setModel(this.model);
				getGlobalBindings().addTarget(JDTVisitor.DEFAULT_PKG_ID, element);
			}
		} else {
			// named package
			Binding id = JavaModelDelegateBindingFactory.getInstance().getBindingForElement(
					packageFragment, this);
			if (!getGlobalBindings().containsTarget(id.toString())) {
				element = createPackageHierarchy(packageFragment);
				ClassFileParserUtils.manageBindingDeclaration(element, packageFragment, this);
			} else {
				element = (Package) getGlobalBindings().getTarget(id.toString());
			}
		}

		this.currentPackage = element;
	}

	// create iterately a hierarchy of packages
	protected Package createPackageHierarchy(final IPackageFragment packageFragment) {
		Package result = getFactory().createPackage();
		Binding id = JavaModelDelegateBindingFactory.getInstance().getBindingForElement(
				packageFragment, this);
		String currentPackageName = id.getName();
		Package localCurrentPackage = result;
		int lastDotIndex = currentPackageName.lastIndexOf('.');
		if (lastDotIndex == -1) {
			this.model.getOwnedElements().add(result);
			localCurrentPackage.setName(currentPackageName);
		} else {
			// iterate on parents packages to create them if needed
			localCurrentPackage.setName(currentPackageName.substring(lastDotIndex + 1));
			while (lastDotIndex > 0) {
				// add qualified name for curent Package
				currentPackageName = currentPackageName.substring(0, lastDotIndex);
				Package aParentPackage = null;
				if (!getGlobalBindings().containsTarget(currentPackageName)) {
					aParentPackage = getFactory().createPackage();

					getGlobalBindings().addTarget(currentPackageName, aParentPackage);
					lastDotIndex = currentPackageName.lastIndexOf('.');
					if (lastDotIndex < 0) { // top level package
						this.model.getOwnedElements().add(aParentPackage);
						aParentPackage.setName(currentPackageName);
					} else {
						aParentPackage.setName(currentPackageName.substring(lastDotIndex + 1));
					}
					aParentPackage.getOwnedPackages().add(localCurrentPackage);
				} else {
					aParentPackage = (Package) getGlobalBindings().getTarget(currentPackageName); // (PackageDeclaration)binding.get(node);
					aParentPackage.getOwnedPackages().add(localCurrentPackage);
					break; // if this package is registered, parents packages
							// also are
				}
				localCurrentPackage = aParentPackage;
			}
		}
		return result;
	}

	/**
	 * Visit the properties of an {@code IType}.
	 * 
	 * @param type
	 *            the {@code IType}
	 * @return the {@link AbstractTypeDeclaration} object corresponding to the
	 *         {@code IType}
	 * @throws JavaModelException
	 */
	protected AbstractTypeDeclaration visitType(final IType type) throws JavaModelException {

		AbstractTypeDeclaration element = null;
		if (type.isEnum()) {
			element = getFactory().createEnumDeclaration();
		} else if (type.isAnnotation()) {
			element = getFactory().createAnnotationTypeDeclaration();
		} else if (type.isInterface()) {
			element = getFactory().createInterfaceDeclaration();
		} else {
			element = getFactory().createClassDeclaration();
		}

		this.currentlyVisitedJavaElement = type;
		initializeNode(element);

		element.setName(type.getElementName());
		element.setPackage(this.currentPackage);
		this.currentPackage.getOwnedElements().add(element);

		// superClass
		// enums are classes but can't have explicit superclass
		String superClass = type.getSuperclassTypeSignature();
		if (type.isClass() && superClass != null
				&& !ClassFileParserUtils.isJavaLangObject(superClass)) {
			((ClassDeclaration) element).setSuperClass(getRefOnType(superClass));
		}

		// superInterfaces
		// annotations can't have explicit annotations
		if (!type.isAnnotation()) {
			for (String superInterface : type.getSuperInterfaceTypeSignatures()) {
				if (!ClassFileParserUtils.isJavaLangObject(superInterface)) {
					element.getSuperInterfaces().add(getRefOnType(superInterface));
				}
			}
		}

		// type parameters
		ITypeParameter[] parameters = type.getTypeParameters();
		for (ITypeParameter parameter : parameters) {
			TypeParameter t = getFactory().createTypeParameter();
			((TypeDeclaration) element).getTypeParameters().add(t);
			visitTypeParameter(parameter, t);
		}

		// annotations
		for (IAnnotation annotation : type.getAnnotations()) {
			Annotation anno = getFactory().createAnnotation();
			element.getAnnotations().add(anno);
			visitAnnotation(annotation, anno);
		}

		// visibility modifier
		Modifier m = getFactory().createModifier();
		element.setModifier(m);
		m.setBodyDeclaration(element);
		manageModifier(m, type.getFlags(), type);

		ClassFileParserUtils.manageBindingDeclaration(element, type, this);

		return element;
	}

	/**
	 * Visit a {@code IField}.
	 * 
	 * @param field
	 *            the {@code IField}
	 * @return the {@link FieldDeclaration} object corresponding to the
	 *         {@code IField}, or {@code null} if the field does not exist or is
	 *         {@link org.eclipse.jdt.core.Flags#isSynthetic(int) synthetic}
	 * @throws JavaModelException
	 */
	protected FieldDeclaration visitField(final IField field) throws JavaModelException {
		if (Flags.isSynthetic(field.getFlags())) {
			return null;
		}

		FieldDeclaration element = getFactory().createFieldDeclaration();
		initializeNode(element);

		// type
		String type = field.getTypeSignature();
		element.setType(getRefOnType(type));

		// visibility modifier
		Modifier m = getFactory().createModifier();
		element.setModifier(m);
		m.setBodyDeclaration(element);
		manageModifier(m, field.getFlags(), field);

		// the fragment of this field
		VariableDeclarationFragment fragment = getFactory().createVariableDeclarationFragment();
		initializeNode(fragment);
		fragment.setExtraArrayDimensions(0);
		fragment.setName(field.getElementName());
		fragment.setVariablesContainer(element);

		for (IAnnotation annotation : field.getAnnotations()) {
			Annotation anno = getFactory().createAnnotation();
			element.getAnnotations().add(anno);
			visitAnnotation(annotation, anno);
		}

		ClassFileParserUtils.manageBindingDeclaration(fragment, field, this);

		return element;
	}

	protected AnnotationTypeMemberDeclaration visitAnnotationTypeMemberDeclaration(
			final IMethod method) throws JavaModelException {
		AnnotationTypeMemberDeclaration element = getFactory()
				.createAnnotationTypeMemberDeclaration();
		initializeNode(element);

		element.setName(method.getElementName());
		element.setType(getRefOnType(method.getReturnType()));

		// the default value of this annotation member
		IMemberValuePair defaultValue = method.getDefaultValue();
		if (defaultValue != null) {
			Expression result = manageMemberValuePair(defaultValue);
			element.setDefault(result);
		}

		Modifier m = getFactory().createModifier();
		m.setBodyDeclaration(element);
		element.setModifier(m);
		manageModifier(m, method.getFlags(), method);

		ClassFileParserUtils.manageBindingDeclaration(element, method, this);

		return element;
	}

	protected EnumConstantDeclaration visitEnumConstantDeclaration(final IField field)
			throws JavaModelException {
		EnumConstantDeclaration element = getFactory().createEnumConstantDeclaration();
		initializeNode(element);

		element.setName(field.getElementName());
		element.setModifier(getFactory().createModifier());

		// annotations
		for (IAnnotation annotation : field.getAnnotations()) {
			Annotation anno = getFactory().createAnnotation();
			element.getAnnotations().add(anno);
			visitAnnotation(annotation, anno);
		}

		ClassFileParserUtils.manageBindingDeclaration(element, field, this);

		return element;
	}

	/**
	 * Visit a {@code IMethod}.
	 * 
	 * @param method
	 *            the {@code IMethod}
	 * @return the {@link AbstractMethodDeclaration} object corresponding to the
	 *         {@code IMethod}, or {@code null} if the method does not exist or
	 *         is {@link org.eclipse.jdt.core.Flags#isSynthetic(int) synthetic}
	 *         or is {@link org.eclipse.jdt.core.Flags#isBridge(int) bridge} or
	 *         its name equals the special name <code>"&lt;clinit&gt;"</code>
	 * @throws JavaModelException
	 * @see org.eclipse.jdt.core.IMethod#getElementName()
	 */
	protected AbstractMethodDeclaration visitMethod(final IMethod method) throws JavaModelException {
		if (Flags.isSynthetic(method.getFlags()) || Flags.isBridge(method.getFlags())
				|| method.getElementName().equals("<clinit>")) { //$NON-NLS-1$
			return null;
		}

		this.currentlyVisitedJavaElement = method;
		AbstractMethodDeclaration element = null;
		if (method.isConstructor()) {
			element = getFactory().createConstructorDeclaration();
		} else {
			element = getFactory().createMethodDeclaration();
		}
		initializeNode(element);

		element.setName(method.getElementName());

		// throwns exceptions
		for (String exc : method.getExceptionTypes()) {
			element.getThrownExceptions().add(getRefOnType(exc));
		}

		// return type
		if (!method.isConstructor()) {
			String returnType = method.getReturnType();
			((MethodDeclaration) element).setReturnType(getRefOnType(returnType));
		}

		// type parameters
		ITypeParameter[] parameters = method.getTypeParameters();
		for (ITypeParameter parameter : parameters) {
			TypeParameter t = getFactory().createTypeParameter();
			element.getTypeParameters().add(t);
			visitTypeParameter(parameter, t);
		}

		// parameters
		for (int i = 0; i < method.getNumberOfParameters(); i++) {
			String parameterType = method.getParameterTypes()[i];
			String parameterName = method.getRawParameterNames()[i];

			SingleVariableDeclaration var = getFactory().createSingleVariableDeclaration();
			initializeNode(var);
			element.getParameters().add(var);
			var.setMethodDeclaration(element);
			var.setName(parameterName);
			var.setExtraArrayDimensions(0);
			// varargs option for the last argument
			if (i == method.getNumberOfParameters() - 1) {
				boolean isMethodVarargs = Flags.isVarargs(method.getFlags());
				var.setVarargs(isMethodVarargs);
			}

			var.setType(getRefOnType(parameterType));
		}

		// annotations
		for (IAnnotation annotation : method.getAnnotations()) {
			Annotation anno = getFactory().createAnnotation();
			element.getAnnotations().add(anno);
			visitAnnotation(annotation, anno);
		}

		// visibility modifier
		Modifier m = getFactory().createModifier();
		element.setModifier(m);
		m.setBodyDeclaration(element);
		manageModifier(m, method.getFlags(), method);

		ClassFileParserUtils.manageBindingDeclaration(element, method, this);

		return element;
	}

	protected void visitAnnotation(final IAnnotation annotation, final Annotation anno)
			throws JavaModelException {
		initializeNode(anno);

		String typeName = annotation.getElementName();
		// typeName is not a signature : java.lang.Deprecated
		String typeSignature = Signature.createTypeSignature(typeName, true);
		anno.setType(getRefOnType(typeSignature));

		for (IMemberValuePair valuePair : annotation.getMemberValuePairs()) {
			AnnotationMemberValuePair element = getFactory().createAnnotationMemberValuePair();
			initializeNode(element);
			anno.getValues().add(element);

			Expression value = manageMemberValuePair(valuePair);
			element.setValue(value);

			// member is a method declared in an annotation
			PendingElement pending = new PendingElement(getFactory());
			pending.setClientNode(element);
			pending.setLinkName("member"); //$NON-NLS-1$
			Binding id = null;
			if (this.typeFinder.isTypeExists(typeName)) {
				ClassBinding parent = JavaModelDelegateBindingFactory.getInstance()
						.getBindingForName(typeSignature, this, true);
				id = new MethodBinding();
				((MethodBinding) id).setDeclaringClass(parent);
				id.setName(valuePair.getMemberName());
			} else {
				id = new Binding();
				id.setName(typeName + "." + valuePair.getMemberName() + "()"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			getGlobalBindings().addPending(pending, id);
		}
	}

	private Expression manageMemberValuePair(final IMemberValuePair defaultValue) {

		Expression result = null;

		switch (defaultValue.getValueKind()) {
		case IMemberValuePair.K_CLASS:
			result = manageValuePairClassKind(defaultValue);
			break;

		case IMemberValuePair.K_ANNOTATION:
			result = manageValuePairAnnotationKind(defaultValue);
			break;

		case IMemberValuePair.K_QUALIFIED_NAME:
			result = manageValuePairQualifiedNameKind(defaultValue);
			break;

		case IMemberValuePair.K_BOOLEAN:
			if (defaultValue.getValue().getClass().isArray()) {
				Object[] tab = (Object[]) defaultValue.getValue();
				ArrayInitializer array = getFactory().createArrayInitializer();
				initializeNode(array);
				for (Object element : tab) {
					BooleanLiteral bool = getFactory().createBooleanLiteral();
					bool.setValue(((Boolean) element).booleanValue());
					array.getExpressions().add(bool);
				}
				result = array;
			} else {
				BooleanLiteral bool = getFactory().createBooleanLiteral();
				bool.setValue(((Boolean) defaultValue.getValue()).booleanValue());
				result = bool;
			}
			break;

		case IMemberValuePair.K_CHAR:
			if (defaultValue.getValue().getClass().isArray()) {
				Object[] tab = (Object[]) defaultValue.getValue();
				ArrayInitializer array = getFactory().createArrayInitializer();
				initializeNode(array);
				for (Object element : tab) {
					CharacterLiteral ch = getFactory().createCharacterLiteral();
					char value = ((Character) element).charValue();
					ch.setEscapedValue(ClassFileParserUtils.escapeCharacter(value));
					array.getExpressions().add(ch);
				}
				result = array;
			} else {
				CharacterLiteral ch = getFactory().createCharacterLiteral();
				char value = ((Character) defaultValue.getValue()).charValue();
				ch.setEscapedValue(ClassFileParserUtils.escapeCharacter(value));
				result = ch;
			}
			break;

		case IMemberValuePair.K_DOUBLE:
		case IMemberValuePair.K_BYTE:
		case IMemberValuePair.K_FLOAT:
		case IMemberValuePair.K_INT:
		case IMemberValuePair.K_LONG:
			if (defaultValue.getValue().getClass().isArray()) {
				Object[] tab = (Object[]) defaultValue.getValue();
				ArrayInitializer array = getFactory().createArrayInitializer();
				initializeNode(array);
				for (Object element : tab) {
					NumberLiteral number = getFactory().createNumberLiteral();
					number.setTokenValue(element.toString());
					array.getExpressions().add(number);
				}
				result = array;
			} else {
				NumberLiteral number = getFactory().createNumberLiteral();
				number.setTokenValue(defaultValue.getValue().toString());
				result = number;
			}
			break;

		case IMemberValuePair.K_STRING:
			if (defaultValue.getValue().getClass().isArray()) {
				Object[] tab = (Object[]) defaultValue.getValue();
				ArrayInitializer array = getFactory().createArrayInitializer();
				initializeNode(array);
				for (Object element : tab) {
					StringLiteral string = getFactory().createStringLiteral();
					String value = String.valueOf(element);
					string.setEscapedValue(ClassFileParserUtils.escapeString(value));
					array.getExpressions().add(string);
				}
				result = array;
			} else {
				StringLiteral string = getFactory().createStringLiteral();
				String value = String.valueOf(defaultValue.getValue());
				string.setEscapedValue(ClassFileParserUtils.escapeString(value));
				result = string;
			}
			break;

		case IMemberValuePair.K_SIMPLE_NAME: // there should be no K_SIMPLE_NAME
												// in .class files
		case IMemberValuePair.K_UNKNOWN:
		default:
			if (defaultValue.getValue().getClass().isArray()) {
				Object[] tab = (Object[]) defaultValue.getValue();
				ArrayInitializer array = getFactory().createArrayInitializer();
				initializeNode(array);
				for (Object element : tab) {
					UnresolvedItemAccess unrAcc = getFactory().createUnresolvedItemAccess();
					UnresolvedItem item = getFactory().createUnresolvedItem();
					unrAcc.setElement(item);
					item.setName(String.valueOf(element));
					array.getExpressions().add(unrAcc);
				}
				result = array;
			} else {
				UnresolvedItemAccess unrAcc = getFactory().createUnresolvedItemAccess();
				UnresolvedItem item = getFactory().createUnresolvedItem();
				unrAcc.setElement(item);
				item.setName(String.valueOf(defaultValue.getValue()));
				result = unrAcc;
			}
			break;
		}
		return result;
	}

	private Expression manageValuePairQualifiedNameKind(final IMemberValuePair defaultValue) {
		Expression result;
		if (defaultValue.getValue().getClass().isArray()) {
			Object[] tab = (Object[]) defaultValue.getValue();
			ArrayInitializer array = getFactory().createArrayInitializer();
			initializeNode(array);
			for (Object element : tab) {
				SingleVariableAccess varAcc = getFactory().createSingleVariableAccess();
				PendingElement pending = new PendingElement(getFactory());
				pending.setClientNode(varAcc);
				pending.setLinkName("variable"); //$NON-NLS-1$
				String name = String.valueOf(element.toString());
				String memberName = name.substring(name.lastIndexOf('.') + 1);
				String typeName = name.substring(0, name.lastIndexOf('.'));

				Binding id = null;
				if (this.typeFinder.isTypeExists(typeName)) {
					ClassBinding parent = JavaModelDelegateBindingFactory.getInstance()
							.getBindingForName(Signature.createTypeSignature(typeName, true), this,
									true);
					id = new FieldBinding();
					((FieldBinding) id).setDeclaringClass(parent);
					id.setName(memberName);
				} else {
					id = new Binding();
					id.setName(typeName + "." + memberName); //$NON-NLS-1$
				}
				getGlobalBindings().addPending(pending, id);
				array.getExpressions().add(varAcc);
			}
			result = array;
		} else {
			// reference on a field or an enum constant
			// can't use
			// JavaModelDelegateBindingFactory.getBindingForName(String,
			// ClassFileParser)
			// since it is dedicated to types references
			SingleVariableAccess varAcc = getFactory().createSingleVariableAccess();
			PendingElement pending = new PendingElement(getFactory());
			pending.setClientNode(varAcc);
			pending.setLinkName("variable"); //$NON-NLS-1$
			String name = String.valueOf(defaultValue.getValue());
			String memberName = name.substring(name.lastIndexOf('.') + 1);
			String typeName = name.substring(0, name.lastIndexOf('.'));

			Binding id = null;
			if (this.typeFinder.isTypeExists(typeName)) {
				ClassBinding parent = JavaModelDelegateBindingFactory.getInstance()
						.getBindingForName(Signature.createTypeSignature(typeName, true), this,
								true);
				id = new FieldBinding();
				((FieldBinding) id).setDeclaringClass(parent);
				id.setName(memberName);
			} else {
				id = new Binding();
				id.setName(typeName + "." + memberName); //$NON-NLS-1$
			}
			getGlobalBindings().addPending(pending, id);
			result = varAcc;
		}
		return result;
	}

	private Expression manageValuePairAnnotationKind(final IMemberValuePair defaultValue) {
		Expression result;
		if (defaultValue.getValue().getClass().isArray()) {
			Object[] tab = (Object[]) defaultValue.getValue();
			ArrayInitializer array = getFactory().createArrayInitializer();
			initializeNode(array);
			for (Object element : tab) {
				IAnnotation annotation = (IAnnotation) element;
				Annotation anno = getFactory().createAnnotation();
				try {
					visitAnnotation(annotation, anno);
				} catch (JavaModelException e) {
					// nothing
					assert (true); // Dummy code for "EmptyBlock" rule
				}
				array.getExpressions().add(anno);
			}
			result = array;
		} else {
			IAnnotation annotation = (IAnnotation) defaultValue.getValue();
			Annotation anno = getFactory().createAnnotation();
			try {
				visitAnnotation(annotation, anno);
			} catch (JavaModelException e) {
				// nothing
				assert (true); // Dummy code for "EmptyBlock" rule
			}
			result = anno;
		}
		return result;
	}

	private Expression manageValuePairClassKind(final IMemberValuePair defaultValue) {
		Expression result;
		if (defaultValue.getValue().getClass().isArray()) {
			Object[] tab = (Object[]) defaultValue.getValue();
			ArrayInitializer array = getFactory().createArrayInitializer();
			initializeNode(array);
			for (Object element : tab) {
				// the type is not a signature, we have to create one
				String typeName = element.toString();
				String typeSignature = Signature.createTypeSignature(typeName, true);
				TypeLiteral literal = getFactory().createTypeLiteral();
				literal.setType(getRefOnType(typeSignature));
				array.getExpressions().add(literal);
			}
			result = array;
		} else {
			// the type is not a signature, we have to create one
			String typeName = defaultValue.getValue().toString();
			String typeSignature = Signature.createTypeSignature(typeName, true);
			TypeLiteral literal = getFactory().createTypeLiteral();
			literal.setType(getRefOnType(typeSignature));
			result = literal;
		}
		return result;
	}

	protected void visitTypeParameter(final ITypeParameter typeParameter,
			final TypeParameter element) throws JavaModelException {
		initializeNode(element);
		element.setName(typeParameter.getElementName());

		String[] boundsNames = typeParameter.getBounds();

		for (String bound : boundsNames) {
			// bound names are not signatures
			String boundSignature = Signature.createTypeSignature(bound, true);
			if (!ClassFileParserUtils.isJavaLangObject(boundSignature)) {
				element.getBounds().add(getRefOnType(boundSignature));
			}
		}

		ClassFileParserUtils.manageBindingDeclaration(element, typeParameter, this);
	}

	/**
	 * Handle a reference on a type.
	 * <p>
	 * Decodes the signature and tries to resolve immediately the reference. If
	 * unresolvable, a pending reference is created.
	 * </p>
	 * So, the returned {@code TypeAccess} may or may not have a resolved
	 * {@link TypeAccess#getType() type}.
	 * 
	 * @param qualifiedName
	 *            the signature of the referenced type (eg : [I,
	 *            Ljava.lang.Object;)
	 * @return a {@code TypeAccess}
	 */
	private TypeAccess getRefOnType(final String qualifiedName) {

		TypeAccess typAcc = getFactory().createTypeAccess();

		switch (Signature.getTypeSignatureKind(qualifiedName)) {
		case Signature.ARRAY_TYPE_SIGNATURE:
			visitArrayType(typAcc, qualifiedName, this);
			break;

		case Signature.BASE_TYPE_SIGNATURE:
			visitPrimitiveType(typAcc, qualifiedName);
			break;

		case Signature.CLASS_TYPE_SIGNATURE:
			// a class type signature can be simple or parameterized
			if (Signature.getTypeArguments(qualifiedName).length > 0) {
				visitParameterizedType(typAcc, qualifiedName); // Ljava.util.List<Ljava.lang.String;>;
			} else {
				ClassFileParserUtils.manageBindingRef(typAcc, qualifiedName, this); // Ljava.util.List;
			}
			break;

		case Signature.WILDCARD_TYPE_SIGNATURE:
			visitWildCardType(typAcc, qualifiedName);
			break;

		case Signature.TYPE_VARIABLE_SIGNATURE:
			ClassFileParserUtils.manageBindingRef(typAcc, qualifiedName, this);
			break;

		case Signature.CAPTURE_TYPE_SIGNATURE:
			ClassFileParserUtils.manageBindingRef(typAcc,
					JavaModelDelegateBindingFactory.JAVA_LANG_OBJECT_SIGNATURE, this);
			break;

		default:
			// nothing
		}
		return typAcc;
	}

	protected void visitParameterizedType(final TypeAccess type, final String qualifiedName) {
		Binding id = JavaModelDelegateBindingFactory.getInstance().getBindingForParameterizedType(
				qualifiedName, this, false);
		ParameterizedType parameterizedType = (ParameterizedType) getGlobalBindings().getTarget(id);
		if (parameterizedType == null) {
			parameterizedType = getFactory().createParameterizedType();
			parameterizedType.setName(id.toString());

			parameterizedType.setType(getRefOnType(Signature.getTypeErasure(qualifiedName)));

			String[] typeArgumentsSignatures = Signature.getTypeArguments(qualifiedName);
			for (String typeArgumentsSignature : typeArgumentsSignatures) {
				parameterizedType.getTypeArguments().add(getRefOnType(typeArgumentsSignature));
			}
			this.model.getOrphanTypes().add(parameterizedType);
			getGlobalBindings().addTarget(id, parameterizedType);
		}
		type.setType(parameterizedType);
	}

	protected void visitWildCardType(final TypeAccess type, final String qualifiedName) {
		Binding id = JavaModelDelegateBindingFactory.getInstance().getBindingForWildCardType(
				qualifiedName, this);
		WildCardType wildCardType = (WildCardType) getGlobalBindings().getTarget(id);
		if (wildCardType == null) {
			wildCardType = getFactory().createWildCardType();

			boolean isUpperBound = false;
			String boundSignature = null;

			switch (qualifiedName.charAt(0)) {
			case Signature.C_EXTENDS:
				isUpperBound = true;
				break;
			case Signature.C_SUPER:
				boundSignature = qualifiedName.substring(1); // remove the first
																// character
				break;
			case Signature.C_STAR:
				break;
			default:
				break;
			}
			if (boundSignature != null) {
				wildCardType.setBound(getRefOnType(boundSignature));
			}
			wildCardType.setUpperBound(isUpperBound);
			wildCardType.setName(id.toString());

			this.model.getOrphanTypes().add(wildCardType);
			getGlobalBindings().addTarget(id, wildCardType);
		}
		type.setType(wildCardType);
	}

	/**
	 * @see JDTVisitorUtils#manageBindingRef(org.eclipse.jdt.core.dom.PrimitiveType,
	 *      JDTVisitor)
	 */
	protected void visitPrimitiveType(final TypeAccess type, final String qualifiedName) {
		Binding id = JavaModelDelegateBindingFactory
				.getBindingForPrimitiveType(qualifiedName, this);
		PrimitiveType primitiveType = (PrimitiveType) getGlobalBindings().getTarget(id.toString());
		if (primitiveType == null) {
			ClassFileParserUtils.initializePrimitiveTypes(getFactory(), this.model,
					getGlobalBindings());
			primitiveType = (PrimitiveType) getGlobalBindings().getTarget(id.toString());
		}
		if (primitiveType == null) { // should never happen
			primitiveType = getFactory().createPrimitiveType();
			primitiveType.setName(id.toString());
			this.model.getOrphanTypes().add(primitiveType);
			getGlobalBindings().addTarget(id, primitiveType);

		}
		type.setType(primitiveType);
	}

	/**
	 * @see JDTVisitorUtils#manageBindingRef(org.eclipse.jdt.core.dom.ArrayType,
	 *      JDTVisitor)
	 */
	protected void visitArrayType(final TypeAccess typAcc, final String qualifiedName,
			final ClassFileParser visitor) {
		Binding id = JavaModelDelegateBindingFactory.getInstance().getBindingForArrayType(
				qualifiedName, visitor, false);
		ArrayType arrayType = (ArrayType) getGlobalBindings().getTarget(id.toString());
		if (arrayType == null) {
			arrayType = getFactory().createArrayType();
			arrayType.setDimensions(Signature.getArrayCount(qualifiedName));
			arrayType.setName(id.toString());

			arrayType.setElementType(getRefOnType(Signature.getElementType(qualifiedName)));

			this.model.getOrphanTypes().add(arrayType);
			getGlobalBindings().addTarget(id, arrayType);
		}
		typAcc.setType(arrayType);
	}

	/**
	 * Complete the MoDisco modifier with the informations of the flags.
	 * 
	 * @param flags
	 *            the flags
	 * @param modiscoModifier
	 *            the MoDisco Modifier
	 * @see Flags
	 */
	private static void manageModifier(final Modifier modiscoModifier, final int flags,
			final IJavaElement element) {
		int kind = element.getElementType();
		// static is applicable on types, methods, fields, and initializers.
		if (!modiscoModifier.isStatic()) {
			if (kind == IJavaElement.TYPE || kind == IJavaElement.METHOD
					|| kind == IJavaElement.FIELD) {
				modiscoModifier.setStatic(Flags.isStatic(flags));
			}
		}
		// native is applicable to methods
		if (!modiscoModifier.isNative()) {
			if (kind == IJavaElement.METHOD) {
				modiscoModifier.setNative(Flags.isNative(flags));
			}
		}
		// strictfp is applicable to types and methods
		if (!modiscoModifier.isStrictfp()) {
			if (kind == IJavaElement.TYPE || kind == IJavaElement.METHOD) {
				modiscoModifier.setStrictfp(Flags.isStrictfp(flags));
			}
		}
		// synchronized is applicable only to methods
		if (!modiscoModifier.isSynchronized()) {
			if (kind == IJavaElement.METHOD) {
				modiscoModifier.setSynchronized(Flags.isSynchronized(flags));
			}
		}
		// transient is applicable only to fields
		if (!modiscoModifier.isTransient()) {
			if (kind == IJavaElement.FIELD) {
				modiscoModifier.setTransient(Flags.isTransient(flags));
			}
		}
		// volatile is applicable only to fields
		if (!modiscoModifier.isVolatile()) {
			if (kind == IJavaElement.FIELD) {
				modiscoModifier.setVolatile(Flags.isVolatile(flags));
			}
		}

		// visibility modifiers are applicable to types, methods, constructors,
		// and fields.
		if (kind == IJavaElement.TYPE || kind == IJavaElement.METHOD || kind == IJavaElement.FIELD) {
			if (Flags.isPrivate(flags)) {
				modiscoModifier.setVisibility(VisibilityKind.PRIVATE);
			} else if (Flags.isProtected(flags)) {
				modiscoModifier.setVisibility(VisibilityKind.PROTECTED);
			} else if (Flags.isPublic(flags)) {
				modiscoModifier.setVisibility(VisibilityKind.PUBLIC);
			}
		}

		// abstract is applicable to types and methods
		// final is applicable to types, methods and variables
		if (kind == IJavaElement.TYPE || kind == IJavaElement.METHOD) {
			if (Flags.isAbstract(flags)) {
				modiscoModifier.setInheritance(InheritanceKind.ABSTRACT);
			} else if (Flags.isFinal(flags)) {
				modiscoModifier.setInheritance(InheritanceKind.FINAL);
			}
		}
	}

	public void setFactory(final JavaFactory factory) {
		this.factory = factory;
	}

	public JavaFactory getFactory() {
		return this.factory;
	}

	public void setGlobalBindings(final BindingManager globalBindings) {
		this.globalBindings = globalBindings;
	}

	public BindingManager getGlobalBindings() {
		return this.globalBindings;
	}

}
