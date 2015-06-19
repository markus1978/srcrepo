package testclasses;

public class McCabeExceptionsTest {
	
	/**
	 * Should give McCabe value = 3: 1 inner return + 1 catch + 1
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
	 * Should give McCabe value = 3: 1 inner return + 1 catch + 1
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
	 * Should give McCabe value = 4 : 1 inner return + 1 catch + 1 throw + 1
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
