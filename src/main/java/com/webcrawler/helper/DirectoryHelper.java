package com.webcrawler.helper;

import java.io.File;
import java.io.IOException;

/**
 * Directory Helper Helps to perform directory level operations like, creating a
 * directory recursively.
 */
public class DirectoryHelper {

	/**
	 * Creates the directories specified in the path, if they are not already
	 * present.
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static void createDirectory(String path) throws IOException {
		File destination = new File(path);
		if (destination.exists()) {
			if (!destination.isDirectory()) {
				throw new IOException("A directory is expected in "
						+ destination.getAbsolutePath());
			}
		} else {
			if (!destination.mkdirs()) {
				throw new IOException("Couldn't create a directory at "
						+ destination.getAbsolutePath());
			}
		}
	}
}
