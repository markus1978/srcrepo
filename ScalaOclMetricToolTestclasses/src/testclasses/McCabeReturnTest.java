package testclasses;

public class McCabeReturnTest {
	
	/**
	 * TODO: what about the last statement restriction?
	 * 
	 * Should give a McCabe value of 3 = 1 binary branch + 1 return + 1
	 * @param pA
	 * @param pB
	 */
	static int returnTest(int pA, int pB){
		int a = pA;
		int b = pB;
		if(a!=b)			
			return -1;
		return 1;
	}

}
