package de.hub.srcrepo.metrics.testdata;

import de.hub.srcrepo.metrics.Metric;

public class Dependencies {

	@Metric(name="cbo", value=2)
	public static class ClassA {
		static int sf1 = 0;
		int f1;
		int f2;
		
		ClassB b;
		ClassC c;
		
		void m1() {
			f1 = b.f3 + f1;
			b.m2();
			c.m3();
		}
	}
	
	@Metric(name="cbo", value=0)
	public static class ClassB {
		int f3;
		
		void m2() {
			f3 = Dependencies.ClassA.sf1;
		}
	}
	
	@Metric(name="cbo", value=1)
	public static class ClassC {
		ClassB b;
		
		void m3() {
			b.f3 = 2;
		}
	}
}
