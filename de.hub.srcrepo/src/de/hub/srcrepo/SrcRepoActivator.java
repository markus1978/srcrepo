package de.hub.srcrepo;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class SrcRepoActivator extends Plugin {

	public static SrcRepoActivator INSTANCE;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
	}

	public void info(String msg) {
		getLog().log(new Status(Status.INFO, getBundle().getSymbolicName(), Status.OK, msg, null));
	}

	public void warning(String msg) {
		getLog().log(new Status(Status.WARNING, getBundle().getSymbolicName(), Status.OK, msg, null));
	}
	
	public void warning(String msg, Exception e) {
		getLog().log(new Status(Status.WARNING, getBundle().getSymbolicName(), Status.OK, msg, e));
	}
	
	public void error(String msg) {
		getLog().log(new Status(Status.ERROR, getBundle().getSymbolicName(), Status.OK, msg, null));
	}
	
	public void error(String msg, Exception e) {
		getLog().log(new Status(Status.ERROR, getBundle().getSymbolicName(), Status.OK, msg, e));
	}	
	
	public static void standalone() {
		INSTANCE = new SrcRepoActivator();
	}
}
