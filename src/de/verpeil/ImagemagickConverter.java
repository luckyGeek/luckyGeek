/**********************************
 * ImagickConverter.java
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
 * Converts an image to an pdf file. Uses im4java - the commandline version of imagemagick.
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
import java.util.logging.Logger;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

/**
 * Implementation of <code>{@link Converter}</code>.
 */
class ImagemagickConverter implements Converter {
	private static final Logger LOG = Logger
			.getLogger(ImagemagickConverter.class.getCanonicalName());

	@Override
	public boolean convert(File imageFile) {
		boolean result = false;
		try {
			ConvertCmd cmd = new ConvertCmd();
			// create the operation, add images and operators/options
			IMOperation op = new IMOperation();
			op.addImage(imageFile.getAbsolutePath());
			op.addImage(Configuration.getLastFileName());

			cmd.run(op);
			result = true;
		} catch (Exception e) {
			LOG.severe(String.format("Can not convert image. Aborting. Message: %s.", e.getMessage()));
		}
		return result;
	}
}
