package de.hub.srcrepo;

public class SrcRepoActivator2 {} 
//extends Plugin {
//	
//	public static void main(String[] args) {
//		System.out.println("Hello World!");
//	}
//
//	public static SrcRepoActivator INSTANCE;
//	private boolean isStandAlone = false;
//	private boolean logInStandAlone = true;
//	
//	private void init() {
//		JStatTrackActivator.instance.withWebServer = true;
//	}
//	
//	@Override
//	public void start(BundleContext context) throws Exception {
//		super.start(context);
//		INSTANCE = this;
//		INSTANCE.init();
//	}
//
//	public void debug(String msg) {
//		log(Status.OK, msg, null);
//	}
//
//	public void info(String msg) {
//		log(Status.INFO, msg, null);
//	}
//
//	public void warning(String msg) {
//		log(Status.WARNING, msg, null);		
//	}
//	
//	public void warning(String msg, Exception e) {
//		log(Status.WARNING, msg, e);
//	}
//	
//	public void error(String msg) {
//		log(Status.ERROR, msg, null);
//	}
//	
//	public void error(String msg, Exception e) {
//		log(Status.ERROR, msg, e);
//	}	
//	
//	public static void standalone() {
//		INSTANCE = new SrcRepoActivator();
//		INSTANCE.isStandAlone = true;
//		JStatTrackActivator.standalone();
//		INSTANCE.init();
//	}
//	
//	private void log(int level, String msg, Exception e) {
//		if (!isStandAlone) {
//			try {
//				getLog().log(new Status(level, getBundle().getSymbolicName(), Status.OK, msg, e));
//			} catch (Exception ex) {
//				isStandAlone = true;
//			}	
//		}
//		if (isStandAlone) {
//			if (logInStandAlone) {
//				System.out.println(new Date(System.currentTimeMillis()).toString() + " LOG(" + level + "): " + (msg != null ? msg : "(null)") + (e != null ? ": " + e.getMessage() : ""));
//			}
//		}		
//	}
//}
