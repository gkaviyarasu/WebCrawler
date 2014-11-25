package com.webcrawler.helper;

import java.io.File;
import java.io.IOException;

public class DirectoryHelper {

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
