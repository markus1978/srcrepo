package de.hub.srcrepo.emffrag;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

import com.google.common.base.Preconditions;

import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.Fragmentation;
import de.hub.emffrag.FragmentationSet;
import de.hub.emffrag.datastore.DataStoreImpl;
import de.hub.emffrag.datastore.IBaseDataStore;
import de.hub.emffrag.datastore.IDataStore;
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.emffrag.mongodb.MongoDBDataStore;
import de.hub.srcrepo.GitSourceControlSystem;
import de.hub.srcrepo.IRepositoryModelVisitor;
import de.hub.srcrepo.ISourceControlSystem;
import de.hub.srcrepo.ISourceControlSystem.SourceControlException;
import de.hub.srcrepo.RepositoryModelRevisionCheckoutVisitor;
import de.hub.srcrepo.RepositoryModelTraversal;
import de.hub.srcrepo.RepositoryModelUtil;
import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.repositorymodel.ImportMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryMetaData;
import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.RepositoryModelDirectory;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.util.RepositoryModelUtils;

public class EmfFragSrcRepoDirectoryImport implements IApplication {
	
	public interface RepositoryModelVisitorFactory {
		public IRepositoryModelVisitor createRepositoryModelVisitor();
	}
	
	public static class Configuration {
		private final ISourceControlSystem scs; 
				
		private String repositoryName = null;
		private URI directoryModelURI = URI.createURI("mongodb://localhost/EclipseAtGitHub");
		private File workingcopies = new File("workingcopies");
		private File locks = new File("locks");
		
		private boolean withDisabledUsages = true;
		private int fragmentCacheSize = 100;
		private boolean skipSourceCodeImport = false;
		private boolean checkOutWithoutImport = false;
		private boolean useCGit = false;
		
		private RepositoryModelVisitorFactory visitorFactory = null;
		
		public Configuration(ISourceControlSystem scs) {
			super();
			this.scs = scs;
		}

		public Configuration withEnabledUsages(boolean withEnabledUsages) {
			this.withDisabledUsages = !withEnabledUsages;
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
		
		public Configuration useCGit() {
			this.useCGit = true;
			return this;
		}
		
		public Configuration repositoryName(String value) {
			this.repositoryName = value;
			return this;
		}
		
		public Configuration directoryModelURI(URI value) {
			this.directoryModelURI = value;
			return this;
		}
		
		public Configuration locks(File value) {
			this.locks = value;
			return this;
		}
		
		public Configuration workingcopies(File value) {
			this.workingcopies = value;
			return this;
		}
		
		public Configuration withRepositoryModelVisitorFactory(RepositoryModelVisitorFactory visitorFactory) {
			this.visitorFactory = visitorFactory;
			return this;
		}
	}
	
	public static class GitConfiguration extends Configuration {
		public GitConfiguration() {
			super(new GitSourceControlSystem());
		}		
	}
	
	private Options createOptions() {
		Options options = new Options();
		options.addOption(Option.builder().
				longOpt("workingcopies").
				desc("Directory where I clone the repository to. Default is ./workingcopies").
				hasArg().argName("directory").build());
		options.addOption(Option.builder().
				longOpt("locks").
				desc("Directory where I put the lock. Default is ./locks").
				hasArg().argName("directory").build());
		options.addOption(Option.builder().
				longOpt("directory-uri").
				desc("Fragmentationi URI where the directory of repositories lies. Default is mongodb://localhost/git.eclipse.org").
				hasArg().argName("uri").build());
		options.addOption(Option.builder().
				longOpt("repository").
				desc("Imports the specifically mentioned repository.").
				hasArg().argName("name").build());
		options.addOption(Option.builder().
				longOpt("fragments-cache").
				desc("Number of cached fragments. Default is 100.").
				hasArg().argName("size").build());
		options.addOption(Option.builder().
				longOpt("enable-usages").
				desc("Enables the tracking of usagesXXX opposites, use with caution.").build());
		options.addOption(Option.builder().
				longOpt("use-c-git").
				desc("Use regular git shell commands and not jGit.").build());
	
		return options;
	}
	
