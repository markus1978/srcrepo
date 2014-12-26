package de.hub.metrics.testclasses;

/**
 * @author Frederik Marticke
 *
 */
public class McCabeTest {

	/**
	 * McCabe: Metrictest for a switch-case statement
	 */
	final static void testSwitchCase(){
		int key = 2;
		switch (key) {
		case 1: System.out.println("key = 1");			
		break;
		case 2: System.out.println("key = 2");
		break;
		default: System.out.println("default");
		break;
		}			
	}
	
	/**
	 * McCabe: Metrictest for an if-else statement
	 */
	final static void testIfElse(){
		boolean tester = false;
		if(true)
			if(tester)
				System.out.println("true -> tester(true)");
			else
				System.out.println("true -> tester(false)");
	}
}
