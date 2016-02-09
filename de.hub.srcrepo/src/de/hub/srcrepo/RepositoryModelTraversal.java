package de.hub.srcrepo;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

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
import de.hub.srcrepo.repositorymodel.TraversalState;

public class RepositoryModelTraversal {
	
	private final static TimeStatistic visitAllStat = 
			new TimeStatistic(TimeUnit.MICROSECONDS)
			.with(Summary.class).with(BatchedPlot.class).with(new WindowedPlot(100)).with(Histogram.class)
			.register(RepositoryModelTraversal.class, "Visit time");
	
	private final static ValueStatistic usedMemoryStat = new ValueStatistic("b").with(BatchedPlot.class).register(RepositoryModelTraversal.class, "Used heap memory.");
	
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
	
	private Stats stats = new Stats();
	
	public class Stats {
		public int importedRevsCounter = 0;
		public int mergeCounter = 0;
		public int branchCounter = 0;
		public int openBranchCounter = 0;
		
		void reset() {
			importedRevsCounter = 0;
			mergeCounter = 0;
			branchCounter = 0;
			openBranchCounter = 0;
		}
	}

	public Stats run(boolean resume, boolean save, TraversalState state, int numberOfRevsToImport) {			
		openBranches.clear();
		completedBranches.clear();
		merges.clear();
		lastImportedRev = null;
		
		int count = 0;
		stats.reset();
		stats.importedRevsCounter = state == null ? 0 : state.getNumberOfImportedRevs();
		int maxRevCount = numberOfRevsToImport > 0 ? numberOfRevsToImport : Integer.MAX_VALUE;		
		
		if (resume) {
			openBranches.addAll(state.getOpenBranches());
			completedBranches.addAll(state.getCompletedBranches());
			stats.branchCounter = openBranches.size() + completedBranches.size();
			merges.addAll(state.getMerges());
			stats.mergeCounter = merges.size();
		} else {
			openBranches.addAll(repositoryModel.getRootRevs());
			stats.branchCounter = repositoryModel.getRootRevs().size();
		}
		
		branchesLoop: while(!openBranches.isEmpty()) {			
			Rev rev = openBranches.pop();
			visitor.onMerge(lastImportedRev, rev);
			branchLoop: while(true) {
				int children = rev.getChildRelations().size();
				int parents = RepositoryModelUtil.parents(rev);
				
				// is merge?
				if (parents > 1 && !onMerge(rev)) {
					stats.mergeCounter++;
					break branchLoop;	
				}
							
				// import current rev
				visitRev(rev, ++stats.importedRevsCounter);
				count++;
				
				// print performance data	
				if (count % 50 == 0 && count != 0) {
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
				if (children == 1) {
					rev = rev.getChildRelations().get(0).getChild();
				} else if (children > 1) {
					rev = onBranch(rev);						
				} else {
					stats.openBranchCounter++;
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
						state.setNumberOfImportedRevs(stats.importedRevsCounter);
					}
					break branchesLoop;
				}
				
				// is branch completed?
				if (rev == null) {
					break branchLoop;
				}
			}						
		}	
		return stats;
	}
	
	private void visitRev(Rev rev, int number) {
		Timer visitAllTimer = visitAllStat.timer();
		visitor.onStartRev(rev, number);
		
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
		visitAllTimer.track();
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
					if (!openBranches.contains(child)) {
						openBranches.push(child);
						stats.branchCounter++;
					}
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

	public static Stats traverse(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor, TraversalState state, boolean resume, boolean saveSate, int stopAfterNumberOfRevs) {
		return new RepositoryModelTraversal(repositoryModel, visitor).run(resume, saveSate, state, stopAfterNumberOfRevs);
	}

	public static Stats traverse(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor) {
		return traverse(repositoryModel, visitor, null, false, false, -1);		
	}
	
}
