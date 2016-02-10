package de.hub.srcrepo;

import static de.hub.srcrepo.RepositoryModelUtil.getImportMetaData;
import static de.hub.srcrepo.RepositoryModelUtil.getMetaData;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;

public class MoDiscoGitImportTest {
	
	public enum TestModelKind { GIT, JAVA };
	public final static URI testJavaModelURI = URI.createURI("testdata/models/example.java.xmi");
	public final static URI testGitModelURI = URI.createURI("testdata/models/example.git.xmi");
	public final static File workingCopy = new File(SrcRepoTestSuite.workingCopiesPrefix + "srcrepo.example.git");
	
	private static boolean isStandalone = false;
	
	protected URI getTestModelURI(TestModelKind kind) {
		if (kind == TestModelKind.GIT) {
			return testGitModelURI;
		} else {
			return testJavaModelURI;
		}
	}
	
	protected File getWorkingCopy() {
		return workingCopy;
	}
	
	protected boolean onlyCloneIfNecessary() {
		return true;
	}
	
	protected String getCloneURL() {
		return "git://github.com/markus1978/srcrepo.example.git";
	}
	
	@BeforeClass
	public static void standaloneSrcRepo() {
		if (SrcRepoActivator.INSTANCE == null) {	
			SrcRepoActivator.standalone();
			isStandalone = true;
		}
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
	
	protected RepositoryModel openRepositoryModel(TestModelKind kind, boolean dropExisting) {
		ResourceSet rs = new ResourceSetImpl();
		Resource resource = null;
		if (new File(getTestModelURI(kind).toFileString()).exists()) {
			resource = rs.getResource(getTestModelURI(kind), true);
			if (dropExisting) {
				resource.getContents().clear();
				RepositoryModel repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel();
				resource.getContents().add(repositoryModel);
			}
		} else {
			resource = rs.createResource(getTestModelURI(kind));
			RepositoryModel repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel();
			resource.getContents().add(repositoryModel);
		}
		return (RepositoryModel)resource.getContents().get(0);
	}
	
	protected void closeRepositoryModel(TestModelKind kind, RepositoryModel model) {
		try {
			model.eResource().save(null);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
	}
	
	protected GitSourceControlSystem openGitSrouceControlSystem() {
		GitSourceControlSystem scs = new GitSourceControlSystem();
		try {			
			scs.createWorkingCopy(getWorkingCopy(), getCloneURL(), true);	
		} catch (SourceControlException e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
		return scs;
	}
	
	protected void importRepositoryModelFromGit(RepositoryModel repositoryModel) {	
		try {
			GitSourceControlSystem scs = openGitSrouceControlSystem();
			scs.importRevisions(repositoryModel);			
			scs.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
		}
	}
	
	protected void importJavaFromModisco(RepositoryModel repositoryModel) {
		if (!isStandalone) {
			try {
				importRepositoryModelFromGit(repositoryModel);
				GitSourceControlSystem scs = openGitSrouceControlSystem();
				IRepositoryModelVisitor visitor = createModiscoRepositoryModelImportVisitor(scs, repositoryModel);
				RepositoryModelTraversal.traverse(repositoryModel, visitor);
				visitor.close(repositoryModel);
				scs.close();
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Exception " + e.getClass() + ": " + e.getMessage());
			}
		} 
	}
	
	protected void assertMetaData(RepositoryModel repositoryModel) {
		RepositoryMetaData metaData = getMetaData(repositoryModel);
		Assert.assertNotNull(metaData.getOrigin());
		Assert.assertNotNull(metaData.getNewestRev());
		Assert.assertNotNull(metaData.getOldestRev());
		Assert.assertTrue(metaData.getNewestRev().getTime() > metaData.getOldestRev().getTime());
		Assert.assertNotNull(getImportMetaData(repositoryModel));
		Assert.assertNotNull(getImportMetaData(repositoryModel).getStatsAsJSON());
		Assert.assertEquals(16, metaData.getRevCount());
		Assert.assertEquals(19, metaData.getCuCount());	
		Assert.assertEquals(1, metaData.getCusWithErrors());
	}
	
	@Test
	public void gitImportTest() {
		RepositoryModel repositoryModel = openRepositoryModel(TestModelKind.GIT, true);
		importRepositoryModelFromGit(repositoryModel);

		assertRepositoryModel(repositoryModel, 0);		
		closeRepositoryModel(TestModelKind.GIT, repositoryModel);
	}
	
	@Test
	public void modiscoImportTest() {	
		// run import
		RepositoryModel repositoryModel = openRepositoryModel(TestModelKind.JAVA, true);
		importJavaFromModisco(repositoryModel);		
		closeRepositoryModel(TestModelKind.JAVA, repositoryModel);
		
		// assert results
		repositoryModel = openRepositoryModel(TestModelKind.JAVA, false);
		assertMetaData(repositoryModel);
		assertRepositoryModel(repositoryModel, 0);		
		closeRepositoryModel(TestModelKind.JAVA, repositoryModel);
	}

	protected Collection<String> assertRepositoryModel(RepositoryModel repositoryModel, int minimumNumberOfRevs) {
		Assert.assertNotNull(repositoryModel);
		
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
		
		Assert.assertTrue(repositoryModel.getAllRevs().size() >= minimumNumberOfRevs);
		
		int count = 0;
		TreeIterator<EObject> iterator = repositoryModel.eAllContents();
		while(iterator.hasNext()) {
			iterator.next();
			count++;
		}
		Assert.assertTrue(count > minimumNumberOfRevs*2);
		
		// assert example branch and merge
		RepositoryModelTraversal.traverse(repositoryModel, new EmptyRepositoryModelVisitor() {
			@Override
			public void onMerge(Rev commonMergedRev, Rev lastBranchRev) {
				super.onMerge(commonMergedRev, lastBranchRev);
				List<String> merges = Lists.newArrayList(
					"879076c35867e58b2a95e17139729315acbc65fa",
					"693dcde1333e7b3d537d529190ffde523642ca72"
				);
				Assert.assertTrue(merges.contains(lastBranchRev.getName()));
			}

			@Override
			public void onBranch(Rev commonPreviousRev, Rev newBranchRev) {				
				super.onBranch(commonPreviousRev, newBranchRev);
				if (commonPreviousRev != null) {
					List<String> branches = Lists.newArrayList(
						"879076c35867e58b2a95e17139729315acbc65fa",
						"98f56da6e548af6d5660f0c42726a5cde17f23e3"
					);
					Assert.assertTrue(branches.contains(newBranchRev.getName()));
				}
			}			
		});
		
		return revNames;
	}

	protected IRepositoryModelVisitor createModiscoRepositoryModelImportVisitor(
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
		df.close();
		rw.close();
	}
}
