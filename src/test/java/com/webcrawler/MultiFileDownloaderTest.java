package com.webcrawler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.webcrawler.helper.DirectoryHelper;
import com.webcrawler.web.MavenURIProvider;
import com.webcrawler.web.URIProvider;
import com.webcrawler.web.URIProviderFactory;

public class MultiFileDownloaderTest {

	private static final String baseURL = new File("src" + File.separator
			+ "test" + File.separator + "resources" + File.separator + "maven"
			+ File.separator + "index.htm").toURI().toString();

	private static final String TEST_DIR = "TestBox";

	@BeforeClass
	public static void setUp() throws IOException {
		DirectoryHelper.createDirectory(TEST_DIR);
	}

	@Test
	@Ignore
	public void testMainMultiFileDownloaderWithEmptyParamters()
			throws IOException, InterruptedException, ClassNotFoundException {
		MultiFileDownloader.main(new String[0]);
		assertForYear(Calendar.getInstance().get(Calendar.YEAR));
	}

	@Test
	@Ignore
	public void testMainMultiFileDownloaderForSpecificYear()
			throws ClassNotFoundException, IOException, InterruptedException {
		String[] args = { "2010" };
		MultiFileDownloader.main(args);
		assertForYear(2010);
	}

	private void assertForYear(int year) throws IOException {
		assertForYear(URIProviderFactory.getInstance(Constants.BASE_URL, year),
				year, Constants.DEFAULT_BASE_DIR);
	}

	private void assertForYear(URIProvider uriProvider, int year,
			String outputDir) throws IOException {
		List<String> fileList = uriProvider.getAvailableFiles();
		String baseDir = outputDir + File.separator;
		for (String file : fileList) {
			assertEquals(new File(baseDir + file).exists(), true);
		}
	}

	@Test
	public void testMultiFileDownloader2010() throws IOException,
			InterruptedException {
		URIProvider uriProvider = new ExtendendMavenURIProvider(baseURL, 2010);
		new MultiFileDownloader(uriProvider, TEST_DIR).startDownload();

		assertForYear(uriProvider, 2010, TEST_DIR);
	}

	@Test
	public void testMultiFileDownloader2014() throws IOException,
			InterruptedException {
		URIProvider uriProvider = new ExtendendMavenURIProvider(baseURL, 2014);
		new MultiFileDownloader(uriProvider, TEST_DIR).startDownload();

		assertForYear(uriProvider, 2014, TEST_DIR);
	}

	private class ExtendendMavenURIProvider extends MavenURIProvider {

		private static final long serialVersionUID = 1L;
		int year;
		String baseURL;

		public ExtendendMavenURIProvider(String baseURL, int year) {
			super(baseURL, year);
			this.year = year;
			this.baseURL = baseURL.substring(0, baseURL.lastIndexOf("/"));
		}

		@Override
		public String getWebURL(String file) {
			return baseURL + "/" + year + "/" + file;
		}
	}

	@AfterClass
	public static void tearDown() throws IOException {
		DirectoryHelper.deleteDirectory(new File(TEST_DIR));
	}
}
