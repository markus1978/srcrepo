package testclasses;

public class McCabeLogicalOperatorsTest {
	
	/**
	 * Tester to find out how to caculated operators like &&, || correctly
	 * Should give a McCabe value of 5 
	 * = 1 binary branch + &&[what is the same like an if] 
	 * + 1 binary branch + ||[what is the same like an if]
	 * + 1
	 * @param pA
	 * @param pB
	 */
	static void whileTest(int pA, int pB){
		int a = pA;
		int b = pB;
		if(a!=b && a>b)			
			System.out.println("a!=b");		
		if(a!=b || a>b)			
			System.out.println("a!=b");
	}

}
