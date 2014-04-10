package de.hub.srcrepo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;

public class RepositoryModelTraversal {
	
	private final RepositoryModel repositoryModel;
	private final IRepositoryModelVisitor visitor;
	
	private Stack<Rev> openBranches = new Stack<Rev>();
	private Collection<Rev> completedBranches = new HashSet<Rev>();
	private Collection<Rev> merges = new HashSet<Rev>();
	private Rev lastImportedRev = null;
	
	public RepositoryModelTraversal(RepositoryModel repositoryModel,
			IRepositoryModelVisitor visitor) {
		super();
		this.repositoryModel = repositoryModel;
		this.visitor = visitor;
	}

	public void run(boolean resume, boolean save, TraversalState state, int numberOfRevsToImport) {
		openBranches.clear();
		completedBranches.clear();
		merges.clear();
		lastImportedRev = null;
		
		int count = 0;
		int maxRevCount = numberOfRevsToImport > 0 ? numberOfRevsToImport : Integer.MAX_VALUE;
		
		if (resume) {
			openBranches.addAll(state.getOpenBranches());
			completedBranches.addAll(state.getCompletedBranches());
			merges.addAll(state.getMerges());
			visitor.loadState(state);
		} else {
			openBranches.add(repositoryModel.getRootRev());
		}
		
		while(!openBranches.isEmpty()) {
			Rev rev = openBranches.pop();
			visitor.onMerge(lastImportedRev, rev);
			branchLoop: while(true) {
				int children = rev.getChildRelations().size();
				int parents = rev.getParentRelations().size();
				
				// is merge?
				if (parents > 1 && !onMerge(rev)) {
					break branchLoop;	
				}
							
				// import current rev
				visitRev(rev);
				count++;
				
				// is branch or branch complete: determine next rev
				if (children == 1) {
					rev = rev.getChildRelations().get(0).getChild();
				} else if (children > 1) {
					rev = onBranch(rev);						
				} else {
					rev = null;
				}
				
				// abort and save?
				if (count >= maxRevCount) {
					if (save) {
						state.getCompletedBranches().clear();
						state.getCompletedBranches().addAll(completedBranches);
						state.getMerges().clear();
						state.getMerges().addAll(merges);
						state.getOpenBranches().clear();
						if (rev != null) {
							state.getOpenBranches().add(rev);
						}
						state.getOpenBranches().addAll(openBranches);
						visitor.saveState(state);
					}
					return;
				}
				
				// is branch completed?
				if (rev == null) {
					break branchLoop;
				}
			}						
		}
	}
	
	private void visitRev(Rev rev) {
		visitor.onStartRev(rev);
		for (ParentRelation parentRelation: rev.getParentRelations()) {
			for (Diff diff : parentRelation.getDiffs()) {
				if (diff.getType() == ChangeType.ADD) {
					visitor.onAddedFile(diff);
				} else if (diff.getType() == ChangeType.COPY) {
					visitor.onCopiedFile(diff);
				} else if (diff.getType() == ChangeType.DELETE) {
					visitor.onDeletedFile(diff);
				} else if (diff.getType() == ChangeType.MODIFY) {
					visitor.onModifiedFile(diff);
				} else if (diff.getType() == ChangeType.RENAME) {
					visitor.onRenamedFile(diff);
				}
			}
		}
		visitor.onCompleteRev(rev);
		lastImportedRev = rev;
	}
	
	/**
	 * @return the revision that import of current branch should be continued with
	 */
	private Rev onBranch(Rev branchingRev) {
		Rev continueWith = null;
		for (ParentRelation childRelation : branchingRev.getChildRelations()) {
			Rev child = childRelation.getChild();
			if (!completedBranches.contains(child)) {
				if (continueWith == null) {
					continueWith = child;
				} else {
					openBranches.push(child);
				}
			}
		}
		return continueWith;
	}
	
	/**
	 * @return if import of this branch should continue
	 */
	private boolean onMerge(Rev mergingRev) {
		if (!merges.contains(mergingRev)) {
			merges.add(mergingRev);
			return true;
		} else {
			return false;
		}
	}

	public static void traverse(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor, TraversalState state, boolean resume, int stopAfterNumberOfRevs) {
		new RepositoryModelTraversal(repositoryModel, visitor).run(resume, stopAfterNumberOfRevs > 0, state, stopAfterNumberOfRevs);
	}

	public static void traverseRepository(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor) {
		traverse(repositoryModel, visitor, null, false, -1);		
	}
	
}
