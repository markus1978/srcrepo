package de.hub.srcrepo.gitmodel.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.IGitModelVisitor;
import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.ParentRelation;

public class GitModelUtil {

//	public enum Direction { TO_PARENT, FROM_PARENT };
	
	private static class BranchPoint {
		private final Commit branchingCommit;
		private final List<Commit> branches;
		private int currentIndex;
		
		public BranchPoint(Commit branchingCommit, List<Commit> branches) {
			this.branchingCommit = branchingCommit;
			this.branches = branches;
			this.currentIndex = 0;
		}
		
		boolean hasNext() {
			return branches.size() > currentIndex;
		}
		
		Commit next() {
			return branches.get(currentIndex++);
		}
	}
	
	private static List<Commit> getChildren(Commit commit) {
		EList<ParentRelation> childRelations = commit.getChildRelations();
		List<Commit> result = new ArrayList<Commit>(childRelations.size());
		for (ParentRelation parentRelation: childRelations) {
			Commit child = parentRelation.getChild();
			if (child != null) {
				result.add(child);
			}
		}
		return result;
	}
	
	public static void visitCommitHierarchy(Commit startCommit, IGitModelVisitor visitor) {
		Queue<BranchPoint> branchPoints = new LinkedList<BranchPoint>();
		Collection<Commit> mergePoints = new HashSet<Commit>();
		
		List<Commit> startBranches = new ArrayList<Commit>(1);
		startBranches.add(startCommit);
		branchPoints.add(new BranchPoint(null, startBranches));
		
		Commit currentCommit = null;
		while (!branchPoints.isEmpty()) {
			BranchPoint currentBranchPoint = branchPoints.poll();
			while (currentBranchPoint.hasNext()) {				
				Commit nextCommit = currentBranchPoint.next();
				visitor.onMerge(currentCommit, currentBranchPoint.branchingCommit);
				currentCommit = nextCommit;
				
				boolean continueOnBranch = true;
				while (continueOnBranch) {
					EList<ParentRelation> parentRelations = currentCommit.getParentRelations();
					boolean isMerge = parentRelations.size() > 1;
					if (isMerge && (mergePoints.contains(currentCommit))) {							
						continueOnBranch = false;						
					} else {					
						continueOnBranch = visitor.onStartCommit(currentCommit);
					}

					if (continueOnBranch) {											
						for (ParentRelation parentRelation: parentRelations) {
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
						visitor.onCompleteCommit(currentCommit);
					
						if (isMerge) {
							mergePoints.add(currentCommit);
						}
						
						List<Commit> children = getChildren(currentCommit);
						if (children.size() == 0) {
							continueOnBranch = false;
						} else {
							BranchPoint branchPoint = new BranchPoint(currentCommit, children);
							currentCommit = branchPoint.next();
							if (branchPoint.hasNext()) {
								branchPoints.add(branchPoint);
							}							
						}
					}					
				}				
			}
		}
	}
	
//	public static void visitCommitHierarchy(Commit startingCommit, Direction direction, IGitModelVisitor visitor) {
//		Queue<Commit> commitsToVisist = new LinkedList<Commit>();
//		commitsToVisist.add(startingCommit);
//		while (!commitsToVisist.isEmpty()) {
//			Commit nextCommit = commitsToVisist.poll();
//			
//			if (visitor.onStartCommit(nextCommit)) {
//				for (ParentRelation parentRelation: nextCommit.getParentRelations()) {
//					for (Diff diff : parentRelation.getDiffs()) {
//						if (diff.getType() == ChangeType.ADD) {
//							visitor.onAddedFile(diff);
//						} else if (diff.getType() == ChangeType.COPY) {
//							visitor.onCopiedFile(diff);
//						} else if (diff.getType() == ChangeType.DELETE) {
//							visitor.onDeletedFile(diff);
//						} else if (diff.getType() == ChangeType.MODIFY) {
//							visitor.onModifiedFile(diff);
//						} else if (diff.getType() == ChangeType.RENAME) {
//							visitor.onRenamedFile(diff);
//						}
//					}
//				}
//				visitor.onCompleteCommit(nextCommit);
//				
//				if (direction == Direction.TO_PARENT) {
//					for (ParentRelation parentRelation: nextCommit.getParentRelations()) {
//						Commit parent = parentRelation.getParent();
//						if (parent != null) {
//							commitsToVisist.add(parent);
//						}
//					}
//				} else { // if (direction == Direction.FROM_PARENT) {
//					for (ParentRelation parentRelation: nextCommit.getChildRelations()) {
//						Commit child = parentRelation.getChild();
//						if (child != null) {
//							commitsToVisist.add(child);
//						}
//					}
//				}
//			}
//		}
//	}
}
