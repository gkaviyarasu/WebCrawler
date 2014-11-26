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
	 * @return
	 * @throws IOException
	 */
	public static List<String> getAvailableArchives(int year)
			throws IOException {

		List<String> archives = new ArrayList<String>();
		URL url = new URL(Constants.BASE_URL);
		URLConnection connection = url.openConnection();
		String content = getHTMLContent(connection);
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
	 * @param connection
	 * @return
	 * @throws IOException
	 */
	private static String getHTMLContent(URLConnection connection)
			throws IOException {
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
