package de.verpeil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class FileDownloaderTest {

	@Test
	public void testDownloadFile() {
		final String url = "http://geekandpoke.typepad.com/geekandpoke/atom.xml";
		final File result = new FileDownloader().download(url, "atom.xml");
		assertNotNull(result);
		assertTrue(result.exists());
		assertTrue(result.isFile());
		assertFalse(result.isHidden());
		assertTrue(result.getName().endsWith(".xml"));
		assertEquals("atom.xml", result.getName());
	}
}
