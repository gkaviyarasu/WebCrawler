package com.webcrawler;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcrawler.helper.DirectoryHelper;
import com.webcrawler.helper.MavenArchiveHelper;

/**
 * MBOX Downloader.
 * 
 * This is the main class which will be invoked when executing as the jar. This
 * class finds the archive that are need to be downloaded and sets the download
 * path, and starts the download one by one.
 * 
 */
public class MBoxDownloader {

	/**
	 * Initialization of Logger statement using SLF4J
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(MBoxDownloader.class);

	/**
	 * Value for output directory. The default/sample path is MBOXArchive/<year>
	 */
	private String outputDir;

	/**
	 * Year for which the Archive needs to be downloaded.
	 */
	private int year;

	/**
	 * This constructor will be invoked when the Year is not passed as argument
	 * to the main. It by default sets the current year to fetch the archive.
	 */
	public MBoxDownloader() {
		this(Calendar.getInstance().get(Calendar.YEAR));
		logger.info(
				"The year for archive is not provided. Assuming the current year {}",
				Calendar.getInstance().get(Calendar.YEAR));

	}

	/**
	 * Incase if the archives for a specific year needs to be downloaded, then
	 * this constructor will be initialized with the first argument provided.
	 * 
	 * @param year
	 *            The year for which the Archive needs to be downloaded.
	 */
	public MBoxDownloader(int year) {
		this.year = year;
		this.outputDir = Constants.BASE_DIR + File.separator + year;

		if (logger.isDebugEnabled()) {
			logger.debug("Initialized the MBOXDownloader for year " + this.year);
			logger.debug("Initialized the output directory to "
					+ this.outputDir);
		}
	}

	/**
	 * Reads the archives for the specified year and starts the Web Downloader
	 * for each archive. It also initializes the directory to which the content
	 * needs to be downloaded.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void startDownload() throws IOException, InterruptedException {

		logger.debug("Initializing download");
		DirectoryHelper.createDirectory(outputDir);
		List<String> archives = MavenArchiveHelper.getAvailableArchives(year);
		for (String archive : archives) {
			if (logger.isDebugEnabled()) {
				logger.debug("Procesing Archive " + archive);
			}
			WebDownloader downloader = new WebDownloader(Constants.BASE_URL,
					archive, outputDir);
			downloader.start();
		}
	}

	/**
	 * The main function which will be called while executing the jar file.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		if (args.length > 0) {
			new MBoxDownloader(Integer.parseInt(args[0])).startDownload();
		} else {
			new MBoxDownloader().startDownload();
		}
	}
}
