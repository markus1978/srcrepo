/*******************************************************************************
 * This is a copy from MoDisco ((c) 2009 Mia-Software, with a view changes 
 * (c) 2013 Markus Scheidgen marked with HUB. 
 *******************************************************************************/

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
 *******************************************************************************/
package de.hub.srcrepo.modisco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.AnnotationTypeMemberDeclaration;
import org.eclipse.gmt.modisco.java.ArrayType;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.EnumConstantDeclaration;
import org.eclipse.gmt.modisco.java.EnumDeclaration;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.InterfaceDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.TypeDeclaration;
import org.eclipse.gmt.modisco.java.TypeParameter;
import org.eclipse.gmt.modisco.java.UnresolvedItem;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.gmt.modisco.java.internal.util.JavaUtil;
import org.eclipse.modisco.java.discoverer.internal.JavaActivator;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.ClassBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.FieldBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.MethodBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PackageBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.ParameterBinding;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement;

/**
 * Class used to store and resolves pending references between Java
 * {@link ASTNode}s.
 * <p>
 * It stores targets ({@link NamedElement}) and pendings ({@link PendingElement}
 * ).
 * <p>
 * Each target is represented by a {@link Binding}. Each pending reference knows
 * its target by a corresponding {@code Binding}. A simple comparison allows to
 * complete references.
 * <p>
 * After resolving pending references, the targets of the remaining references
 * are created as {@link org.eclipse.gmt.modisco.java.NamedElement#isProxy()
 * proxies}.
 * 
 * @see #resolveBindings(Model)
 * @see PendingElement#affectTarget(ASTNode)
 */
@SuppressWarnings("restriction")
public class SrcRepoBindingManager extends BindingManager {

	/**
	 * the targets (declared Java entities).
	 */
	private Map<String, NamedElement> targets = new HashMap<String, NamedElement>();

	/**
	 * Elements which causes problems during resolution.
	 */
	private final Map<String, UnresolvedItem> unresolvedItems = new HashMap<String, UnresolvedItem>();

	/**
	 * the pending references.
	 */
	private List<PendingElement> pendings = new ArrayList<PendingElement>();

	/**
	 * The EMF factory.
	 */
	private final JavaFactory factory;

	private static final char DOT_SEPARATOR = '.';

	/**
	 * the Model used only for the incremental discovery
	 */
	private Model model = null;

	/**
	 * Constructs an empty {@code BindingManager}.
	 * 
	 * @param factory
	 *            the EMF factory
	 */
	public SrcRepoBindingManager(final JavaFactory factory) {
		super(factory);
		this.factory = factory;
	}
	
	public void printTelemetry() {
		StringBuffer info = new StringBuffer();
		info.append("-- SrcRepoBindingManager telemetry ----------------------------\n");
		info.append("Pendings: " + pendings.size() + "\n");
		info.append("Targets: " + targets.size() + "\n");
		info.append("Unresolved " + unresolvedItems.size() + "\n");
		info.append("-- END --------------------------------------------------------" + "\n");
		
		System.out.println(info.toString());
	}

	/**
	 * Constructs a {@code BindingManager} containing the factory, the targets
	 * and the pending references of the specified {@code BindingManager}.
	 * 
	 * @param aBindingManager
	 *            an other {@code BindingManager}
	 */
	public SrcRepoBindingManager(final SrcRepoBindingManager aBindingManager) {
		super(aBindingManager);
		this.factory = aBindingManager.factory;
		this.targets = new HashMap<String, NamedElement>(aBindingManager.targets);
		this.pendings = new ArrayList<PendingElement>(aBindingManager.pendings);
		this.model = aBindingManager.model;
	}

	// HUB start
	public void addBindings(final SrcRepoBindingManager aBindingManager) {
		this.targets.putAll(aBindingManager.targets);
		this.pendings.addAll(aBindingManager.pendings);
	}

