package de.hub.srcrepo;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.osgi.framework.BundleContext;

import de.hub.jstattrack.JStatTrackActivator;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;

public class SrcRepoActivator extends Plugin {

	public static final String commit = "$git$";
	
	public static SrcRepoActivator INSTANCE;
	private boolean isStandAlone = false;
	private boolean logInStandAlone = true;
	private boolean isDebug = false;
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
	
	public static void debug(Function0<String> message) {
		if (INSTANCE.isDebug) {
			String output = message.apply();
			if (output != null) {
				INSTANCE.debug(output);	
			}			
		}
	}
	
	public static void condition(Function0<Boolean> condition) {
		condition("Failed condition.", condition);
	}
	
	public static void condition(String message, Function0<Boolean> condition) {
		if (INSTANCE.isDebug) {
			if (!condition.apply()) {
				throw new IllegalArgumentException(message);
			}
		}
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
		
		EPackage.Registry.INSTANCE.put(RepositoryModelPackage.eINSTANCE.getNsURI(), RepositoryModelPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(JavaPackage.eINSTANCE.getNsURI(), JavaPackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
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
				if (e != null) {
					String trace = ExceptionUtils.getStackTrace(e);
					System.out.println(new Date(System.currentTimeMillis()).toString() + " LOG(" + level + "): " + (msg != null ? msg : "") + "\n" + trace);
				} else {
					System.out.println(new Date(System.currentTimeMillis()).toString() + " LOG(" + level + "): " + (msg != null ? msg : "(null)"));
				}
			}
		}		
	}
}
