package de.hub.srcrepo.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.UnresolvedItem;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement;

import com.google.common.base.Preconditions;

import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.repositorymodel.CompilationUnitModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.Target;
import de.hub.srcrepo.repositorymodel.UnresolvedLink;

@SuppressWarnings("restriction")
public abstract class ImportJavaCompilationUnitsJob extends WorkspaceJob {
	
	private final Collection<ICompilationUnit> compilationUnits;
	private final JavaFactory javaFactory;
	private final RepositoryModelFactory repositoryFactory;
	
	private final Map<ICompilationUnit, CompilationUnitModel> results = new HashMap<>();

	public ImportJavaCompilationUnitsJob(Collection<ICompilationUnit> compilationUnits, 
			JavaFactory javaFactory, 
			RepositoryModelFactory repositoryFactory) {
		super(ImportJavaCompilationUnitsJob.class.getName() + " import compilation units for current ref.");
		this.compilationUnits = compilationUnits;
		this.javaFactory = javaFactory;
		this.repositoryFactory = repositoryFactory;
	}
	
	protected abstract void skipWarning(String message);
	
	protected abstract void skipError(String message, Exception e);
	
	protected void importCompilationUnit(ICompilationUnit compilationUnit) {		
		try {
			long fileSize = EFS.getStore(compilationUnit.getResource().getLocationURI()).fetchInfo().getLength();
			if (fileSize > 300000) { // TODO makes this configurable, add functionality to detect generated files
				skipWarning("Skipped compilation unit " + compilationUnit.getResource().getProjectRelativePath() +
						" because it is awefully large (" + (fileSize/1024) + "kb) and probably generated.");
				return;
			}
		} catch (Exception e) {
			skipError("Could not estimate size of " + compilationUnit.getResource().getProjectRelativePath(), e);
		}
		
		CompilationUnitModel compilationUnitModel = repositoryFactory.createCompilationUnitModel();
		compilationUnitModel.setProjectID(compilationUnit.getJavaProject().getPath().toPortableString());
		
		SrcRepoBindingManager bindings = new SrcRepoBindingManager(javaFactory) {
			@Override
			protected void manageUnresolvedBindings(final Model model1,
					final List<PendingElement> unresolvedBindings) {
				if (model1 != null) {
					for (PendingElement pe : unresolvedBindings) {
						ASTNode source = pe.getClientNode();
						UnresolvedLink unresolvedLink = repositoryFactory.createUnresolvedLink();
						compilationUnitModel.getUnresolvedLinks().add(unresolvedLink);
						unresolvedLink.setId(pe.getBinding().toString());
						unresolvedLink.setSource(source);
						EClass sourceClass = source.eClass();
						EStructuralFeature feature = sourceClass.getEStructuralFeature(pe.getLinkName());
						unresolvedLink.setFeatureID(sourceClass.getFeatureID(feature));
												
						NamedElement target = null;
						target = getProxyElement(pe, model1);
						if (target != null) {
							pe.affectTarget(target);
							Preconditions.checkState(target instanceof UnresolvedItem || target.isProxy());
							unresolvedLink.setTarget(target);
							if (feature.isMany()) {
								unresolvedLink.setFeatureIndex(((List<?>)source.eGet(feature)).indexOf(target));
							} else {
								unresolvedLink.setFeatureIndex(-1);
							}							
						} else {
							// TODO maybe there is a regular case where this happen?
							SrcRepoActivator.INSTANCE.warning("Found an element that could not be resolved, " + 
									"even with proxies or unresolved items: " + pe.getBinding().getName());
						}						
					}
				}
			}
		};
		// TODO reuse javaReader and Discover...
		JavaReader javaReader = new JavaReader(javaFactory, new HashMap<String, Object>(), null);
		javaReader.setDeepAnalysis(true);
		javaReader.setIncremental(false);
		Model javaModel = javaFactory.createModel();
		SrcRepoActivator.INSTANCE.debug("import compilation unit " + compilationUnit.getPath());
		
		try {
			javaReader.readModel(compilationUnit, javaModel, bindings, new NullProgressMonitor());
			// We need to try to resolve the bindings. This will save performance, when snapshots are merged,
			// and more importantly, it will create bindings that result in proxy elements, which will
			// be added to the targets of the binding manager. Those targets are also placed in the java model.
			//bindings.resolveBindings(javaModel);
			javaReader.terminate(new NullProgressMonitor());
		} catch (Exception e) {
			if (e.getClass().getName().endsWith("AbortCompilation")) {
				skipError("Could not compile " + compilationUnit.getResource().getProjectRelativePath() + 
						" (is ignored): " + e.getMessage(), e);
				EcoreUtil.delete(javaModel);
			} else {
				EcoreUtil.delete(javaModel);
				skipError("Could not compile " + compilationUnit.getResource().getProjectRelativePath() +
						" (is ignored) for unknown reasons: " + e.getMessage(), e);
			}
			return;
		}							
		
		if (javaModel.getCompilationUnits().size() == 1) {
			// check if a new top level class was imported (and not only the compilation unit)					
			if (javaModel.getCompilationUnits().get(0).getTypes().isEmpty()) {
				SrcRepoActivator.INSTANCE.warning("A compilation was imported, but no new type created, probably due to parser errors: " + compilationUnit.getPath());						
			}
			
			CompilationUnit importedCompilationUnit = javaModel.getCompilationUnits().get(0);
			compilationUnitModel.setCompilationUnit(importedCompilationUnit);
			compilationUnitModel.setJavaModel(javaModel);
			
			// save targets
			for(Map.Entry<String, NamedElement> target: bindings.getTargets().entrySet()) {
				Target targetModel = repositoryFactory.createTarget();
				targetModel.setId(target.getKey());
				targetModel.setTarget(target.getValue());
				compilationUnitModel.getTargets().add(targetModel);
			}
			
			results.put(compilationUnit, compilationUnitModel);
		} else {
			EcoreUtil.delete(compilationUnitModel);
			EcoreUtil.delete(javaModel);
			skipError("Sucessfully imported a compilation unit, but no model was created: " + compilationUnit, null);
		}
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {									
		// import diffs
		SrcRepoActivator.INSTANCE.info("about to import " + compilationUnits.size() + " compilation units");
		int count = 0;
		for(ICompilationUnit compilationUnit: compilationUnits) {
			importCompilationUnit(compilationUnit);
		}										
			
		SrcRepoActivator.INSTANCE.info("imported " + count + " compilation units");
		return Status.OK_STATUS;
	}	
	
	public Map<ICompilationUnit, CompilationUnitModel> getResults() {
		return results;
	}
}
