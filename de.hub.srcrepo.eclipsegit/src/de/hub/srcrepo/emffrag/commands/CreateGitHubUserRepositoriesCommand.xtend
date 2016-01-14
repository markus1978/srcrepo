package de.hub.srcrepo.emffrag.commands

import com.mashape.unirest.http.Unirest
import de.hub.emffrag.FObject
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.emf.common.util.URI
import org.json.JSONObject

import static extension de.hub.srcrepo.RepositoryModelUtil.*

class CreateGitHubUserRepositoriesCommand extends AbstractSrcRepoCommand {
	
	private def github(String resourcePath) {
		return Unirest.get('''https://api.github.com/«resourcePath»''').basicAuth("markus1978", "FOGcq$W9").asJson.body
	}
	
	private def void createRepositoryModelDirectory(String userName) {
		val userJson = github('''users/«userName»''').getObject
		val repositoryModelDirectory = RepositoryModelFactory.eINSTANCE.createRepositoryModelDirectory
		repositoryModelDirectory.name = userJson.getString("name")
		repositoryModelDirectory.description = '''«repositoryModelDirectory.name» at GitHub: «userJson.get("bio")»'''
		repositoryModelDirectory.url = userJson.getString("html_url")
		fragmentation.root = repositoryModelDirectory as FObject
		
		var page = 1
		var continue = true
		while (continue) {
			val reposJsonArray = github('''users/«userName»/repos?page=«page»''').getArray
			continue = reposJsonArray.length > 0
			for (repoJsonAsObject: reposJsonArray) {
				val repoJson = repoJsonAsObject as JSONObject
				val repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel
				repositoryModelDirectory.repositories += repositoryModel
				repositoryModel.name = repoJson.getString("name")
				repositoryModel.description = '''«repoJson.getString("description")» [«repoJson.getString("updated_at")»]'''
				repositoryModel.url = repoJson.getString("html_url")
				repositoryModel.metaData.origin = repoJson.getString("git_url")
				repositoryModel.metaData.size = repoJson.getInt("size")
				
				val uri = URI.createURI('''«modelURI.scheme»://«modelURI.host»«modelURI.path».«repositoryModel.name»''')
				println('''Added repository «repositoryModel.name» to model and database («uri»).''')
				fs.getFragmentation(uri).root = repositoryModel	as FObject													
			}			
			page++
		}
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("u").longOpt("user").desc("The git hub user to retrive repos from. Default is 'eclipse'").hasArg.build)
	}
	
	override protected run(CommandLine cl) {
		createRepositoryModelDirectory(cl.getOptionValue("u", "eclipse"))
	}
	
}