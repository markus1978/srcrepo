package de.hub.metricTests;

import java.util.Iterator;
import java.util.List;

import de.hub.metrics.ResultObject;

/**
 * @author Frederik Marticke
 * This class provides methods, whch are independent from the specific testcase but can however be useful.
 */
public class TesterTools {
	
	/**
	 * prints output like: 'filename *** metricType: metricValue(s)'
	 * @param result
	 * @param metricType
	 */
	public void printFormattedResult(List<?> result, String metricType){
		System.out.println("#########################################################################");
		System.out.println("--- Result Overview ---");	
		int i = 0;
		for (Iterator<?> iter = result.iterator(); iter.hasNext(); ) {
			i++;
			ResultObject item = (ResultObject)iter.next(); 
			System.out.println("CompilationUnit #" + i + ": " + item.getFileName() + " *** " + metricType + ": " + item.toString());
		}
		System.out.println("#########################################################################");
	}
	
	/**
	 * prints output like: 'filename *** metricType: metricValue(s)' for all values > 0
	 * @param result
	 * @param metricType
	 */
	public void printFormattedResultGreaterZero(List<?> result, String metricType){
		System.out.println("#########################################################################");
		System.out.println("--- Result Overview ---");	
		int i = 0;
		for (Iterator<?> iter = result.iterator(); iter.hasNext(); ) {
			i++;
			ResultObject item = (ResultObject)iter.next();	
			if(!(item.toStringOnlyGreaterZero().isEmpty()))
				System.out.println("CompilationUnit #" + i + ": " + item.getFileName() + " *** " + metricType 
						+ ": " + item.toStringOnlyGreaterZero());
			if(item.getCoupledUnits().size() > 0)
				System.out.println(" --- couplings: <" + item.getCoupledUnits().toString() + ">");
		}
		System.out.println("#########################################################################");
	}

}
