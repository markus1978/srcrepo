package de.hub.srcrepo;

import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public interface IRepositoryModelVisitor {

	/**
	 * Is called when the traversal detects a branch. The method is called for
	 * each branch. Not that all other methods (e.g. onStartRev and
	 * onCompleteRev) are still called for the newBranch revision and already
	 * have been called for the commonParent.
	 * 
	 * @param commonPreviousRev
	 *            refers to the revision that all branches have as a common
	 *            parent revision.
	 * @param newBranchRev
	 *            refers to the first revision on the branch.
	 */
	public void onBranch(Rev commonPreviousRev, Rev newBranchRev);

	/**
	 * Is called when traversal detects a merge. The method is called for all
	 * branches that are merged into another branch. It is not called for the
	 * first branch that reaches the commonMergedRev.
	 * 
	 * @param commonMergedRev
	 *            refers to the next common revision after merge.
	 * @param lastBranchRev
	 *            refers to the last revision on the individual branch.
	 */
	public void onMerge2(Rev commonMergedRev, Rev lastBranchRev);
	

	/**
	 * Is called when a new commit is visited and before the diffs are visited.
	 * 
	 * @return false if the current branch should not be visited further.
	 */
	public boolean onStartRev(Rev rev, int number);

	/**
	 * Is called when the currently visited commit is left, i.e. all diffs have
	 * been visited.
	 * 
	 */
	public void onCompleteRev(Rev rev);

	public void onCopiedFile(Diff diff);

	public void onRenamedFile(Diff diff);

	public void onAddedFile(Diff diff);

	public void onModifiedFile(Diff diff);

	public void onDeletedFile(Diff diff);

	public void close(RepositoryModel repositoryModel);

}
