package de.hub.srcrepo.emffrag;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import de.hub.emffrag.FObject;
import de.hub.emffrag.datastore.IDataStore;
import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor;
import de.hub.srcrepo.RepositoryModelUtil;
import de.hub.srcrepo.repositorymodel.MongoDBMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryModel;

public class EmffragMoDiscoImportRepositoryModelVisitor extends MoDiscoRepositoryModelImportVisitor {
	
	public EmffragMoDiscoImportRepositoryModelVisitor(ISourceControlSystem sourceControlSystem, RepositoryModel repositoryModel, JavaPackage javaPackage) {
		super(sourceControlSystem, repositoryModel, javaPackage);
	}

	@Override
	protected void updateDataStoreMetaData(RepositoryModel model) {
		IDataStore dataStore = ((FObject)model).fStoreObject().fFragmentation().getDataStore();
		Object statObject = dataStore.getStats();
		if (statObject instanceof Map<?, ?>) {
			Map<?,?> stats = (Map<?,?>)statObject;
			if (stats.keySet().containsAll(Arrays.asList("ns", "serverUsed", "count", "avgObjSize", "storageSize"))) {
				MongoDBMetaData dataStoreMetaData = RepositoryModelUtil.getData(model, repositoryPackage.getMongoDBMetaData());				
				dataStoreMetaData.setNs((String)stats.get("ns"));
				dataStoreMetaData.setServer((String)stats.get("serverUsed"));
				dataStoreMetaData.setCount(((Number)stats.get("count")).longValue());
				dataStoreMetaData.setAvgObjectSize(((Number)stats.get("avgObjSize")).longValue());
				dataStoreMetaData.setStoreSize(((Number)stats.get("storageSize")).longValue());			
			}
		}
	}
}
