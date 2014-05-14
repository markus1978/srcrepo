package de.hub.srcrepo;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import etm.contrib.console.HttpConsoleServer;
import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

public class RepositoryModelFlatTraversal {
	
	private final RepositoryModel repositoryModel;
	private final IRepositoryModelVisitor visitor;
	private final EtmMonitor etmMonitor;
	
	public RepositoryModelFlatTraversal(RepositoryModel repositoryModel,
			IRepositoryModelVisitor visitor) {
		super();
		this.repositoryModel = repositoryModel;
		this.visitor = visitor;
		BasicEtmConfigurator.configure();
		this.etmMonitor = EtmManager.getEtmMonitor();
	}

	public void run() {
		etmMonitor.start();
		HttpConsoleServer etmServer = new HttpConsoleServer(etmMonitor);
		etmServer.setListenPort(45000);
		etmServer.start();				
		
		int count = 0;		
		
		for(Rev rev: repositoryModel.getAllRevs()) {
			visitRev(rev, count++);
			
			if (count % 100 == 0 && count != 0) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				etmMonitor.render(new SimpleTextRenderer(new OutputStreamWriter(byteArrayOutputStream)));
				SrcRepoActivator.INSTANCE.info(byteArrayOutputStream.toString());
			}
		}
		
		etmMonitor.stop();
		etmServer.stop();
	}
	
	private void visitRev(Rev rev, int number) {
		EtmPoint visitPoint = etmMonitor.createPoint(getClass().getCanonicalName() + ":visit");
		
		EtmPoint startPoint = etmMonitor.createPoint(getClass().getCanonicalName() + ":start");
		visitor.onStartRev(rev, number);
		startPoint.collect();
		
		for (ParentRelation parentRelation: rev.getParentRelations()) {
			for (Diff diff : parentRelation.getDiffs()) {
				EtmPoint diffPoint = etmMonitor.createPoint(getClass().getCanonicalName() + ":diff");
				if (diff.getType() == ChangeType.ADD) {
					visitor.onAddedFile(diff);
				} else if (diff.getType() == ChangeType.COPY) {
					visitor.onCopiedFile(diff);
				} else if (diff.getType() == ChangeType.DELETE) {
					visitor.onDeletedFile(diff);
				} else if (diff.getType() == ChangeType.MODIFY) {
					visitor.onModifiedFile(diff);
				} else if (diff.getType() == ChangeType.RENAME) {
					visitor.onRenamedFile(diff);
				}
				diffPoint.collect();
			}
		}
		EtmPoint completePoint = etmMonitor.createPoint(getClass().getCanonicalName() + ":complete");
		visitor.onCompleteRev(rev);
		completePoint.collect();
		visitPoint.collect();
	}	

	public static void traverse(RepositoryModel repositoryModel, IRepositoryModelVisitor visitor) {
		new RepositoryModelFlatTraversal(repositoryModel, visitor).run();
	}	
}
