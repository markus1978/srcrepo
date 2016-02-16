package de.hub.srcrepo.emffrag.examples;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

import de.hub.emffrag.EmfFragActivator;
import de.hub.srcrepo.MoDiscoRevVisitor;
import de.hub.srcrepo.RepositoryModelTraversal;
import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;
import de.hub.srcrepo.snapshot.IModiscoSnapshotModel;

public class AnalyzeImportedRepositories {

	public static void main(String[] args) {
		EmfFragActivator.standalone(EcorePackage.eINSTANCE, RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE);
		SrcRepoActivator.standalone();
		
		URI repositoryURI = URI.createURI("mongodb://jupiter.informatik.hu-berlin.de/org.eclipse.emf.java.bin");
		ResourceSet rs = new ResourceSetImpl();
		Resource resource = rs.getResource(repositoryURI, true);
		RepositoryModel repositoryModel = (RepositoryModel)resource.getContents().get(0);
		
		RepositoryModelTraversal.traverse(repositoryModel, new MoDiscoRevVisitor(org.eclipse.gmt.modisco.java.emf.JavaPackage.eINSTANCE) {
				int i = 0;
				@Override
				protected void onRevWithSnapshot(Rev rev, Rev traversalParentRev, Map<String, IModiscoSnapshotModel> snapshot) {
					System.out.println("#" + i++);
				}		      		    
		});
	}
}
