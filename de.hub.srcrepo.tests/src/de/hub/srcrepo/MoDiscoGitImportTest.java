package de.hub.srcrepo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
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
import org.junit.Before;
import org.junit.Test;

import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.RepositoryModelTraversal.Stats;
import de.hub.srcrepo.ocl.OclUtil;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.util.RepositoryModelUtil;

public class MoDiscoGitImportTest {
	
	public final static URI testModelURI = URI.createURI("test-models/example.java.xmi");
	public final static File workingCopy = new File("c:/tmp/srcrepo/clones/srcrepo.example.git");
	
	protected URI getTestModelURI() {
		return testModelURI;
	}
	
	protected File getWorkingCopy() {
		return workingCopy;
	}
	
	protected boolean onlyCloneIfNecessary() {
		return false;
	}
	
	protected String getCloneURL() {
		return "git://github.com/markus1978/srcrepo.example.git";
	}
	
	@Before 
	public void initialize() {
		EPackage.Registry.INSTANCE.put(RepositoryModelPackage.eINSTANCE.getNsURI(), RepositoryModelPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("gitmodel", new XMIResourceFactoryImpl());
	}
	
	@Test
	public void testClone() {
		try {
			GitSourceControlSystem scs = new GitSourceControlSystem();
			scs.createWorkingCopy(getWorkingCopy(), getCloneURL(), onlyCloneIfNecessary());
			scs.close();
		} catch (SourceControlException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
	}
	
	protected void runImport() {
		GitSourceControlSystem scs = new GitSourceControlSystem();
		try {			
			scs.createWorkingCopy(getWorkingCopy(), getCloneURL(), true);	
		} catch (SourceControlException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		
		ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.createResource(getTestModelURI());
		RepositoryModel repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel();
		resource.getContents().add(repositoryModel);
		
		try {
			scs.importRevisions(repositoryModel);
			IRepositoryModelVisitor visitor = createVisitor(scs, repositoryModel);
			RepositoryModelTraversal.traverse(repositoryModel, visitor);
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
	}
	
	@Test
	public void modelImportTest() {	
		// run import
		runImport();
		
		// assert results
		ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.getResource(getTestModelURI(), true);
		RepositoryModel repositoryModel = (RepositoryModel)resource.getContents().get(0);
				
		Collection<String> revNames = assertRepositoryModel(repositoryModel);
		
		final OclUtil scalaTest = new OclUtil();
		System.out.println("Java diffs: " + scalaTest.coutJavaDiffs(repositoryModel));
		
		for(Rev root: repositoryModel.getRootRevs()) {
			Assert.assertTrue("Root revision isn't root.", RepositoryModelUtil.isRoot(root));
		}
		
		final Collection<String> visitedRevNames = new HashSet<String>();
		final PrintStream out = new PrintStream(new ByteArrayOutputStream()); // write to nothing
		final Collection<String> rootNames = new HashSet<String>();
		//final PrintStream out = System.out;
		Stats stats = RepositoryModelTraversal.traverse(repositoryModel, new MoDiscoRevVisitor(JavaPackage.eINSTANCE) {			
			@Override
			protected void onRev(Rev rev, Model model) {				
				try {
					Assert.assertTrue("Revs should not be visited twice", visitedRevNames.add(rev.getName()));
					
					if (RepositoryModelUtil.isRoot(rev)) {
						rootNames.add(rev.getName());
					}
					
					out.println(rev.getName());
					TreeIterator<EObject> i = model.eAllContents();
					while(i.hasNext()) {
						EObject next = i.next();
						if (next instanceof AbstractTypeDeclaration) {
							out.println("Class: " + ((AbstractTypeDeclaration)next).getName());
						}
					}
													
					out.println("Primitives: " + scalaTest.countPrimitives(model));
					out.println("Top level classes: " + scalaTest.countTopLevelClasses(model));
					out.println("Methods: " + scalaTest.countMethodDeclarations(model));
					out.println("Type usages: " + scalaTest.countTypeUsages(model));
					out.println("Methods wo body: " + scalaTest.nullMethod(model));
					out.println("McCabe: " + scalaTest.mcCabeMetric(model));
				} catch (Exception e) {
					Assert.fail(e.getMessage());
				}
			}
		});				
		
		final Collection<String> revNamesDiff = new HashSet<String>();
		for(String name: revNames) {
			if (!visitedRevNames.contains(name)) {
				revNamesDiff.add(name);
				System.out.print("not visited: " + name);
				Rev rev = RepositoryModelUtil.getRev(repositoryModel, name);
				System.out.println(" " + RepositoryModelUtil.isRoot(rev));
			}
		}
		
		Assert.assertEquals("Branches and merges do not match.", stats.mergeCounter + stats.openBranchCounter, stats.branchCounter);
		Assert.assertEquals("Not all revisions are reached by traversal.", revNames.size(), visitedRevNames.size());
	}

	protected Collection<String> assertRepositoryModel(RepositoryModel repositoryModel) {
		// revs are unique
		Collection<String> revNames = new HashSet<String>();
		Collection<Rev> revs = new HashSet<Rev>();
		for(Rev rev: repositoryModel.getAllRevs()) {
			Assert.assertTrue(revNames.add(rev.getName()));
			Assert.assertTrue(revs.add(rev));
		}
		
		// all refs point to stored revs
		for(de.hub.srcrepo.repositorymodel.Ref ref: repositoryModel.getAllRefs()) {
			Assert.assertTrue(revNames.contains(ref.getReferencedCommit().getName()));
			Assert.assertTrue(revs.contains(ref.getReferencedCommit()));
		}
		
		return revNames;
	}

	protected IRepositoryModelVisitor createVisitor(
			GitSourceControlSystem scs, RepositoryModel repositoryModel) {
		MoDiscoRepositoryModelImportVisitor visitor = new MoDiscoRepositoryModelImportVisitor(scs, repositoryModel, JavaPackage.eINSTANCE);
		return visitor;
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