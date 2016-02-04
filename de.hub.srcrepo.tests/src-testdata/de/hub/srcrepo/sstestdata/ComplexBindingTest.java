package de.hub.srcrepo.sstestdata;

public class ComplexBindingTest {
	
	public void foo(String msg) {
		
	}
	
	public void bar1(Bar<ComplexBindingTest> arg) {
		bar2(new Bar<String>());
	}
	
	public void bar2(Bar<String> arg) {
		bar1(new Bar<ComplexBindingTest>());
	}
	
	public static class Bar<E> {
		
	}
}
