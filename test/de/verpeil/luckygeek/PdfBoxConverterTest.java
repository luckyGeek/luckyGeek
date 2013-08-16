/**********************************
 *PdfBoxConverterTest.java
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
 * Test-case for PdfBoxConverter.java.
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
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests <code>{@link PdfBoxConverter}</code>.
 */ 
public class PdfBoxConverterTest {
    
    @BeforeClass
    public static void setUp() {
        assertTrue(UnittestUtil.setUp());
        UnittestUtil.createAllPdf();
    }
    
    @AfterClass
    public static void tearDown() {
        UnittestUtil.tearDown();
    }
    
    @Test
    public void testPdfBoxConverter() {
        final File converted = Configuration.getLastFile();
        assertFalse("Converted image exists.", converted.exists());
        
        final File image = new File(UnittestUtil.getTestResource(Configuration.getLastImageName()).getFile());
        assertTrue("Input image does not exists.", image.exists());
        
        final PdfBoxConverter converter = new PdfBoxConverter();
        assertTrue(converter.convert(image));
        
        assertTrue("Converted image is missing.", converted.exists());
        assertTrue("Converted image is empty.", converted.length() > Long.valueOf(0));
    }
}
