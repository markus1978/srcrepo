/*******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sebastien Minguet (Mia-Software) - initial API and implementation
 *    Frederic Madiot (Mia-Software) - initial API and implementation
 *    Fabien Giquel (Mia-Software) - initial API and implementation
 *    Gabriel Barbier (Mia-Software) - initial API and implementation
 *    Erwan Breton (Sodifrance) - initial API and implementation
 *    Romain Dervaux (Mia-Software) - initial API and implementation
 *    Nicolas Guyomar (Mia-Software)
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *    Nicolas Bros (Mia-Software) - Bug 351590 - [Java discoverer] ClassCastException while discovering Apache math commons
 *    Hervé Esteguet (Mia-Software) - Bug 435282 - [Java Discoverer] Wrong value for Javadoc.content field in model (bad line feeds)
 *******************************************************************************/

package org.eclipse.modisco.java.discoverer.internal.io.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmt.modisco.infra.common.core.logging.MoDiscoLogger;
import org.eclipse.gmt.modisco.java.*;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BijectiveMap;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.JDTDelegateBindingFactory;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement;
import org.eclipse.modisco.java.discoverer.internal.io.library.LibraryReader;

/**
 * The main class for populating the Java model from the JDT model.
 * 
 * @see Model
 * @see ASTVisitor
 */
public class JDTVisitor extends ASTVisitor {

	private static final String TRACEID_DEEPREMOVE2 = "org.eclipse.gmt.modisco.java.discoverer/debug/JDTVisitor/deepRemove2"; //$NON-NLS-1$
	private static final boolean TRACE_DEEPREMOVE2 = JavaActivator.getDefault().isDebugging()
			&& new Boolean(Platform.getDebugOption(JDTVisitor.TRACEID_DEEPREMOVE2));

	private static final char DOT_SEPARATOR = '.';
	private final boolean isINCREMENTALDISCOVERING;
	private final boolean isFULLLEVELANALYSIS;

	/**
	 * The EMF Factory.
	 */
	private final JavaFactory factory;

	/**
	 * The resulting model.
	 * 
	 * @see Model
	 */
	private final Model jdtModel;

	/**
	 * The current package of this <code>CompilationUnit</code>.
	 */
	private Package currentPackage = null;

	/**
	 * The {@link BindingManager} dedicated to local references.
	 */
	private BindingManager localBindings = null;

	/**
	 * The {@link BindingManager} dedicated to global references.
	 */
	private BindingManager globalBindings = null;

	/**
	 * {@link BijectiveMap} used to retrieve a MoDisco <code>ASTnode</code> from
	 * a JDT <code>ASTNode</code>.
	 */
	private final BijectiveMap<org.eclipse.jdt.core.dom.ASTNode, ASTNode> binding;

	/**
	 * {@link BijectiveMap} used to retrieve Java comments.
	 * 
	 * @see CommentsManager
	 */
	private final BijectiveMap<org.eclipse.jdt.core.dom.ASTNode, Comment> commentsBinding = new BijectiveMap<org.eclipse.jdt.core.dom.ASTNode, Comment>();

	/**
	 * The workspace-relative path of the current Java file.
	 */
	private String currentFilePath = null;

	/**
	 * the java source code.
	 */
	private String javaContent = null;

	/**
	 * Current RootType.
	 * 
	 * @see org.eclipse.jdt.core.dom.CompilationUnit#getTypeRoot()
	 */
	private AbstractTypeDeclaration rootTypeOrEnum = null;

	/**
	 * The <code>CompilationUnit</code> currently visited.
	 */
	private org.eclipse.jdt.core.dom.CompilationUnit cuNode = null;

	/**
	 * the name for the default package.
	 */
	public static final String DEFAULT_PKG_ID = "(default package)"; //$NON-NLS-1$

	private boolean isAlreadyVisited = false;

	private boolean logJDTBindingsIssues = false;

	/**
	 * affect generic informations to {@code element}.
	 * 
	 * @param element
	 *            the ASTNode to initialize
	 * @param node
	 *            unused for the moment
	 */
	private final void initializeNode(final ASTNode element,
			final org.eclipse.jdt.core.dom.ASTNode node) {
		CompilationUnit compilationUnit = (CompilationUnit) this.binding.get(this.cuNode);
		if (compilationUnit != null) {
			element.setOriginalCompilationUnit(compilationUnit);
		}
	}

/**
	 * Constructor.
	 * @param factory the EMF factory
	 * @param resultModel the {@code Model)
	 * @param globalBindings a global {@code BindingManager}
	 * @param filepath the workspace relative file path of the compilation unit, can be
	 * 	{@code null} if analysing source code and not a .java file
	 * @param javaContent the java source code
	 * @param isIncrementalDiscovering enable incremental behavior if true
	 * @param isFullAnalysisLevel full or limited analysis (class skeleton)
	 */
	public JDTVisitor(final JavaFactory factory, final Model resultModel,
			final BindingManager globalBindings, final String filepath, final String javaContent,
			final boolean isIncrementalDiscovering, final boolean isFullAnalysisLevel) {
		this(factory, resultModel, globalBindings, filepath, javaContent, isIncrementalDiscovering,
				isFullAnalysisLevel, false);
	}

/**
	 * Constructor.
	 * @param factory the EMF factory
	 * @param resultModel the {@code Model)
	 * @param globalBindings a global {@code BindingManager}
	 * @param filepath the workspace relative file path of the compilation unit, can be
	 * 	{@code null} if analysing source code and not a .java file
	 * @param javaContent the java source code
	 * @param isIncrementalDiscovering enable incremental behavior if true
	 * @param isFullAnalysisLevel full or limited analysis (class skeleton)
	 * @param logJDTBindingsIssues log warnings for unresolved jdt bindings
	 */
	public JDTVisitor(final JavaFactory factory, final Model resultModel,
			final BindingManager globalBindings, final String filepath, final String javaContent,
			final boolean isIncrementalDiscovering, final boolean isFullAnalysisLevel,
			final boolean logJDTBindingsIssues) {
		this.factory = factory;
		this.jdtModel = resultModel;
		setGlobalBindings(globalBindings);
		this.currentFilePath = filepath;
		this.javaContent = javaContent;
		this.isINCREMENTALDISCOVERING = isIncrementalDiscovering;
		this.isFULLLEVELANALYSIS = isFullAnalysisLevel;
		this.logJDTBindingsIssues = logJDTBindingsIssues;
		this.binding = new BijectiveMap<org.eclipse.jdt.core.dom.ASTNode, ASTNode>();
		((JDTDelegateBindingFactory) JDTDelegateBindingFactory.getInstance())
				.setLogJDTBindingsIssues(this.logJDTBindingsIssues);
	}

	/**
	 * Returns the EMF factory.
	 * 
	 * @return the EMF factory
	 */
	JavaFactory getFactory() {
		return this.factory;
	}

	/**
	 * Returns the {@code Model}.
	 * 
	 * @return the {@code Model}
	 */
	Model getJdtModel() {
		return this.jdtModel;
	}

	/**
	 * Returns the {@code BijectiveMap} dedicated to comments.
	 * 
	 * @return the {@code BijectiveMap} dedicated to comments
	 */
	BijectiveMap<org.eclipse.jdt.core.dom.ASTNode, Comment> getCommentsBinding() {
		return this.commentsBinding;
	}

	/**
	 * Returns the {@code BijectiveMap} dedicated to {@code ASTNode}s.
	 * 
	 * @return the {@code BijectiveMap} dedicated to {@code ASTNode}s
	 */
	BijectiveMap<org.eclipse.jdt.core.dom.ASTNode, ASTNode> getBijectiveMap() {
		return this.binding;
	}

	/**
	 * Returns the root type of this compilation unit.
	 * 
	 * @return the root type, or {@code null} if none
	 * @see org.eclipse.jdt.core.dom.CompilationUnit#getTypeRoot()
	 */
	AbstractTypeDeclaration getRootTypeOrEnum() {
		return this.rootTypeOrEnum;
	}

	/**
	 * Returns the JDT {@code CompilationUnit} object currently visited.
	 * 
	 * @return the JDT {@code CompilationUnit} object currently visited
	 */
	org.eclipse.jdt.core.dom.CompilationUnit getCuNode() {
		return this.cuNode;
	}

	/**
	 * Returns the content (text) of this compilation unit.
	 * 
	 * @return the content (text) of this compilation unit.
	 */
	String getJavaContent() {
		return this.javaContent;
	}

	private void setRootTypeOrEnum(final AbstractTypeDeclaration root) {
		this.rootTypeOrEnum = root;
	}

