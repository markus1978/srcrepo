package de.hub.srcrepo

import org.junit.Test
import java.util.List

class IssueReproducingTest {
	
	private val String revision = ""
	private val String workingDirectory = ""
	private val String projectPath = ""
	private val List<String> paths = #[]
	
	@Test
	def void reproduce() {
		// checkout certain revision
		
		// determine CUs added to snapshot
		val cuPaths = if (paths.empty) {
			// find all CU paths	
		} else {
			paths
		}
		
		// create CU models
		val cuModels = cuPaths.map[
			
		] 
		
		// create snapshot
	}
}