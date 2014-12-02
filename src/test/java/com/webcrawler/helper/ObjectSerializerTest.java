package com.webcrawler.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ObjectSerializerTest implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String TEST_DIR = "TestBox";

	@BeforeClass
	public static void setUp() throws IOException {
		DirectoryHelper.createDirectory(TEST_DIR);
	}

	@Test
	public void testWriteObject() throws ClassNotFoundException, IOException {
		File file = new File(TEST_DIR + File.separator + ".jobjects1");
		SerializerTest test = new SerializerTest("Abc", "Efg");
		ObjectSerializer.writeObject(test, file);

		SerializerTest test1 = (SerializerTest) ObjectSerializer
				.readObject(file);
		assertEquals(test.sample, test1.sample);
		assertEquals(test.sample2, "Efg");
		assertNull(test1.sample2);
	}

	private class SerializerTest implements Serializable {
		private static final long serialVersionUID = 1L;
		public String sample;
		public transient String sample2;

		SerializerTest(String sample, String sample2) {
			this.sample = sample;
			this.sample2 = sample2;
		}

	}

	@AfterClass
	public static void tearDown() throws IOException {
		DirectoryHelper.deleteDirectory(new File(TEST_DIR));
	}
}
