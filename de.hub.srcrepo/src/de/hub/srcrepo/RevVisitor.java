package de.hub.srcrepo;

import java.util.HashMap;
import java.util.Map;

import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.Rev;

public abstract class RevVisitor extends AbstractRevVisitor {
	public Map<String, AbstractFileRef> files = new HashMap<String, AbstractFileRef>();
	
	protected abstract void onRev(Rev rev, Rev traversalParentRev, Map<String, AbstractFileRef> files);

	@Override
	protected Object getFile(AbstractFileRef fileRef) {
		return fileRef;
	}
	
	@Override
	protected void clearFiles() {
		files.clear();
	}
	@Override
	protected void addFile(String name, Object fileRef) {
		files.put(name,  (AbstractFileRef)fileRef);
	}
	@Override
	protected void removeFile(String name) {
		files.remove(name);
	}
	@Override
	protected void onRev(Rev rev, Rev traversalParentRev) {
		onRev(rev, traversalParentRev, files);
	}
}
