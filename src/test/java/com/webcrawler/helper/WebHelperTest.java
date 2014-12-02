package com.webcrawler.helper;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WebHelperTest {

	private static final String TEST_FILE = "test.html";
	private static final String CONTENT = "<html><body><h1>sample page</h1></body></html>";
	private static String fileUrl;

	@BeforeClass
	public static void setUp() throws IOException {
		File file = new File(TEST_FILE);
		if (DirectoryHelper.isFileAlreadyExist(file)) {
			file.delete();
		}
		OutputStream out = new FileOutputStream(file);
		out.write(CONTENT.getBytes());
		out.close();
		fileUrl = file.toURI().toString();
	}

	@Test
	public void getHTMLContent() throws IOException {
		String htmlContent = WebHelper.getHTMLContent(fileUrl);
		assertEquals(htmlContent, CONTENT);
	}

	@Test(expected = IOException.class)
	public void getHTMLContentWithInvalidURI() throws IOException {
		WebHelper.getHTMLContent("file:///invalidpath/invalid");
	}

	@AfterClass
	public static void tearDown() {
		File file = new File(TEST_FILE);
		if (file.exists()) {
			file.delete();
		}
	}
}
