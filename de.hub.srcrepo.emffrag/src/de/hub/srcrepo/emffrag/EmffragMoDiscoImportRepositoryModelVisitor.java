package de.hub.srcrepo.emffrag;

import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor;
import de.hub.srcrepo.repositorymodel.RepositoryModel;

public class EmffragMoDiscoImportRepositoryModelVisitor extends MoDiscoRepositoryModelImportVisitor {

	public EmffragMoDiscoImportRepositoryModelVisitor(ISourceControlSystem sourceControlSystem, RepositoryModel repositoryModel, JavaPackage javaPackage) {
		super(sourceControlSystem, repositoryModel, javaPackage);	
	}

	@Override
	public void close() {
		super.close();
	}	
		
}
