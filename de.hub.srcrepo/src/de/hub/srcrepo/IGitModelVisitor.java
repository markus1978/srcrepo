package de.hub.srcrepo;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;

public interface IGitModelVisitor {
	
	public boolean onStartCommit(Commit commit);
	
	public void onCompleteCommit(Commit commit);

	public void onCopiedFile(Diff diff);	

	public void onRenamedFile(Diff diff);
	
	public void onAddedFile(Diff diff);

	public void onModifiedFile(Diff diff);
	
	public void onDeletedFile(Diff diff);
}
