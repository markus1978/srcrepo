package de.hub.srcrepo;

import java.io.File;

import de.hub.srcrepo.repositorymodel.RepositoryModel;

public interface ISourceControlSystem {
	
	public static class SourceControlException extends Exception {

		private static final long serialVersionUID = 1L;

		public SourceControlException() {
			super();
		}

		public SourceControlException(String message, Throwable cause) {
			super(message, cause);
		}

		public SourceControlException(String message) {
			super(message);
		}

		public SourceControlException(Throwable cause) {
			super(cause);		
		}
		
	}

	public void createWorkingCopy(File target, String url) throws SourceControlException;
	public void setWorkingCopy(File target) throws SourceControlException;
	public File getWorkingCopy();
	public void importRevisions(RepositoryModel model) throws SourceControlException;
	public void checkoutRevision(String name) throws SourceControlException;	
	public void close();
	
}