	// @Override
	// public void preVisit(final org.eclipse.jdt.core.dom.ASTNode node) {
	// System.out.println("Visiting " + node.getClass().getName() + " : " +
	// node.toString() + " ...");
	// }
	//
	// @Override
	// public void postVisit(final org.eclipse.jdt.core.dom.ASTNode node) {
	// System.out.println("Visited " + node.getClass().getName());
	// }

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.AnnotationTypeDeclaration node) {

		AnnotationTypeDeclaration element = null;
		if (this.isINCREMENTALDISCOVERING) {
			// To find previously created proxy
			String qname = JDTVisitorUtils.getQualifiedName(node);
			element = (AnnotationTypeDeclaration) getGlobalBindings().getTarget(qname);
			if (element != null) {
				unSetProxy(element);
			}
		}
		if (element == null) {
			element = this.factory.createAnnotationTypeDeclaration();
		}
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.AnnotationTypeDeclaration node) {
		AnnotationTypeDeclaration element = (AnnotationTypeDeclaration) this.binding.get(node);
		initializeNode(element, node);

		endVisitATD(node, element);
		endVisitBD(node, element);

		JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration node) {
		AnnotationTypeMemberDeclaration element = null;
		if (this.isINCREMENTALDISCOVERING) {
			// To find previously created proxy
			String qname = JDTVisitorUtils.getQualifiedName(node);
			element = (AnnotationTypeMemberDeclaration) getGlobalBindings().getTarget(qname);
			if (element != null) {
				unSetProxy(element);
			}
		}
		if (element == null) {
			element = this.factory.createAnnotationTypeMemberDeclaration();
		}
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration node) {
		AnnotationTypeMemberDeclaration element = (AnnotationTypeMemberDeclaration) this.binding
				.get(node);
		initializeNode(element, node);

		element.setName(node.getName().getIdentifier());

		if (this.binding.get(node.getType()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}

		if (this.binding.get(node.getDefault()) != null) {
			element.setDefault(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getDefault()), this));
		}

		endVisitBD(node, element);
		JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.AnonymousClassDeclaration node) {
		AnonymousClassDeclaration element = this.factory.createAnonymousClassDeclaration();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.AnonymousClassDeclaration node) {
		AnonymousClassDeclaration element = (AnonymousClassDeclaration) this.binding.get(node);
		initializeNode(element, node);

		for (Iterator<?> i = node.bodyDeclarations().iterator(); i.hasNext();) {
			BodyDeclaration itElement = (BodyDeclaration) this.binding.get(i.next());
			if (itElement != null) {
				element.getBodyDeclarations().add(itElement);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ArrayAccess node) {
		ArrayAccess element = this.factory.createArrayAccess();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ArrayAccess node) {
		ArrayAccess element = (ArrayAccess) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getArray()) != null) {
			element.setArray(JDTVisitorUtils.completeExpression(this.binding.get(node.getArray()),
					this));
		}

		if (this.binding.get(node.getIndex()) != null) {
			element.setIndex(JDTVisitorUtils.completeExpression(this.binding.get(node.getIndex()),
					this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ArrayCreation node) {
		ArrayCreation element = this.factory.createArrayCreation();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ArrayCreation node) {
		ArrayCreation element = (ArrayCreation) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getType()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}

		for (Iterator<?> i = node.dimensions().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getDimensions().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}

		if (this.binding.get(node.getInitializer()) != null) {
			element.setInitializer((ArrayInitializer) this.binding.get(node.getInitializer()));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ArrayInitializer node) {
		ArrayInitializer element = this.factory.createArrayInitializer();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ArrayInitializer node) {
		ArrayInitializer element = (ArrayInitializer) this.binding.get(node);
		initializeNode(element, node);

		for (Iterator<?> i = node.expressions().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getExpressions().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ArrayType node) {

		// for YYY[][], jdt manages an ArrayType which points to another
		// ArrayType
		// In MoDisco we want only one single ArrayType with the dimension 2
		if (!(node.getParent() instanceof org.eclipse.jdt.core.dom.ArrayType)) {

			// if an ArrayType like this one has already been visited,
			// don't visit this one and use the other
			Binding id = JDTDelegateBindingFactory.getInstance().getBindingForArrayType(node);
			if (getGlobalBindings().getTarget(id) != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ArrayType node) {

		if (!(node.getParent() instanceof org.eclipse.jdt.core.dom.ArrayType)) {

			ArrayType type = JDTVisitorUtils.manageBindingRef(node, this);

			// create a TypeAccess for this ArrayType
			TypeAccess typAcc = this.factory.createTypeAccess();
			typAcc.setType(type);

			this.binding.put(node, typAcc);
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.AssertStatement node) {
		AssertStatement element = this.factory.createAssertStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.AssertStatement node) {
		AssertStatement element = (AssertStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getMessage()) != null) {
			element.setMessage(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getMessage()), this));
		}
		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.Assignment node) {
		Assignment element = this.factory.createAssignment();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.Assignment node) {
		Assignment element = (Assignment) this.binding.get(node);
		initializeNode(element, node);

		element.setOperator(AssignmentKind.get(node.getOperator().toString()));

		if (this.binding.get(node.getLeftHandSide()) != null) {
			element.setLeftHandSide(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getLeftHandSide()), this));
		}
		if (this.binding.get(node.getRightHandSide()) != null) {
			element.setRightHandSide(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getRightHandSide()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.Block node) {
		if (!this.isFULLLEVELANALYSIS) {
			return false;
		}

		Block element = this.factory.createBlock();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.Block node) {
		if (!this.isFULLLEVELANALYSIS) {
			return;
		}

		Block element = (Block) this.binding.get(node);
		initializeNode(element, node);

		for (Iterator<?> i = node.statements().iterator(); i.hasNext();) {
			Statement itElement = (Statement) this.binding.get(i.next());
			if (itElement != null) {
				element.getStatements().add(itElement);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.BlockComment node) {
		// Never called with JLS3
		// (https://bugs.eclipse.org/bugs/show_bug.cgi?format=multiple&id=84528)
		// LineComment and BlockComment are managed on CompilationUnit
		// @see org.eclipse.jdt.core.dom.CompilationUnit#getCommentList()

		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.BlockComment node) {
		// Nothing
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.BooleanLiteral node) {
		BooleanLiteral element = this.factory.createBooleanLiteral();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.BooleanLiteral node) {
		BooleanLiteral element = (BooleanLiteral) this.binding.get(node);
		initializeNode(element, node);

		element.setValue(node.booleanValue());
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.BreakStatement node) {
		BreakStatement element = this.factory.createBreakStatement();
		this.binding.put(node, element);

		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.BreakStatement node) {
		BreakStatement element = (BreakStatement) this.binding.get(node);
		initializeNode(element, node);

		if (node.getLabel() != null) {
			PendingElement label = new PendingElement(this.factory);
			label.setClientNode(element);
			label.setLinkName("label"); //$NON-NLS-1$

			NamedElement target = JDTVisitorUtils.manageBindingRef(label, node.getLabel(), this);
			if (target != null) {
				element.setLabel((LabeledStatement) target);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.CastExpression node) {
		CastExpression element = this.factory.createCastExpression();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.CastExpression node) {
		CastExpression element = (CastExpression) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getType()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}
		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.CatchClause node) {
		CatchClause element = this.factory.createCatchClause();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.CatchClause node) {
		CatchClause element = (CatchClause) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getException()) != null) {
			SingleVariableDeclaration exc = (SingleVariableDeclaration) this.binding.get(node
					.getException());
			exc.setCatchClause(element);
			element.setException(exc);
		}

		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Block) this.binding.get(node.getBody()));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.CharacterLiteral node) {
		CharacterLiteral element = this.factory.createCharacterLiteral();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.CharacterLiteral node) {
		CharacterLiteral element = (CharacterLiteral) this.binding.get(node);
		initializeNode(element, node);

		element.setEscapedValue(node.getEscapedValue());
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ClassInstanceCreation node) {
		ClassInstanceCreation element = this.factory.createClassInstanceCreation();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ClassInstanceCreation node) {
		ClassInstanceCreation element = (ClassInstanceCreation) this.binding.get(node);
		initializeNode(element, node);

		ConstructorDeclaration anonymousConstructor = null;

		if (this.binding.get(node.getAnonymousClassDeclaration()) != null) {
			AnonymousClassDeclaration anoDecl = (AnonymousClassDeclaration) this.binding.get(node
					.getAnonymousClassDeclaration());
			element.setAnonymousClassDeclaration(anoDecl);
			anonymousConstructor = this.factory.createConstructorDeclaration();
			anonymousConstructor.setProxy(true);
			anonymousConstructor.setName(""); //$NON-NLS-1$
			anoDecl.getBodyDeclarations().add(anonymousConstructor);
		}

		if (this.binding.get(node.getType()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}

		for (Iterator<?> i = node.arguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getArguments().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}

		for (Iterator<?> i = node.typeArguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getTypeArguments().add(JDTVisitorUtils.completeTypeAccess(itElement, this));
			}
		}

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}

		// if it is an anonymous class declaration we directly use the anonymous
		// constructor
		if (anonymousConstructor != null) {
			element.setMethod(anonymousConstructor);
		} else {
			PendingElement constructorRef = new PendingElement(this.factory);
			constructorRef.setClientNode(element);
			constructorRef.setLinkName("method"); //$NON-NLS-1$

			JDTVisitorUtils.manageBindingRef(constructorRef, node, this);
		}

	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.CompilationUnit node) {
		this.cuNode = node;

		CompilationUnit element = this.factory.createCompilationUnit();
		this.binding.put(node, element);

		if (!this.isINCREMENTALDISCOVERING) {
			// we check if any of the top-level types of this compilation unit
			// are already defined in the model
			for (Iterator<?> iterator = node.types().iterator(); iterator.hasNext();) {
				if (isTypeAlreadyVisited((org.eclipse.jdt.core.dom.AbstractTypeDeclaration) iterator
						.next())) {
					this.isAlreadyVisited = true;
					return false;
				}
			}
		}
		return true;
	}

	private boolean isTypeAlreadyVisited(final org.eclipse.jdt.core.dom.AbstractTypeDeclaration type) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForName(type.getName());
		return getGlobalBindings().containsTarget(id);
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.CompilationUnit node) {
		CompilationUnit element = (CompilationUnit) this.binding.get(node);

		// if any type of this cu already exists in the model, we don't
		// visit this cu
		if (this.isAlreadyVisited) {
			return;
		}

		this.jdtModel.getCompilationUnits().add(element);
		ITypeRoot rootType = node.getTypeRoot();

		if (rootType != null) {
			if (rootType instanceof IClassFile) {
				// type comes from a .class file
				ClassFile classFile = this.factory.createClassFile();
				classFile.setName(rootType.getElementName());
				classFile.setAttachedSource(element);
				classFile.setOriginalFilePath(this.currentFilePath);

				Archive ar = LibraryReader.getArchive((IClassFile) rootType, this.factory,
						this.jdtModel);
				if (ar == null) {
					this.jdtModel.getClassFiles().add(classFile);
				} else {
					ar.getClassFiles().add(classFile);
				}
			} else if (rootType instanceof ICompilationUnit) {
				// type comes a .java file
				IPath absolutePath = null;
				try {
					absolutePath = rootType.getCorrespondingResource().getLocation();
				} catch (JavaModelException e) {
					absolutePath = ResourcesPlugin.getWorkspace().getRoot().getRawLocation()
							.append(rootType.getPath());
				}
				element.setOriginalFilePath(absolutePath.toOSString());
			} else {
				element.setOriginalFilePath(""); //$NON-NLS-1$
			}
			element.setName(rootType.getElementName());
		} else {
			element.setProxy(true);
		}

		Package packageDeclaration = (Package) this.binding.get(node.getPackage());
		element.setPackage(packageDeclaration);

		for (Object importNode : node.imports()) {
			ImportDeclaration importDeclaration = (ImportDeclaration) this.binding.get(importNode);
			element.getImports().add(importDeclaration);
		}

		for (Object typeNode : node.types()) {
			AbstractTypeDeclaration typeDeclaration = (AbstractTypeDeclaration) this.binding
					.get(typeNode);
			element.getTypes().add(typeDeclaration);
		}

		try {
			// accessing BlockComment and LineComment
			// (https://bugs.eclipse.org/bugs/show_bug.cgi?format=multiple&id=84528)
			List<?> comments = node.getCommentList();
			for (Object name : comments) {
				org.eclipse.jdt.core.dom.Comment aComment = (org.eclipse.jdt.core.dom.Comment) name;
	
				Comment commentElement = null;
				if (aComment.isLineComment()) {
					commentElement = this.factory.createLineComment();
					initializeNode(commentElement, aComment);
					String content = CommentsManager.extractCommentContent(aComment, this.javaContent);
					commentElement.setContent(content);
					this.binding.put(aComment, commentElement);
				} else if (aComment.isBlockComment()) {
					commentElement = this.factory.createBlockComment();
					initializeNode(commentElement, aComment);
					String content = CommentsManager.extractCommentContent(aComment, this.javaContent);
					commentElement.setContent(content);
					this.binding.put(aComment, commentElement);
				} else if (aComment.isDocComment()) {
					// one javadoc node (and its tag elements) should have been
					// already visited
	
					commentElement = (Javadoc) this.binding.get(aComment);
					if (commentElement == null) { // happen if more than one javadoc
													// for a node
						commentElement = this.factory.createJavadoc();
						initializeNode(commentElement, aComment);
					}
					String content = CommentsManager.extractCommentContent(aComment, this.javaContent);
					commentElement.setContent(content);
				}
	
				getCommentsBinding().put(aComment, commentElement);
				// initialisation of element CompilationUnit
				element.getCommentList().add(commentElement);
			}
		} catch (StringIndexOutOfBoundsException e) {
			// ignore
			// markus, hub, sam, srcrepo
			System.out.println();
		}

		CommentsManager.resolveCommentPositions(this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ConditionalExpression node) {
		ConditionalExpression element = this.factory.createConditionalExpression();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ConditionalExpression node) {
		ConditionalExpression element = (ConditionalExpression) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getElseExpression()) != null) {
			element.setElseExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getElseExpression()), this));
		}
		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
		if (this.binding.get(node.getThenExpression()) != null) {
			element.setThenExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getThenExpression()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ConstructorInvocation node) {
		ConstructorInvocation element = this.factory.createConstructorInvocation();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ConstructorInvocation node) {
		ConstructorInvocation element = (ConstructorInvocation) this.binding.get(node);
		initializeNode(element, node);

		for (Iterator<?> i = node.arguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getArguments().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}

		for (Iterator<?> i = node.typeArguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getTypeArguments().add(JDTVisitorUtils.completeTypeAccess(itElement, this));
			}
		}

		PendingElement constructorRef = new PendingElement(this.factory);
		constructorRef.setClientNode(element);
		constructorRef.setLinkName("method"); //$NON-NLS-1$

		JDTVisitorUtils.manageBindingRef(constructorRef, node, this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ContinueStatement node) {
		ContinueStatement element = this.factory.createContinueStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ContinueStatement node) {
		ContinueStatement element = (ContinueStatement) this.binding.get(node);
		initializeNode(element, node);

		if (node.getLabel() != null) {
			PendingElement label = new PendingElement(this.factory);
			label.setClientNode(element);
			label.setLinkName("label"); //$NON-NLS-1$

			NamedElement target = JDTVisitorUtils.manageBindingRef(label, node.getLabel(), this);
			if (target != null) {
				element.setLabel((LabeledStatement) target);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.DoStatement node) {
		DoStatement element = this.factory.createDoStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.DoStatement node) {
		DoStatement element = (DoStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Statement) this.binding.get(node.getBody()));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.EmptyStatement node) {
		EmptyStatement element = this.factory.createEmptyStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.EmptyStatement node) {
		EmptyStatement element = (EmptyStatement) this.binding.get(node);
		initializeNode(element, node);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.EnhancedForStatement node) {
		EnhancedForStatement element = this.factory.createEnhancedForStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.EnhancedForStatement node) {
		EnhancedForStatement element = (EnhancedForStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Statement) this.binding.get(node.getBody()));
		}
		if (this.binding.get(node.getParameter()) != null) {
			SingleVariableDeclaration param = (SingleVariableDeclaration) this.binding.get(node
					.getParameter());
			param.setEnhancedForStatement(element);
			element.setParameter(param);
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.EnumConstantDeclaration node) {

		EnumConstantDeclaration element = null;
		if (this.isINCREMENTALDISCOVERING) {
			// To find previously created proxy
			String qname = JDTVisitorUtils.getQualifiedName(node);
			element = (EnumConstantDeclaration) getGlobalBindings().getTarget(qname);
			if (element != null) {
				unSetProxy(element);
			}
		}
		if (element == null) {
			element = this.factory.createEnumConstantDeclaration();
		}
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.EnumConstantDeclaration node) {
		EnumConstantDeclaration element = (EnumConstantDeclaration) this.binding.get(node);
		initializeNode(element, node);

		element.setName(node.getName().getIdentifier());

		if (this.binding.get(node.getAnonymousClassDeclaration()) != null) {
			element.setAnonymousClassDeclaration((AnonymousClassDeclaration) this.binding.get(node
					.getAnonymousClassDeclaration()));
		}

		for (Iterator<?> i = node.arguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getArguments().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}

		endVisitBD(node, element);
		JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.EnumDeclaration node) {

		EnumDeclaration element = null;
		if (this.isINCREMENTALDISCOVERING) {
			// To find previously created proxy
			String qname = JDTVisitorUtils.getQualifiedName(node);
			element = (EnumDeclaration) getGlobalBindings().getTarget(qname);
			if (element != null) {
				unSetProxy(element);
			}
		}
		if (element == null) {
			element = this.factory.createEnumDeclaration();
		}
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.EnumDeclaration node) {
		EnumDeclaration element = (EnumDeclaration) this.binding.get(node);
		initializeNode(element, node);

		for (Iterator<?> i = node.enumConstants().iterator(); i.hasNext();) {
			element.getEnumConstants().add((EnumConstantDeclaration) this.binding.get(i.next()));
		}

		if (this.isINCREMENTALDISCOVERING) {
			// To avoid to re-create an existing access type
			List<TypeAccess> superInterfaceList = new ArrayList<TypeAccess>(
					element.getSuperInterfaces());
			Iterator<TypeAccess> superInterface = superInterfaceList.iterator();
			while (superInterface.hasNext()) {
				TypeAccess typeAccess = superInterface.next();
				deepRemove(typeAccess);
				element.getSuperInterfaces().remove(typeAccess);
			}
		}
		for (Iterator<?> i = node.superInterfaceTypes().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getSuperInterfaces().add(
						JDTVisitorUtils.completeTypeAccess(itElement, this));
			}
		}

		endVisitATD(node, element);
		endVisitBD(node, element);

		JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ExpressionStatement node) {
		ExpressionStatement element = this.factory.createExpressionStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ExpressionStatement node) {
		ExpressionStatement element = (ExpressionStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.FieldAccess node) {
		if (isArrayLengthAccess(node.getName())) {
			ArrayLengthAccess element = this.factory.createArrayLengthAccess();
			this.binding.put(node, element);
			return true;
		}
		FieldAccess element = this.factory.createFieldAccess();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.FieldAccess node) {
		ASTNode modiscoNode = this.binding.get(node);
		initializeNode(modiscoNode, node);

		if (isArrayLengthAccess(node.getName())) {
			((ArrayLengthAccess) modiscoNode).setArray(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		} else {
			FieldAccess element = (FieldAccess) this.binding.get(node);
			initializeNode(element, node);

			if (this.binding.get(node.getName()) != null) {
				element.setField(JDTVisitorUtils.completeVariableAccess(
						this.binding.get(node.getName()), this));
			}

			if (this.binding.get(node.getExpression()) != null) {
				element.setExpression(JDTVisitorUtils.completeExpression(
						this.binding.get(node.getExpression()), this));
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.FieldDeclaration node) {
		FieldDeclaration element = this.factory.createFieldDeclaration();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.FieldDeclaration node) {
		FieldDeclaration element = (FieldDeclaration) this.binding.get(node);
		initializeNode(element, node);
		element.setProxy(false);
		if (this.binding.get(node.getType()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}

		for (Iterator<?> i = node.fragments().iterator(); i.hasNext();) {
			VariableDeclarationFragment itElement = (VariableDeclarationFragment) this.binding
					.get(i.next());
			if (itElement != null) {
				element.getFragments().add(itElement);
			}
		}
		endVisitBD(node, element);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ForStatement node) {
		ForStatement element = this.factory.createForStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ForStatement node) {
		ForStatement element = (ForStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
		for (Iterator<?> i = node.updaters().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getUpdaters().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}

		for (Iterator<?> i = node.initializers().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getInitializers().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}
		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Statement) this.binding.get(node.getBody()));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.IfStatement node) {
		IfStatement element = this.factory.createIfStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.IfStatement node) {
		IfStatement element = (IfStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
		if (this.binding.get(node.getThenStatement()) != null) {
			element.setThenStatement((Statement) this.binding.get(node.getThenStatement()));
		}

		if (this.binding.get(node.getElseStatement()) != null) {
			element.setElseStatement((Statement) this.binding.get(node.getElseStatement()));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ImportDeclaration node) {
		ImportDeclaration element = this.factory.createImportDeclaration();
		this.binding.put(node, element);

		// we do not visit the name of imports
		// because an ImportDeclaration has
		// a direct reference on the imported element.
		return false;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ImportDeclaration node) {
		ImportDeclaration element = (ImportDeclaration) this.binding.get(node);
		initializeNode(element, node);

		element.setStatic(node.isStatic());

		// manage the simple name here
		PendingElement pending = new PendingElement(this.factory);
		pending.setClientNode(element);
		pending.setLinkName("importedElement"); //$NON-NLS-1$

		NamedElement target = JDTVisitorUtils.manageBindingRef(pending, node.getName(), this);
		if (target != null) {
			element.setImportedElement(target);
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.InfixExpression node) {
		InfixExpression element = this.factory.createInfixExpression();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.InfixExpression node) {
		InfixExpression element = (InfixExpression) this.binding.get(node);
		initializeNode(element, node);

		element.setOperator(InfixExpressionKind.get(node.getOperator().toString()));

		if (this.binding.get(node.getRightOperand()) != null) {
			element.setRightOperand(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getRightOperand()), this));
		}

		if (this.binding.get(node.getLeftOperand()) != null) {
			element.setLeftOperand(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getLeftOperand()), this));
		}
		for (Iterator<?> i = node.extendedOperands().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getExtendedOperands().add(
						JDTVisitorUtils.completeExpression(itElement, this));
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.Initializer node) {
		if (!this.isFULLLEVELANALYSIS) {
			return false;
		}

		Initializer element = this.factory.createInitializer();
		this.binding.put(node, element);

		// localBindings may have been initialized if method is declared in
		// anonymous class declared in a method body
		if (getLocalBindings() == null) {
			setLocalBindings(new BindingManager(this.factory));
		}

		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.Initializer node) {
		if (!this.isFULLLEVELANALYSIS) {
			return;
		}

		Initializer element = (Initializer) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Block) this.binding.get(node.getBody()));
		}

		endVisitBD(node, element);

		// long debut = System.currentTimeMillis();

		getLocalBindings().resolveBindings();

		// long fin = System.currentTimeMillis();
		// TimeResults.resolveBindings += fin - debut;

		// localBindings should be kept if initializer is declared in anonymous
		// class declared in a method body
		if (!(node.getParent() instanceof org.eclipse.jdt.core.dom.AnonymousClassDeclaration || node
				.getParent().getParent() instanceof org.eclipse.jdt.core.dom.TypeDeclarationStatement)) {
			setLocalBindings(null);
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.InstanceofExpression node) {
		InstanceofExpression element = this.factory.createInstanceofExpression();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.InstanceofExpression node) {
		InstanceofExpression element = (InstanceofExpression) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getRightOperand()) != null) {
			element.setRightOperand(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getRightOperand()), this));
		}

		if (this.binding.get(node.getLeftOperand()) != null) {
			element.setLeftOperand(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getLeftOperand()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.Javadoc node) {
		Javadoc element = this.factory.createJavadoc();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.Javadoc node) {
		Javadoc element = (Javadoc) this.binding.get(node);
		initializeNode(element, node);

		for (Iterator<?> i = node.tags().iterator(); i.hasNext();) {
			TagElement itElement = (TagElement) this.binding.get(i.next());
			if (itElement != null) {
				element.getTags().add(itElement);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.LabeledStatement node) {
		LabeledStatement element = this.factory.createLabeledStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.LabeledStatement node) {
		LabeledStatement element = (LabeledStatement) this.binding.get(node);
		initializeNode(element, node);

		element.setName(node.getLabel().getIdentifier());

		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Statement) this.binding.get(node.getBody()));
		}

		JDTVisitorUtils.manageBindingDeclaration(element, node.getLabel(), this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.LineComment node) {
		// Never called with JLS3
		// (https://bugs.eclipse.org/bugs/show_bug.cgi?format=multiple&id=84528)
		// LineComment and BlockComment are managed on CompilationUnit
		// @see org.eclipse.jdt.core.dom.CompilationUnit#getCommentList()
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.LineComment node) {
		// Nothing
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.MarkerAnnotation node) {
		Annotation element = this.factory.createAnnotation();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.MarkerAnnotation node) {
		Annotation element = (Annotation) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getTypeName()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getTypeName()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.MemberValuePair node) {
		AnnotationMemberValuePair element = this.factory.createAnnotationMemberValuePair();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.MemberValuePair node) {
		AnnotationMemberValuePair element = (AnnotationMemberValuePair) this.binding.get(node);
		initializeNode(element, node);
		element.setName(node.getName().getIdentifier());

		if (this.binding.get(node.getValue()) != null) {
			element.setValue(JDTVisitorUtils.completeExpression(this.binding.get(node.getValue()),
					this));
		}

		if (this.binding.get(node.getName()) != null) {
			ASTNode modiscoNode = this.binding.get(node.getName());
			if (JDTVisitorUtils.completeBinding(element, modiscoNode, "member")) { //$NON-NLS-1$
				element.setMember((AnnotationTypeMemberDeclaration) modiscoNode);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.MemberRef node) {
		MemberRef element = this.factory.createMemberRef();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.MemberRef node) {
		MemberRef element = (MemberRef) this.binding.get(node);
		initializeNode(element, node);

		ASTNode modiscoNode = this.binding.get(node.getName());
		if (JDTVisitorUtils.completeBinding(element, modiscoNode, "member")) { //$NON-NLS-1$
			element.setMember((NamedElement) modiscoNode);
		}

		if (this.binding.get(node.getQualifier()) != null) {
			element.setQualifier(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getQualifier()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.MethodDeclaration node) {
		AbstractMethodDeclaration element = null;
		if (this.isINCREMENTALDISCOVERING
				&& !(node.getParent() instanceof org.eclipse.jdt.core.dom.AnonymousClassDeclaration)) {
			String qualifiedName = JDTVisitorUtils.getQualifiedName(node);
			element = (AbstractMethodDeclaration) getGlobalBindings().getTarget(qualifiedName);
			if (element != null) {
				unSetProxy(element);
			}
		}
		if (element == null) {
			if (node.isConstructor()) {
				element = this.factory.createConstructorDeclaration();
			} else {
				element = this.factory.createMethodDeclaration();
			}
		}
		this.binding.put(node, element);
		// localBindings may have been initialized if method is declared in
		// anonymous class declared in a method body
		if (getLocalBindings() == null) {
			setLocalBindings(new BindingManager(this.factory));
		}

		return true;
	}

	private static void unSetProxy(final NamedElement element) {
		element.setProxy(false);
		EObject container = element.eContainer();
		while (container != null) {
			if (container instanceof NamedElement) {
				NamedElement namedElement = (NamedElement) container;
				namedElement.setProxy(false);
				container = container.eContainer();
			} else {
				container = null;
			}
		}
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.MethodDeclaration node) {
		AbstractMethodDeclaration element = (AbstractMethodDeclaration) this.binding.get(node);
		initializeNode(element, node);

		if (!node.isConstructor()) {
			((MethodDeclaration) element).setExtraArrayDimensions(node.getExtraDimensions());
		}

		for (Iterator<?> i = node.parameters().iterator(); i.hasNext();) {
			SingleVariableDeclaration itElement = (SingleVariableDeclaration) this.binding.get(i
					.next());
			if (itElement != null) {
				itElement.setProxy(false);
				itElement.setMethodDeclaration(element);
				element.getParameters().add(itElement);
			}
		}

		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Block) this.binding.get(node.getBody()));
		}
		if (this.isINCREMENTALDISCOVERING) {
			// To avoid to re-create an existing access type
			List<TypeAccess> thrownExceptionsList = new ArrayList<TypeAccess>(
					element.getThrownExceptions());
			Iterator<TypeAccess> thrownExceptions = thrownExceptionsList.iterator();
			while (thrownExceptions.hasNext()) {
				TypeAccess typeAccess = thrownExceptions.next();
				element.getThrownExceptions().remove(typeAccess);
				deepRemove(typeAccess);
			}
		}
		for (Iterator<?> i = node.thrownExceptions().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getThrownExceptions().add(
						JDTVisitorUtils.completeTypeAccess(itElement, this));
			}
		}

		element.setName(node.getName().getIdentifier());

		if (this.binding.get(node.getReturnType2()) != null) {
			MethodDeclaration methodDeclaration = (MethodDeclaration) element;
			if (this.isINCREMENTALDISCOVERING && methodDeclaration.getReturnType() != null) {
				// To avoid to re-create an existing access type
				deepRemove(methodDeclaration.getReturnType());
				methodDeclaration.setReturnType(null);
			}
			methodDeclaration.setReturnType(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getReturnType2()), this));
		}

		for (Iterator<?> i = node.typeParameters().iterator(); i.hasNext();) {
			TypeParameter itElement = (TypeParameter) this.binding.get(i.next());
			if (itElement != null) {
				element.getTypeParameters().add(itElement);
			}
		}
		endVisitBD(node, element);

		// long debut = System.currentTimeMillis();

		getLocalBindings().resolveBindings();

		// long fin = System.currentTimeMillis();
		// TimeResults.resolveBindings += fin - debut;

		// localBindings should be kept if method is declared in anonymous class
		// declared in a method body
		if (!(node.getParent() instanceof org.eclipse.jdt.core.dom.AnonymousClassDeclaration || node
				.getParent().getParent() instanceof org.eclipse.jdt.core.dom.TypeDeclarationStatement)) {
			setLocalBindings(null);
		}

		JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
	}

	private void deepRemove(final EObject eObject) {
		deepRemove2(eObject);
	}

	private void deepRemove2(final EObject eObject) {
		if (eObject != null) {
			if (JDTVisitor.TRACE_DEEPREMOVE2) {
				System.out.println(JDTVisitor.TRACEID_DEEPREMOVE2
						+ ": " + eObject.getClass().getName()); //$NON-NLS-1$
			}
			if (eObject instanceof TypeAccess) {
				TypeAccess typeAccess = (TypeAccess) eObject;
				typeAccess.setType(null);
				deepRemove2(typeAccess.getQualifier());
			} else if (eObject instanceof FieldDeclaration) {
				FieldDeclaration fieldDeclaration = (FieldDeclaration) eObject;
				deepRemove2(fieldDeclaration.getType());
				fieldDeclaration.setType(null);
			} else {
				try {
					EcoreUtil.delete(eObject, true);
				} catch (Exception e) {
					IStatus status = new Status(IStatus.ERROR, JavaActivator.PLUGIN_ID,
							"Error while deleting eObject", e); //$NON-NLS-1$
					JavaActivator.getDefault().getLog().log(status);
				}
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.MethodInvocation node) {
		MethodInvocation element = this.factory.createMethodInvocation();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.MethodInvocation node) {
		MethodInvocation element = (MethodInvocation) this.binding.get(node);
		initializeNode(element, node);

		ASTNode modiscoNode = this.binding.get(node.getName());
		if (JDTVisitorUtils.completeBinding(element, modiscoNode, "method")) { //$NON-NLS-1$
			// see Bug 351590 - [Java discoverer] ClassCastException while discovering Apache math commons
			if(modiscoNode instanceof AbstractMethodDeclaration) {
				element.setMethod((AbstractMethodDeclaration) modiscoNode);
			}
		}

		for (Iterator<?> i = node.arguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getArguments().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}

		for (Iterator<?> i = node.typeArguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getTypeArguments().add(JDTVisitorUtils.completeTypeAccess(itElement, this));
			}
		}

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.MethodRef node) {
		MethodRef element = this.factory.createMethodRef();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.MethodRef node) {
		MethodRef element = (MethodRef) this.binding.get(node);
		initializeNode(element, node);

		ASTNode modiscoNode = this.binding.get(node.getName());
		if (JDTVisitorUtils.completeBinding(element, modiscoNode, "method")) { //$NON-NLS-1$
			element.setMethod((AbstractMethodDeclaration) modiscoNode);
		}

		if (this.binding.get(node.getQualifier()) != null) {
			element.setQualifier(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getQualifier()), this));
		}

		for (Iterator<?> i = node.parameters().iterator(); i.hasNext();) {
			MethodRefParameter itElement = (MethodRefParameter) this.binding.get(i.next());
			if (itElement != null) {
				element.getParameters().add(itElement);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.MethodRefParameter node) {
		MethodRefParameter element = this.factory.createMethodRefParameter();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.MethodRefParameter node) {
		MethodRefParameter element = (MethodRefParameter) this.binding.get(node);
		initializeNode(element, node);

		if (node.getName() != null) {
			element.setName(node.getName().getIdentifier());
		}

		element.setVarargs(node.isVarargs());

		if (this.binding.get(node.getType()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.NormalAnnotation node) {
		Annotation element = this.factory.createAnnotation();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.NormalAnnotation node) {
		Annotation element = (Annotation) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getTypeName()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getTypeName()), this));
		}

		for (Iterator<?> i = node.values().iterator(); i.hasNext();) {
			AnnotationMemberValuePair itElement = (AnnotationMemberValuePair) this.binding.get(i
					.next());
			if (itElement != null) {
				element.getValues().add(itElement);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.NullLiteral node) {
		NullLiteral element = this.factory.createNullLiteral();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.NullLiteral node) {
		NullLiteral element = (NullLiteral) this.binding.get(node);
		initializeNode(element, node);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.NumberLiteral node) {
		NumberLiteral element = this.factory.createNumberLiteral();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.NumberLiteral node) {
		NumberLiteral element = (NumberLiteral) this.binding.get(node);
		initializeNode(element, node);
		element.setTokenValue(node.getToken());
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.PackageDeclaration node) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForName(node.getName());
		Package element = null;
		if (!getGlobalBindings().containsTarget(id)) {
			element = createPackageHierarchy(node);
		} else {
			element = (Package) getGlobalBindings().getTarget(id);
		}
		this.currentPackage = element;
		this.binding.put(node, element);
		return false;
	}

	// create iterately a hierarchy of packages
	private Package createPackageHierarchy(final org.eclipse.jdt.core.dom.PackageDeclaration node) {
		Package result = this.factory.createPackage();
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForName(node.getName());
		String currentPackageName = id.getName();
		Package localCurrentPackage = result;
		int lastDotIndex = currentPackageName.lastIndexOf(JDTVisitor.DOT_SEPARATOR);
		if (lastDotIndex == -1) {
			this.jdtModel.getOwnedElements().add(result);
			localCurrentPackage.setName(currentPackageName);
		} else {
			// iterate on parents packages to create them if needed
			localCurrentPackage.setName(currentPackageName.substring(lastDotIndex + 1));
			while (lastDotIndex > 0) {
				// add qualified name for curent Package
				currentPackageName = currentPackageName.substring(0, lastDotIndex);
				Package aParentPackage = (Package) getGlobalBindings()
						.getTarget(currentPackageName);
				if (aParentPackage == null) {
					aParentPackage = this.factory.createPackage();

					getGlobalBindings().addTarget(currentPackageName, aParentPackage);
					lastDotIndex = currentPackageName.lastIndexOf(JDTVisitor.DOT_SEPARATOR);
					if (lastDotIndex < 0) { // top level package
						this.jdtModel.getOwnedElements().add(aParentPackage);
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

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.PackageDeclaration node) {
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForName(node.getName());
		if (!getGlobalBindings().containsTarget(id)) {
			Package element = (Package) this.binding.get(node);

			JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ParameterizedType node) {
		// if an ParameterizedType like this one has already been visited,
		// don't visit this one and use the other
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForParameterizedType(node);
		if (getGlobalBindings().getTarget(id) != null) {
			return false;
		}
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ParameterizedType node) {
		/*
		 * Link to real type used would be done in JDTDelegateBindingFactory
		 */
		ParameterizedType type = JDTVisitorUtils.manageBindingRef(node, this);

		TypeAccess typAcc = this.factory.createTypeAccess();
		typAcc.setType(type);

		this.binding.put(node, typAcc);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ParenthesizedExpression node) {
		ParenthesizedExpression element = this.factory.createParenthesizedExpression();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ParenthesizedExpression node) {
		ParenthesizedExpression element = (ParenthesizedExpression) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.PostfixExpression node) {
		PostfixExpression element = this.factory.createPostfixExpression();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.PostfixExpression node) {
		PostfixExpression element = (PostfixExpression) this.binding.get(node);
		initializeNode(element, node);

		element.setOperator(PostfixExpressionKind.get(node.getOperator().toString()));

		if (this.binding.get(node.getOperand()) != null) {
			element.setOperand(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getOperand()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.PrefixExpression node) {
		PrefixExpression element = this.factory.createPrefixExpression();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.PrefixExpression node) {
		PrefixExpression element = (PrefixExpression) this.binding.get(node);
		initializeNode(element, node);

		element.setOperator(PrefixExpressionKind.get(node.getOperator().toString()));

		if (this.binding.get(node.getOperand()) != null) {
			element.setOperand(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getOperand()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.PrimitiveType node) {
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.PrimitiveType node) {
		PrimitiveType type = JDTVisitorUtils.manageBindingRef(node, this);

		TypeAccess typAcc = this.factory.createTypeAccess();
		typAcc.setType(type);

		this.binding.put(node, typAcc);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.QualifiedName node) {
		if (isArrayLengthAccess(node)) {
			ArrayLengthAccess element = this.factory.createArrayLengthAccess();
			this.binding.put(node, element);
		}
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.QualifiedName node) {
		if (isArrayLengthAccess(node)) {
			ASTNode element = this.binding.get(node);
			initializeNode(element, node);
			((ArrayLengthAccess) element).setArray(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getQualifier()), this));
		} else {
			ASTNode qualifierAccess = JDTVisitorUtils.completeExpressionOrPackageAccess(
					this.binding.get(node.getQualifier()), this);
			// NameAccess will be of kind TypeAccess or SingleVariableAccess or
			// PackageAccess or UnresolvedItemAccess
			// In the special case of QualifiedName, we anticipate creating the
			// access without knowing the real kind
			// (using a UnresolvedItemAccess) for attaching qualifier to it.
			// When visiting the container, the UnresolvedItemAccess will be
			// substituted with better access kind.
			ASTNode nameAccess = JDTVisitorUtils.completeExpressionOrPackageAccess(
					this.binding.get(node.getName()), this);

			EStructuralFeature feature = nameAccess.eClass().getEStructuralFeature("qualifier"); //$NON-NLS-1$
			nameAccess.eSet(feature, qualifierAccess);

			this.binding.put(node, nameAccess);
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ReturnStatement node) {
		ReturnStatement element = this.factory.createReturnStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ReturnStatement node) {
		ReturnStatement element = (ReturnStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
	}

	/**
	 * @see #isArrayLengthAccess(org.eclipse.jdt.core.dom.SimpleName)
	 */
	private static boolean isArrayLengthAccess(final org.eclipse.jdt.core.dom.QualifiedName node) {
		return isArrayLengthAccess(node.getName());
	}

	/**
	 * Indicate if a {@code SimpleName} is an array length access.
	 * 
	 * @param node
	 *            the {@code SimpleName}
	 * @return {@code true} if {@code node} is a length array access,
	 *         {@code false} otherwise
	 */
	private static boolean isArrayLengthAccess(final org.eclipse.jdt.core.dom.SimpleName node) {
		boolean result = false;
		if (node.getIdentifier().equals("length")) { //test the name //$NON-NLS-1$
			IBinding nodeBinding = node.resolveBinding();
			if (nodeBinding instanceof IVariableBinding) {
				// test if declared in a method or a class
				// not sufficient because a variable declared in an initializer
				// has no declaring class and not declaring method
				if (((IVariableBinding) nodeBinding).getDeclaringClass() == null
						&& (((IVariableBinding) nodeBinding).getDeclaringMethod() == null)) {

					org.eclipse.jdt.core.dom.Expression parent = null;
					// we test if the qualifier is an array, if present
					if (node.getParent() instanceof org.eclipse.jdt.core.dom.Expression) {
						if (node.getParent() instanceof QualifiedName) {
							parent = ((QualifiedName) node.getParent()).getQualifier();
						} else if (node.getParent() instanceof org.eclipse.jdt.core.dom.FieldAccess) { // for
																										// methods
																										// which
																										// returns
																										// arrays
							parent = ((org.eclipse.jdt.core.dom.FieldAccess) node.getParent())
									.getExpression();
						}
						result = isArray(parent);
					}
				}
			}
		}
		return result;
	}

	/**
	 * indicate if {@code node} is of type array.
	 * 
	 * @param node
	 *            the expression
	 * @return {@code true} if it is of type array, {@code false} otherwise
	 * @see org.eclipse.jdt.core.dom.Expression#resolveTypeBinding()
	 */
	private static boolean isArray(final org.eclipse.jdt.core.dom.Expression node) {
		if (node != null) {
			ITypeBinding typebinding = node.resolveTypeBinding();
			return typebinding != null && typebinding.isArray();
		}
		return false;
	}

	/**
	 * Indicate if {@code node} is the name of a constructor declaration.
	 * 
	 * @param node
	 *            the {@code SimpleName}
	 * @return {@code true} if this {@code SimpleName} is a name of a
	 *         constructor declaration, {@code false} otherwise
	 */
	private static boolean isConstructorDeclaration(final org.eclipse.jdt.core.dom.SimpleName node) {
		boolean result = node.getParent() instanceof org.eclipse.jdt.core.dom.MethodDeclaration
				&& ((org.eclipse.jdt.core.dom.MethodDeclaration) node.getParent()).isConstructor()
				&& ((org.eclipse.jdt.core.dom.MethodDeclaration) node.getParent()).getName() == node;
		return result;
	}

	// private static boolean isQualifierChildAsName(final
	// org.eclipse.jdt.core.dom.SimpleName node) {
	// boolean result = false;
	// if (node.getParent() instanceof org.eclipse.jdt.core.dom.QualifiedName) {
	// result = (((org.eclipse.jdt.core.dom.QualifiedName)
	// node.getParent()).getName() == node);
	// }
	// return result;
	// }

	/**
	 * Indicate if a {@code SimpleName} is the name of a
	 * {@link org.eclipse.jdt.core.dom.MemberRef MemberRef}. Used because a
	 * MemberRef directly references a NamedElement, so we don't want
	 * SingleVariableAccess, etc.
	 * 
	 * @param node
	 *            the {@code SimpleName}
	 * @return {@code true} if {@code node} is the name of a MemberRef,
	 *         {@code false} otherwise
	 */
	private static boolean isNameChildOfMemberRef(final org.eclipse.jdt.core.dom.SimpleName node) {
		return (node.getParent() instanceof org.eclipse.jdt.core.dom.MemberRef && ((org.eclipse.jdt.core.dom.MemberRef) node
				.getParent()).getName() == node);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SimpleName node) {
		if (isConstructorDeclaration(node)) {
			return false;

		} else if (!node.isDeclaration()) {
			PendingElement pending = new PendingElement(this.factory);
			this.binding.put(node, pending);
		}
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SimpleName node) {
		if (JDTVisitor.isArrayLengthAccess(node)) {
			return;
		} else if (isConstructorDeclaration(node)) {
			return; // Misc case of constructor answering node.isDeclaration =
					// false. See javadoc of
					// org.eclipse.jdt.core.dom.SimpleName#isDeclaration()
		} else if (isNameChildOfMemberRef(node)) {
			// @see #isNameChildOfMemberRef(org.eclipse.jdt.core.dom.SimpleName)
			NamedElement target = JDTVisitorUtils.manageBindingRef(
					(PendingElement) this.binding.get(node), node, this);
			if (target != null) {
				this.binding.put(node, target);
			}
		} else if (!node.isDeclaration()) {

			PendingElement pending = (PendingElement) this.binding.get(node);
			IBinding jdtBinding = node.resolveBinding();

			if (jdtBinding == null) {
				// unresolved item,
				// we cannot decide if it will be a TypeAccess or PackageAccess
				// or ... etc
				// keep pending element here. Visiting the container will decide
				// the final access type

				NamedElement target = JDTVisitorUtils.manageBindingRef(pending, node, this);
				if (target != null) {
					// do something ?
				}
			} else if (jdtBinding.getKind() == IBinding.METHOD) {
				// no MethodAccess
				NamedElement target = JDTVisitorUtils.manageBindingRef(pending, node, this);
				if (target != null) {
					this.binding.put(node, target);
				}

			} else if (jdtBinding.getKind() == IBinding.PACKAGE) {
				PackageAccess pckAcc = this.factory.createPackageAccess();
				pending.setClientNode(pckAcc);
				pending.setLinkName("package"); //$NON-NLS-1$

				this.binding.put(node, pckAcc);

				NamedElement target = JDTVisitorUtils.manageBindingRef(pending, node, this);
				if (target != null) {
					pckAcc.setPackage((Package) target);
				}

			} else if (jdtBinding.getKind() == IBinding.TYPE) {
				TypeAccess typAcc = this.factory.createTypeAccess();
				pending.setClientNode(typAcc);
				pending.setLinkName("type"); //$NON-NLS-1$

				this.binding.put(node, typAcc);

				NamedElement target = JDTVisitorUtils.manageBindingRef(pending, node, this);
				if (target != null) {
					typAcc.setType((Type) target);
				}

			} else if (jdtBinding.getKind() == IBinding.VARIABLE) {
				SingleVariableAccess varAcc = this.factory.createSingleVariableAccess();
				pending.setClientNode(varAcc);
				pending.setLinkName("variable"); //$NON-NLS-1$

				this.binding.put(node, varAcc);

				NamedElement target = JDTVisitorUtils.manageBindingRef(pending, node, this);
				if (target != null) {
					varAcc.setVariable((VariableDeclaration) target);
				}
			} else {
				MoDiscoLogger
						.logError(
								"Unknown binding type for " + node + " : " + jdtBinding, JavaActivator.getDefault()); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SimpleType node) {
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SimpleType node) {
		this.binding.put(node, this.binding.get(node.getName()));
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SingleMemberAnnotation node) {
		Annotation element = this.factory.createAnnotation();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SingleMemberAnnotation node) {

		Annotation element = (Annotation) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getTypeName()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getTypeName()), this));
		}

		if (this.binding.get(node.getValue()) != null) {
			AnnotationMemberValuePair uniqueMember = this.factory.createAnnotationMemberValuePair();
			uniqueMember.setValue(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getValue()), this));
			// No member can be retrieved for a SingleMemberAnnotation
			element.getValues().add(uniqueMember);
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SingleVariableDeclaration node) {
		SingleVariableDeclaration element = null;
		if (this.isINCREMENTALDISCOVERING) {
			AbstractMethodDeclaration methodDelaration = null;
			if (node.getParent() instanceof org.eclipse.jdt.core.dom.MethodDeclaration) {
				org.eclipse.jdt.core.dom.MethodDeclaration abstractMethodDeclaration = (org.eclipse.jdt.core.dom.MethodDeclaration) node
						.getParent();
				methodDelaration = (AbstractMethodDeclaration) getGlobalBindings().getTarget(
						JDTVisitorUtils.getQualifiedName(abstractMethodDeclaration));
				if (methodDelaration != null) {
					Iterator<SingleVariableDeclaration> parameters = methodDelaration
							.getParameters().iterator();
					while (parameters.hasNext() && element == null) {
						SingleVariableDeclaration singleVariableDeclaration = parameters.next();
						int svdIndex = singleVariableDeclaration.getMethodDeclaration()
								.getParameters().indexOf(singleVariableDeclaration);
						org.eclipse.jdt.core.dom.MethodDeclaration jdtMethodeDeclaration = (org.eclipse.jdt.core.dom.MethodDeclaration) node
								.getParent();
						int nodeIndex = jdtMethodeDeclaration.parameters().indexOf(node);
						if (nodeIndex == svdIndex) {
							element = singleVariableDeclaration;
						}
					}
				}
			}
		}
		if (element == null) {
			element = this.factory.createSingleVariableDeclaration();
		}
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SingleVariableDeclaration node) {
		SingleVariableDeclaration element = (SingleVariableDeclaration) this.binding.get(node);
		initializeNode(element, node);

		element.setExtraArrayDimensions(node.getExtraDimensions());
		element.setVarargs(node.isVarargs());

		if (this.binding.get(node.getInitializer()) != null) {
			element.setInitializer(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getInitializer()), this));
		}

		if (this.binding.get(node.getType()) != null) {
			if (this.isINCREMENTALDISCOVERING && element.getType() != null) {
				// To avoid to re-create an existing access types
				deepRemove(element.getType());
				element.setType(null);
			}
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}

		element.setName(node.getName().getIdentifier());

		// visibility modifiers
		Modifier modiscoModifier = this.factory.createModifier();
		element.setModifier(modiscoModifier);
		modiscoModifier.setSingleVariableDeclaration(element);

		for (Object modifierNode : node.modifiers()) {
			if (((IExtendedModifier) modifierNode).isModifier()) {
				manageModifier((org.eclipse.jdt.core.dom.Modifier) modifierNode, modiscoModifier);
			}
		}

		// annotation modifiers
		for (Object modifierNode : node.modifiers()) {
			if (((IExtendedModifier) modifierNode).isAnnotation()) {
				Annotation annotation = (Annotation) this.binding.get(modifierNode);
				if (annotation != null) {
					element.getAnnotations().add(annotation);
				}
			}
		}

		JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.StringLiteral node) {
		StringLiteral element = this.factory.createStringLiteral();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.StringLiteral node) {
		StringLiteral element = (StringLiteral) this.binding.get(node);
		initializeNode(element, node);

		element.setEscapedValue(node.getEscapedValue());
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SuperConstructorInvocation node) {
		SuperConstructorInvocation element = this.factory.createSuperConstructorInvocation();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SuperConstructorInvocation node) {
		SuperConstructorInvocation element = (SuperConstructorInvocation) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}

		for (Iterator<?> i = node.arguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getArguments().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}

		for (Iterator<?> i = node.typeArguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getTypeArguments().add(JDTVisitorUtils.completeTypeAccess(itElement, this));
			}
		}

		PendingElement constructorRef = new PendingElement(this.factory);
		constructorRef.setClientNode(element);
		constructorRef.setLinkName("method"); //$NON-NLS-1$

		JDTVisitorUtils.manageBindingRef(constructorRef, node, this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SuperFieldAccess node) {
		SuperFieldAccess element = this.factory.createSuperFieldAccess();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SuperFieldAccess node) {
		SuperFieldAccess element = (SuperFieldAccess) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getName()) != null) {
			element.setField(JDTVisitorUtils.completeVariableAccess(
					this.binding.get(node.getName()), this));
		}

		if (this.binding.get(node.getQualifier()) != null) {
			element.setQualifier(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getQualifier()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SuperMethodInvocation node) {
		SuperMethodInvocation element = this.factory.createSuperMethodInvocation();
		this.binding.put(node, element);

		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SuperMethodInvocation node) {
		SuperMethodInvocation element = (SuperMethodInvocation) this.binding.get(node);
		initializeNode(element, node);

		ASTNode modiscoNode = null;

		for (Iterator<?> i = node.arguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getArguments().add(JDTVisitorUtils.completeExpression(itElement, this));
			}
		}

		for (Iterator<?> i = node.typeArguments().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getTypeArguments().add(JDTVisitorUtils.completeTypeAccess(itElement, this));
			}
		}

		modiscoNode = this.binding.get(node.getName());
		if (JDTVisitorUtils.completeBinding(element, modiscoNode, "method")) { //$NON-NLS-1$
			element.setMethod((AbstractMethodDeclaration) modiscoNode);
		}

		if (this.binding.get(node.getQualifier()) != null) {
			element.setQualifier(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getQualifier()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SwitchCase node) {
		SwitchCase element = this.factory.createSwitchCase();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SwitchCase node) {
		SwitchCase element = (SwitchCase) this.binding.get(node);
		initializeNode(element, node);

		element.setDefault(node.isDefault());

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SwitchStatement node) {
		SwitchStatement element = this.factory.createSwitchStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SwitchStatement node) {
		SwitchStatement element = (SwitchStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
		for (Iterator<?> i = node.statements().iterator(); i.hasNext();) {
			Statement itElement = (Statement) this.binding.get(i.next());
			if (itElement != null) {
				element.getStatements().add(itElement);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.SynchronizedStatement node) {
		SynchronizedStatement element = this.factory.createSynchronizedStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.SynchronizedStatement node) {
		SynchronizedStatement element = (SynchronizedStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Block) this.binding.get(node.getBody()));
		}

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.TagElement node) {
		TagElement element = this.factory.createTagElement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.TagElement node) {
		TagElement element = (TagElement) this.binding.get(node);
		initializeNode(element, node);

		for (Iterator<?> iterator = node.fragments().iterator(); iterator.hasNext();) {
			ASTNode itElement = this.binding.get(iterator.next());
			if (itElement != null) {
				// if the tag is a SimpleName, we have a PendingElement here
				if (itElement instanceof PendingElement) {
					UnresolvedItemAccess unrAcc = this.factory.createUnresolvedItemAccess();
					((PendingElement) itElement).setClientNode(unrAcc);
					((PendingElement) itElement).setLinkName("element"); //$NON-NLS-1$
					element.getFragments().add(unrAcc);
				} else {
					element.getFragments().add(itElement);
				}
			}
		}

		element.setTagName(node.getTagName());
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.TextElement node) {
		TextElement element = this.factory.createTextElement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.TextElement node) {
		TextElement element = (TextElement) this.binding.get(node);
		initializeNode(element, node);

		element.setText(node.getText());
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ThisExpression node) {
		ThisExpression element = this.factory.createThisExpression();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ThisExpression node) {
		ThisExpression element = (ThisExpression) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getQualifier()) != null) {
			element.setQualifier(JDTVisitorUtils.completeTypeAccess(
					this.binding.get(node.getQualifier()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.ThrowStatement node) {
		ThrowStatement element = this.factory.createThrowStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.ThrowStatement node) {
		ThrowStatement element = (ThrowStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.TryStatement node) {
		TryStatement element = this.factory.createTryStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.TryStatement node) {
		TryStatement element = (TryStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Block) this.binding.get(node.getBody()));
		}

		if (this.binding.get(node.getFinally()) != null) {
			element.setFinally((Block) this.binding.get(node.getFinally()));
		}

		for (Iterator<?> i = node.catchClauses().iterator(); i.hasNext();) {
			CatchClause itElement = (CatchClause) this.binding.get(i.next());
			if (itElement != null) {
				element.getCatchClauses().add(itElement);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.TypeDeclaration node) {
		TypeDeclaration element = null;
		if (this.isINCREMENTALDISCOVERING) {
			// To find previously created proxy
			String qname = JDTVisitorUtils.getQualifiedName(node);
			element = (TypeDeclaration) getGlobalBindings().getTarget(qname);
			if (element != null) {
				unSetProxy(element);
			}
		}
		if (element == null) {
			if (node.isInterface()) {
				element = this.factory.createInterfaceDeclaration();
			} else {
				element = this.factory.createClassDeclaration();
			}
		}
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.TypeDeclaration node) {
		TypeDeclaration element = (TypeDeclaration) this.binding.get(node);
		initializeNode(element, node);

		if (!node.isInterface()) {
			ClassDeclaration classDeclaration = (ClassDeclaration) element;
			if (this.isINCREMENTALDISCOVERING && classDeclaration.getSuperClass() != null) {
				// To avoid to re-create an existing access type
				deepRemove(classDeclaration.getSuperClass());
				classDeclaration.setSuperClass(null);
			}
			if (this.binding.get(node.getSuperclassType()) != null) {
				classDeclaration.setSuperClass(JDTVisitorUtils.completeTypeAccess(
						this.binding.get(node.getSuperclassType()), this));
			}
		}
		if (this.isINCREMENTALDISCOVERING) {
			// To avoid to re-create an existing access type
			List<TypeAccess> superInterfaceList = new ArrayList<TypeAccess>(
					element.getSuperInterfaces());
			Iterator<TypeAccess> superInterface = superInterfaceList.iterator();
			while (superInterface.hasNext()) {
				TypeAccess typeAccess = superInterface.next();
				deepRemove(typeAccess);
				element.getSuperInterfaces().remove(typeAccess);
			}
		}
		for (Iterator<?> i = node.superInterfaceTypes().iterator(); i.hasNext();) {
			ASTNode modiscoNode = this.binding.get(i.next());
			if (modiscoNode != null) {
				element.getSuperInterfaces().add(
						JDTVisitorUtils.completeTypeAccess(modiscoNode, this));
			}
		}

		for (Iterator<?> i = node.typeParameters().iterator(); i.hasNext();) {
			TypeParameter itElement = (TypeParameter) this.binding.get(i.next());
			if (itElement != null) {
				element.getTypeParameters().add(itElement);
			}
		}

		endVisitATD(node, element);
		endVisitBD(node, element);

		JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.TypeDeclarationStatement node) {
		TypeDeclarationStatement element = this.factory.createTypeDeclarationStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.TypeDeclarationStatement node) {
		TypeDeclarationStatement element = (TypeDeclarationStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getDeclaration()) != null) {
			element.setDeclaration((AbstractTypeDeclaration) this.binding.get(node.getDeclaration()));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.TypeLiteral node) {
		TypeLiteral element = this.factory.createTypeLiteral();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.TypeLiteral node) {
		TypeLiteral element = (TypeLiteral) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getType()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.TypeParameter node) {
		TypeParameter element = this.factory.createTypeParameter();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.TypeParameter node) {
		TypeParameter element = (TypeParameter) this.binding.get(node);
		initializeNode(element, node);
		element.setName(node.getName().getIdentifier());

		for (Iterator<?> i = node.typeBounds().iterator(); i.hasNext();) {
			ASTNode itElement = this.binding.get(i.next());
			if (itElement != null) {
				element.getBounds().add(JDTVisitorUtils.completeTypeAccess(itElement, this));
			}
		}

		JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.VariableDeclarationExpression node) {
		VariableDeclarationExpression element = this.factory.createVariableDeclarationExpression();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.VariableDeclarationExpression node) {
		VariableDeclarationExpression element = (VariableDeclarationExpression) this.binding
				.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getType()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}

		for (Iterator<?> i = node.fragments().iterator(); i.hasNext();) {
			VariableDeclarationFragment itElement = (VariableDeclarationFragment) this.binding
					.get(i.next());
			if (itElement != null) {
				element.getFragments().add(itElement);
			}
		}

		// visibility modifiers
		Modifier modiscoModifier = this.factory.createModifier();
		element.setModifier(modiscoModifier);
		modiscoModifier.setVariableDeclarationExpression(element);

		for (Object modifierNode : node.modifiers()) {
			if (((IExtendedModifier) modifierNode).isModifier()) {
				manageModifier((org.eclipse.jdt.core.dom.Modifier) modifierNode, modiscoModifier);
			}
		}

		// annotation modifiers
		for (Object modifierNode : node.modifiers()) {
			if (((IExtendedModifier) modifierNode).isAnnotation()) {
				Annotation annotation = (Annotation) this.binding.get(modifierNode);
				if (annotation != null) {
					element.getAnnotations().add(annotation);
				}
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.VariableDeclarationFragment node) {
		VariableDeclarationFragment element = null;
		if (this.isINCREMENTALDISCOVERING) {
			if (!JDTDelegateBindingFactory.getInstance().isLocal(node.getName())) {
				String variableDeclarationFragmentQN = JDTVisitorUtils.getQualifiedName(node);
				element = (VariableDeclarationFragment) getGlobalBindings().getTarget(
						variableDeclarationFragmentQN);
			}
		}
		if (element == null) {
			element = this.factory.createVariableDeclarationFragment();
		}
		element.setProxy(false);
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.VariableDeclarationFragment node) {
		VariableDeclarationFragment element = (VariableDeclarationFragment) this.binding.get(node);
		initializeNode(element, node);

		element.setExtraArrayDimensions(node.getExtraDimensions());
		element.setName(node.getName().getIdentifier());

		if (this.binding.get(node.getInitializer()) != null) {
			element.setInitializer(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getInitializer()), this));
		}

		JDTVisitorUtils.manageBindingDeclaration(element, node.getName(), this);
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.VariableDeclarationStatement node) {
		VariableDeclarationStatement element = this.factory.createVariableDeclarationStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.VariableDeclarationStatement node) {
		VariableDeclarationStatement element = (VariableDeclarationStatement) this.binding
				.get(node);
		initializeNode(element, node);

		// visibility modifiers
		Modifier modiscoModifier = this.factory.createModifier();
		element.setModifier(modiscoModifier);
		modiscoModifier.setVariableDeclarationStatement(element);

		for (Object modifierNode : node.modifiers()) {
			if (((IExtendedModifier) modifierNode).isModifier()) {
				manageModifier((org.eclipse.jdt.core.dom.Modifier) modifierNode, modiscoModifier);
			}
		}

		// annotation modifiers
		for (Object modifierNode : node.modifiers()) {
			if (((IExtendedModifier) modifierNode).isAnnotation()) {
				Annotation annotation = (Annotation) this.binding.get(modifierNode);
				if (annotation != null) {
					element.getAnnotations().add(annotation);
				}
			}
		}

		if (this.binding.get(node.getType()) != null) {
			element.setType(JDTVisitorUtils.completeTypeAccess(this.binding.get(node.getType()),
					this));
		}

		for (Iterator<?> i = node.fragments().iterator(); i.hasNext();) {
			VariableDeclarationFragment itElement = (VariableDeclarationFragment) this.binding
					.get(i.next());
			if (itElement != null) {
				element.getFragments().add(itElement);
			}
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.WhileStatement node) {
		WhileStatement element = this.factory.createWhileStatement();
		this.binding.put(node, element);
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.WhileStatement node) {
		WhileStatement element = (WhileStatement) this.binding.get(node);
		initializeNode(element, node);

		if (this.binding.get(node.getExpression()) != null) {
			element.setExpression(JDTVisitorUtils.completeExpression(
					this.binding.get(node.getExpression()), this));
		}

		if (this.binding.get(node.getBody()) != null) {
			element.setBody((Statement) this.binding.get(node.getBody()));
		}
	}

	@Override
	public boolean visit(final org.eclipse.jdt.core.dom.WildcardType node) {
		// if a WildcardType like this one has already been visited,
		// don't visit this one and use the other
		Binding id = JDTDelegateBindingFactory.getInstance().getBindingForWildCardType(node);
		if (getGlobalBindings().getTarget(id) != null) {
			return false;
		}
		return true;
	}

	@Override
	public void endVisit(final org.eclipse.jdt.core.dom.WildcardType node) {
		WildCardType type = JDTVisitorUtils.manageBindingRef(node, this);

		TypeAccess typAcc = this.factory.createTypeAccess();
		typAcc.setType(type);

		this.binding.put(node, typAcc);
	}

	/**
	 * Fill informations in the MoDisco {@code AbstractTypeDeclaration} node
	 * from the JDT {@code AbstractTypeDeclaration} node.
	 * 
	 * @param node
	 *            the JDT {@code AbstractTypeDeclaration} node
	 * @param element
	 *            the MoDisco {@code AbstractTypeDeclaration} node
	 */
	private void endVisitATD(final org.eclipse.jdt.core.dom.AbstractTypeDeclaration node,
			final AbstractTypeDeclaration element) {
		element.setName(node.getName().getIdentifier());
		for (Iterator<?> i = node.bodyDeclarations().iterator(); i.hasNext();) {
			BodyDeclaration itElement = (BodyDeclaration) this.binding.get(i.next());
			if (itElement != null) {
				element.getBodyDeclarations().add(itElement);
			}
		}

		// process for package direct types
		if (node.isPackageMemberTypeDeclaration()) {
			String ext = null;
			if (this.cuNode instanceof ICompilationUnit) {
				ext = ".java"; //$NON-NLS-1$
			} else if (this.cuNode instanceof IClassFile) {
				ext = ".class"; //$NON-NLS-1$
			}
			if (ext == null) {
				// memorize this AST node as the main
				setRootTypeOrEnum(element);
			} else {
				String mainClassName = this.currentFilePath.substring(this.currentFilePath
						.lastIndexOf(java.io.File.separator) + 1);
				mainClassName = mainClassName.substring(0, mainClassName.length() - ext.length());
				// process for main type of the .java file
				if (mainClassName.equalsIgnoreCase(node.getName().getIdentifier())) {
					// memorize this AST node as the main
					setRootTypeOrEnum(element);
				}
			}

			if (this.currentPackage == null) { // Type without package
				this.currentPackage = (Package) getGlobalBindings().getTarget(
						JDTVisitor.DEFAULT_PKG_ID);
				if (this.currentPackage == null) {
					this.currentPackage = this.factory.createPackage();
					this.currentPackage.setName(JDTVisitor.DEFAULT_PKG_ID);
					if (this.currentFilePath != null) {
						this.currentPackage.setModel(this.jdtModel);
					}
					getGlobalBindings().addTarget(JDTVisitor.DEFAULT_PKG_ID, this.currentPackage);
				}
			}
			this.currentPackage.getOwnedElements().add(element);
			// calculate qualified name
			// String qualifiedName = this.currentPackage.getQualifiedName() +
			// "." + element.getName();
		}
		if (this.isINCREMENTALDISCOVERING) {
			List<EObject> toBeRemoved = new ArrayList<EObject>();
			Iterator<BodyDeclaration> bodyDeclarations = element.getBodyDeclarations().iterator();
			while (bodyDeclarations.hasNext()) {
				BodyDeclaration bodyDeclaration = bodyDeclarations.next();
				if (bodyDeclaration.isProxy()) {
					toBeRemoved.add(bodyDeclaration);
				}
			}
			if (element instanceof EnumDeclaration) {
				Iterator<EnumConstantDeclaration> enums = ((EnumDeclaration) element)
						.getEnumConstants().iterator();
				while (enums.hasNext()) {
					EnumConstantDeclaration enumDecl = enums.next();
					if (enumDecl.isProxy()) {
						toBeRemoved.add(enumDecl);
					}
				}
			}
			// To remove proxy Field element that have been replaced by an other
			// one
			// This is useful in the case of the incremental algorithm
			Iterator<EObject> toBeRemovedIter = toBeRemoved.iterator();
			while (toBeRemovedIter.hasNext()) {
				EObject eObject = toBeRemovedIter.next();
				if (eObject instanceof FieldDeclaration) {
					FieldDeclaration fieldDeclaration = (FieldDeclaration) eObject;
					if (fieldDeclaration.getFragments().size() > 0) {
						String message = "Proxy field declaration should not contains any fragments (size=" //$NON-NLS-1$
								+ fieldDeclaration.getFragments().size() + "): " //$NON-NLS-1$
								+ JDTVisitorUtils.getQualifiedName(node) + "."; //$NON-NLS-1$
						try {
							message += (fieldDeclaration.getFragments().get(0)).getName();
						} catch (Exception e) {
							message += "???"; //$NON-NLS-1$
						}
						RuntimeException exception = new RuntimeException(message);
						MoDiscoLogger.logError(exception, JavaActivator.getDefault());
						throw exception;
					}
					deepRemove(eObject);
					element.getBodyDeclarations().remove(eObject);
					if (element instanceof EnumDeclaration) {
						((EnumDeclaration) element).getEnumConstants().remove(eObject);
					}
				}
			}
		}
	}

	/**
	 * Fill informations in the MoDisco {@code BodyDeclaration} node from the
	 * JDT {@code BodyDeclaration} node.
	 * 
	 * @param node
	 *            the JDT {@code BodyDeclaration} node
	 * @param element
	 *            the MoDisco {@code BodyDeclaration} node
	 */
	private void endVisitBD(final org.eclipse.jdt.core.dom.BodyDeclaration node,
			final BodyDeclaration element) {

		// visibility modifiers
		Modifier modiscoModifier = this.factory.createModifier();
		element.setModifier(modiscoModifier);
		modiscoModifier.setBodyDeclaration(element);

		for (Object modifierNode : node.modifiers()) {
			if (((IExtendedModifier) modifierNode).isModifier()) {
				manageModifier((org.eclipse.jdt.core.dom.Modifier) modifierNode, modiscoModifier);
			}
		}

		// annotation modifiers
		for (Iterator<?> anIt = node.modifiers().iterator(); anIt.hasNext();) {
			IExtendedModifier aModifier = (IExtendedModifier) anIt.next();
			if (aModifier.isAnnotation()) {
				Annotation annotation = (Annotation) this.binding.get(aModifier);
				if (annotation != null) {
					element.getAnnotations().add(annotation);
				}
			}
		}
		// Start position adjustment : for jdt visitor
		// a body starts at its javadoc, it is not consistent with others prefix
		// comments
		if (node.getJavadoc() != null) {
			Javadoc javadoc = (Javadoc) this.binding.get(node.getJavadoc());
			element.getComments().add(javadoc);
		}
	}

	/**
	 * Complete the MoDisco modifier with the informations of the JDT Modifier.
	 * 
	 * @param jdtModifier
	 *            the JDT Modifier
	 * @param modiscoModifier
	 *            the MoDisco Modifier
	 */
	private static void manageModifier(final org.eclipse.jdt.core.dom.Modifier jdtModifier,
			final Modifier modiscoModifier) {

		if (!modiscoModifier.isStatic()) {
			modiscoModifier.setStatic(jdtModifier.isStatic());
		}
		if (!modiscoModifier.isNative()) {
			modiscoModifier.setNative(jdtModifier.isNative());
		}
		if (!modiscoModifier.isStrictfp()) {
			modiscoModifier.setStrictfp(jdtModifier.isStrictfp());
		}
		if (!modiscoModifier.isSynchronized()) {
			modiscoModifier.setSynchronized(jdtModifier.isSynchronized());
		}
		if (!modiscoModifier.isTransient()) {
			modiscoModifier.setTransient(jdtModifier.isTransient());
		}
		if (!modiscoModifier.isVolatile()) {
			modiscoModifier.setVolatile(jdtModifier.isVolatile());
		}

		if (jdtModifier.isPrivate()) {
			modiscoModifier.setVisibility(VisibilityKind.PRIVATE);
		} else if (jdtModifier.isProtected()) {
			modiscoModifier.setVisibility(VisibilityKind.PROTECTED);
		} else if (jdtModifier.isPublic()) {
			modiscoModifier.setVisibility(VisibilityKind.PUBLIC);
		}

		if (jdtModifier.isAbstract()) {
			modiscoModifier.setInheritance(InheritanceKind.ABSTRACT);
		} else if (jdtModifier.isFinal()) {
			modiscoModifier.setInheritance(InheritanceKind.FINAL);
		}
	}

	public void setLocalBindings(final BindingManager localBindings) {
		this.localBindings = localBindings;
	}

	public BindingManager getLocalBindings() {
		return this.localBindings;
	}

	public void setGlobalBindings(final BindingManager globalBindings) {
		this.globalBindings = globalBindings;
	}

	public BindingManager getGlobalBindings() {
		return this.globalBindings;
	}

}
