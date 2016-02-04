package de.hub.srcrepo.sstestdata.proxy.goal;

import java.util.List;

public class B {
	public void clear(List<String> a) {
		a.clear();
		while (!a.isEmpty())
			a.remove(0);
	}
}
