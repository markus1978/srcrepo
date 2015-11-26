package de.hub.srcrepo.repositorymodel.util

import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.fragmentation.Fragmentation
import de.hub.emffrag.fragmentation.FragmentationSet
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import org.eclipse.emf.common.util.URI

abstract class AbstractRepositoryModelMain {

	protected val FragmentationSet fs
	protected val Fragmentation fragmentation
	protected val RepositoryModelDirectory directory
	protected val URI modelURI
	
	new() {
		this("mongodb://localhost/git.eclipse.org")
	}
	
	new(String modelURI) {
		EmfFragActivator::standalone(RepositoryModelPackage.eINSTANCE)
		EmfFragMongoDBActivator::standalone()
		SrcRepoActivator::standalone

		this.modelURI = URI.createURI(modelURI)
		fs = new FragmentationSet(100 , [uri|DataStoreImpl::createDataStore(uri)] )		
		fragmentation = fs.getFragmentation(this.modelURI)		
		val contents = fragmentation.rootFragment.contents
		directory = if (contents.size == 0) null else contents.get(0) as RepositoryModelDirectory
	}
				
	protected final def run(String[] args) {
		perform(args)
		after()
	}
				
	protected abstract def void perform(String[] args)
		
	protected def void after() {
		fs.close()
	}
}