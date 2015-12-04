package de.hub.srcrepo;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import de.hub.srcrepo.repositorymodel.DataSet;
import de.hub.srcrepo.repositorymodel.DataStoreMetaData;
import de.hub.srcrepo.repositorymodel.ImportMetaData;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryElement;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;

public class RepositoryModelUtil {
	private static final class RevCacheAdapter extends AdapterImpl {
		final RepositoryModel model;
		final Map<String, Rev> revMap = new HashMap<String, Rev>();
		
		RevCacheAdapter(RepositoryModel model) {
			this.model = model;
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
					// try to find the new rev in the model to get a proxy and deal with emffrags proxy limitations
					Rev modelRev = model.getAllRevs().get(model.getAllRevs().size()-1);
					if (modelRev.getName().equals(rev.getName())) {
						rev = modelRev;
					}
					revMap.put(rev.getName(), rev);
				} else if (msg.getEventType() == Notification.REMOVE) {
					Rev rev = (Rev)msg.getOldValue();
					revMap.remove(rev.getName());
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
	
	public static boolean isRoot(Rev rev) {
		return rev.getParentRelations().size() == 1 && rev.getParentRelations().get(0).getParent() == null;
	}
	
	public static int parents(Rev rev) {
		int result = 0;
		for(ParentRelation parentRelation: rev.getParentRelations()) {
			if (parentRelation.getParent() != null) {
				result++;
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends DataSet> T getData(RepositoryElement element, String name) {
		for(DataSet dataSet: element.getDataSets()) {
			if (name.equals(dataSet.getName())) {
				return (T)dataSet;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends DataSet> T getData(RepositoryElement element, EClass clazz) {
		for(DataSet dataSet: element.getDataSets()) {
			if (clazz.isSuperTypeOf(dataSet.eClass())) {
				return (T)dataSet;
			}
		}
		
		DataSet data = (DataSet)element.eClass().getEPackage().getEFactoryInstance().create(clazz);
		element.getDataSets().add(data);
		return (T)data;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends DataSet> T getData(RepositoryElement element, Class<T> clazz) {
		for(DataSet dataSet: element.getDataSets()) {
			if (clazz.isAssignableFrom(dataSet.getClass())) {
				return (T)dataSet;
			}
		}
		
		return null;
	}
	
	public static ImportMetaData getImportMetaData(RepositoryModel model) {
		return getData(model, ((RepositoryModelPackage)model.eClass().getEPackage()).getImportMetaData());
	}
	
	public static RepositoryMetaData getMetaData(RepositoryModel model) {
		return getData(model, ((RepositoryModelPackage)model.eClass().getEPackage()).getRepositoryMetaData());
	}
	
	public static DataStoreMetaData getDataStoreMetaData(RepositoryModel model) {
		return getData(model, DataStoreMetaData.class);
	}
}
