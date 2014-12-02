package com.webcrawler.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.webcrawler.Constants;
import com.webcrawler.helper.WebHelper;

/**
 * It is an implementation on URIProvider interface for Maven mail archive site.
 * 
 * @see {@link URIProvider}
 * 
 */
public class MavenURIProvider implements URIProvider, Serializable {

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * To store the Maven mail archive base URL
	 */
	private String baseURL;

	/**
	 * To store the Year for which the URIProvider should store the
	 * properties
	 */
	private int year;

	/**
	 * List of archive files' URN
	 */
	/*
	 * Kept as transient, because, wanted it to generate new list in case of
	 * resume (if it all the getAvailableArchives() is invoked)
	 */
	private transient List<String> archives;

	/**
	 * Constructor to initialize MavenURIProvider. It sets the base URL and
	 * the Year.
	 * 
	 * @param baseURL
	 *            - Maven mail archive Base URL
	 * @param year
	 *            - Year for which it should store the properties.
	 */
	public MavenURIProvider(String baseURL, int year) {
		this.baseURL = baseURL;
		this.year = year;
	}

	/**
	 * @see {@link URIProvider#getAvailableFiles()}
	 */
	@Override
	public List<String> getAvailableFiles() throws IOException {
		if (archives != null) {
			return archives;
		}
		archives = new ArrayList<String>();
		String content = WebHelper.getHTMLContent(baseURL);
		/*
		 * The pattern to match the archive names specified in the links on the
		 * page content
		 */
		Pattern pattern = Pattern.compile(year + Constants.ARCHIVE_LIST_REGEX);

		Matcher m = pattern.matcher(content);
		while (m.find()) {
			archives.add(year + m.group(1) + ".mbox");
		}

		return archives;
	}

	/**
	 * @see {@link URIProvider#getWebURL()}
	 */
	@Override
	public String getWebURL(String fileName) {
		return this.baseURL + "/" + fileName;
	}

}
