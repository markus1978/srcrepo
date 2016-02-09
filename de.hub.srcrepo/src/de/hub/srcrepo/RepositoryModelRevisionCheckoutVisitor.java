package de.hub.srcrepo;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.Rev;

/**
 * This visitor checks out each rev.
 */
public class RepositoryModelRevisionCheckoutVisitor implements IRepositoryModelVisitor {

	protected final ISourceControlSystem sourceControlSystem;	
	protected final RepositoryModel repositoryModel;
	protected final RepositoryModelFactory repositoryFactory;
	
	// volatile	
	private Rev currentRev;	
	
	public RepositoryModelRevisionCheckoutVisitor(ISourceControlSystem sourceControlSystem, RepositoryModel repositoryModel) {		
		this.sourceControlSystem = sourceControlSystem;
		this.repositoryFactory = (RepositoryModelFactory)repositoryModel.eClass().getEPackage().getEFactoryInstance();
		this.repositoryModel = repositoryModel;	
	}

	@Override
	public void onMerge2(Rev mergeRev, Rev branchRev) {
		
	}
	
	@Override
	public void onBranch(Rev commonPreviousRev, Rev newBranchRev) {
		
	}

	private void runJob(WorkspaceJob job) {
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		try {
			job.join();
		} catch (InterruptedException e) {
			new RuntimeException("Interupt during job execution. Impossible.", e);
		}
		IStatus result = job.getResult();
		if (result == null) {
			throw new RuntimeException("Job without result after joined execution. Impossible.");
		}
		if (!result.isOK()) {
			if (result.matches(IStatus.ERROR)) {
				Throwable e = result.getException();
				reportImportError(currentRev, "Unexpected error during job execution [" + job.getName() + "]", e, false);
			} else {
				reportImportError(currentRev, "Job with unexpected status [" + job.getName() + "]", null, false);
			}
		}
	}

	@Override
	public boolean onStartRev(final Rev rev, int number) {		
	
		SrcRepoActivator.INSTANCE.info("Visit rev " + rev.getName() + " "   				
				+ "(" + number + "/" + repositoryModel.getAllRevs().size() + ")");
		
		String author = rev.getAuthor(); author = author == null ? "[NO AUTHOR]" : author.trim();		
		String message = rev.getMessage(); message = message == null ? "[NO MESSAGE]" : message.trim();
		SrcRepoActivator.INSTANCE.info("Info for visited ref; " + rev.getTime() +", " + author + ":\n" + message);
		
		// move current ref to the new ref
		currentRev = rev;
		
		// checkout the new ref
		runJob(new CheckoutJob());		
		
		return true;
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
		onModifiedFile(diff);
	}

	@Override
	public void onModifiedFile(final Diff diff) {
	}

	@Override
	public void onDeletedFile(Diff diff) {
		
	}	
	
	private class CheckoutJob extends WorkspaceJob {
		
		public CheckoutJob() {
			super(RepositoryModelRevisionCheckoutVisitor.class.getName() + " git checkout.");
		}		

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {		
			// checkout
			try {
				sourceControlSystem.checkoutRevision(currentRev.getName());				
			} catch (SourceControlException e) {							
				reportImportError(currentRev, "Error while checking out.", e, true);
			} catch (Exception e) {
				reportImportError(currentRev, "Exception while checking out.", e, true);
			}
			
			return Status.OK_STATUS;
		}		
	}
		
	protected void reportImportError(EObject owner, String message, Throwable e, boolean controlledFail) {
		if (e != null) {
			message += ": " + e.getMessage() + "[" + e.getClass().getCanonicalName() + "].";
		}
		if (controlledFail) {			
			SrcRepoActivator.INSTANCE.warning(message, (Exception)e);
		} else {
			SrcRepoActivator.INSTANCE.error(message, (Exception)e);
		}
	}

	@Override
	public void close(RepositoryModel repositoryModel) {
		
	}
}
