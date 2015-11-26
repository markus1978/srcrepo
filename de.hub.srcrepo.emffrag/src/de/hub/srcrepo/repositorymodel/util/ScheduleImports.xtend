package de.hub.srcrepo.repositorymodel.util

class ScheduleImports extends AbstractRepositoryModelMain {
	
	override protected perform(String[] args) {
		directory.subDirectories.filter[it.name == "jdt" || it.name == "emf"].forEach[
			repositories.forEach[
				val importMetaData = it.metaData.importMetaData
				if (!importMetaData.imported && !importMetaData.importing && !importMetaData.scheduled) {
					System.out.println("Schedule " + it.name)				
					importMetaData.scheduled = true				
				}
			]
		]
	}
		
	static def void main(String[] args) {
		val instance = new ScheduleImports()
		instance.run(args)
	}
}