package testclasses;

/**
 * @author Frederik Marticke
 *
 * Testclass for the Weighted Methods Complexity Metric from the CK-Metricssuite.
 * Should give a WMC-value of 9 = 3 Class methods + 3 instance methods + 3 methods with arguments.
 */
public class CkWmcTest {
	
//Class methods
	public static void WmcTestPublicStatic(){
		//nothing to do here
	}
	
	private static void WmcTestPrivateStatic(){
		//nothing to do here
	}
	
	protected static void WmcTestProtectedStatic(){
		WmcTestPrivateStatic();
	}
	
	
//Instance methods
	
	public void WmcTestPublic(){
		//nothing to do here
	}
	
	private void WmcTestPrivate(){
		//nothing to do here
	}
	
	protected void WmcTestProtected(){
		WmcTestPrivate();
	}
	
//methods with parameters	
	public static void WmcTestPublic(int a, String b){
		//nothing to do here
	}
	
	private int WmcTestPrivate(int i){
		return 1 + i;
	}
	
	protected Object WmcTestProtected(Object o){
		WmcTestPrivate(42);
		return o;
	}	

}
