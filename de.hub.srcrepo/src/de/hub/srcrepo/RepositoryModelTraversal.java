package de.hub.srcrepo;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.IRepositoryModelVisitor.ETMExtension;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;
import etm.contrib.console.HttpConsoleServer;
import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

public class RepositoryModelTraversal {
	
	private final RepositoryModel repositoryModel;
	private final IRepositoryModelVisitor visitor;
	
	private Stack<Rev> openBranches = new Stack<Rev>();
	private Collection<Rev> completedBranches = new HashSet<Rev>();
	private Collection<Rev> merges = new HashSet<Rev>();
	private Rev lastImportedRev = null;
	private final EtmMonitor etmMonitor;
	
	public RepositoryModelTraversal(RepositoryModel repositoryModel,
			IRepositoryModelVisitor visitor) {
		super();
		this.repositoryModel = repositoryModel;
		this.visitor = visitor;
		BasicEtmConfigurator.configure();
		this.etmMonitor = EtmManager.getEtmMonitor();
		if (visitor instanceof ETMExtension) {
			((ETMExtension)visitor).setETMMonitor(etmMonitor);
		}
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
		etmMonitor.start();
		HttpConsoleServer etmServer = new HttpConsoleServer(etmMonitor);
		etmServer.setListenPort(45000);
		etmServer.start();
		
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
				if (count % 100 == 0 && count != 0) {
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					etmMonitor.render(new SimpleTextRenderer(new OutputStreamWriter(byteArrayOutputStream)));
					SrcRepoActivator.INSTANCE.info(byteArrayOutputStream.toString());
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
		
		etmMonitor.stop();
		etmServer.stop();
		
		return stats;
	}
	
	private void visitRev(Rev rev, int number) {
		EtmPoint visitPoint = etmMonitor.createPoint("Rev:whole_visit");
		
		EtmPoint startPoint = etmMonitor.createPoint("Rev:start");
		visitor.onStartRev(rev, number);
		startPoint.collect();
		
		for (ParentRelation parentRelation: rev.getParentRelations()) {
			for (Diff diff : parentRelation.getDiffs()) {
				EtmPoint diffPoint = etmMonitor.createPoint("Rev:diff");
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
				diffPoint.collect();
			}
		}
		EtmPoint completePoint = etmMonitor.createPoint("Rev:complete");
		visitor.onCompleteRev(rev);
		completePoint.collect();
		lastImportedRev = rev;
		visitPoint.collect();
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

	public static Stats traverse(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor, TraversalState state, boolean resume, int stopAfterNumberOfRevs) {
		return new RepositoryModelTraversal(repositoryModel, visitor).run(resume, stopAfterNumberOfRevs > 0, state, stopAfterNumberOfRevs);
	}

	public static Stats traverse(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor) {
		return traverse(repositoryModel, visitor, null, false, -1);		
	}
	
}
