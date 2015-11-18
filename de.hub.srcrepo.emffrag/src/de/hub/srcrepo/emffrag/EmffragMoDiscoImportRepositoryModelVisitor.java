package de.hub.srcrepo.emffrag;

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