	public void addPackageBindings(final SrcRepoBindingManager aBindingManager) {
		for (String binding : aBindingManager.targets.keySet()) {
			NamedElement target = aBindingManager.targets.get(binding);
			if (target instanceof Package) {
				addTarget(binding, target);
			}
		}
	}

	// HUB end

	/**
	 * Enable incremental behavior.
	 * 
	 * @param model1
	 */
	public void enableIncrementalDiscovering(final Model model1) {
		this.model = model1;
	}

	/**
	 * Disable incremental behavior.
	 */
	public void disableIncrementalDiscovering() {
		this.model = null;
	}

	/**
	 * Return true if incremental behavior is enabled.
	 * 
	 * @return true if incremental behavior is enabled
	 */
	public boolean isIncrementalDiscovering() {
		return this.model != null;
	}

	/**
	 * Add the Java entity {@code target} represented by the {@code binding} to
	 * the targets of this BindingManager.
	 * 
	 * @param binding
	 *            the string representation of the {@code Binding}
	 * @param target
	 *            the NamedElement object
	 */
	public void addTarget(final String binding, final NamedElement target) {
		this.targets.put(binding, target);
	}

	/**
	 * Add the Java entity {@code target} represented by the {@code binding} to
	 * the targets of this BindingManager.
	 * 
	 * @param binding
	 *            the string representation of the {@code Binding}
	 * @param target
	 *            the NamedElement object
	 */
	public void addTarget(final Binding binding, final NamedElement target) {
		this.addTarget(binding.toString(), target);
	}

	/**
	 * Indicate if a {@code NamedElement} representated by the {@code binding}
	 * is contained in this BindingManager.
	 * 
	 * @param binding
	 *            the string representation of a {@code Binding} representating
	 *            the searched Java entity
	 * @return {@code true} if this BindingManager contains a NamedElement
	 *         corresponding to the {@code binding}, {@code false} otherwise.
	 */
	public boolean containsTarget(final String binding) {
		boolean targetFound;
		if (this.targets.containsKey(binding)) {
			targetFound = true;
		} else {
			NamedElement ne = searchQNInModel(binding);
			if (ne != null) {
				this.addTarget(binding, ne);
				targetFound = true;
			} else {
				targetFound = false;
			}
		}
		return targetFound;
	}

	/**
	 * Indicate if a {@code NamedElement} representated by the {@code binding}
	 * is contained in this BindingManager.
	 * 
	 * @param binding
	 *            a {@code Binding} representating the searched Java entity.
	 * @return {@code true} if this BindingManager contains a
	 *         {@code NamedElement} corresponding to the {@code binding},
	 *         {@code false} otherwise.
	 */
	public boolean containsTarget(final Binding binding) {
		return this.containsTarget(binding.toString());
	}

	/**
	 * Returns the {@code NamedElement} represented by the {@code Binding}.
	 * 
	 * @param binding
	 *            a {@code Binding} representating the searched Java entity.
	 * @return the {@code NamedElement} associated with the {@code binding}, or
	 *         {@code null} if the targeted entity is not contained in this
	 *         {@code BindingManager}
	 */
	public NamedElement getTarget(final String id) {
		NamedElement resultNamedElement = null;
		if (id != null) {
			NamedElement ne = this.targets.get(id);
			if (ne != null) {
				resultNamedElement = ne;
			} else {
				ne = searchQNInModel(id);
				if (ne != null) {
					this.addTarget(id, ne);
					resultNamedElement = ne;
				}
			}
		}
		return resultNamedElement;
	}

	/**
	 * Returns the {@code NamedElement} represented by the {@code Binding}.
	 * 
	 * @param binding
	 *            a {@code Binding} representating the searched Java entity.
	 * @return the {@code NamedElement} associated with the {@code binding}, or
	 *         {@code null} if the targeted entity is not contained in this
	 *         {@code BindingManager}
	 */
	public NamedElement getTarget(final Binding binding) {
		NamedElement target = null;
		// HUB: this causes problems when resolving package comments/packages.
		// For some reason the package binding is unresolved, but the binding
		// manager has an entry for the package.
		// No idea, if this changes causes any other problems. But I guess, if
		// there is a target, it is good enough.
		// if (!(binding instanceof UnresolvedBinding)) {
		target = this.getTarget(binding.toString());
		// }
		return target;
	}

