package com.webcrawler.writer;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This is a wrapper interface for OutputStream where it provides an additional
 * method called clear which clears the stream
 */
public interface StreamWriter extends Closeable {

	/**
	 * Writes the provided data to the OutputStream
	 * 
	 * @param bytes
	 *            the data.
	 * @param offset
	 *            the start offset in the data.
	 * @param length
	 *            the number of bytes to write.
	 * @throws IOException
	 *             IOException if an I/O error occurs.
	 * @see OutputStream#write(byte[], int, int)
	 */
	public void write(byte bytes[], int offset, int length) throws IOException;

	/**
	 * Flushes the OutputStream, so that it will persist all the changes that
	 * are buffered.
	 * 
	 * @throws IOException
	 *             IOException if an I/O error occurs.
	 * @see OutputStream#flush()
	 */
	public void flush() throws IOException;

	/**
	 * Closes the OutputStream
	 * 
	 * @throws IOException
	 *             IOException if an I/O error occurs.
	 * @see OutputStream#close()
	 */
	public void close() throws IOException;

	/**
	 * Clears the OutputStream. i.e., It erases all the content that were
	 * written earlier.
	 * 
	 * @throws IOException
	 *             IOException if an I/O error occurs.
	 */
	public void clear() throws IOException;
}
