package de.hub.srcrepo.sstestdata.unresolved;

import gibt.es.nicht;
import static java.lang.System.gibtesnicht;

public class A {
	
	public void foo() {
		C c = null;
		C=C();
		B b = new B(); // wrong args
		b.doesNotExist();
		b.foo(1); // wrong args
	}
}
