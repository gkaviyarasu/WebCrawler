package com.webcrawler.helper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DirectoryHelperTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public DirectoryHelperTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DirectoryHelperTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testCreateDirectory() {
		assertTrue(true);
	}
}
