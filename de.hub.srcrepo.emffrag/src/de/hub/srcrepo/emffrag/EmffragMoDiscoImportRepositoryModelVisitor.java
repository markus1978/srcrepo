package de.hub.srcrepo.emffrag;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import de.hub.emffrag.datastore.IDataStore;
import de.hub.emffrag.fragmentation.FObject;
import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor;
import de.hub.srcrepo.repositorymodel.MongoDBMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;

public class EmffragMoDiscoImportRepositoryModelVisitor extends MoDiscoRepositoryModelImportVisitor {
	
	public EmffragMoDiscoImportRepositoryModelVisitor(ISourceControlSystem sourceControlSystem, RepositoryModel repositoryModel, JavaPackage javaPackage) {
		super(sourceControlSystem, repositoryModel, javaPackage);
	}

	@Override
	public void onCompleteRev(Rev rev) {
		super.onCompleteRev(rev);
		((FObject)repositoryModel).fFragmentation().gc();
	}

	@Override
	public void close() {
		super.close();
		((FObject)repositoryModel).fFragmentation().close();
	}

	@Override
	protected void updateDataStoreMetaData(RepositoryMetaData metaData, RepositoryModel model) {
		IDataStore dataStore = ((FObject)model).fFragmentation().getDataStore();
		Object statObject = dataStore.getStats();
		if (statObject instanceof Map<?, ?>) {
			Map<?,?> stats = (Map<?,?>)statObject;
			if (stats.keySet().containsAll(Arrays.asList("ns", "serverUsed", "count", "avgObjSize", "storageSize"))) {
				MongoDBMetaData dataStoreMetaData = (MongoDBMetaData)metaData.getDataStoreMetaData();
				if (dataStoreMetaData == null) {
					dataStoreMetaData = repositoryFactory.createMongoDBMetaData();
					metaData.setDataStoreMetaData(dataStoreMetaData);
				}
				dataStoreMetaData.setNs((String)stats.get("ns"));
				dataStoreMetaData.setServer((String)stats.get("serverUsed"));
				dataStoreMetaData.setCount((Integer)stats.get("count"));
				dataStoreMetaData.setAvgObjectSize((Integer)stats.get("avgObjSize"));
				dataStoreMetaData.setStoreSize((Integer)stats.get("storageSize"));			
			}
		}
	}
}
