package de.hub.srcrepo

import de.hub.srcrepo.internal.ImportJavaCompilationUnitsJob
import de.hub.srcrepo.repositorymodel.CompilationUnitModel
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gmt.modisco.java.emf.JavaFactory
import org.eclipse.jdt.core.IClasspathEntry
import org.eclipse.jdt.core.ICompilationUnit
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.ui.wizards.JavaCapabilityConfigurationPage

import static org.junit.Assert.*

class ModiscoSnapshotTestUtils {
	val JavaFactory javaFactory
	val RepositoryModelFactory repositoryModelFactory
	
	new(JavaFactory javaFactory, RepositoryModelFactory repositoryModelFactory) {
		this.javaFactory = javaFactory
		this.repositoryModelFactory = repositoryModelFactory
	}
	
	private def IJavaProject openProject(String projectPath) {
		val projectDescriptionFile = new Path(projectPath + "/.project");
		val description = ResourcesPlugin.getWorkspace().loadProjectDescription(projectDescriptionFile); 
	    var project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
	    JavaCapabilityConfigurationPage.createProject(project, description.getLocationURI(), null);
	    val javaProject = JavaCore.create(project)
	    
	    // add src-testdata as source folder
	    val entries = newArrayList(javaProject.getRawClasspath())
	    if (!entries.exists[path.toString.endsWith("src-testdata")]) {
		    val newEntry = JavaCore.newSourceEntry(new Path("/de.hub.srcrepo.tests/src-testdata"))
			entries += newEntry
		    val IClasspathEntry[] entryArray = entries
		    javaProject.setRawClasspath(entryArray, null);		    
	    }
	    
	    return javaProject
	}
	
	private def void closeProject(IJavaProject javaProject) {
		val entries = newArrayList(javaProject.getRawClasspath())
		val testEntry = entries.findFirst[path.toString.endsWith("src-testdata")]
	    if (testEntry != null) {
			entries -= testEntry
		    val IClasspathEntry[] entryArray = entries
		    javaProject.setRawClasspath(entryArray, null);		    
	    }
	}
	
	def CompilationUnitModel createCompilationUnitModel(String compilationUnitPath, String xmiPath, boolean standalone) {
		val cumPath = compilationUnitPath
		if (standalone) {
			val rs = new ResourceSetImpl();
			val xmiResource = rs.getResource(URI.createURI(xmiPath), true)
			return xmiResource.contents.get(0) as CompilationUnitModel
		} else {
			val javaProject = openProject("/Users/markus/Documents/Projects/srcrepo-mars/03-git/srcrepo/de.hub.srcrepo.tests")
			
			val resource = javaProject.getProject().findMember(cumPath)
			val element = JavaCore.create(resource, javaProject)
			val cu = element as ICompilationUnit
			
			assertNotNull(cu)
			
			val importJob = new ImportJavaCompilationUnitsJob(newArrayList(cu), javaFactory, repositoryModelFactory) {			
				override protected skipError(String message, Exception e) {
					fail('''«message» («e.message»)''')
				}
				
				override protected skipWarning(String message) {
					fail(message)
				}			
			}
			
			importJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
			importJob.schedule();
			importJob.join();
			if (importJob.result == null || !importJob.result.isOK()) {
				fail("Could not run import job successfully.")
			}
			
			val cum = importJob.results.get(cu)
			javaProject.closeProject
			assertNotNull(cum)
			
			val rs = new ResourceSetImpl();
			val xmiResource = rs.createResource(URI.createURI(xmiPath))
			xmiResource.contents += cum
			xmiResource.save(null)			
			
			return cum
		}											
	}
}