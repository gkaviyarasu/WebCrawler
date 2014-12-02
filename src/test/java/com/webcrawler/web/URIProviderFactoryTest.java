package com.webcrawler.web;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class URIProviderFactoryTest {

	@Test
	public void getInstance() {
		assertNotNull(URIProviderFactory.getInstance(
				"http://mail-archives.apache.org/", 2014));
	}

	@Test(expected = IllegalStateException.class)
	public void getInstanceException() {
		assertNotNull(URIProviderFactory.getInstance("http://mail.google./",
				2014));
	}
}