package de.verpeil;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ImageScalerTest {
	@Test
	public void testScaleAndOverrideWithSize() throws IOException {
		final ImageScaler scaler = new ImageScaler();
		assertNotNull(scaler);
		
		final File img = new File("./current.jpg");
		assertNotNull(img);
		assertTrue(img.exists());
		assertTrue(img.isFile());
		
		final int width = 800;
		final int height = 600;
		scaler.resizeAndOverride(img, width, height);
		
		assertNotNull(img);
		assertTrue(img.exists());
		assertTrue(img.isFile());
	}
	
	@Test
	public void testScaleAndOverrideWithSclaeFactor() throws IOException {
		final ImageScaler scaler = new ImageScaler();
		assertNotNull(scaler);
		
		final File img = new File("./current.jpg");
		assertNotNull(img);
		assertTrue(img.exists());
		assertTrue(img.isFile());
		
		final float scaleFactor = 0.25F;
		scaler.resizeAndOverride(img, scaleFactor);
		
		assertNotNull(img);
		assertTrue(img.exists());
		assertTrue(img.isFile());
	}
}
