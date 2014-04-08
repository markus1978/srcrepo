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
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.Traversal;

public class SrcRepoUtil {
	
	public static void traverseRepository(RepositoryModel repositoryModel, Rev startRev, Rev[] stopRevs, IRepositoryModelVisitor visitor) {
		traverseRepository(repositoryModel, startRev, stopRevs, visitor, null, false, -1);
	}
	
	public static void traverseRepository(
			RepositoryModel repositoryModel, Rev startRev, Rev[] stopRevs, IRepositoryModelVisitor visitor, 
			Traversal traversal, boolean resume, int numberOfRevsToImport) {
		
		Collection<Rev> stopRevCollection = new HashSet<Rev>();
		Collections.addAll(stopRevCollection, stopRevs);
		if (startRev == null) {
			startRev = repositoryModel.getRootRev();
		}
		
		Queue<BranchPoint> branchPoints = new LinkedList<BranchPoint>();
		Collection<Rev> mergePoints = new HashSet<Rev>();		
		
		Rev currentRev = null;
		if (traversal != null && resume) {
			mergePoints.addAll(traversal.getMerges());
			for (de.hub.srcrepo.repositorymodel.BranchPoint branchPointModel: traversal.getRemaingBranchPoints()) {
				branchPoints.add(new BranchPoint(traversal.getCurrentBranchpoint()));
				branchPoints.add(new BranchPoint(branchPointModel));
			}
			currentRev = traversal.getNextRev();
		} else {		
			List<Rev> startBranches = new ArrayList<Rev>(1);
			startBranches.add(startRev);
			branchPoints.add(new BranchPoint(null, startBranches));
			currentRev = null;
		}
				
		int importedRevs = 0;
		while (!branchPoints.isEmpty()) {
			BranchPoint currentBranchPoint = branchPoints.poll();
			while (currentBranchPoint.hasNext()) {		
				if (currentRev == null) {
					Rev nextRev = currentBranchPoint.next();				
					visitor.onMerge(currentRev, currentBranchPoint.branchingRev);
					currentRev = nextRev;
				}
				
				boolean continueOnBranch = true;
				while (continueOnBranch) {
					if (stopRevCollection.contains(currentRev)) {
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
								currentRev = null;
							} else if (children.size() == 1) {
								currentRev = children.get(0);
							} else {
								BranchPoint branchPoint = new BranchPoint(currentRev, children);
								currentRev = branchPoint.next();							
								branchPoints.add(branchPoint);
							}
						}					
					}
					
					if (numberOfRevsToImport > 0 && ++importedRevs >= numberOfRevsToImport) {
						// save traversal	
						traversal.getRemaingBranchPoints().clear();
						traversal.getMerges().clear();
						traversal.setCurrentBranchpoint(null);
						traversal.setNextRev(null);
						
						RepositoryModelFactory factory = (RepositoryModelFactory)traversal.eClass().getEPackage().getEFactoryInstance();
						for(BranchPoint branchPoint: branchPoints) {
							de.hub.srcrepo.repositorymodel.BranchPoint branchPointModel = factory.createBranchPoint();
							branchPoint.saveToBranchPoinModel(branchPointModel);
							traversal.getRemaingBranchPoints().add(branchPointModel);
						}
						traversal.getMerges().addAll(mergePoints);
						BranchPoint currentBranchPointToSave = null;
						if (continueOnBranch || currentBranchPoint.hasNext()) {
							currentBranchPointToSave = currentBranchPoint;
							if (continueOnBranch) {
								traversal.setNextRev(currentRev);
							}
						} else {
							currentBranchPointToSave = branchPoints.poll();
						}
						de.hub.srcrepo.repositorymodel.BranchPoint branchPointModel = factory.createBranchPoint();
						currentBranchPointToSave.saveToBranchPoinModel(branchPointModel);
						traversal.setCurrentBranchpoint(branchPointModel);
						visitor.saveState(traversal);
						SrcRepoActivator.INSTANCE.info("Saved the traversal state, aborting traversal now.");
						return;
					}
				}
			}
		}
		
		repositoryModel.setTraversals(null);
		SrcRepoActivator.INSTANCE.info("Traversal complete, removed possible old traversal state from the model.");		
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
		
		public BranchPoint(de.hub.srcrepo.repositorymodel.BranchPoint branchPointModel) {
			branches = new ArrayList<Rev>();
			branches.addAll(branchPointModel.getChildren());
			branchingRev = branchPointModel.getParent();
			currentIndex = 0;
			for(Rev branchRev: branches) {
				if (branchPointModel.getNext() != branchRev) {
					currentIndex++;
				}
			}
		}

		boolean hasNext() {
			return branches.size() > currentIndex;
		}
		
		Rev next() {
			return branches.get(currentIndex++);
		}
		
		public void saveToBranchPoinModel(de.hub.srcrepo.repositorymodel.BranchPoint branchPointModel) {
			branchPointModel.getChildren().clear();
			branchPointModel.getChildren().addAll(branches);
			branchPointModel.setParent(branchingRev);
			branchPointModel.setNext(branches.get(currentIndex-1));
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