	private boolean checkArgs(CommandLine cl) {
		return 
				cl.getArgList().size() == 0 &&
				(!cl.hasOption("log") || 
						(Integer.parseInt(cl.getOptionValue("log")) >= 0 && 
						 Integer.parseInt(cl.getOptionValue("log")) <=4)) &&
				(!cl.hasOption("fragments-cache") || Integer.parseInt(cl.getOptionValue("fragments-cache")) > 0);
	}
	
	private void printUsage() {
		new HelpFormatter().printHelp("eclipse ... [options]", createOptions());
	}
	
	public static JavaPackage configureJavaPackage(boolean disabledUsages) {	
		JavaPackage javaPackage = JavaPackage.eINSTANCE;
		
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
		
		Configuration config = new GitConfiguration();
		
		if (commandLine.hasOption("directory-uri")) {
			config.directoryModelURI(URI.createURI(commandLine.getOptionValue("directory-uri")));
		}
		if (commandLine.hasOption("locks")) {
			config.locks(new File(commandLine.getOptionValue("locks")));
		}
		if (commandLine.hasOption("workingcopies")) {
			config.workingcopies(new File(commandLine.getOptionValue("workingcopies")));
		}
		if (commandLine.hasOption("repository")) {
			config.repositoryName(commandLine.getOptionValue("repository"));
		}
		if (commandLine.hasOption("fragments-cache")) {			
			config.fragmentCacheSize(Integer.parseInt(commandLine.getOptionValue("fragments-cache")));
		}		
		config.withEnabledUsages(commandLine.hasOption("enable-usages"));
		if (commandLine.hasOption("checkout-without-import")) {
			config.checkOutWithoutImport();
		}		
		if (commandLine.hasOption("use-c-git")) {
			config.useCGit();
		}
		
		importRepository(config);
		return IApplication.EXIT_OK;
	}
	
	public static FragmentationSet openFragmentationSet(Configuration config) {	
		return new FragmentationSet(EmffragSrcRepo.packages, new IDataStore.IDataStoreFactory() {			
			@Override
			public IDataStore createDataStore(URI uri) {
				IBaseDataStore baseDataStore = null;
				if ("mongodb".equals(uri.scheme())) {
					MongoDBDataStore mongoDbBaseDataStore = new MongoDBDataStore(uri.authority(), uri.path().substring(1), false);
					baseDataStore = mongoDbBaseDataStore;
					
				} else {
					throw new RuntimeException("Unknown scheme " + uri.scheme());
				}
				
				IDataStore dataStore = new DataStoreImpl(baseDataStore, uri);
				return dataStore;
			}
		}, config.fragmentCacheSize);
	}
	
