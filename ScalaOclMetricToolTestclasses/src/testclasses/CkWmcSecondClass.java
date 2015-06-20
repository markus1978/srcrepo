package testclasses;

/**
 * @author Frederik Marticke
 * Initially a Testclass for WMC. Now also used for CBO by using Methods from CkWmcTest which in turn access' the
 * static variable WmcVariableAccess.
 */
public class CkWmcSecondClass {
	
	public static int WmcVariableAccess = 1;

	public static void main(String[] args) {
		//CkWmcSeconds becomes coupled to CkWmcTest by calling its contructor => +1 
		CkWmcTest t = new CkWmcTest();
		//the methodcall would be another coupling, but the class was already count, therefore the value stays unchanged
		t.WmcTestPublic();
		McCabeForTest.forTest(1, 3);
	}

}
