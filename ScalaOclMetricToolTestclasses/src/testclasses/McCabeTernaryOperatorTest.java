package testclasses;

/**
 * @author Frederik Marticke
 *
 */
public class McCabeTernaryOperatorTest {

	
	/**
	 * Should give a McCabe metric of 2 = 1 binary branch + 1
	 * @return
	 */
	public int simpleTernaryTest() {
		int i = 0;
		int j = 1;
		return i > j ? i : j;
	}

	/**
	 * should give a McCabe metric of 7 = 4 binary branches + 2 inner return + 1
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
