/**********************************
 * ConverterDecorator.java
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
 * Class for decorating converters.
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

/**
 * <b>Decorator</b> of <code>{@link Converter}</code>. 
 */
class ConverterDecorator implements Converter {
	private static final Logger LOG = Logger.getLogger(ConverterDecorator.class.getCanonicalName());
	private final Converter converter;
	
	ConverterDecorator(final Converter converter) {
		LOG.fine("Adding converter: " + converter);
		this.converter = converter;
	}
	
	Converter getConverter() {
		return converter;
	}

	@Override
	public boolean convert(File imageFile) {
		LOG.fine("Converting image: " + imageFile.getAbsolutePath());
		boolean result = false;
		try {
			result = converter.convert(imageFile);
		} catch (Exception e) {
			LOG.severe("Error while converting image. Message: " + e.getMessage());
		}
		LOG.fine("Conversion was successful: " + Boolean.toString(result));
		return result;
	}

}
