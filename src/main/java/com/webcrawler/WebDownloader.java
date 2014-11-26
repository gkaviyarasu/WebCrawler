package com.webcrawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web Downloader It downloads a file/content from web with the given base link
 * and saves it to the output directory. Incase if the network fails, it will
 * retry with the intervel specified in the constants
 * <code>Constants.RETRY_GAP</code>.
 */
public class WebDownloader {

	/**
	 * Initialization of Logger statement using SLF4J
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(WebDownloader.class);

	/**
	 * Web URL of the file to download
	 */
	private String sourceURL;

	/**
	 * Output location/file where the downloaded content needs to be stored.
	 */
	private String outputFilePath;

	/**
	 * Accepts the base URL , file name to be downloaded from the URL and the
	 * output directroy to store it
	 * 
	 * @param baseURL
	 *            Base URL for the files
	 * @param fileName
	 *            file name
	 * @param outputDir
	 *            Directory in which the file needs to be downloaded
	 */
	public WebDownloader(String baseURL, String fileName, String outputDir) {
		this.sourceURL = baseURL + "/" + fileName;
		this.outputFilePath = outputDir + File.separator + fileName;

		if (logger.isDebugEnabled()) {
			logger.debug("Initialized the WebDownloader for " + fileName);
		}
	}

	/**
	 * Checks if the file already exist in the target system. If it does, it
	 * assumes that the file is already downloaded, else starts the download
	 * process
	 * 
	 * @throws IOException
	 *             Throws when the content are not able to download or not able
	 *             to write to the output file
	 * @throws InterruptedException
	 *             Throws this exception when there is an interruption while the
	 *             main tread sleeps.
	 */
	public void start() throws IOException, InterruptedException {
		File outputFile = new File(outputFilePath);
		if (outputFile.exists()) {
			if (!outputFile.isFile()) {
				logger.error("The destination is not a file " + outputFilePath);
				throw new IOException("Unknown target");
			}

			if (logger.isInfoEnabled()) {
				logger.info("File already present, skiping download "
						+ outputFilePath);
			}
			return;
		}
		int retryCount;
		for (retryCount = 1; retryCount <= Constants.MAX_RETRY; retryCount++) {
			if (logger.isInfoEnabled()) {
				logger.info(retryCount + " try to download the archive "
						+ sourceURL);
			}
			boolean isDownloaded = false;
			try {
				downloadToFile(outputFile);
				isDownloaded = true;
				break;
			} catch (UnknownHostException | SocketTimeoutException
					| SocketException e) {
				/*
				 * Ignoring this exception. The cause here could be network
				 * failure. So, if this error occurs, the program should not
				 * abort, instead it should retry the download
				 */

			} finally {
				if (!isDownloaded) {
					// Deleting the output file if it is not fully downloaded.
					outputFile.delete();
				}
			}
			// delay before next try.
			Thread.sleep(Constants.RETRY_GAP);
		}
		if (retryCount > Constants.MAX_RETRY) {
			if (logger.isErrorEnabled()) {
				logger.error("Couldn't download the content from " + sourceURL);
			}
			throw new IOException(
					"Couldn't Download the content. "
							+ "Please check the connection or check whether the URL is correct");
		}
	}

	/**
	 * Downloads the content from sourceURL and saves it to local file
	 * 
	 * @param outputFile
	 *            The File object of output file
	 * @throws UnknownHostException
	 * @throws SocketTimeoutException
	 * @throws SocketException
	 *             The above three errors could be thrown when there is a
	 *             network failure.
	 * @throws IOException
	 *             This error could be thrown when there is an issue in writing
	 *             the content or some other issue which is caused by network
	 */
	private void downloadToFile(File outputFile) throws UnknownHostException,
			SocketTimeoutException, SocketException, IOException {

		FileOutputStream output = null;
		try {
			URL url = new URL(sourceURL);
			URLConnection con = url.openConnection();
			con.setReadTimeout(Constants.READ_TIMEOUT);
			InputStream input = con.getInputStream();
			output = new FileOutputStream(outputFile);
			if (logger.isInfoEnabled()) {
				logger.info("Starting download " + outputFilePath);
			}
			byte[] buffer = new byte[Constants.BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}

			input.close();
			if (logger.isInfoEnabled()) {
				logger.info("Completed download " + outputFilePath);
			}
		} catch (UnknownHostException | SocketTimeoutException
				| SocketException e) {
			if (logger.isErrorEnabled()) {
				logger.error("Error occured while downloading content "
						+ e.getMessage());
			}
			throw e;
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error("Error occured while downloading content "
						+ e.getMessage());
			}
			throw e;
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}

}
