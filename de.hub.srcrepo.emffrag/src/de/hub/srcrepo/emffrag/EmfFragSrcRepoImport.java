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
import de.hub.srcrepo.IRepositoryModelVisitor;
import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.RepositoryModelFlatTraversal;
import de.hub.srcrepo.RepositoryModelRevisionCheckoutVisitor;
import de.hub.srcrepo.RepositoryModelTraversal;
import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.TraversalState;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

public class EmfFragSrcRepoImport implements IApplication {
	
	public static class Configuration {
		private final ISourceControlSystem scs; 
		private final File workingDirectory;
		private final URI modelURI;
		
		private String repositoryURL = null; 
		private boolean withBinaryResources = true; 
		private boolean withDisabledIndexes = false; 
		private boolean withDisabledUsages = false;
		private int bulkInsertSize = 1000;
		private int fragmentCacheSize = 1000;
		private boolean resume = false;
		private int stopAfterNumberOfRevs = -1;
		private boolean skipSourceCodeImport = false;
		private boolean checkOutWithoutImport = false;
		private boolean useFlatTraversal = false;
		
		public Configuration(ISourceControlSystem scs,
				File workingDirectory, URI modelURI) {
			super();
			this.scs = scs;
			this.workingDirectory = workingDirectory;
			this.modelURI = modelURI;
		}

		public Configuration repositoryURL(String repositoryURL) {
			this.repositoryURL = repositoryURL;
			return this;
		}

		public Configuration withBinaryResources(boolean withBinaryResources) {
			this.withBinaryResources = withBinaryResources;
			return this;
		}

		public Configuration withDisabledIndexes(boolean withDisabledIndexes) {
			this.withDisabledIndexes = withDisabledIndexes;
			return this;
		}

		public Configuration withDisabledUsages(boolean withDisabledUsages) {
			this.withDisabledUsages = withDisabledUsages;
			return this;
		}

		public Configuration bulkInsertSize(int bulkInsertSize) {
			this.bulkInsertSize = bulkInsertSize;
			return this;
		}

		public Configuration fragmentCacheSize(int fragmentCacheSize) {
			this.fragmentCacheSize = fragmentCacheSize;
			return this;
		}

		public Configuration resume(boolean resume) {
			this.resume = resume;
			return this;
		}

		public Configuration stopAfterNumberOfRevs(int stopAfterNumberOfRevs) {
			this.stopAfterNumberOfRevs = stopAfterNumberOfRevs;
			return this;
		}	
		
		public Configuration skipSourceCodeImport() {
			this.skipSourceCodeImport = true;
			return this;
		}
		
		public Configuration checkOutWithoutImport() {
			this.checkOutWithoutImport = true;
			return this;
		}
		
		public Configuration useFlatTraversal() {
			this.useFlatTraversal = true;
			return this;
		}
	}
	
