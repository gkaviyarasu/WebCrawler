package com.webcrawler.helper;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class MavenArchiveHelperTest {

	@Test
	public void checkArchiveListFor2013() throws IOException {
		List<String> archiveList = getArchiveList(2013);
		assertEquals(archiveList.size(), 12);
		assertEquals(archiveList.toString(),
				"[201312.mbox, 201311.mbox, 201310.mbox, "
						+ "201309.mbox, 201308.mbox, 201307.mbox, "
						+ "201306.mbox, 201305.mbox, 201304.mbox, "
						+ "201303.mbox, 201302.mbox, 201301.mbox]");
	}

	@Test
	public void checkArchiveListFor2002() throws IOException {
		List<String> archiveList = getArchiveList(2002);
		assertEquals(archiveList.size(), 2);
		assertEquals(archiveList.toString(), "[200212.mbox, 200211.mbox]");
	}

	private List<String> getArchiveList(int year) throws IOException {
		return MavenArchiveHelper.getAvailableArchives(year);
	}

}
