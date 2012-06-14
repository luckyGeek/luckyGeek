/**********************************
 * DataProviderTest.java
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
 * Test-case for DataProvider.java.
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

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * Tests <code>{@link DataProvider}</code>. 
 */
public class DataProviderTest {
	private DataProvider provider = null;
	
	@Before
	public void before() {
		provider = null;
		assertNull(provider);
		provider = new DataProvider();
		assertNotNull(provider);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExtractImageUrlRaisesException() {
		assertEquals("", provider.extractImageUrl(null));
	}

	@Test
	public void testExtractImageUrl() {
		assertEquals("", provider.extractImageUrl(new File(".")));
		assertEquals("", provider.extractImageUrl(new File("testres/not-an-xml.txt")));
		assertEquals("", provider.extractImageUrl(new File("testres/empty-xml.xml")));
		assertEquals("", provider.extractImageUrl(new File("testres/invalid.xml")));
		assertEquals("http://geekandpoke.typepad.com/.a/6a00d8341d3df553ef015438d0e316970c-pi", provider.extractImageUrl(new File("testres/valid.xml")));
	}
}
