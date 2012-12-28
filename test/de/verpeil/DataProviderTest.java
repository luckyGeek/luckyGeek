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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests <code>{@link DataProvider}</code>. 
 */
public class DataProviderTest {
	private DataProvider provider = null;
	
	@BeforeClass
	public static void setUp() {
		assertTrue(UnittestUtil.setUp());
	}
	
	@AfterClass
	public static void tearDown() {
		UnittestUtil.tearDown();
	}
	
	@Before
	public void before() {
		provider = null;
		assertNull(provider);
		provider = new DataProvider();
		assertNotNull(provider);
	}
	
	@Test(expected=NullPointerException.class)
	public void testExtractImageUrlStringRaisesNullPointerException() {
		assertEquals("", provider.extractImageUrl((String) null));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExtractImageUrlStringRaisesIllegalArgumentException() {
		assertEquals("", provider.extractImageUrl("this is not an valid URL"));
	}


	@Test(expected=IllegalArgumentException.class)
	public void testExtractImageUrlInputStreamRaisesException() {
		assertEquals("", provider.extractImageUrl((InputStream) null));
	}
	
	@Test
	public void testExtractImageUrl() throws UnsupportedEncodingException {
		assertExtractedURL("", ".");
		assertExtractedURL("", "<html><body></body></html>");
		assertExtractedURL("http://path/image", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><content type=\"html\">href=\"http://path/image\"&gt;</content>");
	}
	
	private void assertExtractedURL(String expected, String input) throws UnsupportedEncodingException {
		InputStream ins = null;
		try {
			assertEquals(expected, provider.extractImageUrl(createInputSteram(input)));
		} finally {
			IOUtils.closeQuietly(ins);
		}
	}
	
	private InputStream createInputSteram(String string) throws UnsupportedEncodingException {
		return new ByteArrayInputStream(string.getBytes("UTF-8"));
	}
}
