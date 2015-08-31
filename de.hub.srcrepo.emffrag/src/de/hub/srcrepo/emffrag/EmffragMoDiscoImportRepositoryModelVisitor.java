package de.hub.srcrepo.emffrag;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import de.hub.emffrag.fragmentation.FObject;
import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public class EmffragMoDiscoImportRepositoryModelVisitor extends MoDiscoRepositoryModelImportVisitor {

	public EmffragMoDiscoImportRepositoryModelVisitor(ISourceControlSystem sourceControlSystem, RepositoryModel repositoryModel, JavaPackage javaPackage) {
		super(sourceControlSystem, repositoryModel, javaPackage);		
	}

	@Override
	protected void reportImportError(EObject owner, String message, Throwable e, boolean controlledFail) {
		super.reportImportError(owner, message, e, controlledFail);
		// TODO save error in model
	}

	@Override
	public void onCompleteRev(Rev rev) {
		super.onCompleteRev(rev);
		((FObject)repositoryModel).fFragmentation().gc();
	}

	@Override
	public void close() {
		super.close();
		((FObject)repositoryModel).fFragmentation().close();
	}
}
