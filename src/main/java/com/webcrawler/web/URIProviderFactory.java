package com.webcrawler.web;

/**
 * ArchiveFactory is a factory class, which initializes the ArchiveManager based
 * on the URL.
 * 
 */
public class URIProviderFactory {

	/**
	 * Initializes the URIProvider for the specified year, based on the URL.
	 * 
	 * @param baseURL
	 *            Base URL for which an ArchiveManager needs to be created.
	 * @param year
	 *            The year for which it has to be initialized.
	 * @return an Object/instance of ArchiveManager
	 * @throws IllegalStateException
	 *             When it couldn't find an appropriate implementation for the
	 *             specified Web URL.
	 * 
	 */
	public static URIProvider getInstance(String baseURL, int year) {

		if (baseURL.startsWith("http://mail-archives.apache.org/")
				|| baseURL.startsWith("https://mail-archives.apache.org/")) {
			return new MavenURIProvider(baseURL, year);
		} else {
			throw new IllegalStateException(
					"Couldn't find the appropriate ArchiveManager.");
		}
	}
}
