/**********************************
 * ConfigurationTest.java
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
 * Test-case for Configuration.java.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests <code>{@link Configuration}</code>. 
 */
public class ConfigurationTest {

	@Test
	public void testConfiguration() {
		assertEquals(8, Configuration.getPropertiesCount());
		assertEquals("http://geekandpoke.typepad.com/geekandpoke/atom.xml",	Configuration.getDownloadUrl());
		assertEquals("all.pdf", Configuration.getAllFile());
		assertEquals("current.jpg", Configuration.getLastImage());
		assertEquals("last.pdf", Configuration.getLastFile());
		assertEquals("atom.xml", Configuration.getTempXmlName());
		assertEquals(ConversionTypes.PDFBOX, Configuration.getConversionType());
		assertTrue(Configuration.isMergeAllowed());
		assertFalse(Configuration.isSilentPrintAllowed());
	}
}