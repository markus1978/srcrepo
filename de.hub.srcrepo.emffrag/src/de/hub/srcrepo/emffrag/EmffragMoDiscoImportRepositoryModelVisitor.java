package de.hub.srcrepo.emffrag;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.emffrag.util.Extensions;
import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.MoDiscoRepositoryModelImportVisitor;
import de.hub.srcrepo.emffrag.extensions.ExtensionsFactory;
import de.hub.srcrepo.emffrag.extensions.ImportLog;
import de.hub.srcrepo.emffrag.extensions.ImportLogEntry;
import de.hub.srcrepo.emffrag.extensions.ImportLogEntryType;
import de.hub.srcrepo.repositorymodel.RepositoryModel;

public class EmffragMoDiscoImportRepositoryModelVisitor extends MoDiscoRepositoryModelImportVisitor {

	public EmffragMoDiscoImportRepositoryModelVisitor(ISourceControlSystem sourceControlSystem, RepositoryModel repositoryModel, JavaPackage javaPackage) {
		super(sourceControlSystem, repositoryModel, javaPackage);	
	}

	@Override
	protected void reportImportError(EObject owner, String message,
			Throwable e, boolean controlledFail) {
		super.reportImportError(owner, message, e, controlledFail);
		ImportLog importLog = Extensions.get(owner, ImportLog.class);
		if (importLog == null) {
			importLog = ExtensionsFactory.eINSTANCE.createImportLog();
			Extensions.add(owner, importLog);
		}
		
		ImportLogEntry importLogEntry = ExtensionsFactory.eINSTANCE.createImportLogEntry();
		importLogEntry.setType(controlledFail ? ImportLogEntryType.FAILED : ImportLogEntryType.UNDEFINED);
		importLogEntry.setMessage(message);
		if (e != null) {
			importLogEntry.setException(e.getClass().getCanonicalName());
			importLogEntry.setExceptionMessage(e.getMessage());
		}
		importLog.getEntries().add(importLogEntry);
	}

	@Override
	public void close() {
		super.close();
		((FObjectImpl)repositoryModel).fFragmentation().save(null);
	}	
		
}
