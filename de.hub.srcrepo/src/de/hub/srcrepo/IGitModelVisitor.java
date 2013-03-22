package de.hub.srcrepo;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;

public interface IGitModelVisitor {

	/**
	 * Is called when the currently visited branch ends or is merged into an
	 * already visited branch and visiting is continued on the next (not yet
	 * visited) branch. Note, this is also called in the beginning with null
	 * arguments.
	 * 
	 * @param mergeCommit
	 *            The already priorly visited commit that the currently visited
	 *            branch is merged into, or null if the current branch has no
	 *            more children (the branch ends, e.g. is HEAD).
	 * @param branchCommit
	 *            The commit from which the next visited branch is branched
	 *            from, or null if the next visited branch has no parents.
	 */
	public void onMerge(Commit mergeCommit, Commit branchCommit);

	/**
	 * Is called when a new commit is visited and before the diffs are visited.
	 * 
	 * @return false if the current branch should not be visited further.
	 */
	public boolean onStartCommit(Commit commit);

	/**
	 * Is called when the currently visited commit is left, i.e. all diffs have
	 * been visited.
	 * 
	 */
	public void onCompleteCommit(Commit commit);

	public void onCopiedFile(Diff diff);

	public void onRenamedFile(Diff diff);

	public void onAddedFile(Diff diff);

	public void onModifiedFile(Diff diff);

	public void onDeletedFile(Diff diff);
}
