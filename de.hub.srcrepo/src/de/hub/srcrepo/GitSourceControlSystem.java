package de.hub.srcrepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.LockFailedException;
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
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.Rev;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;

public class GitSourceControlSystem implements ISourceControlSystem {
	
	private final boolean isTryHard = false;
	private Git git = null;

	@Override
	public void createWorkingCopy(File target, String url, boolean onlyIfNecessary) throws SourceControlException {
		CloneCommand cloneCommand = new CloneCommand();
		cloneCommand.setCloneAllBranches(true);
		cloneCommand.setRemote("origin");
		cloneCommand.setURI(url);
		if (!target.exists() || !onlyIfNecessary) {
			if (target.exists()) {			
				try {
					FileUtils.delete(target, FileUtils.RECURSIVE);
				} catch (IOException e) {
					throw new SourceControlException("Could not remove existing working copy: " + e.getMessage(), e);
				}
			}
			cloneCommand.setDirectory(target);
			try {
				cloneCommand.call();
			} catch (Exception e) {
				if (e.getCause() != null && e.getCause() instanceof IOException) {
					throw new SourceControlException("IOException during cloning.", e.getCause());
				} else {
					throw new SourceControlException(e);
				}
			}
		}
		setWorkingCopy(target);
	}

	@Override
	public void setWorkingCopy(File target) throws SourceControlException {
		try {
			git = Git.open(target);
			
		} catch (IOException e) {
			throw new SourceControlException(e);
		}
	}
	
	@Override
	public File getWorkingCopy() {
		return git.getRepository().getWorkTree();
	}
	
	private int importedRevCount = 0;

	private Rev createRevModel(RepositoryModel model, RepositoryModelFactory factory, RevCommit commit) throws Exception {
		String revName = commit.getName();
		Rev revModel = RepositoryModelUtil.getRev(model, revName);
		if (revModel == null) {
			revModel = factory.createRev();
			revModel.setName(revName);
			model.getAllRevs().add(revModel);
			if (RepositoryModelUtil.getRev(model, revName) != revModel) {
				System.out.println("### NOOOOOOOOO WAAYYYYYYY");
			}
			SrcRepoActivator.INSTANCE.debug("Created a model for revision " + revModel.getName() + " (" + importedRevCount++ +")");
		}
		
		return revModel;
	}
	
	private void createParentRelation(RepositoryModelFactory factory, Rev childModel, Rev parentModel, List<DiffEntry> diffs) {
		ParentRelation parentRelationModel = factory.createParentRelation();
		childModel.getParentRelations().add(parentRelationModel);
		parentRelationModel.setParent(parentModel);		
		for (DiffEntry diffEntry : diffs) {
			Diff diffModel = null;
			String path = diffEntry.getNewPath();			
			diffModel = factory.createDiff();
			diffModel.setNewPath(path);
			diffModel.setOldPath(diffEntry.getOldPath());
			diffModel.setType(diffEntry.getChangeType());
			parentRelationModel.getDiffs().add(diffModel);
		}
	}
	
	@Override
	public void importRevisions(RepositoryModel model) throws SourceControlException {
		try {
			doImportRevisions(model);
		} catch (Exception e) {
			throw new SourceControlException("Could not import the repository.", e);
		}
	}
	
	private void doImportRevisions(RepositoryModel model) throws Exception {	
		// create helper
		RepositoryModelFactory factory = (RepositoryModelFactory)model.eClass().getEPackage().getEFactoryInstance();
		List<RevCommit> commitsToImport = new ArrayList<RevCommit>();
		Collection<String> commitNamesToImport = new HashSet<String>();
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
					de.hub.srcrepo.repositorymodel.Ref refModel = createRefModel(factory, ref);
					Rev startRevModel = createRevModel(model, factory, startCommit);
					refModel.setReferencedCommit(startRevModel);
					model.getAllRefs().add(refModel);
				}					
				
