package testclasses;

/**
 * ClassVariables = {instanceVar1, instanceVar2}
 * MethodsInsideClass = {foo, bar, fooBar}
 * Mfoo = {instanceVar1}
 * Mbar = {instanceVar1}
 * MfooBar = {instanceVar2}
 * 
 * P = {(Mfoo, MfooBar), (Mbar, MfooBar)} //intersection is empty
 * Q = {(Mfoo, Mbar) } //intersection is not empty
 * 
 * Because of|P| = 2 > 1 = |Q| 
 * => LCOM = |P| - |Q| = 2 - 1 = 1
 * 
 * @author Frederik Marticke
 */
public class CkLcomTest {
	int instanceVar1 = 0;
	String instanceVar2 = "tester";
	
	public void foo(){
		instanceVar1++;
	}
	
	private int bar(){
		return instanceVar1 + 100;
	}
	
	protected String fooBar(){
		int baz = bar();
		return instanceVar2+baz;
	}

}
