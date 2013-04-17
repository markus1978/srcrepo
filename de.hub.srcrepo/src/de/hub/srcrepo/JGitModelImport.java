package de.hub.srcrepo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.GitModelFactory;
import de.hub.srcrepo.gitmodel.ParentRelation;
import de.hub.srcrepo.gitmodel.SourceRepository;

public class JGitModelImport {

	// parameters
	private final GitModelFactory gitFactory;
	private final Git git;
	private final SourceRepository targetModel;

	// helper
	private final DiffFormatter df;
	private final RevWalk rw;
	
	// state
	private boolean hasBeenRun = false;
	private Queue<RevCommit> commitsToImportParentsFrom = new LinkedList<RevCommit>();	

	public JGitModelImport(Git git, SourceRepository targetModel) {
		super();
		this.gitFactory = (GitModelFactory)targetModel.eClass().getEPackage().getEFactoryInstance();
		this.git = git;
		this.targetModel = targetModel;
		
		rw = new RevWalk(git.getRepository());

		df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(git.getRepository());
		df.setDiffComparator(RawTextComparator.DEFAULT);
		df.setDetectRenames(true);

	}

	private Commit getCommitModel(RevCommit commit) throws Exception {
		String commitName = commit.getName();
		Commit commitModel = targetModel.getCommit(commitName);
		if (commitModel == null) {
			commitModel = gitFactory.createCommit();
			commitModel.setName(commit.getName());
			targetModel.getAllCommits().add(commitModel);
			targetModel.putCommit(commit.getName(), commitModel);			
			commitsToImportParentsFrom.add(commit);
		}
		return commitModel;
	}

	private void importParentsForCommit(RevCommit commit) throws Exception {
		SrcRepoActivator.INSTANCE.info("import commit " + commit.getName());
		Commit commitModel = targetModel.getCommit(commit.getName());
		if (commitModel == null) {
			commitModel = getCommitModel(commit);
		}

		commitModel.setTime(new Date(((long) commit.getCommitTime()) * 1000));
		commitModel.setMessage(commit.getFullMessage());

		List<DiffEntry> diffs = null;
		if (commit.getParentCount() > 0) {
			for (RevCommit parent : commit.getParents()) {
				diffs = df.scan(parent, commit);
				Commit parentModel = getCommitModel(parent);
				createParentRelation(commitModel, parentModel, diffs);
			}
		} else {
			diffs = df.scan(new EmptyTreeIterator(), new CanonicalTreeParser(null, rw.getObjectReader(), commit.getTree()));
			createParentRelation(commitModel, null, diffs);
			if (targetModel.getRootCommit() == null) {
				targetModel.setRootCommit(commitModel);
			} else {
				SrcRepoActivator.INSTANCE.error("There are multiple root commits, this can not happen.");
			}
		}
	}

	private void createParentRelation(Commit commitModel, Commit parentModel, List<DiffEntry> diffs) {
		ParentRelation parentRelationModel = gitFactory.createParentRelation();
		commitModel.getParentRelations().add(parentRelationModel);
		parentRelationModel.setParent(parentModel);		
		for (DiffEntry diffEntry : diffs) {
			Diff diffModel = null;
			String path = diffEntry.getNewPath();
			if (path.endsWith(".java")) {
				diffModel = gitFactory.createJavaDiff();
			} else {
				diffModel = gitFactory.createDiff();
			}
			diffModel.setNewPath(path);
			diffModel.setOldPath(diffEntry.getOldPath());
			diffModel.setType(diffEntry.getChangeType());
			parentRelationModel.getDiffs().add(diffModel);
		}
	}

	public void runImport() throws Exception {
		// this class can only be used once
		if (hasBeenRun) {
			throw new IllegalStateException();
		}
		hasBeenRun = true;

		// start to traverse the commits at the given refs
		for (Ref ref : git.getRepository().getAllRefs().values()) {
			de.hub.srcrepo.gitmodel.Ref refModel = gitFactory.createRef();
			refModel.setIsPeeled(ref.isPeeled());
			refModel.setIsSymbolic(ref.isSymbolic());
			refModel.setName(ref.getName());
			targetModel.getAllRefs().add(refModel);

			RevObject revObject = rw.parseAny(ref.getObjectId());
			if (revObject.getType() == Constants.OBJ_COMMIT) {
				RevCommit commit = (RevCommit) revObject;
				refModel.setReferencedCommit(getCommitModel(commit));
			} else {
				SrcRepoActivator.INSTANCE.warning("Encountered a RevObject that is no commit. The current implementation does not recognize these objects and ignores them.");
			}
		}
		
		while (!commitsToImportParentsFrom.isEmpty()) {
			RevCommit nextCommit = commitsToImportParentsFrom.poll();
			// reparse the commit. If I take the commits directly from
			// RevCommit.getParents[], JGit starts to throw random exceptions.
			nextCommit = rw.parseCommit(nextCommit.getId());
			importParentsForCommit(nextCommit);
		}
		
		rw.dispose();
	}
}
