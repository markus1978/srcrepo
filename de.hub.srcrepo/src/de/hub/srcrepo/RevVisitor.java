package de.hub.srcrepo;

import java.util.HashMap;
import java.util.Map;

import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.TraversalState;

public abstract class RevVisitor implements IRepositoryModelVisitor {
	
	public Map<Rev, Map<String, AbstractFileRef>> branches = new HashMap<Rev, Map<String,AbstractFileRef>>();
	public Map<String, AbstractFileRef> files = new HashMap<String, AbstractFileRef>();
	
	protected abstract void onRev(Rev rev, Map<String, AbstractFileRef> files);

	@Override
	public void onMerge(Rev mergeRev, Rev branchRev) {
		Map<String, AbstractFileRef> oldFiles = branches.get(branchRev);
		if (oldFiles == null) {			
			branches.put(branchRev, new HashMap<String, AbstractFileRef>(files));	
		} else {
			files.clear();			
			files.putAll(oldFiles);
		}		
	}


	@Override
	public boolean onStartRev(Rev rev) {
		return false;
	}

	@Override
	public void onCompleteRev(Rev rev) {
		onRev(rev, files);
	}

	@Override
	public void onCopiedFile(Diff diff) {
		AbstractFileRef file = diff.getFile();
		if (file != null) {
			files.put(diff.getNewPath(), file);
		}
	}

	@Override
	public void onRenamedFile(Diff diff) {
		AbstractFileRef file = diff.getFile();
		files.remove(diff.getOldPath());
		if (file != null) {
			files.put(diff.getNewPath(), file);
		}
	}

	@Override
	public void onAddedFile(Diff diff) {
		AbstractFileRef file = diff.getFile();
		if (file != null) {
			files.put(diff.getNewPath(), file);
		}
	}

	@Override
	public void onModifiedFile(Diff diff) {
		AbstractFileRef file = diff.getFile();
		if (file != null) {
			files.put(diff.getNewPath(), file);
		} else {
			files.remove(diff.getOldPath());
		}
	}

	@Override
	public void onDeletedFile(Diff diff) {
		files.remove(diff.getOldPath());
	}

	@Override
	public void loadState(TraversalState traversal) {
		
	}

	@Override
	public void saveState(TraversalState traversal) {
		
	}

	@Override
	public void close() {
		
	}
}