	public static class GitConfiguration extends Configuration {
		public GitConfiguration(File workingDirectory, URI modelURI) {
			super(new GitSourceControlSystem(), workingDirectory, modelURI);
		}		
	}
	
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
				withLongOpt("resume").
				withDescription("Resume import if a prior aborted import is saved within an existing repository model.").create());
		options.addOption(OptionBuilder.
				withLongOpt("abort-after").
				withDescription("Abort after a given number of revisions. The traversal is saved to resume later.").
				hasArg().withArgName("number-of-revs").create());
		options.addOption(OptionBuilder.
				withLongOpt("checkout-without-import").
				withDescription("Just checkout each rev, but do not import via MoDisco.").create());
		options.addOption(OptionBuilder.
				withLongOpt("use-flat-traversal").
				withDescription("Traverse the repository from refs to parents, not from root up.").create());
	
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
		Configuration config = new Configuration(new GitSourceControlSystem(), workingDirectory, modelURI);
		
		if (commandLine.hasOption("clone")) {
			config.repositoryURL(commandLine.getOptionValue("clone"));
		}		
		if (commandLine.hasOption("bulk-insert")) {
			config.bulkInsertSize(Integer.parseInt(commandLine.getOptionValue("bulk-insert")));
		}
		if (commandLine.hasOption("fragments-cache")) {			
			config.fragmentCacheSize(Integer.parseInt(commandLine.getOptionValue("fragments-cache")));
		}
		if (commandLine.hasOption("abort-after")) {
			config.stopAfterNumberOfRevs(Integer.parseInt(commandLine.getOptionValue("abort-after")));
		}
		config.withDisabledIndexes(commandLine.hasOption("disable-indexes"));
		config.withDisabledUsages(commandLine.hasOption("disable-usages"));
		config.withBinaryResources(!commandLine.hasOption("xmi-fragments"));
		config.resume(commandLine.hasOption("resume"));
		if (commandLine.hasOption("checkout-without-import")) {
			config.checkOutWithoutImport();
		}
		if (commandLine.hasOption("use-flat-traversal")) {
			config.useFlatTraversal();
		}
		
		importRepository(config);
		return IApplication.EXIT_OK;
	}
	
	public static RepositoryModel importRepository(Configuration config) {
		
		boolean stop = config.stopAfterNumberOfRevs > 0;
		
		// configuring		
		EmfFragActivator.instance.collectStatistics = true;
		EmfFragActivator.instance.globalEventListener = FGlobalEventListener.emptyInstance;
		EmfFragActivator.instance.useBinaryFragments = config.withBinaryResources;
		EmfFragActivator.instance.idSemantics = new NoReferencesIdSemantics(IdBehaviour.defaultModel);
		EmfFragActivator.instance.cacheSize = config.fragmentCacheSize;
		EmfFragActivator.instance.bulkInsertSize = config.bulkInsertSize;
				
		// init database
		if ("mongodb".equals(config.modelURI.scheme())) {
			EmfFragMongoDBActivator.class.getName();
			if (!config.resume) {
				MongoDBUtil.dropCollection(config.modelURI);
			}
		} else if ("hbase".equals(config.modelURI.scheme())) {
			EmfFragHBaseActivator.class.getName();
			if (!config.resume) {
				HBaseUtil.dropTable(config.modelURI.segment(0));
			}
		}

		
		// create fragmentation
		Resource resource = new ResourceSetImpl().createResource(config.modelURI);
		EmfFragActivator.instance.defaultModel = (FragmentedModel)resource;		
				
		// create necessary models
		RepositoryModelPackage repositoryModelPackage = createRepositoryModelPackage();
		JavaPackage javaModelPackage = createJavaPackage(config.withDisabledIndexes, config.withDisabledUsages);
		RepositoryModel repositoryModel = null;
		
		if (!config.resume) {
			repositoryModel = repositoryModelPackage.getRepositoryModelFactory().createRepositoryModel();		
			resource.getContents().add(repositoryModel);
		} else {
			if (resource.getContents().isEmpty()) {
				SrcRepoActivator.INSTANCE.error("No model found, I cannot resume import.");
				return null;
			}
			repositoryModel = (RepositoryModel) resource.getContents().get(0);
			if (repositoryModel.getTraversals() == null) {
				SrcRepoActivator.INSTANCE.info("No traversal present to resume,");
				return repositoryModel;
			}
		}
		
		
		// creating working copy
		try {
			if (config.repositoryURL != null) {
				SrcRepoActivator.INSTANCE.info("Cloning " + config.repositoryURL + " into " +  config.workingDirectory + ".");
				config.scs.createWorkingCopy(config.workingDirectory, config.repositoryURL);
			} else {
				config.scs.setWorkingCopy(config.workingDirectory);
			}
		} catch (SourceControlException e) {
			SrcRepoActivator.INSTANCE.error("Could not create working copy.", e);
		}
		
		// importing rev model
		if (!config.resume) {
			SrcRepoActivator.INSTANCE.info("Importing into " + config.modelURI + " from " +  config.workingDirectory + ".");
			try {
				config.scs.importRevisions(repositoryModel);
			} catch (SourceControlException e) {
				SrcRepoActivator.INSTANCE.error("Could not import the revision model.", e);
				return null;
			}
		}
		
		// importing source code
		IRepositoryModelVisitor sourceImportVisitor = null;
		if (config.checkOutWithoutImport) {
			sourceImportVisitor = new RepositoryModelRevisionCheckoutVisitor(config.scs, repositoryModel);
		} else {
			sourceImportVisitor = new EmffragMoDiscoImportRepositoryModelVisitor(config.scs, repositoryModel, javaModelPackage);
		}
		if (!config.skipSourceCodeImport) {		
			if (config.resume) {
				RepositoryModelTraversal.traverse(repositoryModel, sourceImportVisitor, repositoryModel.getTraversals(), true, stop ? config.stopAfterNumberOfRevs : -1);
			} else {
				if (stop) {
					TraversalState traversalState = repositoryModelPackage.getRepositoryModelFactory().createTraversalState();
					repositoryModel.setTraversals(traversalState);
					RepositoryModelTraversal.traverse(repositoryModel, sourceImportVisitor,  traversalState, false, config.stopAfterNumberOfRevs);	
				} else {
					if (config.useFlatTraversal) {
						RepositoryModelFlatTraversal.traverse(repositoryModel, sourceImportVisitor);
					} else {
						RepositoryModelTraversal.traverse(repositoryModel, sourceImportVisitor,  null, false, -1);
					}
				}
			}
		}
		
		SrcRepoActivator.INSTANCE.info("Import complete. Saving and closing everything.");
		config.scs.close();
		sourceImportVisitor.close();
		SrcRepoActivator.INSTANCE.info("Import done.");
		
		return repositoryModel;
	}

	@Override
	public void stop() {
		
	}

}
