package de.hub.srcrepo.sstestdata.innerRefs.goal;

public class C1 {
	@SuppressWarnings("unused")
	private static C1 instance = new C1();
	
	public C1() {
		super();
		instance = this;
	}
}
