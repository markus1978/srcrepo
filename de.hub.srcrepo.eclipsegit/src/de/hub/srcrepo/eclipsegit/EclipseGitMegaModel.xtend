package de.hub.srcrepo.eclipsegit

import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory
import de.hub.srcrepo.repositorymodel.util.AbstractRepositoryModelMain
import java.net.URL
import java.util.List
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.regex.Pattern
import org.eclipse.emf.common.util.URI
import org.jsoup.Jsoup

import static extension de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils.*

class EclipseGitMegaModel extends AbstractRepositoryModelMain {
	
	val timeout = 30000
	val executor = Executors::newFixedThreadPool(50)
	
	private def createRepository(URL repositoryURL, String name, String description) {
		val repositoryModel = RepositoryModelFactory.eINSTANCE.createRepositoryModel
		repositoryModel.name = name
		repositoryModel.description = description
		
		val document = Jsoup::parse(repositoryURL, timeout)		
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
		} else {
			repositoryModel.url = url
			System.out.println('''Added «name» with «url»''')	
		}
		
		repositoryModel.metaData = RepositoryModelFactory.eINSTANCE.createRepositoryMetaData
		repositoryModel.metaData.importMetaData = RepositoryModelFactory.eINSTANCE.createImportMetaData
		repositoryModel.metaData.origin = url
		
		return repositoryModel
	}
	
	private def addContent(RepositoryModelDirectory rootDirectory, URL pageURL) {
		val document = Jsoup::parse(pageURL, timeout)
		val repoTable = document.getElementsByAttributeValue("summary", "repository list").get(0)
		var RepositoryModelDirectory currentSubDirectory = null
		if (!rootDirectory.subDirectories.isEmpty) {
			currentSubDirectory = rootDirectory.subDirectories.last
		}
		val List<Callable<Pair<RepositoryModelDirectory, RepositoryModel>>> callables = newArrayList()
		for (row: repoTable.getElementsByTag("tr")) {
			val tds = row.getElementsByTag("td")
			if (!tds.isEmpty) {
				val firstTd = (tds).get(0)
				if ((firstTd).attr("class").equals("reposection")) {
					val subDirectoryName = firstTd.html
					if (currentSubDirectory == null || !currentSubDirectory.name.equals(subDirectoryName)) {
						currentSubDirectory = RepositoryModelFactory.eINSTANCE.createRepositoryModelDirectory
						currentSubDirectory.name = subDirectoryName
						rootDirectory.subDirectories += currentSubDirectory
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
			it.key.repositories.add(repositoryModel)
			val uri = URI.createURI('''«modelURI.scheme»://«modelURI.host»/«repositoryModel.qualifiedName»''')
			fs.getFragmentation(uri).contents.add(repositoryModel)
		]
	}
	
	private def void createRepositoryModelDirectory(String eclipseGitURL) {
		val rootDirectory = RepositoryModelFactory.eINSTANCE.createRepositoryModelDirectory()
		rootDirectory.name = "git.eclipse.org"
		rootDirectory.description = "All officially recognized eclipse related projects and their git repositories."
		rootDirectory.url = eclipseGitURL
		fragmentation.rootFragment.contents.add(rootDirectory)
		
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
	
	static def void main(String[] args) {
		val instance = new EclipseGitMegaModel()
		instance.run(args)
	}
	
	override protected perform(String[] args) {
		createRepositoryModelDirectory("https://git.eclipse.org/c/")
	}
	
}