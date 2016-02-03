package de.hub.srcrepo.emffrag.commands

import de.hub.srcrepo.MoDiscoRevVisitor
import de.hub.srcrepo.RepositoryModelTraversal
import de.hub.srcrepo.repositorymodel.RepositoryModel
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory
import org.apache.commons.cli.CommandLine
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage

class MetricsCommand extends AbstractRepositoryCommand {
	
	static abstract class EmffragModiscoRevVisitor extends MoDiscoRevVisitor {
		new() {
			super(JavaPackage.eINSTANCE);
		}
	}
	
	private def traverse(RepositoryModel repo, EmffragModiscoRevVisitor visitor) {
		RepositoryModelTraversal.traverse(repo, visitor)
	}
	
//	var count = 0
	override protected runOnRepository(RepositoryModelDirectory directory, RepositoryModel repo, CommandLine cl) {
//		AnalysisVisitor.traverse(repo)[o,f,v|
//			count++
//		]
//		println("visited values " + count)
		repo.traverse[rev, projectID, model|
			println('''
				«rev.name»
					size: «model.eAllContents.size» 
			''')
		]					
	}
}