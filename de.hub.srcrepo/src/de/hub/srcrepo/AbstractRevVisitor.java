package de.hub.srcrepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import de.hub.jstattrack.Statistics;
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
	public static final String TRAVERSE_METADATA_KEY = "TraverseMetaData";
	private Map<String, AbstractFileRef> files = new HashMap<String, AbstractFileRef>();
	private Map<Rev, Map<String, AbstractFileRef>> branches = new HashMap<Rev, Map<String,AbstractFileRef>>();
	
	private static final TimeStatistic revVisistETStat = new TimeStatistic(TimeUnit.MICROSECONDS).with(Summary.class).with(BatchedPlot.class).register(AbstractRevVisitor.class, "RevVisitET");
	private Timer revVisitETStatTimer = null;

	protected abstract void clearFiles();
	protected abstract void addFile(String name, AbstractFileRef fileRef);
	protected abstract void removeFile(String name);
	protected abstract void onRev(Rev rev);
	
	@Override
	public void onMerge(Rev mergeRev, Rev branchRev) {
		Map<String, AbstractFileRef> oldFiles = branches.get(branchRev);
		if (mergeRev == null) {			
			branches.put(branchRev, new HashMap<String, AbstractFileRef>(files));	
		} else {
			files.clear();
			clearFiles();
			files.putAll(oldFiles);
			for(Entry<String, AbstractFileRef> entry: oldFiles.entrySet()) {
				addFile(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public boolean onStartRev(Rev rev, int number) {
		revVisitETStatTimer = revVisistETStat.timer();
		return false;
	}

	@Override
	public void onCompleteRev(Rev rev) {
		onRev(rev);
		revVisitETStatTimer.track();
	}

	@Override
	public void onCopiedFile(Diff diff) {
		AbstractFileRef file = diff.getFile();
		if (file != null) {
			files.put(diff.getNewPath(), file);
			addFile(diff.getNewPath(), file);		
		}
	}

	@Override
	public void onRenamedFile(Diff diff) {
		AbstractFileRef file = diff.getFile();
		removeFile(diff.getOldPath());
		if (file != null) {
			files.put(diff.getNewPath(), file);
			addFile(diff.getNewPath(), file);
		}
	}

	@Override
	public void onAddedFile(Diff diff) {
		AbstractFileRef file = diff.getFile();
		if (file != null) {
			files.put(diff.getNewPath(), file);
			addFile(diff.getNewPath(), file);
		}
	}

	@Override
	public void onModifiedFile(Diff diff) {
		AbstractFileRef file = diff.getFile();
		if (!diff.getNewPath().equals(diff.getOldPath())) {
			files.remove(diff.getOldPath());
			removeFile(diff.getOldPath());			
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
		RepositoryModelFactory factory = (RepositoryModelFactory)repositoryModel.eClass().getEPackage().getEFactoryInstance();
		DataSet dataSet = RepositoryModelUtil.getData(repositoryModel, TRAVERSE_METADATA_KEY);
		if (dataSet == null) {
			dataSet = factory.createDataSet();
			dataSet.setName(TRAVERSE_METADATA_KEY);
			repositoryModel.getDataSets().add(dataSet);
		}
		
		updateTraverseMetaData(dataSet);
	}
	
	protected void updateTraverseMetaData(DataSet dataSet) {
		dataSet.setJsonData(Statistics.reportToJSON().toString(1));
	}
}
