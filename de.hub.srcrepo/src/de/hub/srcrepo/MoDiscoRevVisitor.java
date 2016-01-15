package de.hub.srcrepo;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.Binding;

import de.hub.srcrepo.internal.SrcRepoBindingManager;
import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.PendingElement;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Target;

@SuppressWarnings("restriction")
public abstract class MoDiscoRevVisitor extends RevVisitor {
	
	private final JavaPackage targetMetaModel;
	
	public MoDiscoRevVisitor(JavaPackage targetMetaModel) {
		super();
		this.targetMetaModel = targetMetaModel;
	}

	@Override
	protected final void onRev(Rev rev, Map<String, AbstractFileRef> files) {		
		if (!filter(rev)) {
			return;
		}
		
		Model targetModel = targetMetaModel.getJavaFactory().createModel();		
		JavaModelMerge merge = new JavaModelMerge(targetModel);
		
		// merge the models from all compilation units
		for (AbstractFileRef ref: files.values()) {
			if (ref instanceof JavaCompilationUnitRef) {
				JavaCompilationUnitRef compilationUnitRef = (JavaCompilationUnitRef)ref;
				Model sourceModel = compilationUnitRef.getCompilationUnitModel().getJavaModel();
				merge.mergeModel(sourceModel);
			}
		}
		merge.completeMerge();
		
		SrcRepoBindingManager bindingManager = new SrcRepoBindingManager(targetMetaModel.getJavaFactory());
		
		// load all targets and pending elements
		for (AbstractFileRef ref: files.values()) {
			if (ref instanceof JavaCompilationUnitRef) {
				CompilationUnitModel compilationUnitModel = ((JavaCompilationUnitRef)ref).getCompilationUnitModel();
				for (Target target: compilationUnitModel.getTargets()) {
					NamedElement targetElement = (NamedElement)merge.sourceToObject.get(target.getTarget());
					if (targetElement == null) {
						throw new NullPointerException();
					}
					bindingManager.addTarget(target.getId(), targetElement);
				}
				for (PendingElement pending: compilationUnitModel.getPendings()) {
					org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement pendingElement = new org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement(targetMetaModel.getJavaFactory());
					pendingElement.setClientNode((ASTNode)merge.sourceToObject.get(pending.getClientNode()));
					pendingElement.setLinkName(pending.getLinkName());					
					bindingManager.addPending(pendingElement, new Binding(pending.getBinding()));					
				}
			}
		}
		
		// resolve
		bindingManager.resolveBindings();
		
		// do
		onRev(rev, targetModel);
		
		// remove the old model
		EcoreUtil.delete(targetModel);
	}
	
	protected boolean filter(Rev rev) {
		return true;
	}
	
	protected abstract void onRev(Rev rev, Model model);
	
	private class JavaModelMerge {
		final SourceToTargetMetaModelCopier sourceToObject = new SourceToTargetMetaModelCopier();
		final Model targetModel;
		final Map<String, Type> orphanTypes = new HashMap<String, Type>();
		final Map<String, Package> packages = new HashMap<String, Package>();
		
		public JavaModelMerge(Model targetModel) {
			super();
			this.targetModel = targetModel;
		}

		void mergeModel(Model sourceModel) {
			for (Package sourcePackage: sourceModel.getOwnedElements()) {
				mergePackage(sourcePackage);
			}
			for (Type sourceOrphanType: sourceModel.getOrphanTypes()) {
				mergeOrphanType(sourceOrphanType);
			}
			for (CompilationUnit cu: sourceModel.getCompilationUnits()) {
				targetModel.getCompilationUnits().add((CompilationUnit)sourceToObject.copy(cu));
			}
		}
		
		void mergeOrphanType(Type sourceOrphanType) {
			String id = sourceOrphanType.getName();
			Type targetOrphanType = orphanTypes.get(id);
			if (targetOrphanType == null) {
				targetOrphanType = (Type)sourceToObject.copy(sourceOrphanType);
				targetModel.getOrphanTypes().add(targetOrphanType);
				orphanTypes.put(id, targetOrphanType);
			} else {
				sourceToObject.put(sourceOrphanType, targetOrphanType);
			}
		}

		void completeMerge() {
			sourceToObject.copyReferences();
		}
		
		String qualifiedName(Package pkg) {
			return pkg == null ? "" : pkg.getName() + ".";
		}
		
		void mergePackage(Package source) {
			String qualifiedName = qualifiedName(source);
			Package targetPackage = packages.get(qualifiedName);
			if (targetPackage == null) {
				targetPackage = (Package)targetMetaModel.getJavaFactory().create(source.eClass());
				targetPackage.setName(source.getName());
				packages.put(qualifiedName, targetPackage);
				if (source.eContainer() instanceof Package) {
					Package targetContainer = (Package)sourceToObject.get(source.eContainer());
					targetContainer.getOwnedPackages().add(targetPackage);
				} else {					
					targetModel.getOwnedElements().add(targetPackage);
				}
			}
			
			sourceToObject.put(source, targetPackage);
			
			for(AbstractTypeDeclaration type: source.getOwnedElements()) {
				mergeType(targetPackage, type);
			}
			
			for(Package subPackage: source.getOwnedPackages()) {
				mergePackage(subPackage);
			}
		}
		
		void mergeType(Package targetPackage, AbstractTypeDeclaration source) {
			AbstractTypeDeclaration target = (AbstractTypeDeclaration)sourceToObject.copy(source);
			targetPackage.getOwnedElements().add(target);
		}
	}

	private class SourceToTargetMetaModelCopier extends EcoreUtil.Copier {		
		private static final long serialVersionUID = 1L;

		@Override
		protected EClass getTarget(EClass eClass) {
			return (EClass)targetMetaModel.getEClassifier(eClass.getName());
		}

		@Override
		protected EStructuralFeature getTarget(EStructuralFeature eStructuralFeature) {
			return getTarget(eStructuralFeature.getEContainingClass()).getEStructuralFeature(eStructuralFeature.getFeatureID());	// TODO IMPORTANT		
		}

//		@Override
//		protected void copyReference(EReference eReference, EObject eObject,
//				EObject copyEObject) {
//			if (eReference != ((JavaPackage)eObject.eClass().getEPackage()).getASTNode_OriginalCompilationUnit()) { 
//				super.copyReference(eReference, eObject, copyEObject);
//			}
//		}
	}
}
