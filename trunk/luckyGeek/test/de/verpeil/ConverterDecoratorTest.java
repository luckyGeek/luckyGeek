/**********************************
 * ConverterDecoratorTest.java
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
 * Test-case for ConverterDecorator.java.
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**
 * Tests <code>{@link ConverterDecorator}</code>.
 */
public class ConverterDecoratorTest {
	
	@Test(expected=NullPointerException.class)
	public void testDecoratorRaisesException() {
		final ConverterDecorator decorator = new ConverterDecorator(null);
		assertNull(decorator.getConverter());
		decorator.convert(new File(Configuration.getLastImage()));
	}
	
	@Test
	public void testDecorator() {
		final ConverterDecorator decorator = new ConverterDecorator(new PdfBoxConverter());
		assertNotNull(decorator.getConverter());
		assertTrue(decorator.getConverter() instanceof PdfBoxConverter);
	}
}
