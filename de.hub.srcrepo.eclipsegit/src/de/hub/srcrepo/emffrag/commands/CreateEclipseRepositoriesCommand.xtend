package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory
import java.net.URL
import java.util.List
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.regex.Pattern
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.eclipse.emf.common.util.URI
import org.jsoup.Jsoup

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*
import static extension de.hub.srcrepo.RepositoryModelUtil.*

class CreateEclipseRepositoriesCommand extends AbstractSrcRepoCommand {
	
	val timeout = 30000
	val executor = Executors::newFixedThreadPool(50)
	
	private def createRepository(URL repositoryURL, String name, String description) {
		val repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel
		repositoryModel.name = if (name==null) repositoryURL.toString else name
		repositoryModel.description = description
		
		val document = try {
			Jsoup::parse(repositoryURL, timeout)
		} catch (Exception e) {
			System.err.println("Could not import an apparent sublevel-repo! Due due to exception while retrieving " + repositoryURL)
			return null
		}		
		val gitClonePattern = Pattern::compile("git clone ((git|https|http)://\\S+)")
		val matcher = gitClonePattern.matcher(document.text)
		var String url = null
		if (matcher.find) {
			url = matcher.group(1)
		} else {
			val gitUrlPattern = Pattern::compile("(git://\\S+)")
			val urlMatcher = gitUrlPattern.matcher(document.text)
			if (urlMatcher.find) {
				url = urlMatcher.group				
			}
		}	
		
		if (url == null) {
			repositoryModel.url = "<no url found in " + repositoryURL + ">"
			System.err.println('''ERROR for «repositoryURL»''')
		} 
		
		repositoryModel.metaData.origin = url
		
		return repositoryModel
	}
	
	private var RepositoryModelDirectory currentSubDirectory = null
	
	private def addContent(RepositoryModelDirectory rootDirectory, URL pageURL) {			
		val document = try {
			Jsoup::parse(pageURL, timeout)
		} catch(Exception e) {
			System.err.println("Could not import an apparent sublevel-repo! Due due to exception while retrieving " + pageURL)
			return		
		}
		val repoTable = document.getElementsByAttributeValue("summary", "repository list").get(0)
		
		val List<Callable<Pair<RepositoryModelDirectory, RepositoryModel>>> callables = newArrayList()
		for (row: repoTable.getElementsByTag("tr")) {
			val tds = row.getElementsByTag("td")
			if (!tds.isEmpty) {
				val firstTd = (tds).get(0)
				if ((firstTd).attr("class").equals("reposection")) {
					val subDirectoryName = firstTd.html
					if (currentSubDirectory == null || !currentSubDirectory.name.equals(subDirectoryName)) {
						currentSubDirectory = rootDirectory.subDirectories.findFirst[it.name == subDirectoryName]
						if (currentSubDirectory == null) {
							currentSubDirectory = RepositoryModelFactory.eINSTANCE.createRepositoryModelDirectory
							currentSubDirectory.name = subDirectoryName
							rootDirectory.subDirectories += currentSubDirectory	
						}
					}
				} else if (firstTd.attr("class").equals("sublevel-repo")) {
					val parent = currentSubDirectory
					callables.add[
						var RepositoryModel repositoryModel = null
						try {						
							var String name = "<unknown>"
							var String description = "<no description>"
							var String owner = "<no owner>"
							var String lastModify = "<no date>"
							
							try { name = firstTd.getElementsByTag("a")?.get(0)?.html } catch (Exception e) {}
							try { description = tds.get(1).getElementsByTag("a")?.get(0)?.html } catch (Exception e) {}
							try { owner = tds.get(2).getElementsByTag("a")?.get(0)?.html } catch (Exception e) {}
							try { lastModify = tds.get(3).getElementsByTag("span")?.get(0)?.attr("title") } catch (Exception e) {}
							
							val path = firstTd.getElementsByTag("a").get(0).attr("href")
							
							repositoryModel = createRepository(new URL(pageURL.protocol, pageURL.host, path), name, '''«description» («owner», «lastModify»)''')
						} catch (Exception e) {
							System.err.println("Could not import an apparent sublevel-repo!")
							e.printStackTrace(System.err)
						}					
						
						return parent -> repositoryModel
					]
				}
			}			
		}
		executor.invokeAll(callables).stream().map[it.get()].forEach[
			val repositoryModel = it.value
			if (repositoryModel != null) {
				if (it.key.repositories.findFirst[it.name == repositoryModel.name] == null) {
					it.key.repositories.add(repositoryModel)
					val uri = URI.createURI('''«modelURI.scheme»://«modelURI.host»/«repositoryModel.qualifiedName»''')
					fs.getFragmentation(uri).contents.add(repositoryModel)		
					
					System.out.println('''Added «repositoryModel.name»''')			
				} else {
					System.out.println('''Already in the model «repositoryModel.name»''')	
				}				
			}
		]
	}
	
	private def void createRepositoryModelDirectory(String eclipseGitURL) {
		val rootDirectory = if (directory == null) {
			val newRootDirectory = RepositoryModelFactory.eINSTANCE.createRepositoryModelDirectory()
			newRootDirectory.name = "git.eclipse.org"
			newRootDirectory.description = "All officially recognized eclipse related projects and their git repositories."
			newRootDirectory.url = eclipseGitURL
			fragmentation.rootFragment.contents.add(newRootDirectory)
			newRootDirectory
		} else {
			directory
		}
		
		val baseUrl = new URL(eclipseGitURL)
		val eclipseGitRootDocument = Jsoup::parse(baseUrl, timeout)
		val pager = eclipseGitRootDocument.getElementsByClass("pager").get(0)
		val pages = pager.getElementsByTag("a").map[it.attr("href")]
		
		for (String page: pages) {
			val pageURL = new URL(baseUrl.protocol, baseUrl.host, page)
			addContent(rootDirectory, pageURL)
		}
		
		executor.shutdown()
	}
	
	override protected addOptions(Options options) {
		super.addOptions(options)
		options.addOption(Option.builder("u").longOpt("url").desc("The git.eclipse.org url. Default is https://git.eclipse.org/c/.").hasArg.build)
	}
	
	override protected run(CommandLine cl) {
		createRepositoryModelDirectory(cl.getOptionValue("u", "https://git.eclipse.org/c/"))
	}
	
}