	/**
	 * Add a pending reference representated by a {@code PendingElement} to this
	 * {@code BindingManager}.
	 * 
	 * @param ref
	 *            the {@code PendingElement} object
	 * @param binding
	 *            a {@code Binding} representating the referenced Java entity.
	 */
	public void addPending(final PendingElement ref, final Binding binding) {
		ref.setBinding(binding);
		this.pendings.add(ref);
	}

	/**
	 * Returns the {@link PendingElement} contained in this
	 * {@code BindingManager} specified by the {@code clientNode} and the
	 * {@code linkName}.
	 * 
	 * @param clientNode
	 *            the client node
	 * @param linkName
	 *            the name of the feature
	 * @return the {@code PendingElement} object specified by the
	 *         {@code clientNode} and the {@code linkName}, or {@code null} if
	 *         the object is not contained in this {@code BindingManager}
	 */
	public PendingElement getPending(final ASTNode clientNode, final String linkName) {
		PendingElement result = null;
		for (PendingElement pe : this.pendings) {
			if (pe.getClientNode() != null && pe.getClientNode().equals(clientNode) && pe.getLinkName() != null
					&& pe.getLinkName().equals(linkName)) {
				result = pe;
			}
		}
		return result;
	}

	/**
	 * Resolution of the pending references against the targets of this
	 * {@code BindingManager}. if {@code model} is {@code null}, the unresolved
	 * bindings will not be computed.
	 * 
	 * @param model1
	 *            the resulting {@link Model}.
	 */
	public void resolveBindings(final Model model1) {
		List<PendingElement> unresolvedBindings = new ArrayList<PendingElement>();
		for (PendingElement pe : this.pendings) {
			if (pe.getClientNode() != null) {
				NamedElement target = this.getTarget(pe.getBinding());
				if (target == null) {
					unresolvedBindings.add(pe);
				} else {
					pe.affectTarget(target);
				}
			}
		}
		manageUnresolvedBindings(model1, unresolvedBindings);
		// HUB
		this.pendings.clear();
		// HUB
	}

	private void manageUnresolvedBindings(final Model model1, final List<PendingElement> unresolvedBindings) {
		if (model1 != null) {
			for (PendingElement pe : unresolvedBindings) {
				NamedElement target = null;
				target = getProxyElement(pe, model1);
				if (target != null) {
					pe.affectTarget(target);
				}
			}
		}
	}

	private NamedElement searchQNInModel(final String qualifiedName) {
		NamedElement resultNamedElement = null;
		if (isIncrementalDiscovering()) {
			resultNamedElement = JavaUtil.getNamedElementByQualifiedName(this.model, qualifiedName, this.targets);
			if (resultNamedElement != null) {
				this.addTarget(qualifiedName, resultNamedElement);
			}
		}
		return resultNamedElement;
	}

	/**
	 * Convenience method to {@code resolveBindings(null)}.
	 */
	public void resolveBindings() {
		this.resolveBindings(null);
	}

