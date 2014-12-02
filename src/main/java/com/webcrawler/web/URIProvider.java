package com.webcrawler.web;

import java.io.IOException;
import java.util.List;

/**
 * URIProvider used to provide list of files/URIs that are available in the
 * provided URL. It generally reads the list from the base URL.
 * 
 * Each site could have different link structures. So, each site have to
 * implement their own URIProvider class.
 * 
 */
public interface URIProvider {

	/**
	 * Returns List of files that are available.
	 * 
	 * @return List
	 * @throws IOException
	 *             When it could't read the archive list.
	 */
	public List<String> getAvailableFiles() throws IOException;

	/**
	 * Generates/Returns the Web URL for the specified file name.
	 * 
	 * Note: It does not validate whether the URL is actually present or not.
	 * 
	 * @param urn
	 *            - file name
	 * @return String - Web URL / Absolute Web URL
	 */
	public String getWebURL(String fileName);
}
