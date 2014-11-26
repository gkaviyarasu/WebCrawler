package com.webcrawler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Test;

public class WebDowloaderTest {

	@Test
	public void testStartDownload() throws IOException, InterruptedException {
		WebDownloader downloader = new WebDownloader(Constants.BASE_URL,
				"201411.mbox", "TestBox" + File.separator + "2014");
		new File("TestBox/2014").mkdirs();
		downloader.start();
		assertEquals(new File("TestBox" + File.separator + "2014"
				+ File.separator + "201411.mbox").exists(), true);
	}

	@Test(expected = FileNotFoundException.class)
	public void testStartDownloadWithInvalidFileName() throws IOException,
			InterruptedException {
		WebDownloader downloader = new WebDownloader(Constants.BASE_URL,
				"2014111.mbox", "TestBox" + File.separator + "2014");
		new File("TestBox/2014").mkdirs();
		downloader.start();
	}

	@Test(expected = IOException.class)
	public void testStartDownloadWithInvalidHostName() throws IOException,
			InterruptedException {
		WebDownloader downloader = new WebDownloader("http://0.1.2.3/",
				"2014111.mbox", "TestBox" + File.separator + "2014");
		new File("TestBox/2014").mkdirs();
		downloader.start();
	}

	@AfterClass
	public static void tearDown() {
		deleteFile(new File("TestBox" + File.separator + "2014"
				+ File.separator + "201411.mbox"));
		deleteFile(new File("TestBox" + File.separator + "2014"));
		deleteFile(new File("TestBox"));
	}

	private static void deleteFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}
}
