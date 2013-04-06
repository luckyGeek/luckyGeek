/**********************************
 * PdfPrinter.java
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
 * Class for printing PDFs.
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

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * <b>Wrapper</b> for printing PDFs.
 */
class PdfPrinter {
	private static final Logger LOG = Logger.getLogger(PdfPrinter.class.getCanonicalName());
	
	void print(File file) throws FileNotFoundException {
		if (null == file) {
			throw new NullPointerException("Invalid file.");
		}
		if (!file.exists()) {
			throw new FileNotFoundException("Invalid file.");
		}
		
		PDDocument printable = null;
		try {
			printable = PDDocument.load(file);
			printable.silentPrint();
		} catch (PrinterException e) {
			LOG.warning(String.format("Can not print file. Message: %s.", e.getMessage()));
		} catch (IOException e) {
			LOG.warning(String.format("No file for printing found. Message %s.", e.getMessage()));
		} finally {
			closeQuietly(printable);
		}
	}

	private void closeQuietly(PDDocument printable) {
		if (printable != null) {
			try {
				printable.close();
			} catch (IOException e) {
				LOG.warning(String.format(
						"Error while closing printable document. Message: %s.", e.getMessage()));
			}
		}
	}

}
