package de.hub.srcrepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public class SrcRepoUtil {
	
	public static void traverseRepository(RepositoryModel repositoryModel, Rev startRev, Rev[] stopRevs, IRepositoryModelVisitor visitor) {	
		Collection<Rev> stopRevCollection = new HashSet<Rev>();
		Collections.addAll(stopRevCollection, stopRevs);
		if (startRev == null) {
			startRev = repositoryModel.getRootRev();
		}
		
		Queue<BranchPoint> branchPoints = new LinkedList<BranchPoint>();
		Collection<Rev> mergePoints = new HashSet<Rev>();
		
		List<Rev> startBranches = new ArrayList<Rev>(1);
		startBranches.add(startRev);
		branchPoints.add(new BranchPoint(null, startBranches));
		
		Rev currentRev = null;
		while (!branchPoints.isEmpty()) {
			BranchPoint currentBranchPoint = branchPoints.poll();
			while (currentBranchPoint.hasNext()) {				
				Rev nextRev = currentBranchPoint.next();
				
				visitor.onMerge(currentRev, currentBranchPoint.branchingRev);
				currentRev = nextRev;
				
				boolean continueOnBranch = true;
				while (continueOnBranch) {
					if (stopRevCollection.contains(nextRev)) {
						continueOnBranch = false;
					} else {
						EList<ParentRelation> parentRelations = currentRev.getParentRelations();
						boolean isMerge = parentRelations.size() > 1;
						if (isMerge && (mergePoints.contains(currentRev))) {							
							continueOnBranch = false;						
						} else {					
							continueOnBranch = visitor.onStartRev(currentRev);
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
							visitor.onCompleteRev(currentRev);
						
							if (isMerge) {
								mergePoints.add(currentRev);
							}
							
							List<Rev> children = getChildren(currentRev);
							if (children.size() == 0) {
								continueOnBranch = false;
							} else {
								BranchPoint branchPoint = new BranchPoint(currentRev, children);
								currentRev = branchPoint.next();							
								branchPoints.add(branchPoint);
							}
						}					
					}
				}
			}
		}
	}
	
	private static class BranchPoint {
		private final Rev branchingRev;
		private final List<Rev> branches;
		private int currentIndex;
		
		public BranchPoint(Rev branchingRev, List<Rev> branches) {
			this.branchingRev = branchingRev;
			this.branches = branches;
			this.currentIndex = 0;
		}
		
		boolean hasNext() {
			return branches.size() > currentIndex;
		}
		
		Rev next() {
			return branches.get(currentIndex++);
		}
	}
	
	private static List<Rev> getChildren(Rev rev) {
		EList<ParentRelation> childRelations = rev.getChildRelations();
		List<Rev> result = new ArrayList<Rev>(childRelations.size());
		for (ParentRelation parentRelation: childRelations) {
			Rev child = parentRelation.getChild();
			if (child != null) {
				result.add(child);
			}
		}
		return result;
	}
	
	
}
