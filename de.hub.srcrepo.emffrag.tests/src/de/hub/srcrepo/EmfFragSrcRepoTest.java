package de.hub.srcrepo;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;
import org.junit.Before;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.EmfFragActivator.IndexedValueSetBahaviour;
import de.hub.emffrag.fragmentation.FragmentedModel;
import de.hub.emffrag.fragmentation.IndexBasedIdSemantics.IdBehaviour;
import de.hub.emffrag.fragmentation.NoReferencesIdSemantics;
import de.hub.srcrepo.gitmodel.SourceRepository;
import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelFactory;
import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelPackage;

public class EmfFragSrcRepoTest extends SrcRepoTest {
	
	protected URI getTestSourceModelURI() {
		return URI.createURI("memory://models/example.java.gitmodel");
	}		
	
	@Override
	protected void loadDependencies() {
		super.loadDependencies();
		EmfFragActivator.class.getName();
	}
	
	protected JGitUtil.ImportConfiguration createImportConfiguration() {
		JGitUtil.ImportConfiguration config = new JGitUtil.ImportConfiguration() {			
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
				EmfFragActivator.instance.defaultModel = (FragmentedModel)model;
			}
		};
		return config;
	}
	
	protected boolean useBinaryFragments() {
		return false;
	}	
	
	@Before
	@Override
	public void init() {
		super.init();
		EmfFragActivator.instance.useBinaryFragments = useBinaryFragments();
		EmfFragActivator.instance.indexedValueSetBahaviour = IndexedValueSetBahaviour.neverContains;
		EmfFragActivator.instance.idSemantics = 
				//new IndexBasedIdSemantics(IdBehaviour.defaultModel);
				new NoReferencesIdSemantics(IdBehaviour.defaultModel);
	}

	@Override
	protected void afterImport() {
		System.out.println(((FragmentedModel)model).getDataStore());
	}
	
}
