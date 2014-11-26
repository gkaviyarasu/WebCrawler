package com.webcrawler.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.webcrawler.Constants;

/**
 * Maven Archive Helper
 * 
 * Helps to read the archive file details
 */
public class MavenArchiveHelper {

	/**
	 * Reads all the possible archive file names from the Apache Maven email
	 * archive site for the given year and returns it in a List.
	 * 
	 * @param year
	 *            Year for which the archive file list needs to be returned
	 * @return List containing the archive file names.
	 * @throws IOException
	 *             Throws this exception when it couldn't read the HTML content
	 *             from Base Maven URL
	 */
	public static List<String> getAvailableArchives(int year)
			throws IOException {

		List<String> archives = new ArrayList<String>();
		String content = getHTMLContent(Constants.BASE_URL);
		// The pattern to match the archive names specified in the links of page
		// content
		Pattern pattern = Pattern.compile(year + "(..)\\.mbox/thread");

		Matcher m = pattern.matcher(content);
		while (m.find()) {
			archives.add(year + m.group(1) + ".mbox");
		}

		return archives;
	}

	/**
	 * Reads the HTML content from the given URL and returns it as string.
	 * 
	 * @param urlString
	 *            URL String
	 * 
	 * @return HTML content of the given URL
	 * @throws IOException
	 *             Throws this exception when it faces issue with the network
	 *             connection
	 */
	private static String getHTMLContent(String urlString) throws IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		InputStream input = connection.getInputStream();
		StringBuilder content = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String str;
		while ((str = reader.readLine()) != null) {
			content.append(str);
		}
		return content.toString();
	}
}
