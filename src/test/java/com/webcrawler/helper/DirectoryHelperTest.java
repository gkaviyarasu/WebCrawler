package com.webcrawler.helper;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DirectoryHelperTest {

	private static final String TEST_DIRECTORY1 = "TestBox" + File.separator
			+ File.separator + "new";
	private static final String TEST_DIRECTORY2 = "TestBox" + File.separator
			+ File.separator + "old";
	private static final String TEST_FILEPATH = TEST_DIRECTORY2
			+ File.separator + "TestFile.txt";

	@BeforeClass
	public static void setUp() throws IOException {
		File file = new File(TEST_DIRECTORY2);
		file.mkdirs();
		file = new File(TEST_FILEPATH);
		OutputStream tempFile = new FileOutputStream(file);
		tempFile.write('a');
		tempFile.close();
	}

	@Test
	public void testCreateDirectory() throws IOException {
		DirectoryHelper.createDirectory(TEST_DIRECTORY1);
		File file = new File(TEST_DIRECTORY1);
		assertEquals((file.exists() && file.isDirectory()), true);
	}

	@Test
	public void testCreateDirectoryWithExistingDir() throws IOException {
		DirectoryHelper.createDirectory(TEST_DIRECTORY2);
		File file = new File(TEST_DIRECTORY2);
		assertEquals((file.exists() && file.isDirectory()), true);
	}

	@Test(expected = IOException.class)
	public void testCreateDirectoryWithExistingTxtFile() throws IOException {
		DirectoryHelper.createDirectory(TEST_FILEPATH);
	}

	@AfterClass
	public static void tearDown() throws Throwable {
		deleteFile(new File(TEST_DIRECTORY1));
		deleteFile(new File(TEST_FILEPATH));
		deleteFile(new File(TEST_DIRECTORY2));
		deleteFile(new File("TestBox"));
	}

	private static void deleteFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}
}
