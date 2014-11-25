package com.webcrawler;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.webcrawler.helper.DirectoryHelper;

/**
 * MBOX Downloader
 */
public class MBoxDownloader {

	private static final String BASE_URL = "http://mail-archives.apache.org/mod_mbox/maven-users/";
	private static final String BASE_DIR = "MBoxArchive";
	private final String outputDir;

	private int year;

	public MBoxDownloader() {
		this(Calendar.getInstance().get(Calendar.YEAR));
	}

	public MBoxDownloader(int year) {
		this.year = year;
		this.outputDir = BASE_DIR + File.pathSeparator + year;
	}

	public void startDownload() throws Exception {
		DirectoryHelper.createDirectory(outputDir);
		for (int month = 1; month <= 12; month++) {
			String fileName = this.year + String.format("%02d", month)
					+ ".mbox";
			URLDownloader downloader = new URLDownloader(BASE_URL, fileName,
					outputDir);
			downloader.start();
		}

	}

	public static void main(String[] args) throws Exception {
		new MBoxDownloader().startDownload();
	}
}
