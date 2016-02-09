package de.hub.srcrepo.emffrag;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.FObject;
import de.hub.emffrag.Fragmentation;
import de.hub.emffrag.FragmentationImpl;
import de.hub.emffrag.datastore.DataStoreImpl;
import de.hub.emffrag.datastore.IBaseDataStore;
import de.hub.emffrag.datastore.IDataStore;
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.emffrag.mongodb.MongoDBDataStore;
import de.hub.srcrepo.GitSourceControlSystem;
import de.hub.srcrepo.IRepositoryModelVisitor;
import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.RepositoryModelFlatTraversal;
import de.hub.srcrepo.RepositoryModelRevisionCheckoutVisitor;
import de.hub.srcrepo.RepositoryModelTraversal;
import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

public class EmfFragSrcRepoImport implements IApplication {
	
	public interface RepositoryModelVisitorFactory {
		public IRepositoryModelVisitor createRepositoryModelVisitor();
	}
	
	public static class Configuration {
		private final ISourceControlSystem scs; 
		private final File workingDirectory;
		private final URI modelURI;
		
		private String repositoryURL = null; 
		private int fragmentCacheSize = 100;
		private boolean skipSourceCodeImport = false;
		private boolean checkOutWithoutImport = false;
		private boolean useFlatTraversal = false;
		private boolean useCGit = false;
		
		private RepositoryModelVisitorFactory visitorFactory = null;
		
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

		public Configuration fragmentCacheSize(int fragmentCacheSize) {
			this.fragmentCacheSize = fragmentCacheSize;
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
		
		public Configuration useCGit() {
			this.useCGit = true;
			return this;
		}
		
		public Configuration withRepositoryModelVisitorFactory(RepositoryModelVisitorFactory visitorFactory) {
			this.visitorFactory = visitorFactory;
			return this;
		}
	}
	
	public static class GitConfiguration extends Configuration {
		public GitConfiguration(File workingDirectory, URI modelURI) {
			super(new GitSourceControlSystem(), workingDirectory, modelURI);
		}		
	}
	
	private Options createOptions() {
		Options options = new Options();
		options.addOption(Option.builder().
				longOpt("clone").
				desc("Clone the repository, old local copy is removed if it exists.").
				hasArg().
				argName("url").build());
		options.addOption(Option.builder().
				longOpt("fragments-cache").
				desc("Number of cached fragments. Default is 1000.").
				hasArg().argName("size").build());
		options.addOption(Option.builder().
				longOpt("checkout-without-import").
				desc("Just checkout each rev, but do not import via MoDisco.").build());
		options.addOption(Option.builder().
				longOpt("use-flat-traversal").
				desc("Traverse the repository rev by rev not traversining branches.").build());
		options.addOption(Option.builder().
				longOpt("use-c-git").
				desc("Use regular git shell commands and not jGit.").build());
	
		return options;
	}
	
	private boolean checkArgs(CommandLine cl) {
		return 
				cl.getArgList().size() == 2 &&
				(!cl.hasOption("log") || 
						(Integer.parseInt(cl.getOptionValue("log")) >= 0 && 
						 Integer.parseInt(cl.getOptionValue("log")) <=4)) &&
				(!cl.hasOption("fragments-cache") || Integer.parseInt(cl.getOptionValue("fragments-cache")) > 0);
	}
	
