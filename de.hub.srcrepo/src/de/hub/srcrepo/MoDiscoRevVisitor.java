package de.hub.srcrepo;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.gmt.modisco.java.internal.util.JavaUtil;

import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.JavaCompilationUnitRef;
import de.hub.srcrepo.repositorymodel.PendingElement;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Target;

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
				Model sourceModel = compilationUnitRef.getJavaModel();
				merge.mergeModel(sourceModel);
			}
		}
		merge.completeMerge();
										
		// load all targets for this rev
		final Map<String, NamedElement> targets = new HashMap<String, NamedElement>();
		for (AbstractFileRef ref: files.values()) {
			if (ref instanceof JavaCompilationUnitRef) {
				JavaCompilationUnitRef compilationUnitRef = (JavaCompilationUnitRef)ref;
				for (Target target: compilationUnitRef.getTargets()) {
					NamedElement targetElement = (NamedElement)merge.sourceToObject.get(target.getTarget());
					if (targetElement == null) {
						throw new NullPointerException();
					}
					targets.put(target.getId(), targetElement);
				}
			}
		}
		
		// resolve the pending elements of all compilation units
		for (AbstractFileRef ref: files.values()) {
			if (ref instanceof JavaCompilationUnitRef) {
				JavaCompilationUnitRef compilationUnitRef = (JavaCompilationUnitRef)ref;
				for(PendingElement pendingElementModel: compilationUnitRef.getPendings()) {
					org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement pendingElement = 
							new org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement(targetMetaModel.getJavaFactory());
					pendingElement.setClientNode((ASTNode)merge.sourceToObject.get(pendingElementModel.getClientNode()));
					pendingElement.setLinkName(pendingElementModel.getLinkName());
					
					String id = pendingElementModel.getBinding();
					NamedElement target = targets.get(id);
					
					if (target == null) {
						target = JavaUtil.getNamedElementByQualifiedName(targetModel, id, targets);
					}
					
					if (target != null) {
						pendingElement.affectTarget(target);
					} else {
						pendingElement.affectUnresolvedTarget();
					}
				}
			}
		}
		
		// do
		onRev(targetModel);
		
		// remove the old model
		EcoreUtil.delete(targetModel);
	}
	
	protected boolean filter(Rev rev) {
		return true;
	}
	
	protected abstract void onRev(Model model);
	
	private class JavaModelMerge {
		final SourceToTargetMetaModelCopier sourceToObject = new SourceToTargetMetaModelCopier();
		final Model targetModel;
		final Map<String, Type> orphanTypes = new HashMap<String, Type>();
		
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
		
		void mergePackage(Package source) {
			Package targetPackage = (Package)sourceToObject.get(source);
			if (targetPackage == null) {
				targetPackage = (Package)targetMetaModel.getJavaFactory().create(source.eClass());
				targetPackage.setName(source.getName());
				sourceToObject.put(source, targetPackage);
				if (source.eContainer() instanceof Package) {
					Package targetContainer = (Package)sourceToObject.get(source.eContainer());
					targetContainer.getOwnedPackages().add(targetPackage);
				} else {					
					targetModel.getOwnedElements().add(targetPackage);
				}
			}
			
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
			return getTarget(eStructuralFeature.getEContainingClass()).getEStructuralFeature(eStructuralFeature.getFeatureID());			
		}

		@Override
		protected void copyReference(EReference eReference, EObject eObject,
				EObject copyEObject) {
			if (eReference != ((JavaPackage)eObject.eClass().getEPackage()).getASTNode_OriginalCompilationUnit()) { 
				super.copyReference(eReference, eObject, copyEObject);
			}
		}
	}
}
