/**********************************
 * PdfPrinterTest.java
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
 * Tests PdfPrinter.java.
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

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests <code>{@link PdfPrinter}</code>. 
 */
public class PdfPrinterTest {
	private PdfPrinter printer = null;
	
	@Before
	public void before() {
		printer = new PdfPrinter();
		assertNotNull(printer);
	}
	
	@Test(expected=NullPointerException.class)
	public void testRaisesNullPointerException() throws IOException, PrinterException {
		printer.print(null);
	}
	
	@Test(expected=FileNotFoundException.class)
	public void testRaisesFileNotFoundException() throws IOException, PrinterException {
		printer.print(new File("non-existing"));
	}
}
