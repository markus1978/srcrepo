package de.hub.srcrepo.emffrag.commands

import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.Fragmentation
import de.hub.emffrag.FragmentationSet
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.emf.common.util.URI
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage

abstract class AbstractSrcRepoCommand {

	static val defaultModelURI = "mongodb://localhost/EclipseAtGitHub"
	static val defaultCacheSize = 50

	protected var FragmentationSet fs
	protected var Fragmentation fragmentation
	protected var RepositoryModelDirectory directory
	protected var URI modelURI
	
	protected final def void init(CommandLine cl) {
		val metaModels = newArrayList(RepositoryModelPackage.eINSTANCE, JavaPackage.eINSTANCE)
		EmfFragActivator::standalone(metaModels)
		EmfFragActivator::instance.logInStandAlone = false;
		EmfFragMongoDBActivator::standalone()
		SrcRepoActivator::standalone

		this.modelURI = URI.createURI(if (cl.hasOption("m")) cl.getOptionValue("m") else defaultModelURI)
		fs = new FragmentationSet(metaModels, [uri|DataStoreImpl::createDataStore(uri)], if (cl.hasOption("cache")) Integer.parseInt(cl.getOptionValue("cache")) else defaultCacheSize)		
		fragmentation = fs.getFragmentation(this.modelURI)		
		val content = fragmentation.root
		if (content instanceof RepositoryModelDirectory) {
			directory = content as RepositoryModelDirectory		
		} else {
			directory = null
		}
	} 
		
	protected def addOptions(Options options) {
		options.addOption(Option.builder().longOpt("log").desc("Prints log output.").build)
		options.addOption(Option.builder("m").desc('''Uses the given model URI. Default is «defaultModelURI».''').longOpt("model").hasArg.build)
		options.addOption(Option.builder().desc('''Uses the given fragmentation cache size. Default is «defaultCacheSize».''').longOpt("cache").hasArg.build)
	}
	
	protected def checkIntArg(CommandLine cl, String name, int minimum) {
		if (cl.hasOption(name)) {
			try {
				return Integer.parseInt(cl.getOptionValue(name)) > minimum
			} catch (NumberFormatException e) {
				return false
			}			
		}
		return true
	}
	
	protected def validateOptions(CommandLine cl) {
		return checkIntArg(cl, "cache", 0)
	}
				
	protected abstract def void run(CommandLine cl)
		
	protected def void after() {
		fs.close()
	}
	
	
}