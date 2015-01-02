package testclasses;

public class McCabeDoWhileTest {
	
	/**
	 * Should give a McCabe value of 2 = 1 binary branch + 1
	 * @param pA
	 * @param pB
	 */
	static void doWhileTest(int pA, int pB){
		int a = pA;
		int b = pB;
		do{
			System.out.println("a!=b");
		}
		while(a!=b);		
	}

}
