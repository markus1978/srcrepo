package testclasses;

public class McCabeReturnTest {
	
	/**
	 * Should give a McCabe value of 3 = 1 binary branch + 1 return (not the last statement) + 1
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
	
	/**
	 * Should give a McCabe value of 1, because the return is the last statement it does not increase complexity.
	 * @return
	 */
	public int returnIsLastStatementTest(){
		return 0; 
	}
	
	/**
	 * Should give a McCabe value of 3 = 1 binary branch, 1 return which is not the last statement + 1
	 * @return
	 */
	public int returnIsNotLastStatementTest(){
		int i = 0;
		if(i == 0){
			return i;
		}
		//here could go a lot of code...
		return 1;			
	}
}
