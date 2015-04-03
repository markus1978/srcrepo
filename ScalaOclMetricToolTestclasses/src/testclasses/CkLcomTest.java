package testclasses;

/**
 * ClassVariables = {instanceVar1, instanceVar2, instanceVar3}
 * 
 * MethodsInsideClass = {fooPostfixPrefix, ifFoo, ifFooNestedOperators, whileFoo, whileFooNestedOperators, barAssignment, bar2ComplexReturn, fooBar}
 *  
 *  
 *  
 *  
 * MfooPostfixPrefix = {instanceVar1}  
 * MifFoo = {instanceVar1} 
 * MifFooNestedOperators = {instanceVar1, instanceVar3, instanceVar4} 
 * MwhileFoo = {instanceVar3, instanceVar5} 
 * MWhileFooNestedOperators = {instanceVar4, instanceVar5} 
 * MbarAssignment = {instanceVar3} 
 * Mbar2ComplexReturn = {instanceVar5} 
 * MfooBar = {instanceVar2}
 * MfooBarField = {instanceVar3}
 * 
 * //intersection is empty
 * P = { (MfooPostfixPrefix, MwhileFoo),  (MfooPostfixPrefix, MWhileFooNestedOperators), (MfooPostfixPrefix, MbarAssignment),(fooPostfixPrefix, bar2ComplexReturn), (MfooPostfixPrefix, MfooBar), (MfooPostfixPrefixMfooBarField)
 * 		 (MifFoo, MwhileFoo), (MifFoo, MWhileFooNestedOperators), (MifFoo, MbarAssignment),(ifFoo, bar2ComplexReturn), (MifFoo, MfooBar), (MifFoo, MfooBarField)
 * 		 (MifFooNestedOperators, MfooBar), (ifFooNestedOperators, bar2ComplexReturn)
 *  	 (MwhileFoo, MfooBar)
 *    	 (MWhileFooNestedOperators,MbarAssignment),(MWhileFooNestedOperators,MfooBar), (MWhileFooNestedOperators,MfooBarField)
 *	  	 (MbarAssignment, Mbar2ComplexReturn), (MbarAssignment, MfooBar)	
 *		 (Mbar2ComplexReturn, MfooBar), (Mbar2ComplexReturn, MfooBarField)
 *		 (MfooBar, MfooBarField)
 * } 
 * 
 * //intersection is not empty
 * Q = {(MfooPostfixPrefix, MifFoo),(MfooPostfixPrefix, MifFooNestedOperators)
 * 		(MifFoo, MifFooNestedOperators)
 * 		(MifFooNestedOperators, MwhileFoo),(MifFooNestedOperators, MWhileFooNestedOperators),(MifFooNestedOperators, MbarAssignment), (MifFooNestedOperators, MfooBarField) 
 * 		(MwhileFoo, MWhileFooNestedOperators), (MwhileFoo, MbarAssignment), (MwhileFoo, Mbar2ComplexReturn), (MwhileFoo, MfooBarField)
 * 		(MbarAssignment, MfooBarField)
 * 		(MWhileFooNestedOperators,Mbar2ComplexReturn)  		
 * } 
 *
 * |P| = 23
 * |Q| = 13
 * 
 * Because of|P| = 23 > 13 = |Q| 
 * => LCOM = |P| - |Q| = 23 - 13 = 10
 * 
 * @author Frederik Marticke
 */
public class CkLcomTest {
	int instanceVar1 = 0;
	int instanceVar3 = 0;
	int instanceVar4 = 0;
	int instanceVar5 = 0;
	String instanceVar2 = "tester";
	
	//McCabe = 1
	public void fooPostfixPrefix(){
		instanceVar1++;			
		instanceVar1 = ++instanceVar1 + 3;
	}
	
	//McCabe = 3
	public int ifFoo(){
		if(instanceVar1 > 0)
			return 0;					
		return instanceVar1 + 3;
	}
	
	//McCabe = 4
	public int ifFooNestedOperators(){
		if((instanceVar3 > 0) || (3 < ((instanceVar4 + instanceVar3))))
			return 0;					
		return instanceVar1 + 3;
	}
	
	//McCabe = 2
	public int whileFoo(){
		while(instanceVar3 > 0)
			System.out.println("fooWriteLine");					
		return instanceVar5 + 3;
	}
	
	//McCabe = 3
	public int whileFooNestedOperators(){
		while((instanceVar4 > 0) || (3 < ((instanceVar4 + instanceVar5))))
			System.out.println("fooWriteLine");					
		return instanceVar4 + 3;
	}
	
	//McCabe = 1
	private int barAssignment(){
		instanceVar3 = bar2ComplexReturn();
		return 0;
	}
	
	//McCabe = 1
	private int bar2ComplexReturn(){
		return ((instanceVar5 + 100) + 100);		
	}
	
	//McCabe = 1
	protected String fooBar(){
		int baz = barAssignment();
		return instanceVar2+baz;
	}
	
	//McCabe = 1
	protected int fooBarField(){
		int fieldDec = instanceVar3;
		return fieldDec;
	}

}
