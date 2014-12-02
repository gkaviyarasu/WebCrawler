package com.webcrawler.web;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.webcrawler.helper.DirectoryHelper;
import com.webcrawler.writer.FileStreamWriter;

public class WebDowloaderTest {

	private static final String sourceURL;
	private static final String TEST_DIR = "TestBox";
	static {
		sourceURL = new File("src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "maven" + File.separator
				+ "2014" + File.separator + "201405.mbox").toURI().toString();
	};

	@BeforeClass
	public static void setUp() throws IOException {
		DirectoryHelper.createDirectory(TEST_DIR);
	}

	@Test
	public void testStartDownload() throws IOException, InterruptedException {
		new WebDownloader(sourceURL, new FileStreamWriter(new File(TEST_DIR
				+ File.separator + "201405.mbox"))).process();
		assertTrue(new File(TEST_DIR + File.separator + "201405.mbox").exists());
	}
	
	@Test(expected = IOException.class)
	public void testStartDownloadExceptionInvalidURL() throws IOException, InterruptedException {
		new WebDownloader("abc:///invalid/url/", new FileStreamWriter(new File(TEST_DIR
				+ File.separator + "201405.mbox"))).process();
	}
	
	@Test(expected = IOException.class)
	public void testStartDownloadExceptionInvalidStream() throws IOException, InterruptedException {
		new WebDownloader("abc:///invalid/url/", new FileStreamWriter(new File(""))).process();
	}

	@AfterClass
	public static void tearDown() {
		DirectoryHelper.deleteDirectory(new File(TEST_DIR));
	}
}
