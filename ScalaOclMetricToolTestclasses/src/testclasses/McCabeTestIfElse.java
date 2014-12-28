package testclasses;

public class McCabeTestIfElse {
	
	/**
	 * Should give a McCabe value of 3 = 2 binary branches + 1
	 * @param pA
	 * @param pB
	 */
	static void ifElseTest(int pA, int pB){
		int a = pA;
		int b = pB;
		if(a!=b)
			if(b<a)
				System.out.println("b<a");		
			else
				System.out.println("a>b");
		else
			System.out.println("a==b");		
	}
}
