package testclasses;

/**
 * @author Frederik Marticke
 * Testclass for calculating McCabe Metric of do for loops.
 */
public class McCabeForTest {
	
	/**
	 * Should give a McCabe value of 2 = 1 binary branch + 1
	 * @param pA
	 * @param pB
	 */
	static void forTest(int pA, int pB){
		int a = pA;
		int b = pB;
		int i = 0;
		for(i=10;i<a;i++)			
			System.out.println("'b'" + b + "is better than 'a'");		
	}

}
