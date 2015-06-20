package testclasses;

/**
 * @author Frederik Marticke
 * Testclass for calculating McCabe Metric of the logical ternary Operator.
 *
 */
public class McCabeTernaryOperatorTest {
	
	/**
	 * Should give a McCabe metric of 2 = 1 binary branch + 1
	 * 
	 * if(i > j) then
	 * 	return i
	 * else
	 *  return j
	 * 
	 * @return
	 */
	public int simpleTernaryTest() {
		int i = 0;
		int j = 1;
		return i > j ? i : j;
	}

	/**
	 * should give a McCabe metric of 5 = 4 binary branches + 1
	 * 
	 * if(i>0) then
	 * 	 if(i > 10) then
	 * 		if(i>100) then
	 * 			if((i+10) < 200)
	 * 				return true
	 * else
	 * 	return false
	 * 
	 * @return
	 */
	public boolean complexTernaryTest() {
		int i = 0;
		if (i > 0)
			return i > 10 ? (i > 100 && ((i + 10) < 200)) : true;
		else
			return false;
	}

}
