package de.hub.srcrepo.emffrag;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;
import org.eclipse.jgit.api.Git;

import de.hub.emffrag.EmfFragActivator;
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
	
	@Override
	public void configureBefore() {
		EmfFragActivator.instance.useBinaryFragments = true;
	}

	@Override
	public JavaPackage getJavaPackage() {
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
		};
	}
	
	
}
