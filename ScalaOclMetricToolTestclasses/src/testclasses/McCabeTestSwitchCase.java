package testclasses;

public class McCabeTestSwitchCase {
	
	static String wochentagsName(int nummer) {
		switch(nummer) {
		    case 1: return "Montag";
		    case 2: return "Dienstag";
		    case 3: return "Mittwoch";
		    case 4: return "Donnerstag";
		    case 5: return "Freitag";
		  }
		return "unknown";
	}	
	
	static String wochentagsName2(int nummer) {
		switch(nummer) {
		    case 1: return "Montag2";
		    case 2: return "Dienstag2";
		    case 3: return "Mittwoch2";
		    case 4: return "Donnerstag2";
		    case 5: return "Freitag2";
		    case 6: return "Samstag2";
		    case 7: return "Sonntag2";
		  }
		return "unknown";
	}			
}