	public static void importRepository(Configuration config) {
		SrcRepoActivator.INSTANCE.useCGit = config.useCGit;
		RepositoryModel repositoryModel = null;

		// create fragmentation
		FragmentationSet fs = openFragmentationSet(config);
		Fragmentation fragmentation = fs.getFragmentation(config.directoryModelURI);		
		Preconditions.checkArgument(fragmentation.getRoot() != null, "There is no data at " + config.directoryModelURI.toString());
		File lockFile = null;
		try {
			// find a repository to import
			RepositoryModelDirectory directory = (RepositoryModelDirectory)fragmentation.getRoot();
			List<RepositoryModel> scheduledForImport = RepositoryModelUtils.scheduledForImport(directory);
			Iterator<RepositoryModel> scheduledForImportIterator = scheduledForImport.iterator();			
			String repositoryModelNameAsFileName = null;
			RepositoryModel currentRepositoryModel = null;
			while (scheduledForImportIterator.hasNext()) {
				// try to create a lock the next possible repositoryModel
				currentRepositoryModel = scheduledForImportIterator.next();
				if ((ImportMetaData)RepositoryModelUtil.getData(currentRepositoryModel, RepositoryModelPackage.eINSTANCE.getImportMetaData()) == null) {
					continue; // obviously not ready to be imported
				}
				String currentRepositoryName = RepositoryModelUtils.qualifiedName(currentRepositoryModel);
				if (config.repositoryName == null || config.repositoryName.equals(currentRepositoryName)) {
					repositoryModelNameAsFileName = currentRepositoryName.replaceAll("[^\\w_\\.]+", "_");
					lockFile = new File(config.locks, repositoryModelNameAsFileName);
					if (!lockFile.createNewFile()) {
						// could not aquire the lock
						SrcRepoActivator.INSTANCE.warning("Could not aquire lock for scheduled repository " + currentRepositoryName + "!");
					} else {
						repositoryModel = currentRepositoryModel;
					}
				}
			}
			
			if (repositoryModel == null) {
				SrcRepoActivator.INSTANCE.error("Could not find a scheduled repositoryModel.");
				return;
			}
			
			// try to update the import status the repository model
			try {				
				ImportMetaData importMetaData = RepositoryModelUtil.getData(repositoryModel, RepositoryModelPackage.eINSTANCE.getImportMetaData());
				importMetaData.setScheduled(false);
				importMetaData.setImporting(true);
				fs.save();
			} catch (Exception e) {
				SrcRepoActivator.INSTANCE.error("Unexpected error during updating the import status on the repository.", e);
			}
			
			// create necessary models
			JavaPackage javaModelPackage = configureJavaPackage(config.withDisabledUsages);
			
			// creating working copy
			File workingDirectory = null;
			try {
				RepositoryMetaData metaData = RepositoryModelUtil.getData(repositoryModel, RepositoryModelPackage.eINSTANCE.getRepositoryMetaData());
				ImportMetaData importMetaData = RepositoryModelUtil.getData(repositoryModel, RepositoryModelPackage.eINSTANCE.getImportMetaData());
				String repositoryCloneURL = metaData.getOrigin();
				workingDirectory = new File(config.workingcopies, repositoryModelNameAsFileName);
				importMetaData.setWorkingCopy(workingDirectory.getAbsolutePath());
				
				SrcRepoActivator.INSTANCE.info("Cloning " + repositoryCloneURL + " into " +  workingDirectory + ".");
				config.scs.createWorkingCopy(workingDirectory, repositoryCloneURL, true);
			} catch (SourceControlException e) {
				SrcRepoActivator.INSTANCE.error("Could not create working copy.", e);			
			}
			
			// importing rev model
			SrcRepoActivator.INSTANCE.info("Importing into " + EcoreUtil.getURI(repositoryModel) + " from " +  workingDirectory + ".");
			try {				
				config.scs.importRevisions(repositoryModel);
			} catch (SourceControlException e) {
				SrcRepoActivator.INSTANCE.error("Could not import the revision model.", e);
				return;
			}
			
			// importing source code
			if (!config.skipSourceCodeImport) {		
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
				RepositoryModelTraversal.traverse(repositoryModel, sourceImportVisitor,  null, false, -1);
				try {
					sourceImportVisitor.close();
				} catch (Exception e) {
					SrcRepoActivator.INSTANCE.warning("Exception during cleanup and closing.", e);
				}
			}
			
			// close everything
			try {
				SrcRepoActivator.INSTANCE.info("Import complete. Saving and closing everything.");
				config.scs.close();
			} catch (Exception e) {
				SrcRepoActivator.INSTANCE.warning("Exception during cleanup and closing.", e);
			}
			
			// try to update the import status the repository model
			try {
				ImportMetaData importMetaData = RepositoryModelUtil.getData(repositoryModel, RepositoryModelPackage.eINSTANCE.getImportMetaData());
				importMetaData.setScheduled(false);
				importMetaData.setImporting(false);
				importMetaData.setImported(true);
			} catch (Exception e) {
				SrcRepoActivator.INSTANCE.error("Unexpected error during updating the import status on the repository.", e);
			}			
		} catch (Exception e) {
			SrcRepoActivator.INSTANCE.error("Unexpected and unhandled exception occured.", e);
		} finally {
			try {
				if(lockFile != null) {
					if (!lockFile.delete()) {
						SrcRepoActivator.INSTANCE.error("Could not delete the lock file.");	
					}
				}
				fs.close();
			} catch (Exception e) {
				SrcRepoActivator.INSTANCE.error("Unknown error during closing fragmentation.", e);
			}
			SrcRepoActivator.INSTANCE.info("Import done.");
		}
		
		return;
	}

	@Override
	public void stop() {
		
	}

}
