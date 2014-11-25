package com.webcrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class URLDownloader {

	private static final int MAX_RETRY = 10;
	private static final int RETRY_GAP = 3000;
	private static final int BUFFER_SIZE = 4096;

	private String baseURL;
	private String fileName;
	private String outputFilePath;

	public URLDownloader(String sourceURL, String fileName, String outputDir) {
		this.baseURL = sourceURL + "/" + fileName;
		this.fileName = fileName;
		this.outputFilePath = outputDir + File.separator + fileName;
	}

	public void start() throws Exception {
		File outputFile = new File(outputFilePath);
		if (outputFile.exists()) {
			if (!outputFile.isFile()) {
				throw new IOException("Unknown target");
			}
		}
		int currentTry = 0;
		while (currentTry < MAX_RETRY) {
			if (downloadToFile(outputFile)) {
				break;
			} else {
				outputFile.delete();
				Thread.sleep(RETRY_GAP);
			}
		}
	}

	private boolean downloadToFile(File outputFile) {
		try {
			URL url = new URL(baseURL);
			URLConnection con = url.openConnection();

			InputStream input = con.getInputStream();
			FileOutputStream output = new FileOutputStream(outputFile);

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}

			output.close();
			input.close();
		} catch (FileNotFoundException e) {
			return true;
		} catch (UnknownHostException e) {
			return false;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}
