package de.hub.srcrepo;

import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

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
		}
	}
	
	private void visitRev(Rev rev, int number) {
		Timer visitAllTimer = visitAllStat.timer();
		
		visitor.onStartRev(rev, null, number);
		
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
		visitor.onCompleteRev(rev, null);
		visitAllTimer.track();
	}	

	public static void traverse(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor) {
		new RepositoryModelFlatTraversal(repositoryModel, visitor).run();
	}	
}
