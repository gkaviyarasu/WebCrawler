package com.webcrawler.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcrawler.Constants;
import com.webcrawler.writer.StreamWriter;

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
	 * StreamWriter where the downloaded content needs to be stored.
	 */
	private StreamWriter output;

	/**
	 * Accepts the base URL and the StreamWriter to which the downloaded content
	 * needs to be written.
	 * 
	 * @param sourceURL
	 *            Source URL from where the file needs to be downloaded.
	 * @param streamWriter
	 *            StreamWriter where the downloaded content needs to be written.
	 */

	public WebDownloader(String sourceURL, StreamWriter streamWriter) {

		this.sourceURL = sourceURL;
		this.output = streamWriter;
		logger.debug("Initialized the WebDownloader for {}", sourceURL);
	}

	/**
	 * Processes the download operation. In case if the download fails due to
	 * network issue, processing will keep trying for
	 * <code>Constants.MAX_RETRY</code> times
	 * 
	 * @param sourceURL
	 *            Source URL from where the archive needs to be downloaded
	 * @param outputFile
	 *            Output File where the file needs to be stored.
	 * @throws IOException
	 *             Throws this exception when the download fails due to IO issue
	 *             or network is continuously down for long time
	 * @throws InterruptedException
	 *             Throws this exception when the sleep process is interrupted.
	 *             The programs sleeps for <code>Constants.RETRY_GAP</code>
	 *             milliseconds between each retry.
	 */
	public void process() throws IOException, InterruptedException {

		int retryCount = 1;
		for (; retryCount <= Constants.MAX_RETRY; retryCount++) {
			logger.info("{}/{} try to download the archive {}", retryCount,
					Constants.MAX_RETRY, sourceURL);
			boolean isDownloaded = false;
			try {
				download();
				isDownloaded = true;
				break;
			} catch (UnknownHostException | SocketTimeoutException
					| SocketException e) {
				/*
				 * Ignoring these exceptions. The cause here could be network
				 * failure. So, if this error occurs, the program should not
				 * abort, instead it should retry the download
				 */
				logger.error("Error occured while downloading content {}",
						e.getMessage());
			} finally {
				if (!isDownloaded) {
					/*
					 * Clearing the downloaded content since, the download is
					 * not successful.
					 */
					output.clear();
				}
			}
			// delay before next try.
			Thread.sleep(Constants.RETRY_GAP);
		}
		if (retryCount > Constants.MAX_RETRY) {
			logger.error("Couldn't download the content from {}", sourceURL);
			throw new IOException(
					"Couldn't Download the content. "
							+ "Please check the connection or check whether the URL is correct");
		}
	}

	/**
	 * Downloads the content from sourceURL and saves it in the StreamWriter
	 * that is specified
	 * 
	 * @throws UnknownHostException
	 * @throws SocketTimeoutException
	 * @throws SocketException
	 *             The above three exceptions could be thrown when there is a
	 *             network failure.
	 * @throws IOException
	 *             This error could be thrown when there is an issue in writing
	 *             the content or some other issue which is caused by network
	 */
	private void download() throws UnknownHostException, SocketTimeoutException,
			SocketException, IOException {

		try {
			URL url = new URL(sourceURL);
			URLConnection con = url.openConnection();
			con.setReadTimeout(Constants.READ_TIMEOUT);
			InputStream input = con.getInputStream();

			logger.info("Starting download.");

			byte[] buffer = new byte[Constants.BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
			output.flush();
			input.close();
			logger.info("Completed download.");

		} catch (IOException e) {
			logger.error("Error occured while downloading content {}",
					e.getMessage());
			throw e;
		}
	}

}