	private NamedElement getProxyElement(final PendingElement pe, final Model model1) {
		NamedElement result = null;
		Binding bd = pe.getBinding();
		if (bd instanceof PackageBinding) {
			result = getPackageDeclaration((PackageBinding) bd, model1);

		} else if (bd instanceof ClassBinding) {
			result = getTypeDeclaration((ClassBinding) bd, model1);

		} else if (bd instanceof FieldBinding) {
			if (((FieldBinding) bd).isEnumConstant()) {
				result = getEnumConstantDeclaration((FieldBinding) bd, model1);
			} else {
				result = getFieldDeclaration((FieldBinding) bd, model1);
			}

		} else if (bd instanceof MethodBinding) {
			if (((MethodBinding) bd).isAnnotationMember()) {
				result = getAnnotationTypeMemberDeclaration((MethodBinding) bd, model1);
			} else {
				result = getMethodDeclaration((MethodBinding) bd, model1);
			}
		} else {
			result = this.unresolvedItems.get(bd.getName());
			if (result != null) {
				// some misc unresolved bindings might have the same
				// bd.getName()
				EStructuralFeature feature = pe.getClientNode().eClass().getEStructuralFeature(pe.getLinkName());
				if (!feature.getEType().isInstance(result)) {
					result = null;
				}
			}
			if (result == null) {
				result = pe.affectUnresolvedTarget();
				result.setName(bd.getName());
				result.setProxy(true);
				model1.getUnresolvedItems().add((UnresolvedItem) result);
				this.unresolvedItems.put(bd.getName(), (UnresolvedItem) result);
			}
		}
		return result;
	}

	private Package getPackageDeclaration(final PackageBinding binding, final Model model1) {
		Package result = (Package) this.getTarget(binding);
		if (result == null) {
			result = createProxiesPackageHierarchy(binding, model1);
			this.addTarget(binding, result);
		}
		return result;
	}

	/*
	 * We have to let this abstract type, because this method will manage
	 * primitives and also objects declarations
	 */
	private NamedElement getTypeDeclaration(final ClassBinding binding, final Model model1) {
		NamedElement result = this.getTarget(binding); // AbstractTypeDeclaration
														// or PrimitiveType
		if (result == null) {
			if (binding.isAnnotation()) {
				result = this.factory.createAnnotationTypeDeclaration();
			} else if (binding.isEnum()) {
				result = this.factory.createEnumDeclaration();
			} else if (binding.isInterface()) {
				result = this.factory.createInterfaceDeclaration();
			} else {
				result = this.factory.createClassDeclaration();
			}
			result.setName(binding.getName());
			result.setProxy(true);

			if (binding.getOwnerPackage() != null && binding.getDeclaringClass() == null) {
				Package owner = getPackageDeclaration(binding.getOwnerPackage(), model1);
				if (owner != null) {
					if (result instanceof AbstractTypeDeclaration) {
						((AbstractTypeDeclaration) result).setPackage(owner);
					}
					// HUB: I else'ed this brachn. OwnedElements is the oppisite
					// of package, and if the package of result is already set,
					// the result is already part of owner#ownedElements.
					// Otherwise, this will cause inverseRemove and inverseAdd,
					// which the indexed set of ownedElements does not support.
					else {
						owner.getOwnedElements().add((AbstractTypeDeclaration) result);
					}
				} else {
					IStatus status = new Status(IStatus.ERROR, JavaActivator.PLUGIN_ID,
							"Unkown error.", new Exception("owner == null: " //$NON-NLS-1$ //$NON-NLS-2$
									+ binding.getOwnerPackage().getName()));
					JavaActivator.getDefault().getLog().log(status);
				}
			} else if (binding.getDeclaringClass() != null) {
				AbstractTypeDeclaration declaring = (AbstractTypeDeclaration) getTypeDeclaration(binding.getDeclaringClass(),
						model1);
				if (declaring != null) {
					declaring.getBodyDeclarations().add((AbstractTypeDeclaration) result);
				}
			} else {
				if (result instanceof Type) {
					// To be sure that result object is owned by a resource.
					model1.getOrphanTypes().add((Type) result);
				} else {
					String message = binding.toString() + " will not be contained by the model element."; //$NON-NLS-1$
					IStatus status = new Status(IStatus.ERROR, JavaActivator.PLUGIN_ID, message);
					JavaActivator.getDefault().getLog().log(status);
				}
			}
			// declaring the super class
			if (!binding.isInterface() && binding.getSuperClass() != null) {
				ClassDeclaration superClass = (ClassDeclaration) getTypeDeclaration(binding.getSuperClass(), model1);
				if (superClass != null) {
					TypeAccess typAcc = this.factory.createTypeAccess();
					typAcc.setType(superClass);
					((ClassDeclaration) result).setSuperClass(typAcc);
				}
			}
			// declaring the super interfaces
			if (binding.getSuperInterfaces() != null) {
				for (ClassBinding anInterface : binding.getSuperInterfaces()) {
					InterfaceDeclaration superInterface = (InterfaceDeclaration) getTypeDeclaration(anInterface, model1);
					if (superInterface != null) {
						TypeAccess typAcc = this.factory.createTypeAccess();
						typAcc.setType(superInterface);
						((AbstractTypeDeclaration) result).getSuperInterfaces().add(typAcc);
					}
				}
			}
			// declaring the type parameters
			if (binding.getTypeParameters() != null) {
				for (String typeParameterName : binding.getTypeParameters()) {
					TypeParameter typeParameter = this.factory.createTypeParameter();
					typeParameter.setName(typeParameterName);
					typeParameter.setProxy(true);
					((TypeDeclaration) result).getTypeParameters().add(typeParameter);
				}
			}
			this.addTarget(binding, result);
		}
		return result;
	}

