package com.webcrawler;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcrawler.helper.DirectoryHelper;
import com.webcrawler.helper.ObjectSerializer;
import com.webcrawler.web.URIProvider;
import com.webcrawler.web.URIProviderFactory;
import com.webcrawler.web.WebDownloader;
import com.webcrawler.writer.FileStreamWriter;

/**
 * Multiple File Downloader.
 * 
 * This class downloads multiple files from a single Internet source and saves
 * them in FileSystem.
 * 
 * It uses WebDownloader class to download each file.
 * 
 */
public class MultiFileDownloader implements Serializable {

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initialization of Logger statement using SLF4J
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(MultiFileDownloader.class);

	/**
	 * Value for output directory. The default/sample path is MBOXArchive/<year>
	 */
	private String outputDir;

	/**
	 * Holds the ArchiveManager object which provides the list of archives that
	 * need to be dowloaded.
	 */
	private URIProvider uriProvider;

	/**
	 * The list of files' name that need to be downloaded.
	 */
	private List<String> files;

	/**
	 * The index to locate in the file name list where the download is being progressed.
	 */
	private int index = 0;

	/**
	 * Constructor, used to initialize the class with the output directory and
	 * the URIProvider.
	 * 
	 * @param URIProvider
	 *            - The ArchiveManager specific to the Site which provides the
	 *            list of archives to be downloaded.
	 * @param outputDir
	 *            - Output directory in which the downloaded files need to be
	 *            stored.
	 */
	public MultiFileDownloader(URIProvider uriProvider, String outputDir) {
		this.uriProvider = uriProvider;
		this.outputDir = outputDir;

		logger.debug("Initialized the MBOXDownloader");
		logger.debug("Initialized the output directory to {}", this.outputDir);
	}

	/**
	 * Reads the archives from the ArchiveManageer and starts the Web Downloader
	 * for each archive. It also initializes the directory to which the content
	 * needs to be downloaded.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void startDownload() throws IOException, InterruptedException {

		logger.debug("Initializing download");
		DirectoryHelper.createDirectory(outputDir);
		if (files == null) {
			files = uriProvider.getAvailableFiles();
		}
		for (; index < files.size(); index++) {
			File outputFile = new File(outputDir + File.separator
					+ files.get(index));
			if (DirectoryHelper.isFileAlreadyExist(outputFile)) {
				logger.info("Output file already present at {}. Deleting it.",
						outputFile.getPath());
				outputFile.delete();
			}
			logger.debug("Procesing Archive {}", files.get(index));
			WebDownloader downloader = new WebDownloader(
					uriProvider.getWebURL(files.get(index)),
					new FileStreamWriter(outputFile));
			downloader.process();
		}
	}

	/**
	 * The main function which will be called while executing the jar file. This
	 * main function will store/persist the MultiFileDownloader object incase of exception
	 * and to resume the download again.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException, ClassNotFoundException {

		if (args.length > 2) {
			logger.error("Invalid number of arguments provided");
			logger.info("The required arguments are,\n [year] - (optional) "
					+ "Year for which the output file needs to be downloaded. "
					+ "The default value is current calander year.\n"
					+ "[outputDir] - (optional) The base output directory where "
					+ "the files need to be downloaded.A subfolder with the year "
					+ "will be created and the files will be stored inside that By "
					+ "default it creates a directory MBoxArchive\\<year> under "
					+ "the working directory");
			throw new IOException("Invalid number of arguments");
		}

		int year = args.length > 0 ? Integer.parseInt(args[0]) : Calendar
				.getInstance().get(Calendar.YEAR);
		String outputDir = args.length == 2 ? args[1]
				: Constants.DEFAULT_BASE_DIR;
		outputDir += File.separator + year;
		File file = new File(outputDir + File.separator
				+ Constants.SERIALIZABLE_FILE);

		MultiFileDownloader downloader;
		if (DirectoryHelper.isFileAlreadyExist(file)) {
			logger.info("It seems, the data has been downloaded partially. "
					+ "This program is going to resusme the download process");
			downloader = (MultiFileDownloader) ObjectSerializer
					.readObject(file);
			logger.info("Resuming the download process");
		} else {
			downloader = new MultiFileDownloader(
					URIProviderFactory.getInstance(Constants.BASE_URL, year),
					outputDir);
		}
		try {
			downloader.startDownload();
		} catch (IOException e) {
			ObjectSerializer.writeObject(downloader, file);
			throw e;
		}
	}
}
