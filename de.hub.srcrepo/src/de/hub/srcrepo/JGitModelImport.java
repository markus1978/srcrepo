package de.hub.srcrepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
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
import de.hub.srcrepo.gitmodel.JavaDiff;
import de.hub.srcrepo.gitmodel.ParentRelation;
import de.hub.srcrepo.gitmodel.SourceRepository;

public class JGitModelImport {

	private final Git git;
	private final IResourceHandler resourceHandler;
	
	private SourceRepository repositoryModel;	
	private DiffFormatter df;
	private RevWalk rw;
	private boolean hasBeenRun = false;
	private IGitModelImportHandler<JavaDiff> handler;
	
	private List<JavaDiff> tmp_javaDiffs = new ArrayList<JavaDiff>();
	
	private Queue<RevCommit> commitsToVisit = new LinkedList<RevCommit>();
	
	public JGitModelImport(Git git, IResourceHandler resourceHandler) {
		super();
		this.git = git;
		this.resourceHandler = resourceHandler;
		init();
	}
	
	public void setJavaHandler(IGitModelImportHandler<JavaDiff> handler) {
		this.handler = handler;
	}

	private void init() {
		rw = new RevWalk(git.getRepository());
		
		df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(git.getRepository());
		df.setDiffComparator(RawTextComparator.DEFAULT);
		df.setDetectRenames(true);
		
		repositoryModel = GitModelFactory.eINSTANCE.createSourceRepository();	
	}
	
	private Commit addCommit(RevCommit commit) throws Exception {
		String commitName = commit.getName();
		Commit commitModel = repositoryModel.exact(commitName);
		if (commitModel == null) {
			commitModel = GitModelFactory.eINSTANCE.createCommit();
			commitModel.setName(commit.getName());
			repositoryModel.put(commit.getName(), commitModel);
			repositoryModel.getAllCommits().add(commitModel); // TODO this is only for the no frag model
			commitsToVisit.add(commit);
		}
		return commitModel;					
	}
	
	private void visitCommit(RevCommit commit) throws Exception {
		Commit commitModel = repositoryModel.exact(commit.getName());
		if (commitModel == null) {
			commitModel = addCommit(commit);
		}
		
		commitModel.setTime(new Date(((long)commit.getCommitTime())*1000));
		commitModel.setMessage(commit.getFullMessage());
		
		List<DiffEntry> diffs = null;
		tmp_javaDiffs.clear();
		if (commit.getParentCount() > 0) {				
			for (RevCommit parent: commit.getParents()) {
				diffs = df.scan(parent.getTree(), commit.getTree());
				Commit parentModel = addCommit(parent);
				addParentRelation(commitModel, parentModel, diffs);
			}
		} else {
			diffs = df.scan(new EmptyTreeIterator(), new CanonicalTreeParser(null, rw.getObjectReader(), commit.getTree()));
			addParentRelation(commitModel, null, diffs);
		}			
		
		onCommitAdded(commitModel);
	}
	
	private void addParentRelation(Commit commitModel, Commit parentModel, List<DiffEntry> diffs) {
		ParentRelation parentRelationModel = GitModelFactory.eINSTANCE.createParentRelation();
		parentRelationModel.setParent(parentModel);
		commitModel.getParentRelations().add(parentRelationModel);
		for (DiffEntry diffEntry: diffs) {			
			Diff diffModel = null;			
			String path = diffEntry.getNewPath();
			if (path.endsWith(".java")) {
				diffModel = GitModelFactory.eINSTANCE.createJavaDiff();
				tmp_javaDiffs.add((JavaDiff)diffModel);
			} else {
				diffModel = GitModelFactory.eINSTANCE.createDiff();				
			}
			diffModel.setNewPath(path);
			diffModel.setOldPath(diffEntry.getOldPath());
			diffModel.setType(diffEntry.getChangeType());
			parentRelationModel.getDiffs().add(diffModel);
			
		}
	}
	
	private void onCommitAdded(Commit commitModel) {
		try {
			git.checkout().setName(commitModel.getName()).call();
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
			return;
		}
		
		if (handler != null) {
			for(JavaDiff diff: tmp_javaDiffs) {
				if (diff.getType() == ChangeType.ADD) {
					handler.onAddedFile(diff);
				} else if (diff.getType() == ChangeType.COPY) {
					handler.onCopiedFile(diff);
				} else if (diff.getType() == ChangeType.DELETE) {
					handler.onDeletedFile(diff);
				} else if (diff.getType() == ChangeType.MODIFY) {
					handler.onModifiedFile(diff);
				} else if (diff.getType() == ChangeType.RENAME) {
					handler.onRenamedFile(diff);
				}
			}
		}
	}

	public void runImport() throws Exception {
		if (hasBeenRun) {
			throw new IllegalStateException();
		}
		
		hasBeenRun = true;
		resourceHandler.addContents(repositoryModel);
		
		for (Ref ref: git.getRepository().getAllRefs().values()) {
			de.hub.srcrepo.gitmodel.Ref refModel = GitModelFactory.eINSTANCE.createRef();
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
		
		while (!commitsToVisit.isEmpty()) {
			visitCommit(commitsToVisit.poll());
		}
	}
}
