/**********************************
 * JMagickConverter.java
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
 * Converts an image to a PDF-file using JMagick. Requires imagemagick.
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

import java.awt.Rectangle;
import java.io.File;
import java.util.logging.Logger;

import magick.ImageInfo;
import magick.MagickImage;

/**
 * Implementation of <code>{@link Converter}</code>. 
 */
class JMagickConverter implements Converter {
	private static final Logger LOG = Logger.getLogger(JMagickConverter.class.getCanonicalName());
	
	@Override
	public boolean convert(File imageFile) {
		boolean result = false;
		MagickImage originalImage = null;
		try {
			ImageInfo imageInfo = new ImageInfo(imageFile.getAbsolutePath());
			originalImage = new MagickImage(imageInfo);
			originalImage.setFileName(Configuration.getLastFileName());
			// fit for A4
			originalImage.cropImage(new Rectangle(0,0,960,720));
			
			ImageInfo pdf = new ImageInfo();
			originalImage.writeImage(pdf);
			result = true;
		} catch (Exception e) {
			LOG.severe(String.format("Can not scale image. Aborting. Message: %s.", e.getMessage()));
		} finally {
			if (originalImage != null) {
				originalImage.destroyImages();
			}
		}
		return result;
	}
}
