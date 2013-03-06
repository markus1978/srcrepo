package de.hub.srcrepo;

import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jgit.api.Git;
import org.junit.Before;
import org.junit.Test;

import de.hub.srcrepo.gitmodel.GitModelFactory;
import de.hub.srcrepo.gitmodel.SourceRepository;
import de.hub.srcrepo.gitmodel.util.GitModelUtil;
import de.hub.srcrepo.gitmodel.util.GitModelUtil.Direction;

public class SrcRepoTest {
	
	protected Resource model;
	
	protected URI getTestSourceModelURI() {
		return URI.createURI("models/example.java.gitmodel");
	}
	
	@Before
	public void init() {
		loadDependencies();
	}
	
	protected void loadDependencies() {
		
	}
	
	protected void configure() {
		
	}
	
	protected Resource createResource() {
		ResourceSet rs = new ResourceSetImpl();
		return rs.createResource(getTestSourceModelURI());
	}
	
	protected Resource loadResource() {
		ResourceSet rs = new ResourceSetImpl();
		return rs.getResource(getTestSourceModelURI(), true);
	}
	
	protected GitModelFactory gitFactory() {
		return GitModelFactory.eINSTANCE;
	}
	
	protected JavaFactory javaFactory() {
		return JavaFactory.eINSTANCE;
	}
	
	protected SourceRepository createSourceRepository() {
		return gitFactory().createSourceRepository();
	}
	
	protected void afterImport() {
		
	}
	
	@Test
	public void testImportJavaGitModel() throws Exception {				
		init();
		
		model = createResource();
		configure();
		
		SourceRepository gitModel = createSourceRepository();
		Model javaModel = javaFactory().createModel();
		model.getContents().add(gitModel);
		model.getContents().add(javaModel);
		
		// create git and clone repository
		Git git = null;
		try {
			git = JGitUtil.clone("https://github.com/markus1978/srcrepo.example.git", "../../../01_tmp/srcrepo/clones/srcrepo.example.git");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		// import the git commit structure
		JGitModelImport modelImport = new JGitModelImport(git, gitModel);
		try {
			modelImport.runImport();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		// visit the git commits and import java on the fly		
		MoDiscoGitModelImportVisitor visitor = new MoDiscoGitModelImportVisitor(git, javaModel);
		GitModelUtil.visitCommitHierarchy(gitModel.getRootCommit(), Direction.FROM_PARENT, visitor);
		
		// save the resulting model in its resource
		model.save(null);
		afterImport();
	}
	
	@Test
	public void testUseImportedJavaGitModel() throws Exception {
		init();		
		
		model = loadResource();
		configure();
		
		Assert.assertEquals(2, model.getContents().size());
		
		SourceRepository sourceRepository = (SourceRepository)model.getContents().get(0);
		System.out.println("##: " + new ScalaTest().countJavaTypeDefs(sourceRepository));
	}
	
	
}
