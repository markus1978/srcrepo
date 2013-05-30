package de.hub.srcrepo.emffrag;

import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.eclipse.emf.common.util.URI;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import de.hub.emffrag.EmfFragActivator;
import de.hub.emffrag.hbase.EmfFragHBaseActivator;
import de.hub.emffrag.hbase.HBaseUtil;
import de.hub.emffrag.mongodb.EmfFragMongoDBActivator;
import de.hub.emffrag.mongodb.MongoDBUtil;
import de.hub.srcrepo.JGitUtil;
import de.hub.srcrepo.SrcRepoActivator;

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
		
		return options;
	}
	
	private boolean checkArgs(CommandLine cl) {
		return 
				cl.getArgList().size() == 2 &&
				(!cl.hasOption("log") || 
						(Integer.parseInt(cl.getOptionValue("log")) >= 0 && 
						 Integer.parseInt(cl.getOptionValue("log")) <=4)) &&
				(!cl.hasOption("fragments-cache") || Integer.parseInt(cl.getOptionValue("fragments-cache")) > 0) &&
				(!cl.hasOption("bulk-insert") || Integer.parseInt(cl.getOptionValue("bulk-insert")) > 0);
	}
	
	private void printUsage() {
		new HelpFormatter().printHelp("eclipse ... [options] working-copy-path data-base-uri", createOptions());
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
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
		
		EmfFragActivator.class.getName();
		SrcRepoActivator.class.getName();

		URI dbURI = URI.createURI((String)commandLine.getArgList().get(1));
		String workingDirectory = (String)commandLine.getArgList().get(0);
		
		if ("mongodb".equals(dbURI.scheme())) {
			EmfFragMongoDBActivator.class.getName();
			MongoDBUtil.dropCollection(dbURI);		
		} else if ("hbase".equals(dbURI.scheme())) {
			EmfFragHBaseActivator.class.getName();
			if (commandLine.hasOption("bulk-insert")) {
				EmfFragActivator.instance.bulkInsertSize = Integer.parseInt(commandLine.getOptionValue("bulk-insert"));
			}
			HBaseUtil.dropTable(dbURI.segment(0));
		}
		
		if (!commandLine.hasOption("fragments-cache")) {
			EmfFragActivator.instance.cacheSize = 1000;
		} else {
			EmfFragActivator.instance.cacheSize = Integer.parseInt(commandLine.getOptionValue("fragments-cache"));
		}
		
		SrcRepoActivator.INSTANCE.info("Staring import into " + dbURI + " from " +  workingDirectory + ".");
		
		
		JGitUtil.importGit(commandLine.hasOption("clone") ? commandLine.getOptionValue("clone") : "", 
				workingDirectory, 
				dbURI, new EmfFragImportConfiguration());
		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		
	}

}
