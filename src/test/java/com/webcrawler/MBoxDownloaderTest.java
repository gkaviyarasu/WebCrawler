package com.webcrawler;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.webcrawler.Constants;
import com.webcrawler.MBoxDownloader;
import com.webcrawler.helper.MavenArchiveHelper;

public class MBoxDownloaderTest {

	@Test
	public void testMBoxDownloader() throws IOException, InterruptedException {
		MBoxDownloader.main(new String[0]);
		assertForYear(Calendar.getInstance().get(Calendar.YEAR));
		String[] args ={"2010"};
		MBoxDownloader.main(args);
		assertForYear(2010);
	}

	private void assertForYear(int year) throws IOException {
		List<String> archiveList = MavenArchiveHelper
				.getAvailableArchives(year);
		String baseDir = Constants.BASE_DIR + File.separator + year
				+ File.separator;
		for (String archive : archiveList) {
			assertEquals(new File(baseDir + archive).exists(), true);
		}
	}

}
