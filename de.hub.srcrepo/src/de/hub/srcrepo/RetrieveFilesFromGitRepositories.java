package de.hub.srcrepo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


/**
 * This program is untested, requires java 7, and a few de-comments.
 * @author Markus
 *
 */
public class RetrieveFilesFromGitRepositories {
	
	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.out.println("wrong usage: ... list-file tmp-dir pattern");
		}
		
		File repositoryListFile = new File(args[0]);
		final File tmpDirectory = new File(args[1]);
		final String pattern = args[3];
		
		String[] repositoryList = readLines(repositoryListFile.toString());
		SrcRepoActivator.standalone();
		SrcRepoActivator.INSTANCE.info("Start to go through " + repositoryList.length + " repositories.");
		
		ISourceControlSystem sourceControlSystem = new GitSourceControlSystem();
		for(String url: repositoryList) {
			File workingCopy = new File(tmpDirectory.getAbsoluteFile() + "/" + url.substring(url.lastIndexOf("/")+1));
			sourceControlSystem.createWorkingCopy(workingCopy, url);
			List<File> files = new ArrayList<File>();
			walk(workingCopy, pattern, files);
			for (File file: files) {
                String fileSubPath = file.toString().substring(tmpDirectory.getAbsolutePath().length());
				SrcRepoActivator.INSTANCE.info("Moving detected file " + fileSubPath);
				// Java 7 required
				throw new IllegalAccessException("uncomment following three lines and compile with Java 7.");
//				Path path = new File(resultsDirectory.getAbsolutePath() + fileSubPath).toPath();
//				Files.createDirectories(new File(path.toString()).getParentFile().toPath());
//				Files.move(file.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
			}
		}		
		sourceControlSystem.close();
	}
	
    public static void walk(File root, String pattern, List<File> result) {
        File[] list = root.listFiles();
        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk(new File(f.getAbsolutePath()), pattern, result );
            }
            else {
            	if (f.getAbsolutePath().endsWith(pattern)) {
            		result.add(f);
            	}
            }
        }
    }
	
	public static String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Collection<String> lines = new HashSet<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

}
