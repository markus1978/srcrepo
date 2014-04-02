package de.hub.srcrepo.emffrag;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;
import org.eclipse.jgit.api.Git;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.fragmentation.FGlobalEventListener;
import de.hub.emffrag.fragmentation.FragmentedModel;
import de.hub.emffrag.fragmentation.IndexBasedIdSemantics.IdBehaviour;
import de.hub.emffrag.fragmentation.NoReferencesIdSemantics;
import de.hub.emffrag.util.Extensions;
import de.hub.srcrepo.JGitUtil;
import de.hub.srcrepo.MoDiscoGitModelImportVisitor;
import de.hub.srcrepo.emffrag.extensions.ExtensionsFactory;
import de.hub.srcrepo.emffrag.extensions.ImportLog;
import de.hub.srcrepo.emffrag.extensions.ImportLogEntry;
import de.hub.srcrepo.emffrag.extensions.ImportLogEntryType;
import de.hub.srcrepo.gitmodel.SourceRepository;
import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelFactory;
import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage;

public class EmfFragImportConfiguration implements JGitUtil.ImportConfiguration {	
	
	final boolean disableIndexes;
	final boolean disableUsages;
	
	public EmfFragImportConfiguration(boolean disableIndexes, boolean disableUsages) {
		super();
		this.disableIndexes = disableIndexes;
		this.disableUsages = disableUsages;
	}

	@Override
	public void configureBefore() {
		EmfFragActivator.instance.useBinaryFragments = true;
	}
	
	private JavaPackage javaPackage = null;

	@Override
	public JavaPackage getJavaPackage() {
		if (javaPackage == null) {
			javaPackage = JavaPackage.eINSTANCE;
			
			if (disableIndexes) {
				TreeIterator<EObject> eAllContents = javaPackage.eAllContents();
				while (eAllContents.hasNext()) {
					EObject next = eAllContents.next();
					if (next instanceof EAnnotation) {
						EAnnotation eAnnotation = (EAnnotation)next;
						String value = eAnnotation.getDetails().get("indexes");
						if (value != null && value.equals("true")) {
							eAnnotation.getDetails().put("indexes", "false");
						}
					}
				}
			}
			
			if (disableUsages) {
				TreeIterator<EObject> eAllContents = javaPackage.eAllContents();
				while (eAllContents.hasNext()) {
					EObject next = eAllContents.next();
					if (next instanceof EReference) {
						EReference reference = (EReference)next;
						if (reference.getName().startsWith("usage")) {
							EReference eOpposite = reference.getEOpposite();
							if (eOpposite != null) {
								System.out.println(reference.getEContainingClass().getName() + "." + reference.getName() + "->" + reference.getEOpposite().getEContainingClass().getName() + "." + reference.getEOpposite().getName());
								eOpposite.setEOpposite(null);
								reference.setEOpposite(null);								
							}						
						}
					}
				}
			}
		}		
		return JavaPackage.eINSTANCE;
	}
	
	@Override
	public GitModelPackage getGitPackage() {
		return GitModelPackage.eINSTANCE;
	}
	
	@Override
	public SourceRepository createSourceRepository() {
		return GitModelFactory.eINSTANCE.createEmfFragSourceRepository();
	}
	
	@Override
	public void configure(Resource model) {
		EmfFragActivator.instance.collectStatistics = true;
		EmfFragActivator.instance.idSemantics = new NoReferencesIdSemantics(IdBehaviour.defaultModel);
		EmfFragActivator.instance.defaultModel = (FragmentedModel)model;
//		EmfFragActivator.instance.globalEventListener = new TelemetryGlobalEvenListener();
//		EmfFragActivator.instance.globalEventListener = new MemoryGlobalEvenListener();
		EmfFragActivator.instance.globalEventListener = FGlobalEventListener.emptyInstance;
	}

	@Override
	public MoDiscoGitModelImportVisitor createMoDiscoGitModelImportVisitor(Git git, SourceRepository gitModel, Model javaModel,
			String lastCommit) {
		return new MoDiscoGitModelImportVisitor(git, gitModel, javaModel, lastCommit) {
			@Override
			protected void reportImportError(EObject owner, String message, Exception e, boolean controlled) {
				super.reportImportError(owner, message, e, controlled);
				
				ImportLog importLog = Extensions.get(owner, ImportLog.class);
				if (importLog == null) {
					importLog = ExtensionsFactory.eINSTANCE.createImportLog();
					Extensions.add(owner, importLog);
				}
				
				ImportLogEntry importLogEntry = ExtensionsFactory.eINSTANCE.createImportLogEntry();
				importLogEntry.setType(controlled ? ImportLogEntryType.FAILED : ImportLogEntryType.UNDEFINED);
				importLogEntry.setMessage(message);
				if (e != null) {
					importLogEntry.setException(e.getClass().getCanonicalName());
					importLogEntry.setExceptionMessage(e.getMessage());
				}
				importLog.getEntries().add(importLogEntry);
			}

//			@Override
//			public void onCompleteCommit(Commit commit) {
//				super.onCompleteCommit(commit);
////				((TelemetryGlobalEvenListener)EmfFragActivator.instance.globalEventListener).printTelemetry();
////				((MemoryGlobalEvenListener)EmfFragActivator.instance.globalEventListener).removeUnessesaryObjects();
//			}	
		};
	}
	
	
}
