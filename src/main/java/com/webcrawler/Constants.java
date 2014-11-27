package com.webcrawler;

public class Constants {
	/**
	 * Buffer size to read from Input Stream
	 */
	public static final int BUFFER_SIZE = 4096;
	
	/**
	 * Apache Mven email archvie base URL
	 */
	public static final String BASE_URL = "http://mail-archives.apache.org/mod_mbox/maven-users/";
	
	/**
	 * Path to store the email archive files that are downloaded
	 */
	public static final String BASE_DIR = "MBoxArchive";
	
	/**
	 * Maximum retry count, when the download get aborts due to network issues.
	 */
	public static final int MAX_RETRY = 10;
	
	/**
	 * Gap between each retry
	 */
	public static final int RETRY_GAP = 3000;
	
	/**
	 * Read timeout value for InputStream.
	 */
	public static final int READ_TIMEOUT = 10000;
}
