package testclasses;

/**
 * @author Frederik Marticke
 * This is testclass for the Number of children (NOC) metric from the CK metricssuite.
 * The simulated structure looks as follows:
 * 
 * 						CkNocTest
 * 							^
 * 							|
 * 							|
 * 				-------------------------
 * 				|						|
 * 			CkNocChildOne			CkNocChildTwo
 * 				^
 * 				|
 * 		CkNocChildOneChildOne
 * 
 * Therefore the result should be NOC(CkNOCTest) = 2, because 
 * The NOC Value only calculates direct Subtypes. Therefore CkNocChildOneChildOne gets not 
 * included inside the result.
 *
 */
public class CkNocTest {
	//nothing to do here.

}