	private EnumConstantDeclaration getEnumConstantDeclaration(final FieldBinding binding, final Model model1) {
		EnumConstantDeclaration result = (EnumConstantDeclaration) this.getTarget(binding);
		if (result == null) {
			result = this.factory.createEnumConstantDeclaration();
			result.setProxy(true);
			result.setName(binding.getName());

			if (binding.getDeclaringClass() != null) {
				EnumDeclaration declaring = (EnumDeclaration) getTypeDeclaration(binding.getDeclaringClass(), model1);
				if (declaring != null) {
					declaring.getEnumConstants().add(result);
				}
			} else {
				// To be sure that result object is owned by a resource.
				model1.eResource().getContents().add(result);
			}
			this.addTarget(binding, result);
		}
		return result;
	}

	private VariableDeclarationFragment getFieldDeclaration(final FieldBinding binding, final Model model1) {
		VariableDeclarationFragment result = (VariableDeclarationFragment) this.getTarget(binding);
		if (result == null) {
			FieldDeclaration field = this.factory.createFieldDeclaration();
			field.setProxy(true);

			result = this.factory.createVariableDeclarationFragment();
			result.setProxy(true);
			result.setName(binding.getName());

			field.getFragments().add(result);

			if (binding.getDeclaringClass() != null) {
				AbstractTypeDeclaration declaring = (AbstractTypeDeclaration) getTypeDeclaration(binding.getDeclaringClass(),
						model1);
				if (declaring != null) {
					declaring.getBodyDeclarations().add(field);
				}
			} else {
				// To be sure that result object is owned by a resource.
				model1.eResource().getContents().add(field);
			}
			this.addTarget(binding, result);
		}
		return result;
	}

	private AbstractMethodDeclaration getMethodDeclaration(final MethodBinding binding, final Model model1) {
		AbstractMethodDeclaration result = (AbstractMethodDeclaration) this.getTarget(binding);
		if (result == null) {
			if (binding.isConstructor()) {
				result = this.factory.createConstructorDeclaration();
			} else {
				result = this.factory.createMethodDeclaration();
			}

			result.setProxy(true);
			result.setName(binding.getName());
			for (int i = 0; i < binding.getParameters().size(); i++) {
				ParameterBinding param = binding.getParameters().get(i);
				SingleVariableDeclaration paramDecl = this.factory.createSingleVariableDeclaration();
				paramDecl.setProxy(true);
				paramDecl.setName("arg" + i); //$NON-NLS-1$
				result.getParameters().add(paramDecl);

				TypeAccess typAcc = this.factory.createTypeAccess();
				if (param.isArray()) {
					typAcc.setType(getArrayTypeDeclaration(param, model1));
				} else {
					typAcc.setType((Type) getTypeDeclaration(param.getElementType(), model1));
				}
				paramDecl.setType(typAcc);
			}
			// Placed at the end of the method to avoid to connect
			// a type body declaration which is not finished to construct
			// This avoid null pointer exception while searching into the model.
			if (binding.getDeclaringClass() != null) {
				AbstractTypeDeclaration declaring = (AbstractTypeDeclaration) getTypeDeclaration(binding.getDeclaringClass(),
						model1);
				if (declaring != null) {
					declaring.getBodyDeclarations().add(result);
				}
			}
			this.addTarget(binding, result);
		}
		return result;
	}

