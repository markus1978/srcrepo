package de.hub.srcrepo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.DiscoverJavaModelFromClassFile;
import org.eclipse.modisco.java.discoverer.DiscoverJavaModelFromJavaProject;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.kdm.source.extension.discovery.AbstractRegionDiscoverer2;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.junit.Test;

public class MoDiscoTest implements IApplication {
	
	@Test
	public void testJavaProjectDiscoverer() {		
		IJavaProject javaProject = getExampleJavaProject();
		
		DiscoverJavaModelFromJavaProject discoverer = new DiscoverJavaModelFromJavaProject(); 
		try {
			discoverer.discoverElement(javaProject, new NullProgressMonitor());
		} catch (DiscoveryException e) {			
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		} 
		Resource javaResource = discoverer.getTargetModel();
		
		EList<EObject> contents = javaResource.getContents();
		Assert.assertTrue("No elements in project model", !contents.isEmpty());
		for (EObject obj: contents) {
			Assert.assertTrue("The example project does not cointain the Hello World class", new ScalaTest().checkClassExists((Model)obj, "HelloWorld"));
			Assert.assertFalse("A class exists that should not", new ScalaTest().checkClassExists((Model)obj, "NoClass"));
			Assert.assertTrue(new ScalaTest().selectType((Model)obj, "HelloWorld").getName().equals("HelloWorld"));
		}				
	}
	
	@Test
	public void testJavaReader() {
		JavaReader javaReader = new JavaReader(JavaFactory.eINSTANCE, new HashMap<String,Object>(), new AbstractRegionDiscoverer2<Object>() {
			@Override
			public boolean isApplicableTo(Object source) {			
				return false;
			}

			@Override
			protected void basicDiscoverElement(Object source, IProgressMonitor monitor) throws DiscoveryException {
				throw new UnsupportedOperationException();
			}});
		javaReader.setDeepAnalysis(true);
		javaReader.setIncremental(true);
		
		Model javaModel = JavaFactory.eINSTANCE.createModel();		
		ResourceSet rs = new ResourceSetImpl();
		Resource resource = rs.createResource(URI.createURI("models/example.java.javamodel"));
		resource.getContents().add(javaModel);
		
		IJavaProject javaProject = getExampleJavaProject();
		ICompilationUnit cu = null;
		try {
			IPackageFragment[] packageFragments = javaProject.getPackageFragments();
			
			for (int i = 0; i < packageFragments.length; i++) {
				ICompilationUnit[] cus = packageFragments[i].getCompilationUnits();
				for (int j = 0; j < cus.length; j++) {
					cu = cus[j];
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		Assert.assertNotNull("There is no CU in the example project.", cu);
			
		javaReader.readModel(cu, javaModel, new NullProgressMonitor());
		
		TreeIterator<EObject> eAllContents = javaModel.eAllContents();
		while (eAllContents.hasNext()) {
			System.out.println("# " + eAllContents.next());
		}
		
		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJavaClassDiscoverer() {		
		IJavaProject javaProject = getExampleJavaProject();
		IClassFile classFile = null;
		try {
			IPackageFragment[] packageFragments = javaProject.getPackageFragments();
			
			for (int i = 0; i < packageFragments.length; i++) {
				IClassFile[] classFiles = packageFragments[i].getClassFiles();
				for (int j = 0; j < classFiles.length; j++) {
					classFile = classFiles[j];
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		Assert.assertNotNull("There is not class file in the example project.", classFile);
		
		DiscoverJavaModelFromClassFile discoverer = new DiscoverJavaModelFromClassFile(); 
		try {
			discoverer.discoverElement(classFile, new NullProgressMonitor());
		} catch (DiscoveryException e) {			
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		} 
		Resource javaResource = discoverer.getTargetModel();
		
		Assert.assertTrue("No elements in java model", !javaResource.getContents().isEmpty());
		TreeIterator<EObject> allContents = javaResource.getAllContents();
		
		while (allContents.hasNext()) {
			EObject next = allContents.next();
			System.out.println("#: " + next.eClass().getName());
		}
	}

	private IJavaProject getExampleJavaProject() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
	    IWorkspaceRoot root = workspace.getRoot();
	    IProject project = root.getProject("example.java");
	    
	    Assert.assertNotNull("The example project is not there", project);	
	    if (!project.exists()) {
	    	try {
				project = importExistingExampleJavaProject();
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Could not import example project.");
			}
	    }
	    IJavaProject javaProject = JavaCore.create(project);	
		
		if (!javaProject.isOpen()) {
			try {
				javaProject.open(new NullProgressMonitor());
			} catch (JavaModelException e) {
				e.printStackTrace();
				Assert.fail("Could not open java project.");
			}
		}
		Assert.assertTrue("Example project is closed.", javaProject.isOpen());
		return javaProject;
	}
	
	private IProject importExistingExampleJavaProject() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String file = workspace.getRoot().getLocationURI().toURL().getFile() + "/../02_workspace/de.hub.srcrepo/example.git/example.java/";
		IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path(file + "/.project")); 
	    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
	    project.create(description, null);
	    project.open(null);
	    return project;
	}
	
	@SuppressWarnings("unused")
	private IProject importExampleJavaProject() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProjectDescription newProjectDescription = workspace.newProjectDescription("example.java");
		IProject importedProject = workspace.getRoot().getProject("example.java");
		importedProject.create(newProjectDescription, null);
		importedProject.open(null);

		IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
		    public String queryOverwrite(String file) { return ALL; }
		};
		
		
		File file = new File(new Path(workspace.getRoot().getLocationURI().toURL().getFile() + "/../02_workspace/de.hub.srcrepo/example.git/example.java/").toString());
		FileSystemStructureProvider provider = FileSystemStructureProvider.INSTANCE;
		
		ImportOperation importOperation = new ImportOperation(
				importedProject.getFullPath(), 
				file, 
				provider, 
				overwriteQuery);		
		importOperation.setCreateContainerStructure(false);
		importOperation.run(new NullProgressMonitor());
		
		return importedProject;
	}
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("# Start.");
		
		return EXIT_OK;
	}

	@Override
	public void stop() {
		
	}
}
