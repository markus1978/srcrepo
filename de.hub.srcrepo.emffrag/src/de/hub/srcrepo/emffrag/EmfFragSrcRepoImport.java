package de.hub.srcrepo.emffrag;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.emffrag.mongodb.MongoDBUtil;
import de.hub.srcrepo.JGitUtil;
import de.hub.srcrepo.SrcRepoActivator;

public class EmfFragSrcRepoImport implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		EmfFragActivator.class.getName();
		EmfFragMongoDBActivator.class.getName();
		SrcRepoActivator.class.getName();

		final Map<?,?> args = context.getArguments();  
		final String[] appArgs = (String[]) args.get("application.args");
		URI dbURI = URI.createURI(appArgs[2]);
		
		MongoDBUtil.dropCollection(dbURI);		
		if (appArgs.length == 4) {
			EmfFragActivator.instance.cacheSize = Integer.parseInt(appArgs[3]);
		} else {
			EmfFragActivator.instance.cacheSize = 1000;
		}
		
		JGitUtil.importGit(appArgs[0], appArgs[1], dbURI, new EmfFragImportConfiguration());		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		
	}

}
