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
import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

class CreateGitHubUserRepositoriesCommand extends AbstractSrcRepoCommand {
	
	private def github(String resourcePath) {
		return Unirest.get('''https://api.github.com/«resourcePath»''').asJson.body
	}
	
	private def void createRepositoryModelDirectory(String userName) {
		val userJson = github('''users/«userName»''').getObject
		val repositoryModelDirectory = RepositoryModelFactory.eINSTANCE.createRepositoryModelDirectory
		repositoryModelDirectory.name = userJson.getString("name")
		repositoryModelDirectory.description = '''«repositoryModelDirectory.name» at GitHub: «userJson.getString("bio")»'''
		repositoryModelDirectory.url = userJson.getString("http_url")
		fragmentation.root = repositoryModelDirectory as FObject
		
		val reposJsonArray = github('''users/«userName»/repos''').getArray
		for (repoJsonAsObject: reposJsonArray) {
			val repoJson = repoJsonAsObject as JSONObject
			val repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel
			repositoryModelDirectory.repositories += repositoryModel
			repositoryModel.name = repoJson.getString("name")
			repositoryModel.description = '''«repoJson.getString("description")» [«repoJson.getString("updated_at")»]'''
			repositoryModel.url = repoJson.getString("http_url")
			repositoryModel.metaData.origin = repoJson.getString("git_url")
			
			val uri = URI.createURI('''«modelURI.scheme»://«modelURI.host»/«repositoryModel.qualifiedName»''')
			fs.getFragmentation(uri).root = repositoryModel	as FObject
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