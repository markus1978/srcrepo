package de.hub.srcrepo;

import java.util.Date;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

import de.hub.jstattrack.JStatTrackActivator;

public class SrcRepoActivator extends Plugin {

	public static SrcRepoActivator INSTANCE;
	private boolean isStandAlone = false;
	private boolean logInStandAlone = true;
	public boolean useCGit = false;
	
	private void init() {
		JStatTrackActivator.instance.enableWebServer();
		JStatTrackActivator.instance.batchedDataPoints = 100;
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
		INSTANCE.init();
	}

	public void debug(String msg) {
		log(Status.OK, msg, null);
	}

	public void info(String msg) {
		log(Status.INFO, msg, null);
	}

	public void warning(String msg) {
		log(Status.WARNING, msg, null);		
	}
	
	public void warning(String msg, Exception e) {
		log(Status.WARNING, msg, e);
	}
	
	public void error(String msg) {
		log(Status.ERROR, msg, null);
	}
	
	public void error(String msg, Exception e) {
		log(Status.ERROR, msg, e);
	}	
	
	public static void standalone() {
		INSTANCE = new SrcRepoActivator();
		INSTANCE.isStandAlone = true;
		JStatTrackActivator.standalone();
		INSTANCE.init();
	}
	
	private void log(int level, String msg, Exception e) {
		if (!isStandAlone) {
			try {
				getLog().log(new Status(level, getBundle().getSymbolicName(), Status.OK, msg, e));
			} catch (Exception ex) {
				isStandAlone = true;
			}	
		}
		if (isStandAlone) {
			if (logInStandAlone) {
				System.out.println(new Date(System.currentTimeMillis()).toString() + " LOG(" + level + "): " + (msg != null ? msg : "(null)") + (e != null ? ": " + e.getMessage() : ""));
			}
		}		
	}
}
