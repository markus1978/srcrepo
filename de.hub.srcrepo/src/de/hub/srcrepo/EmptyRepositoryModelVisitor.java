package de.hub.srcrepo;

import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public class EmptyRepositoryModelVisitor implements IRepositoryModelVisitor {

	@Override
	public void onBranch(Rev commonPreviousRev, Rev newBranchRev) {
		
	}

	@Override
	public boolean onStartRev(Rev rev, int number) {
		SrcRepoActivator.INSTANCE.info("Visit rev " + rev.getName() + " "   				
				+ "(" + number + "/" + ((RepositoryModel)rev.eContainer()).getAllRevs().size() + "), " );
		
		String author = rev.getAuthor(); author = author == null ? "[NO AUTHOR]" : author.trim();		
		String message = rev.getMessage(); message = message == null ? "[NO MESSAGE]" : message.trim();
		SrcRepoActivator.INSTANCE.info("Info for visited ref; " + rev.getTime() +", " + author + ":\n" + message);
		return false;
	}

	@Override
	public void onCompleteRev(Rev rev) {
		
	}

	@Override
	public void onCopiedFile(Diff diff) {
		
	}

	@Override
	public void onRenamedFile(Diff diff) {
		
	}

	@Override
	public void onAddedFile(Diff diff) {
		
	}

	@Override
	public void onModifiedFile(Diff diff) {
		
	}

	@Override
	public void onDeletedFile(Diff diff) {
		
	}

	@Override
	public void close(RepositoryModel repositoryModel) {
		
	}

	@Override
	public void onMerge2(Rev commonMergedRev, Rev lastBranchRev) {

	}

}
