package de.hub.srcrepo.emffrag;

import java.io.File;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.fragmentation.FGlobalEventListener;
import de.hub.emffrag.fragmentation.FragmentedModel;
import de.hub.emffrag.fragmentation.IndexBasedIdSemantics.IdBehaviour;
import de.hub.emffrag.fragmentation.NoReferencesIdSemantics;
import de.hub.emffrag.hbase.EmfFragHBaseActivator;
import de.hub.emffrag.hbase.HBaseUtil;
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.emffrag.mongodb.MongoDBUtil;
import de.hub.srcrepo.GitSourceControlSystem;
import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.SrcRepoUtil;
import de.hub.srcrepo.repositorymodel.MoDiscoImport;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

public class EmfFragSrcRepoImport implements IApplication {
	
	@SuppressWarnings("static-access")
	private Options createOptions() {
		Options options = new Options();
		options.addOption(OptionBuilder.
				withLongOpt("clone").
				withDescription("Clone the repository, old local copy is removed if it exists.").
				hasArg().
				withArgName("url").create());
		options.addOption(OptionBuilder.
				withLongOpt("fragments-cache").
				withDescription("Number of cached fragments. Default is 1000.").
				hasArg().withArgName("size").create());
		options.addOption(OptionBuilder.
				withLongOpt("bulk-insert").
				withDescription("Number of bulk inserted key-value pairs. Default is 1000.").
				hasArg().withArgName("size").create());
		options.addOption(OptionBuilder.
				withLongOpt("disable-indexes").
				withDescription("Disables the use of indexes, turns all indexed value sets in the model as normal emf value sets.").create());
		options.addOption(OptionBuilder.
				withLongOpt("xmi-fragments").
				withDescription("Use XMI fragments instead of binary fragments.").create());
		options.addOption(OptionBuilder.
				withLongOpt("disable-usages").
				withDescription("Disables the tracking of usagesXXX opposites.").create());
		options.addOption(OptionBuilder.
				withLongOpt("root-commit").
				withDescription("Start the import at a specific commit.").
				hasArg().withArgName("commit").create());
		options.addOption(OptionBuilder.
				withLongOpt("resume").
				withDescription("Resume import if a prior aborted import is saved within an existing repository model.").create());
		options.addOption(OptionBuilder.
				withLongOpt("abort-after").
				withDescription("Abort after a given number of revisions. The traversal is saved to resume later.").
				hasArg().withArgName("number-of-revs").create());
		
		return options;
	}
	
	private boolean checkArgs(CommandLine cl) {
		return 
				cl.getArgList().size() == 2 &&
				(!cl.hasOption("log") || 
						(Integer.parseInt(cl.getOptionValue("log")) >= 0 && 
						 Integer.parseInt(cl.getOptionValue("log")) <=4)) &&
				(!cl.hasOption("fragments-cache") || Integer.parseInt(cl.getOptionValue("fragments-cache")) > 0) &&
				(!cl.hasOption("bulk-insert") || Integer.parseInt(cl.getOptionValue("bulk-insert")) > 0 ) &&
				(!cl.hasOption("abort-after") || Integer.parseInt(cl.getOptionValue("abort-after")) > 0);
	}
	
	private void printUsage() {
		new HelpFormatter().printHelp("eclipse ... [options] working-copy-path data-base-uri", createOptions());
	}
	

	public static JavaPackage createJavaPackage(boolean disabledIndexes, boolean disabledUsages) {	
		JavaPackage javaPackage = JavaPackage.eINSTANCE;
		
		if (disabledIndexes) {
			TreeIterator<EObject> eAllContents = javaPackage.eAllContents();
			while (eAllContents.hasNext()) {
				EObject next = eAllContents.next();
				if (next instanceof EAnnotation) {
					EAnnotation eAnnotation = (EAnnotation)next;
					String value = eAnnotation.getDetails().get("indexes");
					if (value != null && value.equals("true")) {
						eAnnotation.getDetails().put("indexes", "false");
					}
				}
			}
		}
		
		if (disabledUsages) {
			TreeIterator<EObject> eAllContents = javaPackage.eAllContents();
			while (eAllContents.hasNext()) {
				EObject next = eAllContents.next();
				if (next instanceof EReference) {
					EReference reference = (EReference)next;
					if (reference.getName().startsWith("usage")) {
						EReference eOpposite = reference.getEOpposite();
						if (eOpposite != null) {
							SrcRepoActivator.INSTANCE.info(reference.getEContainingClass().getName() + "." + reference.getName() 
									+ "->" + reference.getEOpposite().getEContainingClass().getName() + "." + reference.getEOpposite().getName());
							eOpposite.setEOpposite(null);
							reference.setEOpposite(null);								
						}						
					}
				}
			}
		}
		return javaPackage;
	}
	
