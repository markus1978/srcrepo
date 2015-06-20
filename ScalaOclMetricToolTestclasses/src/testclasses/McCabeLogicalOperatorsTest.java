package testclasses;

/**
 * @author Frederik Marticke
 * Testclass for calculating McCabe Metric of complex logical operators.
 */
public class McCabeLogicalOperatorsTest {
	
	/**
	 * Tester to find out how to caculated operators like &&, || correctly
	 * Should give a McCabe value of 9 
	 * = 1 binary branch + &&[what is the same like an if] 
	 * + 1 binary branch + ||[what is the same like an if]
	 * + 1 while[same like if] + ||[what is the same like an if]
	 * + 1 while[same like if] + &&[what is the same like an if]
	 * + 1
	 * @param pA
	 * @param pB
	 */
	static void logicalOperatorsTest(int pA, int pB){
		int a = pA;
		int b = pB;
		if(a!=b && a>b)			
			System.out.println("a!=b");		
		if(a!=b || a>b)			
			System.out.println("a!=b");
		while(a!=b || a>b)			
			System.out.println("a!=b");
		while(a!=b && a>b)			
			System.out.println("a!=b");
	}
	
	/**
	 * Tester to find out how to caculated operators like &&, || correctly
	 * Should give a McCabe value of 9 
	 * = + 1 for[same like if] + ||[what is the same like an if]
	 * + 1 for[same like if] + &&[what is the same like an if]
	 * + 1 do[same like if] + ||[what is the same like an if]
	 * + 1 do[same like if] + &&[what is the same like an if]
	 * + 1
	 * @param pA
	 * @param pB
	 */
	static void logicalOperatorsTest2(int pA, int pB){
		int a = pA;
		int b = pB;
		for(a=0; a<5 && 0<b; a++)			
			System.out.println("a!=b");		
		for(a=0; a<5 || 0<b; a++)			
			System.out.println("a!=b");
		do
			System.out.println("a!=b");
		while(a!=b && a>b);
		do
			System.out.println("a!=b");
		while(a!=b || a>b);
			
	}
	
	/**
	 * Tester to find out how to calculate operators like &&, || correctly
	 * Should give a McCabe value of 5 
	 * = + 1 for[same like if] 
	 * + 1 &&[what is the same like an if]
	 * + 1 && [what is the same like an if]
	 * + 1 ||[what is the same like an if]
	 * + 1
	 * @param pA
	 * @param pB
	 */
	static void logicalOperatorsTest3(int pA, int pB){
		int a = pA;
		int b = pB;
		for(a=0; a<5 && 0<b && a > 4 || a != b; a++)			
			System.out.println("a!=b");
		}
}
