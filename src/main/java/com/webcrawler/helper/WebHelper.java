package com.webcrawler.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Maven Archive Helper
 * 
 * Helps to read the archive file details
 */
public class WebHelper {

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
	public static String getHTMLContent(String urlString) throws IOException {
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
