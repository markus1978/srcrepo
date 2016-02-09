package de.hub.srcrepo;

import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public interface IRepositoryModelVisitor {

	/**
	 * Is called when the currently visited branch ends or is merged into an
	 * already visited branch and visiting is continued on the next (not yet
	 * visited) branch. Note, this is also called in the beginning of a branch
	 * with mergeRev==null.
	 * 
	 * @param mergeRev
	 *            The already priorly visited revision that the currently visited
	 *            branch is merged into, or null if the current branch has no
	 *            more children (the branch ends, e.g. is HEAD).
	 * @param branchRev
	 *            The revision from which the next visited branch is branched
	 *            from, or null if the next visited branch has no parents.
	 */
	public void onMerge(Rev mergeRev, Rev branchRev);

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
