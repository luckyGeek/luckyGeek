/**********************************
 *PdfBoxConverter.java
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
 *Converts an image to PDF using Apache PDF Box.
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

/**
 *Implementation of <code>{@link Converter}</code>. 
 *
 *<p/>
 *
 *Adapted from <code><a href="http://www.massapi.com/source/PDFBox-0.7.3/src/org/pdfbox/examples/pdmodel/ImageToPDF.java.html">
 *ImageToPDF</a></code> of PDF Box. 
 */
class PdfBoxConverter implements Converter {
    private static final Logger LOG = Logger.getLogger(PdfBoxConverter.class
            .getCanonicalName());

    @Override
    public boolean convert(File imageFile) {
        final String image = imageFile.getName().toLowerCase(Locale.getDefault());
        boolean result = false;
        PDDocument doc = null;
        PDPageContentStream contentStream = null;
        try {
            doc = new PDDocument();

            PDPage page = new PDPage();
            doc.addPage(page);

            PDXObjectImage ximage = null;
            if (image.endsWith(".jpg")) {
                ximage = new PDJpeg(doc, new FileInputStream(image));
            } else if (image.endsWith(".tif")
                    || image.endsWith(".tiff")) {
                ximage = new PDCcitt(doc, new RandomAccessFile(new File(image),
                        "r"));
            } else if (image.endsWith(".png")) {
                // Workaround from http://mail-archives.apache.org/mod_mbox/pdfbox-users/200811.mbox/%3CB47909413E1DC74AB3ED77495F95D65B05EA4CF7@s080a1030.group.rwe.com%3E
                BufferedImage bufferedImage = ImageIO.read(imageFile);
                ximage = new PDJpeg(doc, bufferedImage);
            } else {
                // BufferedImage awtImage = ImageIO.read( new File( image ) );
                // ximage = new PDPixelMap(doc, awtImage);
                throw new IOException("Image type not supported:" + image);
            }
            // new lines
            PDRectangle rectangle = page.getMediaBox();
            contentStream = new PDPageContentStream(doc, page);
            float width = getWidth(ximage, rectangle);
            float height = getHeight(ximage, rectangle);
            contentStream.drawXObject(ximage, rectangle.getLowerLeftX(), rectangle.getUpperRightY() - height, width, height);
            contentStream.close();
            doc.save(Configuration.getLastFileName());
            result = true;
        } catch (IOException e) {
            LOG.severe("Can not transform image to pdf: " + e.getMessage());
        } catch (COSVisitorException e) {
        	LOG.severe("Can not transform image to pdf: " + e.getMessage());
		} finally {
            closeDocument(doc);
        }
        return result;
    }
    
    private float getWidth(final PDXObjectImage image, final PDRectangle rectangle) {
        return image.getWidth() < rectangle.getWidth() ? image.getWidth() : rectangle.getWidth();
    }
    
    private float getHeight(final PDXObjectImage image, final PDRectangle rectangle) {
        return image.getHeight() < rectangle.getHeight() ? image.getHeight() : rectangle.getHeight();
    }
    
    private void closeDocument(PDDocument document) {
        try {
            if (document != null) {
                document.close();
            }
        } catch (Exception e) {
           LOG.finest("Error while closing document. " + e.getMessage());
        }
    }
}
