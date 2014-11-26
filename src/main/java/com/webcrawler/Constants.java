package com.webcrawler;

public class Constants {
	public static final int BUFFER_SIZE = 4096;
	public static final String BASE_URL = "http://mail-archives.apache.org/mod_mbox/maven-users/";
	public static final String BASE_DIR = "MBoxArchive";
	public static final int MAX_RETRY = 10;
	public static final int RETRY_GAP = 3000;
	public static final int READ_TIMEOUT = 10000;
}
