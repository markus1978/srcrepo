package de.hub.srcrepo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
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
	private final String rootCommitName;
	

	public JGitModelImport(Git git, SourceRepository targetModel, String rootCommitName) {
		super();
		this.gitFactory = (GitModelFactory)targetModel.eClass().getEPackage().getEFactoryInstance();
		this.git = git;
		this.targetModel = targetModel;
		this.rootCommitName = rootCommitName;
	}

	private Commit createCommitModel(RevCommit commit) throws Exception {
		String commitName = commit.getName();
		Commit commitModel = targetModel.getCommit(commitName);
		if (commitModel == null) {
			commitModel = gitFactory.createCommit();
			commitModel.setName(commit.getName());
			targetModel.getAllCommits().add(commitModel);
			targetModel.putCommit(commit.getName(), commitModel);			
		}
		
		if (rootCommitName != null && rootCommitName.equals(commitModel.getName())) {
			targetModel.setRootCommit(commitModel);
		}
		
		return commitModel;
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
		// create helper
		Set<RevCommit> commitsToImport = new HashSet<RevCommit>();		
		Repository jGitRepository = git.getRepository();
		RevWalk walk = new RevWalk(jGitRepository);
		
		// resolve all refs, these are our starting points
		Map<AnyObjectId, Set<Ref>> allRefsByPeeledObjectId = jGitRepository.getAllRefsByPeeledObjectId();
		Set<AnyObjectId> peeledRefsIds = allRefsByPeeledObjectId.keySet();
		// walk from all starting refs down to collect all commits (TODO what about abandoned and removed branches)
		for (AnyObjectId peeledRefId: peeledRefsIds) {			
			walk.reset();
			RevObject peeledRef = walk.parseAny(peeledRefId);
			if (peeledRef.getType() == Constants.OBJ_COMMIT) {
				RevCommit startCommit = (RevCommit)peeledRef;
				
				for (Ref ref: allRefsByPeeledObjectId.get(peeledRefId)) {
					de.hub.srcrepo.gitmodel.Ref refModel = createRefModel(ref);
					Commit startCommitModel = createCommitModel(startCommit);
					refModel.setReferencedCommit(startCommitModel);
					targetModel.getAllRefs().add(refModel);	
				}					
				
				walk.markStart(startCommit);
				for(RevCommit commit: walk) {
					commitsToImport.add(commit);					
				}
			}
		}
		
		SrcRepoActivator.INSTANCE.info("Found " + commitsToImport.size() + " commits based on " + peeledRefsIds.size() + " starting refs. Importing now...");
		
		// import all found commits
		walk.reset();
		ObjectReader objectReader = walk.getObjectReader();
		DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(git.getRepository());
		df.setDiffComparator(RawTextComparator.DEFAULT);
		df.setDetectRenames(true);
		
		for (RevCommit commit: commitsToImport) {
			SrcRepoActivator.INSTANCE.debug("import commit " + commit.getName());
			Commit commitModel = targetModel.getCommit(commit.getName());
			if (commitModel == null) {
				commitModel = createCommitModel(commit);
			}
	
			commitModel.setTime(new Date(((long) commit.getCommitTime()) * 1000));
			commitModel.setMessage(commit.getFullMessage());
	
			
			
			List<DiffEntry> diffs = null;
			if (commit.getParentCount() > 0) {
				for (RevCommit parent : commit.getParents()) {
					diffs = df.scan(parent, commit);
					Commit parentModel = createCommitModel(parent);
					createParentRelation(commitModel, parentModel, diffs);
				}
			} else {				
				diffs = df.scan(new EmptyTreeIterator(), new CanonicalTreeParser(null, objectReader, commit.getTree()));
				createParentRelation(commitModel, null, diffs);
				if (targetModel.getRootCommit() == null) {
					targetModel.setRootCommit(commitModel);
				} else {
					SrcRepoActivator.INSTANCE.error("There are multiple root commits, this can not happen.");
				}
			}
		}
		
		walk.dispose();
	}
	
	private de.hub.srcrepo.gitmodel.Ref createRefModel(Ref ref) {
		de.hub.srcrepo.gitmodel.Ref refModel = gitFactory.createRef();
		refModel.setIsPeeled(ref.isPeeled());
		refModel.setIsSymbolic(ref.isSymbolic());
		refModel.setName(ref.getName());
		return refModel;
	}
}
