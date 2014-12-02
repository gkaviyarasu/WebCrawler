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

	/**
	 * Checks whether the file is already present or not.
	 * 
	 * @param file
	 *            File that needs to be checked
	 * @return boolean true - If the file already present. false - If the file
	 *         is not present at all.
	 * @throws IOException
	 *             In case if the input is not a file.
	 */
	public static boolean isFileAlreadyExist(File file) throws IOException {
		if (file.exists()) {
			if (!file.isFile()) {
				throw new IOException("Unknown File URI");
			}
			return true;
		}
		return false;
	}
}
