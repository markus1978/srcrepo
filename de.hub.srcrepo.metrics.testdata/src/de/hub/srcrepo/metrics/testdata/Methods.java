package de.hub.srcrepo.metrics.testdata;

import de.hub.srcrepo.metrics.Metric;

@Metric(name="dit",value=0)
@Metric(name="wmc",value=3)
@Metric(name="lcom", value=1)
public class Methods {
	
	int f1 = 0;
	int f2 = 1;
	int f3 = 2;
	
	public int m1() {
		return f1;
	}
	
	public int m2() {
		return this.f1 + f2;
	}

	public int m3() {
		return f3;
	}
	
	public static class Inner {
		public void noInfluence() {
			
		}
	}
}
