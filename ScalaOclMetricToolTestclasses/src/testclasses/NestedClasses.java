package testclasses;

public class NestedClasses {

	public class nested1 extends CkNocTest implements CkDitInterfaceLevelOneWithNoParent{
		String s1 = "string1";
		
		private void nestedMethod1(){
			System.out.println("method1");
		}
	}
	
	public void outerMethod1(){
		nested1 n1 = new nested1();
		n1.nestedMethod1();
	}
}
