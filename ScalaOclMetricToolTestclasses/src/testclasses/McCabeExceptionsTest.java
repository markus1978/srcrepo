package testclasses;

/**
 * @author Frederik Marticke
 * Testclass for calculating McCabe Metric of Exceptions.
 */
public class McCabeExceptionsTest {
	
	/**
	 * Should give McCabe value = 2 : 1 catch-clause + 1
	 * @return
	 */
	public String tryCatchTest(){
		String[] test = new String[2];
		try{
			String foo = test[-1];
			return foo;
		} catch(IndexOutOfBoundsException e){
			System.out.println("Exception");
		}
		return "bar";
	}
	
	/**
	 * Should give McCabe value = 3: 2 catch-clauses + 1
	 * @return
	 */
	public String tryCatchSideEffectTest(){
		String[] test = new String[2];
		try{
			String foo = test[-1];
			return foo;
		} catch(IndexOutOfBoundsException e){
			System.out.println("Exception");
			return "failed";
		} catch(Exception e){
			System.out.println("Exception");
			//maybe sideeffects...
		}
		//maybe do some stuff after catch
		return "bar";
	}
	
	/**
	 * Should give McCabe value = 2 : 1 catch-clause + 1
	 * @return
	 * @throws Exception
	 */
	public String throwsTest() throws Exception{
		String[] test = new String[2];
		try{
			String foo = test[-1];
			return foo;
		} catch(IndexOutOfBoundsException e){
			throw new Exception("Error found");
		}		
	}
}
