package de.hub.srcrepo;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
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
import org.eclipse.jgit.api.Git;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.discoverer.internal.io.java.JavaReader;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.BindingManager;
import org.eclipse.modisco.kdm.source.extension.discovery.AbstractRegionDiscoverer2;
import org.eclipse.modisco.kdm.source.extension.discovery.SourceVisitListener;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.JavaDiff;

public class MoDiscoGitModelImportVisitor implements IGitModelVisitor, SourceVisitListener {

	// parameter
	private final Model javaModel;
	private final IJavaProject javaProject;
	private final Git git;

	// helper
	private AbstractRegionDiscoverer2<Object> abstractRegionDiscoverer;

	// state
	private JavaReader javaReader;
	private BindingManager javaBindings;
	private CompilationUnit lastCU;

	public MoDiscoGitModelImportVisitor(Git git, IJavaProject javaProject, Model targetModel) {
		super();
		this.javaProject = javaProject;
		this.javaModel = targetModel;
		this.git = git;

		abstractRegionDiscoverer = new AbstractRegionDiscoverer2<Object>() {
			@Override
			public boolean isApplicableTo(Object source) {
				return false;
			}

			@Override
			protected void basicDiscoverElement(Object source, IProgressMonitor monitor) throws DiscoveryException {
				throw new UnsupportedOperationException();
			}
		};
		abstractRegionDiscoverer.addSourceVisitListener(this);
	}

	@Override
	public void sourceRegionVisited(String filePath, int startOffset, int endOffset, int startLine, int endLine,
			EObject targetNode) {
		if (targetNode.eClass() == JavaPackage.eINSTANCE.getCompilationUnit()) {
			lastCU = (CompilationUnit) targetNode;
		}
	}

	@Override
	public void onStartCommit(Commit commit) {
		SrcRepoActivator.INSTANCE.info("Visit commit " + commit.getName());
		// checkout the corresponding revision and update the eclipse project
		try {
			git.checkout().setName(commit.getName()).call();
			javaProject.getProject().refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (Exception e) {
			SrcRepoActivator.INSTANCE.error("Exception while checking out and updating IJavaProject", e);
		}

		// TODO one JavaReader instance should be enough
		// setup the JavaReader used to import the java model
		javaReader = new JavaReader(JavaFactory.eINSTANCE, new HashMap<String, Object>(), abstractRegionDiscoverer) {
			@Override
			protected BindingManager getBindingManager() {
				return getGlobalBindings();
			}
		};
		javaReader.setDeepAnalysis(true);
		javaReader.setIncremental(true);

		// start with fresh bindings for each commit. These are later merged
		// with the existing bindings from former commits.
		javaReader.setGlobalBindings(new BindingManager(JavaFactory.eINSTANCE));
		if (javaReader.isIncremental()) {
			javaReader.getGlobalBindings().enableIncrementalDiscovering(javaModel);
		}
	}

	@Override
	public void onCompleteCommit(Commit commit) {
		// merge bindings and then resolve all references (indirectly via #terminate)
		BindingManager commitBindings = javaReader.getGlobalBindings();
		if (javaBindings == null) {
			javaBindings = commitBindings;
		} else {
			javaBindings.addBindings(commitBindings);
			javaReader.setGlobalBindings(javaBindings);
		}
		javaReader.terminate(new NullProgressMonitor());
	}

	@Override
	public void onCopiedFile(Diff diff) {

	}

	@Override
	public void onRenamedFile(Diff diff) {

	}

	@Override
	public void onAddedFile(Diff diff) {
		onModifiedFile(diff);
	}

	@Override
	public void onModifiedFile(Diff diff) {
		if (diff instanceof JavaDiff) {
			JavaDiff javaDiff = (JavaDiff)diff;
			IPath path = new Path(javaDiff.getNewPath());
			path = path.removeFirstSegments(1); // TODO working directory paths are
												// seldom java project paths
			ICompilationUnit cu = null;
			IJavaElement element = null;
	
			IResource resource = javaProject.getProject().findMember(path);
			element = JavaCore.create(resource, javaProject);
	
			if (element != null && element instanceof ICompilationUnit) {
				cu = (ICompilationUnit) element;
				lastCU = null;
				SrcRepoActivator.INSTANCE.info("import compilation unit " + element.getPath());
				javaReader.readModel(cu, javaModel, new NullProgressMonitor());
				if (lastCU != null) {
					javaDiff.setCompilationUnit(lastCU);
				} else {
					SrcRepoActivator.INSTANCE.error("Reading comilation unit did not result in a CompilationUnit model for " + path);
				}
			} else {
				SrcRepoActivator.INSTANCE.error("A java diff points to a no or a non compilation unit element for " + path);
			}
		}
	}

	@Override
	public void onDeletedFile(Diff diff) {

	}

}
