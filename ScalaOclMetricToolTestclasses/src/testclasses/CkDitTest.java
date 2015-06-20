package testclasses;

/**
 * @author Frederik Marticke
 * This is a testclass for the Depth inheritance tree (DIT) metric from the CK metricssuite.
 *  
 * The simulated structure looks as follows:
 * 
 * 
 * 											CkDitLevelZeroWithNoParent
 * 													^
 * 													|
 * 													|
 * CkDitInterfaceLevelOneWithNoParent <------ CkDitLevelOneWithParent						
 *	^		^										^
 * 	|		|										|
 * 	|		|										|
 * 	|		-----------------------------------------
 * 	|		|
 *  |	CkDitLevelTwoWithTwoParents			CkDitInterfaceLevelTwoWithNoParents		CkNocInterfaceTest
 * 	|		^										^										^
 * 	|		|										|										|
 * 	|		|										|										|
 * 	-----CkDitTest --------------------------------------------------------------------------
 * 
 * Therefore the result should be DIT(CkDitTest) = 3, because 
 * CkDitLevelTwoWithTwoParents -> CkDitLevelOneWithParent -> CkDitLevelZeroWithNoParent
 * is the longest Path inside the inheritance tree.
 */
public class CkDitTest extends CkDitLevelTwoWithTwoParents
implements CkDitInterfaceLevelTwoWithNoParents, CkDitInterfaceLevelOneWithNoParent, CkNocInterfaceTest {
	//nothing to do here.
}
