package de.hub.srcrepo;

import de.hub.srcrepo.gitmodel.Diff;

public interface IGitModelImportHandler<D extends Diff> {
	
	public void onStartCommit();
	
	public void onCompleteCommit();

	public void onCopiedFile(D diff);	

	public void onRenamedFile(D diff);
	
	public void onAddedFile(D diff);

	public void onModifiedFile(D diff);
	
	public void onDeletedFile(D diff);
}
