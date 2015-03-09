package testclasses;

/**
 * ClassVariables = {instanceVar1, instanceVar2, instanceVar3}
 * 
 * MethodsInsideClass = {fooPostfixPrefix, ifFoo, ifFooNestedOperators, whileFoo, whileFooNestedOperators, barAssignment, bar2ComplexReturn, fooBar}
 *  
 *  
 *  
 *  
 * MfooPostfixPrefix = {instanceVar1} * 
 * MifFoo = {instanceVar1} 
 * MifFooNestedOperators = {instanceVar1, instanceVar3, instanceVar4} * 
 * MwhileFoo = {instanceVar3, instanceVar5} 
 * MWhileFooNestedOperators = {instanceVar4, instanceVar5} 
 * MbarAssignment = {instanceVar3} 
 * Mbar2ComplexReturn = {instanceVar1} 
 * MfooBar = {instanceVar2}
 * 
 * //intersection is empty
 * P = { (MfooPostfixPrefix, MwhileFoo),  (MfooPostfixPrefix, MWhileFooNestedOperators), (MfooPostfixPrefix, MbarAssignment), (MfooPostfixPrefix, MfooBar)
 * 		 (MifFoo, MwhileFoo), (MifFoo, MWhileFooNestedOperators), (MifFoo, MbarAssignment), (MifFoo, MfooBar)
 * 		 (MifFooNestedOperators, MfooBar), 
 *  	 (MwhileFoo, Mbar2ComplexReturn),  (MwhileFoo, MfooBar),
 *    	 (MWhileFooNestedOperators,MbarAssignment),(MWhileFooNestedOperators,Mbar2ComplexReturn),(MWhileFooNestedOperators,MfooBar),
 *	  	 (MbarAssignment, Mbar2ComplexReturn), (MbarAssignment, MfooBar)	
 *		 (Mbar2ComplexReturn, MfooBar)
 * } 
 * 
 * //intersection is not empty
 * Q = {(fooPostfixPrefix, ifFoo),(fooPostfixPrefix, ifFooNestedOperators), (fooPostfixPrefix, bar2ComplexReturn)
 * 		(ifFoo, ifFooNestedOperators), (ifFoo, bar2ComplexReturn)
 * 		(ifFooNestedOperators, bar2ComplexReturn),(ifFooNestedOperators, MwhileFoo),(ifFooNestedOperators, MWhileFooNestedOperators),(ifFooNestedOperators, MbarAssignment) 
 * 		(whileFoo, MWhileFooNestedOperators), (whileFoo, MbarAssignment)  		
 * } 
 *
 * |P| = 17
 * |Q| = 11
 * 
 * Because of|P| = 17 > 11 = |Q| 
 * => LCOM = |P| - |Q| = 17 - 11 = 6
 * 
 * @author Frederik Marticke
 */
public class CkLcomTest {
	int instanceVar1 = 0;
	int instanceVar3 = 0;
	int instanceVar4 = 0;
	int instanceVar5 = 0;
	String instanceVar2 = "tester";
	
	public void fooPostfixPrefix(){
		instanceVar1++;			
		instanceVar1 = ++instanceVar1 + 3;
	}
	
	public int ifFoo(){
		if(instanceVar1 > 0)
			return 0;					
		return instanceVar1 + 3;
	}
	
	public int ifFooNestedOperators(){
		if((instanceVar3 > 0) || (3 < ((instanceVar4 + instanceVar3))))
			return 0;					
		return instanceVar1 + 3;
	}
	
	public int whileFoo(){
		while(instanceVar3 > 0)
			System.out.println("fooWriteLine");					
		return instanceVar5 + 3;
	}
	
	public int whileFooNestedOperators(){
		while((instanceVar4 > 0) || (3 < ((instanceVar4 + instanceVar5))))
			System.out.println("fooWriteLine");					
		return instanceVar4 + 3;
	}
	
	private int barAssignment(){
		instanceVar3 = bar2ComplexReturn();
		return 0;
	}
	
	private int bar2ComplexReturn(){
		return ((instanceVar5 + 100) + 100);		
	}
	
	protected String fooBar(){
		int baz = barAssignment();
		return instanceVar2+baz;
	}

}
