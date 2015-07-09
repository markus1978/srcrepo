package de.hub.srcrepo.metrics.testdata;

import de.hub.srcrepo.metrics.Metric;

public class Inheritence {

	@Metric(name="dit", value=0)
	@Metric(name="noc", value=3)
	@Metric(name="rfc", value=1)
	public interface InterfaceA {
		void m1();
	}
	
	@Metric(name="dit", value=1)
	@Metric(name="noc", value=1)
	@Metric(name="rfc", value=2)
	public interface InterfaceB extends InterfaceA {
		void m1(int a);
	}
	
	@Metric(name="dit", value=0)
	@Metric(name="noc", value=1)
	@Metric(name="rfc", value=2)
	public static class ClassA {
		void m1() {}
		void m3() {}
	}
	
	@Metric(name="dit", value=2)
	@Metric(name="noc", value=1)
	@Metric(name="rfc", value=4)
	public static class ClassB extends ClassA implements InterfaceB, InterfaceA {
		public void m1() {}
		@Override
		public void m1(int a) {}
		public void m4() {}
	}
	
	@Metric(name="dit", value=3)
	@Metric(name="noc", value=0)
	@Metric(name="rfc", value=4)
	public static class ClassC extends ClassB implements InterfaceA {
		public void m1() {}
		protected void m5() {}
	}
}
