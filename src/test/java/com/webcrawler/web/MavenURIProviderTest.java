package com.webcrawler.web;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class MavenURIProviderTest {

	private static String baseURL;

	@BeforeClass
	public static void setUp() {
		baseURL = new File("src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "maven" + File.separator
				+ "index.htm").toURI().toString();
	}

	@Test
	public void testGetAllFiles() throws IOException {
		URIProvider provider = new MavenURIProvider(baseURL, 2010);
		List<String> fileList = provider.getAvailableFiles();
		assertEquals(
				fileList.toString(),
				"[201002.mbox, 201001.mbox]");
	}

	@Test
	public void testGetAllFilesForNunknownYear() throws IOException {
		URIProvider provider = new MavenURIProvider(baseURL, 2015);
		List<String> fileList = provider.getAvailableFiles();
		assertEquals(fileList.toString(), "[]");
	}
	
	@Test(expected = IOException.class)
	public void testInvalidBaseURL() throws IOException
	{
		URIProvider provider = new MavenURIProvider("abc:///testurl/invalidurl", 2015);
		provider.getAvailableFiles();
	}
	
	@Test
	public void testGetWebURL() throws IOException
	{
		URIProvider provider = new MavenURIProvider(baseURL, 2015);
		List<String> fileList = provider.getAvailableFiles();
		for(String file : fileList)
		{
			assertEquals(provider.getWebURL(file),baseURL+"/"+file);
		}
	}
}
