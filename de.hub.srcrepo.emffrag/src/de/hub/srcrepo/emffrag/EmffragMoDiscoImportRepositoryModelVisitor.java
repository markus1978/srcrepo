package de.hub.srcrepo.emffrag;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import de.hub.emffrag.datastore.IDataStore;
import de.hub.emffrag.fragmentation.FObject;
import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor;
import de.hub.srcrepo.RepositoryModelUtil;
import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.repositorymodel.MongoDBMetaData;
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
		try {
			repositoryModel.eResource().save(null);
		} catch (Exception e) {
			SrcRepoActivator.INSTANCE.error("Unexpected error during saving the repository fragment.", e);
		}		
	}

	@Override
	public void close() {
		super.close();
		((FObject)repositoryModel).fFragmentation().close();
	}

	@Override
	protected void updateDataStoreMetaData(RepositoryModel model) {
		IDataStore dataStore = ((FObject)model).fFragmentation().getDataStore();
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
