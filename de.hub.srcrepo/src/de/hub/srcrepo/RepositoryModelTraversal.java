package de.hub.srcrepo;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import com.google.common.base.Preconditions;

import de.hub.jstattrack.Statistics;
import de.hub.jstattrack.TimeStatistic;
import de.hub.jstattrack.TimeStatistic.Timer;
import de.hub.jstattrack.ValueStatistic;
import de.hub.jstattrack.services.BatchedPlot;
import de.hub.jstattrack.services.Histogram;
import de.hub.jstattrack.services.Summary;
import de.hub.jstattrack.services.WindowedPlot;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public class RepositoryModelTraversal {
	
	private class Branch {
		private final Rev lastCommonRev;
		private final Rev firstBranchRev;
		public Branch(Rev lastCommonRev, Rev firstBranchRev) {
			super();
			this.lastCommonRev = lastCommonRev;
			this.firstBranchRev = firstBranchRev;
		}		
	}
	
	public final static TimeStatistic visitFullETStat = new TimeStatistic(TimeUnit.MICROSECONDS)
			.with(Summary.class).with(BatchedPlot.class).with(new WindowedPlot(100)).with(Histogram.class)
			.register(RepositoryModelTraversal.class, "Visit time");
	
	public final static ValueStatistic usedMemoryStat = new ValueStatistic("b")
			.with(BatchedPlot.class).register(RepositoryModelTraversal.class, "Used heap memory.");
	
	private final RepositoryModel repositoryModel;
	private final IRepositoryModelVisitor visitor;
	
	private Stack<Branch> openBranches = new Stack<Branch>();
	private Collection<Rev> traversedRevs = new HashSet<Rev>();
	
	public RepositoryModelTraversal(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor) {
		super();
		this.repositoryModel = repositoryModel;
		this.visitor = visitor;
	}
	
	private Stats stats = new Stats();
	
	public class Stats {
		public int importedRevsCounter = 0;
		public int mergeCounter = 0;
		public int branchCounter = 0;
		
		void reset() {
			importedRevsCounter = 0;
			mergeCounter = 0;
			branchCounter = 0;
		}
	}

	public Stats run() {
		openBranches.clear();
		traversedRevs.clear();
		
		int count = 0;
		stats.reset();
		stats.importedRevsCounter = 0;		
		
		for (Rev rootRev: repositoryModel.getRootRevs()) {
			openBranches.add(new Branch(null, rootRev));
			stats.branchCounter++;
		}		
		
		while(!openBranches.isEmpty()) {			
			Branch branch = openBranches.pop();
			visitor.onBranch(branch.lastCommonRev, branch.firstBranchRev);
			Rev rev = branch.firstBranchRev;	
			Rev lastRev = branch.lastCommonRev;
			
			branchLoop: while(true) {
				int children = rev.getChildRelations().size();
				int parents = parentCount(rev);
				
				// is merge?
				if (parents > 1) {
					if (traversedRevs.contains(rev)) {
						stats.mergeCounter++;
						visitor.onMerge(rev, lastRev);
						break branchLoop;	
					}
				}
							
				// import current rev
				visitRev(rev, lastRev, ++stats.importedRevsCounter);
				traversedRevs.add(rev);
				count++;
				
				// print performance data	
				if (count % 1000 == 0 && count != 0) {
					// run gc, after that measure memory
					for (int i = 0; i < 2; i++) {
						Object obj = new Object();
						WeakReference<?> ref = new WeakReference<Object>(obj);
						obj = null;
						while (ref.get() != null) {
							System.gc();
						}
						System.runFinalization();
					}
					long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
					usedMemoryStat.track(usedMemory);
				}
				
				if (count % 1000 == 0 && count != 0) {
					try {
						String json = Statistics.reportToJSON().toString();
						PrintWriter out = new PrintWriter("/tmp/srcrepo.json");
						out.print(json);
						out.close();
					} catch (Exception e) {
						SrcRepoActivator.INSTANCE.warning("Could not write json! " + e.getMessage(), e);
					}					
				}
				
				// is branch or branch complete: determine next rev
				lastRev = rev;
				if (children == 1) {
					rev = rev.getChildRelations().get(0).getChild();
				} else if (children > 1) {
					Rev firstChildRev = rev.getChildRelations().get(0).getChild();
					for (ParentRelation childRelation : rev.getChildRelations()) {
						Rev childRev = childRelation.getChild();
						if (childRev != firstChildRev) {
							openBranches.add(new Branch(rev, childRev));
						} else {
							visitor.onBranch(rev, childRev);
						}
						stats.branchCounter++;
					}
					rev = firstChildRev;
				} else {
					// branch completed
					break branchLoop;
				}
			}						
		}	
		return stats;
	}
	
	private int parentCount(Rev rev) {
		int result = 0;
		for(ParentRelation parentRelation: rev.getParentRelations()) {
			if (parentRelation.getParent() != null) {
				result++;
			}
		}
		return result;
	}
	
	
	private void visitRev(Rev rev, Rev traverseParentRev, int number) {
		Preconditions.checkArgument(!traversedRevs.contains(rev));
		Timer visitAllTimer = visitFullETStat.timer();
		visitor.onStartRev(rev,traverseParentRev, number);
		
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
		visitor.onCompleteRev(rev, traverseParentRev);
		visitAllTimer.track();
	}	

	public static Stats traverse(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor) {
		return new RepositoryModelTraversal(repositoryModel, visitor).run();
	}	
}
