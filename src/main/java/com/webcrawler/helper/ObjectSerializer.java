package com.webcrawler.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializer {

	/**
	 * Writes the Object to the specified file.
	 * 
	 * @param object
	 * @param file
	 * @throws IOException
	 */
	public static void writeObject(Object object, File file)
			throws IOException {
		if (DirectoryHelper.isFileAlreadyExist(file)) {
			file.delete();
		}
		ObjectOutputStream objectOutStream = new ObjectOutputStream(
				new FileOutputStream(file));
		objectOutStream.writeObject(object);
		objectOutStream.close();
	}

	/**
	 * Reads the object from the specified file.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object readObject(File file) throws IOException,
			ClassNotFoundException {
		ObjectInputStream objectInStream = null;
		try {
			objectInStream = new ObjectInputStream(new FileInputStream(file));
			return objectInStream.readObject();
		} finally {
			objectInStream.close();
		}
	}

}
