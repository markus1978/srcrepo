package de.hub.srcrepo.emffrag;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import de.hub.srcrepo.JGitUtil;

public class EmfFragSrcRepoImport implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		final Map<?,?> args = context.getArguments();  
		final String[] appArgs = (String[]) args.get("application.args");		
		JGitUtil.importGit(appArgs[0], appArgs[1], URI.createURI(appArgs[2]), new EmfFragImportConfiguration());		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		
	}

}
