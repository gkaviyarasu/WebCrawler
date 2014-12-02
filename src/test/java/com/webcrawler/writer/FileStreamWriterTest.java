package com.webcrawler.writer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.webcrawler.helper.DirectoryHelper;

public class FileStreamWriterTest {

	private static final String TEST_DIR_NAME = "TestBox";
	private static final byte[] bytes = "Sample Text".getBytes();

	@BeforeClass
	public static void setUp() throws IOException {
		DirectoryHelper.createDirectory(TEST_DIR_NAME);
	}

	@Test
	public void testWriteContent() throws IOException {
		File file = new File(TEST_DIR_NAME + File.separator + "testFile1.txt");
		StreamWriter writer = new FileStreamWriter(file);
		writer.write(bytes, 0, bytes.length);
		writer.flush();
		writer.close();

		assertEquals(getFileContent(file), new String(bytes));
	}

	@Test
	public void testClearContent() throws IOException {
		File file = new File(TEST_DIR_NAME + File.separator + "testFile2.txt");
		StreamWriter writer = new FileStreamWriter(file);
		writer.write(bytes, 0, bytes.length);
		writer.flush();
		writer.clear();
		writer.close();

		assertEquals(getFileContent(file), new String(""));
	}

	private static String getFileContent(File file) throws IOException {
		InputStream in = new FileInputStream(file);
		StringBuilder out = new StringBuilder();
		byte[] b = new byte[1024];
		int length = -1;
		while ((length = in.read(b)) != -1) {
			out.append(new String(b, 0, length));
		}
		in.close();
		return out.toString();
	}

	@AfterClass
	public static void tearDown() {
		DirectoryHelper.deleteDirectory(new File(TEST_DIR_NAME));
	}

}
