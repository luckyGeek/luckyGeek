/**********************************
 * ConversionTypesTest.java
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
 * Test-case for ConversionTypes.java.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.verpeil.luckygeek.ConversionTypes;
import de.verpeil.luckygeek.Converter;
import de.verpeil.luckygeek.ConverterDecorator;
import de.verpeil.luckygeek.ImagemagickConverter;
import de.verpeil.luckygeek.PdfBoxConverter;

/**
 * Tests <code>{@link ConversionTypes}</code>.
 */
public class ConversionTypesTest {

	@Test
	public void testParse() {
		assertParsedResult(ConversionTypes.IMAGEMAGICK);
		assertParsedResult(ConversionTypes.JMAGICK);
		assertParsedResult(ConversionTypes.PDFBOX);
	}
	
	private void assertParsedResult(final ConversionTypes type) {
		assertEquals(ConversionTypes.PDFBOX, ConversionTypes.parse(null));
		assertEquals(ConversionTypes.PDFBOX, ConversionTypes.parse(""));
		assertEquals(type, ConversionTypes.parse(type.name()));
		assertEquals(type, ConversionTypes.parse(type.name().toLowerCase()));
	}
	
	@Test
	public void testCreateConverter() {
		assertConverter(ConversionTypes.IMAGEMAGICK.createConverter());
		assertTrue(unwrap(ConversionTypes.IMAGEMAGICK.createConverter()) instanceof ImagemagickConverter);
		
		assertConverter(ConversionTypes.PDFBOX.createConverter());
		assertTrue(unwrap(ConversionTypes.PDFBOX.createConverter()) instanceof PdfBoxConverter);
	}
	
	private void assertConverter(final Converter converter) {
		assertNotNull(converter);
		assertTrue(converter instanceof ConverterDecorator);
	}
	
	private Converter unwrap(final Converter converter) {
		if (converter instanceof ConverterDecorator) {
			return((ConverterDecorator) converter).getConverter();
		}
		return converter;
	}
}
