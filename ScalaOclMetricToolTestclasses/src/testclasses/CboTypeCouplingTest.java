package testclasses;

/**
 * @author Frederik Marticke
 * Testclass for analyzing methods arguments and returntypes for CBO Metric.
 *
 */
public class CboTypeCouplingTest {

	/**
	 * Testmethod which returns a non primitvie Type leading to a coupling
	 * @return
	 */
	public CkDitTest returnNonPrimitive(){
		return new CkDitTest();
	}
	
	/**
	 * Testmethod which returns a non primitive Type and having a non primitive Type as argument
	 * leading to two couplings.
	 * @param parameter
	 * @return
	 */
	public CkDitTest returnNonPrimitiveWithArgs(CkLcomTest parameter){
		return new CkDitTest();
	}

}