	private void printUsage() {
		new HelpFormatter().printHelp("eclipse ... [options] working-copy-path data-base-uri", createOptions());
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {		
		// ensure loading of plug-ins
		EmfFragActivator.class.getName();
		SrcRepoActivator.class.getName();
		EmfFragMongoDBActivator.class.getName();
		
		// checking command line options
		final Map<?,?> args = context.getArguments();
		final String[] appArgs = (String[]) args.get("application.args");
		
		CommandLineParser cliParser = new DefaultParser();		
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
		if (commandLine.hasOption("fragments-cache")) {			
			config.fragmentCacheSize(Integer.parseInt(commandLine.getOptionValue("fragments-cache")));
		}
		if (commandLine.hasOption("checkout-without-import")) {
			config.checkOutWithoutImport();
		}
		if (commandLine.hasOption("use-flat-traversal")) {
			config.useFlatTraversal();
		}
		if (commandLine.hasOption("use-c-git")) {
			config.useCGit();
		}
		
		importRepository(config);
		return IApplication.EXIT_OK;
	}
	
	public static Fragmentation openFragmentation(Configuration config, boolean dropIfExists) {
		IBaseDataStore baseDataStore = null;
		if ("mongodb".equals(config.modelURI.scheme())) {
			MongoDBDataStore mongoDbBaseDataStore = new MongoDBDataStore(config.modelURI.authority(), config.modelURI.path().substring(1), dropIfExists);
			baseDataStore = mongoDbBaseDataStore;
			
		} else {
			throw new RuntimeException("Unknown scheme " + config.modelURI.scheme());
		}
		
		IDataStore dataStore = new DataStoreImpl(baseDataStore, config.modelURI);
		List<EPackage> packages = new ArrayList<EPackage>();
		packages.add(JavaPackage.eINSTANCE);
		packages.add(RepositoryModelPackage.eINSTANCE);
		Fragmentation fragmentation = new FragmentationImpl(packages, dataStore, config.fragmentCacheSize);
		return fragmentation;
	}
	
	public static void closeFragmentation(Configuration config, Fragmentation fragmentation) {
		fragmentation.close();
		fragmentation.getDataStore().close();
	}
	
	public static void importRepository(Configuration config) {
		SrcRepoActivator.INSTANCE.useCGit = config.useCGit;

		// create fragmentation
		Fragmentation fragmentation = openFragmentation(config, true);
				
		// create necessary models
		RepositoryModelPackage repositoryModelPackage = RepositoryModelPackage.eINSTANCE;
		JavaPackage javaModelPackage = JavaPackage.eINSTANCE;
		RepositoryModel repositoryModel = null;
		

		repositoryModel = repositoryModelPackage.getRepositoryModelFactory().createRepositoryModel();					
		
		// creating working copy
		try {
			if (config.repositoryURL != null) {
				SrcRepoActivator.INSTANCE.info("Cloning " + config.repositoryURL + " into " +  config.workingDirectory + ".");
				config.scs.createWorkingCopy(config.workingDirectory, config.repositoryURL, false);
			} else {
				config.scs.setWorkingCopy(config.workingDirectory);
			}
		} catch (SourceControlException e) {
			SrcRepoActivator.INSTANCE.error("Could not create working copy.", e);
		}
		
		// importing rev model
		SrcRepoActivator.INSTANCE.info("Importing into " + config.modelURI + " from " +  config.workingDirectory + ".");
		try {
			config.scs.importRevisions(repositoryModel);
		} catch (SourceControlException e) {
			SrcRepoActivator.INSTANCE.error("Could not import the revision model.", e);
			return;
		}
		fragmentation.setRoot((FObject)repositoryModel);
		repositoryModel = fragmentation.getRoot();
		
		// importing source code
		IRepositoryModelVisitor sourceImportVisitor = null;
		if (config.visitorFactory != null) {
			sourceImportVisitor = config.visitorFactory.createRepositoryModelVisitor();
		} else {
			if (config.checkOutWithoutImport) {
				sourceImportVisitor = new RepositoryModelRevisionCheckoutVisitor(config.scs, repositoryModel);
			} else {
				sourceImportVisitor = new EmffragMoDiscoImportRepositoryModelVisitor(config.scs, repositoryModel, javaModelPackage);
			}
		}
		if (!config.skipSourceCodeImport) {		
			if (config.useFlatTraversal) {
				RepositoryModelFlatTraversal.traverse(repositoryModel, sourceImportVisitor);
			} else {
				RepositoryModelTraversal.traverse(repositoryModel, sourceImportVisitor);
			}
		}
		
		try {
			SrcRepoActivator.INSTANCE.info("Import complete. Saving and closing everything.");
			config.scs.close();
			sourceImportVisitor.close(repositoryModel);
		} catch (Exception e) {
			SrcRepoActivator.INSTANCE.warning("Exception during cleanup and closing.", e);
		} finally {
			closeFragmentation(config, fragmentation);
			SrcRepoActivator.INSTANCE.info("Import done.");
		}
	}

	@Override
	public void stop() {
		
	}

}
