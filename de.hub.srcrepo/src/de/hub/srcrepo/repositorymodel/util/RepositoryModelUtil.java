package de.hub.srcrepo.repositorymodel.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EReference;

import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;

public class RepositoryModelUtil {
	
	private static final class RevCacheAdapter extends AdapterImpl {
		Map<String, Rev> revMap = new HashMap<String, Rev>();
		
		RevCacheAdapter(RepositoryModel model) {
			for (Rev rev: model.getAllRevs()) {
				revMap.put(rev.getName(), rev);
			}
			model.eAdapters().add(this);
		}

		@Override
		public void notifyChanged(Notification msg) {
			Object feature = msg.getFeature();
			if (feature != null && feature instanceof EReference && ((EReference)feature).getName().equals(RepositoryModelPackage.eINSTANCE.getRepositoryModel_AllRevs().getName())) {
				if (msg.getEventType() == Notification.ADD) {
					Rev rev = (Rev)msg.getNewValue();
					revMap.put(rev.getName(), rev);
				} else if (msg.getEventType() == Notification.REMOVE) {
					Rev rev = (Rev)msg.getOldValue();
					revMap.remove(rev);
				}
			}				
		}
	}
	
	public static Rev getRev(RepositoryModel model, String name) {
		RevCacheAdapter revCacheAdapter = null;
		for (Adapter adapter: model.eAdapters()) {
			if (adapter instanceof RevCacheAdapter) {
				revCacheAdapter = (RevCacheAdapter)adapter;
			}
		}
		
		if (revCacheAdapter == null) {
			revCacheAdapter = new RevCacheAdapter(model);			
		}
		return revCacheAdapter.revMap.get(name);
	}
}