	private ArrayType getArrayTypeDeclaration(final ParameterBinding binding, final Model model1) {
		ArrayType result = (ArrayType) this.getTarget(binding);
		if (result == null) {
			result = this.factory.createArrayType();
			result.setName(binding.toString());
			result.setDimensions(binding.getDimensions());

			TypeAccess typAcc = this.factory.createTypeAccess();
			typAcc.setType((Type) getTypeDeclaration(binding.getElementType(), model1));
			result.setElementType(typAcc);

			model1.getOrphanTypes().add(result);
			this.addTarget(binding, result);
		}
		return result;
	}

	private AnnotationTypeMemberDeclaration getAnnotationTypeMemberDeclaration(final MethodBinding binding, final Model model1) {
		AnnotationTypeMemberDeclaration result = (AnnotationTypeMemberDeclaration) this.getTarget(binding);
		if (result == null) {
			result = this.factory.createAnnotationTypeMemberDeclaration();
			result.setProxy(true);
			result.setName(binding.getName());
			if (binding.getDeclaringClass() != null) {
				AbstractTypeDeclaration declaring = (AbstractTypeDeclaration) getTypeDeclaration(binding.getDeclaringClass(),
						model1);
				if (declaring != null) {
					declaring.getBodyDeclarations().add(result);
				}
			}
			this.addTarget(binding, result);
		}
		return result;
	}

	// create iterately a hierarchy of packages
	// HUB changes throughout the whole method -> this is to create each package
	// only once
	private Package createProxiesPackageHierarchy(final PackageBinding binding, final Model model1) {
		Package result = this.factory.createPackage();
		result.setProxy(true);
		if (binding.getName().indexOf(SrcRepoBindingManager.DOT_SEPARATOR) == -1) {
			result.setName(binding.getName());
			model1.getOwnedElements().add(result);
		} else {
			String currentPackageName = binding.getName();
			Package currentPackage = result;
			int lastDotIndex = currentPackageName.lastIndexOf(SrcRepoBindingManager.DOT_SEPARATOR);
			currentPackage.setName(currentPackageName.substring(lastDotIndex + 1));
			// iterate on parents packages to create them if needed
			while (lastDotIndex > 0) {
				currentPackageName = currentPackageName.substring(0, lastDotIndex);
				Package aParentPackage = null;
				if (!this.containsTarget(currentPackageName)) {
					aParentPackage = this.factory.createPackage();
					aParentPackage.setProxy(true);
					this.addTarget(currentPackageName, aParentPackage);
					lastDotIndex = currentPackageName.lastIndexOf('.');
					if (lastDotIndex < 0) { // top level package
						aParentPackage.setName(currentPackageName);
						model1.getOwnedElements().add(aParentPackage);
					} else {
						aParentPackage.setName(currentPackageName.substring(lastDotIndex + 1));
					}
					aParentPackage.getOwnedPackages().add(currentPackage);
				} else {
					aParentPackage = (Package) this.getTarget(currentPackageName);
					aParentPackage.getOwnedPackages().add(currentPackage);
					break; // if this package is registered, parents packages
							// also are
				}
				currentPackage = aParentPackage;
			}
		}
		return result;
	}
}
