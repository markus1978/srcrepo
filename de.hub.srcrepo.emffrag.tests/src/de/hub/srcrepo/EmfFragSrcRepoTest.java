package de.hub.srcrepo;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.junit.Before;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.EmfFragActivator.IdBehaviour;
import de.hub.emffrag.EmfFragActivator.IndexedValueSetBahaviour;
import de.hub.emffrag.fragmentation.FragmentedModel;
import de.hub.srcrepo.gitmodel.SourceRepository;
import de.hub.srcrepo.gitmodel.emffrag.metadata.GitModelFactory;

public class EmfFragSrcRepoTest extends SrcRepoTest {
	
	protected URI getTestSourceModelURI() {
		return URI.createURI("memory://models/example.java.gitmodel");
	}		
	
	@Override
	protected void loadDependencies() {
		super.loadDependencies();
		EmfFragActivator.class.getName();
	}
	
	@Override
	protected GitModelFactory gitFactory() {
		return GitModelFactory.eINSTANCE;
	}

	@Override
	protected JavaFactory javaFactory() {
		return org.eclipse.gmt.modisco.java.emffrag.metadata.JavaFactory.eINSTANCE;
	}

	@Override
	protected SourceRepository createSourceRepository() {
		return gitFactory().createEmfFragSourceRepository();
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
		EmfFragActivator.instance.idBehaviour = IdBehaviour.defaultModel;
	}

	@Override
	protected void configure() {
		super.configure();
		EmfFragActivator.instance.defaultModelForIdBehavior = (FragmentedModel)model;
	}

	@Override
	protected void afterImport() {
		System.out.println(((FragmentedModel)model).getDataStore());
	}
	
}
