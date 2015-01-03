package testclasses;

public class McCabeContinueTest {
	
	/**
	 * DISCUSS: Why does continue or break increase complexity?
	 * 
	 * Should give a McCabe value of 4 = 3 binary branches + 1
	 * @param pA
	 * @param pB
	 */
	static void continueTest(int pA, int pB){
		int a = pA;
		int b = pB;
		while(a!=b){			
			System.out.println("a==b");
			if(a>b)
				continue;
			if(a==b)
				break;
		}		
	}
}
