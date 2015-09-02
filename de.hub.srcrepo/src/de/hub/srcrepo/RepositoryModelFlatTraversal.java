package de.hub.srcrepo;

import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.jstattrack.Statistics;
import de.hub.jstattrack.TimeStatistic;
import de.hub.jstattrack.TimeStatistic.Timer;
import de.hub.jstattrack.services.BatchedPlot;
import de.hub.jstattrack.services.Summary;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public class RepositoryModelFlatTraversal {
	
	private final static TimeStatistic visitAllStat = new TimeStatistic(TimeUnit.MILLISECONDS).with(Summary.class).with(BatchedPlot.class).register(RepositoryModelFlatTraversal.class, "Visit all time");
	private final static TimeStatistic visitStartStat = new TimeStatistic(TimeUnit.MILLISECONDS).with(Summary.class).register(RepositoryModelFlatTraversal.class, "Visit start time");
	private final static TimeStatistic visitDiffStat = new TimeStatistic(TimeUnit.MILLISECONDS).with(Summary.class).register(RepositoryModelFlatTraversal.class, "Visit diff time");
	private final static TimeStatistic visitCompleteStat = new TimeStatistic(TimeUnit.MILLISECONDS).with(Summary.class).register(RepositoryModelFlatTraversal.class, "Visit complete time");
	
	private final RepositoryModel repositoryModel;
	private final IRepositoryModelVisitor visitor;
	
	public RepositoryModelFlatTraversal(RepositoryModel repositoryModel,
			IRepositoryModelVisitor visitor) {
		super();
		this.repositoryModel = repositoryModel;
		this.visitor = visitor;		
	}

	public void run() {		
		int count = 0;		
		
		for(Rev rev: repositoryModel.getAllRevs()) {
			visitRev(rev, count++);
			
			if (count % 100 == 0 && count != 0) {			
				SrcRepoActivator.INSTANCE.info(Statistics.reportToString());
			}
		}
	}
	
	private void visitRev(Rev rev, int number) {
		Timer visitAllTimer = visitAllStat.timer();
		
		Timer visitStartTimer = visitStartStat.timer();
		visitor.onStartRev(rev, number);
		visitStartTimer.track();
		
		for (ParentRelation parentRelation: rev.getParentRelations()) {
			for (Diff diff : parentRelation.getDiffs()) {
				Timer visitDiffTimer = visitDiffStat.timer();
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
				visitDiffTimer.track();
			}
		}
		Timer visitCompleteTimer = visitCompleteStat.timer();
		visitor.onCompleteRev(rev);
		visitCompleteTimer.track();
		visitAllTimer.track();
	}	

	public static void traverse(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor) {
		new RepositoryModelFlatTraversal(repositoryModel, visitor).run();
	}	
}
