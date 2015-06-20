package testclasses;

/**
 * @author Frederik Marticke
 * Testclass for calculating McCabe Metric of while loops.
 */
public class McCabeWhileTest {
	
	/**
	 * Should give a McCabe value of 2 = 1 binary branch + 1
	 * @param pA
	 * @param pB
	 */
	static void whileTest(int pA, int pB){
		int a = pA;
		int b = pB;
		while(a!=b)			
			System.out.println("a!=b");		
	}

}
