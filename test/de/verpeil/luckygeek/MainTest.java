/**********************************
 * MainTest.java
 * Part of the project "luckyGeek" from
 * ctvoigt (Christian Voigt), chripo2701  2011.
 *
 * 
 * Email: luckygeek@verpeil.de
 * 
 *
 * 
 **********************************
 * 
 * Test-case for Main.java.
 **********************************
 * 
 * This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your
 * option) any later version.
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA 02111-1307, USA.
 */
package de.verpeil.luckygeek;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.verpeil.luckygeek.Configuration;
import de.verpeil.luckygeek.Main;

/**
 * Tests <code>{@link Main}</code>.
 */
public class MainTest {
	private static File allPdf = null;
	
	@BeforeClass
	public static void setUp() {
		assertTrue(UnittestUtil.setUp());
		allPdf = UnittestUtil.createAllPdf();
	}
	
	@AfterClass
	public static void tearDown() {
		FileUtils.deleteQuietly(allPdf);
		UnittestUtil.tearDown();
	}

	@Test
	public void testMain() {
		final Main main = new Main();
		assertNotNull(main);
		main.extractImageUrl();
		
		assertFile(allPdf);
		
		final File currentImage = new File(UnittestUtil.getTestResource(Configuration.getLastImageName()).getFile());
		assertFile(currentImage);
		
		assertTrue(Configuration.getConversionType().createConverter().convert(currentImage));
		final File lastPdf = Configuration.getLastFile();
		assertFile(lastPdf);
	}
	
	private void assertFile(final File file) {
		assertNotNull(file);
		assertTrue(file.exists());
		assertTrue(file.isFile());
		assertFalse(file.isHidden());
	}
}
