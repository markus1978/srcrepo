package de.hub.srcrepo;

import java.util.HashMap;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.kdm.source.extension.discovery.AbstractRegionDiscoverer2;
import org.eclipse.modisco.kdm.source.extension.discovery.SourceVisitListener;

import de.hub.srcrepo.gitmodel.JavaDiff;

public class MoDiscoGitModelImportHandler implements IGitModelImportHandler<JavaDiff>, SourceVisitListener {
	
	private final IResourceHandler resourceHandler;
	private final IJavaProject javaProject;
	private CompilationUnit lastCU;
	private JavaReader javaReader;
	private Model javaModel;

	public MoDiscoGitModelImportHandler(IResourceHandler resourceHandler, IJavaProject javaProject) {
		super();
		this.resourceHandler = resourceHandler;
		this.javaProject = javaProject;
	}

	@Override
	public void sourceRegionVisited(String filePath, int startOffset, int endOffset, int startLine, int endLine,
			EObject targetNode) {
		if (targetNode.eClass() == JavaPackage.eINSTANCE.getCompilationUnit()) {
			lastCU = (CompilationUnit)targetNode;
		}
	}

	public void init() throws Exception {
		AbstractRegionDiscoverer2<Object> abstractRegionDiscoverer = new AbstractRegionDiscoverer2<Object>() {
			@Override
			public boolean isApplicableTo(Object source) {			
				return false;
			}

			@Override
			protected void basicDiscoverElement(Object source, IProgressMonitor monitor) throws DiscoveryException {
				throw new UnsupportedOperationException();
			}};
		abstractRegionDiscoverer.addSourceVisitListener(this);
		javaReader = new JavaReader(JavaFactory.eINSTANCE, new HashMap<String,Object>(), abstractRegionDiscoverer);
		javaReader.setDeepAnalysis(true);
		javaReader.setIncremental(true);
		
		javaModel = JavaFactory.eINSTANCE.createModel();		
		resourceHandler.addContents(javaModel);
	}
	
	@Override
	public void onCopiedFile(JavaDiff diff) {
		
	}

	@Override
	public void onRenamedFile(JavaDiff diff) {
		
	}

	@Override
	public void onAddedFile(JavaDiff diff) {
		IPath path = new Path(diff.getNewPath());
		path = path.removeFirstSegments(1); // TODO working directory paths are seldom java project paths
		ICompilationUnit cu = null;
		IJavaElement element = null;
	
		IResource resource = javaProject.getProject().findMember(path);
		element = JavaCore.create(resource, javaProject);
	
		if (element != null && element instanceof ICompilationUnit) {
			cu = (ICompilationUnit)element;
			lastCU = null;
			javaReader.readModel(cu, javaModel, new NullProgressMonitor());
			if (lastCU != null) {
				diff.setCompilationUnit(lastCU);
			} else {
				// TODO log something
				System.out.println("@ reading cu did not result in a cu model object: " + path);
			}
		} else {
			// TODO log something
			System.out.println("@ non java or unknown file: " + path);
		}
	}

	@Override
	public void onModifiedFile(JavaDiff diff) {
		
	}

	@Override
	public void onDeletedFile(JavaDiff diff) {
		
	}

}
