package de.hub.srcrepo;

import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.junit.Before;
import org.junit.Test;

import de.hub.srcrepo.gitmodel.GitModelFactory;
import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.SourceRepository;

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
	
	protected void afterImport() {
		
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
				return GitModelFactory.eINSTANCE.createSourceRepository();
			}
			
			@Override
			public void configure(Resource model) {
				
			}
		};
		return config;
	}
	
	@Test
	public void testImportJavaGitModel() throws Exception {					
		init();
		try {
			model = JGitUtil.importGit("https://github.com/markus1978/srcrepo.example.git", "../../../01_tmp/srcrepo/clones/srcrepo.example.git", 
					getTestSourceModelURI(), createImportConfiguration());
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		afterImport();
	}
	
	@Test
	public void testUseImportedJavaGitModel() throws Exception {
		init();		
		
		ResourceSet rs = new ResourceSetImpl();
		model = rs.getResource(getTestSourceModelURI(), true);
		createImportConfiguration().configure(model);
		
		Assert.assertEquals(2, model.getContents().size());
		
		SourceRepository sourceRepository = (SourceRepository)model.getContents().get(0);
		System.out.println("##: " + new ScalaTest().countJavaTypeDefs(sourceRepository));
		System.out.println("##: " + new ScalaTest().traverseJavaModel((Model)model.getContents().get(1)));
		System.out.println("##: " + new ScalaTest().traverseJavaModelViaCU((Model)model.getContents().get(1)));
		System.out.println("##: " + new ScalaTest().traversePrimitives((Model)model.getContents().get(1)));
	}
}
