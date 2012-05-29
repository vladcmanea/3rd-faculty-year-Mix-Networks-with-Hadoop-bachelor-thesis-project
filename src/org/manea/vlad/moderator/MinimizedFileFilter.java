package org.manea.vlad.moderator;

import java.io.File;
import java.io.FileFilter;

/**
 * is a file filter for the minimized file
 * 
 * @author administrator
 * 
 */
public class MinimizedFileFilter implements FileFilter {

	// prefix name
	public final String fileNamePrefix = "part-";

	/**
	 * accepts the file
	 * 
	 * @param file
	 *            the file
	 */
	public boolean accept(File file) {

		// return true if and only if the file has the prefix
		return file.getName().startsWith(fileNamePrefix);

	}

}
