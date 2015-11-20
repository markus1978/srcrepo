package de.hub.srcrepo.repositorymodel.util;

import java.util.ArrayList;
import java.util.List;

import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory;

public class RepositoryModelUtil {
	
	public static List<RepositoryModel> scheduledForImport(RepositoryModelDirectory directory) {
		List<RepositoryModel> results = new ArrayList<RepositoryModel>();
		for (RepositoryModelDirectory subDirectory: directory.getSubDirectories()) {
			results.addAll(scheduledForImport(subDirectory));
		}
		for (RepositoryModel repository: directory.getRepositories()) {
			if (repository.getMetaData() != null && repository.getMetaData().getImportMetaData().isScheduled()) {
				results.add(repository);
			}
		}
		
		return results;
	}
	
	public static List<RepositoryModel> imported(RepositoryModelDirectory directory) {
		List<RepositoryModel> results = new ArrayList<RepositoryModel>();
		for (RepositoryModelDirectory subDirectory: directory.getSubDirectories()) {
			results.addAll(imported(subDirectory));
		}
		for (RepositoryModel repository: directory.getRepositories()) {
			if (repository.getMetaData() != null && repository.getMetaData().getImportMetaData().isImported()) {
				results.add(repository);
			}
		}
		
		return results;
	}
	
	public static void updateImportStatuses(RepositoryModelDirectory directory) {
		for (RepositoryModelDirectory subDirectory: directory.getSubDirectories()) {
			updateImportStatuses(subDirectory);
		}
		
		directory.getImported().clear();
		directory.getImported().addAll(imported(directory));
		
		directory.getScheduledForImport().clear();
		directory.getScheduledForImport().addAll(scheduledForImport(directory));
	}
}
