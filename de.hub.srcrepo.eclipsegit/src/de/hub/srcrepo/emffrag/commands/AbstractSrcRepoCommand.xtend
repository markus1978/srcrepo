package de.hub.srcrepo.emffrag.commands

import com.google.common.collect.AbstractIterator
import com.google.common.collect.FluentIterable
import de.hub.emffrag.EmfFragActivator
import de.hub.emffrag.Fragmentation
import de.hub.emffrag.FragmentationSet
import de.hub.emffrag.datastore.DataStoreImpl
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator
import de.hub.srcrepo.SrcRepoActivator
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage
import de.vandermeer.asciitable.v2.V2_AsciiTable
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer
import de.vandermeer.asciitable.v2.render.WidthLongestLine
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.emf.common.util.URI
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage
import org.json.JSONArray
import org.json.JSONObject

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
	
	protected def toCSV(JSONArray json) {
		if (json.length > 0) {
			val keys = json.getJSONObject(0).keySet.toList.sort
			return '''
				«FOR key:keys SEPARATOR ", "»«key»«ENDFOR»
				«FOR entry:json.toIterable»
					«FOR key:keys SEPARATOR ", "»«entry.get(key).toString»«ENDFOR»
				«ENDFOR»
			'''
		} else {
			return ''''''
		}
	}
	
	protected def toHumanReadable(JSONArray json) {
		if (json.length > 0) {
			val keys = json.getJSONObject(0).keySet.toList.sort
			val header = keys.toArray
			val data = json.toIterable.map[entry|keys.map[entry.get(it).toString].toArray]		
			
			val table = new V2_AsciiTable()
			table.addRow(header.toArray)
			table.addRule
			data.forEach[table.addRow(it.toArray)]
			val renderer = new V2_AsciiTableRenderer()
			renderer.width =  new WidthLongestLine
			return (renderer).render(table).toString			
		} else {
			return ""
		}	
	}
	
	protected def toIterable(JSONArray jsonArray) {
		return new FluentIterable<JSONObject> {			
			override iterator() {
				new AbstractIterator<JSONObject>() {
					var index = 0				
					override protected computeNext() {
						if (jsonArray.length > index) {
							return jsonArray.getJSONObject(index++)
						} else {
							endOfData
							return null
						}
					}					
				}
			}			
		}
	}
}