				walk.markStart(startCommit);
				for(RevCommit commit: walk) {
					if (commitNamesToImport.add(commit.getName())) {
						commitsToImport.add(commit);
					}
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
			SrcRepoActivator.INSTANCE.debug("import revision " + commit.getName());
			Rev revModel = RepositoryModelUtil.getRev(model, commit.getName());
			if (revModel == null) {
				revModel = createRevModel(model, factory, commit);
			} 
	
			revModel.setTime(new Date(((long) commit.getCommitTime()) * 1000));
			revModel.setMessage(commit.getFullMessage());
			revModel.setAuthor(commit.getAuthorIdent().getName());		
			
			List<DiffEntry> diffs = null;
			if (commit.getParentCount() > 0) {
				for (RevCommit parent : commit.getParents()) {
					diffs = df.scan(parent, commit);
					Rev parentRevModel = createRevModel(model, factory, parent);
					createParentRelation(factory, revModel, parentRevModel, diffs);
				}
			} else {				
				diffs = df.scan(new EmptyTreeIterator(), new CanonicalTreeParser(null, objectReader, commit.getTree()));
				createParentRelation(factory, revModel, null, diffs);
				if (!model.getRootRevs().isEmpty()) {
					SrcRepoActivator.INSTANCE.warning("There are multiple root revisions, this should not happen: " + revModel.getName() + ", " + revModel.getTime() + ". Using the earlier one.");
				}		
				SrcRepoActivator.INSTANCE.info("Root revision: " + revModel.getName() + ", " + revModel.getTime());							
				model.getRootRevs().add(revModel);
			}
		}
		
		walk.dispose();
	}
	
	private de.hub.srcrepo.repositorymodel.Ref createRefModel(RepositoryModelFactory factory, Ref ref) {
		de.hub.srcrepo.repositorymodel.Ref refModel = factory.createRef();
		refModel.setIsPeeled(ref.isPeeled());
		refModel.setIsSymbolic(ref.isSymbolic());
		refModel.setName(ref.getName());
		return refModel;
	}
	
	private void clean() throws GitAPIException {
		git.clean().setCleanDirectories(true).setIgnore(false).setDryRun(false).call();
		if (isTryHard) {
			org.eclipse.jgit.api.Status status = git.status().call();
			if (!status.isClean()) {
				// try again
				git.clean().setCleanDirectories(true).setIgnore(false).setDryRun(false).call();
				status = git.status().call();
			}
			if (!status.isClean() || status.getUntracked().size() > 0) {
				SrcRepoActivator.INSTANCE.warning("Git clean did not fully clean even after trying again: " + status.isClean() + "/" 
						+ status.getUntracked().size() + ".");
			}
		}
	}
	
	private void removeLock(String file) {
		File lockFile = new File(git.getRepository().getWorkTree().getPath() + "/" + file);
		if (lockFile.exists()) {
			lockFile.delete();
			SrcRepoActivator.INSTANCE.debug("Had to remove git lock file " + file);
		}
	}

	@Override
	public void checkoutRevision(String name) throws SourceControlException {
		EtmMonitor etmMonitor = EtmManager.getEtmMonitor();
		try {
			// remove a possible lock files from prior errors or crashes
			removeLock(".git/index.lock");
			removeLock(".git/HEAD.lock");
			// clean the working tree from ignored or other untracked files
			EtmPoint cleanPoint = etmMonitor.createPoint("GIT:clean");
			clean();
			cleanPoint.collect();
			// reset possible changes
			EtmPoint resetPoint = etmMonitor.createPoint("GIT:reset");
			git.reset().setMode(ResetType.HARD).call();
			resetPoint.collect();
			// checkout the new revision
			EtmPoint checkoutPoint = etmMonitor.createPoint("GIT:checkout");
			git.checkout().setForce(true).setName(name).call();
			checkoutPoint.collect();
		} catch (JGitInternalException e) {
			if (e.getCause() instanceof LockFailedException) {
				throw new SourceControlException("Could not lock the repository.", e);
			} if (e.getMessage().contains("conflict")) {
				throw new SourceControlException("Conflicts during checkout.", e);
			} else {				
				throw new SourceControlException(e);
			}			
		} catch (GitAPIException e) {
			throw new SourceControlException(e);
		}
	}

	@Override
	public void close() {
		if (git != null) {
			git.close();
		}
	}	
}
