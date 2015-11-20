package de.hub.srcrepo.eclipsegit

import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.fragmentation.Fragmentation
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import org.eclipse.emf.common.util.URI

class ScheduleImports {
	
	def void run(String modelURI) {
		EmfFragActivator::standalone(RepositoryModelPackage.eINSTANCE)
		EmfFragMongoDBActivator::standalone()
		SrcRepoActivator::standalone
		val fragmentation = new Fragmentation(DataStoreImpl::createDataStore(URI.createURI(modelURI)), 100)
		
		val directory = fragmentation.rootFragment.contents.get(0) as RepositoryModelDirectory
		
		directory.subDirectories.findFirst[it.name == "jdt"].repositories.forEach[
			val importMetaData = it.metaData.importMetaData
			if (!importMetaData.imported && !importMetaData.importing) {				
				importMetaData.scheduled = true				
			}
		]
		
		fragmentation.close()
	}
	
	static def void main(String[] args) {
		val instance = new ScheduleImports()
		instance.run("mongodb://localhost/git.eclipse.org")
	}
}