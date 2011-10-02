package de.verpeil;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;

import org.junit.Test;

public class MainTest {

	@Test
	public void testMain() {
		final Main main = new Main();
		assertNotNull(main);
		main.extractImageUrl();
		main.storeToFile();
		
		final File allPdf = new File(Configuration.getAllFile());
		assertFile(allPdf);
		
		final File currentImage = new File(Configuration.getLastImage());
		assertFile(currentImage);
		
		final File lastPdf = new File(Configuration.getLastFile());
		assertFile(lastPdf);
	}
	
	private void assertFile(final File file) {
		assertNotNull(file);
		assertTrue(file.exists());
		assertTrue(file.isFile());
		assertFalse(file.isHidden());
	}
}
