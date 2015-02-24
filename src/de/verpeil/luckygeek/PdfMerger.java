/**********************************
 *PdfMerger.java
 *Part of the project "luckyGeek" from
 *ctvoigt (Christian Voigt), chripo2701  2011.
 *
 *
 *Email: luckygeek@verpeil.de
 *
 *
 *
 **********************************
 *
 *Merges two PDFs.
 **********************************
 *
 *This program is free software; you can redistribute it
 *and/or modify it under the terms of the GNU General
 *Public License as published by the Free Software
 *Foundation; either version 2 of the License, or (at your
 *option) any later version.
 *This program is distributed in the hope that it will be
 *useful, but WITHOUT ANY WARRANTY; without even the implied
 *warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *PURPOSE. See the GNU General Public License for more details.
 *You should have received a copy of the GNU General Public
 *License along with this program; if not, write to the Free
 *Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *MA 02111-1307, USA.
 */

package de.verpeil.luckygeek;

import java.io.File;
import java.util.logging.Logger;

import org.apache.pdfbox.util.PDFMergerUtility;

/**
 *Merges two PDFs.
 */
class PdfMerger {
    private static final Logger LOG = Logger.getLogger(PdfMerger.class.getCanonicalName());
    
    boolean merge(File source, File append) {
        boolean result = false;
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        mergePdf.addSource(source);
        mergePdf.addSource(append);
        mergePdf.setDestinationFileName(source.getAbsolutePath());
        try {
            mergePdf.mergeDocuments();
            result = true;
        } catch (Exception e) {
            LOG.severe(String.format("Can not merge pdfs. Message: %s.", e.getMessage()));
        }
        return result;
    }
}
