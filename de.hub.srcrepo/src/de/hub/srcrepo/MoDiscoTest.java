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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.gmt.modisco.java.emffrag.impl.JavaFactoryImpl;
import org.eclipse.gmt.modisco.java.emffrag.impl.JavaPackageImpl;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jgit.api.Git;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.DiscoverJavaModelFromClassFile;
import org.eclipse.modisco.java.discoverer.DiscoverJavaModelFromJavaProject;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.kdm.source.extension.discovery.AbstractRegionDiscoverer2;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.junit.Test;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.fragmentation.FragmentedModel;
import de.hub.emffrag.fragmentation.ReflectiveMetaModelRegistry;
import de.hub.srcrepo.gitmodel.GitModelFactory;
import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.SourceRepository;
import de.hub.srcrepo.gitmodel.util.GitModelUtil;
import de.hub.srcrepo.gitmodel.util.GitModelUtil.Direction;

public class MoDiscoTest implements IApplication {
	
	@Test
	public void testImportJavaGitModel() throws Exception {		
		// load dependencies
		EmfFragActivator.class.getName();
		
		// create a mode resource and root elments for the git and java models
		JavaPackage originalJavaPackage = JavaPackage.eINSTANCE;
		JavaPackage javaPackage = JavaPackageImpl.init();	
		JavaFactory javaFactory = JavaFactoryImpl.init();
		((EPackageImpl)javaPackage).setEFactoryInstance(javaFactory);
		EPackage.Registry.INSTANCE.put(JavaPackage.eINSTANCE.getNsURI(), javaPackage);
		ReflectiveMetaModelRegistry.instance.registerUserMetaModel(GitModelPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(JavaPackage.eINSTANCE.getNsURI(), originalJavaPackage);
		
		Assert.assertTrue("Wrong instance.", javaPackage instanceof JavaPackageImpl);
		Assert.assertTrue("Wrong instance.", javaFactory instanceof JavaFactoryImpl);
		
		ResourceSet rs = new ResourceSetImpl();
		FragmentedModel model = (FragmentedModel)rs.createResource(URI.createURI("memory://localhost/example.java.modiscogitmodel"));
		SourceRepository gitModel = GitModelFactory.eINSTANCE.createSourceRepository();
		Model javaModel = javaFactory.createModel();
		model.root().getContents().add(gitModel);
		model.root().getContents().add(javaModel);
		
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
		
		System.out.println(model.getDataStore());
	}
	
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
	    IJavaProject javaProject = null;
	    
	    Assert.assertNotNull("The example project is not there", project);	
	    if (!project.exists()) {
	    	try {
				javaProject = importExistingExampleJavaProject();
				project = javaProject.getProject();
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Could not import example project.");
			}
	    } else {
	    	javaProject = JavaCore.create(project);
	    }
		
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
	
	private IJavaProject importExistingExampleJavaProject() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String path = workspace.getRoot().getLocationURI().toURL().getFile() + "/../02_workspace/de.hub.srcrepo/example.git/example.java/";
		return importExistingJavaProject(path);
	}
	
	private IJavaProject importExistingJavaProject(String path) throws Exception {		
		IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path(path + "/.project")); 
	    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
	    project.create(description, null);
	    project.open(null);

	    IJavaProject javaProject = JavaCore.create(project);
	    if (!javaProject.isOpen()) {
			javaProject.open(new NullProgressMonitor());
	    				
		}
		return javaProject;
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