	public static RepositoryModelPackage createRepositoryModelPackage() {
		return RepositoryModelPackage.eINSTANCE;
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		// ensure loading of plug-ins
		EmfFragActivator.class.getName();
		SrcRepoActivator.class.getName();
		EmfFragMongoDBActivator.class.getName();
		EmfFragHBaseActivator.class.getName();
		
		// checking command line options
		final Map<?,?> args = context.getArguments();
		final String[] appArgs = (String[]) args.get("application.args");
		
		CommandLineParser cliParser = new PosixParser();		
		Options options = createOptions();
		CommandLine commandLine = null;
		try {			
			commandLine = cliParser.parse(options, appArgs);
		} catch (Exception e) {
			printUsage();
			return IApplication.EXIT_OK;
		}
		if (!checkArgs(commandLine)) {
			printUsage();
			return IApplication.EXIT_OK; 
		}
		
		URI modelURI = URI.createURI((String)commandLine.getArgList().get(1));
		File workingDirectory = new File((String)commandLine.getArgList().get(0));
		
		String repositoryURL = null;
		if (commandLine.hasOption("clone")) {
			repositoryURL = commandLine.getOptionValue("clone");
		}
		
		int bulkInsertSize = 1000;
		if (commandLine.hasOption("bulk-insert")) {
			bulkInsertSize = Integer.parseInt(commandLine.getOptionValue("bulk-insert"));
		}
		
		int fragmentsCacheSize = 1000;		
		if (commandLine.hasOption("fragments-cache")) {			
			fragmentsCacheSize = Integer.parseInt(commandLine.getOptionValue("fragments-cache"));
		}
		
		String rootCommitName = null;
		if (commandLine.hasOption("root-commit")) {
			rootCommitName = commandLine.getOptionValue("root-commit");
		}
		String lastCommitName = null; 
		if (commandLine.hasOption("last-commit")) {
			lastCommitName = commandLine.getOptionValue("last-commit");
		}
		int abortAfterNumberOfRevs = -1;
		if (commandLine.hasOption("abort-after")) {
			abortAfterNumberOfRevs = Integer.parseInt(commandLine.getOptionValue("abort-after"));
		}
		boolean withDisabledIndexes = commandLine.hasOption("disable-indexes");
		boolean withDisabledUsages = commandLine.hasOption("disable-usages");
		boolean withBinaryFragments = !commandLine.hasOption("xmi-fragments");
		boolean resume = commandLine.hasOption("resume");
		
		if (lastCommitName != null) {
			importRepository(
					new GitSourceControlSystem(), 
					workingDirectory, 
					repositoryURL, 
					modelURI, 
					withBinaryFragments, 
					withDisabledIndexes, 
					withDisabledUsages, 
					bulkInsertSize, 
					fragmentsCacheSize, 
					resume, 
					abortAfterNumberOfRevs,
					rootCommitName, 
					lastCommitName);
		} else {
			importRepository(
					new GitSourceControlSystem(), 
					workingDirectory, 
					repositoryURL, 
					modelURI, 
					withBinaryFragments, 
					withDisabledIndexes, 
					withDisabledUsages, 
					bulkInsertSize,
					fragmentsCacheSize, 
					resume, 
					abortAfterNumberOfRevs,
					rootCommitName);
		}
		
		return IApplication.EXIT_OK;
	}
	
	public static RepositoryModel importRepository(
			ISourceControlSystem scs, 
			File workingDirectory, 
			String repositoryURL, 
			URI modelURI, 
			String startRevName,
			String... stopRevNames) {
		return importRepository(scs, workingDirectory, repositoryURL, modelURI, true, true, true, 1000, 1000, false, -1, startRevName, stopRevNames);
	}
	
