package de.hub.srcrepo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.junit.Test;

import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.ocl.OclUtil;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;

public class SrcRepoGitTest {
	
	protected void loadDependencies() {

	}
	
	@Test
	public void testClone() {
		try {
			GitSourceControlSystem scs = new GitSourceControlSystem();
			scs.createWorkingCopy(new File("c:/tmp/srcrepo/clones/srcrepo.example.git"), "git://github.com/markus1978/srcrepo.example.git");
			scs.close();
		} catch (SourceControlException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
	}
	
	@Test
	public void modelImportTest() {
		EPackage.Registry.INSTANCE.put(RepositoryModelPackage.eINSTANCE.getNsURI(), RepositoryModelPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("gitmodel", new XMIResourceFactoryImpl());
		
		GitSourceControlSystem scs = new GitSourceControlSystem();
		try {
			File workingCopy = new File("c:/tmp/srcrepo/clones/srcrepo.example.git");
			if (workingCopy.exists()) {
				scs.setWorkingCopy(workingCopy);
			} else {
				scs.createWorkingCopy(new File("c:/tmp/srcrepo/clones/srcrepo.example.git"), "git://github.com/markus1978/srcrepo.example.git");	
			}
		} catch (SourceControlException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.createResource(URI.createURI("models/example.java.gitmodel"));
		RepositoryModel repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel();
		resource.getContents().add(repositoryModel);
		
		try {
			scs.importRevisions(repositoryModel);
			RepositoryModelTraversal.traverse(repositoryModel, new MoDiscoRepositoryModelImportVisitor(scs, repositoryModel, JavaPackage.eINSTANCE));
			scs.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		repositoryModel = (RepositoryModel)resource.getContents().get(0);
		
		final OclUtil scalaTest = new OclUtil();
		System.out.println("Java diffs: " + scalaTest.coutJavaDiffs(repositoryModel));
		
		RepositoryModelTraversal.traverse(repositoryModel, new MoDiscoRevVisitor(JavaPackage.eINSTANCE) {			
			@Override
			protected void onRev(Model model) {
				System.out.println("-----------------------------------------");
				TreeIterator<EObject> i = model.eAllContents();
				while(i.hasNext()) {
					EObject next = i.next();
					if (next instanceof AbstractTypeDeclaration) {
						System.out.println("Class: " + ((AbstractTypeDeclaration)next).getName());
					}
				}
												
				System.out.println("Primitives: " + scalaTest.countPrimitives(model));
				System.out.println("Top level classes: " + scalaTest.countTopLevelClasses(model));
				System.out.println("Methods: " + scalaTest.countMethodDeclarations(model));
				System.out.println("Type usages: " + scalaTest.countTypeUsages(model));
				System.out.println("Methods wo body: " + scalaTest.nullMethod(model));
				System.out.println("McCabe: " + scalaTest.mcCabeMetric(model));
			}
		});				
	}

	@Test
	public void logTest() throws Exception {
		File file = new File("example.git");
		Git git = Git.init().setDirectory(file).call();
		RevWalk rw = new RevWalk(git.getRepository());
		
		DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(git.getRepository());
		df.setDiffComparator(RawTextComparator.DEFAULT);
		df.setDetectRenames(true);
		
		for (Ref ref: git.getRepository().getAllRefs().values()) {
			try {
				System.out.println("# " + ref.getName() + " peeled:" + ref.isPeeled() + " symbolic:" + ref.isSymbolic() + " id:" + rw.parseAny(ref.getObjectId()).getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!ref.isSymbolic()) {
				RevObject object = null;
				try {
					object = rw.parseAny(ref.getObjectId());
				} catch (Exception e) {
					e.printStackTrace();
					Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
				} 
				if (object.getType() == Constants.OBJ_COMMIT) {
					RevCommit commit = (RevCommit)object;
					System.out.println("# " + commit.getFullMessage() + " id:" + commit.getName());			
					List<DiffEntry> diffs = null;
					if (commit.getParentCount() > 0) {						
						RevCommit parent = commit.getParent(0);
						try {
							diffs = df.scan(parent.getTree(), commit.getTree());
						} catch (IOException e) {
							e.printStackTrace();
							Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
						}				
					} else {
						try {
							diffs = df.scan(new EmptyTreeIterator(), new CanonicalTreeParser(null, rw.getObjectReader(), commit.getTree()));
						} catch (Exception e) {
							e.printStackTrace();
							Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
						}
					}
					
					for (DiffEntry diff : diffs) {
						System.out.println(
								"changeType=" + diff.getChangeType().name() + 
								" newMode=" + diff.getNewMode().getBits() + 
								" newPath=" + diff.getNewPath() + 
								" id=" + commit.getName());
					}
				}
			}
		}
	}
}
