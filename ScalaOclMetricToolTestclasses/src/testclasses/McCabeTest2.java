package testclasses;

public class McCabeTest2 {
	
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

}