	public static RepositoryModel importRepository(
			ISourceControlSystem scs, 
			File workingDirectory, 
			String repositoryURL, 
			URI modelURI, 
			boolean withBinaryResources, 
			boolean withDisabledIndexes, 
			boolean withDisabledUsages, 
			int bulkInsertSize, 
			int fragmentCacheSize,
			boolean resume,
			int stopAfterNumberOfRevs,
			String startRevName,
			String... stopRevNames) {
		
		boolean stop = stopAfterNumberOfRevs > 0;
		
		// configuring		
		EmfFragActivator.instance.collectStatistics = true;
		EmfFragActivator.instance.globalEventListener = FGlobalEventListener.emptyInstance;
		EmfFragActivator.instance.useBinaryFragments = withBinaryResources;
		EmfFragActivator.instance.idSemantics = new NoReferencesIdSemantics(IdBehaviour.defaultModel);
		EmfFragActivator.instance.cacheSize = fragmentCacheSize;
		EmfFragActivator.instance.bulkInsertSize = bulkInsertSize;
				
		// init database
		if ("mongodb".equals(modelURI.scheme())) {
			EmfFragMongoDBActivator.class.getName();
			if (!resume) {
				MongoDBUtil.dropCollection(modelURI);
			}
		} else if ("hbase".equals(modelURI.scheme())) {
			EmfFragHBaseActivator.class.getName();
			if (!resume) {
				HBaseUtil.dropTable(modelURI.segment(0));
			}
		}

		
		// create fragmentation
		Resource resource = new ResourceSetImpl().createResource(modelURI);
		EmfFragActivator.instance.defaultModel = (FragmentedModel)resource;		
				
		// create necessary models
		RepositoryModelPackage repositoryModelPackage = createRepositoryModelPackage();
		JavaPackage javaModelPackage = createJavaPackage(withDisabledIndexes, withDisabledIndexes);
		RepositoryModel repositoryModel = null;
		Model javaModel = null;
		
		if (!resume) {
			repositoryModel = repositoryModelPackage.getRepositoryModelFactory().createRepositoryModel();
			javaModel = javaModelPackage.getJavaFactory().createModel();		
			resource.getContents().add(repositoryModel);
			resource.getContents().add(javaModel);
			repositoryModel.setJavaModel(javaModel);
			javaModel.setName("Java source code repository model.");
		} else {
			if (resource.getContents().isEmpty()) {
				SrcRepoActivator.INSTANCE.error("No model found, I cannot resume import.");
				return null;
			}
			repositoryModel = (RepositoryModel) resource.getContents().get(0);
			javaModel = repositoryModel.getJavaModel();
			if (repositoryModel.getTraversals() == null) {
				SrcRepoActivator.INSTANCE.info("No traversal present to resume,");
				return repositoryModel;
			}
		}
		
		
		// creating working copy
		try {
			if (repositoryURL != null) {
				SrcRepoActivator.INSTANCE.info("Cloning " + repositoryURL + " into " +  workingDirectory + ".");
				scs.createWorkingCopy(workingDirectory, repositoryURL);
			} else {
				scs.setWorkingCopy(workingDirectory);
			}
		} catch (SourceControlException e) {
			SrcRepoActivator.INSTANCE.error("Could not create working copy.", e);
		}
		
		// importing rev model
		if (!resume) {
			SrcRepoActivator.INSTANCE.info("Importing into " + modelURI + " from " +  workingDirectory + ".");
			try {
				scs.importRevisions(repositoryModel);
			} catch (SourceControlException e) {
				SrcRepoActivator.INSTANCE.error("Could not import the revision model.", e);
				return null;
			}
		}
		
		// importing source code
		Rev startRev = startRevName != null ? repositoryModel.getRev(startRevName) : null;
		Rev[] stopRevs = new Rev[stopRevNames.length];
		int i = 0;
		for (String stopRevName: stopRevNames) {
			stopRevs[i] = repositoryModel.getRev(stopRevName);
		}
		EmffragMoDiscoImportRepositoryModelVisitor visitor = new EmffragMoDiscoImportRepositoryModelVisitor(scs, repositoryModel);
		if (resume) {
			SrcRepoUtil.traverseRepository(repositoryModel, startRev, stopRevs, visitor, repositoryModel.getTraversals(), true, stop ? stopAfterNumberOfRevs : -1);
		} else {
			if (stop) {
				MoDiscoImport traversal = repositoryModelPackage.getRepositoryModelFactory().createMoDiscoImport();
				repositoryModel.setTraversals(traversal);
				SrcRepoUtil.traverseRepository(repositoryModel, startRev, stopRevs, visitor, traversal, false, stopAfterNumberOfRevs);	
			} else {
				SrcRepoUtil.traverseRepository(repositoryModel, startRev, stopRevs, visitor);
			}
		}
		
		SrcRepoActivator.INSTANCE.info("Import complete. Saving and closing everything.");
		scs.close();
		visitor.close();
		SrcRepoActivator.INSTANCE.info("Import done.");
		
		return repositoryModel;
	}

	@Override
	public void stop() {
		
	}

}
