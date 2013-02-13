package de.hub.srcrepo;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
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

import de.hub.srcrepo.nofrag.gitmodel.Commit;
import de.hub.srcrepo.nofrag.gitmodel.Diff;
import de.hub.srcrepo.nofrag.gitmodel.GitModelFactory;
import de.hub.srcrepo.nofrag.gitmodel.SourceRepository;

public abstract class JGitModelImport {

	private final String repoURI;
	private SourceRepository repositoryModel;
	private Git git;
	private DiffFormatter df;
	private RevWalk rw;

	public JGitModelImport(String repoURI) {
		super();
		this.repoURI = repoURI;
	}
	
	private Commit addCommit(RevCommit commit) throws Exception {
		String commitName = commit.getName();
		Commit commitModel = repositoryModel.exact(commitName);
		if (commitModel == null) {
			commitModel = GitModelFactory.eINSTANCE.createCommit();
			commitModel.setTime(new Date(((long)commit.getCommitTime())*1000));
			commitModel.setMessage(commit.getFullMessage());
			
			List<DiffEntry> diffs = null;
			if (commit.getParentCount() > 0) {						
				RevCommit parent = commit.getParent(0);
				diffs = df.scan(parent.getTree(), commit.getTree());				
			} else {
				diffs = df.scan(new EmptyTreeIterator(), new CanonicalTreeParser(null, rw.getObjectReader(), commit.getTree()));
			}
			
			for (DiffEntry diffEntry: diffs) {
				Diff diffModel = GitModelFactory.eINSTANCE.createDiff();
				diffModel.setNewPath(diffEntry.getNewPath());
				diffModel.setOldPath(diffEntry.getOldPath());
				diffModel.setType(diffEntry.getChangeType());
				commitModel.getDiffs().add(diffModel);
			}
						
			for (RevCommit parent: commit.getParents()) {
				commitModel.getParents().add(addCommit(parent));
			}
			
			repositoryModel.put(commit.getName(), commitModel);
			repositoryModel.getAllCommits().add(commitModel); // TODO this is only for the no frag model
		}
		
		return commitModel;
	}
	
	public void runImport() throws Exception {
		File file = new File(repoURI);
		git = Git.init().setDirectory(file).call();
		rw = new RevWalk(git.getRepository());
		
		df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(git.getRepository());
		df.setDiffComparator(RawTextComparator.DEFAULT);
		df.setDetectRenames(true);
		
		repositoryModel = GitModelFactory.eINSTANCE.createSourceRepository();	
		addContent(repositoryModel);
		
		for (Ref ref: git.getRepository().getAllRefs().values()) {
			de.hub.srcrepo.nofrag.gitmodel.Ref refModel = GitModelFactory.eINSTANCE.createRef();
			refModel.setIsPeeled(ref.isPeeled());
			refModel.setIsSymbolic(ref.isSymbolic());
			refModel.setName(ref.getName());
			repositoryModel.getAllRefs().add(refModel);
			
			RevObject revObject = rw.parseAny(ref.getObjectId());
			if (revObject.getType() == Constants.OBJ_COMMIT) {
				RevCommit commit = (RevCommit)revObject;
				refModel.setReferencedCommit(addCommit(commit));
			}			
		}
	}
	
	public abstract void addContent(EObject eObject);
	
}
