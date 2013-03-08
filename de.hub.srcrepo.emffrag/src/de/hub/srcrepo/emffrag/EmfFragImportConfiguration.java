package de.hub.srcrepo.emffrag;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.EmfFragActivator.IdBehaviour;
import de.hub.emffrag.EmfFragActivator.IndexedValueSetBahaviour;
import de.hub.emffrag.fragmentation.FragmentedModel;
import de.hub.srcrepo.JGitUtil;
import de.hub.srcrepo.gitmodel.SourceRepository;
import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelFactory;
import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage;

public class EmfFragImportConfiguration implements JGitUtil.ImportConfiguration {		
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
		EmfFragActivator.instance.indexedValueSetBahaviour = IndexedValueSetBahaviour.neverContains;
		EmfFragActivator.instance.idBehaviour = IdBehaviour.defaultModel;
		EmfFragActivator.instance.defaultModelForIdBehavior = (FragmentedModel)model;
	}
}
