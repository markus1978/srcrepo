package de.hub.srcrepo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.DepthWalk.RevWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.junit.Test;

public class GitTest {

	@Test
	public void diffTest() throws Exception {
		//  equal file on different branches
		//	String parentId = "5dd04d35dd1006047fe8aed0d01e510015187544";
		//	String childId = "594ce0283d3bce9d76878dc4690381d4f0e7e8c1";		
		
		printDiffs("change on branch", "2327334f4e763e1f82072eefd89efacf13b2a3bc", "5dd04d35dd1006047fe8aed0d01e510015187544");
				
		printDiffs("change on master", "339612e2c2fe1c36042ff8b359a76f80e481bda2", "c54ce24960cc8557a6a0c06d0095c2d1c249464b");
		
		printDiffs("branch->master", "5dd04d35dd1006047fe8aed0d01e510015187544", "594ce0283d3bce9d76878dc4690381d4f0e7e8c1");
		
		printDiffs("master->master", "c54ce24960cc8557a6a0c06d0095c2d1c249464b", "594ce0283d3bce9d76878dc4690381d4f0e7e8c1");
	}

	private void printDiffs(String msg, String parentId, String childId)
			throws IOException, AmbiguousObjectException,
			IncorrectObjectTypeException {
		String repositoryURL = "bare-repositories/file-contents-changed-to-same-on-merged-branches-dummy.git";
		File workingCopy = new File("C:/tmp/srcrepo/clones/test.git");
		
		if (!workingCopy.exists()) {
			try {
				Git.cloneRepository().setURI(repositoryURL).setDirectory(workingCopy).call();
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}
		}
		
		Git git = Git.open(workingCopy);
		ObjectId parent = git.getRepository().resolve(parentId);
		ObjectId child = git.getRepository().resolve(childId);
		DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(git.getRepository());
		df.setDiffComparator(new RawTextComparator() {			
			int i = 0;
			@Override
			public boolean equals(RawText arg0, int arg1, RawText arg2, int arg3) {
				System.out.println("####");
				return false;
			}
			
			@Override
			protected int hashRegion(byte[] arg0, int arg1, int arg2) {				
				System.out.println("####");
				return i++;
			}

			@Override
			public int hash(RawText seq, int lno) {
				System.out.println("####");
				return i++;
			}

			@Override
			public Edit reduceCommonStartEnd(RawText a, RawText b, Edit e) {
				System.out.println("####");
				return super.reduceCommonStartEnd(a, b, e);
			}	
		});
		df.setDetectRenames(true);
		List<DiffEntry> diffs = df.scan(parent, child);
		System.out.println(msg + " ---------------------");
		System.out.println("Number of diffs: " + diffs.size());
		
		printFileIds(git, "parent", parent);
		printFileIds(git, "child", child);
		
		System.out.println("\n");
	}

	private void printFileIds(Git git, String msg, ObjectId child)
			throws MissingObjectException, IncorrectObjectTypeException,
			IOException, CorruptObjectException {
		RevWalk walk = new RevWalk(git.getRepository(), 0);
        RevCommit commit = walk.parseCommit(child);
        RevTree childTree = commit.getTree();

		TreeWalk treeWalk = new TreeWalk(git.getRepository());
		treeWalk.addTree(childTree);
		while (treeWalk.next()) {
			if (!treeWalk.isSubtree()) {
				System.out.println(msg + ": " + treeWalk.getPathString() + ":" + treeWalk.getObjectId(0));
			}
		}
	}
}
