package com.webcrawler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.webcrawler.web.URIProviderFactory;

public class MultiFileDownloaderTest {

	@Test
	public void testMainMultiFileDownloaderWithEmptyParamters()
			throws IOException, InterruptedException, ClassNotFoundException {
		MultiFileDownloader.main(new String[0]);
		assertForYear(Calendar.getInstance().get(Calendar.YEAR));
	}

	public void testMainMultiFileDownloaderForSpecificYear()
			throws ClassNotFoundException, IOException, InterruptedException {
		String[] args = { "2010" };
		MultiFileDownloader.main(args);
		assertForYear(2010);
	}

	private void assertForYear(int year) throws IOException {
		List<String> urnList = URIProviderFactory.getInstance(
				Constants.DEFAULT_BASE_DIR, year).getAvailableFiles();
		String baseDir = Constants.DEFAULT_BASE_DIR + File.separator + year
				+ File.separator;
		for (String urn : urnList) {
			assertEquals(new File(baseDir + urn).exists(), true);
		}
	}

}
