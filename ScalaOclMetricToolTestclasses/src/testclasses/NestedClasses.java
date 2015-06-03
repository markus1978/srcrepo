package testclasses;

public class NestedClasses {

	public class nested1 extends CkNocTest implements
			CkDitInterfaceLevelOneWithNoParent {
		String s1 = "string1";
		nested2 n2 = new nested2();

		private void nestedMethod1() {
			System.out.println("method1");
			n2.nestedMethod2();
		}

		private class nested2 {
			private void nestedMethod2() {
				// foo
			}
		}
	}

	public void outerMethod1() {
		nested1 n1 = new nested1();
		n1.nestedMethod1();
	}
}
