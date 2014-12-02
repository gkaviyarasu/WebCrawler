package com.webcrawler.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileStreamWriter implements StreamWriter {

	/**
	 * Holds the file Object in which it is going to write.
	 */
	File file;

	/**
	 * Holds the OutputStream in which it is going to write the changes. Or, it
	 * holds the stream object for the above file.
	 */
	OutputStream outputStream;

	/**
	 * Constructor to initialize the FileStreamWriter instance. It initializes
	 * the file object as well as the OutputStream
	 * 
	 * @param file
	 *            File object in which it should write the contents.
	 * @throws IOException
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 */
	public FileStreamWriter(File file) throws IOException {
		this.file = file;
		this.outputStream = new FileOutputStream(file);
	}

	/**
	 * See {@link StreamWriter#write(byte[], int, int)}
	 */
	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {
		outputStream.write(bytes, offset, length);

	}

	/**
	 * see {@link StreamWriter#flush()}
	 */
	@Override
	public void flush() throws IOException {
		outputStream.flush();

	}

	/**
	 * see {@link StreamWriter#close()}
	 */
	@Override
	public void close() throws IOException {
		outputStream.close();

	}

	/**
	 * see {@link StreamWriter#clear()}
	 */
	@Override
	public void clear() throws IOException {
		outputStream.close();
		file.delete();
		outputStream = new FileOutputStream(file);

	}

}
