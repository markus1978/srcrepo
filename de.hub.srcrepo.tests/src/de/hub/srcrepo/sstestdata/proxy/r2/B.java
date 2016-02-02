package de.hub.srcrepo.sstestdata.proxy.r2;

import java.util.List;

public class B {
	public void clear(List<String> a) {
		a.clear();
		while (!a.isEmpty())
			a.remove(0);
	}
}
