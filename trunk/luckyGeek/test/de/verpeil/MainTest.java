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
package de.verpeil;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;

import org.junit.Test;

/**
 * Tests <code>{@link Main}</code>.
 */
public class MainTest {

	@Test
	public void testMain() {
		final Main main = new Main();
		assertNotNull(main);
		main.extractImageUrl();
		main.storeToFile();
		
		final File allPdf = new File(Configuration.getAllFileName());
		assertFile(allPdf);
		
		final File currentImage = new File(Configuration.getLastImageName());
		assertFile(currentImage);
		
		final File lastPdf = new File(Configuration.getLastFileName());
		assertFile(lastPdf);
	}
	
	private void assertFile(final File file) {
		assertNotNull(file);
		assertTrue(file.exists());
		assertTrue(file.isFile());
		assertFalse(file.isHidden());
	}
}
