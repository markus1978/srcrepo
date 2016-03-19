package de.hub.srcrepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

import de.hub.jstattrack.Statistics;
import de.hub.jstattrack.Statistics.UUID;
import de.hub.jstattrack.TimeStatistic;
import de.hub.jstattrack.TimeStatistic.Timer;
import de.hub.jstattrack.services.BatchedPlot;
import de.hub.jstattrack.services.Summary;
import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.DataSet;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.Rev;

public abstract class AbstractRevVisitor implements IRepositoryModelVisitor {
	public static final UUID revVisitET = Statistics.UUID(AbstractRevVisitor.class, "RevVisitET");
	public static final String traverseMetaData = "TraverseMetaData";
	
	private int i = 0;
	
	private Map<String, Object> files = new HashMap<String, Object>();
	private Map<Rev, Map<String, Object>> branches = new HashMap<Rev, Map<String, Object>>();

	public static final TimeStatistic revVisistETStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class)
			.with(BatchedPlot.class).register(revVisitET);
	private Timer revVisitETStatTimer = null;

	protected Rev lastVisitedRev = null;

	protected abstract void clearFiles();

	protected abstract void addFile(String name, Object file);

	protected abstract void removeFile(String name);

	protected abstract void onRev(Rev rev, Rev traversalParentRev);
	
	protected abstract Object getFile(AbstractFileRef fileRef);
	
	protected String revInfo(Rev rev) {
		return rev.getName() + " (" + i++ + "/" + ((RepositoryModel)rev.eContainer()).getAllRevs().size() + ")"; 
	}
 
	@Override
	public void onBranch(Rev commonPreviousRev, Rev newBranchRev) {
		if (commonPreviousRev == null) {
			// new root
			files.clear();
			clearFiles();
		} else {
			Map<String, Object> oldFiles = branches.get(commonPreviousRev);
			if (oldFiles == null) {
				// first time on a branch, the commonPreviousRev should be the
				// last visited revison, can keep all files and should save them for
				// traversal of other branches.
				Preconditions.checkArgument(commonPreviousRev == lastVisitedRev);
				branches.put(commonPreviousRev, new HashMap<String, Object>(files));
			} else {
				// real branch, load the files from the last common revision
				files.clear();
				clearFiles();
				files.putAll(oldFiles);
				for (Entry<String, Object> entry : oldFiles.entrySet()) {
					addFile(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	@Override
	public void onMerge(Rev commonMergedRev, Rev lastBranchRev) {
		// nothing to do
	}

	@Override
	public boolean onStartRev(Rev rev, Rev traversalParentRev, int number) {
		revVisitETStatTimer = revVisistETStat.timer();
		return false;
	}

	@Override
	public void onCompleteRev(Rev rev, Rev traversalParentRev) {
		try {
			onRev(rev, traversalParentRev);
		} catch (Exception e) {
			SrcRepoActivator.INSTANCE.error("Error in visiting rev: " + rev.getName(), e);
		}
		lastVisitedRev = rev;
		revVisitETStatTimer.track();
	}

	@Override
	public void onCopiedFile(Diff diff) {
		AbstractFileRef ref = diff.getFile();
		if (ref != null) {
			Object file = getFile(ref);
			if (file != null) {
				files.put(diff.getNewPath(), file);
				addFile(diff.getNewPath(), file);
			}
		}
	}

	@Override
	public void onRenamedFile(Diff diff) {
		AbstractFileRef ref = diff.getFile();
		removeFile(diff.getOldPath());
		if (ref != null) {
			Object file = getFile(ref);
			if (file != null) {
				files.put(diff.getNewPath(), file);
				addFile(diff.getNewPath(), file);
			}
		}
	}

	@Override
	public void onAddedFile(Diff diff) {
		AbstractFileRef ref = diff.getFile();
		if (ref != null) {
			Object file = getFile(ref);
			if (file != null) {
				files.put(diff.getNewPath(), file);
				addFile(diff.getNewPath(), file);
			}
		}
	}

	@Override
	public void onModifiedFile(Diff diff) {
		AbstractFileRef ref = diff.getFile();
		files.remove(diff.getOldPath());
		removeFile(diff.getOldPath());
		if (ref != null) {
			Object file = getFile(ref);
			if (file != null) {
				files.put(diff.getNewPath(), file);
				addFile(diff.getNewPath(), file);
			}
		}
	}

	@Override
	public void onDeletedFile(Diff diff) {
		files.remove(diff.getOldPath());
		removeFile(diff.getOldPath());
	}

	@Override
	public void close(RepositoryModel repositoryModel) {
		RepositoryModelFactory factory = (RepositoryModelFactory) repositoryModel.eClass().getEPackage()
				.getEFactoryInstance();
		DataSet dataSet = RepositoryModelUtil.getData(repositoryModel, traverseMetaData);
		if (dataSet == null) {
			dataSet = factory.createDataSet();
			dataSet.setName(traverseMetaData);
			repositoryModel.getDataSets().add(dataSet);
		}

		updateTraverseMetaData(dataSet);
	}

	protected void updateTraverseMetaData(DataSet dataSet) {
		dataSet.setJsonData(Statistics.reportToJSON().toString(1));
	}
}
