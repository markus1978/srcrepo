package testclasses;

public class McCabeBreakTest {
	
	/**
	 * DISCUSS: Why does break increase complexity?
	 * 
	 * Should give a McCabe value of 3 = 2 binary branches + 1
	 * @param pA
	 * @param pB
	 */
	static void breakTest(int pA, int pB){
		int a = pA;
		int b = pB;
		while(a!=b){			
			System.out.println("a==b");
			if(a==b)
				break;
		}		
	}
}
